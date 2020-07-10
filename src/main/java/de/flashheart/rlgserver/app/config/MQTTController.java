package de.flashheart.rlgserver.app.config;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import de.flashheart.rlgserver.app.misc.HasLogger;
import de.flashheart.rlgserver.app.misc.NotificationService;
import de.flashheart.rlgserver.backend.service.CoolingDeviceService;
import de.flashheart.rlgserver.backend.service.ReadingService;
import org.apache.commons.lang3.StringUtils;
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
import java.math.BigDecimal;
import java.util.Properties;

@Configuration
public class MQTTController implements HasLogger {
    private final ReadingService readingService;
    private final CoolingDeviceService coolingDeviceService;
    private final NotificationService notificationService;

    public MQTTController(ReadingService readingService, CoolingDeviceService coolingDeviceService, NotificationService notificationService) {
        this.readingService = readingService;
        this.coolingDeviceService = coolingDeviceService;
        this.notificationService = notificationService;
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
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(mqtturl, mqttid, "#");
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
                String command = StringUtils.substringAfterLast(topic, "/");
                String[] payload = StringUtils.split(message.getPayload().toString(), ";");

                /**
                 *
                 * mosquitto_pub -h mqtt -m "keypart1=status.raid;value=Optimal" -t incoming/from/lvolume@srv00/reading
                 * mosquitto_pub -h mqtt -m "keypart1=status.raid.num.failed.devices;value=0" -t incoming/from/lvolume@srv00/reading
                 *
                 */
                if (topic.startsWith("incoming/from/")){
                    String[] elements = topic.split("/");
                    if (elements.length == 4){
                        String service = elements[2].split("@")[0];
                        String host = elements[2].split("@")[1];
                        String subject = elements[3];

                        Properties pload = get_properties_from(message.getPayload().toString());
                        
                        getLogger().debug(pload.toString());



                    }
                }

                if (command.equalsIgnoreCase("cd")) { // create device
                    BigDecimal min = BigDecimal.valueOf(Double.parseDouble(payload[2]));
                    BigDecimal max = BigDecimal.valueOf(Double.parseDouble(payload[3]));
                    coolingDeviceService.createOrUpdate(payload[0], payload[1], min, max);
                } else if (command.equalsIgnoreCase("cr")) { // create reading
                    BigDecimal value = BigDecimal.valueOf(Double.parseDouble(payload[1]));
                    readingService.create(payload[0], value);
                    coolingDeviceService.findByUuid(payload[0]).ifPresent(coolingDevice -> notificationService.addEvent(coolingDevice, value));
                } else if (command.equalsIgnoreCase("dm")) { // device missing
                    coolingDeviceService.findByUuid(payload[0]).ifPresent(coolingDevice -> notificationService.addMissingDeviceEvent(coolingDevice));
                }
            } catch (NumberFormatException nfe) {
                getLogger().warn(nfe.toString());
            } catch (Exception e) {
                getLogger().error(e.toString());
            }
        };
    }

    private Properties get_properties_from(String s) throws IOException {
        final Properties p = new Properties();
        p.load(new StringReader(s.replace(";","\n")));
        return p;
    }

}
