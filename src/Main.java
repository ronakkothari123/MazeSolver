import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Main implements Runnable {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public static MyFrame frame;

    private static final boolean running = true;
    private static int[][] map;
    private static final Random r = new Random();
    private static LinkedList<Cell> queue = new LinkedList<>();
    private static Cell[][] predecessors;

    private static final int REFRESH_RATE = 35;
    private static final int x = 0;


    public static void main(String[] args) throws InterruptedException {
        frame = new MyFrame(WIDTH, HEIGHT);

        Thread.sleep(1000);

        init(frame.panel.getGraphics());
    }

    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }

    private static void init(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 36, WIDTH, 1);
        g.setColor(hex2Rgb("#f8f8f9"));
        g.fillRect(0, 37, WIDTH, HEIGHT - 37);
    }

    public static void solve() {
        queue.clear();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 2) {
                    queue.add(new Cell(i, j));
                    break;
                }
            }
        }

        predecessors = new Cell[map.length][map[0].length];
        new Thread(() -> {
            while (!queue.isEmpty()) {
                Cell current = queue.poll();
                if (map[current.row][current.col] == 3) {
                    tracePath(current);
                    return;
                }
                exploreNeighbors(current);
                render(frame.panel.getGraphics());
                try {
                    Thread.sleep(REFRESH_RATE);
                } catch (InterruptedException e) {
                    System.out.println("There seems to be an issue");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void exploreNeighbors(Cell cell) {
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : dirs) {
            int newRow = cell.row + dir[0];
            int newCol = cell.col + dir[1];
            if (isValid(newRow, newCol)) {
                if(map[cell.row][cell.col] != 2) map[cell.row][cell.col] = 4;
                if(map[newRow][newCol] != 3) map[newRow][newCol] = 5;
                predecessors[newRow][newCol] = cell;
                queue.add(new Cell(newRow, newCol));
            }
        }
    }

    private static boolean isValid(int row, int col) {
        return row >= 0 && row < map.length && col >= 0 && col < map[0].length &&
                (map[row][col] == 1 || map[row][col] == 3);
    }

    private static void tracePath(Cell end) {
        Cell cell = end;
        while (cell != null) {
            if(map[cell.row][cell.col] != 3 && map[cell.row][cell.col] != 2) map[cell.row][cell.col] = 6;
            cell = predecessors[cell.row][cell.col];
        }

        render(frame.panel.getGraphics());
    }

    public static void generateMaze() {
        map = new int[Integer.parseInt(frame.myTextField2.getText())][Integer.parseInt(frame.myTextField.getText())];

        /**
         * 0 = Wall
         * 1 = Floor
         * 2 = Start
         * 3 = End
         */

        int startY = r.nextInt(map.length);
        int startX = r.nextInt(map[0].length);

        map[startY][startX] = 2;

        generatePath(map, startY, startX);
        placeEndPoint(map);

        render(frame.panel.getGraphics());
    }

    private static boolean isValidPosition(int[][] map, int x, int y) {
        return x > 0 && y > 0 && x < map.length - 1 && y < map[0].length - 1;
    }

    private static void generatePath(int[][] map, int y, int x) {
        int[][] DIRECTIONS = {
                {1, 0}, // Down
                {-1, 0}, // Up
                {0, 1}, // Right
                {0, -1} // Left
        };

        ArrayList<Integer> directions = new ArrayList<>();

        for (int i = 0; i < 4; i++) directions.add(i);

        Collections.shuffle(directions);

        for (int i : directions) {
            int dx = DIRECTIONS[i][0];
            int dy = DIRECTIONS[i][1];

            int nx = y + dx * 2;
            int ny = x + dy * 2;

            if (isValidPosition(map, nx, ny) && map[nx][ny] == 0) {
                map[y + dx][x + dy] = 1;
                map[nx][ny] = 1;
                generatePath(map, nx, ny);
            }
        }
    }

    private static void placeEndPoint(int[][] map) {
        int endX, endY;
        do {
            endX = r.nextInt(map.length);
            endY = r.nextInt(map[0].length);
        } while (map[endX][endY] != 1);

        map[endX][endY] = 3;
    }

    public static void render(Graphics g) {
        final int CELL_HEIGHT_OFFSET = 37;

        final int CELL_WIDTH = frame.getWidth() / map[0].length;
        final int CELL_HEIGHT = (frame.getHeight() - CELL_HEIGHT_OFFSET) / map.length;

        g.setColor(Color.WHITE);
        g.fillRect(0, CELL_HEIGHT_OFFSET, frame.getWidth(), frame.getHeight() - CELL_HEIGHT_OFFSET);

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 0) g.setColor(Color.BLACK);
                else if (map[i][j] == 1) g.setColor(Color.WHITE);
                else if (map[i][j] == 2) g.setColor(Color.GREEN);
                else if (map[i][j] == 3) g.setColor(Color.RED);
                else if (map[i][j] == 4) g.setColor(Color.BLUE);
                else if (map[i][j] == 5) g.setColor(Color.YELLOW);
                else if (map[i][j] == 6) g.setColor(Color.MAGENTA);


                g.fillRect(j * CELL_WIDTH, i * CELL_HEIGHT + CELL_HEIGHT_OFFSET, CELL_WIDTH, CELL_HEIGHT);
            }
        }
    }

    @Override
    public void run() {

    }
}
