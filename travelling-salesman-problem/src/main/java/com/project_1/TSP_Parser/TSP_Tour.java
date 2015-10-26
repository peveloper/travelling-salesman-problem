package com.project_1.TSP_Parser;

import java.util.ArrayList;

/**
 * Created by peveloper on 26/10/15.
 */
public class TSP_Tour {

    private ArrayList<TSP_Coordinate> route;
    private double totalDistance;
    private TSP_DistanceMatrix matrix;


    public TSP_Tour(TSP_DistanceMatrix matrix) {
        this.matrix = matrix;
        route = new ArrayList<TSP_Coordinate>();
        totalDistance = 0;
    }

    public void add(TSP_Coordinate coordinate) {
        route.add(coordinate);
        // TODO update new total distance
    }

    public double getDistance() {
        return totalDistance;
    }
}
