package tsp;

import java.util.ArrayList;


public class Tour {

    private ArrayList<Integer> route;
    private double distanceSoFar;
    private DistanceMatrix distanceMatrix;


    public Tour(DistanceMatrix distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
        route = new ArrayList<Integer>();
        distanceSoFar = 0;
    }

    public void push(int city) {
        if (route.size() > 0) {
            Integer start = route.get(0);
            Integer end = route.get(route.size() - 1);
            distanceSoFar -= distanceMatrix.getDistance(start, end);
            distanceSoFar += distanceMatrix.getDistance(city, start);
            distanceSoFar += distanceMatrix.getDistance(city, end);
            route.add(city);
        } else {
            route.add(city);
        }
    }

    public void addAtPosition(int i, int city) {
        int j = (i+1) % route.size();
        double dij = distanceMatrix.getDistance(route.get(i), route.get(j));
        double dik = distanceMatrix.getDistance(route.get(i), city);
        double dkj = distanceMatrix.getDistance(city, route.get(j));
        distanceSoFar -= dij;
        distanceSoFar += dik;
        distanceSoFar += dkj;
        route.add(i+1, city);
    }

    public void minAddCost(int city) {

        double minCost = Double.POSITIVE_INFINITY;
        int pos = -1;
        int j;

        for(int i=0; i < route.size(); i++) {
            j= (i+1) % route.size();
            double dij = distanceMatrix.getDistance(route.get(i), route.get(j));
            double dik = distanceMatrix.getDistance(route.get(i), city);
            double dkj = distanceMatrix.getDistance(city, route.get(j));
            if (dik + dkj - dij  < minCost) {
                minCost = dik + dkj - dij;
                pos = i;
            }
        }

        this.addAtPosition(pos, city);
    }
    
    public void addAll(ArrayList<Integer> cities) {
        for(int city: cities) {
            this.push(city);
        }
    }

    public double minDistanceFromTour(int city) {

        double minCost = Double.POSITIVE_INFINITY;

        for(int i=0; i < route.size(); i++) {
            double dik = distanceMatrix.getDistance(route.get(i), city);
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
        output += "\nTour Length: " + getTotalDistance();
        return output;
    }

    public double getDistanceSoFar() { return distanceSoFar; }

    public int getSize() {
        return route.size();
    }

    public ArrayList<Integer> getTour(){
        return route;
    }

    public int get(int index) {
        return route.get(index);
    }

    public void swapCities(int i, int j) {
        int k = j;
        int limit = i + ((j - i)/ 2);

        for(int x = i; x <= limit; x++) {
            int temp = route.get(x);
            route.set(x, route.get(k));
            route.set(k, temp);
            k--;
        }
    }

    public int getTotalDistance() {
        int distance = 0;
        for (int i = 0; i < route.size(); i++) {
            distance += distanceMatrix.getMatrix()[route.get(i)][route.get((i+1) % route.size())];
        }
        return distance;
    }
}