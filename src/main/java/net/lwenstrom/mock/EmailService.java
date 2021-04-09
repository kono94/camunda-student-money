package net.lwenstrom.mock;


import org.apache.pdfbox.io.IOUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.Properties;
import java.util.logging.Logger;

public class EmailService {
    private final Logger LOGGER = Logger.getLogger(EmailService.class.getName());

    private Properties prop;
    private Session session;
    public EmailService(){
        prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.mailtrap.io");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

        session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("e5692a716eeef7", "7b3f2124e4faec");
            }
        });
    }

    public void sendMail(String receiver, String payload) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("gpma.addmin@gmail.com"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(replaceSpecialChars(receiver)));
        message.setSubject("Begrüßungsgeldantrag");

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(payload, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);

        Transport.send(message);

        LOGGER.info("Email sent! \n Payload: \n\n" + payload);
    }

    public void sendMailWithAttachment(String receiver, String payload, InputStream pdfInputStream) throws MessagingException, IOException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("gpma.addmin@gmail.com"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(replaceSpecialChars(receiver)));
        message.setSubject("PDF-Antrag Begrüßungsgeld");

        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setContent(payload, "text/html; charset=utf-8");



        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        final File tempFile = File.createTempFile("tmpRequest", ".pdf");
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(pdfInputStream, out);
        }
        DataSource source = new FileDataSource(tempFile);
        mimeBodyPart.setDataHandler(new DataHandler(source));
        mimeBodyPart.setFileName("Antrag.pdf");


        Multipart multipart = new MimeMultipart("mixed");
        multipart.addBodyPart(textBodyPart);
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);

        Transport.send(message);

        LOGGER.info("Email sent! \n Payload: \n\n" + payload);
    }

    private String replaceSpecialChars(String s){
        return s.replace("ö","oe")
                .replace("ä","ae")
                .replace("ü","ue")
                .replace("ß","ss");
    }
}
