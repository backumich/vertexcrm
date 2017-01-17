package ua.com.vertex.beans;

import java.util.Objects;

public class UserAccounting {
    private int dealId;
    private int userId;
    private int certificateId;
    private double courseCoast;
    private double debt;

    public UserAccounting(int dealId, int userId, int certificateId, double courseCoast, double debt) {
        this.dealId = dealId;
        this.userId = userId;
        this.certificateId = certificateId;
        this.courseCoast = courseCoast;
        this.debt = debt;
    }

    public int getDealId() {
        return dealId;
    }

    public void setDealId(int dealId) {
        this.dealId = dealId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(int certificateId) {
        this.certificateId = certificateId;
    }

    public double getCourseCoast() {
        return courseCoast;
    }

    public void setCourseCoast(double courseCoast) {
        this.courseCoast = courseCoast;
    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccounting that = (UserAccounting) o;
        return dealId == that.dealId &&
                userId == that.userId &&
                certificateId == that.certificateId &&
                Double.compare(that.courseCoast, courseCoast) == 0 &&
                Double.compare(that.debt, debt) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dealId, userId, certificateId, courseCoast, debt);
    }

    @Override
    public String toString() {
        return "UserAccounting{" +
                "dealId=" + dealId +
                ", userId=" + userId +
                ", certificateId=" + certificateId +
                ", courseCoast=" + courseCoast +
                ", debt=" + debt +
                '}';
    }
}