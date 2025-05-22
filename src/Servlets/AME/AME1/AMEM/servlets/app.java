package Servlets.AME.AME1.AMEM.servlets;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;

public class app extends Frame {
    private Closer Handler;
    
    app () throws Exception {
        Handler = new Closer ();
        setTitle ("Frame Example");
        setSize (900,620);
        addWindowListener (Handler);
        show ();
       
        
    }
 
    public void paint(Graphics s)
    {
    	Controller obj=new Controller();
 		Connection con; 
 		String userid="112",Office_id="",Office_Name="";
		String smonth="",syear="",html="",BEN_TYPE_ID="",qty="";
 		try {
 			con=obj.con();
 	 		obj.createStatement(con);
 			ResultSet rs=obj.getRS("select BEN_TYPE_DESC,BEN_TYPE_ID from PMS_DCB_BEN_TYPE where BEN_TYPE_ID in ( select BENEFICIARY_TYPE_ID from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+")");
 			int x,y;
 			x=100;
 			y=120;
 			while (rs.next())    
			{
				 String BEN_TYPE_DESC=rs.getString("BEN_TYPE_DESC");
				 BEN_TYPE_ID =rs.getString("BEN_TYPE_ID");
 				 s.drawRect(x, y, 10, Integer.parseInt(qty)/1000);
				 y=y+30;
			}	 
 		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static void main (String args[]) throws Exception {
        Frame f;
        f = new app ();
    }
}
       
class Closer extends WindowAdapter {
    public void windowClosing (WindowEvent event) {
        System.exit (0);
    }
}
