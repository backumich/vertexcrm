package ua.com.vertex.dao.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.vertex.context.MainTestContext;
import ua.com.vertex.dao.UserDaoRealizationInf;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainTestContext.class)
@ActiveProfiles("test")
public class UserDaoRealizationTest {

    @Autowired
    UserDaoRealization userDaoRealization;

    @Mock
    private UserDaoRealizationInf mockUserDaoRealizationInf;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockUserDaoRealizationInf = new UserDaoRealization();
    }

    @Test
    public void isRegisteredEmailReturnZero() throws Exception {

        when(mockUserDaoRealizationInf.isRegisteredEmail("1")).thenReturn(0);
        int result = userDaoRealization.isRegisteredEmail("1");
        //verify(userDaoRealization).isRegisteredEmail("chewed.mole@gmail.com");
        //assertEquals(userDaoRealization.isRegisteredEmail("chewed.mole@gmail.com"), 0);


        //int result = userDaoRealizationInf.isRegisteredEmail("");
        //assertNotNull("This email is not yet registered in the system", result);
    }

    @Test
    public void isRegisteredEmailReturnNotEmpty() throws Exception {

    }


}