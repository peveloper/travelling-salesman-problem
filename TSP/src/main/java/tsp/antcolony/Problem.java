package tsp.antcolony;

import java.util.ArrayList;

public class Problem {
    private final int num_of_cities;
    private final ArrayList<City> cities;
    private final int optimalSolution;

    public Problem(int num_of_cities, int optimalSolution) {
        this.num_of_cities = num_of_cities;
        this.cities = new ArrayList<City>(num_of_cities);
        this.optimalSolution = optimalSolution;
    }

    public int size() {
        return num_of_cities;
    }

    public ArrayList<City> cities() {
        return cities;
    }

    public City getCity(final int id) {
        return cities.get(id-1);
    }

    public void add(final City city) {
        cities.add(city);
    }

    public void updateDistances() {
        for (final City c : cities)
            for (final City d : cities)
                if (!c.knows(d)) { c.setDist(d); d.setDist(c); }
    }

    public String toString() {
        return cities.toString();
    }

    public int getOptimalSolution() {
        return optimalSolution;
    }
}
