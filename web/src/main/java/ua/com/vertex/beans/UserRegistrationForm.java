package ua.com.vertex.beans;

import com.sun.istack.internal.NotNull;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Size;
import java.awt.image.BufferedImage;

@Component
public class UserRegistrationForm {
    private int userID;

    @NotNull
    @Email
    @Size(min = 6, max = 255)
    @NotEmpty
    private String email;


    private String password;
    private String verifyPassword;
    private String firstName;
    private String lastName;
    private BufferedImage passportScan;
    private BufferedImage photo;
    private int discount;
    private String phone;


    public UserRegistrationForm() {
    }

    public int getUserID() {
        return userID;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BufferedImage getPassportScan() {
        return passportScan;
    }

    public BufferedImage getPhoto() {
        return photo;
    }

    public int getDiscount() {
        return discount;
    }

    public String getPhone() {
        return phone;
    }

    public static Builder newBuilder() {
        return new UserRegistrationForm().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder setUserID(int userID) {
            UserRegistrationForm.this.userID = userID;
            return this;
        }

        public Builder setEmail(String email) {
            UserRegistrationForm.this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            UserRegistrationForm.this.password = password;
            return this;
        }

        public Builder setVerifyPassword(String verifyPassword) {
            UserRegistrationForm.this.verifyPassword = verifyPassword;
            return this;
        }

        public Builder setFirstName(String firstName) {
            UserRegistrationForm.this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            UserRegistrationForm.this.lastName = lastName;
            return this;
        }

        public Builder setPassportScan(BufferedImage passportScan) {
            UserRegistrationForm.this.passportScan = passportScan;
            return this;
        }

        public Builder setPhoto(BufferedImage photo) {
            UserRegistrationForm.this.photo = photo;
            return this;
        }

        public Builder setDiscount(int discount) {
            UserRegistrationForm.this.discount = discount;
            return this;
        }

        public Builder setPhone(String phone) {
            UserRegistrationForm.this.phone = phone;
            return this;
        }

        public UserRegistrationForm build() {
            return UserRegistrationForm.this;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRegistrationForm that = (UserRegistrationForm) o;

        if (userID != that.userID) return false;
        if (discount != that.discount) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (verifyPassword != null ? !verifyPassword.equals(that.verifyPassword) : that.verifyPassword != null)
            return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (passportScan != null ? !passportScan.equals(that.passportScan) : that.passportScan != null) return false;
        if (photo != null ? !photo.equals(that.photo) : that.photo != null) return false;
        return phone != null ? phone.equals(that.phone) : that.phone == null;
    }

    @Override
    public int hashCode() {
        int result = userID;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (verifyPassword != null ? verifyPassword.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (passportScan != null ? passportScan.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + discount;
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserRegistrationForm{" +
                "userID=" + userID +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", verifyPassword='" + verifyPassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", passportScan=" + passportScan +
                ", photo=" + photo +
                ", discount=" + discount +
                ", phone='" + phone + '\'' +
                '}';
    }
}
