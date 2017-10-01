package ua.com.vertex.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import ua.com.vertex.logic.SpringDataUserDetailsService;
import ua.com.vertex.utils.LoginBruteForceDefender;

import static ua.com.vertex.utils.LoginBruteForceDefender.BLOCKED_NUMBER;

@Configuration
@EnableWebSecurity
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {
    public static final String UNKNOWN_ERROR = "Unknown error during logging in. Database might be offline";
    public static final String RE_CAPTCHA = "ReCaptcha on logging in was missed";
    public static final String LOGIN_ATTEMPTS = "Login attempts counter has been exceeded for this username!";
    private static final int VALIDITY_SECONDS = 604800;

    private final BCryptPasswordEncoder passwordEncoder;
    private final LoginBruteForceDefender defender;

    @Autowired
    public SecurityWebConfig(BCryptPasswordEncoder passwordEncoder, LoginBruteForceDefender defender) {
        this.passwordEncoder = passwordEncoder;
        this.defender = defender;
    }

    @Bean
    public SpringDataUserDetailsService springDataUserDetailsService() {
        return new SpringDataUserDetailsService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);

        http.authorizeRequests()
                .anyRequest().permitAll()

                .and()
                .formLogin()
                .loginPage("/logIn")
                .successHandler(((request, response, authentication) -> {
                    defender.clearEntry(authentication.getName());
                    response.sendRedirect("/loggedIn");
                }))
                .failureHandler((request, response, e) -> {
                    if (e instanceof BadCredentialsException) {
                        if (defender.increment(request.getParameter("username")) == BLOCKED_NUMBER) {
                            throw new RuntimeException(LOGIN_ATTEMPTS);
                        }
                        response.sendRedirect("/logIn?error");
                    } else if (e.getMessage().equals(LOGIN_ATTEMPTS)) {
                        throw new RuntimeException(LOGIN_ATTEMPTS);
                    } else if (e.getMessage().equals(RE_CAPTCHA)) {
                        throw new RuntimeException(RE_CAPTCHA, e);
                    } else {
                        throw new RuntimeException(UNKNOWN_ERROR, e);
                    }
                })
                .permitAll()

                .and()
                .logout()
                .logoutUrl("/logOut")
                .logoutSuccessUrl("/")

                .and()
                .rememberMe()
                .tokenValiditySeconds(VALIDITY_SECONDS)

                .and()
                .exceptionHandling().accessDeniedPage("/403")
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(springDataUserDetailsService())
                .passwordEncoder(passwordEncoder);
    }
}
