<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>DCB Journal Creation  | TWAD Nest - Phase II </title>
	<link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript" src="../scripts/Dcb_Journal_Creation_js.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Cheque_Number_Check_forPAY.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
     <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>
     	
 	<script type="text/javascript" language="javascript">
	    function loadDate()
        {
               // alert("asdf");
        	 var today= new Date(); 
                 var day=today.getDate();
                 var month=today.getMonth();
                 month=month+1;
                 
                 if(day<=9 && day>=1)
                 day="0"+day;
                 if(month<=9 && month>=1)
                 month="0"+month;
                 var year=today.getYear(); 
                 if(year < 1900) year += 1900;                
                 document.Dcb_Journal.txtCrea_date.value=day+"/"+month+"/"+year;    
                 document.Dcb_Journal.cashbookyear.value=year;
                 
                  var month1;
                  if(month==01){month1="Jan"}
                  else if(month==02){month1="Feb"}
                  else if(month==03){month1="Mar"}
                  else if(month==04){month1="Apr"}
                  else if(month==05){month1="May"}
                  else if(month==06){month1="Jun"}
                  else if(month==07){month1="Jul"}
                  else if(month==08){month1="Aug"}
                  else if(month==09){month1="Sep"}
                  else if(month==10){month1="Oct"}
                  else if(month==11){month1="Nov"}
                  else if(month==12){month1="Dec"}
                  document.Dcb_Journal.cashbookmonth.value=month1;
        }
       
</script>
</head>
<%
  
	    Connection con=null;
	    ResultSet rs=null,rs2=null;
	    Statement st=null;
	    PreparedStatement ps=null;
	    try
	    {
	      
	             ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
	             String ConnectionString="";
	    
	             String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
	             String strdsn=rs1.getString("Config.DSN");
	             String strhostname=rs1.getString("Config.HOST_NAME");
	             String strportno=rs1.getString("Config.PORT_NUMBER");
	             String strsid=rs1.getString("Config.SID");
	             String strdbusername=rs1.getString("Config.USER_NAME");
	             String strdbpassword=rs1.getString("Config.PASSWORD");
	    
	         //    ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
	          ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
	    
	             Class.forName(strDriver.trim());
	             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	    }
	    catch(Exception e)
	    {
	        	 System.out.println("Exception in connection...."+e);
	    }
		      
  %>
<body onload="LoadAccountingUnitID('LIST_ALL_UNITS');loadDate();" bgcolor="rgb(255,255,225)"> 
<table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">DCB Journal</font>
          </div>
        </td>
      </tr>
    </table>
 <form name="Dcb_Journal" id="Dcb_Journal" method="post" action="../../../../../Dcb_Journal_Creation?Command=Add" onsubmit="return checkRadio();" >
 <!-- <form name="Dcb_Journal" id="Dcb_Journal" method="post" action="../../../../../Dcb_Journal_Creation?Command=Add" onsubmit="return checkRadio();" >  -->
    <div align="center">
      <table cellspacing="1" cellpadding="2" border="0" width="100%">
             <tr class="table">
                    <td>
                      <div align="left" >
                              Accounting Unit Code  <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);">        
                         </select>
                      </div>
                    </td>
              </tr>
              
              <tr class="table">
                    <td>
                      <div align="left">
                        Accounting For Office Code <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <select size="1" name="cmbOffice_code" id="cmbOffice_code" >                          
                        </select>
                      </div>
                    </td>
              </tr>
              
               <tr class="table">
                    <td>
                      <div align="left">
                        Date
                        <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                      
                      
                       <input type="text" name="txtCrea_date" id="txtCrea_date" 
                               maxlength="10" size="11"
                               onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);"
                               onblur="return call_date(this);loadDate();"
                               onchange="loadDate();"/>
                         
                        <img src="../../../../../images/calendr3.gif"
                             onclick="showCalendarControl(document.Dcb_Journal.txtCrea_date,1);"
                             alt="Show Calendar"></img>  
                                            
                      </div> 
                    </td>
              </tr>
              <tr class="table">
	              	<td>
	                      <div align="left">
	                        Cash Book Year
	                        <font color="#ff2121">*</font>
	                      </div>
	                </td>
	                <td align="left">
	               		 <input type="text" name="cashbookyear" id="cashbookyear" readonly="readonly"/>
	                </td>
              </tr>
              <tr class="table">
	              	<td>
	                      <div align="left">
	                        Cash Book Month
	                        <font color="#ff2121">*</font>
	                      </div>
	                </td>
                        <td align="left">
                          <input type="text" name="cashbookmonth" id="cashbookmonth" readonly="readonly"/>
                        </td>
                  </tr>
            </table>
    </div>
    <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">   
                <input type="button" name="butGo" id="butGo" value="GO" onclick="callGo();"/>                     
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="javascript:self.close();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    <div id="grid" style="display:block">
            <table id="mytable" cellspacing="3" cellpadding="2"
                   border="0" width="100%">
              <tr class="table" >
                   
                    <th align="left">Scheme Type</th>
                    <th align="left">Account Head Code</th>
                    <th align="left">SL Type</th>
                    <th align="left">SL Code</th>
                    <th align="left">Total CR Amount</th>  
                    <th align="left">Particulars</th>
                    <th align="left">Show Details?</th>                       
              </tr>
              <tbody id="grid_body" class="table" align="left" >
              </tbody>
            </table>
    </div>
     <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">   
                    <input type="submit" name="butGo" id="butGo" value="Submit" />                     
              </div>
            </td>
          </tr>
        </table>
      </div>
  </form>

</body>
</html>