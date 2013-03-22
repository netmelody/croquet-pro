package com.scarytom.collider.renderer;

import java.awt.AWTError;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JPanel;

import com.scarytom.collider.model.Ball;
import com.scarytom.collider.model.BallInPlay;
import com.scarytom.collider.model.Hoop;
import com.scarytom.collider.model.Pitch;
import com.scarytom.collider.model.Position;

public final class PitchView extends JPanel {

    private static final long serialVersionUID = 1L;

    private final Pitch pitch;
    private final int panelWidth;
    private final int panelHeight;

    private Graphics2D graphics2d = null;
    private Image dbImage = null;

    private static int scale(float value) {
        return (int)(value * 14.0f);
    }

    public PitchView(Pitch pitch) {
        this.pitch = pitch;
        panelWidth = scale(pitch.width);
        panelHeight = scale(pitch.height);
        setBackground(Color.GREEN);
        setPreferredSize(new Dimension(panelWidth, panelHeight));
    }

    public void draw(List<BallInPlay> ballsInPlay) {
        if(prepare()) {
            drawPitch();
            for (BallInPlay ballInPlay : ballsInPlay) {
                final Ball ball = ballInPlay.ball;
                final Position ballPosition = ballInPlay.position;
                drawCircle(ballPosition.x, ballPosition.y, ball.radius, Color.decode(ball.hexColor));
            }
            unveil();
        }
    }

    private void drawPitch() {
        for (Hoop hoop : pitch.hoops) {
            drawCircle(hoop.leg1Position.x, hoop.leg1Position.y, hoop.legRadius, Color.WHITE);
            drawCircle(hoop.leg2Position.x, hoop.leg2Position.y, hoop.legRadius, Color.WHITE);
        }
        drawCircle(pitch.peg.position.x, pitch.peg.position.y, pitch.peg.radius, Color.WHITE);
    }

    private void drawCircle(float x, float y, float radius, Color color) {
        int diameter = scale(radius * 2.0f);
        graphics2d.setColor(color);
        graphics2d.fillOval(scale(x - radius), scale(y - radius), diameter, diameter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (null != dbImage) {
            g.drawImage(dbImage, 0, 0, null);
        }
    }

    private boolean prepare() {
        if (dbImage == null) {
            dbImage = createImage(panelWidth, panelHeight);
            if (dbImage == null) {
                return false;
            }
            graphics2d = (Graphics2D) dbImage.getGraphics();
            graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        graphics2d.setColor(Color.decode("#084008"));
        graphics2d.fillRect(0, 0, panelWidth, panelHeight);
        return true;
    }

    private void unveil() {
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

}
