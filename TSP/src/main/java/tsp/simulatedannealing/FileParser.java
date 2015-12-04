package tsp.simulatedannealing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;


public class FileParser {

    private String inputFile;
    private DistanceMatrix distanceMatrix;

    public FileParser(String inputFile) {
        this.inputFile = inputFile;
    }

    public DistanceMatrix parseTSPFile() {

        BufferedReader br = null;

        try {

            String currentLine;
            int id = 0;
            ArrayList<City> coordinates = new ArrayList<City>();
            br = new BufferedReader(new FileReader(inputFile));

            // TODO-FIX try to search for the 1st digit instead of verify that the 1st is a digit

            while ((currentLine = br.readLine()) != null) {
                if (Character.isDigit(currentLine.charAt(0)) && !currentLine.equals("EOF")) {
                    coordinates.add(new City(
                                    Double.parseDouble(currentLine.split(" ")[1]),
                                    Double.parseDouble(currentLine.split(" ")[2]),
                                    id)
                    );
                }
                id ++;
            }

            distanceMatrix = new DistanceMatrix(coordinates.size());

            for (int x=0; x < coordinates.size(); x++) {
                for (int y=0; y< coordinates.size(); y++) {
                    int distance = (int) Math.round(
                            Math.sqrt(Math.pow((coordinates.get(x).getX() - coordinates.get(y).getX()), 2) +
                                      Math.pow((coordinates.get(x).getY() - coordinates.get(y).getY()), 2)
                            ));

                    distanceMatrix.addDistance(distance, x, y);
                }
            }

            return distanceMatrix;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return distanceMatrix;
    }
}
