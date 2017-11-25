package com.rit.edu.cs.copads.p3;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by qadirhaqq on 11/18/17.
 */
public class Robot extends Thread {
    private int x;
    private int y;
    private boolean malicious;
    private int id;
    private Cell[][] board;
    private Cell goal;
    private boolean objectFound = false;
    private Robot[] robots;
    private Message[] mailbox;
    private Random rand;

    /**
     * com.rit.edu.cs.copads.p3.Robot Constructor
     *
     * @param id        - id of the robot
     * @param x         - starting x coordinate
     * @param y         - starting y coordinate
     * @param malicious - boolean determining if it's malicious or not
     * @param board     - reference to the shared board object
     */
    Robot(int id, int x, int y, boolean malicious, Cell[][] board, Robot[] robots) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.malicious = malicious;
        this.board = board;
        this.board[x][y].occupyCell(this.id);
        this.robots = robots;
        this.mailbox = new Message[robots.length];
        for (int i = 0; i < robots.length; i++) {
            mailbox[i] = null;
        }
        rand = new Random();
    }

    /**
     * Communication method for a robot
     */
    public void communicate() {
        // Send
        for (int i = 0; i < robots.length; i++) {
            if (i != this.id) {
                if (objectFound) {
                    System.out.println("Robot " + this.id + ": Telling everyone else...");
                    this.robots[i].mailbox[i] = new Message(this.id, this.objectFound, goal);
                } else {
                    this.robots[i].mailbox[i] = new Message(this.id, this.objectFound, null);
                }
            }
        }
        // Read
        for (int i = 0; i < mailbox.length; i++) {
            if (i != this.id && mailbox[i] != null && mailbox[i].objectFound) {
                this.objectFound = true;
                this.goal = mailbox[i].objectLocation;
            }
        }
    }

    private List<Cell> generateNeighbors(int x, int y) {
        List<Cell> nextCells = new LinkedList<Cell>();
        if (x < board.length - 1) nextCells.add(board[x+1][y]);
        if (x > 0) nextCells.add(board[x-1][y]);
        if (y < board[0].length - 1) nextCells.add(board[x][y + 1]);
        if (y > 0) nextCells.add(board[x][y - 1]);
        return nextCells;
    }

    /**
     * Moving function after communication with other robots
     *
     * @param board - shared board object
     */
    private void move(Cell[][] board) {
        List<Cell> nextCells = generateNeighbors(this.x, this.y);
        if (!this.objectFound) {
            Cell nextCell = nextCells.get(rand.nextInt(nextCells.size()));
            if (nextCell != null && nextCell.isOccupied() && nextCell.occupiedBy instanceof Integer) {
                this.objectFound = true;
                this.goal = nextCell;
            } else if (nextCell != null && !nextCell.isOccupied()) {
                synchronized (board[this.x][this.y]) {
                    board[this.x][this.y].resetCell();
                    nextCell.occupiedBy = this;
                }
            }
        } else {
            // bfs to location
            System.out.println("BFS to goal");
        }
    }

    @Override
    public void run() {
        while (!this.objectFound) {
            this.move(this.board);
            this.communicate();
        }
        System.out.println("com.rit.edu.cs.copads.p3.Robot " + this.id + ": Object Found");
    }
}
