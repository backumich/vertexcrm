package ua.com.vertex.beans;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserFormRegistration {
    @Size(min = 5, max = 256, message = "E-mail must be longer than 5 and less than 256 characters")
    @Email
    private String email;

    @Size(min = 5, max = 30, message = "Password should not be longer than 30 characters")
    private String password;

    @Size(min = 5, max = 30, message = "Password should not be longer than 30 characters")
    private String verifyPassword;

    @Size(min = 1, max = 256, message = "This field should not be longer than 256 characters")
    private String firstName;

    @Size(min = 1, max = 256, message = "This field should not be longer than 256 characters")
    private String lastName;

    @Size(min = 1, max = 256, message = "This field should not be longer than 10 characters")
    @Pattern(regexp = "(^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{0,3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$)", message = "Invalid telephone number format")
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

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (verifyPassword != null ? !verifyPassword.equals(that.verifyPassword) : that.verifyPassword != null)
            return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        return phone != null ? phone.equals(that.phone) : that.phone == null;
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
}

//^\s*(?:\+?(\d{1,3}))?[-. (]*(\d{3})[-. )]*(\d{3})[-. ]*(\d{4})(?: *x(\d+))?\s*$
//
//        18005551234
//        1 800 555 1234
//        +1 800 555-1234
//        +86 800 555 1234
//        1-800-555-1234
//        1 (800) 555-1234
//        (800)555-1234
//        (800) 555-1234
//        (800)5551234
//        800-555-1234
//        800.555.1234
//        800 555 1234x5678
//        8005551234 x5678
//        1    800    555-1234
//        1----800----555-1234
