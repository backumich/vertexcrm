package ua.com.vertex.beans;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Course {
    private final String COURSE_NAME_MSG = "Course's name must be longer than 5 and less than 256 characters";
    private final String COURSE_TEACHER_NAME_MSG = "Teacher's name must be longer than 2 and less than 256 characters";
    private final String SCHEDULE_MSG = "The schedule must be longer than 2 and less than 256 characters";
    private final String NOTES_MSG = "The schedule must be up to 256 characters long";

    private int id;

    @Size(min = 2, max = 256, message = COURSE_NAME_MSG)
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate start;

    private boolean finished;

    @DecimalMin("0.00")
    @DecimalMax("999999999.99")
    @NotNull
    private BigDecimal price;

    @Size(min = 2, max = 256, message = COURSE_TEACHER_NAME_MSG)
    private String teacherName;

    @Size(min = 2, max = 256, message = SCHEDULE_MSG)
    private String schedule;

    @Size(max = 256, message = NOTES_MSG)
    private String notes;

    public Course() {
    }

    public Course(int id, String name, LocalDate start, boolean finished, BigDecimal price, String teacherName, String schedule, String notes) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.finished = finished;
        this.price = price;
        this.teacherName = teacherName;
        this.schedule = schedule;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public boolean getFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id &&
                finished == course.finished &&
                Objects.equals(name, course.name) &&
                Objects.equals(start, course.start) &&
                Objects.equals(price, course.price) &&
                Objects.equals(teacherName, course.teacherName) &&
                Objects.equals(schedule, course.schedule) &&
                Objects.equals(notes, course.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, start, finished, price, teacherName, schedule, notes);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", start=" + start +
                ", finished=" + finished +
                ", price=" + price +
                ", teacherName='" + teacherName + '\'' +
                ", schedule='" + schedule + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }

    public static class Builder {
        private final Course instance;

        public Builder() {
            instance = new Course();
        }

        public Builder setId(int courseId) {
            instance.setId(courseId);
            return this;
        }

        public Builder setName(String courseName) {
            instance.setName(courseName);
            return this;
        }

        public Builder setStart(LocalDate startDate) {
            instance.setStart(startDate);
            return this;
        }

        public Builder setFinished(boolean isFinished) {
            instance.setFinished(isFinished);
            return this;
        }

        public Builder setPrice(BigDecimal price) {
            instance.setPrice(price);
            return this;
        }

        public Builder setTeacherName(String teacherName) {
            instance.setTeacherName(teacherName);
            return this;
        }

        public Builder setSchedule(String Schedule) {
            instance.setSchedule(Schedule);
            return this;
        }

        public Builder setNotes(String notes) {
            instance.setNotes(notes);
            return this;
        }

        public Course getInstance() {
            return instance;
        }
    }
}
