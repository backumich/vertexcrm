package ua.com.vertex.beans;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;


public class Certificate {

    public static final Certificate EMPTY_CERTIFICATE = new Builder().setCertificationId(-1).getInstance();
    @Min(value = 1)
    @Max(value = Integer.MAX_VALUE)
    private int certificationId;
    @Min(value = 1, message = "Entered value must be a positive integer!")
    @Max(value = Integer.MAX_VALUE, message = "Invalid value!")
    private int userId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate certificationDate;
    @Size(min = 1, max = 256, message = "This field must be longer than 1 and less than  256 characters")
    private String courseName;
    @Size(min = 1, max = 256, message = "This field must be longer than 1 and less than  256 characters")
    private String language;

    public Certificate() {
    }

    @Override
    public String toString() {
        return String.format(
                "Certification ID: %05d%n" +
                        "User ID: %05d%n" +
                        "Certification Date: %s%n" +
                        "Course Name: %s%n" +
                        "Language: %s%n",
                certificationId, userId, certificationDate, courseName, language);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Certificate certificate2 = (Certificate) o;
        return certificationId == certificate2.certificationId &&
                userId == certificate2.userId &&
                Objects.equals(certificationDate, certificate2.certificationDate) &&
                Objects.equals(courseName, certificate2.courseName) &&
                Objects.equals(language, certificate2.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(certificationId, userId, certificationDate, courseName, language);
    }

    public int getCertificationId() {
        return certificationId;
    }

    public void setCertificationId(int certificationId) {
        this.certificationId = certificationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getCertificationDate() {
        return certificationDate;
    }

    public void setCertificationDate(LocalDate certificationDate) {
        this.certificationDate = certificationDate;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public static class Builder {
        private final Certificate instance;

        public Builder() {
            instance = new Certificate();
        }

        public Builder setCertificationId(int id) {
            instance.setCertificationId(id);
            return this;
        }

        public Builder setUserId(int id) {
            instance.setUserId(id);
            return this;
        }

        public Builder setCertificationDate(LocalDate date) {
            instance.setCertificationDate(date);
            return this;
        }

        public Builder setCourseName(String name) {
            instance.setCourseName(name);
            return this;
        }

        public Builder setLanguage(String language) {
            instance.setLanguage(language);
            return this;
        }

        public Certificate getInstance() {
            return instance;
        }
    }
}