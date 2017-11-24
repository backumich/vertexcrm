package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.com.vertex.beans.PasswordResetDto;
import ua.com.vertex.beans.User;
import ua.com.vertex.controllers.exceptionHandling.exceptions.MultipartValidationException;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.DataNavigator;
import ua.com.vertex.utils.UtilFunctions;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:application.properties")
public class UserLogicImpl implements UserLogic {
    private static final Logger LOGGER = LogManager.getLogger(UserLogicImpl.class);

    private final UserDaoInf userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${image.size.bytes}")
    private int fileSizeInBytes;

    @Value("${passwordLinkExpire}")
    private int passwordLinkExpire;

    @Override
    public List<String> getAllUserIds() {
        return userDao.getAllUserIds().stream().map(id -> Integer.toString(id)).collect(Collectors.toList());
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userDao.getUser(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public void saveImage(String email, MultipartFile file, String imageType) {
        byte[] image;
        validateMultipartFile(file);
        try {
            image = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userDao.saveImage(email, image, imageType);
    }

    private void validateMultipartFile(MultipartFile file) {
        if (file.getSize() > fileSizeInBytes) {
            throw new MultipartValidationException("File size exceeded, max allowed is " +
                    UtilFunctions.humanReadableByteCount(fileSizeInBytes));
        } else if (!file.getContentType().split("/")[0].equals("image")) {
            throw new MultipartValidationException("You have chosen a file of invalid type!");
        }
    }

    @Override
    public Optional<byte[]> getImage(String email, String imageType) {
        return userDao.getImage(email, imageType);
    }

    public int getQuantityUsers() {
        return userDao.getQuantityUsers();
    }

    @Override
    public List<User> getUsersPerPages(DataNavigator dataNavigator) {
        LOGGER.debug("Get part data users list (dataNavigator)");

        return userDao.getUsersPerPages(dataNavigator);
    }

    @Override
    public int saveUserData(User user) {
        return userDao.saveUserData(user);
    }

    @Override
    public int activateUser(String email) {
        return userDao.activateUser(email);
    }

    @Override
    public List<User> searchUser(String userData) {
        return userDao.searchUser(userData);
    }

    @Override
    public Optional<User> userForRegistrationCheck(String userEmail) {
        return userDao.userForRegistrationCheck(userEmail);
    }

    @Override
    public void registrationUserInsert(User user) {
        LOGGER.debug(String.format("Call - userDao.registrationUserInsert(%s) ;", user));
        user.setPassword(encryptPassword(user.getPassword()));
        userDao.registrationUserInsert(user);
    }

    @Override
    public void registrationUserUpdate(User user) throws DataAccessException {
        LOGGER.debug(String.format("Call - userDao.registrationUserUpdate(%s) ;", user));
        user.setPassword(encryptPassword(user.getPassword()));
        userDao.registrationUserUpdate(user);
    }

    @Override
    public Map<Integer, String> getTeachers() throws DataAccessException {
        LOGGER.debug("Call - userDao.getTeachers()");

        return userDao.getTeachers().stream().collect(Collectors.toMap(User::getUserId,
                x -> x.getFirstName() + " " + x.getLastName() + " \'" + x.getEmail() + "\'"));
    }

    @Override
    public String encryptPassword(String password) {
        LOGGER.debug("Password encryption");
        return bCryptPasswordEncoder.encode(password);
    }

    @Autowired
    public UserLogicImpl(UserDaoInf userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> getCourseUsers(int courseId) {

        LOGGER.debug(String.format("Call - accountingDaoInf.getCourseUsers(%s)", courseId));
        return userDao.getCourseUsers(courseId);
    }

    @Override
    public boolean isUserRegisteredAndActive(String email) {
        User user = userDao.userForRegistrationCheck(email).orElse(User.EMPTY_USER);
        return user.isActive();
    }

    @Override
    public long setParamsToRestorePassword(String email, String uuid, LocalDateTime creationTime) {
        return userDao.setParamsToRestorePassword(email, uuid, creationTime);
    }

    @Override
    public String getEmailByUuid(long id, String uuid) {
        PasswordResetDto dto = userDao.getEmailByUuid(id, uuid);
        LOGGER.debug("Checking if link to restore password has expired");
        return dto.getCreationTime().plusMinutes(passwordLinkExpire).isAfter(LocalDateTime.now()) ? dto.getEmail() : "";
    }

    @Override
    public void savePassword(String email, String password) {
        userDao.savePassword(email, bCryptPasswordEncoder.encode(password));
    }
}
