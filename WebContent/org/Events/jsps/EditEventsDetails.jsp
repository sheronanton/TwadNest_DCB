

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>



<html>
  <head>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Edit Events Details</title>    
   
    <script type="text/javascript" src="../scripts/EditEventsDetailsValidations.js"></script>
     <script type="text/javascript" src="../scripts/EditEventsDetailsAjax.js"></script>
    <script type="text/javascript" src="../../Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../Library/scripts/CalendarControl.js"></script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    document.frmEditEvent.txtEventId.focus();
                    window.open('','_parent',''); 
                    window.close(); 
                    
                    var w=window.open(window.location.href,"_self");
                    w.close();
                    window.opener.focus();
                }
                function toExit()
                {
                  //window.close();
                var w=window.open(window.location.href,"_self");
                w.close();
                }
    </script>
    <link href='../../../css/Sample3.css' rel='stylesheet' media='screen'/>
    <link href="../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
  </head>
  <body class="table">
  <%
   Connection connection=null;
   Statement statement=null;
   ResultSet results=null;   

  try
  {
          ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
    String ConnectionString="";
   
    String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
    String strdsn=rs.getString("Config.DSN");
    String strhostname=rs.getString("Config.HOST_NAME");
    String strportno=rs.getString("Config.PORT_NUMBER");
    String strsid=rs.getString("Config.SID");
    String strdbusername=rs.getString("Config.USER_NAME");
    String strdbpassword=rs.getString("Config.PASSWORD");
      
  //  ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
   ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 

     Class.forName(strDriver.trim());
     connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());

       
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
  <form name="frmEditEvent" method="POST" action="../../../ServletEditEventsDetails.con" onsubmit="return nullCheck()">
      <table width="100%" align="center">
        <tr>
            <td class="tdH">
                <center><b>Edit New Event Details</b></center>
            </td>
        </tr>
        <tr>
            <td>
                <table cellspacing="3" cellpadding="1" width="100%" >
                <tr colspan=3>
                  <td width="36%">
                  Event ID<label style="color:rgb(255,0,0);">&nbsp;*</label>
                 
                  </td>
                  <td width="64%">
                  <input type="text" id="txtEventId" onchange="editEventDet(this.value)" name="txtEventId" size="10" /> 
                  ( Enter the Event Id to be Edited )
                  </td>
                 
                </tr>
                <tr>
                  <td  width="36%">Event Description <label style="color:rgb(255,0,0);">&nbsp;*</label></td>
                  <td width="64%">
                    <input type="text" id="txtEventDesc" name="txtEventDesc"  size="40"/>                    
                  </td>
                </tr>
                <tr>
                    <td  width="36%">Event Venue <label style="color:rgb(255,0,0);">&nbsp;*</label></td>
                    <td width="64%">
                        <textarea id="txtEventVenue" name="txtEventVenue" cols="25" rows="4"></textarea>
                    </td>
                </tr>
                <tr>
                  <td  width="36%">
                    Date of Event  
                  <label style="color:rgb(255,0,0);">
                    &nbsp;*
                  </label>
                </td>
                  <td width="64%">
                   <%
               // java.util.Date eve_date=new java.util.Date();
                  String DateToBeDisplayed="";
                    try
                           {
                                 results=statement.executeQuery("select sysdate from dual"); 
                                 while(results.next())
                                 {
                                   java.sql.Date DateOfFormation = results.getDate(1);  
                                    
                                     if(DateOfFormation==null)
                                     {
                                         DateToBeDisplayed="Not Specified";
                                     }
                                     else
                                     {
                                         try
                                         {
                                             java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
                                             DateToBeDisplayed=sdf.format(DateOfFormation);
                                         }
                                         catch(Exception e)
                                         {
                                             System.out.println("error while formatting date : " + e);
                                             DateToBeDisplayed="Not Specified";
                                         }
                                     }     
                                 //out.print("<a href=" + results.getInt("District_Code") + ">" + results.getString("District_Name") + "<BR>");                      
                                  //eve_date=results.getDate(1);
                                  System.out.println("the value is "+DateToBeDisplayed); 
                                  
                                 }
                                results.close();
                           }
                    catch(Exception e)
                           {
                                System.out.println("exception occured : " + e);
                           }    
                    
                    %>
                    
                    
                    
                    
                    <input type="hidden" id="txtEventDate_h" name="txtEventDate_h" value=<%=DateToBeDisplayed%>></input>
                   
                           <input type="text" id="txtEventDate" name="txtEventDate" size="10" maxlength="10"
                           onFocus="javascript:vDateType='3'" onBlur="DateFormat(this,this.value,event,true,'3');return chk_for_expired_date(this);" onkeypress="return  calins(event,this)"/>                   
                           <img src="../../../images/calendr3.gif"  onclick="showCalendarControl(document.frmEditEvent.txtEventDate);" alt="Show Calendar"></img>
                    
                  </td>
                </tr>
                <tr>
                    <td  width="36%">Target URL <label style="color:rgb(255,0,0);">
                    &nbsp;*
                  </label></td>
                    <td width="64%">
                  <input type="text" id="txtTargetURL" name="txtTargetURL" size="40"/>
                </td>
                </tr>
              </table>
          </td>
        </tr>        
        <tr>
        <td align="center" class="tdH">
            <input type="submit" value="Submit" name=submit>
            <input type="button" value="Cancel" onclick="closeWindow()">
        </td>
        </tr>
      </table>
      <p>
        &nbsp;
      </p>
    </form>
  </body>
</html>