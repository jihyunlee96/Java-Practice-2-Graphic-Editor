import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.Vector;

public class Eraser {
	Vector<Line2D> lines = null;
	
	Point current_startPoint = null;
	Point current_endPoint = null;
	
	public Eraser ()
	{
		lines = new Vector<Line2D>();
	}
	
	public void start (Point startPoint)
	{
		current_startPoint = startPoint;
	}
	
	public void end (Point endPoint)
	{
		current_endPoint = endPoint;
		
		Line2D temp = new Line2D.Double(current_startPoint, current_endPoint);
		
		lines.add(temp);
	}
	
	public void draw (Graphics2D g)
	{
		g.setColor(Color.WHITE);
		
		for(int i = 0; i < lines.size(); i ++)
			g.draw(lines.get(i));
	}
	
	public Vector<Line2D> getSketch()
	{
		return lines;
	}
	
	/*public boolean isContain(int x, int y)
	{
		boolean result = false;
		for(int i = 0; i < lines.size(); i ++)
			if(lines.get(i).getBounds().contains(x, y))
				result = true;
		
		return result;
	}
	
	public void move(int dx, int dy)
	{
		for(int i = 0; i < lines.size(); i ++) {
			Line2D temp = lines.get(i);
			temp.setLine(temp.getX1() + dx, temp.getY1() + dy, temp.getX2() + dx, temp.getY2() + dy);
		}
	}*/

}