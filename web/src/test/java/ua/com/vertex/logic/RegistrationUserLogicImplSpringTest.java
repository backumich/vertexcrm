package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.com.vertex.beans.UserFormRegistration;
import ua.com.vertex.context.TestConfigWithMockBeans;
import ua.com.vertex.logic.interfaces.EmailLogic;
import ua.com.vertex.logic.interfaces.RegistrationUserLogic;
import ua.com.vertex.utils.MailService;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;
import static ua.com.vertex.logic.RegistrationUserLogicImpl.OUR_EMAIL;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfigWithMockBeans.class)
@WebAppConfiguration
@ActiveProfiles("withMockBeans")
public class RegistrationUserLogicImplSpringTest {

    @Autowired
    private RegistrationUserLogic userLogic;

    @Autowired
    private JdbcTemplate template;

    @Autowired
    private MailService mailService;

    @Autowired
    private EmailLogic emailLogic;

    @Mock
    private BindingResult bindingResult;
    private UserFormRegistration form;

    @Before
    public void setUp() {
        form = new UserFormRegistration.Builder()
                .setEmail("testableUser@testingUser.com")
                .setFirstName("First")
                .setLastName("Last")
                .setPassword("unbreakable password")
                .setVerifyPassword("unbreakable password")
                .setPhone("+380501112233")
                .getInstance();
    }

    @Test
    @Transactional
    public void testSaveInactiveUserOk() {
        boolean result = userLogic.registerUser(form, bindingResult);

        assertTrue(result);
        verify(emailLogic, times(1)).createRegistrationMessage(form);
        verify(mailService, times(1)).sendMail(OUR_EMAIL, form.getEmail(), "Confirmation of registration",
                emailLogic.createRegistrationMessage(form));
    }

    @Test
    public void transactionRollsBackIfEmailingFails() {
        when(emailLogic.createRegistrationMessage(form)).thenThrow(new RuntimeException());
        try {
            userLogic.registerUser(form, bindingResult);
        } catch (RuntimeException e) {
            // do nothing
        }
        String query = "SELECT count(*) FROM Users u WHERE u.email='testableUser@testingUser.com'";
        assertEquals(0L, (long) template.queryForObject(query, long.class));
    }
}
