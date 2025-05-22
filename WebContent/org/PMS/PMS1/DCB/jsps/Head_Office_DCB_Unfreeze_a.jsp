<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*,Servlets.Security.classes.UserProfile"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Head Office - Divisions DCB Unfreeze</title> 
</head>
<body>

<%
		String userid="0",Office_id="",Office_Name="",table_heading="",table_column="",table_header="";
		String combo1="", regdiv="";
		Connection con=null;
		String smonth="",syear="",html="",office_="";
		Controller obj=null;
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		int prv_month=0;		
		int prv_year=year;
		if (prv_month==12)  prv_year=year-1;
		UserProfile up = null;
		try  
		{  
		  obj=new  Controller();
		  con=obj.con();
		  obj.createStatement(con);
			try
			{
			   userid=(String)session.getAttribute("UserId");
			}catch(Exception e) {}
			if(userid==null)
			{ 
				  response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
			up = (UserProfile) session.getAttribute("UserProfile");
			
			Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
			if (Office_id.equalsIgnoreCase("0")) Office_id="5000";
			smonth=obj.setValue("pmonth",request);//obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
			syear=obj.setValue("pyear",request);//obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
			int row=0;
			System.out.println(up.getEmployeeName());
			PreparedStatement st=null;
			String qry=null;
			Enumeration result;
			result= request.getParameterNames();			
			while (result.hasMoreElements())
			{
				qry=null;
				String column_name=result.nextElement().toString();
				 if (!column_name.equalsIgnoreCase("pyear") && !column_name.equalsIgnoreCase("pmonth")
				  	&& !column_name.equalsIgnoreCase("regdiv") && !column_name.equalsIgnoreCase("row"))
				 {
					 qry=" insert into PMS_DCB_DATA_FREEZE_HIST (OFFICE_ID,ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_MONTH,CASHBOOK_YEAR,TOTAL_DMD "+
						 ", TOTAL_COLL,DCB_FREEZE,UPDATED_BY_USER_ID,UPDATED_DATE,UNFREEZED_USER_ID,UNFREEZED_DATE)   select OFFICE_ID,ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_MONTH,CASHBOOK_YEAR,TOTAL_DMD "+
						 ", TOTAL_COLL,DCB_FREEZE,UPDATED_BY_USER_ID,UPDATED_DATE,'"+userid+"' ,clock_timestamp() from PMS_DCB_DATA_FREEZE where cashbook_month="+smonth+" and cashbook_year="+syear+" and accounting_for_office_id="+
					 request.getParameter(column_name);
					 st=con.prepareStatement(qry);
					 
					 row+=st.executeUpdate();
					 st=null; 
					 	 
					 st=con.prepareStatement("delete from PMS_DCB_DATA_FREEZE where  cashbook_month="+smonth+" and cashbook_year="+syear+" and accounting_for_office_id="+request.getParameter(column_name));
					 row+=st.executeUpdate();
					 st=null; 
					 
					 String qry1="update PMS_DCB_SETTING set MESSAGE_POSTING= " + " ' \n <font color=yellow>DCB Data Unfreezed by Head Office......</font>' where month="+smonth+" and year="+syear+" and office_id="+request.getParameter(column_name);
					 System.out.println(qry1);
					 st=con.prepareStatement(qry1);
					 row+=st.executeUpdate();
					 st=null;  
				 }
				
			}
			
				String path="?pmonth="+smonth+"&pyear="+syear+"&regdiv="+request.getParameter("regdiv");
				if (row>0)
				{
				%>
				<script type="text/javascript">  
					alert("Successfully Unfreezed ");
					window.location="Head_Office_DCB_Unfreeze.jsp<%=path%>";
					
				</script>
				<%
				}
			
		}catch(Exception e) 
		{
			out.println(e.toString());
			out.println(e);
		}
%>

</body>
</html>