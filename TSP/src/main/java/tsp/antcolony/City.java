package tsp.antcolony;

import java.util.ArrayList;

public class City {
    private final int id;
    private final double x;
    private final double y;
    private int distances[];

    public City(final int id, final double x, final double y, final int citiesSize) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.distances = new int[citiesSize];
    }

    public final void setDistanceTo(final City city){
        double dx = this.x - city.x;
        double dy = this.y - city.y;
        int d = (int) Math.round(Math.sqrt(dx*dx + dy*dy));
        distances[city.getId() - 1] = d;
    }

    public int getDistanceTo(final City city) {
        return distances[city.getId() - 1];
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return "" + id;
    }

    public City getNearest(final TourInstance tour, final Tour sol) {
        final ArrayList<City> cities = tour.cities();
        City nearest = null;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i <cities.size(); i++) {
            City city = cities.get(i);
            int tmp = distances[i];
            if (min > tmp && !sol.contains(city) && city != nearest) {
                min = tmp;
                nearest = city;
            }
        }
        return nearest;
    }

}
