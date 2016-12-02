package ua.com.vertex.logic;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserRegistrationForm;
import ua.com.vertex.logic.interfaces.RegistrationUserLogic;

@Component
public class RegistrationUserLogicImpl implements RegistrationUserLogic, Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        UserRegistrationForm User = (UserRegistrationForm) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "label.validate.emailEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "label.validate.passwordEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "verifyPassword", "label.validate.repeatPasswordEmpty");

        if (!User.getPassword().equals(User.getVerifyPassword())) {
            errors.rejectValue("verifyPassword", "label.validate.repeatPasswordNotEquals");
        }

    }

    @Override
    public String registrationUser(User user) {
        return null;
    }

    @Override
    public void validationUserEmail() {

    }

    @Override
    public void validationUserPassword() {

    }

    @Override
    public void validationUserPasswordComplexity() {

    }


//    private int userID;
//    private String email;
//    private String password;
//    private String verifyPassword;
//    private String firstName;
//    private String lastName;
//    private BufferedImage passportScan;
//    private BufferedImage photo;
//    private int discount;
//    private String phone;


}
