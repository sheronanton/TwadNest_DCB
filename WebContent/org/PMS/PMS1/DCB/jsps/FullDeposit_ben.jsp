<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller" %> 
<%@ page import="Servlets.Security.classes.UserProfile"%>




	<link href="../../../../../css/txtbox.css" rel="stylesheet"  media="screen"/>
<title>Full Deposit Beneficiary</title>
<%
	String new_cond=Controller.new_cond;

String userid="0",Office_id="",Office_Name="",table_heading="",table_column="",table_header="";;
 String amt="",billsno="",inp_month="",inp_year="",html;
 String combo="";
	int row=0; 
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
		 
			String Date_dis=day+"/"+month+"/"+year;
			   Controller obj=new Controller();
				Connection con;
				try
				{
				con=obj.con();
				obj.createStatement(con);
				 
				  
				  userid=(String)session.getAttribute("UserId");
			
				if(userid==null)
				{
				 //	response.sendRedirect(request.getContextPath()+"/index.jsp");
				}
				String yr=obj.setValue("year",request);
				String mt=obj.setValue("month",request);
				String ben=obj.setValue("ben",request);
				inp_month=mt;
				inp_year=yr;
				Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
				if (Office_id.equals("")) Office_id="0";
		 		Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " and OFFICE_LEVEL_ID='DN'");
			
				 
		 	
		 		
		 		  
		 		String qry="SELECT BENEFICIARY_SNO, BENEFICIARY_NAME FROM PMS_DCB_MST_BENEFICIARY where "+new_cond+" BENEFICIARY_TYPE_ID=18 and office_id="+Office_id ;
		 		table_column="BENEFICIARY_NAME,";
				table_header="Beneficiary Name,";
				String table_td_set="width='75%' ";
				table_heading=" Full Deposit Beneficiary - "+Office_Name+ "";
				String tab="cellspacing='0' cellpadding='3' width='100%' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'";
				html=obj.genReport(qry,table_header,table_column,tab,table_heading,table_td_set);
		 	  
		 		 
		%>
		<script type="text/javascript">
		function rld()
		{
			document.forms["othercharges"].submit();
		 
		}
		
		</script>
		
</head>
		<script type="text/javascript" src="../scripts/cellcreate.js"></script>
		<script type="text/javascript" src="../scripts/other_charges_collection.js"></script>
		<body onload="flash()">
		<form action="FullDeposit_demand.jsp" method="get" name="othercharges">
			<table align="center" width="55%" cellpadding="5" cellspacing="0" border=0 class="alerts2">
		 <tr  > <td  valign="middle" colspan="3" align="center"><font size='4'> Full Deposit Beneficiary List -<%=Office_Name%></font> </td></tr>
					<TR><td>
					<%=html%></td>
					</TR>	  
          	<tr>
          		<td valign="middle" colspan="3" align="center"><input type="button" class="fb2" value="Exit" onclick="window.close()"  style="font-style: italic;color: red">&nbsp;&nbsp;  </td>
          	</tr>
			</table><input type="hidden" id="row_cnt" name="row_cnt" value="<%=row%>" /><input type="hidden" id="pr_status" name="pr_status" value="1" />
			 
		</form>
		</body>
</html>
<%
				}catch(Exception e)
				{
					  
					out.println(e);
				}
				

%>
	
