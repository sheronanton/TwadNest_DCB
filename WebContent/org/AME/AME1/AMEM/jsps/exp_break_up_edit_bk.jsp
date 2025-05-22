<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%><html>
<head>
 <%@page import="java.util.Calendar" %>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
 			 <link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet" media="screen"/>
<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Actual Expenditure BreakUp </title>
<script type="text/javascript"> 
			function page_size()
			{ 
				window.resizeTo( 790,640 );
			}
			function rowadd()
			{
				var tbody = document.getElementById("td_Ed");
				 var r=parseInt(document.getElementById("tot_row").value)+1;
				var head_row=cell("TR","","","row0","",2,"","","","","","","");
				var rowh=cell("TD","label","","","Sl.No",2,"lbl","","","2%","","","");
				var Billno_cell=cell("TD","input","text","billno"+r,"",2,"tb2",""," ","","center","","");
				
				var Billdate_cell=cell("TD","input","text","billdate"+r,"",2,"tb2",""," ","","center","","");
				var Vouno_cell=cell("TD","input","text","vouno"+r,"",2,"tb2",""," ","","center","","");
 				var Vouno_date_cell=cell("TD","input","text","voudate"+r,"",2,"tb2",""," ","","center","","");
				
				var part=cell("TD","textarea","","part"+r,"",2,"tarea",""," ","20%","center","","");
				var Billamt_cell=cell("TD","input","text","amt"+r,"",2,"tb2",""," ","","center","","");
				head_row.appendChild(Billno_cell);	
				head_row.appendChild(Billdate_cell);	
				head_row.appendChild(Vouno_cell);	
				head_row.appendChild(Vouno_date_cell);	 
				head_row.appendChild(part);	 
				head_row.appendChild(Billamt_cell);
				tbody.appendChild(head_row);
				document.getElementById("tot_row").value=parseInt(document.getElementById("tot_row").value)+1;  
			}
			function del()
			{
				var tbody = document.getElementById("td_Ed");
			 
				var s=document.getElementById("tot_row").value;
				for(t=tbody.rows.length-1;(t>=s && t!=0);t--)
				{
					tbody.deleteRow(t);
				}	 
				document.getElementById("tot_row").value=parseInt(document.getElementById("tot_row").value)-1;
					
			}
</script>
<script type="text/javascript" src="../scripts/transcat.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/CalendarCtrl.js"></script> 
</head>
<%
			ResultSet rs_local ;
			HttpSession session1 = request.getSession(false);
			String	userid = (String) session1.getAttribute("UserId");
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			Connection con=null;
			Controller obj=new Controller();
			con=obj.con();
			if (userid == null) {

				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			String row=obj.setValue("row",request);
			String desc_Sno=obj.setValue("desc",request);
			String pyear=obj.setValue("pyear",request);
			String pmonth=obj.setValue("pmonth",request);
			String sch=obj.setValue("sch_sno",request);
			String MAIN_ITEM_SNO=obj.setValue("MAIN_ITEM_SNO",request);
			String SUB_ITEM_SNO=obj.setValue("SUB_ITEM_SNO",request);
			String GROUP_SNO=obj.setValue("GROUP_SNO",request);
			
			
			String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
			if (Office_id.equals("")) Office_id="0";
			String Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
			String desc=obj.getValue("PMS_AME_MST_MAIN_ITEM", "MAIN_ITEM_DESC","where MAIN_ITEM_SNO="+desc_Sno+ "  ");
			String sch_name=obj.getValue("PMS_SCH_MASTER", "SCH_NAME","where SCH_SNO="+sch);
			
			String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};

			
			String qry=" SELECT BILL_SNO, " +
			" BILL_NO, " +
			  " to_char( BILL_DATE,'DD/MM/YYYY') as BILL_DATE, " +
			  " VOUCHER_NO, " +
			  " to_char( VOUCHER_DATE,'DD/MM/YYYY') as VOUCHER_DATE, " +
			  " BILL_AMT, " +
			  " PARTICULARS " +
			" FROM PMS_AME_EXP_ITEM_BREAKUP " +
			" WHERE OFFICE_ID="+Office_id+" and YEAR= " +pyear+
			" AND MONTH=" +pmonth+
			" AND PERFORM_DESC_SNO=3 " +
			" AND MAIN_ITEM_SNO= " +MAIN_ITEM_SNO+
			" AND SUB_ITEM_SNO="+SUB_ITEM_SNO+" and GROUP_SNO="+GROUP_SNO +" and SCH_SNO="+sch;
			
			%>
<body onload="page_size() ">
<form name="breakup" action="../../../../../Transactions" method="post"> 
<input type="hidden" id="pmonth" name="pmonth" value="<%=pmonth%>"   />
<input type="hidden" id="pyear" name="pyear" value="<%=pyear%>"   />
<input type="hidden" id="sch" name="sch" value="<%=sch%>"   />
<input type="hidden" id="row" name="row" value="<%=row%>"   />
<input type="hidden" id="MAIN_ITEM_SNO" name="MAIN_ITEM_SNO" value="<%=MAIN_ITEM_SNO%>"   />
<input type="hidden" id="SUB_ITEM_SNO" name="SUB_ITEM_SNO" value="<%=SUB_ITEM_SNO%>"   />
 <input type="hidden" id="GROUP_SNO" name="GROUP_SNO" value="<%=GROUP_SNO%>"   />
 
<table width="100%" class="tab" align="center" border=1 cellpadding="5">
<tr>
	<td  align="center" colspan="2"><label class="lbl"><%=Office_Name%><br><br>Actual Expenditure - <%=desc%> </label></td>
</tr>	
<tr bgcolor="skyblue">
	<td  align="left" ><label class="lbl"><font color='blue'>Month & Year: &nbsp;&nbsp;<%=monthArr[Integer.parseInt(pmonth)]%> and <%=pyear%></font></label></td>
	<td  align="right" ><label class="lbl"><font color='blue'><%=sch_name%>&nbsp;&nbsp;</font></label></td>
</tr> 
 <tr>
 		<td colspan="2">   
 				<table width="100%" class="tab" align="center" border="1" cellpadding="5" id='id_tab'>
 					<tbody id="td_Ed">
 					<tr>
 					<td align="center" width="10%"><label class="lbl">Bill No</label></td><td  width="15%"><label class="lbl">Bill Date</label></td>
 					<td align="center" width="10%" ><label class="lbl">Voucher No</label></td><td  width="15%"><label class="lbl">Voucher Date</label></td>
 					<td align="center" ><label class="lbl">Particulars</label></td>
 					<td align="center" width="5%"><label class="lbl">Amount</label></td>
 					</tr>
 					
 					<%
 					PreparedStatement ps_local=con.prepareStatement(qry);
 				 	rs_local=ps_local.executeQuery();
 					int r=0;
 					while(rs_local.next())
 					{
 						 r++;
 					%>	 		 
 					<tr>
 					<td align="left"  width="10%"><input type="text" id="billno<%=r%>" name="billno<%=r%>" value="<%=rs_local.getString("BILL_NO")%>"   class="tb2" /></td>
 					<td  width="15%"> <input type="text" id="billdate<%=r%>" name="billdate<%=r%>"  value="<%=rs_local.getString("BILL_DATE")%>"  class="tb2" /><img src="../../../../../images/calendr3.gif" align="middle" onclick="showCalendarControl(document.breakup.billdate1,1);"  alt="Show Calendar"  >   </td>
 					<td align="left" width="10%" ><input type="text"  id="vouno<%=r%>" name="vouno<%=r%>" value="<%=rs_local.getString("VOUCHER_NO")%>"  class="tb2" /></td>
 					<td  width="15%"><input type="text"  id="voudate<%=r%>" name="voudate<%=r%>" value="<%=rs_local.getString("VOUCHER_DATE")%>"  class="tb2" />
 					<!-- <img src="../../../../../images/calendr3.gif" align="middle" onclick="showCalendarControl(document.breakup.voudate1,1);"  alt="Show Calendar">
 					-->
 					</td>
 					<td align="left"   > <textarea   class="tarea"  id="part<%=r%>" name="part<%=r%>"  /><%=rs_local.getString("PARTICULARS")%></textarea></td>
 					<td align="left"  width="5%"><input type="text" id="amt<%=r%>" name="amt<%=r%>"  value="<%=rs_local.getString("BILL_AMT")%>"  class="tb2"  onkeyup="isInteger(this,9,event,'amt<%=r%>'),digit_control('amt<%=r%>',2)" /> </td>
 					</tr>
 					<%}   %>
 					</tbody>
 					 
 					   
 				</table>   
 		 </td>
 		<Td valign="bottom"> <input type="button" value="  - "   onclick="del()" />  <input type="button" value=" + "    onclick="rowadd()" style="font-weight: bold;font-size: 13" /> </Td>
  </tr>  
<tr>	  	
	<td align="center" colspan="2">
					<input type="button" value="Add" id='save' class="btn" onclick="transaction(5,7)"/>	
					<input type="Reset" value="Reset"  class="btn" /> 			 	 
					<input type="button" value="Exit"  class="btn" onclick="window.close()"/>  
	</td>
</tr>	
</table>
		<input type="hidden" id="dataflag" name="dataflag" value="2"/> 
		<input type="hidden" id="tot_row" name="tot_row" value="<%=r%>"/>  
		<input type="hidden" id="type" name="type" value="5"/> 
		<input type="hidden" id="process_code" name="process_code" value="7"/>  

</form>
</body>
</html>
