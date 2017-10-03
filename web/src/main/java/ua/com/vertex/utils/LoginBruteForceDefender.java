package ua.com.vertex.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@PropertySource("classpath:reCaptcha.properties")
public class LoginBruteForceDefender {
    private static final Logger LOGGER = LogManager.getLogger(LoginBruteForceDefender.class);
    public static final int BLOCKED_NUMBER = -1;
    private final int maxAttempts;
    private final int loginBlockingTime;
    private LoadingCache<String, Integer> loginAttempts;

    public LoginBruteForceDefender(@Value("${login.attempts}") int maxAttempts,
                                   @Value("${login.blocking.time}") int loginBlockingTime) {
        this.maxAttempts = maxAttempts;
        this.loginBlockingTime = loginBlockingTime;
        loginAttempts = initializeLoadingCache();
    }

    private LoadingCache<String, Integer> initializeLoadingCache() {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(loginBlockingTime, TimeUnit.MINUTES).build(
                        new CacheLoader<String, Integer>() {
                            @Override
                            public Integer load(String s) throws Exception {
                                return 1;
                            }
                        }
                );
    }

    public synchronized int verifyUsername(String username) {
        int counter;
        if (loginAttempts.asMap().containsKey(username)) {
            counter = incrementCounterOrBlockUsername(username);
        } else {
            loginAttempts.put(username, 1);
            counter = 1;
            LOGGER.debug(String.format("Login defender incremented for username=%s, counter=%d", username, counter));
        }
        return counter;
    }

    private int incrementCounterOrBlockUsername(String username) {
        int counter = loginAttempts.asMap().get(username);
        if (++counter < maxAttempts) {
            loginAttempts.put(username, counter);
            LOGGER.debug(String.format("Login defender incremented for username=%s, counter=%d", username, counter));
        } else {
            counter = BLOCKED_NUMBER;
            LOGGER.debug(String.format("Login defender blocked username=%s", username));
        }
        return counter;
    }

    public synchronized void clearEntry(String username) {
        loginAttempts.asMap().remove(username);
        LOGGER.debug("Login defender was cleared for username=" + username);
    }
}
