	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
	<%@page import="java.util.Calendar" %>
	<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
	<%@page import="java.sql.Connection"%>
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
	<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
	<title>Item Wise Expenditure - Previous Fin. Year  </title>
	<script type="text/javascript" src="../scripts/cellcreate.js"></script>
	<script type="text/javascript" src="../scripts/transcat.js"></script>
	<script type="text/javascript">
	function acc_view(i)
	{
		var MAIN_ITEM_DESC	=document.getElementById("MAIN_ITEM_DESC"+i).innerHTML;
		var acc=document.getElementById("ACCOUNT_HEAD_CODE"+i).value;
		alert(MAIN_ITEM_DESC +"  \n\n Account Heads Code : \n \t" + acc);
	}
	function allsch()
	{
		var pyear=document.getElementById("pyear").value;
		window.open("pms_ame_sch_prv_all_sch.jsp?fin_year="+pyear);
	}
	function load()
	{
		 document.getElementById("cmd").value='Add';
		 document.forms["item_wise_year"].submit();
	}
	function update()
	{
		 document.getElementById("cmd").value='Update';
		 document.forms["item_wise_year"].submit();
	}function cdelete()
	{
		 document.getElementById("cmd").value='Delete';
		 document.forms["item_wise_year"].submit();
	}
	function msg_cls()
	{
		 document.getElementById("msg_msg").innerHTML="";
	}	
	function report_show_2(process_code)
	{
		var pyear=document.getElementById("pyear").value;
		var sch_sno=document.getElementById("sch_sno").value;
		window.open("../../../../../ame_report?process_code="+process_code+"&fin_year="+pyear+"&sch_sno="+sch_sno);
		
	}	
	function page_total(row)
	{
		var total=document.getElementById("total").value;
		if (total=="") total=0;
		var rowamt=document.getElementById("famt"+row).value;
		total=parseFloat(total)+parseFloat(rowamt);
		document.getElementById("total").value=total;
	}
</script>
</head>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<body onload="transaction(3,16)">  
<form action="Pms_AME_Item_wise_prvyear.jsp" name="item_wise_year" method="post">
<%!String msg=""; %> 
<%

		double ACTUAL_EXP=0.0f; 
		int sub_sno=0,maint_sno=0,gp_sno=0;
		String process_f="";
		try
		{	
		HttpSession session1 = request.getSession(false);
		String	userid = (String) session1.getAttribute("UserId");
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		Controller obj=new Controller();
		Connection con=obj.con();
		obj.createStatement(con);
		if (userid == null) {

			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}		
		String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')");
		String sch_sno = obj.setValue("sch_sno", request);
		String pyear= obj.setValue("pyear", request); 
		
		if (Office_id.equals("")) Office_id="0";
		String Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
		String sch=obj.combo_str("PMS_SCH_MASTER","SCH_NAME","SCH_SNO"," where   sch_sno in (select distinct SCHEME_SNO from  PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+" ) and SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+") order by sch_sno","sch_sno","","  style='width: 180pt;'" );
		String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
		String flag_c=obj.setValue("flag_c",request);
		int year_incr=0;
		if (month <4)
		  year_incr=0;    
		else  
		  year_incr=1;
		   
		int total_row= Integer.parseInt(obj.setValue("row_count",request));
		java.util.Hashtable cols=new java.util.Hashtable();
		process_f=obj.setValue("pyear",request);
		sch_sno	=obj.setValue("sch_sno",request);
		msg	=obj.isNull(obj.setValue("msg",request),2);		  	
		String save=obj.setValue("cmd",request);
		if (save.equals("Delete"))
		{
			int r=obj.delRecord("PMS_AME_TRN_SCH_ACT_EXP_FINYR","where OFFICE_ID="+Office_id + " and FIN_YEAR='"+pyear+ "' and SCH_SNO="+sch_sno  ,con,1);
			 if (r > 0)
			 {%>
			 <script>
			 alert("Item Wise Expenditure for  <%=pyear%> Sucessfully Deleted !!")						 
			 </script>
			 <%
			 }
			 else
			 {
				 msg="";
			 }
		}
		if (save.equals("Update"))
		{
			 int row=0;
			 obj.delRecord("PMS_AME_TRN_SCH_ACT_EXP_FINYR","where OFFICE_ID="+Office_id + " and FIN_YEAR='"+pyear+ "' and SCH_SNO="+sch_sno  ,con);
			 for(int i=1;i<=total_row;i++)
			  	{
			  	   int tab_max_=obj.getMax("PMS_AME_TRN_SCH_ACT_EXP_FINYR","ACTUAL_EXP_SNO", "");
			  	   sub_sno=Integer.parseInt(obj.setValue("SUB_ITEM_SNO"+i,request));
			  	   maint_sno=Integer.parseInt(obj.setValue("MAIN_ITEM_SNO"+i,request));
			  	   gp_sno=Integer.parseInt(obj.setValue("GROUP_SNO"+i,request));
			  	   ACTUAL_EXP=Double.parseDouble(obj.setValue("famt"+i,request));
			       String cond_add=" and SUB_ITEM_SNO="+sub_sno+" and MAIN_ITEM_SNO="+maint_sno+" and GROUP_SNO="+gp_sno;
				   int d_r=obj.delRecord("PMS_AME_TRN_SCH_ACT_EXP_FINYR", "where (1=1) "+cond_add+" and  PERFORM_DESC_SNO=3 and FIN_YEAR='"+process_f+"' and SCH_SNO="+sch_sno+"  and  office_id="+Office_id, con,1);
				   cols.put("ACTUAL_EXP_SNO",tab_max_ );
				   cols.put("FIN_YEAR", "'"+pyear+"'");
				   cols.put("SCH_SNO",sch_sno);					     
				   cols.put("ACTUAL_EXP", ACTUAL_EXP);
				   cols.put("UPDATED_BY_USER_ID", "'"+userid+"'");
				   cols.put("UPDATED_TIME", "clock_timestamp()");
				   cols.put("OFFICE_ID", Office_id);
				   cols.put("PERFORM_DESC_SNO","3");
				   cols.put("GROUP_SNO", gp_sno);
				   cols.put("MAIN_ITEM_SNO", maint_sno); 
				   cols.put("SUB_ITEM_SNO",sub_sno );
				   cols.put("ENTRY_DATE", "clock_timestamp()");				
				   String project_id=obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+obj.setValue("sch_sno", request));
				   cols.put("PROJECT_ID",project_id ); 
				   row+=obj.recordSave(cols,"PMS_AME_TRN_SCH_ACT_EXP_FINYR", con);
				  
			 	}
			 if (row > 0)
			 {%>
				 <script>
				 alert("Item Wise Expenditure for  <%=pyear%> Sucessfully Updated !!")						 
				 </script>
			 <%}
			 else
			 {
				 msg="";
			 }
		}
		if (save.equals("Add"))
		{		
			int row=0;
		  	for(int i=1;i<=total_row;i++)
			  	{
			  	  int tab_max_=obj.getMax("PMS_AME_TRN_SCH_ACT_EXP_FINYR","ACTUAL_EXP_SNO", "");
			  	   sub_sno=Integer.parseInt(obj.setValue("SUB_ITEM_SNO"+i,request));
			  	   maint_sno=Integer.parseInt(obj.setValue("MAIN_ITEM_SNO"+i,request));
			  	   gp_sno=Integer.parseInt(obj.setValue("GROUP_SNO"+i,request));
			  	   ACTUAL_EXP=Double.parseDouble(obj.setValue("famt"+i,request));
			       String cond_add=" and SUB_ITEM_SNO="+sub_sno+" and MAIN_ITEM_SNO="+maint_sno+" and GROUP_SNO="+gp_sno;
				   int d_r=obj.delRecord("PMS_AME_TRN_SCH_ACT_EXP_FINYR", "where (1=1) "+cond_add+" and  PERFORM_DESC_SNO=3 and FIN_YEAR='"+process_f+"' and SCH_SNO="+sch_sno+"  and  office_id="+Office_id, con,1);
				   cols.put("ACTUAL_EXP_SNO",tab_max_ );
				   cols.put("FIN_YEAR", "'"+pyear+"'");
				   cols.put("SCH_SNO",sch_sno);					     
				   cols.put("ACTUAL_EXP", ACTUAL_EXP);
				   cols.put("UPDATED_BY_USER_ID", "'"+userid+"'");
				   cols.put("UPDATED_TIME", "clock_timestamp()");
				   cols.put("OFFICE_ID", Office_id);
				   cols.put("PERFORM_DESC_SNO","3");
				   cols.put("GROUP_SNO", gp_sno);
				   cols.put("MAIN_ITEM_SNO", maint_sno); 
				   cols.put("SUB_ITEM_SNO",sub_sno );
				   cols.put("ENTRY_DATE", "clock_timestamp()");				
				   String project_id=obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+obj.setValue("sch_sno", request));
				   cols.put("PROJECT_ID",project_id ); 
				     row+=obj.recordSave(cols,"PMS_AME_TRN_SCH_ACT_EXP_FINYR", con);
				   
			 	}
			  	 if (row > 0)
				 {%>
				 <script>
				 alert("Item Wise Expenditure for  <%=pyear%> Sucessfully Stored !!")						 
				 </script>
				 <%
				 }
				 else
				 {
					 msg="";
				 }
		  	}  else
		  	{
		  		msg="";
		  	}
%>
		<table align="center" width="60%" border="1" cellspacing="1" cellpadding="1" style="border-collapse: collapse;border-color: skyblue" >
     	<tr>
     	    <th colspan="2" align="center"  class="tdH">  Item Wise Expenditure - Previous Fin. Year  </th></tr> 
		<tr>
			<td width="30%"><label class="fnt">Division Name :</label></td> 
			<td align="left"> <label class="fnt"><%=Office_Name%></label></td>
		</tr>
		<tr> 
			<td width="30%"><label class="fnt">Scheme  Name : </label></td>
			<td align="left"> <%=sch%></td>
		</tr>
		<tr>
			<td><label class="fnt">Financial Year</label></td>
			<td colspan="1">
				<select class="select" id="pyear" name="pyear" style="width: 80pt;">
				<option value="0">Select</option>
				<%
					for (int i = year-2; i <= (year+year_incr)-1; i++) 
					{
					  String inp_year=(i-1)+"-"+i;
					  if (pyear.equalsIgnoreCase(inp_year.trim()))  
					  {
				%><option value="<%=i-1%>-<%=i%>" selected><%=i-1%>-<%=i%></option>
				<%
					  }
					  else
					  {
				%>
				  <option value="<%=i-1%>-<%=i%>"><%=i-1%>-<%=i%></option>
				<%
					  }
					}
				%>
				</select>&nbsp;&nbsp;&nbsp;<input type="button" value="View" onclick="transaction(3,17)"> &nbsp;&nbsp;<label id='msg_msg'><font color='green'> </font></label> 	 
			</td>
	 	 </tr>
		 <tr>
		  	<td colspan="2"><label id='msg'></label></td>
		  </tr>
		</table>
		<table id="etable"   align="center" width="60%" border="1" cellspacing="1" cellpadding="1">
		<tr>
			<td colspan="2" align="right"><label style="font-size: 1.15em;color: blue" id='net_label'></label></td>
		</tr>
		
 		
		<tr><td valign="top" align="center" colspan="2" style="height: 42em;">						
		    <table id="etablesub"   border="1"   align="center" width="100%"  cellspacing="1" cellpadding="1">
			<tr style="height: 2em;">
			<th align="center" width="5%">Sl.No</th>
			<th align="center">Description of Items</th>
			<th align="center" width="15%">Account Head Code</th> 
			<th align="center" width="15%">Amount(Rs.) <Br> <font color='darkred'>(Actuals)</font></th>
		</tr>
				<tbody id="edatasub" align="center" valign="top"/>
		</table> 
		</td>
		</tr>
		<tr  > 
			<td align="right" id='tot2'  width="69%"><font color='darkred'><b>Total</b></font></td> 
			<td align="right"><input type="text" id="total" name="total" style="text-align: right;"/>&nbsp;&nbsp;</td>
		</tr>
		<tr> <a href="Pms_AME_Item_prvyearUI.jsp">.</a>
			<td colspan="2" align="right"><label style="font-size: 1.15em;color: blue" id='net_label1'></label></td>
		</tr>
		<tr>    
				<th align="center" colspan="2">
					<input type="hidden" value="" id="EST_SNO" name="EST_SNO">
					<input type="button" value="Add" id="save" name="save"  onclick="load()" />
					<input type="button" value="Update" id="save" name="save"  onclick="update()" />
					<input type="button" value="Delete" id="save" name="save"  onclick="cdelete()" />
					<input type="button" value="Report" onclick="report_show_2(124)" /> 
					<input type="reset"  value="Reset" />
					<input type="button" value="Exit" onclick="window.close()" />
				 	<input type="hidden" name="key_value" id="key_value" value="">
				 
				</th>
		</tr>				   
	</table>
			<input type="hidden" id="msg_hid" name="msg_hid" value="Expenditure figures are being fetched from FAS ...Please Wait">
			<input type="hidden" id="pr_status" name="pr_status" value="1"/>			
			<input type="hidden" id="flag_c" name="flag_c" value="M"/>
			<input type="hidden" id="days" name="days" value="0"/> 
			<input type="hidden" id="pumped_dpr" name="pumped_dpr" value="0"/>
			<input type="hidden" id="dataflag" name="dataflag" value="0"/> 
			<input type="hidden" id="row_count" name="row_count" value="0"/>
			<input type="hidden" id="desg_hour" name="desg_hour" value="0"/>
			<input type="hidden" id="rowcount2" name="rowcount2" value="0"/>
			<input type="hidden" id="cmd" name="cmd" value="0"/>
	</form>
</body>
	<%
	}catch(Exception e)
	{
		out.println(e);
	}  
	%>
</html>