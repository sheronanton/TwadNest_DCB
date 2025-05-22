
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,java.text.SimpleDateFormat,java.text.DateFormat,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>GPF - Partial Interest - Authorisation Note Order Details</title>
  
   <script type="text/javascript" src="../scripts/GPF_Partial_Interest_Authorisation_Note_Order_Details.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript">

 function btnsubmit1()
{
   
	if(document.frmEmployeeProfile.txtGpf.value==""){
	   		alert("Enter the GPF Number");
          document.frmEmployeeProfile.txtGpf.focus();
          return false;
   	 }
   	 if(document.frmEmployeeProfile.notice.options[0].selected){
   	 	  alert("Select the Notice Authorisation Serial No");
          document.frmEmployeeProfile.notice.focus();
          return false;
   	 }
   	var yer= document.getElementById("c1").innerHTML;
              document.frmEmployeeProfile.action="../../../../../../GPF_Authorisation_Note_Order_Details.av?Command=Report&fin_year="+yer;
              document.frmEmployeeProfile.method="POST";
              document.frmEmployeeProfile.submit();
              return true;
   
}

function wedraft12(){
	//alert("Draft");
	if(nullCheck23())
    {
		var yer= document.getElementById("c1").innerHTML;
	
	document.frmEmployeeProfile.action="../../../../../../GPF_Authorisation_Note_Order_Details.av?Command=draft&fin_year="+yer;
    document.frmEmployeeProfile.method="POST";
    document.frmEmployeeProfile.submit();
    }
}
function nullCheck23()
 {
    if(document.frmEmployeeProfile.txtGpf.value=="")
     {
        alert("Please Enter the GPF No");
        document.frmEmployeeProfile.txtGpf.focus();
        return false;
     }
	
    else if(document.frmEmployeeProfile.notice.options[0].selected){
   	 	  alert("Select the Notice Authorisation Serial No");
          document.frmEmployeeProfile.notice.focus();
          return false;
   	 }
               
    return true;
 }
</script>

   <link href="../../../../../../css/Sample3.css" rel="stylesheet"          media="screen"/>
  
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"          media="screen"/>
     <script type="text/javascript" src="../../scripts/CalendarControl.js"></script>
  </head>
  <body>
       <%
//  System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiii");
  Connection con=null;
  Statement st=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
     ResultSet rs2=null;
    PreparedStatement ps1=null;
    ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
  String year1="",Year="",year2="";
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
             System.out.println("connected");
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  %>
  <%
      HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
        String updatedby=(String)session.getAttribute("UserId");
        System.out.println(updatedby);
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
    System.out.println("Exception in connection...."+e);
  }
     /* */  
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
      <input type="hidden" name="txtseloff">
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>GPF - Partial Interest - Authorisation Note Order Details</strong>
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
                   <td style="width: 495px">
                                       <div align="left">
                                             Account Unit ID
                                        </div>
                                       </td>
                                     <td>
                                      <div align="left">
                                     <input type="text" name="txtOffId" id="txtOffId" maxlength="4" value="<%=oname%>"
                                           size="5" class="disab"  readonly="readonly"/>
                                   </div>
                                   </td>
                                   </tr>
       
              <tr class="table">
            <td>
              <div align="left">Account Unit Name</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtOffName" id="txtOffName" value="<%=ounitname%>"
                        maxlength="60" size="60"
                       readonly="readonly" class="disab"/>
                      
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
<input type="text" name="txtGpf"  onchange="callServer('Existg','null')" onkeypress="return  numbersonly1(event,this)" id="txtGpf" maxlength="6" size="6"/>
                   <input type="hidden" name ="gpf">  <img src="../../../../../../images/c-lovi.gif" width="20" name="toc" id="toc"
                           height="20" alt="empList"
                           onclick="servicepopup();"></img>
                   
                   
                  </td>
                  
                </tr>
 
                <tr class="table">
                  <td>
                    <div align="left">
                      <font color="#808080">Employee Id</font>
                    </div>
                  </td>
                  <td>
                    <div align="left">
                      <input tabindex="1" type="text" readonly="readonly" size="6" style="TEXT-TRANSFORM:UPPERCASE; background-color: #ffffff; class="disab" name="txtEmpId1" maxlength="5" id="txtEmpId1"/>
                              <input tabindex="1" type="HIDDEN" name="EmpId" id="EmpId"/>
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
                      <input type="text" name="txtEmployee" id="txtEmployee" readonly="readonly" class="disab"
                             style="TEXT-TRANSFORM:UPPERCASE; background-color: #ffffff"
                             maxlength="60" size="40"/>
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
                      <input type="text" name="txtEmployeeDes" id="txtEmployeeDes"readonly="readonly" class="disab"
                             style="TEXT-TRANSFORM:UPPERCASE; background-color: #ffffff"
                             maxlength="60" size="40"/>
                    </div>
                  </td>
                </tr>
               <tr class="table">
                  <td>
                    <div align="left">
                      Authorisation Note Serial No.
                    </div>
                  </td>
                  <td>
                    <div align="left">
                    <select name="notice" id="notice" onchange="callServer('GetDetails','null')">
                    <option value="notice">Select Authorisation Note No.</option>
                    </select>
                    </div>
                  </td>
                </tr>
                <tr class="table">
            <td>
              <div align="left">Period<font color="#ff2121">
                </font></div>
            </td>
            <td>
              <div align="left">
                             
          <span id="c1" name="c1">
            
              </span>
                              
              </div>
            </td>
          </tr>

                   <tr class="tdH">
            <td colspan="2">
              <div align="left">Relieval Details</div>
            </td>
          </tr>
          <tr class="table">
            <td >
              <div align="left">Reason For Relieval
              </div>
            </td>
            <td>            
              <div align="left">   
              <span id="spanrelieval" > </span>                              
              </div>
            </td>
          </tr>
          <tr class="table">
            <td >
              <div align="left">Relieval  Date</div>
            </td>
            <td colspan="2">
              <div  align="left">
               <span id="spanrelievaldate" > </span>  
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Date upto which Calculated</div>
            </td>
            <td colspan="2" style="width: 614px">
              <div  align="left">
               <span id="interestdate" > </span>  
             </div>
            </td>
          </tr>   
            <tr class="table">
            <td>
              <div align="left">Financial Year upto which Interest to be Calculated</div>
            </td>
            <td colspan="2" style="width: 614px">
              <div  align="left">
               <span id="finyear"> </span>  
             </div>
            </td>
          </tr>            
            <tr class="table">
            <td>
              <div align="left">Interest Amount Regular</div>
            </td>
            <td colspan="2" style="width: 614px">
              <div  align="left">
               <span id="interestamount" > </span>  
             </div>
            </td>
          </tr>  
          <tr class="table">
            <td>
              <div align="left">Interest Amount Impound</div>
            </td>
            <td colspan="2" style="width: 614px">
              <div  align="left">
               <span id="interest_amt_imp" > </span>  
             </div>
            </td>
          </tr>                            
        </table>
        <table cellspacing="3" cellpadding="2" border="1" width="100%"
               align="center">
          <tr class="tdH">
            <td colspan="3">
              <div align="left">GPF Settlement Interest Details</div>
            </td>
          </tr>
          <tr class="table">
          	<td style="width: 495px"></td>
          	<td ><div align="right">G.P.F. (REGULAR)</div></td>
          	<td ><div align="right">G.P.F. (IMPOUND REGULAR)</div></td>
          </tr>
          <tr class="table">
          	<td>1.<font color=#347C17> C.B. as per  <span id="spanFin" > </span> A/C slip</font></td>
          	<td>
          		<div align="right">
          	<font color=#347C17>	 <span id="txt1GpfReg" > </span>  </font>
			</div>
			</td>
          	<td>
          		<div align="right">   
          	<font color=#347C17>	<span id="txt1GpfIm" > </span>  </font>		
				</div>
			</td>
          </tr>
          <tr class="table">
          	<td>2. <font color="#347C17">Add Missing Credit</font></td>
          	<td>
          	
          		<div align="right">
          	<font color=#347C17>	<span id="txt4GpfReg" > </span>  </font>
				</div>
          		
			</td>
          	<td>
          		<div align="right">
          	<font color=#347C17>	<span id="txt4GpfIm" > </span>  </font>
				</div>
			</td>
          </tr>
                    <tr class="table">
          	<td>3. <font color="#347C17">Add Excess Debit</font></td>
          	<td>
          	
          		<div align="right">
          	<font color=#347C17>	<span id="txtNew11GpfReg" > </span>  </font>
				</div>
          		
			</td>
          	<td>
          		<div align="right">
          	<font color=#347C17>	<span id="txtNew11GpfIm" > </span>  </font>
				</div>
			</td>
          </tr>
          <tr class="table">
          	<td>4. <font color="#347C17">Interest for Missing Credit upto</font><font color=#347C17> <span id="spanTo1" > </span></font></td>
          	<td>
          	<div align="right">
          		<font color="#347C17"><span id="txt5GpfReg"> </span>  </font>
				</div>
			</td>
          	<td>
          	<div align="right">
          		<font color="#347C17"><span id="txt5GpfIm"> </span>  </font>
				</div>
			</td>
          </tr>
           <tr class="table">
          	<td>5. <font color="#347C17">Interest for Excess Debit upto</font><font color=#347C17> <span id="span11To1" > </span></font></td>
          	<td>
          	<div align="right">
          		<font color="#347C17"><span id="txtNew12GpfReg"> </span>  </font>
				</div>
			</td>
          	<td>
          	<div align="right">
          		<font color="#347C17"><span id="txtNew12GpfIm"> </span>  </font>
				</div>
			</td>
          </tr>
          
           <tr class="table">
          	<td align="right">Total</td>
          	<td>
          		<div align="right">
          		<span id="txtadd" > </span>
				</div>
			</td>
          	<td>
          		<div align="right">
          		<span id="txtadd1" > </span> 
				</div>
			</td>
          </tr>
          
          
          
          
          
          
          
          
          
          
          
          <tr class="table">
          	<td>6.<font color=#E41B17>  Excess Credit </font></td>
          	<td>
          		<div align="right">
          		<font color=#E41B17> <span id="txt7GpfReg" > </span>  </font>
				</div>
			</td>
          	<td>
          		<div align="right">
          		<font color=#E41B17> <span id="txt7GpfIm" > </span>  </font>
				</div>
			</td>
          </tr>
          <tr class="table">
          	<td>7.<font color=#E41B17>  Missing Debit </font></td>
          	<td>
          		<div align="right">
          		<font color=#E41B17> <span id="txtNew13GpfReg" > </span>  </font>
				</div>
			</td>
          	<td>
          		<div align="right">
          		<font color=#E41B17> <span id="txtNew13GpfIm" > </span>  </font>
				</div>
			</td>
          </tr>
          <tr class="table">
          	<td>8.<font color=#E41B17>  Interest for Excess Credit upto <span id="spanTo3" > </span></font></td>
          	<td>
          		<div align="right">
          		<font color=#E41B17> <span id="txt8GpfReg" > </span>  </font>
				</div>
			</td>
          	<td>
          		<div align="right">
          		<font color=#E41B17> <span id="txt8GpfIm" > </span>  </font>
			</div>
			</td>
          </tr>
                  <tr class="table">
          	<td>9.<font color=#E41B17>  Interest for Missing Debit upto <span id="span33To3" > </span></font></td>
          	<td>
          		<div align="right">
          		<font color=#E41B17> <span id="txtNew14GpfReg" > </span>  </font>
				</div>
			</td>
          	<td>
          		<div align="right">
          		<font color=#E41B17> <span id="txtNew14GpfIm" > </span>  </font>
			</div>
			</td>
          </tr>
          
           <tr class="table">
          	<td align="right">Balance C.B. as per A/C slip after Missing / Excess  Credit/Debit</td>
          	<td>
          		<div align="right">
          		<span id="txtSubr" > </span> 
				</div>
			</td>
          	<td>
          		<div align="right">
          		<span id="txtSubr1" > </span>
				</div>
			</td>
          </tr>
          
          
          
          
          <tr class="table">
          	<td>10.<font color="#347C17"> Add Subsequent deposit</font></td>
          	<td>
          	<div align="right">
          		<font color=#347C17><span id="txt2GpfReg" > </span>  </font>
				</div>
			</td>
          	<td>
          	
          	<div align="right">
          		<font color="#347C17"><span id="txt2GpfIm"> </span>  </font>
				</div>
          		
			</td>
          </tr>
          <tr class="table">
          	<td>11. <font color="#347C17"> Interest from <span id="spanFrom"> </span> to</font><font color=#347C17> <span id="spanTo2" > </span> </font></td>
          	<td>
          		
          		<div align="right">
          		<font color=#347C17><span id="txt3GpfReg" > </span>  </font>
				</div>
			</td>
          	<td>
          		
          		<div align="right">
          		<font color=#347C17><span id="txt3GpfIm" > </span>  </font>
				</div>
			</td>
          </tr>
          
          <tr class="table">
          	<td align="right">Total</td>
          	<td>
          		<div align="right">
          		<span id="txtSubfirst" > </span> 
				</div>
			</td>
          	<td>
          		<div align="right">
          		<span id="txtSubfirst1" > </span>
				</div>
			</td>
          </tr>
          
          
          
          
          
          
          
          
          
          
          <tr class="table">
          	<td>12.<font color=#E41B17> Subsequent Withdrawls</font></td>
          	<td>
          		<div align="right">
          		<font color=#E41B17> <span id="txt6GpfReg" > </span>  </font>
				</div>
			</td>
          	<td>
          		<div align="right">
          		<font color=#E41B17> <span id="txt6GpfIm" > </span>  </font>
				</div>
			</td>
          </tr>
          <tr class="table">
          	<td>13.<font color=#E41B17>  Amount Already Authorised</font></td>
          	<td>
          		<div align="right">
          		<font color=#E41B17> <span id="txt9GpfReg" > </span>  </font>
			</div>
			</td>
          	<td>
          		<div align="right">
          		<font color=#E41B17> <span id="txt9GpfIm" > </span>  </font>
				</div>
			</td>
          </tr>
          
          
          <tr class="table">
          	<td align="right">Total</td>
          	<td>
          		<div align="right">
          		<span id="txtSubfirstt" > </span> 
				</div>
			</td>
          	<td>
          		<div align="right">
          		<span id="txtSubfirstt1" > </span>
				</div>
			</td>
          </tr>
          
          
          
          
          
          <tr class="table">
          	<td><strong> Balance To Be Authorised</strong></td>
          	<td>
          		<div align="right">    
          		<strong><span id="txt10GpfReg" > </span></strong>        		
				</div>
			</td>
          	<td>
          		<div align="right">
          		<strong><span id="txt10GpfIm" > </span>  </strong>
                </div>
			</td>
          </tr>
        <tr class="table">
   <td align="left">Report&nbsp;Type  
   </td>
   <td colspan="2">
   <select name="cmbReportType">
   <option value="PDF" selected="selected">PDF</option>
   <option value="EXCEL">EXCEL</option>
   <option value="HTML">HTML</option>
   </select>
   </tr>
        <tr class="tdH">
          <td colspan="4">
            <div align="center">
              <input type="button" id="draft" name="draft" value="Generate Note"
                     onclick="wedraft12()"/>
                    <!-- <input type="button" disabled="disabled" name="validate" value="Validate Note" onclick="validate12()"/> --> 
              <input type="button" name="button" value="Clear All"
                     onclick="clearAllit()"/>
              <input type="button" name="button" value="Exit"
                     onclick="self.close()"/>
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>
