<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
<title> Scheme Account Head Mapping  | TWAD Nest - Phase II</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/Bill_Demand.js"></script>
 <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
function rld()
{
   
  document.forms["sch"].submit();
    
}
function callReport(pro)
{
  var option=document.getElementById('pr_type').value;
  var fyear=document.getElementById('fyear').value;
  var fmonth=document.getElementById('fmonth').value;
  var ben_sel=document.getElementById('ben').value;
  window.open("../../../../../Bill_Demand?command=recpdf&fmonth="+fmonth+"&fyear="+fyear+"&ben_sel="+ben_sel+"&option="+option+"&pro="+pro);
}
</script>
</head>
<%
		int month = 0;//cal.get(Calendar.MONTH) + 1;
		int year = 0;//cal.get(Calendar.YEAR);
		String userid="0",Office_id="",Office_Name="",table_heading="",table_column="",table_header="";;
		Connection con;
		String smonth="",syear="",html="",SUB_LEDGER_CODE="";
		Controller obj=null;
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
		Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	//	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		if (Office_id.equals("")) Office_id="0";
		Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " ");		 
		
		int row_=Integer.parseInt(obj.setValue("row",request));
		
		if (row_==0) row_=1;
		String ass=obj.setValue("ass",request);
		String ord=ass;
		if (ord.equals("0")) ord="";
		if (ass.equals("0"))
			ass=" ORDER BY SCH_SNO ASC";
		else
			ass=" ORDER BY SCH_SNO "+ass;
		
		String qry=" SELECT smap.SCH_SNO, "+
		"  sch.SCH_NAME, "+
			  "   stype.SCH_TYPE_ID, "+
			  "   stype.SCH_TYPE_DESC, "+
			  "   smap.FIN_TRAN_ID, "+
			  "   smap.ACC_HD_DR, "+
			  "   smap.ACC_HD_CR "+
			  " FROM "+
			" (SELECT SCH_SNO, "+				 
			    "   FIN_TRAN_ID, "+
			    "  ACC_HD_DR, "+
			    "  ACC_HD_CR "+
			    "  FROM PMS_SCH_ACCOUNT_MAPPING   order by SCH_SNO "+ord+
			  "  )smap "+
			  " JOIN "+
			"  ( SELECT SCH_SNO, SCH_NAME, SCH_TYPE_ID, SCH_STATUS_ID FROM PMS_SCH_MASTER   "+ 
					  "   )sch "+
			  " ON sch.SCH_SNO=smap.SCH_SNO "+
			" JOIN "+
			"  ( SELECT SCH_TYPE_ID, SCH_TYPE_DESC FROM PMS_SCH_LKP_TYPE "+
					  "  )stype "+
			" ON stype.SCH_TYPE_ID=sch.SCH_TYPE_ID  limit "+row_+""+ass ;
 
		table_column="SCH_SNO,SCH_NAME,SCH_TYPE_ID,SCH_TYPE_DESC,ACC_HD_DR,ACC_HD_CR";
		table_header="Scheme Sno,Scheme,Type,Type Description,ACC-HD DR,ACC-HD-CR";
		String table_td_set="width='5%', "+
		 " width='40%', "+		 
		 " ,align=left width='5%',"+
		 " ,align=left ,"+
		 " ,align=center width='10%',"+
		 " align=center width='10%'";
		table_heading="  Scheme Account Head Mapping ";
		String tab="cellspacing='0' cellpadding='3' width='100%' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'";
		html=obj.genReport(qry,table_header,table_column,tab,table_heading,table_td_set);
		con.close();  
		}catch (Exception e) {out.println(e);} 
%>   
		<body onload="startclock(),select_ben('0','bentype','3'),select_ben('0','subdiv','5') ">
		<form name="sch" action="scheme_acc_map.jsp">
		<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
		<title><%=table_heading%></title>
		
		<Table   class="fb2" valign=top  id="" width="75%" align="center" border="1"   cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" class="fb2">
	 
		<tr  >
					<td colspan=2><center><%=table_heading%></center></td>
			 	</tr>
					 
		<tr><td align=left valign="top" width=10%  colspan="2"><font class="tdText"><label class=tb4 id="time" ></label></font> </td>
		
		</tr>
		<tR>
		
		<td colspan="4"><select id="row" name="row"  class="select" >
				<option value="0" selected> Select </option>
				<option value="5"> <= 5</option>
				<option value="10"><= 10</option>
				<option value="50"><= 50</option>
				<option value="100"><= 100</option>
				<option value="400"><= 400</option>
				<option value="1000"><=1000</option>
			</select>	
		 <select id="ass" name="ass" onchange="rld()"  class="select" >
				<option value="0" selected> Select </option>
				<option value="DESC">DESC</option>
				<option value="ASC">ASC</option>
				 
			</select>	
		</td>
		 
		</tR>
       			 <tr>
        <td>
        <div>
        <table width="100%">
    	<Tr><td colspan=2><%=html%></td></Tr>
    	</table>
    	</div>
	<!-- 	<tr><td colspan="2">
						<select id="pr_type" class="select">  
						<option value=1>&nbsp;&nbsp;&nbsp;Select Print Format&nbsp;&nbsp; </option>
						<option value=1>   PDF   </option>
						<option value=2>   EXCEL  </option>
						</select>
		</td></tr>    
		<tr>   
			<td><a href="sch_acc_map.jsp">New Mapping</a></td>
		</tr>  -->
		<tr><td colspan=2 align="center">  <input class="fb2" type="button" id="" value="Exit" onclick="javascript:window.close()" /></td></tr>
		</Table>
		<input type=hidden id="pr_status" name="pr_status" value="0">
		<input type=hidden id="ben" name="ben" value="<%=SUB_LEDGER_CODE%>">
		<input type="hidden" id="fyear" name="fyear" value="<%=syear%>" ></input> 
       	<input type="hidden" id="fmonth" name="fmonth" value='<%=smonth%>' ></input>
       	</form>  
</body>
</html>