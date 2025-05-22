<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page import="Servlets.Security.classes.UserProfile,Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Change Major /Minor Category for the Issue</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../css/Sample3.css" rel='stylesheet' media='screen'/>
      <script type="text/javascript" src="../scripts/Transform_Issue.js"></script>
    <script type="text/javascript" src="../scripts/ViewIssue.js"></script>
    <script type="text/javascript" src="../scripts/ViewResponse.js"></script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
                
                isIE=document.all? 1:0
       
                function noEnter(e)
                 {
       
                   keyEntry = !isIE? e.which:event.keyCode;
                   if(keyEntry=='38')
                   {
                    return false;
                   }
                  }
          </script> 
  </head>
  <body class="table">
  
  <%
  Connection connection=null;
   Statement statement=null;
   PreparedStatement ps=null;
   ResultSet results=null;   

  try
  {
	  LoadDriver driver=new LoadDriver();
  	connection=driver.getConnection();

       
       try
       {
            statement=connection.createStatement();
            connection.clearWarnings();
       }
       catch(SQLException e)
       {
              //System.out.println("Exception in creating statement:"+e);
       }          
  }
  catch(Exception e)
  {
         //System.out.println("Exception in openeing connection:"+e);
  }
  
  %>

  
  <form action="" name="frmHelp" onsubmit="" method="post">
    <table width="100%" >
        <tr>
            <td class="tdH"><center><b>Change Major /Minor Category for the Issue</b></center></td>
        </tr>
          <tr>
            <td>
                <table  cellspacing="3" cellpadding="1" width="100%">
                    <tr>
                        <td colspan="1">
                            Issue_Id
                        </td>
                        <td>
                            <select name="cmbissue_id" id="cmbissue_id" onchange="displaydetails()">
                            <option value=0>--Select Issue Id--</option>
                            <%
                                //session=request.getSession(false);
                               // String Userid=(String)session.getAttribute("UserId");
                                 
                                
                                
                                int issueid=0;
                                //ps=connection.prepareStatement("select ISSUE_ID from HLP_ISSUE_REQUESTS  where REPORTED_BY_USER_ID =?  order by ISSUE_ID");
                                ps=connection.prepareStatement("select ISSUE_ID from HLP_ISSUE_REQUESTS  order by ISSUE_ID");
                                //ps.setString(1,Userid);
                                results=ps.executeQuery();
                                while(results.next())
                                {
                            %>
                                <option value="<%=results.getInt("issue_id")%>"><%=results.getInt("issue_id")%></option>
                            <%
                                    
                                }
                            %>
                            </select>
                        </td>
                         <td>Office Name</td>
                        <td><input type="text" name="txtofficename" size="40" disabled readonly >
                        </td>
                        
                    </tr>
                    <tr>
                        <td>
                            UserName:
                        </td>
                        
                        <td>
                            <input type=text name=txtUserName size=30  disabled >
                            <input type=hidden name=txtHUserName  >
                        </td>
                    <%
                       /* }catch(Exception e)
                        {
                            System.out.println("Exception in Catch:"+e);
                        }*/
                    %>
                        <td>
                        Major System:
                        </td>
                        <td>
                        <select id="major" name="major" onchange="load_Minor();">
                	<option value="0">Select</option> 
                	 <%
                                        try{
                                        
                                        PreparedStatement ps1=connection.prepareStatement("select major_system_id,major_system_desc from sec_mst_major_systems");
                                        ResultSet res1=ps1.executeQuery();
                                        while(res1.next())
                                        {
                                            out.println("<option value="+res1.getString("major_system_id")+">"+res1.getString("major_system_desc")+"</option>");
                                            
                                        }
                                    }catch(Exception e)
                                    {
                                        System.out.println("Exception in major:"+e);
                                    }
                                    %>
                	        	
                </select>
                        <!--<select name="cmbMajor" id="cmbMajor" onchange="minor()" disabled>
                                    <option value=0>--Select Major System--</option>
                                   
                                    </select>-->
                        </td>
                        
                    </tr>
                    <tr>
                        <td>
                            Status:
                        </td>
                        <td>
                            <input type=text name=status size=30 disabled>
                        </td>
                        <td>
                            Minor System:
                        </td>
                        <td>
                        <select id="minor" name="minor" >
                	<option value="0">Select</option>         	
                </select>
                            <!--<select name="cmbMinor" id="cmbMinor" disabled>
                                         <option value=0>--Select Minor System--</option>
                                         </select>-->
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Priority:
                        </td>
                        <td >
                        <input type=text name=txtpriority size="30" disabled>
                            <!--<select name="cmbPriority" id="cmbPriority" disabled>
                            <option value="High">High</option>
                            <option value="Medium">Medium</option>
                            <option value="Low">Low</option>
                            </select>-->
                        </td>
                        <td >
                            Requested Date:
                        </td>
                        <td >
                            <input type=text name=txtdate size=30 disabled>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdH" colspan="4"><b>Problem Description:</b>
                        </td>
                    </tr>
                    <tr>
                        <td >Subject:
                        </td>
                        <td colspan="3">
                            <input type=text name=txtSubject size=60 disabled
                                            onkeypress="return noEnter(event)">
                        </td>
                    </tr>
                    <tr>
                        <td >
                            Description:
                        </td>
                        <td colspan="3">
                            <textarea cols="80" rows="10" name=txtdesc id=txtdesc
                                         onkeypress="return noEnter(event)"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdH" colspan=4><center>
                            <!--<input type=button value=Ok>-->
                            <input type=button value=SUBMIT onclick="updating_function()">
                            <input type=reset value="Clear All" >
                            <input type=button value=Exit onclick="closeWindow()">
                            </center>
                        </td>
                    </tr>
                </table>
            </td>
         </tr>   
    </table>
  </form>
  
  </body>
</html>