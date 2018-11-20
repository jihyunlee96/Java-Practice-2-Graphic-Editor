import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

public class Group {
	Vector<Object> objects = null;
	Vector<Color> colors = null;
	Vector<Integer> fill = null;
	Vector<Integer> thickness = null;

	
	public Group ()
	{
		objects = new Vector<Object>();
		colors = new Vector<Color>();
		fill = new Vector<Integer>();
		thickness = new Vector<Integer>();
	}
	
	public Group (Vector<Object> in_objects, Vector<Color> in_colors,	Vector<Integer> in_fill,	Vector<Integer> in_thickness)
	{
		objects = new Vector<Object>();	
		for (int i = 0; i < in_objects.size(); i ++)
			objects.add(in_objects.get(i));
		
		colors = new Vector<Color>();
		for (int i = 0; i < in_colors.size(); i ++)
			colors.add(in_colors.get(i));
		
		fill = new Vector<Integer>();
		for (int i = 0; i < in_fill.size(); i ++)
			fill.add(in_fill.get(i));
		
		thickness = new Vector<Integer>();
		for (int i = 0; i < in_thickness.size(); i ++)
			thickness.add(in_thickness.get(i));
	}
	
	public Vector<Object> get_objects()
	{
		Vector<Object> o = new Vector<Object>();
		
		for (int i = 0; i < objects.size(); i ++) {
			
			Object current = objects.get(i);
			
			Object temp = null;
			
			if (current instanceof Line2D)
			{
				temp = new Line2D.Double(((Line2D) current).getP1(), ((Line2D) current).getP2());
			}
			
			else if (current instanceof Rectangle2D)
			{
				 temp = new Rectangle2D.Double(((Rectangle2D) current).getX(), ((Rectangle2D) current).getY(),
						((Rectangle2D) current).getWidth(), ((Rectangle2D) current).getHeight());
			}
			
			else if (current instanceof Ellipse2D)
			{
				temp = new Ellipse2D.Double(((Ellipse2D) current).getX(), ((Ellipse2D) current).getY(),
						((Ellipse2D) current).getWidth(), ((Ellipse2D) current).getHeight());
			}
			
			else if (current instanceof Sketch)
			{
				temp = new Sketch(((Sketch) current).getSketch());
			}
			
			else if (current instanceof Spray)
			{
				temp = new Spray(((Spray) current).getSpray());
			}
			
			else if (current instanceof Group)
			{
				temp = new Group(((Group) current).get_objects(), ((Group) current).get_colors(), ((Group) current).get_fill(), ((Group) current).get_thickness());
			}
			
			o.add(temp);
		}
		
		return o;
	}
	
	public Vector<Color> get_colors()
	{
		Vector<Color> c = new Vector<Color>();
		
		for (int i = 0; i < colors.size(); i ++) {
			Color temp = new Color(colors.get(i).getRGB());
			c.add(temp);
		}
		
		return c;
	}
	
	public Vector<Integer> get_fill()
	{
		Vector<Integer> f = new Vector<Integer>();
		
		for (int i = 0; i < fill.size(); i ++) {
			Integer temp = new Integer(fill.get(i));
			f.add(temp);
		}
		
		return f;
	}
	
	public Vector<Integer> get_thickness()
	{
		Vector<Integer> th = new Vector<Integer>();
		
		for (int i = 0; i < thickness.size(); i ++) {
			Integer temp = new Integer(thickness.get(i));
			th.add(temp);
		}
		
		return th;
	}
	
	public void add (Object o, Color c, Integer f, Integer th, int index)
	{	
		objects.add(o);
		colors.add(c);
		fill.add(f);
		thickness.add(th);
		
		Drawer.object_vector.remove(index);
		Drawer.color_vector.remove(index);
		Drawer.fill_vector.remove(index);
		Drawer.thickness_vector.remove(index);
	}
	
	public void draw (Graphics2D g)
	{

		for(int i = 0; i < objects.size(); i ++)
		{
			g.setStroke(new BasicStroke((int)thickness.get(i)));
			g.setColor(colors.get(i));
			
			if(objects.get(i) instanceof Line2D)
			{
				g.draw((Line2D)objects.get(i));
			}
			
			else if(objects.get(i) instanceof Rectangle2D)
			{
				g.draw((Rectangle2D)objects.get(i));
				
				if(fill.get(i) == 1)
					g.fill((Rectangle2D)objects.get(i));
			}
			
			else if(objects.get(i) instanceof Ellipse2D)
			{
				g.draw((Ellipse2D)objects.get(i));
				
				if(fill.get(i) == 1)
					g.fill((Ellipse2D)objects.get(i));
			}
			
			else if(objects.get(i) instanceof Sketch)
			{
				((Sketch) objects.get(i)).draw(Drawer.g2);
			}
		}
	}
	
	
	public boolean isContaining (int x, int y)
	{
		boolean result = false;
		
		for(int i = 0; i < objects.size(); i ++) {
			
			if(objects.get(i) instanceof Line2D) {
					if(((Line2D)objects.get(i)).getBounds().contains(x, y))
						result = true;
				}
			
			else if(objects.get(i) instanceof Rectangle2D) {
					if(((Rectangle2D)objects.get(i)).getBounds2D().contains(x, y))
						result = true;
				}
			
			
			else if(objects.get(i) instanceof Ellipse2D) {
					if(((Ellipse2D)objects.get(i)).getBounds().contains(x, y))
						result = true;
			}
			
			else if(objects.get(i) instanceof Sketch) {
					if(((Sketch)objects.get(i)).isContaining(x, y))
						result = true;	
			}
		}
		return result;
	}
	
	public void move(int dx, int dy)
	{
		
		for(int i = 0; i < objects.size(); i ++) {
			if(objects.get(i) instanceof Line2D)
			{
					Line2D current_line = (Line2D)objects.get(i);
					
					current_line.setLine(current_line.getX1() + dx, current_line.getY1() + dy, current_line.getX2() + dx, current_line.getY2() + dy);
			}
			
			else if(objects.get(i) instanceof Rectangle2D)
			{
					Rectangle2D current_rect = (Rectangle2D)objects.get(i);
					
					current_rect.setRect(current_rect.getX() + dx, current_rect.getY() + dy, current_rect.getWidth(), current_rect.getHeight());
			}
			
			else if(objects.get(i) instanceof Ellipse2D)
			{
					Ellipse2D current_ellipse = (Ellipse2D)objects.get(i);
					
					current_ellipse.setFrame(current_ellipse.getX() + dx, current_ellipse.getY() + dy, current_ellipse.getWidth(), current_ellipse.getHeight());
			}
			
			else if(objects.get(i) instanceof Sketch)
			{
					Sketch current_sketch = (Sketch)objects.get(i);
					
					current_sketch.move(dx, dy);
			}
		}
	}
	
	public void ungroup ()
	{	
		for(int i = 0; i < objects.size(); i ++) {
			Drawer.object_vector.add(objects.get(i));
			Drawer.color_vector.add(colors.get(i));
			Drawer.fill_vector.add(fill.get(i));
			Drawer.thickness_vector.add(thickness.get(i));
		}
		
		objects = new Vector<Object>();
		colors = new Vector<Color>();
		fill = new Vector<Integer>();
		thickness = new Vector<Integer>();
	}

}