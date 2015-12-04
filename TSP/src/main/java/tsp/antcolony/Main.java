package tsp.antcolony;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {


    private static String inputFile;
    private static String resultsDirectory;

    public static void main(String args[]) {
        Problem problem;
        Random rnd;
        String filePath;
        int probNum = 0;
        int bestKnownResult = 6110;

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
                    probNum = Integer.parseInt((String) k);
                    bestKnownResult = Integer.parseInt((String) p.get(1));
                }
            }

            try {
                BufferedReader in = new BufferedReader(new FileReader("../problems/" + inputFile));

                String line;
                while (!(line = in.readLine()).contains("DIMENSION")) {
                }
                String[] inta = line.split(":");
                int num_of_cities = Integer.parseInt(inta[inta.length - 1].trim());
                problem = new Problem(num_of_cities, bestKnownResult);

                Pattern patternLine = Pattern.compile("(\\d+) (.+?) (.+?)$");
                while (!(line = in.readLine()).contains("EOF")) {
                    Matcher regex = patternLine.matcher(line);
                    if (regex.find()) {
                        Integer id = new Integer(regex.group(1));
                        Double x = new Double(regex.group(2));
                        Double y = new Double(regex.group(3));
                        City city = new City(id, x, y, num_of_cities);
                        problem.add(city);
                    }
                }
                problem.updateDistances();
                in.close();

                long start = System.nanoTime();

                long seed;

                seed = System.currentTimeMillis();
                System.out.println("Solving problem: " + inputFile);
                rnd = new Random(seed);

                Tour initialTour = NearestNeighbour.getSolution(problem, 1);

                AntColony colony;

                colony = new AntColony(problem, initialTour, 10, rnd, start);

                Tour finalTour = colony.optimize();

                System.out.println("Total elapsed time: " +
                        Math.round((System.nanoTime() - start) * Math.pow(10, -6)) + " ms");

                System.out.println("Solved problem: "+ inputFile + " using seed: " + seed +
                                    ". Tour length: " + finalTour.getTourLength());

                System.out.println("Tour: " + finalTour);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

