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

    @Value("${login.attempts}")
    private int maxAttempts;

    @Value("${login.blocking.time}")
    private int loginBlockingTime;

    private LoadingCache<String, Integer> setLoadingCache() {
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

    public int increment(String username) {
        if (loginAttempts == null) {
            loginAttempts = setLoadingCache();
        }
        synchronized (this) {
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
    }

    public void clearEntry(String username) {
        if (loginAttempts == null) {
            loginAttempts = setLoadingCache();
        }
        synchronized (this) {
            loginAttempts.asMap().remove(username);
        }
    }
}
