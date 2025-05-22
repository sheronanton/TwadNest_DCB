<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pay Elements Master</title>
<meta http-equiv="Content-Type" content="text/html;charset=windows-1252"/>
<link href="../../../css/Sample3.css" rel="stylesheet" media="screen"/>
<link href="../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../scripts/Sec_MST_Templates_Master.js">  </script>
</head>

<body onload="call('get')">
 <form name="Sec_MST_Templates" id="Sec_MST_Templates">
 
  <table cellspacing="3" cellpadding="2" border="1" width="70%" align="center">
  <tr><th colspan="2" class="tdH">Role Templates Master</th></tr>
  		  <tr>
            <td class="table">Role Template ID</td>
            <td><input type="text" id="role_template_id" size="4" disabled="disabled"/> (Auto Generated)
            <input type="hidden" id="role_template_id" size="4"/></td>
          </tr>
           <tr>
            <td class="table">Role Template Name</td>
            <td><input type="text" id="role_template_name" />  </td>         
          </tr>
          <tr> 
            <td  class="table" colspan="2" align="center">
            	<input type="button" name="add" id="add" value="Add"  onclick="call('Add')"/>
            	<input type="button" name="update" id="update" value="Update"  onclick="call('Update')" disabled/>
            	<input type="button" name="delete" id="delete" value="Delete"  onclick="call('Delete')" disabled/>
            	<input type="button" name="clear" id="clear" value="Clear"  onclick="clearAll()"/>
            </td>
          </tr>         
  </table>
  
  <br></br>
 <table  cellspacing="2" cellpadding="3" border="1" width="70%"  align="center">
	 <tr class="tdH">
	 	<th>Select</th>
	 	<th>Template Id</th>
	    <th>Template Name </th>
	 </tr>
	  <tbody id="tlist" class="table"></tbody>
</table>

</form>
</body>
</html>
