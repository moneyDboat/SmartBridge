package com.captain.smartbridge.model.other;

/**
 * Created by Captain on 17/7/25.
 */

public class EvaHistory {

    /**
     * deck_score : 100.0
     * top_score : 76.77
     * score : 89.556
     * lrsj : 2017-05-30
     * bottom_score : 97.12
     */

    private double deck_score;
    private double top_score;
    private double score;
    private String lrsj;
    private double bottom_score;

    public double getDeck_score() {
        return deck_score;
    }

    public void setDeck_score(double deck_score) {
        this.deck_score = deck_score;
    }

    public double getTop_score() {
        return top_score;
    }

    public void setTop_score(double top_score) {
        this.top_score = top_score;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getLrsj() {
        return lrsj;
    }

    public void setLrsj(String lrsj) {
        this.lrsj = lrsj;
    }

    public double getBottom_score() {
        return bottom_score;
    }

    public void setBottom_score(double bottom_score) {
        this.bottom_score = bottom_score;
    }
}
