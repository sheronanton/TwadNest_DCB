<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="Servlets.PMS.PMS1.DCB.Impl.Common_Impl"%><html>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller" %>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TWAD Nest II | Division Wise DCB Reports</title>
 <link href="../../../../../../css/AME.css" rel="stylesheet" media="screen"/>
 
 	 <script type="text/javascript" src="../../scripts/Office_Shift_new.js"></script>
 
	 <script type="text/javascript">
	 
	 function rld1(a)
	 {
		document.forms["myform"].submit();
	 } 
	 
	 function Data1()
	 {
		var year1=document.getElementById("year1").value;
		//alert("year1 is"+year1);
	 }
	 
	 function Disp()
	 {
		
		//	alert("inside Par")
		 document.getElementById("disp1").style.display="block";
		 document.getElementById("disp2").style.display="block";
		 
	 }
	 
	 function ChooseReptype(choise)
	 {
		// alert("choise is"+choise);
		if (choise == "Par")
			{
		//	alert("inside Par")
		 document.getElementById("dispsupno1").style.display="block";
		 document.getElementById("dispsupno2").style.display="block";
		 document.getElementById("dispsupno3").style.display="block";
		 document.getElementById("dispsupno4").style.display="block";
		 document.getElementById("b1").hidden="true";
		 document.getElementById("b2").hidden="";
		 document.getElementById("b3").hidden="true";
		 document.getElementById("b4").hidden="true";
			}
		else
			{
		//	alert("inside all")
			 document.getElementById("dispsupno1").style.display="none";
			 document.getElementById("dispsupno2").style.display="none";
			 document.getElementById("dispsupno3").style.display="none";
			 document.getElementById("dispsupno4").style.display="none";
			 document.getElementById("b1").hidden="";
			 document.getElementById("b2").hidden="true";
			 document.getElementById("b3").hidden="";
			 document.getElementById("b4").hidden="";
			}
	 }
		 
	 </script> 
	  <script type="text/javascript" src="../scripts/reg_menu_index.js"></script>
 <script type="text/javascript" src="../../scripts/cellcreate.js"></script>
  <script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
  
 <script src="jquery-3.6.0.js" type="text/javascript"></script> 
	  
  
</head>
<body onload="getid()" > 
<form action="reg_menu_index.jsp" method="get" name="myform">

<table align="center" width="75%" cellpadding="2">
<tr bgcolor="skyblue">
	<td colspan="2" align="center" class="tdText"><b>Division Wise DCB Reports</b></td>
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
			obj=new Controller();
			con=obj.con();
		 	obj.createStatement(con);
		 	String cb=obj.combo_str("PMS_DCB_MIS_REPORT_TITLE","REPORT_HEADING","REPORT_REF"," order by DISPLAYORDER","rpt","","class=select style='width:290px' ");  
		 	Calendar cal = Calendar.getInstance();
		 	int day = cal.get(Calendar.DATE);
		 	int month = cal.get(Calendar.MONTH) + 1;     
		 	int year = cal.get(Calendar.YEAR);
		 	Common_Impl impl_obj=new Common_Impl(); 
			String Region_id="0";		 
		 	String Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		 	String Office_Name=obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", " where OFFICE_ID="+Office_id);

		 	
		 	if (Office_id.equalsIgnoreCase("0")) Office_id="5000";  
		 	String regdiv=obj.isNull(obj.setValue("regdiv",request),1);
		 	String disable="";
		 	if (Integer.parseInt(Office_id)!=5000)
		 		disable=" disabled=disabled";

		 //	combo1=obj.combo_str("COM_MST_ALL_OFFICES_VIEW","OFFICE_NAME","OFFICE_ID"," where OFFICE_ID=old_OFFICE_ID order by OFFICE_ID ","regdiv",regdiv," class=select onchange='rld1(this.value)' "+disable);
			 // Region_id=impl_obj.regionId(userid,obj);		 
		 String combo=impl_obj.Div_RegionWise(regdiv,"off_id",Office_id);
		 int prvmonth=obj.prv_month(year,month);
		 int prvyear=obj.prv_year(year,month);
		 
		 int year_incr=0;
		  if (month <4) 
			  year_incr=0;    
		  else
			  year_incr=1;
		  int prvfinyear=obj.prvfinyear(year,month);
		 
%>
	
	<tr class="tdText"> 
		<td>   <label id="Division" >Division</label> </td>
		<td><%=Office_Name%>&nbsp;(<%=Office_id%>)   <input type="text" hidden value="<%=Office_id%>" id="off_id" name="off_id" > 
		
		<input type="text" hidden value="<%=Office_id%>" id="off_name" name="off_id" >
		      </td>
	
	</tr> 
 
 
 <tr class="tdText">
     	<td>
        	<label id="benname" >Financial Year </label> 
           </td>
            <td>
            	<select id="year1"   tabindex="5"   class="select" >
                	<option value="0" selected="selected">- - - Select - - -</option>
                	<%	for (int j=year-7;j<=year+year_incr;j++)
                	{
                	if (j-1==prvfinyear) {
      				 %>
       				 <option value="<%=j-1%>"><%=j-1%>-<%=j%></option>
       				 <% } else { %>
        			<option value="<%=j-1%>"><%=j-1%>-<%=j%></option>
       				<%}} %>                	
                 </select>
            &nbsp;&nbsp;&nbsp;&nbsp;
            (OR)
   &nbsp;&nbsp;&nbsp;&nbsp;
  
 	
 		<label id="benname" >Year</label>&nbsp;&nbsp;
	
 	<select id="year" name="year" tabindex="5"  class="select" onchange="report_period_verify(document.getElementById('selmonth'),this),Disp()" >
 	<option value="0" selected="selected">- - - Select - - -</option>
 	<%
 	for (int j=year;j>=2010;j--)
 	// for (int j=2010;j<=year;j++)
 	{
 	%>
 	<option value="<%=j%>" ><%=j%></option>
 	<%} %>
 </select>
 </td>
 </tr>
 
 
 
 
 <tr class="tdText"> 
 	<td><div  id="disp1"  style="display:none">  <label id="Month" >Month </label></div></td>
 	
 	 <td>
 	 <div  id="disp2"  style="display:none">
 	 From
 	 
	 <select id="selmonth1" name="selmonth1" >
	 <option value="0" selected="selected">- - Select - -</option>
	<%
	 	String[] bmonth = { "-select month-", "January", "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December" };
	 	for (int i=1;i<=12;i++) 
	 	{
	 	%>
	 	<option value="<%=i%>" ><%=bmonth[i]%></option>
	 	<%} %>
	 </select>
	 
	 
 To
	 <select id="selmonth" name="selmonth"  class="select" >
     <option value="0" selected="selected">- - Select - -</option>
 	 <%
	 	String[] amonth = { "-select month-", "January", "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December" };
	 	for (int i=1;i<=12;i++) 
	 	{
	 	%>
	 	<option value="<%=i%>"  ><%=amonth[i]%></option>
	 	<%} %>
	 </select>
	 </div>
 </td>
 </tr>

 <tr>
 <td>Select Defunct Division </td>
 <td> 
 <select name="def_div" id="def_div" onchange="get_ben_type()" >
  <option value="0" selected="selected">- - Select - -</option>
 </select>
 
 </td>
 </tr>
 
 
 <tr>
 <td>Select Report Type </td>
 <td> 
 <input type="radio" id="reptype1" name="reptype" value="All" checked onclick="ChooseReptype(this.value)">All Beneficiaries
  <input type="radio" id="reptype1" name="reptype" value="Par"  onclick="ChooseReptype(this.value)">Particular Beneficiary
 </td>
 </tr>
 
 

 <tr>
 <td> <div  id="dispsupno3"  style="display:none">Select Beneficiary  type </div></td>
 <td>  <div id="dispsupno4"  style="display:none">
 <select name="ben_type" id="ben_type" onblur="get_ben_name()" >
  <option value="0">Select Beneficiary type</option>
 </select></div>
 </td>
 </tr>

 <tr>
 <td><div  id="dispsupno1" name="dispsupno1" style="display:none">Select Beneficiary  Name</div> </td>
 <td><div  id="dispsupno2" name="dispsupno1" style="display:none"> 
 <select name="ben_name" id="ben_name" >
  <option value="">Select Beneficiary Name</option>
 </select>
 
 </div>
  </td>
 </tr>
 

 
 <tr>
		<td>Report Type</td>
		<td>
			<select id="reporttype" name="reporttype">
				<option value="1">PDF</option>
				<option value="2">Excel</option>
							
			</select>
		</td> 
	</tr> 
 
 
 <!-- 
	<tr>
	
	<td class="tdText" width="15%">Report Title</td><td></td>
	</tr>
	
	<tr>	
	<td class="tdText" width="15%"><label id="benname" >Division Name</label></td><td></td>
	</tr>
	
	<tr>
		<td>Report Type</td>
		<td>
			<select id="reporttype" name="reporttype">
				<option value="1">PDF</option>
				<option value="2">Excel</option>
				<option value="3">HTML</option>
				
			</select>
		</td> 
	</tr> 
	 -->
	<!-- <Tr><Td colspan="2" ><a href="sch_master_report.jsp">Report Condition</a></Td></Tr>-->
	<tr bgcolor="skyblue" >
	 <td colspan="2" align="center"> 
	 <input type="button" id="b1" onclick="rpt_defunt('defunt')" value="Print"  class="sa"> 
	 <input type="button" id="b3" onclick="rpt_defunt('defunt_Benwise')" value="Print Beneficiary wise"  class="sa"> 
	  <input type="button" id="b4" onclick="rpt_defunt('defunt_Total')" value="Print Beneficiary wise Total"  class="sa"> 
	 
	 <input type="button" onclick="rpt_defunt1()"  id="b2" value="Print" hidden >&nbsp;&nbsp;
	 <input type="button" onclick="window.close()" value="Exit" class="sa" style="font-style: italic;font-size:7;color:red;font-weight: bold" class="fb2">
	 <img src="../../../../../../images/help_images.jpg" height="18px" width="45px" style="vertical-align: bottom;" onClick="window.open('../../jsps/help1.jsp?fn=67','mywindow','width=600,height=400,scrollbars=yes')"></td>
	</tr>
<%
}catch(Exception e) { 
	%>
	<tr bgcolor="skyblue">
	 <td colspan="2" align="left" class="tdText"><%=e%></td>
	</tr>
	<% 
}
%>
</table>
</form>
</body>
</html>