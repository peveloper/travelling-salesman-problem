package tsp;


public class TwoOpt {

    private Tour tour;
    private DistanceMatrix distanceMatrix;
    private int inputSize;

    public TwoOpt(Tour tour, DistanceMatrix distanceMatrix) {
        this.tour = tour;
        this.distanceMatrix = distanceMatrix;
    }

    public Tour generateTour() {
        Tour tempTour = new Tour(distanceMatrix);
        tempTour.addAll(tour.getTour());
        int tourSize = tour.getSize();
        inputSize = tourSize;
        double [][] distances = distanceMatrix.getMatrix();

        boolean swapped = true;

        while (swapped) {
            swapped = false;
            for (int i = 1; i < tourSize - 1; i++) {
                for (int j = i + 1; j < tourSize - 1; j++) {
                    double oldDistance = distances[tempTour.get(i-1)][tempTour.get(i)] +
                                         distances[tempTour.get(j)][tempTour.get(j+1)];
                    double newDistance = distances[tempTour.get(i-1)][tempTour.get(j)] +
                                         distances[tempTour.get(i)][tempTour.get(j+1)];
                    if (newDistance < oldDistance) {
                        tempTour.swapCities(i, j);
                        swapped = true;
                    }
                }
            }
        }

        int tempTourDistance = tempTour.getTotalDistance();
        if (tempTourDistance < tourSize) {
            tourSize = tempTourDistance;
            tour.getTour().set(0, tempTour.get(1));
            tour.getTour().set(inputSize, tempTour.get(0));
            for (int i = 1; i < inputSize - 1; i++) {
                tour.getTour().set(i, tempTour.get(i + 1));
            }
        }

        return tempTour;
    }



}
