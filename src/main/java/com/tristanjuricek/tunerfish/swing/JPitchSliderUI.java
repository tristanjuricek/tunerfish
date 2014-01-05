package com.tristanjuricek.tunerfish.swing;

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

    private Color centsReg2BC;
    private Color centsReg3BC;
    private Color centsFC1;
    private Color centsFC2;
    private Color centsHighFC;
    private Font centsFont;

    public JPitchSliderUI() {
        this.size = new Dimension(400, 170);
        this.centsHeight = 40;

        this.pitchBackgroundColor = new Color(0, 0, 0);
        this.pitchForegroundColor = new Color(230, 230, 230);

        this.pitchFont = new Font("Helvetica Neue", Font.PLAIN, 100);

        this.pitchSubFont = new Font("Helvetica Neue", Font.PLAIN, 20);

        centsReg2BC = new Color(27, 27, 27);
        centsReg3BC = new Color(50, 53, 55);
        centsFC1 = new Color(0, 0, 0);
        centsFC2 = new Color(166, 166, 166);
        centsHighFC = new Color(245, 30, 30);

        this.centsFont = new Font("Helvetica Neue", Font.PLAIN, 12);

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

        ResourceBundle bundle = ResourceBundle.getBundle("com.tristanjuricek.tunerfish.swing.JPitchSliderUI");

        g.setColor(this.pitchBackgroundColor);
        g.fillRect(0, 0, c.getWidth(), getPitchHeight());

        Graphics2D g2 = (Graphics2D)g;

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

        int y = 90;
        g2.setColor(this.pitchForegroundColor);
        g2.setFont(this.pitchFont);

        String pitch = pitchModel.getPitchName();

        if (!pitch.isEmpty()) {
            String key = "JPitchSliderUI." + pitch;
            String value = bundle.getString(key);

            FontMetrics metrics = g2.getFontMetrics(this.pitchFont);
            int w = metrics.stringWidth(value);
            int x = (c.getWidth() / 2) - (w / 2);

            g2.drawString(value, x, y);

            g2.setFont(this.pitchSubFont);
            y = 120;
            String fmt = String.format(bundle.getString("JPitchSliderUI.freqFormat"),
                    pitchModel.getPitchOctave(), pitchModel.getPitch());
            metrics = g2.getFontMetrics(this.pitchSubFont);
            w = metrics.stringWidth(fmt);
            x = (c.getWidth() / 2) - (w / 2);
            g2.drawString(fmt, x, y);
        }
    }

    public void paintCents(Graphics g, JComponent c) {
        paintCentsBackground(g, c);
        paintCentsMarkers(g, c);
        paintCentsHighlight(g, c);
    }

    public void paintCentsBackground(Graphics g, JComponent c) {
	Graphics2D g2 = (Graphics2D)g;

	int x = c.getWidth() / 2;
	int y1 = getPitchHeight();
	int y2 = c.getHeight();
	GradientPaint paint = new GradientPaint(x, y1, centsReg2BC, x, y2, centsReg3BC);
	g2.setPaint(paint);
	g2.fillRect(0, y1, c.getWidth(), getCentsHeight());
    }

    /**
     * Convert cent value to an x coordinate.
     *
     * @param cent The cent, should be from -50 to 50
     * @return The x position. If cent is 0, x is half the width of the component.
     */
    private int xOfCent(JComponent c, int cent) {
	int halfW = c.getWidth() / 2;

	double norm = cent / 50d; // The value between -1, 1

	int dist = (int)(norm * halfW); // The distance from the middle, range is [-.5w, .5w]

	return halfW + dist;
    }

    private void paintCentsMarkers(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D)g;

        g2.setFont(this.centsFont);

        g2.setColor(this.centsFC2);

	int x = xOfCent(c, -45);
        int y = getPitchHeight() + 38;
        g2.drawString("-45", x, y);

	x = xOfCent(c, -30);
        g2.drawString("-30", x, y);

	x = xOfCent(c, -15);
        g2.drawString("-15", x, y);

        g2.setColor(this.centsFC1);
	x = xOfCent(c, -5);
        g2.drawString("-5", x, y);

	x = xOfCent(c, 0);
        g2.drawString("0", x, y);

	x = xOfCent(c, 5);
        g2.drawString("5", x, y);

        g2.setColor(this.centsFC2);
	x = xOfCent(c, 15);
        g2.drawString("15", x, y);

	x = xOfCent(c, 30);
        g2.drawString("30", x, y);

	x = xOfCent(c, 45);
        g2.drawString("45", x, y);
    }

    public void paintCentsHighlight(Graphics g, JComponent c) {
        JPitchSlider pitchSlider = (JPitchSlider)c;
        Graphics2D g2 = (Graphics2D)g;

        PitchModel pitchModel = pitchSlider.getPitchModel();

        float cents = pitchModel.getDistanceToPitch();

        int width = 10;
        int height = getCentsHeight();
	int x = xOfCent(c, (int)cents);

        g2.setColor(this.centsHighFC);
        g2.fillRect(x, getPitchHeight(), width, height);
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
