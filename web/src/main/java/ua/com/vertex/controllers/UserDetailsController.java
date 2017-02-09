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
@SessionAttributes("users")
public class UserDetailsController {
    private static final String ERROR_JSP = "error";
    private static final String PAGE_JSP = "userDetails";
    private static final String SAVE_USER_DATA_OK_PAGE = "viewAllUsers";
    private static final String USERDATA_MODEL = "user";

    private UserLogic userLogic;
    private CertificateLogic certificateLogic;
    private BindingResult bindingResult;

    @Autowired
    public UserDetailsController(UserLogic userLogic, CertificateLogic certificateLogic) {
        this.userLogic = userLogic;
        this.certificateLogic = certificateLogic;
    }

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @RequestMapping(value = "/userDetails", method = RequestMethod.GET)
    public ModelAndView getUserDetails(@RequestParam("userId") int userId) {
        ModelAndView modelAndView = new ModelAndView();
        User user = null;
        try {
            user = userLogic.getUserDetailsByID(userId);
            //user = null;
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
            getListAllRoles(modelAndView);
            getAllCertificatesByUserId(userId, modelAndView);
        } else {
            LOGGER.debug("During preparation the all data for user ID - " + userId + " there was a database error");
            modelAndView.setViewName(ERROR_JSP);
        }


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

    @RequestMapping(value = "/saveUserData", method = RequestMethod.POST)
    public ModelAndView saveUserData(@RequestPart(value = "passportScan", required = false) byte[] passportScan,
                                     @RequestPart(value = "photo", required = false) byte[] photo,
                                     @Valid @ModelAttribute(USERDATA_MODEL) User user,
                                     BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("error", "WARNING!!! User data is updated but not saved");
            LOGGER.debug("Requested data are invalid for user ID - " + user.getUserId());
        }

        if (user != null) {
            try {
                if (passportScan != null) {
                    user.setPassportScan(passportScan);
                    modelAndView.addObject("imagePassportScan", userLogic.convertImage(user.getPassportScan()));
                    LOGGER.debug("Convert imagePassportScan and save to display for user ID - " + user.getUserId());
                }
            } catch (Exception e) {
                modelAndView.addObject("errorPassportScan", "An error occurred while processing passport scan");
                LOGGER.warn("An error occurred while converting imagePassportScan for user ID - " + user.getUserId());
            }

            try {
                if (photo != null) {
                    user.setPhoto(photo);
                    modelAndView.addObject("imagePhoto", userLogic.convertImage(user.getPhoto()));
                    LOGGER.debug("Convert imagePhoto and save to display for user ID - " + user.getUserId());
                }
            } catch (Exception e) {
                modelAndView.addObject("errorPhoto", "An error occurred while processing photo");
                LOGGER.warn("An error occurred while converting imagePhoto for user ID - " + user.getUserId());
            }
            try {
                if (userLogic.saveUserData(user) > 0) {
                    LOGGER.debug("Update user data successful for user ID - " + user.getUserId());
                } else {
                    LOGGER.debug("Update user data failed for user ID - " + user.getUserId());
                }
            } catch (Exception e) {
                LOGGER.debug("Update user data failed for user ID - " + user.getUserId());
            }
            //userLogic.saveUserData(user);

            if (user.getPassportScan() != null) {
                modelAndView.addObject("imagePassportScan", userLogic.convertImage(user.getPassportScan()));
                LOGGER.debug("Convert imagePassportScan and save to display for user ID - " + user.getUserId());
            }
            if (user.getPhoto() != null) {
                modelAndView.addObject("imagePhoto", userLogic.convertImage(user.getPhoto()));
                LOGGER.debug("Convert imagePassportScan and save to display for user ID - " + user.getUserId());
            }
            getListAllRoles(modelAndView);
            getAllCertificatesByUserId(user.getUserId(), modelAndView);
            modelAndView.setViewName(PAGE_JSP);
            //LOGGER.debug("Update user data successful for user ID - " + user.getUserId());

        } else {
            modelAndView.setViewName(ERROR_JSP);
            LOGGER.debug("Something went wrong for user ID - " + user.getUserId());

        }

        return modelAndView;
    }
}

