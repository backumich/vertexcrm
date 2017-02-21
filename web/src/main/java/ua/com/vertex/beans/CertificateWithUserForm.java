package ua.com.vertex.beans;


import javax.validation.Valid;

public class CertificateWithUserForm {

    @Valid
    private Certificate certificate;

    @Valid
    private User user;

    public CertificateWithUserForm() {
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
