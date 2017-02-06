package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.CertificateLogic;
import ua.com.vertex.logic.interfaces.UserLogic;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Controller
//@RequestMapping(value = "/userDetails")
@SessionAttributes("users")
public class UserDetailsController {
    private static final String ERROR_JSP = "error";
    private static final String PAGE_JSP = "userDetails";
    private static final String SAVE_USER_DATA_OK_PAGE = "viewAllUsers";
    private static final String USERDATA_MODEL = "user";

    private UserLogic userLogic;
    private CertificateLogic certificateLogic;

    @Autowired
    public UserDetailsController(UserLogic userLogic, CertificateLogic certificateLogic) {
        this.userLogic = userLogic;
        this.certificateLogic = certificateLogic;
    }


    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    //    @GetMapping
    @RequestMapping(value = "/userDetails", method = RequestMethod.GET)
    public ModelAndView getUserDetailsByID(@RequestParam("userId") int userId) {
        ModelAndView modelAndView = new ModelAndView();

        User user = null;
        try {
            user = userLogic.getUserDetailsByID(userId);

            LOGGER.debug("Get full data for user ID - " + userId);
        } catch (DataAccessException | SQLException e) {
            LOGGER.debug("During preparation the all data for user ID - " + userId + " there was a database error");
            modelAndView.setViewName(ERROR_JSP);
        }

        if (user != null) {
            modelAndView.setViewName(PAGE_JSP);
            modelAndView.addObject("user", user);
            try {
                modelAndView.addObject("imagePassportScan", userLogic.convertImage(user.getPassportScan()));
                LOGGER.debug("Passports scan is obtained and converted for user ID - " + userId);
            } catch (Throwable t) {
                LOGGER.warn("There are problems with access to passports scan for user ID - " + userId);
            }
            try {
                modelAndView.addObject("imagePhoto", userLogic.convertImage(user.getPhoto()));
                LOGGER.debug("Photo is obtained and converted for user ID - " + userId);
            } catch (Throwable t) {
                LOGGER.warn("There are problems with access to photos for user ID - " + userId);
            }
        }
//        try {
//            HashMap<Role, Role> allRoles = userLogic.getListAllRoles();
//            modelAndView.addObject("allRoles", allRoles);
//            LOGGER.debug("We received all the roles of the system");
//        } catch (Exception e) {
//            LOGGER.debug("There are problems with access to roles of the system");
//        }
//        try {
//            List<Certificate> certificates = certificateLogic.getAllCertificatesByUserId(userId);
//            modelAndView.addObject("certificates", certificates);
//            LOGGER.debug("We received all the roles of the system");
//        } catch (Exception e) {
//            LOGGER.debug("There are problems with access to roles of the system");
//        }
        getListAllRoles(modelAndView);
        getAllCertificatesByUserId(userId, modelAndView);

        return modelAndView;
    }

    private void getAllCertificatesByUserId(@RequestParam("userId") int userId, ModelAndView modelAndView) {
        try {
            List<Certificate> certificates = certificateLogic.getAllCertificatesByUserIdFullData(userId);
            modelAndView.addObject("certificates", certificates);
            LOGGER.debug("We received all the roles of the system");
        } catch (Exception e) {
            LOGGER.debug("There are problems with access to roles of the system");
        }
    }

    private void getListAllRoles(ModelAndView modelAndView) {
        try {
            HashMap<Role, Role> allRoles = userLogic.getListAllRoles();
            modelAndView.addObject("allRoles", allRoles);
            LOGGER.debug("We received all the roles of the system");
        } catch (Exception e) {
            LOGGER.debug("There are problems with access to roles of the system");
        }
    }

    //@PostMapping
    @RequestMapping(value = "/saveUserData", method = RequestMethod.POST)
    public ModelAndView saveUserData(@Valid @ModelAttribute(USERDATA_MODEL) User user, BindingResult bindingResult, ModelAndView modelAndView) {


        if (!bindingResult.hasErrors()) {

        } else {

        }

        getListAllRoles(modelAndView);
        getAllCertificatesByUserId(user.getUserId(), modelAndView);
        modelAndView.setViewName(PAGE_JSP);
        return modelAndView;
    }


}

