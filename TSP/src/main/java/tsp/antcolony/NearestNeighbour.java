package tsp.antcolony;

public class NearestNeighbour {

    public final static Tour getSolution(final Problem problem, final int starting) {
        final Tour tour = new Tour();
        final int size = problem.size();
        int tourLength = 0;
        tour.addCity(problem.getCity(starting));
        for (int i = 0; i<size-1; i++) {
            final City nearest = tour.getLast().getNearest(problem, tour);
            tourLength += tour.getLast().dist(nearest);
            tour.addCity(nearest);
        }
        tourLength += tour.getLast().dist(tour.getFirst());
        tour.setTourLength(tourLength);
        return tour;
    }

}
