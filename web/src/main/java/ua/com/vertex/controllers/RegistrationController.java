package ua.com.vertex.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.com.vertex.dao.MySQLDataSource;
import ua.com.vertex.models.User;

@Controller
public class RegistrationController {


    //@RequestMapping("/registration") // Обрабатывать запросы на получение главной страницы
    @RequestMapping(value = "/RegistrationUser", method = RequestMethod.POST)

    @ResponseBody
    public String registrationUser(@RequestParam String email, String password, String verifyPassword, String firstName, String lastName, String phone) {
        System.out.println(email);
        System.out.println(password);
        System.out.println(verifyPassword);
        System.out.println(firstName);
        System.out.println(lastName);
        System.out.println(phone);


        User user = User.newBuilder()
                .setEmail(email)
                .setPassword(password)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPhone(phone)
                .build();


        MySQLDataSource mySQLDataSource = new MySQLDataSource("jdbc:mysql://seadev.tk:3306/db1", "user1", "111");
        mySQLDataSource.addUser(user);

        return "home";
    }
}

