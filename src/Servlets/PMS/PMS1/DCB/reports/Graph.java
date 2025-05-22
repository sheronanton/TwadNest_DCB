package Servlets.PMS.PMS1.DCB.reports;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
public class Graph extends Frame implements ActionListener {
 
	private static final long serialVersionUID = 1L;
	Button b1;
	int x=0,y=0;
	public Graph() {
		// TODO Auto-generated constructor stub
		setLayout(null);
		b1 = new Button("Exit");
		add(b1);
		b1.setBounds(500, 30, 60, 30);
		b1.addActionListener(this);
	}
	public void paint(Graphics s) 
	{
		int c=360%5;
		int []value={54,10, 80, 120,150};
		Color []cc={Color.red,Color.green,Color.blue ,Color.yellow,Color.pink,Color.cyan};  
		int []eange={40,80,120,150,180};  
	  int mm=50;
		for (int i=0;i<5;i++)
		{
			mm=mm+30;			
			s.drawRect(mm,50,10,value[i] );
		}
		    // Draw the pie chart
	}   
	public static void main(String sr[]) {
		Graph s = new Graph();
		s.setVisible(true);
		s.setLocation(100, 100);
		s.setSize(700, 700);
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		wc c = new wc();
	}
	int i=1;
}
class wc extends WindowAdapter {
	wc() {
		System.exit(0);
	}
}
