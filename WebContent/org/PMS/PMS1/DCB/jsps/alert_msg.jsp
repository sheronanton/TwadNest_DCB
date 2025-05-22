

<%@page import="java.util.*,java.sql.*"%>
 <%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller" %>
 <head>
  </head>
 <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
 
<%
		String c="";
		Controller obj=new Controller();
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);  
		int month = cal.get(Calendar.MONTH)+1 ;
		int year = cal.get(Calendar.YEAR);
		String Date_dis=day+"/"+(month+1)+"/"+year;
		String msg=obj.setValue("msg",request); 
		String flag=obj.setValue("flag",request);
		;
		if (Integer.parseInt(flag)==1)
			c="red";
		else
			c="green";
%> 
<body bgcolor="#82CAFF"> 
<form name="msg">

<table summary="Standard alerts box" class="alerts" cellspacing="0" align="left" border=0 cellpadding="5" bordercolor="	#7F4E52" >
<tr><td class="alertHd" align="left"><font color=blue>DCB</font></td>
<td   class="alertHd" align="right">Message Box </td>
</tr>
 
<tr>
<td colspan="2" ><font color='<%=c%>' size="2"><%=msg%></font></td>
</tr>
<tr>
<td colspan="2"  align="center"><input type="button" value="Close" class="cbutton" onclick="window.close()"> </td>
</tr>
</table> 
</form>
</body>