<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*,Servlets.Security.classes.UserProfile"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Head Office - Divisions   Added Area Request Approve</title> 
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
			int row=Integer.parseInt(obj.setValue("row",request));
			System.out.println(up.getEmployeeName());
			PreparedStatement st=null;
			String qry=null;
			int r=1;
						
			while (r<=row)
			{		 
				String qry1="update PMS_DCB_ADDED_AREA_REQUEST set APPROVED_BY_USER_ID='"+userid+"',APPROVED_DATE=clock_timestamp(),APPROVED='Y' ,RAISE_REQUEST='' where REQUEST_ID="+obj.setValue("off"+r,request)+" and (approved <> 'Y'  OR approved is null) ";	 
				
				out.println(qry1);
				st=con.prepareStatement(qry1);					 
					 row+=st.executeUpdate();
					 st=null;  
			 		 r++;  
			}
			
				String path="?regdiv="+request.getParameter("regdiv");
				if (row>0)
				{
				%>
				<script type="text/javascript">  
					alert("Successfully Approved ");
					window.location="addedarea_request_approve.jsp<%=path%>";
					
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