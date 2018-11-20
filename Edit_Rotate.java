import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Edit_Rotate extends JMenu implements ActionListener {
	
	public Edit_Rotate()
	{
		super("Rotate");
		
		JMenuItem neg_90 = new JMenuItem("-90 degrees");
		neg_90.addActionListener(this);
		add(neg_90);
		
		JMenuItem neg_45 = new JMenuItem("-45 degrees");
		neg_45.addActionListener(this);
		add(neg_45);
		
		JMenuItem pos_45 = new JMenuItem(" 45 degrees");
		pos_45.addActionListener(this);
		add(pos_45);
		
		JMenuItem pos_90 = new JMenuItem(" 90 degrees");
		pos_90.addActionListener(this);
		add(pos_90);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		MainFrame.drawer.whichShape = 103;
		
		if(e.getActionCommand() == "-90 degrees") {
			Drawer.angle = -90;
			JOptionPane.showMessageDialog(null, "Click an object you want to rotate.\nRotate option: -90 degrees");
		}
		
		else if(e.getActionCommand() == "-45 degrees") {
			Drawer.angle = -45;
			JOptionPane.showMessageDialog(null, "Click an object you want to rotate.\nRotate option: -45 degrees");
		}
		
		else if(e.getActionCommand() == " 45 degrees") {
			Drawer.angle = 45;
			JOptionPane.showMessageDialog(null, "Click an object you want to rotate.\nRotate option: 45 degrees");
		}
		
		else if(e.getActionCommand() == " 90 degrees") {
			Drawer.angle = 90;
			JOptionPane.showMessageDialog(null, "Click an object you want to rotate.\nRotate option: 90 degrees");
		}
	}
	
	public static double distance (Point p1, Point p2)
	{
		double distance = Math.sqrt((p1.getX() - p2.getX())*(p1.getX() - p2.getX()) 
				+ (p1.getY() - p2.getY())*(p1.getY() - p2.getY()));
		
		return distance;
	}
	
	public static double distance (Point p1, double x2, double y2)
	{
		double distance = Math.sqrt((p1.getX() - x2)*(p1.getX() - x2) 
				+ (p1.getY() - y2)*(p1.getY() - y2));
		
		return distance;
	}
}
