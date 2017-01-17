package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.vertex.beans.UserMainData;
import ua.com.vertex.dao.interfaces.ViewAllUsersDaoRealizationInf;
import ua.com.vertex.logic.interfaces.UserDetailsLogic;

import java.util.List;

@Component
public class UsersDetailsLogicImpl implements UserDetailsLogic {

    @Autowired
    private ViewAllUsersDaoRealizationInf viewAllUsersDaoRealization;


    @Override
    public List<UserMainData> getUserDetails() {
        return null;
    }
}