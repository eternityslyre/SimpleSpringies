package ignorethis;

import java.awt.*;
import javax.swing.ImageIcon;
import yourwork.Mover;


/**
 * A Mover that represents an Image.
 *
 * Only the following formats are supported: png, jpg, gif.
 *
 * @author Robert C Duvall
 */
public class Image extends Mover
{
    private java.awt.Image myImage;
    private double myAngle;


    /**
     * Construct a shape at the given position, with the given size and
     * velocity, and with the image referred to by the given filename.
     */
    public Image (Point center,
                  Dimension size,
                  Point velocity,
                  String fileName)
    {
        super(center, size, velocity, Color.BLACK);
        setImage(fileName);
    }


    /**
     * Construct a shape at the given position, with the given size, and 
     * with the image referred to by the given filename.
     */
    public Image (Point center,
                  Dimension size,
                  String fileName)
    {
        this(center, size, new Point(0, 0), fileName);
    }


    /**
     * Set this shape's image to the image referred to by the given filename.
     */
    public void setImage (String fileName)
    {
        myImage = new ImageIcon(getClass().getResource("/" + fileName)).getImage();
    }


    /**
     * Set this shape's angle to the given value (in degrees).
     */
    public void setAngle (double degrees)
    {
        myAngle = degrees;
    }
    

    /**
     * Return this shape's angle (in degrees).
     */
    public double getAngle ()
    {
        return myAngle;
    }
    
    
    /**
     * Describes how to draw the shape on the screen.
     *
     * Currently, draws the shape as an image.
     */
    public void paint (Graphics pen)
    {
        // rotate image appropriately based on its angle
        Graphics2D pen2D = (Graphics2D)pen;
        pen2D.translate(getCenter().x, getCenter().y);
        pen2D.rotate(Math.toRadians(myAngle));
        pen.drawImage(myImage,
                      -getSize().width / 2, 
					  -getSize().height / 2,
                      getSize().width, getSize().height,
                      null);
        pen2D.rotate(-Math.toRadians(myAngle));
        pen2D.translate(-getCenter().x, -getCenter().y);
    }
}
