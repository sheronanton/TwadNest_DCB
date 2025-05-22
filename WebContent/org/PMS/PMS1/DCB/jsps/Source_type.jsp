<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet"media="screen" />

<script type="text/javascript" src="../scripts/cellcreate.js"></script>  
<script type="text/javascript" src="../scripts/Source_type.js"></script>
<script type="text/javascript">


function select(a)
{
var id=document.getElementById("ID"+a).value; 
var DESCRIPTION=document.getElementById("DESCRIPTION"+a).innerHTML;

document.getElementById("typeid").value=id;
document.getElementById("desc").value=DESCRIPTION;
}
</script>
</head>
<body onload="view_js()">
<%						//automatic id value set
int id=0;
try {
Controller obj=new Controller();
			Connection con = obj.con();
			obj.createStatement(con);
			id=obj.getMax("SOURCE_TYPE", "ID", "");
}catch(Exception e)
{
}
 %>                             


 <form>							 
 		<table align="center" width="75%" border=1>
			
 			
 			<TR class="tdH" >
 				<td colspan="2" align="center">Source Type Master  </td>
 			</TR>
 			<tr>
 				<Td>ID</Td>
 				<td><input type="text" id="typeid" readonly="readonly" size="3" name="typeid" value="<%=id%>"></td>
 				
 			</tr>			
 			<tr>
 				<Td> Source Description </Td>
 				<td><input type="text" id="desc" name="desc" size="100"></td>
 				
 			</tr>
 			<tr>
 				<td colspan="2">
 					<input type=button value="Add" onclick="add_js()">
 					<input type=button value="Update" onclick="update_js()">
 					<input type=button value="Delete" onclick="delete_js()">
 					<input type=button value="Exit" onclick="javascript:window.close();">
  				</td>
 			</tr>
 		</table>
 		 <table  class="fb2" id="existing"   width="75%" align="center" cellspacing="0" cellpadding="0" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
        <tr class="tdH">
            <td class="tdText" align="center">Select</td>
            <td class="tdText"  align="center">Description</td>
            
        </tr> 
        <tbody id="tblList" name="tblList" class="tdText">
        </tbody>
    </table>
 </form>
 
 
</body>
</html>
