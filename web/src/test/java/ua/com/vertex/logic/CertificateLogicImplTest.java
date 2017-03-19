package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;
import ua.com.vertex.logic.interfaces.CertificateLogic;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;

public class CertificateLogicImplTest {

    private CertificateLogic certificateLogic;

    @Mock
    private CertificateDaoInf certificateDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        certificateLogic = new CertificateLogicImpl(certificateDao);
    }

    @Test
    public void getCertificateByIdIsCalledOnCertificateDao() throws Exception {
        certificateLogic.getCertificateById(1);
        verify(certificateDao).getCertificateById(1);
    }

    @Test
    public void getCertificateByIdReturnNull() throws Exception {
        assertNull("Maybe method was changed", certificateLogic.getCertificateById(1));
    }

    @Test
    public void getAllCertificateByUserIdIsCalledOnCertificateDao() throws Exception {
        certificateLogic.getAllCertificatesByUserId(1);
        verify(certificateDao).getAllCertificatesByUserId(1);
    }

    @Test
    public void getAllCertificateByUserIdNeverReturnNull() throws Exception {
        assertNotNull("Maybe method was changed", certificateLogic.getAllCertificatesByUserId(-1));
    }


}