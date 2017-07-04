package ua.com.vertex.beans;

import java.util.Objects;

public class CourseUserDto {
    private int courseId;
    private int userId;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String searchType;
    private String searchParam;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseUserDto that = (CourseUserDto) o;
        return courseId == that.courseId &&
                userId == that.userId &&
                Objects.equals(email, that.email) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(searchType, that.searchType) &&
                Objects.equals(searchParam, that.searchParam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, userId, email, firstName, lastName, phone, searchType, searchParam);
    }

    @Override
    public String toString() {
        return String.format("%d %d %s %s %s %s %s %s",
                courseId, userId, email, firstName, lastName, phone, searchType, searchParam);
    }
}
