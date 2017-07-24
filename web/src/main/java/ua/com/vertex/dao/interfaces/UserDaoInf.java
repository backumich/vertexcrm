package ua.com.vertex.dao.interfaces;

import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;
import ua.com.vertex.utils.DataNavigator;

import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

public interface UserDaoInf {

    Optional<User> getUser(int id);

    Optional<User> getUserByEmail(String email);

    Optional<User> logIn(String username);

    List<Integer> getAllUserIds();

    List<User> getUsersPerPages(DataNavigator dataNavigator);

    int getQuantityUsers();

    List<User> getAllUsers();

    Optional<User> userForRegistrationCheck(String userEmail);

    EnumMap<Role, Role> getAllRoles();

    int saveUserData(User user);

    void saveImage(int userId, byte[] image, String imageType);

    Optional<byte[]> getImage(int userId, String imageType);

    int activateUser(String email);

    int addUserForCreateCertificate(User user);

    List<User> searchUser(String userData);

    void registrationUserInsert(User user);

    void registrationUserUpdate(User user);

    List<User> getCourseUsers(int courseId);

    List<User> getTeachers();

}
