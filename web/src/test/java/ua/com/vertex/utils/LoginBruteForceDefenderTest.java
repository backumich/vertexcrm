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
import static ua.com.vertex.utils.LoginBruteForceDefender.BLOCKED_NUMBER;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
@PropertySource("classpath:application.properties")
public class LoginBruteForceDefenderTest {

    @Value("${login.attempts}")
    private int loginAttempts;

    @Value("${login.blocking.time.seconds}")
    private int blockingTime;

    private LoginBruteForceDefender defender;

    @Test
    public void verifyUsernameIncrementsCorrectly() {
        defender = new LoginBruteForceDefender(loginAttempts, blockingTime);

        int count = 0;
        for (int i = 1; i <= loginAttempts; i++) {
            count = defender.setCounter("username");
            if (i < loginAttempts) {
                assertEquals(i, count);
            }
        }
        assertEquals(BLOCKED_NUMBER, count);
    }

    @Test
    public void clearEntryWorksCorrectly() {
        defender = new LoginBruteForceDefender(loginAttempts, blockingTime);

        int count = 0;
        for (int i = 0; i < 3; i++) {
            count = defender.setCounter("username");
        }

        assertEquals(3, count);
        defender.clearEntry("username");
        count = defender.setCounter("username");
        assertEquals(1, count);
    }

    @Test
    public void multithreadingWorksCorrectly() throws InterruptedException {
        defender = new LoginBruteForceDefender(loginAttempts, blockingTime);

        Thread thread1 = new Thread(() -> multithreadingWorksCorrectlyHelper("username1"));
        Thread thread2 = new Thread(() -> multithreadingWorksCorrectlyHelper("username2"));
        thread1.start();
        TimeUnit.MILLISECONDS.sleep(200);
        thread2.start();
    }

    private void multithreadingWorksCorrectlyHelper(String username) {
        int count = 0;
        for (int i = 1; i <= 10; i++) {
            count = defender.setCounter(username);
            if (i < 10) {
                assertEquals(i, count);
            }
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
            }
        }
        assertEquals(BLOCKED_NUMBER, count);
    }

    @Test
    public void entryGetsClearedAfterTimeElapsed() throws InterruptedException {
        defender = new LoginBruteForceDefender(loginAttempts, blockingTime);

        int count = defender.setCounter("username");
        assertEquals(1, count);
        TimeUnit.SECONDS.sleep(blockingTime + 1);
        count = defender.setCounter("username");
        assertEquals(1, count);
    }
}