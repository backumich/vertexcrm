package ua.com.vertex.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.UserFormRegistration;
import ua.com.vertex.logic.interfaces.RegistrationUserLogic;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {

    @Autowired
    private RegistrationUserLogic registrationUserLogic;


//    @RequestMapping(value = "/RegistrationUser", method = RequestMethod.POST)
//    @ResponseBody
//    public ModelAndView registrationUser(@RequestParam String email, String password, String verifyPassword, String firstName, String lastName, String phone) {
//        System.out.println(email);
//        System.out.println(password);
//        System.out.println(verifyPassword);
//        System.out.println(firstName);
//        System.out.println(lastName);
//        System.out.println(phone);
//        User user = new User
//                .Builder()
//                .setEmail(email)
//                .setPassword(password)
//                .setFirstName(firstName)
//                .setLastName(lastName)
//                .setPhone(phone)
//                .getInstance();
//
//        user = registrationUserLogic.encryptPassword(user);
//        registrationUserLogic.registrationUser(user);
//        return null;
//    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewRegistrationForm(Map<String, Object> model) {
        return new ModelAndView("registration", "user", new UserFormRegistration());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView processRegistration(@Valid @ModelAttribute("user") UserFormRegistration userFormRegistration, BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
            modelAndView.addObject("user", userFormRegistration);
            return modelAndView;
        }

        userFormRegistration = registrationUserLogic.encryptPassword(userFormRegistration);
        //registrationUserLogic.registrationUser(user);



//        System.out.println("username: " + user.getEmail());
//        System.out.println("password: " + user.getPassword());
//        System.out.println("birth date: " + user.getFirstName());
//        System.out.println("profession: " + user.getLastName());

        return new ModelAndView("registrationSuccess", "user", userFormRegistration);
        //return "registrationSuccess";
    }
}

