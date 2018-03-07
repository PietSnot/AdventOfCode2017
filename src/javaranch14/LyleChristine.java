package javaranch14;

public class LyleChristine {

}

class gridMain {

    public static void main(String[] args) {

        Grid grid = new Grid(10, 20);

        Vehicle v1 = new Vehicle(grid);
        Vehicle v2 = new Vehicle(grid);
        grid.setVehicles(v1, v2);

        while (true) {

            grid.printGrid();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

class Grid {

    //Grid width and height
    private int rows;
    private int columns;
    private String[][] gridArray;
    Vehicle v1;
    Vehicle v2;

    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        gridArray = new String[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setVehicles(Vehicle v1, Vehicle v2) {
        this.v1 = v1;
        this.v2 = v2;
        startVehicles();
    }

    public void startVehicles() {
        Thread t = new Thread(v1);
        t.start();

        Thread t2 = new Thread(v2);
        t2.start();
    }

    public void printGrid() {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i == v1.getX() && j == v1.getY()) {
                    gridArray[v1.getX()][v1.getY()] = "o|";

                } else if (i == v2.getX() && j == v2.getY()) {
                    gridArray[v2.getX()][v2.getY()] = "x|";

                } else {
                    gridArray[i][j] = " |";
                }
            }
        }

        System.out.println();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(gridArray[i][j]);
            }
            System.out.println();
        }
    }

}

class Vehicle implements Runnable {

    private int x; //Horizontal coordinate of vehicle
    private int y; //Vertical coordinate of vehicle
    private Grid g;
    private int speed;

    public Vehicle(Grid g) {
        int max = g.getRows();
        this.x = (int) (Math.random() * (max));
        this.y = 0;
        this.g = g;
        this.speed = (int) (200 + (600 - 200) * Math.random());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void run() {

        for (int i = 0; ; i++) {
            y++;
            if (y > g.getColumns()) y = 0;
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
