package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.com.vertex.beans.Payment;
import ua.com.vertex.beans.PaymentForm;
import ua.com.vertex.dao.interfaces.AccountingDaoInf;
import ua.com.vertex.dao.interfaces.PaymentDaoInf;
import ua.com.vertex.logic.interfaces.PaymentLogic;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class PaymentLogicImplTest {

    @Mock
    private PaymentDaoInf paymentDaoInf;

    @Mock
    private AccountingDaoInf accountingDaoInf;

    private PaymentLogic paymentLogic;
    private PaymentForm paymentForm;

    @Before
    public void setUp() throws Exception {
        paymentLogic = new PaymentLogicImpl(paymentDaoInf, accountingDaoInf);
        paymentForm = new PaymentForm(1, 1, new Payment.Builder().setPaymentId(1).setDealId(1)
                .setAmount(BigDecimal.valueOf(1000))
                .setPaymentDate(LocalDateTime.now()).getInstance());
    }

    @Test
    public void createNewPaymentAndUpdateAccountingCallPaymentAndAccountingDao() throws Exception {
        paymentLogic.createNewPaymentAndUpdateAccounting(paymentForm);

        verify(paymentDaoInf, times(1)).createNewPayment(paymentForm.getCourseId(),
                paymentForm.getUserID(), paymentForm.getPayment());
        verify(accountingDaoInf, times(1)).updateUserDept(paymentForm.getCourseId(),
                paymentForm.getUserID(), paymentForm.getPayment().getAmount().doubleValue());
    }

}
