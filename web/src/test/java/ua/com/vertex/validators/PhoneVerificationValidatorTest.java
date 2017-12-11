package ua.com.vertex.validators;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class PhoneVerificationValidatorTest {

    private PhoneVerificationValidator validator = new PhoneVerificationValidator();

    @Mock
    private ConstraintValidatorContext context;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCountriesFormatValid1() {
        String number = "+380501234567";
        boolean result = validator.isValid(number, context);
        assertEquals(true, result);
    }

    @Test
    public void testCountriesFormatValid2() {
        String number = "+38050 123 4567";
        boolean result = validator.isValid(number, context);
        assertEquals(true, result);
    }

    @Test
    public void testCountriesFormatValid3() {
        String number = "+38 050 123 4567";
        boolean result = validator.isValid(number, context);
        assertEquals(true, result);
    }

    @Test
    public void testCountriesFormatValid4() {
        String number = "+(38050) 123 4567";
        boolean result = validator.isValid(number, context);
        assertEquals(true, result);
    }

    @Test
    public void testCountriesFormatValid5() {
        String number = "+38050-123-45-67";
        boolean result = validator.isValid(number, context);
        assertEquals(true, result);
    }

    @Test
    public void testCountriesFormatValid6() {
        String number = "050-123-45-67";
        boolean result = validator.isValid(number, context);
        assertEquals(true, result);
    }

    @Test
    public void testCountriesFormatValid7() {
        String number = "+48 123 456 789";
        boolean result = validator.isValid(number, context);
        assertEquals(true, result);
    }

    @Test
    public void testCountriesFormatValid8() {
        String number = "+370 528 594 06";
        boolean result = validator.isValid(number, context);
        assertEquals(true, result);
    }

    @Test
    public void testCountriesFormatInvalid1() {
        String number = "+38050-123-45";
        boolean result = validator.isValid(number, context);
        assertEquals(false, result);
    }

    @Test
    public void testCountriesFormatInvalid2() {
        String number = "+48 123 456 78";
        boolean result = validator.isValid(number, context);
        assertEquals(false, result);
    }

    @Test
    public void testCountriesFormatInvalid3() {
        String number = "123 456 789";
        boolean result = validator.isValid(number, context);
        assertEquals(false, result);
    }
}