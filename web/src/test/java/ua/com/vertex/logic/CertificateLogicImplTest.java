package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import ua.com.vertex.dao.CertificateDaoImpl;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CertificateLogicImplTest {

    private CertificateLogicImpl certificateLogic;

    @Mock
    private CertificateDaoImpl certificateDao;

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

    @Test(expected = EmptyResultDataAccessException.class)
    public void getCertificateByIdReturnEmptyResultException() throws Exception {
        when(certificateDao.getCertificateById(-1)).thenThrow(new EmptyResultDataAccessException("1", 1));
        certificateLogic.getCertificateById(-1);
    }

    @Test
    public void getCertificateByIdReturnNull() throws Exception {
        assertNull("Maybe method was changed", certificateLogic.getCertificateById(1));
    }

}