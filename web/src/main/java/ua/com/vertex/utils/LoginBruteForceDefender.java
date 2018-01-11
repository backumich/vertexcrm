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
@PropertySource("classpath:application.properties")
public class LoginBruteForceDefender {
    private static final Logger LOGGER = LogManager.getLogger(LoginBruteForceDefender.class);
    public static final int BLOCKED_NUMBER = -1;
    private final int maxAttempts;
    private final int loginBlockingTime;
    private LoadingCache<String, Integer> loginAttempts;

    public LoginBruteForceDefender(@Value("${login.attempts}") int maxAttempts,
                                   @Value("${login.blocking.time.seconds}") int loginBlockingTime) {
        this.maxAttempts = maxAttempts;
        this.loginBlockingTime = loginBlockingTime;
        loginAttempts = initializeLoadingCache();
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

    public synchronized int verifyUsername(String username) {
        int counter = loginAttempts.asMap().merge(username, 1, Integer::sum);
        return counter < maxAttempts ? counter : BLOCKED_NUMBER;
    }

    public synchronized void clearEntry(String username) {
        loginAttempts.asMap().remove(username);
        LOGGER.debug("Login defender cleared username=" + username);
    }
}