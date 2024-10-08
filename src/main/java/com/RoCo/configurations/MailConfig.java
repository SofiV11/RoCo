package com.RoCo.configurations;


import org.springframework.beans.PropertyAccessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.protocol}")
    private String protocol;


//    @Value("${spring.mail.debug}")
//    private String debug;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String isAuth;

    @Value("${spring.mail.smtp.socketFactory.class}")
    private String socketFactory;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String isStarttlsEnable;

    @Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setPort(port);

        Properties properties = mailSender.getJavaMailProperties();
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.smtp.auth", isAuth);
        properties.setProperty("mail.smtp.socketFactory.class", socketFactory);
        properties.setProperty("mail.smtp.starttls.enable", isStarttlsEnable);


        mailSender.setJavaMailProperties(properties);
        mailSender.setProtocol("smtps");

        return mailSender;
    }
}
