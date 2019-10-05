package de.flashheart.rlgserver.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class ApplicationConfiguration {

	/**
	 * The password encoder to use when encrypting passwords.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	public Map<String, String> myMap() {
	    final Map<String, String> myMap = new HashMap<>();
	    myMap.put("A", "a");
	    return myMap;
	}

//	@Bean
//	public JavaMailSender getJavaMailSender() {
//	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
////	    mailSender.setHost("wp12617924.mailout.server-he.de");
////	    mailSender.setPort(587);
////
////
////
////	    mailSender.setUsername("wp12617924-13");
////	    mailSender.setPassword("ZoMucE@)66iRF"); // this password is already obsolete. so don't bother trying it out.
////
//	    Properties props = mailSender.getJavaMailProperties();
//	    props.put("mail.transport.protocol", "smtp");
//	    props.put("mail.smtp.auth", "true");
//	    props.put("mail.smtp.starttls.enable", "true");
//	    props.put("mail.debug", "true");
//
//	    return mailSender;
//	}

//	@Bean
//	public SimpleMailMessage templateSimpleMessage() {
//	    SimpleMailMessage message = new SimpleMailMessage();
//	    message.setText(
//	      "This is the test email template for your email:\n%s\n");
//	    return message;
//	}

}
