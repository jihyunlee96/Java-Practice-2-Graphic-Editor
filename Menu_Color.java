import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Menu_Color extends JMenu implements ActionListener {
	
	public Menu_Color()
	{
		super("Color");
		
		JMenuItem black = new JMenuItem("Black");
		black.setForeground(Color.BLACK);
		black.addActionListener(this);
		add(black);
		
		JMenuItem red = new JMenuItem("Red");
		red.setForeground(Color.RED);
		red.addActionListener(this);
		add(red);
		
		JMenuItem orange = new JMenuItem("Orange");
		orange.setForeground(Color.ORANGE);
		orange.addActionListener(this);
		add(orange);
		
		JMenuItem yellow = new JMenuItem("Yellow");
		yellow.setForeground(Color.YELLOW);
		yellow.addActionListener(this);
		add(yellow);
		
		JMenuItem green = new JMenuItem("Green");
		green.setForeground(Color.GREEN);
		green.addActionListener(this);
		add(green);
		
		JMenuItem cyan = new JMenuItem("Cyan");
		cyan.setForeground(Color.CYAN);
		cyan.addActionListener(this);
		add(cyan);
		
		JMenuItem blue = new JMenuItem("Blue");
		blue.setForeground(Color.BLUE);
		blue.addActionListener(this);
		add(blue);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "Black")
			Drawer.setColor(Color.BLACK);
			
		else if(e.getActionCommand() == "Red")
			Drawer.setColor(Color.RED);
		
		else if(e.getActionCommand() == "Orange")
			Drawer.setColor(Color.ORANGE);
		
		else if(e.getActionCommand() == "Yellow")
			Drawer.setColor(Color.YELLOW);
		
		else if(e.getActionCommand() == "Green")
			Drawer.setColor(Color.GREEN);
		
		else if(e.getActionCommand() == "Cyan")
			Drawer.setColor(Color.CYAN);
		
		else if(e.getActionCommand() == "Blue")
			Drawer.setColor(Color.BLUE);
			
	}
}
