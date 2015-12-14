package tsp.antcolony;

import java.util.ArrayList;
import java.util.Random;

public class Ant extends Tour {

    private final AntColony colony;
    private ArrayList<City> citiesToVisit;
    private final double taus[][];
    private final Random random;
    private final int size;

    public Ant(final AntColony colony) {
        super();
        this.random = colony.getRandom();

        this.colony = colony;
        this.taus = colony.getPheromoneTrails();
        this.size = colony.getCitiesSize();

        this.citiesToVisit = new ArrayList<City>(colony.getCities());
        moveTo(citiesToVisit.get(random.nextInt(size)), true);
    }

    public void moveTo(final City city, boolean start) {
        if (!start) {
            localUpdate(route.get(route.size() - 1), city);
            tourLength += route.get(route.size() - 1).getDistanceTo(city);
        }
        route.add(city);
        citiesToVisit.remove(city);
        if (citiesToVisit.isEmpty()) {
            tourLength += city.getDistanceTo(route.get(0));
            localUpdate(city,route.get(0));
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
        City max;

        if (random.nextDouble() <= colony.getQ0()) {
            double last = -1;
            max = citiesToVisit.get(0);

            for (final City city : citiesToVisit) {
                double tmp = (getPheromoneTrail(route.get(route.size() - 1), city)
                             * Math.pow(getHeuristic(route.get(route.size() - 1), city), colony.getBeta()));
                if (tmp > last) {
                    last = tmp;
                    max = city;
                }
            }

        } else {
            double sum = 0;
            double params[] = new double[citiesToVisit.size()];

            for (int i = 0; i < citiesToVisit.size(); i++) {
                params[i] = (getPheromoneTrail(route.get(route.size() - 1), citiesToVisit.get(i))
                            * Math.pow(getHeuristic(route.get(route.size() - 1), citiesToVisit.get(i)), colony.getBeta()));
                sum += params[i];
            }

            int i = 0;
            final double rand = random.nextDouble();
            double p = 0;
            max = citiesToVisit.get(params.length-1);

            while (i < params.length && rand >= (p += params[i] / sum)) {
                max = citiesToVisit.get(++i);
            }
        }
        return max;
    }

    private double getHeuristic(final City i, final City j) {
        return 1/((double) (i.getDistanceTo(j)));
    }

    private double getPheromoneTrail(final City i, final City j) {
        return taus[i.getId() - 1][j.getId() - 1];
    }

}
