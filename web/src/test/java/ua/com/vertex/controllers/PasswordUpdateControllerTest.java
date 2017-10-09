package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ua.com.vertex.beans.PasswordResetDto;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.ReCaptchaService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static ua.com.vertex.controllers.PasswordResetEmailController.CAPTCHA_MISSED;
import static ua.com.vertex.controllers.PasswordUpdateController.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
@Transactional
public class PasswordUpdateControllerTest {
    private static final String RE_CAPTCHA_RESPONSE = null;
    private static final String RE_CAPTCHA_REMOTE_ADDRESS = "127.0.0.1";

    @Autowired
    private PasswordUpdateController controller;

    @Autowired
    private ReCaptchaService reCaptchaService;

    @Autowired
    private UserLogic userLogic;

    private HttpServletRequest request;
    private BindingResult bindingResult;
    private Model model;

    @Before
    public void setUp() {
        model = Mockito.mock(Model.class);
        request = new MockHttpServletRequest();
        bindingResult = Mockito.mock(BindingResult.class);
    }

    @Test
    @WithAnonymousUser
    public void passwordEnterNewWithExpiredLinkReturnsCorrectView() throws Exception {
        final String id = "1";
        final String uuid = "06e668ba-d4c1-4f3e-8bea-5935929120c5";

        String view = controller.passwordEnterNew(id, uuid, model);

        assertEquals(PASSWORD_ENTER_NEW, view);
        verify(model, times(1)).addAttribute(EXPIRED, true);
    }

    @Test
    @WithAnonymousUser
    public void passwordEnterNewWithActiveLinkReturnsCorrectView() throws Exception {
        final String id = "3";
        final String email = "email";
        final String uuid = "06e668ba-d4c1-4f3e-8bea-5935929120a5";
        createActiveLinkHelper(email, uuid);

        String view = controller.passwordEnterNew(id, uuid, model);

        assertEquals(PASSWORD_ENTER_NEW, view);
        verify(model, times(1)).addAttribute(DTO, PasswordResetDto.builder().email(email).build());
    }

    private void createActiveLinkHelper(String email, String uuid) {
        userLogic.setParamsToRestorePassword(email, uuid, LocalDateTime.now());
    }

    @Test
    @WithAnonymousUser
    public void passwordSaveNewReturnsCorrectView() throws Exception {
        final String email = "email1";
        final String password = "password";
        final PasswordResetDto dto = preparePasswordResetDto(email, password, password);

        when(reCaptchaService.verify(RE_CAPTCHA_RESPONSE, RE_CAPTCHA_REMOTE_ADDRESS)).thenReturn(true);
        String view = controller.passwordSaveNew(dto, bindingResult, request, model);

        assertEquals(PASSWORD_SAVED, view);
    }

    private PasswordResetDto preparePasswordResetDto(String email, String rawPassword, String repeatPassword) {
        return PasswordResetDto.builder()
                .email(email)
                .rawPassword(rawPassword)
                .repeatPassword(repeatPassword)
                .build();
    }

    @Test
    @WithAnonymousUser
    public void passwordSaveNewMissedReCaptchaReturnsCorrectView() throws Exception {
        final String email = "email1";
        final String password = "password";
        final PasswordResetDto dto = preparePasswordResetDto(email, password, password);

        when(reCaptchaService.verify(RE_CAPTCHA_RESPONSE, RE_CAPTCHA_REMOTE_ADDRESS)).thenReturn(false);
        String view = controller.passwordSaveNew(dto, bindingResult, request, model);

        assertEquals(PASSWORD_ENTER_NEW, view);
        verify(model, times(1)).addAttribute(DTO, PasswordResetDto.builder().email(email).build());
        verify(model, times(1)).addAttribute(CAPTCHA_MISSED, true);
    }

    @Test
    @WithAnonymousUser
    public void passwordSaveNewWithBindingResultErrorsReturnsCorrectView() throws Exception {
        final String email = "email1";
        final String password = "password";
        final PasswordResetDto dto = preparePasswordResetDto(email, password, password);

        when(bindingResult.hasErrors()).thenReturn(true);
        String view = controller.passwordSaveNew(dto, bindingResult, request, model);

        assertEquals(PASSWORD_ENTER_NEW, view);
        verify(model, times(1)).addAttribute(DTO, PasswordResetDto.builder().email(email).build());
        verify(model, times(1)).addAttribute(PASSWORD_INVALID, true);
    }

    @Test
    @WithAnonymousUser
    public void passwordSaveNewWithPasswordMismatchReturnsCorrectView() throws Exception {
        final String email = "email1";
        final String rawPassword = "password1";
        final String repeatPassword = "password2";
        final PasswordResetDto dto = preparePasswordResetDto(email, rawPassword, repeatPassword);

        when(reCaptchaService.verify(RE_CAPTCHA_RESPONSE, RE_CAPTCHA_REMOTE_ADDRESS)).thenReturn(true);
        String view = controller.passwordSaveNew(dto, bindingResult, request, model);

        assertEquals(PASSWORD_ENTER_NEW, view);
        verify(model, times(1)).addAttribute(DTO, PasswordResetDto.builder().email(email).build());
        verify(model, times(1)).addAttribute(PASSWORD_MISMATCH, true);
    }
}