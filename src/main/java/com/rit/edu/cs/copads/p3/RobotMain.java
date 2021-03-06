package com.rit.edu.cs.copads.p3;

import com.rit.edu.cs.copads.p3.exceptions.BoardConstructException;

import java.util.Random;

/**
 * Created by qadirhaqq on 11/18/17.
 */

public class RobotMain {

    public static void main(String[] args) {
        int n = 0;
        int m = 0;
        int k = 0;
        int objectX = 0;
        int objectY = 0;
        try {
            n = Integer.parseInt(args[0]);
            m = Integer.parseInt(args[1]);
            k = Integer.parseInt(args[2]);
            objectX = Integer.parseInt(args[3]);
            objectY = Integer.parseInt(args[4]);
            if (objectY >= n || objectY < 0)
                throw new BoardConstructException("Object Y-val is greater than board height or less than 0.");
            if (objectX >= m || objectX < 0)
                throw new BoardConstructException("Object X-val is greater than board width or less than 0.");
        } catch (BoardConstructException e) {
            usage(e.getMessage());
        } catch (NumberFormatException e) {
            usage("Please use integers only as arguments.");
        } catch (ArrayIndexOutOfBoundsException e) {
            usage(null);
        } catch (Exception e) {
            usage("Unknown exception.");
        }
        Cell[][] board = new Cell[n][m];
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

    public static void usage(String error) {
        System.err.println(error == null ? "An error occurred while parsing the arguments." : error);
        System.err.println(error == null ? "Please check to make sure that they follow the accepted usage." : "");
        System.err.println("Usage: java RobotMain n m k x y");
        System.err.println("\t-n: board height");
        System.err.println("\t-m: board width");
        System.err.println("\t-k: number of robots");
        System.err.println("\t-x: x-val of the object");
        System.err.println("\t-y: y-val of the object");
        System.exit(1);
    }
}
