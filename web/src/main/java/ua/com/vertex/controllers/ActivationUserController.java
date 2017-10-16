package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.Aes;


@Controller
@RequestMapping(value = "/activationUser", method = RequestMethod.GET)
public class ActivationUserController {
    private static final String ERROR_JSP = "error";
    private static final String PAGE_JSP = "successActivation";
    private static final String DECRYPT_KEY = "VeRtEx AcAdeMy";

    private static final Logger Logger = LogManager.getLogger(ActivationUserController.class);

    private UserLogic userLogic;

    @Autowired
    public ActivationUserController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    @GetMapping
    public ModelAndView activateUser(@RequestParam("activeUser") String encodedEmail) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PAGE_JSP);
        String email = "";

        try {
            email = Aes.decrypt(encodedEmail, DECRYPT_KEY);
            Logger.debug("Encrypted email " + encodedEmail);
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", "Your link is not correct");
            modelAndView.setViewName(ERROR_JSP);
            Logger.debug("Decrypt email failed", e);
        }

        try {
            if (userLogic.activateUser(email) != 1) {
                modelAndView.addObject("errorMessage", "This user is not registered |" + encodedEmail + "|");
                Logger.debug("Unsuccessful user activation for email |" + encodedEmail + "|");
                modelAndView.setViewName(ERROR_JSP);
            }
        } catch (Exception e) {
            modelAndView.setViewName(ERROR_JSP);
            Logger.debug("Activate user failed", e);
        }
        return modelAndView;
    }
}

