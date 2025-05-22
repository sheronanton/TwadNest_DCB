<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
		<%@ page import="java.sql.*,java.util.ResourceBundle" %>
 			<%@ page session="false" contentType="text/html;charset=windows-1252"%>
 			<%@page import="java.util.Calendar"  %>
 			<%@page import="Servlets.ASSET.ASSET1.ASSETM.servlets.*" %>
  <head>   
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"></meta>
    <meta http-equiv="cache-control" content="no-cache"/>
    <title>Scheme Master</title>
    <link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet"media="screen" />
<script type="text/javascript" src="../scripts/master1.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../scripts/CalendarCtrl.js"></script> 
    <script type="text/javascript" src="../scripts/Pagination.js"></script>
 <script type="text/javascript"> 
   
    function tech(a)
    {
    	
        var type_id=document.getElementById("SCH_TYPE_ID"+a).innerHTML;

    	var sch_name=document.getElementById("SCH_NAME"+a).innerHTML;
   
    	var amount=document.getElementById("DPR_AMOUNT"+a).innerHTML;
    	var date_of_compl=document.getElementById("ACTUAL_DATE_COMPLETION"+a).innerHTML;
    	 var shtdes=document.getElementById("SCH_SHORT_DESC"+a).value;
    	 var comm=document.getElementById("ACTUAL_DATE_COMMENCEMENT"+a).value;
         
        document.getElementById("sch_code").value=type_id;
    	document.getElementById("sch_name").value=sch_name;
    	document.getElementById("sch_desc").value= shtdes;
    	//alert(sch_name+"sch_name");
    	document.getElementById("dpr_amt").value=amount;
    	document.getElementById("date_of_comm").value=comm
    	document.getElementById("date_of_compl").value=date_of_compl;
    	
    }
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

  

   function load(sn)
   {
      	var params=0;
   		var sch_sno=0;
   		//var sch_name="";
		var sel=document.getElementsByName("chdcb");
   	    var flag=0;
   		for(var i=0; i<sel.length; i++)
   		{
   			if(document.getElementsByName("chdcb")[i].checked==true)
   			{
	   			flag=1;
	   			sch_sno=document.getElementById("SCH_SNO"+(i+1)).value;
	   			//sch_name=document.getElementById("SCH_NAME").value;
				params="sch_sno="+sch_sno;
				//alert(params+"params");
   			}
   			
   		}
   	   			alert(sch_sno+flag);
   	   				if(sn==1 && flag==1)
			   		{
   	   				    //alert(params+"params");
			   			window.open('AssGenDetail1.jsp?'+params);
			   		}
			   		else if(sn==2 && flag==1)
			   		{
				   		//alert(params+"params");
			   			window.open('SourceAssetGeneralDetail.jsp?'+params);
			   		}
			   		else
			   		{
			   			alert("Please Choose or Select Scheme for Entry");
			   		}
   } 
   </script> 
  </head>
  
	<body> 
	<%  String userid="0";
	String office_id="0";
	String office_name="xyz sndsn";
			//HttpSession session1 = request.getSession(false);
			//String	userid = (String) session1.getAttribute("UserId");
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			
			Controller obj=new Controller();
			Connection con=obj.con();
			//if (userid == null) {

				//response.sendRedirect(request.getContextPath() + "/index.jsp");
			//}
			  obj.createStatement(con);
			//String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
			  
			String SCH_CATEGORY_ID=obj.setValue("category_sno",request);
			String sch_year=obj.setValue("pyear",request);  
			String sch_code=obj.setValue("sch_code",request);
			String sch_name=obj.setValue("sch_name",request);
			String sch_type=obj.setValue("sch_type",request);  
			String category=obj.combo_str("PMS_SCH_LKP_CATEGORY","CATEGORY_DESC","SCH_CATEGORY_ID", " order by SCH_CATEGORY_ID ","category_sno",SCH_CATEGORY_ID,"class='pmscombo' style='width: 262' onchange=document.forms['sch_master'].submit()" );
			 
			String sch_fin_year=obj.combo_str("PMS_SCH_MASTER"," distinct SCH_YEAR"," order by SCH_YEAR DESC","pyear","class='pmscombo' style='width: 262'" );
			//combo_str(String table, String text, String value,String cond, String id,String initial_value, String attributes)
			String schtype=obj.combo_str("PMS_SCH_LKP_TYPE","SCH_TYPE_DESC","SCH_TYPE_ID", " where  SCH_CATEGORY_ID="+SCH_CATEGORY_ID+" order by SCH_CATEGORY_ID ","sch_type",sch_type,"class='pmscombo' style='width: 262' onchange=sch_transaction(1,1)" );
			  
			//if (Office_id.equals("")) Office_id="0";
			//String Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
		
			%>  
	 
	<form id="sch_master" name="sch_master" method="get" >
      <table cellspacing="2" cellpadding="2" border="1" width="100%" align="center">
      <tr><th align ="center" colspan ="5" class = "tdH">Scheme Master -<%=office_name %></th></tr>       
        <tr class="lbl">  
	          <td width="30%"><font  class="fnt" color='blue'>Category</font> </td>
	          <td width="70%" colspan ="4">
	            <%=category %>
	          </td> 
        </tr>
        
        
      	 
      	 <tr>
	          <td><label class="lbl"><font class="fnt" color="blue">Scheme Financial Year</font></label> </td>
	          <Td colspan="4"><%=sch_fin_year%></tD>	  </tr> 
         <tr class="lbl">
	          <td width="20%"> <font  class="fnt"  color="blue"> Type</font></td>
	          <td width="80%" colspan ="4">
	             <%=schtype%>
	          </td>
        </tr>
         <tr class="lbl" >
	          <td width="20%"><font color="blue">Code </font></td>
	          <td width="80%" colspan="4">
	            <input type="text" name="sch_code" id="sch_code" alt="Code" tabindex="4" size="60" onkeypress="return ischarValid(event,this,50);" style=" width : 273px;"/>
	          </td>
	          
        </tr>      
	          
          
  		<tr class="lbl"  >
	          <td width="20%"><font color="blue"> Name </font></td>
	          <td width="80%" colspan="4">	            
	            <textarea name="sch_name"  tabindex="5" id="sch_name" title="Scheme" cols="50" alt="Name" ></textarea>
	          </td> 
	          
        </tr>
        <tr class="lbl" >
	          <td width="20%"> <font  class="fnt">Short Name </font></td>
	          <td width="80%" colspan="4">
	            <input type="text" name="sch_desc" id="sch_desc" alt="Name(Short)" tabindex="6" maxlength="50"   size="50" readonly="readonly"/>
	          </td> 
        </tr>

        <tr class="lbl">
	          <td width="20%"> <font  class="fnt">Estimated Cost (Rs. in Lakhs)</font> </td>
	          <td width="80%" colspan="4">
	            <input type="text" name="dpr_amt" id="dpr_amt" alt="Estimate Cost " tabindex="11" maxlength="16"
	                   style=" width : 15%;text-align:right" onkeypress="return isValidDecimal(event,this,12,2)" readonly="readonly" />
	          </td>
        </tr>
       
        <tr class="lbl">
	          <td width="20%"> <font  class="fnt">Actual Date of Commencement (DD/MM/YYYY)</font></td>
	          <td width="80%" colspan="4">
	            <input type="text" name="date_of_comm"  id="date_of_comm"  onkeypress="return isNumberKey_doc(event,this,10)";     alt="Date of commencement"  readonly="readonly"/>
				</td>
       </tr>

        <tr class="lbl">
	          <td width="20%"> <font  class="fnt">Actual Date of Completion (DD/MM/YYYY)</font></td>
	          <td width="80%" colspan="4">
	            <input type="text" name="date_of_compl"  id="date_of_compl"  onkeypress="return isNumberKey_doc(event,this,10)";    alt="Date of completion" readonly="readonly" />
	            </td>
        </tr>
        <tr align="right">
        	<td colspan="5" style="color:blue;">*<b> <font  class="fnt"> Fields used for searching are  in blue</font></b> </td>
        </tr>
        <tr align="center" class="tdH">
		          <td colspan="5">
		           
		            <input type="button" name="search" id="search" value="SEARCH"  style="COLOR: #0000ff;" onclick="sch_transaction(1,1)  "/>
		            <input type="reset" name="Cancel" id="Cancel" value="CLEAR" />
		            <input type="button" name="Exit" id="Exit" value="EXIT" onclick="self.close()"/>
		            
		          </td>
        </tr>
	       	 <tr style=" height : 25px;">
	          	<td colspan="4" class="tdH" style=" width : 400px;"><b>  <font  class="fnt">Existing Details </font></b></td>
	          	<td align="right" class="tdH">
		            <font  class="fnt"> Page&nbsp;Size&nbsp;</font>
		            <select name="cmpage" id="cmpage" onchange="sch_transaction(1,1)">
		              <option value="5">5</option>
		              <option value="10"  selected="selected">10</option>
		              <option value="15">15</option>
		              <option value="20">20</option>
		            </select>
	         	</td>
	        </tr>
      </table>
     
      <table id="Existing" border="1" width="100%" cellspacing="3" class="table" align="center">
           <tr >
           
			<td colspan ="6" align="center">
        	    <A HREF="javascript:load(1);" > <font  class="fnt">Scheme General Details </font></A>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; 
        		<A HREF="javascript:load(2);" > <font  class="fnt">Scheme Technical Details</font></A> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
        	 	
         	    </td>  </tr> 
      <tr class="tdH" >
          <th> <font  class="fnt">Select</font></th>
          <th> <font  class="fnt">Type</font></th>
          <th> <font  class="fnt">Name of the Scheme</font></th>        
          <th> <font  class="fnt">Estimate Cost</font></th>
          <th> <font  class="fnt">Probable Date of completion</font></th>
       </tr>
       <tr>
        		<td id="tdNoData" align="center" colspan="6" class="tdText">
        			<div id="nodata" style="display:none"> </div>
        		</td>
        	</tr>
       <tbody id="tblist"></tbody>
       
      
      </table>
      <table  class="fb2" cellspacing="0" cellpadding="2" border="1" width="100%" align="center" > 
            <tr class="tdH" name="pgbar" id="pgbar" >
              <td> 
                <table align="center" cellspacing="3" cellpadding="2"  class="fb2"
                       border="0" width="100%">
                  <tr>
                    <td width="30%">
                      <div align="left">
                        <div id="divpre" style="display:none"><a href="javascript:prev()"><label> << </label>Previous</a></div>
                      </div>
                    </td>
                    <td width="40%">
                      <div align="center">
                        <table border="0">
                          <tr>
                            <td>
                              <div id="divcmbpage" style="display:none">
                                Page&nbsp;&nbsp;<select name="cmbpage"
                                                        id="cmbpage"
                                                        onchange="sch_transaction(1,1); "
                                                         class="select">
                                                	<option value="1">1</option>        
                                                </select>
                              </div>
                            </td>
                            <td>
                              <div id="divpage" style="display:none" >1</div>
                            </td>
                          </tr>
                        </table>
                      </div>
                    </td>
                    <td width="30%">
                      <div align="right">
                        <div id="divnext" style="display:none"><a href="javascript:next()">Next <label> >> </label></a></div>
                      </div>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
 		</table>
 		<input type="hidden" id="sel_sch">
 		<input type="hidden" id="off" >
    </form></body>
</html>