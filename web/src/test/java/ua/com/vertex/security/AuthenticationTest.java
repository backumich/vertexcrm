package ua.com.vertex.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.com.vertex.context.TestConfig;

import java.util.Collection;
import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class AuthenticationTest {
    private static final String CORRECT_PASSWORD = "123456";
    private static final String EXISTING_EMAIL = "44@test.com";
    private static final String INCORRECT_DATA = "incorrect";
    private static final String ADMIN_EMAIL = "33@test.com";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithAnonymousUser
    public void userTestFormLoginWithCorrectLoginAndPassword() throws Exception {
        Collection<? extends GrantedAuthority> authorities = Collections
                .singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        mockMvc.perform(formLogin("/logIn").user(EXISTING_EMAIL).password(CORRECT_PASSWORD))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/loggedIn"))
                .andExpect(authenticated().withUsername(EXISTING_EMAIL))
                .andExpect(authenticated().withAuthorities(authorities));
    }

    @Test
    @WithAnonymousUser
    public void adminTestFormLoginWithCorrectLoginAndPassword() throws Exception {
        Collection<? extends GrantedAuthority> authorities = Collections
                .singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        mockMvc.perform(formLogin("/logIn").user(ADMIN_EMAIL).password(CORRECT_PASSWORD))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/loggedIn"))
                .andExpect(authenticated().withUsername(ADMIN_EMAIL))
                .andExpect(authenticated().withAuthorities(authorities));
    }

    @Test
    @WithAnonymousUser
    public void testFormLoginWithIncorrectPassword() throws Exception {
        mockMvc.perform(formLogin("/logIn").user(EXISTING_EMAIL).password(INCORRECT_DATA))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/logIn?error"))
                .andExpect(unauthenticated());
    }

    @Test
    @WithAnonymousUser
    public void testFormLoginWithIncorrectLogin() throws Exception {
        mockMvc.perform(formLogin("/logIn").user(INCORRECT_DATA).password(CORRECT_PASSWORD))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/logIn?error"))
                .andExpect(unauthenticated());
    }

    @Test
    @WithAnonymousUser
    public void loginWithValidCsrf() throws Exception {
        mockMvc.perform(post("/logIn")
                .param("username", EXISTING_EMAIL)
                .param("password", CORRECT_PASSWORD)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/loggedIn"))
                .andExpect(authenticated());
    }

    @Test
    @WithAnonymousUser
    public void loginWithInvalidCsrf() throws Exception {
        mockMvc.perform(post("/logIn")
                .param("username", EXISTING_EMAIL)
                .param("password", CORRECT_PASSWORD)
                .with(csrf().useInvalidToken()))
                .andExpect(status().isForbidden())
                .andExpect(forwardedUrl("/403"))
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    public void testFormLogout() throws Exception {
        mockMvc.perform(logout("/logOut"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"))
                .andExpect(unauthenticated());
    }
}
