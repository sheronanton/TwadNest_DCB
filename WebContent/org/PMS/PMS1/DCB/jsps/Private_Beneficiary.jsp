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
    <script type="text/javascript" src="../scripts/cellcreate.js"></script>
     <script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
    <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/msg.js"></script>
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
	  //	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
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
  <body onload="callServer('Get'); callServer('Type'); callServer('District');" >
  <form name="mstprivate" id="mstprivate" action="">
  <table  class="fb2" border="0" width="80%" align="center" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" cellpadding="5">
    <tr class="tdH" align="center" style="color:black">
        <td colspan="2" class="tdText"> <div align="center"> Private Beneficiaries - <%=Office_Name %> </div></td>
    </tr>
    <tr class="tdText">
        <td>Private Beneficiary Code</td>
        <td><input type="text" name="sno" class="tb1"  id="sno" maxlength="5" size="5" readonly="readonly" style="background-color: #ececec"/><small>(Auto generated)</small></td>
    </tr>
    <tr class="tdText">
        <td>District</td>
        <td>
        	<select name="dis" id="dis"  class="select" onchange="callServer('Get')"/>
        		<option value="0">--Select District--</option>
        	</select>
        </td>
    </tr>
    <tr class="tdText">
        <td class="tdText"> Private Beneficiary Type</td>
        <td>
        	<select name="type" id="type" onchange="callServer('Get');"  class="select" />
        		<option value="0">--Select Beneficiary Type--</option>
        	</select>
        </td>
    </tr>
    <tr class="tdText">
        <td>Private Beneficiary Name</td>
        <td><input type="text" name="desc" maxlength="35" size="35" id="desc"  class="tb6"/></td> <!-- style="TEXT-TRANSFORM:UPPERCASE"  -->
    </tr>
    <tr class="tdText">
        <td>Billing Name</td>
        <td><input type="text" name="adr" maxlength="35" size="35" id="adr" class="tb6" /> (Eg: &nbsp; Ms/Mr XYZ, Designation)</td> 
        <!-- style="TEXT-TRANSFORM:UPPERCASE"  -->
    </tr>
     <tr   align="center">
        <td colspan="2" >               
                <input type="button" name="Add" value="Add" id="Add" onclick="callServer('Add')" class="fb2"/>
                <input type="button" name="Update" value="Update" id="Update" class="fb2" onclick="callServer('Update')" disabled/>
                <input type="button" name="Delete" value="Delete" id="Delete" class="fb2" onclick="callServer('Delete')" disabled/>
                <input type="reset" name="Clear" value="Clear"  id="Clear" class="fb2" onclick="clearAll(); callServer('Get');"/>
                <input type="button" name="Exit" value="Exit"  id="Exit" class="fb2" onclick="self.close()"/>&nbsp;<input type="reset" name="Clear" value="View" id="Clear" class="fb2" onclick="callReport();"><img src="../../../../../images/help_images.jpg" height="18px" width="45px" style="vertical-align: bottom;" onClick="window.open('help1.jsp?fn=5','mywindow','scrollbars=yes')">              
        </td>
    </tr>
   
    </table>
     </form>
    <table  class="fb2" id="existing"   width="80%" align="center" cellspacing="0" cellpadding="0" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
        <tr class="tdH">
            <td class="tdText" align="center">Select</td>
            <td class="tdText"  align="center">Private Beneficiary</td>
            <td class="tdText"  align="center">Beneficiary Type</td>
            <td class="tdText"  align="center">District</td>
            <td class="tdText"  align="center">Billing Name</td>
        </tr> 
        <tbody id="tblList" name="tblList" class="tdText">
        </tbody>
    </table>
  </body> 
</html>