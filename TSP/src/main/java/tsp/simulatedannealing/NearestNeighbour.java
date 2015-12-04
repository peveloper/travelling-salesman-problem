package tsp.simulatedannealing;

import java.util.ArrayList;
import java.util.Random;


public class NearestNeighbour {

    private DistanceMatrix distanceMatrix;

    public NearestNeighbour(DistanceMatrix distanceMatrix){
        this.distanceMatrix = distanceMatrix;
    }

    // Generate the NN tour from startNode start if != -1. Random otherwise.
    public Tour generateTour(int start) {

        Random r = new Random();
        Tour tour = new Tour(distanceMatrix);

        int citiesSize = distanceMatrix.getSize() - 1;

        // Passing always the first as 0
        if (start == -1) {
            start = r.nextInt(distanceMatrix.getSize());
        }

        start = start - 1;
        tour.push(start);

        while (citiesSize > 0) {
            int next = nearest(distanceMatrix, start, tour);
            tour.push(next);
            start = next;
            citiesSize--;
        }

        return tour;
    }

    // Determine nearest vertex to a given vertex. -1 if error.
    public static int nearest(DistanceMatrix d, int index, Tour t) {

        //Flag array for visited cities. 0 if unvisited, 1 if visited.
        int[] visited = new int[d.getSize()];
        int [] distances = d.getMatrix()[index];
        int nearest = -1;

        for(int j = 0; j < visited.length; j++){
            visited[j] = 0;
        }
        ArrayList<Integer> cities = t.getTour();
        for (Integer c : cities){
            visited[c] = 1;
        }

        //Linear scan of list for smallest item.
        for(int k = 0; k < distances.length; k++){
            if (nearest == -1){
                if (visited[k] == 0){
                    nearest = k;
                }
            }
            else if (distances[k] < distances[nearest]){
                if (visited[k] == 0){
                    nearest = k;
                }
            }
        }
        return nearest;
    }
}
