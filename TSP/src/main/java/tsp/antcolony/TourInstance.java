package tsp.antcolony;

import java.util.ArrayList;

public class TourInstance {

    private final int citiesSize;
    private final ArrayList<City> cities;
    private final int bestKnownSolution;

    public TourInstance(int citiesSize, int bestKnownSolution) {
        this.citiesSize = citiesSize;
        this.cities = new ArrayList<>(citiesSize);
        this.bestKnownSolution = bestKnownSolution;
    }

    public int size() {
        return citiesSize;
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
                if (c!=d) { c.setDistanceTo(d); d.setDistanceTo(c); }
    }

    public int getBestKnownSolution() {
        return bestKnownSolution;
    }

    public String toString() {
        return cities.toString();
    }


}
