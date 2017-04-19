package ua.com.vertex.beans;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Course {

    private int id;
    private BigDecimal price;
    private String name, teacherName, schedule, notes;
    private LocalDateTime start;
    private boolean finished;

    public Course() {
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

}
