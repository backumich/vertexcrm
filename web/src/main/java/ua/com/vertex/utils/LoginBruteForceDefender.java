package ua.com.vertex.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@PropertySource("classpath:reCaptcha.properties")
public class LoginBruteForceDefender {
    public static final int BLOCKED_NUMBER = -1;
    private LoadingCache<String, Integer> loginAttempts;
    private int maxAttempts;
    private int loginBlockingTime;

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

    public synchronized int increment(String username) {
        int counter = 0;
        if (loginAttempts.asMap().containsKey(username)) {
            counter = loginAttempts.asMap().get(username);
            if (++counter < maxAttempts) {
                loginAttempts.put(username, counter);
            } else {
                counter = BLOCKED_NUMBER;
            }
        } else {
            loginAttempts.put(username, 1);
        }
        return counter;
    }

    public synchronized void clearEntry(String username) {
        loginAttempts.asMap().remove(username);
    }
}
