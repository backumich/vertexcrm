package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserLogicImplTest {

    private final String MSG = "Maybe method was changed";

    @Mock
    private UserDaoInf dao;

    @InjectMocks
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserLogic logic;

    private User user;

    private static final String EMAIL = "33@test.com";
    private static final String NAME = "test";
    private static final String PHOTO = "photo";
    private static final String PASSPORT_SCAN = "passportScan";
    private static final int EXISTING_ID = 33;

    @Before
    public void setUp() {
        logic = new UserLogicImpl(dao, bCryptPasswordEncoder);
        user = new User.Builder().setUserId(EXISTING_ID).setEmail(EMAIL).setPassword(NAME).setFirstName(NAME)
                .setLastName(NAME).setDiscount(0).getInstance();
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

    @Test
    public void getAllUserIdsInvokesDaoAndReturnNotNull() throws Exception {
        assertNotNull(logic.getAllUserIds());
        verify(dao, times(1)).getAllUserIds();
    }

    @Test
    public void searchUserInvokesDaoAndReturnNotNull() throws Exception {
        assertNotNull(logic.searchUser(NAME));
        verify(dao, times(1)).searchUser(NAME);
    }

    @Test
    public void userForRegistrationCheckInvokesDao() throws Exception {
        logic.userForRegistrationCheck(EMAIL);
        verify(dao, times(1)).userForRegistrationCheck(EMAIL);
    }

    @Test(expected = NullPointerException.class)
    public void registrationUserInsertReturnNullPointerException() throws DataAccessException {
        logic.registrationUserInsert(new User());
    }

    @Test
    public void registrationUserInsertEncodePasswordAndInvokesDao() throws DataAccessException {
        String passwordBeforeInsert = user.getPassword();
        logic.registrationUserInsert(user);
        verify(dao, times(1)).registrationUserInsert(user);
        assertNotEquals(MSG, passwordBeforeInsert, user.getPassword());
    }

    @Test(expected = NullPointerException.class)
    public void registrationUserUpdateReturnNullPointerException() throws DataAccessException {

        logic.registrationUserUpdate(new User());
    }

    @Test
    public void registrationUserUpdateEncodePasswordAndInvokesDao() throws DataAccessException {
        String passwordBeforeInsert = user.getPassword();
        logic.registrationUserUpdate(user);
        verify(dao, times(1)).registrationUserUpdate(user);
        assertNotEquals(MSG, passwordBeforeInsert, user.getPassword());
    }

    @Test(expected = NullPointerException.class)
    public void encryptPasswordReturnNullPointerException() throws DataAccessException {
        logic.encryptPassword(null);
    }

    @Test
    public void encryptPasswordReturnEncodePassword() throws DataAccessException {
        assertNotNull(logic.encryptPassword(NAME));
        assertNotEquals(MSG, NAME, logic.encryptPassword(NAME));
    }

    @Test
    public void getTeachersReturnCorrectData() throws Exception {
        List<User> teachers = new ArrayList<>();
        teachers.add(user);
        when(dao.getTeachers()).thenReturn(teachers);
        assertEquals(logic.getTeachers().get(EMAIL),String.format("%s %s \'%s\'",NAME,NAME,EMAIL));

    }

}