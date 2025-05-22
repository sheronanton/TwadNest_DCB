package Servlets.PMS.PMS1.DCB.servlets;

import java.awt.Frame;

public class samplewin extends Frame 
{
	
	public samplewin(String v)
	{
		super(v);		
	}
	
	public void view()
	{
		samplewin p=new samplewin("test");
		p.setVisible(true);
		
	}

}
