import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Main implements Runnable{

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    private static boolean running = true;
    private static int[][] map;
    private static MyFrame frame;
    private static Random r = new Random();


    private static int x = 0;


    public static void main(String[] args) throws InterruptedException {
        frame = new MyFrame(WIDTH, HEIGHT);

        Thread.sleep(1000);

        init(frame.panel.getGraphics());
    }

    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }

    private static void init(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 36, WIDTH, 1);
        g.setColor(hex2Rgb("#f8f8f9"));
        g.fillRect(0, 37, WIDTH, HEIGHT - 37);
    }

   public static void generateMaze(){
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

        for(int i = 0; i < 4; i++) directions.add(i);

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

    private static void render(Graphics g){
        final int CELL_HEIGHT_OFFSET = 37;

        final int CELL_WIDTH = frame.getWidth() / map[0].length;
        final int CELL_HEIGHT = (frame.getHeight() - CELL_HEIGHT_OFFSET) / map.length;

        g.setColor(Color.WHITE);
        g.fillRect(0, CELL_HEIGHT_OFFSET, frame.getWidth(), frame.getHeight() - CELL_HEIGHT_OFFSET);

        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                if(map[i][j] == 0) g.setColor(Color.BLACK);
                else if(map[i][j] == 1) g.setColor(Color.WHITE);
                else if(map[i][j] == 2) g.setColor(Color.GREEN);
                else if(map[i][j] == 3) g.setColor(Color.RED);


                g.fillRect(j * CELL_WIDTH, i * CELL_HEIGHT + CELL_HEIGHT_OFFSET, CELL_WIDTH, CELL_HEIGHT);
            }
        }
    }

    @Override
    public void run() {

    }
}
