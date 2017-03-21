package ua.com.vertex.beans;

import javax.validation.Valid;

public class CertificateWithUserForm {

    @Valid
    private Certificate certificate;

    @Valid
    private User user;

    public CertificateWithUserForm() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CertificateWithUserForm)) return false;
        CertificateWithUserForm that = (CertificateWithUserForm) o;
        return (getCertificate() != null ? getCertificate().equals(that.getCertificate()) : that.getCertificate() == null)
                && (getUser() != null ? getUser().equals(that.getUser()) : that.getUser() == null);
    }

    @Override
    public int hashCode() {
        int result = getCertificate() != null ? getCertificate().hashCode() : 0;
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        return result;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
