<%@page import="java.sql.ResultSet,java.sql.Connection"%><html>
<head>
<%@page import="java.util.*"%>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller"%>
<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet" media="screen" />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Actual Expenditure BreakUp</title>
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
				var hidden_tot_row=parseInt(document.getElementById("hidden_tot_row").value);
				var etypeflag= document.getElementById("etypeflag").value;			
				var head_row=cell("TR","","","row0","",2,"","","","","","","");
				var rowh=cell("TD","label","","","Sl.No",2,"fnt","","","2%","center","","");
				var bn=document.getElementById("billno"+(r-1)).value
				var bdate=document.getElementById("billdate"+(r-1)).value
				var vouno=document.getElementById("vouno"+(r-1)).value
				var voudate=document.getElementById("voudate"+(r-1)).value
				var part=document.getElementById("part"+(r-1)).value
				var camt=document.getElementById("camt"+(r-1)).value
				var Billno_cell=cell("TD","input","text","billno"+r,bn,5,"","","#readonly","","center","","");
				var div_cell=cell("TD","div","text","div"+r,"",5,"",""," ","","center","","");				   
				var Billdate_cell=cell("TD","input","text","billdate"+r,bdate,10+"' maxlength=12","","","#readonly ","","center","onkeypress","return calins(event,this)");
				//	var Vouno_cell=cell("TD","input","text","vouno"+r,vouno,5,"",""," ","","center","","");
				var Vouno_date_cell=cell("TD","input","text","voudate"+r,voudate,10,"","","#readonly","","center","onkeypress","return calins(event,this)");
				var Vouno_cell=cell("TD","input","text","vouno"+r,vouno,6,"","","#readonly","","center","","");
				var part=cell("TD","textarea","","part"+r,"",0,"tarea",part," ","20%","center","","");
				var camt_cell=cell("TD","input","text","camt"+r,camt,6,"","","#readonly","","center","","");
				var damt_cell=cell("TD","input","text","damt"+r,"",6,"","","","","center","onblur","div_v_samebill(this,"+r+","+hidden_tot_row+")");
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
					if (c=='#')		
						{						
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
				 var hidden_tot_row=(parseInt(document.getElementById("hidden_tot_row").value)+1);
				 document.getElementById("hidden_tot_row").value=hidden_tot_row;
				 var etypeflag= document.getElementById("etypeflag").value;
				 var dis="";
				if (parseInt(etypeflag)==2) dis="#disable";
				  
				var head_row=cell("TR","","","row0","",2,"","","","","","","");
				var rowh=cell("TD","label","","","Sl.No",2,"fnt","","","2%","center","","");
				var Billno_cell=cell("TD","input","text","billno"+r,"",5,"",""," ","","center","","");
				var div_cell=cell("TD","div","text","div"+r,"",5,"",""," ","","center","","");
				var Billdate_cell=cell("TD","input","text","billdate"+r,"",10,"",""," ","","center","onkeypress","return calins(event,this)");
				var Vouno_cell=cell("TD","input","text","vouno"+r,"",5,"",""," ","","center","","");
				var Vouno_date_cell=cell("TD","input","text","voudate"+r,"",10,"",""," ","","center","onkeypress","return calins(event,this)");
				var Vouno_cell=cell("TD","input","text","vouno"+r,"",6,"",""," ","","center","","");
				var part=cell("TD","textarea","","part"+r,"",0,"tarea",""," ","20%","center","","");
				var camt_cell=cell("TD","input","text","camt"+r,"",6,"","",dis,"","center","","");
				var hidden_net_amt_cell=cell("TD","input","hidden","hidden_net_amt"+hidden_tot_row,"",6,"","",dis,"","center","","");
				var damt_cell=cell("TD","input","text","damt"+r,"",6,"","",dis,"","center","onblur","div_v(this,"+r+","+hidden_tot_row+")");
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
				head_row.appendChild(hidden_net_amt_cell);
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
			function div_v_samebill(a,row,erow)
			{
			var d_amt=a.value;
			var a_amt=document.getElementById("amt"+(row-1)).value;
			if (parseFloat(d_amt)>parseFloat(a_amt))
			{
				alert("Check Deduction Amount ?");
				a.value="";
			}else {

					var df=roundNumber(parseFloat(a_amt)-parseFloat(a.value));
					document.getElementById("amt"+row).value=roundNumber(df,2);
					document.getElementById("hidden_net_amt"+erow).value=roundNumber(df,2);
				}
			}
			
			function div_v(a,row,erow)
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
					document.getElementById("hidden_net_amt"+erow).value=roundNumber(df,2);
				}
			}
			</script>
			<script type="text/javascript" src="../scripts/transcat.js"></script>
			<script type="text/javascript" src="../scripts/cellcreate.js"></script>
			<script type="text/javascript" src="../scripts/CalendarCtrl.js"></script>
</head>
<%  
int frow=0;
			HttpSession session1 = request.getSession(false);
			String	userid = (String) session1.getAttribute("UserId");
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			
			Controller obj=new Controller();
			Controller obj2=new Controller();
			Connection con=obj.con();
			Connection con1=obj2.con();
			obj2.createStatement(con1); 
			obj.createStatement(con);  
			if (userid == null) {

				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			String row="1";
			String erow=obj.setValue("erow",request);
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
 			
 			String PMS_WB_BILL_DEDNS="",PMS_WB_BILL_DEDNS1="";
 			try {
 				PMS_WB_BILL_DEDNS1="";
 		 	 // PMS_WB_BILL_DEDNS=obj.combo_str("PMS_WB_LKP_BILL_DEDNS","BILL_DEDN_DESC","BILL_DEDN_ID","","cb1","");
 			  //PMS_WB_BILL_DEDNS1=obj.combo_str("PMS_WB_LKP_BILL_DEDNS","BILL_DEDN_DESC","BILL_DEDN_ID","","cb#","");
 			}catch(Exception e)
 			{
 				 
 				
 			}
 			String aggr_contract="  WHERE TENDER_SNO IN " + 
 			" (SELECT TENDER_SNO FROM PMS_TENDER_SCHEME_DETAILS WHERE sch_sno="+sch+" )";
 			// String aggrcon_combo=obj.combo_str("PMS_TENDER_AGREEMENT","AGREE_REF_NO","AGREE_SNO","","cond","onchange=transaction(3,12)");
 			String aggrcon_combo="<select id='cond'><option value=0>Select Aggrement </option> <option value=1>TWAD/2011/123</option><option value=2>B</option></select>";
 			String condcombo="<select id='cb1' name='cb1' > <option value=0>Select Contractor</option><option value=1>Raja</option><option value=2>Babu</option><option value=3>Balaji</option><option value=4>Gokul</option></select>";
 			
 			// Mapping New Scheme 
			
 			
 			int count=obj.getCount("PMS_AME_MST_SCH_MAPPING"," where SCH_SNO="+sch +" and OFFICE_ID="+Office_id);
 			Hashtable ht=new Hashtable();  
 			if (count==0)
 			{ 
 				
 				int max=0;
 				 try
 				 {
 					 max=obj.getMax("PMS_AME_MST_SCH_MAPPING","SCH_MAPPING_SNO","");
 				 }catch(Exception e)
 				 {
 					 
 					 max=obj.getMax("PMS_AME_MST_SCH_MAPPING","SCH_MAPPING_SNO","");
 				 }
 				 String pr_id=obj.getValue("PMS_SCH_MASTER","PROJECT_ID"," where SCH_SNO="+sch);    
 			 
 				 
 				 try  
				 {
 				 	ht.put("SCH_MAPPING_SNO" ,max);	  
 				 	ht.put("SCH_SNO" ,sch);	
 				 	ht.put("OFFICE_ID" ,Office_id);  
 				 	ht.put("UPDATED_BY_USER_ID" , "'"+userid+"'");
 				 	ht.put("UPDATED_TIME" ,"clock_timestamp()");
 				 	ht.put("TWAD_PROJECT_ID" ,"0");  
 				 	ht.put("FAS_PROJECT_ID" ,pr_id);
 				 	int urow=obj.recordSave(ht,"PMS_AME_MST_SCH_MAPPING",con);
				 }catch(Exception e)
 				 { }  	
 			}
 			
 			String ame_project_id=obj.getValue("PMS_AME_MST_SCH_MAPPING","FAS_PROJECT_ID"," where SCH_SNO="+sch +" and OFFICE_ID="+Office_id);
 			String cont=obj.combo_str("PMS_CONT_REQUEST_REGN","CONTRACTOR_NAME","CONTRACTOR_ID"," where OFFICE_ID="+Office_id,"cb1","","");
 			
			String pid=ame_project_id;//obj.getValue("PMS_MST_PROJECTS_VIEW","PROJECT_ID"," where SCH_SNO="+sch);
			
			String ACCOUNT_HEAD_CODE=obj.getValue("PMS_AME_MST_ITEM_ACC_CODE","ACCOUNT_HEAD_CODE","  where MAIN_ITEM_SNO="+MAIN_ITEM_SNO+" and SUB_ITEM_SNO="+SUB_ITEM_SNO);
			 //String qry="SELECT * FROM FAS_PAYMENT_TRANSACTION WHERE ACCOUNTING_FOR_OFFICE_ID="+Office_id+" AND CASHBOOK_YEAR ="+pyear+"  AND CASHBOOK_MONTH="+pmonth+" AND sub_ledger_type_code=10 and ACCOUNT_HEAD_CODE="+ACCOUNT_HEAD_CODE+" and SUB_LEDGER_CODE="+pid;
			// old 	 
			 String cond=" where ACCOUNTING_FOR_OFFICE_ID="+Office_id+"  and SUB_LEDGER_CODE="+pid+" and CASHBOOK_YEAR="+pyear 
	 			+" and CASHBOOK_MONTH="+pmonth+" and SUB_LEDGER_TYPE_CODE= 10 " 
	 			+" and ACCOUNT_HEAD_CODE in ("+ACCOUNT_HEAD_CODE+") and VOUCHER_NO in ( " 
 			+" select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_FOR_OFFICE_ID="+Office_id
 			+" and CASHBOOK_YEAR="+pyear+" and CASHBOOK_MONTH="+pmonth+// and JOURNAL_TYPE_CODE =11 "  
   			" and JOURNAL_STATUS='L')";
			
			 String cond_payment="   ACCOUNTING_FOR_OFFICE_ID="+Office_id+"  and SUB_LEDGER_CODE="+pid+" and CASHBOOK_YEAR="+pyear 
	 			+" and CASHBOOK_MONTH="+pmonth+" and SUB_LEDGER_TYPE_CODE= 10 " 
	 			+" and ACCOUNT_HEAD_CODE in ("+ACCOUNT_HEAD_CODE+") and VOUCHER_NO in ( " 
			+" select VOUCHER_NO from FAS_PAYMENT_MASTER where ACCOUNTING_FOR_OFFICE_ID="+Office_id
			+" and CASHBOOK_YEAR="+pyear+" and CASHBOOK_MONTH="+pmonth+// and JOURNAL_TYPE_CODE =11 "  
			" and PAYMENT_STATUS='L')";
			
			
			
			String qry1="";
			String qry2="";
			String qry3="";
			if (!ACCOUNT_HEAD_CODE.equals("222102"))
			{
		
			}
			else
			{
				 
			}
			qry1="SELECT * FROM FAS_PAYMENT_TRANSACTION WHERE "+cond_payment+"  and ACCOUNT_HEAD_CODE in ("+ACCOUNT_HEAD_CODE+" ) and SUB_LEDGER_CODE="+ame_project_id;
			qry2="SELECT * FROM FAS_JOURNAL_TRANSACTION  "+cond; // WHERE ACCOUNTING_FOR_OFFICE_ID="+Office_id+" AND CASHBOOK_YEAR ="+pyear+"  AND CASHBOOK_MONTH="+pmonth+" AND sub_ledger_type_code=10 and ACCOUNT_HEAD_CODE in ("+ACCOUNT_HEAD_CODE+" ) and SUB_LEDGER_CODE="+pid;
			String ac_st=ACCOUNT_HEAD_CODE.equalsIgnoreCase("0")?"":"("+ACCOUNT_HEAD_CODE+")";
 			qry3="SELECT * FROM fas_receipt_transaction   WHERE ACCOUNTING_FOR_OFFICE_ID="+Office_id+" AND CASHBOOK_YEAR ="+pyear+"  AND CASHBOOK_MONTH="+pmonth+"   and ACCOUNT_HEAD_CODE in ("+ACCOUNT_HEAD_CODE+" ) and SUB_LEDGER_CODE="+ame_project_id;
			%>
				<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/> 
<body  > 
	<form name="breakup" action="../../../../../Transactions" method="post">
		<input type="hidden" id="pmonth" name="pmonth" value="<%=pmonth%>" />
		<input type="hidden" id="pyear" name="pyear" value="<%=pyear%>" /> 
		<input type="hidden" id="sch" name="sch" value="<%=sch%>" /> 
		<input type="hidden" id="row" name="row" value="<%=row%>" /> 
		<input type="hidden" id="erow" name="erow" value="<%=erow%>" /> 
		<input type="hidden" id="MAIN_ITEM_SNO" name="MAIN_ITEM_SNO" value="<%=MAIN_ITEM_SNO%>" /> 
		<input type="hidden" id="SUB_ITEM_SNO"	name="SUB_ITEM_SNO" value="<%=SUB_ITEM_SNO%>" />
		<input type="hidden" id="GROUP_SNO" name="GROUP_SNO" value="<%=GROUP_SNO%>" />
		<input type="hidden" id="aggrcon_combo" name="aggrcon_combo" value="<%=aggrcon_combo%>" />
		<input type="hidden" id="condcombo"	name="condcombo" value="<%=cont%>" />
		<table width="100%"  style="border-collapse: collapse;border-color: skyblue"  align="center" border=1 cellpadding="5" cellspacing="0">
			<tr class="tdH">
					<th align="center" colspan="5"><label>Bill Wise Break Up - <%=desc%>   <%=ac_st%>   </label></th>
			</tr>
			<tr>
					<td width="10%"><input type="radio" name="btype" onclick="etype_en()"> Contractor Bill 
						<input type="radio" 	name="btype" onclick="etype_en()"> HR Bill</td>
					<td colspan="1" width="10%" id='ag'></td>
					<td colspan="1" width="10%"><label id='agvalue'></label></td>
					<td width="20%" colspan="2" align="left" id='ag1'></td>
				 
			</tr>
			</table>
			<table width="100%"  style="border-collapse: collapse;"  align="center" border=1 cellpadding="0">
			<tr>
			<td colspan="2">
				<table width="100%"  style="border-collapse: collapse;" align="center" border="1"	cellpadding="0">
				<tbody id="td_Ed"> 
					<tr>
						<th  width="2%"><font style="font-size:12;">Sl.No</font></th>
						<th align="center" width="5%"><label class="fnt"><font style="font-size:12;">Bill	No</font></label></th>
						<th width="5%"><label class="fnt"><font style="font-size:12;">Bill Date</font><bR><font size=2>(dd/mm/yyyy)</font></label></th>
						<th  width="5%"><font style="font-size:12;">Transaction</font></th>
						<th align="center" width="5%"><label class="fnt"><font style="font-size:12;">Voucher No</font></label></th>
						<th width="5%"><label class="fnt"><font style="font-size:12;">Payment Date</font><BR><font size=2>(dd/mm/yyyy)</font></label></th>
						<th align="center" width="15%"><label class="fnt"><font style="font-size:12;">Particulars</font></label></th>
						<!--  <th align="center" width="5%"><label class="fnt">Gross Amount</label></th> --> 
						<!--  <th align="center" width="5%"><label class="fnt">Deduction Type</label></th>  -->
						<!--  <th align="center" width="5%"><label class="fnt">Deduction Amount</label></th> -->
						<th align="center" width="5%"><label class="fnt"><font style="font-size:12;">Amount&nbsp;(Rs.)</font></label></th>
						<th align="center" width="5%"><label class="fnt"><font style="font-size:12;">CR / DR</font></label></th>
					</tr>
				 <% 
					 String vno="",vdate="",amt="",remarks="",billno="",billdate="",crdr="";
			 		   double net_amt=0.0;
			 		  ResultSet rs_loc=obj.getRS(qry1);  
			 		   while (rs_loc.next()) 
			 		   {
				 			frow++;
				 			vno=rs_loc.getString("VOUCHER_NO");
				 			vdate="";
				 			amt=rs_loc.getString("AMOUNT");		 			
				 			
				 			crdr=rs_loc.getString("CR_DR_INDICATOR");
				 			if (crdr.equalsIgnoreCase("CR"))
				 			net_amt-=Double.parseDouble(amt);
				 			else
				 			net_amt+=Double.parseDouble(amt);  
				 			remarks=obj.isNull(rs_loc.getString("PARTICULARS"),2);
				 			billno=obj.isNull(rs_loc.getString("BILL_NO"),2);
				 			obj.createStatement(con);
							 try
							 {  
								 billdate=obj.isNull(rs_loc.getDate("BILL_DATE").toString().split("\\ ")[0],2);					 		
				 			}catch(Exception e) 
				 			{
				 				 				
				 			}
				 			try
				 			{ 
				 			vdate=obj.getDate("FAS_PAYMENT_MASTER","PAYMENT_DATE"," where VOUCHER_NO="+vno+" and ACCOUNTING_FOR_OFFICE_ID="+Office_id+" AND CASHBOOK_YEAR ="+pyear+"  AND CASHBOOK_MONTH="+pmonth+"");
				 			}catch(Exception e) 
				 			{  
				 				 	 				
				 			}
				%> 
						<tr>  
							<td  width="2%"><%=frow%></td> 
							<td align="center" width="5%">
								<input type="text" size="6"	id="billno<%=frow%>" name="billno<%=frow%>" value="<%=billno%>" class="" /></td>
								<td width="5%">
									<input type="text" id="billdate<%=frow%>" maxlength="10" size=10 name="billdate<%=frow%>" onkeypress="return calins(event,this)" value="<%=billdate%>" class="" /> <!-- <img src="../../../../../images/calendr3.gif" align="middle" onclick="showCalendarControl(document.breakup.billdate1,1);"  alt="Show Calendar"  >  -->
								</td>
								<td><%="Payment"%></td>
								<td align="center" width="5%">
									<input type="text" id="vouno<%=frow%>" name="vouno<%=frow%>" value="<%=vno%>" size="6" class="" onKeyUp="isInteger(this,9,event,'vouno<%=frow%>')"  />
								</td>
								<td align="center" width="5%">
									<input type="text" id="voudate<%=frow%>" name="voudate<%=frow%>" value="<%=vdate%>" class="" size="10"	onkeypress="return calins(event,this)" maxlength="10" /> <!-- <img src="../../../../../images/calendr3.gif" align="middle" onclick="showCalendarControl(document.breakup.voudate1,1);"  alt="Show Calendar"  > -->
								</td>
								<td align="center" width=15%>
									<textarea class="tarea" id="part<%=frow%>" name="part<%=frow%>" /><%=remarks %></textarea>
								
					 			<input type="hidden" id="camt<%=frow%>" size="6" name="camt<%=frow%>" value="0" class="" onkeyup="isInteger(this,9,event,'camt<%=frow%>'),digit_control('camt<%=frow%>',2)" />
					 			<input type="hidden" id="cb<%=frow%>" name="cb<%=frow%>" value="0">  
					 			<input type="hidden" id="damt<%=frow%>" size="6" name="damt<%=frow%>" value="0" class="" onkeyup="isInteger(this,9,event,'damt<%=frow%>'),digit_control('damt<%=frow%>',2)" onblur="div_v(this,<%=row%>,1)" /> 
								</td><td align="center" width="5%">
									<input type="text" id="amt<%=frow%>" size="6" name="amt<%=frow%>" value="<%=amt%>" class="" style="text-align: right;" readonly="readonly" onkeyup="isInteger(this,9,event,'amt<%=frow%>'),digit_control('amt<%=frow%>',2)" />
								</td>
								<td><input type="text" value='<%=crdr%>' id="crdr<%=frow%>" name="crdr<%=frow%>"  size="3"	>
								<input type="hidden" id="hidden_net_amt<%=frow%>" size="6" name="hidden_net_amt<%=frow%>" value="<%=amt%>" /></td>									
						</tr>
			<% } 
			 		  rs_loc=null;
			 		   ResultSet rs_loc_pay=obj.getRS(qry2);
			 		   while (rs_loc_pay.next()) 
			 		   {
				 			frow++;
				 			vno=rs_loc_pay.getString("VOUCHER_NO");
				 			vdate="";
				 			amt=rs_loc_pay.getString("AMOUNT");
				 			
				 			crdr=rs_loc_pay.getString("CR_DR_INDICATOR");
				 			if (crdr.equalsIgnoreCase("CR"))
					 		net_amt-=Double.parseDouble(amt);
					 		else
					 		net_amt+=Double.parseDouble(amt);    
				 			
				 			remarks=obj.isNull(rs_loc_pay.getString("PARTICULARS"),2);
				 			billno=obj.isNull(rs_loc_pay.getString("BILL_NO"),2);		 			
							 try  
							 {  
								billdate=obj.isNull(rs_loc_pay.getDate("BILL_DATE").toString().split("\\ ")[0],2);					
				 			 }catch(Exception e) 
				 			 {
				 				 
				 			 }
				 			try  
							{  							
				 			vdate=obj.getDate("FAS_JOURNAL_MASTER","VOUCHER_DATE"," where VOUCHER_NO="+vno+" and ACCOUNTING_FOR_OFFICE_ID="+Office_id+" AND CASHBOOK_YEAR ="+pyear+"  AND CASHBOOK_MONTH="+pmonth+"");
							}catch(Exception e) 
							{
								 
							}
				%> 
						<tr>  
							<td  width="2%"><%=frow%></td> 
							<td align="center" width="5%">
								<input type="text" size="6"	id="billno<%=frow%>" name="billno<%=frow%>" value="<%=billno%>" class="" /></td>
								<td width="5%">
									<input type="text" id="billdate<%=frow%>" maxlength="10" size=10 name="billdate<%=frow%>" onkeypress="return calins(event,this)" value="<%=billdate%>" class="" /> <!-- <img src="../../../../../images/calendr3.gif" align="middle" onclick="showCalendarControl(document.breakup.billdate1,1);"  alt="Show Calendar"  >  -->
								</td>
								<td><%="Journal"%></td>
								<td align="center" width="5%">
									<input type="text" id="vouno<%=frow%>" name="vouno<%=frow%>" value="<%=vno%>" size="6" class="" onKeyUp="isInteger(this,9,event,'vouno<%=frow%>')"  />
								</td>
								<td align="center" width="5%">
									<input type="text" id="voudate<%=frow%>" name="voudate<%=frow%>" value="<%=vdate%>" class="" size="10"	onkeypress="return calins(event,this)" maxlength="10" /> <!-- <img src="../../../../../images/calendr3.gif" align="middle" onclick="showCalendarControl(document.breakup.voudate1,1);"  alt="Show Calendar"  > -->
								</td>
								<td align="center" width="5%">
									<textarea class="tarea" id="part<%=frow%>" name="part<%=frow%>" /><%=remarks %></textarea>
								
					 			<input type="hidden" id="camt<%=frow%>" size="6" name="camt<%=frow%>" value="0" class="" onkeyup="isInteger(this,9,event,'camt<%=frow%>'),digit_control('camt<%=frow%>',2)" />
					 			<input type="hidden" id="cb<%=frow%>" name="cb<%=frow%>" value="0">  
					 			<input type="hidden" id="damt<%=frow%>" size="6" name="damt<%=frow%>" value="0" class="" onkeyup="isInteger(this,9,event,'damt<%=frow%>'),digit_control('damt<%=frow%>',2)" onblur="div_v(this,<%=row%>,1)" /> 
								</td><td align="center" width="5%">
									<input type="text" id="amt<%=frow%>" size="6" name="amt<%=frow%>" value="<%=amt%>" class="" readonly="readonly"  style="text-align: right;" onkeyup="isInteger(this,9,event,'amt<%=frow%>'),digit_control('amt<%=frow%>',2)" />
								</td>
								<td><input type="text" value='<%=crdr%>' id="crdr<%=frow%>" name="crdr<%=frow%>"  size="3">
								<input type="hidden" id="hidden_net_amt<%=frow%>" size="6" name="hidden_net_amt<%=frow%>" value="<%=amt%>" /></td>									
						</tr>
				<% } %> 
			
			 <%			
			 			rs_loc_pay=null;
			 			ResultSet rs_loc_rec=null;
			 			rs_loc_rec=obj2.getRS(qry3);
			 			while (rs_loc_rec.next()) 
			 		   {
				 			frow++;				 			
				 			vdate="";
				 			amt=rs_loc_rec.getString("AMOUNT");
				 			crdr="CR";
				 			net_amt-=Double.parseDouble(amt);
				 			System.out.println(net_amt);
				 			vno=rs_loc_rec.getString("RECEIPT_NO");
				 			remarks=obj.isNull(rs_loc_rec.getString("PARTICULARS"),2);
				 		 
				 			try
				 			{    
				 				billno=obj.isNull(rs_loc_rec.getString("BILL_NO"),2);
						 		//	billdate=obj.isNull(rs_loc_rec.getString("BILL_DATE"),2);
						 			billdate=obj.isNull(rs_loc_rec.getDate("BILL_DATE").toString().split("\\ ")[0],2);

				 			}catch(Exception e)
				 			{
				 				 
				 				
				 			}
							 try
							 { 
					 			vdate=obj.getDate("FAS_RECEIPT_MASTER","RECEIPT_DATE"," where RECEIPT_NO="+vno+" and ACCOUNTING_FOR_OFFICE_ID="+Office_id+" AND CASHBOOK_YEAR ="+pyear+"  AND CASHBOOK_MONTH="+pmonth+"");
					 			
				 			}catch(Exception e) { out.println(e); }
				  		// PMS_WB_BILL_DEDNS1=obj2.combo_str("PMS_WB_LKP_BILL_DEDNS","BILL_DEDN_DESC","BILL_DEDN_ID","","cb"+frow,"");
				%> 
						<tr>  
							<td  width="2%"><%=frow%></td> 
							<td align="center" width="5%">
								<input type="text" size="6"	id="billno<%=frow%>" name="billno<%=frow%>" value="<%=billno%>" class="" /></td>
								<td width="5%">
									<input type="text" id="billdate<%=frow%>" maxlength="10" size=10 name="billdate<%=frow%>" onkeypress="return calins(event,this)" value="<%=billdate%>" class="" /> <!-- <img src="../../../../../images/calendr3.gif" align="middle" onclick="showCalendarControl(document.breakup.billdate1,1);"  alt="Show Calendar"  >  -->
								</td>
								<td>
									<%="Receipt"%>
								</td>
								<td align="center" width="5%">
									<input type="text" id="vouno<%=frow%>" name="vouno<%=frow%>" value="<%=vno%>" size="6" class="" onKeyUp="isInteger(this,9,event,'vouno<%=frow%>')"  />
								</td>
								<td align="center" width="5%">
									<input type="text" id="voudate<%=frow%>" name="voudate<%=frow%>" value="<%=vdate%>" class="" size="10"	onkeypress="return calins(event,this)" maxlength="10" /> <!-- <img src="../../../../../images/calendr3.gif" align="middle" onclick="showCalendarControl(document.breakup.voudate1,1);"  alt="Show Calendar"  > -->
								</td>
								<td align="center" width="10%">
									<textarea class="tarea" id="part<%=frow%>" name="part<%=frow%>" /><%=remarks %></textarea>								
					 				<input type="hidden" id="camt<%=frow%>" size="6" name="camt<%=frow%>" value="0" class="" onkeyup="isInteger(this,9,event,'camt<%=frow%>'),digit_control('camt<%=frow%>',2)" />
					 				<input type="hidden" id="cb<%=frow%>" name="cb<%=frow%>" value="0">  
					 				<input type="hidden" id="damt<%=frow%>" size="6" name="damt<%=frow%>" value="0" class="" onkeyup="isInteger(this,9,event,'damt<%=frow%>'),digit_control('damt<%=frow%>',2)" onblur="div_v(this,<%=row%>,1)" /> 
								</td>
								<td align="center" width="5%">
									<input type="text" id="amt<%=frow%>" size="6" name="amt<%=frow%>" value="<%=amt%>" class="" style="text-align: right;" readonly="readonly" onkeyup="isInteger(this,9,event,'amt<%=frow%>'),digit_control('amt<%=frow%>',2)" />
								</td>
								<td>
									<input type="text" value='<%=crdr%>' id="crdr<%=frow%>" name="crdr<%=frow%>"  size="3">
								</td>
								
								<input type="hidden" id="hidden_net_amt<%=frow%>" size="6" name="hidden_net_amt<%=frow%>" value="<%=amt%>" />									
						</tr>
			<% } %>  
			  
			<tr>  
				<td colspan="6" align="right"><font style="font-size:12;">Net Total</font></td> 
				<td width=15%><font color='blue' style="font-size:12;">(Total DR - Total CR)</font> </td>
				<td align="right"><input type="text" value='<%=Math.round(net_amt)%>' id="final_amount" name="final_amount" style="text-align: right;" size="6" readonly="readonly"></td>
			</tr>
			</tbody>
		</table>
		</td>
		<Td width="5%" valign="bottom">
			<input type="hidden" value="More Deduction Same Bill" style="font-weight: bold; font-size: 13" class="btn" onclick="samebill()" /> 
			<input type="hidden" value="Remove Bill" style="font-weight: bold; font-size: 13" class=btn onclick="del()" />
			<input type="hidden" value="Add One More Bill" style="font-weight: bold; font-size: 13" class=btn onclick="rowadd()" />
		</Td>
	</tr>
</table>
<table width="100%" class="table" align="center" border=0 cellpadding="5">
	<%
			if (frow==0) 
			{
			%>
			<tr>  
				<td colspan="8" align="center"><font size="4" color=red>No Bills Found for this Account Head Code </font> </td>
			</tr>
		<%}  else { %>
	<tr class="tdH">		
		<td align="center" colspan="2">
			<input type="button" value="Save" id='save' class="btn" onclick="transaction(5,7);" /> 
			<input type="hidden" value="EXIT" class="btn"onclick="parent.document.getElementById('test').src=''" />
		</td>
	</tr><%} %>
	
</table>

		<input type="hidden" id="dataflag" name="dataflag" value="1" />
		<input type="hidden" id="tot_row" name="tot_row" value="<%=frow%>" />
		<input type="hidden" id="hidden_tot_row" name="hidden_tot_row" value="<%=frow%>" />
		<input type="hidden" id="type" name="type" value="5" />
		<input type="hidden" id="process_code" name="process_code" value="7" />
		<input type="hidden" id="etypeflag" name="etypeflag" value="0" />
		<input type="hidden" id="cbs" name="cbs" value="<%=PMS_WB_BILL_DEDNS1%>">
  
</form>
</body>
</html>
