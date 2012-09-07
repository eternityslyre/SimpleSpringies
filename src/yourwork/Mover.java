package yourwork;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;

import ignorethis.Canvas;


/**
 * Represents a shape that moves on its own.
 *
 * @author Robert C. Duvall
 */
public abstract class Mover
{
    // state
    private Point myCenter;
    private Point2D.Double myPreciseCenter;
    private Point2D.Double myVelocity;
    private Dimension mySize;
    private Color myColor;


    /**
     * Construct a shape at the given position, with the given velocity, 
     * size, and color.
     *
     * @param center position of the shape
     * @param size dimensions of the shape
     * @param velocity speed and direction of the shape
     * @param color color of the shape
     * @param trailSize number of lines to draw following the shape
     */
    public Mover (Point center, Dimension size, Point velocity, Color color)
    {
        setCenter(center.x, center.y);
        setSize(size.width, size.height);
        setVelocity(velocity.x, velocity.y);
        setColor(color);
    }


    /**
     * Describes how to "animate" the shape by changing its state.
     *
     * Currently, moves by the current velocity.
     */
    public void update (Canvas canvas)
    {
        translateCenter(myVelocity.x, myVelocity.y);
    }
    
    public void update(Canvas canvas, double timestep)
    {
    	translateCenter(myVelocity.x*timestep, myVelocity.y*timestep);
    }
    
    private void translateCenter(double dx, double dy)
    {
    	myPreciseCenter.x += dx;
    	myPreciseCenter.y += dy;
    	myCenter.x = (int)myPreciseCenter.x;
    	myCenter.y = (int)myPreciseCenter.y;
    }

    /**
     * Describes how to draw the shape on the screen.
     */
    public abstract void paint (Graphics pen);


    /**
     * Returns shape's center.
     */
    public Point getCenter ()
    {
        return myCenter;
    }


    /**
     * Resets shape's center.
     */
    public void setCenter (int x, int y)
    {
        myCenter = new Point(x, y);
        myPreciseCenter = new Point2D.Double(x, y);
    }

    
    /**
     * Returns shape's left-most coordinate.
     */
    public int getLeft ()
    {
        return getCenter().x - getSize().width / 2;
    }

    
    /**
     * Returns shape's top-most coordinate.
     */
    public int getTop ()
    {
        return getCenter().y - getSize().height / 2;
    }


    /**
     * Returns shape's right-most coordinate.
     */
    public int getRight ()
    {
        return getCenter().x + getSize().width / 2;
    }


    /**
     * Reports shape's bottom-most coordinate.
     *
     * @return bottom-most coordinate
     */
    public int getBottom ()
    {
        return getCenter().y + getSize().height / 2;
    }
    

    /**
     * Returns shape's size.
     */
    public Dimension getSize ()
    {
        return mySize;
    }


    /**
     * Resets shape's size.
     */
    public void setSize (int width, int height)
    {
        mySize = new Dimension(width, height);
    }


    /**
     * Returns shape's velocity.
     */
    public Point2D.Double getVelocity ()
    {
        return myVelocity;
    }


    /**
     * Resets shape's velocity.
     */
    public void setVelocity (double x, double y)
    {
        myVelocity = new Point2D.Double(x, y);
    }

    
    /**
     * Returns shape's color.
     */
    public Color getColor ()
    {
        return myColor;
    }


    /**
     * Resets shape's color.
     */
    public void setColor (Color color)
    {
        myColor = color;
    }
}
