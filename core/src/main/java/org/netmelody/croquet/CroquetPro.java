package org.netmelody.croquet;

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

import org.netmelody.croquet.model.Ball;
import org.netmelody.croquet.model.BallInPlay;
import org.netmelody.croquet.model.Game;
import org.netmelody.croquet.model.Strike;
import org.netmelody.croquet.model.Stroke;
import org.netmelody.croquet.model.Team;
import org.netmelody.croquet.physics.StrokeEnactor;
import org.netmelody.croquet.physics.Transition;
import org.netmelody.croquet.renderer.PitchView;
import org.netmelody.croquet.renderer.TransitionProjector;

public final class CroquetPro {

    private final Game game = new Game(new Team("Team 1"), new Team("Team2"));
    private final StrokeEnactor enactor = new StrokeEnactor(game.court);
    private final List<TransitionProjector> projectors = new ArrayList<TransitionProjector>();

    private List<BallInPlay> ballPositions = game.ballPositions;

    public CroquetPro() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() {
                final PitchView view = new PitchView(game.court);
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
        new CroquetPro();
    }
    
    private void showControlsFrame() {
        final JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());

        final Ball[] possibilities = {Ball.BLUE, Ball.BLACK, Ball.YELLOW, Ball.RED}; 
        final JSpinner angleSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 360.0, 1.0));
        final JSpinner powerSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 20.0, 1.0));
        final JComboBox<Ball> ballChooser = new JComboBox<Ball>(possibilities);
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
                final float angle = (float)Math.toRadians((Double)angleSpinner.getValue());
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

