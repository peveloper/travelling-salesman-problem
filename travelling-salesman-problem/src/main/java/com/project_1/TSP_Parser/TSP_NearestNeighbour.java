package com.project_1.TSP_Parser;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by peveloper on 17/11/15.
 */
public class TSP_NearestNeighbour {

    private TSP_DistanceMatrix distanceMatrix;
    private ArrayList<TSP_Coordinate> input;

    public TSP_NearestNeighbour(TSP_DistanceMatrix distanceMatrix){
        this.distanceMatrix = distanceMatrix;
    }

    // Generate the NN tour
    public TSP_Tour generateTour(ArrayList<TSP_Coordinate> cities) {

        Random r = new Random();
        TSP_Tour tour = new TSP_Tour(distanceMatrix);
        input = cities;
        int citiesSize = input.size() - 1;

        int start = r.nextInt(citiesSize);
        start = 0;
        tour.add(start);
        System.out.println(start);

        while (citiesSize > 0) {
            int next = nearest(distanceMatrix, start, tour);
            tour.add(next);
            start = next;
            citiesSize--;
        }

        return tour;
    }

    // Determine nearest vertex to a given vertex. -1 if error.
    public int nearest(TSP_DistanceMatrix d, int index, TSP_Tour t) {

        //Flag array for visited cities. 0 if unvisited, 1 if visited.
        int[] visited = new int[input.size()];
        for(int j = 0; j < visited.length; j++){
            visited[j] = 0;
        }
        ArrayList<Integer> cities = t.getTour();
        for (Integer c : cities){
            visited[c] = 1;
        }

        //Shallow copy of distance matrix.
        double[] distances = d.getMatrix()[index];

        //Linear scan of list for smallest item.
        int nearest = -1;
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
