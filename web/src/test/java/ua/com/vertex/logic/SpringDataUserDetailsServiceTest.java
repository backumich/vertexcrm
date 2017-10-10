package ua.com.vertex.logic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.vertex.beans.Role;
import ua.com.vertex.context.TestConfigWithMockBeans;
import ua.com.vertex.utils.ReCaptchaService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfigWithMockBeans.class)
@WebAppConfiguration
@ActiveProfiles("withMockBeans")
public class SpringDataUserDetailsServiceTest {
    private static final String RE_CAPTCHA_RESPONSE = null;
    private static final String RE_CAPTCHA_REMOTE_ADDRESS = "127.0.0.1";
    private static final String USERNAME = "email1@test.com";
    private static final String PASSWORD = "password";
    private static final String ROLE = Role.ROLE_TEACHER.toString();

    @Autowired
    private SpringDataUserDetailsService service;

    @Autowired
    private ReCaptchaService reCaptchaService;

    @Test(expected = RuntimeException.class)
    @WithAnonymousUser
    public void missedReCaptchaThrowsException() {
        when(reCaptchaService.verify(RE_CAPTCHA_RESPONSE, RE_CAPTCHA_REMOTE_ADDRESS)).thenReturn(false);
        service.loadUserByUsername("");
    }

    @Test
    @WithAnonymousUser
    public void userDetailsReturnedForExistingUser() {
        when(reCaptchaService.verify(RE_CAPTCHA_RESPONSE, RE_CAPTCHA_REMOTE_ADDRESS)).thenReturn(true);
        UserDetails userdetails = service.loadUserByUsername(USERNAME);

        assertEquals(USERNAME, userdetails.getUsername());
        assertEquals(PASSWORD, userdetails.getPassword());
        assertEquals(ROLE, userdetails.getAuthorities().iterator().next().toString());
    }

    @Test(expected = UsernameNotFoundException.class)
    @WithAnonymousUser
    public void exceptionIsThrownForNotExistingUser() {
        when(reCaptchaService.verify(RE_CAPTCHA_RESPONSE, RE_CAPTCHA_REMOTE_ADDRESS)).thenReturn(true);
        service.loadUserByUsername("");
    }
}