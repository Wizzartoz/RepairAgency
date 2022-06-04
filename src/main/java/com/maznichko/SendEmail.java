package com.maznichko;


import org.apache.log4j.Logger;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * This class sending message on email
 */
public class SendEmail extends Thread implements Sender {
    private static final Logger log = Logger.getLogger(SendEmail.class);
    private static final String user = "repairagent455@gmail.com";
    private static final String password = "dkmpirvdknocxtha";
    private String header;
    private String body;
    private String to;
    private static final Properties props = new Properties();

    static {
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");
    }

    Session session = Session.getDefaultInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password);
                }
            });

    @Override
    public void run() {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(header);
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException mex) {
            log.warn("<------------------ send message is failed");
            mex.printStackTrace();
        }
    }

    @Override
    public void send(String header, String body, String to) {
        this.header = header;
        this.body = body;
        this.to = to;
        start();
    }

}

