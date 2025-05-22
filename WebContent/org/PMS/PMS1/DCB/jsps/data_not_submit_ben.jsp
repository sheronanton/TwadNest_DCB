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
</head>

 
<%
		int total_meter_location=0,pumping_count=0;
 	 	String userid = "0", Office_id = "", Office_Name = "", table_heading = "", table_column = "", table_header = "";
 		String smonth = "0", syear = "0", html = "";
 		Controller obj = null;
 		String metstatus="";
 		String new_cond=Controller.new_cond;
 		String meter_status=Controller.meter_status;
		String meter_status2=Controller.meter_status2;
		String meter_ben_same=Controller.meter_ben_same;
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
 		
 		Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

 	//	Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID",		"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
 		if (Office_id.equals(""))
 			Office_id = "0";
		String oid=obj.setValue("oid",request);
 		
 		if (!oid.trim().equalsIgnoreCase("0"))
 			Office_id=oid;
 		
 		Office_Name = obj.getValue("com_mst_offices",
 				"OFFICE_NAME", "where OFFICE_ID=" + Office_id + " 	");
 		
 		/* 
 		smonth = obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID=" + Office_id), 1);
 		syear = obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID=" + Office_id), 1);
 		*/
 		 String flag="0";
 		  try
 		  {
 		 		flag=session.getAttribute("dcbflag").toString();
 		  } catch(Exception e) {
 		  	flag="0";
 		  }
 		  if (flag.equalsIgnoreCase("")) flag="0"; 
 		 String arg_month=obj.setValue("arg_month",request);
  		 String arg_year=obj.setValue("arg_year",request);
  		
  		if (!arg_month.equalsIgnoreCase("0"))
  		{
  			smonth=arg_month;
 			syear=arg_year;
  		}
  		else
  		{
 		  
 		 
		 		  if (flag.equalsIgnoreCase("1"))
		 		  {
		 		   	String dcbmonth=session.getAttribute("dcbmonth").toString();
		 		  	String dcbyear=session.getAttribute("dcbyear").toString();		  	
		 		  	
		 		  	
		 			  smonth=dcbmonth;
		 		  	  syear=dcbyear;
		 		  }
		 		  else
		 		  {
		 			 smonth = obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID=" + Office_id), 1);
		 	 		syear = obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID=" + Office_id), 1);
		 		  }
  		}		  
 		  
 		  total_meter_location = obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE"," where "+meter_status+"  BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+" office_id="+Office_id+" ) and   office_id="+Office_id);	
 		  pumping_count = obj.getCount("PMS_DCB_TRN_MONTHLY_PR"," where "+meter_ben_same+" and office_id="+Office_id+" ) and   BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+" office_id="+Office_id+" ) and   office_id="+Office_id+" and month="+smonth+" and year="+syear);
 		//metre count in PMS_DCB_MST_BENEFICIARY_METRE table
		int meter_count=Integer.parseInt(obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE" ,"count(distinct BENEFICIARY_SNO) ","c", "where"+meter_status+" OFFICE_ID="+Office_id ));
		int ben_count=Integer.parseInt(obj.getValue("PMS_DCB_MST_BENEFICIARY" ,"count(distinct BENEFICIARY_SNO) ","c", "where "+new_cond+" OFFICE_ID="+Office_id ));
		Controller.condition(1,Office_id); 
		String Ben_Met_Not_Avl=obj.meter_not_for_ben;  
		if (meter_count==ben_count)
		{
			
		}
		else
		{
			metstatus="<font color='red'>Check Meter Missing Status </font>";
		}
	
 		/*String qry ="SELECT na.BENEFICIARY_SNO AS BENEFICIARY_SNO, " 
						+"  na.beneficiary_name     AS beneficiary_name, " 
						+"  ty.BEN_TYPE_DESC        AS BEN_TYPE_DESC,d.BLOCK_NAME as BLOCK_NAME " 
						+"FROM " 
						+"  (SELECT BENEFICIARY_SNO, " 
						+"    beneficiary_name, BLOCK_SNO," 
						+"    BENEFICIARY_TYPE_ID " 
						+"  FROM PMS_DCB_MST_BENEFICIARY " 
						+"  WHERE "+new_cond+" office_id          ="+Office_id 
						+"  AND "+Ben_Met_Not_Avl+" and beneficiary_sno IN ( SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY_METRE WHERE "+meter_status+" office_id="+Office_id
						+"    AND METRE_SNO not IN   " 
						+"    (SELECT METRE_SNO " 
						+"    FROM PMS_DCB_TRN_MONTHLY_PR " 
						+"    WHERE office_id= "+Office_id 
						+"    AND MONTH      = "+smonth 
						+"    AND YEAR       = " +syear
						+"      ) ) "   
						+"  )na "  
						+"JOIN " 
						+"  (SELECT BEN_TYPE_ID, BEN_TYPE_DESC FROM PMS_DCB_BEN_TYPE " 
						+"  )ty " 
						+" ON na.BENEFICIARY_TYPE_ID=ty.BEN_TYPE_ID " 
						+"  LEFT OUTER JOIN "
  						+" (   SELECT      BLOCK_SNO,      BLOCK_NAME    FROM COM_MST_BLOCKS  )d "
						+"  ON d.BLOCK_SNO = na.BLOCK_SNO  order by BEN_TYPE_ID";
					 
 	 */
 		String qry ="select "
 			+" subdiv.OFFICE_NAME, met.metre_location,"
 		  +" ben.beneficiary_sno AS beneficiary_sno,"
 		  +" ben.beneficiary_name AS beneficiary_name,"
 		  +" ty.ben_type_desc AS ben_type_desc,"
 		  +" d.block_name AS block_name"
 		  +" from "
 		  +" ( "
 		  +" SELECT METRE_SNO,BENEFICIARY_SNO,SUB_DIV_ID,OFFICE_ID,METRE_LOCATION "
 		  +" FROM pms_dcb_mst_beneficiary_metre "
 		  +" WHERE office_id ="+Office_id
 		  +" AND meter_status = 'L' "
 		  +" AND metre_sno NOT IN "
 		  +" (SELECT metre_sno "
 		  +" FROM pms_dcb_trn_monthly_pr "
 		  +" WHERE office_id ="+Office_id
 		  +" AND MONTH ="+smonth
 		  +" AND YEAR ="+syear+") "
 		  +" )met  "
 		  +" join  "
 		  +" ( "
 		  + " sELECT beneficiary_sno, "
 		  + " beneficiary_name, "
 		  + " block_sno,"
 		  +  " beneficiary_type_id,"
 		  +  " office_id "
 		  +  " FROM pms_dcb_mst_beneficiary where  office_id = "+Office_id+" and status='L' "
 		  +    " )ben "
 		  + " on ben.beneficiary_sno=met.beneficiary_sno "
 		  + " and met.office_id=ben.office_id " 
 		  + " JOIN "
 		  + " (SELECT ben_type_id, "
 		  + 	  " ben_type_desc "
 		  +  " FROM pms_dcb_ben_type) "
 		  + " ty ON ben.beneficiary_type_id = ty.ben_type_id LEFT "
 		  + " OUTER JOIN "
 		  + " (SELECT block_sno, "
 		  + 	  " block_name "
 		  + "  FROM com_mst_blocks) "
 		  +  " d ON d.block_sno = ben.block_sno join " 
 		  + " (select OFFICE_ID,OFFICE_NAME from com_mst_offices  ) subdiv on subdiv.OFFICE_ID=met.SUB_DIV_ID ";   	
 	 
 		 //out.println(qry);
 		table_column = "BENEFICIARY_NAME,metre_location,BEN_TYPE_DESC,OFFICE_NAME,BLOCK_NAME"; 
 		table_header = "Beneficiary Name,metre_location,Beneficiary Type,Sub Division,Block Name";
 		String table_td_set = " width='25%', width='15%', align=left width='10%' ,align=left width='25%',align=left width='15%'"; 
 		table_heading = "Beneficiary Pumping Return Omission - "+ Office_Name + "";
 		String tab = "cellspacing='0' cellpadding='3' width='100%' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'";
 		html = obj.genReport(qry, table_header, table_column, tab,table_heading, table_td_set);
 		con.close();
 	} catch (Exception e) {  
 		 	response.sendRedirect(request.getContextPath()+"/index.jsp");
 	}
 %> 
		<body onload="blink()"> 
		<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
		<title><%=table_heading%>  | TWAD Nest - Phase II </title>
		<Table class="fb2"    id="" width=100% align="center" border="1"   cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
				<tr>
					<td colspan=2 class=fnt><center><font size='3'><%=table_heading%></font></center></td>
				</tr> 
				
				<tr>
					<td colspan=2 class="tdText" width="20%"><b> Month and Year : <%=obj.month_val(smonth)%> <%=syear%> </b></td>
				</tr>
				<tr >
			 		<td colspan=2><label class="lbl">Total Meter Location : <font color='green'> &nbsp;&nbsp;&nbsp;  <%=total_meter_location%></font>&nbsp;&nbsp;&nbsp;and&nbsp;&nbsp;&nbsp; Monthly Pumping Return  Count:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color='blue'> <%=pumping_count%></font> &nbsp;&nbsp;</label></td> 
			 	</tr>
				<Tr>
					<td colspan=2><%=html%></td>  
				</Tr> 
				<tr>
					<td colspan=2 align="center"><input type="button" class="fb2" value="Back" onclick="history.go(-1)">
					<input class="fb2" style="font-style: italic;font-size:7;color:red;font-weight: bold"  type="button" id="" value="Exit" onclick="javascript:window.close()" />
					<img src="../../../../../images/help_images.jpg" height="18px" width="45px" style="vertical-align: bottom;" onClick="window.open('help1.jsp?fn=68','mywindow','scrollbars=yes')">
				</td>
				</tr> 
		</Table>
		 <input type="hidden" id="fyear" name="fyear" value="<%=syear%>" ></input>
		  <input type="hidden" id="mst" name="mst"></input>  
       	  <input type="hidden" id="fmonth" name="fmonth" value='<%=smonth%>' ></input> 
</body>
</html>