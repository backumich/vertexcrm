package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.UserLogic;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
//@RequestMapping(value = "saveUserData")
//@RequestMapping(value = "/saveUserData", method = RequestMethod.POST)
public class SaveUserDataController {

    static final String USER_DETAIL_PAGE = "userDetails";
    static final String SAVE_USER_DATA_OK_PAGE = "userDetails";
    private static final String REGISTRATION_SUCCESS_PAGE = "registrationSuccess";
    private static final String ERROR_PAGE = "error";
    private static final String USERDATA_MODEL_FOR_SAVE = "user";

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private UserLogic userLogic;

    @Autowired
    public SaveUserDataController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

//    @PostMapping
//    public ModelAndView saveUserData(@RequestParam("roleId") int role, @Valid @ModelAttribute(USERDATA_MODEL_FOR_SAVE)
//            User user, BindingResult bindingResult, ModelAndView modelAndView) {

    //@PostMapping
    public ModelAndView saveUserData(@Valid @ModelAttribute(USERDATA_MODEL_FOR_SAVE)
                                             User user, BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            try {
//                List<Role> roles = userLogic.getListAllRoles();
//                modelAndView.addObject("roles", roles);
            } catch (DataAccessException /*| SQLException*/ e) {

            }

            modelAndView.setViewName(USER_DETAIL_PAGE);
            modelAndView.addObject(USERDATA_MODEL_FOR_SAVE, user);
        } else {
            try {
                List<Role> currentRoles = new ArrayList<>();
//                currentRoles.add(userLogic.getRoleById(role));
//                user.setRole(currentRoles);

//                List<Role> roles = userLogic.getListAllRoles();
//                modelAndView.addObject("roles", roles);

            } catch (DataAccessException /*| SQLException*/ e) {


            }

            modelAndView.setViewName(SAVE_USER_DATA_OK_PAGE);
            modelAndView.addObject(USERDATA_MODEL_FOR_SAVE, user);

        }

        return modelAndView;
    }
}

