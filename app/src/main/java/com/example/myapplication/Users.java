package com.example.myapplication;

public class Users
{
    private String name;
    private Double points;

    public Users(String name,Double points)
    {
        this.name = name;
        this.points = points;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public Double getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }
}
