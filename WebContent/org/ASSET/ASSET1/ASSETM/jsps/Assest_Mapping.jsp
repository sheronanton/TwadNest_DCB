<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Asset  - Mapping </title>
 <script type="text/javascript" src="../scripts/cellcreate.js"></script>
    <script type="text/javascript" src="../scripts/Assest_Mapping.js"></script>
    <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript">
    function fcheck(a)
    {
 
    	var comp_=document.getElementById('comp_').value;
    	var sub_comp_=document.getElementById('sub_comp_').value;
    	var form_name=document.getElementById('form_name').value;
    	if (comp_==0 || sub_comp_==0 || form_name==0 )
    	{		
    		alert(" Select Component , Sub Component ,Form Name ");  
    	}
    	else
    	{
        	if (a==1)
    		map_submit();
        	else if (a==2)
        	up_submit();
        	else if (a==3)
        	del_submit()
    	}
    }  
        
    </script>
</head>
<body onload="comp('comp','comp_')  ">
 <%
 	Connection con;
	Controller obj=new Controller();
	String msg="";
 try
	{
		
		con=obj.con();
		 
		obj.createStatement(con);
	}catch(Exception e)
	{   
		out.println(e);
	}
	msg=obj.isNull(obj.setValue("msg",request),4);  
 	if (!msg.equalsIgnoreCase("0"))
	{%>
		<script type="text/javascript">
		alert('<%=msg%>')  
		</script>
		
	<%}
 %>
 <table align="center" width="75%" border="1" style="border-collapse:collapse; " cellpadding="5" cellspacing="0" bordercolor="skyblue">
 		<tr>
 				<td align="center" colspan="2"> Component - Sub Component - Form Mapping </td>
 		</tr>  
 		<tr>
 			<td width="20%"> Component </td>
 			<td> <select id="comp_"  class=select  name="comp_" onchange="subcomp('subcomp',this,'sub_comp_')" style="width:200pt"><option value=0>Select</option></select></td>
 		</tr>
 		<tr>
 			<td width="20%">Sub Component </td>
 			<td> <select id="sub_comp_"  class=select  name="sub_comp_" onchange=" " style="width:200pt"><option value=0>Select</option></select></td>
 		</tr>
 		<tr>
 			<td width="20%">Form Name </td>
 			<td> <select id="form_name"  class=select  name="form_name" onchange=" " style="width:200pt"><option value=0>Select</option></select></td>
 		</tr>
 		<tr>
 		
		<td align="center" colspan="2"> <input type="hidden" id="d">
			<input type="button" name="store" value="Submit" onclick="fcheck(1)" >
			<input type="button" name="update" value="Update" onclick="fcheck(2)">
			<input type="button" name="update" value="Delete" onclick="fcheck(3)">
			<input type="hidden" id="MAP_SNO">
			<input type="button" value="Exit" onclick="window.close()"> 
			<input type="Reset" value="Clear" onclick="javascript: window.location.reload()" >  
		</td>
	</tr>
	
	 
 		
 </table>
 <table align="center" width="75%" border="1" style="border-collapse:collapse; " cellpadding="5" cellspacing="0" bordercolor="skyblue">
	
					<tr>
						 <td align="center">&nbsp;</td>
						<td align="center">Component</td>
						<td align="center">Sub Component </td>
						<td align="center">Form Name </td>
					</tr>
					<tbody id="tblList" name="tblList" class="tdText">
	
	
</table> 
 
 
</body>
</html>