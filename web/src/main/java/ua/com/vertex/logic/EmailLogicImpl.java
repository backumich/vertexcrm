package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ua.com.vertex.beans.UserFormRegistration;
import ua.com.vertex.controllers.UserController;
import ua.com.vertex.logic.interfaces.EmailLogic;
import ua.com.vertex.utils.Aes;

@Configuration
@PropertySource("classpath:emailMessages.properties")
class EmailLogicImpl implements EmailLogic {
    private static final String ENCRYPT_KEY = "VeRtEx AcAdeMy";

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Value("${registration.header}")
    private String header;

    @Value("${registration.body}")
    private String body;

    @Value("${registration.confirmationLink}")
    private String confirmationLink;

    @Value("${registration.footer}")
    private String footer;


    @Override
    public String createRegistrationMessage(UserFormRegistration user) {

        String stringEmailAES = "";
        try {
            stringEmailAES = Aes.encrypt(user.getEmail(), ENCRYPT_KEY);
        } catch (Exception e) {
            LOGGER.warn("While encrypting email any errors" + user.getEmail());
        }

        String emailMessage = header + user.getFirstName() + " " + user.getLastName() + "." + "\n"
                + body + "\n"
                + confirmationLink + "http://localhost:8080/activationUser?activeUser=" + stringEmailAES + "\n"
                + footer;

        return emailMessage;
    }
}
