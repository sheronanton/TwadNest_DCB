
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,java.text.SimpleDateFormat,java.text.DateFormat,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>GPF Proceedings - Payment Of Interest</title>
    <link href="../scripts/Proceeding_jquery-ui.css" rel="stylesheet" type="text/css"/>
  <script src="../scripts/Proceeding_jquery-3.6.0.js"></script>
  <script src="../scripts/Proceeding_jquery-ui.min.js"></script>
   <script type="text/javascript" src="../../scripts/CalendarControl.js"></script>
        <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
          
           

  
  <script>
  $(document).ready(function() {
    $("#tabs").tabs();
  });
  </script>
    
    <script type="text/javascript" src="../scripts/GPF_Proceding_Details.js"></script>
    <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript">
/*    function btnsubmit1()
{
   //  alert("submit");
        //var c=toFocus();
       // if(c==true)
      //  {
            //  var path=document.getElementById("photoPath").value;
             // alert(path);
            
              //alert(path);
              
            //  path1="../../images/EmpPhotos/"+path;
            //  alert(path1);
	if(nullCheck23())
    {
              document.frmEmployeeProfile.action="../../../../../../Create_GPF_Proceeding.av?Command=Report";
              document.frmEmployeeProfile.method="POST";
              document.frmEmployeeProfile.submit();
              return true;
    }
     //   }
   //     else
   //     {
       //         return false;
     //   }
}*/
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
              document.frmEmployeeProfile.action="../../../../../../Create_GPF_Proceeding.av?Command=Report";
              document.frmEmployeeProfile.method="POST";
              document.frmEmployeeProfile.submit();
              return true;
   
}

function wedraft12(){
	if(nullCheck23())
    {
		 
   // var yer=document.frmEmployeeProfile.per1.value;---New Changed
   // var fwd_desig=document.frmEmployeeProfile.fwd_desig.value;
  //  var fwd_place=document.frmEmployeeProfile.fwd_place.value;
    var intdate=document.getElementById("interestdate").innerHTML;    
    var yer= document.getElementById("c1").innerHTML;
   
	document.frmEmployeeProfile.action="../../../../../../Create_GPF_Proceeding.av?Command=draft&yer="+yer+"&intdate="+intdate;
    document.frmEmployeeProfile.method="POST";    
    document.frmEmployeeProfile.submit();
    if( document.getElementById("validateTXT").value=="1")
    document.frmEmployeeProfile.validate.disabled=true;
    else
    document.frmEmployeeProfile.validate.disabled=false;
    }
}
function nullCheck23()
 {

	try
	 {
	if(document.frmEmployeeProfile.txtref.value=="")
     {
        alert("Please Enter the Proceeding Reference No");
        document.frmEmployeeProfile.txtref.focus();
        return false;
     }	 
	
    else if(document.frmEmployeeProfile.txtEmpId1.value=="")
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
    
       else if(document.frmEmployeeProfile.txtPDat.value=="")
       {
          alert("Please Enter the Proceeding Date");
          document.frmEmployeeProfile.txtPDat.focus();
          return false;
       }
       else if(document.frmEmployeeProfile.txtPO.value=="")
       {
          alert("Please Enter the Presiding Officer");
          document.frmEmployeeProfile.txtPO.focus();
          return false;
       }
       else if(document.frmEmployeeProfile.txtPODesig.value=="")
       {
          alert("Please Enter the Presiding Officer Designation");
          document.frmEmployeeProfile.txtPODesig.focus();
          return false;
       }
       else if(document.frmEmployeeProfile.txtSubject.value=="")
       {
           alert("Please Enter the Subject");
           document.frmEmployeeProfile.txtSubject.focus();
           return false;
        }
       else if(document.frmEmployeeProfile.txtRef.value=="")
       {
          alert("Please Enter the Reference");
          document.frmEmployeeProfile.txtRef.focus();
          return false;
       }
       else if(document.frmEmployeeProfile.txtbills.value=="")
       {
          alert("Please Enter the Presenting Bills At Office");
          document.frmEmployeeProfile.txtbills.focus();
          return false;
       }
       else if(document.frmEmployeeProfile.txtCopy.value=="")
       {
          alert("Please Enter the Copy To");
          document.frmEmployeeProfile.txtCopy.focus();
          return false;
       }
	 }
	 catch(e)
	 {
		 alert(e.message);
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

           // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
     /* */  
     st=con.createStatement();
     rs2=st.executeQuery("SELECT SLIP_ISSUED_FIN_YEAR FROM HRM_GPF_LAST_SLIP_ISSUED");
          					while(rs2.next()){
          						year1=rs2.getString("SLIP_ISSUED_FIN_YEAR");
          						int ye1=Integer.parseInt(year1.substring(0,4));
          						//System.out.println("Year1 "+ye1);
          						int ye2=Integer.parseInt(year1.substring(5,9));          						
          						//System.out.println("Year2 "+ye2);
          						year2=(ye1-1)+"-"+(ye2-1);
          						ye1=ye1+1;   
          						ye2=ye2+1;
          						Year=ye1+"-"+ye2;
          						//System.out.println("Year "+Year);
          						}
                
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
System.out.println("year----------------------------------->"+year);
/*HttpSession session=request.getSession(false);
    UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
     
    System.out.println("user id::"+empProfile.getEmployeeId());*/

%>
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
                   <td style="width: 417px">
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
<input type="text" name="txtGpf"  onchange="callServer('Existg','null')" onkeypress="return  numbersonly1(event,this)" id="txtGpf" maxlength="10" size="6" />
                   <input type="hidden" name ="gpf">  <img src="../../../../../../images/c-lovi.gif" width="20" name="toc" id="toc"
                           height="20" alt="empList"
                           onclick="servicepopup();"></img>
                   
                   
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
                      <input tabindex="1" type="text" readonly="readonly" size="6" style="TEXT-TRANSFORM:UPPERCASE; background-color: #ffffff; width:class="disab" name="txtEmpId1" maxlength="5" id="txtEmpId1"/>
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
              <input type="hidden" value="<%=Year%>" name="per1"></input>  
                                 
              </div>
            </td>
          </tr>


             <input type="hidden" name="txtPNo" id="txtPNo" >
          </td>
        </tr>
         
          
                </table>
               <div id="tabs">
    <ul>
        <li><a href="#fragment-1"><span><strong>Notice Details</strong></span></a></li>
        <li><a href="#fragment-2"><span><strong>Additional Proceedings Details</strong></span></a></li>
    </ul>
    <div id="fragment-1">
      <table cellspacing="3" cellpadding="2" border="1" width="100%">
         

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
          	<td style="width: 471px"></td>
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
          	<td>5.<font color="#347C17"> Interest for Excess Debit upto</font><font color=#347C17> <span id="span11To1" > </span></font></td>
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
          		<strong><span id="txt10GpfReg" > </span> </strong>       		
				</div>
			</td>
          	<td>
          		<div align="right">
          		<strong><span id="txt10GpfIm" > </span></strong>  
                </div>
			</td>
          </tr>
        </table>
    </div>
    <div id="fragment-2">
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
        <tr class="table">
            <td>
              <div align="left">Additional Authorised <font color="#ff2121">                
              </font></div>
            </td>
            <td>                     
                           <input type="radio" name="txtAdditionAuthFlag" id="txtAdditionAuthFlagYes" value="Yes" checked="true" /> Yes 
                           <input type="radio" name="txtAdditionAuthFlag" id="txtAdditionAuthFlagNo" value="No" /> No                    
              
            </td>
          </tr>          
          <tr class="table">
            <td>
              <div align="left">Missing Credit Content<font color="#ff2121">                
              </font></div>
            </td>
            <td>
                          <input type="radio" name="txtMissCreditContent" id="txtMissCreditContentYes" value="Yes" /> Yes 
                           <input type="radio" name="txtMissCreditContent" id="txtMissCreditContentNo" value="No" checked="true" /> No                    
              
            </td>
          </tr>
       
       <tr class="table">
            <td>
              <div align="left">Proceedings Reference No.<font color="#ff2121">
                
              </font></div>
            </td>
            <td>
              <div align="left">
                               
                       
                           <input type="text" name="txtref" id="txtref" style="background-color: #ffffff"   onkeypress="return noEnter(event)" 
                       size="100"readonly="readonly" />  
                      
                       
              </div>
            </td>
          </tr>
        <tr class="table">
          <td style="width: 460px">
            <div align="left">
              Proceedings Date
              <font color="#ff2121">
                
              </font>
            </div>
          </td>
          <td>
            <div align="left">

         <input type="text" name="txtPDat" id="txtPDat" size="10"  onkeypress="return  calins(event,this)"
                         onblur="return checkdt(this);"
                         onfocus="javascript:vDateType='3'" maxlength="10"/><img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmEmployeeProfile.txtPDat);" alt="Show Calendar" height="24" width="19"/>
            </div>
          </td>
        </tr>
        <tr class="table">
          <td>
            <div align="left">
              Presiding Officer
              <font color="#ff2121">
                
              </font>
            </div>
          </td>
          <td>
            <div align="left">
             Prefix <input type="text" name="prefix"  id="prefix" size="7" /> <input type="text" name="txtPO" id="txtPO" size="50"  />  <br></br>Suffix <input type="text" name="suffix"  id="suffix" size="12"  />
            </div>
          </td>
        </tr>
        <tr class="table">
          <td>
            <div align="left">
              Presiding Officer Designation
              <font color="#ff2121">
                
              </font>
            </div>
          </td>
          <td>
            <div align="left">
              <input type="text" name="txtPODesig" id="txtPODesig" size="50"  />
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
              <textarea name="txtSubject"cols="50" rows="2" id="txtSubject"
              title="Don't type '&' Character while entering data"
              id="txtSubject" onkeypress="return noEnter(event)" onblur="return chkSubj()" ></textarea>
            </div>
          </td>
        </tr>
        <tr class="table">
          <td style="width: 411px">
            <div align="left">
              Reference
            </div>
          </td>
          <td>
            <div align="left">
              <textarea cols="50" rows="2" id="txtRef" name="txtRef" 
              title="Don't type '&' Character while entering data"
               onkeypress="return noEnter(event)"  ></textarea>
            </div>
          </td>
        </tr>
  		<!--<tr class="table">
          <td>
            <div align="left">
            Presenting Bills At
            Office</div>
          </td>
          <td>
            <div align="left">
              <textarea cols="50" rows="1" id="txtbills" name="txtbills" 
              title="Don't type '&' Character while entering data"
               onkeypress="return noEnter(event)"  ></textarea>
            </div>
          </td>
        </tr>

        -->
        <tr class="table">
          <td>
            <div align="left">
            Presenting Bills At
            Office</div>
          </td>
          <td>
            <div align="left">
              <input type="text" name="txtbills" id="txtbills" title="Don't type '&' Character while entering data"
               onkeypress="return noEnter(event)" size="50"  />
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
              <input type="text" name="txtbills1" id="txtbills1" title="Don't type '&' Character while entering data"
               onkeypress="return noEnter(event)" size="50"  />
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
              <input type="text" name="txtbills2" id="txtbills2" title="Don't type '&' Character while entering data"
               onkeypress="return noEnter(event)" size="50"  />
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
            <div align="left">
              <textarea name="txtCopy" id="txtCopy"
                            cols="40" rows="3" onkeypress="return noEnter(event)" onblur="return chkCopy()"  ></textarea>
<input type="hidden" name="Cpy"></input>                           
            </div>
          </td>
        </tr>
       <!-- 
        <tr class="tdH">
            <td colspan="2">
              <div align="left">Forwarded by Authority</div>
            </td>
          </tr>
         <tr class="table">
          <td>
            <div align="left">
            
          Designation</div>
          </td>
          <td>
            <div align="left">
              <input type="text" name="fwd_desig" id="fwd_desig" title="Don't type '&' Character while entering data"
               onkeypress="return noEnter(event)" size="50"  />
            </div>
          </td>
        </tr>
        
        
         <tr class="table">
          <td>
            <div align="left">
           Place</div>
          </td>
          <td>
            <div align="left">
              <input type="text" name="fwd_place" id="fwd_place" title="Don't type '&' Character while entering data"
               onkeypress="return noEnter(event)" size="50"  />
            </div>
          </td>
        </tr>
       -->
        </table>
    </div>
    
</div>
                
         <table cellspacing="2" cellpadding="3" border="1" width="100%">       
        <tr class="table">
   <td align="left" style="width: 317px">Report&nbsp;Type  
   </td>
   <td style="width: 460px">
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
     
             <input type="button" id="draft" name="draft" value="Generate Draft"
                     onclick="wedraft12()"/>                      
             
             <input type="button" disabled="disabled" id="validate" name="validate" value="Validate" onclick="validate12()"/>
             <input type="hidden" name="validateTXT" id="validateTXT"></input>
              <input type="button" name="button" value="Clear All"
                     onclick="clearAllit()"/>
              
              <input type="button" name="button" value="Cancel"
                     onclick="self.close()"/>
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>
