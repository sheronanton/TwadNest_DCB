 <%@ page import="java.sql.*,java.util.ResourceBundle"%>
 <%@ page session="false" contentType="text/html;charset=windows-1252"%>
  <%@page import="java.util.Calendar" %>
 <%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
  <%@ page import="Servlets.Security.classes.UserProfile"%>
 <html>
  <head>
     
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Pumping Return Report  | TWAD Nest - Phase II   </title>
     <script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
       <script type="text/javascript" src="../scripts/cellcreate.js"></script>
       <script type="text/javascript" src="../scripts/Bill_Demand.js"></script>
         <script type="text/javascript" src="../scripts/journal_breakup.js"></script>
           <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
   <script type="text/javascript">
   function rld1(process,ben_value,ben_type)
   {

   } 
   </script>
    </head>
     <%
   Calendar cal = Calendar.getInstance();
   int day = cal.get(Calendar.DATE);
   int month = cal.get(Calendar.MONTH) + 1;
   int year = cal.get(Calendar.YEAR);

   String month_text=Integer.toString(month);
   String Year_text=Integer.toString(year);
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
   		response.sendRedirect(request.getContextPath()+"/index.jsp");
   	}
   	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
   	if (Office_id.equals("")) Office_id="0";
   	Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
   	obj.conClose(con);
   }catch(Exception e) {
   	
    
   }	

   int col=0;
   %>
     <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
    <body  onload="select_ben('0','bentype','3'),select_ben('0','subdiv','5'),flash()" >
     <table  style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" id="data_table" width=100% align=center border=0  cellspacing="0" cellpadding="5">
        <tr  bgcolor="#C6DEFF">
          <td align="center"   colspan="3">
             
              <b>Pumping Return Report - Validation - <%=Office_Name%> </b>   
             
          </td>
        </tr>
         <tr><td align=left valign="top" width=10% style="width: 382px"> &nbsp;<font class="tdText">Sub Division</font> </td>
			 <td width=5% align=left>:
			 <select class='select'  id="subdiv" name="subdiv" onchange="slab('show',15,0)" ></select></td>
		</tr>
       <tr class="">
			<td width=25%><font class="tdText"> Beneficiary Type</font> </td>
			<td   width=75% align=left>: <select  id="bentype" name="bentype" onchange="select_ben(this.value,'ben_sel',4),data_show('show',15,0)" class="select">  </select></td>
			
       </tr>
       
       <Tr><td width=10%><font class="tdText">Select Beneficiary Name </td><td colspan=3>: <select id="ben_sel"  name="ben_sel" onchange="data_show('show',15,this.value)"  class="select"  ><option value="0">--Select Beneficiary--</option> </select></td></Tr>
       
       
       <input type=hidden id="subdiv"> 
      
       
          
        <tr>
       
         
       <td><font class="tdText"><b>Pumping Return Month & Year </b></font></td>
       <td>
       <%=month_text%> /<%=Year_text%>   <font size="2" color="red">&nbsp;<label  id="msg"></label></font>
       </td>
       </tr>
       <tr>
     
      </table>
          <input type=hidden id="pr_status" name="pr_status" value="0"> 
      <input type="hidden" id="useform" name="useform" value=4 ></input>
       	  <input type="hidden" id="fyear" name="fyear" value="<%=year%>" ></input> 
       	  <input type="hidden" id="fmonth" name="fmonth" value='<%=month%>' ></input> 
</body>
</html>