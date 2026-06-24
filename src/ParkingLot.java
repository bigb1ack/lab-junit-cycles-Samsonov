public class ParkingLot {
    private int totalSpaces;
    private int occupiedSpaces;
    private double hourlyRate;

    private static int totalCarsParked = 0;

    public ParkingLot(int totalSpaces, double hourlyRate) {
        this.totalSpaces = totalSpaces;
        this.hourlyRate = hourlyRate;
        this.occupiedSpaces = 0;
    }

    public boolean parkCar() {
        if (occupiedSpaces < totalSpaces) {
            occupiedSpaces++;
            totalCarsParked++;
            return true;
        }
        return false;
    }

    public boolean leaveCar() {
        if (occupiedSpaces > 0) {
            occupiedSpaces--;
            return true;
        }
        return false;
    }

    public double calculatePayment(int hours) {
        return hours * hourlyRate;
    }

    public int getAvailableSpaces() {
        return totalSpaces - occupiedSpaces;
    }

    public int getTotalSpaces() {
        return totalSpaces;
    }

    public int getOccupiedSpaces() {
        return occupiedSpaces;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public static int getTotalCarsParked() {
        return totalCarsParked;
    }

    public static void resetTotalCarsParked() {
        totalCarsParked = 0;
    }
}