package ua.com.vertex.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.CertificateDao;
import ua.com.vertex.dao.UserDaoInf;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings("Duplicates")
@Component
@WebServlet(urlPatterns = "/unregistered")
public class JspServlet2 extends HttpServlet {
    private UserDaoInf userDao;

    @Autowired
    private void userDao(UserDaoInf userDao) {
        this.userDao = userDao;
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        int certificateID;
        try {
            certificateID = Integer.parseInt(req.getParameter("certificationId"));
        } catch (NumberFormatException e) {
            certificateID = -1;
        }

//        ApplicationContext context = new AnnotationConfigApplicationContext(MainContext.class);
//        UserDao userDao = context.getBean(UserDao.class);
        CertificateDao cDao = new CertificateDao();
        Certificate cert;
        User user;
        System.out.println("userDao: " + userDao);

        try (PrintWriter writer = resp.getWriter()) {
            if (certificateID == -1) {
                writer.print("<h2>Incorrect User ID! </h2>");
                writer.print("<h2><a href=\"/unregistered.jsp\">Try again!<a/></h2>");
            } else {
                cert = cDao.getCertificate(certificateID);
                user = userDao.getUser(cert.getUserId());

                if (cert.getCertificationId() == 0) {
                    writer.print("<h2>Incorrect User ID! </h2>");
                    writer.print("<h2><a href=\"/unregistered.jsp\">Try again!<a/></h2>");
                } else {
                    writer.print("<h1>Certificate Details:</h1>");
                    writer.printf("<h2>Certification ID: %05d<br>" +
                                    "User ID: %05d<br>" +
                                    "User First Name: %s<br>" +
                                    "User Last Name: %s<br>" +
                                    "Certification Date: %s<br>" +
                                    "Course Name: %s<br>" +
                                    "Language: %s</h2>",
                            cert.getCertificationId(), cert.getUserId(), user.getFirstName(), user.getLastName(),
                            cert.getCertificationDate(), cert.getCourseName(), cert.getLanguage());

                    writer.print("<a href=\"javascript:history.back();\"><h3>Back</h3></a>");
                    writer.print("<a href=\"/\"><h3>Home</h3></a>");
                }
            }
        }
    }
}
