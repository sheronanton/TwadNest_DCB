<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Employee Fund Details Report</title>
   <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/Emp_Fund_Details_Report.js"></script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
</head>
 <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
<body>
<form name="frmEmployeeCategory" id="frmEmployeeCategory"  action="../../../../../../EmployeeProfileServSR" method="POST" >
 <table border="2" width="100%">
                
 <tr>
  <td colspan='4' class="tdH" align="center"><strong>Employee Details  - Fund Category</strong></td>
</tr>
<tr>
<td>         
<input type="radio" name="options" id="option1" value="all" checked="checked" >All Employees
</td>
</tr>
<tr>
<td>
<input type="radio" name="options" id="option2" value="gpf">GPF Employees
</td>
</tr>
<tr>
<td>
<input type="radio" name="options" id="option3" value="cps">CPS Employees
</td>
</tr><tr>
<td>
<input type="radio" name="options" id="option4" value="not_yet">Number yet to be alloted</td>
</tr>
  
<tr align="center" colspan="4">

<td align="center">

<input type="button" name="Print" value="Print" onclick="return callServer()"><input type="button" name ="Canel" value="Cancel" onclick="self.close()"></input>
Report&nbsp;Type
                       <select name="cmbReportType">
                              <option value="PDF" selected="selected">PDF</option>
                              <option value="EXCEL">EXCEL</option>
                            <!--  <option value="TXT">TXT</option>-->
                              <option value="HTML">HTML</option>
                            </select> 
</td>
</tr>
</table>
</form>
</body>
</html>