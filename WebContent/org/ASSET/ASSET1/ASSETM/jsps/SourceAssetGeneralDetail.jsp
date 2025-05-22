<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
 <head><%@page import="Servlets.ASSET.ASSET1.ASSETM.servlets.Controller"%>
<%@page import="java.util.*,java.sql.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scheme Asset General Details</title>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript">
function fun()
{
	window.open("SchemeTechDetails.jsp");
	self.close();  
}
function pdf_rpt()
{ 
	var sno=document.getElementById("sch_sno").value;
    window.open("../../../../../Service_Report?command=PDF&sno="+sno);
}
 
function loadcall(a)
{
	document.getElementById('process').value=a;
	document.forms['component'].submit();
}   
function fun2()
{
	var count=document.getElementById('count').value;
	 
	if(count==0)
	{
		alert(" There are NO sub components for selected Component!!!");
	}
	else
	{
	}
	
}
function editfun()
{
 
var sch_sno=document.getElementById('sch_sno').value;
var src_comp=document.getElementById('src_comp').value;
var t1=document.getElementById("s1").value;
	var sub_src_comp=document.getElementById('sub_src_comp').value;
window.open("LocBeneficiaries_Edit.jsp?sch_sno="+sch_sno  + "&src_comp=" + src_comp + "&sub_src_comp=" +sub_src_comp+"&t1="+t1,"self","location=1,status=1,scrollbars=1,resizable=1");
}
function addfun()
{
	var sch_sno=document.getElementById('sch_sno').value;
	var src_comp=document.getElementById('src_comp').value;
	var t1=document.getElementById("s1").value;
	var sub_src_comp=document.getElementById('sub_src_comp').value;
	window.open("LocBeneficiaries.jsp?sch_sno="+sch_sno  + "&src_comp=" + src_comp + "&sub_src_comp=" +sub_src_comp+"&t1="+t1,"self","location=1,status=1,scrollbars=1,resizable=1");
}

function loadpage() 
{
	var count=document.getElementById('count').value; 
	var src_comp=document.getElementById('src_comp').value;
	var len_comp=document.getElementById('src_comp').length;
	var sub_src_comp=document.getElementById('sub_src_comp').value;
	var len_subcomp=document.getElementById('sub_src_comp').length;
 	fun2();
	 if(len_comp<=1)
		 {
	 		document.getElementById('ss').style.display = "none";
		 	alert("There are NO components related this Scheme!!!");
		 }
	
			var sch_sno=document.getElementById('sch_sno').value;
			var file=document.getElementById('file').value;
			var jsno=document.getElementById('jsno').value;
			if (file!=0)
			var s=window.open(file+"?sch_sno=" + sch_sno + "&src_comp=" + src_comp + "&sub_src_comp=" +sub_src_comp+"&jsno="+jsno ,"self","location=1,status=1,scrollbars=1,resizable=1");
	
} 
</script>
</head>
<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet"
	media="screen" />

<body>
<%
	String id = "", stype = "";
	String sch_name = "";
	String src_type = "", comp = "", sub_comp = "";
	String src_comp = "";
	String sub_src_comp = "";
	String file="";
	String process="";
	String COMP_SNO="",jsno="";
	int subcomp_count=0,count=0;
	try {
		Controller obj = new Controller();
		Connection con = obj.con();
		obj.createStatement(con);
		id = obj.setValue("sch_sno", request);
		src_comp = obj.setValue("src_comp", request);
		process= obj.setValue("process", request);
		sub_src_comp = obj.setValue("sub_src_comp", request);
		sch_name = obj.getValue("PMS_SCH_MASTER", "SCH_NAME","where SCH_SNO=" + id + "  ");
		String lcompdesc="",lcompid="";
		PreparedStatement ps=con.prepareStatement("select  a.COMP_DESC as COMP_DESC,a.COMP_ID as COMP_ID from  PMS_SCH_LKP_COMPONENT a,PMS_SCH_COMPONENT b where  a.COMP_ID=b.COMP_ID and b.SCH_SNO="+id);
		ResultSet rs=ps.executeQuery();
		comp="<select id='src_comp' name='src_comp' onchange=loadcall(1) ><option value=0>Select</option>";
		while (rs.next())
		{
			lcompdesc=rs.getString("COMP_DESC");
			lcompid=rs.getString("COMP_ID");
			if (lcompid.equalsIgnoreCase(src_comp))
			comp+="<option value='"+lcompid+"' selected>"+lcompdesc+"</option>";
			else
			comp+="<option value='"+lcompid+"' >"+lcompdesc+"</option>";
		}
		comp+="</select>";
		String lscompdesc="",lscompid=""; 
		 
		//count=obj.getCount()
		sub_comp="<select id='sub_src_comp' name='sub_src_comp'  onchange=loadcall(2)><option value=0>Select</option>";
		 
		 COMP_SNO=obj.getValue("PMS_SCH_COMPONENT","COMP_SNO"," where COMP_ID="+src_comp+" and SCH_SNO="+id);
		 
		ps=con.prepareStatement(" select b.SUBCOMP_DESC as SUBCOMP_DESC,b.SUBCOMP_ID as SUBCOMP_ID from PMS_SCH_COMP_SUB_COMPONENT a ,PMS_SCH_LKP_SUBCOMPONENT b where a.SUBCOMP_ID= b.SUBCOMP_ID and    a.COMP_SNO="+COMP_SNO);
		rs=ps.executeQuery();
	   //System.out.println("select b.SUBCOMP_DESC as SUBCOMP_DESC,b.SUBCOMP_ID as SUBCOMP_ID from PMS_SCH_COMP_SUB_COMPONENT a ,PMS_SCH_LKP_SUBCOMPONENT b where a.SUBCOMP_ID= b.SUBCOMP_ID and    a.COMP_SNO="+COMP_SNO+" ");
		//if(rs.getRow())
		while (rs.next())
		{ 
			count=rs.getRow();
			System.out.println("count"+count);
			lscompdesc=rs.getString("SUBCOMP_DESC");
			lscompid=rs.getString("SUBCOMP_ID");
			if (lscompid.equalsIgnoreCase(sub_src_comp))
			sub_comp+="<option value='"+lscompid+"' selected>"+lscompdesc+"</option>";
			else
				sub_comp+="<option value='"+lscompid+"'>"+lscompdesc+"</option>";
		}
		sub_comp+="</select>";
		
		 
		String FORM_SNO="";
		if (process.equalsIgnoreCase("1"))
		  FORM_SNO=obj.getValue("PMS_ASSET_MAP","FORM_SNO"," where ( COMP_ID="+src_comp+" and  SUBCOMP_ID="+sub_src_comp+" ) or COMP_ID="+src_comp+"");
		else
			FORM_SNO=obj.getValue("PMS_ASSET_MAP","FORM_SNO"," where ( COMP_ID="+src_comp+" and  SUBCOMP_ID="+sub_src_comp+" ) and COMP_ID="+src_comp+"");
		file=obj.getValue("PMS_ASSET_FORM","FORM_JSP"," where FORM_SNO="+FORM_SNO);
		  jsno=obj.getValue("PMS_ASSET_MAP","JASPERSNO"," where COMP_ID="+src_comp+" and  SUBCOMP_ID="+sub_src_comp+" and FORM_SNO="+FORM_SNO);
		subcomp_count=obj.getCount("PMS_SCH_LKP_SUBCOMPONENT"," where COMP_ID="+src_comp);
	} catch (Exception e) {
		out.println(e);
	} 
%>

<form name="component" id="component" method="get">
<input type="hidden" id="sch_sno" readonly="readonly" name="sch_sno" value="<%=id%>">
<input type="hidden" id="count" name="count" value="<%=count%>">
<input type="hidden" id="file" name="file" value="<%=file%>">
<input type="hidden" id="jsno" name="jsno" value="<%=jsno%>">

<input type="hidden" id="process" name="process" value="<%=process%>"> 
	
<table border="1" width="70%" align="center">
	<tr class="tdH">
		<td colspan="3">Scheme Technical Details Entry</td>
	</tr>
	<tr>
		<td>Scheme Name:</td>
		<td><label id="l1" colspan="2"><%=sch_name%></label></td>
	</tr>


	<tr class="tdH">
		<td colspan="3">Component and Sub Component Details Entry</td>
	</tr>
	<tr id="ss"> 
		<td>Select Scheme Component</td>
		<td colspan="2"><%=comp%></td>

	</tr>
	<tr id="mm">
		<td>Select Scheme Sub Component</td>
		<td><%=sub_comp%></td><td><div id="divid"><input type="button" value="Submit" onclick="loadpage()"></div></td>
	</tr>
	
 
	<tr>
		<td align="center" colspan="3">
			<input type="button" value="Exit" onclick="javascript:window.close()">
		</td>
			
			
	</tr>

	 
</table> 
  

</form>
</body>
</html>