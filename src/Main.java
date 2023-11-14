import java.awt.*;
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

        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                map[i][j] = r.nextInt(2);
            }
        }

        render(frame.panel.getGraphics());
   }

    private static void render(Graphics g){
        final int CELL_HEIGHT_OFFSET = 37;

        final int CELL_WIDTH = frame.getWidth() / map[0].length;
        final int CELL_HEIGHT = (frame.getHeight() - CELL_HEIGHT_OFFSET) / map.length;

        g.setColor(Color.WHITE);
        g.fillRect(0, CELL_HEIGHT_OFFSET, frame.getWidth(), frame.getHeight() - CELL_HEIGHT_OFFSET);

        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                if(map[i][j] == 0){
                    g.setColor(Color.BLACK);
                    g.fillRect(j * CELL_WIDTH, i * CELL_HEIGHT + CELL_HEIGHT_OFFSET, CELL_WIDTH, CELL_HEIGHT);
                } else if(map[i][j] == 1){
                    g.setColor(Color.WHITE);
                    g.fillRect(j * CELL_WIDTH, i * CELL_HEIGHT + CELL_HEIGHT_OFFSET, CELL_WIDTH, CELL_HEIGHT);
                }
            }
        }
    }

    @Override
    public void run() {

    }
}
