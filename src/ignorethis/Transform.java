package ignorethis;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yourwork.Mover;

public class Transform {
	private Map<Mover, Delta> Deltas;
	private Map<Mover, Movement> Movements;
	
	public Transform(List<Mover> movers)
	{
		Movements = new HashMap<Mover, Movement>();
		Deltas = new HashMap<Mover, Delta>();
		snapshot(movers);
	}

	public void combine(Transform t)
	{
		t.applyTransform(Deltas);
	}

	public void snapshot(List<Mover> movers)
	{
		for (Mover m : movers)
		{
			Point center = m.getCenter();
			Point2D.Double velocity = m.getVelocity();
			Movements.put(m,new Movement(center.x, center.y, velocity.x, velocity.y));
		}
	}

	public void diff(List<Mover> movers)
	{
		if (Movements == null) snapshot(movers);
		for(Mover m : movers)
		{
			Deltas.put(m, Movements.get(m).createDelta(m));
		}
	}

	public void scale(double scale)
	{
		for(Delta d : Deltas.values())
		{
			d.scale(scale);
		}
	}

	public void restoreSnapshot(List<Mover> movers)
	{
		for(Mover m : movers)
		{
			if(!Movements.containsKey(m))
				continue;
			Movement movement = Movements.get(m);
			movement.restoreState(m);
		}
	}

	public void applyTransform(List<Mover> movers)
	{
		for (Mover m : movers)
		{
			if(!Deltas.containsKey(m)) 
				continue;
			Delta delta = Deltas.get(m);
			delta.applyDelta(m);
		}
	}

	public void applyTransform(Map<Mover, Delta> TargetDeltas)
	{
		for (Mover m : Deltas.keySet())
		{
			if(!TargetDeltas.containsKey(m)) {
				TargetDeltas.put(m, Deltas.get(m));
				continue;
			}
			Delta original = TargetDeltas.get(m);
			original.add(Deltas.get(m));
		}
	}
}
