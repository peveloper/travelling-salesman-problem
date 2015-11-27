package tsp;

import java.io.PrintWriter;


public class DistanceMatrix {

    private int dimension;
    private double[][] matrix;

    public DistanceMatrix(int dimension){
        this.dimension = dimension;
        this.matrix = new double[dimension][dimension];
        this.initMatrix();

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

    public int getSize() {
        return dimension;
    }

}
