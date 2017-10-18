package ua.com.vertex.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ua.com.vertex.beans.Accounting;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.dao.interfaces.AccountingDaoImplForTest;
import ua.com.vertex.dao.interfaces.AccountingDaoInf;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
@Transactional
public class AccountingDaoImplTest {

    @Autowired
    private AccountingDaoInf accountingDaoInf;

    @Autowired
    private AccountingDaoImplForTest accountingDaoImplForTest;

    @Test
    public void insertAccountingRowCorrectInsert() throws Exception {

        Accounting accounting = new Accounting.Builder().setUserId(22).setCourseId(2).setCourseCoast(8000d)
                .setDept(8000d).getInstance();
        accounting.setDealId(accountingDaoInf.insertAccountingRow(accounting));
        assertEquals("Maybe method was changed", accounting,
                accountingDaoImplForTest.getAccountingByCourseIdAndUserId(2, 22).orElse(new Accounting()));
    }

    @Test
    public void updateUserDeptCorrectUpdate() throws Exception {

        Accounting accounting = new Accounting.Builder().setUserId(2).setCourseId(2).setCourseCoast(8000d).
                setDept(8000d).getInstance();
        double amount = 3000d;
        accounting.setDealId(accountingDaoInf.insertAccountingRow(accounting));
        accountingDaoInf.updateUserDept(2, 2, amount);
        accounting.setDebt(accounting.getDebt() - amount);
        assertEquals("Maybe method was changed", accounting,
                accountingDaoImplForTest.getAccountingByCourseIdAndUserId(2, 2).orElse(new Accounting()));
    }

}
