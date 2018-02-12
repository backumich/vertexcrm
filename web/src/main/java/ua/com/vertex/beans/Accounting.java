package ua.com.vertex.beans;


public class Accounting {
    private int dealId, userId, courseId;
    private double courseCoast;
    private double debt;

    public Accounting() {
    }

    public Accounting(int dealId, int userId, int courseId, double courseCoast, double debt) {
        this.dealId = dealId;
        this.userId = userId;
        this.courseId = courseId;
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

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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

        return dealId == that.dealId && userId == that.userId && courseId == that.courseId &&
                Double.compare(that.courseCoast, courseCoast) == 0 && Double.compare(that.debt, debt) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = dealId;
        result = 31 * result + userId;
        result = 31 * result + courseId;
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
                ", courseId=" + courseId +
                ", courseCoast=" + courseCoast +
                ", debt=" + debt +
                '}';
    }

    public static class Builder {
        private final Accounting instance;

        public Builder() {
            instance = new Accounting();
        }

        public Builder setDealId(int dealId) {
            instance.setDealId(dealId);
            return this;
        }

        public Builder setUserId(int userId) {
            instance.setUserId(userId);
            return this;
        }

        public Builder setCourseId(int courseId) {
            instance.setCourseId(courseId);
            return this;
        }

        public Builder setCourseCoast(double courseCoast) {
            instance.setCourseCoast(courseCoast);
            return this;
        }

        public Builder setDept(double dept) {
            instance.setDebt(dept);
            return this;
        }

        public Accounting getInstance() {
            return instance;
        }
    }
}