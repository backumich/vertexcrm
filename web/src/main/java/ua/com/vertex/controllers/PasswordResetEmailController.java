package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ua.com.vertex.beans.PasswordResetDto;
import ua.com.vertex.logic.interfaces.EmailLogic;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.MailService;
import ua.com.vertex.utils.ReCaptchaService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static ua.com.vertex.logic.RegistrationUserLogicImpl.OUR_EMAIL;

@Controller
public class PasswordResetEmailController {
    private static final Logger LOGGER = LogManager.getLogger(PasswordResetEmailController.class);
    public static final String PASSWORD_RESET = "password/passwordReset";
    static final String EMAIL_SENT = "password/passwordResetEmailSent";
    static final String EMAIL_INVALID = "emailInvalid";
    static final String EMAIL = "email";
    static final String EMAIL_NOT_FOUND = "emailNotFound";
    static final String CAPTCHA_MISSED = "captcha";

    private final ReCaptchaService reCaptchaService;
    private final MailService mailService;
    private final EmailLogic emailLogic;
    private final UserLogic userLogic;

    @Autowired
    public PasswordResetEmailController(ReCaptchaService reCaptchaService, MailService mailService,
                                        EmailLogic emailLogic, UserLogic userLogic) {
        this.reCaptchaService = reCaptchaService;
        this.mailService = mailService;
        this.emailLogic = emailLogic;
        this.userLogic = userLogic;
    }

    @GetMapping(value = "/resetPassword")
    public String resetPassword(Model model) {
        LOGGER.debug("Password Reset page entered");
        model.addAttribute(new PasswordResetDto());
        return PASSWORD_RESET;
    }

    @GetMapping(value = "/sendEmail")
    public String sendEmail(@Valid @ModelAttribute PasswordResetDto dto, BindingResult result,
                            HttpServletRequest request, Model model) {
        String view = PASSWORD_RESET;
        String email = dto.getEmail();

        if (result.hasErrors()) {
            emailNotValidatedOrCaptchaMissed(email, EMAIL_INVALID, "Email to change the password was invalid", model);

        } else if (!userLogic.isUserRegisteredAndActive(email)) {
            emailNotValidatedOrCaptchaMissed(email, EMAIL_NOT_FOUND,
                    "This email is not registered or has not been activated yet", model);

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