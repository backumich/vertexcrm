package ua.com.vertex.logic.interfaces;

import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;

import java.util.Optional;

public interface CertDetailsPageLogic {

    Optional<Certificate> getCertificateDetails(int certificationId);

    Optional<User> getUserDetails(int userId);
}
