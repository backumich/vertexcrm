package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.dao.UserDaoRealizationInf;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserLogicImpl implements UserLogic {

    @Autowired
    private UserDaoRealizationInf userDao;

    @Override
    public List<String> getAllUserIds() {
        return userDao.getAllUserIds().stream().map(id -> Integer.toString(id)).collect(Collectors.toList());
    }
}
