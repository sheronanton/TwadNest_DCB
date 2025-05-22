<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link href="../../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
<script type="text/javascript" src="../../../../../../org/Library/scripts/CalendarControl.js"></script>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<title>TWAD PHASE II | Review Report</title>
<script type="text/javascript">
 
function rld10() 
{
	var year=document.getElementById("pyear").value; 
	var smonth=document.getElementById("pmonth").value;
	var option=document.getElementById("rtype").value;    
	var date=(document.getElementById("date").value).split("/");
	var sdate=new Date();
	var sd=date[0];
	var sm=date[1];
	var sy=date[2];
	var cd=sdate.getDate();
	var cm=sdate.getMonth()+1;
	var cy=sdate.getFullYear();
	var ss=sd+"/"+sm+"/"+sy;  
	var flag=0;
	
	if (sy <= cy)
	{
		if (sm <= cm)
		{
			if (sd <= cd)
			{
				flag=0;
			}
			else
			{
				flag=1;
			}			
		}else
		{
			flag=1;
		}
	}else
	{
		flag=1;
	}
	var ssdate=new Date(date[0]+"/"+date[1]+"/"+date[2]);  
	
	  
	if (flag > 0 )
	{
		alert("Please check selected date..\n Date cannot be future date.");     
	} 
	else
	{  
	var url="../../../../../../Pms_Dcb_Ledger_Report?cdate="+ss+"&pr=7&option="+option+"&month="+smonth+"&year="+year;	
	window.open(url,'','toolbar=0,titlebar=0,fullscreen=1,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1,width=800,height=600,left = 82,top = 54','');
	}
}  
function rld12_1(a) 
{
	var year=document.getElementById("pyear").value; 
	var smonth=document.getElementById("pmonth").value;
	var option=0;
	var date_cond="";
	var select_count=0;
	 
	
	var url="Dash_board_stmt1.jsp?pr="+a+"&option="+option+"&month="+smonth+"&year="+year+"&select_count="+(select_count+1);;

	if (year==0 || smonth==0 )
	{
		alert("Select month and year");
	} 
	else
	{
		window.open(url,'','toolbar=0,titlebar=0,fullscreen=1,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1,width=800,height=600,left = 82,top = 54','');
	}
}
function rld12(a) 
{
	var year=document.getElementById("pyear").value; 
	var smonth=document.getElementById("pmonth").value;
	var option=document.getElementById("rtype").value;   
	var dselect=document.getElementsByName("dselect");
	var selectvalue=0;
	  
	for (var i=0;i<dselect.length; i++)
	{  
		if (dselect[i].checked) {
			selectvalue=dselect[i].value;
			break;
          }
	}

	var url="../../../../../../Pms_Dcb_Ledger_Report?pr="+a+"&option="+option+"&month="+smonth+"&year="+year;

	if (year==0 || smonth==0 )
	{
		alert("Select month and year");
	} 
	else
	{
		window.open(url,'','toolbar=0,titlebar=0,fullscreen=1,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1,width=800,height=600,left = 82,top = 54','');
	}
}  
function rld14(a) 
{
	var year=document.getElementById("pyear").value; 
	var smonth=document.getElementById("pmonth").value;
	var option=document.getElementById("rtype").value;   
	var dselect=document.getElementsByName("dselect");
	var date_cond="";
	var selectvalue=0;
	var select_count=0;
	var count=-1;
	for (var i=0;i<dselect.length; i++)
	{
		if (dselect[i].checked) 
			{		
			count++;
			selectvalue=dselect[i].value;
			break;
            }
	}
	select_count=i;  
	var mcount=0;
	if (year==0 || smonth==0 )
	{
		alert("Select month and year");
		mcount=1;
	} else
	{
		if (count!=-1   )    
		{
					var spl_val_check=0;
					for (var i=0;i<=select_count; i++)
					{
						var startDate =document.getElementById("sdate"+(i+1)).value;
						var endDate =document.getElementById("edate"+(i+1)).value;						 
						var regExp = /(\d{1,2})\/(\d{1,2})\/(\d{2,4})/;
						if(parseInt(endDate.replace(regExp, "$3$2$1")) >= parseInt(startDate.replace(regExp, "$3$2$1")))
						{
							spl_val_check=0;
						}else
						{  
							alert("starting date cannot be greater than ending date");
							spl_val_check=1;
							break;
						}
						   
						
						date_cond+="&sdate"+(i+1)+"="+document.getElementById("sdate"+(i+1)).value
						date_cond+="&edate"+(i+1)+"="+document.getElementById("edate"+(i+1)).value
					}
								
					var url="../../../../../../Pms_Dcb_Ledger_Report?pr="+a+"&option="+option+"&month="+smonth+"&year="+year+"&select_count="+(select_count+1)+""+date_cond;
					 
					if (year==0 || smonth==0 )
					{
						alert("Select month and year");
					} 
					else
					{
						if (select_count==1)
						{
						}else if (select_count==2)
						{
						}else if (select_count==1)
						{
						}else
						{
							url=""+url+""+date_cond+""+"&select_count="+select_count;        
						}

					  		if (spl_val_check==0) 	
							window.open(url,'','toolbar=0,titlebar=0,fullscreen=1,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1,width=800,height=600,left = 82,top = 54','');
					   
					}		 
			}else   
			{
				alert("Please Select Start and End Dates.")
			}
	}
}  
function rld15(a) 
{
	var year=document.getElementById("pyear").value; 
	var smonth=document.getElementById("pmonth").value;
	var option=document.getElementById("rtype").value;   
	var dselect=document.getElementsByName("dselect");
	var date_cond="";
	var selectvalue=0;
	var select_count=0;
	var count=-1;
	for (var i=0;i<dselect.length; i++)
	{
		if (dselect[i].checked) 
			{		
			count++;
			selectvalue=dselect[i].value;
			break;
            }
	}
	select_count=i;  
	var mcount=0;
	if (year==0 || smonth==0 )
	{
		alert("Select month and year");
		mcount=1;
	} else
	{
		if (count!=-1   )    
		{
					var spl_val_check=0;
					for (var i=0;i<=select_count; i++)
					{
						var startDate =document.getElementById("sdate"+(i+1)).value;
						var endDate =document.getElementById("edate"+(i+1)).value;						 
						var regExp = /(\d{1,2})\/(\d{1,2})\/(\d{2,4})/;
						if(parseInt(endDate.replace(regExp, "$3$2$1")) >= parseInt(startDate.replace(regExp, "$3$2$1")))
						{
							spl_val_check=0;
						}else
						{  
							alert("starting date cannot be greater than ending date");
							spl_val_check=1;
							break;
						}
						   
						
						date_cond+="&sdate"+(i+1)+"="+document.getElementById("sdate"+(i+1)).value
						date_cond+="&edate"+(i+1)+"="+document.getElementById("edate"+(i+1)).value
					}
								  
					var url="dash_board_html_abs.jsp?pr="+a+"&option="+option+"&month="+smonth+"&year="+year+"&select_count="+(select_count+1)+""+date_cond;
					 
					if (year==0 || smonth==0 )
					{
						alert("Select month and year");
					} 
					else
					{
						if (select_count==1)
						{
						}else if (select_count==2)
						{
						}else if (select_count==1)
						{
						}else
						{
							url=""+url+""+date_cond+""+"&select_count="+select_count;        
						}

					  		if (spl_val_check==0) 	
							window.open(url,'','toolbar=0,titlebar=0,fullscreen=1,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1,width=800,height=600,left = 82,top = 54','');
					   
					}		 
			}else   
			{
				alert("Please Select Start and End Dates.")
			}
	}
}  
function sub()
{
	  document.myform.submit();
}
  
</script>
 <script type="text/javascript" src="../../scripts/cellcreate.js"></script>
</head>  
<body>
<form action="mdrpt_2.jsp" method="get" name="myform">
<%
				String userid="0",Office_id="",Office_Name="",table_heading="",table_column="",table_header="";
				String combo1="";
				Connection con=null;
				String smonth="",syear="",html="",office_="";
				Controller obj=null;
				Calendar cal = Calendar.getInstance();
				int day = cal.get(Calendar.DATE);
				
				int month = cal.get(Calendar.MONTH) + 1;
				int year = cal.get(Calendar.YEAR);
				int dy=cal.get(Calendar.DAY_OF_YEAR);
				int prv_month=0;
				if (month==1) prv_month=12;
				else
				prv_month=month-1;
				int prv_year=year;
				if (prv_month==12)  prv_year=year-1;
				String Date_dis=day+"/"+month+"/"+year;
				try
				{
				  obj=new  Controller();
				  con=obj.con();
				  obj.createStatement(con);
				  try
				  {
					   userid=(String)session.getAttribute("UserId");
				  }catch(Exception e) {userid="0";}
				  if(userid==null)
				  { 
						  response.sendRedirect(request.getContextPath()+"/index.jsp");
				  }
				  Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
				  smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
				  syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
				  if (Office_id.equals("")) Office_id="0";					
				  String regdiv=obj.isNull(obj.setValue("regdiv",request),1);
				  String disable="";
				  if (Integer.parseInt(Office_id)!=5000) disable=" disabled=disabled";
				  combo1=obj.combo_str("COM_MST_ALL_OFFICES_VIEW","OFFICE_NAME","OFFICE_ID"," where OFFICE_LEVEL_ID='RN' order by OFFICE_ID ","regdiv",regdiv," class=select onchange='rld1(this.value)' "+disable);
					
				  String rd="";
				  Office_Name=obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", " where OFFICE_ID="+Office_id);
				  if (Integer.parseInt(Office_id)==5000) rd=""; else rd="where OFFICE_ID="+Office_id;	
					
				  if (Integer.parseInt(Office_id)!=5000)
				  office_=obj.combo_str("COM_MST_OFFICES","OFFICE_NAME","OFFICE_ID"," where OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP "+rd+" )   ","div",Office_id," class=select  "  );
				  else
				  office_=obj.combo_str("COM_MST_ALL_OFFICES_VIEW","OFFICE_NAME","OFFICE_ID"," where OFFICE_LEVEL_ID='DN' and  REGION_OFFICE_ID="+regdiv+" and  OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP  )  ","div",Office_id," class=select "  );					
				}catch(Exception e)
				{
					userid="0";
					out.println(e);	  
				}		
				String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
				
				// combo1  Region   
				// width=  Division     
				
				int sel_month=Integer.parseInt(obj.setValue("pmonth",request));
				int sel_year=Integer.parseInt(obj.setValue("pyear",request));
				
				ArrayList a1=obj.week_dates_start(sel_year,sel_month);  
				ArrayList a2=obj.week_dates_end(sel_year,sel_month);  
				 int total_month=obj.number_of_week(sel_year,sel_month,1);
%>
 <table border="1" width="65%" align="center" cellpadding="10" cellspacing="0" style="border-collapse: collapse" >
 <tr  bgcolor="#3080A0"><td colspan="3" align="center"><b><font color=white>DCB Dash Board Report - Statement I	</font></b></td></tr>
  <!--  <tr>  
   	<td align="left"> <label class="lbl">Collection as on </label>				 	 	 		
				</td> 
				<Td>  
			<input class="tb5" type="text" name="date"  id="date" maxlength="10" size="10" onFocus="javascript:vDateType='3'"  value="<%=Date_dis%>" onkeypress="return  calins(event,this)" onblur="return checkdt(this);"></input>
       	 	&nbsp;&nbsp;&nbsp;<img src="../../../../../../images/calendr3.gif" onclick=" showCalendarControl(document.myform.date);"  alt="Show Calendar" height="24" width="19"></img>
					 </td><td> 	 <input type="button" style="color: green;font-weight: bold" value="Print" onclick="rld10()" id="b1" ></td>
	</tr> 
	   <tr><td colspan="3" align="center">Annexure Report</td></tr>   	--> 		 
					     
		<tr>
		  <td><font><label class="lbl">Month</label></font></td>	  	          
			   <td colspan="2">
			        <select class="select" id="pmonth" name="pmonth"  style="width:120pt;" onchange="report_period_verify(this,document.getElementById('pyear'))">
					<option value="0">-Select Month-</option>
				  	<% 	for (int j=1;j<=12;j++)	
				  	{
				  	if (j==prv_month) {	%>
				    <option value="<%=j%>" selected><%=monthArr[j]%></option>
				  	<%  } else {%>
				  	<option value="<%=j%>" ><%=monthArr[j]%></option>
				  	<%
				  	} 
				  	}%>
				 </select>
			 </td>	          
    </tr> 
		<tr>
	          <td><font><label class="lbl">Year</label></font></td>
			  <td colspan="2">
				  <select class="select"  id="pyear" name="pyear"  style="width:120pt;" onchange="report_period_verify(document.getElementById('pmonth'),this)" >
				   <option value="0">-Select Year-</option>
				  <% for (int i=year-2;i<=year;i++) 
				   { 
				   if (i==prv_year) {	%>
				   <option value="<%=i%>" selected><%=i%></option>
				   <%  } else {%>
				  <option value="<%=i%>"><%=i%></option>
				  <%}
				   }
				   %>
				  </select>
			   </td>
			  </tr>
		 
       		<tr> 
       			<td class="tdText" align="center" colspan="3">
          			<input type="button"  style="color: green;font-weight: bold"  value="Submit" onclick="rld12_1(14)">
       			 <input type="button"  style="color: red;font-weight: bold"  value="Close" onclick="javascript:window.close()">	</td>
       		</tr>
	</table>
	 
	</form>
</body>
</html>
 