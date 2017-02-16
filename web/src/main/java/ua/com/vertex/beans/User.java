package ua.com.vertex.beans;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Objects;

public class User {

    private final String NAME_MSG = "This field must be longer than 1 and less than  256 characters";
    private final String MAIL_MSG = "E-mail must be longer than 5 and less than 256 characters";
    private final String MAIL_FORMAT_MSG = "E-mail address format is incorrect";

    private int userId;
    @Size(min = 5, max = 256, message = MAIL_MSG)
    @Email(message = MAIL_FORMAT_MSG)
    private String email;
    private String password;
    @Size(min = 5, max = 30, message = NAME_MSG)
    private String firstName;
    @Size(min = 5, max = 30, message = NAME_MSG)
    private String lastName;
    private byte[] passportScan;
    private byte[] photo;
    private int discount;
    private String phone;

    public static final User EMPTY_USER = new Builder().setUserId(-1).getInstance();

    public User() {
    }

    public User(UserFormRegistration userFormRegistration) {
        email = userFormRegistration.getEmail();
        password = userFormRegistration.getPassword();
        firstName = userFormRegistration.getFirstName();
        lastName = userFormRegistration.getLastName();
        phone = userFormRegistration.getPhone();
    }

    public static class Builder {
        private final User user;

        public Builder() {
            user = new User();
        }

        public Builder setUserId(int userId) {
            user.setUserId(userId);
            return this;
        }

        public Builder setEmail(String email) {
            user.setEmail(email);
            return this;
        }

        public Builder setPassword(String password) {
            user.setPassword(password);
            return this;
        }

        public Builder setFirstName(String firstName) {
            user.setFirstName(firstName);
            return this;
        }

        public Builder setLastName(String lastName) {
            user.setLastName(lastName);
            return this;
        }

        public Builder setPassportScan(byte[] data) {
            user.setPassportScan(data);
            return this;
        }

        public Builder setPhoto(byte[] data) {
            user.setPhoto(data);
            return this;
        }

        public Builder setDiscount(int discount) {
            user.setDiscount(discount);
            return this;
        }

        public Builder setPhone(String phone) {
            user.setPhone(phone);
            return this;
        }

        public User getInstance() {
            return user;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", passportScan=" + passportScan +
                ", photo=" + photo +
                ", discount=" + discount +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                discount == user.discount &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Arrays.equals(passportScan, user.passportScan) &&
                Arrays.equals(photo, user.photo) &&
                Objects.equals(phone, user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, password, firstName, lastName, passportScan, photo, discount, phone);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public byte[] getPassportScan() {
        return passportScan;
    }

    public void setPassportScan(byte[] data) {
        this.passportScan = data;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] data) {
        this.photo = data;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}