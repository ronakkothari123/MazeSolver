import Components.MyButton;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

    private final MyPanel panel;

    public MyFrame(int width, int height){
        panel = new MyPanel(width, height);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(panel);

        MyButton myButton = new MyButton(new Dimension(300,40), "Generate Maze", Color.BLACK, Color.WHITE, 12);
        panel.add(myButton);

        this.pack();

        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }


}
