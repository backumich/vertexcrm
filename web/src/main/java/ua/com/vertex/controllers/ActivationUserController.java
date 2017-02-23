package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.utils.AES;


@Controller
@RequestMapping(value = "/activationUser", method = RequestMethod.GET)
public class ActivationUserController {

//    private static final String NAME_USER_MODEL_FOR_REGISTRATION_PAGE = "userFormRegistration";

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @GetMapping
    public ModelAndView activateUser(@RequestParam("activeUser") String encodedEmail) {
        String email = "";
        try {
            email = AES.decrypt(encodedEmail, "123");
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}

