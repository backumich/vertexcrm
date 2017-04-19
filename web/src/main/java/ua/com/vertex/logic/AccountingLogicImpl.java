package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.AccountingDaoInf;
import ua.com.vertex.logic.interfaces.AccountingLogic;

import java.util.List;

@Service
public class AccountingLogicImpl implements AccountingLogic {

    private final AccountingDaoInf accountingDaoInf;

    @Override
    public List<User> getCourseUsers(int courseId) {
        return accountingDaoInf.getCourseUsers(courseId);
    }

    @Autowired
    public AccountingLogicImpl(AccountingDaoInf accountingDaoInf) {
        this.accountingDaoInf = accountingDaoInf;
    }


}
