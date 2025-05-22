<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import=" java.sql.* "%>
<%@page import="java.util.Hashtable"%>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript"> 
	function rld(a)
	{
		document.location.href="ben_scheme_desg.jsp?sch_sno="+a.value;
	}
	function rld2(a,b)
	{
		document.getElementById("uprow").value=a;
		document.getElementById("sno").value=b;  
	}
	function add()
	{
		var rc=document.getElementById("rc").value;
		var s=0;
		for (i=1;i<=rc;i++)
		{
		var qty=document.getElementById("desgqty"+i).value;
		s=parseFloat(s)+parseFloat(qty);
		}
		document.getElementById("schtot").value=s;  
	}

	function cls()
	{
		var stot=document.getElementById("schtot").value;
		var hab_count=document.getElementById("hab_count").value;
		opener.document.getElementById("sch_qty").value=stot;
		opener.document.getElementById("noofhab").value=hab_count;
		
		window.close();
	}
</script>
<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet" media="screen"/>

</head>  
<body>
<form action="ben_scheme_desg.jsp" method="get" >
 <%
 		String userid="";
 		Controller obj=new Controller();
 		Controller obj1=new Controller();
 		Connection con=obj.con(); 		
 		obj.createStatement(con);
 		obj1.createStatement(con);
 		try
 		{
 				 	userid = (String) session.getAttribute("UserId");
 					if (userid == null) {
						response.sendRedirect(request.getContextPath() + "/index.jsp");
					}
 		}catch(Exception e ) {}
		
		String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='" 
						+ userid + "')");
		
		if (Office_id.equals("")) Office_id="0";
		String Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
 		String qry="SELECT SCH_SNO, SCH_NAME FROM PMS_SCH_MASTER where SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where office_id="+Office_id+")";
 		ResultSet rs=obj.getRS(qry);
 		String sch_sno=obj.setValue("sch_sno",request);
 		  
 		String sch=obj.combo_str("PMS_SCH_MASTER","SCH_NAME","SCH_SNO", 
				 " where SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+") order by SCH_NAME","sch_sno",sch_sno,"class='select' style='width: 262' onchange=rld(this) " );
 		String qry1="";
 		int hab_count=obj.getCount("com_mst_habitations"," WHERE panch_sno IN  (SELECT village_panchayat_sno "+
 		   						   " FROM PMS_DCB_MST_BENEFICIARY  WHERE office_id="+Office_id+
 		  						   " AND beneficiary_type_id=6  AND beneficiary_sno   IN "+
 		    					   " ( SELECT DISTINCT beneficiary_sno FROM pms_dcb_mst_beneficiary_metre "+
 		    					   " WHERE office_id="+Office_id+" AND beneficiary_type_id=6 "+
 		    					   " AND scheme_sno         ="+sch_sno+" ) )");
 %>
 	<table border=1 align="center" width="75%"  style="border-collapse: collapse;border-color: skyblue"> 
 				<td   align="left"  >  
 				<div id='scroll_clipper' style='position:relative; width:100%; border-height:1px; height: 620px; overflow:auto;white-space:nowrap;'>
				<div id='scroll_text'  >
 			<%
 					int row=0;
 					String sch_name="",  ben="",ben_type="",ben_sno="";
 				 
 					String rc=obj.setValue("rc",request);
 					String but=obj.setValue("but",request);
 					String update=obj.setValue("update",request);
 					if (but.equalsIgnoreCase("Submit"))
 					{
	 					for (int i=1;i<=Integer.parseInt(rc);i++)
	 					{
	 						String ben_=obj.setValue("bensno"+i,request);
	 						String desgqty=obj.setValue("desgqty"+i,request);
	 						Hashtable ht=new Hashtable();
	 						int max=obj.getMax("PMS_AME_MST_SCH_DQTY","SCH_DQTY_SNO","");
	 						String pid=obj.getValue("PMS_MST_PROJECTS_VIEW","PROJECT_ID"," where SCH_SNO="+sch_sno);
	 						ht.put("SCH_DQTY_SNO",max);
	 						ht.put("OFFICE_ID",Office_id);
	 						ht.put("SCH_SNO",sch_sno);
	 						ht.put("PROJECT_ID",pid);
	 						ht.put("BENEFICIARY_SNO",ben_);
	 					    ht.put("QTY_DESIGN",desgqty);
	 					    ht.put("UPDATED_BY_USER_ID", "'"+userid+"'");    
	 					    ht.put("UPDATED_TIME", "clock_timestamp()");
	 					 int r=	obj.recordSave(ht,"PMS_AME_MST_SCH_DQTY ", con);    
	 					}   
 					}
 					if (update.equalsIgnoreCase("Update"))
 					{
 						String sno=obj.setValue("sno",request);
 						String uprow=obj.setValue("uprow",request);
 							Hashtable ht1=new Hashtable();
 							Hashtable ht2=new Hashtable();
 							ht2.put("QTY_DESIGN",obj.setValue("desgqty"+uprow,request));
	 					    ht2.put("UPDATED_BY_USER_ID", "'"+userid+"'");    
	 					    ht2.put("UPDATED_TIME", "clock_timestamp()");
	 					   ht1.put("SCH_DQTY_SNO",sno);
	 					  int r=	obj.recordSave(ht2,ht1,"PMS_AME_MST_SCH_DQTY ", con);    
 					} 
 					
 					sch_name=obj.getValue("PMS_SCH_MASTER","SCH_NAME" ,"where SCH_SNO="+sch_sno); 
 				%>  
 				
				 				<table width="100%" border=1  style="border-collapse: collapse;border-color: skyblue">
				 				<tr><td colspan="4">Scheme : <%=sch_name%></td></tr>
				 				<tr class="tdH">
 									<td>Sl.No</td><td> Beneficiaries Name </td><td> Beneficiaries Type </td><td> Design Qty(ML)</td></tr>
 							 
				 				<%
				 				qry1=" SELECT  ben.BENEFICIARY_SNO,ben.BENEFICIARY_NAME, btype.BEN_TYPE_DESC "+
							       " FROM ( SELECT BENEFICIARY_SNO, BENEFICIARY_NAME,  BENEFICIARY_TYPE_ID,"+
							       " OFFICE_ID  FROM PMS_DCB_MST_BENEFICIARY WHERE STATUS ='L' "+
							  	   " AND office_id="+Office_id+" )ben JOIN ( SELECT DISTINCT "+
							       " BENEFICIARY_SNO, office_id  FROM PMS_DCB_MST_BENEFICIARY_METRE"+
							  	   " WHERE METER_STATUS='L' AND office_id     = "+Office_id+
							  	   " AND SCHEME_SNO="+sch_sno+" ) met ON met.BENEFICIARY_SNO=ben.BENEFICIARY_SNO "+
								   " AND met.office_id     =ben.office_id JOIN ( SELECT BEN_TYPE_ID, BEN_TYPE_DESC FROM PMS_DCB_BEN_TYPE"+
							  	   " )btype ON btype.BEN_TYPE_ID=ben.BENEFICIARY_TYPE_ID  ORDER BY BEN_TYPE_DESC, BENEFICIARY_NAME ";
				 					//System.out.println(qry1);
				 					ResultSet rs2=obj1.getRS(qry1);
				 					double tot=0.0;
				 					while (rs2.next())
				 					{
				 						ben=rs2.getString("BENEFICIARY_NAME");
				 						ben_type=rs2.getString("BEN_TYPE_DESC");
				 						ben_sno=rs2.getString("BENEFICIARY_SNO");
				 						row++;
				 						int c=obj.getCount("PMS_AME_MST_SCH_DQTY"," where OFFICE_ID="+Office_id+" and BENEFICIARY_SNO="+ben_sno+" and  SCH_SNO="+sch_sno);
				 						String desg_qty=obj.getValue("PMS_AME_MST_SCH_DQTY","QTY_DESIGN"," where OFFICE_ID="+Office_id+" and BENEFICIARY_SNO="+ben_sno+" and  SCH_SNO="+sch_sno);
				 					%>
				 				
				 						
				 		 				<tr>
				 		 					<td width="5%"><%=row%></td>
				 		 					<td ><%=ben%></td>
				 		 					<td width="20%"><%=ben_type%></td>
				 		 					<td align="left" width="20%"> <input type="text" id='desgqty<%=row%>'  name='desgqty<%=row%>' maxlength="5" size="5" value="<%=desg_qty%>" onblur="add()" style="text-align: right;"  >
				 		 					<input type="hidden" maxlength="5" size="5"  id='bensno<%=row%>' name='bensno<%=row%>' value="<%=ben_sno%>" >
				 		 					<%
				 		 						if (c!=0) 
				 		 						{
				 		 							String SCH_DQTY_SNO=obj.getValue("PMS_AME_MST_SCH_DQTY","SCH_DQTY_SNO"," where OFFICE_ID="+Office_id+" and BENEFICIARY_SNO="+ben_sno+" and  SCH_SNO="+sch_sno);
				 		 							tot+=Double.parseDouble(desg_qty);
				 		 							out.println("<input type=submit value='Update' name='update' onClick='rld2("+row+","+SCH_DQTY_SNO+")'>");
				 		 						}
				 		 					%>
				 		 				</td></tr>
				 		 					  
				 		 				<%	
				 						 
				 					}
				 				 
				 			  %>
				 			  <tr><td colspan=3 align="right">Scheme Total</td><td><input type="text" id='schtot' name="schtot" style="text-align: right;" size="5" value="<%=tot%>"></td></tr>
				 			   
				 				</table>
 				</div><input type='hidden' name="sno" id="sno" value="0"><input type='hidden' name="sch_sno" id="sch_sno" value="<%=sch_sno%>">
 				
 				</div><input type="hidden" id="uprow" name="uprow" value="0">
 				</td><input type="hidden" id="rc" name="rc" value="<%=row%>">
 					<td width="10%" valign="top"><input type="submit"" value="Submit" name="but"><input type="button" id='exit' name="exit" value="Exit" onclick="cls()"></td>
 				   
 				<input type="hidden" id="hab_count" name="hab_count" value="<%=row%>">
 				
 	</table>
 
 
 
</body>  
</html>