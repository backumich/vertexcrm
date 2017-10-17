package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.vertex.logic.interfaces.EmailLogic;
import ua.com.vertex.utils.MailService;
import ua.com.vertex.utils.ReCaptchaService;

import javax.servlet.http.HttpServletRequest;

import static ua.com.vertex.controllers.RegistrationController.OUR_EMAIL;

@Controller
public class PasswordResetEmailController {
    private static final Logger LOGGER = LogManager.getLogger(PasswordResetEmailController.class);
    public static final String PASSWORD_RESET = "password/passwordReset";
    static final String EMAIL_SENT = "password/passwordResetEmailSent";
    static final String EMAIL_INVALID = "emailInvalid";
    static final String EMAIL = "email";
    static final String CAPTCHA_MISSED = "captcha";

    private final ReCaptchaService reCaptchaService;
    private final MailService mailService;
    private final EmailLogic emailLogic;

    @Autowired
    public PasswordResetEmailController(ReCaptchaService reCaptchaService,
                                        MailService mailService, EmailLogic emailLogic) {
        this.reCaptchaService = reCaptchaService;
        this.mailService = mailService;
        this.emailLogic = emailLogic;
    }

    @GetMapping(value = "/resetPassword")
    public String resetPassword() {
        LOGGER.debug("Password Reset page entered");
        return PASSWORD_RESET;
    }

    @GetMapping(value = "/sendEmail")
    public String sendEmail(@RequestParam String email, HttpServletRequest request, Model model) {
        String view = PASSWORD_RESET;

        if (!emailLogic.verifyEmail(email)) {
            emailNotValidatedOrCaptchaMissed(email, EMAIL_INVALID, "Email to change the password was invalid", model);

        } else if (!reCaptchaService.verify(request.getParameter("g-recaptcha-response"), request.getRemoteAddr())) {
            emailNotValidatedOrCaptchaMissed(email, CAPTCHA_MISSED, "", model);

        } else {
            mailService.sendMail(OUR_EMAIL, email, "Reset Your Password", emailLogic.createPasswordResetMessage(email));
            view = EMAIL_SENT;
            LOGGER.debug("Email to change the password was sent to " + email);
        }
        return view;
    }

    private void emailNotValidatedOrCaptchaMissed(String email, String attrName, String log, Model model) {
        model.addAttribute(EMAIL, email);
        model.addAttribute(attrName, true);
        if (!log.isEmpty()) {
            LOGGER.debug(log);
        }
    }
}