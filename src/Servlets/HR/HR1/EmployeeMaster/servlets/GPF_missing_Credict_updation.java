package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

public class GPF_missing_Credict_updation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";  
   
   public GPF_missing_Credict_updation() {
       super();
      
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Connection con=null;
		 
  	  System.out.println("Updation/Validation");
  	 ResultSet rs=null,rs1=null,rs2=null;
     CallableStatement cs=null;
     PreparedStatement ps=null,ps1=null,ps2=null;
     String html="";
    	     
   
     response.setContentType(CONTENT_TYPE);
     PrintWriter out = response.getWriter();
     response.setHeader("Cache-Control","no-cache");  
  	HttpSession session=request.getSession(false);
    try
    {
            if(session==null)
            {
            	response.sendRedirect(request.getContextPath()+"/index.jsp?message=sessionout");
                    return;

                }
             

    }catch(Exception e)
    {
          e.printStackTrace();
          
    }
	        try{
	        	LoadDriver driver=new LoadDriver();
	        	con=driver.getConnection();
	                    
	        }
	        catch(Exception e){
	        	System.out.println("Exception in opening connection :"+e); 
	        }
	       
	        
	      
	        String command;
	        command = request.getParameter("command")!=null?request.getParameter("command"):"";
	        int journalId= 0;
	        int i=0;
	        if(command.equalsIgnoreCase("get")){
	        try {
	        	  journalId= Integer.parseInt(request.getParameter("jid").trim());
	        	System.out.println("-----enter servlet");
				String month []={"Jan","Feb","Mar","April","May","June","July","Aug","Sep","Oct","Nov","Dec"};
				 
				 ps1=con.prepareStatement("select ASSOCIATE_GPF_NO,ASSOCIATE_AC_HEADCODE,ASSOCIATE_TRANS_TYPE,AMOUNT,RELATIVE_MONTH,RELATIVE_YEAR,PROCESS_FLOW_STATUS_ID,FUND_CATEGORY,INT_CALCULATED_YEAR,INT_CALCULATED_MONTH,SLIP_PRINT_UNDER,INST_NO,TOT_INST" +
				 		"  from HRM_GPF_MISSING_CR_DB_TRN where HO_JRNL_MST_ID=? ");
			
			 ps1.setInt(1, journalId);
			 rs1=ps1.executeQuery(); 
			 html=html+"<table cellspacing='3' cellpadding='2' border='1' width='100%'> <tr class='tdH'><th>select</th><th>GPF.NO/SPL AC Head Code</th><th>Type</th><th>Amount" +
		 		"</th><th>Relating Month</th><th>Relating Year</th><th>Interest Calculated Month</th><th> Interest Calculated Year</th></tr>";
			 while(rs1.next()) {
				 System.out.println("------------"+i);
				
				 if((rs1.getString("PROCESS_FLOW_STATUS_ID")!=null?rs1.getString("PROCESS_FLOW_STATUS_ID"):"").equalsIgnoreCase("FR"))
				 {
					
				 }
				 else{
					 i++;
					 if(rs1.getInt("ASSOCIATE_AC_HEADCODE")==0)
					 {
				 html=html+"<tr><td ><a href='javascript:viewjournal("+i+");'>Edit</a><input type=hidden id=id"+i+" name=id"+i+" value="+journalId+" />" +
			 		"<input type=hidden id=type"+i+" name=type"+i+" value="+rs1.getString("ASSOCIATE_TRANS_TYPE")+" />" +
			 				"<input type=hidden id=amt"+i+" name=amt"+i+" value="+rs1.getString("AMOUNT")+" />" +
			 						"<input type=hidden id=gpf"+i+" name=gpf"+i+" value="+rs1.getString("ASSOCIATE_GPF_NO")+" />" +
			 								"<input type=hidden id=year"+i+" name=year"+i+" value="+rs1.getString("RELATIVE_YEAR")+" />" +
			 										"<input type=hidden id=month"+i+" name=month"+i+" value="+rs1.getString("RELATIVE_MONTH")+" />" +
			 										"<input type=hidden id=intyear"+i+" name=intyear"+i+" value="+rs1.getString("INT_CALCULATED_YEAR")+" />" +
			 										"<input type=hidden id=intmonth"+i+" name=intmonth"+i+" value="+rs1.getString("INT_CALCULATED_MONTH")+" />" +
			 										
			 										"<input type=hidden id=SLIP_PRINT_UNDER"+i+" name=SLIP_PRINT_UNDER"+i+" value="+rs1.getString("SLIP_PRINT_UNDER")+" />" +
			 										"<input type=hidden id=INST_NO"+i+" name=INST_NO"+i+" value="+rs1.getString("INST_NO")+" />" +
			 										"<input type=hidden id=TOT_INST"+i+" name=TOT_INST"+i+" value="+rs1.getString("TOT_INST")+" />" +
			 									
			 								"<input type=hidden id=category"+i+" name=category"+i+" value="+rs1.getString("FUND_CATEGORY")+" />" +
			 								"<input type=hidden id=select"+i+" name=select"+i+" value=1 />" +
			 										"</td>";
				 if(Integer.parseInt(rs1.getString("INT_CALCULATED_YEAR"))==0)
			 html=html+"<td>"+rs1.getString("ASSOCIATE_GPF_NO")+
			 "</td><td>"+rs1.getString("ASSOCIATE_TRANS_TYPE")+"</td><td>"+rs1.getString("AMOUNT")+
			 "</td><td>"+month [rs1.getInt("RELATIVE_MONTH")-1]+"</td><td>"+rs1.getString("RELATIVE_YEAR")+"</td><td></td><td></td></tr>";
				 else
					 html=html+"<td>"+rs1.getString("ASSOCIATE_GPF_NO")+
					 "</td><td>"+rs1.getString("ASSOCIATE_TRANS_TYPE")+"</td><td>"+rs1.getString("AMOUNT")+
					 "</td><td>"+month [rs1.getInt("RELATIVE_MONTH")-1]+"</td><td>"+rs1.getString("RELATIVE_YEAR")+"</td><td>"+month [rs1.getInt("INT_CALCULATED_MONTH")-1]+"</td><td>"+rs1.getString("INT_CALCULATED_YEAR")+"</td></tr>";
						
					 }
					 else
					 {
						 html=html+"<tr><td ><a href='javascript:viewjournal("+i+");'>Edit</a><input type=hidden id=id"+i+" name=id"+i+" value="+journalId+" />" +
					 		"<input type=hidden id=type"+i+" name=type"+i+" value="+rs1.getString("ASSOCIATE_TRANS_TYPE")+" />" +
					 				"<input type=hidden id=amt"+i+" name=amt"+i+" value="+rs1.getString("AMOUNT")+" />" +
					 						"<input type=hidden id=head"+i+" name=head"+i+" value="+rs1.getString("ASSOCIATE_AC_HEADCODE")+" />" +
					 								"<input type=hidden id=year"+i+" name=year"+i+" value="+rs1.getString("RELATIVE_YEAR")+" />" +
			 										"<input type=hidden id=month"+i+" name=month"+i+" value="+rs1.getString("RELATIVE_MONTH")+" />" +
			 										"<input type=hidden id=intyear"+i+" name=intyear"+i+" value="+rs1.getString("INT_CALCULATED_YEAR")+" />" +
			 										"<input type=hidden id=intmonth"+i+" name=intmonth"+i+" value="+rs1.getString("INT_CALCULATED_MONTH")+" />" +
			 										"<input type=hidden id=SLIP_PRINT_UNDER"+i+" name=SLIP_PRINT_UNDER"+i+" value="+rs1.getString("SLIP_PRINT_UNDER")+" />" +
			 										"<input type=hidden id=INST_NO"+i+" name=INST_NO"+i+" value="+rs1.getString("INST_NO")+" />" +
			 										"<input type=hidden id=TOT_INST"+i+" name=TOT_INST"+i+" value="+rs1.getString("TOT_INST")+" />" +
			 									
			 								"<input type=hidden id=category"+i+" name=category"+i+" value="+rs1.getString("FUND_CATEGORY")+" />" +
			 								"<input type=hidden id=select"+i+" name=select"+i+" value=0 /></td>";
					 html=html+"<td>"+rs1.getString("ASSOCIATE_AC_HEADCODE")+
					 "</td><td>"+rs1.getString("ASSOCIATE_TRANS_TYPE")+"</td><td>"+rs1.getString("AMOUNT")+
					 "</td><td>"+month [rs1.getInt("RELATIVE_MONTH")-1]+"</td><td>"+rs1.getString("RELATIVE_YEAR")+"</td><td></td><td></td></tr>";	 
					 }
				 }
			 }
			 html=html+"<tr><td><input type=hidden id='row' name='row' value="+i+" /></td></tr></table>";
			 System.out.println(html);
			 if(i==0)
			 {
				 html="<center>No data to display</center>"; 
			 }
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
	        }
	        else if(command.equalsIgnoreCase("checkbalnce"))
	        {
	        	 journalId= Integer.parseInt(request.getParameter("jid").trim());
	        	 int cr=0,db=0;
	        	try{
	        		ps=con.prepareStatement("select AMOUNT,RELATIVE_YEAR,RELATIVE_MONTH,MISSING_TRANS_TYPE," +
		        			"PROCESS_FLOW_STATUS_ID,JOURNAL_REF_NO from HRM_GPF_MISSING_CR_DB_MST " +
		        			"where HO_JRNL_MST_ID=? ");
			
			 ps.setInt(1, journalId);
			 rs=ps.executeQuery(); 
			  
			
			 while(rs.next()) {
				if(rs.getString("MISSING_TRANS_TYPE").equalsIgnoreCase("CR"))
						{
					cr=cr+rs.getInt("AMOUNT");
						}
				else
				{
					db=db+rs.getInt("AMOUNT");
				}
			 }
			 ps.close();
			 rs.close();
			 ps=con.prepareStatement("select ASSOCIATE_GPF_NO,ASSOCIATE_TRANS_TYPE,AMOUNT,RELATIVE_MONTH,RELATIVE_YEAR,PROCESS_FLOW_STATUS_ID" +
				 		" from HRM_GPF_MISSING_CR_DB_TRN where HO_JRNL_MST_ID=? ");
		
		 ps.setInt(1, journalId);
		 rs=ps.executeQuery(); 
		  
		
		 while(rs.next()) {
			if(rs.getString("ASSOCIATE_TRANS_TYPE").equalsIgnoreCase("CR"))
					{
				cr=cr+rs.getInt("AMOUNT");
					}
			else
			{
				db=db+rs.getInt("AMOUNT");
			}
		 }
		 
		 if(cr==db)
		 {
			 html=html+"<response><status>balance</status></response>"; 
		 }
		 else
		 {
			 html=html+"<response><status>Notbalance</status></response>";
		 }
	        	}
	        	catch (Exception e) {
					e.printStackTrace();
				}
	        }
	        
	        else if(command.equalsIgnoreCase("get1"))
	        {
	        	 journalId= Integer.parseInt(request.getParameter("jid").trim());
	        	try
	        	{
	        		
	        	ps=con.prepareStatement("select GPF_NO,AMOUNT,RELATIVE_YEAR,RELATIVE_MONTH," +
	        			"MISSING_TRANS_TYPE,PROCESS_FLOW_STATUS_ID,JOURNAL_REF_NO,emp_name,JOURNAL_REF_DATE,REMARKS,FUND_CATEGORY,INT_CALCULATED_YEAR,INT_CALCULATED_MONTH,SLIP_PRINT_UNDER,INST_NO,TOT_INST  " +
	        			"from (select GPF_NO,AMOUNT,RELATIVE_YEAR,RELATIVE_MONTH,MISSING_TRANS_TYPE," +
	        			"PROCESS_FLOW_STATUS_ID,JOURNAL_REF_NO,TO_CHAR(JOURNAL_REF_DATE,'dd/MM/yyyy') as JOURNAL_REF_DATE,REMARKS,FUND_CATEGORY,INT_CALCULATED_YEAR,INT_CALCULATED_MONTH,SLIP_PRINT_UNDER,INST_NO,TOT_INST from HRM_GPF_MISSING_CR_DB_MST " +
	        			"where HO_JRNL_MST_ID=?) a left outer join (select EMPLOYEE_ID,EMPLOYEE_NAME ||'.'|| " +
	        			"EMPLOYEE_INITIAL as emp_name from HRM_MST_EMPLOYEES) b on a.GPF_NO=b.EMPLOYEE_ID ");
		
		 ps.setInt(1, journalId);
		 rs=ps.executeQuery(); 
		 				 
		
		 while(rs.next()) {
			 i++;
			 if((rs.getString("PROCESS_FLOW_STATUS_ID")!=null?rs.getString("PROCESS_FLOW_STATUS_ID"):"").equalsIgnoreCase("FR"))
			 {
				 html=html+"<response><status>validated</status></response>";
			 }
			 else
			 {
				 html=html+"<response><status>success</status><GPF_NO>"+rs.getString("GPF_NO")+"</GPF_NO> "+
				 		"<MISSING_TRANS_TYPE>"+rs.getString("MISSING_TRANS_TYPE")+"</MISSING_TRANS_TYPE>" +
				 		"<AMOUNT>"+rs.getString("AMOUNT")+"</AMOUNT><RELATIVE_MONTH>"+rs.getString("RELATIVE_MONTH")+"</RELATIVE_MONTH>" +
				 		"<RELATIVE_YEAR>"+rs.getString("RELATIVE_YEAR")+"</RELATIVE_YEAR>" +
				 				"<Interest_MONTH>"+rs.getString("INT_CALCULATED_MONTH")+"</Interest_MONTH>" +
				 		"<Interest_YEAR>"+rs.getString("INT_CALCULATED_YEAR")+"</Interest_YEAR><JOURNAL_REF_NO>"+rs.getString("JOURNAL_REF_NO")+"</JOURNAL_REF_NO>" +
				 		"<emp_name>"+rs.getString("emp_name")+"</emp_name><JOURNAL_REF_DATE>"+rs.getString("JOURNAL_REF_DATE")+"</JOURNAL_REF_DATE>" +
				 				"<REMARKS>"+rs.getString("REMARKS")+"</REMARKS>" +
				 				"<SLIP_PRINT_UNDER>"+rs.getString("SLIP_PRINT_UNDER")+"</SLIP_PRINT_UNDER>" +
				 				"<INST_NO>"+rs.getString("INST_NO")+"</INST_NO>" +
				 				"<TOT_INST>"+rs.getString("TOT_INST")+"</TOT_INST>" +
				 						"<FUND_CATEGORY>"+rs.getString("FUND_CATEGORY")+"</FUND_CATEGORY></response>";
				 
			 }
		 }
		 }
	        	catch (Exception e) {
					e.printStackTrace();
				}
	        }
	        else if(command.equalsIgnoreCase("view"))
	        {
	        	int journalId1=Integer.parseInt(request.getParameter("journalId").trim());
	        	String type=request.getParameter("type");
	        	String amt=request.getParameter("amt");
	        	String gpf=request.getParameter("gpf")!=null?request.getParameter("gpf"):"";
	        	String head=request.getParameter("head")!=null?request.getParameter("head"):"";
	        		try {
	        			if(gpf.length()>0)
	        			{
	        				ps1=con.prepareStatement("select ASSOCIATE_GPF_NO,ASSOCIATE_TRANS_TYPE,AMOUNT,RELATIVE_MONTH,RELATIVE_YEAR,INT_CALCULATED_YEAR,INT_CALCULATED_MONTH,SLIP_PRINT_UNDER,INST_NO,TOT_INST " +
					 		" from HRM_GPF_MISSING_CR_DB_TRN where HO_JRNL_MST_ID=? and ASSOCIATE_GPF_NO=? and AMOUNT=? and ASSOCIATE_TRANS_TYPE=? ");
								 ps1.setInt(1, journalId1);
								 ps1.setString(2, gpf);
								 ps1.setString(3, amt);
								 ps1.setString(4, type);
								 rs1=ps1.executeQuery(); 
								 while(rs1.next()) {
									 html=html+"<details><journalId>"+journalId1+"</journalId>" +
								 		"<gpfNo>"+rs1.getString("ASSOCIATE_GPF_NO")+"</gpfNo><amount>"+rs1.getString("AMOUNT")+"</amount>" +
								 		"<year>"+rs1.getString("RELATIVE_YEAR")+"</year><month>"+rs1.getString("RELATIVE_MONTH")+"</month>" +
								 		"<type>"+rs1.getString("ASSOCIATE_TRANS_TYPE")+"</type></details>"+
								 		"<Interest_MONTH>"+rs1.getString("INT_CALCULATED_MONTH")+"</Interest_MONTH>" +
								 		"<Interest_YEAR>"+rs1.getString("INT_CALCULATED_YEAR")+"</Interest_YEAR>"+
									 "<SLIP_PRINT_UNDER>"+rs1.getString("SLIP_PRINT_UNDER")+"</SLIP_PRINT_UNDER>"+
								 		"<INST_NO>"+rs1.getString("INST_NO")+"</INST_NO>" +
								 		"<TOT_INST>"+rs1.getString("TOT_INST")+"</TOT_INST>";
								 }
							 
	        			}
	        			else
	        			{
	        				ps1=con.prepareStatement("select ASSOCIATE_AC_HEADCODE,ASSOCIATE_TRANS_TYPE,AMOUNT,RELATIVE_MONTH,RELATIVE_YEAR,INT_CALCULATED_MONTH,INT_CALCULATED_YEAR,SLIP_PRINT_UNDER,INST_NO,TOT_INST " +
					 		" from HRM_GPF_MISSING_CR_DB_TRN where HO_JRNL_MST_ID=? and ASSOCIATE_AC_HEADCODE=? and AMOUNT=? and ASSOCIATE_TRANS_TYPE=? ");
								 ps1.setInt(1, journalId1);
								 ps1.setString(2, head);
								 ps1.setString(3, amt);
								 ps1.setString(4, type);
								 rs1=ps1.executeQuery(); 
								 while(rs1.next()) {
									 html=html+"<details><journalId>"+journalId1+"</journalId>" +
								 		"<gpfNo>"+rs1.getString("ASSOCIATE_AC_HEADCODE")+"</gpfNo><amount>"+rs1.getString("AMOUNT")+"</amount>" +
								 		"<year>"+rs1.getString("RELATIVE_YEAR")+"</year><month>"+rs1.getString("RELATIVE_MONTH")+"</month>" +
								 		"<type>"+rs1.getString("ASSOCIATE_TRANS_TYPE")+"</type></details>"+
								 		"<Interest_MONTH>"+rs1.getString("INT_CALCULATED_MONTH")+"</Interest_MONTH>" +
								 		"<SLIP_PRINT_UNDER>"+rs1.getString("SLIP_PRINT_UNDER")+"</SLIP_PRINT_UNDER>"+
								 		"<INST_NO>"+rs1.getString("INST_NO")+"</INST_NO>" +
								 		"<TOT_INST>"+rs1.getString("TOT_INST")+"</TOT_INST>"+
								 		"<Interest_YEAR>"+rs1.getString("INT_CALCULATED_YEAR")+"</Interest_YEAR>" ;
								 }
	        			}
	        		}
	        		catch (Exception e) {
						e.printStackTrace();
					}
	        		
						
					
					
	        	
	        }
	        else if(command.equalsIgnoreCase("update")){
	        	
        	try {
        		session =request.getSession(false);
    	        String updatedby=(String)session.getAttribute("UserId");
    	        long l=System.currentTimeMillis();
    	        java.sql.Timestamp ts=new java.sql.Timestamp(l);
	        	int journalId2=Integer.parseInt(request.getParameter("journalId"));
	        	String gpfname=request.getParameter("gpfname")!=null?request.getParameter("gpfname").replace(" ", ""):"";
	        	String amt=request.getParameter("amt");
	        	String rel_year=request.getParameter("rel_year");
	        	String rel_month=request.getParameter("rel_month");
	        	String Int_year=request.getParameter("int_year");
	        	String int_month=request.getParameter("int_month");
	        	String missing_type=request.getParameter("missing_type");
	        	String ac_head_code=request.getParameter("ac_head_code")!=null?request.getParameter("ac_head_code").replace(" ", ""):"";
	        	String oldamt=request.getParameter("oldamt");
	        	String oldtype=request.getParameter("oldtype");
	        	String oldgpf=request.getParameter("oldgpf");
	        	String oldhead=request.getParameter("oldhead");
	        	String slipPrint=request.getParameter("slipPrint");
	        	 int totalInstall=0,totalInstallamount=0;
	            if(!slipPrint.equalsIgnoreCase("S"))
	            {
	            	  totalInstall=Integer.parseInt(request.getParameter("Install_no").trim());
	            	   totalInstallamount=Integer.parseInt(request.getParameter("totalNOInstall").trim());
	            }
	        	System.out.println("************11****************missing_type"+ missing_type + gpfname + ac_head_code +amt);
	        	System.out.println(oldamt+oldtype+oldgpf+oldhead+journalId2+Int_year+"nm"+int_month);
	        	if(ac_head_code.length()>0){
	        	try{
	        		System.out.println("enter head code");
	        		if(oldhead.length()>0){
	        		 ps=con.prepareStatement("update HRM_GPF_MISSING_CR_DB_TRN set AMOUNT=?,RELATIVE_MONTH=?,RELATIVE_YEAR=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,PROCESS_FLOW_STATUS_ID='MD',ASSOCIATE_AC_HEADCODE=?,ASSOCIATE_TRANS_TYPE=?,ASSOCIATE_GPF_NO='0'," +
	        		 		"INT_CALCULATED_MONTH=?,INT_CALCULATED_YEAR=?,SLIP_PRINT_UNDER=?,INST_NO=?,TOT_INST=? " +
				 		"  where HO_JRNL_MST_ID=? and ASSOCIATE_AC_HEADCODE=?  and ASSOCIATE_TRANS_TYPE=? and AMOUNT=? ");
System.out.println("update HRM_GPF_MISSING_CR_DB_TRN set AMOUNT=?,RELATIVE_MONTH=?,RELATIVE_YEAR=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,PROCESS_FLOW_STATUS_ID='MD'" +
				 		"  where HO_JRNL_MST_ID=? and ASSOCIATE_AC_HEADCODE=?  and ASSOCIATE_TRANS_TYPE=?");
			 ps.setString(6, ac_head_code);
			 ps.setString(1, amt);
			 ps.setString(3, rel_year);
			 ps.setString(2, rel_month);
			 ps.setString(7, missing_type);
			 ps.setString(8, int_month);
			 ps.setString(9, Int_year);
			 ps.setInt(13, journalId2);
			 ps.setString(4, updatedby);
			 ps.setTimestamp(5, ts);
			 ps.setString(14, oldhead);
			 ps.setString(15, oldtype);
			 ps.setString(16, oldamt);
			 ps.setString(10, slipPrint);
			 ps.setInt(11, totalInstall);
			 ps.setInt(12, totalInstallamount);
	        		}
	        		else
	        		{
	        			 ps=con.prepareStatement("update HRM_GPF_MISSING_CR_DB_TRN set AMOUNT=?,RELATIVE_MONTH=?,RELATIVE_YEAR=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,PROCESS_FLOW_STATUS_ID='MD',ASSOCIATE_AC_HEADCODE=?,ASSOCIATE_TRANS_TYPE=?,ASSOCIATE_GPF_NO='0'" +
	        					 ",INT_CALCULATED_MONTH=?,INT_CALCULATED_YEAR=?,SLIP_PRINT_UNDER=?,INST_NO=?,TOT_INST=?" +
					 		"  where HO_JRNL_MST_ID=? and ASSOCIATE_GPF_NO=?  and ASSOCIATE_TRANS_TYPE=? and AMOUNT=? ");
	System.out.println("1update HRM_GPF_MISSING_CR_DB_TRN set AMOUNT=?,RELATIVE_MONTH=?,RELATIVE_YEAR=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,PROCESS_FLOW_STATUS_ID='MD',ASSOCIATE_AC_HEADCODE=?,ASSOCIATE_TRANS_TYPE=?,ASSOCIATE_GPF_NO='0'" +
	        					 ",INT_CALCULATED_MONTH=?,INT_CALCULATED_YEAR=?" +
					 		"  where HO_JRNL_MST_ID=? and ASSOCIATE_GPF_NO=?  and ASSOCIATE_TRANS_TYPE=? and AMOUNT=? ");
	
				 ps.setString(6, ac_head_code);
				 ps.setString(1, amt);
				 ps.setString(3, rel_year);
				 ps.setString(2, rel_month);
				 ps.setString(7, missing_type);
				 ps.setString(8, int_month);
				 ps.setString(9, Int_year);
				 ps.setInt(13, journalId2);
				 ps.setString(4, updatedby);
				 ps.setTimestamp(5, ts);
				 ps.setString(14, oldgpf);
				 ps.setString(15, oldtype);
				 ps.setString(16, oldamt);
				 ps.setString(10, slipPrint);
				 ps.setInt(11, totalInstall);
				 ps.setInt(12, totalInstallamount);
	        		}
			 ps.executeUpdate(); 
			 html="<response><status>success</status><value>update</value></response>";
	        	}
	        	catch (Exception e) {
	        		 html="<response><status>failure</status></response>";
					e.printStackTrace();
				}
	        	}
	        	else
	        	{
	        	try
	        	{
	        		System.out.println("enter gpf");
	        		if(oldhead.length()>0){
		        		 ps1=con.prepareStatement("update HRM_GPF_MISSING_CR_DB_TRN set AMOUNT=?,RELATIVE_MONTH=?,RELATIVE_YEAR=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,PROCESS_FLOW_STATUS_ID='MD',ASSOCIATE_GPF_NO=?,ASSOCIATE_TRANS_TYPE=?,ASSOCIATE_AC_HEADCODE='0'" +
		        				 ",INT_CALCULATED_MONTH=?,INT_CALCULATED_YEAR=?,SLIP_PRINT_UNDER=?,INST_NO=?,TOT_INST=? " +
					 		"  where HO_JRNL_MST_ID=? and ASSOCIATE_AC_HEADCODE=?  and ASSOCIATE_TRANS_TYPE=? and AMOUNT=? ");
	System.out.println("update HRM_GPF_MISSING_CR_DB_TRN set AMOUNT=?,RELATIVE_MONTH=?,RELATIVE_YEAR=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,PROCESS_FLOW_STATUS_ID='MD',ASSOCIATE_GPF_NO=?,ASSOCIATE_TRANS_TYPE=?" +
					 		"  where HO_JRNL_MST_ID=? and ASSOCIATE_AC_HEADCODE=?  and ASSOCIATE_TRANS_TYPE=? and AMOUNT=?");
	 ps1.setString(6, gpfname);
	 ps1.setString(1, amt);
	 ps1.setString(3, rel_year);
	 ps1.setString(2, rel_month);
	 ps1.setString(7, missing_type);
	 ps1.setString(8, int_month);
	 ps1.setString(9, Int_year);
	 ps1.setInt(13, journalId2);
	 ps1.setString(4, updatedby);
	 ps1.setTimestamp(5, ts);
	 ps1.setString(14, oldhead);
	 ps1.setString(15, oldtype);
	 ps1.setString(16, oldamt);
	 ps1.setString(10, slipPrint);
	 ps1.setInt(11, totalInstall);
	 ps1.setInt(12, totalInstallamount);
		        		}
		        		else
		        		{
		        			 ps1=con.prepareStatement("update HRM_GPF_MISSING_CR_DB_TRN set AMOUNT=?,RELATIVE_MONTH=?,RELATIVE_YEAR=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,PROCESS_FLOW_STATUS_ID='MD',ASSOCIATE_GPF_NO=?,ASSOCIATE_TRANS_TYPE=?,ASSOCIATE_AC_HEADCODE='0'" +
		        					 ",INT_CALCULATED_MONTH=?,INT_CALCULATED_YEAR=?,SLIP_PRINT_UNDER=?,INST_NO=?,TOT_INST=? " +
						 		"  where HO_JRNL_MST_ID=? and ASSOCIATE_GPF_NO=?  and ASSOCIATE_TRANS_TYPE=? and AMOUNT=? ");
		System.out.println("update HRM_GPF_MISSING_CR_DB_TRN set AMOUNT=?,RELATIVE_MONTH=?,RELATIVE_YEAR=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,PROCESS_FLOW_STATUS_ID='MD',ASSOCIATE_GPF_NO=?,ASSOCIATE_TRANS_TYPE=?" +
						 		"  where HO_JRNL_MST_ID=? and ASSOCIATE_GPF_NO=?  and ASSOCIATE_TRANS_TYPE=? and AMOUNT=? ");
		 ps1.setString(6, gpfname);
		 ps1.setString(1, amt);
		 ps1.setString(3, rel_year);
		 ps1.setString(2, rel_month);
		 ps1.setString(7, missing_type);
		 ps1.setString(8, int_month);
		 ps1.setString(9, Int_year);
		 ps1.setInt(13, journalId2);
		 ps1.setString(4, updatedby);
		 ps1.setTimestamp(5, ts);
		 ps1.setString(14, oldgpf);
		 ps1.setString(15, oldtype);
		 ps1.setString(16, oldamt);
		 ps1.setString(10, slipPrint);
		 ps1.setInt(11, totalInstall);
		 ps1.setInt(12, totalInstallamount);
		        		}
					 ps1.executeUpdate(); 
					 html="<response><status>success</status><value>update</value></response>";
	        	}
	        	catch (Exception e) {
	        		 html="<response><status>failure</status></response>";
					e.printStackTrace();
				}
	        	}
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				
	        	
	        	
	        	
	        }
	        else if(command.equalsIgnoreCase("validate"))
	        {
	        	System.out.println("-----------enter validate");
	        	int id=Integer.parseInt(request.getParameter("jid")!=null?request.getParameter("jid").trim():""),k=0,status=0;
	        	try{
	        	Statement st=con.createStatement();
	        	ResultSet rs3=st.executeQuery("select PROCESS_FLOW_STATUS_ID from HRM_GPF_MISSING_CR_DB_MST where HO_JRNL_MST_ID='"+id+"' and PROCESS_FLOW_STATUS_ID='FR'");
	        	while(rs3.next())
	        	{
	        		k++;
	        	}
	        	ResultSet rs4=st.executeQuery("select PROCESS_FLOW_STATUS_ID from HRM_GPF_MISSING_CR_DB_TRN where HO_JRNL_MST_ID='"+id+"' and PROCESS_FLOW_STATUS_ID='FR'");
	        	while(rs4.next())
	        	{
	        		k++;
	        	}
	        	if(k==0){
	        	try{
					ps=con.prepareStatement("update HRM_GPF_MISSING_CR_DB_MST set PROCESS_FLOW_STATUS_ID=?" +
							"  where HO_JRNL_MST_ID=? " );
					 ps.setString(1, "FR");
					 ps.setInt(2, id);
					 ps.executeUpdate(); 
					// html="<response><status>success</status><value>validate</value></response>";
					 status++;
	        	}
	        	catch (Exception e) {
	        		 html="<response><status>failure</status></response>";
					e.printStackTrace();
				}
	        	try{
					ps1=con.prepareStatement("update HRM_GPF_MISSING_CR_DB_TRN set PROCESS_FLOW_STATUS_ID=?" +
							"  where HO_JRNL_MST_ID=? " );
					System.out.println("update HRM_GPF_MISSING_CR_DB_TRN set PROCESS_FLOW_STATUS_ID=?" +
							"  where HO_JRNL_MST_ID=? ");
					 ps1.setString(1, "FR");
					 ps1.setInt(2, id);
					 ps1.executeUpdate(); 
					// html="<response><status>success</status><value>validate</value></response>";
					 CallableStatement call=con.prepareCall("{call HRM_GPF_MOVE_MISS_CRDB_CONS(?)}");
					 call.setInt(1, id);
					 call.execute();
					 status++;
	        	}
	        	catch (Exception e) {
	        		 html="<response><status>failure</status></response>";
					e.printStackTrace();
				}
	        	if(status==2)
	        	{
	        		 html="<response><status>success</status><value>validate</value></response>";
	        	}
	        	}
	        	else
	        	{
	        		 html="<response><status>success</status><value>validated</value></response>";
	        	}
	        	}
	        	catch (Exception e) {
	        		 html="<response><status>failure</status></response>";
					e.printStackTrace();
				}
	        	System.out.println("hfgdfgdhfhfh"+html);
	        }
	         else if(command.equalsIgnoreCase("achead"))
		        {
		        	
		        	System.out.print("i am in achead");
		        	 int headCode=Integer.parseInt(request.getParameter("acheadcode"));
		        	 html="<response><command>achead</command>";
		        	 try{
		        		 ps=con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=? union" +
		        		 		" select TYPE from HRM_GPF_SPL_ACHEAD where AC_HEAD=?");  
		        		 ps.setInt(1, headCode);
		        		 ps.setInt(2, headCode);
		        		 rs=ps.executeQuery(); 
		        		 
		        		 if(rs.next()) {
		        		  html=html+"<flag>success</flag>";
		        		  html=html+"<hcocde>"+rs.getString("ACCOUNT_HEAD_DESC")+"</hcocde>";
		        		  //System.out.print("i am in achead"+rs.getString("ACCOUNT_HEAD_DESC"));
		        		  
		        		 }else{
		        			 html=html+"<flag>failure</flag>";
		        		 }
		        		 
		        		 html=html+"</response>";
		        	 }catch(Exception e){
		        		 System.out.println(e);
		        		 e.printStackTrace();
		        	 }
		        	 
		        	 
		        }
	         else if(command.equalsIgnoreCase("updateMaster"))
	         {
	        	 String xml="";
	        	
	 	        String updatedby=(String)session.getAttribute("UserId");
	 	        long l=System.currentTimeMillis();
	 	        java.sql.Timestamp ts=new java.sql.Timestamp(l);
	        	 int gpf_no=0;
		            int unit=0;
		            if(request.getParameter("gpfno").trim()!=null || request.getParameter("gpfno").trim()!="")
		            gpf_no = Integer.parseInt(request.getParameter("gpfno").trim());
		            if(request.getParameter("unit").trim()!=null || request.getParameter("unit").trim()!="")
		            	unit=Integer.parseInt(request.getParameter("unit").trim());
		            int relMonth=Integer.parseInt(request.getParameter("rel_month").trim());
		            int relYear=Integer.parseInt(request.getParameter("rel_year").trim());
		            int intMonth=Integer.parseInt(request.getParameter("int_month").trim());
		            int intYear=Integer.parseInt(request.getParameter("int_year").trim());
		            int amount=Integer.parseInt(request.getParameter("amount").trim());
		            String slipPrint=request.getParameter("slipPrint")!=null?request.getParameter("slipPrint"):"";
		        	 int totalInstall=0,totalInstallamount=0;
		            if(!slipPrint.equalsIgnoreCase("S"))
		            {
		            	  totalInstall=Integer.parseInt(request.getParameter("Install_no").trim());
		            	   totalInstallamount=Integer.parseInt(request.getParameter("totalNOInstall").trim());
		            }
		            String missingType=request.getParameter("misstype");
		            String jid=request.getParameter("jid");
		            String categoryType=request.getParameter("category_type");
		            
		            int jNo=Integer.parseInt(request.getParameter("jno").trim());
		            
		            String date=request.getParameter("date");
		          	
		            String remarks=request.getParameter("remark");
		            
		            xml="<response><command>add</command>";
		            try{
		            	 xml=xml+"<flag>success</flag>";
		            
		            	
		            	
		            	 ps=con.prepareStatement("update HRM_GPF_MISSING_CR_DB_MST set ACCOUNTING_UNIT_ID=?,MISSING_TRANS_TYPE=?,GPF_NO=?,AMOUNT=?,RELATIVE_YEAR=?,RELATIVE_MONTH=?,JOURNAL_REF_NO=?,JOURNAL_REF_DATE=to_date(?,'dd/MM/yyyy'),UPDATED_BY_USER_ID=?,UPDATED_DATE=?,REMARKS=?,PROCESS_FLOW_STATUS_ID=?,INT_CALCULATED_MONTH=?,INT_CALCULATED_YEAR=?" +
		            	 		",SLIP_PRINT_UNDER=?,INST_NO=?,TOT_INST=? where HO_JRNL_MST_ID=?");
			             ps.setInt(1,unit);
			             ps.setString(2,missingType);
			             ps.setInt(3,gpf_no);
			             ps.setInt(4,amount);
			             ps.setInt(5,relYear);
			             ps.setInt(6,relMonth);
			             ps.setInt(7,jNo);
			             ps.setString(8,date);                
			             ps.setString(9,updatedby);
			             ps.setTimestamp(10,ts);
			             ps.setString(11,remarks);
			            // ps.setString(12,categoryType);
			             ps.setString(12,"MD");
			             ps.setInt(14,intYear);
			             ps.setInt(13,intMonth);
			             ps.setString(15, slipPrint);
			    		 ps.setInt(16, totalInstall);
			    		 ps.setInt(17, totalInstallamount);
			             ps.setString(18,jid);
			             ps.executeUpdate();
		            
		            } catch(Exception e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	        	 
	        	 
	         }
	         else if(command.equalsIgnoreCase("updatetransaction"))
	         {
	        	  String updatedby=(String)session.getAttribute("UserId");
		 	        long l=System.currentTimeMillis();
		 	        java.sql.Timestamp ts=new java.sql.Timestamp(l);

	        	 System.out.println("TRN");	 
	            
	           
	           
	            String head=request.getParameter("spl_ac_head");
	            String gpf=request.getParameter("other_gpfno");
	            String Type=request.getParameter("missingtype");
	            String amt=request.getParameter("other_amount");
	            String year=request.getParameter("rel_year");
	            String mnth=request.getParameter("rel_month");
	            String int_year=request.getParameter("int_year");
	            String int_month=request.getParameter("int_month");
	            String fund_cate=request.getParameter("category_type");
	            String journalid=request.getParameter("journalid");
	          //  xml="<response><command>addtrn</command>";
	            try{
	            /*	System.out.println(gpf_no);   
	            	System.out.println(acHead);   
	            	System.out.println(relMonth);   
	            	System.out.println(relYear);   
	            	System.out.println(amount);  */
	            	
	                String[] acHead= head.split(",");
	                String[] gpf_no= gpf.split(",");
	                String[] transType= Type.split(",");
	                String[] amount= amt.split(",");
	                String[] relMonth= mnth.split(",");
	                String[] relYear= year.split(",");
	                String[] intMonth= int_month.split(",");
	                String[] intYear= int_year.split(",");
	               int transCount=0;
	            	
	            	  System.out.println("jounalId");
	            	 ps=con.prepareStatement("select HO_JRNL_TRANS_SLNO as joural_slno from HRM_GPF_MISSING_CR_DB_TRN where HO_JRNL_MST_ID=?");
	            	 ps.setString(1,journalid);
		            	System.out.println("trn----?"+journalid);
		            	rs=ps.executeQuery(); 
		            	while(rs.next())
		            	{
	            	 ps=con.prepareStatement("update HRM_GPF_MISSING_CR_DB_TRN set ASSOCIATE_AC_HEADCODE=?,ASSOCIATE_GPF_NO=?,ASSOCIATE_TRANS_TYPE=?,AMOUNT=?,RELATIVE_MONTH=?,RELATIVE_YEAR=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,PROCESS_FLOW_STATUS_ID=?,fund_category=?,INT_CALCULATED_MONTH=?,INT_CALCULATED_YEAR=? where HO_JRNL_MST_ID=? and HO_JRNL_TRANS_SLNO=?");
	            	 ps.setString(13,journalid);  
	            	 ps.setString(1,acHead [transCount]);
		             ps.setString(2,gpf_no [transCount]);
		             ps.setString(3,transType [transCount]);
		             ps.setString(14,rs.getString("HO_JRNL_TRANS_SLNO") );
		             ps.setString(4,amount [transCount]);
		             ps.setString(5,relMonth [transCount]);
		             ps.setString(6,relYear [transCount]);
		             ps.setString(7,updatedby);
		             ps.setTimestamp(8,ts);
		             ps.setString(9,"MD");
		             ps.setString(10,fund_cate);
		             ps.setString(11,intMonth [transCount]);
		             ps.setString(12,intYear [transCount]);
		             ps.executeUpdate();  
		             transCount++;
		            	}
		      } catch(Exception e) {
             
                e.printStackTrace();
            }
           
         
	         }
	        System.out.println("out:"+html);
			 out.println(html);
	}
}
