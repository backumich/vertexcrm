package ua.com.vertex.beans;


public class Accounting {
    private int dealId;
    private int userId;
    private int certificateId;
    private double courseCoast;
    private double debt;

    public Accounting() {
    }

    public Accounting(int dealId, int userId, int certificateId, double courseCoast, double debt) {
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

        Accounting that = (Accounting) o;

        if (dealId != that.dealId) return false;
        if (userId != that.userId) return false;
        if (certificateId != that.certificateId) return false;
        if (Double.compare(that.courseCoast, courseCoast) != 0) return false;
        return Double.compare(that.debt, debt) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = dealId;
        result = 31 * result + userId;
        result = 31 * result + certificateId;
        temp = Double.doubleToLongBits(courseCoast);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(debt);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Accounting{" +
                "dealId=" + dealId +
                ", userId=" + userId +
                ", certificateId=" + certificateId +
                ", courseCoast=" + courseCoast +
                ", debt=" + debt +
                '}';
    }
}