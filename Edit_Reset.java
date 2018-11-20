import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Edit_Reset extends JMenuItem implements ActionListener {
	
	public Edit_Reset()
	{
		super("Reset");
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "Reset") {
			JOptionPane.showMessageDialog(null, "Reset!");
			MainFrame.drawer.reset();
		}
	}
}
