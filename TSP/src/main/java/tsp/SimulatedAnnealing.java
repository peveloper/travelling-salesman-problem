package tsp;

import java.util.Random;

public class SimulatedAnnealing {

    private Tour tour;
    private DistanceMatrix distanceMatrix;


    public SimulatedAnnealing(Tour tour, DistanceMatrix distanceMatrix) {
        this.tour = tour;
        this.distanceMatrix = distanceMatrix;
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

        double temperature = 1.0e+6;
        double coolingRate = 0.99; //limit 0.00001

        Tour currentSolution = new Tour(distanceMatrix);
        currentSolution.addAll(tour.getTour());

        Tour bestSoFar = new Tour(distanceMatrix);
        bestSoFar.addAll(tour.getTour());

        Random r = new Random(175375829147462l);


        while (temperature > 1) {
            Tour newSolution = new Tour(distanceMatrix);
            newSolution.addAll(tour.getTour());

            int tourPos1 = r.nextInt(newSolution.getSize());
            int tourPos2 = r.nextInt(newSolution.getSize());

            if(tourPos1 == tourPos2) {
                tourPos2 = (tourPos2 + 1) % newSolution.getSize();
            }

            if(tourPos2 < tourPos1) {
                int temp = tourPos2;
                tourPos2 = tourPos1;
                tourPos1 = temp;
            }

            int citySwap1 = newSolution.get(tourPos1);
            int citySwap2 = newSolution.get(tourPos2);

            newSolution.getTour().set(tourPos2, citySwap1);
            newSolution.getTour().set(tourPos1, citySwap2);

            int currentEnergy = tour.getTotalDistance();
            int neighbourEnergy = newSolution.getTotalDistance();


            int diff = neighbourEnergy - currentEnergy;

            if (diff < 0 || acceptanceProbability(currentEnergy, neighbourEnergy, temperature) > Math.random())

            if (acceptanceProbability(currentEnergy, neighbourEnergy, temperature) > Math.random()) {
                currentSolution = new Tour(distanceMatrix);
                currentSolution.addAll(newSolution.getTour());
                currentSolution.getTotalDistance();
            }

            // Keep track of the best solution found
            if (currentSolution.getTotalDistance() < bestSoFar.getTotalDistance()) {
                bestSoFar = new Tour(distanceMatrix);
                bestSoFar.addAll(currentSolution.getTour());
            }

            temperature *= 1-coolingRate;

        }


        return bestSoFar;
    }
}
