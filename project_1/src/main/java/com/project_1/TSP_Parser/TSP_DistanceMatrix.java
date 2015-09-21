package com.project_1.TSP_Parser;

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

    public int getNextFreeCell() {
        int c =0;
        for(int x=0; x<dimension; x++) {
            for(int y=0; y<dimension; y++) {
                if ((matrix[x][y] == 0.0 || matrix[x][y] == 0.0) && x!=y)  {
                    return x + y + c;
                }
            }
            c += dimension;
        }
        return -1;
    }

    public void addCoordinate(double distance) {
        int c = 0;
        for(int x=0; x<dimension; x++) {
            for(int y=0; y<dimension; y++) {
                int nextFreeCell = getNextFreeCell();
                if (nextFreeCell != -1) {
                    if (x + y + c == nextFreeCell) {
                        matrix[x][y] = distance;
                        return;
                    }
                }
            }
            c += dimension;
        }
    }

    public void printMatrix() {
        int rows = 0;
        for(int x=0; x<dimension; x++) {
            for(int y=0; y<dimension; y++) {
                System.out.print(matrix[x][y] + ", ");
            }
            System.out.println("\n");
            rows++;
        }
        System.out.println(rows);
    }
}
