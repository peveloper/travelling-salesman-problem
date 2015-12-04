package tsp.antcolony;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.Random;

public class AntColony {
    private final int numOfAnts;
    private final int citiesSize;
    private final double[][] pheromoneMatrix;
    private final ArrayList<City> cities;
    private final ArrayList<Ant> ants;
    private final Problem problem;

    private double q0;
    private final int beta;
    private final double alpha;
    private final double ro;
    private final double tau0;

    private Ant bestSolutionEver;

    private final Random rnd;
    private final long now;
    final double TIME_LIMIT = 180.0;

    public AntColony(final Problem problem, final Tour tour, final int numOfAnts, final Random rnd,
                     final long now) {

        // Initialize parameters
        this.q0 = 0.9d;
        this.beta = 2;
        this.alpha = 0.1d;
        this.citiesSize = problem.size();
        this.tau0 = 1/new Double(tour.getTourLength() * citiesSize);
        this.ro = 0.1d;


        this.problem = problem;
        this.bestSolutionEver = null;
        this.pheromoneMatrix = new double[citiesSize][citiesSize];
        this.ants = new ArrayList<Ant>(numOfAnts);
        this.numOfAnts = numOfAnts;
        this.cities = problem.cities();

        this.rnd = rnd;
        this.now = now;
    }

    public final Tour optimize() {
        initPheromone();
        while((((System.nanoTime() - now) * Math.pow(10, -9)) < TIME_LIMIT) && ((bestSolutionEver == null) ||
                (bestSolutionEver.getTourLength() > problem.getOptimalSolution()))) {

            //Add new ants
            for (int i = 0; i < numOfAnts; i++) {
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
            TwoOpt.optimize(bestAnt, false);

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

    public void globalTrailUpdate(final Ant ant) {
        final double constant = 1/new Double(ant.getTourLength());
        City prev = ant.getLast();
        ListIterator<City> itr = ant.getTour().listIterator();
        while (itr.hasNext()) {
            City current = itr.next();
            final double currentTau = pheromoneMatrix[prev.getId() - 1][current.getId() - 1];
            double newTau = (1 - alpha) * currentTau + alpha * (constant);
            pheromoneMatrix[prev.getId() - 1][current.getId() - 1] = newTau;
            pheromoneMatrix[current.getId() - 1][prev.getId() - 1] = newTau;
            prev = current;
        }
    }

    private final void initPheromone() {
        for (int i = 0; i < citiesSize; i++) {
            Arrays.fill(pheromoneMatrix[i], tau0);
        }
    }

    public Tour getBestSolution() {
        return bestSolutionEver;
    }

    public double[][] getPheromoneTrails() {
        return pheromoneMatrix;
    }

    public int getNumOfCities() {
        return citiesSize;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public double getQ0() {
        return q0;
    }
    public int getBeta() {
        return beta;
    }
    public double getAlpha() {
        return alpha;
    }
    public double getRo() {
        return ro;
    }
    public double getTau0() {
        return tau0;
    }
    public Random getRandom() {
        return rnd;
    }
}
