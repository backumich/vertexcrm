package ua.com.vertex.logic.interfaces;

import org.springframework.dao.DataAccessException;
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;
import ua.com.vertex.utils.DataNavigator;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserLogic {

    List<String> getAllUserIds() throws DataAccessException;

    Optional<User> getUserById(int id) throws DataAccessException;

    Optional<User> getUserByEmail(String email) throws DataAccessException;

    void saveImage(int userId, byte[] image, String imageType) throws DataAccessException;

    Optional<byte[]> getImage(int userId, String imageType) throws DataAccessException;

    List<User> getUsersPerPages(DataNavigator dataNavigator);
    List<User> getAllUsers() throws DataAccessException;

    EnumMap<Role, Role> getAllRoles() throws DataAccessException;

    int saveUserData(User user) throws DataAccessException;

    int activateUser(String email) throws DataAccessException;

    List<User> searchUser(String userData) throws DataAccessException;

    Optional<User> userForRegistrationCheck(String userEmail) throws DataAccessException;

    String encryptPassword(String password);

    void registrationUserInsert(User user) throws DataAccessException;

    void registrationUserUpdate(User user) throws DataAccessException;

    Map<Integer, String> getTeachers() throws DataAccessException;


    List<User> getCourseUsers(int courseId) throws DataAccessException;

    int getQuantityUsers() throws SQLException;
}
