<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*,java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DCB Data Freeze</title>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
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
	try
	{
	  con=obj.con();
	  obj.createStatement(con);
	  userid=(String)session.getAttribute("UserId");			
	  if(userid==null)
	  {
		 	response.sendRedirect(request.getContextPath()+"/index.jsp");
	  }
	}catch(Exception e) {}
	String pmonth=obj.setValue("month",request);
	String pyear=obj.setValue("year",request);
	String sub=obj.setValue("store",request);
	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
	Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " and OFFICE_LEVEL_ID='DN'");
	String acc_unit_id=obj.getValue("PMS_DCB_COM_OFFICE","ACCOUNTING_UNIT_ID"," where office_id="+Office_id);
	if (Office_id.equals("")) Office_id="0";
		Hashtable ht=new Hashtable();
		PreparedStatement proc_stmt=null;
  		proc_stmt = con.prepareCall("{call pms_dcb_head_office_trace (?,?) }");
		proc_stmt.setInt(1, Integer.parseInt(pmonth));	        					
		proc_stmt.setInt(2,Integer.parseInt(pyear) );	        		  			   // P Month         month_process       
		proc_stmt.execute(); 
%>
<form action="Head_Office_Trace.jsp" method="get">


<table  class="fnt"  align="center" border=1 width="50%"  bordercolor="#00FFFF"  >
	<tr> 
		<td colspan="2" align="center">  
			DCB Data Freeze
		</td>
		</tr> 
		<tr>
		<td>
			<%=Office_Name%> (<%= acc_unit_id%>)
		</td>
		</tr>
		<tr>
  		<td colspan="5"  align="left"> Month: <select name="month" id="month"   >
	         <option value="0" selected="selected" >Select</option>
	         	<%
	         	String[] amonth = { "-select month-", "January", "February", "March", "April",
				"May", "June", "July", "August", "September", "October", "November", "December" };	         	
	         	for (int i=1;i<=12;i++) {
	         	%>
	         	<option value="<%=i%>" <% if (Integer.parseInt(pmonth)==i) {%>selected<%} else { }%>><%=amonth[i]%></option>
	         	<%} %>
	          </select> Year:<select id="year" name="year"  tabindex="5">
                <option value="0" selected="selected">Select</option>
                <%
                for (int j=2010;j<=year;j++)
                {
                %>
                <option value="<%=j%>" <% if (Integer.parseInt(pyear)==j) {%>selected<%} else { }%>><%=j%></option>
                <%} %>  
    </select> </td>
    </tr>
    <tr>    
    	<td><input type="submit" id="" name="store" value="Submit" ><input type=button value="Exit" onclick="javascript:window.close()"></td>
    </tr>
    <Tr>
    <td>
    
	<%
		int smonth=0, syear=0,off_count=0;
		String TB_FREEZE="",DMD_BILL="",DATA_POST="",DIFF_DMD="",DIFF_COLL="";
		ResultSet rs=con.createStatement().executeQuery("select (TOTAL_OFFICE-TB_FREEZE) as TB_FREEZE,month,year,(TOTAL_OFFICE-DATA_POST) as DATA_POST ,(TOTAL_OFFICE-DIFF_DMD) as DIFF_DMD ,(TOTAL_OFFICE-DMD_BILL) as DMD_BILL ,DIFF_COLL ,TOTAL_OFFICE from PMS_DCB_REPORT_MONTH where (TOTAL_OFFICE is not null and total_office <>0) order by year,month");
		StringBuffer html=new StringBuffer();
		html.append("<table border='1' cellpadding='0' cellspacing='0'><tr><td align='center'>Month</td><td align='center'>Year</td><td align='center'>T.B Freezed</td><td align='center'>Demand Bill</td><td align='center'>Data Ledger Posting</td><td align='center'>Difference in Demand</td><td align='center'>Difference in Collection</td></tr>");
		while(rs.next())
		{
				
			smonth=rs.getInt("month");
			syear=rs.getInt("year");
			off_count=rs.getInt("TOTAL_OFFICE");
			TB_FREEZE=obj.isNull(rs.getString("TB_FREEZE"),1);
			DMD_BILL=obj.isNull(rs.getString("DMD_BILL"),1);
			DATA_POST=obj.isNull(rs.getString("DATA_POST"),1);
			DIFF_DMD=obj.isNull(rs.getString("DIFF_DMD"),1);
			DIFF_COLL=obj.isNull(rs.getString("DIFF_COLL"),1);
			html.append("<tr><td align='center'>"+amonth[smonth]+"</td><td align='center'>"+syear+"</td><td align='center'>"+TB_FREEZE+"</td><td align='center'>"+DMD_BILL+"</td><td align='center'>"+DATA_POST+"</td><td align='center'>"+DIFF_DMD+"</td><td align=center><a href='Head_Office_Trace_II.jsp?month="+pmonth+"&year="+pyear+"'>"+DIFF_COLL+"</a></td></tr>");
		}
		html.append("</table>");
		out.println(html.toString());
	%>
</td>
</Tr>
</table>
</form>
</body>
</html>