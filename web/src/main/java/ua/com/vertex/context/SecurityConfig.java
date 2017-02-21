package ua.com.vertex.context;

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

import static ua.com.vertex.beans.Role.ADMIN;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final int ENCRYPTION_STRENGTH = 10;
    private static final int VALIDITY_SECONDS = 604800;
    private final String[] permittedAllRequests = {"/css/**", "/javascript/**", "/", "/registration",
            "/logIn", "/logOut", "/loggedOut", "/certificateDetails", "/processCertificateDetails",
            "/userPhoto", "/403", "/error"};
    private final String[] permittedAdminRequests = {"/viewAllUsers", "/userDetails", "/saveUserData"};


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
        auth.userDetailsService(springDataUserDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);

        http.authorizeRequests()
                .antMatchers(permittedAllRequests)
                .permitAll()
                .antMatchers(permittedAdminRequests).hasAuthority(ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/logIn")
                .successForwardUrl("/loggedIn")
                .failureHandler((request, response, e) -> {
                    if (e instanceof BadCredentialsException) {
                        request.setAttribute("param", "error");
                        response.sendRedirect("/logIn?error");
                    } else {
                        response.sendRedirect("/error");
                    }
                })
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logOut")
                .logoutSuccessUrl("/loggedOut")
                .and()
                .rememberMe()
                .tokenValiditySeconds(VALIDITY_SECONDS)
                .and()
                .exceptionHandling().accessDeniedPage("/403")
        ;
    }
}
