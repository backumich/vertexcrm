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

    public User() {
    }

    public User(UserFormRegistration userFormRegistration) {
        email = userFormRegistration.getEmail();
        password = userFormRegistration.getPassword();
        firstName = userFormRegistration.getFirstName();
        lastName = userFormRegistration.getLastName();
        phone = userFormRegistration.getPhone();
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

    public Blob getPassportScan() {
        return passportScan;
    }

    public void setPassportScan(Blob passportScan) {
        this.passportScan = passportScan;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
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

        public User getInstance() {
            return instance;
        }
    }
}
