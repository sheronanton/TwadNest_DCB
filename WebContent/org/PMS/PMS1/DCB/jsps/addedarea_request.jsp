<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@ page import="java.util.Calendar,java.sql.*,Servlets.Security.classes.UserProfile"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Raise Added Area Request </title>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
		
		function rld_clr()
		{
			try
			{ 
			document.getElementById("b1").disabled=false;
			}catch(e) {}
			document.forms["freezerequest"].submit(); 
		}
		 
</script>
</head>   
<body>  
<form action="addedarea_request.jsp" name="freezerequest" method="post" > 
<%
	 	String userid="112",Office_id="",Office_Name="";
		String smonth="",syear="",html="";
		String process="0";
		Controller obj=new Controller();
		Connection con=null; 
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year =  cal.get(Calendar.YEAR);	
		int prv_month=0;
		int prv_year=year;
		String disable="";
		UserProfile up = null;
		try
		{     
			con=obj.con();
			obj.createStatement(con);
			userid=(String)session.getAttribute("UserId");
			
			if(userid==null)
			{
			 	response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
				up = (UserProfile) session.getAttribute("UserProfile");
		//	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
			Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

			if (Office_id.equals("")) Office_id="0";
			Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "::numeric ");
			int	cnt=obj.getCount("PMS_DCB_ADDED_AREA_REQUEST","where RAISE_REQUEST='Y' and office_id="+Office_id+"::numeric");
			if (cnt!=0)
			{}
			String mmsg="";
			int freeze_status=0,already_freezed=0; 
			String APPROVED=""; 
			try  
			{
				APPROVED=obj.getValue("PMS_DCB_ADDED_AREA_REQUEST", "APPROVED", "where   OFFICE_ID="+Office_id+"::numeric");
				String remarks =obj.setValue("reason",request);
				String sub=obj.setValue("b1",request);
						if (sub.equalsIgnoreCase("Submit"))
						{ 
							  
								int maxcnt=obj.getMax("PMS_DCB_ADDED_AREA_REQUEST","REQUEST_ID","");
								String ins_qry="insert into PMS_DCB_ADDED_AREA_REQUEST (OFFICE_ID,REMARKS,REQUEST_USER_ID,REQUEST_DATE,RAISE_REQUEST,REQUEST_ID) values (?::numeric,?,?,clock_timestamp(),'Y',"+maxcnt+")";
								PreparedStatement ps=con.prepareStatement(ins_qry);				
								ps.setString(1,Office_id);
							 	ps.setString(2,remarks);
							 	ps.setString(3,userid);
								int r_s=ps.executeUpdate();
								if (r_s>=1) 
								{%>
									<script type="text/javascript">
										alert("Added Area Request Sent to Head Office");
										window.close();
									</script>
						  		<%} 
								r_s=0;
						}
						sub="";
							
			}catch(Exception e)
			{
				out.println(e);
			} 
		 %> 
		<table border="1" width="65%" align="center" cellpadding="5" cellspacing="0" style="border-collapse: collapse" >
			<tr  bgcolor="#322970">
			      <td align="center" colspan="2" style="color: white;">
			       		Raise Added Area Request  <br>   <jsp:expression>Office_Name</jsp:expression> <font class="tdText" color="red"><label id="msg"></label></font>
			     </td>
			</tr>
			 
       <tr>
       	<td>Reason <font color=red>**</td>
       	<td>
       			<textarea rows="10" cols="30" id="reason" name="reason"></textarea>
       	</td>
       </tr> 
       <tr>
       		<td colspan="2" align="center"><input  type="submit" id="b1" name="b1" value="Submit" <%=disable%>><input type="button" value="Exit" onclick="window.close()">
       		</td> 
       </tr> 
       </table> 
       <%
        int row=0;
        StringBuffer sb=new StringBuffer();
        sb.append("<table width='65%' style='border-collapse: collapse' align='center' border='1' width='85%' align='center' cellpadding='7' cellspacing='0'><tr  bgcolor='#322970'  style='color: white;'><td align='center'>Sl.No</td><td align='center'>Request Date</td><td>Remarks</td><td align='center'>Approved Status</td><td align='center'>Approved Date</td></tr>");
        StringBuffer res=new StringBuffer("select to_char(APPROVED_DATE,'dd/MM/yyyy') as APPROVED_DATE,to_char(REQUEST_DATE,'dd/MM/yyyy') as REQUEST_DATE,a.REMARKS,APPROVED,b.office_name,a.OFFICE_ID from pms_dcb_added_area_request a,com_mst_offices  b where a.office_id::numeric=b.office_id::numeric and   b.OFFICE_ID::numeric="+Office_id+"::numeric ");
		ResultSet rs=obj.getRS(res.toString());			
		while(rs.next()) 
		{ 
			row++;
			APPROVED=obj.isNull(rs.getString("APPROVED"),2);
			String UPDATED_DATE=rs.getString("REQUEST_DATE");
			String APPROVED_DATE=obj.isNull(rs.getString("APPROVED_DATE"),2);
			String REMARKS=rs.getString("REMARKS"); 
			APPROVED=(APPROVED.equalsIgnoreCase("Y"))?"Yes":"-";
			sb.append(" <tr><td align='center'>"+row+"</td><td>"+UPDATED_DATE+"</td> <td>"+REMARKS+"</td> <td>"+APPROVED+"</td><td>"+APPROVED_DATE+"</td></tr>");
		} 

		sb.append("</table>");
	
		%>
		<%=sb.toString() %> 
       <%
       }
       catch(Exception e) {}%>
       
  </form>     
</body>
</html>