package ua.com.vertex.models;


import java.awt.image.BufferedImage;

public class User {
    private int userID;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private BufferedImage passportScan;
    private BufferedImage photo;
    private int discount;
    private String phone;


    public User() {
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
        return new User().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder setUserID(int userID) {
            User.this.userID = userID;
            return this;
        }

        public Builder setEmail(String email) {
            User.this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            User.this.password = password;
            return this;
        }

        public Builder setFirstName(String firstName) {
            User.this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            User.this.lastName = lastName;
            return this;
        }

        public Builder setPassportScan(BufferedImage passportScan) {
            User.this.passportScan = passportScan;
            return this;
        }

        public Builder setPhoto(BufferedImage photo) {
            User.this.photo = photo;
            return this;
        }

        public Builder setDiscount(int discount) {
            User.this.discount = discount;
            return this;
        }

        public Builder setPhone(String phone) {
            User.this.phone = phone;
            return this;
        }

        public User build() {
            return User.this;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userID != user.userID) return false;
        if (discount != user.discount) return false;
        if (!email.equals(user.email)) return false;
        if (!password.equals(user.password)) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (passportScan != null ? !passportScan.equals(user.passportScan) : user.passportScan != null) return false;
        if (photo != null ? !photo.equals(user.photo) : user.photo != null) return false;
        return phone != null ? phone.equals(user.phone) : user.phone == null;

    }

    @Override
    public int hashCode() {
        int result = userID;
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
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
        return "User{" +
                "userID=" + userID +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", passportScan='" + passportScan + '\'' +
                ", photo='" + photo + '\'' +
                ", discount=" + discount +
                ", phone='" + phone + '\'' +
                '}';
    }
}
