package com.scarytom.collider;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import com.scarytom.collider.model.Ball;
import com.scarytom.collider.model.BallInPlay;
import com.scarytom.collider.model.Game;
import com.scarytom.collider.model.Pitch;
import com.scarytom.collider.model.Strike;
import com.scarytom.collider.model.Stroke;
import com.scarytom.collider.physics.StrokeEnactor;
import com.scarytom.collider.physics.Transition;
import com.scarytom.collider.renderer.PitchView;
import com.scarytom.collider.renderer.TransitionProjector;

public final class CroquetMaster {

    private final Game game = new Game();
    private final StrokeEnactor enactor = new StrokeEnactor(game.pitch);
    private final List<TransitionProjector> projectors = new ArrayList<TransitionProjector>();

    private List<BallInPlay> ballPositions = game.ballPositions;

    public CroquetMaster() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() {
                final PitchView view = new PitchView(new Pitch());
                projectors.add(new TransitionProjector(view));
                
                final JFrame frame = new JFrame("Testaroonie");
                frame.setLayout(new BorderLayout());
                frame.add(view, "Center");
                frame.pack();
                view.draw(ballPositions);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                showControlsFrame();
            }
        });
    }
    
    public static void main(String[] args) throws Exception {
        new CroquetMaster();
    }
    
    private void showControlsFrame() {
        final JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());

        final Ball[] possibilities = {Ball.BLUE, Ball.BLACK, Ball.YELLOW, Ball.RED}; 
        final JSpinner angleSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 360.0, 1.0));
        final JSpinner powerSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 20.0, 1.0));
        final JComboBox<Ball> ballChooser = new JComboBox<>(possibilities);
        final JButton goButton = new JButton("Go!");
        
        final JPanel panel = new JPanel();
        panel.add(new JLabel("angle:"));
        panel.add(angleSpinner);
        panel.add(new JLabel("power:"));
        panel.add(powerSpinner);
        panel.add(new JLabel("ball:"));
        panel.add(ballChooser);
        panel.add(goButton);
        
        goButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                goButton.setEnabled(false);
                final Ball ball = ballChooser.getItemAt(ballChooser.getSelectedIndex());
                final Double power = (Double)powerSpinner.getValue();
                final float angle = (float)Math.toRadians((double)angleSpinner.getValue());
                makeShot(goButton, Stroke.standard(ball, new Strike(angle, power.floatValue())));
            }
        });
        
        frame.add(panel, "Center");
        frame.pack();
        frame.setVisible(true);
    }
    
    private void makeShot(final JButton goButton, final Stroke stroke) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() {
                    final Transition t = enactor.makeStroke(ballPositions, stroke);
                    for (TransitionProjector projector : projectors) {
                        projector.project(t);
                        ballPositions = t.finalPositions();
                    }
                    goButton.setEnabled(true);
            }
        });
    }
}

