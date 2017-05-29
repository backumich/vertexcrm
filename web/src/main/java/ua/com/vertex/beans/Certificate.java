package ua.com.vertex.beans;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

public class Certificate {

    private final String NAME_MSG = "This field must be longer than 1 and less than  256 characters";

    public static final Certificate EMPTY_CERTIFICATE = new Builder().setCertificationId(-1).getInstance();

    @Min(value = 1)
    @Max(value = Integer.MAX_VALUE)
    private int certificationId;

    private String certificateUid;

    @Min(value = 1, message = "Entered value must be a positive integer!")
    @Max(value = Integer.MAX_VALUE, message = "Invalid value!")
    private int userId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate certificationDate;

    @Size(min = 1, max = 256, message = NAME_MSG)
    private String courseName;

    @Size(min = 1, max = 256, message = NAME_MSG)
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

        public Builder setCertificateUid(String uid) {
            instance.setCertificateUid(uid);
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
                        "UID: %s%n" +
                        "User ID: %05d%n" +
                        "Certification Date: %s%n" +
                        "Course Name: %s%n" +
                        "Language: %s%n",
                certificationId, certificateUid, userId, certificationDate, courseName, language);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Certificate that = (Certificate) o;
        return certificationId == that.certificationId &&
                userId == that.userId &&
                Objects.equals(certificateUid, that.certificateUid) &&
                Objects.equals(certificationDate, that.certificationDate) &&
                Objects.equals(courseName, that.courseName) &&
                Objects.equals(language, that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(certificationId, certificateUid, userId, certificationDate, courseName, language);
    }

    public int getCertificationId() {
        return certificationId;
    }

    public void setCertificationId(int certificationId) {
        this.certificationId = certificationId;
    }

    public String getCertificateUid() {
        char[] symbols = certificateUid.toCharArray();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < symbols.length; i++) {
            sb.append(symbols[i]);
            if ((i + 1) % 4 == 0 && (i + 1) < symbols.length) {
                sb.append("-");
            }
        }

        return sb.toString();
    }

    public String getCertificateUidWithoutDashes() {
        return certificateUid;
    }

    public void setCertificateUid(String certificateUid) {
        this.certificateUid = certificateUid;
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