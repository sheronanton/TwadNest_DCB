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
    <title>Seniority Report</title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <script type="text/javascript"
            src="../scripts/EmployeeDetailsReportJS_New_Interface_dob.js"></script>
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
    <script type="text/javascript">
    
    function frmsubmit1()
	{
	
	var alldes="";
	var stdata="";
	var count=0;
	var chkcadre="";
	try{
	
	
	  for(i=0;i<document.frmValidationSummaryRep.chkcadre.length;i++)
    	        	
    	        {
    	        	
    	        	if(document.frmValidationSummaryRep.chkcadre[i].checked==true)
    	        	{
    	        		count++;
    	        		stdata+=document.frmValidationSummaryRep.chkcadre[i].value+",";
    	        	}
    	                
    	        }
    	        //stdata=stdata.substring(0,stdata.length-1);
    	        //var ss=stdata.length();
    	        //alert(ss)
    	        stdata = stdata.replace(stdata.substring(stdata.length()-1), "");
    	        }
    	        catch (e) {
					//alert("hai akrthik")
				}
    	    
    	     try
    	     {
    	       
    	        for(i=0;i<document.frmValidationSummaryRep.chkpanelcdr.length;i++)
    	        	
    	        {
    	        	
    	        	if(document.frmValidationSummaryRep.chkpanelcdr[i].checked==true)
    	        	{
    	        	
    	        	
    	        		count++;
    	        		stdata+=document.frmValidationSummaryRep.chkpanelcdr[i].value+",";
    	        	}
    	                
    	        }
    	        
    	        stdata=stdata.substring(0,stdata.length-1);
    	        }
    	        catch (e) {
					
				}
				if(stdata=="")
				{
				alert("please select Cadre Or  List");
				return false;
				}
				else
				{
				stdata=stdata;
				}
    	      // alert(stdata+"st data is----->");  
    	        
	/*if(document.Compassionate_details.panel_repo[0].checked==true)
	{
	alldes="all";
	title="Executive Engineer and  Superintending Engineer";
	}
	
	else if(document.Compassionate_details.panel_repo[1].checked==true)
	{
	alldes="AE";
	title="Superintending Engineer ";
	}
	else 
	{
	alldes="EE";
	title="Executive Engineer ";
	}*/
	//alert(alldes);
	document.frmValidationSummaryRep.action="cadre_wise_emp_rep_new_mis.jsp?alldes="+stdata;
	
	//alert(url);
	document.frmValidationSummaryRep.submit();
	
	}
    </script>
     
  </head>
  <!--- action="../../../../../EmployeeDetailReportServ.rep" -->
  <body onload="getdesig();selectoption1();"><form name="frmValidationSummaryRep" method="POST" 
                       >
  
         
          
      <div align="center">
        <table border='0' width="100%" >
          <tr>
            <td colspan="2" align="left" valign="top" class="table" width="60%">
              <table border="0" width="100%">
             
                <tr>
                  <td colspan='2' class="tdH" align="center"><strong> Seniority  Report</strong></td>
                </tr>
               <tr>
                  <td colspan="2" class="tdH" style="display:none">Selection of Status</td>
                </tr>
                 <tr>
                     <td colspan="2">
                            <table border="0" cellpadding="0" cellspacing="0"
                                   width="100%">
                              <tr>
                                <td style="display:none">
                                  <select name="cmbstatus" id="cmdstatus"
                                         
                                          onclick="getStatus()">
                                    <option>Select the Status</option>
                                  </select>
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <div class="scroll" style="display:none" id="diviframestatus" align='left' style='width:27%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                                </td>
                              </tr>
                            </table>
                          </td>
                        </tr> 
                       
       <tr>
                  <td colspan="2" class="tdH">Selection  Cadre</td>
                </tr>
       
       <tr  >                  
                <td class="table" align="left" width="500" colspan="1"><input type="radio" value="Dest" style="display:none" onclick="selectoption1()"
                             name="optselect" id="optselect1" ></input>
                      
                      <input type="radio" value="Rank" name="optselect"  onclick="selectoption1()"
                             id="optselect2" style="display:none" ></input>
                     
                       <input type="radio" value="Cadr" name="optselect" style="display:none" onclick="selectoption1()"
                             id="optselect3" ></input>
                    
                       <div id="divallofficelevel"   style="cursor:hand">
                     <input type="radio" name="optofficelevel" style="display:none"
                             checked="checked" ></input>Cadre 
                    </div>
                    </td>
                  <td class="table" align="center" colspan="1"  width="500">
                    <div id="divspecificofficelevel"  style="cursor:hand" >
                    <input type="radio" name="optofficelevel" style="display:none"></input>
                                                                         List
                    </div>
                     
                  </td>         
        </tr>
        
        
        
                   
                    
                
                
                
       
                
                
                <tr  id="divcadre" >
                
                  <td  align="left"  style="width:500px">
                  
                    <div >
                    <select name="cmbsgroup2" id="cmbsgroup2" onchange="getcadre()">
                <option value="0">Select Service Group</option>
                <%
          ResultSet   rss=null;
           try
           {
           ps=connection.prepareStatement("select SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_MST_SERVICE_GROUP  order by SERVICE_GROUP_NAME");
            rss=ps.executeQuery();
              int strcode=0;
            String strname="";          
            while(rss.next())
            {
              
               
                strcode=rss.getInt("SERVICE_GROUP_ID");
                strname=rss.getString("SERVICE_GROUP_NAME");
                
                out.println("<option value='"+strcode+"'>"+strname+"</option>");
                
             }
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+e);
          }
           finally
          {
                rss.close();
                ps.close();
          
          }    
                
        %>
               
               </select> 
                     <div class="scroll" id="divdesignation2" align='left' style='width:70%;'></div>
                    </div></td><td width="500" class="table" align="center" >
                   <div class="scroll" id="divdesign"  style='width:50%;'></div>
                    
                  </td>
                  
                </tr>
                
                
              <tr class="table" id="desigblock" style="display:none; ">
               <td>
               <div class="scroll" align='left' style='width:37%'  id="divdesignation">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
               </td>  
               </tr>  
                
                
                
                
                
                
                
                
              
                
                
                
                
                
                <tr>
                  <td colspan="2" class="tdH" style="display:none">Selection of Office Jurisdiction</td>
                </tr>
                <tr>
                  <td class="table" align="left">
                                            
                              <%
                      HttpSession session=request.getSession(false);
                      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                      
                    System.out.println("user id::"+empProfile.getEmployeeId());
                    int empid=empProfile.getEmployeeId();
                    int oid=0;
                    String deptid="",offlevel="";
                    
                         try
                        {
                               
                                ps=connection.prepareStatement("select b.OFFICE_LEVEL_ID,b.office_id from hrm_emp_current_posting a "+
                                        " inner join com_mst_offices b on b.office_id=a.office_id "+
                                        " where a.employee_id=?" );
                                ps.setInt(1,empid);
                                results=ps.executeQuery();
                                     if(results.next()) 
                                     {
                                        offlevel=results.getString("OFFICE_LEVEL_ID");
                                        oid=results.getInt("office_id");
                                     }
                                results.close();
                                ps.close();
                                System.out.println("office id:"+oid);
                                System.out.println("dept id:"+deptid);
                                
                                }
                                 catch(Exception e)
                        {
                            System.out.println(e);
                        }
                        
                        
   
                    
                    %>
                         
                         
                                                    
                           

                    </td>
                    
                </tr>
                    
                
              
                    
          <tr>
            <td colspan="2" class="tdH" align="center"><input type="button" value="Submit" onclick="frmsubmit1()">
            <input type="button" id="cmdcancel" name="cancel" value="Cancel" onclick="self.close();"> </td>
          </tr>
        </table>
      </div>
      </td>
      </tr>
      </table>
      </div>
      
    </form></body>
</html>