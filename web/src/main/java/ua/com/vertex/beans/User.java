package ua.com.vertex.beans;

import java.sql.Blob;
import java.util.Objects;

public class User {
    private int userId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Blob passportScan;
    private Blob photo;
    private int discount;
    private String phone;

    private User() {
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

        public Builder setPassportScan(Blob blob) {
            user.setPassportScan(blob);
            return this;
        }

        public Builder setPhoto(Blob blob) {
            user.setPhoto(blob);
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

        public synchronized User getInstance() {
            return user;
        }
    }

    @Override
    public String toString() {
        return String.format(
                "User ID: %s%n" +
                        "E-mail: %s%n" +
                        "First Name: %s%n" +
                        "Last Name: %s%n" +
                        "Discount: %d%n" +
                        "Phone: %s%n",
                userId, email, firstName, lastName, discount, phone == null ? "-" : phone);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user2 = (User) o;
        return userId == user2.userId &&
                Objects.equals(email, user2.email) &&
                Objects.equals(firstName, user2.firstName) &&
                Objects.equals(lastName, user2.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, firstName, lastName);
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

    public Blob getPassportScan() {
        return passportScan;
    }

    public void setPassportScan(Blob blob) {
        this.passportScan = blob;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob blob) {
        this.photo = blob;
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