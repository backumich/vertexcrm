package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.LoggingLogic;
import ua.com.vertex.utils.ReCaptchaService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static ua.com.vertex.context.SecurityWebConfig.RE_CAPTCHA;

public class SpringDataUserDetailsService implements UserDetailsService {
    private LoggingLogic loggingLogic;
    private ReCaptchaService reCaptchaService;
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String reCaptchaResponse = request.getParameter("g-recaptcha-response");
        String reCaptchaRemoteAddr = request.getRemoteAddr();
        User user;
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (reCaptchaService.verify(reCaptchaResponse, reCaptchaRemoteAddr)) {
            user = loggingLogic.logIn(username)
                    .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
            authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        } else {
            throw new RuntimeException(RE_CAPTCHA);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                authorities);
    }

    @Autowired
    public void setSpringDataUserDetailsService(LoggingLogic loggingLogic) {
        this.loggingLogic = loggingLogic;
    }

    @Autowired
    public void setReCaptchaVerification(ReCaptchaService reCaptchaService) {
        this.reCaptchaService = reCaptchaService;
    }

    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
