package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.UserLogic;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;


public class UserLogicImplTest {

    private UserLogic userLogic;

    @Mock
    private UserDaoInf userDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userLogic = new UserLogicImpl(userDao);
    }

    @Test
    public void daoNotBeNull() throws Exception {
        assertNotNull(userDao);
    }

    @Test
    public void getAllUserIdsCalledInUserDaoAndReturnNotNull() throws Exception {
        assertNotNull(userLogic.getAllUserIds());
        verify(userDao).getAllUserIds();
    }

    @Test
    public void searchUserCalledInUserDaoAndReturnNotNull() throws Exception {
        assertNotNull(userLogic.searchUser("test"));
        verify(userDao).searchUser("test");

    }
}