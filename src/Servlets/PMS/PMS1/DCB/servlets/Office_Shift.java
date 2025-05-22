package Servlets.PMS.PMS1.DCB.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Office_Shift extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql=null;
		
		
		
		Controller obj=new Controller();
       
        try
        {
        	con=obj.con();
       	  System.out.println("Connected");
       		                        
        }
        catch(Exception e)
        {
             System.out.println("Exception in openeing connection:"+e);
        }
		
		
			
		String xml="";
		response.setContentType("text/xml");
		PrintWriter out=response.getWriter();
		String command=null;
		command=request.getParameter("command");
		
		if(command.equalsIgnoreCase("getdata"))
		{
			xml=xml+"<response><command>getdata</command>";
			int frm_off=Integer.parseInt(request.getParameter("frm_off"));
			try
			{
				sql="select * from COM_MST_OFFICES where OFFICE_ID=?";
				ps=con.prepareStatement(sql);
				ps.setInt(1,frm_off);
				rs=ps.executeQuery();
				while(rs.next())
				{
					xml=xml+"<off_name>"+rs.getString("OFFICE_NAME")+"</off_name>";
					xml=xml+"<off_level>"+rs.getString("OFFICE_LEVEL_ID")+"</off_level>";
					xml=xml+"<off_status>"+rs.getString("OFFICE_STATUS_ID")+"</off_status>";
				
				}
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
			}
			}
		
		
		
			
		
		
		
		else if(command.equalsIgnoreCase("loadgrid1"))
		{
			System.out.println("reached loadgrid1");
			xml=xml+"<response><command>loadgrid1</command>";
			int frm_off=Integer.parseInt(request.getParameter("frm_off"));
//			String name=request.getParameter("sname");
			System.out.println("name--->"+frm_off);
			
			try
			{
		//		PMS_DCB_MST_BENEFICIARY_METRE
	    //      sql="select distinct SUB_DIV_ID from PMS_DCB_MST_BENEFICIARY_METRE where office_id=? and METER_STATUS='L'";
		//		sql="select distinct SUB_DIV_ID, (select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID) as office_name from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID = ? and METER_STATUS='L' ";
		//		sql="select distinct SUB_DIV_ID , (select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID) as office_name , (select OFFICE_LEVEL_ID from COM_MST_OFFICES where OFFICE_ID=PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID) as OFFICE_LEVEL_ID , (select OFFICE_STATUS_ID from COM_MST_OFFICES where OFFICE_ID=PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID)as OFFICE_STATUS_ID from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID = ? and  METER_STATUS='L'";

				sql="select distinct scheme_sno, (select sch_name from pms_sch_master where sch_sno=PMS_DCB_MST_BENEFICIARY_METRE.scheme_sno) as sch_name from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID =? and    METER_STATUS='L' ";
				
				
				ps=con.prepareStatement(sql);
				ps.setInt(1,frm_off);
//				ps.setString(1, name);
				System.out.println("Query$$$"+sql);
				rs=ps.executeQuery();
				while(rs.next())
				{
	    			xml=xml+"<count><scheme_sno>"+rs.getString("scheme_sno").trim()+"</scheme_sno>";
					xml=xml+"<sch_name>"+rs.getString("sch_name").trim()+"</sch_name></count>";
					
				}
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}
//			System.out.println("xml--->"+xml);
		}
		else if(command.equalsIgnoreCase("loadgrid"))
		{
			System.out.println("reached loadgrid");
			xml=xml+"<response><command>loadgrid</command>";
			int frm_off=Integer.parseInt(request.getParameter("frm_off"));
//			String name=request.getParameter("sname");
			System.out.println("name--->"+frm_off);
			
			try
			{
		//		PMS_DCB_MST_BENEFICIARY_METRE
	    //      sql="select distinct SUB_DIV_ID from PMS_DCB_MST_BENEFICIARY_METRE where office_id=? and METER_STATUS='L'";
		//		sql="select distinct SUB_DIV_ID, (select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID) as office_name from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID = ? and METER_STATUS='L' ";
				sql="select distinct SUB_DIV_ID , (select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID) as office_name , (select OFFICE_LEVEL_ID from COM_MST_OFFICES where OFFICE_ID=PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID) as OFFICE_LEVEL_ID , (select OFFICE_STATUS_ID from COM_MST_OFFICES where OFFICE_ID=PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID)as OFFICE_STATUS_ID from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID = ? and  METER_STATUS='L'";

				ps=con.prepareStatement(sql);
				ps.setInt(1,frm_off);
//				ps.setString(1, name);
				System.out.println("Query$$$"+sql);
				rs=ps.executeQuery();
				while(rs.next())
				{
	    			xml=xml+"<count><SUB_DIV_ID>"+rs.getString("SUB_DIV_ID").trim()+"</SUB_DIV_ID>";
					xml=xml+"<OFFICE_NAME>"+rs.getString("OFFICE_NAME").trim()+"</OFFICE_NAME>";
					xml=xml+"<OFFICE_LEVEL_ID>"+rs.getString("OFFICE_LEVEL_ID").trim()+"</OFFICE_LEVEL_ID>";
					xml=xml+"<OFFICE_STATUS_ID>"+rs.getString("OFFICE_STATUS_ID").trim()+"</OFFICE_STATUS_ID></count>";
				}
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}
//			System.out.println("xml--->"+xml);
		}
		
		else if(command.equalsIgnoreCase("loadvalue"))
		{
			System.out.println("reached loadvalue");
			xml=xml+"<response><command>loadvalue</command>";
			int frm_off=Integer.parseInt(request.getParameter("frm_off"));
			int sub_div_id=Integer.parseInt(request.getParameter("sub_div_id"));

	//		System.out.println("frm_off--->"+frm_off);
	//		System.out.println("sub_div_id--->"+sub_div_id);

//			String name=request.getParameter("sname");
			
			
			try
			{
				sql="select distinct BENEFICIARY_SNO , (select BENEFICIARY_NAME from PMS_DCB_MST_BENEFICIARY where BENEFICIARY_SNO=PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_SNO and STATUS='L') as BENEFICIARY_NAME from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID =?  and sub_div_id=? and beneficiary_sno in (select distinct beneficiary_sno from PMS_DCB_MST_BENEFICIARY where status='L' and office_id=?)";

				ps=con.prepareStatement(sql);
				ps.setInt(1,frm_off);
				ps.setInt(2,sub_div_id);
				ps.setInt(3,frm_off);

				System.out.println("Query$$$"+sql);
				rs=ps.executeQuery();
				while(rs.next())
				{
					System.out.println("sucess");

	    			xml=xml+"<count><BENEFICIARY_SNO>"+rs.getString("BENEFICIARY_SNO").trim()+"</BENEFICIARY_SNO>";
					xml=xml+"<BENEFICIARY_NAME>"+rs.getString("BENEFICIARY_NAME").trim()+"</BENEFICIARY_NAME></count>";
					
				}
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}
			System.out.println("xml--->"+xml);
		}

		
		else if(command.equalsIgnoreCase("loadvalue1"))
		{
			System.out.println("reached loadvalue1");
			xml=xml+"<response><command>loadvalue1</command>";
			int frm_off=Integer.parseInt(request.getParameter("frm_off"));
			int sche_id=Integer.parseInt(request.getParameter("sche_id"));

//			String name=request.getParameter("sname");
			
			
			try
			{
				sql="select distinct beneficiary_sno, (select BENEFICIARY_NAME from PMS_DCB_MST_BENEFICIARY where BENEFICIARY_SNO=PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_SNO and STATUS='L') as Beneficiary_Name from PMS_DCB_MST_BENEFICIARY_METRE where office_id= ?  and scheme_sno=? and beneficiary_sno in (select distinct beneficiary_sno from PMS_DCB_MST_BENEFICIARY where status='L' and office_id=?) ";

				ps=con.prepareStatement(sql);
				ps.setInt(1,frm_off);
				ps.setInt(2,sche_id);
				ps.setInt(3,frm_off);

				System.out.println("Query$$$"+sql);
				rs=ps.executeQuery();
				while(rs.next())
				{
	    			xml=xml+"<count><BENEFICIARY_SNO>"+rs.getString("BENEFICIARY_SNO").trim()+"</BENEFICIARY_SNO>";
					xml=xml+"<BENEFICIARY_NAME>"+rs.getString("BENEFICIARY_NAME").trim()+"</BENEFICIARY_NAME></count>";
					
				}
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}
			System.out.println("xml--->"+xml);
		}

		
		out.write(xml);
		}

	
		
		
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
