import java.util.Random;

/**
 * Created by qadirhaqq on 11/18/17.
 */

public class main {

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int M = Integer.parseInt(args[1]);
        int K = Integer.parseInt(args[2]);
        int objectX = Integer.parseInt(args[3]);
        int objectY = Integer.parseInt(args[4]);
        Cell[][] board = new Cell[N][M]; // ideal board implementation?
        Robot[] robots = new Robot[K];
        Random rand = new Random();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                board[i][j] = new Cell(i, j);
            }
        }

        board[objectX][objectY].occupyCell(Integer.MAX_VALUE);

        for (int i = 0; i < K; i++) {
            int x = rand.nextInt(N);
            int y = rand.nextInt(M);
            while (!board[x][y].isOccupied()) {
                x = rand.nextInt(N);
                y = rand.nextInt(M);
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
