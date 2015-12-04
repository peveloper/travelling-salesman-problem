package tsp.simulatedannealing;


public class TwoOpt {

    private Tour tour;
    private DistanceMatrix distanceMatrix;

    public TwoOpt(Tour tour, DistanceMatrix distanceMatrix) {
        this.tour = tour;
        this.distanceMatrix = distanceMatrix;
    }

    public Tour generateTour(boolean firstImprovement) {

        int bestGain = Integer.MAX_VALUE;
        int bestI = Integer.MAX_VALUE;
        int bestJ = Integer.MAX_VALUE;

        while(bestGain > 0) {
            bestGain = 0;

            for(int i = 0; i < tour.getSize() - 1; i++) {
                for(int j = i+1; j < tour.getSize(); j++) {
                    if(i!=j) {
                        int gain = computeGain(i, j);

                        if(gain < bestGain) {
                            bestGain = gain;
                            bestI = i;
                            bestJ = j;
                            if(firstImprovement == true) {
                                break;
                            }

                            swap(bestI, bestJ);
                        }
                    }
                }
                if(firstImprovement == true) {
                    break;
                }
            }

            if(firstImprovement == true && bestI != Integer.MAX_VALUE && bestJ != Integer.MAX_VALUE) {
                swap(bestI, bestJ);
            }
        }

        return tour;
    }

    public int computeGain(final int cityIndex1, final int cityIndex2) {

        int src1 = tour.get(cityIndex1);
        int src2 = tour.get(cityIndex2);

        int dest1 = tour.get((cityIndex1 + 1) % tour.getSize());
        int dest2 = tour.get((cityIndex2 + 1) % tour.getSize());

        return ((distanceMatrix.getMatrix()[src1][src2] + distanceMatrix.getMatrix()[dest1][dest2]) -
                (distanceMatrix.getMatrix()[src1][dest1] + distanceMatrix.getMatrix()[src2][dest2]));
    }

    public void swap(final int cityIndex1, final int cityIndex2) {
//        long swap = System.nanoTime();

        int indexDest1 = (cityIndex1 + 1) % tour.getSize();
        int indexDest2 = (cityIndex2 + 1) % tour.getSize();

        Tour pathNew = new Tour(distanceMatrix);
        pathNew.initTour();

        int indexOfPathNew = 0;

        // TODO change into linkedList implementation
        int i = 0;
        while(i <= cityIndex1) {
            if (!pathNew.getTour().contains(tour.get(i))) {
                pathNew.getTour().set(indexOfPathNew, tour.get(i));
                indexOfPathNew++;
            }
            i++;
        }

        i = cityIndex2;
        while(i >= indexDest1) {
            if (!pathNew.getTour().contains(tour.get(i))) {
                pathNew.getTour().set(indexOfPathNew, tour.get(i));
                indexOfPathNew++;
            }
            i--;
        }

        i = indexDest2;
        while(i < tour.getSize()) {
            if (!pathNew.getTour().contains(tour.get(i))) {
                pathNew.getTour().set(indexOfPathNew, tour.get(i));
                indexOfPathNew++;
            }
            i++;
        }

        // copy the new path into the old one
        for(int k = 0; k < pathNew.getSize(); k++) {
            tour.getTour().set(k, pathNew.get(k));
        }

//        System.out.println("Swap takes " + (System.nanoTime() - swap) + "ms");

    }

    public void setTour(final Tour tour) {
        this.tour = tour;
    }

    public Tour getTour() {
        return tour;
    }




}
