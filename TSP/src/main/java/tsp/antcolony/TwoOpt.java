package tsp.antcolony;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class TwoOpt {

    public static void improveTour (final Tour tour, final boolean firstGain) {

        final int distance = tour.getTourLength();
        final ArrayList<City> path = tour.getTour();
        int bestGain = -1;
        int finalGain = 0;

        while (bestGain != 0) {
            bestGain = 0;
            City bestI = path.get(0);
            City bestJ = null;
            City last = path.get(path.size() - 1);
            
            ListIterator<City> firstEdge = path.listIterator(path.indexOf(bestI));
            firstEdge.add(path.get(path.size() - 1));

            while (firstEdge.hasNext()) {

                final City i = firstEdge.previous();
                firstEdge.next();
                final City j = firstEdge.next();

                if (i.equals(j) || last.equals(j)) { break; }
                ListIterator<City> secondEdge = path.listIterator(path.indexOf(j) + 2);

                while (secondEdge.hasNext()) {
                    final City k = secondEdge.previous();
                    secondEdge.next();
                    final City l = secondEdge.next();
                    int gain = (l.getDistanceTo(j) + k.getDistanceTo(i)) - (i.getDistanceTo(j) + k.getDistanceTo(l));
                    if (gain < bestGain) {
                        bestGain = gain;
                        bestJ = j;
                        bestI = k;
                        if (firstGain) break;
                    }
                }

                if (bestGain < 0 && firstGain) break;
            }

            path.remove(path.get(0));
            finalGain += bestGain;

            if (bestJ != null && bestI != null) {
                int posJ = path.indexOf(bestJ);
                int posK = path.indexOf(bestI);
                List<City> l = path.subList(Math.min(posK, posJ), Math.max(posK, posJ) + 1);
                Collections.reverse(l);
            }
        }
        tour.setTourLength(distance + finalGain);
    }

}