import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Edit_Move extends JMenuItem implements ActionListener {
	
	public Edit_Move()
	{
		super("Move");
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "Move") {
			JOptionPane.showMessageDialog(null, "Move Mode On!");
			MainFrame.drawer.whichShape = 100;
		}
	}

}
