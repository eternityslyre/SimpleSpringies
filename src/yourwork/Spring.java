package yourwork;

import ignorethis.Canvas;
import ignorethis.Image;
import java.awt.*;


public class Spring extends Image
{
    private Mass myStart;
    private Mass myEnd;
    private double myLength;
    private double myK;


    public Spring (Mass start, Mass end, double length, double kVal)
    {
        super(start.getCenter(), new Dimension(), new Point(), "images/spring.gif");
        myStart = start;
        myEnd = end;
        myLength = length;
        myK = kVal;
    }


    public void update (Canvas canvas)
    {
        Point start = myStart.getCenter();
        Point end = myEnd.getCenter();        
        int dx = start.x - end.x;
        int dy = start.y - end.y;

        // apply hooke's law to each attached mass
        Force f = new Force(Force.angleBetween(dx, dy),
                            myK * (myLength - Force.distanceBetween(dx, dy)));
        myStart.applyForce(f);
        myEnd.applyForce(f.negate());

        // update spring values based on attached masses
        setCenter((start.x + end.x) / 2, (start.y + end.y) / 2);
        setSize((int)start.distance(end), 20);
        setAngle(Force.angleBetween(dx, dy));
    }
}
