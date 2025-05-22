package Servlets.PMS.PMS1.DCB.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Add_Demand extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con=null;
		PreparedStatement ps=null,ps1=null,ps2=null;
		ResultSet rs=null,rs1=null;
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
		System.out.println("command is "+command);

		if(command.equalsIgnoreCase("Add_Demand"))
		{
			xml=xml+"<response><command>Add_Demand</command>";
			int year=Integer.parseInt(request.getParameter("year"));
			int month=Integer.parseInt(request.getParameter("month"));
			int off_id=Integer.parseInt(request.getParameter("off_id"));
			int prv_month=Integer.parseInt(request.getParameter("prv_month"));
			int prv_year=Integer.parseInt(request.getParameter("prv_year"));
			String ben_sno,sel_qry;
			int	ADD_DMD_PRV_UPTO=0,ADD_DMD_WC_PRV=0,ADD_DMD_UPTO=0;
			try
			{
				ps=con.prepareStatement(" select distinct BENEFICIARY_SNO from PMS_DCB_LEDGER_ACTUAL where  MONTH="+month+" and YEAR="+year+"and OFFICE_ID="+off_id);
				
				System.out.println("Query$$$"+ps);
				rs=ps.executeQuery();
				System.out.println("stage 1");
				while(rs.next())
				{
					ben_sno=rs.getString("BENEFICIARY_SNO");
				 sel_qry="select SCH_SNO from PMS_DCB_LEDGER_ACTUAL where MONTH="+month+" and YEAR="+year+"and OFFICE_ID="+off_id+" and BENEFICIARY_SNO="+ben_sno;

		       //		sel_qry="select  SCH_SNO,OFFICE_ID, BENEFICIARY_SNO from PMS_DCB_LEDGER_ACTUAL  where   ROWNUM <=1 and MONTH="+month_var+" and YEAR="+year_var+" "+contqry +" and BENEFICIARY_SNO="+ben_sno_other+"  ";
		       		ps1=con.prepareStatement(sel_qry);
		       		rs1=ps1.executeQuery();
		       		
		       		while (rs1.next()) 
		       		{	     
		       			String schsno_other=rs1.getString("SCH_SNO");        			
		       		//	String off=rs1.getString("OFFICE_ID");
		       			System.out.println("Scheme is "+schsno_other+"and beneficiary"+ben_sno);
		       			String qry_new1="Select ADD_DMD,ADD_DMD_UPTO from PMS_DCB_LEDGER_ACTUAL where MONTH="+prv_month+" and SCH_SNO="+schsno_other+" and OFFICE_ID="+off_id+" and YEAR="+prv_year+" and BENEFICIARY_SNO="+ben_sno;
		       			ps2=con.prepareStatement(qry_new1);
		       			ResultSet rs_new1=ps2.executeQuery();
		       		//	ResultSet rs_new1=obj.getRS(qry_new1);
					//	System.out.println("stage 2");
		       			while (rs_new1.next())
						{	
							ADD_DMD_PRV_UPTO = rs_new1.getInt("ADD_DMD_UPTO");				
							 ADD_DMD_WC_PRV =  rs_new1.getInt("ADD_DMD");       		
							ADD_DMD_UPTO = ADD_DMD_PRV_UPTO + ADD_DMD_WC_PRV ;
						}
						rs_new1.close();
						String	upd="update PMS_DCB_LEDGER_ACTUAL set ADD_DMD_UPTO="+ADD_DMD_UPTO+"where  MONTH="+month+" and   OFFICE_ID="+off_id+" and YEAR="+year+" and SCH_SNO="+schsno_other +" and  BENEFICIARY_SNO="+ben_sno;

					//	String	upd="update PMS_DCB_LEDGER_ACTUAL set ADD_DMD_UPTO="+(Double.parseDouble(ADD_DMD_PRV_UPTO)+Double.parseDouble(ADD_DMD_WC_PRV))+"where  MONTH="+month+" and   OFFICE_ID="+off+" and YEAR="+year+" and SCH_SNO="+schsno_other +" and  BENEFICIARY_SNO="+ben_sno;
							        
							ps=null;  
							try    
							{			
								ps=con.prepareStatement(upd);	 
								ps.execute();
							}catch(Exception e)   
							{
								System.out.println("Error "+e+" --- At final update");
							}
							System.out.println("updddddddddddddddddddddddddddddddd"+upd);
			       		}  
			       		rs1.close();
			       	}
				rs.close();
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}
			}
		
		if(command.equalsIgnoreCase("getval1"))
		{
			xml=xml+"<response><command>getval1</command>";
			int off_id=Integer.parseInt(request.getParameter("off_id"));
			int month=Integer.parseInt(request.getParameter("month"));
			int year=Integer.parseInt(request.getParameter("year"));
			
			try
			{
				sql="select sum(ADD_DMD_UPTO+ADD_DMD) as total from PMS_DCB_LEDGER_ACTUAL where OFFICE_ID=? and month=? and  YEAR=?";
				ps=con.prepareStatement(sql);
				ps.setInt(1,off_id);
				ps.setInt(2,month);
				ps.setInt(3,year);
				System.out.println("Query$$$"+sql);
				System.out.println("off_id$$$"+off_id);
				System.out.println("month$$$"+month);
				System.out.println("year$$$"+year);
				rs=ps.executeQuery();
				while(rs.next())
				{
					xml=xml+"<count><total>"+rs.getString("total")+"</total></count>";
					
				}
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}
			}
		if(command.equalsIgnoreCase("check"))
		{
			xml=xml+"<response><command>check</command>";
			
			
			try
			{
				sql="update PMS_DCB_MST_BENEFICIARY set BENEFICIARY_TYPE_ID_SUB=BENEFICIARY_TYPE_ID where BENEFICIARY_TYPE_ID_SUB is null";
				ps=con.prepareStatement(sql);
				System.out.println("Query$$$"+sql);
				
				rs=ps.executeQuery();
				
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}
			}
		if(command.equalsIgnoreCase("getval11"))
		{
			xml=xml+"<response><command>getval11</command>";
		//	int off_id=Integer.parseInt(request.getParameter("off_id"));
			int month=Integer.parseInt(request.getParameter("month"));
			int year=Integer.parseInt(request.getParameter("year"));
			
			try
			{
				sql="select sum(ADD_DMD_UPTO+ADD_DMD) as total from PMS_DCB_LEDGER_ACTUAL where  month=? and  YEAR=?";
				ps=con.prepareStatement(sql);
			//	ps.setInt(1,off_id);
				ps.setInt(1,month);
				ps.setInt(2,year);
				System.out.println("Query$$$"+sql);
			//	System.out.println("off_id$$$"+off_id);
				System.out.println("month$$$"+month);
				System.out.println("year$$$"+year);
				rs=ps.executeQuery();
				while(rs.next())
				{
					xml=xml+"<count><total>"+rs.getString("total")+"</total></count>";
					
				}
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}
			}
		
		
				System.out.println("xml--->"+xml);
			
			out.write(xml);
		}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
