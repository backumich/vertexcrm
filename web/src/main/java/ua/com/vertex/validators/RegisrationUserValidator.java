package ua.com.vertex.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserRegistrationForm;

public class RegisrationUserValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        UserRegistrationForm User = (UserRegistrationForm) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "label.validate.emailEmpty", "Email is the mandatory field!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "label.validate.passwordEmpty", "Password is the mandatory field!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "verifyPassword", "registration.repeatPassword.empty", "Repeat the password");

        if (!User.getPassword().equals(User.getVerifyPassword())) {
            errors.rejectValue("verifyPassword", "registration.repeatPassword.notEquals", "Passwords are not equals");
        }

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
