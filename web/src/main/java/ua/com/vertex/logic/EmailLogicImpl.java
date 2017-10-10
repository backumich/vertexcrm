package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ua.com.vertex.beans.UserFormRegistration;
import ua.com.vertex.controllers.exceptionHandling.PasswordResetEmailNotFound;
import ua.com.vertex.logic.interfaces.EmailLogic;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.Aes;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.time.LocalDateTime;
import java.util.UUID;

@Configuration
@PropertySource("classpath:emailMessages.properties")
class EmailLogicImpl implements EmailLogic {
    private static final Logger LOGGER = LogManager.getLogger(EmailLogicImpl.class);
    private static final String ENCRYPT_KEY = "VeRtEx AcAdeMy";
    private final UserLogic userLogic;

    @Value("${registration.header}")
    private String header;

    @Value("${registration.body}")
    private String body;

    @Value("${registration.confirmationLink}")
    private String confirmationLink;

    @Value("${registration.footer}")
    private String footer;

    @Autowired
    public EmailLogicImpl(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    @Override
    public String createRegistrationMessage(UserFormRegistration user) {

        LOGGER.debug(String.format("Call - emailLogicImpl.createRegistrationMessage(%s) ;", user));
        String stringEmailAES = "";
        try {
            stringEmailAES = Aes.encrypt(user.getEmail(), ENCRYPT_KEY);
        } catch (Exception e) {
            LOGGER.warn("While encrypting email any errors" + user.getEmail());
        }

        return header + user.getFirstName() + " " + user.getLastName() + "." + "\n"
                + body + "\n"
                + confirmationLink + "http://localhost:8080/activationUser?activeUser=" + stringEmailAES + "\n"
                + footer;
    }

    @Override
    public String createPasswordResetMessage(String email) {
        String uuid = UUID.randomUUID().toString();
        long id = userLogic.setParamsToRestorePassword(email, uuid, LocalDateTime.now());

        String passwordResetLink = String.format("http://localhost:8080/passwordEnterNew?id=%d&uuid=%s", id, uuid);
        String passwordResetPage = "http://localhost:8080/resetPassword";
        LOGGER.debug("Message to reset password was compiled");

        return String.format("You can use the following link to reset your password:%n%n%s%n%n" +
                "If you don’t use this link within 3 hours, it will expire. " +
                "To get a new password reset link, visit %s", passwordResetLink, passwordResetPage);
    }

    @Override
    public boolean verifyEmail(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException e) {
            result = false;
        }
        if (!userLogic.userForRegistrationCheck(email).isPresent()) {
            throw new PasswordResetEmailNotFound(String.format("Email to reset the password %s not found in database",
                    email));
        }
        return result;
    }
}
