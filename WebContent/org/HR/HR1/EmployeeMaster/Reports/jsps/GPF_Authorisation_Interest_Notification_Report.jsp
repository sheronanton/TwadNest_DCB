<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<% System.out.println("This is my jsp1");%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile,Servlets.HR.HR1.EmployeeMaster.servlets.GPF_Authorisation_Interest_Notification"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="Cache-Control" content="No-Cache"/>
    <title>Authorisation Note For GPF Interest Report</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"
            src="../../../../../Library/scripts/checkDate.js"></script>
              
    <!-- <script type="text/javascript"       src="../../../../../org/Library/scripts/CalendarControl.js"></script>-->
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <script type="text/javascript" src="../../scripts/CalendarControl.js"></script>
    <script type="text/javascript" src="../../scripts/selectOfficeAttached.js">  </script>
    <script type="text/javascript" src="../scripts/GPF_Authorisation_Interest_Notification_Report.js">  </script>
  
    <style type="text/css">
.cal {font-family:verdana; font-size:12px;}
</style>
  </head>
  <body id="bodyid" >
  <form name="Hrm_GpfSettlementForm">

      <div align="center">
        <table cellspacing="3" cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <strong>Authorisation Note For GPF Interest Report</strong>
              </div>
            </td>
          </tr>
          
          <tr class="tdH">
            <td colspan="2">
              <div align="left">Employee Details</div>
            </td>
          </tr>
                   
           <tr class="table">
            <td>
              <div align="left">GPF NO<font color="#ff2121">*</font></div>  
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtGpfNo" id="txtGpfNo"  onkeypress="return  numbersonly1(event,this)"
                       maxlength="6" size="6" onchange="call('Get');" />
                       <img src="../../../../../../images/c-lovi.gif" width="20"
                     height="20" alt="empList" onclick="servicepopup();"></img>
              </div>
            </td>
          </tr>
          
          <tr class="table">
            <td>
              <div align="left">Employee Name</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="comEmpId" id="comEmpId" size="40"
                       readonly="readonly" style="background-color: #ececec"/>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Employee ID 
               
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtEmpId" id="txtEmpId" style="background-color: #ececec" readonly="readonly"   maxlength="6" size="6"/>
                                
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Designation</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="designation" id="designation" size="40"
                       readonly="readonly" style="background-color: #ececec"/>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">DOB</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtDOB" id="txtDOB" maxlength="10"
                       readonly="readonly" size="10"
                       style="background-color: #ececec"/><!--<img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.Hrm_TransJoinForm.txtDOB);" alt="Show Calendar" ></img>-->
              </div>
            </td>
          </tr>
         
      
        <tr class="tdH">
            <td colspan="2">
              <div align="left">Relieval Details</div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Reason For Relieval
              <font color="#ff2121">*</font>
              </div>
              
            </td>
            <td>            
              <div align="left">                 
                 <input type="radio" name="radRlv" id="radioRetired" value="R" checked="checked" />Super-Annuation
                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                 <input type="radio" name="radRlv" id="radioVrs" value="V"  />VRS
                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                 <input type="radio" name="radRlv" id="radioDied" value="D"  />Death
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Relieval  Date</div>
            </td>
            <td colspan="2">
              <div  align="left">
                <input type="text" class="cal" name="date2" id="date2" 
                			maxlength="10" size="10"
                            onfocus="javascript:vDateType='3';"
                            onblur="return checkdt(this);"
                            onkeypress="return calins(event,this);" />
                 
                <img src="../../../../../../images/calendr3.gif" id="imgDate2" onclick="showCalendarControl(document.getElementById('date2'));"  alt="Show Calendar"></img>
                
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Date upto interest to be calculated</div>
            </td>
            <td colspan="2">
              <div  align="left">
                
               <select id="listYear" onclick="return nullCheckRelieveDate();" onfocus="call('LoadYear');"  onchange="call('LoadMonth');" >
                	<option>Select-Year</option>
                </select>
                <select id="listMonth" onclick="return nullCheckRelieveDate();">
                	<option>Select-Month</option>
                </select>  
                
              </div>
            </td>
          </tr>
              <tr class="tdH">
            <td colspan="2">
              <div align="left">Authorisation Note Details</div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Authorisation Serial Number               
              </div>
            </td>
            <td>
              <div align="left">
                 
                <select id="listAuth" name="listAuth" onchange="callFromAuth();">
                	<option value="0">Select-Auth-Serial-No</option>
                </select>
                <input type="hidden" id="hdnSerialNo" value="12">
                                
              </div>
            </td>
          </tr>
           <tr class="table">
            <td>
              <div align="left">
                Financial Year               
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtFinYear" id="txtFinanceYear" style="background-color: #ececec" readonly="readonly"   maxlength="12" size="12"/>
                                
              </div>
            </td>
          </tr>
           
          <tr class="table">
            <td>
              <div align="left">Proceeding No:
              <font color="#ff2121">*</font>
              </div>
              
            </td>
            <td>            
              <div align="left">
                 <input type="text" name="txtMDProc" id="txtMDProc"  maxlength="40"   size="40"/>
              </div>
            </td>
          </tr>
           
           <tr class="table">
            <td>
              <div align="left">Proceeding  Date</div>
            </td>
            <td colspan="2">
              <div align="left">
                <input type="text" class="cal" name="date1" id="date1" 
                			maxlength="10" size="10"
                            onfocus="javascript:vDateType='3';"
                            onblur="return checkdt(this);"
                            onkeypress="return calins(event,this);"  />
                 
                <img src="../../../../../../images/calendr3.gif"  id="imgDate1" onclick="showCalendarControl(document.getElementById('date1'));"  alt="Show Calendar"></img>
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
          <tr>
          	<td width="60%"></td>
          	<td  width="20%"><div align="left">G.P.F. (REGULAR)</div></td>
          	<td width="20%"><div align="left">G.P.F. (IMPOUNDED)</div></td>
          </tr>
          <tr>
          	<td><div align="left">1. C.B. as per  <span id="spanFin" > </span><%= ""%> A/C slip</div></td>
          	<td>
          		<div align="left">
					<input type="text"  name="txt1GpfReg" id="txt1GpfReg"  style="background-color: #ececec" readonly="readonly"   maxlength="20" size="20"/>
                
				</div>
			</td>
          	<td>
          		<div align="left">          		
					<input type="text" name="txt1GpfIm" id="txt1GpfIm"  style="background-color: #ececec" readonly="readonly"   maxlength="20" size="20"/>
                
				</div>
			</td>
          </tr>
          <tr>
          	<td><div align="left">2. Add Subsequent Details</div>  </td>
          	<td>
          		<div align="left">
					<input type="text" name="txt2GpfReg" id="txt2GpfReg"  style="background-color: #ececec" readonly="readonly"   maxlength="20" size="20"/>
                
				</div>
			</td>
          	<td>
          		<div align="left">
					<input type="text" name="txt2GpfIm" id="txt2GpfIm"  style="background-color: #ececec" readonly="readonly"   maxlength="20" size="20"/>
                
				</div>
			</td>
          </tr>
          <tr>
          	<td><div align="left">3. Interest from <span id="spanFrom" > </span> <%= ""%> to <span id="spanTo1" > </span><%="" %> </div>  </td>
          	<td>
          		<div align="left">
					<input type="text" name="txt3GpfReg" id="txt3GpfReg" style="background-color: #ececec" readonly="readonly"   maxlength="20" size="20"/>
                
				</div>
			</td>
          	<td>
          		<div align="left">
					<input type="text" name="txt3GpfIm" id="txt3GpfIm"  style="background-color: #ececec" readonly="readonly"   maxlength="20" size="20"/>
                
				</div>
			</td>
          </tr>
          <tr>
          	<td><div align="left">4. Add missing Credit/excess debit</div>  </td>
          	<td>
          		<div align="left">
					<input type="text" name="txt4GpfReg" id="txt4GpfReg"  style="background-color: #ececec" readonly="readonly"   maxlength="20" size="20"/>
                
				</div>
			</td>
          	<td>
          		<div align="left">
					<input type="text" name="txt4GpfIm" id="txt4GpfIm" style="background-color: #ececec" readonly="readonly"   maxlength="20" size="20"/>
                
				</div>
			</td>
          </tr>
          <tr>
          	<td><div align="left">5. Interest for missing credit/excess debit upto <span id="spanTo2" > </span> <%= ""%></div>  </td>
          	<td>
          		<div align="left">
					<input type="text" name="txt5GpfReg" id="txt5GpfReg"  style="background-color: #ececec" readonly="readonly"   maxlength="20" size="20"/>
                
				</div>
			</td>
          	<td>
          		<div align="left">
					<input type="text" name="txt5GpfIm" id="txt5GpfIm"  style="background-color: #ececec" readonly="readonly"   maxlength="20" size="20"/>
                
				</div>
			</td>
          </tr>
          <tr>
          	<td><div align="left">6. Subsequent withdrawls</div>  </td>
          	<td>
          		<div align="left">
					<input type="text" name="txt6GpfReg" id="txt6GpfReg"  style="background-color: #ececec" readonly="readonly"   maxlength="20" size="20"/>
                
				</div>
			</td>
          	<td>
          		<div align="left">
					<input type="text" name="txt6GpfIm" id="txt6GpfIm"  style="background-color: #ececec" readonly="readonly"   maxlength="20" size="20"/>
                
				</div>
			</td>
          </tr>
          <tr>
          	<td><div align="left">7. Excess credit/missing debit </div>  </td>
          	<td>
          		<div align="left">
					<input type="text" name="txt7GpfReg" id="txt7GpfReg"  style="background-color: #ececec" readonly="readonly"   maxlength="20" size="20"/>
                
				</div>
			</td>
          	<td>
          		<div align="left">
					<input type="text" name="txt7GpfIm" id="txt7GpfIm"  style="background-color: #ececec" readonly="readonly"   maxlength="20" size="20"/>
                
				</div>
			</td>
          </tr>
          <tr>
          	<td><div align="left">8. Interest for excess credit/mising debit upto <span id="spanTo3" > </span><%= "" %></div>  </td>
          	<td>
          		<div align="left">
					<input type="text" name="txt8GpfReg" id="txt8GpfReg"  style="background-color: #ececec" readonly="readonly"   maxlength="20" size="20"/>
                
				</div>
			</td>
          	<td>
          		<div align="left">
					<input type="text" name="txt8GpfIm" id="txt8GpfIm"  style="background-color: #ececec" readonly="readonly"   maxlength="20" size="20"/>
                
				</div>
			</td>
          </tr>
          <tr>
          	<td><div align="left">9.Amount already authorised</div>  </td>
          	<td>
          		<div align="left">
					<input type="text" name="txt9GpfReg" id="txt9GpfReg"  style="background-color: #ececec" readonly="readonly"   maxlength="20" size="20"/>
                
				</div>
			</td>
          	<td>
          		<div align="left">
					<input type="text" name="txt9GpfIm" id="txt9GpfIm"  style="background-color: #ececec" readonly="readonly"   maxlength="20" size="20"/>
                
				</div>
			</td>
          </tr>
          <tr>
          	<td><div align="left"><strong> Balance To Be Authorised</strong></div>  </td>
          	<td>
          		<div align="left">          		
					<input type="text" name="txt10GpfReg" id="txt10GpfReg"  style="background-color: #ececec" readonly="readonly"   maxlength="20" size="20"/>                
				</div>
			</td>
          	<td>
          		<div align="left">
					<input type="text" name="txt10GpfIm" id="txt10GpfIm"  style="background-color: #ececec" readonly="readonly"   maxlength="20" size="20"/>
                </div>
			</td>
          </tr>
          
        </table>
        
         <table cellspacing="3" cellpadding="2" border="1" width="100%">
	         <tr class="table">
	            <td>
	              <div align="left">Select Report Type</div>  
	            </td>
	            <td>
	              <div align="left">
	                <select id="listReporttype" name="listReporttype">
	                	<option value="PDF">PDF</option>
	                	<option value="HTML">HTML</option>
	                	<option value="EXCEL">EXCEL</option>
	                	<option value="EXCEL">TXT</option>
	                </select>
	              </div>
	            </td>
	          </tr>         
         </table> 

         <table id="Exit" cellspacing="2" cellpadding="3" border="1" width="100%"
               align="center">
          <tr class="tdH">
            <td >
              <div align="center">
                
             	<input type="button" name="validateBtn" id="validateBtn" value="VALIDATE" onclick="call('CalcInterest');"/>
              	<input type="button" name="reportBtn" id="reportBtn" value="PRINT"
              		onclick="call('Report');"/>
              	<input type="button" name="clearBtn" id="clearBtn" value="CLEAR" onclick="clearData();"/>
           		<input type="button" name="CmdExit" value="EXIT" id="CmdExit"
                   onclick="Exit()" align="middle"/>
                
              </div>
            </td>
          </tr>  
               
          
        </table>
      </div>
  
    </form></body>
</html>