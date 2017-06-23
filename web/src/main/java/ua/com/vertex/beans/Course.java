package ua.com.vertex.beans;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Course {

    private int id;
    private BigDecimal price;
    private String name, teacherName, schedule, notes;
    private LocalDateTime start;
    private boolean finished;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return getId() == course.getId() &&
                isFinished() == course.isFinished() &&
                Objects.equals(getPrice(), course.getPrice()) &&
                Objects.equals(getName(), course.getName()) &&
                Objects.equals(getTeacherName(), course.getTeacherName()) &&
                Objects.equals(getSchedule(), course.getSchedule()) &&
                Objects.equals(getNotes(), course.getNotes()) &&
                Objects.equals(getStart(), course.getStart());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPrice(), getName(), getTeacherName(), getSchedule(), getNotes(), getStart(),
                isFinished());
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", teacherName='" + teacherName + '\'' +
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

        public Builder setStart(LocalDateTime startDate) {
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

        public Builder setShedule(String shedule) {
            instance.setSchedule(shedule);
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
