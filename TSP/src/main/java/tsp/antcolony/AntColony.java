package tsp.antcolony;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.Random;

public class AntColony {

    private final int colonySize;
    private final int citiesSize;
    private final double[][] pheromoneMatrix;
    private final ArrayList<City> cities;
    private final ArrayList<Ant> ants;
    private final TourInstance tourInstance;
    private final boolean firstGain;
    private Ant bestSolutionEver;


    private double q0;
    private final double alpha;
    private final int beta;
    private final double ro;
    private final double tau0;

    private final Random random;
    private final long now;
    private final double TIME_LIMIT = 180.0;

    public AntColony(final TourInstance tourInstance, final Tour tour, final int colonySize,
                     final Random random, long now, boolean firsGain) {

        // Initialize parameters
        this.q0 = 0.9d;
        this.beta = 2;
        this.alpha = 0.1d;
        this.citiesSize = tourInstance.size();
        this.tau0 = 1 / new Double(tour.getTourLength() * citiesSize);
        this.ro = 0.1d;

        this.tourInstance = tourInstance;
        this.pheromoneMatrix = new double[citiesSize][citiesSize];
        this.ants = new ArrayList<>(colonySize);
        this.colonySize = colonySize;
        this.cities = tourInstance.cities();
        this.random = random;
        this.now = now;
        this.firstGain = firsGain;
    }

    public double getQ0() {
        return q0;
    }

    public int getBeta() {
        return beta;
    }

    public double getRo() {
        return ro;
    }

    public double getTau0() {
        return tau0;
    }

    public Random getRandom() {
        return random;
    }

    private final void initializePheromone() {
        for (int i = 0; i < citiesSize; i++) {
            Arrays.fill(pheromoneMatrix[i], tau0);
        }
    }

    public void globalTrailUpdate(final Ant ant) {
        final double constant = 1/new Double(ant.getTourLength());
        City previous = ant.getLast();
        ListIterator<City> itr = ant.getTour().listIterator();
        while (itr.hasNext()) {
            City current = itr.next();
            final double currentTau = pheromoneMatrix[previous.getId() - 1][current.getId() - 1];
            double newTau = (1 - alpha) * currentTau + alpha * (constant);
            pheromoneMatrix[previous.getId() - 1][current.getId() - 1] = newTau;
            pheromoneMatrix[current.getId() - 1][previous.getId() - 1] = newTau;
            previous = current;
        }
    }

    public double[][] getPheromoneTrails() {
        return pheromoneMatrix;
    }

    public int getCitiesSize() {
        return citiesSize;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public final Tour improveTour() {
        initializePheromone();
        while((((System.nanoTime() - now) * Math.pow(10, -9)) < TIME_LIMIT) && ((bestSolutionEver == null) ||
                (bestSolutionEver.getTourLength() > tourInstance.getBestKnownSolution()))) {

            //Add new ants
            for (int i = 0; i < colonySize; i++) {
                ants.add(new Ant(this));
            }

            //Move all ants through each city
            for (int i = 0; i < citiesSize - 1; i++) {
                for (final Ant ant : ants) {
                    final City next = ant.nextCity();
                    ant.moveTo(next, false);
                }
            }

            //Find local best Ant
            int minL = ants.get(0).getTourLength();
            Ant bestAnt = ants.get(0);
            for (final Ant ant : ants) {
                int tmp = ant.getTourLength();
                if (tmp <= minL) {
                    bestAnt = ant;
                    minL = tmp;
                }
            }

            //2OPT on the best local ant
            TwoOpt.improveTour(bestAnt, firstGain);

            //Update only best ever ant pheromone
            if (bestSolutionEver == null) globalTrailUpdate(bestAnt);
            else globalTrailUpdate(bestSolutionEver);

            //If the best ant has found a better solution that the best ever, update the best solution ever
            if (bestSolutionEver == null || bestAnt.getTourLength() <= bestSolutionEver.getTourLength()) {
                bestSolutionEver = bestAnt;

                System.out.println("Best distance so far: " + bestSolutionEver.getTourLength());
            }

            ants.clear();
        }
        return bestSolutionEver;
    }

}
