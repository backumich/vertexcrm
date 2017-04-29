package ua.com.vertex.beans;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {

    private int paymentId;
    private int dealId;

    @Digits(integer = 19, fraction = 2)
    @NotNull
    private BigDecimal amount;
    private LocalDateTime paymentDate;

    public Payment() {
    }

    public Payment(BigDecimal amount) {
        this.amount = amount;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getDealId() {
        return dealId;
    }

    public void setDealId(int dealId) {
        this.dealId = dealId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;

        Payment payment = (Payment) o;

        return getPaymentId() == payment.getPaymentId() && getDealId() == payment.getDealId() &&
                (getAmount() != null ? getAmount().equals(payment.getAmount()) : payment.getAmount() == null) &&
                (getPaymentDate() != null ? getPaymentDate().equals(payment.getPaymentDate()) : payment.getPaymentDate()
                        == null);
    }

    @Override
    public int hashCode() {
        int result = getPaymentId();
        result = 31 * result + getDealId();
        result = 31 * result + (getAmount() != null ? getAmount().hashCode() : 0);
        result = 31 * result + (getPaymentDate() != null ? getPaymentDate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", dealId=" + dealId +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                '}';
    }

    public static class Builder {
        private final Payment instance;

        public Builder() {
            instance = new Payment();
        }

        public Payment.Builder setPaymentId(int paymentId) {
            instance.setPaymentId(paymentId);
            return this;
        }

        public Payment.Builder setDealId(int dealId) {
            instance.setDealId(dealId);
            return this;
        }

        public Payment.Builder setAmount(BigDecimal amount) {
            instance.setAmount(amount);
            return this;
        }

        public Payment.Builder setPaymentDate(LocalDateTime paymentDate) {
            instance.setPaymentDate(paymentDate);
            return this;
        }

        public Payment getInstance() {
            return instance;
        }
    }
}