<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*, Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver"%>
<html>
  <head>
  <link href="../../../css/Sample3.css" rel='stylesheet' media='screen'/>
  <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>View All Response</title>
  <script type="text/javascript" src="../scripts/ViewAllResponse.js"></script>
    
    <script type="text/javascript"       src="../../../org/Library/scripts/checkDate.js"></script>
  
     <link  href="../../../css/CalendarControl.css" rel="stylesheet"         media="screen"/>
    
      <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
     
     <style type="text/css">
      .tl{
      width: 100em;
      }
    </style>
    
    <script language="javascript" type="text/javascript">
    
    function closeWindow()
    {                
      window.open('','_parent','');                
      window.close(); 
      window.opener.focus();
    }
    </script>
      
  </head>
  <!--onload="callServer()"-->
  <body class="table" >
  <%
  Connection connection=null;
   Statement statement=null;
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
       catch(SQLException e1)
       {
          System.out.println("Exception in creating statement:"+e1.getMessage());
       }          
  }
  catch(Exception e)
  {
        System.out.println("Exception in openeing connection:"+e.getMessage());
  }
  
  %>

  
  <form name="frmIssue"  action="">
  <table  border="1" cellspacing="2"   cellpadding="1"  class="tl"  >
        <tr>
            <td class="tdH"><center><b>View All Response</b></center></td>
        </tr>
        <tr>
        <td>
         <table border="1" cellspacing="2"   cellpadding="1" style="width: 1615px">
              <tr>
                <td style="width: 156px">From&nbsp;Date:</td>
                <td style="width: 200px">
                  <input type="text" name="txtfromdate" id="txtfromdate"
                         onkeypress="return  calins(event,this)"
                         onblur="return checkdt(this);"
                         onfocus="javascript:vDateType='3'" maxlength="10"/><img src="../../../images/calendr3.gif"
                       onclick="showCalendarControl(document.frmIssue.txtfromdate);"
                       alt="Show Calendar" height="24" width="19"/>
                </td>
                <td  style="width: 197px">To&nbsp;Date:</td>
                <td  style="width: 254px">
                  <input type="text" name="txttodate" id="txttodate"
                         onkeypress="return  calins(event,this)"
                         onblur="return checkdt(this);"
                         onfocus="javascript:vDateType='3'" maxlength="10"/><img src="../../../images/calendr3.gif"
                       onclick="showCalendarControl(document.frmIssue.txttodate);"
                       alt="Show Calendar" height="24" width="19"/>
                </td>
                <td style="width: 107px">Status</td>
                <td ><select name="cmbstatus" >
                <option value="O">Open</option>
                <option value="C">Closed</option>
                <option value="A">All</option>
                </select>
                </td></tr><tr>
                <td>Major System</td>
                <td>
                <select name="cmbMajor" id="cmbMajor" onchange="loadMinor('major');">
                  <option value=0>--Select Major System--</option>
                  <option value="All">ALL CATEGORY</option>
                  <%
                     try
                     {
                                        
                       PreparedStatement ps1=connection.prepareStatement("select major_system_id,major_system_desc from sec_mst_major_systems where major_system_id not in ('ASA','HLD')");
                       ResultSet res1=ps1.executeQuery();
                       while(res1.next())
                        {
                           out.println("<option value="+res1.getString("major_system_id")+">"+res1.getString("major_system_desc")+"</option>");
                                           
                        }
                        res1.close();
                        ps1.close();
                      }
                      catch(Exception e)
                      {
                        System.out.println("Exception in major:"+e);
                      }
                   %>
                  </select>
                  </td>
                  <td>Minor System</td>
                        <td>
                        
                        <select name="cmbMinor" id="cmbMinor">
                  <option value=0>--Select Minor System--</option></select>
                        
                        </td>
                        
                        <td>Office</td>
                        <td>
                        <select name="offices" id="offices">
                        <option value="0">Select Office</option>
                        <option value="ALL">ALL OFFICES</option>
                        <%
                        int count=0;
                        String sql="select OFFICE_ID,OFFICE_NAME from com_mst_offices where office_status_id ='CR' order by office_name";
                        PreparedStatement pss=connection.prepareStatement(sql);
                        ResultSet rss=pss.executeQuery();
                        
                        while(rss.next())
                        {
                      
                   //   count++; // out.println("<option value="+rss.getInt("OFFICE_ID")+">"+rss.getString("OFFICE_NAME")+"</option>");
                        %>
                        
                         <option value="<%=rss.getInt("OFFICE_ID")%>"><%=rss.getString("OFFICE_NAME") %></option>
                        
                   <%    
                   //System.out.println("count == "+count);
                    }
                                              
                         %>
                         
                         
                        </select>
                        
                        </td>
                        
                        </tr>
                
                
                <!--td width="100%">
                &nbsp;
                </td>
              </tr-->
              <tr>
                <td colspan="12" class="tdH" align="left"  width="auto">
                  <input type="button" onclick="buttonsubmit()" value="Submit" />
                <!--  <input type="reset" value="Clear"/>-->
                  <input type="button" value="Exit" onclick="closeWindow()"/>
                </td>
              </tr>
            </table>
        </td>
        </tr>
          <tr>
            <td>
            <div style="overflow:auto">
                <table  border=1 cellspacing="3"  class="tl" cellpadding="1"  id="Existing">
                    <tr>
                  
                      
                         <th>Issue Id</th>
                        <th>Emp. Code</th>
                        <th>Employee Name</th>
                        <th>Office Name</th>
                        
                        <th>Major System Id</th>
                        <th>Minor System Id</th>
                        <th>Subject</th>
                        <th>Reported Date</th>
                        <th>Status</th>
                        <th>Description</th>
                        <th>Solution</th>
                    </tr>
                    <tr>
                        <tbody id="tblList" >
                        </tbody>
                    
                    </tr>
                </table>
                
                </div>
            </td>
         </tr>
    </table>
  <% connection.close(); %>
  </form>
  
  </body>
</html>