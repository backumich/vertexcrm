package ua.com.vertex.context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import ua.com.vertex.logic.SpringDataUserDetailsService;
import ua.com.vertex.utils.LoginBruteForceDefender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

import static ua.com.vertex.controllers.exceptionHandling.AppErrorController.*;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LogManager.getLogger(SecurityWebConfig.class);
    private final SpringDataUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final LoginBruteForceDefender defender;
    private final DataSource dataSource;

    @Value("${remember.me.validity.seconds}")
    private int validityTime;

    @Value("${login.attempts}")
    private int maxAttempts;

    @Autowired
    public SecurityWebConfig(SpringDataUserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder,
                             LoginBruteForceDefender defender, @Qualifier("DS") DataSource dataSource) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.defender = defender;
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);

        http
                .authorizeRequests()
                .anyRequest().permitAll()

                .and()
                .formLogin()
                .loginPage("/logIn")
                .successHandler(((request, response, authentication) -> {
                    defender.clearEntry(authentication.getName());
                    response.sendRedirect("/loggedIn");
                }))
                .failureHandler(this::handleFailure)
                .permitAll()

                .and()
                .logout()
                .logoutUrl("/logOut")
                .logoutSuccessUrl("/")

                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(validityTime)

                .and()
                .exceptionHandling().accessDeniedPage("/403")
        ;
    }

    private void handleFailure(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        String username = request.getParameter("username");

        if (e.getMessage().equals(LOGIN_ATTEMPTS)) {
            response.sendRedirect(getLink(username));

        } else if (defender.setCounter(username) >= maxAttempts) {
            response.sendRedirect(getLink(username));

        } else if (e instanceof BadCredentialsException) {
            logger.debug(() -> e);
            response.sendRedirect("/logIn?error");

        } else {
            logger.error(() -> e);
            response.sendRedirect("/error?reason=" + UNKNOWN);
        }
    }

    private String getLink(String username) {
        return String.format("/error?reason=%s&username=%s", ATTEMPTS, username);
    }

    @Bean
    PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }
}
