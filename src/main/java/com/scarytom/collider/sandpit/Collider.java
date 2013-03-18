package com.scarytom.collider.sandpit;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public final class Collider {

    public static void main(String[] args) {
        Model model = new Model();
        
        Canvas canvas = new Canvas(600, 600);
        Artist artist = new Artist(canvas);
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
