package ua.com.vertex.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.vertex.context.TestConfig;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ua.com.vertex.utils.LoginBruteForceDefender.BLOCKED_NUMBER;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
@PropertySource("classpath:reCaptcha.properties")
public class LoginBruteForceDefenderTest {

    @Value("${login.attempts}")
    private int propertiesLoginAttemptsValue;
    private static final int MIN_ALLOWED = 2;

    private LoginBruteForceDefender defender;
    private int maxAttempts;
    private int loginBlockingTime;

    @Test
    public void verifyUsernameIncrementsCorrectly() {
        maxAttempts = 10;
        loginBlockingTime = 60;
        defender = new LoginBruteForceDefender(maxAttempts, loginBlockingTime);

        int count = 0;
        for (int i = 1; i <= 10; i++) {
            count = defender.verifyUsername("username");
            if (i < 10) {
                assertEquals(i, count);
            }
        }
        assertEquals(BLOCKED_NUMBER, count);
    }

    @Test
    public void clearEntryWorksCorrectly() {
        maxAttempts = 5;
        loginBlockingTime = 60;
        defender = new LoginBruteForceDefender(maxAttempts, loginBlockingTime);

        int count = 0;
        for (int i = 1; i < 4; i++) {
            count = defender.verifyUsername("username");
        }

        assertEquals(3, count);
        defender.clearEntry("username");
        count = defender.verifyUsername("username");
        assertEquals(1, count);
    }

    @Test
    public void multithreadingWorksCorrectly() throws InterruptedException {
        maxAttempts = 10;
        loginBlockingTime = 60;
        defender = new LoginBruteForceDefender(maxAttempts, loginBlockingTime);

        Thread thread1 = new Thread(() -> multithreadingWorksCorrectlyHelper("username1"));
        Thread thread2 = new Thread(() -> multithreadingWorksCorrectlyHelper("username2"));
        thread1.start();
        TimeUnit.MILLISECONDS.sleep(200);
        thread2.start();
    }

    private void multithreadingWorksCorrectlyHelper(String username) {
        int count = 0;
        for (int i = 1; i <= 10; i++) {
            count = defender.verifyUsername(username);
            if (i < 10) {
                assertEquals(i, count);
            }
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertEquals(BLOCKED_NUMBER, count);
    }

    @Test
    public void entryGetsClearedAfterTimeElapsed() throws InterruptedException {
        maxAttempts = 10;
        loginBlockingTime = 1;
        defender = new LoginBruteForceDefender(maxAttempts, loginBlockingTime);

        int count = defender.verifyUsername("username");
        assertEquals(1, count);
        TimeUnit.SECONDS.sleep(2);
        count = defender.verifyUsername("username");
        assertEquals(1, count);
    }

    @Test
    public void checkPropertiesLoginAttemptsValue() {
        assertTrue(propertiesLoginAttemptsValue >= MIN_ALLOWED);
    }
}