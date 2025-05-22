<%@ page language="java" import="java.util.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.sql.*,Servlets.PMS.PMS1.DCB.servlets.*"%>
<%@page import="java.util.Enumeration"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="admin_level.jsp" method="post">
<%
	String combo = "";
	String tab_head = "", tab_value = "";
	try {
		Controller obj = new Controller();
		Connection con = obj.con();
		ArrayList al=obj.tableName(con);		
		Iterator ir=al.iterator();		
		combo = "<select id='tab' name='tab'>";
		while (ir.hasNext()) 
		{
			String tab=ir.next().toString();
			combo += "<option value='"+tab+"'>"+tab+ "</option>";
		}
		combo += "</select>";
		String but = obj.setValue("b1", request);
		String tab = obj.setValue("tab", request);
		if (but.equalsIgnoreCase("->")) {
			tab_head = obj.columnName(con, tab, 1);
			tab_value = obj.columnValues(con, tab, 1);
		}
		
		
		
%> <%
 	} catch (Exception e) {
 		out.println(e);

 	}
 %>

<table align="left" width="15%" border="1">
	<tr>
		<td width="15%"><%=combo%> <input type="submit" value="->"
			name="b1"><input type="submit" value="Excel"></td>

	</tr>
	<tr>

	</tr>

</table>

 
		<div id='scroll_clipper'
			style='position: relative; vertical-align: top; width: 872; border-height: 1px; height: 570px; overflow: auto; white-space: nowrap;'>
		<div id='scroll_text'>
		 
		<table><tr bgcolor="#cc00dd"><%=tab_head%></tr>
		<%=tab_value%></table>
		</div>
		</div>
		 
</form>
</body>
</html>