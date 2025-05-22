<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>	
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Division Record Report</title>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
</head>
<body>
<%
	String userid="",Office_id="",Office_Name="",smonth="",syear="",table_column="";
	String table_header="",table_heading="",html="";
	Controller obj=null;
	Connection con;
try {
 	
		String new_cond=Controller.new_cond;
		  obj = new Controller();
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
 		Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

 	//	Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID",			"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"					+ userid + "')");
		String fun=obj.setValue("fun",request);
 		String oid=obj.setValue("oid",request);
 		Controller.condition(1,Office_id); 
		String Ben_Met_Not_Avl=obj.meter_not_for_ben;  
		
	 
		
 		if (!oid.trim().equalsIgnoreCase("0"))
 			Office_id=oid;
 		
 		String cond="",addcolumn="",addhead="",addcolumn1="";
 		String head="",addcond="",addset="";
 		
 		if (fun.trim().equalsIgnoreCase("1")) {
 			cond="";
 			head="Beneficiary Details ";
 			}
 		else if (fun.trim().equalsIgnoreCase("2")) {
 			cond=" and  BENEFICIARY_TYPE_ID <=6 ";
 			head="  LocalBody Beneficiary Details ";
 		}
 		else if (fun.trim().equalsIgnoreCase("3")) {
 			cond=" and  BENEFICIARY_TYPE_ID >6 ";
 			head=" Private Beneficiary Details ";
 		}else if (fun.trim().equalsIgnoreCase("9"))
 		{	
 			
 			head=" Beneficiary Details - Tariff Not Set ";
 			cond=" AND beneficiary_sno  IN " 
	 			+"    (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY_METRE WHERE    meter_status='L' and SETTING_FLAG is null and  office_id= "+Office_id 
	 			+"    ) " ; 
 		}  
 		else if (fun.trim().equalsIgnoreCase("10"))  
 		{	
 			cond=" AND beneficiary_sno  IN " 
	 			+"    ( SELECT beneficiary_sno FROM PMS_DCB_TARIFF_SLAB WHERE office_id= "+Office_id 
	 			+"     ) ";
 			head=" Beneficiary Details - Rate Entered";
 		}else if (fun.trim().equalsIgnoreCase("11"))
 		{	
 			head=" Beneficiary Details - Rate Not Entered";
 			cond=" AND beneficiary_sno Not  IN " 
	 			+"    ( SELECT beneficiary_sno FROM PMS_DCB_TARIFF_SLAB WHERE office_id= "+Office_id 
	 			+"    ) and  "+Ben_Met_Not_Avl; 
 		}
 		  
 		else  if (fun.trim().equalsIgnoreCase("5"))
 		{
 			head=" Meter Location of  Beneficiary ";
 			addcolumn1=" ,met.METRE_LOCATION as METRE_LOCATION ";
 			addcolumn=" , METRE_LOCATION ";
 			addhead=" ,Metre Location "; 
 			addset=" ,'align=left width='25%' ";
 			addcond=" JOIN ( select METRE_LOCATION ,OFFICE_ID,BENEFICIARY_SNO "
 					+" FROM PMS_DCB_MST_BENEFICIARY_METRE  where  meter_status='L' ) met "
 					+" ON met.BENEFICIARY_SNO=ben.BENEFICIARY_SNO "
 					+" AND met.OFFICE_ID     =ben.OFFICE_ID ";
 		}
 		else  if (fun.trim().equalsIgnoreCase("6"))
 		{
 			head=" Meter Location of  Beneficiary  - Location Based";
 			addcolumn1=" ,met.METRE_LOCATION as METRE_LOCATION ";
 			addcolumn=" , METRE_LOCATION ";
 			addhead=" ,Metre Location "; 
 			addset=" ,'align=left width='25%' ";
 			addcond=" JOIN ( select METRE_LOCATION ,OFFICE_ID,BENEFICIARY_SNO "
 					+" FROM PMS_DCB_MST_BENEFICIARY_METRE where TARIFF_FLAG='L'  and meter_status='L') met "
 					+" ON met.BENEFICIARY_SNO=ben.BENEFICIARY_SNO "
 					+" AND met.OFFICE_ID     =ben.OFFICE_ID ";
 		}
 		else  if (fun.trim().equalsIgnoreCase("7"))
 		{
 			head="  Meter Location of  Beneficiary  - Scheme Based";
 			addcolumn1=" ,met.METRE_LOCATION as METRE_LOCATION ";
 			addcolumn=" , METRE_LOCATION ";
 			addhead=" ,Metre Location "; 
 			addset=" ,'align=left width='25%' ";
 			addcond=" JOIN ( select METRE_LOCATION ,OFFICE_ID,BENEFICIARY_SNO "
 					+" FROM PMS_DCB_MST_BENEFICIARY_METRE where TARIFF_FLAG='S' and meter_status='L') met "
 					+" ON met.BENEFICIARY_SNO=ben.BENEFICIARY_SNO "
 					+" AND met.OFFICE_ID     =ben.OFFICE_ID ";
 		}
 		else if (fun.trim().equalsIgnoreCase("14"))
 		{	 
 			
 			head=" Beneficiary Details - Tariff Setting in Create Status  ";
 			cond=" AND beneficiary_sno  IN " 
	 			+"    (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY_METRE WHERE    meter_status='L' and SETTING_FLAG='CR' and  office_id= "+Office_id 
	 			+"    ) " ; 
 		} else if (fun.trim().equalsIgnoreCase("15"))
 		{	
 			
 			head=" Beneficiary Details - Tariff Setting in Freezed Status  ";
 			cond=" AND beneficiary_sno  IN " 
	 			+"    (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY_METRE WHERE    meter_status='L' and SETTING_FLAG='FR' and  office_id= "+Office_id 
	 			+"    ) " ; 
 		} 
 		if (Office_id.equals(""))
 			Office_id = "0";
 		if (!oid.trim().equalsIgnoreCase("0"))
 			Office_id=oid;   
 		
 		Office_Name = obj.getValue("com_mst_offices","OFFICE_NAME", "where OFFICE_ID=" + Office_id + " 	");
 		
 		smonth = obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID=" + Office_id), 1);
 		
 		syear = obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID=" + Office_id), 1);
		
 		String qry= " SELECT ben.BENEFICIARY_NAME as BENEFICIARY_NAME,"
 			 	   +" ty.BEN_TYPE_DESC as  BEN_TYPE_DESC " + addcolumn1
 			 	   +"	FROM "
 			   	   +" ( SELECT BENEFICIARY_SNO, "
 			       +" BENEFICIARY_TYPE_ID, "
 			       +" BENEFICIARY_NAME, "
 			       +" office_id "
 			       +" FROM PMS_DCB_MST_BENEFICIARY "
 			       +" WHERE "+new_cond+"  office_id ="+Office_id+" "+cond+"  ) ben "
 			       +" JOIN "
 			       +" (SELECT BEN_TYPE_ID, BEN_TYPE_DESC FROM PMS_DCB_BEN_TYPE "
 			       +" )ty "
 			       +" ON ty.BEN_TYPE_ID=ben.BENEFICIARY_TYPE_ID "+addcond +" order by BEN_TYPE_ID,BENEFICIARY_NAME";
 			
 		 
 	// out.println(qry);
 		
 		table_column = "BENEFICIARY_NAME,BEN_TYPE_DESC"+addcolumn;	   	   
 		table_header = "Beneficiary,Beneficiary Type"+addhead;
 		String table_td_set = "width='25%',width='15%'"+addset;
 		table_heading = "   "+head+" - "+ Office_Name + "";
 		String tab = "cellspacing='0' cellpadding='3' width='100%' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'";
 		html = obj.genReport(qry, table_header, table_column, tab,table_heading, table_td_set);

 		con.close();
 		con=null;
}catch(Exception e) {}

%>

		<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
		<title><%=table_heading%> | TWAD Nest - Phase II </title>
		<Table      id="" width=75% align="center" border="1"   cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
				<tr>
					<td colspan="2"><center><b><%=table_heading%></b> </center></td>
				</tr> 
				<tr>
					<td colspan="2" align="right"><input type="button" class="fb2" value="Back" onclick="history.go(-1)">  <input class="fb2"   type="button" id="" value="Exit" onclick="javascript:window.close()" /></td>
				</tr> 
				 
				   
				<Tr>
					<td colspan="2"><%=html%></td>  
				</Tr>
				<tr>
					<td colspan="2" align="center"><input type="button" class="fb2" value="Back" onclick="history.go(-1)">  <input class="fb2"   type="button" id="" value="Exit" onclick="javascript:window.close()" /></td>
				</tr> 
			 	 
		</Table>
		 <input type="hidden" id="fyear"  name="fyear"  value="<%=syear%>"></input> 
       	 <input type="hidden" id="fmonth" name="fmonth" value='<%=smonth%>'></input> 




</body>
</html>
 