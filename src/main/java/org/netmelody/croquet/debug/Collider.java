package com.scarytom.collider.sandpit;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import org.jbox2d.dynamics.World;

public final class Collider {

    public static void main(String[] args) {
        World world = new Model().pose();
        debugWorld(world);
    }

    public static void debugWorld(World world) {
        Canvas canvas = new Canvas(2400, 1200);
        Artist artist = new Artist(canvas);
        ArtistStudio studio = new ArtistStudio(world, artist);
        
        JFrame frame = new JFrame("Testaroonie");
        frame.setLayout(new BorderLayout());
        frame.add(canvas, "Center");
        frame.pack();

        studio.start();

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

