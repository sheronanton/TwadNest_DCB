<?xml version="1.0" encoding="ISO-8859-1" ?>
<html xmlns="http://www.w3.org/1999/xhtml">
 <%@page import="java.util.Calendar" %>
 <%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
 <%@page import="java.util.*,java.sql.*,java.io.*"%>
 <%@ page import="Servlets.Security.classes.UserProfile"%>
<head>
 <link href="../../../../../css/common.css" rel="stylesheet" media="screen"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Help Form </title>

<style type="">

 table.tr
{
	background-color: red;
}

</style>
 <script type="text/javascript">
function rld_2()
{
	 document.help.location="help_entry.jsp";
}
 function filedelete(a) 
 {

	 document.help.location="help_entry?update=filedel&file="+a;  
	 document.help.submit();
 }
function rpt1()
{
	 var rpt=document.getElementById("rpt").value;
	 if (rpt==1)
	 {

		 var s=window.open("../../../../../count_report_serv?command=mismatch");
		 
	 }
}
function rld()
{
		 var FORMSNO=document.getElementById("FORMSNO").value;			 
		 document.help.location="help_entry.jsp?FORMSNO="+FORMSNO;
		 document.help.submit();
} 

function rld2(a,b)
{
	var ono=document.getElementById("order"+b).innerHTML;
	var desc=document.getElementById("desc"+b).innerHTML;
	document.getElementById("orno").value=ono;
	document.getElementById("desc").value=desc;
	document.getElementById("process_v").value=a;
	
}

</script>

</head>
<body>
<form name="help" action="help_entry.jsp" method="get">

<%
		Controller obj=new Controller();
		Controller obj1=new Controller();
		Connection con=null;
		String main="",FORMSNO="";
	
		try
		{
				con=obj.con();
				obj.createStatement(con);
				obj1.createStatement(con);
				FORMSNO=obj.setValue("FORMSNO",request);
				 String	userid = (String) session.getAttribute("UserId");
				 
				 

			 	 
				main=obj.combo_str("PMS_DCB_HELP_MAIN","FORMNAME","SNO"," order by SNO","FORMSNO",FORMSNO,"class='select'  onchange='rld()'");			
				String sub= obj.setValue("sub",request);
				String update= obj.setValue("update",request);
				String delete= obj.setValue("delete",request);
				String desc= obj.setValue("desc",request);
				String orno= obj.setValue("orno",request);
				int maxordsno=0;
				int tab_max_=0;
				String qry="";
				 maxordsno=obj.getMax("PMS_DCB_HELP","ORDERSNO","where FORMSNO="+FORMSNO);
				if (sub.equalsIgnoreCase("Submit"))
				{
					tab_max_=obj.getMax("PMS_DCB_HELP","SNO","");
					String count=obj.getValue("PMS_DCB_HELP", "count(*)", "where FORMSNO="+FORMSNO+" and ORDERSNO="+orno); 
					maxordsno=obj.getMax("PMS_DCB_HELP","ORDERSNO","where FORMSNO="+FORMSNO);
					  if (Integer.parseInt(count)==0)
					  {
						  
						  qry="insert into PMS_DCB_HELP (SNO, FORMSNO, ORDERSNO,HELPTEXT) values (?,?,?,?)";
						  PreparedStatement ps=con.prepareStatement(qry);
						  ps.setInt(1,tab_max_);
						  ps.setString(2,FORMSNO);
						  ps.setString(3,orno);
						  ps.setString(4,desc);
						  ps.executeUpdate();
						  maxordsno++;
					  }	  
				}  
				if (update.equalsIgnoreCase("filedel"))
				{
					 String file= obj.setValue("file",request);
					File f=new File(request.getContextPath()+"/excel/"+file);
					f.delete();
					
				}
				if (update.equalsIgnoreCase("Update"))
				{
					 String sno= obj.setValue("process_v",request);
					  qry="update PMS_DCB_HELP set ORDERSNO="+orno+",HELPTEXT='"+desc+"' where sno="+sno;
					  PreparedStatement ps=con.prepareStatement(qry);
					  ps.executeUpdate();
				}
				if (delete.equalsIgnoreCase("Delete"))
				{
					 String sno= obj.setValue("process_v",request);
					  qry="Delete PMS_DCB_HELP   where sno="+sno;  
					  PreparedStatement ps=con.prepareStatement(qry);
					  ps.executeUpdate();
				}
		%>
		<table  border="1" width="75%" align="center"  cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
				<tr>
				
					<td colspan="2" align="center"><font class="fnt" ><h4> Form Help Entry </h4></font> </td>
					</tr>
				<tr>
				<td> 
						 <font class="fnt">Document Name    </font>
				</td>
				
				<td>  <%=main%></td>				
				</tr>
				<tr> <td>  <font class="fnt">Order No  </td>
					 <td><input type=text  id="orno" name="orno" class="tb4" value="<%=maxordsno%>" > </td>
				</tr>
				<Tr>
						<Td> <font class="fnt"> Help Text </font>  </Td>
						<Td><textarea id="desc" name="desc" class="textarea" style="height: 95px"></textarea></Td>
				</Tr>		
				<tr>
					<td colspan="2" align="center"> <input type="submit" value="Submit" name="sub"> 
					  &nbsp; &nbsp; &nbsp;  &nbsp; &nbsp;  &nbsp; &nbsp;
						<input type="submit" value="Update" name="update">&nbsp;   &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
						<input type="submit" value="Delete" name="delete">
					&nbsp; &nbsp; &nbsp;  &nbsp;  
						<input type="button" value="Exit" onclick="javascript:window.close()">	
					</td>
				</tr>
				<%
					    
					if (userid.equalsIgnoreCase("nic008"))
					{%>
						<tr><td><a href="adminhelp.jsp">Query</a> &nbsp; <a href="../../../../../videoUpload.jsp">Video Upload</a></td></tr>
					<%}
				%>
		</table>		
		
		<table border="1" width="75%" align="center"  cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
		
				
		<tr>
				<Td>  <font class="fnt">Edit</font></Td>  
				<Td>  <font class="fnt">Order No</font></Td>
				<Td>  <font class="fnt">Help Text</font></Td>
		</tr>		
		<%
			 
			 
			ResultSet rs=obj1.getRS("select sno, FORMSNO,ORDERSNO,HELPTEXT from PMS_DCB_HELP   where FORMSNO="+FORMSNO+" order by ORDERSNO");
			int row=0;
			int sno=0;
			while (rs.next())
			{
				row++;
				sno=rs.getInt(1);
			%>
				<Tr>
				<Td><a href="javascript:rld2(<%=sno%>,<%=row%>)">  <font class="fnt">Edit</font></a></Td>
				<Td> <font class="fnt"><label id=order<%=row%>><%=rs.getString(3)%></label></font></Td>
				<Td> <font class="fnt"><label id=desc<%=row%>><%=rs.getString(4)%></label></font></Td>
				
				</Tr>
			<%}
		%>
				
		</table>
			
		<table  border="1" width="75%" align="center"  cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
					<tr>
						<Td>
								Report Title												
						</Td>
						<td>
								<select id="rpt" onchange="rpt1()">
										<option value="0">Select</option>
										<option value="1">Ben - Meter Mismatch</option>
								</select>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="Contents.jsp">Hosted War Details</a>
						</td>
					</tr>
					      
			</table>	
			<table  border="1" width="75%" align="center"  cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
			<tr bgcolor="green"><td>Excel Report </td></tr>	 
			<%

		 try   
			{
			java.io.File fl=new java.io.File (application.getRealPath("/excel"));
	  
			if (fl.isDirectory())
			{
				
				String []ls=fl.list();    
			 
				int i=0;
				int len=ls.length;
				while (i<len)
				{%>
				<tr>
					<td colspan="2"><a href="<%=request.getContextPath()%>/excel/<%=ls[i]%>"><%=ls[i]%></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:filedelete('<%=ls[i]%>')">Delete</td>
				</tr>		
					
				<%  
				i++;  
				}  
			}
			
			}catch(Exception e) {}	
			  
%>
		<%}catch(Exception e1)
		 {
	 		out.println(e1);
	 
		 }
		
%></table><a href="help1.jsp">..</a>
	<input type=hidden id="process_v" name="process_v"  value="0">
</form>
</body>
</html>
 