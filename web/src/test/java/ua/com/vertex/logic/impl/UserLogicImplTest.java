package ua.com.vertex.logic.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.com.vertex.dao.impl.CertificateDaoImpl;
import ua.com.vertex.dao.impl.UserDaoImpl;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;

public class UserLogicImplTest {

    private UserLogicImpl underTest;

    @Mock
    CertificateDaoImpl certificateDaoTest;

    @Mock
    UserDaoImpl userDaoTest;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        underTest = new UserLogicImpl(userDaoTest, certificateDaoTest);
    }

    @Test
    public void getAllCertificateByUserIdIsCalledOnCertificateDao() throws Exception {
        underTest.getAllCertificateByUserId(anyInt());
        verify(certificateDaoTest).getAllCertificateByUserId(anyInt());
    }

}