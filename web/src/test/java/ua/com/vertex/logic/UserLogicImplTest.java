package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
    public void getUserByEmailInvokesDao() throws SQLException {
        logic.getUserByEmail(EMAIL);
        verify(dao, times(1)).getUserByEmail(EMAIL);
    }

    @Test
    public void getImageInvokesDao() throws Exception {
        logic.getImage(EMAIL, PHOTO);
        verify(dao, times(1)).getImage(EMAIL, PHOTO);
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
    public void registrationUserInsertReturnNullPointerException() throws SQLException {
        logic.registrationUserInsert(new User());
    }

    @Test
    public void registrationUserInsertEncodePasswordAndInvokesDao() throws SQLException {
        String passwordBeforeInsert = user.getPassword();
        logic.registrationUserInsert(user);
        verify(dao, times(1)).registrationUserInsert(user);
        assertNotEquals(MSG, passwordBeforeInsert, user.getPassword());
    }

    @Test(expected = NullPointerException.class)
    public void registrationUserUpdateReturnNullPointerException() throws SQLException {

        logic.registrationUserUpdate(new User());
    }

    @Test
    public void registrationUserUpdateEncodePasswordAndInvokesDao() throws SQLException {
        String passwordBeforeInsert = user.getPassword();
        logic.registrationUserUpdate(user);
        verify(dao, times(1)).registrationUserUpdate(user);
        assertNotEquals(MSG, passwordBeforeInsert, user.getPassword());
    }

    @Test(expected = NullPointerException.class)
    public void encryptPasswordReturnNullPointerException() throws SQLException {
        logic.encryptPassword(null);
    }

    @Test
    public void encryptPasswordReturnEncodePassword() {
        assertNotNull(logic.encryptPassword(NAME));
        assertNotEquals(MSG, NAME, logic.encryptPassword(NAME));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void getCourseUsersVerifyAccountingDaoAndReturnException() throws Exception {
        when(logic.getCourseUsers(1)).thenThrow(new DataIntegrityViolationException("Test"));
        logic.getCourseUsers(1);
        verify(dao, times(1)).getCourseUsers(1);
    }

    @Test
    public void getTeachersReturnCorrectData() throws SQLException {
        List<User> teachers = new ArrayList<>();
        teachers.add(user);
        when(dao.getTeachers()).thenReturn(teachers);
        assertEquals(logic.getTeachers().get(EXISTING_ID), String.format("%s %s \'%s\'", NAME, NAME, EMAIL));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void getTeachersReturnException() throws SQLException {
        when(dao.getTeachers()).thenThrow(new DataIntegrityViolationException("test"));
        logic.getTeachers();
    }

    @Test
    public void isUserRegisteredAndActiveReturnsTrue() {
        when(dao.userForRegistrationCheck(EMAIL))
                .thenReturn(Optional.of(new User.Builder().setIsActive(true).getInstance()));
        boolean result = logic.isUserRegisteredAndActive(EMAIL);

        assertEquals(true, result);
    }

    @Test
    public void isUserRegisteredAndActiveReturnsFalse() {
        when(dao.userForRegistrationCheck(EMAIL))
                .thenReturn(Optional.of(new User.Builder().setIsActive(false).getInstance()));
        boolean result = logic.isUserRegisteredAndActive(EMAIL);

        assertEquals(false, result);
    }

    @Test
    public void setParamsToRestorePasswordInvokesUserDao() {
        final String email = "someEmail";
        final String uuid = "uuid";
        final LocalDateTime dateTime = LocalDateTime.now();

        logic.setParamsToRestorePassword(email, uuid, dateTime);
        verify(dao, times(1)).setParamsToRestorePassword(email, uuid, dateTime);
    }
}