package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.*;
import ua.com.vertex.logic.interfaces.CourseLogic;
import ua.com.vertex.logic.interfaces.PaymentLogic;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static ua.com.vertex.controllers.AdminController.ADMIN_JSP;
import static ua.com.vertex.controllers.CertificateDetailsPageController.ERROR;
import static ua.com.vertex.controllers.CreateCertificateAndAddToUser.USERS;
import static ua.com.vertex.controllers.CreateCertificateAndUserController.MSG;
import static ua.com.vertex.controllers.CreateNewPaymentController.*;

@RunWith(MockitoJUnitRunner.class)
public class CreateNewPaymentControllerTest {

    private final String MSG_INVALID_DATA = "Have wrong objects in model";
    private final String MSG_INVALID_VIEW = "Have wrong viewName in ModelAndView";

    private CreateNewPaymentController underTest;
    private User user;
    private Course course;
    private PaymentForm paymentForm;

    @Mock
    private CourseLogic courseLogic;

    @Mock
    private UserLogic userLogic;

    @Mock
    private PaymentLogic paymentLogic;

    @Mock
    private BindingResult bindingResult;

    @Before
    public void setUp() throws Exception {
        underTest = new CreateNewPaymentController(courseLogic, userLogic, paymentLogic);

        user = new User.Builder().setUserId(1).setEmail("test@mail.com").setFirstName("Test").setLastName("Test")
                .getInstance();
        course = new Course.Builder().setId(1).setName("JavaPro").
                setStart(LocalDate.of(2017, 4, 25)).setFinished(false)
                .setPrice(BigDecimal.valueOf(4000)).setTeacher(new User.Builder().setUserId(1)
                        .setFirstName("Mr. Teacher").setLastName("Mr.teacher").setRole(Role.ROLE_TEACHER).getInstance())
                .setNotes("Test").getInstance();
        paymentForm = new PaymentForm(1, 1, new Payment.Builder().setPaymentId(1).setDealId(1)
                .setAmount(BigDecimal.valueOf(1000))
                .setPaymentDate(LocalDateTime.of(2017, 4, 25, 12, 30)).getInstance());

    }

    @Test
    public void selectCourseForPaymentReturnCorrectViewWhenException() throws Exception {
        when(courseLogic.getAllCoursesWithDept()).thenThrow(new DataIntegrityViolationException("Test"));
        assertEquals(MSG_INVALID_VIEW, underTest.selectCourseForPayment().getViewName(), ERROR);
    }

    @Test
    public void selectCourseForPaymentReturnCorrectViewAndDataInModel() throws Exception {
        when(courseLogic.getAllCoursesWithDept()).thenReturn(Collections.singletonList(course));

        ModelAndView result = underTest.selectCourseForPayment();
        verify(courseLogic, times(1)).getAllCoursesWithDept();

        assertEquals(MSG_INVALID_VIEW, result.getViewName(), SELECT_COURSE_FOR_PAYMENT_JSP);
        assertEquals(MSG_INVALID_DATA, result.getModel().get(COURSES), Collections.singletonList(course));
    }

    @Test
    public void selectUserForPaymentReturnCorrectViewWhenException() throws Exception {
        when(userLogic.getCourseUsers(anyInt())).thenThrow(new DataIntegrityViolationException("Test"));
        assertEquals(MSG_INVALID_VIEW, underTest.selectUserForPayment(1).getViewName(), ERROR);
    }

    @Test
    public void selectUserForPaymentReturnCorrectViewAndDataInModel() throws Exception {
        when(userLogic.getCourseUsers(anyInt())).thenReturn(Collections.singletonList(user));

        ModelAndView result = underTest.selectUserForPayment(1);
        verify(userLogic, times(1)).getCourseUsers(1);

        assertEquals(MSG_INVALID_VIEW, result.getViewName(), SELECT_USER_FOR_PAYMENT_JSP);
        assertEquals(MSG_INVALID_DATA, result.getModel().get(USERS), Collections.singletonList(user));
    }

    @Test
    public void createPaymentReturnCorrectViewWhenBindingResultHasError() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(true);

        ModelAndView result = underTest.createPayment(paymentForm, bindingResult, new ModelAndView());

        assertEquals(MSG_INVALID_VIEW, result.getViewName(), SELECT_USER_FOR_PAYMENT_JSP);

        assertTrue(MSG_INVALID_DATA, result.getModel().containsKey(COURSE_ID_FOR_PAY));
        assertEquals(MSG_INVALID_DATA, result.getModel().get(COURSE_ID_FOR_PAY), 1);

        assertTrue(MSG_INVALID_DATA, result.getModel().containsKey(USER_ID_FOR_PAY));
        assertEquals(MSG_INVALID_DATA, result.getModel().get(USER_ID_FOR_PAY), 1);
    }

    @Test
    public void createPaymentReturnCorrectViewWhenException() throws Exception {
        when(paymentLogic.createNewPaymentAndUpdateAccounting(paymentForm))
                .thenThrow(new DataIntegrityViolationException("Test"));
        assertEquals(MSG_INVALID_VIEW, underTest.createPayment(paymentForm, bindingResult, new ModelAndView())
                .getViewName(), ERROR);
    }

    @Test
    public void createPaymentReturnCorrectViewAndDataInModel() throws Exception {
        when(paymentLogic.createNewPaymentAndUpdateAccounting(paymentForm)).thenReturn(1);

        ModelAndView result = underTest.createPayment(paymentForm, bindingResult, new ModelAndView());
        verify(paymentLogic, times(1)).createNewPaymentAndUpdateAccounting(paymentForm);

        assertEquals(MSG_INVALID_VIEW, result.getViewName(), ADMIN_JSP);
        assertTrue(MSG_INVALID_DATA, result.getModel().containsKey(MSG));
        assertEquals(MSG, result.getModel().get(MSG), "Payment create successful!!!");

    }
}
