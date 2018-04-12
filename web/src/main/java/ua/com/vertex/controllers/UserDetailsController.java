package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.CertificateLogic;
import ua.com.vertex.logic.interfaces.UserLogic;

import javax.validation.Valid;
import java.io.IOException;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView getUserDetails(@RequestParam("userId") int userId) {
        ModelAndView modelAndView = new ModelAndView();

        Optional<User> optionalUser = userLogic.getUserById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            modelAndView.setViewName(PAGE_JSP);
            modelAndView.addObject("user", user);
        }
        LOGGER.debug("Get full data for user ID - " + userId);

        modelAndView.addObject("allRoles", Role.values());
        LOGGER.debug("We received all the roles of the system");

        modelAndView.addObject("certificates", certificateLogic.getAllCertificatesByUserIdFullData(userId));
        LOGGER.debug("Get all user certificates by userID - " + userId);
        return modelAndView;
    }

    @RequestMapping(value = "/saveUserData", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView saveUserData(
            @RequestPart(value = "imagePassportScan", required = false) MultipartFile imagePassportScan,
            @RequestPart(value = "imagePhoto", required = false) MultipartFile imagePhoto,
            @Valid @ModelAttribute(USERDATA_MODEL) User user,
            BindingResult bindingResult, ModelAndView modelAndView) throws IOException {

        modelAndView.setViewName(PAGE_JSP);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("msg",
                    "We'd like to save your changes but we can't while one or more fields are invalid");
            LOGGER.debug("Requested data are invalid for user ID - " + user.getUserId());
        } else {
            if (userLogic.validateMultipartFileWithBindingResult(imagePassportScan, bindingResult, "passportScan")) {
                user.setPassportScan(imagePassportScan.getBytes());
            }
            if (userLogic.validateMultipartFileWithBindingResult(imagePhoto, bindingResult, "photo")) {
                user.setPhoto(imagePhoto.getBytes());
            }
            if (userLogic.saveUserData(user) == 1) {
                modelAndView.addObject("msg", "Congratulations! Your data is saved!");
                LOGGER.debug("Update user data successful for user ID - " + user.getUserId());
            } else {
                modelAndView.setViewName(ERROR_JSP);
                LOGGER.debug("Update user data failed for user ID - " + user.getUserId());
            }
        }

        modelAndView.addObject("allRoles", Role.values());
        LOGGER.debug("We received all the roles of the system");

        modelAndView.addObject("certificates", certificateLogic.getAllCertificatesByUserIdFullData(user.getUserId()));
        LOGGER.debug("Received all roles");
        return modelAndView;
    }

    @Autowired
    public UserDetailsController(UserLogic userLogic, CertificateLogic certificateLogic) {
        this.userLogic = userLogic;
        this.certificateLogic = certificateLogic;
    }
}
