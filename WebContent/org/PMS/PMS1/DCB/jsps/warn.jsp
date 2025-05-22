<%@ page language="java" contentType="text/html; charset=ISO-8859-1" import="java.sql.*,Servlets.PMS.PMS1.DCB.servlets.Controller"  pageEncoding="ISO-8859-1"%>
 <html>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>  
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%	
	Connection con;
	Controller obj=null;
	String userid="",Office_id="",Office_Name="";
	try
	{
		  obj = new Controller();
		con=obj.con();
		obj.createStatement(con);
		try
		{
		   userid=(String)session.getAttribute("UserId");
		  }catch(Exception e) { }    
		if(userid==null)
		{
			 response.sendRedirect(request.getContextPath()+"/index.jsp");
		}
		Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		if (Office_id.equals("")) Office_id="0";
		Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");		 
		String smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
		String syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);		
		int month_set=0,year_set=0;	 
		if ((Integer.parseInt(smonth)) == 1) {  
			month_set = 12;
			year_set = Integer.parseInt(syear) - 1;
		} else {
			month_set = (Integer.parseInt(smonth) - 1);
			year_set =Integer.parseInt(syear);
		}
		String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};	
		String count1=obj.getValue("PMS_DCB_TRN_MONTHLY_PR"," count(distinct BENEFICIARY_SNO) ","c1","where OFFICE_ID="+Office_id+" and MONTH="+month_set+" and YEAR="+year_set);
		//out.println(count1);
		
		
		if (Integer.parseInt(count1)==0)
		 {  
 
 
						String qry="";
						 qry="update PMS_DCB_SETTING set PR_ENABLE='Y' where YEAR="+syear+" and MONTH="+smonth+" and OFFICE_ID="+Office_id;
						  
						try
						{
							int res=obj.setUpd(qry);
							if (res >0)
							{
								
							%>
						<script>
							alert ("Pumping Return Entry Authorized ");
						</script>
							<%
							response.sendRedirect("Bill_Pumping.jsp");
							}
							
						}catch(Exception e) {}}
		else{
						
		
		String count2=obj.getValue("PMS_DCB_TRN_BILL_DMD"," count(distinct BENEFICIARY_SNO) ","c1","where OFFICE_ID="+Office_id+" and BILL_MONTH="+month_set+" and BILL_YEAR="+year_set);
		//out.println(count2);
		String count3=obj.getValue("PMS_DCB_LEDGER_ACTUAL"," count(distinct BENEFICIARY_SNO) ","c1","where OFFICE_ID="+Office_id+" and MONTH="+month_set+" and YEAR="+year_set);
		//String prset=obj.setValue("prset",request);
	//	out.println(prset);
		//String mnth=obj.setValue("smonth",request);
		// syear=obj.setValue("year",request);
%>

 <table align="center" border=1 width="80%" cellpadding="10" cellspacing="0">
  <tr class="tdH"> 
	          <td colspan=4 height=30 align="center">
	         	   <font size="4" > <%=Office_Name%> </font> <font color="red"><label id="msg"></label></font>
	          </td>
	 </tr>    
 <tr>  
 		<td align="center"> 
 			 <font size="5" color="red">Warning</font>     
 		</td>
 </tr>
 
 
    
 <%
   
 
 if (Integer.parseInt(count1)!= Integer.parseInt(count2))
		 {  
 %>  
 <tr>
  	<td align="left">
 		<strong> 
 		Bills Not Generated online for the month of <%=cmonth[month_set]%>&nbsp;<%=year_set%>
 		 </strong>
 	</td>
 </tr>
 <% } %> 
 <% if (!count1.equalsIgnoreCase(count3))
 {
%>
 <tr>
  	<td align="left">
 	 <strong>
 		Ledger Data Not Uploaded for  all Beneficiaries for the month of <%=cmonth[month_set]%>&nbsp;<%=year_set%>
 		</strong> 
 	</td> 
 </tr>  
 <% }  %>
 
  <% if (count1=="0")
 {
%>
 <tr>
  	<td align="left">
 	 <strong>
 		Ledger Data Not Uploaded for  all Beneficiaries for the month of <%=cmonth[month_set]%>&nbsp;<%=year_set%>
 		</strong> 
 	</td> 
 </tr>  
 <% }  %>
 
 
 
 <tr> 
  	<td> <font size=4  color=green> 
 		<strong>  
 			 Pumping Return entry for the month of <%=cmonth[Integer.parseInt(smonth)]%>&nbsp;<%=syear%> will be Enabled only if <Br>
 			Bills and ledger statements are generated online for the month of <%=cmonth[month_set]%>&nbsp;<%=year_set%> 
 		</strong></font>
 	</td> 
 </tr>  
 
 <tr>
  	<td align="center"><input type="button" value="   Exit   " onclick="javascript:window.close()" style="color: red;"></td>
 </tr>
 <tr>  
 		<td align="right">  
 			 <font size="3"><pre>Please contact TWAD, HO for further clarification</pre></font>     
 		</td>
 </tr>
 </table>
</body> 
<%
  
		}}catch(Exception e)
	{
	
		out.println(e);
		
	}
%>
</html>