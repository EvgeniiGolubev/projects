package com.company;

import java.util.Random;

public class Point {
    protected int x, y;
    protected int countNeighbors;
    protected boolean isAlive;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        isAlive = (new Random().nextInt(3) == 1);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCountNeighbors() {
        return countNeighbors;
    }

    public void setCountNeighbors(int countNeighbors) {
        this.countNeighbors = countNeighbors;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public String toString() {
        if (isAlive) return "X";
        return ".";
    }
}
