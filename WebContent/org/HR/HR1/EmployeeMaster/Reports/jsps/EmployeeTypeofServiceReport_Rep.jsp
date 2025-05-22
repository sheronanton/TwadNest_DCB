<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver,java.sql.*,java.io.*,java.util.*" %>
    <%@ page import="Servlets.Security.classes.UserProfile" %>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 
<link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
          
          
          <script src="../../../../../../jquerycalendar/jquery-1.7.2.js"></script>
          <script src="../../../../../../jquerycalendar/jquery-ui.min.js"></script>
          
		  <link rel="stylesheet" href="../../../../../../jquerycalendar/sort_freeze.css">
		  <link rel="stylesheet" href="../../../../../../jquerycalendar/jquery-ui.css">
          <script src="../../../../../../jquerycalendar/jquery.tablesorter.widgets1.js"></script>
	      <script src="../../../../../../jquerycalendar/jquery.tablesorter1.js"></script>
<style type="text/css">
</style>


	
	<script type="text/javascript" >

$(document).ready(function(){
       $("table").tablesorter({theme : 'blue', cssInfoBlock : "tablesorter-no-sort", widgets: [ 'zebra', 'stickyHeaders' ]});

});

</script>


 -->
    <script type="text/javascript"
            src="../scripts/EmployeeTypeofServiceReport.js"></script>




<script type="text/javascript" src="jquery.tablesorter.min.js"></script>
    <script src="../../../../../../jquerycalendar/jquery-latest.js"></script>
	      <script src="../../../../../../jquerycalendar/jquery.tablesorter.js"></script>
	      
	     <script src="../../../../../../jquerycalendar/jquery.tablesorter.pager.js"></script>



<script>

function ChangeColor(tableRow, highLight)
    {
   
       if (highLight){
       
           tableRow.style.backgroundColor = '00CCCC';
       }

    else{
         tableRow.style.backgroundColor = 'white';
        }   
  }
  
  
  function Generate_Report()
  {
  		 try {
     
        var tbl = document.getElementById("myTable");
     var rCount = tbl.rows.length;
     
        // //alert("value ====  "+tbl.rows[1].cells[1].innerHTML);
        var header = document.getElementById("order_desc").value;
                
        if(header.trim()=="Employee_Id")
        {
         var td_value=(tbl.rows[1].cells[1].innerHTML);
         var array=new Array();
         array=td_value.split(">");
         var eid=new Array();
         eid=array[2].split("<");
         ////alert(eid[0]);
         
         
         var td_value1=(tbl.rows[2].cells[1].innerHTML);
         var array1=new Array();
         array1=td_value1.split(">");
         var eid1=new Array();
         eid1=array1[2].split("<");
         ////alert(eid1[0]);
        var order;

        
        
	         if(eid[0]>eid1[0])
	         {
	         ////alert(eid[0] + " > "+eid1[0]);
	         document.getElementById("order_desc").value="order by "+header +" desc";
	                  ////alert(document.getElementById("order_desc").value);
	       	 }
	         else
	         {
	         ////alert(eid[0] + " < "+eid1[0]);
	         document.getElementById("order_desc").value="order by "+header +" asc";
	            
	         }
	         ////alert("value   =====  "+document.getElementById("order_desc").value);
         }
         
           if(header=="totalservice")
         {
         
         
         // order by Service_Yr desc ,  Service_Mnth desc
          
          var eid=(tbl.rows[1].cells[4].innerHTML);       
         var eid1=(tbl.rows[arr[arr.length-1]].cells[4].innerHTML);
         //alert(eid+"    ===   "+eid1);
         
          var td_value=(tbl.rows[1].cells[4].innerHTML);
         var array=new Array();
         array=td_value.split(">");
         //alert(array[1]);
         var eid=new Array();
         var eid_year=array[1].split("<");
         var eid_month=array[2].split("<");
         //alert("ED YEAR = "+eid_year[0]);
         //alert("ED MONTH = "+eid_month[0]);
         var top_Year=eid_year[0].substring(1, 4);
         var top_Month=eid_month[0].substring(1, 4);
         //alert("top year  == "+eidYear);
         //alert("top month  == "+eidMonth);
         ////alert(eid[0]);
         
         
         var td_value=(tbl.rows[arr[arr.length-1]].cells[4].innerHTML);
         var array=new Array();
         array=td_value.split(">");
         //alert(array[1]);
         var eid=new Array();
         var eid_year=array[1].split("<");
         var eid_month=array[2].split("<");
         //alert("ED YEAR = "+eid_year[0]);
         //alert("ED MONTH = "+eid_month[0]);
         var bottom_Year=eid_year[0].substring(1, 4);
         var bottom_month=eid_month[0].substring(1, 4);
         //alert("bottom year  == "+eidYear1);
         //alert("bottom month  == "+eidMonth1);
         
         if(top_Year > bottom_Year)
         {
         //alert("top greater....");
       			  document.getElementById("order_desc").value="order by total_service_yr desc, total_service_mnth desc";
         }
         
         else if(top_Year < bottom_Year)
         {
         //alert("top lesser....");
       			  document.getElementById("order_desc").value="order by total_service_yr ASC, total_service_mnth ASC";
         }
         
         else if(top_Year == bottom_Year)
         {
        // alert("equal");
         		if( top_Month > bottom_month )
         		{
       			  document.getElementById("order_desc").value=" order by  total_service_mnth desc";
       			}
       			else if( top_Month < bottom_month )
       			{
       				document.getElementById("order_desc").value=" order by   total_service_mnth asc";
       			}
       			else
       			{
       				document.getElementById("order_desc").value=" order by total_service_yr desc ,  total_service_mnth desc";
       			}
         }
         else
         {
         	document.getElementById("order_desc").value=" order by total_service_yr desc ,  total_service_mnth desc";
         }
			return true;
         
         
         }
         
         if(header=="totalpfservice")
         {
         // order by Service_Yr desc ,  Service_Mnth desc
          
          var eid=(tbl.rows[1].cells[5].innerHTML);       
         var eid1=(tbl.rows[arr[arr.length-1]].cells[5].innerHTML);
         //alert(eid+"    ===   "+eid1);
         
          var td_value=(tbl.rows[1].cells[5].innerHTML);
         var array=new Array();
         array=td_value.split(">");
         //alert(array[1]);
         var eid=new Array();
         var eid_year=array[1].split("<");
         var eid_month=array[2].split("<");
         //alert("ED YEAR = "+eid_year[0]);
         //alert("ED MONTH = "+eid_month[0]);
         var top_Year=eid_year[0].substring(1, 4);
         var top_Month=eid_month[0].substring(1, 4);
         //alert("top year  == "+eidYear);
         //alert("top month  == "+eidMonth);
         ////alert(eid[0]);
         
         
         var td_value=(tbl.rows[arr[arr.length-1]].cells[5].innerHTML);
         var array=new Array();
         array=td_value.split(">");
         //alert(array[1]);
         var eid=new Array();
         var eid_year=array[1].split("<");
         var eid_month=array[2].split("<");
         //alert("ED YEAR = "+eid_year[0]);
         //alert("ED MONTH = "+eid_month[0]);
         var bottom_Year=eid_year[0].substring(1, 4);
         var bottom_month=eid_month[0].substring(1, 4);
         //alert("bottom year  == "+eidYear1);
         //alert("bottom month  == "+eidMonth1);
         
         if(top_Year > bottom_Year)
         {
        // alert("top greater....");
       			  document.getElementById("order_desc").value="order by Pf_Office_Yr desc, Pf_Office_Month desc";
         }
         
         else if(top_Year < bottom_Year)
         {
        // alert("top lesser....");
       			  document.getElementById("order_desc").value="order by Pf_Office_Yr ASC, Pf_Office_Month ASC";
         }
         
         else if(top_Year == bottom_Year)
         {
         //alert("equal");
         		if( top_Month > bottom_month )
         		{
       			  document.getElementById("order_desc").value=" order by  Pf_Office_Month desc";
       			}
       			else if( top_Month < bottom_month )
       			{
       				document.getElementById("order_desc").value=" order by   Pf_Office_Month asc";
       			}
       			else
       			{
       				document.getElementById("order_desc").value=" order by Pf_Office_Yr desc ,  Pf_Office_Month desc";
       			}
         }
         else
         {
         	document.getElementById("order_desc").value=" order by total_service_yr desc ,  total_service_mnth desc";
         }
			return true;
         }
         
                  if(header=="yearofservice")
         {
         // order by Service_Yr desc ,  Service_Mnth desc
          
          var eid=(tbl.rows[1].cells[6].innerHTML);       
         var eid1=(tbl.rows[arr[arr.length-1]].cells[6].innerHTML);
         //alert(eid+"    ===   "+eid1);
         
          var td_value=(tbl.rows[1].cells[6].innerHTML);
         var array=new Array();
         array=td_value.split(">");
         //alert(array[1]);
         var eid=new Array();
         var eid_year=array[1].split("<");
         var eid_month=array[2].split("<");
         //alert("ED YEAR = "+eid_year[0]);
         //alert("ED MONTH = "+eid_month[0]);
         var top_Year=eid_year[0].substring(1, 4);
         var top_Month=eid_month[0].substring(1, 4);
         //alert("top year  == "+eidYear);
         //alert("top month  == "+eidMonth);
         ////alert(eid[0]);
         
         
         var td_value=(tbl.rows[arr[arr.length-1]].cells[6].innerHTML);
         var array=new Array();
         array=td_value.split(">");
         //alert(array[1]);
         var eid=new Array();
         var eid_year=array[1].split("<");
         var eid_month=array[2].split("<");
         //alert("ED YEAR = "+eid_year[0]);
         //alert("ED MONTH = "+eid_month[0]);
         var bottom_Year=eid_year[0].substring(1, 4);
         var bottom_month=eid_month[0].substring(1, 4);
         //alert("bottom year  == "+eidYear1);
         //alert("bottom month  == "+eidMonth1);
         
         if(top_Year > bottom_Year)
         {
        // alert("top greater....");
       			  document.getElementById("order_desc").value="order by Serviceyear desc, serviceMonth desc";
         }
         
         else if(top_Year < bottom_Year)
         {
        // alert("top lesser....");
       			  document.getElementById("order_desc").value="order by Serviceyear ASC, serviceMonth ASC";
         }
         
         else if(top_Year == bottom_Year)
         {
        // alert("equal");
         		if( top_Month > bottom_month )
         		{
       			  document.getElementById("order_desc").value=" order by  serviceMonth desc";
       			}
       			else if( top_Month < bottom_month )
       			{
       				document.getElementById("order_desc").value=" order by   serviceMonth asc";
       			}
       			else
       			{
       				document.getElementById("order_desc").value=" order by Serviceyear desc ,  serviceMonth desc";
       			}
         }
         else
         {
         	document.getElementById("order_desc").value=" order by total_service_yr desc ,  total_service_mnth desc";
         }
			return true;
         
         }
         
         if(header=="dateofjoin")
         {
         // order by Service_Yr desc ,  Service_Mnth desc
          
          var eid=(tbl.rows[1].cells[7].innerHTML);       
         var eid1=(tbl.rows[arr[arr.length-1]].cells[7].innerHTML);
         //alert(eid+"    ===   "+eid1);
         
          var td_value=(tbl.rows[1].cells[7].innerHTML);
         var array=new Array();
         array=td_value.split(">");
         //alert(array[1]);
         var eid=new Array();
         var eid_year=array[1].split("<");
         //alert("ED YEAR = "+eid_year[0]);
         //alert("ED MONTH = "+eid_month[0]);
         //var top_Year=eid_year[0];
         var top_Year = new Date(eid_year[0]).getTime();
         //var top_Month=eid_month[0].substring(1, 4);
         //alert("top year  == "+eidYear);
         //alert("top month  == "+eidMonth);
         ////alert(eid[0]);
         
         
         var td_value=(tbl.rows[arr[arr.length-1]].cells[7].innerHTML);
         var array=new Array();
         array=td_value.split(">");
         //alert(array[1]);
         var eid=new Array();
         var eid_year=array[1].split("<");
         //var eid_month=array[2].split("<");
         //alert("ED YEAR = "+eid_year[0]);
         //alert("ED MONTH = "+eid_month[0]);
         //var bottom_Year=eid_year[0];
         var bottom_Year = new Date(eid_year[0]).getTime();
         
         //var bottom_month=eid_month[0].substring(1, 4);
         //alert("bottom year  == "+eidYear1);
         //alert("bottom month  == "+eidMonth1);
         
         
 
if (top_Year > bottom_Year) {
//alert("top year Date is big");
document.getElementById("order_desc").value="ORDER BY to_date(Boar_Join_Date,'dd/mm/yyyy') desc";
}
else if (top_Year < bottom_Year)
{
//alert("bottom year Date is big");
document.getElementById("order_desc").value="ORDER BY to_date(Boar_Join_Date,'dd/mm/yyyy') ASC";
}
else
{
//alert("equal...");
document.getElementById("order_desc").value=" order by total_service_yr desc ,  total_service_mnth desc";
}        
       
			return true;
         
         }
         
         
                  if(header=="datefromwork")
         {
         // order by Service_Yr desc ,  Service_Mnth desc
          
          var eid=(tbl.rows[1].cells[8].innerHTML);       
         var eid1=(tbl.rows[arr[arr.length-1]].cells[8].innerHTML);
         //alert(eid+"    ===   "+eid1);
         
          var td_value=(tbl.rows[1].cells[8].innerHTML);
         var array=new Array();
         array=td_value.split(">");
         //alert(array[1]);
         var eid=new Array();
         var eid_year=array[1].split("<");
         //alert("ED YEAR = "+eid_year[0]);
         //alert("ED MONTH = "+eid_month[0]);
         //var top_Year=eid_year[0];
         var top_Year = new Date(eid_year[0]).getTime();
         //var top_Month=eid_month[0].substring(1, 4);
         //alert("top year  == "+eidYear);
         //alert("top month  == "+eidMonth);
         ////alert(eid[0]);
         
         
         var td_value=(tbl.rows[arr[arr.length-1]].cells[8].innerHTML);
         var array=new Array();
         array=td_value.split(">");
         //alert(array[1]);
         var eid=new Array();
         var eid_year=array[1].split("<");
         //var eid_month=array[2].split("<");
         //alert("ED YEAR = "+eid_year[0]);
         //alert("ED MONTH = "+eid_month[0]);
         //var bottom_Year=eid_year[0];
         var bottom_Year = new Date(eid_year[0]).getTime();
         
         //var bottom_month=eid_month[0].substring(1, 4);
         //alert("bottom year  == "+eidYear1);
         //alert("bottom month  == "+eidMonth1);
         
         
 
if (top_Year > bottom_Year) {
//alert("top year Date is big");
document.getElementById("order_desc").value="ORDER BY to_date(Join_Date_Design,'dd/mm/yyyy') desc";
}
else if (top_Year < bottom_Year)
{
//alert("bottom year Date is big");
document.getElementById("order_desc").value="ORDER BY to_date(Join_Date_Design,'dd/mm/yyyy') ASC";
}
else
{
//alert("equal...");
document.getElementById("order_desc").value=" order by total_service_yr desc ,  total_service_mnth desc";
}        
       
			return true;
         
         }
         
         
         
                  
                  if(header=="datefromworkpresent")
         {
         // order by Service_Yr desc ,  Service_Mnth desc
          
          var eid=(tbl.rows[1].cells[9].innerHTML);       
         var eid1=(tbl.rows[arr[arr.length-1]].cells[9].innerHTML);
         //alert(eid+"    ===   "+eid1);
         
          var td_value=(tbl.rows[1].cells[9].innerHTML);
         var array=new Array();
         array=td_value.split(">");
         //alert(array[1]);
         var eid=new Array();
         var eid_year=array[1].split("<");
         //alert("ED YEAR = "+eid_year[0]);
         //alert("ED MONTH = "+eid_month[0]);
         //var top_Year=eid_year[0];
         var top_Year = new Date(eid_year[0]).getTime();
         //var top_Month=eid_month[0].substring(1, 4);
         //alert("top year  == "+eidYear);
         //alert("top month  == "+eidMonth);
         ////alert(eid[0]);
         
         
         var td_value=(tbl.rows[arr[arr.length-1]].cells[9].innerHTML);
         var array=new Array();
         array=td_value.split(">");
         //alert(array[1]);
         var eid=new Array();
         var eid_year=array[1].split("<");
         //var eid_month=array[2].split("<");
         //alert("ED YEAR = "+eid_year[0]);
         //alert("ED MONTH = "+eid_month[0]);
         //var bottom_Year=eid_year[0];
         var bottom_Year = new Date(eid_year[0]).getTime();
         
         //var bottom_month=eid_month[0].substring(1, 4);
         //alert("bottom year  == "+eidYear1);
         //alert("bottom month  == "+eidMonth1);
         
         
 
if (top_Year > bottom_Year) {
//alert("top year Date is big");
document.getElementById("order_desc").value="ORDER BY to_date(date_of_join_office,'dd/mm/yyyy') desc";
}
else if (top_Year < bottom_Year)
{
//alert("bottom year Date is big");
document.getElementById("order_desc").value="ORDER BY to_date(date_of_join_office,'dd/mm/yyyy') ASC";
}
else
{
//alert("equal...");
document.getElementById("order_desc").value=" order by total_service_yr desc ,  total_service_mnth desc";
}        
       
			return true;
         
         }
         
         
           if(header=="dateofretire")
         {
         // order by Service_Yr desc ,  Service_Mnth desc
          
          var eid=(tbl.rows[1].cells[10].innerHTML);       
         var eid1=(tbl.rows[arr[arr.length-1]].cells[10].innerHTML);
         //alert(eid+"    ===   "+eid1);
         
          var td_value=(tbl.rows[1].cells[10].innerHTML);
         var array=new Array();
         array=td_value.split(">");
         //alert(array[1]);
         var eid=new Array();
         var eid_year=array[1].split("<");
         //alert("ED YEAR = "+eid_year[0]);
         //alert("ED MONTH = "+eid_month[0]);
         //var top_Year=eid_year[0];
         var top_Year = new Date(eid_year[0]).getTime();
         //var top_Month=eid_month[0].substring(1, 4);
         //alert("top year  == "+eidYear);
         //alert("top month  == "+eidMonth);
         ////alert(eid[0]);
         
         
         var td_value=(tbl.rows[arr[arr.length-1]].cells[10].innerHTML);
         var array=new Array();
         array=td_value.split(">");
         //alert(array[1]);
         var eid=new Array();
         var eid_year=array[1].split("<");
         //var eid_month=array[2].split("<");
         //alert("ED YEAR = "+eid_year[0]);
         //alert("ED MONTH = "+eid_month[0]);
         //var bottom_Year=eid_year[0];
         var bottom_Year = new Date(eid_year[0]).getTime();
         
         //var bottom_month=eid_month[0].substring(1, 4);
         //alert("bottom year  == "+eidYear1);
         //alert("bottom month  == "+eidMonth1);
         
         
 
if (top_Year > bottom_Year) {
//alert("top year Date is big");
document.getElementById("order_desc").value="ORDER BY to_date(Retire_Date,'dd/mm/yyyy') desc";
}
else if (top_Year < bottom_Year)
{
//alert("bottom year Date is big");
document.getElementById("order_desc").value="ORDER BY to_date(Retire_Date,'dd/mm/yyyy') ASC";
}
else
{
//alert("equal...");
document.getElementById("order_desc").value=" order by total_service_yr desc ,  total_service_mnth desc";
}        
       
			return true;
}
    //emame va;odatopm    
         if(header.trim()=="Empname")
        {
        //alert("within Header..");
        
         var eid=(tbl.rows[1].cells[2].innerHTML);       
         var eid1=(tbl.rows[arr[arr.length-1]].cells[2].innerHTML);
        // var eid3=(tbl.rows[2].cells[3].innerHTML);
        
        //alert(eid+"    ===   "+eid1);
        
         orderBy(eid,eid1,header);
         
        

 
          	     	   // //alert("value   =====  "+document.getElementById("order_desc").value);
         }
         
         
         
         
         if(header.trim()=="Designation")
        {
        
        
         var eid=(tbl.rows[1].cells[3].innerHTML);
        
       
         var eid1=(tbl.rows[arr[arr.length-1]].cells[3].innerHTML);
        // var eid3=(tbl.rows[2].cells[3].innerHTML);
        //alert(eid+"    ===   "+eid1);
      
         orderBy(eid,eid1,header);
        

 
    
	     	        // //alert("value   =====  "+document.getElementById("order_desc").value);
         }
         
            

     } catch (e) {
         //alert(e);
     }
     return true;
  }
  
</script>


<script>

function orderBy(eid,eid1,header)
{
		  var ename = eid.localeCompare(eid1);
		  //alert(header);
          //alert(ename);
    if(header!="Designation")
    {
		    if(ename>0)
		    {
		    	document.getElementById("order_desc").value="order by "+header +" desc";
		    }    
		    else if(ename<0)
		    {
			    document.getElementById("order_desc").value="order by "+header +" asc";
			            
			}
		    else
			{
			    document.getElementById("order_desc").value=" order by Service_Yr desc ,  Service_Mnth desc";
			}
	}
	else if(header=="Designation")
	{
	
	        if(ename>0)
		    {
		    	document.getElementById("order_desc").value="order by "+header +" desc, Service_Yr desc ,  Service_Mnth desc";
		    }    
		    else if(ename<0)
		    {
			    document.getElementById("order_desc").value="order by "+header +" asc, Service_Yr desc ,  Service_Mnth desc";
			            
			}
		    else
			{
			    document.getElementById("order_desc").value=" order by Service_Yr desc ,  Service_Mnth desc";
			}
	}
		
}

</script>

	
	<script type="text/javascript" >
	
	
	var arr=new Array();
	
	$(document).ready(function() 
    { 
    
        $("#myTable").tablesorter( {sortList: [[0,0], [1,0]]} ); 
        
        
        $("#ecode").click(function(){
        
        var header = document.getElementById("ecode").headers;
        document.getElementById("ecode_color").color="#FFFBBA;";
        
        
    //    document.getElementById("ecode_color").color="";
        document.getElementById("empname_color").color="";
        document.getElementById("designation_color").color="";
        document.getElementById("tot_service_color").color="";
        document.getElementById("pf_service_color").color="";
        document.getElementById("service_color").color="";
        document.getElementById("dob_color").color="";
        document.getElementById("present_desi_color").color="";
        document.getElementById("work_color").color="";
        document.getElementById("retire_color").color="";
       
        document.getElementById("order_desc").value=header;
 
        
        });
        
        
        $("#ename").click(function(){
        
        var header = document.getElementById("ename").headers;
        
        
        document.getElementById("empname_color").color="#FFFBBA;";
        
          document.getElementById("ecode_color").color="";
        //document.getElementById("empname_color").color="";
        document.getElementById("designation_color").color="";
        document.getElementById("tot_service_color").color="";
        document.getElementById("pf_service_color").color="";
        document.getElementById("service_color").color="";
        document.getElementById("dob_color").color="";
        document.getElementById("present_desi_color").color="";
        document.getElementById("work_color").color="";
        document.getElementById("retire_color").color="";
        
       //alert(header);
        document.getElementById("order_desc").value=header;
        
        });
        
        
        
        
        
        
        
        $("#des").click(function(){
        
        var header = document.getElementById("des").headers;
        
        document.getElementById("designation_color").color="#FFFBBA;";
          document.getElementById("ecode_color").color="";
        document.getElementById("empname_color").color="";
        //document.getElementById("designation_color").color="";
        document.getElementById("tot_service_color").color="";
        document.getElementById("pf_service_color").color="";
        document.getElementById("service_color").color="";
        document.getElementById("dob_color").color="";
        document.getElementById("present_desi_color").color="";
        document.getElementById("work_color").color="";
        document.getElementById("retire_color").color="";
        
       ////alert(header);
        document.getElementById("order_desc").value=header;
        
        });
        
        
        $("#totser").click(function(){
        
        var header = document.getElementById("totser").headers;
        
        document.getElementById("tot_service_color").color="#FFFBBA;";
          document.getElementById("ecode_color").color="";
        document.getElementById("empname_color").color="";
        document.getElementById("designation_color").color="";
        //document.getElementById("tot_service_color").color="";
        document.getElementById("pf_service_color").color="";
        document.getElementById("service_color").color="";
        document.getElementById("dob_color").color="";
        document.getElementById("present_desi_color").color="";
        document.getElementById("work_color").color="";
        document.getElementById("retire_color").color="";
        
       //alert(header);
        document.getElementById("order_desc").value=header;
        
        });
        
        
        
        
        
        
        
        
        $("#totpfservice").click(function(){
        
        var header = document.getElementById("totpfservice").headers;
        
        document.getElementById("pf_service_color").color="#FFFBBA;";
          document.getElementById("ecode_color").color="";
        document.getElementById("empname_color").color="";
        document.getElementById("designation_color").color="";
        document.getElementById("tot_service_color").color="";
        //document.getElementById("pf_service_color").color="";
        document.getElementById("service_color").color="";
        document.getElementById("dob_color").color="";
        document.getElementById("present_desi_color").color="";
        document.getElementById("work_color").color="";
        document.getElementById("retire_color").color="";
        
       ////alert(header);
        document.getElementById("order_desc").value=header;
        
        });
        
        
        $("#yearofser").click(function(){
        
        var header = document.getElementById("yearofser").headers;
        
        document.getElementById("service_color").color="#FFFBBA;";
          document.getElementById("ecode_color").color="";
        document.getElementById("empname_color").color="";
        document.getElementById("designation_color").color="";
        document.getElementById("tot_service_color").color="";
        document.getElementById("pf_service_color").color="";
       // document.getElementById("service_color").color="";
        document.getElementById("dob_color").color="";
        document.getElementById("present_desi_color").color="";
        document.getElementById("work_color").color="";
        document.getElementById("retire_color").color="";
        
       ////alert(header);
        document.getElementById("order_desc").value=header;
        
        });
        
        
        
        
        
        
        $("#doj").click(function(){
        
        var header = document.getElementById("doj").headers;
        
        document.getElementById("dob_color").color="#FFFBBA;";
          document.getElementById("ecode_color").color="";
        document.getElementById("empname_color").color="";
        document.getElementById("designation_color").color="";
        document.getElementById("tot_service_color").color="";
        document.getElementById("pf_service_color").color="";
        document.getElementById("service_color").color="";
        //document.getElementById("dob_color").color="";
        document.getElementById("present_desi_color").color="";
        document.getElementById("work_color").color="";
        document.getElementById("retire_color").color="";
        
       ////alert(header);
        document.getElementById("order_desc").value=header;
        
        });
        
        
        $("#datefrom").click(function(){
        
        var header = document.getElementById("datefrom").headers;
        
        document.getElementById("present_desi_color").color="#FFFBBA;";
          document.getElementById("ecode_color").color="";
        document.getElementById("empname_color").color="";
        document.getElementById("designation_color").color="";
        document.getElementById("tot_service_color").color="";
        document.getElementById("pf_service_color").color="";
        document.getElementById("service_color").color="";
        document.getElementById("dob_color").color="";
        //document.getElementById("present_desi_color").color="";
        document.getElementById("work_color").color="";
        document.getElementById("retire_color").color="";
        
       ////alert(header);
        document.getElementById("order_desc").value=header;
        
        });
        
        
        
        
        
        $("#datefrompresent").click(function(){
        
        var header = document.getElementById("datefrompresent").headers;
        
        document.getElementById("work_color").color="#FFFBBA;";
          document.getElementById("ecode_color").color="";
        document.getElementById("empname_color").color="";
        document.getElementById("designation_color").color="";
        document.getElementById("tot_service_color").color="";
        document.getElementById("pf_service_color").color="";
        document.getElementById("service_color").color="";
        document.getElementById("dob_color").color="";
        document.getElementById("present_desi_color").color="";
        //document.getElementById("work_color").color="";
        document.getElementById("retire_color").color="";
        
       ////alert(header);
        document.getElementById("order_desc").value=header;
        
        });
        
        
        $("#retire").click(function(){
        
        var header = document.getElementById("retire").headers;
        
        document.getElementById("retire_color").color="#FFFBBA;";
          document.getElementById("ecode_color").color="";
        document.getElementById("empname_color").color="";
        document.getElementById("designation_color").color="";
        document.getElementById("tot_service_color").color="";
        document.getElementById("pf_service_color").color="";
        document.getElementById("service_color").color="";
        document.getElementById("dob_color").color="";
        document.getElementById("present_desi_color").color="";
        document.getElementById("work_color").color="";
        //document.getElementById("retire_color").color="";
        
       ////alert(header);
        document.getElementById("order_desc").value=header;
        
        });
        
         $("#generate_report_Excel1").click(function(){
         
         Generate_Report();
         
         document.getElementById("output").value='EXCEl';
		 document.frmTypeOfService.submit();
         
         });
        
        
        $("#generate_report_PDF1").click(function(){
        
        
        Generate_Report();
        
       
         
          document.getElementById("output").value='PDF';
		 document.frmTypeOfService.submit();
         
      
        
        
        //var xx = document.getElementById("eidvalue1").value;
        ////alert("valus dynamic == "+xx);
        
        
        });
        
        
         $("#generate_report_Excel").click(function(){
         
         Generate_Report();
         
         document.getElementById("output").value='EXCEl';
		 document.frmTypeOfService.submit();
         
         });
        
        
        $("#generate_report_PDF").click(function(){
        
        
        Generate_Report();
        
       
         
          document.getElementById("output").value='PDF';
		 document.frmTypeOfService.submit();
         
      
        
        
        //var xx = document.getElementById("eidvalue1").value;
        ////alert("valus dynamic == "+xx);
        
        
        });
        
        $("#ename").click(function(){
        var x = document.getElementById("ename").headers;
        ////alert("valu == "+x);
        });
        
        
        $("#des").click(function(){
        var x = document.getElementById("des").headers;
        ////alert("valu == "+x);
        });
        
        
        $("#totser").click(function(){
        var x = document.getElementById("totser").headers;
        ////alert("valu == "+x);
        });
        
        
        $("#totpfservice").click(function(){
        var x = document.getElementById("totpfservice").headers;
        ////alert("valu == "+x);
        });
        
        
        $("#yearofser").click(function(){
        var x = document.getElementById("yearofser").headers;
        ////alert("valu == "+x);
        });
        
        
        $("#doj").click(function(){
        var x = document.getElementById("doj").headers;
        ////alert("valu == "+x);
        });
        
        
        $("#datefrom").click(function(){
        var x = document.getElementById("datefrom").headers;
        ////alert("valu == "+x);
        });
        
        
        $("#datefrompresent").click(function(){
        var x = document.getElementById("datefrompresent").headers;
        ////alert("valu == "+x);
        });
        
        
        $("#retir").click(function(){
        var x = document.getElementById("retir").headers;
        //////alert("valu == "+x);
        });
        
       
        
    } 
); 


</script>            

	


            
</head>
<body>
<%
int kk=0;
String desigval = "", newdesigval = "";
String officeID="";

Connection connection = null;

try {

	LoadDriver driver=new LoadDriver();
	connection=driver.getConnection();
} catch (Exception ex) {
    String connectMsg =
        "Could not create the connection" + ex.getMessage() + " " +
        ex.getLocalizedMessage();
    System.out.println(connectMsg);
}

try {
    session = request.getSession(false);
    if (session == null) {
        System.out.println(request.getContextPath() + "/index.jsp");
        response.sendRedirect(request.getContextPath() + "/index.jsp");

    }
    System.out.println(session);

} catch (Exception e) {
    System.out.println("Redirect Error :" + e);
}

// JasperDesign jasperDesign = null;
File reportFile = null;

String offlevel = "";
String office = "";
String officetype = "";
String officeselected = "";
String designationlevel = "";
String designation = "";
String outputtype = "";
String ordertype = "";
String oflevel = "";
String hier = "";
String rank = "";
String order = "";
String order_desc = "", order_name = "";
Map map = null;
try {

    System.out.println("inside employee service details report");

    offlevel = request.getParameter("offlevel");
    designationlevel = request.getParameter("designationlevel");
    outputtype = request.getParameter("outputtype");
    ordertype = request.getParameter("ordertype");

    System.out.println("Office Level:" + offlevel);
    System.out.println("Designation  Level:" + designationlevel);
    System.out.println("Output Type:" + outputtype);
    System.out.println("Order Type:" + ordertype);

    designation = request.getParameter("designation");
    System.out.println("Designation  Level:" + designation);

    office = request.getParameter("office");
    System.out.println("Office Range Combo:" + office);

    officetype = request.getParameter("officetype");
    System.out.println("Office Type Option:" + officetype);

    officeselected = request.getParameter("officeselected");
    System.out.println("Office Selected:" + officeselected);

    oflevel = request.getParameter("rad_off");
    System.out.println("office level new..." + oflevel);


    hier = request.getParameter("allhier");
    System.out.println("include off hier:" + hier);

    rank = request.getParameter("rank");
    System.out.println("rank..." + rank);

  //  order = request.getParameter("order");
    System.out.println("order..." + order);

    order_desc = request.getParameter("order_desc");
    System.out.println("order_desc" + order_desc);


} catch (Exception e) {
    System.out.println("Assigning Error:" + e);
}

try {
    System.out.println("calling Employee Detail servlet");


    /*************************************************************************/
    /*  to get the office level  */
    session = request.getSession(false);
    UserProfile empProfile =
        (UserProfile)session.getAttribute("UserProfile");

    System.out.println("user id::" + empProfile.getEmployeeId());
    int empid = empProfile.getEmployeeId();
    int oid = 0;
    String deptid = "", officelevel = "";

    PreparedStatement ps = null;
    PreparedStatement ps1 = null;
    try {

    	
//    	if(officeselected.equalsIgnoreCase("all"))
//    		
//    	{
//    		
//    	}
//    	else
//    	{
        ps =
connection.prepareStatement("SELECT c.OFFICE_LEVEL_ID, " +
"  a.office_id, " +
"  OFF_ORDER " +
"FROM " +
"  (SELECT office_id " +
"  FROM " +
"    (SELECT office_id, " +
"      DEPARTMENT_ID " +
"    FROM hrm_emp_current_posting " +
"    WHERE employee_id=? " +
"    UNION " +
"    SELECT office_id, " +
"      DEPARTMENT_ID " +
"    FROM HRM_EMP_ADDL_CHARGE " +
"    WHERE employee_id=? " +
"    ) " +
"  WHERE DEPARTMENT_ID='TWAD' " +
"  )a " +
"LEFT OUTER JOIN " +
"  (SELECT OFFICE_ID, " +
"    OFF_ORDER, " +
"    OFFICE_LEVEL_ID " +
"  FROM COM_MST_ALL_OFFICES_VIEW " +
"  ORDER BY OFF_ORDER ASC " +
"  )c " +
"ON c.OFFICE_ID=a.OFFICE_ID " +
"WHERE rownum  =1");
        ps.setInt(1, empid);
          ps.setInt(2, empid);
        ResultSet results = ps.executeQuery();
        System.out.println("Employee id:" + empid);

        if (results.next()) {
            officelevel = results.getString("OFFICE_LEVEL_ID");
            oid = results.getInt("office_id");
        }
        results.close();
        ps.close();

        /* other office  */
        String profile = (String)session.getAttribute("profile");
        if (profile.equalsIgnoreCase("other")) {
            officelevel = "HO";
            ps =
connection.prepareStatement("select office_id from com_mst_offices where  office_level_id=? ");
            ps.setString(1, officelevel);
            results = ps.executeQuery();
            if (results.next()) {
                oid = results.getInt("office_id");
            }
        }

        /* other office  */
        System.out.println("office id:" + oid);
        System.out.println("dept id:" + deptid);

    } catch (Exception e) {
        System.out.println(e);
    }

    /*  to get the office level  */


    // order by  Office / Designation
    String offids = "";


    if (oflevel.equalsIgnoreCase("HO")) { //Head Office


        if (hier != null) {
            System.out.println("report 2.2");

            try {
                ps =
connection.prepareStatement("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW order by off_order");
                ResultSet results = null;
                results = ps.executeQuery();
                int i = 0;
                while (results.next()) {
                    offids += results.getInt("office_id") + ",";
                    i++;
                }
                // System.out.println(offids);
                if (i != 0) {
                    offids = offids.substring(0, offids.length() - 1);
                    System.out.println("office id in ho..." + offids);
                }

            } catch (Exception e) {
                System.out.println("error in all:" + e);
            }


        }

        else //if(hier==null)
        {
            System.out.println("report 2.1");
            offids = String.valueOf(oid);
            System.out.println("inside ho report 2.1..." + offids);
        }

    }

    //start for region
    else if (oflevel.equalsIgnoreCase("RG")) //Regin  Office
    {

        System.out.println("inside region");
        System.out.println("office level..." + officelevel);
        hier = request.getParameter("allhier");
        System.out.println("include off hier-1..." + hier);


        //if(hier.equalsIgnoreCase("include"))  // Region All
        if (hier != null) {
            System.out.println("report 3.1");
            if (officelevel.equalsIgnoreCase("HO")) {
                try {
                    ps =
connection.prepareStatement("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('RN','CL','DN','SD') and region_office_id in (" +
                      officeselected + ") order by off_order");
                    ResultSet results = null;
                    results = ps.executeQuery();
                    int i = 0;
                    oid = 0;
                    System.out.println("office id:" + offids);
                    while (results.next()) {
                        offids += results.getInt("office_id") + ",";
                        //offids=results.getInt("office_id")+",";
                        i++;
                    }
                    System.out.println("outside i" + offids);
                    if (i != 0) {
                        //offids+=oid;
                        offids =
                                offids.substring(0, offids.length() - 1);
                        System.out.println("in i..." + offids);
                        //offids=oid;
                    } else {
                        offids = String.valueOf(oid);
                    }


                } catch (Exception e) {
                    System.out.println("error in all:" + e);
                }

            }

            else if (officelevel.equalsIgnoreCase("RN")) {
                //offids=String.valueOf(oid);
                try {
                    ps =
connection.prepareStatement("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('RN','CL','DN','SD') and region_office_id in (" +
                      officeselected + ") order by off_order");
                    ResultSet results = null;
                    results = ps.executeQuery();
                    int i = 0;
                    oid = 0;
                    System.out.println("office id:" + offids);
                    while (results.next()) {
                        offids += results.getInt("office_id") + ",";
                        //offids=results.getInt("office_id")+",";
                        i++;
                    }
                    System.out.println("outside i" + offids);
                    if (i != 0) {
                        //offids+=oid;
                        offids =
                                offids.substring(0, offids.length() - 1);
                        System.out.println("in i..." + offids);
                        //offids=oid;
                    } else {
                        offids = String.valueOf(oid);
                    }


                } catch (Exception e) {
                    System.out.println("error in all:" + e);
                }
            }

        }

        else if (hier == null) // Region Specific
        {

            System.out.println("inside specific region");
            System.out.println("report 4.1");
            if (officelevel.equalsIgnoreCase("HO")) {
                try {
                    // from this it is real
                    //offids=officeselected+","+oid;
                    offids = officeselected;
                    System.out.println("region selected..." + offids);

                } catch (Exception e) {
                    System.out.println("error in all:" + e);
                }

            }

            else if (officelevel.equalsIgnoreCase("RN")) {
                offids = String.valueOf(oid);
            }


        }

    }
    //end for region

    //start for circle
    else if (oflevel.equalsIgnoreCase("CR")) { //Circle  Office
        //if(hier.equalsIgnoreCase("include"))  // Circle All
        if (hier != null) {
            System.out.println("report 5.1");
            String options = request.getParameter("regions");
            System.out.println("Region selected:" + options);


            if (officelevel.equalsIgnoreCase("HO")) {
                try {
                    ps =
connection.prepareStatement("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('CL','DN','SD') and circle_office_id in (" +
                      officeselected + ") order by off_order");
                    ResultSet results = null;
                    results = ps.executeQuery();
                    int i = 0;
                    oid = 0;
                    while (results.next()) {
                        offids += results.getInt("office_id") + ",";
                        i++;
                    }
                    System.out.println("offids..." + offids);
                    if (i != 0) {

                        offids =
                                offids.substring(0, offids.length() - 1);
                        System.out.println("in i circle..." + offids);

                        /*
                                            Statement st=connection.createStatement();
                                            ResultSet rs=null;
                                            String tempoffids=offids.substring(0,offids.length()-1);
                                            rs=st.executeQuery("select distinct REGION_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID in ("+tempoffids+")");
                                            int j=0;
                                            while(rs.next())
                                            {
	                                               offids+=rs.getInt("REGION_OFFICE_ID")+",";
	                                               j++;
                                            }
                                            offids+=oid;

                                           */

                    } else {
                        offids = String.valueOf(oid);
                    }


                } catch (Exception e) {
                    System.out.println("error in all:" + e);
                }

            } else if (officelevel.equalsIgnoreCase("RN")) {
                System.out.println("inside region");
                officeselected =
                        request.getParameter("officeselected");
                System.out.println("officeselected..." +
                                   officeselected);
                try {
                    ps =
connection.prepareStatement("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('CL','DN','SD') and circle_office_id in (" +
                      officeselected + ") order by off_order");
                    //ps.setInt(1,oid);
                    ResultSet results = null;
                    results = ps.executeQuery();
                    int i = 0;
                    oid = 0;
                    while (results.next()) {
                        offids += results.getInt("office_id") + ",";
                        i++;
                    }
                    // System.out.println(offids);
                    if (i != 0) {
                        offids += oid;
                    } else {
                        offids = String.valueOf(oid);
                    }

                } catch (Exception e) {
                    System.out.println("error in all:" + e);
                }
            } else if (officelevel.equalsIgnoreCase("CL")) {
                //offids=String.valueOf(oid);
                try {
                    ps =
connection.prepareStatement("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('CL','DN','SD') and circle_office_id in (" +
                      officeselected + ") order by off_order");
                    //ps.setInt(1,oid);
                    ResultSet results = null;
                    results = ps.executeQuery();
                    int i = 0;
                    oid = 0;
                    while (results.next()) {
                        offids += results.getInt("office_id") + ",";
                        i++;
                    }
                    // System.out.println(offids);
                    if (i != 0) {
                        offids += oid;
                    } else {
                        offids = String.valueOf(oid);
                    }

                } catch (Exception e) {
                    System.out.println("error in all:" + e);
                }
            }

        } else if (hier == null) // Circle Specific
        {
            System.out.println("report 6.1");
            if (officelevel.equalsIgnoreCase("HO")) {
                try {

                    Statement st = connection.createStatement();
                    ResultSet rs = null;
                    rs =
st.executeQuery("select distinct CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID in (" +
          officeselected + ")");
                    int j = 0;
                    while (rs.next()) {
                        offids += rs.getInt("CIRCLE_OFFICE_ID") + ",";
                        j++;
                    }
                    //offids+=officeselected+","+oid;  from this it is real
                    offids += officeselected;
                } catch (Exception e) {
                    System.out.println("error in all:" + e);
                }

            } else if (officelevel.equalsIgnoreCase("RN")) {
                try {
                    // offids=officeselected+","+oid; from this it is real
                    offids = officeselected;
                } catch (Exception e) {
                    System.out.println("error in all:" + e);
                }
            } else if (officelevel.equalsIgnoreCase("CL")) {
                offids = String.valueOf(oid);
            }


        }

    }


    else if (oflevel.equalsIgnoreCase("DV")) {

        if (hier != null) {
            System.out.println("report 7.1");

            System.out.println("aggre  yes All");

            if (officelevel.equalsIgnoreCase("HO")) {
                System.out.println("ho");
                try {
                    ps =
connection.prepareStatement("select distinct OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID='DN' and OFFICE_ID in (" +
                      officeselected + ")");
                    ResultSet results = null;
                    results = ps.executeQuery();
                    int i = 0;
                    while (results.next()) {
                        offids += results.getInt("office_id") + ",";
                        i++;
                    }

                    if (i != 0) {


                        Statement st = connection.createStatement();
                        ResultSet rs = null;
                        String tempoffids =
                            offids.substring(0, offids.length() - 1);

                        rs =
st.executeQuery("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where DIVISION_OFFICE_ID in (" +
          tempoffids +
          ") and OFFICE_LEVEL_ID='SD' order by off_order");
                        while (rs.next()) {
                            offids += rs.getInt("OFFICE_ID") + ",";

                        }


                        offids =
                                offids.substring(0, offids.length() - 1);
                        System.out.println("in subdivision..." +
                                           offids);

                    } else {
                        offids = String.valueOf(oid);
                    }


                } catch (Exception e) {
                    System.out.println("error in all:" + e);
                }

            } else if (officelevel.equalsIgnoreCase("RN")) {
                System.out.println("rn");
                try {

                    ps =
connection.prepareStatement("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('DN','SD') and  DIVISION_OFFICE_ID in (" +
                      officeselected + ") order by off_order");
                    ResultSet results = null;
                    results = ps.executeQuery();
                    int i = 0;
                    while (results.next()) {
                        offids += results.getInt("office_id") + ",";
                        i++;
                    }
                    if (i != 0) {
                        offids =
                                offids.substring(0, offids.length() - 1);
                        System.out.println("in i division1...." +
                                           offids);

                    } else {
                        offids = String.valueOf(oid);
                    }

                } catch (Exception e) {
                    System.out.println("error in all:" + e);
                }
            } else if (officelevel.equalsIgnoreCase("CL")) {
                System.out.println("cl");
                try {

                    ps =
connection.prepareStatement("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('DN','SD') and  DIVISION_OFFICE_ID in (" +
                      officeselected + ") order by off_order");
                    ResultSet results = null;
                    results = ps.executeQuery();
                    int i = 0;
                    while (results.next()) {
                        offids += results.getInt("office_id") + ",";
                        i++;
                    }
                    if (i != 0) {

                        offids =
                                offids.substring(0, offids.length() - 1);
                        System.out.println("in circle division...." +
                                           offids);
                    } else {
                        offids = String.valueOf(oid);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (officelevel.equalsIgnoreCase("DN")) {
                System.out.println("dn");

                ps =
connection.prepareStatement("select distinct OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where DIVISION_OFFICE_ID =? and OFFICE_LEVEL_ID='SD'");
                ps.setInt(1, oid);
                ResultSet rs = ps.executeQuery();
                int i = 0;
                while (rs.next()) {
                    offids += rs.getInt("OFFICE_ID") + ",";
                    i++;

                }
                if (i != 0) {
                    offids += oid;
                } else {
                    offids = String.valueOf(oid);
                }
            }


        } else if (hier == null) {
            System.out.println("report is 8.1");
            if (officelevel.equalsIgnoreCase("HO")) {
                try {

                    Statement st = connection.createStatement();
                    ResultSet rs = null;
                    rs =
st.executeQuery("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW " +
          " where" + " division_OFFICE_ID in (" + officeselected +
          ")" + " and OFFICE_LEVEL_ID in ('DN') order by off_order");
                    int i = 0;
                    while (rs.next()) {
                        offids += rs.getInt("OFFICE_ID") + ",";
                        i++;
                    }
                    if (i != 0) {

                        offids =
                                offids.substring(0, offids.length() - 1);
                        System.out.println("offids in divi..." +
                                           offids);


                    } else {

                        st = connection.createStatement();
                        rs =
st.executeQuery("select distinct OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where DIVISION_OFFICE_ID in (" +
          officeselected + ") and OFFICE_LEVEL_ID='SD'");
                        while (rs.next()) {
                            offids += rs.getInt("OFFICE_ID") + ",";

                        }
                        // offids+=officeselected+","+oid;  from this it is real
                        offids += officeselected;
                    }
                } catch (Exception e) {
                    System.out.println("error in all:" + e);
                }

            } else if (officelevel.equalsIgnoreCase("RN")) {
                try {


                    Statement st = connection.createStatement();
                    ResultSet rs = null;

                    rs =
st.executeQuery("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW " +
          " where" + " division_OFFICE_ID in (" + officeselected +
          ")" + " and OFFICE_LEVEL_ID in ('DN') order by off_order");
                    int i = 0;

                    while (rs.next()) {
                        offids += rs.getInt("OFFICE_ID") + ",";
                        i++;
                    }

                    if (i != 0) {

                        offids =
                                offids.substring(0, offids.length() - 1);
                        System.out.println("offids in divi..." +
                                           offids);
                    }

                    else {

                        st = connection.createStatement();
                        rs =
st.executeQuery("select distinct OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where DIVISION_OFFICE_ID in (" +
          officeselected + ") and OFFICE_LEVEL_ID='SD'");
                        while (rs.next()) {
                            offids += rs.getInt("OFFICE_ID") + ",";

                        }
                        // offids+=officeselected+","+oid;  from this it is real
                        offids += officeselected;
                    }


                } catch (Exception e) {
                    System.out.println("error in all:" + e);
                }
            } else if (officelevel.equalsIgnoreCase("CL")) {


                Statement st = connection.createStatement();
                ResultSet rs = null;

                rs =
st.executeQuery("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW " +
          " where" + " division_OFFICE_ID in (" + officeselected +
          ")" + " and OFFICE_LEVEL_ID in ('DN') order by off_order");
                int i = 0;

                while (rs.next()) {
                    offids += rs.getInt("OFFICE_ID") + ",";
                    i++;
                }

                if (i != 0) {

                    offids = offids.substring(0, offids.length() - 1);
                    System.out.println("offids in divi..." + offids);
                }

                else {

                    st = connection.createStatement();
                    rs =
st.executeQuery("select distinct OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where DIVISION_OFFICE_ID in (" +
          officeselected + ") and OFFICE_LEVEL_ID='SD'");
                    while (rs.next()) {
                        offids += rs.getInt("OFFICE_ID") + ",";

                    }
                    // offids+=officeselected+","+oid;  from this it is real
                    offids += officeselected;
                }


            } else if (officelevel.equalsIgnoreCase("DN")) {

                ps =
connection.prepareStatement("select distinct OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where DIVISION_OFFICE_ID =? and OFFICE_LEVEL_ID='SD'");
                ps.setInt(1, oid);
                ResultSet rs = ps.executeQuery();
                int i = 0;
                while (rs.next()) {
                    offids += rs.getInt("OFFICE_ID") + ",";
                    i++;

                }
                if (i != 0) {
                    offids += oid;
                } else {
                    offids = String.valueOf(oid);
                }
            }


        }


    }


    String emp_id = "";

    try {
        ResultSet rs = null;
        Statement st = connection.createStatement();
        String sql =
            "select employee_id from hrm_emp_current_posting where employee_status_id='WKG' ";
        if (rank.equals("AE"))
            sql = sql + " and designation_id in(13,14,15)";
        else if (rank.equals("AEE"))
            sql = sql + " and designation_id in(10,12,11)";
        else
            sql = sql + " and designation_id in(7,8,9)";
        rs = st.executeQuery(sql);
        int i = 0;
        while (rs.next()) {
            emp_id += rs.getInt("employee_id") + ",";
            i++;
        }
        if (i != 0) {

            emp_id = emp_id.substring(0, emp_id.length() - 1);
            System.out.println("Employee Id..." + emp_id);
        }
    } catch (Exception e) {
        System.out.println("Err in rank selection:" + e.getMessage());
    }


    try {
        System.out.println(" Inside try");
        String optbase = request.getParameter("optselect");
        System.out.println("Options Selected:" + optbase);
        String optbase1 = request.getParameter("optselectgrp");
        System.out.println("Options Selected:" + optbase1);
        map = new HashMap();
        String rad_off = request.getParameter("rad_off");
        System.out.println("rad_off Selected:" + rad_off);
        System.out.println("Office Selected:" + officeselected);
        System.out.println(" outside condition");
        
        String rep_type=request.getParameter("report_type");
        System.out.println("rep_type : "+rep_type);
        String ranks=request.getParameter("rank");
       
        

        if (hier != null) {
            System.out.println("check");
            System.out.println("offids" + offids);
            map.put("offids", offids);
            officeID=offids;

        } else if (hier == null) {

            if (oflevel.equalsIgnoreCase("ho")) {
                map.put("offids", offids);
                officeID=offids;
            }

            else {
                System.out.println("uncheck");
                map.put("offids", officeselected);
                officeID=officeselected;

            }
        } else {
            System.out.println("othter");
            System.out.println("office ids:" + offids);
            System.out.println("desig ids:" + newdesigval);
            map.put("offids", offids);
            officeID=offids;
        }
        System.out.println(" emp_id:::" + emp_id);
        map.put("emp_id", emp_id);
        

        map.put("order_type", order_name);
        if(rank.equals("AE"))
        {
        	  System.out.println("inside if loop for AE");
        	rank="AE/JE";
        }
        else
        {
        	System.out.println("inside else part for AE");
        	rank=request.getParameter("rank");
        }
        
        System.out.println("final rank parameter for report" + rank);
        map.put("sel_design", rank);
        System.out.println(" order_type:::" + order_name);


        


        String rtype = request.getParameter("outputtype");
        
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }


} catch (Exception ex) {
    String connectMsg =
        "Could not create the report " + ex.getMessage() + " " +
        ex.getLocalizedMessage();
    System.out.println(connectMsg);
}


String [] service;
String typeofservice="",printService="";
service=request.getParameterValues("service");
System.out.println(service.length);
for(int k=0;k<service.length;k++)
{
	typeofservice=typeofservice+"'"+service[k]+"',";
	
	if(service[k].equalsIgnoreCase("RL"))
		printService=printService+"RWS,";
	else if(service[k].equalsIgnoreCase("UR"))
		printService=printService+"Urban, ";
	else if(service[k].equalsIgnoreCase("SW"))
		printService=printService+"Sewarage, ";
	else if(service[k].equalsIgnoreCase("MN"))
		printService=printService+"Maintenance, ";
	else if(service[k].equalsIgnoreCase("DN"))
		printService=printService+"Deputation, ";
	else
		printService=printService+service[k] + " , ";
	
	System.out.println(typeofservice);
}
typeofservice=typeofservice.substring(0, typeofservice.length() - 1);
printService=printService.trim();
printService=printService.substring(0, printService.length() - 1);
System.out.println(typeofservice);

String rType=request.getParameter("rType");
String yeartype=request.getParameter("yeartype");
String nyear=request.getParameter("nyear");
String rad_off=request.getParameter("rad_off");
String ranks=request.getParameter("rank");
order_desc=request.getParameter("order_desc");
String Condition="",Condition1="",Condition3="",OrderBy="";

if(rType.equalsIgnoreCase("PDESIGN"))
{	
	Condition="AND I.DESIGNATION_ID = (SELECT J.Designation_Id FROM Hrm_Emp_Current_Posting J WHERE I.EMPLOYEE_ID=J.EMPLOYEE_ID)   ";
}


System.out.println(officeID); 
if(yeartype.equalsIgnoreCase("All") && officeID.equalsIgnoreCase("all")){
	System.out.println("1"); 

	Condition1="";
}
else if(yeartype.equalsIgnoreCase("All") && !officeID.equalsIgnoreCase("all")){
	System.out.println("2"); 
	
	Condition1=" where  Office_Id IN ("+officeID+") ";
}
else if(!yeartype.equalsIgnoreCase("All") && officeID.equalsIgnoreCase("all")){
	System.out.println("2"); 
	int year=Integer.parseInt(nyear);
	Condition1=" where  (Service_Yr > "+year+" or (Service_Yr ="+year+"  and Service_Mnth >0))";
}
else if(!yeartype.equalsIgnoreCase("All") && !officeID.equalsIgnoreCase("all")){
	System.out.println("3"); 
	int year=Integer.parseInt(nyear);
	Condition1=" where Office_Id IN ("+officeID+") and  (Service_Yr > "+year+" or (Service_Yr ="+year+"  and Service_Mnth >0))";
}

System.out.println("Condition1 : "+Condition1);
	OrderBy=" order by Service_Yr "+order_desc+" ,  Service_Mnth "+order_desc+"";

String query="";
if(rType.equalsIgnoreCase("POFFICE")){

if(ranks.equalsIgnoreCase("EE"))
{
	query="SELECT Employee_Id, " +
			"  Empname, " +
			"  Office_Name, " +
			"  Designation_Id, " +
			"  Designation, " +
			"  DECODE(Total_Service_Yr,NULL,' ' ,TO_CHAR(Total_Service_Yr,'00') " +
			"  || ' Year') AS Total_Service_Yr, " +
			"  DECODE(Total_Service_Mnth,NULL,' ' ,TO_CHAR(Total_Service_Mnth,'00') " +
			"  || ' Month') AS Total_Service_Mnth, " +
			"  DECODE(Pf_Office_Yr,NULL,' ' ,TO_CHAR(Pf_Office_Yr,'00') " +
			"  || ' Year') AS Pf_Office_Yr, " +
			"  DECODE(Pf_Office_Month,NULL,' ' ,TO_CHAR(Pf_Office_Month,'00') " +
			"  || ' Month') AS Pf_Office_Month, " +
			"  DECODE(Service_Yr,NULL,' ' ,TO_CHAR(Service_Yr,'00') " +
			"  || ' Year') AS Serviceyear, " +
			"  DECODE(Service_Mnth,NULL,' ' ,TO_CHAR(Service_Mnth,'00') " +
			"  || ' Month')                                                                  AS serviceMonth, " +
			"  NVL(Service_Yr,0)                                                             AS Service_Yr, " +
			"  NVL(Service_Mnth,0)                                                           AS Service_Mnth, " +
			"  TO_CHAR(Hrm_Emp_Get_Desig_Join_Date(Employee_Id,Designation_Id),'dd/mm/yyyy') AS Join_Date_Design, " +
			"  TO_CHAR(Hrm_Emp_Get_Board_Join_Date(Employee_Id),'dd/mm/yyyy')                AS Boar_Join_Date , " +
			"  DECODE(Hrm_Emp_Get_Retire_Date(Employee_Id),NULL,'Update Date of Birth',TO_CHAR(Hrm_Emp_Get_Retire_Date(Employee_Id),'dd/mm/yyyy'))                      AS Retire_Date,date_of_join_office " +
			"  FROM " +
			"  (SELECT A.Employee_Id, " +
			"    A.Service, " +
			"    Empname, " +
			"    C.Office_Id, " +
			"    DECODE(Office_Name,NULL,Employee_Status_Desc,Office_Name) AS Office_Name, " +
			"    Employee_Status_Id, " +
			"    C.Designation_Id, " +
			"    E.Designation, " +
			"    Service_Yr, " +
			"    Service_Mnth, " +
			"    Total_Service, " +
			"    Total_Service_Yr, " +
			"    Total_Service_Mnth, " +
			"    Pfservice, " +
			"    Pf_Office_Yr, " +
			"    Pf_Office_Month,date_of_join_office " +
			"  FROM " +
			"    (SELECT Employee_Id, " +
			"      SUM(Total_Days)                       AS Service, " +
			"      Floor(SUM(Total_Days)          /365)  AS Service_Yr , " +
			"      Floor(Mod(SUM(Total_Days),365) /30.5) AS Service_Mnth " +
			"    FROM HRM_CALC_TYPEOFSERVICE_EE_CUR I " +
			"    WHERE I.Primary_Work_Id IN ("+typeofservice+")   "+Condition+"  " +
			"    GROUP BY Employee_Id " +
			"    )A " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      Employee_Name " +
			"      || ' . ' " +
			"      || Employee_Initial AS Empname " +
			"    FROM Hrm_Mst_Employees " +
			"    )B " +
			"  ON A.Employee_Id=B.Employee_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      Office_Id,to_char(date_effective_from,'dd/mm/yyyy') as date_of_join_office, " +
			"      Designation_Id, " +
			"      EMPLOYEE_STATUS_ID " +
			"    FROM Hrm_Emp_Current_Posting " +
			"    )C " +
			"  ON A.Employee_Id=C.Employee_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Office_Id,Office_Name FROM Com_Mst_Offices " +
			"    ) D " +
			"  ON C.Office_Id=D.Office_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Designation_Id,Designation FROM Hrm_Mst_Designations " +
			"    )E " +
			"  ON C.Designation_Id=E.Designation_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Status_Id,Employee_Status_Desc FROM Hrm_Mst_Employee_Status " +
			"    ) F " +
			"  ON F.Employee_Status_Id=C.Employee_Status_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      SUM(Total_Days)                       AS Total_Service, " +
			"      Floor(SUM(Total_Days)          /365)  AS Total_Service_Yr , " +
			"      Floor(Mod(SUM(Total_Days),365) /30.5) AS Total_Service_Mnth " +
			"    FROM HRM_CALC_TYPEOFSERVICE_EE_CUR " +
			"    GROUP BY Employee_Id " +
			"    )G " +
			"  ON A.Employee_Id=G.Employee_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      SUM(Total_Days)                       AS Pfservice, " +
			"      Floor(SUM(Total_Days)          /365)  AS Pf_Office_Yr , " +
			"      Floor(Mod(SUM(Total_Days),365) /30.5) AS Pf_Office_Month " +
			"    FROM HRM_CALC_TYPEOFSERVICE_EE_CUR " +
			"    WHERE Primary_Work_Id IN ('HO','Office','PF') " +
			"    GROUP BY Employee_Id " +
			"    )H " +
			"  ON A.EMPLOYEE_ID=H.EMPLOYEE_ID " +
			"  ) " +
			" "+Condition1+" ";
}
else if(ranks.equalsIgnoreCase("AEE"))
{
	query="SELECT Employee_Id, " +
			"  Empname, " +
			"  Office_Name, " +
			"  Designation_Id, " +
			"  Designation, " +
			"  DECODE(Total_Service_Yr,NULL,' ' ,TO_CHAR(Total_Service_Yr,'00') " +
			"  || ' Year') AS Total_Service_Yr, " +
			"  DECODE(Total_Service_Mnth,NULL,' ' ,TO_CHAR(Total_Service_Mnth,'00') " +
			"  || ' Month') AS Total_Service_Mnth, " +
			"  DECODE(Pf_Office_Yr,NULL,' ' ,TO_CHAR(Pf_Office_Yr,'00') " +
			"  || ' Year') AS Pf_Office_Yr, " +
			"  DECODE(Pf_Office_Month,NULL,' ' ,TO_CHAR(Pf_Office_Month,'00') " +
			"  || ' Month') AS Pf_Office_Month, " +
			"  DECODE(Service_Yr,NULL,' ' ,TO_CHAR(Service_Yr,'00') " +
			"  || ' Year') AS Serviceyear, " +
			"  DECODE(Service_Mnth,NULL,' ' ,TO_CHAR(Service_Mnth,'00') " +
			"  || ' Month')                                                                  AS serviceMonth, " +
			"  NVL(Service_Yr,0)                                                             AS Service_Yr, " +
			"  NVL(Service_Mnth,0)                                                           AS Service_Mnth, " +
			"  TO_CHAR(Hrm_Emp_Get_Desig_Join_Date(Employee_Id,Designation_Id),'dd/mm/yyyy') AS Join_Date_Design, " +
			"  TO_CHAR(Hrm_Emp_Get_Board_Join_Date(Employee_Id),'dd/mm/yyyy')                AS Boar_Join_Date , " +
			"  DECODE(Hrm_Emp_Get_Retire_Date(Employee_Id),NULL,'Update Date of Birth',TO_CHAR(Hrm_Emp_Get_Retire_Date(Employee_Id),'dd/mm/yyyy'))                        AS Retire_Date,date_of_join_office " +
			"  FROM " +
			"  (SELECT A.Employee_Id, " +
			"    A.Service, " +
			"    Empname, " +
			"    C.Office_Id, " +
			"    DECODE(Office_Name,NULL,Employee_Status_Desc,Office_Name) AS Office_Name, " +
			"    Employee_Status_Id, " +
			"    C.Designation_Id, " +
			"    E.Designation, " +
			"    Service_Yr, " +
			"    Service_Mnth, " +
			"    Total_Service, " +
			"    Total_Service_Yr, " +
			"    Total_Service_Mnth, " +
			"    Pfservice, " +
			"    Pf_Office_Yr, " +
			"    Pf_Office_Month,date_of_join_office " +
			"  FROM " +
			"    (SELECT Employee_Id, " +
			"      SUM(Total_Days)                       AS Service, " +
			"      Floor(SUM(Total_Days)          /365)  AS Service_Yr , " +
			"      Floor(Mod(SUM(Total_Days),365) /30.5) AS Service_Mnth " +
			"    FROM HRM_CALC_TYPEOFSERVICE_AEE_CUR I " +
			"    WHERE I.Primary_Work_Id IN ("+typeofservice+")  "+Condition+"  " +
			"    GROUP BY Employee_Id " +
			"    )A " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      Employee_Name " +
			"      || ' . ' " +
			"      || Employee_Initial AS Empname " +
			"    FROM Hrm_Mst_Employees " +
			"    )B " +
			"  ON A.Employee_Id=B.Employee_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      Office_Id,to_char(date_effective_from,'dd/mm/yyyy') as date_of_join_office, " +
			"      Designation_Id, " +
			"      EMPLOYEE_STATUS_ID " +
			"    FROM Hrm_Emp_Current_Posting " +
			"    )C " +
			"  ON A.Employee_Id=C.Employee_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Office_Id,Office_Name FROM Com_Mst_Offices " +
			"    ) D " +
			"  ON C.Office_Id=D.Office_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Designation_Id,Designation FROM Hrm_Mst_Designations " +
			"    )E " +
			"  ON C.Designation_Id=E.Designation_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Status_Id,Employee_Status_Desc FROM Hrm_Mst_Employee_Status " +
			"    ) F " +
			"  ON F.Employee_Status_Id=C.Employee_Status_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      SUM(Total_Days)                       AS Total_Service, " +
			"      Floor(SUM(Total_Days)          /365)  AS Total_Service_Yr , " +
			"      Floor(Mod(SUM(Total_Days),365) /30.5) AS Total_Service_Mnth " +
			"    FROM HRM_CALC_TYPEOFSERVICE_AEE_CUR " +
			"    GROUP BY Employee_Id " +
			"    )G " +
			"  ON A.Employee_Id=G.Employee_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      SUM(Total_Days)                       AS Pfservice, " +
			"      Floor(SUM(Total_Days)          /365)  AS Pf_Office_Yr , " +
			"      Floor(Mod(SUM(Total_Days),365) /30.5) AS Pf_Office_Month " +
			"    FROM HRM_CALC_TYPEOFSERVICE_AEE_CUR " +
			"    WHERE Primary_Work_Id IN ('HO','Office','PF') " +
			"    GROUP BY Employee_Id " +
			"    )H " +
			"  ON A.EMPLOYEE_ID=H.EMPLOYEE_ID " +
			"  ) " +
			"  "+Condition1+" ";
}
else
{
	query="SELECT Employee_Id, " +
			"  Empname, " +
			"  Office_Name, " +
			"  Designation_Id, " +
			"  Designation, " +
			"  DECODE(Total_Service_Yr,NULL,' ' ,TO_CHAR(Total_Service_Yr,'00') " +
			"  || ' Year') AS Total_Service_Yr, " +
			"  DECODE(Total_Service_Mnth,NULL,' ' ,TO_CHAR(Total_Service_Mnth,'00') " +
			"  || ' Month') AS Total_Service_Mnth, " +
			"  DECODE(Pf_Office_Yr,NULL,' ' ,TO_CHAR(Pf_Office_Yr,'00') " +
			"  || ' Year') AS Pf_Office_Yr, " +
			"  DECODE(Pf_Office_Month,NULL,' ' ,TO_CHAR(Pf_Office_Month,'00') " +
			"  || ' Month') AS Pf_Office_Month, " +
			"  DECODE(Service_Yr,NULL,' ' ,TO_CHAR(Service_Yr,'00') " +
			"  || ' Year') AS Serviceyear, " +
			"  DECODE(Service_Mnth,NULL,' ' ,TO_CHAR(Service_Mnth,'00') " +
			"  || ' Month')                                                                  AS serviceMonth, " +
			"  NVL(Service_Yr,0)                                                             AS Service_Yr, " +
			"  NVL(Service_Mnth,0)                                                           AS Service_Mnth, " +
			"  TO_CHAR(Hrm_Emp_Get_Desig_Join_Date(Employee_Id,Designation_Id),'dd/mm/yyyy') AS Join_Date_Design, " +
			"  TO_CHAR(Hrm_Emp_Get_Board_Join_Date(Employee_Id),'dd/mm/yyyy')                AS Boar_Join_Date , " +
			"  DECODE(Hrm_Emp_Get_Retire_Date(Employee_Id),NULL,'Update Date of Birth',TO_CHAR(Hrm_Emp_Get_Retire_Date(Employee_Id),'dd/mm/yyyy'))                AS Retire_Date,date_of_join_office " +
			"  FROM " +
			"  (SELECT A.Employee_Id, " +
			"    A.Service, " +
			"    Empname, " +
			"    C.Office_Id, " +
			"    DECODE(Office_Name,NULL,Employee_Status_Desc,Office_Name) AS Office_Name, " +
			"    Employee_Status_Id, " +
			"    C.Designation_Id, " +
			"    E.Designation, " +
			"    Service_Yr, " +
			"    Service_Mnth, " +
			"    Total_Service, " +
			"    Total_Service_Yr, " +
			"    Total_Service_Mnth, " +
			"    Pfservice, " +
			"    Pf_Office_Yr, " +
			"    Pf_Office_Month,date_of_join_office " +
			"  FROM " +
			"    (SELECT Employee_Id, " +
			"      SUM(Total_Days)                       AS SERVICE, " +
			"      floor(SUM(Total_Days)          /365)  AS SERVICE_YR , " +
			"      floor(mod(SUM(Total_Days),365) /30.5) AS SERVICE_MNTH " +
			"    FROM HRM_CALC_TYPEOFSERVICE_AE_CUR " +
			"    WHERE Primary_Work_Id IN ("+typeofservice+")  " +
			"    GROUP BY Employee_Id " +
			"    )A " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      Employee_Name " +
			"      || ' . ' " +
			"      || Employee_Initial AS Empname " +
			"    FROM Hrm_Mst_Employees " +
			"    )B " +
			"  ON A.Employee_Id=B.Employee_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      Office_Id,to_char(date_effective_from,'dd/mm/yyyy') as date_of_join_office, " +
			"      Designation_Id, " +
			"      EMPLOYEE_STATUS_ID " +
			"    FROM Hrm_Emp_Current_Posting " +
			"    )C " +
			"  ON A.Employee_Id=C.Employee_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Office_Id,Office_Name FROM Com_Mst_Offices " +
			"    ) D " +
			"  ON C.Office_Id=D.Office_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Designation_Id,Designation FROM Hrm_Mst_Designations " +
			"    )E " +
			"  ON C.Designation_Id=E.Designation_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Status_Id,Employee_Status_Desc FROM Hrm_Mst_Employee_Status " +
			"    ) F " +
			"  ON F.Employee_Status_Id=C.Employee_Status_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      SUM(Total_Days)                       AS Total_Service, " +
			"      Floor(SUM(Total_Days)          /365)  AS Total_Service_Yr , " +
			"      Floor(Mod(SUM(Total_Days),365) /30.5) AS Total_Service_Mnth " +
			"    FROM HRM_CALC_TYPEOFSERVICE_AE_CUR " +
			"    GROUP BY Employee_Id " +
			"    )G " +
			"  ON A.Employee_Id=G.Employee_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      SUM(Total_Days)                       AS Pfservice, " +
			"      Floor(SUM(Total_Days)          /365)  AS Pf_Office_Yr , " +
			"      Floor(Mod(SUM(Total_Days),365) /30.5) AS Pf_Office_Month " +
			"    FROM HRM_CALC_TYPEOFSERVICE_AE_CUR " +
			"    WHERE Primary_Work_Id IN ('HO','Office','PF') " +
			"    GROUP BY Employee_Id " +
			"    )H " +
			"  ON A.EMPLOYEE_ID=H.EMPLOYEE_ID " +
			"  ) " +
			" "+Condition1+" ";
}

}
else
{
if(ranks.equalsIgnoreCase("EE"))
{
	query="SELECT Employee_Id, " +
			"  Empname, " +
			"  Office_Name, " +
			"  Designation_Id, " +
			"  Designation, " +
			"  DECODE(Total_Service_Yr,NULL,' ' ,TO_CHAR(Total_Service_Yr,'00') " +
			"  || ' Year') AS Total_Service_Yr, " +
			"  DECODE(Total_Service_Mnth,NULL,' ' ,TO_CHAR(Total_Service_Mnth,'00') " +
			"  || ' Month') AS Total_Service_Mnth, " +
			"  DECODE(Pf_Office_Yr,NULL,' ' ,TO_CHAR(Pf_Office_Yr,'00') " +
			"  || ' Year') AS Pf_Office_Yr, " +
			"  DECODE(Pf_Office_Month,NULL,' ' ,TO_CHAR(Pf_Office_Month,'00') " +
			"  || ' Month') AS Pf_Office_Month, " +
			"  DECODE(Service_Yr,NULL,' ' ,TO_CHAR(Service_Yr,'00') " +
			"  || ' Year') AS Serviceyear, " +
			"  DECODE(Service_Mnth,NULL,' ' ,TO_CHAR(Service_Mnth,'00') " +
			"  || ' Month')                                                                  AS serviceMonth, " +
			"  NVL(Service_Yr,0)                                                             AS Service_Yr, " +
			"  NVL(Service_Mnth,0)                                                           AS Service_Mnth, " +
			"  TO_CHAR(Hrm_Emp_Get_Desig_Join_Date(Employee_Id,Designation_Id),'dd/mm/yyyy') AS Join_Date_Design, " +
			"  TO_CHAR(Hrm_Emp_Get_Board_Join_Date(Employee_Id),'dd/mm/yyyy')                AS Boar_Join_Date , " +
			"  DECODE(Hrm_Emp_Get_Retire_Date(Employee_Id),NULL,'Update Date of Birth',TO_CHAR(Hrm_Emp_Get_Retire_Date(Employee_Id),'dd/mm/yyyy'))                      AS Retire_Date,date_of_join_office " +
			"  FROM " +
			"  (SELECT A.Employee_Id, " +
			"    A.Service, " +
			"    Empname, " +
			"    C.Office_Id, " +
			"    DECODE(Office_Name,NULL,Employee_Status_Desc,Office_Name) AS Office_Name, " +
			"    Employee_Status_Id, " +
			"    C.Designation_Id, " +
			"    E.Designation, " +
			"    Service_Yr, " +
			"    Service_Mnth, " +
			"    Total_Service, " +
			"    Total_Service_Yr, " +
			"    Total_Service_Mnth, " +
			"    Pfservice, " +
			"    Pf_Office_Yr, " +
			"    Pf_Office_Month,date_of_join_office " +
			"  FROM " +
			"    (SELECT Employee_Id, " +
			"      SUM(Total_Days)                       AS Service, " +
			"      Floor(SUM(Total_Days)          /365)  AS Service_Yr , " +
			"      Floor(Mod(SUM(Total_Days),365) /30.5) AS Service_Mnth " +
			"    FROM HRM_CALC_TYPEOFSERVICE_EE I " +
			"    WHERE I.Primary_Work_Id IN ("+typeofservice+")   "+Condition+"  " +
			"    GROUP BY Employee_Id " +
			"    )A " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      Employee_Name " +
			"      || ' . ' " +
			"      || Employee_Initial AS Empname " +
			"    FROM Hrm_Mst_Employees " +
			"    )B " +
			"  ON A.Employee_Id=B.Employee_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      Office_Id,to_char(date_effective_from,'dd/mm/yyyy') as date_of_join_office, " +
			"      Designation_Id, " +
			"      EMPLOYEE_STATUS_ID " +
			"    FROM Hrm_Emp_Current_Posting " +
			"    )C " +
			"  ON A.Employee_Id=C.Employee_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Office_Id,Office_Name FROM Com_Mst_Offices " +
			"    ) D " +
			"  ON C.Office_Id=D.Office_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Designation_Id,Designation FROM Hrm_Mst_Designations " +
			"    )E " +
			"  ON C.Designation_Id=E.Designation_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Status_Id,Employee_Status_Desc FROM Hrm_Mst_Employee_Status " +
			"    ) F " +
			"  ON F.Employee_Status_Id=C.Employee_Status_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      SUM(Total_Days)                       AS Total_Service, " +
			"      Floor(SUM(Total_Days)          /365)  AS Total_Service_Yr , " +
			"      Floor(Mod(SUM(Total_Days),365) /30.5) AS Total_Service_Mnth " +
			"    FROM HRM_CALC_TYPEOFSERVICE_EE " +
			"    GROUP BY Employee_Id " +
			"    )G " +
			"  ON A.Employee_Id=G.Employee_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      SUM(Total_Days)                       AS Pfservice, " +
			"      Floor(SUM(Total_Days)          /365)  AS Pf_Office_Yr , " +
			"      Floor(Mod(SUM(Total_Days),365) /30.5) AS Pf_Office_Month " +
			"    FROM HRM_CALC_TYPEOFSERVICE_EE " +
			"    WHERE Primary_Work_Id IN ('HO','Office','PF') " +
			"    GROUP BY Employee_Id " +
			"    )H " +
			"  ON A.EMPLOYEE_ID=H.EMPLOYEE_ID " +
			"  ) " +
			" "+Condition1+" ";
}
else if(ranks.equalsIgnoreCase("AEE"))
{
	query="SELECT Employee_Id, " +
			"  Empname, " +
			"  Office_Name, " +
			"  Designation_Id, " +
			"  Designation, " +
			"  DECODE(Total_Service_Yr,NULL,' ' ,TO_CHAR(Total_Service_Yr,'00') " +
			"  || ' Year') AS Total_Service_Yr, " +
			"  DECODE(Total_Service_Mnth,NULL,' ' ,TO_CHAR(Total_Service_Mnth,'00') " +
			"  || ' Month') AS Total_Service_Mnth, " +
			"  DECODE(Pf_Office_Yr,NULL,' ' ,TO_CHAR(Pf_Office_Yr,'00') " +
			"  || ' Year') AS Pf_Office_Yr, " +
			"  DECODE(Pf_Office_Month,NULL,' ' ,TO_CHAR(Pf_Office_Month,'00') " +
			"  || ' Month') AS Pf_Office_Month, " +
			"  DECODE(Service_Yr,NULL,' ' ,TO_CHAR(Service_Yr,'00') " +
			"  || ' Year') AS Serviceyear, " +
			"  DECODE(Service_Mnth,NULL,' ' ,TO_CHAR(Service_Mnth,'00') " +
			"  || ' Month')                                                                  AS serviceMonth, " +
			"  NVL(Service_Yr,0)                                                             AS Service_Yr, " +
			"  NVL(Service_Mnth,0)                                                           AS Service_Mnth, " +
			"  TO_CHAR(Hrm_Emp_Get_Desig_Join_Date(Employee_Id,Designation_Id),'dd/mm/yyyy') AS Join_Date_Design, " +
			"  TO_CHAR(Hrm_Emp_Get_Board_Join_Date(Employee_Id),'dd/mm/yyyy')                AS Boar_Join_Date , " +
			"  DECODE(Hrm_Emp_Get_Retire_Date(Employee_Id),NULL,'Update Date of Birth',TO_CHAR(Hrm_Emp_Get_Retire_Date(Employee_Id),'dd/mm/yyyy'))                        AS Retire_Date,date_of_join_office " +
			"  FROM " +
			"  (SELECT A.Employee_Id, " +
			"    A.Service, " +
			"    Empname, " +
			"    C.Office_Id, " +
			"    DECODE(Office_Name,NULL,Employee_Status_Desc,Office_Name) AS Office_Name, " +
			"    Employee_Status_Id, " +
			"    C.Designation_Id, " +
			"    E.Designation, " +
			"    Service_Yr, " +
			"    Service_Mnth, " +
			"    Total_Service, " +
			"    Total_Service_Yr, " +
			"    Total_Service_Mnth, " +
			"    Pfservice, " +
			"    Pf_Office_Yr, " +
			"    Pf_Office_Month,date_of_join_office " +
			"  FROM " +
			"    (SELECT Employee_Id, " +
			"      SUM(Total_Days)                       AS Service, " +
			"      Floor(SUM(Total_Days)          /365)  AS Service_Yr , " +
			"      Floor(Mod(SUM(Total_Days),365) /30.5) AS Service_Mnth " +
			"    FROM Hrm_Calc_Typeofservice_Aee I " +
			"    WHERE I.Primary_Work_Id IN ("+typeofservice+")  "+Condition+"  " +
			"    GROUP BY Employee_Id " +
			"    )A " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      Employee_Name " +
			"      || ' . ' " +
			"      || Employee_Initial AS Empname " +
			"    FROM Hrm_Mst_Employees " +
			"    )B " +
			"  ON A.Employee_Id=B.Employee_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      Office_Id,to_char(date_effective_from,'dd/mm/yyyy') as date_of_join_office, " +
			"      Designation_Id, " +
			"      EMPLOYEE_STATUS_ID " +
			"    FROM Hrm_Emp_Current_Posting " +
			"    )C " +
			"  ON A.Employee_Id=C.Employee_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Office_Id,Office_Name FROM Com_Mst_Offices " +
			"    ) D " +
			"  ON C.Office_Id=D.Office_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Designation_Id,Designation FROM Hrm_Mst_Designations " +
			"    )E " +
			"  ON C.Designation_Id=E.Designation_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Status_Id,Employee_Status_Desc FROM Hrm_Mst_Employee_Status " +
			"    ) F " +
			"  ON F.Employee_Status_Id=C.Employee_Status_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      SUM(Total_Days)                       AS Total_Service, " +
			"      Floor(SUM(Total_Days)          /365)  AS Total_Service_Yr , " +
			"      Floor(Mod(SUM(Total_Days),365) /30.5) AS Total_Service_Mnth " +
			"    FROM HRM_CALC_TYPEOFSERVICE_AEE " +
			"    GROUP BY Employee_Id " +
			"    )G " +
			"  ON A.Employee_Id=G.Employee_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      SUM(Total_Days)                       AS Pfservice, " +
			"      Floor(SUM(Total_Days)          /365)  AS Pf_Office_Yr , " +
			"      Floor(Mod(SUM(Total_Days),365) /30.5) AS Pf_Office_Month " +
			"    FROM HRM_CALC_TYPEOFSERVICE_AEE " +
			"    WHERE Primary_Work_Id IN ('HO','Office','PF') " +
			"    GROUP BY Employee_Id " +
			"    )H " +
			"  ON A.EMPLOYEE_ID=H.EMPLOYEE_ID " +
			"  ) " +
			"  "+Condition1+" ";
}
else
{
	query="SELECT Employee_Id, " +
			"  Empname, " +
			"  Office_Name, " +
			"  Designation_Id, " +
			"  Designation, " +
			"  DECODE(Total_Service_Yr,NULL,' ' ,TO_CHAR(Total_Service_Yr,'00') " +
			"  || ' Year') AS Total_Service_Yr, " +
			"  DECODE(Total_Service_Mnth,NULL,' ' ,TO_CHAR(Total_Service_Mnth,'00') " +
			"  || ' Month') AS Total_Service_Mnth, " +
			"  DECODE(Pf_Office_Yr,NULL,' ' ,TO_CHAR(Pf_Office_Yr,'00') " +
			"  || ' Year') AS Pf_Office_Yr, " +
			"  DECODE(Pf_Office_Month,NULL,' ' ,TO_CHAR(Pf_Office_Month,'00') " +
			"  || ' Month') AS Pf_Office_Month, " +
			"  DECODE(Service_Yr,NULL,' ' ,TO_CHAR(Service_Yr,'00') " +
			"  || ' Year') AS Serviceyear, " +
			"  DECODE(Service_Mnth,NULL,' ' ,TO_CHAR(Service_Mnth,'00') " +
			"  || ' Month')                                                                  AS serviceMonth, " +
			"  NVL(Service_Yr,0)                                                             AS Service_Yr, " +
			"  NVL(Service_Mnth,0)                                                           AS Service_Mnth, " +
			"  TO_CHAR(Hrm_Emp_Get_Desig_Join_Date(Employee_Id,Designation_Id),'dd/mm/yyyy') AS Join_Date_Design, " +
			"  TO_CHAR(Hrm_Emp_Get_Board_Join_Date(Employee_Id),'dd/mm/yyyy')                AS Boar_Join_Date , " +
			"  DECODE(Hrm_Emp_Get_Retire_Date(Employee_Id),NULL,'Update Date of Birth',TO_CHAR(Hrm_Emp_Get_Retire_Date(Employee_Id),'dd/mm/yyyy'))                AS Retire_Date,date_of_join_office " +
			"  FROM " +
			"  (SELECT A.Employee_Id, " +
			"    A.Service, " +
			"    Empname, " +
			"    C.Office_Id, " +
			"    DECODE(Office_Name,NULL,Employee_Status_Desc,Office_Name) AS Office_Name, " +
			"    Employee_Status_Id, " +
			"    C.Designation_Id, " +
			"    E.Designation, " +
			"    Service_Yr, " +
			"    Service_Mnth, " +
			"    Total_Service, " +
			"    Total_Service_Yr, " +
			"    Total_Service_Mnth, " +
			"    Pfservice, " +
			"    Pf_Office_Yr, " +
			"    Pf_Office_Month,date_of_join_office " +
			"  FROM " +
			"    (SELECT Employee_Id, " +
			"      SUM(Total_Days)                       AS SERVICE, " +
			"      floor(SUM(Total_Days)          /365)  AS SERVICE_YR , " +
			"      floor(mod(SUM(Total_Days),365) /30.5) AS SERVICE_MNTH " +
			"    FROM Hrm_Calc_Typeofservice_Ae " +
			"    WHERE Primary_Work_Id IN ("+typeofservice+")  " +
			"    GROUP BY Employee_Id " +
			"    )A " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      Employee_Name " +
			"      || ' . ' " +
			"      || Employee_Initial AS Empname " +
			"    FROM Hrm_Mst_Employees " +
			"    )B " +
			"  ON A.Employee_Id=B.Employee_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      Office_Id,to_char(date_effective_from,'dd/mm/yyyy') as date_of_join_office, " +
			"      Designation_Id, " +
			"      EMPLOYEE_STATUS_ID " +
			"    FROM Hrm_Emp_Current_Posting " +
			"    )C " +
			"  ON A.Employee_Id=C.Employee_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Office_Id,Office_Name FROM Com_Mst_Offices " +
			"    ) D " +
			"  ON C.Office_Id=D.Office_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Designation_Id,Designation FROM Hrm_Mst_Designations " +
			"    )E " +
			"  ON C.Designation_Id=E.Designation_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Status_Id,Employee_Status_Desc FROM Hrm_Mst_Employee_Status " +
			"    ) F " +
			"  ON F.Employee_Status_Id=C.Employee_Status_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      SUM(Total_Days)                       AS Total_Service, " +
			"      Floor(SUM(Total_Days)          /365)  AS Total_Service_Yr , " +
			"      Floor(Mod(SUM(Total_Days),365) /30.5) AS Total_Service_Mnth " +
			"    FROM Hrm_Calc_Typeofservice_Ae " +
			"    GROUP BY Employee_Id " +
			"    )G " +
			"  ON A.Employee_Id=G.Employee_Id " +
			"  LEFT OUTER JOIN " +
			"    (SELECT Employee_Id, " +
			"      SUM(Total_Days)                       AS Pfservice, " +
			"      Floor(SUM(Total_Days)          /365)  AS Pf_Office_Yr , " +
			"      Floor(Mod(SUM(Total_Days),365) /30.5) AS Pf_Office_Month " +
			"    FROM Hrm_Calc_Typeofservice_Ae " +
			"    WHERE Primary_Work_Id IN ('HO','Office','PF') " +
			"    GROUP BY Employee_Id " +
			"    )H " +
			"  ON A.EMPLOYEE_ID=H.EMPLOYEE_ID " +
			"  ) " +
			" "+Condition1+" ";
}
}
query=query +OrderBy;
System.out.println(query);
PreparedStatement ps=connection.prepareStatement(query);
ResultSet rs=ps.executeQuery();
%>

<form name="frmTypeOfService" method="POST" action="../../../../../../EmployeeTypeofServiceReport">
<input type="hidden" name="typeofservice" value="<%=typeofservice %>" />
<input type="hidden" name="printService" value="<%=printService %>" />
<input type="hidden" name="rType" value="<%=rType %>" />
<input type="hidden" name="yeartype" value="<%=yeartype %>" />
<input type="hidden" name="nyear" value="<%=nyear %>" />
<input type="hidden" name="ranks" value="<%=ranks %>" />
<input type="hidden" name="officeID" value="<%=officeID %>" />
<input type="hidden" name="order_desc" id="order_desc"  />
<input type="hidden" name="output" id="output" value="PDF" />

<table width="100%" style="border:1px solid #0489B1" >
<tr style="height:30px" >

<td width="30%" align="right" style="color: #fff;padding-left:5px;" bgcolor="#006699"><img alt="Back" src="back.png" width="30" height="30" style="cursor:pointer" onclick="window.history.go(-1)"  title="Go Back" > &nbsp; &nbsp; <img alt="Back" src="close.png" width="30" height="30" style="cursor:pointer" onclick="self.close()" title="Close" ></td>
<td width="40%" align="center" style="color: #fff;padding-left:5px;" bgcolor="#006699"  > Type of Service Report - <%=ranks %></td>
<td width="30%" align="left" style="color: #fff;padding-left:5px;" bgcolor="#006699"> <img alt="PDF" id="generate_report_PDF"  src="pdf.png" width="30" height="30" style="cursor:pointer" class="PDF"  title="Download PDF" > &nbsp; &nbsp; <img alt="Excel" width="30" height="30" src="excel.png" class="EXCEL" style="cursor:pointer" id="generate_report_Excel"  title="Download Excel" ></td>
</tr>
</table>
<div>

<table width="100%"><tr><td width="100%">
<table>
<tr>

<td>
<table width="100%" style="border:1px solid #0489B1" border="1" bordercolor="#fffddd"  id="myTable" class="tablesorter">
<thead>
<tr style="color:#fffddd" bgcolor="#4D94B8"  align="center" >
<th width="3%" style="display: none">Sl. No </th>
<th width="8%" id="ecode" headers="Employee_Id" class="header_val" ><font id="ecode_color">EmpCode </font></th>
<th width="12%" id="ename" headers="Empname" class="header_val" ><font id="empname_color">Employee Name  </font></th>
<th width="16%" id="des" headers="Designation"><font id="designation_color">Designation ,Office Name </font> </th>
<th width="9%" id="totser" headers="totalservice"><font id="tot_service_color">Total service  </font></th>
<th width="8%" id="totpfservice" headers="totalpfservice"><font id="pf_service_color">Total pf+office service  </font></th>
<th width="8%" id="yearofser" headers="yearofservice"><font id="service_color"> Year of Service in <%=printService %>  </font></th>
<th width="9%" id="doj" headers="dateofjoin"><font id="dob_color">Date of joining in Board  </font></th>
<th width="9%" id="datefrom" headers="datefromwork"><font id="present_desi_color">Date from working in the present designation </font></th>
<th width="9%" id="datefrompresent" headers="datefromworkpresent"><font id="work_color">Date from working in present office  </font></th>
<th width="9%" id="retire" headers="dateofretire"><font id="retire_color">Date of retirement </font> </th>
</tr>
</thead>
<tbody>
<%
int i=0,j=0;
while(rs.next())
{	
	
	i++;
	kk++;
	System.out.println("kk == "+kk);
	String style="";
	String color="";
	int Employee_Id=rs.getInt("Employee_Id");
	String Empname=rs.getString("Empname");
	String Designation=rs.getString("Designation");
	String Officename=rs.getString("Office_Name");
	if(Officename.length()>1)
		Designation=Designation+","+Officename;
	
	String Total_Service_Yr=rs.getString("Total_Service_Yr");
	String Total_Service_Mnth=rs.getString("Total_Service_Mnth");
	String Pf_Office_Yr=rs.getString("Pf_Office_Yr");
	String Pf_Office_Month=rs.getString("Pf_Office_Month");
	String Service_Yr=rs.getString("Serviceyear");
	String Service_Mnth=rs.getString("serviceMonth");
	
	String date_of_join_office=rs.getString("date_of_join_office");
	String Join_Date_Design=rs.getString("Join_Date_Design");
	String Boar_Join_Date=rs.getString("Boar_Join_Date");
	String Retire_Date=rs.getString("Retire_Date");
	
	if(j==0)
	{
		j++;
		
		style="background:#fff;" ;
	}
	else
	{
		j=0;
		style="background:#B2D1F0; " ;
	}
	
	%>
	
	<script type="text/javascript">
	
	arr[<%=i%>]=<%=i%>;
	</script>
	 
<tr  height="60px"    onmouseover="ChangeColor(this, true);" 
                onmouseout="ChangeColor(this, false);"  >
<td style="color: blue;padding-left:5px; display: none" ><font size="2"><%=i %></font> </td>
<td style="color: blue;padding-left:5px;"  id="eidvalue<%=kk %>" tbval="<%=i %>"><font size="2"><a href="viewServiceParticular.jsp?txtEmpId=<%=Employee_Id%>" title="View <%=Empname %> Profile" id="" ><%=Employee_Id%></a> </font> </td>
<td style="color: blue;padding-left:5px;"><font size="2"><%=Empname %></font>  </td>
<td style="color: blue;padding-left:5px;"><font size="2"><%=Designation %> </font> </td>
<td style="color: blue;padding-left:5px;"><font size="2"><%=Total_Service_Yr%> <br> <%= Total_Service_Mnth%></font>  </td>
<td style="color: blue;padding-left:5px;"><font size="2"><%=Pf_Office_Yr%>  <br> <%= Pf_Office_Month%>  </font></td>
<td style="color: blue;padding-left:5px;"><font size="2"><%=Service_Yr%>  <br> <%= Service_Mnth%>  </font> </td>
<td style="color: blue;padding-left:5px;"><font size="2"><%=Boar_Join_Date  %> </font> </td>
<td style="color: blue;padding-left:5px;"><font size="2"><%=Join_Date_Design %> </font> </td>
<td style="color: blue;padding-left:5px;"><font size="2"><%=date_of_join_office %> </font> </td>
<td style="color: blue;padding-left:5px;"><font size="2"><%=Retire_Date %></font> </td>
</tr>
 
 
	<%
	
}

%>


</tbody>
</table>
</td></tr></table>
</td></tr>
</table>
<%

int[] emparr=new int[2];
int x=0;
System.out.println("leng  == "+rs.isBeforeFirst());
while(rs.next())
{
System.out.println("  ===  ");
	if(x<2)
	{
		emparr[x]=rs.getInt("Employee_Id");
		x++;
	}
}
System.out.println(emparr[0]+"  ===  "+emparr[1] );


 %>
</div>
<table width="100%" style="border:1px solid #0489B1" >
<tr style="height:30px" >
<td width="30%" align="right" style="color: #fff;padding-left:5px;" bgcolor="#006699"><img alt="Back" src="back.png" width="30" height="30" style="cursor:pointer" onclick="window.history.go(-1)"  title="Go Back" > &nbsp; &nbsp; <img alt="Back" src="close.png" width="30" height="30" style="cursor:pointer" onclick="self.close()" title="Close" ></td>
<td width="40%" align="center" style="color: #fff;padding-left:5px;" bgcolor="#006699"  > </td>
<td width="30%" align="left" style="color: #fff;padding-left:5px;" bgcolor="#006699"> <img alt="PDF" src="pdf.png" width="30" height="30" style="cursor:pointer" id="generate_report_PDF1"  title="Download PDF" > &nbsp; &nbsp; <img alt="Excel" width="30" height="30" src="excel.png" style="cursor:pointer" id="generate_report_Excel1"  title="Download Excel" ></td>
</tr>
<tr><td colspan="3" align="left" style="color: #fff;padding-left:5px;" bgcolor="#006699"><font size="2">1. The report based on the service entry made in the online system. If any unfreezed records of the employee or overlapping in the service particulars, then it will give wrong report
on the individual .</font> </td></tr>
<tr><td colspan="3" align="left" style="color: #fff;padding-left:5px;" bgcolor="#006699"><font size="2">2. Division details only captured for all old offices in the service particulars. Hence the work nature is based on the division only. If the individual worked in the sub division and it
contains different nature of work, then it will give the wrong result.</font></td></tr>
</table>


</form>
</body>
</html>