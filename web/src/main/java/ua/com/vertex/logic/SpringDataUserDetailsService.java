package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.User;
import ua.com.vertex.controllers.exceptionHandling.exceptions.LoginAttemptsException;
import ua.com.vertex.logic.interfaces.LoggingLogic;
import ua.com.vertex.utils.LoginBruteForceDefender;

import java.util.ArrayList;
import java.util.List;

import static ua.com.vertex.controllers.exceptionHandling.AppErrorController.LOGIN_ATTEMPTS;

@Service
@PropertySource("classpath:application.properties")
public class SpringDataUserDetailsService implements UserDetailsService {
    private final LoggingLogic loggingLogic;
    private final LoginBruteForceDefender defender;

    @Value("${login.attempts}")
    private int maxAttempts;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (defender.checkCounter(username) >= maxAttempts) {
            throw new LoginAttemptsException(LOGIN_ATTEMPTS);
        }
        User user = loggingLogic.logIn(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                authorities);
    }

    @Autowired
    public SpringDataUserDetailsService(LoggingLogic loggingLogic, LoginBruteForceDefender defender) {
        this.loggingLogic = loggingLogic;
        this.defender = defender;
    }
}