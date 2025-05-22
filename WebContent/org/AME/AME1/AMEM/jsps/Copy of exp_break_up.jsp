   
<html>
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
				window.resizeTo( 790,740 );
			}

			function etype_en()
			{

				var r=parseInt(document.getElementById("tot_row").value);
			 
				if (document.breakup.btype[0].checked) {
					document.getElementById("camt"+r).disabled=false;
					document.getElementById("damt"+r).disabled=	false;
					document.getElementById("etypeflag").value=1;
				}else
				{
					document.getElementById("camt"+r).disabled=true;
					document.getElementById("damt"+r).disabled=true;
					document.getElementById("etypeflag").value=2; 

				}
				


			}
			
			function rowadd()
			{
				var tbody = document.getElementById("td_Ed");
				 var r=parseInt(document.getElementById("tot_row").value)+1;
				 var etypeflag= document.getElementById("etypeflag").value;


				 var dis="";
						if (parseInt(etypeflag)==2) dis="#disable";
				  
				var head_row=cell("TR","","","row0","",2,"","","","","","","");
				var rowh=cell("TD","label","","","Sl.No",2,"lbl","","","2%","left","","");
				var Billno_cell=cell("TD","input","text","billno"+r,"",5,"",""," ","","left","","");
				
				var Billdate_cell=cell("TD","input","text","billdate"+r,"",10,"",""," ","","left","onkeypress","return calins(event,this)");
				var Vouno_cell=cell("TD","input","text","vouno"+r,"",5,"",""," ","","left","","");
				var Vouno_date_cell=cell("TD","input","text","voudate"+r,"",10,"",""," ","","left","onkeypress","return calins(event,this)");
				var Vouno_cell=cell("TD","input","text","vouno"+r,"",6,"",""," ","","left","","");
				var part=cell("TD","textarea","","part"+r,"",0,"tarea",""," ","20%","left","","");
				var camt_cell=cell("TD","input","text","camt"+r,"",6,"","",dis,"","left","","");
				var damt_cell=cell("TD","input","text","damt"+r,"",6,"","",dis,"","left","onblur","sub(this)");
				var Billamt_cell=cell("TD","input","text","amt"+r,"",6,"",""," ","","left","","");
				head_row.appendChild(Billno_cell);	
				head_row.appendChild(Billdate_cell);	
				head_row.appendChild(Vouno_cell);	
				head_row.appendChild(Vouno_date_cell);	 
				head_row.appendChild(part);	 
				head_row.appendChild(camt_cell);
				head_row.appendChild(damt_cell);
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
			HttpSession session1 = request.getSession(false);
			String	userid = (String) session1.getAttribute("UserId");
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			
			Controller obj=new Controller();
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
 			
 			
 			
 			
 			String PMS_WB_BILL_DEDNS="";
 			try {
 			 PMS_WB_BILL_DEDNS=obj.combo_str("PMS_WB_LKP_BILL_DEDNS","BILL_DEDN_DESC","BILL_DEDN_ID","","cond","");
 			}catch(Exception e) { out.println(e);}
 			String aggr_contract="  WHERE TENDER_SNO IN " +
 			" (SELECT TENDER_SNO FROM PMS_TENDER_SCHEME_DETAILS WHERE sch_sno=41     )";
 		//	String aggrcon_combo=obj.combo_str("PMS_TENDER_AGREEMENT","AGREE_REF_NO","AGREE_SNO",aggr_contract,"cond","");
 			String aggrcon_combo="<select id='cond'><option value=0>Select Aggrement </option> <option value=1>TWAD/2011/123</option><option value=2>B</option></select>";
 			String condcombo="<select id='cond'> <option value=0>Select Contractor</option><option value=1>Raja</option><option value=2>Babu</option><option value=3>Balaji</option><option value=4>Gokul</option></select>";
 			

			%>


<body  >
<form name="breakup" action="../../../../../Transactions" method="post"> 
<input type="hidden" id="pmonth" name="pmonth" value="<%=pmonth%>"   />
<input type="hidden" id="pyear" name="pyear" value="<%=pyear%>"   />
<input type="hidden" id="sch" name="sch" value="<%=sch%>"   />
<input type="hidden" id="row" name="row" value="<%=row%>"   />
<input type="hidden" id="MAIN_ITEM_SNO" name="MAIN_ITEM_SNO" value="<%=MAIN_ITEM_SNO%>"   />
<input type="hidden" id="SUB_ITEM_SNO" name="SUB_ITEM_SNO" value="<%=SUB_ITEM_SNO%>"   />
 <input type="hidden" id="GROUP_SNO" name="GROUP_SNO" value="<%=GROUP_SNO%>"   />
 

<table width="75%" class="table" align="center" border=1 cellpadding="5">
<tr class="tdH">
	<td  align="center" colspan="3"><label class="lbl"><%=Office_Name%><br><br>Actual Expenditure - <%=desc%> </label></td>
</tr>	
<tr  >
	<td  align="left"    ><label class="lbl"> Month : &nbsp;&nbsp;<%=monthArr[Integer.parseInt(pmonth)]%>  &nbsp;&nbsp; Year :&nbsp;&nbsp;<%=pyear%> </label></td>
	<td  align="left" width="70%" colspan="2" ><label class="lbl"><font color='blue'> Scheme Name :  &nbsp;&nbsp; <%=sch_name%>&nbsp;&nbsp;</font></label></td>
</tr> 
<tr><td colspan="3"><input  type="radio" name="btype"  onchange="etype_en()"> Contractor Bill <input type="radio"  name="btype"     onchange="etype_en()"> HR Bill</td></tr>
<tr><td colspan="1">Aggrement No  </td><td><%=aggrcon_combo%></td></tr>
<tr><td colspan="1">Contractor Name  </td><td><%=condcombo%></td></tr>
</table>
<table width="75%" class="table" align="center" border=1 cellpadding="5">
 <tr>  
 		<td colspan="2">   
 				<table width="100%" class="table" align="center" border="1" cellpadding="5">
 					<tbody id="td_Ed">
 					<tr>
 					<td align="center" width="10%"><label class="lbl">Bill No</label></td>
 					<td  width="10%"><label class="lbl">Bill Date<bR><font size=2>(dd/mm/yyyy)</font></label></td>
 					
 					<td align="center" width="10%" ><label class="lbl">Voucher No</label></td>
 					<td  width="10%"><label class="lbl">Voucher Date<BR><font size=2>(dd/mm/yyyy)</font></label></td>
 					
 					<td align="center" width="20%"><label class="lbl">Particulars</label></td>
 					<td align="center" width="5%"  ><label class="lbl">Gross Amount</label></td>
 					<td align="center" width="5%"  ><label class="lbl">Deduction Type</label></td>
 					<td align="center" width="5%"  ><label class="lbl">Deduction Amount</label></td>
 					<td align="center" width="5%"  ><label class="lbl">Net Amount</label></td>
 					
 					</tr>	 		 
 					
 					
 					 <tr>
 					<td align="left"  width="10%"><input type="text"  size="6" id="billno1" name="billno1" value=""  class="" /></td><td  width="15%"> <input type="text" id="billdate1"  maxlength="10" size=10 name="billdate1"  onkeypress="return calins(event,this)" value=""  class="" />
 					
 					  
 					<!-- <img src="../../../../../images/calendr3.gif" align="middle" onclick="showCalendarControl(document.breakup.billdate1,1);"  alt="Show Calendar"  >  --> </td>
 					<td align="left" width="10%" ><input type="text"    id="vouno1" name="vouno1" value="" size="6" class="" onKeyUp="isInteger(this,9,event,'vouno1')"  /></td>
 					<td  width="15%"><input type="text"  id="voudate1" name="voudate1" value=""  class="" size="10" onkeypress="return calins(event,this)" maxlength="10" />
 					<!-- <img src="../../../../../images/calendr3.gif" align="middle" onclick="showCalendarControl(document.breakup.voudate1,1);"  alt="Show Calendar"  > --> </td>
 					<td align="left"   > <textarea   class="tarea"  id="part1" name="part1" /></textarea></td>
 					
 					<td align="left"  width="5%"><input type="text" id="camt1" size="6" name="camt1"  value=""  class="" onkeyup="isInteger(this,9,event,'camt1'),digit_control('camt1',2)"/> </td>
 					<td><%=PMS_WB_BILL_DEDNS%></td>
 					<td align="left"  width="5%"><input type="text" id="damt1" size="6" name="damt1"  value=""  class="" onkeyup="isInteger(this,9,event,'damt1'),digit_control('damt1',2)"/> </td>
 					
 					<td align="left"  width="5%"><input type="text" id="amt1" size="6" name="amt1"  value=""  class="" onkeyup="isInteger(this,9,event,'amt1'),digit_control('amt1',2)"/> </td>
 					
 					</tr>
 					</tbody>
 				</table>
 		 </td>
 <Td  width="10%" valign="bottom"> <input type="button" value="-"  style="font-weight: bold;font-size: 13"  class=btn  onclick="del()" />  <input type="button" value="+"   style="font-weight: bold;font-size: 13"  class=btn  onclick="rowadd()" /> </Td>
  </tr> 
  </table>
<table width="75%" class="table" align="center" border=1 cellpadding="5">
<tr>
		<input type="hidden" id="dataflag" name="dataflag" value="1"/> 
		<input type="hidden" id="tot_row" name="tot_row" value="1"   />
		<input type="hidden" id="type" name="type" value="5"   /> 
		<input type="hidden" id="process_code" name="process_code" value="7"   />  	
		<input type="hidden" id="etypeflag" name="etypeflag" value="0"   />  	
	<td align="center" colspan="2">
					<input type="button" value="Save" id='save' class="btn" onclick="transaction(5,7)"/>	
					<input type="Reset" value="Reset"  class="btn" /> 			 	 
					<input type="button" value="Exit"  class="btn" onclick="window.close()"/>  
	</td>
</tr>	
</table>



</form>
</body>
</html>
