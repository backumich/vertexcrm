package ua.com.vertex.beans;

import java.util.Objects;

public class UserMainData {

    private int userID;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public static class Builder {
        private final UserMainData userMainData;

        public Builder() {
            userMainData = new UserMainData();
        }

        public Builder setUserId(int userID) {
            userMainData.setUserID(userID);
            return this;
        }

        public Builder setEmail(String email) {
            userMainData.setEmail(email);
            return this;
        }

        public Builder setFirstName(String firstName) {
            userMainData.setFirstName(firstName);
            return this;
        }

        public Builder setLastName(String lastName) {
            userMainData.setLastName(lastName);
            return this;
        }

        public Builder setPhone(String phone) {
            userMainData.setPhone(phone);
            return this;
        }

        public UserMainData getInstance() {
            return userMainData;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMainData that = (UserMainData) o;
        return userID == that.userID &&
                Objects.equals(email, that.email) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, email, firstName, lastName, phone);
    }

    @Override
    public String toString() {
        return "UserMainData{" +
                "userID=" + userID +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}