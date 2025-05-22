<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="Servlets.PMS.PMS1.DCB.Impl.Common_Impl"%><html>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller" %>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <link href='../../../../../css/CalendarControl.css' rel='stylesheet' media='screen'/>
    <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../../../../../org/Library/scripts/CalendarControl.js"></script>
    <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
    
<title>TWAD Nest II | Tariff Rate Revision </title>
 <link href="../../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
 <script type="text/javascript" src="../scripts/Office_Shift_new.js">
</script>
 <script type="text/javascript">
		 function rld1(a)
		{
			document.forms["myform"].submit();
		
		}
		  
		 
 </script>
  <script type="text/javascript" src="../scripts/cellcreate.js"></script>
  <script type="text/javascript" src="../scripts/pms_dcb_mst_tariff.js"></script>
  <script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
  <script type="text/javascript" src="../scripts/reg_menu_index.js"></script>
</head>
<body onload="flash()">
<form action="tariff_rate_change.jsp" method="get" name="myform">  
<table align="center" width="75%" cellpadding="2">
	<tr  bgcolor="skyblue">
	 <td colspan="2" align="center" class="tdText"><b> Tariff Rate Revision </b></td>
	</tr>
	 
<%
		String combo1="";
		Controller obj=null;
		Connection con;
		try
		{
	   		   String userid=(String)session.getAttribute("UserId");
			   if(userid==null)
			   { 
					response.sendRedirect(request.getContextPath()+"/index.jsp");
			   } 

		  obj=new  Controller();
		  con=obj.con();
		  obj.createStatement(con);
		  String Region_id="0";		  
		  String Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ; 
		  String regdiv=obj.isNull(obj.setValue("regdiv",request),1);
		  
		  String disable="";
		  if (Integer.parseInt(Office_id)!=5000)
		  disable=" disabled=disabled";
		  String cb=obj.combo_str("PMS_DCB_BEN_TYPE","BEN_TYPE_DESC","BEN_TYPE_ID"," where BEN_TYPE_ID in  (select BENEFICIARY_TYPE_ID	 from PMS_DCB_MST_BENEFICIARY where  office_id="+Office_id+" and STATUS='L') ","type","","class=select style='width:225px'  ");
		  Calendar cal = Calendar.getInstance();
		  int day = cal.get(Calendar.DATE);
		  int month = cal.get(Calendar.MONTH) + 1;
		  int year = cal.get(Calendar.YEAR);
		  Common_Impl impl_obj=new Common_Impl();
		  
		  
		  combo1=obj.combo_str("COM_MST_ALL_OFFICES_VIEW","OFFICE_NAME","OFFICE_ID"," where OFFICE_LEVEL_ID='RN' order by OFFICE_ID ","regdiv",regdiv," class=select '   onchange='rld1(this.value)' "+disable);
	
		 // Region_id=impl_obj.regionId(userid,obj);		  
		  String combo=impl_obj.Div_RegionWise(regdiv,"off_id",Office_id,"onchange='ben_type_load()' ");
		     
		  
%> 

	<tr>
		<td>Regions</td>
		<td><%=combo1%></td>
	</tr>
	<tr>
		<td>Division</td>
		<td><%=combo%>&nbsp;&nbsp;&nbsp;<input type="text" id="Div" name="Div" hidden><label id="msg"></label></td>
	</tr>
	<tr>
	<td>Exceptional Tariff rate applicable</td>
        <td><input type="radio" name="Exception_no"  id="Exception_no" value="no" checked="checked" onclick='ben_type_load()'/>No
        <input type="radio" name="Exception_no"  id="Exception_yes" value="yes" onclick='ben_type_load()'/>yes
              
	</tr>
	
	<tr>
	<td>Tariff w.e.f</td>
        <td><input type="text" name="Tariff_wef" maxlength="35" size="12" id="Tariff_wef" class="tb4" />
       <img src="../../../../../images/calendr3.gif" alt="calendar" onclick="showCalendarControl(document.myform.Tariff_wef)"></img>
        </td>
	</tr>
	
	
	
 	<tr>
		<td colspan="2">DCB Freeze Status as on Month &nbsp;
		<input type="text" id="month" maxlength="2" size="2" name="month">
&nbsp;
 Year &nbsp;<input type="text" id="year" name="year" size="4" maxlength="4" onblur="Newcheck(),NewValue()"> 
 <p id="demo2"></p></td>
	</tr>
     
	 
	<tr>
	<td align="center" colspan="3">
			<table width="100%" border=1>	
			<tr>
				<td>Sl.No</td>
				<td>Beneficiary Type</td>
				<td>Old Tariff Rate</td>
				<td>Revised Tariff Rate</td>
<!-- 			<td>W.e.f Date</td>  -->
				<td>Apply Tariff Revision</td>
			</tr> 
			<tbody id="id_tbody"></tbody>
			
		   </table>
   </td>
  </tr> 
	<tr bgcolor="skyblue"  >  
	 <td colspan="2" align="center"><input type="button" onclick="window.close()" value="Exit" style="font-style: italic;font-size:7;color:red;font-weight: bold" class="fb2"></td>
	</tr> 
	
<%
  		  	  
		  
  	
}catch(Exception e) { 
	%>
	<tr  bgcolor="skyblue">
	 <td colspan="2" align="left" class="tdText"><%=e%></td>
	</tr>
	<% 
	
	
}

%>
<input type="hidden" id="pr_status" name="pr_status" value="1"/>  

</table>
</form>
</body>
</html>