<?xml version="1.0" encoding="ISO-8859-1" ?> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type " content="text/html; charset=ISO-8859-1" />
<title>Group Master</title>
<script type="text/javascript"> 
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
			<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/> 
			<script type="text/javascript" src="../scripts/master.js"></script>
			<script type="text/javascript" src="../scripts/reports.js"></script>
			<script type="text/javascript" src="../scripts/cellcreate.js"></script>
</head>
<body onload="page_size(),master(1,3)">
<form> 
<table class=table width=100% align="center" border="1" style="border-collapse: collapse;border-color: skyblue"   >
		<tr> 
			<th  colspan="2" align="center" class="tdH" >Expenditure <label class="headlbl"> Group Master </label> </th>		 
		</tr>
		  
		<tr> 
			<td align="left"></td>          
		</tr>		
		<tr> 
		 	<td align="left"> <label class="lbl">Expenditure Group </label></td>
			<td align="left"><input type="text" name="desc" id="desc" class="tb4"/></td>		
		 </tr>		  
		 <tr class="tdH">		 	
			<th align="center" colspan="2" >
					<input type="button" value="Add" class="btn" id="add" onclick="master(1,1)"    />
					<input  type=hidden value="Update" class="btn" onclick="master(1,2)"/>
					<input type=hidden value="Delete"  class="btn" onclick="master(1,4)"/>
					<input type="button" value="Report" class="btn" onclick="report(5)"/>
					<input type="reset" value="Clear"  class="btn" onclick="javascript:window.location.reload()"/>					 				  		
					<input type="button" value="HELP" onclick="javascript:window.open('./twad_note.html#m2','_blank','toolbar=no, location=yes, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=yes, width=700, height=600')"/>	 
					<input type=button value="Exit"  class="btn" onclick="window.close()"/>
			</th>					  
		</tr>  
		<tr>  
		<td width=100%  align="center"  colspan="2">	 
		<table class="table" id="etable"  border="1"  align="center" width="100%" border="1"  style="border-collapse: collapse;border-color: skyblue"  >  
		<tr class="tdH">
			<th width="20%" align="center"> Edit</th>
			<th  align="center"> Expenditure Group </th>
		</tr> 
		<tbody id="edata" align="left" valign="top"/>		 
      	</table>      		 	
		</td> 
		 
		</tr>		
</table>
	<input type="hidden" id="uprow"  value=0/>
	<input type="hidden" id="rowcount" name="rowcount" value="0"/>
	</form>
</body>
</html> 