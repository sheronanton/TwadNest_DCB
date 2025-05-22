<%@ page language="java" contentType="text/html; charset=ISO-8859-1"

    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/> 
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script> 
  <script type="text/javascript">
    
  function callReport()
{
	 
	 
	 var fyear=document.getElementById('fyear').value;
	 var fmonth=document.getElementById('fmonth').value;
	 
     window.open("../../../../../Bill_Demand?command=prmispdf&fmonth="+fmonth+"&fyear="+fyear);
}
  
  </script> 
</head>

 
<%

		String userid="0",Office_id="",Office_Name="",table_heading="",table_column="",table_header="";;
		Connection con;
		
		String smonth="",syear="",html="";
		Controller obj=null;
		String meter_status=Controller.meter_status;
		try
		{
		  obj=new  Controller();
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
		 
		Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		if (Office_id.equals("")) Office_id="0";
		Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " 	");
		smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
		syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
		String qry="  SELECT sch.SCH_SNO , " +
		"  sch.SCH_NAME, " +
			  "  smap.FIN_TRAN_ID, " +
			  "  smap.ACC_HD_DR, " +
			  "  smap.ACC_HD_CR, " +
			  " smap.SCH_STATUS_ID ,sch.SCH_YEAR" +
			  " FROM " +
			"  (SELECT SCH_SNO , " +
					  "   FIN_TRAN_ID, " +
			    "  ACC_HD_DR, " +
			    "   ACC_HD_CR, " +
			    "    SCH_STATUS_ID " +
			    "  FROM PMS_SCH_ACCOUNT_MAPPING " +
			  " )smap " +
			  " JOIN " +
			"  ( SELECT SCH_SNO,SCH_NAME ,SCH_YEAR FROM PMS_SCH_MASTER where SCH_SNO in (Select SCHEME_SNO  from  PMS_DCB_MST_BENEFICIARY_METRE where "+meter_status+" office_id= "+Office_id+") " +
					  " )sch " +
			" ON sch.SCH_SNO=smap.SCH_SNO order by trim(SCH_NAME) ASC";  
		   
		 
	 
		 
		table_column="SCH_NAME,SCH_SNO,FIN_TRAN_ID,ACC_HD_DR,ACC_HD_CR,SCH_STATUS_ID,SCH_YEAR";
		table_header="Scheme Name,Scheme SNO,Trans ID,DR Head,CR Head,Status Id,Scheme Year "; 
		String table_td_set=",align=center width='5%',align=center width='5%',align=center width='5%',align=center width='5%',align=center width='5%',align=center width='5%'";
		table_heading="Beneficiary Scheme Mapping  - "+Office_Name+ "";
		String tab="cellspacing='0' cellpadding='3' width='100%' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'";
		html=obj.genReport(qry,table_header,table_column,tab,table_heading,table_td_set); 
		String path = getServletContext().getRealPath("/");
		
		 
		obj.ben_excel(qry,table_header,table_column,"",table_heading,tab,path);
		con.close(); 
		}catch (Exception e) {out.println(e);
		//x	response.sendRedirect(request.getContextPath()+"/index.jsp");
			
		} 
%> 
		<body> 
	 
		<title><%=table_heading%>  | TWAD Nest - Phase II </title>
		<Table valign=top  id="" width=75% align="center" border="1"   cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
				<tr>
					<td colspan=2><center><font color='green' size=2><%=table_heading%></font></center></td>
				</tr> 
				<tr>
					<td colspan=2 align="center"><input class="fb2" type="button" id="" value="Back" style="font-style: italic;font-size:7;color:green;font-weight: bold"  onclick="window.history.go(-1)"/> </td>
				</tr> 
			 
				 
				<Tr>
					<td colspan=2><%=html%></td>
				</Tr>
				<tr>
					<td colspan=2 align="center"><input class="fb2" type="button" id="" value="Back" style="font-style: italic;font-size:7;color:green;font-weight: bold"  onclick="window.history.go(-1)"/> </td>
				</tr> 
			 
		</Table>
		 <input type="hidden" id="fyear" name="fyear" value="<%=syear%>" ></input> 
       	  <input type="hidden" id="fmonth" name="fmonth" value='<%=smonth%>' ></input> 
</body>


</html>