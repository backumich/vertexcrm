package ua.com.vertex.logic;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;

import javax.servlet.http.HttpSession;

import static java.lang.String.format;

@Service
public class CertDetailsPageLogicImpl implements CertDetailsPageLogic {
    private static final Logger LOGGER = Logger.getLogger(CertDetailsPageLogicImpl.class);
    private final UserDaoInf userDao;
    private final CertificateDaoInf certificateDao;

    @Override
    public void getCertificateDetails(HttpSession session, String certificationId) {
        Certificate cert;
        User user = null;
        clearSessionAttributes(session);

        LOGGER.debug("Accessing certificateDao");
        try {
            cert = certificateDao.getCertificateById(Integer.parseInt(certificationId));
        } catch (EmptyResultDataAccessException | NumberFormatException e) {
            session.setAttribute("certificateIsNull", "Incorrect Certificate ID! Try again!");
            return;
        }

        LOGGER.debug("Accessing userDao");
        try {
            user = userDao.getUser(cert.getUserId());
        } catch (EmptyResultDataAccessException e) {
            session.setAttribute("certificateIsNull", "No user is assigned to this certificate ID");
        }

        setSessionAttributes(session, cert, user);
    }

    private void clearSessionAttributes(HttpSession session) {
        session.removeAttribute("certificateIsNull");
        session.removeAttribute("certificationId");
        session.removeAttribute("userFirstName");
        session.removeAttribute("userLastName");
        session.removeAttribute("certificationDate");
        session.removeAttribute("courseName");
        session.removeAttribute("language");
    }

    private void setSessionAttributes(HttpSession session, Certificate cert, User user) {
        session.setAttribute("certificationId", format("Certification ID: %05d", cert.getCertificationId()));
        session.setAttribute("userFirstName", format("User First Name: %s",
                user == null ? "-" : user.getFirstName()));
        session.setAttribute("userLastName", format("User Last Name: %s",
                user == null ? "-" : user.getLastName()));
        session.setAttribute("certificationDate", format("Certification Date: %s", cert.getCertificationDate()));
        session.setAttribute("courseName", format("Course Name: %s", cert.getCourseName()));
        session.setAttribute("language", format("Programming Language: %s", cert.getLanguage()));
    }

    @Autowired
    public CertDetailsPageLogicImpl(UserDaoInf userDao, CertificateDaoInf certificateDao) {
        this.userDao = userDao;
        this.certificateDao = certificateDao;
    }
}
