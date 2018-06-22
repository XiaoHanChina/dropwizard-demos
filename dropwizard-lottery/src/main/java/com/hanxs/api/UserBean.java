package com.hanxs.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserBean {

    /**
     * id : 10000000
     * name : hanxs
     * balance : 9940
     * status : 0
     * created_at : 2018-06-21 03:26:56
     */

    private int id;
    private String name;
    private String password;
    private int balance;
    private int status;
    private String createdAt;
    private UserLotteryStat lottery;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserLotteryStat getLottery() {
        return lottery;
    }

    public void setLottery(UserLotteryStat lottery) {
        this.lottery = lottery;
    }
}
