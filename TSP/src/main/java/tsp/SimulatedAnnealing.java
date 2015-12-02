package tsp;

import java.util.Random;

public class SimulatedAnnealing {

    private Tour tour;
    private DistanceMatrix distanceMatrix;
    private TwoOpt twoOpt;
    private Random r;
    private final double TIME_LIMIT = 180.0;

    private double temperature = 0.2;
    private final double coolingRate = 0.95;



    public SimulatedAnnealing(Tour tour, DistanceMatrix distanceMatrix) {
        this.tour = tour;
        this.distanceMatrix = distanceMatrix;
        r = new Random(179142946891462l);
        twoOpt = new TwoOpt(tour, distanceMatrix);
    }

    public static double acceptanceProbability(int energy, int newEnergy, double temperature) {
        // If the new solution is better, accept it
        if (newEnergy < energy) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp(-(newEnergy - energy) / temperature);
    }

    public Tour generateTour() {
        long now = System.nanoTime();

        twoOpt.setTour(tour);

        Tour currentSolution = twoOpt.generateTour(false);

        Tour bestSoFar = new Tour(distanceMatrix);
        bestSoFar.addAll(tour.getTour());

        int bestDistance = bestSoFar.getTotalDistance();
        int currentDistance = bestDistance;

        while (true) {

            System.out.println("Temperature: " + temperature + " BestSoFar: " + bestDistance
                             + " Current: " + currentDistance);

            if (((System.nanoTime()) - now) * Math.pow(10, -9) > TIME_LIMIT) {
                break;
            }

            int i = 0;
            while( i < 50 * currentSolution.getSize()) {

                Tour newSolution = new Tour(distanceMatrix);
                newSolution.addAll(tour.getTour());

                int tourPos1 = r.nextInt(newSolution.getSize());
                int tourPos2 = r.nextInt(newSolution.getSize());

                if (tourPos1 == tourPos2) {
                    tourPos2 = (tourPos2 + 1) % newSolution.getSize();
                }

                if (tourPos2 < tourPos1) {
                    int temp = tourPos2;
                    tourPos2 = tourPos1;
                    tourPos1 = temp;
                }

                int delta = twoOpt.computeGain(tourPos1, tourPos2);

                if (delta < 0) {
                    twoOpt.swap(tourPos1, tourPos2);
                    currentDistance = currentSolution.getTotalDistance();
                    if (currentDistance < bestDistance) {
                        bestSoFar = new Tour(distanceMatrix);
                        bestSoFar.addAll(currentSolution.getTour());
                        bestDistance = currentDistance;
                    }
                } else {
                    double newRandom = r.nextInt(101)/100.0;

                    if (Math.exp(-delta/temperature) > newRandom) {
                        twoOpt.swap(tourPos1, tourPos2);
                        currentDistance = currentSolution.getTotalDistance();
                    }

                }

                i++;
            }

            temperature = coolingRate * temperature;
        }

        twoOpt.setTour(bestSoFar);

        twoOpt.generateTour(false);

        tour = twoOpt.getTour();

        return tour;
    }
}
