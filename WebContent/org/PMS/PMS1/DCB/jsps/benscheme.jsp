<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@ page import="Servlets.Security.classes.UserProfile"%>	
<%@page import="java.util.*,java.sql.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
		String new_cond=Controller.new_cond;
		String meter_status=Controller.meter_status;
		String meter_status2=Controller.meter_status2;
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
		String qry=" SELECT met.SCHEME_SNO,ben.BENEFICIARY_NAME,btype.BEN_TYPE_DESC, "+
					" sch.SCH_NAME "+
						  " FROM "+
						  " (SELECT BENEFICIARY_SNO, "+
						    		" BENEFICIARY_NAME,BENEFICIARY_TYPE_ID, "+
						      " OFFICE_ID "+
						      " FROM PMS_DCB_MST_BENEFICIARY "+
						    " WHERE  "+new_cond+" office_id="+Office_id+
						    " )ben "+
						    " JOIN "+
						  " ( SELECT DISTINCT SCHEME_SNO, "+
						    		" BENEFICIARY_SNO, "+
						      " office_id "+
						      " FROM PMS_DCB_MST_BENEFICIARY_METRE "+
						    " WHERE "+meter_status+" office_id= "+Office_id+
						    " )met "+
						    " ON met.BENEFICIARY_SNO=ben.BENEFICIARY_SNO "+ 
						  " AND met.office_id     =ben.office_id "+
						  " JOIN "+
						  " ( SELECT SCH_SNO,SCH_NAME FROM PMS_SCH_MASTER "+
						    		"   )sch "+
						  " ON sch.SCH_SNO=met.SCHEME_SNO "+ 
						  " join "+
					         "( "+
				               " select "+
				                   " BEN_TYPE_ID,"+
				                  " BEN_TYPE_DESC "+
				               " from "+
				                   " PMS_DCB_BEN_TYPE"+
				         ")btype " +
				          " on btype.BEN_TYPE_ID=ben.BENEFICIARY_TYPE_ID  order BY BEN_TYPE_DESC,BENEFICIARY_NAME,SCH_NAME";
		
		 
	   
		table_column="BENEFICIARY_NAME,BEN_TYPE_DESC,SCH_NAME";
		table_header="Beneficiary,Beneficiary Type,Scheme Name"; 
		String table_td_set=",,align=left width='35%'";
		table_heading="Beneficiary Schemes List - "+Office_Name+ "";
		String tab="cellspacing='0' cellpadding='3' width='100%' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'";
		html=obj.genReport(qry,table_header,table_column,tab,table_heading,table_td_set); 
	
		con.close(); 
		}catch (Exception e) 
		{
			
			response.sendRedirect(request.getContextPath()+"/index.jsp");
			
		} 
%> 
		<body> 
		<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
		<title><%=table_heading%>  | TWAD Nest - Phase II </title>
		<Table class="fb2"    id="" width=75% align="center" border="1"   cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
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