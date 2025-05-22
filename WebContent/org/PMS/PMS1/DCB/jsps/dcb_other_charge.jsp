<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
 	<link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
 	<script type="text/javascript" src="../scripts/cellcreate.js"></script>
 	<script type="text/javascript" src="../scripts/dcb_other_charge.js"></script>
 	<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
 	<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
  	<body onload="charge_show('show',1,'0')">
	<table   id="charge_data" width="50%" align=center border=1 cellspacing="0" cellpadding="3"   >
	<tr class="tdH" >
       	  <td colspan=5 align=center >
          			DCB - Other Charges 
          </td>
    </tr>
	<tr>
			<td width=2% align="center"> <font size=2 > Sl.No</font> </td>
 			<td width=2% align="center"><font size=2 > Select</font> </td>
 			<td width=30% align="center"> <font size=2 >Charge Type  </font> </td>
			<td width=10% align="center"> <font size=2 > A/C Code </font> </td>
			<td width=2% align="center"> <font size=2 > Add Amount to Demand</font> </td>
 	</tr>
			<tbody id="charge_body" align="left"></tbody>
	</table> 
	<table cellspacing="0" cellpadding="0" border="0" width="50%" align="center" >
    <tr>                            
    		<td class="tdH">
        	<div align="center">
	          	<input type="button" name="add" value="Submit" class="fb2" id="add" onclick="charge_show('add',3,0)" align="middle"/>
	            <input type="button" name="add" value="Update" class="fb2" id="add" onclick="charge_show('update',0,0)" align="middle"/>
	          	<input type="button" name="" value="Refresh" class="fb2" id="clear" align="middle" onclick="window.location.reload(true)"/>
	          	<input type="button" name="exit" value="Delete" class="fb2" id="exit" onclick="self.close()" align="middle"/>
	          	<input type="button" name="exit" value="Exit" class="fb2" id="exit" onclick="self.close()" align="middle">
            </div>                   
          		</td>
          	</tr>
      </table>
</body>
<input type=hidden id="rowcnt" name="rowcnt"> 
</html>