package cn.rx.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class SysUser implements Serializable {
    private Long userId;
    private String userName;
    private String userPassword;
    private String userMemo;
   //多对多关系建立
    private Set<Role> roles = new HashSet<>(0);

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserMemo() {
        return userMemo;
    }

    public void setUserMemo(String userMemo) {
        this.userMemo = userMemo;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userMemo='" + userMemo + '\'' +
                '}';
    }
}
