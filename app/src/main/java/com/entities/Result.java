package com.entities;

import java.io.Serializable;

public class Result implements Serializable {
    private String phase;
    private String date;
    private String teamHm;
    private int goaltmHm;
    private String teamAw;
    private int goaltmAw;

    public Result(String phase, String date, String teamHm, int goaltmHm, String teamAw, int goaltmAw) {
        this.phase = phase;
        this.date = date;
        this.teamHm = teamHm;
        this.goaltmHm = goaltmHm;
        this.goaltmAw = goaltmAw;
        this.teamAw = teamAw;
    }

    @Override
    public String toString() {
        return "Results: " +
                "Phase = " + phase + '\'' +
                ", Date = " + date + '\'' +
                ", Home Team='" + teamHm + '\'' +
                ", Goals Home Team = " + goaltmHm +
                ", Team Away = '" + goaltmAw+ '\'' +
                ", Goals Team Away = " + goaltmAw;
    }

    public Result() {}

    public String getTeamHm() {
        return teamHm;
    }

    public String getTeamAw() {
        return teamAw;
    }

    public String getPhase() {
        return phase;
    }

    public String getDate() {
        return date;
    }

    public int getGoalTmHm() {
        return goaltmHm;
    }

    public int getGoalTmAw() {
        return goaltmAw;
    }

    public void setTeamHm(String teamHm) {
        this.teamHm = teamHm;
    }

    public void setTeamAw(String teamAw) {
        this.teamAw = teamAw;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }
}
