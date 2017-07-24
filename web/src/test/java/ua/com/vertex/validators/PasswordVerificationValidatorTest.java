package ua.com.vertex.validators;

import org.junit.Ignore;
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

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
@Ignore
public class PasswordVerificationValidatorTest {

    @Autowired
    private Validator validator;

    @Test
    public void isValidWhenPasswordEqualsVerifyPassword() throws Exception {

        Set<ConstraintViolation<UserFormRegistration>> violations = validator.validate(new UserFormRegistration.Builder().setEmail("Test@test.com").
                setPassword("1111111").setVerifyPassword("1111111").setFirstName("test").setLastName("test").
                setPhone("0999999999").getInstance());
        assertTrue(violations.isEmpty());
    }

    @Test
    public void isValidWhenPasswordVerificationError() throws Exception {

        Set<ConstraintViolation<UserFormRegistration>> violations = validator.validate(new UserFormRegistration.Builder().setEmail("Test@test.com").
                setPassword("1111111").setVerifyPassword("2222222").setFirstName("test").setLastName("test").
                setPhone("0999999999").getInstance());
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }
}