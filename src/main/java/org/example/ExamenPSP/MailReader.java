package org.example.ExamenPSP;

import jakarta.mail.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

public class MailReader {
    String username = "sendemailpsp@gmail.com";
    String password2FA = "vmfd iauj giiz vhcj";
    int port = 995;
    private final BufferedReader reader;
    private final PrintWriter writer;
    private final Properties properties;

    public MailReader(BufferedReader reader, PrintWriter writer) {
        this.reader = reader;
        this.writer = writer;
        properties = new Properties();
        properties.put("mail.store.protocol", "pop3");
        properties.put("mail.pop3.host", "pop.gmail.com");
        properties.put("mail.pop3.port", port);
        properties.put("mail.pop3.ssl.enable", "true");


    }

    public void readEmails( ) {
        try {
            Session session = Session.getInstance(properties);
            Store store = session.getStore("pop3");
            store.connect(username, password2FA);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            Message[] messages = inbox.getMessages();
            System.out.println("No of Messages : " + inbox.getMessageCount());
            writer.println("1 for next email, 2  to exit");
            for (Message message : messages) {
                if (reader.readLine().equals("2")) {
                    break;
                }
                writer.println("From: " + message.getFrom()[0]+
                        " Subject: " + message.getSubject()+
                        " Sent Date: " + message.getSentDate()+
                        " Content: " + message.getContent().toString());
            }
            inbox.close(false);
            store.close();
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

}
