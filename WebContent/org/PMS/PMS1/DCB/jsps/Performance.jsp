<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><%@page import="Servlets.PMS.PMS1.DCB.servlets.*"%>
<%@page import="java.util.*,java.sql.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/Performance.js"></script>
<script type="text/javascript">
function select(a)
{
var sno=document.getElementById("PERFORM_DESC_SNO"+a).value; 
document.getElementById("ssno").value=sno;
var desc=document.getElementById("PERFORM_DESC"+a).innerHTML;


var flag_val=new String(document.getElementById("FYEAR_MTH_FLAG"+a).innerHTML);
var ronly=new String(document.getElementById("READONLY"+a).value);
 var Formula=new String(document.getElementById("Formula"+a).innerHTML);
 var MANDATORY=new String(document.getElementById("MANDATORY"+a).innerHTML);
 
				var i=0;    
				var len=document.getElementById("fid").options.length;
				for (i=0;i<len;i++)
				{
					 var optn = document.getElementById("fid").options[i];
					 var value=new String(optn.value);
					 if (value.toUpperCase()==flag_val.toUpperCase())
          	     	 {
              	     optn.setAttribute("selected","1");
              	     break;
          	     	 } 
				}	
				 

				   
					var dd1 = document.getElementById('rid');
					for (var i = 0; i < dd1.options.length; i++) 
						{	
						 var valuec=new String(dd1.options[i].value);	
					    if (valuec.toUpperCase() == ronly.toUpperCase())
						 {
					    	dd1.selectedIndex = i;
					        break;  
					     }
					}	

				
				var k=0;   
			 
				var dd = document.getElementById('man');
				for (var i = 0; i < dd.options.length; i++) 
					{
				    if (dd.options[i].value == MANDATORY)
					 {
				        dd.selectedIndex = i;
				        break;
				     }
				}

				
var userid=document.getElementById("UPDATED_BY_USER_ID"+a).value; 
var time=document.getElementById("UPDATED_TIME"+a).value; 
var unit=document.getElementById("UNITS"+a).innerHTML;

var dlim=document.getElementById("DLIMIT"+a).innerHTML;
var dord=document.getElementById("DISPLAYORDER"+a).innerHTML;
var dtype=document.getElementById("DATATYPE"+a).innerHTML;

document.getElementById("sid").value=sno;
document.getElementById("did").value=desc;
//document.getElementById("fid").value=flag;
//document.getElementById("bid").value=userid;
//document.getElementById("aid").value=time;
document.getElementById("uid").value=unit;
document.getElementById("rid").value=ronly;
document.getElementById("lid").value=dlim;
document.getElementById("oid").value=dord;
document.getElementById("tid").value=dtype;
document.getElementById("Formula").value=Formula;  
				 
			   var ACTIVE_STATUS=document.getElementById('ACTIVE_STATUS'+a).innerHTML;
				var dd1 = document.getElementById('astatus');
				for (var i = 0; i < dd1.options.length; i++) 
					{					 
				    if (dd1.options[i].value == ACTIVE_STATUS)
					 {
				    	dd1.selectedIndex = i;
				        break;  
				     }
				}	    
}
</script>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/> 
</head>
 	
<body onload="view()">
<%
int id=0;
try{
	Controller obj = new Controller();
	Connection con = obj.con();
	obj.createStatement(con);
	id=obj.getMax("PMS_AME_MST_DESC","PERFORM_DESC_SNO","");
	//out.println("id"+id);
	}
catch(Exception e){}
%>
 
<form>
<table border="1" align="center" width="100%">

	<tr class = "tdH">
		<th align="center" colspan="2">Scheme Performance Master<input type="hidden" id="sid" readonly="readonly" name="sid" value=<%=id%>>
		</th>
	</tr>
	<tr>  
		<td>Scheme Performance Description</td>
		<td><input type="text" id="did" name="did" size="100"></td>
	</tr>
	<tr>
		<td>Month or Year </td>
		<td>
			<select id="fid" name="fid">
				<option value="0">Select</option>
				<option value="y">YEAR</option>
				<option value="m">MONTH</option>
			</select>
		</td>
	</tr>

	<tr>
		<td>Units</td>
		<td><input type="text" id="uid" name="uid"></td>
	</tr>
	<tr>
		<td>Column Readonly</td>
		<td>
			<select id="rid" name="rid">
				<option value="0">Select</option>
				<option value="y">YES</option>
				<option value="n">NO</option>
			</select>
		</td>
	</tr>
	<tr>
		<td>Decimal Delimit</td>
		<td><input type="text" id="lid" name="lid" maxlength="3" size=3></td>
	</tr>
	<tr>
		<td>Display Sequence</td>
		<td><input type="text" id="oid" name="oid" maxlength="3" size=3></td>
	</tr>
	<tr>
		<td>Formula</td>
		<td><input type="text" id="Formula" name="Formula" maxlength="103" size=100></td>
	</tr>
	<tr>
		<td>Content Type</td>
		<TD><select id="tid" name="tid">
			<option value="0">Select</option>
			<option value="1">INTEGER</option>
			<option value="2">FLOAT</option>
			<option value="3">DATE</option>
			<option value="4">CHAR</option>
		</select></TD>
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
		<td>Mandatory</td>
		<td>
			<select id="man" name="man">
				<option value="0" >Select</option>
				<option value="Y">YES</option>
				<option value="N">NO</option>
			</select>
		</td>
	</tr>
	<tr>
		<th colspan="2">
		<input type="button" value="Add" onclick="add()"/>
		<input type="button" value="Update" onclick="update()"/> 
		<input type="button" value="Delete" onclick="delete_()" />
		<input type="reset" value="Clear" />  
		<input type="button" value="Exit" onclick="javascript:window.close();"/>  
		</th>
	</tr>  
	<tr>
	<td colspan="2">  
	<table width="100%" align="center" cellspacing="0" cellpadding="0"  style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
		<tr>
			<td class="tdText" align="center">SELECT</td>
			<td class="tdText" align="center">Scheme Performance Description</td>
			<td class="tdText" align="center">Month or Year</td>
			<td class="tdText" align="center">Units</td>
			<td class="tdText" align="center">Column Readonly</td>
			<td class="tdText" align="center">Decimal Delimit</td>
				<td class="tdText" align="center">Formula Sequence</td>
			<td class="tdText" align="center">Display Sequence</td>
			<td class="tdText" align="center">Content Type</td>
			<td class="tdText" align="center">Formula</td>
			<td class="tdText" align="center">Mandatory Status</td>
			<td class="tdText" align="center">Active Status</td>

		</tr>  
		<tbody id="tblList" name="tblList" class="tdText">
		</tbody>
	</table>
	</td>
	</tr> <input type="hidden" id="ssno">
</table>
</form>
</body>
</html>