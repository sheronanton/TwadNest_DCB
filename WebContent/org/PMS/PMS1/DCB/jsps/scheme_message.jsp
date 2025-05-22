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
		var s=window.open("Scheme_Change.jsp","");
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
				 
				<td colspan="2" align="left"><b>Transfer beneficiaries to another division</b> 		 
						<ol>
								  <li>Beneficiaries under same scheme<br>
								  			<ul type="square">  <br>
								  			<li>Using your PMS login do scheme mapping to include the scheme under the division to be transferred.</li><br>
											<li>Follow the Beneficiary change procedure as per menu.</li><br>
											</ul><br><br>
											Note: To know the procedure to do scheme mapping contact TWAD, HO PMS Co-ordinator<br><br> 
										</li>
									<li>Beneficiaries under different Scheme
											<ul type="square"> <br> 
													<li>Follow the Beneficiary change procedure as per menu</li>
											</ul>
								 </li>
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