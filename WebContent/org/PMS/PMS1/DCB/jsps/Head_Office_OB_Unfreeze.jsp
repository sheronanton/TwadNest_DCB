<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Head Office - Divisions Opening Balance Unfreeze</title>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>  
<script type="text/javascript">
	function rld1( )
	{
		
		
		document.forms["myform"].action="./Head_Office_OB_Unfreeze.jsp";
		document.forms["myform"].submit();  
	}
	function rld2()
	{
		var row=document.getElementById("row").value;
		var click_count=0;
		for(var i=1;i<=row;i++)
		{
			if (document.getElementById("off"+i).checked)
			{
				click_count++;
			}
		}
		if (click_count==0)
		{
			alert("Select Division Name");
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
<form method="post" name="myform" action="Head_Office_OB_Unfreeze_a.jsp" onsubmit="return rld2()">
<%
		String userid="0",Office_id="",Office_Name="",table_heading="",table_column="",table_header="";
		String combo1="", regdiv="";
		Connection con=null;
		String smonth="",syear="",html="",office_="";
		String reg_id_qry="";
		Controller obj=null;
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		int prv_month=month;
		int prv_year=year;
		if (prv_month==1)  prv_year=year-1; 
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
				  response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
			Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
			if (Office_id.equalsIgnoreCase("0")) Office_id="5000";
			smonth=obj.setValue("pmonth",request);//obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
			syear=obj.setValue("pyear",request);//obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
			
			prv_month=obj.prv_month(year,month);
			prv_year=obj.prv_year(year,month);
			
			if (syear=="0")
				{
					smonth=Integer.toString(prv_month);
					syear=Integer.toString(prv_year);
					if (month==1) prv_month=12;  
					else
					prv_month=month-1;  
					if (prv_month==12)  prv_year=year-1; else prv_year=year; 
				}else
				{
					if (smonth=="0") prv_month=month-1; else prv_month=Integer.parseInt(smonth);;   
					prv_year=Integer.parseInt(syear);
					
				}
			if (Office_id.equals("")) Office_id="0";
			 	regdiv=obj.isNull(obj.setValue("regdiv",request),1);
				reg_id_qry=regdiv.equalsIgnoreCase("0")?" and (1=1) ": " AND a.REGION_OFFICE_ID="+regdiv;
				String disable="";
			if (Integer.parseInt(Office_id)!=5000)
				disable=" disabled=disabled";
			combo1=obj.combo_str("COM_MST_OFFICES","OFFICE_NAME","OFFICE_ID"," where OFFICE_LEVEL_ID='RN' order by OFFICE_ID ","regdiv",regdiv," class=select onchange='rld1()' ");
				String rd="";
			
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
		
		if (month > 3 ) prv_year=Integer.parseInt(syear);
		else
			prv_year=Integer.parseInt(syear);

%>
<table border="1" width="85%" align="center" cellpadding="5" cellspacing="0" style="color: white;border-collapse: collapse" >
	<tr  bgcolor="#11297C"><td colspan="2" align="center"><b>  Opening Balance Unfreeze</b></td></tr>
	
	 <tr>
	     <td><font color="#0000A0">Year </font></td>
		 <td colspan="2">
		 <select class="select"  id="pyear" name="pyear"  style="width:120pt;" onchange="rld1()">
		 	<option value="0">-Select Year-</option>
		  	<%
				for (int i=year-6;i<=year;i++)
			  	{
		   			if (i==prv_year) 
		   			{
		   	%>
		   	<option value="<%=i%>" selected="selected"><%=i%></option><% } else {	%>
		    <option value="<%=i%>"><%=i%></option><%
		    		}
			 	}
		  	%>
		</select></td></tr>
	<tr>
		<td><font color="#0000A0">Month </font></td>	  	          
		<td colspan="1">
		<select class="select" id="pmonth" name="pmonth"  style="width:120pt;" >
		<option value="0">-Select Month-</option>
		<%
				for (int j=4;j<=4;j++)
				{
					if (j==4) 
					{
		%>
				<option value="<%=j%>" selected="selected"><%=monthArr[j]%></option><% }  else { %>
				<option value="<%=j%>"><%=monthArr[j]%></option> <% 
					} 
				 } 
		%> 
		</select> 
		</td></tr>
		<tr> 
		<td><font color="#0000A0">Region</font></td>
		<td><%=combo1%></td>
		</tr> 
	 
		</table>
		<% 
			int row=0;
			StringBuffer sb=new StringBuffer("");
			sb.append("<table style='border-collapse: collapse' align='center' border='1' width='85%' align='center' cellpadding='7' cellspacing='0'><tr><td align='center'>Sl.No</td><td align='center'>Office  Name </td><td align='center'>Freezed Date</td><td>Reason to Unfreeze</td><td align='center'>Unfreeze</td></tr>");
			String off_name,off_id;
			StringBuffer res=new StringBuffer("SELECT a.OFFICE_NAME,a.OFFICE_ID,to_char(b.UPDATED_DATE,'dd/MM/yyyy') as UPDATED_DATE  ,b.REMARKS FROM COM_MST_OFFICES a,PMS_DCB_OB_FREEZE b ");
						 res.append(" WHERE a.OFFICE_LEVEL_ID='DN' "+reg_id_qry+" AND a.OFFICE_ID IN ");
						 res.append(" (SELECT OFFICE_ID FROM PMS_DCB_DIV_DIST_MAP )  AND b.year ="+syear);
						 res.append(" AND a.office_id=b.office_id  and "); 
						 res.append(" a.OFFICE_ID in ( select OFFICE_ID from PMS_DCB_OB_FREEZE where UNFREEZE_REQUEST='Y' and  YEAR="+syear+" )");
	 		//out.println(res);
						 ResultSet rs=obj.getRS(res.toString());
			while(rs.next()) 
			{ 
				
				row++;	
				 off_name=rs.getString("OFFICE_NAME");
				off_id=rs.getString("OFFICE_ID");  
				String UPDATED_DATE=rs.getString("UPDATED_DATE");
				String REMARKS=rs.getString("REMARKS"); 
				sb.append(" <tr><td align='center'>"+row+"</td><td>"+off_name+"</td><td>"+UPDATED_DATE+"</td><td>"+REMARKS+"</td><td align='center'><input type=checkbox id='off"+row+"' name='off"+row+"' value='"+off_id+"'></td></tr>");
			} 
			sb.append("<tr><td colspan=5 align='center'><input type=Submit value='Submit'><input type=button onclick='javascript:window.close()' value='Exit'></td></td>"); 
			sb.append("</table>");
		
			%>
			<%=sb.toString() %> 
			<input type=hidden name="row" id='row' value="<%=row%>">
		</form>
</body>
</html>