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
    private CertificateDaoImpl certificateDao;
    @Mock
    private UserDaoImpl userDao;
    private UserLogicImpl userLogic;

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
        // TODO: 08.12.16 change message please
        assertNotNull("Maybe mapping for this method was changed", userLogic.getAllCertificatesByUserId(-1));
    }

}