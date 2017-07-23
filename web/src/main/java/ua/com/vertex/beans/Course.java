package ua.com.vertex.beans;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Objects;

public class Course {
    private final String COURSE_NAME_MSG = "Course's name must be longer than 5 and less than 256 characters";
    private final String COURSE_TEACHER_NAME_MSG = "Teacher's name must be longer than 2 and less than 256 characters";
    private final String SCHEDULE_MSG = "The schedule must be longer than 2 and less than 256 characters";
    private final String NOTES_MSG = "The schedule must be up to 256 characters long";

    private int id;
    private BigDecimal price;

    @Size(min = 1, max = 256, message = COURSE_NAME_MSG)
    private String name;

    @Valid
    private User teacher;

    @Size(max = 256, message = SCHEDULE_MSG)
    private String schedule;

    @Size(max = 256, message = NOTES_MSG)
    private String notes;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull
    private LocalDateTime start;
    private boolean finished;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        return getId() == course.getId() && isFinished() == course.isFinished()
                && (getPrice() != null ? getPrice().equals(course.getPrice()) : course.getPrice() == null)
                && (getName() != null ? getName().equals(course.getName()) : course.getName() == null)
                && (getTeacher() != null ? getTeacher().equals(course.getTeacher()) : course.getTeacher() == null)
                && (getSchedule() != null ? getSchedule().equals(course.getSchedule()) : course.getSchedule() == null)
                && (getNotes() != null ? getNotes().equals(course.getNotes()) : course.getNotes() == null)
                && (getStart() != null ? getStart().equals(course.getStart()) : course.getStart() == null);
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getTeacher() != null ? getTeacher().hashCode() : 0);
        result = 31 * result + (getSchedule() != null ? getSchedule().hashCode() : 0);
        result = 31 * result + (getNotes() != null ? getNotes().hashCode() : 0);
        result = 31 * result + (getStart() != null ? getStart().hashCode() : 0);
        result = 31 * result + (isFinished() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", teacher=" + teacher +
                ", schedule='" + schedule + '\'' +
                ", notes='" + notes + '\'' +
                ", start=" + start +
                ", finished=" + finished +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int courseId) {
        this.id = courseId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
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

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
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

        public Builder setTeacher(User teacher) {
            instance.setTeacher(teacher);
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