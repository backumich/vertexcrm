package ua.com.vertex.logic.interfaces;

import ua.com.vertex.beans.PaymentForm;

public interface Paymentlogic {
    int createNewPaymentAndUpdateAccounting(PaymentForm payment);
}
