<?xml version="1.0" encoding="ISO-8859-1" ?>
 
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<meta http-equiv="Content-Type " content="text/html; charset=ISO-8859-1" />
<title>Sub Item Master</title>
<script type="text/javascript"> 
			function page_size()
			{
				window.resizeTo( 900,700 )
			}
			function set(op,row) 
			{
			   
			 document.getElementById("main")[op].selected = "1"
		 	 document.getElementById("desc").value=document.getElementById("SUB_ITEM_DESC"+parseInt(row)).innerHTML
		 	 document.getElementById("shortdesc").value=document.getElementById("SHORT_DESC"+parseInt(row)).innerHTML
			//document.getElementById("desc").style.background="#eeeeee";
 			//document.getElementById("chkSUB_ITEM_SNO"+row).checked=false;
 			var s = document.getElementById("chkAPPLY_CENTAGE"+row).checked;
			if (s==true)
			document.getElementById("cenageapl").checked=true;
			else
			document.getElementById("cenageapl").checked=false;
 			//document.getElementById("chkMAIN_ITEM_SNO"+row).checked=false;
			document.getElementById("key_value").value=document.getElementById("SUB_ITEM_SNO"+parseInt(row)).value
			}
			 
			</script>
			<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/> 
			<script type="text/javascript" src="../scripts/master.js"></script>
			<script type="text/javascript" src="../scripts/cellcreate.js"></script>
			<script type="text/javascript" src="../scripts/reports.js"></script>
</head>

<body onload="page_size(),master_load_url('main',2),master(3,3)">
<table class="table" width=100% align="center" border="1" cellpadding="5" style="border-collapse: collapse;border-color: skyblue" >
	<tr> 
			<th align="center" colspan="4" class="tdH">Expenditure <label class="headlbl"> Sub  Item Master </label> </th>
		 
		</tr>  
		  
		<tr> 
			  
			<th align="center" > Expenditure <label class="lbl">Main Item </label></th>
			<th align="center"  >Expenditure <label class="lbl">Sub Item </label></th>
			<th align="center"  ><label class="lbl">Short Description</label></th>			
			<th  align="center"> Apply Centage</th>
		</tr>
		
		<tr>	
			 
			<td width="48%"  align="left"><select id="main" name="main" class="select" style="width: 262"><option value=0>Select</option> </select></td>
			<td align="left"><input type="text" name="desc" id="desc" style="width: 410px"/></td>
			<td><input type="text" name="shortdesc" style="width: 70px;" id="shortdesc"  maxlength="10"/></td>
			<td  width="10%" align="center">
				<input type="checkbox" id="cenageapl" name="cenageapl"/>
			</td>  
			  
		 </tr>  
		 <tr class="tdH">
			<th align="center" colspan="4"> 
				<input type="button" value="Add" class="btn" onclick="master(3,1)" />
				<input class="btn" type=button value="Update" onclick="master(3,2)"/>
				<input type=button value="Delete"  class="btn"   onclick="master(3,4)"/>
				<input type="button" value="Report" class="btn" onclick="report(5)"/>
				<input type="reset" value="Clear"   onclick="javascript:window.location.reload()"/>				
				<input type="button" value="HELP" onclick="javascript:window.open('./twad_note.html#m4','_blank','toolbar=no, location=yes, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=yes, width=500, height=500');"/>
				<input type=button value="Exit"  class="btn" onclick="window.close()"/>   
			</th>
		</tr>
		<tr>
			<td align="center" colspan="4" >
			<table id="etable" class="table" align="center"  width="100%" border="1" cellspacing="0" cellpadding="5" style="border-collapse: collapse;border-color: skyblue" >
			 <tr class="tdH"> 
			 	<Th>&nbsp;</Th>
				<th align="center" > <label class="lbl">Expenditure Main Item </label></th>
				<th align="center"  > <label class="lbl">Expenditure Sub Item </label></th>
					<th align="center"  > <label class="lbl">Short Description</label></th>
				<th align="center"  > <label class="lbl">Apply Centage</label></th>
			</tr>
			<tbody id="edata" align="left" valign="top"/>
			</table>
			</td>
		</tr>
		
		<input type="hidden" id="rowcount" name="rowcount" value="0"/>	
		<input type="hidden" id="key_value" name="key_value" value="0"/> 
</table>
</body>
</html>
 