package ua.com.vertex.beans;

import java.util.Arrays;
import java.util.Objects;

public class User {

    private long userId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private byte[] passportScan;
    private byte[] photo;
    private int discount;
    private String phone;

    public User() {
    }

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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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

    public void setPassportScan(byte[] passportScan) {
        this.passportScan = passportScan;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
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

    public static class Builder {

        private final User instance;

        public Builder() {
            instance = new User();
        }

        public Builder setUserId(long userId) {
            instance.setUserId(userId);
            return this;
        }

        public Builder setEmail(String email) {
            instance.setEmail(email);
            return this;
        }

        public Builder setPassword(String password) {
            instance.setPassword(password);
            return this;
        }

        public Builder setFirstName(String firstName) {
            instance.setFirstName(firstName);
            return this;
        }

        public Builder setLastName(String lastName) {
            instance.setLastName(lastName);
            return this;
        }

        public Builder setPassportScan(byte[] passportScan) {
            instance.setPassportScan(passportScan);
            return this;
        }

        public Builder setPhoto(byte[] photo) {
            instance.setPhoto(photo);
            return this;
        }

        public Builder setDiscount(int discount) {
            instance.setDiscount(discount);
            return this;
        }

        public Builder setPhone(String phone) {
            instance.setPhone(phone);
            return this;
        }

        public User getInstance() {
            return instance;
        }
    }
}
