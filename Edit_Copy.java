import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Edit_Copy extends JMenuItem implements ActionListener {
	
	public Edit_Copy()
	{
		super("Copy & Paste");
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "Copy & Paste") {
			Drawer.temp_copy.setSize(0);
			JOptionPane.showMessageDialog(null, "Click an object you want to copy!");
			MainFrame.drawer.whichShape = 104;
		}
	}
	
	public static void deep_copy_to_temp (Object object, Color color, Integer fill, Integer thickness)
	{
		Object o;
		Color c;
		Integer f;
		Integer th;
		
		Drawer.temp_copy.setSize(0);
		
		if (object instanceof Line2D)
		{
			o = new Line2D.Double(((Line2D) object).getP1(), ((Line2D) object).getP2());
			Drawer.temp_copy.add(o);
		}
		
		else if (object instanceof Rectangle2D)
		{
			o = new Rectangle2D.Double(((Rectangle2D) object).getX(), ((Rectangle2D) object).getY(),
					((Rectangle2D) object).getWidth(), ((Rectangle2D) object).getHeight());
			Drawer.temp_copy.add(o);
		}
		
		else if (object instanceof Ellipse2D)
		{
			o = new Ellipse2D.Double(((Ellipse2D) object).getX(), ((Ellipse2D) object).getY(),
					((Ellipse2D) object).getWidth(), ((Ellipse2D) object).getHeight());
			Drawer.temp_copy.add(o);
		}
		
		else if (object instanceof Sketch)
		{
			o = new Sketch(((Sketch) object).getSketch());
			Drawer.temp_copy.add(o);
		}
		
		else if (object instanceof Spray)
		{
			o = new Spray(((Spray) object).getSpray());
			Drawer.temp_copy.add(o);
		}
		
		else if (object instanceof Group)
		{
			o = new Group(((Group) object).get_objects(), ((Group) object).get_colors(), ((Group) object).get_fill(), ((Group) object).get_thickness());
			Drawer.temp_copy.add(o);
		}

		c = new Color(color.getRGB());
		Drawer.temp_copy.add(c);
		
		f = new Integer(fill);
		Drawer.temp_copy.add(f);
		
		th = new Integer(thickness);
		Drawer.temp_copy.add(th);
		
	}

}
