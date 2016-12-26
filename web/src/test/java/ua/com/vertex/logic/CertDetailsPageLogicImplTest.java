package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.ImageStorage;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.MainTestContext;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;
import ua.com.vertex.dao.interfaces.UserDaoInf;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainTestContext.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class CertDetailsPageLogicImplTest {

    @Autowired
    private UserDaoInf userDao;

    @Autowired
    private CertificateDaoInf certificateDao;

    @Mock
    private ImageStorage storage;

    private CertDetailsPageLogicImpl logic;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        logic = new CertDetailsPageLogicImpl(userDao, certificateDao, storage);
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
    public void certificateAndUserFieldsForCertificateStoredInDBWithUserAssignedShouldNotBeNull() {
        Certificate certificate = logic.getCertificateDetails(222);
        User user = logic.getUserDetails(certificate.getUserId());

        assertEquals(222, certificate.getCertificationId());
        assertNotNull(user.getFirstName());
        assertNotNull(user.getLastName());
        assertNotNull(certificate.getCertificationDate());
        assertNotNull(certificate.getCourseName());
        assertNotNull(certificate.getLanguage());
    }

    @Test
    public void accessToCertificateNotStoredInDBShouldReturnEmptyCertificate() {
        Certificate certificate = logic.getCertificateDetails(55555);
        assertEquals(0, certificate.getCertificationId());
    }

    @Test
    public void accessToUserNotStoredInDBShouldReturnEmptyUser() {
        User user = logic.getUserDetails(55555);
        assertEquals(0, user.getUserId());
    }

    @Test
    public void getUserDetailsForUserWithPhotoShouldReturnUserWithPhoto() {
        User user = logic.getUserDetails(22);
        assertEquals(user.getPhoto().length, 1);
    }

    @Test
    public void getUserDetailsForUserWithoutPhotoShouldReturnUserWithNullPhoto() {
        User user = logic.getUserDetails(33);
        assertNull(user.getPhoto());
    }
}
