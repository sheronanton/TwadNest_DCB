<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*,java.sql.*,Servlets.Security.servlets.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	function sub()
	{
		document.getElementById('rate_update').submit();
	}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> </title>
</head>
<body>

<form action="PMS_DCB_Rate_update.jsp" method="get" name="rate_update" id="rate_update">

<%
			Connection con=null;
			Controller obj=null,obj1=null;
			String region_combo="",division_combo="",type_combo="";
			int region_office_id=0,office_id=0;
			try 
			{  
			
				 obj=new Controller();
				 obj1=new Controller();
				 con=obj.con();
				 obj.createStatement(con);
				 obj1.createStatement(con);
				
				 region_office_id=Integer.parseInt(obj.setValue("region_office",request));
				 office_id=Integer.parseInt(obj.setValue("office",request));
				 region_combo=obj.combo_str("com_mst_all_offices_view","office_name","office_id","where office_level_id='RN'","region_office",Integer.toString(region_office_id),"onchange=sub()");
				 division_combo=obj1.combo_str("com_mst_all_offices_view","office_name", "OFFICE_ID"," where   office_id in (select office_id from pms_dcb_div_dist_map) and office_level_id='DN' and REGION_OFFICE_ID="+region_office_id+" ","office",Integer.toString(office_id),"onchange=sub()");
				 type_combo=obj1.combo_str("PMS_DCB_BEN_TYPE","BEN_TYPE_DESC","BEN_TYPE_ID"," where BEN_TYPE_ID <=6 and  BEN_TYPE_ID in (select BENEFICIARY_TYPE_ID from pms_dcb_mst_beneficiary where OFFICE_ID_BEN ="+Integer.toString(office_id)+" and status='L')","type","");
				 
				 
				 
				%>
					<table align="center" width="50%"> 
						<tr>
							<td>Region</td>
							<td><%=region_combo%></td>
						</tr>
						<tr>
							<td>Division</td>
							<td><%=division_combo%></td>
						</tr>
						<tr>
							<td>Type</td>
							<td><%=type_combo%></td>
						</tr>
						<tr>
							<td>Old Rate</td>
							<td>
								<input type="text" id="" name=""/>
							</td>
						</tr>
						<tr>
							<td>New Rate</td>
							<td>
								<input type="text" id="" name=""/>
							</td>
						</tr>
					</table>
				
				<% 
				 
				 
				
			}catch(Exception e)
			{
				out.print(e);
				
			}

%>
</form>
</body>
</html>

