<%@page import="java.sql.*"  %>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type " content="text/html; charset=ISO-8859-1" />
<title>Group Master</title>
<script type="text/javascript"> 
	function select_edit(a,b,c,d,e,f)
	{
		var ACC_CODE_SNO=document.getElementById("ACC_CODE_SNO"+b).value;
		try
		{	
			
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
						document.getElementById("ac").value = document.getElementById("ACCOUNT_HEAD_CODE"+b).value;
						document.getElementById("wef").value = document.getElementById("CODE_W_E_F"+b).innerHTML;

						var astatus_value=document.getElementById("ACTIVE_STATUS"+b).innerHTML;;
						var len=document.getElementById("astatus").length;
						for(var i=0;i<len;i++)
						{ 
								if (document.getElementById("astatus")[i].value==astatus_value)
								{
									document.getElementById("astatus")[i].selected = "1";
								}
						}
						
						
		}catch(e) 
		{
			alert(e);
		}
		document.getElementById("acsno").value=ACC_CODE_SNO;
		 	
	}function select_edit_delete(a,b)
	{
		var ACC_CODE_SNO=document.getElementById("ACC_CODE_SNO"+b).value;
		document.getElementById("acsno").value=ACC_CODE_SNO;
		transaction(6,3)	;		
	}
	function update()
	{
		transaction(6,4);	
		transaction(6,5);
	}
	function page_size()
	{
		window.resizeTo( 700,700 )
	}
	function vset(i)
	{
		document.getElementById("add").disabled=true;
		document.getElementById("uprow").value=i;
		document.getElementById("desc").value=document.getElementById("GROUP_DESC"+i).innerHTML;
	}
	</script>
</head>
<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet" media="screen"/>
<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen" />
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen" />
<script type="text/javascript" src="../scripts/transcat.js"></script>
<script type="text/javascript" src="../scripts/reports.js"></script>
<script type="text/javascript" src="../scripts/CalendarCtrl.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript">
function show()
{  
	window.open("../../../../../ame_report?process_code=112");
} 

 </script>
 
 <body onload="transaction(6,1)">
 <form name="head">
 <%
  			//onchange=document.forms['head'].submit()
 			Controller obj=new Controller(); 
			Connection con=obj.con();
			String mainitem=obj.setValue("mainitem",request);
		 	String subitem=obj.setValue("subitem",request);
		 	String sch_type=obj.setValue("subitem",request);
			String mainitemc=obj.combo_str("PMS_AME_MST_MAIN_ITEM","MAIN_ITEM_DESC","MAIN_ITEM_SNO", " order by MAIN_ITEM_SNO  ","mainitem",mainitem,"class='pmscombo' style='width: 262' " );
 			String subitemc=obj.combo_str("PMS_AME_MST_SUB_ITEM","SUB_ITEM_DESC","SUB_ITEM_SNO", " order by SUB_ITEM_SNO","subitem",subitem,"class='pmscombo' style='width: 262'" );
 			String ACC_GROUP=obj.combo_str("PMS_AME_ACC_GROUP","ACC_GROUP_DESC","ACC_GROUP_SNO", " order by ACC_GROUP_SNO","ACC_GROUP","","class='pmscombo' style='width: 262'" );
			String schtype=obj.combo_str("PMS_SCH_LKP_TYPE","SCH_TYPE_DESC","SCH_TYPE_ID", " where  SCH_CATEGORY_ID=4 order by SCH_TYPE_ID ","sch_type",sch_type,"class='pmscombo' style='width: 262'" );

  %>
 <table width="75%" align="center" border=1  style="border-collapse: collapse;border-color: skyblue" >
 	<tr class = "tdH">
 		<th colspan="2" align="center">Expenditure Item Account Head Mapping</th>
 	</tr>
 	<tr>
 		<td>Scheme Type</td><td><%=schtype %></td> 
 	</tr>
 	<tr>
 		<td>Account Group </td><td><%=ACC_GROUP %></td>
 	</tr> 
 	<tr>
 		<td>Main Item </td><td><%=mainitemc %></td>
 	</tr>
 	<tr>
 		<td>Sub Item </td><td><%=subitemc %></td>
 	</tr>
 	
 	 <tr> 
 	  
 		<td>  Account Head Code </td><td><input type="text" id="ac" name="ac" maxlength="510" size="10" style="width: 50em;"></td>  
 	</tr> 
 	 <tr>  
 		<td> With Effect From </td><td><input type="text" style="width: 21em;" id="wef" name="wef"  >
 		<img src="../../../../../images/calendr3.gif" align="middle" onclick="showCalendarControl(document.head.wef,1);" alt="Show Calendar"> 
 		</td>
 	</tr>
 	 <tr> 
		<td>Active Status</td>
		<TD><select id="astatus" name="astatus">
			<option value="0">Select</option>
			<option value="A">Active</option>
			<option value="D">Defunct</option>			
		</select></TD>
	</tr> 
	<tr>
		<th colspan="2">
		<input type=button value="Add" onclick="transaction(6,2)"/>
		<input type=button value="Update" onclick="update()"/>  
		<input type=reset value="Clear"/>     
		<input type=button value="Exit" onclick="javascript:window.close();"/>
		<input type=button value="Report" onclick="show()"/>     
		</th>
	</tr>
 	<tr>   
		<td width=75%  align="center"  colspan="2">
		<table class="table" id="etable"  border="1"  align="center" width="100%" border="1" style="border-collapse: collapse;border-color: skyblue">  
		<tr class="tdH">
			<th width="2%" align="center">Sl.No</th>
			<th width="5%" align="center">Process</th>
			<th width="15%" align="center">Scheme Type</th>
			<th width="15%" align="center">Account Group</th>
			<th width="15%" align="center">Main Item</th>
			<th width="15%" align="center">Sub Item</th>
			<th width="20%" align="center">Account Head Code</th>
			<th width="10%" align="center">With Effect From</th>
			<th width="5%" align="center">Status</th>
			<th width="5%" align="center">Process</th>
		</tr> 
		<tbody id="edata" align="left" valign="top"/>
      	</table>
		</td>
		</tr>
		<input type="hidden" id="acsno" name="acsno" value="0"/>
 </table>	<input type="hidden" id="rowcount" name="rowcount" value="0"/>
 
 
 </form>
</body>
</html>
