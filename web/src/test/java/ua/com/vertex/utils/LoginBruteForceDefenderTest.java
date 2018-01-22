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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

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
        defender = new LoginBruteForceDefender(blockingTime);

        int count;
        for (int i = 1; i <= loginAttempts; i++) {
            count = defender.setCounter("username");
            assertEquals(i, count);
        }
    }

    @Test
    public void clearEntryWorksCorrectly() {
        defender = new LoginBruteForceDefender(blockingTime);

        for (int i = 0; i < 3; i++) {
            defender.setCounter("username");
        }

        assertEquals(3, defender.checkCounter("username"));
        defender.clearEntry("username");
        defender.setCounter("username");
        assertEquals(1, defender.checkCounter("username"));
    }

    @Test
    public void multithreadingWorksCorrectly() throws InterruptedException {
        defender = new LoginBruteForceDefender(blockingTime);

        final int numOfTasks = 100;

        List<Callable<Object>> tasks1 = prepareTasks1(numOfTasks);

        ExecutorService exec1 = Executors.newFixedThreadPool(6);
        exec1.invokeAll(tasks1);
        exec1.shutdown();
        exec1.awaitTermination(5, TimeUnit.SECONDS);

        List<Callable<Object>> tasks2 = prepareTasks2(numOfTasks);

        ExecutorService exec2 = Executors.newFixedThreadPool(6);
        exec2.invokeAll(tasks2);
        exec2.shutdown();
        exec2.awaitTermination(5, TimeUnit.SECONDS);

        assertEquals(defender.checkCounter("count1"), numOfTasks);
        assertEquals(defender.checkCounter("count2"), numOfTasks * 2);
    }

    private List<Callable<Object>> prepareTasks1(int number) {
        Callable<Object> task = () -> {
            defender.setCounter("count1");
            defender.setCounter("count2");
            return null;
        };
        return fillTaskList(task, number);
    }

    private List<Callable<Object>> fillTaskList(Callable<Object> task, int number) {
        List<Callable<Object>> tasks = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            tasks.add(task);
        }
        return tasks;
    }

    private List<Callable<Object>> prepareTasks2(int number) {
        Callable<Object> task = () -> {
            defender.setCounter("count2");
            return null;
        };
        return fillTaskList(task, number);
    }

    @Test
    public void entryGetsClearedAfterTimeElapsed() throws InterruptedException {
        defender = new LoginBruteForceDefender(blockingTime);

        defender.setCounter("username");
        assertEquals(1, defender.checkCounter("username"));
        TimeUnit.SECONDS.sleep(blockingTime + 1);
        assertEquals(0, defender.checkCounter("username"));
    }
}