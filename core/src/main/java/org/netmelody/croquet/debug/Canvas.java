package org.netmelody.croquet.debug;

import java.awt.AWTError;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JPanel;

import org.jbox2d.common.Vec2;

public final class Canvas extends JPanel {

    private static final long serialVersionUID = 1L;

    private final int panelWidth;
    private final int panelHeight;

    private Graphics2D graphics2d = null;
    private Image dbImage = null;

    public Canvas(int width, int height) {
        panelWidth = width;
        panelHeight = height;
        setBackground(Color.GREEN);
        setPreferredSize(new Dimension(panelWidth, panelHeight));
    }

    public Graphics2D getGraphics2d() {
        return graphics2d;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (null != dbImage) {
            g.drawImage(dbImage, 0, 0, null);
        }
    }

    public boolean prepare() {
        if (dbImage == null) {
            dbImage = createImage(panelWidth, panelHeight);
            if (dbImage == null) {
                return false;
            }
            graphics2d = (Graphics2D) dbImage.getGraphics();
            graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        graphics2d.setColor(Color.black);
        graphics2d.fillRect(0, 0, panelWidth, panelHeight);
        return true;
    }

    public void unveil() {
        try {
            Graphics g = this.getGraphics();
            if ((g != null) && dbImage != null) {
                g.drawImage(dbImage, 0, 0, null);
                Toolkit.getDefaultToolkit().sync();
                g.dispose();
            }
        } catch (AWTError e) {
        }
    }

    public Vec2 getCentre() {
        return new Vec2(panelWidth / 2, panelHeight / 2);
    }
}
