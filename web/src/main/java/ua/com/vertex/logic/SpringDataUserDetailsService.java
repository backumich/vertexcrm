package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.LoggingLogic;
import ua.com.vertex.utils.ReCaptchaService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static ua.com.vertex.context.SecurityWebConfig.RE_CAPTCHA;

@Service
public class SpringDataUserDetailsService implements UserDetailsService {
    private final LoggingLogic loggingLogic;
    private final ReCaptchaService reCaptchaService;
    private final HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (reCaptchaService.verify(request.getParameter("g-recaptcha-response"), request.getRemoteAddr())) {
            User user = loggingLogic.logIn(username)
                    .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                    authorities);
        } else {
            throw new RuntimeException(RE_CAPTCHA);
        }
    }

    @Autowired
    public SpringDataUserDetailsService(LoggingLogic loggingLogic, ReCaptchaService reCaptchaService,
                                        HttpServletRequest request) {
        this.loggingLogic = loggingLogic;
        this.reCaptchaService = reCaptchaService;
        this.request = request;
    }
}