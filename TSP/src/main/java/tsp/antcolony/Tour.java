package tsp.antcolony;

import java.util.ArrayList;

public class Tour {

    protected final ArrayList<City> route;
    protected int tourLength;

    public Tour() {
        route = new ArrayList<City>();
        tourLength = 0;
    }

    public ArrayList<City> getTour() {
        return route;
    }

    public void addCity(final City c) {
        route.add(c);
    }

    public City getFirst() {
        return route.get(0);
    }

    public City getLast() {
        return route.get(route.size() - 1);
    }

    public String toString() {
        String buffer = "";
        for (final City c : route) {
            buffer += c.toString() + "\n";
        }
        buffer += "\nTour length: " + tourLength;
        return buffer;
    }

    public int getTourLength() {
        return tourLength;
    }

    public void setTourLength(final int tourLength) {
        this.tourLength = tourLength;
    }

    public boolean contains(final City c) {
        return route.contains(c);
    }


}