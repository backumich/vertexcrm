package ua.com.vertex.validators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.vertex.beans.UserFormRegistration;
import ua.com.vertex.context.TestConfig;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class PasswordVerificationValidatorTest {

    @Autowired
    private Validator validator;

    @Test
    public void isValid() throws Exception {
        UserFormRegistration userFormRegistration = new UserFormRegistration.Builder().setEmail("Test@test.com").
                setPassword("1111111").setVerifyPassword("2222222").setFirstName("test").setLastName("test").
                setPhone("0999999999").getInstance();

        Set<ConstraintViolation<UserFormRegistration>> violations = validator.validate(userFormRegistration);
        assertTrue(violations.isEmpty());
    }

}