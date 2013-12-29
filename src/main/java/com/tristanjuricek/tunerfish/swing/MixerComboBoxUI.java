package com.tristanjuricek.tunerfish.swing;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

/**
 * Created by tristan on 12/28/13.
 */
public class MixerComboBoxUI extends BasicComboBoxUI {

    private Color backgroundColor;
    private Color fgColor;

    public MixerComboBoxUI() {
        this.backgroundColor = new Color(35, 35, 35);
        this.fgColor = new Color(180, 180, 180);
    }

    @Override
    protected ListCellRenderer createRenderer() {
        return new MixerListCellEditor();
    }

    @Override
    protected JButton createArrowButton() {
        return new MixerButton();
    }

    public class MixerButton extends JButton {

        public MixerButton() {
            super();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(backgroundColor);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(fgColor);
            int pad = 5;
            int x_1 = pad;
            int x_2 = getWidth() / 2;
            int x_3 = getWidth() - pad;
            int y_1 = pad * 2 - 3;
            int y_2 = getHeight() - (pad + 3);
            int y_3 = pad * 2 - 3;
//            Stroke s = new BasicStroke(2.0f);
//            g2.setStroke(s);
            g2.drawLine(x_1, y_1, x_2, y_2);
            g2.drawLine(x_2, y_2, x_3, y_3);
        }
    }
}
