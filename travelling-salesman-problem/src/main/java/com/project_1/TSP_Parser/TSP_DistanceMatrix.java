package com.project_1.TSP_Parser;

import java.io.PrintWriter;

/**
 * Created by peveloper on 17/09/15.
 */

public class TSP_DistanceMatrix {

    private int dimension;
    private double[][] matrix;

    public TSP_DistanceMatrix(int dimension){
        this.dimension = dimension;
        this.matrix = new double[dimension][dimension];

    }

    public void initMatrix() {
        for(int x=0; x<dimension; x++) {
            for(int y=0; y<dimension; y++) {
                matrix[x][y] = 0.0;
            }
        }
    }

    public double getDistance(int x, int y) {
        return matrix[x][y];
    }

    public void addDistance(double distance, int x, int y) {
        if (x != y) {
            matrix[x][y] = distance;
        }
    }

    public void printMatrixToFile(PrintWriter writer) {
        for(int x=0; x<dimension; x++) {
            for(int y=0; y<dimension; y++) {
                writer.print(matrix[x][y] + " ");
            }
            writer.print("\n");
        }
    }

    public double[][] getMatrix() {
        return matrix;
    }
}
