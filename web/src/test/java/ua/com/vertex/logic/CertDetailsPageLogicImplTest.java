package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
        Certificate certificate;
        User user;

        int certificateId = 222;

        certificate = logic.getCertificateDetails(certificateId);
        user = logic.getUserDetails(certificate.getUserId());

        assertNotNull(certificate.getCertificationId());
        assertNotNull(user.getFirstName());
        assertNotNull(user.getLastName());
        assertNotNull(certificate.getCertificationDate());
        assertNotNull(certificate.getCourseName());
        assertNotNull(certificate.getLanguage());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    //todo: fix it please
    public void accessToCertificateNotStoredInDBShouldThrowEmptyResultDataAccessException() {
        int certificateId = 0;
        logic.getCertificateDetails(certificateId);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void accessToUserNotStoredInDBShouldThrowEmptyResultDataAccessException() {
        Certificate certificate;
        int certificateId = 500;

        certificate = logic.getCertificateDetails(certificateId);
        logic.getUserDetails(certificate.getUserId());
    }

    @Test
    public void getUserDetailsForUserWithPhotoShouldReturnUserWithEmptyPhotoAndNullPassportScan() {
        Certificate certificate;
        User user;
        int certificateId = 222;

        certificate = logic.getCertificateDetails(certificateId);
        user = logic.getUserDetails(certificate.getUserId());

        assertEquals(user.getPhoto().length, 0);
        assertNull(user.getPassportScan());
    }

    @Test
    public void getUserDetailsForUserWithoutPhotoShouldReturnUserWithNullPhoto() {
        Certificate certificate;
        User user;
        int certificateId = 333;

        certificate = logic.getCertificateDetails(certificateId);
        user = logic.getUserDetails(certificate.getUserId());

        assertNull(user.getPhoto());
    }
}
