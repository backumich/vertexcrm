package ua.com.vertex.beans;


import java.util.Objects;

public class CourseForOutput {

    private String teacherFirstName, teacherLastName;

    private Course course;

    public CourseForOutput() {
    }

    public CourseForOutput(String teacherFirstName, String teacherLastName, Course course) {
        this.teacherFirstName = teacherFirstName;
        this.teacherLastName = teacherLastName;
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseForOutput that = (CourseForOutput) o;
        return Objects.equals(teacherFirstName, that.teacherFirstName) &&
                Objects.equals(teacherLastName, that.teacherLastName) &&
                Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherFirstName, teacherLastName, course);
    }

    @Override
    public String toString() {
        return "CourseForOutput{" +
                "teacherFirstName='" + teacherFirstName + '\'' +
                ", teacherLastName='" + teacherLastName + '\'' +
                ", course=" + course +
                '}';
    }

    public String getTeacherFirstName() {
        return teacherFirstName;
    }

    public void setTeacherFirstName(String teacherFirstName) {
        this.teacherFirstName = teacherFirstName;
    }

    public String getTeacherLastName() {
        return teacherLastName;
    }

    public void setTeacherLastName(String teacherLastName) {
        this.teacherLastName = teacherLastName;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
