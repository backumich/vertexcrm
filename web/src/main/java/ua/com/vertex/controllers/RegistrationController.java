package ua.com.vertex.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.RegistrationUserLogicImpl;
import ua.com.vertex.logic.interfaces.RegistrationUserLogic;

@Controller
public class RegistrationController {

    @Autowired
    private RegistrationUserLogic registrationUserLogic;

    @Autowired
    private RegistrationUserLogicImpl validator;

    @RequestMapping(value = "/RegistrationUser", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView registrationUser(@RequestParam String email, String password, String verifyPassword, String firstName, String lastName, String phone) {
        System.out.println(email);
        System.out.println(password);
        System.out.println(verifyPassword);
        System.out.println(firstName);
        System.out.println(lastName);
        System.out.println(phone);

        User user = new User
                .Builder()
                .setEmail(email)
                .setPassword(password)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPhone(phone)
                .getInstance();


        //registrationUserValidator.validate(user, validator);

        registrationUserLogic.registrationUser(user);


//        ModelAndView result = new ModelAndView("redirect:registration.jsp");
//        result.addObject(("yes"), registrationUserLogic.registrationUser());
        return null;
        //result.addObject("userIds", userLogic.getAllUserIds());


//        User user = User.newBuilder()
//                .setEmail(email)
//                .setPassword(password)
//                .setFirstName(firstName)
//                .setLastName(lastName)
//                .setPhone(phone)
//                .build();
//
//
//        MySQLDataSource mySQLDataSource = new MySQLDataSource("jdbc:mysql://seadev.tk:3306/db1", "user1", "111");
//        mySQLDataSource.addUser(user);

        //return "home";
    }


}

