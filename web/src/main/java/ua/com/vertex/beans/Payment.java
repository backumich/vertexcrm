package ua.com.vertex.beans;

import java.time.LocalDateTime;

public class Payment {
    private int paytmensId;
    private int dealId;
    private double amount;
    private LocalDateTime paymentDate;

    public Payment() {
    }

    public Payment(int paytmensId, int dealId, double amount, LocalDateTime paymentDate) {
        this.paytmensId = paytmensId;
        this.dealId = dealId;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public int getPaytmensId() {
        return paytmensId;
    }

    public void setPaytmensId(int paytmensId) {
        this.paytmensId = paytmensId;
    }

    public int getDealId() {
        return dealId;
    }

    public void setDealId(int dealId) {
        this.dealId = dealId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
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
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (paytmensId != payment.paytmensId) return false;
        if (dealId != payment.dealId) return false;
        if (Double.compare(payment.amount, amount) != 0) return false;
        return paymentDate != null ? paymentDate.equals(payment.paymentDate) : payment.paymentDate == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = paytmensId;
        result = 31 * result + dealId;
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (paymentDate != null ? paymentDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paytmensId=" + paytmensId +
                ", dealId=" + dealId +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                '}';
    }
}