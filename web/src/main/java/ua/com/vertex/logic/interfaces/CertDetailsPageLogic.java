package ua.com.vertex.logic.interfaces;

import javax.servlet.http.HttpSession;
import java.io.IOException;

public interface CertDetailsPageLogic {

    HttpSession getCertificateDetails(HttpSession session, String certificationId) throws IOException;
}
