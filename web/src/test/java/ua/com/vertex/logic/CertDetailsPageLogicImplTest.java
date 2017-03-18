package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.LogInfo;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CertDetailsPageLogicImplTest {

    @Mock
    private CertificateDaoInf dao;

    @Mock
    private UserLogic userLogic;

    @Mock
    private LogInfo logInfo;

    private CertDetailsPageLogic certLogic;

    private static final int EXISTING_CERT_ID = 222;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        certLogic = new CertDetailsPageLogicImpl(dao, userLogic, logInfo);
    }

    @Test
    public void certificateOptionalForCertificateStoredInDBShouldBeReturned() {
        certLogic.getCertificateDetails(EXISTING_CERT_ID);
        verify(dao, times(1)).getCertificateById(EXISTING_CERT_ID);
    }
}
