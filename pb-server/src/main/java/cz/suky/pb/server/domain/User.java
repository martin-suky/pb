package cz.suky.pb.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;

@Entity
public class User extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    private String username;

    @JsonIgnore
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}