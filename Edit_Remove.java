import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Edit_Remove extends JMenuItem implements ActionListener {
	
	public Edit_Remove()
	{
		super("Remove");
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "Remove") {
			JOptionPane.showMessageDialog(null, "Cliek an object you want to remove.");
			MainFrame.drawer.whichShape = 102;
		}
	}

}
