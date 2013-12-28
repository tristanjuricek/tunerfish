package com.tristanjuricek.tunerfish.swing;

import com.sun.javafx.binding.StringFormatter;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * Draws the pitch "slider" in two main regions.
 *
 * This does not allow for "pluggable" UIs necessarily.
 */
public class JPitchSliderUI extends ComponentUI {

    // The total size of the UI, which is really going to be fixed at the
    // moment.
    private Dimension size;

    // We use the bottom region of this many pixels to draw the "cents"
    // meter.
    private int centsHeight;

    // The overall background color for the pitch area. It's going to be
    // just a solid color at the moment
    private Color pitchBackgroundColor;

    // The foreground color.
    private Color pitchForegroundColor;
    private Font pitchFont;
    private Font pitchSubFont;

    private Color centsReg1BC;
    private Color centsReg2BC;
    private Color centsReg3BC;
    private Color centsReg4BC;
    private Color centsReg5BC;
    private Color centsFC1;
    private Color centsFC2;
    private Color centsHighFC;

    public JPitchSliderUI() {
        this.size = new Dimension(400, 170);
        this.centsHeight = 40;

        this.pitchBackgroundColor = new Color(0, 0, 0);
        this.pitchForegroundColor = new Color(230, 230, 230);

        this.pitchFont = new Font("Helvetica Neue", Font.PLAIN, 100);
        this.pitchSubFont = new Font("Helvetica Neue", Font.PLAIN, 20);

        centsReg1BC = new Color(0, 0, 0);
        centsReg2BC = new Color(27, 27, 27);
        centsReg3BC = new Color(50, 53, 55);
        centsReg4BC = new Color(75, 75, 75);
        centsReg5BC = new Color(101, 101, 101);
        centsFC1 = new Color(0, 0, 0);
        centsFC2 = new Color(166, 166, 166);
        centsHighFC = new Color(245, 30, 30);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);

        paintPitch(g, c);
        paintCents(g, c);
    }

    public void paintPitch(Graphics g, JComponent c) {
        JPitchSlider slider = (JPitchSlider)c;
        PitchModel pitchModel = slider.getPitchModel();

        g.setColor(this.pitchBackgroundColor);
        g.fillRect(0, 0, c.getWidth(), getPitchHeight());

        Graphics2D g2 = (Graphics2D)g;

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

        int x = (c.getWidth() / 2) - 40;
        int y = 90;
        g2.setColor(this.pitchForegroundColor);
        g2.setFont(this.pitchFont);
        g2.drawString(pitchModel.getPitchName(), x, y);


        x = (c.getWidth() / 2) + 30;
        g2.setFont(this.pitchSubFont);
        g2.drawString(Integer.toString(pitchModel.getPitchOctave()), x, y);

        x = (c.getWidth() / 2) - 40;
        y = 120;
        ResourceBundle bundle = ResourceBundle.getBundle("com.tristanjuricek.tunerfish.swing.JPitchSliderUI");
        String fmt = String.format(bundle.getString("JPitchSliderUI.freqFormat"), pitchModel.getPitch());
        g2.drawString(fmt, x, y);
    }

    public void paintCents(Graphics g, JComponent c) {
        paintCentsBackground(g, c);
    }

    public void paintCentsBackground(Graphics g, JComponent c) {
        int r1_w = (int)(c.getWidth() * .3);
        int r2_w = (int)(c.getWidth() * .25);
        int r3_w = (int)(c.getWidth() * .2); // .75
        int r4_w = (int)(c.getWidth() * .15); // .90
        int r5_w = (int)(c.getWidth() * .10);

        g.setColor(this.centsReg1BC);
        int x = 0;
        int y = getPitchHeight();
        g.fillRect(0, y, r1_w / 2, getCentsHeight());

        g.setColor(this.centsReg2BC);
        x = r1_w / 2;
        g.fillRect(x, y, r2_w / 2, getCentsHeight());

        g.setColor(this.centsReg3BC);
        x += r2_w / 2;
        g.fillRect(x, y, r3_w / 2, getCentsHeight());

        g.setColor(this.centsReg4BC);
        x += r3_w / 2;
        g.fillRect(x, y, r4_w / 2, getCentsHeight());

        g.setColor(this.centsReg5BC);
        x += r4_w / 2;
        g.fillRect(x, y, r5_w, getCentsHeight());

        g.setColor(this.centsReg4BC);
        x += r5_w;
        g.fillRect(x, y, r4_w / 2, getCentsHeight());

        g.setColor(this.centsReg3BC);
        x += r4_w / 2;
        g.fillRect(x, y, r3_w / 2, getCentsHeight());

        g.setColor(this.centsReg2BC);
        x += r3_w / 2;
        g.fillRect(x, y, r2_w / 2, getCentsHeight());

        g.setColor(this.centsReg1BC);
        x += r2_w / 2;
        g.fillRect(x, y, r1_w / 2, getCentsHeight());
    }

    public int getPitchHeight() {
        return (int)this.size.getHeight() - this.centsHeight;
    }

    public int getCentsHeight() {
        return this.centsHeight;
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        return this.size;
    }

    @Override
    public Dimension getMinimumSize(JComponent c) {
        return this.size;
    }

    @Override
    public Dimension getMaximumSize(JComponent c) {
        return this.size;
    }
}
