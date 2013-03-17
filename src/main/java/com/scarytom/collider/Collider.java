package com.scarytom.collider;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;

import com.scarytom.collider.Model.BallPosition;

public final class Collider {

    public static void main(String[] args) {
        Model model = new Model();
//        List<List<BallPosition>> simulation = model.simulate();
        
        ArtistCanvas canvas = new ArtistCanvas(600, 600);
        Artist artist = new Artist(canvas);
//        Projector studio = new Projector(simulation, artist);
        ArtistStudio studio = new ArtistStudio(model, artist);
        
        JFrame frame = new JFrame("Testaroonie");
        frame.setLayout(new BorderLayout());
        frame.add(canvas, "Center");
        frame.pack();

        studio.start();

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

