package tsp;

import java.util.ArrayList;


public class Tour {

    private ArrayList<Integer> route;
    private double totalDistance;
    private DistanceMatrix matrix;


    public Tour(DistanceMatrix matrix) {
        this.matrix = matrix;
        route = new ArrayList<Integer>();
        totalDistance = 0.0;
    }

    public void push(int coordinate) {
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

    public void addAtPosition(int i, int coordinate) {
        int j = (i+1) % route.size();
        double dij = matrix.getDistance(route.get(i), route.get(j));
        double dik = matrix.getDistance(route.get(i), coordinate);
        double dkj = matrix.getDistance(coordinate, route.get(j));
        totalDistance -= dij;
        totalDistance += dik;
        totalDistance += dkj;
        route.add(i+1, coordinate);
    }

    public void minAddCost(int coordinate) {

        double minCost = Double.POSITIVE_INFINITY;
        int pos = -1;
        int j;

        for(int i=0; i < route.size(); i++) {
            j= (i+1) % route.size();
            double dij = matrix.getDistance(route.get(i), route.get(j));
            double dik = matrix.getDistance(route.get(i), coordinate);
            double dkj = matrix.getDistance(coordinate, route.get(j));
            if (dik + dkj - dij  < minCost) {
                minCost = dik + dkj - dij;
                pos = i;
            }
        }

        this.addAtPosition(pos, coordinate);
    }

    public double minDistanceFromTour(int coordinate) {

        double minCost = Double.POSITIVE_INFINITY;

        for(int i=0; i < route.size(); i++) {
            double dik = matrix.getDistance(route.get(i), coordinate);
            if (dik > minCost) {
                minCost = dik;
            }
        }

        return minCost;
    }

    public String toString() {
        String output = "";
        for (Integer i : route) {
            output += ((i+1) + "\n");
        }
        if (route.size() > 0){
            output += (route.get(0)+1) + "\n";
        }
        output += "\nTour Length: " + totalDistance;
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