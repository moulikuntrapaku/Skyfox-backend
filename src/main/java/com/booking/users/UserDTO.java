package com.booking.users;

public class UserDTO {
    private String userName;
    private String oldPassword;
    private String newPassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public User mapUserDtoToUser(UserDTO userDTO, User user) {
        user.setUsername(userDTO.getUserName());
        user.setPassword(userDTO.getNewPassword());
        return user;
    }
}

