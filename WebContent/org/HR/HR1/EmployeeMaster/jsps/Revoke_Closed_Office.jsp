<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252" import="java.util.*" %>

<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile,Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Revoke Closed Office</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>  
      <script type="text/javascript" src="../scripts/Revoke_Closed_Office.js">  </script>
       <script type="text/javascript"  src="../../../../Library/scripts/checkDate.js"></script>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <style type="text/css">
.cal {font-family:verdana; font-size:12px;}
</style>
  </head>
  
<%
/*
ArrayList<String> offices=new ArrayList<String>();
offices=s.loadOffice();
ArrayList<String> users=new ArrayList<String>();
users=s.loadUsers();*/
Connection con=null;
ResultSet rs=null;
PreparedStatement ps=null;


 Connection connection=null;

ResultSet results=null;
ResultSet results1=null;
ResultSet results2=null;

try
{

	LoadDriver driver=new LoadDriver();
	con=driver.getConnection();
}
catch(Exception e)
{
System.out.println("Exception in connection...."+e);
}
try
{
       
        ps=con.prepareStatement("select * from COM_MST_OFFICES where office_id in(select office_id from com_mst_closed_offices_view)" );
       
        results=ps.executeQuery();
        
        ps=con.prepareStatement("select * from sec_MST_users" );
        
        results1=ps.executeQuery();
            
    }
    catch(SQLException e)
{
    System.out.println(e);
}




%>
  <body onload="call('get')">
    <div align="center" id="xxx">
    <form name="Hrm_TransJoinForm" id="Hrm_TransJoinForm">
    
        <table cellspacing="3" cellpadding="2" border="1" width="100%" id="ggg">
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <strong>Revoke Authorization for Closed Office</strong>
              </div>
            </td>
          </tr>
         
         
          <tr class="table">
            <td>
              <div align="left">
              Defunct Office
            </div>
            </td>
            <td>
              <div align="left">
                <select id="defanct_office" name="defanct_office" onchange="checkName()" class="disab" disabled="disabled">
                  <option>--Select office Name--</option>
                  
                  <%
          while(results.next()){
          %>
          <option value="<%=results.getInt("office_id")%>"><%=results.getString("office_name")%></option>
          <%
          }
          %>
         </select>
         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                <input type="text" name=""unitid"" id="unitid" maxlength="5" size="6" class="disab" readonly="readonly"/>
         </div>
         </td>
         </tr>
          <tr class="table">
              
              <td><div align="left">Office Level</div></td>
              
             <td>
              <div align="left">
                <input type="text" name="officelevel" id="officelevel" maxlength="6" value="" size="6" class="disab" readonly="readonly" disabled="disabled"/>
                
              </div>
            </td>
          </tr>
          <tr class="table">
            <td><div align="left">Office Address</div></td>
            <td>
            <div align="left">
            <textarea rows="4" cols="40" name="txtOffAddr" id="txtOffAddr"
                          readonly="readonly" class="disab">
            </textarea>
            </div>
            </td>
          </tr>
          <tr class="table">
          
          <td>
            <div align="left">
              User Login Id
            </div>
            </td>
            <td>
              <div align="left">
            <!--    <select name="userid" id="userid" class="disab" disabled="disabled">
                  <option>--Select User Login Id --</option>
                  <%
                //  while(results1.next()){
                %>
        
          <%
         // }
          %>
                </select> -->
               
             <input type="text" name=""userid" id="userid"  readonly="readonly" disabled="disabled"/>   
              </div>
            </td>
           
          </tr>
          <tr class="table">
           <td>
              <div align="left">
              Date Allowed Upto
              </div>
            </td>
            <td class="table">
              <div align="left">
            
                <input type=text name="date" id="date" class="disab"  onfocus="javascript:vDateType='3';" maxlength="10" size="11" onkeypress="return calins(event,this);" onblur="return checkdt(this);">
                
              </div>
            </td>
            
        
          </tr>
           <tr class="table">
           <td>
              <div align="left">
              Access Status
              </div>
            </td>
            <td>
              <div align="left">
         <!--     <input type="text" name="access_status" id="access_status" maxlength="8" value="Disabled" size="8" class="disab" readonly="readonly"/> -->
           <input type="radio" name="access_status" id="access_status" value="Y" />Enable  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
           
             <input type="radio" name="access_status" id="access_status" value="N" />Disable
             </div>
            </td>
          </tr>
          
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
              <!--  <input type="button" name="add" id="add" value="ADD"
                       onclick="call('Add')"/>-->
                 
                <input type="button" name="update" id="update"
                       onclick="call('Update');" value="Revoke"/>
                                              
                <!--<input type="button" name="clear" id="clear" value="CLEAR ALL"
                       onclick="clear1();"/>-->
              </div>
            </td>
          </tr>
        </table>
        
        
         <table cellspacing="3" cellpadding="2" border="1" width="100%"
               align="center">
          <tr>
            <td class="table">Existing Details</td>
            
          </tr>
        </table>
        
         
        <table id="Existing" cellspacing="2" cellpadding="3" border="1"
               width="100%" align="center">
          <tr class="tdH">
            <th>Select</th>
           
            <th> Defanct Office Id</th>
             <th>Accounting Unit Id</th>
           
            <th>User Id</th>
            <th>Date Allowed Up To</th>
           <th>Access Status</th>
          </tr>
             <tbody id="tlist" class="table">
          
          </tbody>
         
        </table>
        
        
        
        </form>
        </div>
  </body>
</html>