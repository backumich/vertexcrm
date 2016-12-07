package ua.com.vertex.beans;

import java.sql.Blob;

public class User {

    private long userId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Blob passportScan;
    private Blob photo;
    private int discount;
    private String phone;

    @SuppressWarnings("WeakerAccess")
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return getUserId() == user.getUserId() &&
                getDiscount() == user.getDiscount() && getEmail().equals(user.getEmail())
                && getPassword().equals(user.getPassword()) && getFirstName().equals(user.getFirstName())
                && getLastName().equals(user.getLastName()) && getPassportScan().equals(user.getPassportScan())
                && getPhoto().equals(user.getPhoto()) && getPhone().equals(user.getPhone());

    }

    public int hashCode() {
        int result = (int) (getUserId() ^ (getUserId() >>> 32));
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + getPassportScan().hashCode();
        result = 31 * result + getPhoto().hashCode();
        result = 31 * result + getDiscount();
        result = 31 * result + getPhone().hashCode();
        return result;
    }

    @SuppressWarnings("WeakerAccess")
    public long getUserId() {
        return userId;
    }

    @SuppressWarnings("WeakerAccess")
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @SuppressWarnings("WeakerAccess")
    public String getEmail() {
        return email;
    }

    @SuppressWarnings("WeakerAccess")
    public void setEmail(String email) {
        this.email = email;
    }

    @SuppressWarnings("WeakerAccess")
    public String getPassword() {
        return password;
    }

    @SuppressWarnings("WeakerAccess")
    public void setPassword(String password) {
        this.password = password;
    }

    @SuppressWarnings("WeakerAccess")
    public String getFirstName() {
        return firstName;
    }

    @SuppressWarnings("WeakerAccess")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @SuppressWarnings("WeakerAccess")
    public String getLastName() {
        return lastName;
    }

    @SuppressWarnings("WeakerAccess")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @SuppressWarnings("WeakerAccess")
    public Blob getPassportScan() {
        return passportScan;
    }

    @SuppressWarnings("WeakerAccess")
    public void setPassportScan(Blob passportScan) {
        this.passportScan = passportScan;
    }

    @SuppressWarnings("WeakerAccess")
    public Blob getPhoto() {
        return photo;
    }

    @SuppressWarnings("WeakerAccess")
    public void setPhoto(Blob photo) {
        this.photo = photo;
    }

    @SuppressWarnings("WeakerAccess")
    public int getDiscount() {
        return discount;
    }

    @SuppressWarnings("WeakerAccess")
    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @SuppressWarnings("WeakerAccess")
    public String getPhone() {
        return phone;
    }

    @SuppressWarnings("WeakerAccess")
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

        public Builder setPassportScan(Blob passportScan) {
            instance.setPassportScan(passportScan);
            return this;
        }

        public Builder setPhoto(Blob photo) {
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

        public synchronized User getInstance() {
            return instance;
        }
    }
}
