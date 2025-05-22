<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.sql.Connection,java.sql.PreparedStatement,java.util.*"%>
<%@ page import="java.sql.DriverManager,java.sql.ResultSet,java.util.ResourceBundle,java.util.Date"%>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller;"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
<meta http-equiv="cache-control" content="no-cache">
<title>Expenditure</title>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
</head>
<body class="table">
<form name="HR_Note" id="HR_Note" method="POST" action="../../../../../ame_report">
 <% Connection con=null;
             ResultSet rs=null;
             PreparedStatement ps=null,ps2=null;
             Connection connection=null;
             ResultSet results=null,rs2=null;
             ResultSet results1=null;
             ResultSet results2=null;
             try
             {
                    String ConnectionString="";
                    Controller obj=new Controller();
                    con=obj.con();
                    obj.createStatement(con);
                    HttpSession session1 = request.getSession(false);
	           		String	userid = (String) session1.getAttribute("UserId");
	           		Calendar cal = Calendar.getInstance();
	           		int day = cal.get(Calendar.DATE);
	           		int month = cal.get(Calendar.MONTH) + 1;
	           		int year = cal.get(Calendar.YEAR);
	           		if (userid == null) { 
	
	           			response.sendRedirect(request.getContextPath() + "/index.jsp");
	           		}
	           		String sch_sno=obj.setValue("sch_sno",request);
	           		String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')");	           		
	           		if (Office_id.equals("")) Office_id="0";            
%>

      <table cellspacing="2" cellpadding="3" border="1" width="50%" align="center">    
          <tr class="table">
          <th>
             Item Wise Expenditure Details          
          </th>
        </tr> 
        </table> 
      <table cellspacing="2" cellpadding="3" border="1" width="50%" align="center">
	  <tr><td>Scheme Name</td><td><select name="sch_sno" id="sch_sno"  style="max-width:250px;">
			<%   try{ 
				 			 out.println("<option value=0>Select</option>");            
                             String getWing="SELECT SCH_SNO, SCH_NAME FROM PMS_SCH_MASTER where  sch_sno in (select distinct SCHEME_SNO from  PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+" ) and SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+") order by SCH_SNO"; 
                             ps=con.prepareStatement(getWing);
                             rs=ps.executeQuery();
                             while(rs.next())
                             {
    	                       	 int a= rs.getInt("SCH_SNO");
	                          	  String name=rs.getString("SCH_NAME");
                                   out.println("<option value="+rs.getInt("SCH_SNO")+">"+rs.getString("SCH_NAME")+" -"+rs.getInt("SCH_SNO")+"</option>");            
                               }
                               results.close();
                               ps.close();
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                    }             
			                %>   				  
							 
							  </select></td></tr>
  
					   <tr>	<td>Financial Year:</td>
					   <td>
					     <select class="select"  id="fin_year" name="fin_year">
			  	  		 <option value="0">Select</option>
			  	  		 <%
			  	   			for (int i=2013;i<=year;i++) 
			  	   			{
			  	   		 %>
			  	  		<option value="<%=i%>"><%=i%>-<%=i+1%></option>
			  	  		<%
			  	    		}
			  	  		%>
			      </select> 
			            </td>
				</tr>                          
      			<tr class="tdH">
      			<th align="center" colspan="2">
      				<input type=hidden id="process_code" name="process_code" value="123">
      				<input id="butSub" type="submit" value="SUBMIT" name="butSub"/>
        			<input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="javascript:window.close()">
        		</th>
      </tr>
      	
      </table>
      <%
             }
	     	 catch(Exception e)
             {
	     		response.sendRedirect(request.getContextPath() + "/index.jsp");
             }  
      %>
    </form>
    </body>
</html>

 
 
 
 
 
 
 