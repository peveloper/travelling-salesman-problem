package com.project_1.TSP_Parser;

import java.util.ArrayList;

/**
 * Created by peveloper on 26/10/15.
 */
public class TSP_Tour implements Comparable{

    private ArrayList<Integer> route;
    private double totalDistance;
    private TSP_DistanceMatrix matrix;


    public TSP_Tour(TSP_DistanceMatrix matrix) {
        this.matrix = matrix;
        route = new ArrayList<Integer>();
        totalDistance = 0;
    }

    public void add(int coordinate) {
        if (route.size() > 0) {
            Integer start = route.get(0);
            Integer end = route.get(route.size() - 1);
            totalDistance -= matrix.getDistance(start, end);
            totalDistance += matrix.getDistance(coordinate, start);
            totalDistance += matrix.getDistance(coordinate, end);
            route.add(coordinate);
        } else {
            route.add(coordinate);
        }
    }

    public int compareTo(Object otherTour) {
        if (otherTour instanceof TSP_Tour) {
            return (int) (totalDistance - ((TSP_Tour) otherTour).getDistance());
        } else
            return 1;
    }

    public String toString() {
        String output = "";
        for (Integer i : route) {
            output += ((i+1) + "\n");
        }
//        if (route.size() > 0){
//            output += (route.get(0)+1);
//        }
        output += " distance: " + totalDistance;
        return output;
    }

    public double getDistance() { return totalDistance; }

    public int getSize() {
        return route.size();
    }

    public ArrayList<Integer> getTour(){
        return route;
    }
}
