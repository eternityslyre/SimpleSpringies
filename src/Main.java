import java.awt.*;
import javax.swing.*;


/**
 * Creates window that can be moved, resized, and closed by the user.
 *
 * @author Robert C. Duvall
 */
public class Main
{
    // constants
    public static final Dimension SIZE = new Dimension(800, 600);
    public static final String TITLE = "Springies!";


    // main --- where the program starts
    public static void main (String args[])
    {
        Applet app = new Applet();
        app.init(SIZE);

        // create container that will work with Window manager
        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // add our user interface components to Frame and show it
        frame.getContentPane().add(app, BorderLayout.CENTER);
        frame.pack();        
        frame.setVisible(true);

        // start the animation
        app.start();
    }
}
