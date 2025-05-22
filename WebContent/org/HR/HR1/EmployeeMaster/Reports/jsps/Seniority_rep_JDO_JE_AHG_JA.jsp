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

        //    ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
    <title> Employees Seniority list </title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <script type="text/javascript"
            src="../scripts/Seniority_rep_JDO_JE_AHG_JA.js"></script>
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
  </head>

  <body><form name="Seniority_rep_JDO_JE_AHG_JA" >
  
         
            <table border="0" width="100%">
            <tr class="tdH">
            
            <td align="center" colspan="2"><b>
           Employee Seniority Reports for DO/JE/AHG/JA </b>
            </td>
            </tr>
            <tr class="tdH">
            
            <td align="left" colspan="2"><b>
            Selection Of Cadre</b>
            </td>
            </tr>
            <tr>
            <td>Select Cadre</td>
            <td>
            <select name="cadre_id" id="cadre_id" onchange="getcadre()">
            <option >---Select Cadre Id---</option>
            <%
            String sql="SELECT mst.CADRE_ID AS CADRE_ID , " +
							"  CADRE_NAME, " +
							"  ORDERING_SEQUENCE_NO " +
							"FROM " +
							"  (SELECT CADRE_ID, " +
							"    CADRE_NAME " +
							"  FROM HRM_MST_CADRE " +
							"  WHERE CADRE_ID  IN(16,22,44,129) " +
							"  )mst " +
							"LEFT OUTER JOIN " +
							"  (SELECT CADRE_ID, " +
							"    MIN(ORDERING_SEQUENCE_NO) AS ORDERING_SEQUENCE_NO " +
							"  FROM HRM_MST_DESIGNATIONS " +
							"  GROUP BY CADRE_ID " +
							"  )sub " +
							"ON sub.CADRE_ID=mst.CADRE_ID " +
							"ORDER BY CADRE_NAME";
            ps=connection.prepareStatement(sql);
            results=ps.executeQuery();
            while(results.next())
            {
            out.println("<option value="+results.getInt("CADRE_ID")+">"+results.getString("CADRE_NAME")+"</option>");
            //out.print("<option value="+results.getInt("CADRE_ID")+">"+results.getString("CADRE_NAME")+"</option>";
            }
            
            
             %>
            </select>
            </td>
            </tr>
            <tr class="tdH">
            
            <td align="left" colspan="2"><b>
            Selection Of Status</b>
            </td>
            </tr>
            <tr>
            <td colspan="2">
            <input type="radio" name="Seni_sta" id="Seni_sta1" value="PRO" onclick="show()">Proceeding
             <input type="radio" name="Seni_sta" id="Seni_sta2" value="All" onclick="hide()">All
               <input type="radio" name="Seni_sta" id="Seni_sta3" value="WRK" onclick="hide()">Working
            </td>
            </tr>
            <tr id="proc_no" style="display: none">
            <td colspan="3">
            <select  name="pro_id" id="pro_id">
            <option value="">---Select Proceeding No---</option>
            </select>
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
                      
                        <input type="radio" name="optoutputtype" value="pdf" checked></input>PDF
                                                                         Format
                      
                    </td>
                  </tr>
                  <tr>
                    <td colspan="2" class="table" align="left">
                      
                        <input type="radio" name="optoutputtype" value="excel"></input>EXCEL
                                                                         Format
                      
                    </td>
                  </tr>
                  <tr>
                    <td colspan="2" class="table" align="left">
                      
                        <input type="radio" name="optoutputtype" value="html"></input>HTML
                                                                         Format
                      
                    </td>
                  </tr>
                </table>
              </div>
            </td>
          </tr>
          <tr>
            <td colspan="2" class="tdH" align="center">
            <input type="button" value="Submit" onclick="getdata();">
            <input type="button" id="cmdcancel" name="cancel" value="Cancel" onclick="self.close();">
            </td>
          </tr>
            </table>
           
            
  
     
    </form></body>
</html>