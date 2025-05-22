 <%@ page import="java.sql.*,java.util.ResourceBundle"%>
 <%@ page session="false" contentType="text/html;charset=windows-1252"%>
 <%@ page import="java.util.Calendar" %>
 <%@ page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
 <%@ page import="Servlets.Security.classes.UserProfile"%>
 <html>
 <head>
		<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
 		<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
 		<jsp:useBean id="bean" class="Servlets.PMS.PMS1.DCB.reports.count_bean" scope="page"></jsp:useBean>
		<style>
 			fr
 			{
				scrollbar-face-color:#9898D0;
				scrollbar-arrow-color:indigo;
				scrollbar-track-color:#C0DCC0;
				scrollbar-shadow-color:'';
				scrollbar-highlight-color:'';
				scrollbar-3dlight-color:'';
				scrollbar-darkshadow-Color:'';
			  }
		</style>
 	<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
 	<title>Bill Demand | TWAD Nest - Phase II</title>
 	 <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
 	 <script type="text/javascript" src="../scripts/Bill_Demand.js"></script>
     <script type="text/javascript" src="../scripts/Bill_Demand_Report.js"></script>
     <script type="text/javascript"  src="../scripts/RIW.js"></script>
     <script type="text/javascript" src="../scripts/msg.js"></script>
       <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
 	 <script type="text/javascript" src="../scripts/cellcreate.js"></script>
 	 <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
 	 <script type="text/javascript" src="../scripts/Office_Shift_new.js"></script>
 	 <script type="text/javascript">
 	 	function ch_wind()
 	 	{
 	 		var c=window.open("Ben_type_wise_bill.jsp","500","500");
 	 	}
 		function clr()
 		{ 
	 		 document.getElementById('test').src='';
 		}
 		 
 		function other_charges()
 		{ 
 			document.getElementById("test").src = "other_charges_collection.jsp";
		}
		
 		function date_ch()
 		{
  			var datefrom=document.getElementById("datefrom").value;
  			var dateto=document.getElementById("dateto").value;
  			var s=new String(datefrom).split('/');
  			var s1=new String(dateto).split('/');
  			var cm=document.getElementById("cmonth").value;

			if (datefrom!="" && dateto!="") 
			{
  			
						if ( (parseInt(s[1]) >  parseInt(cm)) || (parseInt(s1[1]) >  parseInt(cm)))
			  			{
			   				demand_ins();  
			   				flash();  	
			  			}
			  			else
			   			{ 
			   				demand_ins();  
			   				flash();
			   			}
			}
			else 
			{
				alert("Select Bill Period From * ")
			}
 		}
 		
 		function date_ch2()
 		{
  			var datefrom=document.getElementById("datefrom").value;
  			var dateto=document.getElementById("dateto").value;
  			var s=new String(datefrom).split('/');
  			var s1=new String(dateto).split('/');
  			var cm=document.getElementById("cmonth").value;
  			
			if ( (parseInt(s[1]) >  parseInt(cm)) || (parseInt(s1[1]) >  parseInt(cm)))
  			{
   				demand_ins2();  
   				flash();  	
  			}  
  			else
   			{ 
   				demand_ins2();  
   				flash();
   			}
 		}
 	</script>
 	 <script type="text/javascript">
 	 function view()
 	 {
		 var maxsno=document.getElementById("maxsno").value;
	 	document.location.href="Bill_Demand_Report.jsp?maxsno="+maxsno
 	 }
 	</script>
 </head>
   <%
     	    int count=0,count1=0,total_meter_location=0,total_meter_location_from_pr=0,pumping_count=0,Bill_count=0,ben_count=0,pumping_count_entry=0;;
            String Bill_month="0",Ben_type="0",Bill_year="0", color_="",count_msg="",msg2="",wcfreeze="";
            String tbfreeze="",msg="",dis="",ob_missing_count="";
            String ben="";
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			int journal_verification_code=0;
			String userid="0",Office_id="",Office_Name="",journal_ver_msg="";
			String Date_dis=day+"/"+month+"/"+year;
			String journal_verificaiton="";
			int other_charges_count=0;
			Controller obj=new Controller();
			Connection con=null;
			String new_cond=Controller.new_cond;
			String meter_status=Controller.meter_status;
			String meter_status2=Controller.meter_status2;
			String ben_type="";
			try
			{ 
				con=obj.con();
				obj.createStatement(con);
				HttpSession session=request.getSession(false);
				userid=(String)session.getAttribute("UserId");
 				if(userid==null)
				{
				  	response.sendRedirect(request.getContextPath()+"/index.jsp");
				}
 				
 		//  Office_id=obj.getValu("HRM_EMP_CURRENT_POSTING", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp()  THEN OLD_OFFICE_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

 					Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

			//	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
				if (Office_id.equals("")) Office_id="0";
		 		Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
			   	Bill_month=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
				Bill_year=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
 				Bill_count=obj.getCount("PMS_DCB_TRN_BILL_DMD","where BILL_MONTH="+Bill_month+" and BILL_YEAR="+Bill_year+" and OFFICE_ID="+Office_id);
 				ben_count=obj.getCount("PMS_DCB_MST_BENEFICIARY","where "+new_cond+" OFFICE_ID="+Office_id +"  and BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY_METRE where METER_STATUS='L' and    office_id="+Office_id+" ) and BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_TRN_MONTHLY_PR where month="+Bill_month+" and year="+Bill_year+"  and office_id="+Office_id+" )");
				Statement stmt=null;
				String sel_qry="  SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,ACCOUNTING_UNIT_OFFICE_ID FROM FAS_MST_ACCT_UNITS WHERE ACCOUNTING_UNIT_OFFICE_ID ="+Office_id;
    	       	stmt=con.createStatement();
    	     	ResultSet  rs_sch_type=stmt.executeQuery(sel_qry);
    	      	String ACCOUNTING_UNIT_ID="",ACCOUNTING_UNIT_OFFICE_ID="";
    	    	if (rs_sch_type.next())
			 		{
    	    			ACCOUNTING_UNIT_ID=obj.isNull(rs_sch_type.getString(1),1);
    	    			ACCOUNTING_UNIT_OFFICE_ID=obj.isNull(rs_sch_type.getString(3),1);
	  			    } 
			    try
			 	{ 
			   		tbfreeze=obj.isNull(obj.getValue("FAS_TRIAL_BALANCE_STATUS","TB_STATUS","where ACCOUNTING_UNIT_ID="+ACCOUNTING_UNIT_ID+" and CASHBOOK_YEAR="+Bill_year+" and  CASHBOOK_MONTH="+Bill_month),2);
			   		
			 	    if (tbfreeze.trim().equalsIgnoreCase("") ) tbfreeze="CR";
			 	}catch(Exception e1)
				{
		 			 tbfreeze="N";
			 	}
			 }catch(Exception e) 
			 {
				userid="0";  
				response.sendRedirect(request.getContextPath()+"/index.jsp");
			 }
			 total_meter_location_from_pr = obj.getCount("PMS_DCB_TRN_MONTHLY_PR"," where    office_id="+Office_id+" and month="+Bill_month+" and year="+Bill_year+" and  BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where  "+new_cond+" office_id="+Office_id+" ) and    METRE_SNO in (select METRE_SNO from PMS_DCB_MST_BENEFICIARY_METRE where METER_STATUS='L' and    office_id="+Office_id+" )");
			 total_meter_location = obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE"," where "+meter_status+" BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where  "+new_cond+" office_id="+Office_id+" ) and   office_id="+Office_id);	
	 		 pumping_count = obj.getCount("PMS_DCB_TRN_MONTHLY_PR"," where BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where  "+new_cond+" office_id="+Office_id+" ) and    METRE_SNO in (select METRE_SNO from PMS_DCB_MST_BENEFICIARY_METRE where METER_STATUS='L' and    office_id="+Office_id+" ) and    office_id="+Office_id+" and month="+Bill_month+" and year="+Bill_year+" and PROCESS_FLAG='FR'");
	 		 pumping_count_entry = obj.getCount("PMS_DCB_TRN_MONTHLY_PR"," where BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where  "+new_cond+" office_id="+Office_id+" ) and    METRE_SNO in (select METRE_SNO from PMS_DCB_MST_BENEFICIARY_METRE where METER_STATUS='L' and    office_id="+Office_id+" ) and     office_id="+Office_id+" and month="+Bill_month+" and year="+Bill_year+"  ");
	 		 wcfreeze=obj.isNull(obj.getValue("PMS_DCB_FREEZE_STATUS","WC_FREEZED","where OFFICE_ID="+Office_id+" and CASHBOOK_YEAR="+Bill_year+" and  CASHBOOK_MONTH="+Bill_month),1);
	 		 other_charges_count=obj.getCount("PMS_DCB_OTHER_CHARGES","where office_id='"+Office_id+"' and CASHBOOK_MONTH="+Bill_month+" and CASHBOOK_YEAR="+Bill_year+" ");;
	 		// out.println("other_charges_count"+other_charges_count);
	 		 ben_type=obj.combo_str("PMS_DCB_BEN_TYPE","BEN_TYPE_DESC","BEN_TYPE_ID"," where BEN_TYPE_ID in (SELECT BENEFICIARY_TYPE_ID FROM PMS_DCB_MST_BENEFICIARY_METRE WHERE office_id="+Office_id+")","btype",""," onchange='ben_combo_load()'");
	 		 color_="";   
			 color_=(total_meter_location_from_pr==pumping_count) ? "green":"red";
			 int check_result=(total_meter_location_from_pr==pumping_count)? 1:0;
			  String DCB_Freeze="N",DCB_Freeze_msg="";
			  DCB_Freeze=obj.getValue("PMS_DCB_FREEZE_RECEIPT","RECEIPT_FREEZE"," where OFFICE_ID="+Office_id+" and CASHBOOK_MONTH="+Bill_month+" and CASHBOOK_YEAR="+Bill_year).trim();
			  journal_verificaiton=obj.getValue("PMS_DCB_FREEZE_RECEIPT","JOURNAL_FREEZE"," where OFFICE_ID="+Office_id+" and CASHBOOK_MONTH="+Bill_month+" and CASHBOOK_YEAR="+Bill_year).trim();
			  String Cr_Advice="";
			  Cr_Advice=obj.getValue("PMS_DCB_FAS_CREDIT_ADVISE","FAS"," where OFFICE_ID='"+Office_id+"' and CASHBOOK_MONTH="+Bill_month+" and CASHBOOK_YEAR="+Bill_year).trim();
				int Cr_Advice1=Integer.parseInt(Cr_Advice);	  
					  
			  if (journal_verificaiton.equalsIgnoreCase("Y")  ) 
				 {
			  			  journal_ver_msg="<font color='green'>Journal Verification Done</font>";
			  			  journal_verification_code=1;
				 }else
				 {
					  	  journal_ver_msg="<font color='red'>Journal Verification not done....Refer Help</font>";
					  	  journal_verification_code=0;
				 }
			  
			  if (DCB_Freeze.trim().equalsIgnoreCase("Y")) DCB_Freeze_msg="<font class='fnt' color='green' ><b>DCB Receipt Verified</b></font>"; else DCB_Freeze_msg="<font class='fnt' color=red><b>DCB Receipt Not Verified</b></font>";
			// if (tbfreeze.equalsIgnoreCase("Y")  ) 
			 //{
				if (check_result==1) 
					{  
				  		 count=1;	 
				  		msg2=""; 
					}else
					{
						msg2="  Pumping Return Entry Omission Found. <a href='data_not_submit_ben.jsp'> Check Pumping Return Missing Report  </a>";
					}
				msg="<font Color='green' size='3'> Freezed</font>";
			/* }
			 else
			 {
				  count=0;
				  msg="<font Color='red' size='3'>Not Freezed</font>";
			 }*/
			 
			 if (DCB_Freeze.trim().equalsIgnoreCase("Y") ) 
			 {
				 count1=1;  
			 }else
			 {
				 count1=0;
				 
			 }
				String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
			count_msg="";
			if (Bill_count ==ben_count ) 
			{
				count_msg=" <font class='fnt' > Already  "+ Bill_count +" Bills Generated.&nbsp;&nbsp; Check Bill Generated Check List.</font> &nbsp;&nbsp;  <font class='fnt' color=red>Refer Help.....</font> ";
				dis="disabled='disabled'";  
			}
			// This Procedure for get count of DCB Journal 
			int  total_count=0;
			try
			{
			CallableStatement ct=con.prepareCall("call PMS_DCB_OTHER_CHARGES_COUNT (?,?,?,?) ");
			ct.setInt(1, Integer.parseInt(Bill_month));
			ct.setInt(2, Integer.parseInt(Bill_year));
			ct.setInt(3, Integer.parseInt(Office_id));
			ct.setNull(4,Types.NULL);
			ct.registerOutParameter(4,Types.INTEGER);
			ct.execute();
			total_count =ct.getInt(4);	
			//out.println("total_count"+total_count);
			}catch(Exception e) 
			{    
			out.println(e);
			}
			bean.setOffid(Integer.parseInt(Office_id));
	 		ob_missing_count=obj.getValue("PMS_DCB_TRN_MONTHLY_PR","count(distinct beneficiary_sno)","ct"," where BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where  "+new_cond+" office_id="+Office_id+" ) and BENEFICIARY_SNO not in ( select BENEFICIARY_SNO from pms_dcb_ob_yearly where month="+Bill_month+" and  fin_year="+Bill_year+" and office_id="+Office_id+") and month="+Bill_month+" and year="+Bill_year+" and office_id="+Office_id+"  ");
	 		if (!ob_missing_count.equalsIgnoreCase("0")) ob_missing_count="Opening Balance Missing : <font color='red'>"+ob_missing_count+" Beneficiary  </font> <a target='_blank' href='../reports/jsps/OBMissingReport.jsp'>Click Here</a>"; else ob_missing_count=""; 
			obj.conClose(con);
		//onload="billcount()"
%>   
 <body>   
  <form name="billdemand" >
   <table style="border-collapse: collapse;"   id="data_table" width=85% align="center"   cellspacing="0" cellpadding="3">
    <tr>
    	<th  align="center"   colspan="3"><font size='2'> Bill Demand Generation</font>  <label id="msg"></label> </th>
    	<th width="30%"><jsp:expression>Office_Name</jsp:expression></th>
    </tr>
   <tr > 
   <input type=hidden name="pmonth" id="pmonth" value=<%=Integer.parseInt(Bill_month)%> />
      <input type=hidden name="pyear" id="pyear" value=<%=Bill_year%> />
   
   		<td  width="20%"><label class="lbl">Bill Month : <%=cmonth[Integer.parseInt(Bill_month)]%></label></td>
   		<td ><label class="lbl">Bill Year : <%=Bill_year%></label></td>
   		<td  width="20%"><div align="left"><label class="lbl"> Bill Date</label></div> </td>
        <td colspan="1" align="left">
        <input class="tb5" type="text" name="date" id="date" maxlength="10" size="10" onFocus="javascript:vDateType='3'"  value="<%=Date_dis%>" onkeypress="return  calins(event,this)" onblur="return checkdt(this);"></input>
        <img src="../../../../../images/calendr3.gif" onclick=" showCalendarControl(document.billdemand.date);"
        alt="Show Calendar" height="24" width="19"></img>
        </td>
   </tr>
    <tr>
    	<td><label class="lbl"> Bill Period From <font size='4' color='red'>*</font> </label></td> 
        <td align="left"  ><input type="text"   name="datefrom" id="datefrom" maxlength="10" size="4" onFocus="javascript:vDateType='3'"  onblur="month_select(this)"  onblur="return checkdt(this);"/>
        <img align="top" src="../../../../../images/calendr3.gif"  onclick=" showCalendarControl(document.billdemand.datefrom);"  alt="Show Calendar"></img>
         <label class="lbl"> To</label>   <input type="text" class="tb5" name="dateto" id="dateto" width=10 maxlength="10" size="10" onFocus="return date_val('datefrom',this);" onChange="return date_val('datefrom',this);"/>
        <img src="../../../../../images/calendr3.gif" onclick=" showCalendarControl(document.billdemand.dateto);" alt="Show Calendar"></img>             
        </td>
        <td></td>
        <td align="right"><a href="javascript:other_charges()"><font color="red">Include Other Charges(Through Journal)</font></a></td> 
     </tr>
     <tr>
     	<td><label class="lbl"> Beneficiary Type</label></td>
     	<td><%=ben_type%></td><Td><label class="lbl">Beneficiary</label> </td><td><select id='ben_combo'>
     		<option value=0>Select Beneficiary </option>
     	</select></Td>
     </tr>
     <!--<tr><td>Beneficiary</td>
     	<td colspan="2"><select id='ben_combo'>
     		<option value=0>Select Beneficiary </option>
     	</select></td>
     </tr>  -->
     <tr>
     <td colspan="4" align="center"><font color='red'>
     			Trial Balance Freeze condition before Bill Generation has been Relaxed from December 2014... Post Ledger and Verify Ledger statement before TB freezing</font>
     </td>
     </tr>
     <tr>
     	<td colspan="3"><%=ob_missing_count %> </td><Td colspan="3" align="right"><img src="../../../../../images/new_.jpg" width="30" height="20" /><b><input type="button" value="Bills Generated so far" onclick="billcount()">&nbsp;&nbsp;&nbsp;<font color="green" size="3"><label id='idtab'></label></font>&nbsp;&nbsp;&nbsp; </Td>
     </tr>
     <tr>
	      <td colspan="4" align="right" >
	      <table style="border-collapse: collapse;" bordercolor="skyblue" id="data_table" width=100% align=center border="0"  cellspacing="0" cellpadding="3">
	     		<tr>
	     			<td><label class="lbl"  ><%=count_msg%></label></td><td colspan="3" align="right" > <label class="lbl"  > Total Beneficiaries : <font color="green" size="3"> <b> <%=ben_count %></b> </font></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	     			</td>
	    		</tr>
	    		
	     		<tr>
	     	 		<td colspan="4" align="left" ><label class="lbl"  > <%=msg2 %></label>&nbsp;&nbsp;&nbsp;&nbsp;<label class="lbl">Total Meter Location &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;<font color='green'><%=total_meter_location%></font>&nbsp;&nbsp;&nbsp;  Pumping Return Entered &nbsp; :<font color='green'  class='fnt'> <%=pumping_count_entry%></font> &nbsp;&nbsp;&nbsp;   Pumping Return Freezed : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;<font color="<%=color_%>"><%=pumping_count%></font> </label></td>
	     		</tr>
	     		
	     	</table>
	     </td>
	   </tr>   
      <tr>
        <!-- <td><label class="lbl">Trial Balance Status</label></td>
        <td   class="tdText"><%=msg%></td> -->
        <td><label class="lbl">DCB Receipt Verification Status</label></td>
        <td colspan='1'  class="tdText"><%=DCB_Freeze_msg%></td><td colspan='2'><%=journal_ver_msg%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Credit Advise Given by Ho is :<%=Cr_Advice %> .Please confirm with HO  </td>
      </tr>
  </table>
  <table cellspacing="0" cellpadding="0"   width="85%" align="center"    style="border-collapse: collapse">
  <tr><td><font color="blue">Generate Demand option enabled only if....... 1. DCB Receipt Should be Verified  2. Water Charges Journal Adjustments is Verified 3. DCB Data Unfreezed</font></td></tr></table>
         <input type="hidden" value="" id="fyear" name="fyear" /> 
         <input type="hidden" value="" id="command" name="command"/>
         <input type="hidden" value="" id="fmonth" name="fmonth" />
       
         
        <table cellspacing="0" cellpadding="0"   width="85%" align="center"    style="border-collapse: collapse">
        <tr>                            
        	<th align="center">
        	       	 
          	 <%  
          				out.println("<font color='skyblue'>"+total_count+"</font>");
          				out.println("<font color='skyblue'>"+other_charges_count+"</font>");  
          	 		  String hidden_var="";
            		  if ( total_count > 0 && (other_charges_count!=total_count) )
          	 		  {
          	 			 hidden_var="style='disabled='disabled'";  
          	 		  }
            		   
          			  if (count!=0 && count1!=0) 
          			  {      
          				  if (wcfreeze.trim().equalsIgnoreCase("Y")   ) 
          				  {
          					 
          					if (journal_verification_code!=1)
          					{
          						 dis="disabled='disabled'";  
          					} 
          				   	%>  
          				  		<input class="fb2" type="hidden" name="add" value="Generate Demand Full Deposit" id="add" onclick="date_ch2()" align="middle">
          			 			<input class="fb2" type="button" <%=hidden_var%>=name="add" value="Generate Demand" id="add" onclick="date_ch()" align="middle" <%=dis%>>	     	
							<%
							
						   }  
          				   else 
          				   {
          					  out.println("<font class='fnt' color='red'><b> Water Charge Should Be Freezed Before Bill Generation Process</b></font>");
          				   }
          			  }
          			
          				%>&nbsp;&nbsp;
          				 &nbsp;
          				
                   			<input type="button" onclick="ch_wind()" value="Bill Generated Check List"  class='fb2' >		
          		&nbsp;&nbsp;&nbsp;&nbsp; <input class="fb2" type="button" name="exit" value="EXIT" id="exit" onclick="self.close()" align="middle"><img src="../../../../../images/help_images.jpg" height="18px" width="45px" style="vertical-align: bottom;" onclick="javascript:window.open('./help1.jsp?fn=27','mywindow','width=600,height=400,scrollbars=yes')">
          		&nbsp;<input type="button" value="CheckData"  onclick="CheckData()" />
          		&nbsp;<input type="button" value="Missing Beneficiary"  onclick="CheckMiss()" />
          		&nbsp;<input type="button" value="Delete Duplicate Beneficiary"  onclick="CheckDup()" />
          		&nbsp;<input type="button" value="Delete incorrect Beneficiary"  onclick="CheckDatadel()" />
          	
          		</th>
          	
          	</tr>
          	  <tr > 
    <td  colspan="4"><iframe  frameborder="0" allowtransparency="true" id='test' align="center"  style="width: 100%;height:10cm;scrollbar-face-color:#9898D0;"> 
    
    </iframe></td> </tr>
    <tr><th   align="center"><input class="fb2" type="button" name="clear" value="Exit" id="clear" onclick="javascript:clr()" align="middle"/></th></tr>
      </table>
        <input type="hidden" id="selected_ben" name="selected_ben" value="0"></input>
        <input type="hidden" id="total_charge_row" name="total_charge_row" ></input>    
 	    <input type="hidden" id="t1" name="t1" ></input>
 	  	<input type="hidden" id="net_qty" name="net_qty" ></input>
 	  	<input type="hidden" id="net_eqty" name="net_eqty" ></input>
 	  	<input type="hidden" id="ADf_ANY" value="0"></input>
 	  	<input type="hidden" id="WC_CB_TOTAL" value="1254512"></input>
 	  	<input type="hidden" id="MAINT_CB_TOTAL" value=0></input>
 	  	<input type="hidden" id="YESTERYR_CB" value=0></input>
  	  	<input type="hidden" id="BENEFICIARY_SNO" value=0></input>
  	  	<input type="hidden" id="maxsno" value=0></input>
  	  	<input type="hidden" id="cmonth" value=<%=Bill_month%>></input> 
  	  	<input type="hidden" id="billmonth" value=<%=Bill_month%>></input>
  	  	<input type="hidden" id="billyear" value=<%=Bill_year%>></input>
  	 	<input type="hidden" id="office" value=<%=Office_id%>></input>
  	 	<input type="hidden" id="off" value=<%=Office_id%>></input>
  	 	<input type="hidden" id="bcount" value=0></input>  
  	 	<input type="hidden" id="bencount" value="<%=ben_count%>"></input>
	    <input type="hidden" id="pr_status" name="pr_status" value="0">   
 	  	<%     
 	  		 if ( total_count > 0 && (other_charges_count!=total_count) )
 	  		 {
 	    %>
        	<script type="text/javascript">
        	    		alert("Other Charges Journal Entries Found \n \n  Click Include Other Charges( Through Journal if already not included )  Before Bill Demand Generation \n \n Otherwise Demand Bill cannot be generated \n ---------------------------------------------");        	          	  
        	</script>
        <%   } %>
        <%     
 	  		 if ( Cr_Advice1 > 0 )
 	  		 {
 	    %>
        	<script type="text/javascript">
        	    		alert("Credit Advise is found . Accept the adj memo amount for any doubt contact HO");        	          	  
        	</script>
        <%   } %>
        
        
        <a href="ben_added_area_master.jsp">.</a>
        </form>
 	 </body>  
 	 </html>      