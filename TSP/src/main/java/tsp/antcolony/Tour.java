package tsp.antcolony;

import java.util.LinkedList;

public class Tour {
    protected final LinkedList<City> route;
    protected int tourLength;

    public Tour() {
        route = new LinkedList<City>();
        tourLength = 0;
    }

    public LinkedList<City> getTour() {
        return route;
    }

    public void addCity(final City c) {
        route.add(c);
    }

    public City getFirst() {
        return route.getFirst();
    }

    public City getLast() {
        return route.getLast();
    }

    public String toString() {
        String buffer = "[";
        for (final City c : route) {
            buffer += c.toString()+" ";
        }
        return buffer + route.getFirst() + "]";
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
