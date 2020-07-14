package de.flashheart.rlgserver.app.config;

import de.flashheart.rlgserver.app.misc.HasLogger;
import de.flashheart.rlgserver.app.misc.NotificationService;
import de.flashheart.rlgserver.backend.data.entity.IncomingMessage;
import de.flashheart.rlgserver.backend.service.IncomingMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.io.IOException;
import java.io.StringReader;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Properties;

@Configuration
public class MQTTController implements HasLogger {
    private final NotificationService notificationService;
    private final IncomingMessageService incomingMessageService;


    public MQTTController(NotificationService notificationService, IncomingMessageService incomingMessageService) {
        this.notificationService = notificationService;
        this.incomingMessageService = incomingMessageService;
    }

    @Value("${spring.mqtt.url}")
    public String mqtturl;
    @Value("${spring.mqtt.clientid}")
    public String mqttid;
    @Value("${spring.mqtt.topic}")
    public String mqttopic; // dont forget the closing '/'

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(mqtturl, mqttid, mqttopic + "#");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(2);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return message -> {
            try {
                getLogger().debug(message.getHeaders().get("mqtt_receivedTopic").toString());
                getLogger().debug(message.getPayload().toString());
                getLogger().debug(message.getHeaders().toString());
                String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
//                String command = StringUtils.substringAfterLast(topic, "/");
//                String[] payload = StringUtils.split(message.getPayload().toString(), ";");

                /**
                 *
                 * mosquitto_pub -h mqtt -m "keypart1=status.raid;value=Optimal" -t incoming/from/lvolume@srv00/reading
                 * mosquitto_pub -h mqtt -m "keypart1=status.raid.num.failed.devices;value=0" -t incoming/from/lvolume@srv00/reading
                 *
                 * im/ service=28-01143bb438aa;host=iot01;value=-23.80
                 * im/ service=28-02159245cb05;host=iot01;value=-24.40
                 * im/ service=28-01145e9e64d6;host=iot01;value=1.50
                 * im/ service=28-01143b9d24aa;host=iot01;value=-23.80
                 * im/ service=28-01143b87deaa;host=iot01;value=-20.80
                 *
                 * im host=srv02;service=db-kueche-prod;pit=2020-07-13T17:06:02+02:00;subject=backup;key1=crc.table;key2=vorrat;value=3766279728;reference=
                 *
                 */

                Properties pload = get_properties_from(message.getPayload().toString());

                getLogger().debug(pload.toString());

                Optional<IncomingMessage> incomingMessage = incomingMessageService.create(
                        pload.getProperty("host", "unknown"),
                        pload.getProperty("service", "unknown"),
                        pload.getProperty("pit", null),
                        pload.getProperty("subject", "error"),
                        pload.getProperty("reference", null),
                        pload.getProperty("key1", "error"),
                        pload.getProperty("key2", null),
                        pload.getProperty("value", "error")
                );

//                if (command.equalsIgnoreCase("cd")) { // create device
//                    BigDecimal min = BigDecimal.valueOf(Double.parseDouble(payload[2]));
//                    BigDecimal max = BigDecimal.valueOf(Double.parseDouble(payload[3]));
//                    coolingDeviceService.createOrUpdate(payload[0], payload[1], min, max);
//                } else if (command.equalsIgnoreCase("cr")) { // create reading
//                    BigDecimal value = BigDecimal.valueOf(Double.parseDouble(payload[1]));
//                    readingService.create(payload[0], value);
//                    coolingDeviceService.findByUuid(payload[0]).ifPresent(coolingDevice -> notificationService.addEvent(coolingDevice, value));
//                } else if (command.equalsIgnoreCase("dm")) { // device missing
//                    coolingDeviceService.findByUuid(payload[0]).ifPresent(coolingDevice -> notificationService.addMissingDeviceEvent(coolingDevice));
//                }
            } catch (NumberFormatException nfe) {
                getLogger().warn(nfe.toString());
            } catch (Exception e) {
                getLogger().error(e.toString());
            }
        };
    }

    private Properties get_properties_from(String s) throws IOException {
        final Properties p = new Properties();
        p.load(new StringReader(s.replace(";", "\n")));
        return p;
    }

}
