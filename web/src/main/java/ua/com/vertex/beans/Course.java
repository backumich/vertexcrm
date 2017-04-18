package ua.com.vertex.beans;


import java.time.LocalDate;
import java.util.Objects;

public class Course {
    private int id;
    private String name;
    private LocalDate start;
    private boolean finished;
    private double price;
    private String teacherName;
    private String schedule;
    private String notes;

    public Course() {
    }

    public Course(int id, String name, LocalDate start, boolean finished, double price, String teacherName, String schedule, String notes) {
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

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
                Double.compare(course.price, price) == 0 &&
                Objects.equals(name, course.name) &&
                Objects.equals(start, course.start) &&
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
}
