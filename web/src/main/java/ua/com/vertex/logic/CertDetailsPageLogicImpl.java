package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.CertificateDaoInf;
import ua.com.vertex.dao.UserDaoInf;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.lang.String.format;

@Service
public class CertDetailsPageLogicImpl implements CertDetailsPageLogic {
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

        Certificate cert = null;
        User user = null;

        HttpSession session = req.getSession();
        session.removeAttribute("certificateIsNull");
        session.removeAttribute("certificationId");
        session.removeAttribute("userId");
        session.removeAttribute("userFirstName");
        session.removeAttribute("userLastName");
        session.removeAttribute("certificationDate");
        session.removeAttribute("courseName");
        session.removeAttribute("language");

        try {
            cert = certificateDao.getCertificateById(Integer.parseInt(req.getParameter("certificationId")));
            user = userDao.getUser(cert.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (cert == null) {
            session.setAttribute("certificateIsNull", "Incorrect User ID! Try again!");
        } else {

            session.setAttribute("certificationId", format("Certification ID: %05d<br>", cert.getCertificationId()));
            session.setAttribute("userId", format("User ID: %05d<br>", cert.getUserId()));
            session.setAttribute("userFirstName", format("User First Name: %s<br>",
                    user == null ? "-" : user.getFirstName()));
            session.setAttribute("userLastName", format("User Last Name: %s<br>",
                    user == null ? "-" : user.getLastName()));
            session.setAttribute("certificationDate", format("Certification Date: %s<br>",
                    cert.getCertificationDate()));
            session.setAttribute("courseName", format("Course Name: %s<br>", cert.getCourseName()));
            session.setAttribute("language", format("Programming Language: %s", cert.getLanguage()));
        }

        resp.sendRedirect("/certificateDetails.jsp");
    }
}
