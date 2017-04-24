package ua.com.vertex.logic.interfaces;

import ua.com.vertex.beans.Payment;

public interface Paymentlogic {
    int createNewPaymentAndUpdateAccounting(int courseId, int userId, Payment payment);
}
