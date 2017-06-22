package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.CertificateLogic;
import ua.com.vertex.logic.interfaces.UserLogic;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserDetailsController {
    private static final String ERROR_JSP = "error";
    private static final String PAGE_JSP = "userDetails";
    private static final String USERDATA_MODEL = "user";

    private UserLogic userLogic;
    private CertificateLogic certificateLogic;

    private static final Logger LOGGER = LogManager.getLogger(UserDetailsController.class);

    @RequestMapping(value = "/userDetails", method = RequestMethod.GET)
    public ModelAndView getUserDetails(@RequestParam("userId") int userId) {
        ModelAndView modelAndView = new ModelAndView();

        // -- Get user data by ID
        try {
            Optional<User> optionalUser = userLogic.getUserDetailsByID(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                modelAndView.setViewName(PAGE_JSP);
                modelAndView.addObject("user", user);
            }
            LOGGER.debug("Get full data for user ID - " + userId);
        } catch (Exception e) {
            LOGGER.debug("During preparation the all data for user ID - " + userId + " there was a database error", e);
            modelAndView.setViewName(ERROR_JSP);
        }

        //  -- Get all system roles
        try {
            modelAndView.addObject("allRoles", userLogic.getAllRoles());
            LOGGER.debug("We received all the roles of the system");
        } catch (Exception ignore) {
            LOGGER.warn("There are problems with access to roles of the system", ignore);
        }

        //  -- Get all user certificate
        try {
            modelAndView.addObject("certificates", certificateLogic.getAllCertificatesByUserIdFullData(userId));
            LOGGER.debug("We received all the roles of the system");
        } catch (Exception ignore) {
            LOGGER.warn("There are problems with access to roles of the system", ignore);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/saveUserData", method = RequestMethod.POST)
    public ModelAndView saveUserData(@RequestPart(value = "imagePassportScan", required = false) MultipartFile imagePassportScan,
                                     @RequestPart(value = "imagePhoto", required = false) MultipartFile imagePhoto,
                                     @Valid @ModelAttribute(USERDATA_MODEL) User user,
                                     BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName(PAGE_JSP);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("msg", "WARNING!!! User data is updated, but not saved");
            LOGGER.debug("Requested data are invalid for user ID - " + user.getUserId());
        } else {
            // -- check image files
            try {
                if (checkImageFile(imagePassportScan)) {
                    user.setPassportScan(imagePassportScan.getBytes());
                    LOGGER.debug("Checked passportScan file for user ID - " + user.getUserId());
                } else {
                    LOGGER.debug("Checked passportScan file is not image for user ID - " + user.getUserId());
                }
                if (checkImageFile(imagePhoto)) {
                    user.setPhoto(imagePhoto.getBytes());
                    LOGGER.debug("Checked photo file for user ID - " + user.getUserId());
                } else {
                    LOGGER.debug("Checked photo file is not image for user ID - " + user.getUserId());
                }
            } catch (Exception e) {
                LOGGER.warn("An error occurred when working with image for user ID - " + user.getUserId(), e);
            }
            // -- check correct update user data
            try {
                if (userLogic.saveUserData(user) == 1) {
                    modelAndView.addObject("msg", "Congratulations! Your data is saved!");
                    LOGGER.debug("Update user data successful for user ID - " + user.getUserId());
                } else {
                    modelAndView.setViewName(ERROR_JSP);
                    LOGGER.debug("Update user data failed for user ID - " + user.getUserId());
                }
            } catch (Exception e) {
                modelAndView.setViewName(ERROR_JSP);
                LOGGER.debug("Update user data failed for user ID - " + user.getUserId(), e);
            }
        }

        //  -- Get all system roles
        try {
            modelAndView.addObject("allRoles", userLogic.getAllRoles());
            LOGGER.debug("We received all the roles of the system");
        } catch (Exception e) {
            modelAndView.setViewName(ERROR_JSP);
            LOGGER.debug("There are problems with access to roles of the system", e);
        }

        //  -- Get all user certificates
        try {
            modelAndView.addObject("certificates", certificateLogic.getAllCertificatesByUserIdFullData(user.getUserId()));
            LOGGER.debug("Received all roles");
        } catch (Exception e) {
            modelAndView.setViewName(ERROR_JSP);
            LOGGER.debug("There are problems with access to roles of the system", e);
        }
        return modelAndView;
    }

    private boolean checkImageFile(MultipartFile file) {
        return !file.isEmpty() && file.getContentType().split("/")[0].equals("image");
    }

    @Autowired
    public UserDetailsController(UserLogic userLogic, CertificateLogic certificateLogic) {
        this.userLogic = userLogic;
        this.certificateLogic = certificateLogic;
    }

}

