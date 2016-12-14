package ua.com.vertex.beans;


import java.time.LocalDate;


public class Certificate {

    private int certificationId;
    private int userId;
    private LocalDate certificationDate;
    private String courseName;
    private String language;

    @SuppressWarnings("WeakerAccess")
    public Certificate() {
    }

    public String toString() {
        return "Certificate{" +
                "certificationId=" + certificationId +
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
        if (getCertificationDate().equals(that.getCertificationDate()))
            if (getCourseName().equals(that.getCourseName())) return true;
        return false;

    }

    public int hashCode() {
        int result = getCertificationId();
        result = 31 * result + getCertificationDate().hashCode();
        result = 31 * result + getCourseName().hashCode();
        result = 31 * result + getLanguage().hashCode();
        return result;
    }

    @SuppressWarnings("WeakerAccess")
    public int getCertificationId() {
        return certificationId;
    }

    @SuppressWarnings("WeakerAccess")
    public void setCertificationId(int certificationId) {
        this.certificationId = certificationId;
    }

    @SuppressWarnings("WeakerAccess")
    public int getUserId() {
        return userId;
    }

    @SuppressWarnings("WeakerAccess")
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @SuppressWarnings("WeakerAccess")
    public LocalDate getCertificationDate() {
        return certificationDate;
    }

    @SuppressWarnings("WeakerAccess")
    public void setCertificationDate(LocalDate certificationDate) {
        this.certificationDate = certificationDate;
    }

    @SuppressWarnings("WeakerAccess")
    public String getCourseName() {
        return courseName;
    }

    @SuppressWarnings("WeakerAccess")
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @SuppressWarnings("WeakerAccess")
    public String getLanguage() {
        return language;
    }

    @SuppressWarnings("WeakerAccess")
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

        public Builder setUserId(int userId) {
            instance.setUserId(userId);
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
