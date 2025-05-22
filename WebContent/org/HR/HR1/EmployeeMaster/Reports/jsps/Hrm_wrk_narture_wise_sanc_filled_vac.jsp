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
    <title> Designation wise Details Report</title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <script type="text/javascript"
            src="../scripts/Hrm_EMP_desig_wise_sum_rep.js"></script>
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
function nullcheck()
{
if(document.Desig_wise_summ_rep.optselect[0].checked==false && document.Desig_wise_summ_rep.optselect[1].checked==false && document.Desig_wise_summ_rep.optselect[2].checked==false)
{
alert("please Select Designation/Rank/Cadre");
return false;
}
} 
	function frmsubmit()
	{
	//alert('dfdf');
	var san="";
	var status="";
	var option="";
	var optdes="";
	var wrk_id="";
	if(document.Desig_wise_summ_rep.Sanc_Str[0].checked==true)
	{
	
	 san="INC";
	}
	else if(document.Desig_wise_summ_rep.Sanc_Str[1].checked==true)
	{
	 san="EXC";
	}
   if(document.Desig_wise_summ_rep.dep_str[0].checked==true)
	{
	 status="DPT";
	}
	else if(document.Desig_wise_summ_rep.dep_str[1].checked==true)
	{
	 status="OTHERS";
	}
	 if(document.Desig_wise_summ_rep.optselectgrp[0].checked==true)
	{
	 option="All";
	}
	else if(document.Desig_wise_summ_rep.optselectgrp[1].checked==true)
	{
	 option="Spc";
	}
	 if(document.Desig_wise_summ_rep.optselect[0].checked==true)
	{
	 optdes="Desig";
	}
	else if(document.Desig_wise_summ_rep.optselect[1].checked==true)
	{
	 optdes="Rank";
	}
	
	else if(document.Desig_wise_summ_rep.optselect[2].checked==true)
	{
	 optdes="Cadre";
	}
	var des_id="";
	try
	{
	 if((document.Desig_wise_summ_rep.optselect[0].checked==false)&&(document.Desig_wise_summ_rep.optselect[1].checked==false)&&(document.Desig_wise_summ_rep.optselect[2].checked==false))
        	
        {
        	alert("Please select any Desigantion category ");
        	return false;
        }
        else
        ///alert("sdgfsg");
	if(document.Desig_wise_summ_rep.optselectgrp[1].checked==true)
	{
	if(document.Desig_wise_summ_rep.optselect[0].checked==true)
	{
	if(document.Desig_wise_summ_rep.cmbsgroup.value==0){
        alert("Please select the service group");
        return false;
        }
        else
        {
	if(document.Desig_wise_summ_rep.chkdesig)
    {
      if(document.Desig_wise_summ_rep.chkdesig.length )
       {
	    for(i=0;i<document.Desig_wise_summ_rep.chkdesig.length;i++)
          {
          if(document.Desig_wise_summ_rep.chkdesig[i].checked==true)
                des_id= des_id+document.Desig_wise_summ_rep.chkdesig[i].value +",";                                                                                                       
           }
           if(des_id!="")
           {
          des_id= des_id.substring(0, des_id.length-1);
          }
          else
          {
           alert("Please Select Designation!");
           return false;
          }
	   }
    }
    }
    }
    }
    }
    catch (e) {
		// TODO: handle exception
	}
    var rand_id="";
    try
    {
    
	if(document.Desig_wise_summ_rep.optselectgrp[1].checked==true)
	{
    if(document.Desig_wise_summ_rep.optselect[1].checked==true)
    {
    if(document.Desig_wise_summ_rep.cmbsgroup1.value==0){
        alert("Please select the service group");
        return false;
        }
        else
        {
				if(document.Desig_wise_summ_rep.chkrank)
			    {
			      if(document.Desig_wise_summ_rep.chkrank.length )
			       {
				    for(i=0;i<document.Desig_wise_summ_rep.chkrank.length;i++)
			          {
			          if(document.Desig_wise_summ_rep.chkrank[i].checked==true)
			                rand_id= rand_id+document.Desig_wise_summ_rep.chkrank[i].value +",";                                                                                                       
			           }
			           
			          if(rand_id!="")
			          {
			          rand_id= rand_id.substring(0, rand_id.length-1);
			          }
			          else
			          {
			           alert("Please Select Rank! ");
			           return false;
			          }
				   }
				   
				     
			    }
			    }
			    }
			    }
    }
    catch(e)
    {
    }
    var cadre_id="";
    try
    {
    
	if(document.Desig_wise_summ_rep.optselectgrp[1].checked==true)
	{
    if(document.Desig_wise_summ_rep.optselect[2].checked==true)
    {
    if(document.Desig_wise_summ_rep.cmbsgroup2.value==0){
        alert("Please select the service group");
        return false;
        }
        else
        {
	if(document.Desig_wise_summ_rep.chkcadre)
    {
      if(document.Desig_wise_summ_rep.chkcadre.length )
       {
	    for(i=0;i<document.Desig_wise_summ_rep.chkcadre.length;i++)
          {
          if(document.Desig_wise_summ_rep.chkcadre[i].checked==true)
                cadre_id= cadre_id+document.Desig_wise_summ_rep.chkcadre[i].value +",";                                                                                                       
           }
           if(cadre_id!="")
           {
            cadre_id= cadre_id.substring(0, cadre_id.length-1);
            }
            else
            {
            alert("Please Select Cadre!");
            return false;
            }
	   }
    }
    }
    }
    }
    }
    catch (e) {
		// TODO: handle exception
	}
	//alert("des_id-->"+des_id);
	if(document.getElementById("wrk_nature_id").value==0)
	{
	alert("Please select Office Work Nature");
	return false;
	}
	wrk_id=document.getElementById("wrk_nature_id").value;
	document.Desig_wise_summ_rep.action="WRK_nature_wise_Details.jsp?san="+san+"&status="+status+"&option="+option+"&optdes="+optdes+"&des_id="+des_id+"&rand_id="+rand_id+"&cadre_id="+cadre_id+"&wrk_id="+wrk_id;
	document.Desig_wise_summ_rep.submit();
	}
</script>
    
  </head>
  <!--- action="../../../../../EmployeeDetailReportServ.rep" -->
  <body>
  <form name="Desig_wise_summ_rep" method="POST" action="../../../../../../Compassionate_details_report">    
    <table border='2' width="100%">
     <tr>
      <td colspan="2" align="left" valign="top" class="table" width="60%">
       <table border="0" width="100%">
        <tr>
          	<td colspan="8" class="tdH" align="center"> Office Work Nature wise Summary Report</td>
        </tr>
         <tr>
          	<td colspan="8" class="tdH" align="left"> Summary Report Details</td>
        </tr>
        <tr>
	         <td  colspan="8"> <input type="radio" id="Sanc_Str_inc" name="Sanc_Str"  value="INC" checked="checked" onclick="hideradio()"></input>
	         Include Sanction Strength
	         <input style="display:none" type="radio" id="Sanc_Str_exc" name="Sanc_Str"  value="EXC" onclick="showradio()"></input>
	         </td>
	       
	                                                                                                                                        
       </tr>      
       <tr>
	         <td  colspan="8" style="display:none"> <input type="radio" id="dep_Str_inc" name="dep_str" disabled="disabled" value="INC_Dep" ></input>
	         
	         <input type="radio" id="desig_Str_inc" style="display:none" name="dep_str"  value="EXC_Dep" checked="checked" ></input>
	         </td>
	       
	                                                                                                                                        
       </tr>          
          <tr>
             <td colspan="8" class="tdH">Designation</td>
             
       </tr>
      <tr class="table">    
        <td >       
              <input type="radio" value="RadAl" name="optselectgrp" id="chkone" checked="checked" onclick="hide()">
               All 
         
               <!--input type="checkbox" name="chkall" onclick="sellectall()"--> 
               <input type="radio" value="radsel" onclick="sellectall();show();unchkrank()" name="optselectgrp" >
               
                Specific           
       </td>   
       
       </tr>   
       
       
        <tr class="table" id="divselect"  >                  
                <td> <input type="radio" value="Dest"  onclick="selectoption1();hide()" 
                             name="optselect" id="optselect1" disabled="disabled" style="display:none" ></input>
                       
                      <input type="radio" value="Rank" name="optselect"  onclick="selectoption1();hide();"
                             id="optselect2" checked  ></input>
                       Rank
                       <input type="radio" value="Cadr" name="optselect"  onclick="selectoption1();hide();" 
                             id="optselect3" disabled="disabled" style="display:none" ></input>
                       
                </td>
               
        </tr>
           <tr class="table" id="divdest" style="display:none;">
                  <td>
                    <div align="left">
                    <select name="cmbsgroup" id="cmbsgroup" onchange="getDesignation()">
                <option value="0">Select Service Group</option>
                        <%
           ResultSet rs=null;
           try
           {
           ps=connection.prepareStatement("select SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_MST_SERVICE_GROUP  order by SERVICE_GROUP_NAME");
            rs=ps.executeQuery();
              int strcode=0;
            String strname="";          
            while(rs.next())
            {
              
               
                strcode=rs.getInt("SERVICE_GROUP_ID");
                strname=rs.getString("SERVICE_GROUP_NAME");
                System.out.println("service group code:"+strcode);
                System.out.println("service gorup name:"+strname);
                out.println("<option value='"+strcode+"'>"+strname+"</option>");
                
             }
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+e);
          }
           finally
          {
                rs.close();
                ps.close();
          
          }    
                
        %>
               
               </select> 
               </div>
                  </td>
                  
           </tr>
               
                   <!---  designation -->
                    <!--div id='divdesignation' style='display:none'-->
                      <!--select name="cmbDesignation" id="cmbDesignation" 
                               >
                        <option value="0"></option>
                        
                      </select-->
                      
                      <!--/div-->
                    
                
                
                
        
          <tr class="table" id="divrank" style="display:none">
                  <td colspan='2'>
                    <div align="left"  id='divrankwhole'>
                    <select name="cmbsgroup1" id="cmbsgroup1" onchange="getDesignation1()">
                <option value="0">Select Service Group</option>
                        <%
            rs=null;
           try
           {
           ps=connection.prepareStatement("select SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_MST_SERVICE_GROUP  order by SERVICE_GROUP_NAME");
            rs=ps.executeQuery();
              int strcode=0;
            String strname="";          
            while(rs.next())
            {
              
               
                strcode=rs.getInt("SERVICE_GROUP_ID");
                strname=rs.getString("SERVICE_GROUP_NAME");
                
                out.println("<option value='"+strcode+"'>"+strname+"</option>");
                
             }
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+e);
          }
           finally
          {
                rs.close();
                ps.close();
          
          }    
                
        %>
               
               </select> 
                    <!---  cadre -->
                      <!--select name="cmbRank" id="cmbRank"
                              onclick="return checkGroup1();">
                        <option value="0"></option>
                        
                      </select-->
                    </div>
                  </td>
                </tr>             
                <!--tr class="table">
               <td>
               <div class="scroll" align='left' style='width:37%'  id="divranknew">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
               </td>
                </tr-->
                
                
                <tr class="table" id="divcadre" style="display:none;">
                  <td colspan='2'>
                    <div align="left">
                    <select name="cmbsgroup2" id="cmbsgroup2" onchange="getDesignation2()">
                <option value="0">Select Service Group</option>
                <%
            rs=null;
           try
           {
           ps=connection.prepareStatement("select SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_MST_SERVICE_GROUP  order by SERVICE_GROUP_NAME");
            rs=ps.executeQuery();
              int strcode=0;
            String strname="";          
            while(rs.next())
            {
              
               
                strcode=rs.getInt("SERVICE_GROUP_ID");
                strname=rs.getString("SERVICE_GROUP_NAME");
                
                out.println("<option value='"+strcode+"'>"+strname+"</option>");
                
             }
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+e);
          }
           finally
          {
                rs.close();
                ps.close();
          
          }    
                
        %>
               
               </select> 
                    <!---  rank -->
                      <!--select name="cmbCadre" id="cmbCadre"
                              onclick="return checkGroup2();">
                        <option value="0"></option>
                        
                      </select-->
                    </div>
                  </td>
                </tr>
                <!--tr class="table">
               <td>
               <div class="scroll" align='left' style='width:37%'  id="divcadrenew">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
               </td>
            </tr-->
                
              <tr class="table" id="desigblock" style="display:none">
               <td>
               <div class="scroll" align='left' style='width:100%'  id="divdesignation">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
               </td>
               </tr>  
                
               
                
                
                
                
                
                
                
                
                
                <tr>
             <td colspan="8" class="tdH">Office Work Nature</td>
             
       </tr>
                  <tr class="table">
                  <td colspan='2'>
                    <div align="left">
                    <select name="wrk_nature_id" id="wrk_nature_id" >
                <option value="0">Select Office Work Nature </option>
                <%
            rs=null;
           try
           {
           ps=connection.prepareStatement("select WORK_NATURE_ID,WORK_NATURE_DESC from COM_MST_WORK_NATURE order by ORDER_SEQ");
            rs=ps.executeQuery();
              String  wrk_id="";
            String wrk_name="";          
            while(rs.next())
            {
              
               
                wrk_id=rs.getString("WORK_NATURE_ID");
                wrk_name=rs.getString("WORK_NATURE_DESC");
                
                out.println("<option value='"+wrk_id+"'>"+wrk_name+"</option>");
                
             }
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+e);
          }
           finally
          {
                rs.close();
                ps.close();
          
          }    
                
        %>
               
               </select> 
                    <!---  rank -->
                      <!--select name="cmbCadre" id="cmbCadre"
                              onclick="return checkGroup2();">
                        <option value="0"></option>
                        
                      </select-->
                    </div>
                  </td>
                </tr>   
      
       <tr>
			<td class="tdH" align="center">	
			     <input type="button" value="Submit"  onclick="frmsubmit()">
			    <input type="button" id="cmdcancel" name="cancel" value="Close" onclick="self.close();"> 
           </td>
       </tr>
  </table>
</td>
</tr>
</table>
</form>
</body>
</html>