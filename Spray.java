import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Vector;

public class Spray {
	Vector<Ellipse2D> dots = null;
	
	public Spray ()
	{
		dots = new Vector<Ellipse2D>();
	}
	
	public Spray (Vector<Ellipse2D> in_dots)
	{
		dots = new Vector<Ellipse2D>();
		
		for (int i = 0; i < in_dots.size() - 1; i ++) {
			in_dots.add(new Ellipse2D.Double(in_dots.get(i).getX(), in_dots.get(i).getY(), in_dots.get(i).getWidth(),
					 in_dots.get(i).getHeight()));
		}
	}
	
	public void start (Point currentPoint)
	{
		int thickness = Drawer.thickness * 5;
		
		for (int i = 0; i < thickness; i ++) {
			int dx = (int)(Math.random() * thickness - thickness/2);
			int dy = (int)(Math.random() * thickness - thickness/2);
			
			dots.add(new Ellipse2D.Double(currentPoint.x + dx, currentPoint.y + dy, 1, 1));
		}
	}
	
	public void draw (Graphics2D g)
	{
		g.setStroke(new BasicStroke(1));

		for(int i = 0; i < dots.size(); i ++)
			g.draw(dots.get(i));
	}
	
	public Vector<Ellipse2D> getSpray()
	{
		return dots;
	}
	
	public boolean isContaining (int x, int y)
	{
		boolean result = false;
		for(int i = 0; i < dots.size(); i ++)
			if(dots.get(i).getBounds().contains(x, y))
				result = true;
		
		return result;
	}
	
	public void move(int dx, int dy)
	{
		for(int i = 0; i < dots.size(); i ++) {
			Ellipse2D temp = dots.get(i);
			temp.setFrame(temp.getX() + dx, temp.getY() + dy, 1, 1);
		}
	}

}