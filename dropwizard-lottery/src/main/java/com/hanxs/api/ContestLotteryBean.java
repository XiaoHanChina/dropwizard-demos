package com.hanxs.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ContestLotteryBean {

    /**
     * id : 10000000
     * contest_id : 10000000
     * type : 0
     * win : 胜
     * offer_goal : null
     * offer_win : null
     * total_goal : null
     * host_goal : null
     * visitor_goal : null
     * odds : 1.71
     * result : null
     * status : 0
     * created_at : 2018-06-21 03:35:36
     */

    private int id;
    private int contestId;
    private int type;
    private String win;
    private int offerGoal;
    private String offerWin;
    private int totalGoal;
    private int hostGoal;
    private int visitorGoal;
    private double odds;
    private double result;
    private int status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public int getOfferGoal() {
        return offerGoal;
    }

    public void setOfferGoal(int offerGoal) {
        this.offerGoal = offerGoal;
    }

    public String getOfferWin() {
        return offerWin;
    }

    public void setOfferWin(String offerWin) {
        this.offerWin = offerWin;
    }

    public int getTotalGoal() {
        return totalGoal;
    }

    public void setTotalGoal(int totalGoal) {
        this.totalGoal = totalGoal;
    }

    public int getHostGoal() {
        return hostGoal;
    }

    public void setHostGoal(int hostGoal) {
        this.hostGoal = hostGoal;
    }

    public int getVisitorGoal() {
        return visitorGoal;
    }

    public void setVisitorGoal(int visitorGoal) {
        this.visitorGoal = visitorGoal;
    }

    public double getOdds() {
        return odds;
    }

    public void setOdds(double odds) {
        this.odds = odds;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
