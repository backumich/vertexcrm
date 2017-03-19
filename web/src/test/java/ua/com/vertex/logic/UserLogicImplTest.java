package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.UserLogic;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserLogicImplTest {

    @Mock
    private UserDaoInf dao;

    private UserLogic logic;

    private static final String EMAIL = "33@test.com";
    private static final String PHOTO = "photo";
    private static final String PASSPORT_SCAN = "passportScan";
    private static final int EXISTING_ID = 33;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        logic = new UserLogicImpl(dao);
    }

    @Test
    public void getUserByIdInvokesDao() {
        logic.getUserById(EXISTING_ID);
        verify(dao, times(1)).getUser(EXISTING_ID);
    }

    @Test
    public void getUserByEmailInvokesDao() {
        logic.getUserByEmail(EMAIL);
        verify(dao, times(1)).getUserByEmail(EMAIL);
    }

    @Test
    public void saveImageInvokesDao() throws Exception {
        byte[] image = new byte[]{1, 2, 3};
        logic.saveImage(EXISTING_ID, image, PASSPORT_SCAN);
        verify(dao, times(1)).saveImage(EXISTING_ID, image, PASSPORT_SCAN);
    }

    @Test
    public void getImageInvokesDao() throws Exception {
        logic.getImage(EXISTING_ID, PHOTO);
        verify(dao, times(1)).getImage(EXISTING_ID, PHOTO);
    }
}
