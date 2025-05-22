package Servlets.PMS.PMS1.DCB.reports;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class myf extends JFrame {

	 
	public myf()
	{
		draw d=new draw();
		add(new draw());
		
		 
	}
	
	   
}

class draw extends JPanel
{
	protected void paintComponent(Graphics s)
	{
		super.paintComponent(s);
		
		s.fillArc(0,0,300,300,0,50);
		s.setColor(Color.RED);
		s.fillArc(0,0,300,300,50,40);
		s.setColor(Color.GREEN);
		s.fillArc(0,0,300,300,90,40);
		
	}
}