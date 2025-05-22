	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
	<%@page import="java.util.Calendar" %>
	<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
	<%@page import="java.sql.Connection"%>
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
	<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
	<title>Item Wise Expenditure - Previous Fin. Year  </title>
	<script type="text/javascript" src="../scripts/cellcreate.js"></script>
	<script type="text/javascript" src="../scripts/transcat.js"></script>
	<script type="text/javascript">
	 
	function allsch()
	{
		var pyear=document.getElementById("pyear").value;
		window.open("pms_ame_sch_prv_all_sch.jsp?fin_year="+pyear);
	}
	 
</script>
</head>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<body>  
<form name="item_wise_year" method="post">
<%!String msg=""; %> 
<%

		double ACTUAL_EXP=0.0f; 
		int sub_sno=0,maint_sno=0,gp_sno=0;
		String process_f="";
		try
		{	
		HttpSession session1 = request.getSession(false);
		String	userid = (String) session1.getAttribute("UserId");
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		Controller obj=new Controller();
		Connection con=obj.con();
		obj.createStatement(con);
		if (userid == null) {

			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}		
		String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')");
		String sch_sno = obj.setValue("sch_sno", request);
		String pyear= obj.setValue("pyear", request); 
		
		if (Office_id.equals("")) Office_id="0";
		String Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
		String sch=obj.combo_str("PMS_SCH_MASTER","SCH_NAME","SCH_SNO"," where   sch_sno in (select distinct SCHEME_SNO from  PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+" ) and SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+") order by sch_sno","sch_sno","","  style='width: 80pt;'" );
		String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
		String flag_c=obj.setValue("flag_c",request);
		int year_incr=0;
		if (month <4)
		  year_incr=0;    
		else  
		  year_incr=1;
		   
		int total_row= Integer.parseInt(obj.setValue("row_count",request));
		java.util.Hashtable cols=new java.util.Hashtable();
		process_f=obj.setValue("pyear",request);
		sch_sno	=obj.setValue("sch_sno",request);
		msg	=obj.isNull(obj.setValue("msg",request),2);		  	
		String save=obj.setValue("cmd",request);
		 
%>
		<table align="center" width="60%" border="1" cellspacing="1" cellpadding="1" style="border-collapse: collapse;border-color: skyblue" >
     	<tr>
     	    <th colspan="2" align="center"  class="tdH">  Item Wise Expenditure - Previous Fin. Year  </th></tr> 
		<tr>
			<td width="30%"><label class="fnt">Division Name :</label></td> 
			<td align="left"> <label class="fnt"><%=Office_Name%></label></td>
		</tr>
	 
		<tr>
			<td><label class="fnt">Financial Year</label></td>
			<td colspan="1">
				<select class="select" id="pyear" name="pyear" style="width: 80pt;">
				<option value="0">Select</option>
				<%
					for (int i = year-2; i <= (year+year_incr)-1; i++) 
					{
					  String inp_year=(i-1)+"-"+i;
					  if (pyear.equalsIgnoreCase(inp_year.trim()))  
					  {
				%><option value="<%=i-1%>-<%=i%>" selected><%=i-1%>-<%=i%></option>
				<%
					  }
					  else
					  {
				%>
				  <option value="<%=i-1%>-<%=i%>"><%=i-1%>-<%=i%></option>
				<%
					  }
					}
				%>
				</select>
				 
			</td>
	 	 </tr>
		 <tr>
		  	<td colspan="2" align="center">
		  			  	<input type="button" value="View" onclick="javascript:allsch()">
		  			  	<input type="button" value="Exit" onclick="window.close()">
		  	</td>
		  </tr>
		</table>
		<table id="etable"   align="center" width="60%" border="1" cellspacing="1" cellpadding="1">
		<tr>
			<td colspan="2" align="right"><label style="font-size: 1.15em;color: blue" id='net_label'></label></td>
		</tr>
		
 		
		
						   
	</table>
		
	</form>
</body>
	<%
	}catch(Exception e)
	{
		out.println(e);
	}  
	%>
</html>