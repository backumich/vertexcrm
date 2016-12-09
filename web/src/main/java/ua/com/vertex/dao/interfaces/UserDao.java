package ua.com.vertex.dao.interfaces;


import java.util.List;

public interface UserDao {


    @SuppressWarnings("unused")
    void deleteUser(int id);

    List<Integer> getAllUserIds();

}
