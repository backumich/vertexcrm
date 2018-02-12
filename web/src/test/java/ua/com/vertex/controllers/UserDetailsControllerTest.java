package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.logic.interfaces.CertificateLogic;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.UtilFunctions;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static ua.com.vertex.logic.UserLogicImpl.FILE_SIZE;
import static ua.com.vertex.logic.UserLogicImpl.FILE_TYPE;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
@PropertySource("classpath:application.properties")
@Transactional
public class UserDetailsControllerTest {

    @Autowired
    private UserDetailsController controller;

    @Autowired
    private UserLogic logic;

    @Autowired
    private CertificateLogic certificateLogic;

    private UserDetailsController userDetailsController;

    private User user;

    @Value("${image.size.bytes}")
    private int fileSizeInBytes;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userDetailsController = new UserDetailsController(logic, certificateLogic);
        user = new User.Builder().setUserId(22).setRole(Role.ROLE_USER).setEmail("22@test.com").setPassword("password")
                .setFirstName("FirstName").setLastName("LastName").getInstance();
    }

    @Test
    public void userDetailsControllerReturnedPassViewTest() throws Exception {
        MockMvc mockMvc = standaloneSetup(userDetailsController)
                .setSingleView(new InternalResourceView("userDetails"))
                .build();
        mockMvc.perform(get("/userDetails")
                .param("userId", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(view().name("userDetails"));
    }

    @Test
    public void viewAllUsersControllerCheckDataTest() throws Exception {
        User returned = logic.getUserById(22).orElse(User.EMPTY_USER);

        assertEquals(returned.getUserId(), user.getUserId());
        assertEquals(returned.getRole(), user.getRole());
        assertEquals(returned.getEmail(), user.getEmail());
        assertEquals(returned.getPassword(), user.getPassword());
        assertEquals(returned.getFirstName(), user.getFirstName());
        assertEquals(returned.getLastName(), user.getLastName());
    }

    @Test
    public void getUserDetailsByIDRequestingWrongId() throws Exception {
        Optional<User> optional = logic.getUserById(-1);
        assertFalse(optional.isPresent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getUserDetailsValidAuthorization() {
        String view = controller.getUserDetails(1).getViewName();
        assertNotNull(view);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles = "USER")
    public void getUserDetailsInvalidAuthorization() {
        controller.getUserDetails(1);
    }

    @Test(expected = AccessDeniedException.class)
    @WithAnonymousUser
    public void getUserDetailsUnauthorized() {
        controller.getUserDetails(1);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void saveUserDataValidAuthorization() throws Exception {
        String view = controller.saveUserData(new MockMultipartFile("name", new byte[]{}),
                new MockMultipartFile("name", new byte[]{}), user, mock(BindingResult.class),
                new ModelAndView()).getViewName();

        assertNotNull(view);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles = "USER")
    public void saveUserDataInvalidAuthorization() throws Exception {
        controller.saveUserData(null, null, user, mock(BindingResult.class), new ModelAndView());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles = "USER")
    public void saveUserDataUnauthorized() throws Exception {
        controller.saveUserData(null, null, user, mock(BindingResult.class), new ModelAndView());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void passportSaved() throws IOException {
        byte[] bytes = {1, 2, 3, 4, 5};

        controller.saveUserData(new MockMultipartFile("file", "file", "image/jpg", bytes),
                new MockMultipartFile("file", "file", "image/jpg", new byte[]{}), user, mock(BindingResult.class),
                new ModelAndView()).getViewName();

        User updatedUser = logic.getUserById(22).orElse(User.EMPTY_USER);
        assertTrue(Arrays.equals(bytes, updatedUser.getPassportScan()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void photoSaved() throws IOException {
        byte[] bytes = {1, 2, 3, 4, 5};

        controller.saveUserData(new MockMultipartFile("file", "file", "image/jpg", new byte[]{}),
                new MockMultipartFile("file", "file", "image/jpg", bytes), user, mock(BindingResult.class),
                new ModelAndView()).getViewName();

        User updatedUser = logic.getUserById(22).orElse(User.EMPTY_USER);
        assertTrue(Arrays.equals(bytes, updatedUser.getPhoto()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void tooBigToSave() throws IOException {
        byte[] bytes = new byte[fileSizeInBytes + 1];
        BindingResult result = mock(BindingResult.class);

        controller.saveUserData(new MockMultipartFile("file", "file", "image/jpg", bytes),
                new MockMultipartFile("file", "file", "image/jpg", new byte[]{}), user, result,
                new ModelAndView());

        Mockito.verify(result).rejectValue("passportScan", "error.passportScan",
                FILE_SIZE + UtilFunctions.humanReadableByteCount(fileSizeInBytes));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void wrongImageType() throws IOException {
        byte[] bytes = {1};
        BindingResult result = mock(BindingResult.class);

        controller.saveUserData(new MockMultipartFile("file", "file", "wrong/jpg", bytes),
                new MockMultipartFile("file", "file", "wrong/jpg", new byte[]{}), user, result,
                new ModelAndView());

        Mockito.verify(result).rejectValue("passportScan", "error.passportScan", FILE_TYPE);
    }
}