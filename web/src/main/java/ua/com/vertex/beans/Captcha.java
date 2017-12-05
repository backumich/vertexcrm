package ua.com.vertex.beans;

public class Captcha {

    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "Captcha{" +
                "success=" + success +
                '}';
    }
}
