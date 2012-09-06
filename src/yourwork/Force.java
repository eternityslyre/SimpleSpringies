package yourwork;
import java.awt.Point;


public class Force
{
    private double myAngle;      // in degrees
    private double myMagnitude;  // in pixels


    public Force ()
    {
        this(0, 0);
    }

    public Force (double angle, double magnitude)
    {
        setDirection(angle);
        setMagnitude(magnitude);
    }

    public Force (Point source, Point target)
    {
        double dx = target.x - source.x; 
        double dy = source.y - target.y;
        setDirection(angleBetween(dx, dy));
        setMagnitude(distanceBetween(dx, dy));
    }

    public Force (Force other)
    {
        this(other.getDirection(), other.getMagnitude());
    }


    public void reset ()
    {
        setDirection(0);
        setMagnitude(0);
    }

    public double getMagnitude ()
    {
        return myMagnitude;
    }

    public double getRelativeMagnitude (Force other)
    {
        return -getMagnitude() * Math.cos(Math.toRadians(getAngleBetween(other)));
    }

    public void scale (double change)
    {
        setMagnitude(getMagnitude() * change);
    }

    protected void setMagnitude (double value)
    {
        myMagnitude = value;
    }


    public double getDirection ()
    {
        // standardize between -360 and +360 (keep 360, -360, and 0 as distinct values)
        final double OFFSET = 0.001;
        double sign = (myAngle < 0) ? 1 : -1;
        return ((myAngle + sign * OFFSET) % 360) - sign * OFFSET;
    }

    public double getAngleBetween (Force other)
    {
        return getDirection() - other.getDirection();
    }

    public void turn (double change)
    {
        setDirection(getDirection() + change);
    }

    protected void setDirection (double value)
    {
        myAngle = value;
    }


    public double getXChange ()
    {
        return getMagnitude() * Math.cos(Math.toRadians(getDirection()));
    }

    public double getYChange ()
    {
        return getMagnitude() * Math.sin(Math.toRadians(getDirection()));
    }


    public void add (Force other)
    {
/*
        double a1 = getAngle();
        double a2 = other.getAngle();
        double m1 = getMagnitude();
        double m2 = other.getMagnitude();
        double speed = Math.sqrt(m1 * m1 + m2 * m2 + 2 * m1 * m2 * Math.cos(Math.toRadians(a1 - a2))); 
        double angle = Math.toDegrees(Math.asin(m2 * Math.sin(Math.toRadians(a2 - a1)) / speed)) + a1;
        return new Force(angle, speed);
*/
        // more readable, although slightly slower
        double dx = getXChange() + other.getXChange();
        double dy = getYChange() + other.getYChange();
        setDirection(angleBetween(dx, dy));
        setMagnitude(distanceBetween(dx, dy));
    }

    public void difference (Force other)
    {
        add(other.negate());
    }

    public Force average (Force other)
    {
        return new Force((getDirection() + other.getDirection()) / 2.0,
                         (getMagnitude() + other.getMagnitude()) / 2.0);
    }

    public Force negate ()
    {
        return new Force(getDirection() + 180, getMagnitude());
    }

    public boolean equals (Object force)
    {
        if (force instanceof Force)
        {
            Force other = (Force)force;
            return (getMagnitude() == other.getMagnitude() &&
                    getDirection() == other.getDirection());
        }
        else
        {
            return false;
        }
    }

    public String toString ()
    {
        return String.format("(%2.2f, %2.2f)", getDirection(), getMagnitude());
    }


    /**
     * Returns distance between two points
     */
    public static double distanceBetween (Point p1, Point p2)
    {
        return distanceBetween(p1.x - p2.x, p1.y - p2.y);
    }

    /**
     * Returns distance given dx and dy
     */
    public static double distanceBetween (double dx, double dy)
    {
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Returns angle between two points
     */
    public static double angleBetween (Point p1, Point p2)
    {
        return angleBetween(p1.y - p2.y, p1.x - p2.x);
    }

    /**
     * Returns angle given dx and dy
     */
    public static double angleBetween (double dx, double dy)
    {
        return Math.toDegrees(Math.atan2(dy, dx));
    }
}
