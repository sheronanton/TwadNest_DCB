 <%@ page import="java.sql.*,java.util.ResourceBundle"%>
 <%@ page session="false" contentType="text/html;charset=windows-1252"%>
  <%@page import="java.util.Calendar" %>
 <%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
 <%@ page import="Servlets.Security.classes.UserProfile"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <%
    String Year_text="",month_text="";
   Calendar cal = Calendar.getInstance();
   int day = cal.get(Calendar.DATE);
   int month = 0;//cal.get(Calendar.MONTH) + 1;
   int year = 0;//cal.get(Calendar.YEAR);

   
   String userid="0",Office_id="",Office_Name="";
   Controller obj=new Controller();
   Connection con;
   try
   {
   	con=obj.con();
   	obj.createStatement(con);
   	
   	try
   	{
   		HttpSession session=request.getSession(true);
   	   userid=(String)session.getAttribute("UserId");
   	}catch(Exception e) {userid="0";}
   	if(userid==null)
   	{
   		//response.sendRedirect(request.getContextPath()+"/index.jsp");
   	}
   	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
   	if (Office_id.equals("")) Office_id="0";
   	Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
   	
   	
   	 String smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
	 String syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
	
	if (smonth.equalsIgnoreCase("0"))
	 month=month;
	else
	 month=Integer.parseInt(smonth);
	 
	 if (syear.equalsIgnoreCase("0"))
	  year= year;
	else
	 year=Integer.parseInt(syear);
	 
	  String []cmonth ={"-select month-","Jan","Feb","Mar","Apr","May","Jun","July","Aug","Sep","Oct","Nov","Dec"};
   	  month_text=cmonth[month];
      Year_text=Integer.toString(year);
   	
   	obj.conClose(con);
   }catch(Exception e) {
   	
    
   }	
   
   int col=0;
   %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UnFreeze Form | TWAD Nest - Phase II </title>
</head>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<script type="text/javascript" src="../scripts/Bill_Demand.js"></script>
<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
 <body  onload="select_ben('0','bentype','3'),select_ben('0','subdiv','5'),flash()" >
     <form name="billdemand" >
      <table   class="fb2" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" id="data_table" width=100% align=center border=0  cellspacing="0" cellpadding="5">
        <tr  bgcolor="#C6DEFF">
          <td align="center"   colspan="3">
             
              <b>UnFreeze Form - <%=Office_Name%> <label  id="msg"></label> </b>   
             
          </td>
        </tr>
         <tr><td align=left class="tdText" valign="top" width=10% style="width: 382px"> &nbsp; Sub Division  </td>
			 <td width=5% align=left>:
			 <select class='select'  id="subdiv" name="subdiv" ></select></td>
		</tr>
       <tr class="">
			<td width=25% class="tdText" > Beneficiary Type </td>
			<td   width=75% align=left>: <select  id="bentype" name="bentype" onchange="select_ben(this.value,'ben_sel',4)" class="select">  </select></td>
			
       </tr>
       
       <Tr><td width=10% class="tdText" >  Select Beneficiary Name </td><td colspan=3>: <select id="ben_sel"  name="ben_sel" onchange="data_show('show',15,this.value)"  class="select"  ><option value="0">--Select Beneficiary--</option> </select></td></Tr>
       
      
       <input type=hidden id="subdiv"> 
      
          <input type="hidden" id="fyear" name="fyear" value="<%=year%>" ></input> 
       	  <input type="hidden" id="fmonth" name="fmonth" value='<%=month%>' ></input>  
        
     
      </table>

   <input type=hidden id="pr_status" name="pr_status" value="0"> 

</form>



</body>
</html>