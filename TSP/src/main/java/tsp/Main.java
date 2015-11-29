package tsp;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import java.util.*;

public class Main {

    private static HashMap<String, Object> problemInstances = new HashMap<String, Object>();
    private static HashMap<String, Tour> solutionInstances = new HashMap<String, Tour>();
    private static String inputFile;
    private static String resultsDirectory;
    private static DistanceMatrix distanceMatrix;


    public static Tour solve(String algorithm) {
        return (Tour) problemInstances.get(algorithm);
    }

    public static void printMatrixToFile(DistanceMatrix distanceMatrix) {
        PrintWriter distanceMatrixWriter = null;
        try {
            distanceMatrixWriter = new PrintWriter(resultsDirectory + "DistanceMatrix.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        distanceMatrix.printMatrixToFile(distanceMatrixWriter);
        distanceMatrixWriter.close();
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        String filePath = args[0];
        inputFile = filePath.split("/")[filePath.split("/").length-1];
        FileParser fileParser = new FileParser(filePath);
        distanceMatrix = fileParser.parseTSPFile();
        problemInstances.put("NearestNeighbour", new NearestNeighbour(distanceMatrix).generateTour(1));
        problemInstances.put("FarthestInsertion", new FarthestInsertion(distanceMatrix).generateTour());
        problemInstances.put("TwoOpt", new TwoOpt(solve("FarthestInsertion"), distanceMatrix).generateTour());

        JSONParser jsonParser = new JSONParser();

        try {

            Object configObject = jsonParser.parse(new FileReader("src/main/resources/config.json"));
            JSONObject config = (JSONObject) configObject;
            resultsDirectory = (String) config.get("path_results");
            printMatrixToFile(distanceMatrix);
            JSONArray algorithms = (JSONArray) config.get("algorithms");
            JSONArray problems = (JSONArray) config.get("problems");
            ArrayList<String> resultFiles = new ArrayList<String>();

            Iterator<String> problemsIterator = problems.iterator();
            while (problemsIterator.hasNext()) {
                String problem = problemsIterator.next();
                if(problem.equals(inputFile)) {
                    Iterator<String> algorithmsIterator = algorithms.iterator();
                    while (algorithmsIterator.hasNext()) {
                        String algorithm = algorithmsIterator.next();
                        resultFiles.add(algorithm);
                        solutionInstances.put(algorithm, solve(algorithm));
                    }
                }
            }

            PrintWriter writer = null;

            for(String algorithm : solutionInstances.keySet()) {
                try {
                    writer = new PrintWriter(resultsDirectory + inputFile.split("\\.")[0] + "/" + algorithm + ".txt", "UTF-8");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                writer.println(algorithm + " solution for TSP problem on " +  inputFile + "\n");
                writer.println(solutionInstances.get(algorithm).toString());
                writer.close();
            }

            long elapsedTime = System.currentTimeMillis() - start;
            System.out.println("Elapsed Running Time: " + elapsedTime + " ms.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
