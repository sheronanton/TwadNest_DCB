<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.*" %>
<%@ page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
<html>
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet" media="screen"/>
<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen" />
 <link href="../../../../../css/AME.css" rel="stylesheet" media="screen" />
	<title>Expenditure Item Account Head Mapping</title>
	<script type="text/javascript" src="../scripts/cellcreate.js"></script>
	<script type="text/javascript" src="../scripts/Pms_Ame_Acc_Mapping.js"></script>
	 <script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
	 
	<script type="text/javascript">
	function form_load()
	{
		document.getElementById("up_but").disabled=true;   
	}
	function cls()
	{
		 
		document.getElementById("mainitem")[0].selected = "1";
		document.getElementById("subitem")[0].selected = "1";
		document.getElementById("ACC_GROUP")[0].selected = "1";
		document.getElementById("sch_type")[0].selected = "1";
		document.getElementById("ac").value = "";
		document.getElementById("acdesc").value = "";
		document.getElementById("add_but").disabled=false;
		document.getElementById("up_but").disabled=true;   
	}
	 
	function select_edit_new(a,b,c,d,e,f)
	{
		var ACC_CODE_SNO=document.getElementById("ACC_CODE_SNO"+b).value;
		try
		{	
						document.getElementById("up_but").disabled=false;   
						document.getElementById("add_but").disabled=true;   
						document.getElementById("ACC_GROUP")[c].selected = "1";
						var len=document.getElementById("sch_type").length;
						for(var i=0;i<len;i++)
						{ 
								if (document.getElementById("sch_type")[i].value==d)
								{
									document.getElementById("sch_type")[i].selected = "1";
								}
						}
						document.getElementById("mainitem")[e].selected = "1";
						document.getElementById("subitem")[f].selected = "1";
						document.getElementById("ac").value = document.getElementById("ACCOUNT_HEAD_CODE"+b).innerHTML;
						document.getElementById("acdesc").value = document.getElementById("ACC_DESC"+b).innerHTML;

						 
						
						 
		}catch(e) 
		{
			
		}
		document.getElementById("acsno").value=ACC_CODE_SNO;
		 	
	}
	function select_edit_delete(a,b)
	{
		var ACC_CODE_SNO=document.getElementById("ACC_CODE_SNO"+b).value;
		document.getElementById("acsno").value=ACC_CODE_SNO;		
		new_transaction(9,4)	;		
	}
	function update()
	{
		new_transaction(9,4);	
		new_transaction(9,5);
	}
	function report_open()
	{
		try { ACC_GROUP=document.getElementById("ACC_GROUP").value;} catch(e) {};
		try { mainitem=document.getElementById("mainitem").value;} catch(e) {};
		try { subitem = document.getElementById("subitem").value;} catch(e) {};
		try { sch_type = document.getElementById("sch_type").value;} catch(e) {};
		
		params = "?subitem=" +subitem +"&mainitem=" +mainitem;
		params += "&sch_type="+sch_type+"&ACC_GROUP_SNO="+ACC_GROUP;
		window.open('Pms_Ame_Acc_Map_Excel.jsp'+params,'Abstract ','width=400,height=200')
	}
	</script>
</head>
<body onload="new_transaction(9, 1);form_load()">
<%
	try 
	{  
		 
		Controller obj=new Controller(); 
		Connection con=obj.con();
		String mainitem=obj.setValue("mainitem",request); 
	 	String subitem=obj.setValue("subitem",request);
	 	String sch_type=obj.setValue("sch_type",request); 
		String mainitemc=obj.combo_str("PMS_AME_MST_MAIN_ITEM","MAIN_ITEM_DESC","MAIN_ITEM_SNO", " order by MAIN_ITEM_SNO  ","mainitem",mainitem,"  style='width:120pt;'  onchange='new_transaction(9,7)'" );
		String subitemc=obj.combo_str("PMS_AME_MST_SUB_ITEM","SUB_ITEM_DESC","SUB_ITEM_SNO", " order by SUB_ITEM_SNO","subitem",subitem,"class='pmscombo' style='width:120pt;'" );
		String ACC_GROUP=obj.combo_str("PMS_AME_ACC_GROUP","ACC_GROUP_DESC","ACC_GROUP_SNO", " order by ACC_GROUP_SNO","ACC_GROUP","","class='pmscombo' style='width: 120pt'" );
		String schtype= obj.combo_str("PMS_SCH_LKP_TYPE","SCH_TYPE_DESC","SCH_TYPE_ID", " where  SCH_CATEGORY_ID=4 and SCH_TYPE_ID in (select SCH_TYPE_ID from  PMS_DCB_APPLICABLE_SCH_TYPE  )    order by SCH_TYPE_ID ","sch_type",sch_type,"class='pmscombo' style='width: 120pt'" );

%>    	<a href="../reports/pms_sch_live_list.jsp">.</a>
<table width="75%" align="center" border=1  style="border-collapse: collapse;border-color: skyblue" >
 	<tr class = "tdH"> 
 		<th colspan="4" align="center">Group wise Expenditure Item Account Head Mapping    </th>
 	</tr>
 	<tr> 
 		<td width="15%">Scheme Type</td><td width="15%"><%=schtype %></td>  
 	 
 		<td width="15%">Account Group </td><td width="35%"><%=ACC_GROUP %></td>
    </tr>
 	<tr>
 		<td width="15%">Main Item </td><td width="15%"><%=mainitemc %></td>
 	  
 		<td width="15%">Sub Item </td><td width="35%"><%=subitemc %></td>
 	</tr> 
 	
 	 <tr> 
 	   
 		<td width="15%">  Account Head Code </td>
 		<td width="15%"><input type="text" id="ac" name="ac" maxlength="6" size="6" style="width: 10em;" onblur="new_transaction(9, 6)"  onkeyup="isInteger(this,9,event)"></td>
 		<td> Account Head Description </td>
 		<td width="35%"><input type="text" id="acdesc" name="acdesc" maxlength="710" size="10" style="width: 30em;"></td>  
 	</tr>  
 	<tr>  
		<th><a href="javascript:new_transaction(9, 1)"><img src="../../../../../images/arow.jpg" border="1" width="90" height="32" style="vertical-align: middle"></a></th><th colspan="3">
		&nbsp;&nbsp;<input type="button" value="Add" id='add_but' onclick="new_transaction(9,2)"/>  
		<input type="button" value="Update" id='up_but' onclick="update()"/>	
		<input type="button" value="Clear" onclick="cls()"/>		
		<input type="button" name="Report" value="Excel Report" onclick="report_open()">
		<input type="button" value="Exit" onclick="javascript:window.close();"/>     
		</th> 
	</tr>
 	</table>
 	 
		<table class="table" id="etable"  border="1"  align="center" width="100%" border="1" style="border-collapse: collapse;border-color: skyblue">  
		<tr class="tdH" >
			<th width="2%" align="center" >Sl.No</th>
			<th width="2%" align="center">&nbsp;&nbsp;</th>
			<th width="6%" align="center">Scheme Type</th>
			<th width="10%" align="center">Account Group</th> 
			<th width="10%" align="center">Main Item</th>
			<th width="10%" align="center">Sub Item</th>
			<th width="2%" align="center">Account Head Code</th>
			<th width="20%" align="center">Account Head Description </th>
			<th width="2%" align="center">&nbsp;&nbsp;</th>
		</tr>  
		<tbody id="edata" align="left" valign="top"/>
      	</table>
		  
 <input type="hidden" id="acsno" name="acsno" value="0"/>
 	<input type="hidden" id="rowcount" name="rowcount" value="0"/>
 	<%

	}catch(Exception e) 
	{
		
		
	} 

 	%>
 	</body>
</html>

