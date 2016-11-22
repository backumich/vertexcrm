package ua.com.vertex.dao.impl;


import java.time.LocalDate;

public class Certificate {
    private int certificationId;
    private int userId;
    private LocalDate certificationDate;
    private String courseName;
    private String language;

    private Certificate() {
    }

    @SuppressWarnings("WeakerAccess")
    public synchronized int getCertificationId() {
        return certificationId;
    }

    private synchronized void setCertificationId(int certificationId) {
        this.certificationId = certificationId;
    }

    @SuppressWarnings("WeakerAccess")
    public synchronized int getUserId() {
        return userId;
    }

    private synchronized void setUserId(int userId) {
        this.userId = userId;
    }

    @SuppressWarnings("WeakerAccess")
    public synchronized LocalDate getCertificationDate() {
        return certificationDate;
    }

    private synchronized void setCertificationDate(LocalDate certificationDate) {
        this.certificationDate = certificationDate;
    }

    @SuppressWarnings("WeakerAccess")
    public synchronized String getCourseName() {
        return courseName;
    }

    private synchronized void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @SuppressWarnings("WeakerAccess")
    public synchronized String getLanguage() {
        return language;
    }

    @SuppressWarnings("WeakerAccess")
    private synchronized void setLanguage(String language) {
        this.language = language;
    }

    public String toString() {
        return "Certificate{" +
                "certificationId=" + certificationId +
                ", userId=" + userId +
                ", certificationDate=" + certificationDate +
                ", courseName='" + courseName + '\'' +
                ", language='" + language + '\'' +
                '}';
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Certificate)) return false;

        Certificate that = (Certificate) o;

        if (getCertificationId() != that.getCertificationId()) return false;
        if (getUserId() != that.getUserId()) return false;
        if (getCertificationDate().equals(that.getCertificationDate()))
            if (getCourseName().equals(that.getCourseName())) if (getLanguage().equals(that.getLanguage())) return true;
        return false;

    }

    public int hashCode() {
        int result = getCertificationId();
        result = 31 * result + getUserId();
        result = 31 * result + getCertificationDate().hashCode();
        result = 31 * result + getCourseName().hashCode();
        result = 31 * result + getLanguage().hashCode();
        return result;
    }

    static class Builder {
        private final Certificate instance;

        Builder() {
            instance = new Certificate();
        }

        Builder setCertificationId(int id) {
            instance.setCertificationId(id);
            return this;
        }

        Builder setUserId(int id) {
            instance.setUserId(id);
            return this;
        }

        Builder setCertificationDate(LocalDate date) {
            instance.setCertificationDate(date);
            return this;
        }

        Builder setCourseName(String name) {
            instance.setCourseName(name);
            return this;
        }

        Builder setLanguage(String language) {
            instance.setLanguage(language);
            return this;
        }

        synchronized Certificate getInstance() {
            return instance;
        }
    }
}
