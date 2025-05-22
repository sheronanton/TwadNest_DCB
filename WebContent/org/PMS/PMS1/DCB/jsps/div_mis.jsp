<%@ page import="java.sql.*,java.util.ResourceBundle"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@page import="java.util.Calendar" %>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
	<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>   </title>
    <script type="text/javascript" src="../scripts/cellcreate.js"></script>
    <script type="text/javascript" src="../scripts/Bill_Demand.js"></script>
    <script type="text/javascript" src="../scripts/dcbvalidation.js"></script>    
   	<script type="text/javascript">
   	function rld1(process,ben_value,ben_type)
   	{
			
	   } 
   	</script>
    </head>
    <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
   <%
    String Year_text="",month_text="";
    Calendar cal = Calendar.getInstance();
    int day = cal.get(Calendar.DATE);
    int month = 0;//cal.get(Calendar.MONTH) + 1;
    int year = 0;//cal.get(Calendar.YEAR);
    String userid="0",Office_id="",Office_Name="",all_div="";
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
   	Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " ");
   	
   	
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
      all_div=obj.combo_str("com_mst_all_offices_view","OFFICE_NAME", "OFFICE_ID"," where OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP)","div","");
   	obj.conClose(con);
   }catch(Exception e) {
   	
    
   }	 
   
   int col=0;
   %>
   <script type="text/javascript">
   function ps()
   {
   alert("TRP");
   }
   
   </script>
     <script type="text/javascript">
    
  function callReport()
{
	 
	 var div=document.getElementById('div').value;
	 var fyear=document.getElementById('fyear').value;
	 var fmonth=document.getElementById('fmonth').value;
	 
     window.open("../../../../../Bill_Demand?command=mispdf&div="+div+"&fmonth="+fmonth+"&fyear="+fyear);
}
  
  </script> 
  <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
    <body  >
     <form name="billdemand"  method="get" action="">
      <table  style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" id="data_table" width=100% align=center border=0  cellspacing="0" cellpadding="5">
          
         <tr><td align=left><font class="tdText">Division</font> </td>
			 <td  align=left">:
			<%=all_div %> &nbsp;&nbsp;<input type="button" value="All Division" onclick="callReport()" class="fb2">&nbsp;&nbsp;&nbsp; <input type="button" value="Print" onclick="callReport()" class="fb2"></td>
			
		</tr>  
          
       <tr>
       		<td colspan="2" align="center">
       			
       			
       		</td>
       </tr>
       
         
       <input type=hidden id="subdiv"> 
      
       
        
      
     
      </table>
      
        
          
          <input type="hidden" id="useform" name="useform" value=4 ></input>
       	  <input type="hidden" id="fyear" name="fyear" value="<%=year%>" ></input> 
       	  <input type="hidden" id="fmonth" name="fmonth" value='<%=month%>' ></input> 
      
    <input type=hidden id="pr_status" name="pr_status" value="0"> 
     <input type="hidden" id="formflag" value="2"/>      
 	  <input type="hidden" id="t1" name="t1" ></input>  
 	  </form>
 	  		  </body>
 	  		 
 	  		  </html>
      