package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.vertex.beans.PasswordResetDto;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.ReCaptchaService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static ua.com.vertex.controllers.PasswordResetEmailController.CAPTCHA_MISSED;

@Controller
public class PasswordUpdateController {
    private static final Logger LOGGER = LogManager.getLogger(PasswordUpdateController.class);
    static final String PASSWORD_ENTER_NEW = "password/passwordEnterNew";
    static final String PASSWORD_SAVED = "password/passwordSaved";
    static final String PASSWORD_INVALID = "passwordInvalid";
    static final String PASSWORD_MISMATCH = "passwordMismatch";
    static final String EXPIRED = "expired";
    static final String DTO = "passwordDto";

    private final ReCaptchaService reCaptchaService;
    private final UserLogic userLogic;

    @Autowired
    public PasswordUpdateController(ReCaptchaService reCaptchaService, UserLogic userLogic) {
        this.reCaptchaService = reCaptchaService;
        this.userLogic = userLogic;
    }

    @GetMapping(value = "/passwordEnterNew")
    public String passwordEnterNew(@RequestParam String id, @RequestParam String uuid, Model model) {
        String email = userLogic.getEmailByUuid(Long.parseLong(id), uuid);
        if (!email.isEmpty()) {
            model.addAttribute(DTO, PasswordResetDto.builder().email(email).build());
        } else {
            model.addAttribute(EXPIRED, true);
            LOGGER.debug("Link to restore password has expired");
        }
        return PASSWORD_ENTER_NEW;
    }

    @PostMapping(value = "/passwordSaveNew")
    public String passwordSaveNew(@Valid @ModelAttribute PasswordResetDto passwordDto,
                                  BindingResult result, HttpServletRequest request, Model model) {
        String view = PASSWORD_SAVED;
        String email = passwordDto.getEmail();

        if (result.hasErrors()) {
            view = PASSWORD_ENTER_NEW;
            passwordNotValidated(email, PASSWORD_INVALID, "New password validation error", model);

        } else if (!passwordDto.getRawPassword().equals(passwordDto.getRepeatPassword())) {
            view = PASSWORD_ENTER_NEW;
            passwordNotValidated(email, PASSWORD_MISMATCH, "Mismatching passwords", model);

        } else {
            if (reCaptchaService.verify(request.getParameter("g-recaptcha-response"), request.getRemoteAddr())) {
                userLogic.savePassword(passwordDto.getEmail(), passwordDto.getRawPassword());
            } else {
                view = PASSWORD_ENTER_NEW;
                passwordNotValidated(email, CAPTCHA_MISSED, "", model);
            }
        }
        return view;
    }

    private void passwordNotValidated(String email, String attrName, String log, Model model) {
        model.addAttribute(DTO, PasswordResetDto.builder().email(email).build());
        model.addAttribute(attrName, true);
        if (!log.isEmpty()) {
            LOGGER.debug(log);
        }
    }
}
