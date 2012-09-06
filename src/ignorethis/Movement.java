package ignorethis;

import java.awt.Point;
import java.awt.geom.Point2D;

import yourwork.Mover;

public class Movement
{
	private int x, y;
	private double vx, vy;
	public Movement(int newx, int newy, double newvx, double newvy)
	{
		x = newx;
		y = newy;
		vx = newvx;
		vy = newvy;
	}
	public void restoreState(Mover m)
	{
		m.setCenter(x,y);
		m.setVelocity(vx,vy);
	}

	public Delta createDelta(Mover m)
	{
		Point newCenter = m.getCenter();
		Point2D.Double newVelocity = m.getVelocity();
		return new Delta(newCenter.x - x, newCenter.y - y, newVelocity.x - vx, newVelocity.y - vy);
	}
}
