package Servlets.PMS.PMS1.DCB.reports;

import java.applet.Applet;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import Servlets.PMS.PMS1.DCB.servlets.Controller;

public class diagram extends Applet {
	Controller obj;
	String benname="TEST";
	public ResultSet rs;
	int y=10;
	String str[] = new String[50];
	public void init() {
		
		try {
		obj = new Controller();
		Connection con=obj.con();
		obj.createStatement(con);
		String ben=getParameter("ben");
		ben="1476";
		
			
			
			rs=obj.getRS("SELECT REGION_OFFICE_ID,OFFICE_LEVEL_ID,OFFICE_ID,OFFICE_NAME FROM COM_MST_ALL_OFFICES_VIEW " +
					" WHERE OFFICE_LEVEL_ID='DN'  AND OFFICE_ID       IN  " +
					" (SELECT OFFICE_ID FROM PMS_DCB_DIV_DIST_MAP )" );
			
			//String val=obj.getValue("PMS_DCB_MST_BENEFICIARY", "BENEFICIARY_NAME", "where BENEFICIARY_SNO="+ben);
			//benname=val;
			int i=0;
			while (rs.next())
			{
			 
				benname=rs.getString("OFFICE_NAME") ;
				str[i]=benname;
				i++;
				
			}
			repaint();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public void paint(Graphics s)
	{
		 
		int j=0;
			 while (str[j]!=null)
			 {
				 y=y+20;
				s.drawString(str[j], 122, y);
				j++;
			 }
		 
	}
}
