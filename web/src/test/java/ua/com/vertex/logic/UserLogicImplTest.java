package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.com.vertex.dao.CertificateDaoImpl;
import ua.com.vertex.dao.UserDaoImpl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

public class UserLogicImplTest {

    private UserLogicImpl userLogic;

    @Mock
    private CertificateDaoImpl certificateDao;

    @Mock
    private UserDaoImpl userDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userLogic = new UserLogicImpl(userDao, certificateDao);
    }

    @Test
    public void getAllCertificateByUserIdIsCalledOnCertificateDao() throws Exception {
        userLogic.getAllCertificatesByUserId(1);
        verify(certificateDao).getAllCertificatesByUserId(1);
    }

    @Test
    public void getAllCertificateByUserIdNeverReturnNull() throws Exception {
        assertNotNull("Maybe method was changed", userLogic.getAllCertificatesByUserId(-1));
    }

}