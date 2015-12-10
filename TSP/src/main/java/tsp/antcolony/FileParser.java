package tsp.antcolony;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileParser {

    private final String inputFile;
    private final int bestKnownResult;
    private TourInstance tourInstance;

    public FileParser(String inputFile, int bestKnownResult) {
        this.inputFile = inputFile;
        this.bestKnownResult = bestKnownResult;
    }

    public TourInstance generateTourInstance() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("../problems/" + inputFile));

        String currentLine;
        while (!(currentLine = in.readLine()).contains("DIMENSION")) {}
        String[] dimension = currentLine.split(":");
        int citiesSize = Integer.parseInt(dimension[dimension.length - 1].trim());

        tourInstance = new TourInstance(citiesSize, bestKnownResult);

        while (!(currentLine = in.readLine()).contains("EOF")) {
            if (Character.isDigit(currentLine.charAt(0))) {
                String [] splittedLine = currentLine.split(" ");
                int id = Integer.parseInt(splittedLine[0]);
                double x = Double.parseDouble(splittedLine[1]);
                double y = Double.parseDouble(splittedLine[2]);
                City city = new City(id, x, y, citiesSize);
                tourInstance.add(city);
            }
        }

        return tourInstance;
    }
}
