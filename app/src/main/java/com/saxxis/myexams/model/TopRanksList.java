package com.saxxis.myexams.model;

/**
 * Created by saxxis25 on 10/10/2017.
 */

public class TopRanksList {

    private String name;
    private String UserScore;
    private String rank;

public TopRanksList(String name,String UserScore,String rank){
    this.name=name;
    this.UserScore=UserScore;
    this.rank=rank;

}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserScore() {
        return UserScore;
    }

    public void setUserScore(String userScore) {
        UserScore = userScore;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
