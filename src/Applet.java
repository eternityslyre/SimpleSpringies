import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import ignorethis.*;
import yourwork.*;


/**
 * Creates an applet that be viewed over the web.
 *
 * @author Robert C. Duvall
 */
@SuppressWarnings("serial")
public class Applet extends JApplet
{
    // animate 25 times per second if possible
    public static final int DEFAULT_DELAY = 1000 / 25;  // in milliseconds

    // state
    private Canvas myDisplay;
    private Timer myTimer;


    /**
     * Initializes the applet --- called by the browser.
     */
    public void init ()
    {
        // create container to display animations
        init(new Dimension(Integer.parseInt(getParameter("width")),
                           Integer.parseInt(getParameter("height"))));
    }


    /**
     * Initializes the applet --- called by main.
     */
    public void init (Dimension size)
    {
        // create container to display animations
        myDisplay = new Canvas(size);
        myDisplay.setPreferredSize(size);
        myDisplay.setFocusable(true);
        myDisplay.requestFocus();

        // add commands to test here
        ButtonPanel commands = new ButtonPanel(myDisplay);
        commands.add(new SpringFactory());

        // add our user interface components to applet
        getContentPane().add(commands, BorderLayout.NORTH);
        getContentPane().add(myDisplay, BorderLayout.CENTER);
    }
    
    
    /**
     * Starts the applet's action, i.e., starts the animation.
     */
    public void start ()
    {
        // create a timer to animate the canvas
        myTimer = new Timer(DEFAULT_DELAY, 
            new ActionListener()
            {
                long lastTime = 0;
                long elapsedTime = 0;
                int numFrames = 0;

                public void actionPerformed(ActionEvent e)
                {
                	System.out.print(".");
                    myDisplay.update();
                    myDisplay.repaint();
                    myTimer.setDelay(Math.max(1, DEFAULT_DELAY)); calculateFrameRate();
                }

                private int calculateFrameRate ()
                {
                    numFrames++;
                    long currentTime = System.currentTimeMillis();
                    elapsedTime += (currentTime - lastTime);
                    if (elapsedTime >= 1000)
                    {
                        System.out.println("FPS = " + numFrames);
                        elapsedTime = 0;
                        numFrames = 0;
                    }
                    int stepTime = (int)(currentTime - lastTime);
                    lastTime = currentTime;
                    //System.out.println(stepTime);
                    return stepTime;
                }
            });
        // start the animation
        myTimer.start();
    }


    /**
     * Stops the applet's action, i.e., the animation.
     */
    public void stop ()
    {
        myTimer.stop();
    }
}
