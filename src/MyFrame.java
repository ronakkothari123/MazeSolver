import Components.MyButton;
import Components.MyTextField;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame{

    public final MyPanel panel;
    public final MyTextField myTextField;
    public final MyTextField myTextField2;
    public final MyButton myButton;
    public final MyButton myButton2;
    
    public MyFrame(int width, int height) {
        panel = new MyPanel(width, height);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(panel);

        myTextField = new MyTextField(6, "Width", "128");
        myTextField2 = new MyTextField(6, "Height", "72");

        myButton = new MyButton(new Dimension(300, 40), "Generate Maze", Color.BLACK, Color.WHITE, 12);
        myButton.addActionListener(e -> {
            Main.generateMaze();
        });

        myButton2 = new MyButton(new Dimension(300, 40), "Solve Maze", Color.BLACK, Color.WHITE, 12);
        myButton2.addActionListener(e -> {
            // Solve Maze Algorithm
        });

        panel.add(myButton);
        panel.add(myTextField);
        panel.add(myTextField2);
        panel.add(myButton2);

        this.pack();

        ImageIcon img = new ImageIcon("res/icon.png");



        this.setIconImage(img.getImage());

        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
}
