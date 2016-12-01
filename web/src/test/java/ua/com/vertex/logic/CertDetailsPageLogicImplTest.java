package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.vertex.context.TestContext;
import ua.com.vertex.dao.CertificateDaoInf;
import ua.com.vertex.dao.UserDaoInf;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestContext.class)
@ActiveProfiles("test")
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
    public void sessionAttributesForCertificateStoredInDBShouldBeSetAndNotNull() {
        String certificateId = "222";
        logic.getCertificateDetails(session, certificateId);
        assertNull(session.getAttribute("certificateIsNull"));
        assertNotNull(session.getAttribute("certificationId"));
        assertNotNull(session.getAttribute("userFirstName"));
        assertNotNull(session.getAttribute("userLastName"));
        assertNotNull(session.getAttribute("certificationDate"));
        assertNotNull(session.getAttribute("courseName"));
        assertNotNull(session.getAttribute("language"));
    }

    @Test
    public void sessionAttributesForCertificateNotStoredInDBShouldBeNull() {
        String certificateId = "1000";
        logic.getCertificateDetails(session, certificateId);
        assertNotNull(session.getAttribute("certificateIsNull"));
        assertNull(session.getAttribute("certificationId"));
        assertNull(session.getAttribute("userFirstName"));
        assertNull(session.getAttribute("userLastName"));
        assertNull(session.getAttribute("certificationDate"));
        assertNull(session.getAttribute("courseName"));
        assertNull(session.getAttribute("language"));
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
    }
}
