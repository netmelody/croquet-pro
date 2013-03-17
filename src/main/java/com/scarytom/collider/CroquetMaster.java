package com.scarytom.collider;

import java.awt.BorderLayout;

import javax.swing.JFrame;
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

    public static void main(String[] args) {
        final Game game = new Game();

        final StrokeEnactor enactor = new StrokeEnactor(game.pitch);
        final Stroke stroke = Stroke.standard(Ball.BLUE, new Strike(0.1f, 0.1f));
        final Transition transition = enactor.makeStroke(game.ballPositions, stroke);

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
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(400L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        projector.project(transition);
                    }
                });
                
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
            }
        });
    }
}

