package ua.com.vertex.logic;


import ua.com.vertex.beans.Certificate;

import java.util.List;

public interface UserLogic {

    List<Certificate> getAllCertificateByUserId(int userId);

    List<String> getAllUserIds();

}
