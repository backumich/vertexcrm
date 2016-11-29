package ua.com.vertex.controllers;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;

import ua.com.vertex.dao.impl.CertificateDaoRealization;
import ua.com.vertex.dao.impl.UserDaoRealization;


import java.util.*;

@Controller
public class ShowUserListController {


    @Autowired
    private UserDaoRealization userDaoRealization;

    @Autowired
    private CertificateDaoRealization certificateDaoRealization;

    @RequestMapping("/userlist")
    public String getData(HttpServletRequest request) {

        List<User> users = userDaoRealization.getAllUsers();
        List<Certificate> certificates = certificateDaoRealization.getAllCertificateWithUserNotNull();


        request.getSession().setAttribute("users", users);
        request.getSession().setAttribute("certificates", certificates);
        return "redirect:/userlist.jsp";

    }


}
