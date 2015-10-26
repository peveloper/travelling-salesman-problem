package com.project_1.TSP_Parser;

/**
 * Created by peveloper on 17/09/15.
 */

public class TSP_Coordinate {

    private double x;
    private double y;
    private int id;

    public TSP_Coordinate(double x, double y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public double getX() { return this.x; }
    public double getY() { return this.y; }
    public int getId() { return this.id; }
}
