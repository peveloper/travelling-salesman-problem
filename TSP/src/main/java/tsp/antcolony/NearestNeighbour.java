package tsp.antcolony;

public class NearestNeighbour {

    public static Tour improveTour (final TourInstance tourInstance, final int starting) {

        final Tour tour = new Tour();
        int tourLength = 0;

        tour.addCity(tourInstance.getCity(starting));

        for (int i = 0; i < tourInstance.size() - 1; i++) {
            final City nearest = tour.getLast().getNearest(tourInstance, tour);
            tourLength += tour.getLast().getDistanceTo(nearest);
            tour.addCity(nearest);
        }

        tourLength += tour.getLast().getDistanceTo(tour.getFirst());
        tour.setTourLength(tourLength);

        return tour;
    }

}