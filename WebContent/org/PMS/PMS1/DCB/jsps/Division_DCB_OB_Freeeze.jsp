<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*,java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Opening Balance Freeze</title>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
function rld()
{
	  document.forms["freeze"].submit();
}
function view()
{
	  var month=document.getElementById("month").value;
      var year=document.getElementById("year").value;
	  var v=window.open("../reports/jsps/actual_data_verificaiton.jsp?year="+year+"&month="+month);
}
 
</script>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
</head>

<body>
<%
	String userid="0",Office_id="",Office_Name="";
	Calendar cal = Calendar.getInstance();
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
	Controller obj=new Controller();
	Connection con=null;
	java.text.DecimalFormat df=new java.text.DecimalFormat("0.00");
	String pmonth="0",pyear="0";
	try
	{
	  con=obj.con();
	  obj.createStatement(con);
	  userid=(String)session.getAttribute("UserId");			
	  if(userid==null)
	  {
		 	response.sendRedirect(request.getContextPath()+"/index.jsp");
	  }
	}catch(Exception e) 
	{
		out.println(e);  
	}
	
	Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	// Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
	Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " ");
	  pyear=obj.isNull(obj.setValue("year",request),1);
	int count_2=obj.getCount("PMS_DCB_OB_FREEZE","where OB_FREEZE='Y' and OFFICE_ID="+Office_id+" and YEAR="+pyear);
	String sub=obj.setValue("store",request);
	if (Office_id.equals("")) Office_id="0";
	  if(sub.equalsIgnoreCase("OB Freeze"))
		{
		   if (count_2==0) 
		   {
			Hashtable ht=new Hashtable();
			ht.put("OFFICE_ID",Office_id);
			ht.put("MONTH",4);
			ht.put("YEAR",pyear);			
			ht.put("OB_FREEZE","'Y'");			
			ht.put("UPDATED_DATE","clock_timestamp()");  
			ht.put("UPDATED_BY_USER_ID","'"+userid+"'");
			int r=obj.recordSave(ht,"pms_dcb_ob_freeze",con);
			
			String msg="April -"+ pyear;
			
			Hashtable ht1=new Hashtable();
			Hashtable ht2=new Hashtable();
			ht2.put("OFFICE_ID",Office_id);
			ht2.put("MONTH","4");
			ht2.put("YEAR",pyear); 
			ht1.put("OB_MESSAGE","''");
			int r1=obj.recordSave(ht1,ht2,"PMS_DCB_SETTING",con);
			
			if (r > 0) 
			{  
				count_2=r;
				sub="";
				%>
				<script> 
				 	alert (" Opening Balance Freezed Successfully ");
					 window.close();
				</script>		
				<%
			}
		   }
		   else 
			{
				%>
				<script>
				 	alert (" Opening Balance Aleady Freezed  "); 
				 	window.close();
				</script>		
				<%
				
			}
		}
		String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
	  int prvfinyear=obj.prvfinyear(year,month);
	  int year_incr=0;
	  if (month <4) 
		  year_incr=0;    
	  else
		  year_incr=1; 
%> 
	<form action="Division_DCB_OB_Freeeze.jsp" method="post" name="freeze">
	<table   align="center" border=1 width="70%"  bordercolor="#00FFFF" cellpadding="5" style="border-collapse: collapse" cellspacing="0" > 
	<tr >  
		<td colspan="2" align="center">Opening Balance Freeze</td>
		</tr> 
	<tr>
		<td colspan="2" align="center"><%=Office_Name%></td>
	</tr>
 <tr class="tdText"> 
				 <td class="tdText">Month</td>
        		 <td align="left"> 
         			<select  class="select" id="smonth" name="smonth"  style="width:100pt" onchange="report_period_verify_2(this,document.getElementById('year')),rld_clr()"  >        			
		 			<%
						for (int i=4;i<=4;i++) 
						{
							if (i==4) 
							{
						%>
						<option value="<%=i%>" selected="selected"><%=cmonth[i]%></option><% }  else { %>
						<option value="<%=i%>"><%=cmonth[i]%></option> <% 
							} 
						%>	
							  
					<%	} %>
		 			</select> 
		 		</td>
		  </tr>
    <tr class="tdText">
    	<td>
        	<label id="benname" >  Year </label> 
    	</td>
        <td>
        	<select id="year" name="year"   tabindex="5" style="width:120pt"  class="select">
            <option value="0" selected="selected">- - - Select - - -</option>
            <%	for (int j=year-3;j<=year+year_incr;j++)
                	{
                	if (j-1==prvfinyear) {
      				 %>
       				 <option value="<%=j-1%>" selected><%=j-1%></option>
       				 <% } else { %>
        			<option value="<%=j-1%>"><%=j-1%></option>
       				<%}} %>                	
                 </select>
             </td>
     </tr>
   
    <tr> 
    
    <td align="center" colspan="2">
     	<input type=submit name="store" id="store" value="OB Freeze">
     	<input type="button" class="fb2" value="Exit" onclick="javascript:window.close();"> 
    </td>
    </tr>
    </table> 
</form>
</body>
</html>