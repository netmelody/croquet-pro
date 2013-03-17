package com.scarytom.collider.renderer;
import java.awt.AWTError;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JPanel;

import org.jbox2d.common.Vec2;

import com.scarytom.collider.model.BallInPlay;
import com.scarytom.collider.model.Hoop;
import com.scarytom.collider.model.Pitch;

public final class PitchView extends JPanel {

    private static final long serialVersionUID = 1L;

    private final Pitch pitch;
    private final int panelWidth;
    private final int panelHeight;

    private Graphics2D graphics2d = null;
    private Image dbImage = null;


    private static int scale(float value) {
        return (int)(value * 20.0f);
    }

    public PitchView(Pitch pitch) {
        this.pitch = pitch;
        panelWidth = scale(pitch.width);
        panelHeight = scale(pitch.height);
        setBackground(Color.GREEN);
        setPreferredSize(new Dimension(panelWidth, panelHeight));
    }

    public void draw(List<BallInPlay> balls) {
        if(prepare()) {
            drawPitch();
            for (BallInPlay ballPosition : balls) {
                graphics2d.setColor(Color.decode(ballPosition.ball.hexColor));
                graphics2d.fillOval(scale(ballPosition.position.x), scale(ballPosition.position.y), scale(1.0f), scale(1.0f));
            }
            unveil();
        }
    }

    private void drawPitch() {
        for (Hoop hoop : pitch.hoops) {
            graphics2d.setColor(Color.WHITE);
            graphics2d.fillOval(scale(hoop.position.x), scale(hoop.position.y - 1.1f), scale(0.05f), scale(0.05f));
            graphics2d.fillOval(scale(hoop.position.x), scale(hoop.position.y + 1.1f), scale(0.05f), scale(0.05f));
        }
        graphics2d.setColor(Color.WHITE);
        graphics2d.fillOval(scale(pitch.peg.position.x), scale(pitch.peg.position.y), scale(0.1f), scale(0.1f));
    }

    public Graphics2D getGraphics2d() {
        return graphics2d;
    }

    public boolean prepare() {
        if (dbImage == null) {
            if (panelWidth <= 0 || panelHeight <= 0) {
                return false;
            }
            dbImage = createImage(panelWidth, panelHeight);
            if (dbImage == null) {
                return false;
            }
            graphics2d = (Graphics2D) dbImage.getGraphics();
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
