package Servlets.PMS.PMS1.DCB.servlets;
import java.sql.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
public class app extends Applet implements WindowListener {
    private Closer Handler;
  public void init ()   {
        Handler = new Closer ();
    }
    public void paint(Graphics s)
    {
    	Controller obj=new Controller();
 		Connection con; 
 		String userid="112",Office_id="5982",Office_Name="";
		String smonth="2",syear="2012",html="",BEN_TYPE_ID="",qty="",wcamt="0";
 		try {
 			con=obj.con();
 	 		obj.createStatement(con);
 			ResultSet rs=obj.getRS("select BEN_TYPE_DESC,BEN_TYPE_ID from PMS_DCB_BEN_TYPE where BEN_TYPE_ID in ( select BENEFICIARY_TYPE_ID from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+")");
 			int x,y;
 			x=160;
 			y=80;
 			int tx=0;
 			int txt1=0;
 			int txt2=0;
 			int txt3=0;
 			CallableStatement proc_stmt = null;
 			 s.setFont(new Font("arial",Font.BOLD,12)); 
 			String office_name="xc";
 			 s.setColor(Color.orange);
 			 s.drawString("Pumping Return Qty", 600, 60);
 			s.setColor(Color.CYAN);
			 s.drawString("Receipt(FAS)", 1000, 60);
			 s.setColor(new Color(200,105,200));
 			 s.drawString("Water Charges(Demand)", 1100,60);
 			 s.setColor(Color.BLACK);
 			while (rs.next())    
			{
				 String BEN_TYPE_DESC=rs.getString("BEN_TYPE_DESC");
				 BEN_TYPE_ID =rs.getString("BEN_TYPE_ID");
				 qty=obj.getValue("PMS_DCB_TRN_MONTHLY_PR","sum(QTY_CONSUMED_NET) ","qty"," where BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where OFFICE_ID="+Office_id+" and STATUS='L' and BENEFICIARY_TYPE_ID="+BEN_TYPE_ID+") and  OFFICE_ID="+Office_id+" and year="+syear+" and month="+smonth+" group by year,month");
				 tx=Integer.parseInt(qty);
				 txt1=x+Integer.parseInt(qty)/1000; 
				 	proc_stmt = con.prepareCall("{call PMS_DCB_TYPE_WISE_COLLECTIN (?,?,?,?,?) }");
					proc_stmt.setInt(1, Integer.parseInt(smonth));
					proc_stmt.setInt(2, Integer.parseInt(syear));
					proc_stmt.setInt(3, Integer.parseInt(Office_id));
					proc_stmt.setInt(4, Integer.parseInt(BEN_TYPE_ID));
					proc_stmt.registerOutParameter(5,java.sql.Types.VARCHAR);
					proc_stmt.execute();  
					String Tot_Amt = obj.isNull(proc_stmt.getString(5), 1);
				 s.setFont(new Font("arial",Font.BOLD,12)); 
				 s.drawString(BEN_TYPE_DESC, 10, y+15);
				// s.fillRoundRect(x,y, Integer.parseInt(qty)/1000, 10,10,10);
				 s.setColor(Color.orange);
				 s.fill3DRect(x,y,Integer.parseInt(qty)/1000,10,true);
				 s.drawString(Integer.toString((Integer.parseInt(qty)))+" KL", txt1+3, y+10);
				 s.setColor(Color.BLACK);
				 s.setColor(Color.CYAN);
				 s.fill3DRect(x,y+15,Integer.parseInt(Tot_Amt)/1000,10,true);
				 txt2=x+Integer.parseInt(Tot_Amt)/1000;
				 s.setColor(Color.BLACK);
				 s.drawString("Rs. "+Integer.toString((Integer.parseInt(Tot_Amt))), txt2+3, y+25);
				 s.setColor(Color.BLACK);
				 s.setColor(new Color(200,105,200));
				 wcamt=obj.isNull(obj.getValue("PMS_DCB_WC_BILLING","sum(TOTAL_AMT)","calamt", "where BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where OFFICE_ID="+Office_id+" and STATUS='L' and BENEFICIARY_TYPE_ID="+BEN_TYPE_ID+") and  OFFICE_ID="+Office_id+" and year="+syear+" and month="+smonth),1);
				 s.fill3DRect(x,y+30,Integer.parseInt(wcamt)/1000,10,true);
				 s.setColor(Color.BLACK);
				 txt3=x+Integer.parseInt(wcamt)/1000;
				 s.drawString("Rs. "+Integer.toString((Integer.parseInt(wcamt))), txt3+3, y+40);
				 s.setColor(Color.BLACK);
				 y=y+50;  
			}	     
 		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
    }
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
	}
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
	}
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
	}
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
	}
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
	}
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
	}
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
	}
}
class Closer extends WindowAdapter {
    public void windowClosing (WindowEvent event) {
        System.exit (0);
    }
}
