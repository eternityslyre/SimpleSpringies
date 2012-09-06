package ignorethis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.BevelBorder;
import yourwork.Mover;


/**
 * Simulates objects moving around in a bounded environment.
 * 
 * @author Robert C. Duvall
 */
@SuppressWarnings("serial")
public class Canvas extends JComponent
{
    // things to be animated
    private List<Mover> myMovers;
    // additional state for adding and removing of shapes during animation
    private List<Mover> myMoversToRemove;
    private ListIterator<Mover> myIterator;
    private Mover myCurrent;
    // like a dice, generates a series of random numbers
    private Random myGenerator;


    /**
     * Create a Canvas with the given size.
     * 
     * @param size width and height of canvas in pixels
     */
    public Canvas (Dimension size)
    {
        // distinguish canvas from the rest of the window
        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        // initialize animation state
        myGenerator = new Random();
        myMovers = new ArrayList<Mover>();
        myMoversToRemove = new ArrayList<Mover>();
    }


    /**
     * Remember given mover so it is painted on Canvas.
     * 
     * Note, movers are rendered in the order they are added.
     * 
     * @param m mover to be added to those that are drawn
     */
    public void add (Mover shape) 
    {
        if (myIterator == null)  myMovers.add(shape);
        else                     myIterator.add(shape);
    }
    
    
    /**
     * Forget given mover so it is not painted on Canvas.
     * 
     * @param m mover to be added to those that are drawn
     */
    public void remove (Mover shape)
    {
        if (myIterator == null)       myMovers.remove(shape);
        else if (myCurrent == shape)  myIterator.remove();
        else                          myMoversToRemove.add(shape);
    }
    
    
    /**
     * Remove all shapes from the canvas.
     */
    public void clear ()
    {
        if (myIterator == null)     myMovers.clear();
        else                        myMoversToRemove.addAll(myMovers);
    }


    /**
     * Paint the contents of the canvas.
     *
     * Never called by you directly, instead called by Java runtime
     * when area of screen covered by this container needs to be 
     * displayed (i.e., creation, uncovering, change in status)
     *
     * @param pen used to paint shape on the screen
     */
    public void paintComponent (Graphics pen)
    {
        // distinguish canvas from the rest of the window
        pen.setColor(Color.WHITE);
        pen.fillRect(0, 0, getSize().width, getSize().height);
        // paint shapes to be animated
        for (Mover m : myMovers)
        {
            m.paint(pen);
        }
    }


    /**
     * Called by each step of timer, multiple times per second.
     * 
     * This should update the state of the animated shapes by just
     * a little so they appear to move over time.
     */
    public void update ()
    {
        // animate each mover, taking care to add or remove new ones appropriately
        // clear out updates made during animation
    	RungeKuttaUpdate(1);
        for (Mover current : myMoversToRemove)
        {
            myMovers.remove(current);
        }
        myMoversToRemove.clear();
    }

	private void RungeKuttaUpdate(double t)
	{
		Transform initial = new Transform(myMovers);
		Transform finalState = new Transform(myMovers);

		initial.snapshot(myMovers);	
		finalState.snapshot(myMovers);

		updateMovers(t);
		initial.diff(myMovers);
		Transform k1 = initial;
		k1.scale(0.5);
		finalState.combine(k1);
		initial.restoreSnapshot(myMovers);
		k1.applyTransform(myMovers);

		updateMovers(t/2.0);
		k1.diff(myMovers);
		Transform k2 = k1;
		initial.restoreSnapshot(myMovers);
		finalState.combine(k2);
		k2.scale(0.5);
		k2.applyTransform(myMovers);

		updateMovers(t/2.0);
		k2.diff(myMovers);
		Transform k3 = k2;
		finalState.combine(k3);
		initial.restoreSnapshot(myMovers);
		k3.applyTransform(myMovers);

		updateMovers();
		k3.diff(myMovers);
		Transform k4 = k3;
		k4.scale(0.5);
		finalState.combine(k4);

		initial.restoreSnapshot(myMovers);
		finalState.scale(1/3.0);
       	finalState.applyTransform(myMovers); 
	}
	
	private void updateMovers()
	{
		updateMovers(1);
	}

	private void updateMovers(double t)
	{
        for (Mover myCurrent : myMovers)
        {
            myCurrent = myIterator.next();
			myCurrent.update(this, t);
        }
	}

	private void EulerUpdate()
	{
		myIterator = myMovers.listIterator();
        while (myIterator.hasNext())
        {
            myCurrent = myIterator.next();
            if (myMoversToRemove.contains(myCurrent))
            {
                myMoversToRemove.remove(myCurrent);
                myIterator.remove();
            }
            else
            {
                myCurrent.update(this);
            }
        }
        myIterator = null;
        
	}
    /**
     * Returns a random value between min and max, inclusive
     *
     * @param min minimum possible value
     * @param max maximum possible value
     */
    public int nextIntInRange (int min, int max)
    {
        return myGenerator.nextInt(max - min + 1) + min;
    }


    /**
     * Returns a random value between min and max, inclusive
     * that is guaranteed not to be zero
     *
     * @param min minimum possible value
     * @param max maximum possible value
     */
    public int nextNonZeroIntInRange (int min, int max)
    {
        int result = 0;

        while (result == 0)
        {
            result = nextIntInRange(min, max);
        }

        return result;
    }
}
