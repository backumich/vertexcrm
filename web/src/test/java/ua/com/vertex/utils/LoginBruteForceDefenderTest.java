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

        int count1;
        int count2;

        defender.setCounter("counter1");
        defender.setCounter("counter1");
        count1 = defender.setCounter("counter1");
        assertEquals(3, count1);

        defender.setCounter("counter2");
        count2 = defender.setCounter("counter2");
        assertEquals(3, count1);
        assertEquals(2, count2);
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