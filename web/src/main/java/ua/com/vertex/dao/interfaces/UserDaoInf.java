package ua.com.vertex.dao.interfaces;


import org.springframework.dao.DataAccessException;
import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;

import java.sql.SQLException;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

public interface UserDaoInf {

    Optional<User> getUser(int id);

    Optional<User> getUserByEmail(String email);

    Optional<User> logIn(String username);

    @SuppressWarnings("unused")
    void deleteUser(int id);

    List<Integer> getAllUserIds();

    List<User> getAllUsers() throws SQLException;

    Optional<User> getUserDetailsByID(int userID) throws SQLException;

    Optional<User> userForRegistrationCheck(String userEmail) throws DataAccessException;

    EnumMap<Role, Role> getAllRoles();

    int saveUserData(User user);

    void saveImage(int userId, byte[] image, String imageType) throws Exception;

    Optional<byte[]> getImage(int userId, String imageType);

    int activateUser(String email);

    int addUserForCreateCertificate(User user);

    List<User> searchUser(String userData) throws Exception;

    void registrationUserInsert(User user) throws DataAccessException;

    void registrationUserUpdate(User user) throws DataAccessException;

    List<User> getCourseUsers(int courseId) throws DataAccessException;

}
