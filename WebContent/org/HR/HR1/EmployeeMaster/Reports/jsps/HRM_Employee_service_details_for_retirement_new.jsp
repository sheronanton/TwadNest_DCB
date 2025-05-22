<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%
  
  Connection connection=null;
  PreparedStatement ps=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
  
  
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

         //   ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
          ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 

             Class.forName(strDriver.trim());
             connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  
  %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title> Service particulars - Retiring Employees - Officewise</title>
    
    <script type="text/javascript"
            src="../scripts/HRM_Employee_service_for_retirement_list_new.js"></script>
        <!--below  js for loading region, circle,division--> 
   
    <script type="text/javascript"
            src="../scripts/ListAllScopeofOffices.js"></script>
                    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
          <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
          <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script> 
   <script type="text/javascript" src="../../scripts/CalendarControl_before.js"></script>
            
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <style type="text/css">
      body {
      background-color: #ffffff; 
}
      a:link { color: #002173; }
      
      <!--div.scroll {	
      height: 100px;	
      width: 100%;	
      overflow: auto;	
      border: 1px solid #666;	
      background-color: #fff;	
      padding: 0px;
     visibility: hidden;
     position: relative;
      }-->
      
    </style>
     <style type="text/css">
      .tl{
      width: 100em;
      }
    </style>
  </head>
  <!--- action="../../../../../EmployeeDetailReportServ.rep" -->
  <body><form name="frmValidationSummaryRep" method="POST" 
                       action="../../../../../../HRM_Employee_service_for_retirement_list_new">
  
         
                   
            <input type="hidden" name="offlevel">
            <input type="hidden" name="office">
            <input type="hidden" name="officetype">
            <input type="hidden" name="officeselected">
            <input type="hidden" name="designationlevel">
            <input type="hidden" name="designation">
            <input type="hidden" name="outputtype">
            <input type="hidden" name="ordertype">
             <input type="hidden" name="reprtype" id="reprtype" value="newrpt">
            
  
      <div align="center">
        <table border='2' width="100%">
          <tr>
            <td colspan="2" align="left" valign="top" class="table" width="100%">
              <table border="0" width="100%">
             
                <tr>
                  <td colspan='2' class="tdH" align="center"><strong>Service particulars - Retiring Employees - Officewise</strong></td>
                </tr>
                
                
          <!--tr class="tdH">
          <th colspan="2" align="left">Select Designation / Rank</th>
        </tr-->
        <tr class="table">    
        <!--td >       
              <input type="radio" value="RadAl" name="optselectgrp" id="chkone" checked="checked" onclick="hidedesig()">
               All Designation
         
               <input type="radio" value="radsel" onclick="sellectall()" name="optselectgrp">
               
               Select Specific Designation / Cadre / Rank     -->          
       <!--/td-->   
       <td class="tdH" colspan="2" >
       Selection of Specific Rank
       </td>
       
       </tr>       
       
       <tr class="table" id="divselect" > 
                
                <td> <input type="radio" value="allRank"  onclick="hideSpecRank()"
                             name="optsel" id="optselect1" checked></input>
                      All Rank             
                </td>
                <td> <!--input type="radio" value="Dest"  onclick="selectoption1()"
                             name="optselect" id="optselect1" ></input-->
                      <!-- Designation-->
                      
                      <input type="radio" value="Specific Rank" name="optselect"  onclick="selectoption1()"
                             id="optselect2" ></input>
                       Rank
                       <!--input type="radio" value="Cadr" name="optselect"  onclick="selectoption1()"
                             id="optselect3" ></input-->
                      <!-- Cadre-->
                </td>
               
        </tr>
        
         <tr class="table" id="divdest" style="display:none;">
                  <td>
                    <div align="left">
                    <select name="cmbsgroup" id="cmbsgroup" onchange="getDesignation()">
                <option value="0">Select Service Group</option>
                        <%
           ResultSet rs=null;
           try
           {
           ps=connection.prepareStatement("select SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_MST_SERVICE_GROUP  order by SERVICE_GROUP_NAME");
            rs=ps.executeQuery();
              int strcode=0;
            String strname="";          
            while(rs.next())
            {
              
               
                strcode=rs.getInt("SERVICE_GROUP_ID");
                strname=rs.getString("SERVICE_GROUP_NAME");
                System.out.println("service group code:"+strcode);
                System.out.println("service gorup name:"+strname);
                out.println("<option value='"+strcode+"'>"+strname+"</option>");
                
             }
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+e);
          }
            
                
        %>
               
               </select> 
               </div>
                  </td>
                  
           </tr>                                              
                              
                
        
          <tr class="table" id="divrank" style="display:none">
                  <td colspan='2'>
                    <div align="left"  id='divrankwhole'>
                    <select name="cmbsgroup1" id="cmbsgroup1" onchange="getDesignation1()">
                <option value="0">Select Service Group</option>
                        <%
            rs=null;
           try
           {
           ps=connection.prepareStatement("select SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_MST_SERVICE_GROUP  order by SERVICE_GROUP_NAME");
            rs=ps.executeQuery();
              int strcode=0;
            String strname="";          
            while(rs.next())
            {
              
               
                strcode=rs.getInt("SERVICE_GROUP_ID");
                strname=rs.getString("SERVICE_GROUP_NAME");
                
                out.println("<option value='"+strcode+"'>"+strname+"</option>");
                
             }
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+e);
          }
            
                
        %>
               
               </select> 
                     </div>
                  </td>
                </tr>             
                              
                
                <tr class="table" id="divcadre" style="display:none;">
                  <td colspan='2'>
                    <div align="left">
                    <select name="cmbsgroup2" id="cmbsgroup2" onchange="getDesignation2()">
                <option value="0">Select Service Group</option>
                <%
            rs=null;
           try
           {
           ps=connection.prepareStatement("select SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_MST_SERVICE_GROUP  order by SERVICE_GROUP_NAME");
            rs=ps.executeQuery();
              int strcode=0;
            String strname="";          
            while(rs.next())
            {
              
               
                strcode=rs.getInt("SERVICE_GROUP_ID");
                strname=rs.getString("SERVICE_GROUP_NAME");
                
                out.println("<option value='"+strcode+"'>"+strname+"</option>");
                
             }
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+e);
          }
           
                
        %>
               
               </select> 
                    
                    </div>
                  </td>
                </tr>
              
                
              <tr class="table" id="desigblock" style="display:none">
               <td>
               <div class="navigation" align='left' id="divdesignation">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
               <!--p class="scroll" align='left' id="divdesignation">&nbsp;</p-->
               </td>
               </tr>  
                
                         
                
                
                
                
                
                
              
                    
                             
                    <%--}else{ System.out.println("no");--%>
                              <!--div id="divallofficelevel" >
                               <input type="radio" name="optofficelevel"
                              onclick="document.frmVacantRep.optofficelevel[1].checked=true;"></input>All Levels
                           </div>  
                           </td>
                  <td class="table" align="left" >
                    <div id="divspecificofficelevel" onclick="showofficelevel()"
                         style="cursor:hand">
                    <input checked type="radio" name="optofficelevel" ></input>Specific
                                                                        Levels
                    </div>
                    <script language="javascript">
                           document.frmVacantRep.optofficelevel[1].checked=true;
                           showofficelevel();
                           </script>
                  </td>
                </tr-->
                           
                           
        <%--}--%>
                             
                    
                  
                
                  
              
          <!--tr class=table >
            <td colspan=2 align=left><input type="checkbox" name="includeho" id="includeho" value="checked">Include HO</input></td>
          </tr-->
          <!--tr class='table' id="divincludeho">
            <td colspan=2 align=left><input type="checkbox" name="includeho" id="includeho" value="checked">Include HO</input></td>
          </tr-->
         <tr>
                    <td colspan="2" class="tdH">Retirement Projection (Duration) </td>
                  </tr>
          <tr>
                <td >From Date:
               
                  <input type="text" name="txtfromdate" id="txtfromdate"
                         onkeypress="return  calins(event,this)"
                         onblur="return checkdat(); return checkdt(this);"
                         onfocus="javascript:vDateType='3'" maxlength="10"/>
                  <img src="../../../../../../images/calendr3.gif"
                       onclick="showCalendarControl(document.frmValidationSummaryRep.txtfromdate);"
                       alt="Show Calendar" height="24" width="19"/>
                
                To Date:
              
                  <input type="text" name="txttodate" id="txttodate"
                         onkeypress="return  calins(event,this)"
                         onblur="return checkdat1(); return checkdt(this);"
                         onfocus="javascript:vDateType='3'" maxlength="10"/>
                  <img src="../../../../../../images/calendr3.gif"
                       onclick="showCalendarControl(document.frmValidationSummaryRep.txttodate);"
                       alt="Show Calendar" height="24" width="19"/>
                </td>
              </tr>
          <tr>
                    
             <td colspan="2" align="left" valign="top" class="table">
              <div align="center">
                <table border="0" width="100%">
                  <tr>
                    <td colspan="2" class="tdH">Report Output Format</td>
                  </tr>
                  <tr>
                    <td colspan="2" class="table" align="left">
                      <div onclick="document.frmEmployeeDetailRep.optoutputtype[0].checked=true;"
                           style="cursor:hand">
                        <input type="radio" name="optoutputtype" checked></input>PDF
                                                                         Format
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td colspan="2" class="table" align="left">
                      <div onclick="document.frmEmployeeDetailRep.optoutputtype[1].checked=true;"
                           style="cursor:hand">
                        <input type="radio" name="optoutputtype"></input>EXCEL
                                                                         Format
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td colspan="2" class="table" align="left">
                      <div onclick="document.frmEmployeeDetailRep.optoutputtype[2].checked=true;"
                           style="cursor:hand">
                        <input type="radio" name="optoutputtype"></input>HTML
                                                                         Format
                      </div>
                    </td>
                  </tr>
                </table>
              </div>
            </td>
          </tr>
          <tr>
            <td colspan="2" class="tdH" align="center"><input type="button" value="Submit" onclick="frmsubmit()">
            <input type="button" id="cmdcancel" name="cancel" value="Cancel" onclick="self.close();"> </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>