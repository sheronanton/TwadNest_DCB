<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scheme Wise Water Charges Collection</title>
<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>  
<script type="text/javascript">
function rld1()    
{
	var rtype= document.getElementById("rtype").value;
	var finyear= document.getElementById("finyear").value;    
	var s=window.open("../../../../../../Pms_Dcb_Ledger_Report?option="+rtype+"&pr=160&year="+finyear);			
}
function rld3()    
{
	var month= document.getElementById("month").value; 
	var year= document.getElementById("year").value;          
	var rtype= document.getElementById("rtype").value;
	

	
	if (year=="0" || month=="0")
	{                      
		alert("Select Month and Year! ");  
	}        
	else        
	{
		
		var s=window.open("../../../../../../Pms_Dcb_Ledger_Report?option="+rtype+"&pr=161&year="+year+"&month="+month);
		
	}  
}        
function rld()    
{
	var month= document.getElementById("month").value; 
	var year= document.getElementById("year").value;          
	var rtype= document.getElementById("rtype").value;
	

	
	if (year=="0" || month=="0")
	{                      
		alert("Select Month and Year! ");  
	}        
	else        
	{
		
		var s=window.open("../../../../../../Pms_Dcb_Ledger_Report?option="+rtype+"&pr=9&year="+year+"&month="+month);
		
	}  
}          
</script>  
</head>
<%
		String Office_id="0",Office_Name="0",userid="";
		Controller obj = new Controller();		
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		int prvfinyear=obj.prvfinyear(year,month);
		int prvmonth=obj.prv_month(year,month);
		int year_incr=0;
		if (month <4)
			  year_incr=0;    
		else
			  year_incr=1;
		obj.createStatement(obj.con());
		  userid = (String) session.getAttribute("UserId");
		if (userid == null) {
		 response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

		//Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID  from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		if (Office_id.equals("")) Office_id="0";
		Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
		
%>
<body>
<table align="center" width="45%" cellpadding="5" cellspacing="0" border="1" style="border-collapse: collapse;">
	<tr  bgcolor="skyblue">
	 <td colspan="2" align="center" class="tdText"><b>Scheme Wise Water Charges Collection <%=Office_Name %></b></td>
	</tr>
	
     <tr class="tdText">   
     		<td>     
        	<label id="benname">Year </label>                            
          </td>
            <td >
            	<select id="year"  tabindex="5" style="width:220pt"  class="select" onchange="report_period_verify(document.getElementById('month'),this)">
                	<option value="0" selected="selected">- - - Select - - -</option>
                	<%
                	
                	for (int j=2012;j<=year;j++)
                	{       
                	%>    
                	<option value="<%=j%>"><%=j%></option>
                	<%} %>
                	  

                	
                 </select>
             </td>
     </tr> <tr class="tdText">  
    	<td>Month </td>
         <td>  
	         <select id="month"  style="width: 220pt" class="select"  onchange="report_period_verify(this,document.getElementById('year'))" style="width: 80pt;" >
	         
	         	<option value="0" selected="selected" >- - - Select - - -</option>   
	         	<%
	         	
	         	String[] amonth = { "-select month-", "January", "February", "March", "April",
				"May", "June", "July", "August", "September", "October", "November", "December" };
	         	
	         	for (int i=1;i<=12;i++) {
	         	%>
	         	<option value="<%=i%>" ><%=amonth[i]%></option>
	         	<%} %>
	          </select><input type=button id="b1" value="New Report" onclick="rld3()">   
         </td>
     </tr>
       <Tr>
		    <td>  
				 Financial Year 
			</td>
			 	<td colspan="2">
            	<select id="finyear"   tabindex="5" style="width:120pt"  >
                	<option value="0" selected="selected">- - - Select - - -</option>
                	<%                	
                	for (int j=year-5;j<=year+year_incr;j++)
                	{
                		if (j-1==prvfinyear)
                		{
                	%>
                	<option value="<%=j-1%>" selected="selected"><%=j-1%>-<%=j%></option>
                	<%
                		}
                		else
                		{
                			%>
                        	<option value="<%=j-1%>"><%=j-1%>-<%=j%></option>
                        	<%		
                		}
                		 
                	}
                	%>               	
                 </select><input type=button id="b1" value="Report" onclick="rld1()">      
             </td>
        </Tr> 
     <tr><Td>Report Type</td><td>
				<select id="rtype" name="rtype" class='select'>
						<option value="0">Select  </option>
							<option value="2">  Excel </option>
							<option value="1" selected="selected">  PDF </option>  
				</select></Td>
	</tr>
      <tr>
      	<td align="center" colspan="2">
      		<input type=button id="b1" value="Report" onclick="rld()">      	 
       		<input type="button" value="Exit" onclick="window.close()"></td>
       </tr>    
</table>
	
</body>
</html>