package tsp.simulatedannealing;

import java.util.ArrayList;


public class FarthestInsertion {

    private DistanceMatrix distanceMatrix;
    private double [] tourDistances;

    public FarthestInsertion(DistanceMatrix distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
        tourDistances = new double[distanceMatrix.getSize()];
    }

    public ArrayList<Integer> buildInitialTour() {
        ArrayList<Integer> subTour = new ArrayList<Integer>();
        double initialMinCost = 0;
        int first = -1, second = -1;

        for(int i=0; i < distanceMatrix.getSize(); i++) {
            for(int j=0; j < distanceMatrix.getSize(); j++) {
                if(i != j) {
                    if(distanceMatrix.getDistance(i,j) + distanceMatrix.getDistance(j,i) > initialMinCost) {
                        initialMinCost = distanceMatrix.getDistance(i,j) + distanceMatrix.getDistance(j,i);
                        first = i;
                        second = j;
                    }
                }
            }
        }

        subTour.add(first);
        subTour.add(second);

        return subTour;
    }

    public Tour generateTour() {
        ArrayList<Integer> subTour = buildInitialTour();
        Tour tour = new Tour(distanceMatrix);


        for(int city: subTour) {
            tour.push(city);
        }

        for (int i=0; i < distanceMatrix.getSize(); i++) {
            if (!tour.getTour().contains(i)){
                tourDistances[i] = tour.minDistanceFromTour(i);
            }
        }

        int nextCity;

        while (tour.getSize() < distanceMatrix.getSize()) {
            nextCity = farthest(tour);
            tour.minAddCost(nextCity);

            for (int i=0; i < distanceMatrix.getSize(); i++) {
                if (!tour.getTour().contains(i)){
                    if (tourDistances[i] < distanceMatrix.getDistance(i, nextCity)) {
                        tourDistances[i] = distanceMatrix.getDistance(i, nextCity);
                    }
                }
            }
        }

        return tour;
    }

    public int farthest(Tour subTour) {
        double max = 0;
        int pos = -1;

        for (int i = 0; i < distanceMatrix.getSize(); i++) {
            if(!subTour.getTour().contains(i)) {
                if (tourDistances[i] > max) {
                    max = tourDistances[i];
                    pos = i;
                }
            }
        }

        return pos;
    }

}
