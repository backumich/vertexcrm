package ua.com.vertex.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@PropertySource("classpath:application.properties")
public class LoginBruteForceDefender {
    private static final Logger LOGGER = LogManager.getLogger(LoginBruteForceDefender.class);
    public static final int BLOCKED_NUMBER = -1;
    private final int maxAttempts;
    private final int loginBlockingTime;
    private Map<String, Integer> loginAttempts;

    public LoginBruteForceDefender(@Value("${login.attempts}") int maxAttempts,
                                   @Value("${login.blocking.time.seconds}") int loginBlockingTime) {
        this.maxAttempts = maxAttempts;
        this.loginBlockingTime = loginBlockingTime;
        loginAttempts = initializeLoadingCache().asMap();
    }

    private LoadingCache<String, Integer> initializeLoadingCache() {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(loginBlockingTime, TimeUnit.SECONDS).build(
                        new CacheLoader<String, Integer>() {
                            @Override
                            public Integer load(String s) throws Exception {
                                return 1;
                            }
                        }
                );
    }

    public synchronized int setCounter(String username) {
        int counter = loginAttempts.computeIfAbsent(username, (unused) -> 0);
        if (counter != BLOCKED_NUMBER) {
            counter = loginAttempts.merge(username, 1, Integer::sum);
        }
        if (counter == maxAttempts) {
            counter = loginAttempts.compute(username, (unused1, unused2) -> BLOCKED_NUMBER);
        }
        LOGGER.debug(String.format("Login defender set: username=%s, counter=%d", username, counter));
        return counter;
    }

    public synchronized int checkCounter(String username) {
        int counter = loginAttempts.getOrDefault(username, 0);
        LOGGER.debug(String.format("Login defender check: username=%s, counter=%d", username, counter));
        return counter < maxAttempts ? counter : BLOCKED_NUMBER;
    }

    public synchronized void clearEntry(String username) {
        loginAttempts.remove(username);
        LOGGER.debug("Login defender cleared username=" + username);
    }
}