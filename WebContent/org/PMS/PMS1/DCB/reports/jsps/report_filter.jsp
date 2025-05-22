<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller"%>
<%@page import="java.sql.*"%>
<%@page import="java.sql.PreparedStatement"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="../css/txtbox.css" />
<title>Report Filter</title>
 <script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
	function rld()
	{
		document.location.href="report_filter.jsp";
		document.forms["frm"].submit();		
	}
	function rld3()  
	{
		var tab2=document.getElementById('tab2');
		var len=tab2.options.length;
		var columns_list="";
		for(i=0;i<len;i++)
		{
			if (i==0)
				columns_list="";
			else
				columns_list=columns_list+",";
			var v=tab2.options[i].value;
			columns_list=columns_list+""+v;
		}
		document.getElementById('col_list').value=columns_list;

		var tab6=document.getElementById('tab6');
		var len3=tab6.options.length;
		var columns_order_list="";
		for(i=0;i<len3;i++)
		{
			if (i==0)
				columns_order_list="";
			else
				columns_order_list=columns_order_list+",";
			var v=tab6.options[i].value;
			columns_order_list=columns_order_list+""+v;
		}
		document.getElementById('order_col_list').value=columns_order_list;

		
	 	var add="columns_list="+columns_list+"&spl="+document.getElementById('spl').value+"&columns_order_list="+columns_order_list+"&tab="+document.getElementById('tab').value;

		window.location.href="excel_report_gen.jsp?"+add;
		//document.forms["frm"].submit();		
	}
	
	function rld1()
	{
		var first = document.getElementById('tab1');
	    var second = document.getElementById('tab2');
	    addOption(document.getElementById('tab1').options[document.getElementById('tab1').selectedIndex].value , second);	
	}
	function rld5()
	{
		var first = document.getElementById('tab4');
	    var second = document.getElementById('tab6');
	    addOption(document.getElementById('tab4').options[document.getElementById('tab4').selectedIndex].value , second);	
	}
	 function dup(fld)
	 {
		 var tab6=document.getElementById(fld);
		 var len3=tab6.options.length;
		 
		 for(i=0;i<len3;i++)
			{
				
				if (i==0)
					columns_order_list="";
				else
					columns_order_list=columns_order_list+",";
				var v=tab6.options[i].value;
				columns_order_list=columns_order_list+""+v;
			}
	}
 
	function addOption(val, select)
	 {
		  var option = document.createElement('option');
		  option.value = val;
		  option.innerHTML = val;
		  select.appendChild(option);
		}
	function remove()
	{
		var v=document.getElementById('tab2').selectedIndex;
		if (v==-1)
			v=document.getElementById('tab2').options.length-1;
			document.getElementById('tab2').remove(v);
	}
	function remove2()
	{
		var v=document.getElementById('tab6').selectedIndex;
		if (v==-1)
			v=document.getElementById('tab6').options.length-1;
		document.getElementById('tab6').remove(v);
	}
	function row_add()
	{
		
	}

	function cond_selection()
	{

		var val=document.getElementById("tab2").options[document.getElementById("tab2").selectedIndex].value;
		val(); 

	}
</script>
<script type="text/javascript" src="row_add.js"></script>
</head>
<body>
<form   method="get" name="frm">

<%
	Controller obj=new Controller();

	Connection con=null;
	
	try
	{
		
		con=obj.con();
		obj.createStatement(con);
	}catch(Exception e)
	{
		
		
	}
	
	
	String tab=obj.setValue("tab",request);
	String qry="";
	out.println(tab);
	try
	{
		if (!tab.equalsIgnoreCase("0")) 
		qry="select * from ["+tab+"]" ;
		out.println(qry);
		DatabaseMetaData dbmd=con.getMetaData();
		String tabel_combo;
		ResultSet rs1=dbmd.getTables(con.getCatalog(),null,null,new String[]{"TABLE"});
		ResultSetMetaData md=rs1.getMetaData();
		tabel_combo="<select id='tab' name='tab' multiple class=select2  onchange='rld()'  style='height: 25em;width: 20em;'> ";
		while (rs1.next())
		{
			String tabel_name=rs1.getString("TABLE_NAME");
			
			if(!tabel_name.startsWith("Staf"))		
			{
				if (tab.equalsIgnoreCase(tabel_name))
				tabel_combo+="<option value='"+tabel_name+"' selected>"+tabel_name+"</option>";
				else
				tabel_combo+="<option value='"+tabel_name+"'>"+tabel_name+"</option>";	
			}
		
		}
		tabel_combo+="</select>";
	
	String column_combo="<select id='tab1' name='tab1' multiple class=select2  onchange='rld1()'  style='height: 25em;width: 20em;'> ";
	String column_combo_order="<select id='tab4' name='tab4' multiple class=select2  onchange='rld5()'  style='height: 25em;width: 20em;'> ";
	String cond="<select id='cond'><option value=0>Select</option><option value=1>and</option><option value=2>or</option><option value=3>like</option></select>";
	if (!tab.equalsIgnoreCase("0"))
	
	{
			ResultSet rs=(con.prepareStatement(qry)).executeQuery();
			ResultSet rs3=dbmd.getColumns(null, null, tab, null);
			
			while (rs3.next())
			{
					String column_name=rs3.getString("COLUMN_NAME");
					column_combo+="<option value='"+column_name+"'>"+column_name+"</option>";	
					column_combo_order+="<option value='"+column_name+"'>"+column_name+"</option>";
			}
	}
	column_combo+="</select>";
	column_combo_order+="</select>";
	
	
%>
<table border='1' cellpadding="5" cellspacing="0" align="center" style="height: 31em;" width="70%">
<tr>
	<td colspan="6" align="center">
			Report Query
	</td>
</tr>
<tr>
	<td valign="top">Table Name<br>
		<%=tabel_combo %>
	</td>
 
	<td valign="top">Columns<br>
		<%=column_combo%>
	</td>
	<td>Column Selection<br>
		<select id='tab2' name='tab2' multiple="multiple" class=select2  style='height: 25em;width: 20em;' onchange="val()">
		</select>
		<input type="button" value="X" name="b1" id="b1" onclick="remove()">
	</td>
	<td valign="top">Columns<br>
		<%=column_combo_order%>
	</td>
	<td width="42%">Order By<br>
		<select id='tab6' name='tab6' multiple="multiple" class=select2  style='height: 25em;width: 20em;'>
		</select>
		<input type="button" value="X" name="b2" id="b2" onclick="remove2()">
	</td>
	 
</tr>
<tr>
	<td><input type="checkbox">where</td>
	<td><div id='cond_select'></div></td>
</tr>
<tr>
	<td colspan="5">
		<input  type=button value="SUBMIT" name="spl" id='spl' onclick="rld3()"> 
	
	<input type=hidden id='col_list' name='col_list'>
	<input type=hidden id='order_col_list' name='order_col_list'></td>
</tr>

</table>

<%

}catch(Exception e) {}

%>
</form>
</body>
</html>