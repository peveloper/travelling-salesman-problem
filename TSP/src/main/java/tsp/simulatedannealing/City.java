package tsp.simulatedannealing;

public class City {

    private double x;
    private double y;
    private int id;

    public City(double x, double y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public double getX() { return this.x; }
    public double getY() { return this.y; }

}
