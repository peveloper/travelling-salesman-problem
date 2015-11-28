package tsp;


public class TwoOpt {

    private Tour tour;
    private DistanceMatrix distanceMatrix;

    public TwoOpt(Tour tour, DistanceMatrix distanceMatrix) {
        this.tour = tour;
        this.distanceMatrix = distanceMatrix;
    }

    public Tour generateTour() {
        Tour tempTour = new Tour(distanceMatrix);
        tempTour.addAll(tour.getTour());

        return tempTour;
    }

    public void swapCities(int[] cities, int i, int j) {
    }
}
