import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JMenuItem;

public class File_Save extends JMenuItem implements ActionListener {

	public File_Save()
	{
		super("Save");
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "Save")
		{

		}
	}
}
