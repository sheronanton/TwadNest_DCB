<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>	
<%
/*  
 * Created on : dd-mm-yy 
 * Author     : Panneer Selvam.K
 * Last Date  : 
 *----------------------------------------------------------------------------- 
 * Revision History (Release 1.0.0.0) 
 *-----------------------------------------------------------------------------
 * Date			Description
 * 27/02/2013	New   
 * 
 *---------|---------------|--------------------------------------------------- 
 */

%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Scheme Performance Month Wise - Status</title>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/> 
</head>
<body>
<%
	String userid="",Office_id="",Office_Name="",smonth="",syear="",table_column="";
	String table_header="",table_heading="",html="";
	Controller obj=null;
	Connection con;
	String cond="",addcolumn="",addhead="",addcolumn1="",addset,head="";
try {
  	
	  obj=new Controller();  
	con = obj.con();
	obj.createStatement(con);
	try {
		userid = (String) session.getAttribute("UserId");
	} catch (Exception e) {
		userid = "0";
	}
	if (userid == null) {
		response.sendRedirect(request.getContextPath()+ "/index.jsp");
	}
	Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID",
			"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
					+ userid + "')");
	
	
	String month=obj.setValue("month",request);
	String year=obj.setValue("year",request);
	
	String fin_year="";
	if (Integer.parseInt(month)>4)
	{
		fin_year=year+"-"+(Integer.parseInt(year)+1);
		
	}else
	{
		fin_year=(Integer.parseInt(year)-1)+"-"+year;		
	} 
	
	String mvalue=obj.month_val(month);
	if (!Office_id.trim().equalsIgnoreCase("0")) 
		Office_id=Office_id;  
		addcolumn =" sname ,  cs2, cs4,cs   ";  
		head="Scheme Performance "; 
		addhead="  Scheme Name,  Budget Estimate "+fin_year+",Annual Maintenance Estimate <br> "+fin_year+",Scheme Performance Entry Status for the month  " +mvalue+"-"+year; 
		addset="  'align=left width='25%',  'align=center  width='15%','align=center  width='15%','align=center  width='15%'' ";
		Office_Name = obj.getValue("com_mst_all_offices_view","OFFICE_NAME", "where OFFICE_ID=" + Office_id + " 	");
		String qry="SELECT sch.sch_sno, " +
		"  sch.sname, " +
		"  mth.sch_sno, " +
		"  CASE " +
		"    WHEN SCH_DET.c IS NULL " +
		"    THEN ' - ' " +
		"    ELSE ' Completed' " +
		"  END AS cs1 , " +
		"  CASE " +
		"    WHEN BUD_EST.c IS NULL " +
		"    THEN ' - ' " +
		"    ELSE ' Completed' " +
		"  END AS cs2, " +		 
		"  CASE " +
		"    WHEN AME_ENT.c IS NULL " +
		"    THEN ' - ' " +
		"    ELSE ' Completed' " +
		"  END AS cs4, " +
		"  CASE " +
		"    WHEN mth.c IS NULL " +
		"    THEN ' - ' " +
		"    ELSE ' Completed' " +
		"  END AS cs , CASE  WHEN dpr.c IS NULL THEN ' - ' ELSE ' Completed'  END AS cs5 " +
		"FROM " +
		"  (SELECT sch_sno , " +
		"    sch_name AS sname " +
		"  FROM pms_sch_master " +
		"  WHERE  sch_sno   IN " +
		"    ( SELECT sch_sno FROM PMS_DCB_DIV_SCHEME_MAP WHERE office_id="+Office_id+ 
		"    ) and sch_sno in (select distinct SCHEME_SNO from  PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+" )   " +
		"  ) sch " +
		"LEFT outer JOIN " +
		"  ( SELECT DISTINCT sch_sno AS sch_sno, " +
		"    COUNT(*)                AS c " +
		"  FROM PMS_AME_TRN_SCH_PERFORM_MTH " +
		"  WHERE office_id  ="+Office_id+
		"  AND MONTH        ="+month+
		"  AND YEAR         ="+year+
		"  HAVING COUNT (*) > 0 " +
		"  GROUP BY sch_sno, " +
		"    MONTH, " +
		"    YEAR " +
		"  ) mth " +
		"ON mth.sch_sno=sch.sch_sno " +
		"LEFT outer JOIN " +
		"  ( SELECT DISTINCT sch_sno AS sch_sno, " +
		"    COUNT(*)                AS c " +
		"  FROM PMS_AME_MST_SCH_DETAILS " +
		"  WHERE office_id ="+Office_id+
		"  HAVING COUNT (*)>0 " +
		"  GROUP BY sch_sno " +
		"  ) SCH_DET " +
		"ON SCH_DET.sch_sno=sch.sch_sno " +
		"  LEFT OUTER JOIN " +
		"  ( SELECT DISTINCT sch_sno AS sch_sno, " +
		"    COUNT(*)                AS c " +
		"  FROM PMS_AME_TRN_BUDGET " +
		"  WHERE office_id  ="+Office_id+
		"  AND FIN_YEAR     ='"+fin_year+"' " +
		"  HAVING COUNT (*) > 0 " +
		"  GROUP BY sch_sno " +
		"  ) BUD_EST " +
		"ON BUD_EST.sch_sno=sch.sch_sno " +	
		"left outer JOIN " +
		"  ( SELECT DISTINCT sch_sno AS sch_sno, " +
		"    COUNT(*)                AS c " +
		"  FROM PMS_AME_TRN_ABSTRACT " +
		"  WHERE office_id  ="+Office_id+
		"  AND FIN_YEAR     ='"+fin_year+"' " +
		//"  HAVING COUNT (*) > 0 " +
		"  GROUP BY sch_sno " +
		"  ) AME_ENT " +
		" ON AME_ENT.sch_sno=sch.sch_sno left join ( "+
  		" SELECT DISTINCT sch_sno AS sch_sno,COUNT(*) AS c "+ 
  		" FROM PMS_AME_TRN_SCH_DPQTY  WHERE office_id  ="+Office_id+"  AND "+  
  		" MONTH="+month+" and year="+year+"   HAVING COUNT (*) > 0  GROUP BY sch_sno ) dpr "+
  		" ON dpr.sch_sno=sch.sch_sno " +
		"ORDER BY sch.sch_sno asc ";	
	table_column = addcolumn;	   	     
	table_header =addhead;
	String table_td_set =addset;
	table_heading = "   "+Office_Name+" <br> "+ head + "   "+mvalue+","+year;  
	String tab = "cellspacing='0' cellpadding='3' width='100%' ";
	html = obj.genReport3(qry, table_header, table_column, tab,table_heading, table_td_set);
	con.close();
	 
}catch(Exception e) 
{
out.println(e);	
}  
 


%>

<table align="center" width="75%" border=1>   
	<tr>	
		<td align="center"><%=table_heading %></td>
	</tr>
	
	<tr>
		<td> <%=html %></td>
	</tr>
	<tr>	
		<td align="center"><input  type=button value="Exit" onclick="window.close()" > </td>
	</tr>
</table>
</body>
</html>