<html><head>
<%@page import="java.util.Calendar,java.sql.*" %>
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
				 document.getElementById("agvalue").innerHTML = "";
				 var r=parseInt(document.getElementById("tot_row").value);
			  if (document.breakup.btype[0].checked) 
			  {
					document.getElementById("camt"+r).disabled=false;
					document.getElementById("damt"+r).disabled=	false;
					document.getElementById("etypeflag").value=1;  
					var aggrcon_combo=document.getElementById("aggrcon_combo").value;
					document.getElementById("ag").innerHTML="Aggrement No  : "+aggrcon_combo;
					var condcombo=document.getElementById("condcombo").value;
					document.getElementById("ag1").innerHTML="Contractor Name  : "+condcombo;
			  }else
			  {
					document.getElementById("ag").innerHTML="";
					document.getElementById("ag1").innerHTML="";
					document.getElementById("camt"+r).disabled=true;
					document.getElementById("damt"+r).disabled=true;
					document.getElementById("etypeflag").value=2; 
			}
		}

			function samebill()
			{

				var tbody = document.getElementById("td_Ed");
				var r=parseInt(document.getElementById("tot_row").value)+1;
				var etypeflag= document.getElementById("etypeflag").value;
				var head_row=cell("TR","","","row0","",2,"","","","","","","");
				var rowh=cell("TD","label","","","Sl.No",2,"lbl","","","2%","center","","");
				var bn=document.getElementById("billno"+(r-1)).value
				var bdate=document.getElementById("billdate"+(r-1)).value
				var vouno=document.getElementById("vouno"+(r-1)).value
				var voudate=document.getElementById("voudate"+(r-1)).value
				var part=document.getElementById("part"+(r-1)).value
				var camt=document.getElementById("camt"+(r-1)).value
				var Billno_cell=cell("TD","input","text","billno"+r,bn,5,"","","#readonly","","center","","");
				var div_cell=cell("TD","div","text","div"+r,"",5,"",""," ","","center","","");
				
				var Billdate_cell=cell("TD","input","text","billdate"+r,bdate,10,"","","#readonly ","","center","onkeypress","return calins(event,this)");
				//	var Vouno_cell=cell("TD","input","text","vouno"+r,vouno,5,"",""," ","","center","","");
					var Vouno_date_cell=cell("TD","input","text","voudate"+r,voudate,10,"","","#readonly","","center","onkeypress","return calins(event,this)");
					var Vouno_cell=cell("TD","input","text","vouno"+r,vouno,6,"","","#readonly","","center","","");
					var part=cell("TD","textarea","","part"+r,"",0,"tarea",part," ","20%","center","","");
					var camt_cell=cell("TD","input","text","camt"+r,camt,6,"","","#readonly","","center","","");
					var damt_cell=cell("TD","input","text","damt"+r,"",6,"","","","","center","onblur","div_v_samebill(this,"+r+")");
					var Billamt_cell=cell("TD","input","text","amt"+r,"",6,"",""," ","","center","","");
					head_row.appendChild(Billno_cell);	
					head_row.appendChild(Billdate_cell);	
					head_row.appendChild(Vouno_cell);	
					head_row.appendChild(Vouno_date_cell);	 
					head_row.appendChild(part);	 
					head_row.appendChild(camt_cell);
					head_row.appendChild(div_cell);
					head_row.appendChild(damt_cell);
					head_row.appendChild(Billamt_cell);  
					tbody.appendChild(head_row);
					var cb=document.getElementById("cbs").value;
					var ss="";
					for (i = 0; i < cb.length; i++)
				      {
						 var c = cb.charAt(i);
						if (c=='#')		{						
							ss+=r;
						}
						else
						{
							ss+=c;
						}
				      }
				 
					document.getElementById("div"+r).innerHTML=ss;
					document.getElementById("tot_row").value=parseInt(document.getElementById("tot_row").value)+1;  
			}
			
			function rowadd()
			{
				var tbody = document.getElementById("td_Ed");
				 var r=(parseInt(document.getElementById("tot_row").value)+1);
				 
				 var etypeflag= document.getElementById("etypeflag").value;

  
					 var dis="";
						if (parseInt(etypeflag)==2) dis="#disable";
				  
				var head_row=cell("TR","","","row0","",2,"","","","","","","");
				var rowh=cell("TD","label","","","Sl.No",2,"lbl","","","2%","center","","");
				var Billno_cell=cell("TD","input","text","billno"+r,"",5,"",""," ","","center","","");
				var div_cell=cell("TD","div","text","div"+r,"",5,"",""," ","","center","","");
				var Billdate_cell=cell("TD","input","text","billdate"+r,"",10,"",""," ","","center","onkeypress","return calins(event,this)");
				var Vouno_cell=cell("TD","input","text","vouno"+r,"",5,"",""," ","","center","","");
				var Vouno_date_cell=cell("TD","input","text","voudate"+r,"",10,"",""," ","","center","onkeypress","return calins(event,this)");
				var Vouno_cell=cell("TD","input","text","vouno"+r,"",6,"",""," ","","center","","");
				var part=cell("TD","textarea","","part"+r,"",0,"tarea",""," ","20%","center","","");
				var camt_cell=cell("TD","input","text","camt"+r,"",6,"","",dis,"","center","","");
				var damt_cell=cell("TD","input","text","damt"+r,"",6,"","",dis,"","center","onblur","div_v(this,"+r+")");
				var Billamt_cell=cell("TD","input","text","amt"+r,"",6,"",""," ","","center","","");
				head_row.appendChild(Billno_cell);	
				head_row.appendChild(Billdate_cell);	
				head_row.appendChild(Vouno_cell);	
				head_row.appendChild(Vouno_date_cell);	 
				head_row.appendChild(part);	 
				head_row.appendChild(camt_cell);
				head_row.appendChild(div_cell);
				head_row.appendChild(damt_cell);
				head_row.appendChild(Billamt_cell);  
				tbody.appendChild(head_row);
				var cb=document.getElementById("cbs").value;
				var ss="";
				for (i = 0; i < cb.length; i++)
			      {
					 var c = cb.charAt(i);
					if (c=='#')		{						
						ss+=r;
					}
					else
					{
						ss+=c;
					}
			      }
			 
				document.getElementById("div"+r).innerHTML=ss;
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
			function div_v_samebill(a,row)
			{
			var d_amt=a.value;
			var a_amt=document.getElementById("amt"+(row-1)).value;
			if (parseFloat(d_amt)>parseFloat(a_amt))
			{
				alert("Check Deduction Amount ?");
				a.value="";
			}else {

					var df=roundNumber(parseFloat(a_amt)-parseFloat(a.value));
					document.getElementById("amt"+row).value=roundNumber(df,2)
				}
			}
			
			function div_v(a,row)
			{
			//row=parseInt(row)+1;
			var d_amt=a.value;
			var a_amt=document.getElementById("camt"+row).value;
			if (parseFloat(d_amt)>parseFloat(a_amt))
			{
				alert("Check Deduction Amount ?");
				a.value="";
			}else {

					var df=roundNumber(parseFloat(a_amt)-parseFloat(a.value));
					document.getElementById("amt"+row).value=roundNumber(df,2)
				}
			}
			</script>
			<script type="text/javascript" src="../scripts/transcat.js"></script>
			<script type="text/javascript" src="../scripts/cellcreate.js"></script>
			<script type="text/javascript" src="../scripts/CalendarCtrl.js"></script>	
</head>
<%
	HttpSession session1 = request.getSession(false);
	String userid = (String) session1.getAttribute("UserId");
	Calendar cal = Calendar.getInstance();
	Controller obj = new Controller();
	Controller obj1 = new Controller();
	Connection con=null;
	Connection con1=null;
	if (userid == null) {

		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}
	con=obj.con();
	con1=obj1.con();
	
	obj1.createStatement(con1);  
	String row = "1";
	String erow=obj.setValue("erow",request);
	String desc_Sno = obj.setValue("desc", request);
	String pyear = obj.setValue("pyear", request);
	String pmonth = obj.setValue("pmonth", request);
	String sch = obj.setValue("sch_sno", request);
	String MAIN_ITEM_SNO = obj.setValue("MAIN_ITEM_SNO", request);
	String SUB_ITEM_SNO = obj.setValue("SUB_ITEM_SNO", request);
	String GROUP_SNO = obj.setValue("GROUP_SNO", request);
	String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING",
			"OFFICE_ID",
			"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
					+ userid + "')");
	if (Office_id.equals(""))
		Office_id = "0";
	String Office_Name = obj.getValue("com_mst_all_offices_view","OFFICE_NAME", "where OFFICE_ID=" + Office_id + "  ");
	String desc = obj.getValue("PMS_AME_MST_MAIN_ITEM","MAIN_ITEM_DESC", "where MAIN_ITEM_SNO=" + desc_Sno + "  ");
	String sch_name = obj.getValue("PMS_SCH_MASTER", "SCH_NAME","where SCH_SNO=" + sch);
	String[] monthArr = { "Select", "January", "February", "March","April", "May", "June", "July", "August", "September",
			"October", "November", "December" };

	String PMS_WB_BILL_DEDNS = "", PMS_WB_BILL_DEDNS1 = "";
	try {
		PMS_WB_BILL_DEDNS = obj.combo_str("PMS_WB_LKP_BILL_DEDNS","BILL_DEDN_DESC", "BILL_DEDN_ID", "", "cb1", "");
		PMS_WB_BILL_DEDNS1 = obj.combo_str("PMS_WB_LKP_BILL_DEDNS","BILL_DEDN_DESC", "BILL_DEDN_ID", "", "cb#", "");
	} catch (Exception e) {
		out.println(e);
	}
	String aggr_contract = "  WHERE TENDER_SNO IN "
			+ " (SELECT TENDER_SNO FROM PMS_TENDER_SCHEME_DETAILS WHERE sch_sno="
			+ sch + " )";
	String aggrcon_combo = obj.combo_str("PMS_TENDER_AGREEMENT","AGREE_REF_NO", "AGREE_SNO", "", "cond","onchange=transaction(3,12)");
	//String aggrcon_combo="<select id='cond'><option value=0>Select Aggrement </option> <option value=1>TWAD/2011/123</option><option value=2>B</option></select>";
	String condcombo = "<select id='cb1' name='cb1' > <option value=0>Select Contractor</option><option value=1>Raja</option><option value=2>Babu</option><option value=3>Balaji</option><option value=4>Gokul</option></select>";

	String qry = " SELECT BILL_SNO, " + " BILL_NO, "
			+ "  CASE WHEN (bill_date = '-') THEN  '-' ELSE to_char( BILL_DATE,'DD/MM/YYYY') end as BILL_DATE, "
			+ " VOUCHER_NO, "
			+ "   VOUCHER_DATE, "
			+ " BILL_AMT,GROSS_AMT,DEDN_AMT,BILL_DEDN_ID, " + " PARTICULARS "
			+ " FROM PMS_AME_EXP_ITEM_BREAKUP " + " WHERE OFFICE_ID="
			+ Office_id + " and YEAR= " + pyear + " AND MONTH="
			+ pmonth + " AND PERFORM_DESC_SNO=3 "
			+ " AND MAIN_ITEM_SNO= " + MAIN_ITEM_SNO
			+ " AND SUB_ITEM_SNO=" + SUB_ITEM_SNO + " and GROUP_SNO="
			+ GROUP_SNO + " and SCH_SNO=" + sch;
	 
%>


<body  >
<form name="breakup" action="../../../../../Transactions" method="post"> 
<input type="hidden" id="pmonth" name="pmonth" value="<%=pmonth%>"   />
<input type="hidden" id="pyear" name="pyear" value="<%=pyear%>"   />
<input type="hidden" id="sch" name="sch" value="<%=sch%>"   />
<input type="hidden" id="row" name="row" value="<%=row%>"   />
<input type="hidden" id="erow" name="erow" value="<%=erow%>"   />

<input type="hidden" id="MAIN_ITEM_SNO" name="MAIN_ITEM_SNO" value="<%=MAIN_ITEM_SNO%>"   />
<input type="hidden" id="SUB_ITEM_SNO" name="SUB_ITEM_SNO" value="<%=SUB_ITEM_SNO%>"   />
 <input type="hidden" id="GROUP_SNO" name="GROUP_SNO" value="<%=GROUP_SNO%>"   />
 <input type="hidden" id="aggrcon_combo" name="aggrcon_combo" value="<%=aggrcon_combo%>"   />
 <input type="hidden" id="condcombo" name="condcombo" value="<%=condcombo%>"   />
<table width="100%" class="table" align="center" border=0 cellpadding="5" cellspacing="0">
<tr class="tdH">	
<td  align="center" colspan="5"><label>Bill Wise Break Up - <%=desc%> </label></td>
</tr>
	<tr>
		<td width="10%"><input type="radio" name="btype"
			onclick="etype_en()"> Contractor Bill <input type="radio"
			name="btype" onclick="etype_en()"> HR Bill</td>
		<td colspan="1" width="10%" id='ag'></td>
		<td colspan="1" width="7%"><label id='agvalue'></label></td>
		<td width="7%" colspan="1" align="left" id='ag1'></td>

		<td width="5%" align="left"><font size=4 color=blue>
		Amount in Actuals &nbsp;&nbsp;&nbsp;&nbsp;</font></td>

	</tr>
</table>
<table width="100%" class="table" align="center" border=1 cellpadding="5">
 <tr>  
 		<td colspan="2">   
 				<table width="100%" class="table" align="center" border="1" cellpadding="5">
 					<tbody id="td_Ed">
 					<tr>
 					<th align="center" width="10%"><label class="lbl">Bill No</label></th>
 					<th  width="10%"><label class="lbl">Bill Date<bR><font size=2>(dd/mm/yyyy)</font></label></th>
 					
 					<th align="center" width="10%" ><label class="lbl">Voucher No</label></th>
 					<th  width="10%"><label class="lbl">Voucher Date<BR><font size=2>(dd/mm/yyyy)</font></label></th>
 					
 					<th align="center" width="20%"><label class="lbl">Particulars</label></th>
 					
 					<th align="center" width="5%" ><label class="lbl">Gross Amount</label></th>
 					<th align="center" width="5%"  ><label class="lbl">Deduction Type</label></th>
 					<th align="center" width="5%"  ><label class="lbl">Deduction Amount</label></th>
 					<th align="center" width="5%"  ><label class="lbl">Net Amount</label></th>
 					
 					</tr>	 		 
 					
 					<%									String BILL_SNO="",BILL_AMT="",VOUCHER_NO="",VOUCHER_DATE="",BILL_NO="",BILL_DATE="",
 														PARTICULARS="",GROSS_AMT="",BILL_DEDN_ID="",DEDN_AMT="" ;
									 					int r = 0;
									 					try
				 		  					 			{
 														  
 														ResultSet rs_local=null;
	 		  					 						PreparedStatement ps_local = con1.prepareStatement(qry);
	 		  					 					 
	 		  					 						
	 		  					 						rs_local = ps_local.executeQuery();	 		  					 						
				 		  					 			while (rs_local.next())
				 		  					 			{
				 		  					 						r++;
				 		  					 						BILL_SNO=rs_local.getString("BILL_SNO");
				 		  					 						BILL_AMT=rs_local.getString("BILL_AMT");
				 		  					 						BILL_NO=rs_local.getString("BILL_NO");
				 		  					 						BILL_DATE=rs_local.getString("BILL_DATE");
				 		  					 						PARTICULARS=rs_local.getString("PARTICULARS");
				 		  					 						VOUCHER_NO=rs_local.getString("VOUCHER_NO");
				 		  					 						VOUCHER_DATE=rs_local.getString("VOUCHER_DATE");
				 		  					 						BILL_AMT=rs_local.getString("BILL_AMT");
				 		  					 						GROSS_AMT=rs_local.getString("GROSS_AMT");
				 		  					 						BILL_DEDN_ID=rs_local.getString("BILL_DEDN_ID");
				 		  					 						DEDN_AMT=rs_local.getString("DEDN_AMT");
				 		  					 					 	PMS_WB_BILL_DEDNS = obj1.combo_str("PMS_WB_LKP_BILL_DEDNS","BILL_DEDN_DESC", "BILL_DEDN_ID", "","cb"+r,BILL_DEDN_ID,   "");
						 		  					 			
	 		  					 					%>	 	
 					 <tr>
	 					<td align="center"  width="10%"><input type="text"  size="6" id="billno<%=r%>" name="billno<%=r%>" value="<%=BILL_NO%>"  class="" /></td>
	 					<td  width="15%"> <input type="text" id="billdate<%=r%>"  maxlength="10" size=10 name="billdate<%=r%>"  onkeypress="return calins(event,this)" value="<%=BILL_DATE%>"  class="" />
	 					<!-- <img src="../../../../../images/calendr3.gif" align="middle" onclick="showCalendarControl(document.breakup.billdate1,1);"  alt="Show Calendar"  >  --> </td>
	 					<td align="center"   width="10%" ><input type="text"    id="vouno<%=r%>" name="vouno<%=r%>" value="<%=VOUCHER_NO%>" size="6" class="" onKeyUp="isInteger(this,9,event,'vouno1')"  /></td>
	 					<td align="center" width="15%"><input type="text"  id="voudate<%=r%>" name="voudate<%=r%>" value="<%=VOUCHER_DATE%>"  class="" size="10" onkeypress="return calins(event,this)" maxlength="10" />
	 					<!-- <img src="../../../../../images/calendr3.gif" align="middle" onclick="showCalendarControl(document.breakup.voudate1,1);"  alt="Show Calendar"  > --> </td>
	 					<td align="center"   > <textarea   class="tarea"  id="part<%=r%>" name="part<%=r%>" /><%=PARTICULARS%></textarea></td>
	 					<td align="center"  width="5%"><input type="text" id="camt<%=r%>" size="6" name="camt<%=r%>"  value="<%=GROSS_AMT%>"  class="fnt" onkeyup="isInteger(this,9,event,'camt1'),digit_control('camt1',2)"/> </td>
	 					<td><%=PMS_WB_BILL_DEDNS%></td>
	 					<td align="center"  width="5%"><input type="text" id="damt<%=r%>" size="6" name="damt<%=r%>"  value="<%=DEDN_AMT%>"  class="fnt"   onkeyup="isInteger(this,9,event,'damt1'),digit_control('damt1',2)" onblur="div_v(this,<%=row%>)"/> </td>
	 					<td align="center"  width="5%"><input type="text" id="amt<%=r%>" size="6" name="amt<%=r%>"  value="<%=BILL_AMT%>"  class="fnt" onkeyup="isInteger(this,9,event,'amt1'),digit_control('amt1',2)"/> </td>
	 					<input type="hidden" id="hidden_net_amt<%=r%>" size="6" 	name="hidden_net_amt<%=r%>" value="<%=BILL_AMT%>" />
 					</tr>
 					<%
 						}
 						}catch(Exception e) {
						 		  					 			out.println(e);
						 		  					 			} 
 					%>
 					 
 					</tbody>
 				</table>
 		 </td>
 				<Td  width="5%" valign="bottom"> 
 						<input type="button" value="More Deduction Same Bill"  style="font-weight: bold;font-size: 13"  class=btn   onclick="samebill()" />
 						 <input type="button" value="Remove Bill"  style="font-weight: bold;font-size: 13"  class=btn  onclick="del()" /> 
 						 <input type="button" value="Add One More Bill"   style="font-weight: bold;font-size: 13"  class=btn  onclick="rowadd()" /> </Td>
  </tr> 
  </table>
<table width="100%" class="table" align="center" border=0 cellpadding="5">
<tr class="tdH"> 
<input type="hidden" id="hidden_tot_row" name="hidden_tot_row"	value="<%=r%>" />
		<input type="hidden" id="dataflag" name="dataflag" value="2"/> 
		<input type="hidden" id="tot_row" name="tot_row" value="1"   />
		<input type="hidden" id="type" name="type" value="5"   /> 
		<input type="hidden" id="process_code" name="process_code" value="7"   />  	
		<input type="hidden" id="etypeflag" name="etypeflag" value="0"   />
		<input type="hidden" id="cbs" name="cbs" value="<%=PMS_WB_BILL_DEDNS1%>">  	
	<td align="center" colspan="2">
					<input type="button" value="Submit" id='save' class="btn" onclick="transaction(5,7)"/>	
				 	<input type="button" value="Exit"  class="btn" onclick="parent.document.getElementById('test').src=''"/>  
	</td>
</tr>	
</table>  



</form>
</body>
</html>
