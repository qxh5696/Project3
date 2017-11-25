

import java.util.Random;

/**
 * Created by qadirhaqq on 11/18/17.
 */
public class Robot extends Thread{
    int x,y;
    boolean malicious;
    int id;
    Cell[][] board;
    Cell goal;
    boolean objectFound = false;
    Robot[] robots;
    Message[] mailbox;
    Random rand;

    /**
     * Robot Constructor
     * @param id - id of the robot
     * @param x - starting x coordinate
     * @param y - starting y coordinate
     * @param malicious - boolean determining if it's malicious or not
     * @param board - reference to the shared board object
     */
    public Robot (int id, int x, int y, boolean malicious, Cell[][] board, Robot[] robots){
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
                if(objectFound) {
                    System.out.println("Robot " + this.id  + ": Telling everyone else...");
                    this.robots[i].mailbox[i] =  new Message(this.id, this.objectFound, goal);
                } else {
                    this.robots[i].mailbox[i] =  new Message(this.id, this.objectFound, null);
                }
            }
        }
        // Read
        for (int i = 0; i < mailbox.length; i++) {
            if (i != this.id) {
                if (mailbox[i] != null && mailbox[i].objectFound) {
                    this.objectFound = true;
                    this.goal = mailbox[i].objectLocation;
                }
            }
        }
    }

    private Cell[] generateNeighbors(int x, int y){
        Cell[] nextCells = new Cell[4];
        nextCells[0] =  (x < board.length-1) ? board[x+1][y] : null;
        nextCells[1] = (x > 0) ? board[x-1][y] : null;
        nextCells[2] = (y < board[0].length - 1) ? board[x][y+1]: null;
        nextCells[3] = (y > 0) ? board[x][y-1] : null;
        return nextCells;
    }

    /**
     * Moving function after communication with other robots
     * @param board - shared board object
     */
    public void move(Cell board[][]) {
        Cell[] nextCells = generateNeighbors(this.x, this.y);
        Cell nextCell = nextCells[rand.nextInt(4)];
        if (!this.objectFound) {
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

    public void run(){
        while(!this.objectFound){
            this.move(this.board);
            this.communicate();
        }
        System.out.println("Robot " + this.id + ": Object Found");
    }
}
