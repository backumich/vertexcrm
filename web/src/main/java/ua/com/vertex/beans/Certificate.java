package ua.com.vertex.beans;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Objects;

public class Certificate {
    @Min(value = 1)
    private int certificationId;

    private int userId;
    private LocalDate certificationDate;
    private String courseName;
    private String language;

    public Certificate() {
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
}