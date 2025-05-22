<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%@ taglib uri="/struts-tags" prefix="s"%>
	<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.Connection"%>
	<%@page import="java.util.Calendar"%><html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
		<script type="text/javascript" src="../scripts/cellcreate.js"></script>
		<script type="text/javascript" src="../scripts/ben_added_area_master.js"></script>
	</head>
	<body onload="call_process(1);call_process(5)">
<%

		int c=0,cnt=0;;
		try
		{  
			 
			String Office_name="";
			Calendar cal=Calendar.getInstance();	
			int day=cal.get(Calendar.DATE);
			int month=cal.get(Calendar.MONTH)+1;
			int year=cal.get(Calendar.YEAR);
			Controller obj=new Controller();
			Connection con=null; 
			String userid="";
			obj=new Controller();
			con=obj.con();
			obj.createStatement(con);
			  try
				{
				   userid=(String)session.getAttribute("UserId");
				}catch(Exception e) {userid="0";}
				if(userid==null)
				{ 
					response.sendRedirect(request.getContextPath()+"/index.jsp");
				}
				String Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

			//	String Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
				if (Office_id.equals("")) Office_id="0";
				c=obj.getCount("PMS_DCB_ADDED_AREA_REQUEST","where (approved <> 'Y' or approved is null) and office_id="+Office_id);
				
				cnt=obj.getCount("PMS_DCB_ADDED_AREA_REQUEST","where RAISE_REQUEST='Y' and office_id="+Office_id);
				
		//String combo1=obj .combo_str("district_code","district_name","com_mst_districts a "," exists (select district_code from pms_dcb_div_dist_map where office_id="+Office_id+" and a.district_code= district_code) ","","","");
		 
	
		String combo1=obj.combo_str("com_mst_districts  ","district_name","district_code"," where exists (select district_code from pms_dcb_div_dist_map where office_id="+Office_id+" and com_mst_districts.district_code= district_code) ","d1","");
		String combo2=obj.combo_str("com_mst_districts  ","district_name","district_code"," where exists (select district_code from pms_dcb_div_dist_map where office_id="+Office_id+" and com_mst_districts.district_code= district_code) ","d2","onchange=call_process_2(10)");
			
%> <a href="rectify_journal_data.jsp">.</a>

	
		<center><h3><strong>Beneficiary Added Area</strong></h3></center>
	
	<table align="center" width="75%" height="" border="1" style="border-collapse: collapse;border-color: purple;">
	 
		<tr > 
			<td colspan="2" align="left" style="color:#ff0080;font-size: 1.2em;font-weight: bold">Add  Beneficiary  From </td> 
		</tr> 
		<tr> 
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;District</td>
			<td><%=combo1%></td>
		</tr>
		<tr>
			<td width="50%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Beneficiary Type</td>
			<td><select id="btype" name="btype" onchange='call_process(2);call_process_2(9)'></select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	Block : <select id="block_1" name="block_1"></select>&nbsp;&nbsp;&nbsp;&nbsp;<input type=button value="GO" onclick="call_process(2)"></td>
		</tr>
		<tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Beneficiary Name</td>
			<td><select id="ben_name" name="ben_name"></select></td>
		</tr>
		<tr > 
			<td colspan="2" align="left" style="color: blue;font-size: 1.2em;font-weight: bold">Add  Beneficiary  To</td> 
		</tr> 
		<tr>  
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;District</td> 
			<td><%=combo2%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			Block : <select id="block_2" name="block_2"></select></td>
		</tr>
		<tr> 
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Beneficiary Type</td>
			<td><select id="btype2" name="btype2" onchange='call_process(3)'>&nbsp;&nbsp;&nbsp;&nbsp;<input type=button value="GO" onclick="call_process(3)"></select></td>
		</tr>
		<tr> 
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Beneficiary Name</td>
			<td><select id="ben_name2" name="ben_name2"></select></td>
		</tr>
		<tr>
			<td colspan="2" align="center"> 
			<% 
				String msg="";
					
				//if (c!=0)
				//{  
					if (c!=0 )  
					{		
				 		msg="disabled='disabled'";
				 	}
				//}else
			//	{
			//		msg="disabled='disabled'";
			//	}
				%>
					 
				<input type=button value="Submit" name="sub" id="sub" <%=msg%> onclick="call_process(4),call_process(5)" >				
				<input type="button" value="Exit" onclick="javascript:window.close()"> 
			</td>
		</tr>
	</table><br>  
	<table align="center" width="75%" height="" border="1"  style="border-collapse: collapse;border-color: purple"> 
				<tbody id='tbody'>
					<table id='ttable'></table>
				</tbody>
	</table>
<%
		}catch(Exception e)
		{
			out.println(e);
		}
%>
</body>
</html>