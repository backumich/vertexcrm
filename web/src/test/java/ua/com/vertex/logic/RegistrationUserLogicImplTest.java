//package ua.com.vertex.logic;
//
//
//import org.junit.Before;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.validation.BindingResult;
//import ua.com.vertex.beans.UserFormRegistration;
//import ua.com.vertex.context.TestConfig;
//
//@SuppressWarnings("Duplicates")
//@ContextConfiguration(classes = TestConfig.class)
//@RunWith(MockitoJUnitRunner.class)
//public class RegistrationUserLogicImplTest {
//
//    UserFormRegistration userFormRegistrationCorrect, userFormRegistrationInCorrect;
//
//    @Mock
//    private BindingResult bindingResult;
//
//    @Before
//    public void setUp() throws Exception{
//        userFormRegistrationCorrect = new UserFormRegistration();
//        userFormRegistrationCorrect.setEmail("test@test.com");
//        userFormRegistrationCorrect.setPassword("111111");
//        userFormRegistrationCorrect.setVerifyPassword("111111");
//        userFormRegistrationInCorrect = new UserFormRegistration();
//        userFormRegistrationInCorrect.setEmail("test@test.com");
//        userFormRegistrationInCorrect.setPassword("111111");
//        userFormRegistrationInCorrect.setVerifyPassword("222222");
//    }
//
//
//}
