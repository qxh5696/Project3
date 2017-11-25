package com.rit.edu.cs.copads.p3;

/**
 * Created by qadirhaqq on 11/18/17.
 */
public class Cell {
    Object occupiedBy; // integer of the robot id that's at this location
    int x;
    int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isOccupied() {
        return this.occupiedBy != null;
    }

    public Object isOccupiedBy() {
        return this.occupiedBy;
    }


    public void resetCell() {
        this.occupiedBy = null;
    }

    public void occupyCell(Object obj) {
        this.occupiedBy = obj;
    }
}
