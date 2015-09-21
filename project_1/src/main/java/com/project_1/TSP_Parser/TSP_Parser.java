package com.project_1.TSP_Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by peveloper on 17/09/15.
 */

public class TSP_Parser {

    public static void main(String args[]) {
        TSP_Parser reader = new TSP_Parser();
        reader.run();
    }

    public void run() {

        BufferedReader br = null;

        try {

            String currentLine;
            int startingLine = 0;
            ArrayList<TSP_Coordinate> coordinates = new ArrayList<TSP_Coordinate>();

            br = new BufferedReader(new FileReader("/Users/peveloper/Documents/usi/5ths/Artificial " +
                    "Intelligence/artificial-intelligence/ch130.tsp"));

            while ((currentLine = br.readLine()) != null) {
                if (startingLine >= 6 && !currentLine.equals("EOF")) { //First coordinate at line #6
                    coordinates.add(new TSP_Coordinate(Double.parseDouble(currentLine.split(" ")[1]),
                            Double.parseDouble(currentLine.split(" ")[2])));
                }

                startingLine++;
            }

            for (TSP_Coordinate coordinate : coordinates) {
                System.out.println("x: " + coordinate.getX() + ", y: " + coordinate.getY());
            }

            TSP_DistanceMatrix distanceMatrix = new TSP_DistanceMatrix(coordinates.size());
            distanceMatrix.initMatrix();

            for (int x=0; x < coordinates.size(); x++) {
                for (int y=x+1; y< coordinates.size(); y++) {
                    double distance = Math.sqrt(
                            Math.pow((coordinates.get(x).getX() - coordinates.get(y).getX()), 2) +
                            Math.pow((coordinates.get(x).getY() - coordinates.get(y).getY()), 2));
                    distanceMatrix.addCoordinate(distance);
                }
            }

            distanceMatrix.printMatrix();




        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
