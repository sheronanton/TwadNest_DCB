<%@ page language="java" contentType="text/html; charset=ISO-8859-1"

    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="java.sql.Connection,java.sql.ResultSet,java.sql.PreparedStatement,java.sql.Statement"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
  <script type="text/javascript">
	function callReport()
	{
		 var fyear=document.getElementById('fyear').value;
		 var fmonth=document.getElementById('fmonth').value;
	     window.open("../../../../../Bill_Demand?command=prmispdf&fmonth="+fmonth+"&fyear="+fyear);
	}
  </script> 
  <script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
  <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
  </head>
<%
		String new_cond=Controller.new_cond;
		int total_meter_location=0,pumping_count=0;
 	 	String userid = "0", Office_id = "", Office_Name = "", table_heading = "", table_column = "", table_header = "";
 		String smonth = "0", syear = "0", html = "";
 		Controller obj = null;
 		String meter_status=Controller.meter_status;
 		String metstatus="";
	 	try 
	 	{
 		Connection con;
 		obj = new Controller();
 		con = obj.con();
 		obj.createStatement(con);
 		PreparedStatement ps = null;
 		String monthly_count = "", metre_count = "";
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
 		
 		  
 		  total_meter_location = obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE"," where "+meter_status+" BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+" office_id="+Office_id+" ) and   office_id="+Office_id);	
 		  pumping_count = obj.getCount("PMS_DCB_TRN_MONTHLY_PR"," where   BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+"  office_id="+Office_id+" ) and   office_id="+Office_id+" and month="+smonth+" and year="+syear);
 		//metre count in PMS_DCB_MST_BENEFICIARY_METRE table
 		 
	int meter_count=Integer.parseInt(obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE" ,"count(distinct BENEFICIARY_SNO) ","c", "where "+meter_status+" OFFICE_ID="+Office_id ));
	int ben_count=Integer.parseInt(obj.getValue("PMS_DCB_MST_BENEFICIARY" ,"count(distinct BENEFICIARY_SNO) ","c", "where "+new_cond+" OFFICE_ID="+Office_id ));
	if (meter_count==ben_count)
	{
		
	}
	else
	{
		metstatus="<font color='red'>Check Meter Missing Status </font>";
	}
 		String qry = "SELECT ben.BENEFICIARY_NAME, blk.BLOCK_NAME," +
			 		"  btype.BEN_TYPE_DESC, " +
			 		"  DECODE(met.RC,NULL,0,met.RC)                                                              AS count1, " +
			 		"  DECODE(met2.RC,NULL,0,met2.RC)                                                            AS count2 , " +
			 		"  DECODE(met.RC-DECODE(met2.RC,NULL,0,met2.RC),NULL,0,met.RC-DECODE(met2.RC,NULL,0,met2.RC))AS missing , " +
			 		"  DECODE(pr.val,NULL,0,pr.val)                                                              AS val , " +
			 		"  DECODE(met_sett.set_count,NULL,0,met_sett.set_count)                                      AS setflag, " +
			 		"  DECODE(rate_sett. rate_count,NULL,0,rate_sett. rate_count)                                AS ratecount, " +
			 		"  DECODE(vpr.not_validate,NULL,0,vpr.not_validate)                                          AS not_validate, " +
			 		"  DECODE(prf.fr_val,NULL,0,prf.fr_val)                                                              AS fr_val  " +
			 		
			 		"FROM ( " +
			 		"  (SELECT BENEFICIARY_NAME, " +
			 		"    BENEFICIARY_TYPE_ID, " +
			 		"    OFFICE_ID , " +
			 		"    BENEFICIARY_SNO,BLOCK_SNO " +
			 		"  FROM PMS_DCB_MST_BENEFICIARY " +
			 		"  WHERE "+new_cond+" OFFICE_ID=  " +Office_id+
			 		"  ORDER BY BENEFICIARY_TYPE_ID " +
			 		"  )ben " +
			 		"JOIN " +
			 		"  ( SELECT BEN_TYPE_ID, BEN_TYPE_DESC FROM PMS_DCB_BEN_TYPE " +
			 		"  )btype " +
			 		"ON btype.BEN_TYPE_ID=ben.BENEFICIARY_TYPE_ID " +
			 		"LEFT JOIN " +
			 		"  (SELECT COUNT(*) AS RC, " +
			 		"    BENEFICIARY_SNO,Office_id " +
			 		"  FROM PMS_DCB_MST_BENEFICIARY_METRE " +
			 		"  WHERE "+meter_status+" OFFICE_ID= Office_id " +
			 		"  GROUP BY BENEFICIARY_SNO,Office_id " +
			 		"  )met " +
			 		"ON met.BENEFICIARY_SNO=ben.BENEFICIARY_SNO and met.Office_id=ben.OFFICE_ID " +
			 		"LEFT JOIN " +
			 		"  (SELECT COUNT(*) AS RC, " +
			 		"    BENEFICIARY_SNO , " +
			 		"    OFFICE_ID " +
			 		"  FROM PMS_DCB_MST_BENEFICIARY_METRE " +
			 		"  WHERE "+meter_status+" METRE_SNO IN " +
			 		"    (SELECT METRE_SNO " +
			 		"    FROM PMS_DCB_TRN_MONTHLY_PR " +
			 		"    WHERE OFFICE_ID=  " +Office_id +
			 		"    AND MONTH      = "+smonth +
			 		"    AND YEAR       =  " + syear +
			 		"    ) " +
			 		"  GROUP BY BENEFICIARY_SNO, " +
			 		"    OFFICE_ID " +
			 		"  )met2 " +
			 		"ON met2.BENEFICIARY_SNO=ben.BENEFICIARY_SNO " +
			 		"AND met2.OFFICE_ID     =ben.OFFICE_ID " +
			 		"LEFT JOIN " +
			 		"  (SELECT BENEFICIARY_SNO, " +
			 		"    COUNT(*) AS val " +
			 		"  FROM PMS_DCB_TRN_MONTHLY_PR " +
			 		"  WHERE OFFICE_ID =  " +Office_id +
			 		"  AND MONTH       =  " +smonth +
			 		"  AND YEAR        =  " +syear +
			 		"  AND PROCESS_FLAG='V' " +
			 		"  GROUP BY BENEFICIARY_SNO " +
			 		"  ) pr " +
			 		"ON pr.BENEFICIARY_SNO=met2.BENEFICIARY_SNO " +
			 		"LEFT JOIN " +
			 		"  (SELECT BENEFICIARY_SNO, " +
			 		"    COUNT(*) AS fr_val " +
			 		"  FROM PMS_DCB_TRN_MONTHLY_PR " +
			 		"  WHERE OFFICE_ID =  " +Office_id +
			 		"  AND MONTH       =  " +smonth +
			 		"  AND YEAR        =  " +syear +
			 		"  AND PROCESS_FLAG='FR' " +
			 		"  GROUP BY BENEFICIARY_SNO " +
			 		"  ) prf " +
			 		"ON prf.BENEFICIARY_SNO=met2.BENEFICIARY_SNO " +
			 		"LEFT JOIN " +
			 		"  (SELECT BENEFICIARY_SNO, " +
			 		"    COUNT(*) AS not_validate " +
			 		"  FROM PMS_DCB_TRN_MONTHLY_PR " +
			 		"  WHERE OFFICE_ID   =  " +Office_id +
			 		"  AND MONTH         =  " +smonth +
			 		"  AND YEAR          =  " + syear +
			 		"  AND PROCESS_FLAG <>'V' " +
			 		"  AND PROCESS_FLAG  ='CR' " +
			 		"  GROUP BY BENEFICIARY_SNO " +
			 		"  ) vpr " +
			 		"ON vpr.BENEFICIARY_SNO=met2.BENEFICIARY_SNO " +
			 		"LEFT JOIN " +
			 		"  (SELECT COUNT(*) AS set_count, " +
			 		"    BENEFICIARY_SNO " +
			 		"  FROM PMS_DCB_MST_BENEFICIARY_METRE " +
			 		"  WHERE "+meter_status+" setting_flag IS NULL " +
			 		"  GROUP BY BENEFICIARY_SNO " +
			 		"  )met_sett " +
			 		"ON met_sett.BENEFICIARY_SNO=met.BENEFICIARY_SNO " +
			 		"LEFT JOIN " +
			 		"  (SELECT COUNT(*) AS rate_count, " +
			 		"    BENEFICIARY_SNO " +
			 		"  FROM PMS_DCB_MST_BENEFICIARY_METRE " +
			 		"  WHERE "+meter_status+" tariff_rate IS NULL " +
			 		"  GROUP BY BENEFICIARY_SNO " +
			 		"  )rate_sett " +
			 		"ON rate_sett.BENEFICIARY_SNO=met.BENEFICIARY_SNO"+
			 		" left join ( select BLOCK_NAME,BLOCK_SNO from COM_MST_BLOCKS )blk on blk.BLOCK_SNO=ben.BLOCK_SNO ) " +
			 		"ORDER BY BEN_TYPE_DESC, " +
			 		"  BENEFICIARY_NAME ";

 		table_column = "BENEFICIARY_NAME,BEN_TYPE_DESC,BLOCK_NAME,count1,setflag,ratecount,count2,missing,val,not_validate,fr_val";
 		table_header = "Beneficiary,Beneficiary Type, Block Name,Total<br> Locations ,Tariff Not Set,Rate Not Entered,Pumping <Br> Return <br Entered,Pumping <Br> Return <br> Omission,Pumping <Br> Return  Validated,Pumping <Br> Return Not Validated,Pumping <Br> Return Freeze";
 		String table_td_set = ",,,align=center width='5%',align=center class='fnt' width='5%',align=center width='5%',align=center width='5%' ,align=center width='5%' ,align=center width='5%' ,align=center width='5%'  ,align=center width='5%'";
 		table_heading = "Beneficiary Pumping Return  Status - "+ Office_Name + "";
 		String tab = "cellspacing='0' cellpadding='3' width='100%' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'";
 		html = obj.genReport(qry, table_header, table_column, tab,table_heading, table_td_set);

 		con.close(); 
 		

 	} catch (Exception e) {
 		out.println(e);
 		//x	response.sendRedirect(request.getContextPath()+"/index.jsp");

 	}
 	
 %> 
		<body onload="blink()"> 
		<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
		<title><%=table_heading%>  | TWAD Nest - Phase II </title>
		<Table      id="" width=75% align="center" border="1"   cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
				<tr>
					<td colspan=2><center><%=table_heading%></center></td>
				</tr> 
				
				<tr>
					<td class="tdText">Month&nbsp;&nbsp;:&nbsp;&nbsp;<%=obj.month_val(smonth)%></td>
					<td class="tdText" align="right">Year&nbsp;&nbsp;:&nbsp;&nbsp;<%=syear%></td>
				</tr>
				<tr>
			 		<td><label class="lbl">Total Meter Location : <font color='green'> &nbsp;&nbsp;&nbsp;  <%=total_meter_location%></font>&nbsp;&nbsp;&nbsp;and&nbsp;&nbsp;&nbsp; Monthly PR Count:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color='blue'> <%=pumping_count%></font> &nbsp;&nbsp;</label></td><td     align="right"> <a href="metre_missing.jsp"> <label class="lbl" id='mst'> <b><%=metstatus %></b></label> </a></td> 
			 	</tr>
				<Tr>
					<td colspan=2><%=html%></td>  
				</Tr>
				<tr>
					<td colspan=2 align="center"><input class="fb2" type="button" id="" value="Print" style="font-style: italic;font-size:7;color:green;font-weight: bold"  onclick="callReport()"/> <input class="fb2" style="font-style: italic;font-size:7;color:red;font-weight: bold"  type="button" id="" value="Exit" onclick="javascript:window.close()" /></td>
				</tr> 
			 	 
		</Table>
		 <input type="hidden" id="fyear" name="fyear" value="<%=syear%>" ></input> 
       	  <input type="hidden" id="fmonth" name="fmonth" value='<%=smonth%>' ></input> 
</body>
</html>