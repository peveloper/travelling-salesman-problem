package tsp.antcolony;

import java.util.ArrayList;
import java.util.Random;

public class Ant extends Tour {
    private final AntColony colony;
    private final double taus[][];
    private ArrayList<City> citiesToVisit;
    private final Random rnd;
    private final int size;

    public Ant(final AntColony colony) {
        super();
        this.rnd = colony.getRandom();

        this.colony = colony;
        this.taus = colony.getPheromoneTrails();
        this.size = colony.getNumOfCities();

        this.citiesToVisit = new ArrayList<City>(colony.getCities());
        moveTo(citiesToVisit.get(rnd.nextInt(size)), true);
    }

    public void moveTo(final City city, boolean start) {
        if (!start) {
            localUpdate(route.getLast(), city);
            tourLength += route.getLast().dist(city);
        }
        route.add(city);
        citiesToVisit.remove(city);
        if (citiesToVisit.isEmpty()) {
            tourLength += city.dist(route.getFirst());
            localUpdate(city,route.getFirst());
        }
    }

    private final void localUpdate(final City current, final City city) {
        double newTau = (1 - colony.getRo()) * getPheromoneTrail(current,city) + colony.getRo() * colony.getTau0();
        taus[current.getId() - 1][city.getId() - 1] = newTau;
        taus[city.getId() - 1][current.getId() - 1] = newTau;
    }

    /**
        Decides the nextCity to which an ant will move to according to a probabilistic formula:
        @variable tau(r,u) represent the amount of pheromone trail on edge r,u
        @variable eta(r,u) represent the heuristic function which is the inverse of the distance from r to u.
     */

    public City nextCity() {
        City max = null;

        if (rnd.nextDouble() <= colony.getQ0()) {
            double last = -1;
            max = citiesToVisit.get(0);

            for (final City city : citiesToVisit) {
                double tmp = (getPheromoneTrail(route.getLast(), city)
                             * Math.pow(getHeuristic(route.getLast(), city), colony.getBeta()));
                if (tmp > last) {
                    last = tmp;
                    max = city;
                }
            }

        } else {
            double sum = 0;
            double params[] = new double[citiesToVisit.size()];

            for (int i = 0; i < citiesToVisit.size(); i++) {
                params[i] = (getPheromoneTrail(route.getLast(), citiesToVisit.get(i))
                            * Math.pow(getHeuristic(route.getLast(),citiesToVisit.get(i)), colony.getBeta()));
                sum += params[i];
            }

            int i = 0;
            final double rand = rnd.nextDouble();
            double p = 0;
            max = citiesToVisit.get(params.length-1);

            while (i < params.length && rand >= (p += params[i] / sum)) {
                max = citiesToVisit.get(++i);
            }
        }
        return max;
    }

    private double getHeuristic(final City a, final City b) {
        return 1/new Double(a.dist(b));
    }

    private double getPheromoneTrail(final City a, final City b) {
        return taus[a.getId() - 1][b.getId() - 1];
    }

}
