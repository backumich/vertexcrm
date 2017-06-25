package ua.com.vertex.beans;

import java.util.Objects;

public class CourseUserDTO {
    private int courseId;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String typeOfSearch;
    private String searchParam;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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

    public String getTypeOfSearch() {
        return typeOfSearch;
    }

    public void setTypeOfSearch(String typeOfSearch) {
        this.typeOfSearch = typeOfSearch;
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
        CourseUserDTO that = (CourseUserDTO) o;
        return courseId == that.courseId &&
                Objects.equals(email, that.email) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(typeOfSearch, that.typeOfSearch) &&
                Objects.equals(searchParam, that.searchParam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, email, firstName, lastName, phone, typeOfSearch, searchParam);
    }

    @Override
    public String toString() {
        return String.format("%d %s %s %s %s %s %s",
                courseId, email, firstName, lastName, phone, typeOfSearch, searchParam);
    }
}
