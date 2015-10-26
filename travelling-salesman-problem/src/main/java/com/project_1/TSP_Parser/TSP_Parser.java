package com.project_1.TSP_Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by peveloper on 17/09/15.
 */

public class TSP_Parser {

    public static void main(String args[]) {
        TSP_Parser reader = new TSP_Parser();
        reader.run();
    }

    public String setPrecision(String stringVal, int precision) {
        final BigDecimal value = new BigDecimal(stringVal);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(precision);
        df.setMinimumFractionDigits(precision);
        return df.format(value);
    }

    public void run() {

        BufferedReader br = null;
        String inputFile = "../ch130.tsp";
        String outputFile = "distance_matrix.txt";

        try {

            String currentLine;
            int startingLine = 0;
            int identifier = 0;
            ArrayList<TSP_Coordinate> coordinates = new ArrayList<TSP_Coordinate>();

            br = new BufferedReader(new FileReader(inputFile));

            while ((currentLine = br.readLine()) != null) {
                if (startingLine >= 6 && !currentLine.equals("EOF")) { // First coordinate at line #6
                    coordinates.add(new TSP_Coordinate(Double.parseDouble(currentLine.split(" ")[1]),
                            Double.parseDouble(currentLine.split(" ")[2]), identifier));
                }
                identifier ++;
                startingLine++;
            }

            TSP_DistanceMatrix distanceMatrix = new TSP_DistanceMatrix(coordinates.size());
            distanceMatrix.initMatrix();
            int precision = 4;

            for (int x=0; x < coordinates.size(); x++) {
                for (int y=0; y< coordinates.size(); y++) {
                    double distance = Math.sqrt(
                            Math.pow((coordinates.get(x).getX() - coordinates.get(y).getX()), 2) +
                            Math.pow((coordinates.get(x).getY() - coordinates.get(y).getY()), 2));
                    distance = Double.parseDouble(setPrecision(Double.toString(distance), precision));
                    distanceMatrix.addDistance(distance, x, y);
                }
            }



            PrintWriter writer = new PrintWriter(outputFile, "UTF-8");  // Write Matrix into a txt file
            distanceMatrix.printMatrixToFile(writer);
            writer.close();

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
