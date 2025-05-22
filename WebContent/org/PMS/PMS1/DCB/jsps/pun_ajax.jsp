 
 
<%@ page language="java"
	import="Servlets.PMS.PMS1.DCB.servlets.*,java.sql.*,java.io.*"
	%>
	<%@ page import="Servlets.Security.classes.UserProfile"%>
 <%   
	 response.setContentType("text/html");
	java.sql.Connection con = null;
	Controller obj = new Controller();
	con = obj.con();
	obj.createStatement(con);
	  String strCommand=obj.setValue("command", request);
	  System.out.println(" strCommand is "+strCommand);
	  ResultSet result=null;
	  Statement statement=null;
	  String xml;
	  statement=con.createStatement();
	  con.clearWarnings();
	  String table="";
try
 {
	
	if(strCommand.equalsIgnoreCase("stype"))
	{
		String sch=obj.setValue("sch",request);
		String schtype=obj.getValue("PMS_SCH_MASTER","SCH_TYPE_ID"," where SCH_SNO="+sch);
		String schtypevalue=obj.getValue("PMS_SCH_LKP_TYPE","SCH_TYPE_DESC"," where SCH_TYPE_ID="+schtype);
		response.getWriter().write("text" + "|" +schtypevalue);
	} else if(strCommand.equalsIgnoreCase("wccalprv"))
	{
		String userid = (String) session.getAttribute("UserId");
 		if (userid == null) 
		{
				response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
 		String 	Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	//	String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
 		String tot_read=obj.setValue("tot_read",request);
 		String ben_sno=obj.setValue("ben_value",request);
 		String rectmonth=obj.setValue("rectmonth",request);
 		String rectyear=obj.setValue("rectyear",request);
 		String meter_sno=obj.setValue("selsno",request);
 		String TARIFF_FLAG=obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE","TARIFF_FLAG","where METRE_SNO="+ meter_sno+ " and BENEFICIARY_SNO="+ ben_sno+ " and Office_id="+Office_id+" and METER_STATUS='L'");
  		
		 long res=0;	
 		 Pumping_Value val=new Pumping_Value();
 		 
 		 System.out.println("quantity is "+Double.parseDouble(tot_read));
 		 
 		 res =val.Value(Double.parseDouble(tot_read),TARIFF_FLAG,ben_sno,Office_id,meter_sno,rectmonth,rectyear);
 		 System.out.println("Demand is "+res);
 		 response.getWriter().write("text" + "|" +res);
		
		  
	}
		
	else
	{
		 	if(strCommand.equalsIgnoreCase("Urban_Details"))
		        {
		        	result=null;
		        		try {
		        			String URBANLB_SNO=obj.setValue("ursno", request);
							result=statement.executeQuery("select URBANLB_SNO,STATE_CODE,DISTRICT_CODE,	URBANLB_NAME,URBANLB_TYPE_ID,URBANLB_GRADE_ID,AREA_TYPE_ID from COM_MST_URBAN_LB where URBANLB_SNO="+URBANLB_SNO);
							String cb_1_v="",cb_2_v="",cb_3_v="",cb_4_v="",cb_5_v="",URBANLB="";
							  
				                 if(result.next())
				                 {
				                	 URBANLB=obj.isNull(result.getString("URBANLB_NAME"),1);
				                	 cb_1_v=obj.isNull(result.getString("URBANLB_TYPE_ID"),1);
				                	 cb_2_v= obj.isNull(result.getString("URBANLB_GRADE_ID"),1);
				                	 cb_4_v= obj.isNull(result.getString("DISTRICT_CODE"),1);
				                	 cb_5_v= obj.isNull(result.getString("AREA_TYPE_ID"),1);
				                 }
							String cb_1=obj.combo_str("COM_MST_URBAN_LB_TYPE", "URBANLB_TYPE_DESC", "URBANLB_TYPE_ID", "","", cb_1_v," class=select");
							String cb_2=obj.combo_str("COM_MST_URBAN_LB_GRADE", "URBANLB_GRADE_DESC", "URBANLB_GRADE_ID","", "", cb_2_v," class=select");
							String cb_3=obj.combo_str("COM_MST_DISTRICTS", "DISTRICT_NAME", "DISTRICT_CODE","", "", cb_4_v," class=select");
							  table="<table  cellspacing='0' cellpadding='3' style='BORDER-COLLAPSE: collapse' width='75%'>" +
									"<tr  >" +
									"<td class='tdText' align=left>Urban Name </td><td align=left><input type=text class=tbaddr value="+URBANLB+"></td>" +
									"</tr>" +
									"<tr  >" +
									"<td class='tdText'  align=left>Urban Type</td><td align=left>"+cb_1+"</td>" + 
									"</tr>" + 
									"<tr  >" +
									"<td class='tdText'  align=left>Urban Grade</td><td align=left>"+cb_2+"</td>" +
									"</tr>" +							
									"<tr  >" +
									"<td class='tdText'  align=left>District</td><td align=left>"+cb_3+"</td>" +
									"</tr>" +
									"<tr  >" +
									"<td class='tdText'  align=left>Area Type</td><td align=left><input type=text class=tb5 value="+cb_5_v+"></td>" +
									"</tr>" +
									"</table>";
		 					 
						 	 response.getWriter().write("text" + "|" +table.trim());
						} catch (Exception e) {
							e.printStackTrace();
						}  
		        }
		 	else
		 	{
		 		String userid = (String) session.getAttribute("UserId");
		 		if (userid == null) 
				{
						response.sendRedirect(request.getContextPath() + "/index.jsp");
				}
		 	String	Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

				//String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
		 		String tot_read=obj.setValue("tot_read",request);
		 		String ben_sno=obj.setValue("ben_value",request);
		 		String meter_sno=obj.setValue("selsno",request);
		 		String TARIFF_FLAG=obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE","TARIFF_FLAG","where METRE_SNO="+ meter_sno+ " and BENEFICIARY_SNO="+ ben_sno+ " and Office_id="+Office_id+" and METER_STATUS='L'");
		  		
				 long res=0;	
		 		 Pumping_Value val=new Pumping_Value();
		 		 res =val.Value(Double.parseDouble(tot_read),TARIFF_FLAG,ben_sno,Office_id,meter_sno);
		 		 response.getWriter().write("text" + "|" +res);
		 	} 
	}
 }catch(Exception e) { }
        %>