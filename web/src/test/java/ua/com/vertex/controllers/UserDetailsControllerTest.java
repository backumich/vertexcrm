package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.logic.interfaces.CertificateLogic;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")

public class UserDetailsControllerTest {

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
    @WithMockUser
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
}