package ua.com.vertex.logic;


import ua.com.vertex.beans.Certificate;

import java.util.List;

public interface UserLogic {

    List<Certificate> getAllCertificatesByUserId(int userId);

    List<String> getAllUserIds();

}
