package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import ua.com.vertex.dao.interfaces.AccountingDaoInf;
import ua.com.vertex.logic.interfaces.AccountingLogic;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountingLogicImplTest {

    @Mock
    private AccountingDaoInf accountingDaoInf;

    private AccountingLogic accountingLogic;

    @Before
    public void setUp() {
        accountingLogic = new AccountingLogicImpl(accountingDaoInf);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void getCourseUsersVerifyAccountingDaoAndReturnException() throws Exception {
        when(accountingDaoInf.getCourseUsers(1)).thenThrow(new DataIntegrityViolationException("Test"));
        accountingLogic.getCourseUsers(1);
        verify(accountingDaoInf, times(1)).getCourseUsers(1);
    }
}
