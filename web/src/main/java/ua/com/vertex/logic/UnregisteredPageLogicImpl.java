package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.CertificateDaoInf;
import ua.com.vertex.dao.UserDaoInf;
import ua.com.vertex.logic.interfaces.UnregisteredPageLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@SuppressWarnings("Duplicates")
public class UnregisteredPageLogicImpl implements UnregisteredPageLogic {
    private UserDaoInf userDao;
    private CertificateDaoInf certificateDao;

    @Autowired
    void setUserDao(UserDaoInf userDao) {
        this.userDao = userDao;
    }

    @Autowired
    void setCertificateDao(CertificateDaoInf certificateDao) {
        this.certificateDao = certificateDao;
    }

    @Override
    public void getCertificateDetails(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        int certificateID;
        try {
            certificateID = Integer.parseInt(req.getParameter("certificationId"));
        } catch (NumberFormatException e) {
            certificateID = -1;
        }

        Certificate cert = null;
        User user = null;

        try (PrintWriter writer = resp.getWriter()) {
            if (certificateID == -1) {
                writer.print("<h2>Incorrect User ID! </h2>");
                writer.print("<h2><a href=\"/unregistered.jsp\">Try again!<a/></h2>");
            } else {
                try {
                    cert = certificateDao.getCertificateById(certificateID);
                    user = userDao.getUser(cert.getUserId());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (cert == null) {
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
                            cert.getCertificationId(), cert.getUserId(),
                            user == null ? "-" : user.getFirstName(), user == null ? "-" : user.getLastName(),
                            cert.getCertificationDate(), cert.getCourseName(), cert.getLanguage());

                    writer.print("<a href=\"javascript:history.back();\"><h3>Back</h3></a>");
                    writer.print("<a href=\"/\"><h3>Home</h3></a>");
                }
            }
        }
    }
}
