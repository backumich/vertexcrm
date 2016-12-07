package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.vertex.logic.UserLogic;


@Controller
public class UserController {

    @SuppressWarnings("WeakerAccess")
    public static final String CERTIFICATES = "certificates";
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    private final UserLogic userLogic;

    @Autowired
    public UserController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    @SuppressWarnings("SpringMVCViewInspection")
    @RequestMapping(value = "/getCertificateByUserId", method = RequestMethod.GET)
    // TODO: 07.12.2016  Maybe to change the method name into getAllCertificatesByUserId ("s" is missing)
    public String getAllCertificateByUserId(@RequestParam("userId") int userId, Model model) {
        
        LOGGER.info("Request to '/getCertificateByUserId', call  - model.addAttribute()");
        model.addAttribute(CERTIFICATES, userLogic.getAllCertificateByUserId(userId));
        // TODO: 07.12.2016 I think you need to return cetrificates.jsp, because you get all the certificates, but not all the users 
        LOGGER.info("Request to '/getCertificateByUserId' return 'user.jsp' ");
        return "user";
    }


}
