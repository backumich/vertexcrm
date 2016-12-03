package ua.com.vertex.logic.interfaces;

import javax.servlet.http.HttpSession;
import java.io.IOException;

public interface CertDetailsPageLogic {

    void getCertificateDetails(HttpSession session, String certificationId) throws IOException;

    byte[] getUserPhoto(String userId) throws IOException;
}
