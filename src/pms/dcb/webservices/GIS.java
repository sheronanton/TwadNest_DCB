package pms.dcb.webservices;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import Servlets.PMS.PMS1.DCB.servlets.Controller;
public class GIS {
	public ArrayList ben_type(String qry)
	{
			ArrayList val=new ArrayList();
			Connection con;
			Controller obj,obj1;
			ResultSet rs=null;
			ResultSet rs1=null;
			try
			{
				obj=new Controller();
				obj1=new Controller();
				con=obj.con();    
				
				obj.createStatement(con);
				obj1.createStatement(con);
				System.out.println(" TEST " + qry);
				rs1=obj.getRS(qry);		
				   		
				ResultSetMetaData rsmd = rs1.getMetaData();
				int c= rsmd.getColumnCount();
				int i=0;
				rs=obj1.getRS(qry);
				while(rs.next())
				{
					for(i=0;i<c;i++)
					{   
						val.add(rs.getString(i+1));
					}
				} 
				con.close();	
			}catch(Exception e)
			{
				System.out.println(e);
				
			}			
			con=null;
			obj=null;  
			return val;
	}
}
