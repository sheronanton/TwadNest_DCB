<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.io.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.ArrayList,java.util.Iterator,Servlets.AME.AME1.AMEM.servlets.excel2"%><html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/adminhelp.js"></script>
<link href="../../../../../css/txtbox.css" rel="stylesheet"
	media="screen" />
	
<script  type="text/javascript" language="JavaScript">
function sel(a,e)
{
    var d=e.keyCode;
	var sr=a.value;
	var str=new String(document.getElementById("query").value);	
	if (d==13)
	{
			var t2 = document.getElementById("query");
		    t2.focus(); 
		    t2.value =t2.value + " " + sr;  
	}	
}
function sel2(a1,e)
{
	var d=e.keyCode; 
	var sr=a1.value;
	var str=new String(document.getElementById("query").value);
	
	if (d==13)
	{
		 var t2 = document.getElementById("query");
		    var s=t2.value;
		    t2.value="";
		    s+=""+sr;
		    t2.value=s;
		    s="";  
	}	
}

function getCaretPosition(elm) {
	if (typeof elm.selectionStart != "undefined")
	return elm.selectionStart;
	else if (document.selection)
	return Math.abs(document.selection.createRange().moveStart("character",-1000000));
	}

function pls()
{
	var pos=getCaretPosition(document.getElementById('query'));
 
}
</script>	  
</head>
<body>  

<form name="adminhelp.jsp" method="post">
<%
	Controller obj = new Controller();
	Connection con = obj.con();
	ArrayList ar = null;
	ArrayList ar1 = null;
	String b1 = obj.setValue("b1", request);
	String query = obj.setValue("query", request);
	String ename = obj.setValue("ename", request);
	out.println(query.indexOf('('));
	query=query.replace('(','-');  
	
	
		if (query.equalsIgnoreCase("0")) query="";  
	try {
		if (b1.equalsIgnoreCase("Run")) 
		{  
			out.println(query);
			if (!query.equalsIgnoreCase(""))
			{
				if (query.trim().startsWith("select", 0)) 
				{
					
					ar = obj.columnNames(con, query);
					ar1 = obj.columnValues(con, query, obj);
					%>
					<script type="text/javascript">
					function rld()
					{
						help(document.getElementById('tbname'));
					}  
					</script>
					<%
				}
			}
		}
		
		if (b1.equalsIgnoreCase("Excel"))
		{
		 try
	 	  {  
	 	  	  excel2.excel_write(new File(application.getRealPath("/excel")),ename+".xls",con,query);
	 	  }catch(Exception e)
	 	  {
	 		  out.println(e);
	 	  }
		}
	} catch (Exception e) {

		out.println(e);
	}    
%>    
<table cellpadding="0" cellspacing="0" border="1" width="100%"
	style="border-collapse: collapse; border-color: gray">
	<tr>
		<td width="20%" valign="top">  
			<input type="submit" name="b1" value="Run"  ><input type="submit" name="b1" value="Excel"  ><input type="reset" name="rest" value="Clear"><input type=Button name="rest" value="Exit" onclick="window.close()">
		 <BR> File Name <input type="text" name="ename"></td>  
		<td  width="80%" align="left"><textarea name="query" id="query" rows="5"  cols="95" 
			style="  color:blue; font-size: 1.1em; border: 0;width: 150"><%=query%></textarea>
		</td>
		  
	</tr>     
</table><br> 
<table align="center" border="1" width="100%" cellpadding="0" class="select2"  
	cellspacing="0" style="border-collapse: collapse;">
	<tr>
		<Td valign="top" width="20%">    
		<div class="styled-select">
		   <select multiple="multiple"    name="tbname" id="tbname" onchange="help(document.getElementById('tbname'))" onkeydown="sel(this,event)">
				<%  ArrayList tab = obj.tableName(con);    
					try {
						Iterator taber = tab.iterator();
							while (taber.hasNext()) 
							{
								String val1 = taber.next().toString();
								out.println("<option value="+val1+">" + val1 + "</option>");
							}
	
					} catch (Exception e) {
					}
				%>
			</select>
		</div>	
		<b><label id='tt'></label></b> 
		<br> 
		     <div class="styled-select"><select multiple="multiple"  id="column" class='select3' style="font-size: 1.45ex;" onkeydown="sel2(this,event)"></select></div>
		</td>
	  <td width="80%">   
		<div style="height: 510px; width: 1040px; overflow: scroll;background-color: lightgray;" class="styled-select">
	     <table align="left" border="0" width="100%" cellpadding="0" cellspacing="0" style="border-collapse: collapse; border-color: gray">
			<tr>
				<td>

				<table align="left" border="1" width="100%" cellpadding="0" cellspacing="0" style="border-collapse: collapse; border-color: gray">
					<%
						try {
							Iterator er = ar.iterator();
								while (er.hasNext())   
								{
									String val = er.next().toString();
									out.println("" + val + "");
								}
							Iterator er1 = ar1.iterator();

							while (er1.hasNext()) {
								String val1 = er1.next().toString();
								out.println("" + val1 + "");
							}
						} catch (Exception e) {
						}
					%>
				</table>
				</td>
			</tr>
		</table>
		</div>
	 </td>
	</tr>
</table>
	<input type="thidden" name="cur" id="cur" value=""> 	
</form>
</body>
</html>