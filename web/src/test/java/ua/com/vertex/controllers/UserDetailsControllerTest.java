package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
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

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
@Transactional

public class UserDetailsControllerTest {

    @Autowired
    private UserDetailsController controllerWired;

    @Autowired
    private UserLogic logicWired;

    @Mock
    private UserLogic logic;

    @Mock
    private CertificateLogic certificateLogic;

    private UserDetailsController userDetailsController;

    private User user;
    private Optional<User> optional;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userDetailsController = new UserDetailsController(logic, certificateLogic);
        user = new User.Builder().getInstance();
        optional = Optional.ofNullable(user);
    }

    @Test
    public void userDetailsControllerReturnedPassViewTest() throws Exception {
        when(logic.getUserById(1)).thenReturn(optional);
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
        String passportScan = "passportScan";
        byte[] passportScanByte = passportScan.getBytes();

        String photo = "photo";
        byte[] photoByte = photo.getBytes();

        User testUser = new User();
        testUser.setUserId(1);
        testUser.setEmail("chewed.mole@gmail.com");
        testUser.setLastName("Bond");
        testUser.setFirstName("James");
        testUser.setPassportScan(passportScanByte);
        testUser.setPhoto(photoByte);
        testUser.setDiscount(10);
        testUser.setPhone("0000000000");

        when(logic.getUserById(1)).thenReturn(Optional.of(testUser));
        assertNotNull(testUser);

        assertEquals(1, testUser.getUserId());
        assertEquals("chewed.mole@gmail.com", testUser.getEmail());
        assertEquals("Bond", testUser.getLastName());
        assertEquals("James", testUser.getFirstName());
        assertEquals("passportScan", new String(testUser.getPassportScan()));
        assertEquals("photo", new String(testUser.getPhoto()));
        assertEquals(10, testUser.getDiscount());
        assertEquals("0000000000", testUser.getPhone());
    }

    @Test
    public void getUserDetailsByIDRequestingWrongId() throws Exception {
        String passportScan = "passportScan";
        byte[] passportScanByte = passportScan.getBytes();

        String photo = "photo";
        byte[] photoByte = photo.getBytes();

        user.setUserId(1);
        user.setEmail("chewed.mole@gmail.com");
        user.setLastName("Bond");
        user.setFirstName("James");
        user.setPassportScan(passportScanByte);
        user.setPhoto(photoByte);
        user.setDiscount(10);
        user.setPhone("0000000000");

        when(logic.getUserById(-1)).thenReturn(Optional.empty());

        Optional<User> optional = logic.getUserById(-1);
        assertEquals(null, optional.orElse(null));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void passportSaved() throws IOException {
        User user = new User.Builder().setUserId(1).setRole(Role.ROLE_USER).setEmail("1@1.com")
                .setFirstName("F").setLastName("L").getInstance();
        byte[] bytes = {1, 2, 3, 4, 5};

        controllerWired.saveUserData(new MockMultipartFile("file", "file", "image/jpg", bytes),
                new MockMultipartFile("file", "file", "image/jpg", new byte[]{}), user, mock(BindingResult.class),
                new ModelAndView()).getViewName();

        User updatedUser = logicWired.getUserById(1).get();
        assertTrue(Arrays.equals(bytes, updatedUser.getPassportScan()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void photoSaved() throws IOException {
        User user = new User.Builder().setUserId(1).setRole(Role.ROLE_USER).setEmail("1@1.com")
                .setFirstName("F").setLastName("L").getInstance();
        byte[] bytes = {1, 2, 3, 4, 5};

        controllerWired.saveUserData(new MockMultipartFile("file", "file", "image/jpg", new byte[]{}),
                new MockMultipartFile("file", "file", "image/jpg", bytes), user, mock(BindingResult.class),
                new ModelAndView()).getViewName();

        User updatedUser = logicWired.getUserById(1).get();
        assertTrue(Arrays.equals(bytes, updatedUser.getPhoto()));
    }
}