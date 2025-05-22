<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Private Beneficiaries | TWAD Nest - Phase II </title>
      <script type="text/javascript" src="../scripts/Private_Beneficiary.js"></script>
     <script type="text/javascript" src="../scripts/Basic.js"></script>
         <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
     
  </head>
  <%
  Controller obj=new Controller();
 String userid="0",Office_id="",Office_Name="";
 
Connection con;
try
{
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
	//  Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	if (Office_id.equals("")) Office_id="0";
	Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
	
     obj.conClose(con);
	}catch(Exception e) {
	out.println(e);
	  response.sendRedirect(request.getContextPath()+"/index.jsp");
}
   %>
  <script type="text/javascript">
  function callReport()
{
	 var type=document.getElementById('type').value;
	 var dis=document.getElementById('dis').value;
 	 window.open("../../../../../privateBeneficiaryListReport?action=listAll&dis="+dis+"&type="+type);
}
  
  </script>
  
  <body onload="callServer('Type'); callServer('District');" >
  <form name="mstprivate" action="">
  <table border="0" width="80%" align="center" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" cellpadding="10">
    <tr class="tdH" align="center" style="color:black">
        <td colspan="2"> <div align="center"><strong>Private Beneficiaries - <%=Office_Name %></strong></div></td>
    </tr>
     
 <input type="hidden" name="sno" class="tb1"  id="sno" maxlength="5" size="5" readonly="readonly" style="background-color: #ececec"/> 
  
           
        
    <tr class="tdText">
        <td>District</td>
        <td>
        	<select name="dis" id="dis"  class="select" />
        		<option value="0">--Select District--</option>
        	</select>
        </td>
    </tr>
    
    
    <tr class="tdText">
        <td>Private Beneficiary Type</td>
        <td>
        	<select name="type" id="type"  class="select" />
        		<option value="0">--Select Beneficiary Type--</option>
        	</select>
        </td>
    </tr>
 
    
     
    
    

    <tr  class="tdH"  align="center">
        <td colspan="2" >            
        		<input type="button" name="Clear" value="Print" id="Clear" class="fb2" onclick="callReport();">      
                 <input type="reset" name="Clear" value="Clear"  id="Clear" class="fb2" onclick="clearAll(); callServer('Get');"/>
                <input type="button" name="Exit" value="Exit"  id="Exit" class="fb2" onclick="self.close()"/>           
        </td>
    </tr>
   
    </table>
     </form>
     
  </body>
</html>