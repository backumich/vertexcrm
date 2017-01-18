package ua.com.vertex.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.com.vertex.logic.SpringDataUserDetailsService;

import static ua.com.vertex.utils.Role.ADMIN;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final int ENCRYPTION_STRENGTH = 10;
    private static final int VALIDITY_SECONDS = 604800;
    private final String[] permittedAllRequests = {"/css/**", "/javascript/**", "/", "/registration",
            "/logIn", "/logOut", "/loggedOut", "/certificateDetails",
            "/certificateHolderPhoto"};
    private final String[] permittedAdminRequests = {"/processCertificateDetails"};

    @Bean
    public SpringDataUserDetailsService springDataUserDetailsService() {
        return new SpringDataUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(ENCRYPTION_STRENGTH);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(springDataUserDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(permittedAllRequests)
                .permitAll()
                .antMatchers(permittedAdminRequests).hasAuthority(ADMIN.toString())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/logIn")
                .successForwardUrl("/loggedIn")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logOut")
                .logoutSuccessUrl("/loggedOut")
                .and()
                .rememberMe()
                .tokenValiditySeconds(VALIDITY_SECONDS)
        ;
    }
}
