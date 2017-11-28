package ua.com.vertex.validators;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import ua.com.vertex.beans.UserFormRegistration;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class PhoneVerificationValidatorTest {

    private PhoneVerificationValidator validator = new PhoneVerificationValidator();

    @Mock
    private ConstraintValidatorContext context;
    private UserFormRegistration form;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        form = new UserFormRegistration();
    }

    @Test
    public void testCountriesFormatValid1() {
        form.setPhone("+380501234567");
        boolean result = validator.isValid(form, context);
        assertEquals(true, result);
    }

    @Test
    public void testCountriesFormatValid2() {
        form.setPhone("+38050 123 4567");
        boolean result = validator.isValid(form, context);
        assertEquals(true, result);
    }

    @Test
    public void testCountriesFormatValid3() {
        form.setPhone("+38050-123-45-67");
        boolean result = validator.isValid(form, context);
        assertEquals(true, result);
    }

    @Test
    public void testCountriesFormatValid4() {
        form.setPhone("050-123-45-67");
        boolean result = validator.isValid(form, context);
        assertEquals(true, result);
    }

    @Test
    public void testCountriesFormatValid5() {
        form.setPhone("+48 123 456 789");
        boolean result = validator.isValid(form, context);
        assertEquals(true, result);
    }

    @Test
    public void testCountriesFormatValid6() {
        form.setPhone("+370 528 594 06");
        boolean result = validator.isValid(form, context);
        assertEquals(true, result);
    }

    @Test
    public void testCountriesFormatInvalid1() {
        form.setPhone("+38050-123-45");
        boolean result = validator.isValid(form, context);
        assertEquals(false, result);
    }

    @Test
    public void testCountriesFormatInvalid2() {
        form.setPhone("+48 123 456 78");
        boolean result = validator.isValid(form, context);
        assertEquals(false, result);
    }
}