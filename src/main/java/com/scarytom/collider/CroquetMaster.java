package com.scarytom.collider;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import com.scarytom.collider.model.Ball;
import com.scarytom.collider.model.Game;
import com.scarytom.collider.model.Pitch;
import com.scarytom.collider.model.Strike;
import com.scarytom.collider.model.Stroke;
import com.scarytom.collider.physics.StrokeEnactor;
import com.scarytom.collider.physics.Transition;
import com.scarytom.collider.renderer.PitchView;
import com.scarytom.collider.renderer.TransitionProjector;

public final class CroquetMaster {

    public static void main(String[] args) throws Exception {
        final Game game = new Game();

        final StrokeEnactor enactor = new StrokeEnactor(game.pitch);

        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() {
                final PitchView view = new PitchView(new Pitch());
                final TransitionProjector projector = new TransitionProjector(view);
                
                final JFrame frame = new JFrame("Testaroonie");
                frame.setLayout(new BorderLayout());
                frame.add(view, "Center");
                frame.pack();
                
                view.draw(game.ballPositions);

                SwingUtilities.invokeLater(new Runnable() {
                    @Override public void run() {
                        Stroke s = showOptionPane();
                        if (null != s) {
                            final Transition t = enactor.makeStroke(game.ballPositions, s);
                            projector.project(t);
                        }
                    }
                });

                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
            }

        });
    }
    
    private static Stroke showOptionPane() {
        Object[] possibilities = {Ball.BLUE, Ball.BLACK, Ball.YELLOW, Ball.RED}; 
        JSpinner angleSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 360.0, 1.0));
        JSpinner powerSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 20.0, 1.0));
        JPanel panel = new JPanel();
        panel.add(new JLabel("angle:"));
        panel.add(angleSpinner);
        panel.add(new JLabel("power:"));
        panel.add(powerSpinner);
        panel.add(new JLabel("ball:"));
        Ball b = (Ball)JOptionPane.showInputDialog(
                            null,
                            panel,
                            "Customized Dialog",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            possibilities,
                            Ball.BLUE);

        //If a string was returned, say so.
        if (b != null) {
            Double power = (Double)powerSpinner.getValue();
            final float angle = (float)Math.toRadians((double)angleSpinner.getValue());
            return Stroke.standard(b, new Strike(angle, power.floatValue()));
        }
        return null;
    }
}

