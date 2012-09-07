package ignorethis;

import java.awt.Point;
import java.awt.geom.Point2D;

import yourwork.Mover;

public class Delta
{
	private int dx, dy;
	private double dvx, dvy;

	public Delta(int x, int y, double vx, double vy)
	{
		dx = x;
		dy = y;
		dvx = vx; 
		dvy = vy;
	}

	public void applyDelta(Mover m)
	{
		m.getCenter().translate(dx,dy);
		Point2D.Double oldVelocity = m.getVelocity();
		m.setVelocity(oldVelocity.x+dvx, oldVelocity.y+dvy);
	}

	public void scale(double scale)
	{
		dx *= scale;
		dy *= scale;
		dvx *= scale;
		dvy *= scale;
	}

	public void add(Delta d)
	{
		dx += d.getx();
		dy += d.gety();
		dvx += d.getvx();
		dvy += d.getvy();
	}
	
	public String toString()
	{
		return "["+dx+", "+dy+", "+dvx+", "+dvy+"]";
	}
	
	public int getx(){
		return dx;
	}
	
	public int gety(){
		return dy;
	}
	
	public double getvx(){
		return dvx;
	}
	
	public double getvy(){
		return dvy;
	}
}
