package Components;

import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton {

    public MyButton(Dimension d, String text, Color bgc, Color tc, int fs){
        this.setSize(d);
        this.setText(text);
        this.setBackground(bgc);
        this.setForeground(tc);
        this.setBorderPainted(false);

        this.setFont(new Font("Arial", Font.PLAIN, fs));
        this.setFocusable(false);
    }

}
