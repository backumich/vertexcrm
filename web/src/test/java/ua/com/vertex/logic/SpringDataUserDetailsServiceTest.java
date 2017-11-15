package ua.com.vertex.logic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.vertex.beans.Role;
import ua.com.vertex.context.TestConfig;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class SpringDataUserDetailsServiceTest {
    private static final String USERNAME = "email1@test.com";
    private static final String PASSWORD = "password";
    private static final String ROLE = Role.ROLE_TEACHER.toString();

    @Autowired
    private SpringDataUserDetailsService service;

    @Test
    @WithAnonymousUser
    public void userDetailsReturnedForExistingUser() {
        UserDetails userdetails = service.loadUserByUsername(USERNAME);

        assertEquals(USERNAME, userdetails.getUsername());
        assertEquals(PASSWORD, userdetails.getPassword());
        assertEquals(ROLE, userdetails.getAuthorities().iterator().next().toString());
    }

    @Test(expected = UsernameNotFoundException.class)
    @WithAnonymousUser
    public void exceptionIsThrownForNotExistingUser() {
        service.loadUserByUsername("");
    }
}