var xmlhttp;var seq=0;var common="",mn,yr,v;
var final_diff_imp_reg_db=0,final_diff_imp_reg_cr=0,final_diff_imp_2003_db=0,final_diff_imp_2003_cr=0;
//alert("javascript");
function displayMonth()
{
       
                 var currentTime = new Date()
                 var month = currentTime.getMonth() + 1
                 var day = currentTime.getDate()
                 var year = currentTime.getFullYear()       
                 fin_year_from="",fin_year_to="";
                 var itemcombo=document.getElementById("fin_year");
                 if(month<4)
                        year=year-1;
                 i=0;
                 while(i<2)
                 {
                        fin_year_from=year;fin_year_to=year+1;
                        var option=document.createElement("option");
                        var text=document.createTextNode(fin_year_from+"-"+fin_year_to);
                        option.setAttribute("value",fin_year_from+"-"+fin_year_to);
                       option.appendChild(text);
                        itemcombo.appendChild(option);
                        year=year-1;i++;
                }
         v=new Date();
	 mn=v.getMonth();
	 yr=v.getFullYear();
      
                        /*--- Account Month--*/
	
        
        var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
        document.getElementById("ac_year").options[1].selected='selected';
    /*     var year1=document.getElementById("ac_year").value;
    
    if(year1==yr)
    {  */
        
       for(var i=(mn+1);i<12;i++)
       {    
    
           document.getElementById("ac_month").options[i].disabled=true;
          
       }
   
        
        document.getElementById("rel_year").options[1].selected='selected';
     /*    var year2=document.getElementById("rel_year").value;
        
    
    if(year2==yr)
    {
        */
       for(var i=(mn+1);i<12;i++)
       {    
    
           document.getElementById("rel_month").options[i].disabled=true;
          
       }
        document.getElementById("ac_month").options[mn].selected='selected';
        document.getElementById("rel_month").options[mn].selected='selected';
        
        
                       /*--- Relative Month--*/
      
         document.Hrm_TransJoinForm.txtEmpId.value="";
        
        document.Hrm_TransJoinForm.comEmpId.value="";
        document.Hrm_TransJoinForm.designation.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.unit_name.focus();
       
        document.Hrm_TransJoinForm.add.disabled=false;
        document.Hrm_TransJoinForm.update.disabled=true;
        document.Hrm_TransJoinForm.delete1.disabled=true;
       
	
}
 function loadvalue(empid)
    {      
          common=empid;
          var emp_id=document.getElementById(empid); 
          var rcells=emp_id.cells;
          document.Hrm_TransJoinForm.txtEmpId.value=rcells.item(1).firstChild.nodeValue;
           document.Hrm_TransJoinForm.fin_year.value=rcells.item(2).firstChild.nodeValue;
         
          var ac_month_year=rcells.item(3).lastChild.nodeValue.split("-");
          var rel_month_year=rcells.item(4).lastChild.nodeValue.split("-");
          var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
           var j,k;
           for(var i=0;i<12;i++)
          {
            if(ac_month_year[0]==month[i])
            {
               j=i;
            }
            if(rel_month_year[0]==month[i])
            {
                k=i;
            }
          }
          document.Hrm_TransJoinForm.ac_month[j].selected='selected';
          document.Hrm_TransJoinForm.ac_month.disabled=true;
           if(yr==rel_month_year[1])
         {
            
          for(var i=(mn+1);i<12;i++)
            {    
            
           document.getElementById("rel_month").options[i].disabled=true;
          
            }
       document.getElementById("rel_month").options[k].selected='selected';
       }
          else
          {
           for(var i=0;i<12;i++)
            {    
    
           document.getElementById("rel_month").options[i].disabled=false;
          
            }
          document.Hrm_TransJoinForm.rel_month[k].selected='selected';
          }
          
          
          //document.getElementById("ac_month").options[document.getElementById("ac_month").selectedIndex].text=rcells.item(2).firstChild.nodeValue;
          //var acyear=rcells.item(2).lastChild.value;
          document.getElementById("ac_year").value=ac_month_year[1];
          document.Hrm_TransJoinForm.ac_year.disabled=true;
          //document.getElementById("rel_month").options[document.getElementById("rel_month").selectedIndex].text=rcells.item(3).firstChild.nodeValue;
          
          document.getElementById("rel_year").value=rel_month_year[1];
          
          var type_trans=rcells.item(5).firstChild.nodeValue;
          
         
        
          if(type_trans=="CR")
          {
          
          document.Hrm_TransJoinForm.trans[0].checked='checked';
          }
          else
          { 
          document.Hrm_TransJoinForm.trans[1].checked='checked';
          }
          var remark=rcells.item(5).lastChild.value;
           if(remark=="noRemarks")
          {
           
            remark=" ";
          }
          document.getElementById("remarks").value=remark;
          
          document.Hrm_TransJoinForm.amount.value=rcells.item(6).firstChild.nodeValue;
          document.Hrm_TransJoinForm.date.value=rcells.item(7).firstChild.value;
          
          
          var impound_type=rcells.item(8).firstChild.nodeValue;
           document.Hrm_TransJoinForm.Dis_sl_no.value=rcells.item(8).lastChild.value;
          var len=document.Hrm_TransJoinForm.impound_type.length;
         
          for(var i=0;i<len;i++)
          {
        	  
        	  if(document.Hrm_TransJoinForm.impound_type[i].value==impound_type)
        	  {
        		  document.Hrm_TransJoinForm.impound_type[i].selected='selected';  
        	  }
          }
          
          
          var types=rcells.item(9).firstChild.nodeValue.split(",");;
          var i=0,j=0;
          
          for(i=0;i<types.length;i++){
          for(j=0;j<document.Hrm_TransJoinForm.install_type.length;j++)
          {
        	if(document.Hrm_TransJoinForm.install_type[j].value==types[i])
        	{
        		document.Hrm_TransJoinForm.install_type[j].checked=true;
        	}
          }
          }
  
          document.Hrm_TransJoinForm.impound_type.disabled=true;
          document.Hrm_TransJoinForm.add.disabled=true;
        document.Hrm_TransJoinForm.update.disabled=false;
        document.Hrm_TransJoinForm.delete1.disabled=false;
        
          
      
    }
 
function getxmlhttpObject()
{
 var req = false;
 try 
 {
       req= new ActiveXObject("Msxml2.XMLHTTP");
 }
 catch (e) 
 {
       try 
       {
            req = new ActiveXObject("Microsoft.XMLHTTP");
       }
       catch (e2) 
       {
            req = false;
       }
 }
 if (!req && typeof XMLHttpRequest != 'undefined') 
 {
       req = new XMLHttpRequest();
 }   
 return req;
}

function stateChanged()
{
    var flag,command,response;
    var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
   //alert("statechanged");
    if(xmlhttp.readyState==4)
    {
    	
       if(xmlhttp.status==200)
       {
    	   //alert("status is 200");
            response=xmlhttp.responseXML.getElementsByTagName("response")[0];
            //alert("response"+response);
            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
        //    flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            //alert("command"+command);
         
        if(command=="unfreeze")
            {
            	//alert("command"+command);
                var record=response.getElementsByTagName("record")[0].firstChild.nodeValue; 
                //alert("record"+record);
            	if(record=='available'){
            		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue; 
            		if(flag=='success')
            			alert("Unfreezed Successfully");
            		clearall();
            	}
            	else if(record=='NotAvailable')
            		  alert("There is no record to freeze");
            	
            	clearall();
         	    
            }
                        
       else if(command=="AccountUnit"){
            var minorcmb = document.getElementById("unit_name");
            document.getElementById("unit_name").length=1;
            var acc_unit_id = baseResponse.getElementsByTagName("acc_unit_id"); 
            var acc_unit_name = baseResponse.getElementsByTagName("acc_unit_name");
            for(var i=0; i<acc_unit_id.length; i++){
                         var opt1 = document.createElement('option');
                         opt1.value = acc_unit_id[i].firstChild.nodeValue;
                         opt1.innerHTML = acc_unit_name[i].firstChild.nodeValue;
                         minorcmb.appendChild(opt1);
            } 
        }
     }
           
 }
}

function clearall()
{
	document.Hrm_TransJoinForm.ac_year.options[0].selected='selected';
	document.Hrm_TransJoinForm.ac_month.options[0].selected='selected';
	document.Hrm_TransJoinForm.fin_year.options[0].selected='selected';
	document.Hrm_TransJoinForm.unit_name.options[0].selected='selected';
	document.Hrm_TransJoinForm.Acc_unit_code.value=" ";	 
	document.Hrm_TransJoinForm.withdrawradio[0].checked=false;
	document.Hrm_TransJoinForm.withdrawradio[1].checked=false;
	document.Hrm_TransJoinForm.withdrawradio[2].checked=false;
}
function disablefun()
{
   // alert("function called-------->");
    var ac_year=0,ac_month=0;
    ac_year=document.getElementById("ac_year").value;
    ac_month=document.getElementById("ac_month").value;
    document.getElementById("rel_month").value=ac_month;
    document.getElementById("rel_year").value=ac_year;
    document.getElementById("rel_month").disabled=true;
    document.getElementById("rel_year").disabled=true;
     call('acc_head_code');
           
}
function assignyear()
{
    ac_year=document.getElementById("ac_year").value;
    ac_month=document.getElementById("ac_month").value;
    document.getElementById("rel_month").value=ac_month;
    document.getElementById("rel_year").value=ac_year;
}

function enablefun()
{
       document.getElementById("rel_month").disabled=false;
       document.getElementById("rel_year").disabled=false;
       call('acc_head_code');
}

function assignyear()
{
     var ac_year=0;
     ac_year=document.getElementById("ac_year").value;
     document.getElementById("rel_year").value=ac_year;
     
}
function assignmonth()
{
    var ac_month=0;
     ac_year=document.getElementById("ac_month").value;
     document.getElementById("ac_month").value=ac_year;
}

function call(command)
{
//alert(command);
xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your borwser doesnot support AJAX");
    return;
    }   
    
        
        var office_id;
        var division_id;
        var ac_month;
        var rel_month;
        var ac_year;
        var rel_year;
        var type_trans;
        var impound_type;
        var emp_id;
        var amount;
        var date_trans;
        var remarks;       
        var url="";

   
      if(command=="UnitName"){
    	//alert("unitname");
    	var unit_id=document.getElementById("unit_name").value;
        document.getElementById("Acc_unit_code").value=unit_id;
      //  clearall();
    }           
}
function nullCheck()
{
	
	if(document.Hrm_TransJoinForm.fin_year.value=="--Select Financial Year--")
    {
     alert("please Choose Financial Year");
     return 0;
    }
	else if((document.Hrm_TransJoinForm.unit_name.value=="--Select Accounting Unit--"))
        {
        alert("please Choose Accounting unit ");
        return 0;
        }
                        
        else if(document.Hrm_TransJoinForm.ac_year.value=="Select Year")
        {
        alert("please Choose Account Year");
         return 0;
        }
        else if(document.Hrm_TransJoinForm.ac_month.value=="Select Month")
        {
        alert("please Choose Account Month");
         return 0;
        }
        else  
        	  return 1;

}
function filter_real(evt,item,n,pre)
{
         var charCode = (evt.which) ? evt.which : event.keyCode
// allow "." for one time 
         if(charCode==46){
                        //    alert("Position of . "+item.value.indexOf("."));
                                if(item.value.indexOf(".")<0)    return (item.value.length>0)?true:false;
                                else return false;
          }
         if (!(charCode > 31 && (charCode < 48 || charCode > 57))){
                // to avoid over flow
                        if(item.value.indexOf(".")<0){
        //            alert("Length without . ="+item.value.length);
                                return (item.value.length<n)?true:false;
                        }
                // dont allow more than 2 precision no's after the point
                        if(item.value.indexOf(".")>0){
                        //    alert("precision count ="+item.value.split(".")[1].length);
                                if(item.value.split(".")[1].length<pre) return true;
                                else return false;
                        }
                        return false;
        }else{
                        return false;
        }
}
function selectActMonth()
{
    var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
    var year1=document.getElementById("ac_year").value;
    
    if(year1==yr)
    {
        
       for(var i=(mn+1);i<12;i++)
       {    
    
           document.getElementById("ac_month").options[i].disabled=true;
          
       }
       document.getElementById("ac_month").options[mn].selected='selected';
       
    }
    else
    {
         document.getElementById("ac_month").disabled=false;
       for(var i=0;i<12;i++)
       {    
    
           document.getElementById("ac_month").options[i].disabled=false;
          
       }
      
    }
    ac_year=document.getElementById("ac_year").value;
    ac_month=document.getElementById("ac_month").value;
    document.getElementById("rel_month").value=ac_month;
    document.getElementById("rel_year").value=ac_year;
    
}
function selectRelMonth()
{
    var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
    var year2=document.getElementById("rel_year").value;
    
    if(year2==yr)
    {
        
       for(var i=(mn+1);i<12;i++)
       {    
    
           document.getElementById("rel_month").options[i].disabled=true;
          
       }
       document.getElementById("rel_month").options[mn].selected='selected';
       
    }
    else
    {
         //document.getElementById("rel_month").disabled=false;
         
       for(var i=0;i<12;i++)
       {    
     
           document.getElementById("rel_month").options[i].disabled=false;
          
       }
      
    }
}

function checkName()
{
  
    var unit_name=document.getElementById("unit_name").value;
    document.getElementById("oldcode").value=unit_name;
    
       /* url="../../../../../GPF_Impound.view?command=unit&unit_name="+unit_name;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);   */
}

function dateset()
{
	
	var dateString=document.getElementById("date").value;
	dd=dateString.substring(0,2);
	mm=dateString.substring(3,5);
	yyy=dateString.substring(6,11);
	dateString=mm+"/"+dd+"/"+yyy;
	 var date= new Date(dateString);
 	 var mmm = date.getMonth() +1;
	 var yy = date.getFullYear();
	 document.getElementById("ac_month").disabled=false;
	 for(var j=0;j<12;j++){
	 if(document.getElementById("ac_month").options[j].value==mmm)
	 {
		 document.getElementById("ac_month").options[j].selected=true;  
	 }
	 }

	 for(var i=0; i<1;i++){
	 if(document.getElementById("ac_year").options[i].value==yy)
	 {
		 document.getElementById("ac_year").options[i].selected=true; 
	 }
	 }
 }

function getAccountUnit(){
	//alert("Account unit");
    var finyear=document.getElementById("fin_year");
    var finyear_val=finyear.options[finyear.selectedIndex].value;
    var finyear_arr=new Array(2);
    var now=new Date();
    finyear_val=finyear_val.split("-");
    if(document.getElementById("ac_year")!=null){
    var minorcmb = document.Hrm_TransJoinForm.ac_year;
    document.Hrm_TransJoinForm.ac_year.length=1;
    for(var i=0; i<finyear_val.length; i++){
    if(finyear_val[i]<=now.getFullYear()){
            var opt1 = document.createElement('option');
            opt1.value = finyear_val[i];
            opt1.innerHTML = finyear_val[i];
            minorcmb.appendChild(opt1);
            }
    }}
        var office_lvl=document.getElementById("txtOffLevel").value;
        var office_id=document.getElementById("txtOffId").value;
        var fin_year=document.getElementById("fin_year").value;
        var url="../../../../../AccountUnitServlet?office_lvl="+office_lvl+"&fin_year="+fin_year+"&office_id="+office_id;
        var req=getTransport();
            req.open("POST",url,true);
            req.onreadystatechange=function(){
                handleResponse(req);
            } 
            req.send(null);
}
function handleResponse(req)
{
//alert("handle response");
   if(req.readyState==4)
    {
	   //alert("readyState=4");
         if(req.status==200)
        {
        	// alert("status=200");
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
          //  alert("****1");
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
         
            if(Command=="AccountUnit"){
                var minorcmb = document.getElementById("unit_name");
                document.getElementById("unit_name").length=1;
                var acc_unit_id = baseResponse.getElementsByTagName("acc_unit_id"); 
                var acc_unit_name = baseResponse.getElementsByTagName("acc_unit_name");
                for(var i=0; i<acc_unit_id.length; i++){
                             var opt1 = document.createElement('option');
                             opt1.value = acc_unit_id[i].firstChild.nodeValue;
                             opt1.innerHTML = acc_unit_name[i].firstChild.nodeValue;
                             minorcmb.appendChild(opt1);
                } 
            }
        }
    }
}

 function  unfreezegpf()
 {
    var unfreeze= nullCheck();
	 if (unfreeze==1)
	 {
		 var r=confirm("Are you sure! Do you really want to unfreeze??");
		 if (r==true)
		   {
			 var unit_name = document.Hrm_TransJoinForm.unit_name.value;
	         var ac_year=document.Hrm_TransJoinForm.ac_year.value;
	         var fin_year=document.Hrm_TransJoinForm.fin_year.value;
	         var ac_month=document.Hrm_TransJoinForm.ac_month.value;
	         if(document.Hrm_TransJoinForm.withdrawradio[0].checked==true)
	              var type=document.Hrm_TransJoinForm.withdrawradio[0].value;
	         if(document.Hrm_TransJoinForm.withdrawradio[1].checked==true)
		         var type=document.Hrm_TransJoinForm.withdrawradio[1].value;
	         if(document.Hrm_TransJoinForm.withdrawradio[2].checked==true)
		         var type=document.Hrm_TransJoinForm.withdrawradio[2].value;
	        	 
	         url="../../../../../GPF_Unfreezedata_servlet?&command=Unfreeze&unit_name="+unit_name+"&ac_year="+ac_year+"&fin_year="+fin_year+"&ac_month="+ac_month+"&type="+type;	       
	         //alert("url="+ url);
	         xmlhttp.open("GET",url,true);
	         xmlhttp.onreadystatechange=function()
	         {
	         	  stateChanged(xmlhttp);
	         	 
	         }
	          xmlhttp.send(null);        
	         }
	  }
			             
 }
	 

