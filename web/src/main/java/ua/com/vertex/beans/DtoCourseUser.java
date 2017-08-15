package ua.com.vertex.beans;

import java.util.Objects;

public class DtoCourseUser {
    private int courseId;
    private int userId;
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
        DtoCourseUser that = (DtoCourseUser) o;
        return courseId == that.courseId &&
                userId == that.userId &&
                Objects.equals(searchType, that.searchType) &&
                Objects.equals(searchParam, that.searchParam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, userId, searchType, searchParam);
    }

    @Override
    public String toString() {
        return String.format("%d %d %s %s",
                courseId, userId, searchType, searchParam);
    }
}
