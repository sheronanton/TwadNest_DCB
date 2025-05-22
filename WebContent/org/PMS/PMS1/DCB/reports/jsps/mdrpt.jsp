<html>
<head>     
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link href="../../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<title>TWAD PHASE II | Review Report</title>
<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<script type="text/javascript" src="../../scripts/Office_Shift_new.js"></script>
<script src="jquery-3.6.0.js" type="text/javascript"></script> 
<script type="text/javascript" >
//$("#off_id").val() != '5000' ? $("#d1").show() : $("#d1").hide();

   $(function get() {
               var tname = $("#off_id").val();
            if (tname != "5000") {
                $("#d1").show();
                $("#d2").show();
                } else {
                 $("#d1").hide();
                 $("#d2").hide();
                }
         })
	
</script>
<script type="text/javascript">
function rld1(a)
{
	document.forms["myform"].submit();   
}
function rld(a)
{
	 var fmonth=document.getElementById('pmonth').value;
	 var fyear=document.getElementById('pyear').value; 
	 var div=document.getElementById('div').value;
	 var rtype=document.getElementById('rtype').value;
	 var regdiv=document.getElementById('rtype').value;

	 var Off=document.getElementById('def_div').value;
	 
	// alert("Off is "+Off);
	 
    if (fmonth=="0" || fyear=="0" || regdiv=="0" || div=="0")
    {
		alert("Select Region,Division,Year and Year"); 	   
    }else 
    {
        
    	if (parseInt(a)!=0)   
    	{
        	var url="../../../../../../Bill_Demand?command=mdrpt&process_code="+a+"&option_pdf_xls="+rtype+"&fmonth="+fmonth+"&fyear="+fyear+"&div="+div+"&Off="+Off;
//alert(url);
        	 window.open(url);
    	}
    	else
    	{
    		alert("Select Report Title");
        }
    }
}
</script>
</head>
<body onload="check2(),getid(),get()">
<form action="mdrpt.jsp" method="get" name="myform">
<%
String userid="0",Office_id="",Office_Name="",table_heading="",table_column="",table_header="";
String combo1="";
Connection con=null;
String smonth="",syear="",html="",office_="";
Controller obj=null;
Calendar cal = Calendar.getInstance();
int day = cal.get(Calendar.DATE);
int month = cal.get(Calendar.MONTH) + 1;
int year = cal.get(Calendar.YEAR);
int prv_month=0;  
if (month==1) prv_month=12;
else
prv_month=month-1;   
int prv_year=year;
if (prv_month==12)  prv_year=year-1; 
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
		  //response.sendRedirect(request.getContextPath()+"/index.jsp");
	}
	
	 // Office_id=obj.getValu("HRM_EMP_CURRENT_POSTING", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp()  THEN OLD_OFFICE_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;
	Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	
//	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
	if (Office_id.equalsIgnoreCase("0")) Office_id="5000";
	smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
	syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
	if (Office_id.equals("")) Office_id="0";
	String regdiv=obj.isNull(obj.setValue("regdiv",request),1);
	String disable="";
	if (Integer.parseInt(Office_id)!=5000)
	disable=" disabled=disabled";
	combo1=obj.combo_str("COM_MST_ALL_OFFICES_VIEW","OFFICE_NAME","OFFICE_ID"," where OFFICE_LEVEL_ID='RN' order by OFFICE_ID ","regdiv",regdiv," class=select onchange='rld1(this.value)' "+disable);
	String rd="";
	
	
	
    Office_Name=obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", " where OFFICE_ID="+Office_id);
	if (Integer.parseInt(Office_id)==5000) rd=""; else rd="where OFFICE_ID="+Office_id;
	if (Integer.parseInt(Office_id)!=5000)
	office_=obj.combo_str("COM_MST_OFFICES","OFFICE_NAME","OFFICE_ID"," where OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP "+rd+" )   ","div",Office_id," class=select  "  );
	else
	office_=obj.combo_str("COM_MST_ALL_OFFICES_VIEW","OFFICE_NAME","OFFICE_ID"," where OFFICE_LEVEL_ID='DN' and  REGION_OFFICE_ID="+regdiv+" and  OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP  )  ","div",Office_id," class=select "  );		

}catch(Exception e)
{
	userid="0";
	out.println(e);	  
}
String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
%>  
  
 <table border="1" width="85%" align="center" cellpadding="10" cellspacing="0" >
	<tr  bgcolor="#20f0f0"><td colspan="2" align="center"><b>Review Report</b></td></tr>
	<tr> 
		<td><font color="#0000A0">Region</font></td>
		<td><%=combo1%><input type="text" hidden value="<%=Office_id%>" id="off_id" name="off_id" > 
		<input type="text"  hidden id="def_name" name="def_name" > 
		</td>
		
	</tr> 
	<tr>	
		<td class=tdText width="40%"><font color="#0000A0">Current Division </font></td>
     	<td><%=office_%></td>
     </tr>
     
             	 <tr>
 <td> <div id="d1">  Select Defunct Division </div> </td>
 <td>  <div id="d2">  
 <select name="def_div" id="def_div" onblur="check2()">
  <option value="<%=Office_id%>"> <%=Office_Name%> </option>
  
 </select>
 </div>
 </td>
 </tr>
     
	 <tr>
	     <td><font color="#0000A0">Year </font></td>
		 <td colspan="2">
		  <select  class="select"  id="pyear" name="pyear"  style="width:120pt;" onchange="report_period_verify(document.getElementById('pmonth'),this)" onblur="check1()" >
		  <option value="0">-Select Year-</option>
		  <%
			  for (int i=year-6;i<=year;i++)
			  {
		   		if (i==prv_year) 
		   		{
		   		%>
		   		<option value="<%=i%>" selected="selected"><%=i%></option>
		  		<% } else {	%> <option value="<%=i%>"><%=i%></option><%}
			   }
		  %>
				  </select>
			   </td>
			  </tr>
			 <Tr >
			   <td><font color="#0000A0">Month </font></td>	  	          
			   <td colspan="1">
			   		<select class="select" id="pmonth" name="pmonth"  style="width:120pt;" onchange="report_period_verify(this,document.getElementById('pyear'))" onblur="check1()">
					  	<option value="0">-Select Month-</option>
				  		<%
				  			for (int j=1;j<=12;j++)
				  			{
				  			  if (j==prv_month) { 
				   		%>  <option value="<%=j%>" selected="selected"><%=monthArr[j]%></option><% }  else { %>
					    	<option value="<%=j%>"><%=monthArr[j]%></option> <% } %>
				  		<%  } %>
				 </select>
			 </td>	        
          	</tr>
     
    	
          	
          	<tr>
          		<td>
          			<font color="#0000A0">Reports</font>     
          		</td>
          		<td>
          		<!--  <option value="1"  style="visibility: hidden">Beneficiary Wise</option>-->
          					<!--<option value="2" style="visibility: hidden">Beneficiary Type Wise Abstract</option>-->
          			<select   id="rpt"  style="width:270pt;">
          					<option value="0" selected>Select Report Title</option>					 
          					<option value="3">Scheme Wise Receipt in Lakhs</option>
          					
          					<option value="888">Habitation Wise Quantity</option>
          					
          					 <option value="4">Annexure I Abstract- Beneficiary Type wise in Lakhs</option>          					
          					<option value="7">Annexure I Abstract- Beneficiary Type wise in Actual</option>   
          					     					
          					<option value="6">Beneficiary - Union Wise in Actual</option>
          					<option value="66">Beneficiary - Union Wise in Lakhs</option>
          					<option value="5">Annexure II - Beneficiary Wise Details in Lakhs</option>
          					<option value="55">Annexure II - Beneficiary Wise Details in Actual</option>
          					<!--
          					<option value="9">Annexure II - Beneficiary Wise Details With Interest</option>
          					--></select>
          		</td>
          	</tr>  
          	
          	 <tr><Td>Report Type</td><td>
				<select id="rtype" name="rtype" class='select'>
							<option value="0">Select  </option>
							<option value="1" >  PDF </option>
							<option value="2">  Excel </option>							  
				</select>&nbsp;&nbsp;&nbsp;<input type="button" class="fb2"  value="Print" onclick="rld(document.getElementById('rpt').value)" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="fb2"  value="Close" onclick="javascript:window.close()"></Td>
			</tr>
          	<tr>
          		<td class="tdText" colspan="2" align="center">
          			<input type="hidden" id="" class="fb2" name="" value="Beneficiary Wise" onclick="rld(1)">
          			&nbsp;<input type="hidden"   class="fb2" value="Scheme Wise Receipt" onclick="rld(3)">			
       			   	&nbsp;<input type="hidden" id="" name=""  class="fb2"  value="Beneficiary Type Wise Abstract" onclick="rld(2)">
       			   	&nbsp;<input type="hidden" id="" name=""  class="fb2"  value="Annexure I - Beneficiary Wise" onclick="rld(5)">
       			   	&nbsp;&nbsp;<input type="hidden" id="" name=""  class="fb2"  value="Annexure I - Beneficiary Type wise " onclick="rld(4)">       			   	
       		  </td>
       		  
       		  
       		</tr>  
	</table>
	
	</form> 
</body>
</html>
