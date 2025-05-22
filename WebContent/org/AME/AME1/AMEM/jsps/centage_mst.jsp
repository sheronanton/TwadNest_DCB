<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
	<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
     <link href='../../../../../css/CalendarControl.css' rel='stylesheet' media='screen'/>
    <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/> 
    
     <script type="text/javascript" src="../scripts/master.js"></script>
    <script type="text/javascript" src="../scripts/cellcreate.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/CalendarControl.js"></script>
    <script type="text/javascript" src="../scripts/msg.js"></script>
    <script type="text/javascript">
    
    function cls_r()
    {

    	document.getElementById("cmdadd").disabled=false;
		document.getElementById("cmdupdate").disabled=true;
		}
    
    </script>
    
    <title>Centage Rate Master | TWAD Nest - Phase II </title>
  </head>
  <body onload="master(4,3)">
  <form name="chargedetails" action="">
   <table class=table width=75% align="center" border="1" cellpadding="5" style="border-collapse: collapse;border-color: skyblue"  >
    <tr align="center" style="color:black">
        <th colspan="2" class="tdH"> <div align="center"><strong>Centage Rate Master</strong></div></th>
    </tr>
    
 	<%		HttpSession session1 = request.getSession(false);
 			String	userid = (String) session1.getAttribute("UserId");
 			if (userid == null) {

				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			 
			Controller obj=new Controller(); 
			String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID",
					"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
							+ userid + "')");
			 
			
			if (Office_id.equals("")) Office_id="0";
			String Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
			if (Office_id.equals("")) Office_id="0";
 					String sch=obj.combo_str("PMS_SCH_MASTER","SCH_NAME","SCH_SNO", 
					 " where SCH_SNO in ( select distinct SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+") and sch_sno in (select scheme_sno from PMS_DCB_MST_BENEFICIARY_METRE where METER_STATUS='L' and office_id="+Office_id+") order by sch_name","sch_sno","","class='select' style='width: 262'   " );
  %>
  
  <tr >
        <td class="tdText"><label class="lbl">Centage No</label></td>
        <td><input type="text" name="centage_Id" id="centage_Id"  maxlength="5" size="7" readonly="readonly" style="background-color: #ececec"   /><small>(Auto generated)</small></td>
    </tr>
  	<Tr>
 	<td>Scheme Name </td>
 	<td><%=sch%></td>
 </Tr>
    <tr  >
        <td ><label class="lbl">Centage Rate</label></td>
        <td><input type="text"   name="centage_Rate" maxlength="5" size="7" id="centage_Rate"   /></td>
    </tr>
   
    
    <tr >
        <td ><label class="lbl">Centage w.e.f</label></td>
        <td><input type="text" name="centage_wef" maxlength="15" size="12" id="centage_wef"  />
       <img src="../../../../../images/calendr3.gif" alt="calendar" onclick="showCalendarControl(document.chargedetails.centage_wef)"></img>
        </td>
    </tr>
   <tr >
   
        <td > <div  id="statusdiv_name"><label class="lbl">Active Status </label></div>
        </td>
        <td><div  id="statusdiv"><select id="status" >
            <option value="" selected="selected" >Select</option>
            <option value="A">Active</option>
            <option value="D">Defunct</option>
        </select></div>
        </td>
        </tr>
    <tr align="center">
        <th colspan="2" >               
                <input type="button" name="cmdadd" value="Add" id="cmdadd" onclick="master(4,1);" class="btn"/>
                <input type="button" name="cmdupdate" value="Update" id="cmdupdate" onclick="master(4,2);" class="btn" disabled="disabled"/>
                <input type="reset" name="cmdclear" value="Clear"  id="cmdclear" onclick="cls_r()" class="btn"/>                
                 <input type="button" value="HELP" onclick="javascript:window.open('./twad_note.html#m5','_blank','toolbar=no, location=yes, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=yes, width=700, height=600');">
                 <input type="button" name="cmdexit" value="Exit"  id="cmdlist" onclick="self.close();" class="btn"/>   
        </th>
    </tr>  
    </table>
    <table class=table width=75% align="center" border="1" cellpadding="5" id="etable" style="border-collapse: collapse;border-color: skyblue" >
			<tr class="tdH">
				<td class="tdText">Select</td>		
				<td class="tdText">Scheme Name</td>		 
				<td class="tdText">Centage Rate</td>
				<td class="tdText">Centage w.e.f</td>
				<td class="tdText">Active Status</td>
			
			</tr>

		<tbody id="edata" align="left" valign="top"/>
    </table>
    <input type="hidden" id="rowcount" name="rowcount" value="0"/>
     </form>
  </body>
</html>