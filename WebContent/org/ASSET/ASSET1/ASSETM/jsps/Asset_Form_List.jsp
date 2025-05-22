<html>
<%@ page language="java" import="java.io.File,java.sql.Connection,java.io.File,java.sql.ResultSet" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Hashtable" %>

<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Asset Form Master </title>
</head>
<body>
   <link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet"media="screen" />
 <form action="Asset_Form_List.jsp" method="post">
  
 <%
 String str="";
 ResultSet rs=null;
 try  
 {
	 	Controller obj=new Controller();
	 	Connection con=obj.con();
	 	obj.createStatement(con);
	 	
	 
	 	File f=new File(request.getRealPath("/org/ASSET/ASSET1/ASSETM/jsps/"));
 		String []ls=f.list();
 		  str=" <select id='filename' name='filename'>";
 			   str+="<option>Select File </option>";	
 		for(int i=0;i<ls.length;i++)
 		{
 				str+="<option>"+ls[i]+"</option>";
 			
 		}
 		 str+="</select>";
 		 
 		 
 		String store=obj.setValue("store",request);
 		
 		if (store.equalsIgnoreCase("Submit"))
 		{
 		String formname=obj.setValue("formname",request);
 		String flong=obj.setValue("flong",request);
 		String filename=obj.setValue("filename",request);
 		
 		Hashtable ht=new Hashtable();
 		int max=obj.getMax("PMS_ASSET_FORM","FORM_SNO","");
	 		ht.put("FORM_SNO",max);
	 		ht.put("FORM_DESC","'"+formname+"'");
	 		ht.put("FORM_LONG_DESC","'"+flong+"'");
	 		ht.put("FORM_JSP","'"+filename+"'");
	 		obj.recordSave(ht,"PMS_ASSET_FORM",con);		
 		
 		} 
 		
 		 rs=obj.getRS("select * from PMS_ASSET_FORM order by FORM_SNO ");
 			  
 }catch(Exception e)
 {
	 out.println(e);
 }
 %>
 
 <table align="center" width="50%" border="1">
 	<tr>
 		<td align="center" colspan="2"> Asset Form Master </td>
 	</tr>
	<tr>
			<td>Form Name : </td>
			<td><input type=text id="formname" name="formname"></td>
	</tr> 
 	<tr>
			<td>Form Long Description : </td>
			<td><input type=text id="flong" name="flong"></td>
	</tr>
	<tr>
			<td>File Name</td>
			<td><%=str %></td>
	</tr>
	
		<tr>
 		
		<td align="center" colspan="2"> <input type="hidden" id="d">
			<input type="Submit" name="store" value="Submit"  >
			<input type="button" name="update" value="Update" onclick="fcheck(2)">
			<input type="button" name="update" value="Delete" onclick="fcheck(3)">
			<input type="hidden" id="MAP_SNO">
			<input type="button" value="Exit" onclick="window.close()"> 
			<input type="Reset" value="Clear" onclick="javascript: window.location.reload()" >  
		</td>
	</tr> 
	<tr>
	<td colspan="2">
	<table width="100%"  border="1">
		 <tr  class = "tdH" align="center" bgcolor="gray">
			<td>Form Name  </td>
			<td>Form Long Description  </td>
			<td>File Name</td>	
	</tr>
	<%
		String FORM_DESC="",FORM_LONG_DESC="",FORM_JSP="";
		
		while(rs.next())
		{
			FORM_DESC=rs.getString("FORM_DESC");
			FORM_LONG_DESC=rs.getString("FORM_LONG_DESC");
			FORM_JSP=rs.getString("FORM_JSP");
		%>
		<tr>
			<td><%=FORM_DESC%></td>	
			<td><%=FORM_LONG_DESC%></td>
			<td><%=FORM_JSP%></td>
		</tr>
	 	<%
			}
	 	%>
	 	</table>
	 	</td>
	 	</tr>
	 	
 </table>
 </form>
</body>
</html>