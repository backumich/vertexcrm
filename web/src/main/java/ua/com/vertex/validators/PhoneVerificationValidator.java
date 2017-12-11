package ua.com.vertex.validators;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import ua.com.vertex.validators.interfaces.PhoneVerification;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneVerificationValidator implements ConstraintValidator<PhoneVerification, String> {
    @Override
    public void initialize(PhoneVerification phoneVerification) {
    }

    @Override
    public boolean isValid(String phoneToParse, ConstraintValidatorContext context) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        boolean result;
        Phonenumber.PhoneNumber number;
        try {
            number = phoneUtil.parse(phoneToParse, "UA");
            result = phoneUtil.isValidNumber(number);
        } catch (NumberParseException e) {
            result = false;
        }
        return result;
    }
}
