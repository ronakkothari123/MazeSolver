package Components;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MyTextField extends JTextField {
    public MyTextField(int width, String ph){
        this.setColumns(width);
        this.setBackground(Color.WHITE);
        this.setForeground(Color.GRAY);
        this.setFont(new Font("Arial", Font.PLAIN, 12));
        this.setBorder(new LineBorder(Color.BLACK, 1));

        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e){
                if (MyTextField.this.getText().equals(ph)) {
                    MyTextField.this.setText("");
                    MyTextField.this.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (MyTextField.this.getText().isEmpty()) {
                    MyTextField.this.setForeground(Color.GRAY);
                    MyTextField.this.setText(ph);
                }
            }
        });
    }

    public MyTextField(int width, String ph, String text){
        this(width, ph);

        this.setText(text);
    }
}
