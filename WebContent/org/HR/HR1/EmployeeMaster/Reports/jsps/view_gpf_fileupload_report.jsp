<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
  
<title>GPF Subscription File Upload data Status</title>
<script>
function call()
{

    document.getElementById("filter_div").style.display="block";    
}
function callFun1()
{
    
     document.getElementById("divValidate").style.display="block";   
     document.getElementById("filter_div").style.display="block";   
}
function callFun2()
{
    
     document.getElementById("divValidate").style.display="none";  
//     document.getElementById("filter_div").style.display="none";   

}

function f()
{
	if((document.getElementById("OfcLoad1").checked==false)&&(document.getElementById("OfcLoad2").checked==false))
	{
		alert("Choose File Upload Option");
	}
	
	else if((document.getElementById("filter1").checked==false)&&(document.getElementById("filter2").checked==false))
	{
		alert("Choose Month wise OR Office wise");
	}
	else if(document.getElementById("fin_year").value==0)
	{
		alert("Choose Financial Year");
	}
	else
	{
		document.FrmGPFReport.action="../../../../../../FileUploadForGPFReport";
		document.FrmGPFReport.submit();
	}
}

</script>
</head>
<body onload="javascript:call()" >  

<form name="FrmGPFReport">
<%
java.util.Date d=new java.util.Date();

System.out.println("Current MM-->"+d.getMonth()+"Current Year-->"+(d.getYear()+1900));
int mth=d.getMonth()+1;
int year=(d.getYear()+1900);
/*HttpSession session=request.getSession(false);
    UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
    System.out.println("user id::"+empProfile.getEmployeeId());*/
String emp_id="11263";
%>
   <blockquote>
    <p>
     <u>
      &nbsp;
     </u>
    </p>
   </blockquote>
   <table cellspacing="2" cellpadding="3" border="1" width="80%" align="center">

<tr class="tdH">
<th align="center" colspan="5">GPF Subscription File Upload data Status</th> </tr>
<tr class="table"> <td > Generate Report for:</td> 
                    <td colspan="2" class="table" align="left">
              <input type="radio" name="OfcLoad" id="OfcLoad1" value="in" onclick="callFun1()"></input> Office With file Uploaded    </td>
              <td colspan="2" class="table" align="left">
              <input type="radio" name="OfcLoad" id="OfcLoad2" value="not in" onclick="callFun2()"></input> Offices which yet to upload the files    </td>
</tr>


<tr>  
		<td  class="table" align="left" colspan="5">
			<div id="divValidate" style="display:none">
			  <table cellspacing="2" cellpadding="3" border="0" width="100%" align="left">
			  <tr class="table" > <td colspan="3"> Validated:</td>
				 
					  <td colspan="2"  class="table" align="left">
		              <input type="radio" name="validated" value="Y"></input>Yes   </td>
		              <td colspan="2"   class="table" align="left">
		              <input type="radio" name="validated" value="N"></input>No  </td>  
		        </tr>
			  </table>
			</div>
		</td>
</tr>
<tr>  
		<td  class="table" align="left" colspan="5">
			<div id="filter_div" style="display:none">
			  <table cellspacing="2" cellpadding="3" border="0" width="100%" align="left">
			  <tr class="table" > <td colspan="3"> Report View:</td>
				 
					  <td colspan="2"  class="table" align="left">
		              <input type="radio" name="filter" id="filter1" value="office"></input> office wise </td>
		              <td colspan="2"   class="table" align="left">
		              <input type="radio" name="filter" id="filter2" value="month"></input> Month wise</td>  
		        </tr>
			  </table>
			</div>
		</td>
</tr>
<tr class="table"> <td > Financial Year:</td> <td colspan="5">
<select name="fin_year" id="fin_year">
 <option value=0>--Select Year-- </option>
<%
if(mth<4)
{
	%>
	<option value="<%=year-2%>-<%=year-1%>"><%=year-2%>-<%=year-1%></option>
	<option value="<%=year-1%>-<%=year%>"><%=year-1%>-<%=year%></option>
	
	<%
}
else
{
	%>
	
	<option value="<%=year-1%>-<%=year%>"><%=year-1%>-<%=year%></option>
	<option value="<%=year%>-<%=year+1%>"><%=year%>-<%=year+1%></option>
	<%
	
}
%>
</select> 
 </td></tr>
              </table>
           
<table cellspacing="2" cellpadding="3" border="1" width="80%" align="center">
 <tr>
 
                    <td colspan="5" class="tdH" align="center">Report Output Format</td>
                  </tr>
                  <tr>
                    <td colspan="5" class="table" align="left">
                      
                        <input type="radio" name="optoutputtype" checked value="pdf"></input>PDF
                                                                         Format
                      
                    </td>
                  </tr>
                  <tr>
                    <td colspan="5" class="table" align="left">
                      
                        <input type="radio" name="optoutputtype" value="excel"></input>EXCEL
                                                                         Format
                      
                    </td>
                  </tr>
                 
     
<tr class="table"><td colspan="5" align="center" >

<input type="button" value="Submit" onclick="f();"></td ></tr>

</table>
</form>
</body>
</html>