package com.hanxs.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLotteryStat {

    /**
     * total_count : 6
     * wating_count : 6
     * win_count : 0
     * lose_count : 0
     * win_prize : 0.0
     */

    private int totalCount;
    private int watingCount;
    private int winCount;
    private int loseCount;
    private double winPrize;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getWatingCount() {
        return watingCount;
    }

    public void setWatingCount(int watingCount) {
        this.watingCount = watingCount;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getLoseCount() {
        return loseCount;
    }

    public void setLoseCount(int loseCount) {
        this.loseCount = loseCount;
    }

    public double getWinPrize() {
        return winPrize;
    }

    public void setWinPrize(double winPrize) {
        this.winPrize = winPrize;
    }
}
