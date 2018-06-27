package com.hanxs.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserLotteryBean {

    /**
     * id : 10000000
     * user_id : 10000000
     * contest_lottery_ids : [10000000,10000001]
     * times : 10
     * result : null
     * status : 0
     * created_at : 2018-06-21T05:42:36.395095+00:00
     */

    private int id;
    private int userId;
    private int times;
    private int result;
    private int prize;
    private int status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;
    private List<ContestLotteryBean> contestLotteries;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
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

    public int getPrize() {
        return prize;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public List<ContestLotteryBean> getContestLotteries() {
        return contestLotteries;
    }

    public void setContestLotteries(List<ContestLotteryBean> contestLotteries) {
        this.contestLotteries = contestLotteries;
    }
}
