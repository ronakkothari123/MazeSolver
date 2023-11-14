import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    public MyPanel(int width, int height){
        this.setPreferredSize(new Dimension(width, height));
        this.setMinimumSize(new Dimension(width, height));
        this.setMaximumSize(new Dimension(width, height));
    }

    public void paint(Graphics graphics){
        Graphics2D g = (Graphics2D) graphics;
    }
}
