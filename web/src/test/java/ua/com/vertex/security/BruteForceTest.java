package ua.com.vertex.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
import static ua.com.vertex.controllers.exceptionHandling.AppErrorController.ATTEMPTS;

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
    private static final String USERNAME_4 = "forBruteTest_4";

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

    @Test
    public void loginFailureHandlerRedirectsToErrorPageOnReachingMaxLimit() throws Exception {
        /* these are fine */
        for (int i = 0; i < loginAttempts - 1; i++) {
            mockMvc.perform(formLogin("/logIn").user(USERNAME_1).password(INCORRECT_PASSWORD))
                    .andExpect(redirectedUrl("/logIn?error"))
                    .andExpect(unauthenticated());
        }

        /* this one is redirected to error page after reaching max attempts number */
        mockMvc.perform(formLogin("/logIn").user(USERNAME_1).password(INCORRECT_PASSWORD))
                .andExpect(redirectedUrl("/error?reason=" + ATTEMPTS))
                .andExpect(unauthenticated());
    }

    @Test
    public void loginOkAfterWrongAttemptsAndThenCorrectOne() throws Exception {
        /* these are fine */
        for (int i = 0; i < loginAttempts - 2; i++) {
            mockMvc.perform(formLogin("/logIn").user(USERNAME_2).password(INCORRECT_PASSWORD))
                    .andExpect(redirectedUrl("/logIn?error"))
                    .andExpect(unauthenticated());
        }

        /* this one logs in */
        mockMvc.perform(formLogin("/logIn").user(USERNAME_2).password(CORRECT_PASSWORD))
                .andExpect(authenticated());
    }

    @Test
    public void loginFailureHandlerRedirectsToErrorPageOnLoggingInWhenBlocked() throws Exception {
        /* these are fine */
        for (int i = 0; i < loginAttempts - 1; i++) {
            mockMvc.perform(formLogin("/logIn").user(USERNAME_3).password(INCORRECT_PASSWORD))
                    .andExpect(redirectedUrl("/logIn?error"))
                    .andExpect(unauthenticated());
        }

        /* this one triggers blocking after reaching limit */
        mockMvc.perform(formLogin("/logIn").user(USERNAME_3).password(INCORRECT_PASSWORD))
                .andExpect(redirectedUrl("/error?reason=" + ATTEMPTS))
                .andExpect(unauthenticated());

        /* this one fails to log in with correct credentials while being blocked */
        mockMvc.perform(formLogin("/logIn").user(USERNAME_3).password(CORRECT_PASSWORD))
                .andExpect(redirectedUrl("/error?reason=" + ATTEMPTS))
                .andExpect(unauthenticated());
    }

    @Test
    public void loginOkAfterBlockingTimeElapsed() throws Exception {
        /* these are fine */
        for (int i = 0; i < loginAttempts - 1; i++) {
            mockMvc.perform(formLogin("/logIn").user(USERNAME_4).password(INCORRECT_PASSWORD))
                    .andExpect(redirectedUrl("/logIn?error"))
                    .andExpect(unauthenticated());
        }

        /* this one triggers blocking after reaching limit */
        mockMvc.perform(formLogin("/logIn").user(USERNAME_4).password(INCORRECT_PASSWORD))
                .andExpect(redirectedUrl("/error?reason=" + ATTEMPTS))
                .andExpect(unauthenticated());

        /* wait for blocking time to elapse */
        TimeUnit.SECONDS.sleep(blockingTime + 1);

        /* this one logs in with correct credentials */
        mockMvc.perform(formLogin("/logIn").user(USERNAME_4).password(CORRECT_PASSWORD))
                .andExpect(authenticated());
    }
}
