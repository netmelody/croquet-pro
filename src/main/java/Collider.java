
import java.awt.BorderLayout;

import javax.swing.JFrame;

public final class Collider {

    public static void main(String[] args) {
        ArtistCanvas canvas = new ArtistCanvas(600, 600);
        Artist artist = new Artist(canvas);
        Model model = new Model();
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

