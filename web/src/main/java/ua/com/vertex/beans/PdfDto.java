package ua.com.vertex.beans;

import java.util.Objects;

public class PdfDto {
    private String firstName;
    private String lastName;
    private String courseName;
    private String certificationDate;
    private String certificateUid;

    public PdfDto(String firstName, String lastName, String courseName, String certificationDate,
                  String certificateUid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.courseName = courseName;
        this.certificationDate = certificationDate;
        this.certificateUid = certificateUid;
    }

    public PdfDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCertificationDate() {
        return certificationDate;
    }

    public String getCertificateUid() {
        return certificateUid;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCertificationDate(String certificationDate) {
        this.certificationDate = certificationDate;
    }

    public void setCertificateUid(String certificateUid) {
        this.certificateUid = certificateUid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PdfDto that = (PdfDto) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(courseName, that.courseName) &&
                Objects.equals(certificationDate, that.certificationDate) &&
                Objects.equals(certificateUid, that.certificateUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, courseName, certificationDate, certificateUid);
    }
}
