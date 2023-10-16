package org.newrain.base.ioc.bean;

/**
 * @author newRain
 * @description User Bean
 */
public class User {

    private Integer userId;
    private String userName;

    public User() {
    }

    public Integer getUserId() {
        System.out.println("User:getUserID");
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        System.out.println("User:getUserName");
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
