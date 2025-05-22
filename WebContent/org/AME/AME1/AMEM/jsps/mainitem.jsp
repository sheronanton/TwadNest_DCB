<?xml version="1.0" encoding="ISO-8859-1" ?>
 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type"   content="text/html; charset=ISO-8859-1" />
<title>Main Item Master</title>
<script type="text/javascript"> 
			function set(op,row) 
			{
			document.getElementById("group")[op].selected = "1";
		 	document.getElementById("desc").value=document.getElementById("MAIN_ITEM_DESC"+parseInt(row)).innerHTML
		//	document.getElementById("desc").style.background="#eeeeee";
			//var s = document.getElementById("chkAPPLY_CENTAGE"+row).checked;
		//	if (s==true)
		////	document.getElementById("cenageapl").checked=true;
	//		else
		//	document.getElementById("cenageapl").checked=false;
 			//document.getElementById("chkMAIN_ITEM_SNO"+row).checked=false;
			document.getElementById("key_value").value=document.getElementById("MAIN_ITEM_SNO"+parseInt(row)).value
			}
			 
			function page_size()  
			{
				window.resizeTo( 900,700 )
			}
			</script>
			<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/> 
			<script type="text/javascript" src="../scripts/master.js"></script>
			<script type="text/javascript" src="../scripts/cellcreate.js"></script>
			<script type="text/javascript" src="../scripts/reports.js"></script>
</head> 
	 <body onload="page_size(),master_load_url('group',1),master(2,3)" >
	 <table  class="table" width="100%"  align="center" border="1" style="border-collapse: collapse;border-color: skyblue" >
	 <tr>
			<th   align="center" colspan="4" class="tdH">Expenditure <label class="headlbl"> Main Item Master </label> </th>
		 
		</tr>
		      
		<tr> 
			<th  width="36%" align="center" >  <label class="lbl">Expenditure Group </label></th>
			<th align="center"  >   <label class="lbl">Expenditure Main Item </label></th>
			 
		</tr>
		         
		<tr>    
			<td width="48%" align="left"><select id="group" name="group" class="select" style="width: 152"><option value=0>Select</option> </select></td>
			<td  align="left" style="width: 446px"><input type="text" name="desc" id="desc" style="width: 410px"></input></td>
			
			 
		 </tr>
		  <tr class="tdH">
			<td align="center" colspan="4">  
			<input type="button" value="Add" class="btn" onclick="master(2,1)"/> 
		     <input  class="btn" type=button value="Update"  onclick="master(2,2)"/> 
			<input type=button value="Delete"  class="btn" onclick="master(2,4)"/>
			<input type="button" value="Report" class="btn" onclick="report(5)"/>
			<input type="reset" value="Clear"  class="btn" onclick="javascript:window.location.reload()"/>
			<input type="button" value="HELP" onclick="javascript:window.open('./twad_note.html#m3',100,100);"/> 	
			<input type=button value="Exit"  class="btn" onclick="window.close()"/>		
		 </td>
		 </tr>
		<tr><td align="center" colspan="4">
		 
		<table id="etable"   border="1"  align="center" width="100%"  style="border-collapse: collapse;border-color: skyblue"  >
		 <tr class="tdH">
			<th width="10%" align="center"> Edit</th>
			<th  align="center"> Expenditure Group  </th>
			<th  align="center"> Expenditure Main Item  </th>
			
		</tr> 
		<tbody id="edata" align="left" valign="top"/>
		 
      	</table>
		 	
		</td>
		
		</tr>
		
	<input type="hidden" id="rowcount" name="rowcount" value="0"/>	
	<input type="hidden" id="key_value" name="key_value" value="0"/>	
</Table>
</body>
</html>
 