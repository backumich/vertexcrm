package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ua.com.vertex.beans.PasswordResetDto;
import ua.com.vertex.context.TestConfigWithMockBeans;
import ua.com.vertex.utils.ReCaptchaService;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static ua.com.vertex.controllers.PasswordResetEmailController.*;
import static ua.com.vertex.controllers.exceptionHandling.GlobalExceptionHandler.EMAIL_NOT_FOUND;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfigWithMockBeans.class)
@WebAppConfiguration
@ActiveProfiles("withMockBeans")
@Transactional
public class PasswordResetEmailControllerTest {
    private static final String RE_CAPTCHA_RESPONSE = null;
    private static final String RE_CAPTCHA_REMOTE_ADDRESS = "127.0.0.1";
    private static final String EXISTING_EMAIL = "email1@test.com";
    private static final String NOT_EXISTING_EMAIL = "email_not_existing@test.com";

    @Autowired
    private PasswordResetEmailController controller;

    @Autowired
    private ReCaptchaService reCaptchaService;

    @Mock
    private BindingResult result;

    private Model model;
    private HttpServletRequest request;
    private PasswordResetDto passwordResetDto;

    @Before
    public void setUp() {
        model = Mockito.mock(Model.class);
        request = new MockHttpServletRequest();
    }

    @Test
    public void resetPasswordReturnsCorrectView() {
        assertEquals(PASSWORD_RESET, controller.resetPassword(model));
    }

    @Test
    public void sendEmailBindingResultErrorReturnsCorrectView() {
        passwordResetDto = PasswordResetDto.builder().email(EMAIL_INVALID).build();
        when(result.hasErrors()).thenReturn(true);
        String view = controller.sendEmail(passwordResetDto, result, request, model);

        assertEquals(PASSWORD_RESET, view);
        verify(model, times(1)).addAttribute(EMAIL_INVALID, true);
        verify(model, times(1)).addAttribute(EMAIL, EMAIL_INVALID);
    }

    @Test
    public void sendEmailReturnsCorrectView() {
        passwordResetDto = PasswordResetDto.builder().email(EXISTING_EMAIL).build();
        when(reCaptchaService.verify(RE_CAPTCHA_RESPONSE, RE_CAPTCHA_REMOTE_ADDRESS)).thenReturn(true);

        String view = controller.sendEmail(passwordResetDto, result, request, model);

        assertEquals(EMAIL_SENT, view);
    }

    @Test
    public void sendEmailNotVerifiedReturnsCorrectView() {
        passwordResetDto = PasswordResetDto.builder().email(NOT_EXISTING_EMAIL).build();
        String view = controller.sendEmail(passwordResetDto, result, request, model);

        assertEquals(PASSWORD_RESET, view);
        verify(model, times(1)).addAttribute(EMAIL_NOT_FOUND, true);
        verify(model, times(1)).addAttribute(EMAIL, NOT_EXISTING_EMAIL);
    }

    @Test
    public void missedReCaptchaReturnsCorrectView() {
        passwordResetDto = PasswordResetDto.builder().email(EXISTING_EMAIL).build();
        when(reCaptchaService.verify(RE_CAPTCHA_RESPONSE, RE_CAPTCHA_REMOTE_ADDRESS)).thenReturn(false);

        String view = controller.sendEmail(passwordResetDto, result, request, model);

        assertEquals(PASSWORD_RESET, view);
        verify(model, times(1)).addAttribute(CAPTCHA_MISSED, true);
        verify(model, times(1)).addAttribute(EMAIL, EXISTING_EMAIL);
    }
}