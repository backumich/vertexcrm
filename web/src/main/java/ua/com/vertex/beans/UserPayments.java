package ua.com.vertex.beans;

import java.util.Objects;

public class UserPayments {
    private int paytmensId;
    private int dealId;
    private double ammount;

    public UserPayments(int paytmensId, int dealId, double ammount) {
        this.paytmensId = paytmensId;
        this.dealId = dealId;
        this.ammount = ammount;
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

    public double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPayments that = (UserPayments) o;
        return paytmensId == that.paytmensId &&
                dealId == that.dealId &&
                Double.compare(that.ammount, ammount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(paytmensId, dealId, ammount);
    }

    @Override
    public String toString() {
        return "UserPayments{" +
                "paytmensId=" + paytmensId +
                ", dealId=" + dealId +
                ", ammount=" + ammount +
                '}';
    }
}