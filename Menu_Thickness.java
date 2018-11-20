import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Menu_Thickness extends JMenu implements ActionListener {
	
	public Menu_Thickness()
	{
		super("Stroke Weight");
		
		JMenuItem th_1 = new JMenuItem("1");
		th_1.addActionListener(this);
		add(th_1);
		
		JMenuItem th_2 = new JMenuItem("2");
		th_2.addActionListener(this);
		add(th_2);
		
		JMenuItem th_3 = new JMenuItem("3");
		th_3.addActionListener(this);
		add(th_3);
		
		JMenuItem th_4 = new JMenuItem("4");
		th_4.addActionListener(this);
		add(th_4);
		
		JMenuItem th_5 = new JMenuItem("5");
		th_5.addActionListener(this);
		add(th_5);
		
		JMenuItem th_6 = new JMenuItem("6");
		th_6.addActionListener(this);
		add(th_6);
		
		JMenuItem th_7 = new JMenuItem("7");
		th_7.addActionListener(this);
		add(th_7);
		
		JMenuItem th_8 = new JMenuItem("8");
		th_8.addActionListener(this);
		add(th_8);
		
		JMenuItem th_9 = new JMenuItem("9");
		th_9.addActionListener(this);
		add(th_9);
		
		JMenuItem th_10 = new JMenuItem("10");
		th_10.addActionListener(this);
		add(th_10);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "1")
			Drawer.setThickness(1);
		
		else if(e.getActionCommand() == "2")
			Drawer.setThickness(2);
		
		else if(e.getActionCommand() == "3")
			Drawer.setThickness(3);
		
		else if(e.getActionCommand() == "4")
			Drawer.setThickness(4);
		
		else if(e.getActionCommand() == "5")
			Drawer.setThickness(5);
		
		else if(e.getActionCommand() == "6")
			Drawer.setThickness(6);
		
		else if(e.getActionCommand() == "7")
			Drawer.setThickness(7);
		
		else if(e.getActionCommand() == "8")
			Drawer.setThickness(8);
		
		else if(e.getActionCommand() == "9")
			Drawer.setThickness(9);
		
		else if(e.getActionCommand() == "10")
			Drawer.setThickness(10);
	}
}
