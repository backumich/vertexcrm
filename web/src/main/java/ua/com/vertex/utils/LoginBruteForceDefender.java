package ua.com.vertex.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@Component
@PropertySource("classpath:application.properties")
public class LoginBruteForceDefender {
    private static final Logger LOGGER = LogManager.getLogger(LoginBruteForceDefender.class);
    private final int loginBlockingTime;
    private final ConcurrentMap<String, Integer> loginAttempts;

    public LoginBruteForceDefender(@Value("${login.blocking.time.seconds}") int loginBlockingTime) {
        this.loginBlockingTime = loginBlockingTime;
        loginAttempts = initializeLoadingCache();
    }

    private ConcurrentMap<String, Integer> initializeLoadingCache() {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(loginBlockingTime, TimeUnit.SECONDS).build(
                        new CacheLoader<String, Integer>() {
                            @Override
                            public Integer load(String s) throws Exception {
                                return 1;
                            }
                        }
                ).asMap();
    }

    public int setCounter(String username) {
        int counter = loginAttempts.merge(username, 1, Integer::sum);
        LOGGER.debug(String.format("Login defender set: username=%s, counter=%d", username, counter));
        return counter;
    }

    public int checkCounter(String username) {
        int counter = loginAttempts.getOrDefault(username, 0);
        LOGGER.debug(String.format("Login defender check: username=%s, counter=%d", username, counter));
        return counter;
    }

    public void clearEntry(String username) {
        loginAttempts.remove(username);
        LOGGER.debug("Login defender cleared username=" + username);
    }
}