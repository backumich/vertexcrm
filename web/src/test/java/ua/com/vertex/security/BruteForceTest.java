package ua.com.vertex.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.com.vertex.context.TestConfig;

import java.util.concurrent.TimeUnit;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
@PropertySource("classpath:application.properties")
public class BruteForceTest {
    private static final String CORRECT_PASSWORD = "123456";
    private static final String INCORRECT_PASSWORD = "incorrect";
    private static final String USERNAME_1 = "forBruteTest_1";
    private static final String USERNAME_2 = "forBruteTest_2";
    private static final String USERNAME_3 = "forBruteTest_3";

    @Autowired
    private WebApplicationContext context;

    @Value("${login.attempts}")
    private int loginAttempts;

    @Value("${login.blocking.time.seconds}")
    private int blockingTime;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test(expected = RuntimeException.class)
    public void loginFailureHandlerThrowsExceptionOnReachingMaxLimit() throws Exception {
        /* these are fine */
        try {
            for (int i = 0; i < loginAttempts - 1; i++) {
                mockMvc.perform(formLogin("/logIn").user(USERNAME_1).password(INCORRECT_PASSWORD))
                        .andExpect(status().isFound())
                        .andExpect(redirectedUrl("/logIn?error"))
                        .andExpect(unauthenticated());
            }
        } catch (Exception e) {
            throw new Exception("This exception should not be here!");
        }

        /* this one throws exception after reaching max attempts number */
        mockMvc.perform(formLogin("/logIn").user(USERNAME_1).password(INCORRECT_PASSWORD));
    }

    @Test(expected = InternalAuthenticationServiceException.class)
    public void loginFailureHandlerThrowsExceptionOnLoggingInWhenBlocked() throws Exception {
        /* these trigger blocking after reaching limit */
        try {
            for (int i = 0; i < loginAttempts; i++) {
                mockMvc.perform(formLogin("/logIn").user(USERNAME_2).password(INCORRECT_PASSWORD))
                        .andExpect(redirectedUrl("/logIn?error"))
                        .andExpect(unauthenticated());
            }
        } catch (Exception e) {
            /* nothing to do here */
        }

        /* this one fails to log in with correct credentials while being blocked */
        mockMvc.perform(formLogin("/logIn").user(USERNAME_2).password(CORRECT_PASSWORD));
    }

    @Test
    public void loginOkAfterBlockingTimeElapsed() throws Exception {
        /* these trigger blocking after reaching limit */
        try {
            for (int i = 0; i < loginAttempts; i++) {
                mockMvc.perform(formLogin("/logIn").user(USERNAME_3).password(INCORRECT_PASSWORD))
                        .andExpect(redirectedUrl("/logIn?error"))
                        .andExpect(unauthenticated());
            }
        } catch (Exception e) {
        }

        /* wait for blocking time to elapse */
        TimeUnit.SECONDS.sleep(blockingTime + 1);

        /* this one logs in with correct credentials */
        mockMvc.perform(formLogin("/logIn").user(USERNAME_3).password(CORRECT_PASSWORD))
                .andExpect(authenticated());
    }
}
