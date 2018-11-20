import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Edit_Redo extends JMenuItem implements ActionListener {
	
	static Vector<Object> object = null;
	static Vector<Color> color = null;
	static Vector<Integer> fill = null;
	static Vector<Integer> thickness = null;
	
	public Edit_Redo()
	{
		super("Redo");
		addActionListener(this);
		
		object = new Vector<Object>();
		color = new Vector<Color>();
		fill = new Vector<Integer>();
		thickness = new Vector<Integer>();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "Redo") {
			JOptionPane.showMessageDialog(null, "Redo!");
			MainFrame.drawer.redo(Drawer.getWhichShape());
		}
	}
	
	public static void redo_save (Object in_object, Color in_color, Integer in_fill, 
			Integer in_thickness)
	{
		object.add(in_object);
		color.add(in_color);
		fill.add(in_fill);
		thickness.add(in_thickness);
	}
	
	public static void redo_last (Vector<Object> in_object, Vector<Color> in_color, Vector<Integer> in_fill, 
			Vector<Integer> in_thickness)
	{
		int last_index = object.size() - 1;
		
		in_object.add(object.get(last_index));
		in_color.add(color.get(last_index));
		in_fill.add(fill.get(last_index));
		in_thickness.add(thickness.get(last_index));
		
		object.setSize(last_index);
		color.setSize(last_index);
		fill.setSize(last_index);
		thickness.setSize(last_index);
	}
	
	public void redo_reset (Vector<Object> in_object, Vector<Color> in_color, Vector<Integer> in_fill, 
			Vector<Integer> in_thickness)
	{
		object = new Vector<Object>();
		color = new Vector<Color>();
		fill = new Vector<Integer>();
		thickness = new Vector<Integer>();
	}

}
