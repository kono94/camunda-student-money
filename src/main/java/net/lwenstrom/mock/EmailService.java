package net.lwenstrom.mock;

import java.net.URI;
import java.util.logging.Logger;

public class EmailService {
    private final Logger LOGGER = Logger.getLogger(EmailService.class.getName());

    public EmailService(){

    }
    public void sendMail(URI receiver, String payload){
        LOGGER.info("Email sent! \n Payload: \n\n" + payload);
    }
}
