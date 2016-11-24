package ua.com.vertex.controllers;
import javax.servlet.*;
import javax.servlet.http.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import  ua.com.vertex.dao.UserDao;


import ua.com.vertex.models.User;

import java.util.*;

@Controller
public class ShowUserListController {
/*@RequestMapping(value = "/userlist.jsp")
public ModelAndView getdata() {
        UserDao userDao = new UserDao();
        List<User> users = userDao.getAllUsers();


        ModelAndView model = new ModelAndView();
        model.addObject("users",users);

        for (User u: users) {
                System.out.println(u.getUserId());
        }
        return model;

        }*/

/*public String getData(@RequestParam Model model) {
        UserDao userDao = new UserDao();
        List<User> users = userDao.getAllUsers();

        model.addAttribute("users", users);

        return "userlist";*/

        @RequestMapping("/userlist")
        public String getData(HttpServletRequest request) {

                UserDao userDao = new UserDao();
                List<User> users = userDao.getAllUsers();


                request.getSession().setAttribute("users", users);
                return "redirect:/userlist.jsp";
        }



        public static void main(String[] args) {
                ShowUserListController sc= new ShowUserListController();
             //sc.getData();
        }

}
