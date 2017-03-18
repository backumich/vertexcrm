package ua.com.vertex.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.com.vertex.context.TestConfig;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class LogInLogOutTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private static final String CORRECT_PASSWORD = "123456";
    private static final String INCORRECT_PASSWORD = "1234567";
    private static final String EXISTING_EMAIL = "44@test.com";
    private static final String NOT_EXISTING_EMAIL = "not_existing@test.com";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testFormLoginWithCorrectLoginAndPassword() throws Exception {
        mockMvc.perform(post("/logIn")
                .param("username", EXISTING_EMAIL)
                .param("password", CORRECT_PASSWORD)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/loggedIn"));
    }

    @Test
    public void testFormLoginWithIncorrectPassword() throws Exception {
        mockMvc.perform(post("/logIn")
                .param("username", EXISTING_EMAIL)
                .param("password", INCORRECT_PASSWORD)
                .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/logIn?error"));
    }

    @Test
    public void testFormLoginWithIncorrectLogin() throws Exception {
        mockMvc.perform(post("/logIn")
                .param("username", NOT_EXISTING_EMAIL)
                .param("password", CORRECT_PASSWORD)
                .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/logIn?error"));
    }

    @Test
    @WithMockUser
    public void testFormLogout() throws Exception {
        mockMvc.perform(post("/logOut")
                .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
    }
}
