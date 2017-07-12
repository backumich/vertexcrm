package ua.com.vertex.beans;

public enum Role {
    ROLE_ADMIN(1), ROLE_USER(2), ROLE_TEACHER(3);

    private final int id;

    Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
