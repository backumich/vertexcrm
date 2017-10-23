package ua.com.vertex.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import ua.com.vertex.logic.SpringDataUserDetailsService;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {
    public static final String UNKNOWN_ERROR = "Unknown error during logging in. Database might be offline";
    private final SpringDataUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${remember.me.validity.seconds}")
    private int validityTime;

    @Autowired
    public SecurityWebConfig(SpringDataUserDetailsService userDetailsService,
                             BCryptPasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
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
                .successForwardUrl("/loggedIn")
                .failureHandler((request, response, e) -> {
                    if (e instanceof BadCredentialsException) {
                        response.sendRedirect("/logIn?error");
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
                .tokenValiditySeconds(validityTime)

                .and()
                .exceptionHandling().accessDeniedPage("/403")
        ;
    }
}
