package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.AccountingDaoInf;
import ua.com.vertex.logic.interfaces.AccountingLogic;

import java.util.List;


@Service
public class AccountingLogicImpl implements AccountingLogic {

    private static final Logger LOGGER = LogManager.getLogger(AccountingLogicImpl.class);

    private final AccountingDaoInf accountingDaoInf;

    @Autowired
    public AccountingLogicImpl(AccountingDaoInf accountingDaoInf) {
        this.accountingDaoInf = accountingDaoInf;
    }

    @Override
    public List<User> getCourseUsers(int courseId) {

        LOGGER.debug(String.format("Call - accountingDaoInf.getCourseUsers(%s)", courseId));
        return accountingDaoInf.getCourseUsers(courseId);
    }


}
