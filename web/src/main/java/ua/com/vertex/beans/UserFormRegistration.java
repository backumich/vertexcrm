package ua.com.vertex.beans;

import org.hibernate.validator.constraints.Email;
import ua.com.vertex.validators.interfaces.PasswordVerification;
import ua.com.vertex.validators.interfaces.PhoneVerification;

import javax.validation.constraints.Size;

@PasswordVerification(message = "Password and password confirmation fields don't match")
@PhoneVerification(message = "Wrong phone number format")
public class UserFormRegistration {
    @Size(min = 5, max = 256, message = "E-mail must be longer than 5 and less than 256 characters")
    @Email(message = "E-mail address format is incorrect")
    private String email;

    @Size(min = 5, max = 30, message = "Password must be longer than 5 and less than 30 characters")
    private String password;

    @Size(min = 5, max = 30, message = "Confirm password must be longer than 5 and less than 30 characters")
    private String verifyPassword;

    @Size(min = 1, max = 256, message = "This field must be longer than 1 and less than  256 characters")
    private String firstName;

    @Size(min = 1, max = 256, message = "This field must be longer than 1 and less than  256 characters")
    private String lastName;
    private String phone;

    public UserFormRegistration() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserFormRegistration that = (UserFormRegistration) o;

        return (email != null ? email.equals(that.email) : that.email == null)
                && (password != null ? password.equals(that.password) : that.password == null)
                && (verifyPassword != null ? verifyPassword.equals(that.verifyPassword) : that.verifyPassword == null)
                && (firstName != null ? firstName.equals(that.firstName) : that.firstName == null)
                && (lastName != null ? lastName.equals(that.lastName) : that.lastName == null)
                && (phone != null ? phone.equals(that.phone) : that.phone == null);
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (verifyPassword != null ? verifyPassword.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserFormRegistration{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", verifyPassword='" + verifyPassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public static class Builder {
        private final UserFormRegistration userFormRegistration;

        public Builder() {
            userFormRegistration = new UserFormRegistration();
        }


        public Builder setEmail(String email) {
            userFormRegistration.setEmail(email);
            return this;
        }

        public Builder setPassword(String password) {
            userFormRegistration.setPassword(password);
            return this;
        }

        public Builder setVerifyPassword(String verifyPassword) {
            userFormRegistration.setVerifyPassword(verifyPassword);
            return this;
        }

        public Builder setFirstName(String firstName) {
            userFormRegistration.setFirstName(firstName);
            return this;
        }

        public Builder setLastName(String lastName) {
            userFormRegistration.setLastName(lastName);
            return this;
        }

        public Builder setPhone(String phone) {
            userFormRegistration.setPhone(phone);
            return this;
        }

        public UserFormRegistration getInstance() {
            return userFormRegistration;
        }
    }
}