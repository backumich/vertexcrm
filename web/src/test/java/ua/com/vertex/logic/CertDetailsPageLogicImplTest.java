package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.vertex.context.MainContext;
import ua.com.vertex.dao.CertificateDaoInf;
import ua.com.vertex.dao.UserDaoInf;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainContext.class)
public class CertDetailsPageLogicImplTest {

    @Autowired
    private UserDaoInf userDao;

    @Autowired
    private CertificateDaoInf certificateDao;
    private HttpSession session;

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
    }

    @Test
    public void sessionAttributesForCertificateStoredInDBShouldBeSetAndNotNull() {
        CertDetailsPageLogicImpl logic = new CertDetailsPageLogicImpl(userDao, certificateDao);
        String certificateId = "1";
        logic.getCertificateDetails(session, certificateId);
        assertNotNull(session.getAttribute("certificationId"));
        assertNotNull(session.getAttribute("userFirstName"));
        assertNotNull(session.getAttribute("userLastName"));
        assertNotNull(session.getAttribute("certificationDate"));
        assertNotNull(session.getAttribute("courseName"));
        assertNotNull(session.getAttribute("language"));
    }

    @Test
    public void sessionAttributesForCertificateNotStoredInDBShouldBeNull() {
        CertDetailsPageLogicImpl logic = new CertDetailsPageLogicImpl(userDao, certificateDao);
        String certificateId = String.valueOf(Integer.MAX_VALUE);
        logic.getCertificateDetails(session, certificateId);
        assertNull(session.getAttribute("certificationId"));
        assertNull(session.getAttribute("userFirstName"));
        assertNull(session.getAttribute("userLastName"));
        assertNull(session.getAttribute("certificationDate"));
        assertNull(session.getAttribute("courseName"));
        assertNull(session.getAttribute("language"));
    }
}
