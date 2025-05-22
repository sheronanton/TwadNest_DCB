<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.*" %> 
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
<head> 
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href='../../../../../css/txtbox.css' rel='stylesheet' media='screen'/>
<script type="text/javascript" src="../scripts/Pms_Dcb_Ledger.js"></script>
<script type="text/javascript" src="../scripts/after_posted.js"></script>
<script type="text/javascript" src="../scripts/msg.js"></script>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<script type="text/javascript" src="../scripts/Office_Shift_new.js">
</script>
<script type="text/javascript">

function mess()
{
	var str = "Please Wait";
    var result = str.fontcolor("red");
   
document.getElementById("demo").innerHTML = result;
		
	}

function rld() 
{  
	 

			var res=report_period_verify_2(document.getElementById('month'),document.getElementById('year'));
			var res1=month_year_check(document.getElementById('month').value,document.getElementById('year').value);
			 
				if (res1!=1)
				{
					if (res!=1)  
					{
						document.getElementById("pms_dcb_ledger").submit();
					} 
				}
				 document.getElementById("demo").innerHTML = "";
}		
</script>
<title>DCB Monthly Data Posting | TWAD Nest - Phase II</title>
</head>
<body onload="divload(),flash()">
<form id="pms_dcb_ledger" name="pms_dcb_ledger" action="Pms_Dcb_ledger_create_div.jsp" method="post">
<jsp:useBean id="bean" class="Servlets.PMS.PMS1.DCB.reports.Process_status_bean" scope="page"></jsp:useBean>
<%
			String userid="0",Office_id="",Office_Name="",Beneficiary_Combo="";
            int yr=0;
            int mt=0;
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			Connection connection = null;
			PreparedStatement ps;
			ResultSet res;
			Controller obj = new Controller();
			try 
			{
			    connection = obj.con(); 
			    userid = (String) session.getAttribute("UserId");
				obj.createStatement(connection);
				
		 		//  Office_id=obj.getValu("HRM_EMP_CURRENT_POSTING", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp()  THEN OLD_OFFICE_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;
		 			Office_id=obj.getValue("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

				
	//			Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
				if (Office_id.equalsIgnoreCase("0") || Office_id.trim().equalsIgnoreCase("")) Office_id="5000";
				Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
			 	Beneficiary_Combo=obj.combo_str("PMS_DCB_MST_BENEFICIARY","BENEFICIARY_NAME","BENEFICIARY_SNO"," where status='L' and office_id="+Office_id ,"ben","","");
			    try 
			    {   
			        connection.clearWarnings();
			    } 
			    catch (SQLException e)  
			    {
			        System.out.println("Exception in creating statement:" + e);
			    }
			} 
			catch (Exception e) 
			{
			    System.out.println("Exception in openeing connection:" + e); 
			}
			String pmonth="0",pyear="0";
			pmonth=obj.setValue("month",request);
			pyear=obj.setValue("year",request);
						
		
			
			String new_cond=Controller.new_cond;
			String obfile="", report_head="",oid="";
			Connection con= null;
			String bmonth="", byear="", html="";
			int obstatusflag=0;
			String path="", path2="", path3="", path4="",path5="", offname="";
			try
			{
				obj = new Controller();
				con = obj.con();
				obj.createStatement(con);	
				try 
				{
					userid = (String) session.getAttribute("UserId");
				} catch (Exception e) 
				{
					userid = "0";
				}
				if (userid == null) 
				{
					response.sendRedirect(request.getContextPath()+ "/index.jsp");
				}
				
		 		//  Office_id=obj.getValu("HRM_EMP_CURRENT_POSTING", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp()  THEN OLD_OFFICE_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;
	
		 			Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	//			Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
				if (Office_id.equals("")) 	Office_id = "0";
				bmonth = pmonth;
				byear = pyear;
				oid=obj.setValue("oid",request);	 		
			 	if (!oid.trim().equalsIgnoreCase("0")) 		Office_id=oid;   
			 	bean.setYear(Integer.parseInt(byear));
			 	bean.setMonth(Integer.parseInt(bmonth));
				bean.setOffid(Integer.parseInt(Office_id));
				offname = bean.getProcess();
				path = bean.getTariff_img();
				path2 = bean.getPump_img();
				path3 = bean.getOb_img();
				path4 = bean.getWc_img();
				path5 = bean.getDemand_generation_img();
				obstatusflag = bean.getObstatusflag();
				if (obstatusflag == 1) 
				{
					obfile = "OpeningBalanceReport.jsp";
					report_head = " Opening Balance Report";
				} else 
				{
					obfile = "OBMissingReport.jsp";
					report_head = " Beneficiary Scheme Wise Omission Report";
				}
				 
			} catch (Exception e) {
				userid = "0";
			}  
 		String freeze_count= obj.getValue("PMS_DCB_DATA_FREEZE" ,"DCB_FREEZE","c","where CASHBOOK_YEAR="+pyear+" and CASHBOOK_MONTH="+pmonth+" and  ACCOUNTING_FOR_OFFICE_ID="+Office_id );
%>
<input type="text" value="<%=Office_id%>" id="office_id" name="office_id" hidden>
 <input type="radio" value="AllDivision" id="divselection "name="divselection" hidden  onclick="showdiv();"></input>
 <input type="radio" value="SelectDivision" id="divselection "name="divselection" hidden checked onclick="showdiv();"></input>


<table border="1" width="85%" align="center" cellpadding="4" cellspacing="0" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
	<tr class="tdH"  style="color:black">
   		<th colspan="4"   align="center" ><strong>DCB Monthly Data Posting -- <%=Office_Name%></strong></th>
   </tr>
   <tr class="tdText">
    		<td width="40%" colspan="2">Month</td>
         	<td colspan="2">
	         	<select id="month"  name="month"  style="width: 220pt" class="select"  onchange="rld()">
	        	 	<option value="0" selected="selected" >- - - Select - - -</option>
	         	<%
	         	String[] amonth = { "-select month-", "January", "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December" };
	         	for (int i=1;i<=12;i++) 
	         	{
	         	%>
	         	<option value="<%=i%>"  <% if (Integer.parseInt(pmonth)==i) { %> selected <%}%>><%=amonth[i]%></option>
	         	<%} %>
	          </select>
         </td>
     </tr>
     <tr class="tdText">
     		<td width="40%" colspan="2" ><label id="benname">Year</label></td>
            <td  colspan="2">
            	<select id="year"  name="year" tabindex="5" style="width:220pt"  class="select" onchange="rld(),mess()">
                	<option value="0" selected="selected">- - - Select - - -</option>
                	<%
                	for (int j=year;j>=2015;j--)
                	{
                	%>
                	<option value="<%=j%>"  <% if (Integer.parseInt(pyear)==j) {%>selected<%} %>><%=j%></option>
                	<%} %>
                 </select>
             </td>
     </tr>
      <tr class="tdText">
     	<td  width="40%" colspan="2" >
        	<label id="benname">Division </label> 
           </td>
            <td  colspan="2">
			<%
		    try 
	        {
		       	ps = connection.prepareStatement("select distinct COM_MST_OFFICES.OFFICE_ID,COM_MST_OFFICES.OFFICE_NAME from PMS_DCB_DIV_DIST_MAP join COM_MST_OFFICES on COM_MST_OFFICES.OFFICE_ID=PMS_DCB_DIV_DIST_MAP.OFFICE_ID where COM_MST_OFFICES.OFFICE_ID=? order by OFFICE_NAME");
		       	ps.setInt(1,Integer.parseInt(Office_id));
	            res = ps.executeQuery();%>
	       <select name="divisionid" id="divisionid">
	       <option value=0>- - - Select - - -</option>
	       <% while(res.next())
	        {
		   %>
		   <option value="<%=res.getInt("OFFICE_ID")%>" selected><%=res.getString("OFFICE_NAME")%></option>    
		   <% } %>            	
           </select>
           <% 
            connection.close();
	       }      catch (Exception e) 
		        {
		            System.out.println("Exception in openeing connection:" + e); 
		        }%>
             </td>
     </tr>    
     <TR> <td colspan="4" align="center"><font color=red>DCB Freeze has been made mandatory.&nbsp;&nbsp;&nbsp;&nbsp;Goto DCB Ledger -- > Monthly DCB --> Freeze DCB </font><br>&nbsp;<img id='img' src=''> <label id="msg"></label><br><p id="demo"></p> </td>   </TR>
     </table>  
    <div id="showdivload">  
     <table border="1" width="85%" align="center" cellpadding="4" cellspacing="0" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" >
      <tr class="tdText">
     	<td  width="40%" colspan="2" >
        	<label id="benname">Division </label> 
           </td>
            <td  colspan="2">
			<%
		    try 
	        {
		       	ps = connection.prepareStatement("select distinct COM_MST_OFFICES.OFFICE_ID,COM_MST_OFFICES.OFFICE_NAME from PMS_DCB_DIV_DIST_MAP join COM_MST_OFFICES on COM_MST_OFFICES.OFFICE_ID=PMS_DCB_DIV_DIST_MAP.OFFICE_ID where COM_MST_OFFICES.OFFICE_ID=? order by OFFICE_NAME");
		       	ps.setInt(1,Integer.parseInt(Office_id));
	            res = ps.executeQuery();%>
	       <select name="divisionid" id="divisionid">
	       <option value=0>- - - Select - - -</option>
	       <% while(res.next())
	        {
		   %>
		   <option value="<%=res.getInt("OFFICE_ID")%>" selected><%=res.getString("OFFICE_NAME")%></option>    
		   <% } %>            	
           </select>
           <% 
            connection.close();
	       }      catch (Exception e) 
		        {
		            System.out.println("Exception in openeing connection:" + e); 
		        }%>
             </td>
     </tr>    
     </table>
     </div>
    <table border="1" width="85% " align="center" cellpadding="4" cellspacing="0" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8"  >
       <tr>
         <td style="padding-top:20px;" colspan="6" ><input type="hidden" id="pr_status" name="pr_status" value="1"/>
                 <div align="center">
                
             	<%
            	String disable="";
            	if (freeze_count.equalsIgnoreCase("0")) 
            	{
            		disable="";
            	}else  
            	{
            		disable="disabled='disabled'";
            		 out.println("<font color='red'>DCB Data Freezed </font>&nbsp;&nbsp;&nbsp;");
            	}
            	%>
                 
     				 
                    <input type="button" name="Generateactual" value="Post Data" id="cmdgenerate" onclick="funcmdgenerate_actual();"   <%=disable %>><input type="hidden" name="Generate" value="Upload Data    [ Lakhs ]   " id="cmdgenerate" onclick="funcmdgenerate();"  class="bprint"/> 
                    
                    <input type="button" name="Clear" value="Clear" id="clear" onclick="refresh();" class="blprint"/>    
                    <input type="button"  value="Delete"  onclick="del()"> 
                    <input type="button" name="Exit" value="Exit" id="exit" onclick="exitwindow();"  class="bexit"/> 
                    <img src="../../../../../images/help_images.jpg" height="18px" width="45px" style="vertical-align: bottom;" onClick="window.open('help1.jsp?fn=38','mywindow','width=600,height=400,scrollbars=yes')">                            
                 </div>
         </td>   
       </tr>
<%
	   
		
		int ben_count=0,ben_count2=0;
		if (!bmonth.equalsIgnoreCase("0"))
		{    
		  ben_count=Integer.parseInt(obj.getValue("PMS_DCB_MST_BENEFICIARY" ,"count(distinct BENEFICIARY_SNO) ","c", "where status='L' and  OFFICE_ID="+Office_id ));
		  ben_count2=Integer.parseInt(obj.getValue("PMS_DCB_MST_BENEFICIARY" ,"count(distinct BENEFICIARY_SNO) ","c", "where BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_TRN_MONTHLY_PR where office_id="+Office_id+"  and month="+bmonth+" and year="+byear+") and status='L' and  OFFICE_ID="+Office_id+"   "));
		  String c_count=obj.getValue("PMS_DCB_LEDGER_ACTUAL","count(*)","c"," where OFFICE_ID="+Office_id+" and YEAR="+byear+" and MONTH="+bmonth);
		  String c_count1=obj.getValue("PMS_DCB_LEDGER_ACTUAL","count(distinct BENEFICIARY_SNO)","c"," where OFFICE_ID="+Office_id+" and YEAR="+byear+" and MONTH="+bmonth);
		  String c_count2=obj.getValue("PMS_DCB_LEDGER_ACTUAL","count(distinct SCH_NAME)","c"," where OFFICE_ID="+Office_id+" and YEAR="+byear+" and MONTH="+bmonth);
		%>		
		<tr>  
					<td colspan="3" > Total Beneficiaries &nbsp;:&nbsp;<%=ben_count2%> </td><td colspan="3"  align="right"> Total Beneficiaries Posted&nbsp;:&nbsp; <%=c_count1%>    </td>   
		</tr>		
		<tr>  
 					<td colspan=6  valign="top"> 
 					<table border=0 cellpadding="5" width="100%">
 					<tr>  
 						<td valign="top"><img src="../../../../../images/arrow.jpg" height="50" width="150"></img></td>
 						<td valign="top" colspan="3"  class='bld1'><div style="vertical-align: top"><font size=2>Check Verification List.  If all status <img src="../../../../../images/tick_3.jpg" height="20" width="25"> then only proceed with uploading data for correct DCB ledger statements</font> </div> </td>
 					</tr></table></td>
 		</tr>
 		<tr>  
		<td colspan="6">
				<table align="center" width="100%" cellpadding="5" cellspacing="0"   style="border-collapse:collapse; " bordercolor="skyblue">
				 <TR>
					<td colspan="2"><font size='2'><b>Check List</b></font> </td> 
					<td colspan="4" align="right"><label class="lbl">Month And Year </label>&nbsp;&nbsp;:&nbsp;&nbsp;<label class="lbl"><font> <%=obj.month_val(bmonth)%>-<%=byear%></font></label></td>
				</TR>
				 	<tr align="left" class="tdH">
				 	<th align="center" width="5%"><label class="lbl">Sl. No</label> </th>  
				 	<th align="center" width="15%"  ><label class="lbl">Process</label> </th>
				 	<th align="center"><label class="lbl">Verify</label> </th>
				 	<th align="center"><label class="lbl">Action</label> </th>
				 	<th align="center" width="5%" ><label class="lbl">Status</label> </th>
				 	</tr>  
				<TR>    
				 	<td align="center"><label class="lbl">1</label> </td>
					<td><label class="lbl">Opening Balance</label> </td>
					<td><label class="lbl"><jsp:getProperty name="bean" property="ob"/></label></td>
					<td><label class="lbl" id="mst"><a href="../reports/jsps/<%=obfile%>" target="_blank"><%=bean.getMsg_late2()%> <%=report_head%></a></label></td>
					<td align="center"><img src="<%=path3%>"   width=20 height=20/> </td>
				</TR>
				 <TR>    
				 	<td align="center"><label class="lbl">2</label></td>
					<td><label class="lbl">Pumping Return</label></td>
					<td><label class="lbl"><jsp:getProperty name="bean" property="pump_rtrn"/></label></td>
					<td><label class="lbl"> <%=bean.getMsg_late3()%>  </label></td>
					<td align="center"><img src="<%=path2%>"   width='20' height='20'/> </td>
				</TR>	    
				 <TR>
				 	<td align="center"><label class="lbl">3</label></td> 
					<td><label class="lbl">Water Charges</label></td>
					<td><label class="lbl"><jsp:getProperty name="bean" property="wc"/></label></td>
					<td><label class="lbl"><a href="pr_amount.jsp?arg_year=<%=byear%>&arg_month=<%=bmonth%>" target="_blank"><%=bean.getMsg_late4()%> Water Charges Calculation Report</a></label></td>
					<td align="center"><img src="<%=path4%>"  width=20 height=20/> </td>
				</TR>
				 <TR>
				 	<td align="center"><label class="lbl">4</label></td> 
					<td><label class="lbl">Demand Notice Generation</label></td>
					<td><label class="lbl"><jsp:getProperty name="bean" property="demand_generation"/></label></td>
					<td><label class="lbl"><a href="Demand_Report_List.jsp" target="_blank"><%=bean.getMsg_late5()%> Demand List</a></label></td>
					<td align="center"><img src="<%=path5%>"   width=20 height=20/> </td>
					
				</TR>	  	  
			</table> 
		</td>
	</tr>
	 
 	<%} %> 
 	<tr><th colspan="6" id='after_posted'>&nbsp;</th></tr>	 
</table>
	<input type="hidden" id="freeze_count" name="freeze_count" value="<%=freeze_count%>">
	<script type="text/javascript">
    	var OID = <%=Office_id%>    	
    </script>
</form>
</body>
</html>