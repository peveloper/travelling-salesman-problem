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

    public final void setDistanceTo(final City c){
        double dx = this.x - c.x;
        double dy = this.y - c.y;
        int d = (int) Math.round(Math.sqrt(dx*dx + dy*dy));
        distances[c.getId() - 1] = d;
    }

    public int getDistanceTo(final City c) {
        return distances[c.getId() - 1];
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
            City c = cities.get(i);
            int tmp = distances[i];
            if (min > tmp && !sol.contains(c) && c != nearest) {
                min = tmp;
                nearest = c;
            }
        }
        return nearest;
    }

}
