package ua.com.vertex.dao.interfaces;

import org.springframework.dao.DataAccessException;
import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;
import ua.com.vertex.utils.DataNavigator;

import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

public interface UserDaoInf {

    Optional<User> getUser(int id)throws DataAccessException;

    Optional<User> getUserByEmail(String email)throws DataAccessException;

    Optional<User> logIn(String username);

    @SuppressWarnings("unused")
    void deleteUser(int id) throws DataAccessException;

    List<Integer> getAllUserIds() throws DataAccessException;

    List<User> getAllUser() throws SQLException;

    List<User> getUsersPerPages(DataNavigator dataNavigator);

    int getQuantityUsers() throws SQLException;

    Optional<User> getUserDetailsByID(int userID) throws SQLException;
    List<User> getAllUsers() throws DataAccessException;

    Optional<User> userForRegistrationCheck(String userEmail) throws DataAccessException;

    EnumMap<Role, Role> getAllRoles() throws DataAccessException;

    int saveUserData(User user) throws DataAccessException;

    void saveImage(int userId, byte[] image, String imageType) throws DataAccessException;

    Optional<byte[]> getImage(int userId, String imageType) throws DataAccessException;

    int activateUser(String email) throws DataAccessException;

    int addUserForCreateCertificate(User user) throws DataAccessException;

    List<User> searchUser(String userData) throws DataAccessException;

    void registrationUserInsert(User user) throws DataAccessException;

    void registrationUserUpdate(User user) throws DataAccessException;

    List<User> getCourseUsers(int courseId) throws DataAccessException;

    List<User> getTeachers () throws DataAccessException;

}
