package tsp.antcolony;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class TwoOpt {

    public final static void optimize(final Tour tour, final boolean firstImprovement) {
        final int distance = tour.getTourLength();
        int bestGain = -1;
        int finalGain = 0;
        final boolean first_improvement = firstImprovement;
        final LinkedList<City> path = tour.getTour();

        while (bestGain != 0) {
            bestGain = 0;
            City bestC = path.getFirst();
            City bestB = null;
            City last = path.getLast();
            ListIterator<City> itr = path.listIterator(path.indexOf(bestC));
            itr.add(path.getLast());

            while (itr.hasNext()) {
                final City a = itr.previous();
                itr.next();
                final City b = itr.next();

                if (last == b || a == b) { break; }

                ListIterator<City> itr2 = path.listIterator(path.indexOf(b) + 2);
                while (itr2.hasNext()) {
                    final City c = itr2.previous();
                    itr2.next();
                    final City d = itr2.next();
                    int gain = (d.dist(b) + c.dist(a)) - (a.dist(b) + c.dist(d));
                    if (gain < bestGain) {
                        bestGain = gain;
                        bestB = b;
                        bestC = c;
                        if (first_improvement) break;
                    }
                }
                if (bestGain < 0 && first_improvement) break;
            }
            path.remove(path.getFirst());
            finalGain += bestGain;
            if (bestB != null && bestC != null) {
                int posb = path.indexOf(bestB);
                int posc = path.indexOf(bestC);
                List<City> l = path.subList(Math.min(posc, posb), Math.max(posc, posb)+1);
                Collections.reverse(l);
            }
        }
        tour.setTourLength(distance+finalGain);
    }

}