package org.example.ExamenPSP;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Logger;

public class MailSender {
    private final BufferedReader reader;
    private final PrintWriter writer;
    private HashMap<String, String> mailData;
    private Properties properties;
    private final Session session;
    private final Message message;

    public MailSender(BufferedReader reader, PrintWriter writer) {
        this.reader = reader;
        this.writer = writer;
        mailData = new HashMap<>();
        mailData.put("sender", "");
        mailData.put("receiver", "");
        mailData.put("subject", "");
        mailData.put("message", "");
        properties = new Properties();
        properties.put("mail.smtp.host", "aspmx.l.google.com");
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.auth", "false");
        properties.put("mail.smtp.starttls.enable", "true");
        session = Session.getDefaultInstance(properties);
        message = new MimeMessage(session);
    }

    public void startEmailHandler() throws IOException {
        for (String key : mailData.keySet()) {
            writer.println("Enter " + key + ": ");
            mailData.put(key, reader.readLine());
        }
        try {
            sendEmail();
        } catch (MessagingException e) {
            Logger.getGlobal().severe("Failed to send email: " + e.getMessage());
            writer.println("Failed to send email: " + e.getMessage());
        }
    }

    private void sendEmail() throws MessagingException {
        message.setFrom(new InternetAddress(mailData.get("sender")));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailData.get("receiver")));
        message.setSubject(mailData.get("subject"));
        message.setText(mailData.get("message"));
        jakarta.mail.Transport.send(message);
    }
}
