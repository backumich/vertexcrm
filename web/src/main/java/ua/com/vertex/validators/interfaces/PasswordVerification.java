package ua.com.vertex.validators.interfaces;

import ua.com.vertex.validators.PasswordVerificationValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordVerificationValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordVerification {
    String message() default "{Passwords do not match!}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

