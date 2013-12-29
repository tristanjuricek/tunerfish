package com.tristanjuricek.tunerfish.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Created by tristan on 12/28/13.
 */
public class MixerListCellEditor extends JLabel implements  ListCellRenderer<String> {

    public MixerListCellEditor() {
        setText("--");
        setBackground(new Color(30, 30, 30));
        setForeground(new Color(180, 180, 180));
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends String> list,
                                                  String value,
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {
        setText(value);
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setFont(new Font("Helvetica Neue", Font.BOLD, 12));

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR);

        g2.setColor(new Color(30, 30, 30));
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(new Color(180, 180, 180));
        g2.drawString(getText(), 2, getHeight() - 5);
    }
}
