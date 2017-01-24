package ua.com.vertex.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.sql.SQLException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@WebAppConfiguration
public class UserDetailsControllerTest {


    @Mock
    private UserLogic logic;

    @Mock
    private UserDetailsController userDetailsController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userDetailsController = new UserDetailsController(logic);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userDetailsController).build();
    }

    @Test
    public void userDetailsControllerReturnedPassViewTest() throws Exception {
        when(logic.getUserDetailsByID(1)).thenReturn(new User());
        MockMvc mockMvc = standaloneSetup(userDetailsController)
                .setSingleView(new InternalResourceView("userDetails"))
                .build();
        mockMvc.perform(get("/userDetails")
                .param("userId", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(view().name("userDetails"));
    }

    @Test
    public void userDetailsControllerReturnedFailViewTest() throws Exception {
        when(logic.getUserDetailsByID(-1)).thenThrow(new SQLException());
        MockMvc mockMvc = standaloneSetup(userDetailsController)
                .setSingleView(new InternalResourceView("error"))
                .build();
        mockMvc.perform(get("/userDetails")
                .param("userId", String.valueOf(-1)))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
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

        when(logic.getUserDetailsByID(1)).thenReturn(testUser);
        Assert.assertNotNull(testUser);

        Assert.assertEquals(1, testUser.getUserId());
        Assert.assertEquals("chewed.mole@gmail.com", testUser.getEmail());
        Assert.assertEquals("Bond", testUser.getLastName());
        Assert.assertEquals("James", testUser.getFirstName());
        Assert.assertEquals("passportScan", new String(testUser.getPassportScan()));
        Assert.assertEquals("photo", new String(testUser.getPhoto()));
        Assert.assertEquals(10, testUser.getDiscount());
        Assert.assertEquals("0000000000", testUser.getPhone());
    }
}
