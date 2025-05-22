<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/> 
 
<title>Scheme - Account Mapping </title>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
function stype(a)
{
	command = "stype";	 
 	var url = "pun_ajax.jsp?command="+command+"&sch="+a;
	var xmlobj3 = createObject();
	xmlobj3.open("GET", url, true);
	xmlobj3.onreadystatechange = function() 
	{
		stype_process(xmlobj3);
	}
	xmlobj3.send(null);
}
function stype_process(xmlobj3)
{
	if (xmlobj3.readyState == 4) 
	{
		if (xmlobj3.status == 200) 
		{ 
			var results_str =new String(xmlobj3.responseText);
			var results=results_str.split("|");
			var res = results[1];
			document.getElementById("stype").value=res;
		}
		
	}
	
}
function sel_sch(a)
{

	var sel_id2=document.getElementById(a);
	var val2=sel_id2.options[sel_id2.selectedIndex].value;
	document.getElementById("sch_sel").value=val2;
	stype(val2)
}

function sel()
{
	var sel_id=document.getElementById("drac");
	var val=sel_id.options[sel_id.selectedIndex].value;
	var tval=sel_id.options[sel_id.selectedIndex].text;
	document.getElementById("drac1").value=tval;
	document.getElementById("dr").value=val;  
	
}

function sel2()
{
	var sel_id2=document.getElementById("crac");
	var val2=sel_id2.options[sel_id2.selectedIndex].value;
	document.getElementById("cr").value=val2;
	var tval=sel_id2.options[sel_id2.selectedIndex].text;
	document.getElementById("crac1").value=tval;
}

function modify(a)   
{
	 
	var sch=document.getElementById("sch_sel"+a).value;
	var schtypevalue=document.getElementById("schtypevalue"+a).value;
	var msno=document.getElementById("row"+a).value;
	document.getElementById("stype").value=schtypevalue;
	document.getElementById("msno").value=msno;
	var sel_id=document.getElementById("sch2");
	document.getElementById('sel_sch2').style.display = "block";
	document.getElementById('sel_sch1').style.display = "none";    
	var dr=document.getElementById("dr"+a).innerHTML;
	var cr=document.getElementById("cr"+a).innerHTML;
	document.getElementById("drac").value=dr;
	document.getElementById("crac").value=cr;
	document.getElementById("drac1").value=dr;
	document.getElementById("crac1").value=cr;
	document.getElementById("sch_sel").value=sch;
//	alert("schtypevalue is---->"+schtypevalue);
//	alert("msno is---->"+msno);
//	alert("sel_id is---->"+sel_id.options.length);
//	alert("dr is---->"+dr);
//	alert("cr is---->"+cr);
//	alert("sch is---->"+sch);

	    var len=sel_id.options.length;  
		for ( var i = 0; i <parseInt(len); i++ ) 
	    {
			 var val=sel_id.options[i].value;
			 if(val==sch)  
			 {
				 	
						 if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) // IE 
						 {
							 sel_id.selectedIndex = i;
						 }
						 else
						 {
							sel_id.options[i].selected = true;
						 }
			} 
			else
			{
				 sel_id.options[i].selected = false;
			 }
	     }

		var drac=document.getElementById("drac");
		var drac_len=drac.options.length;
		for ( var i = 0; i <parseInt(drac_len); i++ ) 
	    {
			 var val=drac.options[i].text;
			 if(dr==val)  
			 {
				 if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) // IE 
				 {
					 drac.selectedIndex = i;
				 }
				 else
				 {
					 drac.options[i].selected = true;
				 }
			 }else
			 {
				 	drac.options[i].selected = false;
			 }
	    }
	       
		var crac=document.getElementById("crac");
		var crac_len=crac.options.length;
		for ( var i = 0; i <parseInt(crac_len); i++ ) 
	    {
			 var val=crac.options[i].text;
			 
			 if(cr==val)  
			 {
				 if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) // IE 
				 {
					 crac.selectedIndex = i;
				 }
				 else
				 {
					 crac.options[i].selected = true;
				 }
			 }else
			 {
				 	crac.options[i].selected = false;
			 }
	    }
}

function sch_swap()
{
	document.getElementById('sel_sch2').style.display = "none";
	document.getElementById('sel_sch1').style.display = "block";
}

function fcheck()
{

	var sch1=document.getElementById('sch').value;
	var drac=document.getElementById('drac').value;
	var crac=document.getElementById('crac').value;
	if (sch1==0 || drac==0 || crac==0 )
	{		
		alert(" Select Scheme , DR Account Head , CR Account Head  ");  
		return false;
	}
	else
	{
		return true;
	}
}  

function fcheck1()
{
	var sch1=document.getElementById('sch2').value;
	var drac=document.getElementById('drac').value;
	var crac=document.getElementById('crac').value;
	if (sch1==0 || drac==0 || crac==0 )
	{		
		alert(" Select Scheme , DR Account Head , CR Account Head  ");  
		return false;
	}
	else
	{
		return true;
	}
}

function no_one(a)
{
		var v=document.getElementById(a).value;
		try
		{
			var v1=parseInt(v);
		}catch(e)
		{
				alert("Only Number Allowed");
				document.getElementById(a).value="";
				
		}
}
	</script>
</head> 
<body onload="sch_swap()">
<%   
		Connection con,con2;
		Controller obj=new Controller();
		Controller obj2=new Controller();
		String BEN_TYPE_ID="",Office_name="";
		
		String userid="112",Office_id="",Office_Name="";
		String Schemestr="",Schemestr2="",drcmob="",crcmob="";
		ResultSet rs=null;
		String msg="";
		try
		{
			
			con=obj.con();
			con2=obj2.con(); 
			obj.createStatement(con);
			userid=(String)session.getAttribute("UserId");
		
			if(userid==null)  
			{
			 	 response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
			msg=obj.isNull(obj.setValue("msg",request),4);  
		    Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
			Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
		
		    if (Office_id.equals("")) Office_id="0"; 
			BEN_TYPE_ID=obj.combo_str("PMS_DCB_BEN_TYPE","BEN_TYPE_DESC","BEN_TYPE_ID"," where BEN_TYPE_ID in ( select BENEFICIARY_TYPE_ID from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+")","BEN_TYPE_ID","","  onchange=sch_load(2,'')" );
			Schemestr=obj.combo_str("PMS_SCH_MASTER","SCH_NAME","SCH_SNO","where sch_type_id in (select sch_type_id from pms_dcb_applicable_sch_type) and  SCH_SNO not in (select SCH_SNO from PMS_SCH_ACCOUNT_MAPPING ) and SCH_STATUS_ID in (4,9) and SCH_CATEGORY_ID=4 order by SCH_NAME asc","sch","  onchange=sel_sch('sch')");
			Schemestr2=obj.combo_str("PMS_SCH_MASTER","SCH_NAME","SCH_SNO","where   SCH_STATUS_ID in (4,9) order by SCH_SNO desc","sch2","   class='select' onchange=sel_sch('sch2')");
			drcmob=obj.combo_str("PMS_DCB_RECEIPT_ACCOUNT_MAP","ACCOUNT_HEAD_CODE","RECEIPT_TRN_DESC"," where   COLLECTION_TYPE=10 ","drac","   class='select'  onchange='sel()'");
			crcmob=obj.combo_str("PMS_DCB_RECEIPT_ACCOUNT_MAP","ACCOUNT_HEAD_CODE","RECEIPT_TRN_DESC"," where   COLLECTION_TYPE=11 ","crac","   class='select'  onchange='sel2()'");
			rs=obj.getRS("select  a.SCH_ACC_MAP_SNO,a.ACC_HD_DR,a.ACC_HD_CR,b.SCH_SHORT_DESC,b.sch_name,b.SCH_SNO as bsch , b.SCH_TYPE_ID	 as stype from PMS_SCH_ACCOUNT_MAPPING a,PMS_SCH_MASTER b where  a.sch_sno=b.sch_sno  order by a.UPDATED_TIME DESC	 ");
			}catch(Exception e)
			{     
				out.println(e);
			}  
			if (!msg.equalsIgnoreCase("0"))
			{%>
				<script type="text/javascript">
				alert('<%=msg%>')  
				</script>
				
			<%}
			 
%>     

 <form action="../../../../../Scheme_Acc_mapping" method="get" >
 <table id="" name="" width="100%" border="1" style="border-collapse:collapse; " cellpadding="5" cellspacing="0" bordercolor="skyblue">
	<tr class="tdH">
		<th colspan="2" align="center">Scheme - Account Mapping</th> 
	</tr> 
	<tr>
		<td width="15%"> Scheme Name </td>
		<td ><div id='sel_sch1'><%=Schemestr%></div>
		
		<div id='sel_sch2' hidden><%=Schemestr2%></div>
		
		</td>
	</tr>
	<tr>
		<td>Scheme Type </td><td ><input type=text name="stype" id="stype" readonly="readonly" style="size: 12em;" size="150"></td>
	</tr>
		<input type=hidden name="finid" id="finid" readonly="readonly" value="4">  
		<input type=hidden name="schmestatusid" id="schmestatusid" readonly="readonly" value="4">  
	<tr>
		<td  width="15%"> DR Account Head </td>  
		<td><%=drcmob%> <input type=text name="dr" id="dr" maxlength=6 onblur="isInteger2('drac')" readonly="readonly" size="75" hidden> </td>
	</tr>
	  
	<tr>
		<td  width="15%"> CR Account Head </td>
		<td><%=crcmob%><input type=text name="cr" id="cr" maxlength=6 onblur="isInteger2('crac')" readonly="readonly" size="75" hidden> </td>
	</tr>
					
	<tr>
		<td align="center" colspan="2"> 
		<input type="Submit" name="store" value="Submit" onsubmit="return fcheck()" >
		<input type="Submit" name="update" value="Update" onsubmit="return fcheck1()" >
		<input type="button" value="Exit" onclick="window.close()"> 
		<input type="Reset" value="Clear"  >  
		</td>
	</tr>
 </table>
<table width="100%">
	 <tr class="tdH">
		<th width="3%">&nbsp;</th>
		<th width="7%">Sl.No</th>
		<th>Scheme Name</th>
	 	<th width="10%">Debit A/c</th>
		<th width="10%">Credit A/c</th>				 	
	</tr>
	<tr>     			 
     			<Td colspan="5" align="center" valign="top">
		     			<div id='scroll_clipper' style=' vertical-align:top;position:inherit; width:100%; border-height:1px; height: 400px; overflow:auto;white-space:nowrap;'>
						<div id='scroll_text' style="vertical-align: top" ><table width="100%"   cellpadding="5"  style=" vertical-align top;BORDER-COLLAPSE: collapse"   id="pr_ben_data"  border="1"  align="center"  cellspacing="0" cellpadding="0"  bgcolor='white'>
						 <%
							String SCH_NAME="",short_desc="",sch_desc="",stype="",  schtypevalue="";
							int row=0;
							while (rs.next())
							{
							row++;  
							short_desc=rs.getString("SCH_SHORT_DESC");
							SCH_NAME=rs.getString("SCH_NAME");
							stype=rs.getString("stype");
							if ( short_desc==null)
								short_desc=SCH_NAME;
							  schtypevalue=obj2.getValue("PMS_SCH_LKP_TYPE","SCH_TYPE_DESC"," where SCH_TYPE_ID="+stype);
							%>
							<tr>
									<td><a href="javascript:modify(<%=row%>)">Edit</a></td>
									<td width="10%"><%=row%></td>
									<td align="left"><%=short_desc%> </td>  
									<td width="10%"><label id='dr<%=row%>'><%=rs.getString("ACC_HD_DR")%></label></td>
									<td width="10%"><label id='cr<%=row%>'><%=rs.getString("ACC_HD_CR")%></label></td>
									<input type="hidden" id="row<%=row%>" value='<%=rs.getString("SCH_ACC_MAP_SNO")%>'>
									<input type="hidden" id="sch_sel<%=row%>" value='<%=rs.getString("bsch")%>'>					
									<input type="hidden" id="stype<%=row%>" value='<%=stype%>'>
									<input type="hidden" id="schtypevalue<%=row%>" value='<%=schtypevalue%>'>
									    
							</tr>	  
							<%
								short_desc="";
							}    
						%>
						</table>
						</div>
						</div>
				</Td>
		</tr>
	</table>
			 	<input type="hidden" id="drac1" name="drac1">
			 	<input type="hidden" id="sch_sel" name="sch_sel">
			 	<input type="hidden" id="crac1" name="crac1">		
				<input type="hidden" id="msno" name="msno">
 	</form>
</body>
</html>