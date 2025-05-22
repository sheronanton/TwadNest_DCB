<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

   <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
  
<title>Pending Opening Balance for Employee</title>
</head>
<body>
<form action="../../../../../../PendingOpeningBalance"  >
<table cellspacing="2" cellpadding="3" border="1" width="80%" align="center">
<tr class="tdH">
<th align="center" colspan="2">Pending GPF&nbsp;Impound-2003 Balance Details</th> </tr>
<tr>
                    <td colspan="2" class="tdH">Report Output Format</td>
                  </tr>
                  <tr>
                    <td colspan="2" class="table" align="left">
                      
                        <input type="radio" name="optoutputtype" checked value="pdf"></input>PDF
                                                                         Format
                      
                    </td>
                  </tr>
                  <tr>
                    <td colspan="2" class="table" align="left">
                      
                        <input type="radio" name="optoutputtype" value="excel"></input>EXCEL
                                                                         Format
                      
                    </td>
                  </tr>
                 
     
<tr class="table"><td colspan="2" align="center" >

<input type="submit" value="Submit"></td ></tr>

</table>
</form>
</body>
</html>