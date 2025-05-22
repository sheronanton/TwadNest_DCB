<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%> 
<%@ page import="Servlets.Security.classes.UserProfile"%>
<%@ page import="java.sql.Connection,java.sql.ResultSet,java.sql.PreparedStatement,java.sql.Statement;"%>
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
</head>
<%
		String headhtml="",countheadhtml="";
		int total_meter_location=0,pumping_count=0;
 	 	String userid = "0", Office_id = "", Office_Name = "", table_heading = "", table_column = "", table_header = "";
 		String smonth = "0", syear = "0", html = "";
 		Controller obj = null;
 		String metstatus="";
 		int metc=0,
 			metsetf=0,ratecount,count2,missing,val,not_validate,fr_val;
 		
 		String new_cond=Controller.new_cond;
 		String meter_status=Controller.meter_status;
		String meter_status2=Controller.meter_status2;
 	try {
 	
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
 		//Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID",	"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='" 	 		+ userid + "')");
 		Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

 		if (Office_id.equals("")) 	Office_id = "0";
 		Office_Name = obj.getValue("com_mst_offices","OFFICE_NAME", "where OFFICE_ID=" + Office_id + " 	");
 		
 		String arg_month=obj.setValue("arg_month",request);
 		String arg_year=obj.setValue("arg_year",request);
 		
 		if (arg_month.equalsIgnoreCase("0"))
 		{
 			smonth = obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID=" + Office_id), 1);
 			syear = obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID=" + Office_id), 1);
 		}
 		else
 		{
 			smonth=arg_month;
 			syear=arg_year;
 		}
 		
 		
 		total_meter_location = obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE"," where "+meter_status+" BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+"  office_id="+Office_id+" ) and   office_id="+Office_id);	
 		pumping_count = obj.getCount("PMS_DCB_TRN_MONTHLY_PR"," where   BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+" office_id="+Office_id+" ) and   office_id="+Office_id+" and month="+smonth+" and year="+syear);
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
			 		"  btype.BEN_TYPE_DESC, btype.BEN_TYPE_ID," +
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
			 		"  FROM PMS_DCB_MST_BENEFICIARY_METRE  " +
			 		"  WHERE "+meter_status+" setting_flag IS NULL and BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+"  office_id="+Office_id+" ) " +
			 		"  GROUP BY BENEFICIARY_SNO " +
			 		"  )met_sett " +
			 		"ON met_sett.BENEFICIARY_SNO=met.BENEFICIARY_SNO " +
			 		"LEFT JOIN " +
			 		"  (SELECT COUNT(*) AS rate_count, " +
			 		"    BENEFICIARY_SNO " +
			 		"  FROM PMS_DCB_MST_BENEFICIARY_METRE " +
			 		"  WHERE "+meter_status+" tariff_rate IS NULL  and BENEFICIARY_SNO not in ( select BENEFICIARY_SNO from PMS_DCB_TARIFF_SLAB where office_id="+Office_id+")" +
			 		"  GROUP BY BENEFICIARY_SNO " +
			 		"  )rate_sett " +
			 		"ON rate_sett.BENEFICIARY_SNO=met.BENEFICIARY_SNO"+
			 		" left join ( select BLOCK_NAME,BLOCK_SNO from COM_MST_BLOCKS )blk on blk.BLOCK_SNO=ben.BLOCK_SNO ) " +
			 		"ORDER BY BEN_TYPE_ID,BEN_TYPE_DESC, " +
			 		"  BENEFICIARY_NAME ";

 		String ben_cond="and BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+"  office_id="+Office_id+" ) ";
 		String met_cond="and METRE_SNO    IN   (SELECT METRE_SNO FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE METER_STATUS='L'  and office_id="+Office_id+") ";
 		
 		
 		int total_ben=obj.getCount("PMS_DCB_MST_BENEFICIARY ", "where "+new_cond+" office_id="+Office_id);
 		int total_trnot_set=obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE ", "where METER_STATUS='L' "+ben_cond+" AND setting_flag IS NULL  and  office_id="+Office_id);
 		int total_ratenot_set=obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE ", "where METER_STATUS='L'  "+ben_cond+" AND tariff_rate IS NULL  and  office_id="+Office_id+" and BENEFICIARY_SNO not in ( select BENEFICIARY_SNO from PMS_DCB_TARIFF_SLAB where office_id="+Office_id+")");
 		int total_pr_=obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE ", "where METER_STATUS='L'  "+ben_cond+" AND METRE_SNO    IN   (SELECT METRE_SNO FROM PMS_DCB_TRN_MONTHLY_PR  WHERE MONTH ="+smonth+" AND YEAR="+syear+" and office_id="+Office_id+")");
 		
 		  
 		
 		
 		int total_meter= obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE ", "where METER_STATUS='L' "+ben_cond+" AND office_id="+Office_id);
 		int total_pr_missing=total_meter-total_pr_;
 		
 		int total_val=obj.getCount("PMS_DCB_TRN_MONTHLY_PR ", "where MONTH ="+smonth+" AND YEAR="+syear+" and  office_id="+Office_id+"  AND PROCESS_FLAG='V'   "+ben_cond+"" + met_cond);
 		int total_not_val=obj.getCount("PMS_DCB_TRN_MONTHLY_PR ", "where MONTH ="+smonth+" AND YEAR="+syear+" and  office_id="+Office_id+"  AND PROCESS_FLAG <> 'V' AND PROCESS_FLAG  ='CR'	"+ben_cond+"" + met_cond );
 		int total_val_fr=obj.getCount("PMS_DCB_TRN_MONTHLY_PR ", "where MONTH ="+smonth+" AND YEAR="+syear+" and  office_id="+Office_id+"  AND PROCESS_FLAG='FR'"+ben_cond+"" + met_cond); 			
 			
 		String countrow="&nbsp;,&nbsp;,&nbsp;,"+total_meter+","+total_trnot_set+","+total_ratenot_set+","+total_pr_+","+total_pr_missing+","+total_val+","+total_not_val+","+total_val_fr;
 		
 		table_column = "BENEFICIARY_NAME,BEN_TYPE_DESC,BLOCK_NAME,count1,setflag,ratecount,count2,missing,val,not_validate,fr_val";
 		table_header = "Beneficiary,Beneficiary Type, Block Name,Total<br> Locations ,Tariff Not Set,Rate Not Entered,Pumping <Br> Return <br Entered,Pumping <Br> Return <br> Omission,Pumping <Br> Return  Validated,Pumping <Br> Return Not Validated,Pumping <Br> Return Freeze";
 		String table_td_set = "align=left width='27%'  ,align=left width='12' ,align=left width='12%',align=center width='5%',align=center class='fnt' width='5%',align=center width='6%',align=center width='5%' ,align=center width='6%' ,align=center width='6%' ,align=center width='5%'  ,align=center width='5%'";
 		String table_td_set1 = ", align=left width='13%' ,align=left width='11%' ,align=center width='5%',align=center class='fnt' width='5%',align=center width='5%',align=center width='6%' ,align=center width='6%' ,align=center width='5%' ,align=center width='6%'  ,align=center width='6%'";
 	
 		
 		table_heading = "Pumping Return Process Status - "+ Office_Name + "";
 		String tab = "cellspacing='0' cellpadding='3' width='100%' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'";
 		html = obj.genReport2(qry, table_column, tab,table_heading, table_td_set);
 
 		int totben=obj.getCount("PMS_DCB_MST_BENEFICIARY ", "where "+new_cond+" office_id="+Office_id);  
 		
 		  
 		    headhtml ="<Table id='' width='100%' align='center' border='1' cellspacing='0' cellpadding='3' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'><tr bgcolor='#408080'>";
 		    headhtml += "<td class='tdText' width='5%' align='center' ><font   size=2 color='white'><b>Sl.no </b></td>";
			String[] table_header_f = table_header.split(",");
			
			
			String[] table_td_set_f = table_td_set1.split(",");
			
 		    for (int i = 0; i < table_header_f.length; i++) {

			headhtml += " <td class='tdText' align=center "+table_td_set_f[i]+" ><font   size=2  color='white'><b>" + table_header_f[i].trim() + "</b></font></td>";
			 
			}    
 		   headhtml +="</tr></table>";
 		   
 		  
 		   
 		  String[] countrow_f = countrow.split(",");
 		  countheadhtml="<Table id='' width='100%' align='center' border='1' cellspacing='0' cellpadding='3' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'><tr>";
 		  countheadhtml+="<td class='tdText' colspan='5' width=50% align='center'>&nbsp;</td";
 		  
 		  for (int i = 2; i < countrow_f.length; i++) 
 		  {
 			 countheadhtml += " <td class='tdText' align=center width='5%' ><font size=2><b>"+countrow_f[i].trim()+"</b></font></td>";
 		  }
 		 
 		   
 		   
 		con.close();  
 		

 	} catch (Exception e) {
 		out.println(e);
 		//x	response.sendRedirect(request.getContextPath()+"/index.jsp");

 	}
 	
 %> 
		<body onload="blink()"> 
		<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
		<title><%=table_heading%>  | TWAD Nest - Phase II </title>
		
		<Table id="" width=80% align="center" border="1"   cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
						
		
		
				<tr>
					<td colspan=2><center><%=table_heading%></center></td>
				</tr> 
				
				<tr>
					<td width="40%"><font size=2><b>Month and Year  :</b></font> &nbsp;&nbsp; <font color='blue' size=2><b><%=obj.month_val(smonth)%> and <%=syear%></b></font></td>
				 
			 		<td>
			 			<label class="lbl">Total Meter Location : <font color='green'> &nbsp; <%=total_meter_location%></font>
			 		&nbsp;&nbsp;&nbsp;and&nbsp;&nbsp;&nbsp; Monthly Pumping Return Count:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color='blue'> <%=pumping_count%></font> &nbsp;&nbsp;</label> 
			 		 <a href="metre_missing.jsp"> <label class="lbl" id='mst'> <b><%=metstatus %></b></label> </a>
			 		</td> 
			 	</tr>
			 	<tr>
			 	<td colspan=2>
			 		<%=headhtml%>
			 		</td>
			 	</tr>
			 	
				<Tr>
					<td colspan=2>
					
					<div id='scroll_clipper' style='position:relative; width:100%; border-height:1px; height: 450px; overflow:auto;white-space:nowrap;'>
					<div id='scroll_text'  >
					
					<%=html%>
					</div>
					</div>
					</td>  
				</Tr>
				
			 	</table>
			 	<table id="" width=80% align="center" border="1"   cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
				  <tr>
			 		<td >			 	
			 		<%=countheadhtml%>
			 		</td>
			 	</tr>
				 
		</Table>
		<table id="" width=80% align="center" border="1"   cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
		 <tr>
					 
					<td align="center"><input class="fb2" type="button" id="" value="Print" style="font-style: italic;font-size:7;color:green;font-weight: bold"  onclick="callReport()"/> <input class="fb2" style="font-style: italic;font-size:7;color:red;font-weight: bold"  type="button" id="" value="Exit" onclick="javascript:window.close()" /></td>
					 
				 
		</tr>
		</table> 
		 <input type="hidden" id="fyear" name="fyear" value="<%=syear%>" ></input> 
       	  <input type="hidden" id="fmonth" name="fmonth" value='<%=smonth%>' ></input> 
</body>

 
</html>