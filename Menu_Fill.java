import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBoxMenuItem;

public class Menu_Fill extends JCheckBoxMenuItem implements ActionListener {
	
	public Menu_Fill()
	{
		super("Fill");
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(getState() == true)
			Drawer.fillMode = 1;
		
		else	
			Drawer.fillMode = 0;
	}

}
