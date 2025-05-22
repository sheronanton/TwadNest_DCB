<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="java.sql.Connection,java.sql.ResultSet,java.sql.PreparedStatement,java.sql.Statement"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <script type="text/javascript">
    
  function callReport()
{
	 
	 
	 var fyear=document.getElementById('fyear').value;
	 var fmonth=document.getElementById('fmonth').value;
	 
     window.open("../../../../../Bill_Demand?command=prmispdf&fmonth="+fmonth+"&fyear="+fyear);
}
  
  </script> 
</head>

 
<%
		int total_meter_location=0,pumping_count=0;
 	 	String userid = "0", Office_id = "", Office_Name = "", table_heading = "", table_column = "", table_header = "";
 		String smonth = "0", syear = "0",color_="0", html = "";
 		Controller obj = null;

 	try {
 	
 		Connection con;
 		obj = new Controller();
 		con = obj.con();
 		obj.createStatement(con);
 		PreparedStatement ps = null;

 		//metre count in PMS_DCB_TRN_MONTHLY_PR table
 		String monthly_count = "", metre_count = "";
 		
 		//con = Obj.con();
 		

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
 		if (Office_id.equals(""))
 			Office_id = "0";
 		Office_Name = obj.getValue("com_mst_all_offices_view",
 				"OFFICE_NAME", "where OFFICE_ID=" + Office_id + " 	");
 		smonth = obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH",
 				" where OFFICE_ID=" + Office_id), 1);
 		syear = obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR",
 				"where OFFICE_ID=" + Office_id), 1);
 		
 		
 		  total_meter_location = obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE"," where    office_id="+Office_id);	
 		  pumping_count = obj.getCount("PMS_DCB_TRN_MONTHLY_PR"," where    office_id="+Office_id+" and month="+smonth+" and year="+syear);
 		//metre count in PMS_DCB_MST_BENEFICIARY_METRE table
 		  color_="";
		  color_=(total_meter_location==pumping_count) ? "green":"red";
	
 		
 		
 		
 		String qry = "select "
 				+ "ben.BENEFICIARY_NAME,"
 				+ " btype.BEN_TYPE_DESC, DECODE(met.RC,NULL,0,met.RC) AS count1,"
 				+ " DECODE(met2.RC,NULL,0,met2.RC) AS count2 ,"
 				+ " DECODE(met.RC-DECODE(met2.RC,NULL,0,met2.RC),NULL,0,met.RC-DECODE(met2.RC,NULL,0,met2.RC))AS missing ,"
 				+ " DECODE(pr.val,NULL,0,pr.val) AS val ,"
 				+ " DECODE(met_sett.set_count,NULL,0,met_sett.set_count)            AS setflag,"
 				+ " DECODE(rate_sett. rate_count,NULL,0,rate_sett. rate_count) AS ratecount "
 				+ " from " + "( " + "( " + "select "
 				+ "  BENEFICIARY_NAME, " + "   BENEFICIARY_TYPE_ID, "
 				+ "    OFFICE_ID ,BENEFICIARY_SNO" + " from  "
 				+ "     PMS_DCB_MST_BENEFICIARY "
 				+ " where   OFFICE_ID="
 				+ Office_id
 				+ " order by BENEFICIARY_TYPE_ID)ben "
 				+ "join "
 				+ "( "
 				+ " select "
 				+ " BEN_TYPE_ID,"
 				+ " BEN_TYPE_DESC "
 				+ " from "
 				+ " PMS_DCB_BEN_TYPE"
 				+ ")btype"
 				+ " on btype.BEN_TYPE_ID=ben.BENEFICIARY_TYPE_ID "
 				+ " left join "
 				+ "   ( select "
 				+ "     count(*) as RC, "
 				+ "     BENEFICIARY_SNO  "
 				+ "  from  "
 				+ "    PMS_DCB_MST_BENEFICIARY_METRE  where   OFFICE_ID= "
 				+ Office_id
 				+ "  GROUP by BENEFICIARY_SNO "
 				+ "  )met "
 				+ "  on met.BENEFICIARY_SNO=ben.BENEFICIARY_SNO "
 				+ " left join "
 				+ "   ( select  "
 				+ "   count(*) as RC, "
 				+ "      BENEFICIARY_SNO ,OFFICE_ID "
 				+ " from  "
 				+ "    PMS_DCB_MST_BENEFICIARY_METRE "
 				+ " where  "
 				+ "  METRE_SNO in ( "
 				+ "  SELECT METRE_SNO "
 				+ "  FROM PMS_DCB_TRN_MONTHLY_PR "
 				+ "  WHERE OFFICE_ID= "
 				+ Office_id
 				+ "  AND MONTH      = "
 				+ smonth
 				+ "  AND YEAR       = "
 				+ syear
 				+ "  ) "
 				+ " GROUP by BENEFICIARY_SNO,OFFICE_ID "
 				+ " )met2 "
 				+ " on met2.BENEFICIARY_SNO=ben.BENEFICIARY_SNO and  met2.OFFICE_ID=ben.OFFICE_ID  "
 				+ " left join "
 				+ " ( "
 				+ " SELECT "
 				+ "     BENEFICIARY_SNO, "
 				+ "      count(*) as val "
 				+ "     FROM PMS_DCB_TRN_MONTHLY_PR "
 				+ "      WHERE OFFICE_ID= "
 				+ Office_id
 				+ "      AND MONTH      = "
 				+ smonth
 				+ "     AND YEAR       = "
 				+ syear
 				+ "      AND PROCESS_FLAG='V' "
 				+ "  GROUP BY BENEFICIARY_SNO "
 				+ "  ) pr  "
 				+ "  on pr.BENEFICIARY_SNO=met2.BENEFICIARY_SNO "
 				+ " LEFT JOIN  "
 				+ " ( "
 				+ " SELECT COUNT(*) AS set_count, "
 				+ " BENEFICIARY_SNO "
 				+ " FROM PMS_DCB_MST_BENEFICIARY_METRE where setting_flag is null "
 				+ " GROUP BY BENEFICIARY_SNO  "
 				+ " )met_sett "
 				+ " ON met_sett.BENEFICIARY_SNO=met.BENEFICIARY_SNO"
 				+ " LEFT JOIN "
 				+ " "
 				+ " ( "
 				+ " SELECT COUNT(*) AS rate_count, "
 				+ " BENEFICIARY_SNO "
 				+ " FROM PMS_DCB_MST_BENEFICIARY_METRE where tariff_rate is null "
 				+ " GROUP BY BENEFICIARY_SNO  "
 				+ " )rate_sett "
 				+ " ON rate_sett.BENEFICIARY_SNO=met.BENEFICIARY_SNO  "
 				+ " ) order by BEN_TYPE_DESC,BENEFICIARY_NAME ";

 		table_column = "BENEFICIARY_NAME,BEN_TYPE_DESC,count1,setflag,ratecount,count2,missing";
 		table_header = "Beneficiary,Beneficiary Type, Total<br> Locations ,Tariff Not Set,Rate Not Entered,PR<br>Entered,PR Omission";
 		String table_td_set = ",,align=center width='5%',align=center width='5%',align=center width='5%',align=center width='5%' ,align=center width='5%'";
 		table_heading = "Beneficiary Pumping Return  Status - "+ Office_Name + "";
 		String tab = "cellspacing='0' cellpadding='3' width='100%' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'";
 		html = obj.genReport(qry, table_header, table_column, tab,table_heading, table_td_set);

 		con.close();
 		
 	if(monthly_count==metre_count)
		{
		}
 		else
 		{
 			%>
 			<script type="text/javascript">
 				alert("All Pumping Return Entered for this Month.");
 			</script>
 			
 			<%
 		}
 	} catch (Exception e) {
 		out.println(e);
 		//x	response.sendRedirect(request.getContextPath()+"/index.jsp");

 	}
 	
 %> 
		<body> 
		<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
		<title><%=table_heading%>  | TWAD Nest - Phase II </title>
		<Table class="fb2"    id="" width=75% align="center" border="1"   cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
				<tr>
					<td colspan=2><center><%=table_heading%></center></td>
				</tr> 
				
				<tr>
					<td class="tdText"><label class="lbl"> Month</label>&nbsp;&nbsp;:&nbsp;&nbsp;<font color='green'><%=obj.month_val(smonth)%></font></td>
					<td class="tdText" align="right"><label class="lbl">Year</label>&nbsp;&nbsp;:&nbsp;&nbsp;<font color='green'><%=syear%></font></td>
				</tr>
				<tr> 
			 		<td><label class="lbl">Total Meter Location &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;<font color='green'><%=total_meter_location%></font> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Total PR Entered &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;<font color="<%=color_%>"><%=pumping_count%></font> </label></td><td     align="right"> <a href="metre_missing.jsp"><label class="lbl">Meter Missing Status</label></a></td> 
			 	</tr>
				<Tr>
					<td colspan=2><%=html%></td>   
				</Tr>
				<tr>
					<td colspan=2 align="center"><input class="fb2" type="button" id="" value="Print" style="font-style: italic;font-size:7;color:green;font-weight: bold"  onclick="callReport()"/> <input class="fb2" style="font-style: italic;font-size:7;color:red;font-weight: bold"  type="button" id="" value="Exit" onclick="javascript:window.close()" /></td>
				</tr> 
			 	<tr>
			 		<td colspan="2" align="right"> <a href="metre_missing.jsp"> <label class="lbl">Meter Missing Status</label> </a></td> 
			 	</tr>
		</Table>
		 <input type="hidden" id="fyear" name="fyear" value="<%=syear%>" ></input> 
       	  <input type="hidden" id="fmonth" name="fmonth" value='<%=smonth%>' ></input> 
</body>

 
</html>