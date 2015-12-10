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

                final City a = firstEdge.previous();
                firstEdge.next();
                final City b = firstEdge.next();

                if (a.equals(b) || last.equals(b)) { break; }
                ListIterator<City> secondEdge = path.listIterator(path.indexOf(b) + 2);

                while (secondEdge.hasNext()) {
                    final City c = secondEdge.previous();
                    secondEdge.next();
                    final City d = secondEdge.next();
                    int gain = (d.getDistanceTo(b) + c.getDistanceTo(a)) - (a.getDistanceTo(b) + c.getDistanceTo(d));
                    if (gain < bestGain) {
                        bestGain = gain;
                        bestJ = b;
                        bestI = c;
                        if (firstGain) break;
                    }
                }

                if (bestGain < 0 && firstGain) break;
            }

            path.remove(path.get(0));
            finalGain += bestGain;

            if (bestJ != null && bestI != null) {
                int posb = path.indexOf(bestJ);
                int posc = path.indexOf(bestI);
                List<City> l = path.subList(Math.min(posc, posb), Math.max(posc, posb)+1);
                Collections.reverse(l);
            }
        }
        tour.setTourLength(distance+finalGain);
    }

}