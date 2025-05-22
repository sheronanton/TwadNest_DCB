<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile,java.text.DateFormatSymbols"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="Cache-Control" content="No-Cache"/>
    <title>GPF_Subscription</title>
  
    <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/Hrm_TransJoinJS_New.js"></script>
   <script type="text/javascript" src="../scripts/New_Trans_Joinedit_Script_New.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/CalenderControl_ParticularMnt.js.js"></script>
    <script type="text/javascript" src="../scripts/selectOfficeAttached.js">  </script>
    <script type="text/javascript" src="../scripts/GPF_Subscription_New.js">  </script>
  
    <style type="text/css">
.cal {font-family:verdana; font-size:12px;}
</style>
  </head>
  <body id="bodyid" ><form name="Hrm_TransJoinFormpop" id="Hrm_TransJoinFormpop"  >
      <%
  
      String command=request.getParameter("command");
     int amt_arr=0;
      int updation=Integer.parseInt(request.getParameter("updation"));
     //  int amt_ref=Integer.parseInt(request.getParameter("amt_ref"));
     
     String amount_arrear=request.getParameter("amt_ref_arr");
      String emp_name=request.getParameter("emp_name_val");
if(amount_arrear!=null)
      {
       amt_arr=Integer.parseInt(amount_arrear);
      }
      else
      {
      amt_arr=0;
      }
     
      
      int officeId=Integer.parseInt(request.getParameter("officeid"));
      String acMonth=request.getParameter("acmonth"); 
      String acYear=request.getParameter("acyear"); 
      String unit=request.getParameter("unit"); 
      String gpfNo=request.getParameter("gpfno"); 
   String aarr_year=request.getParameter("aarr_year"); 
      
     
      int arr_ref_yr=0;
      if(request.getParameter("aarr_year1")!=null)
          arr_ref_yr=Integer.parseInt(aarr_year);
      
      
      
     String aarr_Month=request.getParameter("aarr_month"); 
      
      int arr_ref_mth=0;
      String arr_months[]=new String[]{"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
      for(int i=1;i<=12;i++)
      
    	 if(arr_months[i-1].equalsIgnoreCase(aarr_Month))   
    			 
    		 arr_ref_mth=i;
    		
    		 
    	 
      
       
       
      String relMonth=request.getParameter("relmonth"); 
      
      int rel_month=0;
      String rel_months[]=new String[]{"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
      for(int i=1;i<=12;i++)
    	 if(rel_months[i-1].equalsIgnoreCase(relMonth))    		 
    		 rel_month=i;
    		 
    		 
  System.out.println("rel_month--->"+rel_month);
  
  
  
      String relYear=request.getParameter("relyear"); 
      int rel_year=0;
      if(request.getParameter("relyear")!=null)
          rel_year=Integer.parseInt(relYear);
      String subAmount=request.getParameter("subamount"); 
      String arrAmount=request.getParameter("arramount"); 
      String refAmount=request.getParameter("refamount"); 
      String refTot=request.getParameter("reftot"); 
      String refIns=request.getParameter("refins");
      String arrTot=request.getParameter("arrtot"); 
      String arrins=request.getParameter("arrins");
      String remark=request.getParameter("remark");
      System.out.println("rel_month..."+rel_month);
      
      String subSeq=request.getParameter("sub_no");
      String fileType=request.getParameter("filetype");
      int sub_slno=0;
      if(request.getParameter("subscribe_slno")!=null)
        sub_slno=Integer.parseInt(request.getParameter("subscribe_slno"));
      else
    	  sub_slno=0;
        
      if(subAmount==null){
    	  subAmount="";
      }
      
      
      if(remark==null){
    	  remark="";
      }
      
      if(gpfNo==null){
    	  gpfNo="";
      }
      if(subSeq==null){
    	  subSeq="";
      }
   if(refAmount==null){
    	  refAmount="0";
      }
     if(refTot==null){
    	  refTot="0";
      }
      
      if(refIns==null){
    	  refIns="0";
      }
      
      
      
      if(arrAmount==null){
    	  arrAmount="0";
      }
     if(arrTot==null){
    	  arrTot="0";
      }
      
      if(arrins==null){
    	  arrins="0";
      }
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    
    
     Connection connection=null;

  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
    
   try
  {
  
             ResourceBundle rb=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String strDriver=rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rb.getString("Config.DSN");
            String strhostname=rb.getString("Config.HOST_NAME");
            String strportno=rb.getString("Config.PORT_NUMBER");
            String strsid=rb.getString("Config.SID");
            String strdbusername=rb.getString("Config.USER_NAME");
            String strdbpassword=rb.getString("Config.PASSWORD");

      //      ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
       ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 

             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  %>
      <!-- OFFICE DETAILS -->
      <% 
    HttpSession session=request.getSession(false);
    UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
    System.out.println("user id::"+empProfile.getEmployeeId());
    int empid=empProfile.getEmployeeId();
    int  oid=0,unit_id=0,i=0;
    int acc_for[]=new int[5];
    String oname="",oadd1="",oadd2="",ocity="",odist="",olid="",owid="",olevelname="",oldcode="";
    String olname="",olevelid=""; 
    String ownature="";
    ArrayList unit_name=new ArrayList();
    Calendar cal=Calendar.getInstance();
   // int year=cal.get(Calendar.YEAR);
   // int month = cal.get(Calendar.MONTH) + 1;
    int year=Integer.parseInt(request.getParameter("acyear"));
    int month =Integer.parseInt(request.getParameter("acmonth"));
 
 
 int years=Integer.parseInt(request.getParameter("rel_year"));
    int months =Integer.parseInt(request.getParameter("rel_month"));
 
 
    System.out.println("R month & Year=========>: "+months + " "+years );
  
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
            ps=con.prepareStatement("select a.OFFICE_NAME,a.OFFICE_ADDRESS1,a.OFFICE_ADDRESS2,a.DISTRICT_CODE,a.CITY_TOWN_NAME,a.OFFICE_LEVEL_ID,a.PRIMARY_WORK_ID,b.DISTRICT_NAME,c.OFFICE_LEVEL_NAME from COM_MST_OFFICES a "+
            " left outer join com_mst_districts b on b.DISTRICT_CODE= a.DISTRICT_CODE "+
            " inner join com_mst_office_levels c on  c.office_level_id=a.office_level_id "+
            " where OFFICE_ID=?");
            ps.setInt(1,oid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oname=results.getString("OFFICE_NAME");
                    oadd1=results.getString("OFFICE_ADDRESS1");
                    oadd2=results.getString("OFFICE_ADDRESS2");
                    ocity=results.getString("CITY_TOWN_NAME");
                    odist=results.getString("DISTRICT_NAME");
                    olevelname=results.getString("OFFICE_LEVEL_NAME");
                    olevelid=results.getString("OFFICE_LEVEL_ID");
                  }
        }
        catch(SQLException e)
    {
        System.out.println("/////////"+e);
    }
        
       /* try{
                 ps=con.prepareStatement("select accounting_unit_name from fas_mst_acct_units where accounting_unit_office_id=?" );
                 ps.setInt(1,oid);
                 results=ps.executeQuery();
                 if(results.next())
                 {
                 ps=con.prepareStatement("select accounting_unit_name from fas_mst_acct_units where accounting_unit_office_id=?" );
                 ps.setInt(1,oid);
                 results=ps.executeQuery();
                 while(results.next()) 
                 {
                    
                    unit_name.add(results.getString(1));
                                   
                     
                 }
                 }
                 else
                 {
                     out.println("<script language='javascript'>");
                     out.println("alert('You Dont Have a privilage to access this page')");
                     out.println("window.close()");
                     out.println("</script>");
                 }
                
                
            results.close();
            ps.close();
               
            }
    catch(SQLException e)
    {
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+e);
    } */
   
    session.setAttribute("acmonth",acMonth);
    session.setAttribute("acyear",acYear);
    session.setAttribute("unit",unit);
   %>
      <div align="center">
        <table cellspacing="3" cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <strong>GPF Subscription Details</strong>
              </div>
            </td>
          </tr>
          <tr class="tdH">
          <td colspan="2">
              <div align="center">
                <strong>Employe</strong>
              </div>
            </td>
          </tr>
           <tr class="table">
            <td>
              <div align="left">GPF NO<font color="#ff2121">*</font></div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtGpfNo" id="txtGpfNo" maxlength="5" value="<%=gpfNo%>" onblur="call('get','s')" onkeypress="return  numbersonly1(event,this)" size="7"/>
                <img src="../../../../../images/c-lovi.gif" width="20"
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
                <input type="text" name="comEmpId" id="comEmpId" size="40" readonly="readonly" style="background-color: #ececec"/>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Designation</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="designation" id="designation" size="40" readonly="readonly" style="background-color: #ececec"/>              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">DOB</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtDOB" id="txtDOB" maxlength="10" readonly="readonly" size="10" style="background-color: #ececec"/>
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
                <input type="text" name="txtEmpId" id="txtEmpId" style="background-color: #ececec"  maxlength="6" size="6" />
              
                
              </div>
            </td>
          </tr>
          <tr class="tdH">
            <td colspan="2">
              <div align="left">Subscription and Refund Details</div>
            </td>
          </tr>
          
     <!--      <tr class="table">
          <td>
              <div align="left">Serial No</div>
            </td>
            <td>
              <div align="left">
             
                <input type="text" name="sub_no" id="sub_no" maxlength="5" value="<%=subSeq %>" size="6" disabled="disabled" />(System Generated)
              </div>
            </td> 
           
          </tr> -->
          
        
          <tr class="table">
            <td>
              <div align="left">Relative Month &amp; Year</div>
            </td>
            <td>
              <div align="left">
                Year : 
                <select name="rel_year" id="rel_year" onchange="getRelMonth('<%=year%>','<%=month%>')" >
                <option value="0">Select</option>
                 <option value="<%=year %>" > <%=year %>     </option>
                 <option value="<%=(year-1)%>">   <%=(year-1)%>  </option>
                 <option value="<%=(year-2)%>">   <%=(year-2)%>  </option>
                 <option value="<%=(year-3)%>">   <%=(year-3)%>  </option>
                 <option value="<%=(year-4)%>">   <%=(year-4)%>  </option>
                 <option value="<%=(year-5)%>">   <%=(year-5)%>  </option>
                  <option value="<%=(year-6)%>">   <%=(year-6)%>  </option>
                 <option value="<%=(year-7)%>">   <%=(year-7)%>  </option>
                 <option value="<%=(year-8)%>">   <%=(year-8)%>  </option>
                 <option value="<%=(year-9)%>">   <%=(year-9)%>  </option>
                 <option value="<%=(year-10)%>">   <%=(year-10)%>  </option>
                </select>
                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                Month : 
                <select name="rel_month" id="rel_month" >
                 <option value="0">Select</option>
                <%
                String[] shortMonths = new DateFormatSymbols().getShortMonths();
                if(rel_year<year){
                for(int j=1;j<=12;j++){
                %>
                <%
                if(j==rel_month ){%>
                <option value="<%=j %>" selected="selected"><%=shortMonths[rel_month-1].toUpperCase() %></option>
                <%}
                 else{
                %>
                  <option value="<%=j %>"><%=shortMonths[j-1].toUpperCase() %></option>
                <%
                }
               }
                }
                else{
                	for(int j=1;j<=month;j++){
                        %>
                        <%
                        
                        if(j==rel_month ){%>
                        <option value="<%=j %>" selected="selected"><%=shortMonths[j-1].toUpperCase() %></option>
                        <%
                        
                            System.out.println("i1..."+j);
                        }
                        else{
                        %>
                          <option value="<%=j %>"><%=shortMonths[j-1].toUpperCase() %></option>
                        <%
                        System.out.println("i1..in else."+j);
                        }}
                }
                %>
                </select>
              </div>
            </td>
          </tr>
          <!---
          -->
          
          <tr class="table">
            <td>
              <div align="left" >Subscription Amount</div>
            </td>
            <td>
             <div align="left">
                <input type="text" name="amount" id="amount" maxlength="10" value="<%=subAmount %>" onkeypress="return filter_real(event,this,7,2)" />
             
              </div>
            </td>
          </tr>
          
          
          
          
         <tr class="table">
            <td>
              <div align="left">Category  </div>
            </td>
            <td>
              <div align="left" >                                                           
                 <input type="checkbox" name="trans" id="trans1" value="arr" onclick="check()"/> Arrear
                 </div>
                   <table><tr><td class="table" id="arrid" style="display:none">
              <div align="left" > <table cellspacing="3" cellpadding="2" border="1" >   
           
             
              <tr>
              	<td>Arrear Amount&nbsp;&nbsp;&nbsp;(RS)</td>
              	<td><input type="text" name="arramt" id="arramt" value=<%=arrAmount %> maxlength="10"  onkeypress="return filter_real(event,this,5,2)" /></td>
              </tr>
              
              <tr>
              	<td>Arrear Total Installments &nbsp;&nbsp;</td>
              	<td> <input type="text" name="arr_total" id="arr_total" value=<%=arrTot %> maxlength="10"  onkeypress="return  numbersonly1(event,this)" onchange="checkTotal1();" /></td>
              </tr>
              <tr>
              	<td>Arrear Installment No &nbsp;&nbsp;&nbsp;</td>
              	<td> <input type="text" name="arr_no" id="arr_no" value=<%=arrins %> maxlength="10"  onkeypress="return  numbersonly1(event,this)" onchange="checkTotal1();"/></td>
              </tr>
              </table></div></td></tr></table></td>
             </tr>
               
             
             
                <tr ><td></td><td><div align="left" ><input type="checkbox" name="trans" id="trans" value="ref" onclick="check()" />Refund</div><table><tr> <td class="table" id="refid" style="display:none">
                
              <div align="left" id="refund_div" > 
               <table cellspacing="3" cellpadding="2" border="1" >
              
              <tr>
              	<td>Refund Amount&nbsp;&nbsp;&nbsp;(RS)</td>
              	<td><input type="text" name="ref_amount" id="ref_amount" value=<%=refAmount %> maxlength="10"  onkeypress="return filter_real(event,this,5,2)" /></td>
              </tr>
              <tr>
              	<td>Refund Total Installments &nbsp;&nbsp;</td>
              	<td> <input type="text" name="ref_total" id="ref_total" value=<%=refTot %> maxlength="10"  onkeypress="return  numbersonly1(event,this)" onchange="checkTotal1();" /></td>
              </tr>
              <tr>
              	<td>Refund Installment No &nbsp;&nbsp;&nbsp;</td>
              	<td> <input type="text" name="ref_no" id="ref_no" value=<%=refIns %> maxlength="10"  onkeypress="return  numbersonly1(event,this)" onchange="checkTotal1();"/></td>
              </tr>
              </table></div></td></tr></table></td>
             </tr>
               
                
            
           
        <!--  <tr class="table">
            <td>
              <div align="left">Arrear Recovery(If Exist) &nbsp;&nbsp;&nbsp;&nbsp;   <input type="checkbox" name="arrear" id="arrear" onClick="showArrear();"/> </div>
            </td>
            <td>
              <div align="left" id="arrear_div" > 
               <table cellspacing="3" cellpadding="2" border="1" >
              <tr><td>Recovery Amount</td><td> <input type="text" name="rec_amount" id="rec_amount" maxlength="10" onkeypress="return filter_real(event,this,5,2)" /></td>
               <tr><td>Recovery Total Installments</td><td> <input type="text" name="rec_total" id="rec_total" maxlength="10" onchange="checkTotal1();"  onkeypress="return  numbersonly1(event,this)"/></td>
                <tr><td>Recovery Installment No </td><td> <input type="text" name="rec_no" id="rec_no" maxlength="10" onchange="checkTotal1();"  onkeypress="return  numbersonly1(event,this)"/></td>
               </table>
              </div>
            </td>
          </tr>-->
          <tr></tr>
          
          <tr class="table">
            <td>
              <div align="left"> </div>
            </td>
            <td>
              <div align="left" >                                                           
                 <input type="checkbox" name="trans" id="trans2" value="arr_ref" onclick="check()"/> Refund Arrear
                 </div>
                   <table><tr><td class="table" id="arr_ref_id" style="display:none">
              <div align="left" > <table cellspacing="3" cellpadding="2" border="1" >   
           
             
              <tr>
              	<td>Arrear Refund Amount&nbsp;&nbsp;&nbsp;(RS)</td>
              	<td><input type="text" name="arr_ref_amt" id="arr_ref_amt" value=<%=amt_arr %> maxlength="10"  onkeypress="return filter_real(event,this,5,2)" /></td>
              </tr>
              
              <tr>
              	<td>Arrear Refund Relative Month &amp; Year</td>
              	<td>Year
              	<select name="arr_ref_yr" id="arr_ref_yr" onchange="getRelMonth('<%=year%>','<%=month%>')" >
                <option value="0">Select</option>
                 <option value="<%=year %>" > <%=year %>     </option>
                 <option value="<%=(year-1)%>">   <%=(year-1)%>  </option>
                 <option value="<%=(year-2)%>">   <%=(year-2)%>  </option>
                 <option value="<%=(year-3)%>">   <%=(year-3)%>  </option>
                 <option value="<%=(year-4)%>">   <%=(year-4)%>  </option>
                 <option value="<%=(year-5)%>">   <%=(year-5)%>  </option>
                  <option value="<%=(year-6)%>">   <%=(year-6)%>  </option>
                 <option value="<%=(year-7)%>">   <%=(year-7)%>  </option>
                 <option value="<%=(year-8)%>">   <%=(year-8)%>  </option>
                 <option value="<%=(year-9)%>">   <%=(year-9)%>  </option>
                 <option value="<%=(year-10)%>">   <%=(year-10)%>  </option>
                </select>
                 Month :
                 <select name="arr_ref_mth" id="arr_ref_mth" >
                 <option value="0">Select</option>
                <%
                 shortMonths = new DateFormatSymbols().getShortMonths();
                if(arr_ref_yr<year){
                for(int j=1;j<=12;j++){
                %>
                <%
                if(j==arr_ref_mth ){%>
                <option value="<%=j %>" selected="selected"><%=shortMonths[arr_ref_mth-1].toUpperCase() %></option>
                <%}
                 else{
                %>
                  <option value="<%=j %>"><%=shortMonths[j-1].toUpperCase() %></option>
                <%
                }
               }
                }
                else{
                	for(int j=1;j<=month;j++){
                        %>
                        <%
                        
                        if(j==arr_ref_mth ){%>
                        <option value="<%=j %>" selected="selected"><%=shortMonths[j-1].toUpperCase() %></option>
                        <%
                        
                            System.out.println("i1..."+j);
                        }
                        else{
                        %>
                          <option value="<%=j %>"><%=shortMonths[j-1].toUpperCase() %></option>
                        <%
                        System.out.println("i1..in else."+j);
                        }}
                }
                %>
                </select>
                </td>
               
              </tr>
             
              </table></div></td></tr></table></td>
             </tr>
               
          
          
          <tr class="table">
            <td>
              <div align="left">Remarks</div>
            </td>
            <td>
              <div align="left">
                <textarea id="remarks" name="remarks" rows="3" cols="30"
                          style="algin:left;"><%=remark %></textarea>
              </div>
            </td>
          </tr>
         
        </table>
         <input type="hidden" id="officeid" value="<%=officeId%>" >
         <input type="hidden" id="acmonth" value="<%=acMonth%>" >
         <input type="hidden" id="acyear" value="<%=acYear%>" >
         <input type="hidden" id="oldcode" value="<%=unit%>" >
         <input type="hidden" id="unit" value="<%=unit%>" >
         <input type="hidden" id="filetype" value="<%=fileType%>" >  
         <input type="hidden" name="subscribe_slno" id="subscribe_slno" value="<%=sub_slno %>" />
     	<input type="hidden" name="sub_no" id="sub_no"  value="<%=subSeq %>"  />    
        
        
                 <table id="Exit" cellspacing="2" cellpadding="3" border="1" width="100%"
               align="center">
          <tr>
            <td class="tdH">
              <div align="center">
               <input type="button" name="add" id="add" value="ADD"
                       onclick="call('Add','s')"/>
                       
               <input type="button" name="update" id="update"
                       onclick="call('Update','s');" value="UPDATE"/>         
                       
                 <input type="button" name="delete1" id="delete1" value="DELETE"
                       onclick="call('Delete','s')"/>
                       
                <input type="button" name="CmdExit" value="EXIT" id="CmdExit"
                       onclick="Exit()" align="middle"/>
              </div>
            </td>
          </tr>
        </table>
         
     
        <script type="text/javascript">

     /*   document.Hrm_TransJoinFormpop.rel_year.options[5].selected='selected';
        v=new Date();
   	 mn=v.getMonth();
   	 yr=v.getFullYear();
  	document.Hrm_TransJoinFormpop.rel_month.options[mn].selected='selected';
lno=mn+1;


   	for(var i=lno;i<12;i++)
    {    
 
   		document.Hrm_TransJoinFormpop.rel_month.options[i].disabled='disabled';
       
    }
           
   //	document.Hrm_TransJoinFormpop.rel_month.options[mn].selected='selected';
*/

		if( document.getElementById("txtGpfNo").value!=""){
			call('get','s');
			
			 var len=document.Hrm_TransJoinFormpop.rel_year.length;
			for(i=0;i<len;i++)
			{
				if(document.Hrm_TransJoinFormpop.rel_year.options[i].value=='<%=relYear%>')
				{
					document.Hrm_TransJoinFormpop.rel_year.options[i].selected='selected';
					
					}	
			}

			

			  var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
			  var k;
			for(i=0;i<12;i++)
			{
				
				if(month[i]=='<%=relMonth%>')
	            {
	           
	                k=i;
	            
	            }

					
			}
			
			var len=document.Hrm_TransJoinFormpop.arr_ref_yr.length;
			for(i=0;i<len;i++)
			{
				if(document.Hrm_TransJoinFormpop.arr_ref_yr.options[i].value=='<%=aarr_year%>')
				{
					document.Hrm_TransJoinFormpop.arr_ref_yr.options[i].selected='selected';
					
					}	
			}

			
			
			 var months=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
			  var l;
			for(i=0;i<12;i++)
			{
				//alert(aarr_month);
				if(months[i]=='<%=aarr_Month%>')
	            {
	         
	                l=i;
	           
	            }

					
			}
			
			//document.Hrm_TransJoinFormpop.rel_month.options[k].selected='selected';

            if(<%=amt_arr%>!=0){
				document.Hrm_TransJoinFormpop.trans[2].checked=true;
				document.getElementById("arr_ref_id").style.display = 'block';
					
			}

			
			 if(<%=arrAmount%>!=0){
				document.Hrm_TransJoinFormpop.trans[0].checked=true;
				document.Hrm_TransJoinFormpop.arramt.value=<%=arrAmount%>;
				
				//document.Hrm_TransJoinFormpop.subid.style.display='block';
				document.getElementById("arrid").style.display = 'block';
					
			}
			 if(<%=refAmount%>!=0){
				document.Hrm_TransJoinFormpop.trans[1].checked=true;
				document.Hrm_TransJoinFormpop.ref_amount.value=<%=refAmount%>;
				document.Hrm_TransJoinFormpop.ref_total.value=<%=refTot%>;
				document.Hrm_TransJoinFormpop.ref_no.value=<%=refIns%>;
				
				//document.Hrm_TransJoinFormpop.subid.style.display='block';
				document.getElementById("refid").style.display = 'block';
					
			}
			 document.Hrm_TransJoinFormpop.txtGpfNo.disabled=true; 
			document.Hrm_TransJoinFormpop.add.disabled=true;
			document.Hrm_TransJoinFormpop.update.disabled=false;
			document.Hrm_TransJoinFormpop.delete1.disabled=false;
			/*document.Hrm_TransJoinFormpop.rel_year.disabled=true;
			document.Hrm_TransJoinFormpop.rel_month.disabled=true;*/
			
if(<%=updation%>==0){
	document.Hrm_TransJoinFormpop.add.disabled=true;
	document.Hrm_TransJoinFormpop.update.disabled=true;
	document.Hrm_TransJoinFormpop.delete1.disabled=false;
}
		
			
			}
		else{
			
				document.Hrm_TransJoinFormpop.add.disabled=false;
				document.Hrm_TransJoinFormpop.update.disabled=true;
				document.Hrm_TransJoinFormpop.delete1.disabled=true;
				/*document.Hrm_TransJoinFormpop.rel_year.disabled=false;
				document.Hrm_TransJoinFormpop.rel_month.disabled=false;*/
				}
if(<%=updation%>==0){
	document.Hrm_TransJoinFormpop.add.disabled=true;
	document.Hrm_TransJoinFormpop.update.disabled=true;
	document.Hrm_TransJoinFormpop.delete1.disabled=false;
}
        
        </script>
        
        
      </div>
    </form></body>
</html>                                                                                                                                                                