import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    public MyPanel(int width, int height){
        this.setPreferredSize(new Dimension(width, height));
        this.setMinimumSize(new Dimension(width, height));
        this.setMaximumSize(new Dimension(width, height));
    }
}
