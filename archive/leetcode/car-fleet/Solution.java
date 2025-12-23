class Solution {
    public int carFleet(int target, int[] position, int[] speed) {
        int n = position.length;
        Car[] cars = new Car[n];
        for (int i = 0; i < n; i++) {
            cars[i] = new Car(position[i], speed[i], target);
        }
        Arrays.sort(cars, (Car c1, Car c2) -> {
            return -Integer.compare(c1.position, c2.position);
        });
        final double EPS = 1e-9;
        double currentFleetTime = 0;
        int fleetSize = 0;
        int fleetCount = 0;
        for (int i = 0; i < n; i++) {
            if (cars[i].time - currentFleetTime > EPS) {
                currentFleetTime = cars[i].time;
                if (fleetSize > 0) {
                    fleetCount++;
                }
                fleetSize = 1;
            } else {
                fleetSize++;
            }
        }
        if (fleetSize > 0) {
            fleetCount++;
        }
        return fleetCount;
    }
}

public class Car {
    int position;
    int speed;
    double time;

    Car(int position, int speed, int target) {
        this.position = position;
        this.speed = speed;
        this.time = (double) (target - position) / speed;
    }
}
