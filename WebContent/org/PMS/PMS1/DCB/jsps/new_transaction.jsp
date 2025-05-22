<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Calendar"%>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="../../../../../../css/txtbox.css" rel="stylesheet"	media="screen" />
<title>Region Wise Report | TWAD Phase2</title>
<link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
<script type="text/javascript" src="../../../../../../org/Library/scripts/CalendarControl.js"></script>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
 
<script type="text/javascript">
function c1()
{

	try {
		document.getElementById("b1").style.color="#800517";
		document.getElementById("b1").style.backgroundColor ="white";
		document.getElementById("b2").style.color="yellow";
		document.getElementById("b2").style.backgroundColor ="#C85A17";
		setTimeout ("c2()", 1000 );
	}catch(e) {}
}
	
function month_process_view()
{
	try {
			var smonth=document.getElementById("smonth").value;
		if (smonth==0)
		{
			document.getElementById("master_process").style.display = 'block';
			document.getElementById("month_process").style.display = 'none'; 
		}else
		{
			document.getElementById("master_process").style.display = 'none';
			document.getElementById("month_process").style.display = 'block';
		}
	}catch(e) {}
}
function yearload(year)
{
	var date=new Date();
	var year_v1 =new Array("-select year-","2010","2011");	
	var year_v =new Array("-select year-","2010-2011","2011-2012");
 	for (i=2010;i<=date.getFullYear();i++)
	{
		var sno=i;
		var name =i;
		addOption_load(document.getElementById(year),name,sno)
	}
}
function addOption_load(selectbox,text,value)
{  
	try
	{
			var optn = document.createElement("OPTION");
			optn.text = text;
			optn.value = value;
			if (this.src=='ob')
			{
				if (selectbox.id=="year")
				{
					 var d=new Date();
					 var cy=d.getFullYear();
					 var cm=d.getMonth()+1;
					if (value==parseInt(cy))
					{
							if ( parseInt(cm) <=3)
							{
								
							}	
						
					optn.selected=1;
					}
				}
				if (selectbox.id=="month")
				{
					if (value=="4")
					optn.selected=1;
				}
			}
			selectbox.options.add(optn);
	}catch(e)
	{
			alert("Option Creation Have Problem\n-------------------------------------")
	}
}
function c2()
{ 
document.getElementById("b1").style.color="yellow";
document.getElementById("b1").style.backgroundColor ="#C85A17";
document.getElementById("b2").style.color="#800517";
document.getElementById("b2").style.backgroundColor ="white";
setTimeout ("c1()", 1000 );
}
function rld1(a)
{
	document.forms["myform"].submit();
}
function rld_new6()
{
	var year=document.getElementById("year").value; 
	var smonth=document.getElementById("smonth").value;
	var rpt=document.getElementById("rpt").value;
	if (monthchk(smonth,year)!=1)
	{
		window.open("../../../../../../PMS_DCB_HO_DIST_WISE?month="+smonth+"&year="+year+"&ref_sno="+(parseInt(rpt)+20));
	}    
}
function rld_new5()
{
	var mon=document.getElementById("year").value; 
	var smonth=document.getElementById("smonth").value;
	var rpt=document.getElementById("rpt5").value;
	var rtype=document.getElementById("rtype").value;
	if (monthchk(smonth,mon)!=1)
	{ 
		window.open("../../../../../../reg_menu_index?month="+smonth+"&option="+rtype+"&year="+mon+"&ref_sno="+(parseInt(rpt)+20));
	}    
}
function rld_new()
{
	var mon=document.getElementById("year").value; 
	var smonth=document.getElementById("smonth").value;
	var rpt=document.getElementById("rpt2").value;
	var rtype=document.getElementById("rtype").value;
	if (monthchk(smonth,mon)!=1)
	{ 
		window.open("../../../../../../reg_menu_index?month="+smonth+"&option="+rtype+"&year="+mon+"&ref_sno="+(parseInt(rpt)+20));
	}    
}
function rld_new1()
{
	var mon=document.getElementById("year").value; 
	var smonth=document.getElementById("smonth").value;
	var rpt=document.getElementById("rpt1").value;
	var rtype=document.getElementById("rtype").value;
	if (monthchk(smonth,mon)!=1)
	{  
		window.open("../../../../../../reg_menu_index?month="+smonth+"&option="+rtype+"&year="+mon+"&ref_sno="+(parseInt(rpt)+20));
	}    
}
function rld_new2()  
{
	var mon=document.getElementById("year").value; 
	var smonth=document.getElementById("smonth").value;
	var rpt=document.getElementById("rpt2").value;
	var rtype=document.getElementById("rtype").value;
	if (monthchk(smonth,mon)!=1)
	{  
		window.open("../../../../../../reg_menu_index?month="+smonth+"&option="+rtype+"&year="+mon+"&ref_sno="+(parseInt(rpt)+20));
	}    
}
function rld_new3()  
{
	var mon=document.getElementById("year").value; 
	var smonth=document.getElementById("smonth").value;
	var rpt=document.getElementById("rpt3").value;
	var rtype=document.getElementById("rtype").value;
	if (monthchk(smonth,mon)!=1)  
	{    
		window.open("../../../../../../PMS_DCB_HO_DIST_WISE?month="+smonth+"&splflag="+rtype+"&year="+mon+"&ref_sno="+(parseInt(rpt)+20));
	}    
}  
function rld_new7()    
{
	var mon=document.getElementById("year").value; 
	var smonth=document.getElementById("smonth").value;
	var rpt=document.getElementById("rpt4").value;
	var rtype=document.getElementById("rtype").value;
	if (monthchk(smonth,mon)!=1)  
	{    
		window.open("../../../../../../Pms_Dcb_Ledger_Report?month="+smonth+"&option="+rtype+"&year="+mon+"&pr="+(parseInt(rpt)+50));
	}     
}  

function monthchk(a,b)
{
	if (a==0 || b==0)
	{
		alert("Select Month and Year ");
		return 1;
	}else
	{
	return 0;
	}
} 


function month_year_check1(month,year)
{
	var flag=2;
	if (month!=0 && year != 0)
	{
		flag=0;   
	}else if (month==0 && year != 0)
	{
		flag=1;  
	}else if ( month!=0 && year == 0) 
	{
		flag=1;
	}else
	{
		alert("Select Month and Year");
		
		flag=1;
	}
	return flag;
}
function rld()
{
	
	var a=document.getElementById("process").value;
	var rtype=document.getElementById("rtype").value;
	var report=document.getElementById("report").value;
	if (report==0)
		report=document.getElementById("report1").value;
	var mon=document.getElementById("year").value; 
	var smonth=document.getElementById("smonth").value;
  
	var res=month_year_check1(smonth,mon);
	 
	if (res==0 && report!=0)
	{
			if (report==10)
			{
				window.open("../../../../../../ame_report?option="+rtype+"&process_code=29&pmonth="+smonth+"&pyear="+mon+"&office_id="+document.getElementById("off").value);
			}  
		   if (report==18)    
		   {
		   	window.open("../../../../../../ame_report?pmonth="+smonth+"&pyear="+mon+"&process_code="+report+"&option="+rtype);
		   }else if (a==1)
			{
				if (report!=7)   
				{
				document.forms["myform"].submit();
				}else
				{
					window.open("count_report.jsp?reg="+document.getElementById("reg").value+"&regname="+document.getElementById("reg").options[document.getElementById("reg").selectedIndex].text+"&option="+rtype);
				}
			}
			else if (a==2)
			{
				var off_id=document.getElementById("off").value;
				
						if (off_id !=0 || report==7 || report==9)
						{		 
										if (report==1)
										{
											
											
										window.open("../../../../../../BeneficiaryMetreReportPdf?command=Ben_Met&office_id="+document.getElementById("off").value);
										}
										else if (report==2)
										{
											 
										//window.open("../../../../../../OpeningBalanceReport?command=Region_Wise_Op&month="+document.getElementById("smonth").value+
										var str="../../../../../../OpeningBalanceReport?command=Region_Wise_Op&month="+document.getElementById("smonth").value+
										"&year="+document.getElementById("year").value+
										"&report_office_id="+document.getElementById("off").value+"&process=1&flag='DCB'";
										window.open("../../../../../../OpeningBalanceReport?command=Region_Wise_Op&month="+document.getElementById("smonth").value+
												"&year="+document.getElementById("year").value+
												"&report_office_id="+document.getElementById("off").value+"&process=1&flag='DCB'");
										}else if (report==3)
										{
											window.open("../../../../../ Bill_Demand1?command=All_benList_RO&office_id="+document.getElementById("off").value );
										}else if (report==6)
										{
											 
											window.open("../../../../../Bill_Demand1?command=AllDiv_recpdf&fmonth="+document.getElementById("smonth").value+
													"&fyear="+document.getElementById("year").value+
													"&Office_id="+document.getElementById("off").value);
										}else if (report==9)
										{
											window.open("../../../../../../count_report_serv?command=pdf&fmonth="+document.getElementById("smonth").value+"&fyear="+document.getElementById("year").value);
										}else if (report==8)
										{  
											 
											window.open("../../../../../Bill_Demand1?command=PR_SCH_WISE&fmonth="+document.getElementById("smonth").value+
													"&fyear="+document.getElementById("year").value+
													"&Office_id="+document.getElementById("off").value);
										}
										else if (report==4)
										{
											 
													window.open("../../../../../Bill_Demand1?command=AllDiv_pumppdf&fmonth="+document.getElementById("smonth").value+
													"&fyear="+document.getElementById("year").value+
													"&Office_id="+document.getElementById("off").value);
										}else if (report==7)
										{
											//var pr=prompt("Press 1)PDF ,2)XLS ","1");
											var pr=1; 
											//if (parseInt(pr)==1) 
											 	window.open("../../../../../Bill_Demand1?command=record_pdf&process_code=1&fmonth="+document.getElementById("smonth").value+"&fyear="+document.getElementById("year").value+"&option="+rtype);
										//	else
											//	window.open("../../../../../../Bill_Demand?command=record_pdf&process_code=2&fmonth="+document.getElementById("smonth").value+"&fyear="+document.getElementById("year").value);
										}   
						}else     
						{
							alert(" Select Division ");
						}
				 
			}else
			{
					alert("Select any one type")
			}
	}
}
function rld6()
{
	var mon=document.getElementById("year").value; 
	var smonth=document.getElementById("smonth").value;
	var off_id=document.getElementById("off").value;
	var url="./Receipt_Troubleshoot_1.jsp?office_id="+document.getElementById("off").value+"&fmonth="+document.getElementById("smonth").value+"&fyear="+document.getElementById("year").value;
	 //window.open("./Receipt_Troubleshoot_1.jsp?office_id="+document.getElementById("off").value+"&fmonth="+document.getElementById("smonth").value+"&fyear="+document.getElementById("year").value,"","width=400,height=500" );
	window.open(url,'','toolbar=0,titlebar=0,fullscreen=1,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1,width=800,height=600,left = 82,top = 54','');
}
function rld7()
{
	var mon=document.getElementById("year").value; 
	var smonth=document.getElementById("smonth").value;
	var off_id=document.getElementById("off").value;
	var url="./Receipt_Troubleshoot_3.jsp?office_id="+document.getElementById("off").value+"&fmonth="+document.getElementById("smonth").value+"&fyear="+document.getElementById("year").value;
	 //window.open("./Receipt_Troubleshoot_1.jsp?office_id="+document.getElementById("off").value+"&fmonth="+document.getElementById("smonth").value+"&fyear="+document.getElementById("year").value,"","width=400,height=500" );
	window.open(url,'','toolbar=0,titlebar=0,fullscreen=1,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1,width=800,height=600,left = 82,top = 54','');
}
function rld8()
{
	var mon=document.getElementById("year").value; 
	var smonth=document.getElementById("smonth").value;
	var off_id=document.getElementById("off").value;
	var url="./Receipt_Troubleshoot_4.jsp?office_id="+document.getElementById("off").value+"&fmonth="+document.getElementById("smonth").value+"&fyear="+document.getElementById("year").value;
	 //window.open("./Receipt_Troubleshoot_1.jsp?office_id="+document.getElementById("off").value+"&fmonth="+document.getElementById("smonth").value+"&fyear="+document.getElementById("year").value,"","width=400,height=500" );
	window.open(url,'','toolbar=0,titlebar=0,fullscreen=1,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1,width=800,height=600,left = 82,top = 54','');
}
function new_reports()  
{ 
	var mon=document.getElementById("year").value; 
	 var smonth=document.getElementById("smonth").value;
	window.open("../../../../../../Bill_Demand1?command=newrecord_pdf&process_code=1&fmonth="+document.getElementById("smonth").value+"&fyear="+document.getElementById("year").value);
}
function rld9()
{
	var mon=document.getElementById("year").value; 
	var smonth=document.getElementById("smonth").value;
	var off_id=document.getElementById("off").value;
	var url="../../../../../../Pms_Dcb_Ledger_Report?pr=5";
	 //window.open("./Receipt_Troubleshoot_1.jsp?office_id="+document.getElementById("off").value+"&fmonth="+document.getElementById("smonth").value+"&fyear="+document.getElementById("year").value,"","width=400,height=500" );
	window.open(url,'','toolbar=0,titlebar=0,fullscreen=1,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1,width=800,height=600,left = 82,top = 54','');
}
function rld10() 
{
	var year=document.getElementById("year").value; 
	var smonth=document.getElementById("smonth").value;
	var option=document.getElementById("rtype").value;    
	var date=document.getElementById("date").value;
	var url="../../../../../../Pms_Dcb_Ledger_Report?cdate="+date+"&pr=7&option="+option+"&month="+smonth+"&year="+year;
	 //window.open("./Receipt_Troubleshoot_1.jsp?office_id="+document.getElementById("off").value+"&fmonth="+document.getElementById("smonth").value+"&fyear="+document.getElementById("year").value,"","width=400,height=500" );
	//window.open(url,'','toolbar=0,titlebar=0,fullscreen=1,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1,width=800,height=600,left = 82,top = 54','');
	window.open(url,'','toolbar=0,titlebar=0,fullscreen=1,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1,width=800,height=600,left = 82,top = 54','');
}  
function rld11()  
{
	var year=document.getElementById("year").value; 
	var smonth=document.getElementById("smonth").value;
 	var url="../../../../../../Pms_Dcb_Ledger_Report?pr=10&option=1&month="+smonth+"&year="+year;
	 //window.open("./Receipt_Troubleshoot_1.jsp?office_id="+document.getElementById("off").value+"&fmonth="+document.getElementById("smonth").value+"&fyear="+document.getElementById("year").value,"","width=400,height=500" );
	window.open(url,'','toolbar=0,titlebar=0,fullscreen=1,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1,width=800,height=600,left = 82,top = 54','');
} 

</script>
  
</head>  

<body onload="c1(),month_process_view()">
 
<form action="allben_with_met.jsp" method="get" name="myform">
<table width="75%" align="center" border="1" cellspacing="0" cellpadding="10" style="BORDER-COLLAPSE: collapse"
	border="1" borderColor="#92c2d8">
	<Tr bgcolor="skyblue"> 
	<td colspan="3" align="center" ><font color="#003300"><b> Transaction Monitoring Reports </b></font> </td></tr>
	<% 
	System.out.println("TEST"); 
	String new_cond=Controller.new_cond;
	Controller obj = new Controller();
	Calendar cal = Calendar.getInstance();
	int pr=Integer.parseInt( obj.setValue("process",request));
	int report=Integer.parseInt( obj.setValue("report",request));
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
	int prv_month=0;
	if (month==1) prv_month=12;
	else
	prv_month=month-1;
	
	
	int prv_year=year;
	if (prv_month==12)  prv_year=year-1;
	String reg= obj.setValue("reg",request);
	String off_id= obj.setValue("off",request);
	String userid="0",Office_id="",Office_Name="";
	String Date_dis=day+"/"+month+"/"+year;
     Connection con=obj.con();
	 obj.createStatement(con);
	try {
		String qry = "  select " + " REGION_OFFICE_ID, "
				+ " OFFICE_LEVEL_ID, " + " OFFICE_ID, "
				+ " OFFICE_NAME " + " from  "
				+ " COM_MST_ALL_OFFICES_VIEW " + " where  "
				+ " OFFICE_LEVEL_ID='RN' order by OFFICE_ID ";
		PreparedStatement ps = con.prepareStatement(qry);
		ResultSet rs_sub1=ps.executeQuery();;
		String combo1="<select name='reg' id='reg' class='select' onchange='rld1(this.value)' ><option value=0>Select</option>";
		String reg_id="",selc1="";
		while (rs_sub1.next())  
		{
			reg_id=rs_sub1.getString("OFFICE_ID");
			if (Integer.parseInt(reg_id)==Integer.parseInt(reg))
				selc1="selected";
			else
				selc1="";
			System.out.println(selc1);
			combo1+=" <option value='"+rs_sub1.getString("OFFICE_ID")+"' "+selc1+">"+rs_sub1.getString("OFFICE_NAME")+"</option>";		
		}
	   ps.close();
	   rs_sub1.close();
		combo1+="</select>";
		
			qry = "  select  REGION_OFFICE_ID,OFFICE_LEVEL_ID,OFFICE_ID,OFFICE_NAME from  "
					+ " COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID='DN' and  REGION_OFFICE_ID="
					+ reg +" and OFFICE_ID  in ( select OFFICE_ID from PMS_DCB_DIV_DIST_MAP) order by OFFICE_ID";
		 
			PreparedStatement ps_sub2 = con.prepareStatement(qry);
			ResultSet rs_sub2 = ps_sub2.executeQuery();
			String combo2="<select name='off' id='off' class='select' ><option value=0>Select</option>";
			String off_id2="";
			selc1="";
			while (rs_sub2.next())  
			{
				off_id2=rs_sub2.getString("OFFICE_ID");
				if (Integer.parseInt(off_id2)==Integer.parseInt(off_id))
					selc1="selected";
				else
					selc1="";
				System.out.println(selc1);
				combo2+=" <option value='"+off_id2+"' "+selc1+">"+rs_sub2.getString("OFFICE_NAME")+"</option>";		
				 
			}
		combo2+="</select>";
		ps_sub2.close();
		rs_sub2.close();  
	 	String[] monthArrv = { "0","1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11", "12" };
		String []monthArr ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
				String prallow=obj.setValue("prallow",request);
				String mnth=obj.setValue("smonth",request);
				String syear=obj.setValue("year",request);
				String prset=obj.setValue("prset",request);
				 
				if (prallow.equalsIgnoreCase("Submit") && !off_id.equalsIgnoreCase("0")   )
				{
					if (!mnth.equalsIgnoreCase("0") && !syear.equalsIgnoreCase("0")   )
					{
						 qry="";
						 qry="update PMS_DCB_SETTING set PR_ENABLE='"+prset+"' where YEAR="+syear+" and MONTH="+mnth+" and OFFICE_ID="+off_id;
						  
						try
						{
							int res=obj.setUpd(qry);
							if (res >0)
							{
							%>
						<script>
							alert ("Authorized Done");
						</script>
							<%
							}
						}catch(Exception e) {}
						
						prallow="";  
						mnth="";
						syear="";
						prset="";
					}
				}
	 	String cb=obj.combo_str("PMS_DCB_MIS_REPORT_TITLE","REPORT_HEADING","REPORT_REF"," where REPORT_SNO between 2 and 6 order by REPORT_REF","rpt","","class=select style='width:250px' ");
		%>
	  
				<Tr>
						<td    align="left" ><label class="lbl">Region</label></td> 
			   			<td    align="left" ><%=combo1%> </td>
			   	</Tr>
			   	<tr>
			   			<td  align="left"  width="25%"> <label class="lbl">Division</label></td>
			   			<td   align="left" ><jsp:expression>combo2</jsp:expression></td>
				</tr>		
			<tr>
				<td><label class="lbl">Master Report</label></td>
			     		<td>
			     			<select id="report" name="report" class='select' onchange="month_process_view()">
							<option value="0" <%=(report==0)?"selected":""%>>Select</option>
							<option value="3" <%=(report==3)?"selected":""%>>Beneficiary Master</option>				
							<option value="1" <%=(report==1)?"selected":""%>>Beneficiary Meter</option>
							</select>							
						</td>
						<td> 
							<input  class="fb2"  type="button" value="View" onclick="rld()" id="b1" >&nbsp;&nbsp;
							<input class="fb2" type="button" value="Refresh" onclick="window.location.reload()" id="b1">&nbsp;
						</td>
			</tr>
				<tr><td style="width: 326px"><label class="lbl">&nbsp;&nbsp;Year </label> &nbsp;&nbsp;&nbsp;</td>
				<td  style="width: 426px">
				
			 
				        <select class="select" id="year" name="year" style="width: 110pt;" onchange="report_period_verify(document.getElementById('smonth'),this)" >
				        <option value="0">Select Year</option>			 
						  <%
                			for (int i=year-6;i<=year;i++)
                			{
                 				if (i==prv_year)
                 				{
                			%><option value="<%=i%>" selected="selected"><%=i%></option><%} else { %>
                			<option value="<%=i%>"><%=i%></option><%} %>
                			<%} %>   
					</select>&nbsp;&nbsp;&nbsp;&nbsp;Month&nbsp;&nbsp;&nbsp; 
					 <select class="select" id="smonth" name="smonth" onchange="report_period_verify(this,document.getElementById('year'))" style="width: 110pt;"  >
				<option value="0">-Select Month-</option>
				 	<%  for (int j = 1; j <=12  ; j++)
				 	{
				 		if (j==prv_month) 
				 		{
						%><option value="<%=j%>"  selected="selected"><%=monthArr[j]%></option><%}else{ %>
						  <option value="<%=j%>" ><%=monthArr[j]%></option><%} %>
					<% }  %>
				</select>
				     
				         </td> </tr>
			   <tr>
				    <td><label class="lbl">  Authorize Pumping Return Entry</label> </td><td>
						<select name="prset"  class="select" >
							<option value="0">--Select---</option>
							<option value="Y">Yes</option>
							<option value="N">No</option>
						</select>
					<td>
						<input type="submit" name="prallow" value="Submit" class="fb2" />
					</td>
				</tr> 
			<tr>  
			     <td><label class="lbl">Select Transaction Report&nbsp;&nbsp;</label></td>
					  <td>
						<select id="report1" name="report1" class='select'>
							<option value="0" <%=(report==0)?"selected":""%>>Select</option>
							<option value="2" <%=(report==2)?"selected":""%>>Opening Balance </option>
							<option value="4" <%=(report==4)?"selected":""%>>Pumping Return</option>
							<option value="6" <%=(report==6)?"selected":""%>>Beneficiary Receipt</option> 
							<option value="7" <%=(report==7)?"selected":""%>>DCB Statistics </option>
							<option value="9" <%=(report==9)?"selected":""%>>Demand and Collection</option>
							<option value="10" <%=(report==10)?"selected":""%>>Design,Pumped and Supply Qty</option>
							
							<!-- 	
							<option value="8" <%=(report==8)?"selected":""%>>Pumping Return - Scheme Wise </option>							
						   <option value="18" <%=(report==18)?"selected":""%>>Pumping Return - Division-Scheme Wise</option>
					    -->  
						</select>&nbsp;&nbsp;&nbsp;&nbsp;
			</td>
			<td>	 
				<input  class="fb2"  type="button" value="View" onclick="rld()" id="b1" >&nbsp;&nbsp;
				<input class="fb2" type="button" value="Refresh" onclick="window.location.reload()" id="b1">&nbsp;
				<a href="javascript:rld6()">..</a>				 
				<a href="javascript:rld9()">.</a>  
			</td>
			</tr> 
			 
	<tr> 
     	<td colspan="3" align="right">  
       	<select id="rtype" name="rtype" style="width: 110pt;">
       						<option value="0">  Report View Type</option>
       						<option value="1" selected="selected">PDF </option>  
							<option value="2" >  Excel </option>							
							<option value="3">  HTML </option>  
		</select></td>     		
     </tr>
	<tr>    
	<td colspan=3 align="center"><input type=button value="Exit" onclick="self.close()"></td></tr>	
	</table> 
		<%
			} catch (Exception e) {  
				out.println(e);
			}
		%>
		<% 
		con.close(); 
		%>   
<input type=hidden id="process" value="2"> 
</form>
</body>
</html>