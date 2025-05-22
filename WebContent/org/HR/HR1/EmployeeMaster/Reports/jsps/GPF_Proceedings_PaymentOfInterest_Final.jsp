
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,java.text.SimpleDateFormat,java.text.DateFormat,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>GPF Proceeding - Payment Of Interest</title>
     <script type="text/javascript" src="../scripts/GPF_Proceding_Details.js"></script>
    <script type="text/javascript">
    function btnsubmit1()
{
   
	if(document.frmEmployeeProfile.txtGpf.options[0].selected){
	   		alert("Select the GPF Number");
          document.frmEmployeeProfile.txtGpf.focus();
          return false;
   	 }
              document.frmEmployeeProfile.action="../../../../../../Create_GPF_Proceeding.av?Command=Report";
              document.frmEmployeeProfile.method="POST";
              document.frmEmployeeProfile.submit();
              return true;
   
}

      
</script>

   <link href="../../../../../../css/Sample3.css" rel="stylesheet"          media="screen"/>
  
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"          media="screen"/>
     <script type="text/javascript" src="../../scripts/CalendarControl.js"></script>
  </head>
  <body onload="callServer1('GetProGPF','null')">
       <%
  //System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiii");
  Connection con=null;
  Statement st=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
     ResultSet rs2=null;
    PreparedStatement ps1=null;
    ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
  String year1="",Year="";
   try
  {
           // System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiii");
             ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rs1.getString("Config.DSN");
            String strhostname=rs1.getString("Config.HOST_NAME");
            String strportno=rs1.getString("Config.PORT_NUMBER");
            String strsid=rs1.getString("Config.SID");
            String strdbusername=rs1.getString("Config.USER_NAME");
            String strdbpassword=rs1.getString("Config.PASSWORD");

          //  ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
           ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
       
             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
        //     System.out.println("connected");
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  String gpfNo="";
  try{
  gpfNo=request.getParameter("gpfNo");
  }
  catch(Exception e){
  System.out.println(e);
  }
  %>
  
  <%
      HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
        String updatedby=(String)session.getAttribute("UserId");
      //  System.out.println(updatedby);
                    long l=System.currentTimeMillis();
                    Timestamp ts=new Timestamp(l);
    System.out.println("user id::"+empProfile.getEmployeeId());
    int empid=empProfile.getEmployeeId();
    int  oid=0;
    String oname="",ounitname="",oadd2="",ocity="",odist="",olid="",owid="";
    String olname="";
    String ownature="";
    String proceed="";
    try
    {
          
            ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
            ps.setInt(1,empid);
            results=ps.executeQuery();
                 if(results.next())
                 {
                    oid=results.getInt("OFFICE_ID");
                
                 }
            results.close();
            ps.close();
            ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where accounting_unit_id not in('999','5','6') and ACCOUNTING_UNIT_OFFICE_ID=?" );
            ps.setInt(1,oid);
            results=ps.executeQuery();
                 if(results.next())
                 {
                    oname=results.getString("ACCOUNTING_UNIT_ID");
                   ounitname=results.getString("ACCOUNTING_UNIT_NAME");
                   
                   
                  }
            results.close();
            ps.close();
     
                
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
  
   %>

  <form name="frmEmployeeProfile"  method="POST">
  <%
java.util.Date d=new java.util.Date();
DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//System.out.println("Start");
//System.out.println("currnt date----->"+dateFormat.format(d));
int mth=d.getMonth()+1;
int year=(d.getYear()+1900);
/*HttpSession session=request.getSession(false);
    UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
     
    System.out.println("user id::"+empProfile.getEmployeeId());*/
String emp_id="11263";
%>
       <input type="hidden" value="<%=gpfNo%>" name="getGPF">
       <input type="hidden" name="txtseloff">
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>GPF Proceedings - Payment Of Interest</strong>
            </div>
          </td>
        </tr>
       <tr class="tdH">
            <td colspan="2">
              <div align="left">
                <strong>Account Unit Details</strong>
               </div>
            </td>
          </tr>
          <tr class="table">
                   <td style="width: 184px">
                                       <div align="left">
                                             Account Unit ID
                                        </div>
                                       </td>
                                     <td style="width: 445px">
                                      <div align="left">
                                     <%=oname%>
                                   </div>
                                   </td>
                                   </tr>
       
              <tr class="table">
            <td>
              <div align="left">Account Unit Name</div>
            </td>
            <td>
              <div align="left">
             <%=ounitname%>                      
                            </div>
            </td>
          </tr>
         
         
         <tr class="tdH">
          <td colspan="2">
            <div align="left">
              <strong>Proceedings Details</strong>
            </div>
          </td>
        </tr>
        
         
          <tr class="table">
                  <td>
                    <div align="left">GPF No.<font color="#ff2121">
                *
              </font></div>
                  </td>
                  <td>
                   
                   <select name="txtGpf" id="txtGpf" onchange="callServer1('GetProDetails','null')">
                   <option value="Select GPF No">Select GPF No</option>
                   </select>
                   
                  </td>
                  
                </tr>
                </tr>
                <tr class="table">
                  <td>
                    <div align="left">
                      <font color="#808080">Employee Id</font>
                    </div>
                  </td>
                  <td>
                    <div align="left">
                    <span id="empid" > </span>
                    </div>
                  </td>
                </tr>
                 <tr class="table">
                  <td>
                    <div align="left">
                      <font color="#808080">Employee Name</font>
                    </div>
                  </td>
                  <td>
                    <div align="left">
                   <span id="empname" > </span>
                    </div>
                  </td>
                </tr>
                 <tr class="table">
                  <td>
                    <div align="left">
                      <font color="#808080">Employee Designation</font>
                    </div>
                  </td>
                  <td>
                    <div align="left">
                    <span id="empdes" > </span> 
                    </div>
                  </td>
                </tr>
                 <tr class="table">
            <td>
              <div align="left">Period</div>
            </td>
            <td>
              <div align="left">
              <span id="c1" > </span> 
              </div>
            </td>
          </tr>
 <tr class="table">
          <td>
            <div align="left">
              Proceedings No.
             
            </div>
          </td>
          <td>
 
            <!--  <select size="1" name="cmbProceed" id="cmbProceed"
                    onchange="loadServer('loadEmp')" >
              <option value="">
                --Select Proceedings ID--
              </option> --><span id="PRONo" > </span> 
           
          </td>
        </tr>
         
          <tr class="table">
            <td>
              <div align="left">Proceedings Reference No.</div>
            </td>
            <td>
              <div align="left">
                               
                       
                       <span id="proRefNo" > </span>
                      
                       
              </div>
            </td>
          </tr>
        
       
       
        <tr class="tdH">
          <td colspan="5">
            <div align="left">
              <strong>Additional Proceedings Details</strong>
            </div>
          </td>
        </tr>
        <tr class="table">
          <td>
            <div align="left">
              Proceedings Date
              
            </div>
          </td>
          <td>
            <div align="left">
           <span id="PROcdate" > </span>           
            </div>
          </td>
        </tr>
        <tr class="table">
          <td>
            <div align="left">
              Presiding Officer
              
            </div>
          </td>
          <td>
            <div align="left">
         <span id="txtPO" > </span> 
            </div>
          </td>
        </tr>
        <tr class="table">
          <td>
            <div align="left">
              Presiding Officer Designation
              
            </div>
          </td>
          <td>
            <div align="left">
             <span id="txtPODesig" > </span> 
            </div>
          </td>
        </tr>
        <tr class="table">
          <td>
            <div align="left">
              Subject
            </div>
          </td>
          <td>
            <div align="left">
            <span id="txtSubject" > </span> 
            </div>
          </td>
        </tr>
        <tr class="table">
          <td>
            <div align="left">
              Reference
            </div>
          </td>
          <td>
            <div align="left">
           <span id="txtRef" > </span>    
            </div>
          </td>
        </tr>
<tr class="table">
          <td>
            <div align="left">
              Presenting Bills At 
            Office</div>
          </td>
          <td>
            <div align="left">
           <span id="txtbills" > </span>    
            </div>
          </td>
        </tr>
        <tr class="table">
          <td>
            <div align="left">
              Copy To
              <!--font color="#ff2121">*</font-->
            </div>
          </td>
          <td>
            <div id="ccy" align="left">
            <span id="txtCopy" > </span>
            </div>
          </td>
        </tr>
        <tr class="table">
   <td align="left">Report&nbsp;Type  
   </td>
   <td>
   <select name="cmbReportType">
   <option value="PDF" selected="selected">PDF</option>
   <option value="EXCEL">EXCEL</option>
   <option value="HTML">HTML</option>
   </select>
   </tr>
   
              <!--  <tr  class="table">
        <td>
        Signed By Presiding Officer
        </td>
        <td>
        Yes
        <input type="radio" name="signed" value="Y" checked onclick="checkfwd1()"/>
        No
        <input type="radio" name="signed" value="N"  onclick="checkfwd1()"/>
        </td>
        </tr>
        <tr  class="table" style="display:none" id="fwdoff1">
        <td>
        Signed By Forwarding Officer
        </td>
        <td>
        Yes
        <input type="radio" name="fsigned" value="Y" onclick="checkfwd()"/>
        No
        <input type="radio" name="fsigned" value="N" onclick="checkfwd()"/>
        </td>
        </tr>
        <tr class="table" style="display:none" id="fwdoff">
        <td>
        Fowarding officer Designation
        </td>
        <td >
         <input type="text" name="fwoffdesig" id="fwoffdesig"/>
         <input type="hidden" name="txtseloff1"   >
        </td>
        </tr>-->
       
       
        <!--tr class="tdH">
            <td colspan="2">
              <div align="center">
                <input type="button" name="butSave" id="butSave" value="Save" onclick="loadServer('save')" />
                 &nbsp;&nbsp;&nbsp;
                <input type="button" name="butCan" id="butCan" value="Cancel"
                       onclick="javascript:self.close();"/>
              </div>
            </td>
          </tr-->
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
            
              <input type="button" name="btnSubmit" value="Generate Report"
                     onclick="btnsubmit1()"/>
                    <!--   <input type="button" name="btn1" value="Create New Proceeding"
                     onclick="btn11()"/>-->
             
              <input type="button" name="button" value="Cancel"
                     onclick="self.close()"/>
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>
