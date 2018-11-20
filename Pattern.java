import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

public class Pattern {
	Vector<Object> objects = null;
	Object init_ob;
	public Color color;
	public Integer fill;
	public Integer thickness;
	
	public Pattern ()
	{
		objects = new Vector<Object>();
	}
	
	public void start (int dx, int dy)
	{	
		
		color = (Color)Drawer.temp_copy.get(1);
		fill = (Integer)Drawer.temp_copy.get(2);
		thickness = (Integer)Drawer.temp_copy.get(3);
		
		if (Drawer.temp_copy.get(0) instanceof Line2D)
		{
			Line2D current_line = (Line2D)Drawer.temp_copy.get(0);
			
			objects.add(current_line);
			
			init_ob = current_line;
		}		
		
		else if (Drawer.temp_copy.get(0) instanceof Rectangle2D)
		{
			Rectangle2D current_rect = (Rectangle2D)Drawer.temp_copy.get(0);
			
			objects.add(current_rect);
			
			init_ob = current_rect;
		}
		
		else if (Drawer.temp_copy.get(0) instanceof Ellipse2D)
		{
			Ellipse2D current_ellipse = (Ellipse2D)Drawer.temp_copy.get(0);
			
			objects.add(current_ellipse);
			
			init_ob = current_ellipse;
		}
		
		else if (Drawer.temp_copy.get(0) instanceof Sketch)
		{
			Sketch current_sketch = (Sketch)Drawer.temp_copy.get(0);
			
			objects.add(current_sketch);
			
			init_ob = current_sketch;
		}	

		else if (Drawer.temp_copy.get(0) instanceof Group)
		{
			Group current_group = (Group)Drawer.temp_copy.get(0);
			
			objects.add(current_group);
			
			init_ob = current_group;
		}	
		
		// 자기 복제
		Edit_Copy.deep_copy_to_temp (init_ob, (Color)Drawer.temp_copy.get(1), 
				(Integer)Drawer.temp_copy.get(2), (Integer)Drawer.temp_copy.get(3));

	}
	
	public void add (int dx, int dy)
	{	

		if (init_ob instanceof Line2D)
		{
			Line2D current_line = (Line2D)Drawer.temp_copy.get(0);

			current_line.setLine(((Line2D)init_ob).getX1() + dx, ((Line2D)init_ob).getY1() + dy, ((Line2D)init_ob).getX2() + dx, ((Line2D)init_ob).getY2() + dy);	
			
			objects.add(current_line);			
		}	
		
		else if (init_ob instanceof Rectangle2D)
		{
			Rectangle2D current_line = (Rectangle2D)Drawer.temp_copy.get(0);

			current_line.setRect(((Rectangle2D)init_ob).getX() + dx, ((Rectangle2D)init_ob).getY() + dy, ((Rectangle2D)init_ob).getWidth(), ((Rectangle2D)init_ob).getHeight());	
			
			objects.add(current_line);			
		}
		
		else if (init_ob instanceof Ellipse2D)
		{
			Ellipse2D current_ellipse = (Ellipse2D)Drawer.temp_copy.get(0);

			current_ellipse.setFrame(((Ellipse2D)init_ob).getX() + dx, ((Ellipse2D)init_ob).getY() + dy, ((Ellipse2D)init_ob).getWidth(), ((Ellipse2D)init_ob).getHeight());	
			
			objects.add(current_ellipse);			
		}	
		
		else if (init_ob instanceof Sketch)
		{
			Sketch current_sketch = (Sketch)Drawer.temp_copy.get(0);

			current_sketch.move(dx, dy);
			
			objects.add(current_sketch);			
		}	
		
		else if (init_ob instanceof Group)
		{
			Group current_group = (Group)Drawer.temp_copy.get(0);

			current_group.move(dx, dy);
			
			objects.add(current_group);			
		}	
		
		// 자기 복제
		Edit_Copy.deep_copy_to_temp (init_ob, (Color)Drawer.temp_copy.get(1), 
				(Integer)Drawer.temp_copy.get(2), (Integer)Drawer.temp_copy.get(3));

	}
	
	public void draw (Graphics2D g)
	{
		g.setStroke(new BasicStroke(thickness));
		g.setColor(color);

		for(int i = 0; i < objects.size(); i ++)
		{
			if(init_ob instanceof Line2D)
			{
				g.draw((Line2D)objects.get(i));
			}
			
			else if(init_ob instanceof Rectangle2D)
			{
				g.draw((Rectangle2D)objects.get(i));
			}
			
			else if(init_ob instanceof Ellipse2D)
			{
				g.draw((Ellipse2D)objects.get(i));
			}
			
			else if(init_ob instanceof Sketch)
			{
				((Sketch) objects.get(i)).draw(Drawer.g2);
			}
			
			else if(init_ob instanceof Group)
			{
				((Group) objects.get(i)).draw(Drawer.g2);
			}
		}
	}
	
	public Vector<Object> getPattern()
	{
		return objects;
	}
	
	public boolean isContaining (int x, int y)
	{
		boolean result = false;
		
		if(init_ob instanceof Line2D) {
			for(int i = 0; i < objects.size(); i ++) {
				if(((Line2D)objects.get(i)).getBounds().contains(x, y))
					result = true;
			}
		}
		
		else if(init_ob instanceof Rectangle2D) {
			for(int i = 0; i < objects.size(); i ++) {
				if(((Rectangle2D)objects.get(i)).getBounds2D().contains(x, y))
					result = true;
			}
		}
		
		else if(init_ob instanceof Ellipse2D) {
			for(int i = 0; i < objects.size(); i ++) {
				if(((Ellipse2D)objects.get(i)).getBounds().contains(x, y))
					result = true;
			}
		}
		
		else if(init_ob instanceof Sketch) {
			for(int i = 0; i < objects.size(); i ++) {
				if(((Sketch)objects.get(i)).isContaining(x, y))
					result = true;
			}
		}
		
		else if(init_ob instanceof Group) {
			for(int i = 0; i < objects.size(); i ++) {
				if(((Group)objects.get(i)).isContaining(x, y))
					result = true;
			}
		}
			
		
		return result;
	}
	
	public void move(int dx, int dy)
	{
		if(init_ob instanceof Line2D)
		{
			for(int i = 1; i < objects.size(); i ++) {
				Line2D current_line = (Line2D)objects.get(i);
				
				current_line.setLine(current_line.getX1() + dx, current_line.getY1() + dy, current_line.getX2() + dx, current_line.getY2() + dy);
			}
		}
		
		else if(init_ob instanceof Rectangle2D)
		{
			for(int i = 1; i < objects.size(); i ++) {
				Rectangle2D current_rect = (Rectangle2D)objects.get(i);
				
				current_rect.setRect(current_rect.getX() + dx, current_rect.getY() + dy, current_rect.getWidth(), current_rect.getHeight());
			}
		}
		
		else if(init_ob instanceof Ellipse2D)
		{
			for(int i = 1; i < objects.size(); i ++) {
				Ellipse2D current_ellipse = (Ellipse2D)objects.get(i);
				
				current_ellipse.setFrame(current_ellipse.getX() + dx, current_ellipse.getY() + dy, current_ellipse.getWidth(), current_ellipse.getHeight());
			}
		}
		
		else if(init_ob instanceof Sketch)
		{
			for(int i = 1; i < objects.size(); i ++) {
				Sketch current_sketch = (Sketch)objects.get(i);
				
				current_sketch.move(dx, dy);
			}
		}
		
		else if(init_ob instanceof Group)
		{
			for(int i = 1; i < objects.size(); i ++) {
				Group current_group = (Group)objects.get(i);
				
				current_group.move(dx, dy);
			}
		}
	}

}