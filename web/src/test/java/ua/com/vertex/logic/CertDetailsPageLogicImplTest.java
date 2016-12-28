package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.MainTestContext;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;
import ua.com.vertex.dao.interfaces.UserDaoInf;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainTestContext.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class CertDetailsPageLogicImplTest {

    @Autowired
    private UserDaoInf userDao;

    @Autowired
    private CertificateDaoInf certificateDao;

    private CertDetailsPageLogicImpl logic;

    @Before
    public void setUp() {
        logic = new CertDetailsPageLogicImpl(userDao, certificateDao);
    }

    @Test
    public void userDaoShouldNotBeNull() {
        assertNotNull(userDao);
    }

    @Test
    public void certificateDaoShouldNotBeNull() {
        assertNotNull(certificateDao);
    }

    @Test
    public void certificateOptionalForCertificateStoredInDBShouldBeReturned() {
        Optional<Certificate> optional = logic.getCertificateDetails(222);
        assertNotNull(optional);
        assertEquals(222, optional.get().getCertificationId());
    }

    @Test
    public void certificateOptionalForCertificateNotStoredInDBShouldBeReturned() {
        Optional<Certificate> optional = logic.getCertificateDetails(55555);
        assertNotNull(optional);
        assertEquals(new Certificate(), optional.orElse(new Certificate()));
    }

    @Test
    public void userOptionalForUserStoredInDBShouldBeReturned() {
        Optional<User> optional = logic.getUserDetails(22);
        assertNotNull(optional);
        assertEquals(22, optional.get().getUserId());
    }

    @Test
    public void userOptionalForUserNotStoredInDBShouldBeReturned() {
        Optional<User> optional = logic.getUserDetails(55555);
        assertNotNull(optional);
        assertEquals(new User(), optional.orElse(new User()));
    }
}
