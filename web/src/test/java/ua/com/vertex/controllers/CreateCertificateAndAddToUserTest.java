package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.com.vertex.controllers.AdminController.ADMIN_JSP;
import static ua.com.vertex.controllers.CertificateDetailsPageController.ERROR;
import static ua.com.vertex.controllers.CourseDetailsController.SEARCH_COURSE_JSP;
import static ua.com.vertex.controllers.CreateCertificateAndAddToUser.*;
import static ua.com.vertex.controllers.CreateCertificateAndUserController.MSG;


@RunWith(MockitoJUnitRunner.class)
public class CreateCertificateAndAddToUserTest {

    private final String MSG_INVALID_DATA = "Have wrong objects in model";
    private final String MSG_INVALID_VIEW = "Have wrong viewName in ModelAndView";

    private CreateCertificateAndAddToUser underTest;
    private Model model;
    private Certificate certificate;
    private User user;

    @Mock
    private CertificateLogic certificateLogic;

    @Mock
    private UserLogic userLogic;

    @Mock
    private BindingResult bindingResult;

    @Before
    public void setUp() throws Exception {
        underTest = new CreateCertificateAndAddToUser(certificateLogic, userLogic);
        model = new ExtendedModelMap();
        certificate = new Certificate.Builder().setUserId(1).setCertificationDate(LocalDate.parse("2016-12-01"))
                .setCourseName("Java Professional").setLanguage("Java").getInstance();
        user = new User.Builder().setUserId(1).setEmail("test@mail.com").setFirstName("Test").setLastName("Test")
                .getInstance();

    }

    @Test
    public void addCertificateWithUserIdReturnCorrectView() throws Exception {
        ModelAndView result = underTest.addCertificateWithUserId();
        assertEquals(MSG_INVALID_VIEW, result.getViewName(), SEARCH_COURSE_JSP);
    }

    @Test
    public void searchUserCalledInUserLogic() throws Exception {
        underTest.searchUser("Test", model);
        verify(userLogic).searchUser("Test");
    }

    @Test
    public void searchUserHasCorrectDataInModelAndReturnCorrectViewWhenEmptyList() throws Exception {

        when(userLogic.searchUser("Test")).thenReturn(new ArrayList<>());
        assertEquals(MSG_INVALID_VIEW, underTest.searchUser("Test", model), SELECT_USER_JSP);
        assertTrue(MSG_INVALID_DATA, model.containsAttribute(MSG));
        assertNotNull(MSG_INVALID_DATA, model.asMap().get(MSG));
        assertTrue(MSG_INVALID_DATA, model.containsAttribute(USERS));
        assertNotNull(MSG_INVALID_DATA, model.containsAttribute(USERS));
        assertTrue(MSG_INVALID_DATA, model.asMap().containsValue("User not found, try again."));
        @SuppressWarnings("unchecked") List<User> users = (List<User>) model.asMap().get(USERS);
        assertTrue(MSG_INVALID_DATA, users.isEmpty());
    }

    @Test
    public void searchUserHasCorrectDataInModelAndReturnCorrectViewWhenNotEmptyList() throws Exception {
        when(userLogic.searchUser("Test")).thenReturn(Collections.singletonList(user));
        assertEquals(MSG_INVALID_VIEW, underTest.searchUser("Test", model), SELECT_USER_JSP);
        verify(userLogic).searchUser("Test");
        assertTrue(MSG_INVALID_DATA, model.containsAttribute(USERS));
        //noinspection unchecked
        List<User> users = (List<User>) model.asMap().get(USERS);
        assertFalse(MSG_INVALID_DATA, users.isEmpty());
        assertEquals(MSG_INVALID_DATA, users.get(0), user);
    }

    @Test
    public void searchUserHasCorrectDataInModelAndReturnCorrectViewWhenEmptyInputString() throws Exception {
        assertEquals(MSG_INVALID_VIEW, underTest.searchUser("", model), SELECT_USER_JSP);
        assertTrue(MSG_INVALID_DATA, model.containsAttribute(MSG));
        assertTrue(MSG_INVALID_DATA, model.asMap().containsValue("The data have not been validated!!!"));
        assertNotNull(MSG_INVALID_DATA, model.asMap().containsValue("The data have not been validated!!!"));
    }

    @Test
    public void searchUserHasCorrectDataInModelAndReturnCorrectViewWhenException() throws Exception {
        when(userLogic.searchUser("Test")).thenThrow(new Exception("Test"));
        assertEquals(MSG_INVALID_VIEW, underTest.searchUser("Test", model), ERROR);
    }

    @Test
    public void selectUserHasCorrectDataInModelAndView() throws Exception {
        ModelAndView result = underTest.selectUser(333);
        assertEquals(MSG_INVALID_VIEW, result.getViewName(), ADD_CERTIFICATE_WITH_USER_ID_JSP);
        assertEquals(MSG_INVALID_DATA, result.getModel().get(USER_ID), 333);
        assertEquals(MSG_INVALID_DATA, result.getModel().get(CERTIFICATE), new Certificate());
    }

    @Test
    public void checkCertificateWithUserIdCalledInCertificateLogic() throws Exception {
        underTest.checkCertificateWithUserId(certificate, bindingResult, model);
        verify(certificateLogic).addCertificate(certificate);
    }

    @Test
    public void checkCertificateWithUserIdHasCorrectDataInModelAndReturnCorrectView() throws Exception {
        when(certificateLogic.addCertificate(certificate)).thenReturn(333);
        assertEquals(MSG_INVALID_VIEW, underTest.checkCertificateWithUserId(certificate, bindingResult, model)
                , ADMIN_JSP);
        assertTrue(MSG_INVALID_DATA, model.containsAttribute(MSG));
        assertTrue(MSG_INVALID_DATA, model.asMap().containsValue("Certificate added. Certificate id = " + "333"));
    }

    @Test
    public void checkCertificateWithUserIdReturnCorrectViewWhenException() throws Exception {
        when(certificateLogic.addCertificate(certificate)).thenThrow(new Exception("Test"));
        assertEquals(MSG_INVALID_VIEW, underTest.checkCertificateWithUserId(certificate, bindingResult, model)
                , ERROR);
    }

    @Test
    public void checkCertificateWithUserIdHasCorrectDataInModelAndReturnCorrectViewWhenBindingResultHasError()
            throws Exception {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertEquals(MSG_INVALID_VIEW, underTest.checkCertificateWithUserId(certificate, bindingResult, model)
                , ADD_CERTIFICATE_WITH_USER_ID_JSP);
        assertTrue(MSG_INVALID_DATA, model.containsAttribute(MSG));
        assertTrue(MSG_INVALID_DATA, model.asMap().containsValue("The data have not been validated!!!"));
    }
}
