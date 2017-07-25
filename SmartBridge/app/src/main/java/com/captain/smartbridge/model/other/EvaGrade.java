package com.captain.smartbridge.model.other;

/**
 * Created by Captain on 17/7/25.
 */

public class EvaGrade {
    /**
     * top_score : 73.93
     * score : 98.7480000000
     * deck_score : 98.44
     * bottom_score : 93.72
     */

    private double top_score;
    private String score;
    private double deck_score;
    private double bottom_score;

    public double getTop_score() {
        return top_score;
    }

    public void setTop_score(double top_score) {
        this.top_score = top_score;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public double getDeck_score() {
        return deck_score;
    }

    public void setDeck_score(double deck_score) {
        this.deck_score = deck_score;
    }

    public double getBottom_score() {
        return bottom_score;
    }

    public void setBottom_score(double bottom_score) {
        this.bottom_score = bottom_score;
    }
}
