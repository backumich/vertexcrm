package ua.com.vertex.beans;

import javax.validation.Valid;

public class PaymentForm {

    private Integer courseId, userID;

    @Valid
    private Payment payment;

    public PaymentForm() {
    }

    public PaymentForm(Integer courseId, Integer userID, Payment payment) {
        this.courseId = courseId;
        this.userID = userID;
        this.payment = payment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentForm)) return false;

        PaymentForm that = (PaymentForm) o;

        return (getCourseId() != null ? getCourseId().equals(that.getCourseId()) : that.getCourseId() == null)
                && (getUserID() != null ? getUserID().equals(that.getUserID()) : that.getUserID() == null)
                && (getPayment() != null ? getPayment().equals(that.getPayment()) : that.getPayment() == null);
    }

    @Override
    public int hashCode() {
        int result = getCourseId() != null ? getCourseId().hashCode() : 0;
        result = 31 * result + (getUserID() != null ? getUserID().hashCode() : 0);
        result = 31 * result + (getPayment() != null ? getPayment().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PaymentForm{" +
                "courseId=" + courseId +
                ", userID=" + userID +
                ", payment=" + payment +
                '}';
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
