package tsp.antcolony;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Random;

public class Main {

    private static String inputFile;
    private static String resultsDirectory;

    public static void main(String args[]) {
        TourInstance tourInstance;
        Random random;
        String filePath;
        String output = "";

        // default values if 1st argument is not passed
        int bestKnownResult = 6110;
        long seed = System.currentTimeMillis();
        boolean firstGain = false;

        if (args.length > 0) {
            filePath = args[0];
        } else {
            filePath = "../problems/ch130.tsp";
        }

        inputFile = filePath.split("/")[filePath.split("/").length - 1];

        JSONParser jsonParser = new JSONParser();

        try {

            Object configObject = jsonParser.parse(new FileReader("src/main/resources/config.json"));
            JSONObject config = (JSONObject) configObject;
            resultsDirectory = (String) config.get("path_results");
            JSONObject problems = (JSONObject) config.get("problems");
            for(Object k : problems.keySet()) {
                JSONArray p = (JSONArray) problems.get(k);
                if (p.get(0).equals(inputFile)) {
                    bestKnownResult = Integer.parseInt((String) p.get(1));
                    seed = Long.parseLong((String) p.get(2));
                    firstGain = Boolean.parseBoolean((String) p.get(3));
                }
            }

                tourInstance = new FileParser(inputFile, bestKnownResult).generateTourInstance();
                tourInstance.updateDistances();
                long start = System.nanoTime();
                random = new Random(seed);
                System.out.println("Solving " + inputFile + "...\n");
                Tour initialTour = NearestNeighbour.improveTour(tourInstance, 1);
                Tour finalTour = new AntColony(tourInstance, initialTour, 10, random, start, firstGain).improveTour();
                retrieveResult(finalTour);
                output += "Total elapsed time: " +
                        Math.round((System.nanoTime() - start) * Math.pow(10, -6)) + " ms\n" +
                        "Results can be found at " +
                        resultsDirectory + inputFile.split("\\.")[0] + "/" + inputFile.split("\\.")[0] + ".txt";
                double error = ((double) (finalTour.getTourLength() - bestKnownResult)/(double) bestKnownResult) * 100;

                System.out.println(error + "\n" + seed);


        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void retrieveResult(Tour finalTour) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer;
        writer = new PrintWriter(resultsDirectory + inputFile.split("\\.")[0] + "/" + inputFile.split("\\.")[0] + ".txt", "UTF-8");
        writer.println("Solution for TSP problem on " +  inputFile + "\n");
        writer.println(finalTour);
        writer.close();
    }
}

