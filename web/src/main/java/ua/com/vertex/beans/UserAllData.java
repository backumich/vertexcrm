package ua.com.vertex.beans;

public class UserAllData extends User {
    private int roleId;
    private String name;

    public UserAllData(int roleId, String name) {
        super();
        this.roleId = roleId;
        this.name = name;
    }


    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}