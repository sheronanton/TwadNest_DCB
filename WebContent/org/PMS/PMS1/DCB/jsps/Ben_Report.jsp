 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<style type="text/css">
			.selectbox
			{
			  font-family: Verdana, Geneva, Arial, Helvetica, sans-serif;
			  font-size: 8x;
			  display : red;   
			} 
	</style>

	<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Beneficiary   | TWAD Nest - Phase II </title>
	<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
	<script type="text/javascript" src="../scripts/Ben_Report.js"></script>
	<script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
	<script type="text/javascript" src="../scripts/cellcreate.js"></script>
	<script type="text/javascript" src="../scripts/master.js"></script>
	
	<script language="javascript" type='text/javascript'>
		function sh()
		{
			
			document.getElementById('block').style.visibility = 'hidden';
			document.getElementById('dis').style.visibility = 'hidden';
		}
	</script>

</head>
<body onload="clickIE4(),sh(),data_show_dcb('show',2,'bentype'),ben_report_show('show',1,0),report('show',1,'dis_value','')">

<table align=center width="95%" border=0 cellspacing="0" cellpadding="2" height="40">
 	<tr class="tdH">
 	        <td align="center" colspan=8    height="10"> <font   size="2"><b>Beneficiary View</b></font></td>
 	</tr>
 	
	<tr>
			<td  style="width: 357px"><font  class="tdText" size="2"> Beneficiary Type&nbsp;:&nbsp; </font> <select  class=select id="bentype" name="bentype" onchange="ben_report_show('show',2,this)" style="width:150pt"></select>
          	<td style="width: 309px"><div id="dis"><font    class="tdText" size="2"> District </font> &nbsp;:&nbsp; <select id="dis_value"  class=select name="dis_value" onchange="ben_report_show('show',3,this),report('show',2,'block_value',this)" style="width:150pt"></select></div></td>
         	<td style="width: 442px"> <div id="block"><font    class="tdText"> Block </font> &nbsp;:&nbsp;<select id="block_value" class=select  name="block_value"  onchange="ben_report_show('show',4,this)" style="width:150pt"> /select></div>&nbsp;&nbsp;&nbsp;<label id="msg"></label></td>
	</tr>
	
 	<tr valign="top" height=670 >
			<td colspan="5"   >
				<div id='scroll_clipper' style='position:absolute; width:1150px; border-height:3px; height: 670px; overflow:auto;white-space:nowrap;'>
					<div id='scroll_text'  align="center" style="height: 72px; width: 1000px">
						<table bordercolor="f0f0f0" id="entred_data" align=center width="100%" border=1 cellspacing=0 cellpadding=1>
							<tbody id="entred_body_head" align="left" ></tbody>
							<tbody id="entred_body" align="left"   ></tbody>
	         			</table>
					</div>
				</div>
			</td>  
	</tr>
	 			 
	<tr align="center" colspan="3">
		  <td align="center" colspan="3">
				<input type="button" value="EXIT" onclick="self.close();" class="fb2">
		  </td>
	</tr>	 	
 </table> 
    	<input type=hidden id="pr_status" name="pr_status" value="0">  
 		<input type="hidden" id="subdiv" value="5110">  
</body>
</html>