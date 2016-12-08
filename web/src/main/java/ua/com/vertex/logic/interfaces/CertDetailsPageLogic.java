package ua.com.vertex.logic.interfaces;

import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;

public interface CertDetailsPageLogic {

    Certificate getCertificateDetails(int certificationId);

    User getUserDetails(int userId);
}
