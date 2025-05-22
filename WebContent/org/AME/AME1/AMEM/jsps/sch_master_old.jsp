<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ page import="java.lang.*,java.sql.*, javax.servlet.*, Servlets.Security.classes.*,java.util.*"%>
<%@ page import="java.util.Calendar"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"></meta>
    <meta http-equiv="cache-control" content="no-cache"/>
    <link href="css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <title>SCHEME MASTER</title>
   <link href="../../css/pmsSchemeStyle.css" rel="stylesheet" media="screen"/>
<link href="../../Calender/SchCalendarControl.css" rel="stylesheet" media="screen"/>

<script language="javascript" src="../scripts/CalendarControl_new.js"  type="text/javascript"></script>
    <script type="text/javascript" src="../scripts/sch_master.js"></script>
     <script type="text/javascript" src="../scripts/common_js.js"></script>
      <script type="text/javascript" src="../scripts/masterbeneficiary.js"></script>
    <script type="text/javascript"> 
    function isNumberKey_doc(evt,item,len)
    {
        var chrcode = (evt.which) ? evt.which : evt.keyCode;       
        if (chrcode > 31 && chrcode !=47 && (chrcode < 48 || chrcode > 57 ) ){
			return false;
        }
        else if(item.value.length < len){
        
            return  true;
        }        
   		return false;
   }
   function ischarValid(evt,item,len)
   {
         var chrcode = (evt.which) ? evt.which : evt.keyCode
                 if (chrcode!=46 && (chrcode!=08) && (chrcode!=09)&& (chrcode!=32) && (chrcode!=40) && (chrcode!=41)&& (chrcode!=38)
                         
                		 && !(chrcode>=48 && chrcode<=57)&& (chrcode!=45) && (chrcode!=95) && (chrcode < 97 || chrcode > 122) && (chrcode < 65 || chrcode > 90))
                 {
                         return false;    
                 }
                 else if(item.value.length < len)
                 {
                     return  true;
                     
                 
                 }
          return false;
   }
  function upload()
  {

    
	  	
  	var winemp;
    var my_window;
    var wininterval;
   	winemp= window.open("ImageLoad.jsp","viewd","status=1,height=500,width=600,resizable=YES, scrollbars=yes");
  	winemp.moveTo(250,250);
  	winemp.focus();	
  	
  }
  function doParentTieup(val,v1,v2,v3) {
	   	var ar=val;
	   	var ar1=v1;
	   	var ar2=v2;
		var ar3=v3;
	   	document.sch_master.sch_name.value=(ar.concat("-","(","Panchayat"," ",ar1, " of Block ",ar2,")"));
	   	document.getElementById("hab_serialno").value=v3;
	   	document.getElementById("hab_name").value=val;
  }
 </script>
 </head>
  <body >
	  <% 
	   
				   Connection con=null;
				  Statement statement=null;
				  PreparedStatement ps = null;
				  ResultSet results=null;
				  
				  try{
				      ResourceBundle rss=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
				                  String ConnectionString="";
				
				                  String strDriver=rss.getString("Config.DATA_BASE_DRIVER");
				                  String strdsn=rss.getString("Config.DSN");
				                  String strhostname=rss.getString("Config.HOST_NAME");
				                  String strportno=rss.getString("Config.PORT_NUMBER");
				                  String strsid=rss.getString("Config.SID");
				                  String strdbusername=rss.getString("Config.USER_NAME");
				                  String strdbpassword=rss.getString("Config.PASSWORD");
				
				       //           ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
				        ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
				
				                   Class.forName(strDriver.trim());
				                   con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
				      System.out.println("connected inside");
				  }
				  
				  catch(Exception e) {
				  System.out.println("the value is"+e);
				  }
	  
	        
	    %>
	<form id="sch_master" name="sch_master" method="GET" action="">
          
          
      <table cellspacing="2" cellpadding="2" border="1" width="100%"
             align="center">
      <tr   >
					<th align ="center" colspan ="5" class = "tdH">
						<font size ="5">Scheme Master</font>
					</th>
					
		</tr>       
        <tr class="table">
	          <td width="30%">
	            <font face="Times New Roman">
	              <strong style="COLOR: #0000ff;"> Category </strong>
	            </font>
	          </td>
	          <td width="70%" colspan ="4">
	           <input type="hidden" name="sch_no" id="sch_no" size="10" 
	                alt="sch_no"   />
	            <select size="1" name="cat_id" id="cat_id" tabindex="1"
	                    title="Category" style="width : 235px;" onchange=" return callServer('load',null);">
	              <option value="0">--Select--</option>
	              <%
	                    try
	                    {
	                        ps=con.prepareStatement("select  SCH_CATEGORY_ID,CATEGORY_DESC from PMS_SCH_LKP_CATEGORY order by SCH_CATEGORY_ID ");
	                        results=ps.executeQuery();
	                        while(results.next())
	                        {
	                            
	                            String st="<option value='"+results.getInt("SCH_CATEGORY_ID")+"'>"+results.getString("CATEGORY_DESC")+"</option>";
	                            out.println(st);
	                           
	                        }
	                    }
	                    catch(Exception e)
	                    {
	                        System.out.println("Exception in Select CATEGORY:"+e);
	                    }
	                      
	            %>
	            </select>
	          </td> 
	          
        </tr>
        
        <tr class="table">
	          <td width="20%">
	            <font face="Times New Roman">
	              <strong style="COLOR: #0000ff;">Year</strong>
	            </font>
	          </td>
	          <td width="80%" colspan ="4">
	            <select size="1" name="sch_year" id="sch_year" title="Year"
	                    tabindex="2" style=" width : 120px;">
	              <option value="0">--Select--</option>
	              <%
	            Calendar cal = Calendar.getInstance();
	    		int year = cal.get(Calendar.YEAR);
	    		int year1=year+3;
	    		System.out.println(year1);
	    		int year2 = year - 50;
	    		for (int i = year1; i >= year2; i--) {
	    			int z = i - 1;
	    			
	    			
	            %>
	              <option value='<%=z+"-"+i%>'>
	                <%=z+"-"+i%>
	              </option>
	              <% } %>
	            </select>
	          </td>
        </tr>
        <tr class="table">
	          <td width="20%">
	            <font face="Times New Roman">
	              <strong>Type </strong>
	            </font>
	          </td>
	          <td width="80%" colspan ="4">
	            <select size="1" name="type_id" id="type_id" tabindex="3"
	                    title="Type" style=" width : 291px;">
	              <option value="0">--Select--</option>
	              
	            </select>
	          </td>
        </tr>
        <tr class="table" id="code_row">
	          <td width="20%">
	            <font face="Times New Roman">
	              	<strong style="COLOR: #0000ff;"> Code</strong>             
	     		 </font>
	          </td>
	          <td width="80%" colspan="4">
	            <input type="text" name="sch_code" id="sch_code" alt="Code" tabindex="4"
	                   size="60" onkeypress="return ischarValid(event,this,50);" style=" width : 273px;"/>
	      
	          </td>
	          
        </tr>      
  		<tr class="table" id="name_row">
	          <td width="20%">
	            <font face="Times New Roman">
	              <strong style="COLOR: #0000ff;"> Name</strong>
	            </font>
	          </td>
	          <td width="80%" colspan="4">	            
	            <textarea type="text" name="sch_name"  tabindex="5" id="sch_name" title="Scheme" cols="50" alt="Name" ></textarea>
	          </td> 
	          
        </tr>
        <tr class="table" >
	          <td width="20%">
	            <font face="Times New Roman">
	              	<strong>Name  (Short)</strong>
	     		 </font>
	          </td>
	          <td width="80%" colspan="4">
	            <input type="text" name="sch_desc" id="sch_desc" alt="Name(Short)" tabindex="6" maxlength="50"
	                   size="50" onkeypress="return ischarValid(event,this,50);"/>
	          </td> 
        </tr>
        <tr class="table">
	          <td width="20%">
	            <font face="Times New Roman">
	              <strong>Plan </strong>
	            </font>
	          </td>
	          <td width="80%" colspan="4">
	            <select size="1" name="plan_id" id="plan_id" tabindex="7"
	                    title="Plan" style=" width : 200px;">
	              <option value="0">--Select--</option>
	              <%
	                    try
	                    {
	                       
	                        ps=con.prepareStatement("select  plan_id,plan_short_desc,PLAN_DESC from PMS_SCH_LKP_plan order by plan_id");
	                        results=ps.executeQuery();
	                        while(results.next())
	                        {
	                            
	                            String st="<option value='"+results.getInt("plan_id")+"'>"+results.getString("plan_short_desc")+"</option>";
	                            out.println(st);
	                           
	                        }
	                    }
	                    catch(Exception e)
	                    {
	                        System.out.println("Exception in Select plan:"+e);
	                    }
	                      
	            %>
	            </select>
	          </td>
        </tr>
        <tr class="table">
	          <td width="20%">
	            <font face="Times New Roman">
	              <strong>Special Coverage  </strong>
	            </font>
	          </td>
	          <td width="80%" colspan="4">
	            <select size="1" name="sch_coverage" id="sch_coverage" tabindex="8"
	                    title="Coverage" style=" width : 200px;">
	              <option value="0">--Select--</option>
	              <%
	                    try
	                    {
	                       
	                        ps=con.prepareStatement("select SCH_SPLCOVERAGE_ID,SPLCOVERAGE_DESC  from PMS_SCH_LKP_SPL_COVERAGE  order by SCH_SPLCOVERAGE_ID");
	                        results=ps.executeQuery();
	                        while(results.next())
	                        {
	                            
	                            String st="<option value='"+results.getInt("SCH_SPLCOVERAGE_ID")+"'>"+results.getString("SPLCOVERAGE_DESC")+"</option>";
	                            out.println(st);
	                           
	                        }
	                    }
	                    catch(Exception e)
	                    {
	                        System.out.println("Exception in coverage:"+e);
	                    }
	                      
	            %>
	            </select>
	          </td>
        </tr>
         <tr class="table">
	          <td width="20%">
	            <font face="Times New Roman">
	              <strong> Maintained By Twad</strong>
	            </font>
	          </td>
	          <td width="80%" colspan="4">
	            <input type="checkbox" name="maitn_twad"  id="maitn_twad" value="N" 
	          		   tabindex="9"     alt="Maintained By Twad"  />	          		    	
	          </td>
        </tr>
        <tr class="table">
	          <td width="20%">
	            <font face="Times New Roman">
	              <strong> Probable Date of Completion (DD/MM/YYYY)</strong>
	            </font>
	          </td>
	          <td width="80%" colspan="4">
	            <input type="text" name="pro_date"  id="pro_date"
	           onkeypress="return isNumberKey_doc(event,this,10)";  tabindex="10"     alt="Date of completion"  />
	                   
	           &nbsp;&nbsp;<img alt="SEARCH"  src="../../images/calendr3.gif" 
	          onclick="showCalendarControl(document.sch_master.pro_date,'pro_date');" />
	          </td>
        </tr>
        <tr class="table">
	          <td width="20%">
	            <font face="Times New Roman">
	              <strong>Estimated Cost (Rs. in Lakhs)</strong>
	            </font>
	          </td>
	          <td width="80%" colspan="4">
	            <input type="text" name="dpr_amt" id="dpr_amt" alt="Estimate Cost " tabindex="11" maxlength="16"
	                   style=" width : 15%;text-align:right" onkeypress="return isValidDecimal(event,this,12,2)"/>&nbsp;
	          </td>
	          
        </tr>
        <tr class="table" id="" >
	          <td width="20%">
	            <font face="Times New Roman">
	              <strong>Status </strong>
	            </font>
	          </td>
	          <td width="80%" colspan="4">
	            <select size="1" name="status_id" id="status_id" tabindex="12"
	                    title="Status" style=" width : 276px;">
	              <option value="0">--Select--</option>
	              <%
	                    try
	                    {
	                       
	                        ps=con.prepareStatement("select  sch_status_Id,sch_status_Desc from PMS_SCH_LKP_STATUS order by sch_status_Desc");
	                        results=ps.executeQuery();
	                        while(results.next())
	                        {
	                            
	                            String st="<option value="+results.getInt("sch_status_Id")+">"+results.getString("sch_status_Desc")+"</option>";
	                            out.println(st);
	                           
	                        }
	                    }
	                    catch(Exception e)
	                    {
	                        System.out.println("Exception in Select Sch _Status:"+e);
	                    }
	                      
	            %>
	            </select>
	          </td>
        </tr>
        
        <tr class="table">
	          <td width="20%">
	            <font face="Times New Roman">
	              <strong> Actual Date of Commencement (DD/MM/YYYY)</strong>
	            </font>
	          </td>
	          <td width="80%" colspan="4">
	            <input type="text" name="date_of_comm"  id="date_of_comm"
	           onkeypress="return isNumberKey_doc(event,this,10)";     alt="Date of commencement"  />
	                   
	           &nbsp;&nbsp;<img alt="SEARCH"  src="../../images/calendr3.gif" 
	          onclick="showCalendarControl(document.sch_master.date_of_comm,'date_of_comm');" />
	          </td>
        </tr>
        <tr class="table">
	          <td width="20%">
	            <font face="Times New Roman">
	              <strong>Actual Date of Completion (DD/MM/YYYY)</strong>
	            </font>
	          </td>
	          <td width="80%" colspan="4">
	            <input type="text" name="date_of_compl"  id="date_of_compl"
	           onkeypress="return isNumberKey_doc(event,this,10)";    alt="Date of completion"  />
	                   
	           &nbsp;&nbsp;<img alt="SEARCH"  src="../../images/calendr3.gif" 
	          onclick="showCalendarControl(document.sch_master.date_of_compl,'date_of_compl');" />
	          </td>
        </tr>
       <tr class="table">
	          <td width="20%">
	            <font face="Times New Roman">
	              <strong>Project Id</strong>
	            </font>
	          </td>
	          <td width="20%"> <input type="text" name="txtProId" id="txtProId" alt="Projct Id "  onkeypress="return isValidDecimal(event,this,15,2)" onkeyup="onKeyUpCheck();"/><input type="button" value="Verify" id="btnverify" onclick="verifyProjectId();" >
	          </td>
	          <td width="60%" >&nbsp;<span id="getResult"></span></td>
        </tr>
        <tr align="right">
        	<td colspan="5" style="color:blue;">*<b> Fields used for searching are  in blue</b> </td>
        </tr>
        <tr align="center" class="tdH">
		          <td colspan="5">
		            <input type="button" name="Add" id="Add" value="ADD"
		                   onclick="return callServer('Add',null);"/>
		            <input type="button" name="Update" id="Update" value="UPDATE"
		                   onclick="return callServer('Update',null);"  disabled="disabled" />
		            <input type="button" name="Delete" id="Delete" value="DELETE"
		                   onclick="return callServer('Delete',null);"  disabled="disabled" />
		                   <input type="button" name="list" id="list" value="SEARCH"
		                   onclick="listmethod();" style="COLOR: #0000ff;"/>
		            <input type="button" name="Cancel" id="Cancel" value="CANCEL"
		                   onclick="cancelmethod1();"/>
		            <input type="button" name="Exit" id="Exit" value="EXIT"
		                   onclick="exitmethod()"/>
		            
		          </td>
        </tr>
      </table>
      <table cellspacing="3" cellpadding="2" border="1" width="100%"
             align="center">
	       	 <tr style=" height : 25px;">
	          	<td class="table" style=" width : 400px;">
	            	<strong>Existing Details</strong>
	          	</td>
	          	<td align="right" class="table">
		            Page&nbsp;Size&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		            <select name="cmbpagination" onchange="changepagesize1()">
		              <option value="5" selected="selected">5</option>
		              <option value="10">10</option>
		              <option value="15">15</option>
		              <option value="20">20</option>
		            </select>
	         	</td>
	        </tr>
      </table>
      <table id="Existing" border="1" width="100%" cellspacing="3"
             align="center">
           <tr class="tdH">
             <!--<td colspan ="1" align="center">
		           &nbsp;&nbsp;
		        <A HREF="#s"  id="editlink" onClick="javascript:return btnsubmit('editl')">Edit</A>
        	</td>
        	--><td colspan ="6" align="center">
        	     <A HREF="#"   id="benefilink"  onClick="javascript:return btnsubmit('beneficiary')">
        					Beneficiary</A>&nbsp;&nbsp;&nbsp; 
        		<A HREF="#"  id="complink" onClick="javascript:return btnsubmit('COMP')">
        					Component</A>&nbsp;&nbsp;&nbsp;
        	 	<A HREF="#"  onClick="javascript:return btnsubmit('itemofwork')">
        	 				Item Of Work</A>&nbsp;&nbsp;&nbsp;
 				<A HREF="#"  onClick="javascript:return btnsubmit('adminsanction')">
    	 					AdminSanction</A>&nbsp;&nbsp;&nbsp;
       			<A HREF="#"  onClick="javascript:return btnsubmit('technicalsanction')">
        					TechnicalSanction</A>  &nbsp;&nbsp;&nbsp;    	 					        	 				
       			<A HREF="#"  onClick="javascript:return btnsubmit('fundtieup')">
       						Fundtieup</A>&nbsp;&nbsp;&nbsp;
     			<A HREF="#"  onClick="javascript:return btnsubmit('fundreceipt')">
     						FundReceipt</A>&nbsp;&nbsp;&nbsp;
     			<A HREF="#"  onClick="javascript:return btnsubmit('budget')">
     						Budgetary Exp </A>&nbsp;&nbsp;&nbsp;
         		<!--<A HREF="#" onClick="javascript:return btnsubmit('upload')">
         					DPR Upload/Download</A>
			--></td>
      </tr> 
      <tr class="tdH" >
          <td width="5%">
            <strong>Select</strong>
          </td>
          <td width="50%" align="center">
            <strong  > Name of the Scheme</strong>
          </td>
          
          <td align="center" width="15%">
            <strong>Type</strong>
          </td>
          <td align="center" width="10%">
            <strong> Plan </strong>
            </td>
            <td  align="center" width="10%">
            <strong>Estimate Cost</strong>
          </td>
         <td align="center" width="10%">
            <strong>Probable Date of completion</strong>
         </td>
       </tr>
       <tbody id="tblList" class="table">
       </tbody>
       <tr class="tdH">
          <td colspan="7">
            <table align="center" cellspacing="3" cellpadding="2" border="0" 
                   width="100%" class="tdH">
              <tr>
                <td width="30%">
                  <div align="left">
                    <div id="divpre" style="display:none"></div>
                  </div>
                </td>
                <td width="40%">
                  <div align="center">
                    <table border="0" id="page">
                      <tr>
                        <td>
                          <span id="divcmbpage" style="display:none">
                            <font color="Black" size="2">
                              <strong>Page&nbsp; 
                                <select name="cmbpage" id="cmbpage"
                                        onchange="changepageno()"></select>
                                 </strong>
                            </font>
                          </span>
                        </td>
                        <td><span id="divpage" ></span></td>
                      </tr>
                    </table>
                  </div>
                </td>
                <td width="30%">
                  <div align="right">
                    <div id="divnext" style="display:none"></div>
                  </div>
                </td>
              </tr>
            </table>
          </td>
       </tr>
      </table>
    </form></body>
</html>