<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Beneficiary Change </title>
<script type="text/javascript">
	function hide(flag)
	{
		if (flag==1)
		document.getElementById('but').disabled = true;
		else 
			{
			if (document.getElementById('chk').checked)
		 	document.getElementById('but').disabled = false;
			else
			document.getElementById('but').disabled = true;
			}
	}
	function reload()
	{
        window.location="tariff_rate_change.jsp";
	}
</script>
</head>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<body onload=" hide(1)">

		<table cellpadding="10" align="center" style="height: 1em;">
		<tr>
			<th colspan="2">Instructions &nbsp;</th>
		</tr>
		
		<tr> 
		<td colspan="2" align="left"> 		 
						<ol>
								  <li>Pls. consult concerned division before applying Tariff Revision<br>
								  (Concerned Divison would have already finished Tariff Rate Revision using Tariff Edit Routine)
								  </li><br>
								  <li>Some division  may have a different Tariff Rate than prescribed Tariff Rate for Local Body</li><br>
								  <li>Any Previous month demand bills should not be generated after applying Tariff Revision</li><br>
								  <li>Some Exceptional Tariff  is possible for some divisions other than prescribed Tariff Rate for Local bodies.<br>  
                                       Such divisions are advised to Update Tariff Rate  using Tariff Rate  Edit Option.</li><br>
								  <li>Tariff Rate Update  to be done before Pumping Return Entry<br> Example: <br>
								  If Tariff Rate has changed w.r.t April 2018 Tariff Rate Editing to be done before PR Entry for correct Water Charge Calculation.
	                              </li><br>
	                              <li>Tariff Rate Edit is  Not Possible After Water Charge Freezing for that Transaction month as per Month Setting.</li>
								 
	</ol>	
			</td>
		</tr> 
		<tr>
			<td><input type=checkbox id='chk' onchange="hide(2)" value="0"> I have read the instructions</td>
			<td align="right"><input type="button" value="Exit" onclick="window.close()"><input type="button" value='Proceed' id='but' onclick="reload()"></td>
		</tr>
		<tr>
			<th colspan="2" >&nbsp;</th>
		</tr>
		</table> 


</body>
</html>