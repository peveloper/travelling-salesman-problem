package com.project_1.TSP_Parser;

import java.util.ArrayList;

/**
 * Created by peveloper on 17/11/15.
 */
public class TSP_NearestNeighbour {

    private TSP_DistanceMatrix distanceMatrix;

    public TSP_NearestNeighbour(TSP_DistanceMatrix distanceMatrix){
        this.distanceMatrix = distanceMatrix;
    }

    // Generate the NN tour
    public TSP_Tour generateTour(ArrayList<TSP_Coordinate> cities) {
        return new TSP_Tour(distanceMatrix);
    }

    // Determine nearest vertex to a given vertex. -1 if error.
    public int nearest(TSP_DistanceMatrix distances, int index, TSP_Tour t) {
        return -1;
    }
}
