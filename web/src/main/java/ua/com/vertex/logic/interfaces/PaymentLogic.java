package ua.com.vertex.logic.interfaces;

import ua.com.vertex.beans.PaymentForm;

public interface PaymentLogic {
    int createNewPaymentAndUpdateAccounting(PaymentForm payment);
}
