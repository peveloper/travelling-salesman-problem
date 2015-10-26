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
        if (route.size() > 0) {
            TSP_Coordinate start = route.get(0);
            TSP_Coordinate end = route.get(route.size() - 1);
            totalDistance -= matrix.getDistance(start.getId(), end.getId());
            totalDistance += matrix.getDistance(coordinate.getId(), start.getId());
            totalDistance += matrix.getDistance(coordinate.getId(), end.getId());
            route.add(coordinate);
        } else {
            route.add(coordinate);
        }
    }

    public double getDistance() {
        return totalDistance;
    }
}
