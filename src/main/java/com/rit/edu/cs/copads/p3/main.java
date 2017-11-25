package com.rit.edu.cs.copads.p3;

import java.util.Random;

/**
 * Created by qadirhaqq on 11/18/17.
 */

public class main {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        int k = Integer.parseInt(args[2]);
        int objectX = Integer.parseInt(args[3]);
        int objectY = Integer.parseInt(args[4]);
        Cell[][] board = new Cell[n][m]; // ideal board implementation?
        Robot[] robots = new Robot[k];
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                board[i][j] = new Cell(i, j);
            }
        }

        board[objectX][objectY].occupyCell(Integer.MAX_VALUE);

        for (int i = 0; i < k; i++) {
            int x = rand.nextInt(n);
            int y = rand.nextInt(m);
            while (!board[x][y].isOccupied()) {
                x = rand.nextInt(n);
                y = rand.nextInt(m);
            }
            int mal = rand.nextInt(100);
            boolean bad_robot = (mal < 25);

            robots[i] = new Robot(x, y, i, bad_robot, board, robots);
        }

        for (int i = 0; i < robots.length; i++) {
            robots[i].start();
        }


    }
}
