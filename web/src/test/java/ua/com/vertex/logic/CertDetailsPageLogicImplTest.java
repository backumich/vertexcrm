package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.vertex.context.TestContext;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;
import ua.com.vertex.dao.interfaces.UserDaoInf;

import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestContext.class)
public class CertDetailsPageLogicImplTest {

    @Autowired
    private UserDaoInf userDao;

    @Autowired
    private CertificateDaoInf certificateDao;

    private HttpSession session;
    private CertDetailsPageLogicImpl logic;

    @Test
    public void userDaoShouldNotBeNull() {
        assertNotNull(userDao);
    }

    @Test
    public void certificateDaoShouldNotBeNull() {
        assertNotNull(certificateDao);
    }

    @Before
    public void setUp() {
        session = new MockHttpSession();
        logic = new CertDetailsPageLogicImpl(userDao, certificateDao);
    }

    @Test
    public void sessionAttributesForCertificateStoredInDBShouldBeNotNull() {
        String certificateId = "222";
        logic.getCertificateDetails(session, certificateId);
        assertNull(session.getAttribute("certificateIsNull"));
        assertNotNull(session.getAttribute("certificationId"));
        assertNotNull(session.getAttribute("userFirstName"));
        assertNotNull(session.getAttribute("userLastName"));
        assertNotNull(session.getAttribute("certificationDate"));
        assertNotNull(session.getAttribute("courseName"));
        assertNotNull(session.getAttribute("language"));
        assertNotNull(session.getAttribute("userPhoto"));
    }

    @Test
    public void sessionAttributesForCertificateNotStoredInDBShouldBeNull() {
        String certificateId = "0";
        logic.getCertificateDetails(session, certificateId);
        assertNotNull(session.getAttribute("certificateIsNull"));
        assertNull(session.getAttribute("certificationId"));
        assertNull(session.getAttribute("userFirstName"));
        assertNull(session.getAttribute("userLastName"));
        assertNull(session.getAttribute("certificationDate"));
        assertNull(session.getAttribute("courseName"));
        assertNull(session.getAttribute("language"));
        assertNull(session.getAttribute("userPhoto"));
    }

    @Test
    public void sessionAttributesForCertificateStoredInDBWithoutUserAssignedShouldNotBeNull() {
        String certificateId = "333";
        logic.getCertificateDetails(session, certificateId);
        assertNotNull(session.getAttribute("certificateIsNull"));
        assertNotNull(session.getAttribute("certificationId"));
        assertEquals(session.getAttribute("userFirstName"), "User First Name: -");
        assertEquals(session.getAttribute("userLastName"), "User Last Name: -");
        assertNotNull(session.getAttribute("certificationDate"));
        assertNotNull(session.getAttribute("courseName"));
        assertNotNull(session.getAttribute("language"));
        assertNull(session.getAttribute("userPhoto"));
    }

    @Test
    public void sessionAttributesForCertificateIdNullShouldBeNull() {
        logic.getCertificateDetails(session, null);
        assertNotNull(session.getAttribute("certificateIsNull"));
        assertNull(session.getAttribute("certificationId"));
        assertNull(session.getAttribute("userFirstName"));
        assertNull(session.getAttribute("userLastName"));
        assertNull(session.getAttribute("certificationDate"));
        assertNull(session.getAttribute("courseName"));
        assertNull(session.getAttribute("language"));
        assertNull(session.getAttribute("userPhoto"));
    }

    @Test()
    public void getUserPhotoForExistingUserWithNoPhotoShouldReturnZeroLengthArray() throws IOException {
        String certificateId = "222";
        String userId = "22";
        logic.getCertificateDetails(session, certificateId);
        assertEquals(logic.getUserPhoto(userId).length, 0);
    }

    @Test
    public void getUserPhotoForNonExistingUserShouldReturnZeroLengthArray() throws IOException {
        String userId = "1000";
        assertEquals(logic.getUserPhoto(userId).length, 0);
    }
}
