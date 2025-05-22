<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@ page import="java.util.Calendar,java.sql.*,Servlets.Security.classes.UserProfile"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Opening Balance Unfreeze Request</title>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
   <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<script type="text/javascript">
		
		function rld_clr()
		{
			try
			{ 
			document.getElementById("msg_new").innerHTML="";
			document.getElementById("b1").disabled=false;
			}catch(e) {}
			document.forms["freezerequest"].submit(); 
		}
		function rld()
		{
			var month=document.getElementById("smonth").value;
			var year=document.getElementById("year").value;
			var reason=document.getElementById("reason").value;
			var count=0;
			if (parseInt(month)==0)
			{	
				alert("please select month !!! ");
				count=1;
			} else if (parseInt(year)==0)
			{
				alert("please select year !!! ");
				count=1; 
			}else if (reason=="")
			{
				alert("please enter the reason !!! ");
				count=1; 
			}
			if (count==1)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
</script>
</head>   
<body>  
<form action="OBunfreeze_request.jsp" name="freezerequest" method="post" onsubmit="return rld()"> 
<%
	 	String userid="112",Office_id="",Office_Name="";
		String smonth="",syear="",html="";
		String process="0";
		Controller obj=new Controller();
		Connection con=null; 
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year =  cal.get(Calendar.YEAR);	
		int prv_month=0;
		int prv_year=year;
		String disable="";
		UserProfile up = null;
	
		try
		{     
			con=obj.con();
			obj.createStatement(con);
			userid=(String)session.getAttribute("UserId");
			
			if(userid==null)
			{
			 	response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
				up = (UserProfile) session.getAttribute("UserProfile");
		
		
				Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

		//Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
			if (Office_id.equals("")) Office_id="0";
			Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
			
			syear=obj.setValue("year",request); 
			if (month>3) 
			{
				smonth=Integer.toString(month);
	    		syear=syear;
			}else
			{
				smonth="4";
	    		syear=Integer.toString(year-1);
			}
				//smonth=obj.setValue("smonth",request);
		   		//
		    
		    smonth="4";
		    	prv_year=obj.prv_year(Integer.parseInt(syear),Integer.parseInt("4"));
		   		prv_month=obj.prv_month(Integer.parseInt(syear),Integer.parseInt("4"));
			process=obj.setValue("process",request);
			String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
			String mmsg="";
			int freeze_status=0,already_freezed=0; 
			String freeze_on="",freeze_by=""; 
			try  
			{
				   
			    freeze_status=obj.getCount("PMS_DCB_OB_FREEZE", "where OB_FREEZE='Y' and  month="+smonth+" and year="+syear+" and  OFFICE_ID="+Office_id);
			    freeze_on=obj.getValue("PMS_DCB_OB_FREEZE", "to_char(UPDATED_DATE,'dd/mm/yyyy')","frdate","where month="+smonth+" and year="+syear+" and  OFFICE_ID="+Office_id);
			    ///freeze_on=(freeze_on.equalsIgnoreCase("0")?"":" on " +freeze_on);
			    freeze_by=obj.getValue("PMS_DCB_OB_FREEZE", "UPDATED_BY_USER_ID","id","where month="+smonth+" and year="+syear+" and  OFFICE_ID="+Office_id);
			    if (!freeze_by.equalsIgnoreCase("0"))
			    { 
			    	freeze_by=freeze_by.substring(4);
			    	freeze_by=obj.isNull(obj.getValue("HRM_MST_EMPLOYEES", "EMPLOYEE_NAME","where GPF_NO="+freeze_by),5);
			    }
			    freeze_by=obj.isNull(freeze_by,4);
			    freeze_on=obj.isNull(freeze_on,4);
			    already_freezed=obj.getCount("PMS_DCB_OB_FREEZE", "where UNFREEZE_REQUEST='Y' and  month="+smonth+" and year="+syear+" and  OFFICE_ID="+Office_id);
				String remarks =obj.setValue("reason",request);
				String sub=obj.setValue("b1",request);
						if (sub.equalsIgnoreCase("Submit"))
						{ 
							if (already_freezed==0)
							{
						
								String ins_qry="update PMS_DCB_OB_FREEZE set UNFREEZE_REQUEST=? , REMARKS=?,REQUEST_USER_ID=?, REQUEST_DATE=clock_timestamp() where  month="+smonth+" and year="+syear+" and  OFFICE_ID="+Office_id;
								PreparedStatement ps=con.prepareStatement(ins_qry);				
								ps.setString(1,"Y");
							 	ps.setString(2,remarks);
							 	ps.setString(3,userid);
								int r_s=ps.executeUpdate();
								if (r_s>=1) 
								{%>
									<script type="text/javascript">
									alert("Opening Balance UnFreeze Request Sent to Head Office");
									</script>
						  		<%} 
								r_s=0;
						} else
						  		{
						  			%>
									<script type="text/javascript">
									alert("Opening Balance UnFreeze Request Not Sent to Head Office \n \t\t\t\t\tOr \n Request already sent   ");
									</script>
						  		<%
						  		}
				 }
				
							
			}catch(Exception e)
			{
				out.println(e);
			} 
		 %> 
		<table border="1" width="65%" align="center" cellpadding="5" cellspacing="0" style="border-collapse: collapse" >
			<tr>
			      <th align="center" colspan="2">
			       		Opening Balance Unfreeze Request <br>   <jsp:expression>Office_Name</jsp:expression> <font class="tdText" color="red"><label id="msg"></label></font>
			     </th>
			</tr>
			<tr class="tdText"> 
				 <td class="tdText">Month</td>
        		 <td align="left"> 
         			<select class="select" id="smonth" name="smonth"  style="width:100pt" onchange="report_period_verify_2(this,document.getElementById('year')),rld_clr()"  >
        			<option value="0">Select</option>
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
		  <tr>
		 		<td class="tdText">Year</td>
		 		<td>		 
         			<select class="select" id="year" name="year"  style="width:100pt"  onchange="report_period_verify_2(document.getElementById('smonth'),this),rld_clr()">
					<option value="">-Year-</option>
					<%
             			for (int j=year-4;j<=year;j++)
             			{
             				if (j==prv_year) 
        		   			{
        		   	%>
        		   	<option value="<%=j%>" selected="selected"><%=j%></option><% } else {	%>
        		    <option value="<%=j%>"><%=j%></option><%
        		    		}
             		  	} %> 
		 			</select> 
		 		</td>	 
       	</tr>
       	<tr>
       		<td>Status</td>
       		<% 
       			if (freeze_status==0) out.println("<td><font color=red>Opening Balance Not yet Freezed</font></td>");
       			else
       			 out.println("<td><font color=green><b>Opening Balance Freezed</b></td>");
       		 %>
       	</tr>
       	<%
       	 
       		if (!freeze_on.equalsIgnoreCase(""))
       		{
       	 %>
       	<tr>
       		<td>Freezed By</td>       		
       			<%
       			 out.println("<td><font color=blue><b>"+freeze_by+" &nbsp;&nbsp;  "+freeze_on+"</td>");
       			 %>
       		</tr>
       		<div>
       		<%}
       		 
       		if (already_freezed==1)
       		{
       			out.println("<tr><td colspan='2' align='center'><div id='msg_new'><font color='red'>Your Request Already sent to Head office </font></td></tr>");
       			disable="disabled='disabled'";
       		}
       		 %>
       		 </div>
       <tr>
       	<td>Reason for Unfreeze<font color="red">**</font></td>
       	<td>
       			<textarea rows="10" cols="30" id="reason" name="reason"></textarea>
       	</td>
       </tr> 
       <tr>
       		<th colspan="2" align="center"><input type="submit" id="b1" name="b1" value="Submit" <%=disable%>><input type="button" value="Exit" onclick="window.close()">
       		</th>
       </tr>
       </table> 
       <%
        obj.conClose(con);
		}catch(Exception e) 
		{
			out.println(e);
		 	response.sendRedirect(request.getContextPath()+"/index.jsp");
		} 
       %>
  </form>     
</body>
</html>