package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.LogInfo;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class CertDetailsPageLogicImplTest {

    @Autowired
    private CertificateDaoInf certificateDao;

    @Autowired
    private UserLogic userLogic;

    @Autowired
    private LogInfo logInfo;

    private CertDetailsPageLogic certLogic;

    private static final int EXISTING_CERT_ID = 222;
    private static final int NOT_EXISTING_ID = Integer.MIN_VALUE;

    @Before
    public void setUp() {
        certLogic = new CertDetailsPageLogicImpl(certificateDao, userLogic, logInfo);
    }

    @Test
    @WithMockUser
    public void certificateOptionalForCertificateStoredInDBShouldBeReturned() {
        Optional<Certificate> optional = certLogic.getCertificateDetails(EXISTING_CERT_ID);
        assertNotNull(optional);
        assertEquals(EXISTING_CERT_ID, optional.get().getCertificationId());
    }

    @Test
    @WithMockUser
    public void certificateNullOptionalForCertificateNotStoredInDBShouldBeReturned() {
        Optional<Certificate> optional = certLogic.getCertificateDetails(NOT_EXISTING_ID);
        assertNotNull(optional);
        assertEquals(null, optional.orElse(null));
    }
}
