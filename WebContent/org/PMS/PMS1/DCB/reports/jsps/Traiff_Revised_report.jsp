<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller"%>
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TWAD : Demand Raised Current Vs RevisedTariff </title>
<script type="text/javascript" src="../scripts/Traiff_Revised_report.js"></script>
 <script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
</head>    
<body> 
<form action="Traiff_Revised_report.jsp" method="get" name="myform">
<%  
Calendar cal = Calendar.getInstance();
int day = cal.get(Calendar.DATE);
int month = cal.get(Calendar.MONTH) + 1;
int year = cal.get(Calendar.YEAR);
Connection con;
String BEN_TYPE_ID="",Office_name="",userid="",Office_id="",combostr="";
Controller obj=new Controller();
String combo1="";
try
{
	con=obj.con();
	obj.createStatement(con);
	userid=(String)session.getAttribute("UserId");
	if(userid==null)  
	{
	response.sendRedirect(request.getContextPath()+"/index.jsp");
	}		 
	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
	Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
	if (Office_id.equals("")) Office_id="0"; 
	BEN_TYPE_ID=obj.combo_str("PMS_DCB_BEN_TYPE","BEN_TYPE_DESC","BEN_TYPE_ID"," where BEN_TYPE_ID in ( select BENEFICIARY_TYPE_ID from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+")","BEN_TYPE_ID",""," style='width: 200;font-size: 0.7em; color: maroon;' onchange=sch_load(2,'')" );
	
	String regdiv=obj.isNull(obj.setValue("regdiv",request),1);
	String disable="";
	if (Integer.parseInt(Office_id)!=5000)
			disable=" disabled=disabled";
			combo1=obj.combo_str("COM_MST_ALL_OFFICES_VIEW","OFFICE_NAME","OFFICE_ID"," where OFFICE_LEVEL_ID='RN' order by OFFICE_ID ","regdiv",regdiv," class=select onchange='rld1(this.value)' "+disable);
			String rd="";
			if (Integer.parseInt(Office_id)==5000) rd=""; else rd="where OFFICE_ID="+Office_id;
			if (Integer.parseInt(Office_id)!=5000)
				combostr=obj.combo_str("COM_MST_OFFICES","OFFICE_NAME","OFFICE_ID"," where OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP "+rd+" )   ","DV",Office_id," class=select  "  );
			else
				combostr=obj.combo_str("COM_MST_ALL_OFFICES_VIEW","OFFICE_NAME","OFFICE_ID"," where OFFICE_LEVEL_ID='DN' and  REGION_OFFICE_ID="+regdiv+" and  OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP  )  ","DV",Office_id," class=select "  );
			}catch(Exception e) {}
			
			int year_incr=0;
			  if (month <4)
				  year_incr=0;    
			  else
				  year_incr=1; 
			  
			  int prvfinyear=obj.prvfinyear(year,month);
%>
<table align="center" width="66%" cellpadding="10" cellspacing="0" border=1 bordercolor="darkgray">
 <tr bgcolor="skyblue">
 		<td colspan="3" align="center"><%=Office_name%></td>
 </tr>
  <tr>  
 		<td colspan="3" align="center">Demand Raised Current Vs RevisedTariff </td>
 </tr>
 <tr class="tdText">
   <td>
        	<label id="benname"> <font color="#0000A0"> Financial Year</font> </label> 
    </td>
    <td>
       <select id="year"   tabindex="5" style="width:220pt"  class="select">
       <option value="0" selected="selected">- - - Select - - -</option>
       <%for (int j=year-5;j<=year+year_incr;j++)
        {
    	   	if (j-1==prvfinyear) {
       %>
        <option value="<%=j-1%>" selected><%=j-1%>-<%=j%></option>
        <% } else { %>
        <option value="<%=j-1%>"><%=j-1%>-<%=j%></option>
       <%}
        }%>
       </select>
    </td>  
    <td><input type="button" value="Selected Division" onclick="tariff_revised_rpt()"></td>
  </tr>      
   
     <tr> 
     	<td colspan="3">
       	<select id="rtype" name="rtype" class='select'>
			<option value="0" selected>Select Report Type</option>
			<option value="2"> Excel </option>
			<option value="1">PDF </option>  
		</select>
		</td>     		
     </tr>
       <tr>
       	<td colspan="3" align="center">	
       		<input type="button" value="Exit" onclick="window.close()"></td>
       </tr>    
  </table>
  </form>
</body>
</html>