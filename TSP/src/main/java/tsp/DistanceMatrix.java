package tsp;

import java.io.PrintWriter;


public class DistanceMatrix {

    private int dimension;
    private int [][] matrix;

    public DistanceMatrix(int dimension){
        this.dimension = dimension;
        this.matrix = new int [dimension][dimension];
        this.initMatrix();

    }

    public void initMatrix() {
        for(int x=0; x<dimension; x++) {
            for(int y=0; y<dimension; y++) {
                matrix[x][y] = 0;
            }
        }
    }

    public int getDistance(int x, int y) {
        return matrix[x][y];
    }

    public void addDistance(int distance, int x, int y) {
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

    public int[][] getMatrix() {
        return matrix;
    }

    public int getSize() {
        return dimension;
    }

}
