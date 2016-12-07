package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.com.vertex.dao.impl.CertificateDaoImpl;
import ua.com.vertex.dao.impl.UserDaoImpl;
import ua.com.vertex.logic.impl.UserLogicImpl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;

public class UserLogicImplTest {

    @Mock
    CertificateDaoImpl certificateDao;
    @Mock
    UserDaoImpl userDao;
    private UserLogicImpl userLogic;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userLogic = new UserLogicImpl(userDao, certificateDao);
    }

    @Test
    public void getAllCertificateByUserIdIsCalledOnCertificateDao() throws Exception {
        userLogic.getAllCertificatesByUserId(anyInt());
        verify(certificateDao).getAllCertificatesByUserId(anyInt());
    }

    @Test
    public void getAllCertificateByUserIdNeverReturnNull() throws Exception {
        assertNotNull("Maybe mapping for this method was changed", userLogic.getAllCertificatesByUserId(anyInt()));
    }

}