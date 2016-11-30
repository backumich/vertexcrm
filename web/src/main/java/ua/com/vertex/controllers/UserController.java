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

    private final UserLogic userLogic;

    private static final Logger loger = LogManager.getLogger(UserController.class);

    @Autowired
    public UserController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    @SuppressWarnings("SpringMVCViewInspection")
    @RequestMapping(value = "/getCertificateByUserId", method = RequestMethod.GET)
    public String getAllCertificateByUserId(@RequestParam("userId") int userId, Model model) {
        loger.trace("Test");
        loger.debug("Test");
        loger.info("Test");
        loger.warn("Test");
        loger.error("Test");
        loger.fatal("Test");
        model.addAttribute("certificates", userLogic.getAllCertificateByUserId(userId));
        return "user.jsp";
    }


}
