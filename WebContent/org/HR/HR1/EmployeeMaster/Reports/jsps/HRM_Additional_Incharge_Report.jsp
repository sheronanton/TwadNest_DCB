<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.syntax.jedit.InputHandler.insert_break"%>
<%@page import="org.w3c.dom.Document"%>
<%@page import="org.syntax.jedit.InputHandler.document_end"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%
 
  Connection connection=null;
  PreparedStatement ps=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
  
  
  try
  {
  
            ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rs.getString("Config.DSN");
            String strhostname=rs.getString("Config.HOST_NAME");
            String strportno=rs.getString("Config.PORT_NUMBER");
            String strsid=rs.getString("Config.SID");
            String strdbusername=rs.getString("Config.USER_NAME");
            String strdbpassword=rs.getString("Config.PASSWORD");

          //  ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
           ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 

             Class.forName(strDriver.trim());
             connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  
  %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title> Additional Incharge Details Report</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"         media="screen"/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"         media="screen"/>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript" src="../../scripts/CalendarControl.js"></script>
    <script type="text/javascript"
            src="../scripts/Hrm_desig_wise_sum_rep.js"></script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
          
           <style >
option.red {color: #6600FF; }
option.pink {background-color: #ffcccc;}

 table.tablesorterform {
               font:bold 14px times new roman;
               text-align: center;
               border-collapse:collapse;
               border: 1px solid #5FC1F5;
       }
       table.tablesorterform thead tr th {
               background-image: -moz-linear-gradient(top, #dcf4fe 25%, #b6eaff 80%);
               border: 1px solid #5FC1F5;
               font: 12px Verdana, Geneva, sans-serif;
               padding: 4px;
       }
       table.tablesorterform tr th {
               background-image: -moz-linear-gradient(top, #dcf4fe 25%, #b6eaff 80%);
               border: 1px solid #5FC1F5;
               font-size: 9pt;
               padding: 4px;
       }
       body{
       background-image: -moz-linear-gradient(bottom, #fff 10%, #fff 60%);
       }
.colorch tr:nth-child(odd) {
 background-color: #fff;
 border: 1px solid #5FC1F5;
}
.colorch tr:nth-child(even) {
 background-color: #eff8fd;
 border: 1px solid #5FC1F5;
}
.spname td
{
padding-left:10px;
}

 
 

</style>
          
          
    <style type="text/css">
      body {
      background-color: #ffffff; 
}
      a:link { color: #002173; }
      
      <!--div.scroll {	
      height: 100px;	
      width: 100%;	
      overflow: auto;	
      border: 1px solid #666;	
      background-color: #fff;	
      padding: 0px;
     visibility: hidden;
     position: relative;
      }-->
      
    </style>
      <%
  Calendar c = new GregorianCalendar();
int fyr=c.get(Calendar.YEAR);
int tyr=fyr+1;
 int colspan=3; 
  
   System.out.println("fyr--->"+fyr);
     System.out.println("tyr--->"+tyr);
  String finyr=fyr+"-"+tyr;
  System.out.println("finyr--->"+finyr);
    String san_str="";
  String status="";
  String empstatus="";
  String desig="";
  String des_id="";
  String rank_id="";
  String cadre_id="";
  
  san_str=request.getParameter("san");
  status=request.getParameter("status");
  empstatus=request.getParameter("option");
  desig=request.getParameter("optdes");
  des_id=request.getParameter("des_id");
  rank_id=request.getParameter("rand_id");
  cadre_id=request.getParameter("cadre_id");
  
  String tit=""; String tit1="";
  
 
  
  System.out.println("san_str--->"+san_str);
  System.out.println("status--->"+status);
  System.out.println("empstatus--->"+empstatus);
  System.out.println("desig--->"+desig);
  System.out.println("des_id--->"+des_id);
 System.out.println("rank_id--->"+rank_id);
 System.out.println("cadre_id--->"+cadre_id);
   %>
<script type="text/javascript">   
	
	function nullcheck()
	{
	
	if(document.getElementById("eff_from").value=="")
	{
		alert("Please Select From Date");
		document.getElementById("eff_from").focus();
		return false;
	}
	else  if(document.getElementById("eff_to").value=="")
	{
		alert("Please Select To Date");
		document.getElementById("eff_to").focus();
		return false;
	}
	
	return true;
	
	}
	function getDate()
	{
	
	var frm_date=document.getElementById("eff_from").value;
	var to_date=document.getElementById("eff_to").value;
	var add_inc="";
	var Title="";
	var val=nullcheck();
	var valid=Compare('eff_from','eff_to');
	if(val==true)
	{
	if(valid==true)
	{
	if(document.HRM_Additional_Incharge_Report.add_inc[0].checked==true)
	{
	add_inc='Addi';
	Title=" Additional ";
	}
	else if(document.HRM_Additional_Incharge_Report.add_inc[1].checked==true)
	{
	add_inc='Incharge';
	Title=" In ";
	}
	else if(document.HRM_Additional_Incharge_Report.add_inc[2].checked==true)
	{
	add_inc='Both';
	Title=" Additional / In ";
	}
	
	document.HRM_Additional_Incharge_Report.action="Addition_Incharge_Report.jsp?frm_date="+frm_date+"&to_date="+to_date+"&add_inc="+add_inc+"&Title="+Title;
	document.HRM_Additional_Incharge_Report.submit();
	}
	}
	}
	function clears()
	{
	
		document.getElementById("eff_from").value="";
		document.getElementById("eff_to").value="";
	}
	
	function Compare(fdate,tdate)
{
	
	var fromDate=document.getElementById(fdate).value;
	var toDate=document.getElementById(tdate).value;
	
	
	
	
	var ret=true;
	if(fromDate =="" || toDate == "")
	{
	}
	else
	{
		var fret=check_dateformat(document.getElementById(fdate));
		var tret=check_dateformat(document.getElementById(tdate));
		if(fret==true && tret==true)
		{
	var f_date =new Array();
	var t_date =new Array();
	
	f_date=fromDate.split("/");
	t_date=toDate.split("/");
	
	var fromYear=0,toYear=0,fromMonth=0,toMonth=0,fromDay=0,toDay=0;
	
	if(f_date[1]=='08' || f_date[1] =='8')
		f_date[1]="08";
	
	if(f_date[1]=='09' || f_date[1] =='9')
		f_date[1]="09";
	
	if(f_date[0]=='08' || f_date[0] =='8')
		f_date[0]="08";
	
	if(f_date[0]=='09' || f_date[0] =='9')
		f_date[0]="09";
	
	if(t_date[1]=='08' || t_date[1] =='8')
		t_date[1]="08";
	
	if(t_date[1]=='09' || t_date[1] =='9')
		t_date[1]="09";
	
	if(t_date[0]=='08' || t_date[0] =='8')
		t_date[0]="08";
	
	if(t_date[0]=='09' || t_date[0] =='9')
		t_date[0]="09";
	
	fromYear=parseInt(f_date[2],10);
	fromMonth=parseInt(f_date[1],10);
	fromDay=parseInt(f_date[0],10);
	
	toYear=parseInt(t_date[2],10);
	toMonth=parseInt(t_date[1],10);
	toDay=parseInt(t_date[0],10);
	
	if(fromYear>toYear)
	{
		ret= false;
	}
	else if(fromYear < toYear)
	{
		ret= true;
	}
	else if(fromMonth > toMonth)
	{
		ret= false;
		
	}
	else if(fromMonth < toMonth)
	{
		ret= true;
		
	}
	else if(fromDay < toDay)
	{
		ret= true;
		
	}
	else if(fromDay >= toDay)
	{
		ret= false;
		
	}
	
	}
	}
	
	if(ret==false)
	{
		alert("From Date Should be less than To Date");
		//document.getElementById(fdate).value="";
		document.getElementById(tdate).value="";
		return false;
	}
	else
	{
		return true;
		
	}
	
}

function check_dateformat(field)
{
	
	var arr=new Array();
	
	var field_value=field.value;
	field_value=field_value.trim();

	if(field_value=="")
	{
	}
	else
	{

		
	arr=field_value.split("/");
	
//	alert(arr.length);
	if(arr.length==3)
	{
		var ret=check_validdate(arr[0],arr[1],arr[2]);
		if(ret==false)
		{
			alert("Invalid Date.");
			field.value="";
			return false;
		}
		else
		{
			return true;
		}
	}
	else
	{
		alert("Date format Should be DD/MM/YYYY");
		field.value="";
		return false;
	}
	
	}
	
}

function check_validdate(Day,Mn,Yr){
    var DateVal = Mn + "/" + Day + "/" + Yr;
    var dt = new Date(DateVal);
    
    if(parseInt(Yr)<=1900 || parseInt(Yr)>2100)
	{
		return false;
	}
	else if(dt.getDate()!=Day)
	{
       return(false);
        
	}
    else if(dt.getMonth()!=Mn-1)
    {        
        return(false);
       
    }
    else if(dt.getFullYear()!=Yr)
    {
    	return(false);
    }
    else
    {
    	return true;
    }

 }
function loaddate()
{
var today=new Date();
		var month = today.getMonth() + 1;
		var day = today.getDate();
		var year = today.getFullYear();
		
   document.getElementById("eff_from").value='01/'+month+'/'+year;
    document.getElementById("eff_to").value=day+'/'+month+'/'+year;
}
</script>
    
  </head>
  <!--- action="../../../../../EmployeeDetailReportServ.rep" -->
  <body onload="loaddate()">
  

  <form name="HRM_Additional_Incharge_Report" method="POST" action="../../../../../../HRM_Additional_Incharge_Report">    
    <table border='1' width="70%" align="center" >
     <tr>
      <td colspan="2" align="center" valign="top" class="table" width="55%">
       <table border="1" width="100%">
        <tr>
          	<td class="tdH" colspan="2" align="center">Charge Details Report</td>
        </tr>
        <tr>
        <td> From Date
        </td>
        <td>
        <input type="text" name="eff_from" id="eff_from" size="10" maxlength="10">
        <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.HRM_Additional_Incharge_Report.eff_from);" alt="Show Calendar"/>
        </td>
        </tr>
        
           
           <tr>
        <td> To Date
        </td>
        <td>
        <input type="text" name="eff_to" id="eff_to" size="10" maxlength="10">
        <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.HRM_Additional_Incharge_Report.eff_to);" alt="Show Calendar"/>
        
        </td>
        </tr>
               
           <tr>
           <td>Select Charge Type</td>
           <td><input type="radio" name="add_inc" id="add_inc" checked="checked" >Additional Charge
           <input type="radio" name="add_inc" id="add_inc">In Charge
           <input type="radio" name="add_inc" id="add_inc">Both
           </tr>     
                
                
                
                
                
                
                
      
       <tr>
			<td colspan="7" class="tdH" align="center">	
			     <input type="button" id="cmdback" name="Submit" value="Submit" onclick="getDate();"> 
			    <input type="button" id="clear" name="clear" value="Clear" onclick="clears();"> 
			    <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="self.close();"> 
           </td>
       </tr>
  </table>
</td>
</tr>
</table>
</form>
</body>
</html>