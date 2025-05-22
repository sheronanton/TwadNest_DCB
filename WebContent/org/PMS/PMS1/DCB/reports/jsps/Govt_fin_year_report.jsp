<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TWAD : Performance of Maintenance of CWSS</title>
<script type="text/javascript" src="../scripts/Govt_fin_year_report.js"></script>
<script type="text/javascript" src="../../scripts/cellcreate.js"></script> 
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>

<script type = "text/javascript" > 
//function datept() 
//{
//	alert("inside ");

	//var D1=document.getElementById("d1").value;  
//	var D2=document.getElementById("d2").value; 
//		var k1=new Date(D1);
//	k2=k1.getFullYear();
	
//	alert("year of d1 is "+k2); 
	
//	var date1=Date.parse(D1);
//	var date2=Date.parse(D2);
	
//var timeDiff = date2 - date1;
  //          daysDiff = Math.floor(timeDiff / (1000 * 60 * 60 * 24));
  //          var day=daysDiff+1;
 //           alert("day is :"+day);


///}
  
</script> 



</head>  
<body> 
<form action="Govt_fin_year_report.jsp" method="get" name="myform">
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
			int prvmonth=obj.prv_month(year,month);
			int prvyear=obj.prv_year(year,month);
%>
<table align="center" width="75%" cellpadding="10" cellspacing="0" border=1 bordercolor="darkgray">
 <tr bgcolor="skyblue">
 		<td colspan="3" align="center"><%=Office_name%></td>
 </tr>
  <tr>
 		<td colspan="3" align="center">Performance of Maintenance of CWSS</td>
 </tr>
 <tr>
		<td> <font color="#0000A0">Region</font></td>       
		<td colspan="2"><%=combo1%></td>
	</tr>
	<tr>
		<td class=tdText width="20%"   > <font color="#0000A0"> Division </font></td>
      <td colspan="2"><%=combostr%></td>
     
     </tr>
    
   
     
 <tr class="tdText">
     	<td>
        	<label id="benname"> <font color="#0000A0"> Financial Year</font> </label> 
           </td>
            <td   >
            From Date <input type="date" value="date1" id="d1">
            	   to 
                 <input type="date" value="date2" id="d2">
                 
             </td>
             <td><% if (Office_id.equals("5000")) {%><input type="button" value="All Division" onclick="rpt1()">
              <input type="button" value="All Div new" onclick="rpt11()">
              <input type="button" value="All Div Private" onclick="rpt12()">
              <input type="button" value="Arr for sel Div" onclick="rpt13()">
              <input type="button" value="Ben type" onclick="rpt14()">
              <input type="button" value="sch Abstract" onclick="rpt15()">
     <!--         <input type="button" value="Sch ben" onclick="rpt16()">   -->  
              
              <% } %>
             	<input type="button" value="Sel Div" onclick="rpt()"> 
     	</td>
     </tr>   
     
     
     <tr class="tdText">
    	<td> <font color="#0000A0"> Year</font></td>
         <td> <select id="process_year"  tabindex="5" style="width:60pt"  class="select" onchange="report_period_verify(document.getElementById('process_month'),this)">
                	<option value="0" selected="selected">- - - Select - - -</option>
                	<%
                	for (int j=year;j>=2015;j--)

               //  	for (int j=year-6;j<=year;j++)
                	{
                		if (j==prvyear) { 
                		%><option value="<%=j%>" selected><%=j%></option>
                		<%}else { %><option value="<%=j%>"><%=j%></option><% } %>
                	<%} %>
                 </select>
	        &nbsp;&nbsp;&nbsp;Month &nbsp;&nbsp;&nbsp;
            <select id="process_month"  style="width: 100pt" class="select" onchange="report_period_verify(this,document.getElementById('process_year'))">	         
	         	<option value="0" selected="selected" >- - - Select - - -</option>
	         	<%	         	
	         	String[] amonth = { "-select month-", "January", "February", "March", "April",
				"May", "June", "July", "August", "September", "October", "November", "December" };	         	
	         	for (int i=1;i<=12;i++) 
	         	{
	         		if (i==prvmonth) { 
	         	%><option value="<%=i%>" selected><%=amonth[i]%></option>
	         	<%}else { %><option value="<%=i%>" ><%=amonth[i]%></option>
	         	<% }
	         	} %>
	          </select>
           </td>
             <td><% if (Office_id.equals("5000")) {%><input type="button" value="All Division" onclick="rpt3()"> <input type="button" value="All Division new" onclick="rpt5()"><% } %><input type="button" value="Selected Division" onclick="rpt4()"></td>
     </tr>
     <tr>
     	<td colspan="31">
       	<select id="rtype" name="rtype" class='select'>
       						<option value="0">Select Report Type</option>
							<option value="2" selected>  Excel </option>
							<option value="1">  PDF </option>  
		</select></td>     		
     </tr>
       <tr> 
       	<td colspan="3" align="center">	
       		<input type="button" value="Exit" onclick="window.close()"></td>
       </tr>    
    
  </table>
  </form>
</body>
</html>