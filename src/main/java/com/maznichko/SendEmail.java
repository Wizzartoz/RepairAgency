package com.maznichko;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class SendEmail {
    final String username = "maznichkogame@gmail.com";
    final String password = "Maznichko455";
    private static final Properties prop = new Properties();

    static {
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
    }
    Session session = Session.getInstance(prop,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

    public void sendEmail(){
        try {
            Class.forName("javax.activation.DataHandler");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("maznichkogame@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("mmaznicko@gmail.com")
            );
            message.setSubject("Testing Gmail TLS");
            message.setText("Dear Mail Crawler,"
                    + "\n\n Please do not spam my email!");

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}

