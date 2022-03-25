package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * main class
 */
public class Main {
    private static final int SIDE = 20;  //field size
    private static final Point[][] field = new Point[SIDE][SIDE];

    public static void main(String[] args) throws InterruptedException {
        createField();
        for (int i = 0; i < 30; i++) {
            step();
            Thread.sleep(2000);
        }
    }

    // create field
    private static void createField() {
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                field[i][j] = new Point(j, i);
            }
        }
    }

    // step with showing field in console and field updating
    private static void step() {
        showField();
        createFieldWithNewNeighbors();
    }

    // show field
    private static void showField() {
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                System.out.print(field[i][j].toString() + " ");
            }
            System.out.println();
        }
        System.out.println("______________________________________");
    }

    // create new field with new neighbors
    private static void createFieldWithNewNeighbors() {
        countNeighbors();
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                if(!field[i][j].isAlive() && field[i][j].countNeighbors == 3) {
                    field[i][j].setAlive(true);
                } else if (field[i][j].isAlive() && field[i][j].countNeighbors < 2 || field[i][j].countNeighbors > 3) {
                        field[i][j].setAlive(false);
                }
            }
        }
    }

    // get all neighbors of a certain point
    private static List<Point> getNeighbors(Point point) {
        List<Point> result = new ArrayList<>();
        for (int y = point.y - 1; y <= point.y + 1; y++) {
            for (int x = point.x - 1; x <= point.x + 1; x++) {
                if (y < 0 || y >= SIDE) {
                    continue;
                }
                if (x < 0 || x >= SIDE) {
                    continue;
                }
                if (field[y][x] == point) {
                    continue;
                }
                result.add(field[y][x]);
            }
        }
        return result;
    }

    // count all neighbors of a certain point
    private static void countNeighbors() {
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                field[i][j].countNeighbors = 0;
                for (Point o : getNeighbors(field[i][j])) {
                    if(o.isAlive()) field[i][j].countNeighbors++;
                }
            }
        }
    }
}
