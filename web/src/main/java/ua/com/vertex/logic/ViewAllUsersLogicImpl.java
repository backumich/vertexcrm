package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import ua.com.vertex.beans.UserMainData;
import ua.com.vertex.dao.interfaces.ViewAllUsersDaoRealizationInf;
import ua.com.vertex.logic.interfaces.ViewAllUsersLogic;

import java.util.List;

public class ViewAllUsersLogicImpl implements ViewAllUsersLogic {

    @Autowired
    private ViewAllUsersDaoRealizationInf viewAllUsersDaoRealization;

    public List<UserMainData> getListUsers() {
        viewAllUsersDaoRealization.getListUsers();
        return viewAllUsersDaoRealization.getListUsers();
    }
}