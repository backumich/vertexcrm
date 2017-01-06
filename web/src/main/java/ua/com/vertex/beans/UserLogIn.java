package ua.com.vertex.beans;

import ua.com.vertex.utils.UserRole;

import java.util.Objects;

public class UserLogIn {
    private String password;
    private UserRole userRole;

    public static final UserLogIn EMPTY_USER_LOG_IN = new Builder().getInstance();

    public UserLogIn() {
    }

    public static class Builder {
        private final UserLogIn userLogIn;

        public Builder() {
            userLogIn = new UserLogIn();
        }

        public Builder setPassword(String password) {
            userLogIn.setPassword(password);
            return this;
        }

        public Builder setUserRole(UserRole userRole) {
            userLogIn.setUserRole(userRole);
            return this;
        }

        public UserLogIn getInstance() {
            return userLogIn;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLogIn userLogIn = (UserLogIn) o;
        return Objects.equals(password, userLogIn.password) &&
                userRole == userLogIn.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, userRole);
    }
}
