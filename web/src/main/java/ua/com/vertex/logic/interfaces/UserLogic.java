package ua.com.vertex.logic.interfaces;

import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import ua.com.vertex.beans.User;
import ua.com.vertex.utils.DataNavigator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserLogic {

    List<String> getAllUserIds();

    Optional<User> getUserById(int id);

    Optional<User> getUserByEmail(String email);

    void saveImage(String email, MultipartFile file, String imageType);

    boolean validateMultipartFileWithBindingResult(MultipartFile file, BindingResult result, String image);

    Optional<byte[]> getImage(String email, String imageType);

    List<User> getUsersPerPages(DataNavigator dataNavigator);

    int saveUserData(User user);

    int activateUser(String email);

    List<User> searchUser(String userData);

    Optional<User> userForRegistrationCheck(String userEmail);

    String encryptPassword(String password);

    void registrationUserInsert(User user);

    void registrationUserUpdate(User user);

    Map<Integer, String> getTeachers();

    List<User> getCourseUsers(int courseId);

    int getQuantityUsers();

    boolean isUserRegisteredAndActive(String email);

    long setParamsToRestorePassword(String email, String uuid, LocalDateTime creationTime);

    String getEmailByUuid(long id, String uuid);

    void savePassword(String email, String password);
}
