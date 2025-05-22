var xmlhttp;var seq=0;var common="",mn,yr,v;
var trialVal;

var totref;
var trialbalancesub,trialbalancesub1;
var trialbalanceref;
var adjustmentsub;
var adjustmentref;
var final_diff_sub,final_diff_ref;
var data_value="false";
var validate="False";
function Exit()
{
    window.close();
}

function doParentEmp(emp)
{
document.getElementById("txtGpfNo").value=emp;
call('get','s');
}

function popParentEmp(emp)
{
	call('getone','s');
}

var winemp1;
var winempadd;
function servicepopup()
{
	
     if (winemp1 && winemp1.open && !winemp1.closed) 
    {
       winemp1.resizeTo(500,500);
       winemp1.moveTo(250,250); 
       winemp1.focus();
    }
    else
    {
        winemp1=null
    }
    winemp1= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpJoiningPopup.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp1.moveTo(250,250); 
    winemp1.focus();
}
function getGetone(){
	
	clear();
	clear_journal();
	call('getone','s');	
}

function subscribepopup(comfun)
{
     if (winemp && winemp.open && !winemp.closed) 
    {
    	 winemp.resizeTo(500,500);
    	 winemp.moveTo(250,250); 
    	 winemp.focus();
    }
    else
    {
    	winemp=null
    }
    // startwaiting(document.Hrm_TransJoinForm);  
   if(comfun=="Add"){	   
	   if(nullCheck())
	   {
		   var officeid=document.forms[0].txtOffId.value;
		   var acmonth=document.forms[0].ac_month.value;
		   var acyear=document.forms[0].ac_year.value;
		   var unit=document.forms[0].Acc_unit_code.value;
		   winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/GPF_Subscription_Popup.jsp?command=Add&officeid="+officeid+"&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit+"&updation=1"+"","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes");
	  	   winemp.moveTo(250,250);  
		   winemp.focus();
	   }
   }
   else if(comfun=="Update")
   {
	   

	   var v=document.getElementsByName("sel");
	   if(v){	    
		   for(i=0;i<v.length;i++){
	           if(v[i].checked==true)
	           {	        	   
	        	   var emp_id=document.getElementById(i); 
	               var rcells=emp_id.cells;
	               var officeid=document.forms[0].txtOffId.value;
	               var gpfno=rcells.item(7).lastChild.value;
	               var ac_month_year=rcells.item(1).lastChild.nodeValue.split("-");
	               acmonth=ac_month_year[0];
	               acyear=ac_month_year[1];	               
	               var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
	 			  	var k;
	 			for(i=0;i<12;i++)
	 			{
	 				
	 				if(month[i]==acmonth)
	 	            {
	 	                k=i;
	 	               
	 	            }

	 					
	 			}
	 			acmonth=k+1;  
	               
	               
	               
	               var rel_month_year=rcells.item(2).lastChild.nodeValue.split("-");
	               relmonth=rel_month_year[0];
	               relyear=rel_month_year[1];
	               
	               var subamount=rcells.item(4).firstChild.nodeValue;
	               
	               var refamount=rcells.item(5).firstChild.nodeValue;
	               
	               var ref_tot_no=rcells.item(6).lastChild.nodeValue.split("/");
	               
	               var reftot=ref_tot_no[1];
	               var refins=ref_tot_no[0];
	              
	               var arramount=rcells.item(7).firstChild.nodeValue;
	              
	               var arr_tot_no=rcells.item(8).lastChild.nodeValue.split("/");
	               var arrtot=arr_tot_no[1];
	               var arrins=arr_tot_no[0];
	             
	               var remark=rcells.item(3).lastChild.value;
	              
	               if(remark=="noRemarks")
	               {
	                
	                 remark=" ";
	               }
	               
	               var officeid=document.forms[0].txtOffId.value;
	             
	               winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/GPF_Subscription_Popup.jsp?command=update&officeid="+officeid+"&acmonth="+acmonth+"&acyear="+acyear+"&gpfno="+gpfno+"&relmonth="+relmonth+"&relyear="+relyear+"&subamount="+subamount+"&arramount="+arramount+"&refamount="+refamount+"&reftot="+reftot+"&refins="+refins+"&arrtot="+arrtot+"&arrins="+arrins+"&remark="+remark+"","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes");
	               winemp.moveTo(250,250);  
	        	   winemp.focus();
	               
	               
	           }
	          
	       }
	   }
	   else
	   {
	              alert('Select an Employee ');
	              return false; 

	   }
   }
   
   
   
   
}
window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
}


function displayMonth()
{
       
         v=new Date();
	 mn=v.getMonth();
	 yr=v.getFullYear();
      
                        /*--- Account Month--*/
	
        
        var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
        document.getElementById("ac_year").options[1].selected='selected';
        
    for(var i=(mn+1);i<12;i++)
       {    
    
           document.getElementById("ac_month").options[i].disabled=true;
          
       }
   
        
        document.getElementById("rel_year").options[5].selected='selected';
        for(var i=(mn+1);i<12;i++)
        {    
           document.getElementById("rel_month").options[i].disabled=true;
        }
       /*--- Relative Month--*/
        document.getElementById("ac_month").options[mn].selected='selected';
        document.getElementById("rel_month").options[mn].selected='selected';
        document.Hrm_TransJoinForm.txtEmpId.value="";
        
        document.Hrm_TransJoinForm.comEmpId.value="";
        document.Hrm_TransJoinForm.designation.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.unit_name.focus();
        document.Hrm_TransJoinForm.amount.value="";
        document.Hrm_TransJoinForm.ref_amount.value="";
        document.Hrm_TransJoinForm.ref_no.value="";
        document.Hrm_TransJoinForm.ref_total.value="";
        document.Hrm_TransJoinForm.rec_amount.value="";
        document.Hrm_TransJoinForm.rec_no.value="";
        document.Hrm_TransJoinForm.rec_total.value=""; 
        document.Hrm_TransJoinForm.remarks.value="";
        document.Hrm_TransJoinForm.trans[0].checked='checked';
       document.getElementById("refund_div").disabled=false;
       document.Hrm_TransJoinForm.ref_amount.disabled=false; 
       document.Hrm_TransJoinForm.ref_no.disabled=false;
       document.Hrm_TransJoinForm.ref_total.disabled=false;
       
      /* document.getElementById("arrear_div").disabled=true;
       document.Hrm_TransJoinForm.rec_amount.disabled=true; 
       document.Hrm_TransJoinForm.rec_no.disabled=true;
       document.Hrm_TransJoinForm.rec_total.disabled=true;*/
        document.Hrm_TransJoinForm.ac_month.disabled=false;
        document.Hrm_TransJoinForm.ac_year.disabled=false;
       
        document.Hrm_TransJoinForm.add.disabled=false;
        document.Hrm_TransJoinForm.update.disabled=true;
        document.Hrm_TransJoinForm.delete1.disabled=true;
                
	
}
 function loadvalue(empid)
    {      
	
          common=empid;
          var emp_id=document.getElementById(empid); 
          var rcells=emp_id.cells;
         // document.Hrm_TransJoinForm.txtEmpId.value=rcells.item(1).firstChild.nodeValue;
          var ac_month_year=rcells.item(1).lastChild.nodeValue.split("-");
          var rel_month_year=rcells.item(2).lastChild.nodeValue.split("-");
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
          
          var type_trans=rcells.item(3).firstChild.nodeValue;
          
        
          if(type_trans=="CR")
          {
          
          document.Hrm_TransJoinForm.trans[0].checked='checked';
          }
          else
          { 
          document.Hrm_TransJoinForm.trans[1].checked='checked';
          }
          var remark=rcells.item(3).lastChild.value;
         
          if(remark=="noRemarks")
          {
           
            remark=" ";
          }
          document.getElementById("remarks").value=remark;
          document.Hrm_TransJoinForm.amount.value=rcells.item(4).firstChild.nodeValue;
           var ref_amount=rcells.item(5).firstChild.nodeValue;
          document.Hrm_TransJoinForm.ref_amount.value=ref_amount;
         
         
        document.getElementById("refund_div").disabled=false;
       document.Hrm_TransJoinForm.ref_amount.disabled=false; 
       document.Hrm_TransJoinForm.ref_no.disabled=false;
       document.Hrm_TransJoinForm.ref_total.disabled=false;
      
 
           
          var ref_no_total=rcells.item(6).lastChild.nodeValue.split("/");
          document.Hrm_TransJoinForm.ref_no.value=ref_no_total[0];
          document.Hrm_TransJoinForm.ref_total.value=ref_no_total[1];
         // var rec_amount=rcells.item(7).firstChild.nodeValue;;
        //  document.Hrm_TransJoinForm.rec_amount.value=rec_amount;
         /* if(rec_amount!=0)
          {
        document.getElementById("arrear_div").disabled=false;
       document.Hrm_TransJoinForm.rec_amount.disabled=false; 
       document.Hrm_TransJoinForm.rec_no.disabled=false;
       document.Hrm_TransJoinForm.rec_total.disabled=false;
              document.Hrm_TransJoinForm.arrear.checked=true;
          }
          var rec_no_total=rcells.item(8).lastChild.nodeValue.split("/");
          document.Hrm_TransJoinForm.rec_no.value=rec_no_total[0];
          document.Hrm_TransJoinForm.rec_total.value=rec_no_total[1];*/
          
      

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

function stateChanged(xmlhttp)
{
    var flag,command,response;
    var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
  
    if(xmlhttp.readyState==4)
    {
    	
       if(xmlhttp.status==200)
       {
    	 /*   alert("request ::: "+xmlhttp);
    	    alert("Response :: "+xmlhttp.responseXML.getElementsByTagName("response")[0]);*/
    	 //   alert(xmlhttp.responseText);
            response=xmlhttp.responseXML.getElementsByTagName("response")[0];
            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;

            if(command=="get")
            {
            	
                if(flag=='success'){
                   var tlist=document.getElementById("tlist");
                   var empname=response.getElementsByTagName("emp_name")[0].firstChild.nodeValue;
                   var d_id=response.getElementsByTagName("designation")[0].firstChild.nodeValue;
                   var dob=response.getElementsByTagName("date_of_birth")[0].firstChild.nodeValue;
                   var gpfno=response.getElementsByTagName("gpf_no")[0].firstChild.nodeValue;
                   var emp_id=response.getElementsByTagName("emp_id")[0].firstChild.nodeValue;
                   document.getElementById("comEmpId").value=empname;
                   document.getElementById("txtDOB").value=dob;
                   document.getElementById("txtGpfNo").value=gpfno;
                   document.getElementById("designation").value=d_id;
                   document.getElementById("txtEmpId").value=emp_id;
               }
               else{
                   document.Hrm_TransJoinForm.txtEmpId.value="";
                   document.getElementById("comEmpId").value="";
                   document.getElementById("txtDOB").value="";
                   document.getElementById("txtGpfNo").value="";
                   document.getElementById("designation").value="";
                   document.getElementById("amount").value="";
                   document.getElementById("ref_amount").value="";
                   document.getElementById("ref_no").value="";
                   document.getElementById("ref_total").value="";
                   document.Hrm_TransJoinForm.txtGpfNo.focus();
                   alert("This Employee GPF.NO doesnot Exist");
                }
                    
                   
            }
            else if(command=="AccountUnit"){
            
                var minorcmb = document.getElementById("unit_name");
                document.getElementById("unit_name").length=1;
                var acc_unit_id = response.getElementsByTagName("acc_unit_id"); 
                var acc_unit_name = response.getElementsByTagName("acc_unit_name");
                for(var i=0; i<acc_unit_id.length; i++){
                             var opt1 = document.createElement('option');
                             opt1.value = acc_unit_id[i].firstChild.nodeValue;
                             opt1.innerHTML = acc_unit_name[i].firstChild.nodeValue;
                             minorcmb.appendChild(opt1);
                } 
            }
            else if(command=="getone")
            {       
            	if(flag=='success')
                {
                	try{
                	  var tlist=document.getElementById("tlist");
                    while (tlist.childNodes.length > 0) {
                   	 tlist.removeChild(tlist.firstChild);
                    }
                    var len=response.getElementsByTagName("emp_id").length;
                  
                    seq=0;
                    for(var i=0;i<len;i++)
	                {                    	
	                    var emp_id=response.getElementsByTagName("emp_id")[i].firstChild.nodeValue;
	                    var ac_month=response.getElementsByTagName("ac_month")[i].firstChild.nodeValue;
	                    ac_month=month[ac_month-1];
	                    var acyear=response.getElementsByTagName("ac_year")[i].firstChild.nodeValue;
	                    var rel_month=response.getElementsByTagName("rel_month")[i].firstChild.nodeValue;
	                    if(rel_month==0)
	                    {
	                    	rel_month="0";
	                    }
	                    
	                    else
	                    {
	                    	 rel_month=month[rel_month-1];
	                    }
	                   // rel_month=month[rel_month-1];
	                    var relyear=response.getElementsByTagName("rel_year")[i].firstChild.nodeValue;
	                    
	                    var REF_ARR_AMT=0;
	                      var REF_ARR_REL_YR=0;
	                      var REF_ARR_REL_MTH=0;
	                      
	                      
	                      
	                      
	                      if(REF_ARR_AMT==0)
	                      {
	                    	 
	                    	  var REF_ARR_AMT=response.getElementsByTagName("REF_ARR_AMT")[i].firstChild.nodeValue;
	                    	  if(REF_ARR_AMT=="null"||REF_ARR_AMT==null||REF_ARR_AMT=="")
	                          {
	                        	  REF_ARR_AMT="";
	                          }
	                      }
	                      
	                      
	                      
	                      if(REF_ARR_REL_YR==0)
	                      {
	                    	  var REF_ARR_REL_YR=response.getElementsByTagName("REF_ARR_REL_YR")[i].firstChild.nodeValue;
	                    	  
	                    	  if(REF_ARR_REL_YR=="null"||REF_ARR_REL_YR==null||REF_ARR_REL_YR==""||REF_ARR_REL_YR=='0')
	                          {
	                    		  REF_ARR_REL_YR="";
	                          }
	                      }
	                     
	                      
	                      
	                      
	                      if(REF_ARR_REL_MTH==0)
	                      {
	                      	  var REF_ARR_REL_MTH=response.getElementsByTagName("REF_ARR_REL_MTH")[i].firstChild.nodeValue;
	                      
	                      	if(REF_ARR_REL_MTH=="null"||REF_ARR_REL_MTH==null||REF_ARR_REL_MTH==""||REF_ARR_REL_MTH=='0')
	                        {
	                      		REF_ARR_REL_MTH="";
	                        }
	                      	else
	                      		REF_ARR_REL_MTH=month[REF_ARR_REL_MTH-1];
	                      }
	                      
	                  
	                    var type_trans=response.getElementsByTagName("type_trans")[i].firstChild.nodeValue;
	                    var amount=response.getElementsByTagName("amount")[i].firstChild.nodeValue;
	                    var remark=response.getElementsByTagName("remarks")[i].firstChild.nodeValue;
	                    var ref_amount=response.getElementsByTagName("ref_amount")[i].firstChild.nodeValue;
	                    var ref_no=response.getElementsByTagName("ref_no")[i].firstChild.nodeValue;
	                    var ref_total=response.getElementsByTagName("ref_total")[i].firstChild.nodeValue;
	                    var rec_amount=response.getElementsByTagName("rec_amount")[i].firstChild.nodeValue;
	                    var rec_no=response.getElementsByTagName("rec_no")[i].firstChild.nodeValue;
	                    var rec_total=response.getElementsByTagName("rec_total")[i].firstChild.nodeValue;
	                    var gpf_no=response.getElementsByTagName("gpf_no")[i].firstChild.nodeValue;
	                    var sub_no=response.getElementsByTagName("sub_seq")[i].firstChild.nodeValue;
	                    
	                    var process_id=response.getElementsByTagName("process_id")[i].firstChild.nodeValue;
	                    
	                    var unit_id=response.getElementsByTagName("acc_unit")[i].firstChild.nodeValue;
	                    
	                    var filetype=response.getElementsByTagName("filetype")[i].firstChild.nodeValue;
	                    
	                    
	                    var name=response.getElementsByTagName("name")[i].firstChild.nodeValue;
	                    var subscribe_slno=response.getElementsByTagName("subscribe_slno")[i].firstChild.nodeValue;
	                    
	                    
	                   
	                    
	                    if(remark=="null"||remark==null||remark=="noRemarks"||remark=="")
	                     {
	                        remark="noRemarks";
	                     }
	               
	                    if(name=="null"||name==null||name=="notmentioned"||name=="")
	                    {
	                    	name=" ";
	                    }
	                    
	                   var tr=document.createElement("TR");
	                    tr.id="a"+seq;
	                    var test="a"+seq;
	                    var td=document.createElement("TD");
	                    var anc=document.createElement("A");
	                    var url="javascript:pick('"+test+"')";
	                    anc.href=url;
	                  
	                    if(process_id=="FR"){
	                    	var edit=document.createTextNode("");
	                        td.appendChild(edit);
	                        document.getElementById("add").disabled = true;
	                        document.getElementById("errorButton").disabled = true; 
	                        validate="True";
	                   }
	                   else{
	                	   var edit=document.createTextNode("Edit");
	                   	   anc.appendChild(edit);
	                       td.appendChild(anc);
	                       document.getElementById("add").disabled = false; 
	                       validate="False";
	                   }
	                    
	                    tr.appendChild(td);
	                    var td=document.createElement("TD");
	                    var gpd=document.createTextNode(gpf_no);
	                    
	                    td.appendChild(gpd);
	                    var hab=document.createElement("TEXT");
	                    hab.type="hidden";
	                    hab.name="unit";
	                    hab.value=unit_id;
	                    td.appendChild(hab);
	                    tr.appendChild(td); 
	                    
	                    
	                    var td=document.createElement("TD");
	                    var nam=document.createTextNode(name);
	                    td.appendChild(nam);
	                    var fil=document.createElement("TEXT");
	                    fil.type="hidden";
	                    fil.name="ftype";
	                    fil.value=filetype;
	                    td.appendChild(fil);
	                    td.align="left";
	                    tr.appendChild(td); 
	              	                    
	                    var td=document.createElement("TD");
	                    var h1=document.createElement("TEXT");
	                    h1.type="hidden";
	                    h1.name="ac_month";
	                    h1.value=ac_month;
	                    td.appendChild(h1);
	                    var h5=document.createElement("TEXT");
	                    h5.type="hidden";
	                    h5.name="ac_year";
	                    h5.value=acyear;
	                    td.appendChild(h5);
	                    var h2=document.createTextNode(ac_month+"-"+acyear);
	                    td.appendChild(h2);
	                    tr.appendChild(td);
	                      
	                    var td=document.createElement("TD");
	                    var h3=document.createElement("TEXT");
	                    h3.type="hidden";
	                    h3.name="rel_month";
	                    h3.value=rel_month;
	                    td.appendChild(h3);
	                    var h6=document.createElement("TEXT");
	                    h6.type="hidden";
	                    h6.name="rel_year";
	                    h6.value=relyear;
	                    td.appendChild(h6);
	                    
	                    var h4=document.createTextNode(rel_month+"-"+relyear);
	                    td.appendChild(h4);
	                    tr.appendChild(td);
	                      
	                    var td=document.createElement("TD");
	                    var typetrans=document.createTextNode(type_trans);
	                    td.appendChild(typetrans);
	                    var h7=document.createElement("TEXT");
	                    h7.type="hidden";
	                    h7.name="remarks";
	                    h7.value=remark;
	                    td.appendChild(h7);
	                    tr.appendChild(td);
	                      
	                    var td=document.createElement("TD");
	                    var subamt=document.createTextNode(amount);
	                    td.appendChild(subamt);
	                    var hw=document.createElement("TEXT");
	                    hw.type="hidden";
	                    hw.name="sub";
	                    hw.value=sub_no;
	                    td.appendChild(hw);
	                    tr.appendChild(td);
	                    
	                    var td=document.createElement("TD");
	                    var refamt=document.createTextNode(ref_amount);
	                    td.appendChild(refamt);
	                    tr.appendChild(td);
	                      
	                    var td=document.createElement("TD");
	                    var h8=document.createElement("TEXT");
	                    h8.type="hidden";
	                    h8.name="ref_no";
	                    h8.value=ref_no;
	                    td.appendChild(h8);
	                    var h9=document.createElement("TEXT");
	                    h9.type="hidden";
	                    h9.name="ref_total";
	                    h9.value=ref_total;
	                    td.appendChild(h9);
	                    var h13=document.createTextNode(ref_no+"/"+ref_total);
	                    td.appendChild(h13);
	                    tr.appendChild(td);
	                    
	                    var td=document.createElement("TD");
	                    var recamt=document.createTextNode(rec_amount);
	                    td.appendChild(recamt);  
	                    var h10=document.createElement("TEXT");
	                    h10.type="hidden";
	                    h10.name="gpf";
	                    h10.value=gpf_no;
	                    td.appendChild(h10);
	                                 
	                    tr.appendChild(td);
	                     
	                    var td=document.createElement("TD");
	                    var h10=document.createElement("TEXT");
	                    h10.type="hidden";
	                    h10.name="rec_no";
	                    h10.value=rec_no;
	                    td.appendChild(h10);
	                    var h11=document.createElement("TEXT");
	                    h11.type="hidden";
	                    h11.name="rec_total";
	                    h11.value=rec_total;
	                    td.appendChild(h11);
	                    var h12=document.createTextNode(rec_no+"/"+rec_total);
	                    td.appendChild(h12);
	                    tr.appendChild(td);
	                 
	                   var td=document.createElement("TD");
	                    var h13=document.createElement("TEXT");
	                    h13.type="hidden";
	                    h13.name="subscribe_slno";
	                    h13.value=subscribe_slno;
	                    td.appendChild(h13);
	                    tr.appendChild(td);
	                  
	                   
	                    
	                    var td=document.createElement("TD");
	                    var REF_ARR_AMT=document.createTextNode(REF_ARR_AMT);
	                    td.appendChild(REF_ARR_AMT);
	                    tr.appendChild(td);
	                    
	                    
	                    var td=document.createElement("TD");
	                    var h1=document.createElement("TEXT");
	                    h1.type="hidden";
	                    h1.name="REF_ARR_REL_YR";
	                    h1.value=REF_ARR_REL_YR;
	                    td.appendChild(h1);
	                    var h5=document.createElement("TEXT");
	                    h5.type="hidden";
	                    h5.name="REF_ARR_REL_MTH";
	                    h5.value=REF_ARR_REL_MTH;
	                    td.appendChild(h5);
	                    var h2=document.createTextNode(REF_ARR_REL_MTH+"-"+REF_ARR_REL_YR);
	                    td.appendChild(h2);
	                    tr.appendChild(td);
	                    
	                   
	                    
	                    
	                    tlist.appendChild(tr);             
	                    seq++;
	                 } 
                    
                    var totsubamount=response.getElementsByTagName("subamount")[0].firstChild.nodeValue; 
                    var totrefamount=response.getElementsByTagName("refamount")[0].firstChild.nodeValue;   
                    var totarramount=response.getElementsByTagName("arramount")[0].firstChild.nodeValue; 
                    var totrefarramount=response.getElementsByTagName("rerarramount")[0].firstChild.nodeValue;
                    
                    
                   
                 
               document.getElementById("firstamount").value=parseInt(totsubamount,10);
               document.getElementById("secondamount").value=parseInt(totrefamount,10);     
               document.getElementById("thirdamount").value=parseInt(totarramount,10);     
               document.getElementById("tot_ref_arr_amt").value=parseInt(totrefarramount,10); 
               
               document.getElementById("totsubscription").value=parseInt(totsubamount,10)+parseInt(totarramount,10);
               
               document.getElementById("totrefund").value=parseInt(totrefamount,10)+parseInt(totrefarramount,10);
             
               if(len>0){
            	
                   data_value="true";
               }
               else{
            	  
            	   data_value="false";
               }
               
               call('getupload','s');
                }
                	catch(e){ alert(e);}
                	
                }
            	
            	document.getElementById("sub_total_all").value="";
            	document.getElementById("refund_total_all").value="";
            	document.getElementById("utotsubscription").value="";
            	document.getElementById("utotrefund").value="";
            }
       
                       
            else if(command=="getupload")
            {
            	if(flag=='success')
                {
            		
                	try{
                	  var tlist=document.getElementById("tupload");
                    while (tlist.childNodes.length > 0) {
                   	 tlist.removeChild(tlist.firstChild);
                    }
                    var len=response.getElementsByTagName("emp_id").length;
                   // alert("getUpload:len"+len+"data_value"+data_value);
                   var seq1=0;
                    for(var i=0;i<len;i++)
                    {
                    var emp_id=response.getElementsByTagName("emp_id")[i].firstChild.nodeValue;
                    var ac_month=response.getElementsByTagName("ac_month")[i].firstChild.nodeValue;
                    ac_month=month[ac_month-1];
                    var acyear=response.getElementsByTagName("ac_year")[i].firstChild.nodeValue;
                    var rel_month=response.getElementsByTagName("rel_month")[i].firstChild.nodeValue;
                    if(rel_month==0)
                    {
                    	rel_month="0";
                    }
                    
                    else
                    {
                    	 rel_month=month[rel_month-1];
                    }
                   // rel_month=month[rel_month-1];
                    var relyear=response.getElementsByTagName("rel_year")[i].firstChild.nodeValue;
                    var type_trans=response.getElementsByTagName("type_trans")[i].firstChild.nodeValue;
                    var amount=response.getElementsByTagName("amount")[i].firstChild.nodeValue;
                    var remark=response.getElementsByTagName("remarks")[i].firstChild.nodeValue;
                    var ref_amount=response.getElementsByTagName("ref_amount")[i].firstChild.nodeValue;
                    var ref_no=response.getElementsByTagName("ref_no")[i].firstChild.nodeValue;
                    var ref_total=response.getElementsByTagName("ref_total")[i].firstChild.nodeValue;
                    var rec_amount=response.getElementsByTagName("rec_amount")[i].firstChild.nodeValue;
                    var rec_no=response.getElementsByTagName("rec_no")[i].firstChild.nodeValue;
                    var rec_total=response.getElementsByTagName("rec_total")[i].firstChild.nodeValue;
                    var gpf_no=response.getElementsByTagName("gpf_no")[i].firstChild.nodeValue;
                    var sub_no=response.getElementsByTagName("sub_seq")[i].firstChild.nodeValue;
                   
                    var process_id=response.getElementsByTagName("process_id")[i].firstChild.nodeValue;
                    
                    var name=response.getElementsByTagName("name")[i].firstChild.nodeValue;
                    var filetype=response.getElementsByTagName("filetype")[i].firstChild.nodeValue;
                      var subscribe_slno=response.getElementsByTagName("subscribe_slno")[i].firstChild.nodeValue;            
                    
                     // alert(subscribe_slno+"maonr")
	                   // var REF_ARR_AMT=response.getElementsByTagName("REF_ARR_AMT")[i].firstChild.nodeValue;
	                    //alert("REF_ARR_AMT----->"+REF_ARR_AMT);
	                    //var REF_ARR_REL_YR=response.getElementsByTagName("REF_ARR_REL_YR")[i].firstChild.nodeValue;
	                    //var REF_ARR_REL_MTH=response.getElementsByTagName("REF_ARR_REL_MTH")[i].firstChild.nodeValue;
                      var REF_ARR_AMT=0;
                      var REF_ARR_REL_YR=0;
                      var REF_ARR_REL_MTH=0;
                      
                      
                      
                      
                      if(REF_ARR_AMT==0)
                      {
                    	 
                    	  var REF_ARR_AMT=response.getElementsByTagName("ref_arr_amt")[i].firstChild.nodeValue;
                    	  if(REF_ARR_AMT=="null"||REF_ARR_AMT==null||REF_ARR_AMT=="")
                          {
                        	  REF_ARR_AMT="";
                          }
                      }
                      
                      
                      
                      if(REF_ARR_REL_YR==0)
                      {
                    	  var REF_ARR_REL_YR=response.getElementsByTagName("ref_arr_rel_yr")[i].firstChild.nodeValue;
                    	  if(REF_ARR_REL_YR=="null"||REF_ARR_REL_YR==null||REF_ARR_REL_YR=="")
                          {
                    		  REF_ARR_REL_YR="";
                          }
                      }
                     
                      
                      
                      
                      if(REF_ARR_REL_MTH==0)
                      {
                      	  var REF_ARR_REL_MTH=response.getElementsByTagName("ref_arr_rel_mth")[i].firstChild.nodeValue;
                      	if(REF_ARR_REL_MTH=="null"||REF_ARR_REL_MTH==null||REF_ARR_REL_MTH=="")
                        {
                      		REF_ARR_REL_MTH="";
                        }
                      	else
                      		REF_ARR_REL_MTH=month[REF_ARR_REL_MTH-1];
                      }
                      
                      
                      
                    if(remark=="null"||remark==null||remark=="noRemarks"||remark=="")
                     {
                        remark="noRemarks";
                     }
                    
                    
                    if(name=="null"||name==null||name=="notmentioned"||name=="" || name=="No")
                    {
                    	name=" ";
                    }
                     
                    var tr=document.createElement("TR");
                    tr.id=seq1;
          
                    var td=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:pick('"+seq1+"')";
                    anc.href=url;
                    
                    if(process_id=="FR"){
                    	var edit=document.createTextNode("");
                        td.appendChild(edit);
                        document.getElementById("add").disabled = true; 
                        document.getElementById("errorButton").disabled = true; 
                        validate="True";
                   }
                   else{
                	   var edit=document.createTextNode("Edit");
                   	   anc.appendChild(edit);
                       td.appendChild(anc);
                       document.getElementById("add").disabled = false; 
                       validate="False";
                   }
                    tr.appendChild(td);
                    
                    var tdg=document.createElement("TD");
                    var gpd=document.createTextNode(gpf_no);
                    tdg.appendChild(gpd);
                    tr.appendChild(tdg); 
                    
                    var tdname=document.createElement("TD");
                    var nam=document.createTextNode(name);
                    tdname.appendChild(nam);
                    tdname.align="left";
                    var fil1=document.createElement("TEXT");
                    fil1.type="hidden";
                    fil1.name="ftype";
                    fil1.value=filetype;
                    tdname.appendChild(fil1);
                    tr.appendChild(tdname); 
                                                        
                    var td2=document.createElement("TD");
                    var h1=document.createElement("TEXT");
                    h1.type="hidden";
                    h1.name="ac_month";
                    h1.value=ac_month;
                    td2.appendChild(h1);
                    var h5=document.createElement("TEXT");
                    h5.type="hidden";
                    h5.name="ac_year";
                    h5.value=acyear;
                    td2.appendChild(h5);
                    var h2=document.createTextNode(ac_month+"-"+acyear);
                    td2.width="270";
                    td2.appendChild(h2);
                    tr.appendChild(td2);
                      
                    var td3=document.createElement("TD");
                    var h3=document.createElement("TEXT");
                    h3.type="hidden";
                    h3.name="rel_month";
                    h3.value=rel_month;
                    td3.appendChild(h3);
                    var h6=document.createElement("TEXT");
                    h6.type="hidden";
                    h6.name="rel_year";
                    h6.value=relyear;
                    td3.appendChild(h6);
                    var h4=document.createTextNode(rel_month+"-"+relyear);
                 //   td3.width="70";
                    td3.appendChild(h4);
                    tr.appendChild(td3);
                      
                    var td4=document.createElement("TD");
                    var typetrans=document.createTextNode(type_trans);
                    td4.appendChild(typetrans);
                    var h7=document.createElement("TEXT");
                    h7.type="hidden";
                    h7.name="remarks";
                    h7.value=remark;
                    td4.appendChild(h7);
                   // td4.width="30";
                    tr.appendChild(td4);
                    
                   
                    
                    var td5=document.createElement("TD");
                    var subamt=document.createTextNode(amount);
                    td5.appendChild(subamt);
                    var hw=document.createElement("TEXT");
                    hw.type="hidden";
                    hw.name="sub";
                    hw.value=sub_no;
                    td5.appendChild(hw);
                    //td5.width="60";
                    td5.align="right";
                    tr.appendChild(td5);
                    
                    var td6=document.createElement("TD");
                    var refamt=document.createTextNode(ref_amount);
                    td6.appendChild(refamt);
                   // td6.width="60";
                    td6.align="right";
                    tr.appendChild(td6);
                      
                    var td7=document.createElement("TD");
                    var h8=document.createElement("TEXT");
                    h8.type="hidden";
                    h8.name="ref_no";
                    h8.value=ref_no;
                    td7.appendChild(h8);
                    var h9=document.createElement("TEXT");
                    h9.type="hidden";
                    h9.name="ref_total";
                    h9.value=ref_total;
                    td7.appendChild(h9);
                    var h13=document.createTextNode(ref_no+"/"+ref_total);
                  //  td7.width="40";
                    td7.appendChild(h13);
                    tr.appendChild(td7);
                    
                    var td8=document.createElement("TD");
                    var recamt=document.createTextNode(rec_amount);
                    td8.appendChild(recamt);  
                    var h10=document.createElement("TEXT");
                    h10.type="hidden";
                    h10.name="gpf";
                    h10.value=gpf_no;
                  //  td8.width="60";
                    td8.align="right";
                    td8.appendChild(h10);
                                 
                    tr.appendChild(td8);
                     
                    var td9=document.createElement("TD");
                    var h10=document.createElement("TEXT");
                    h10.type="hidden";
                    h10.name="rec_no";
                    h10.value=rec_no;
                    td9.appendChild(h10);
                    var h11=document.createElement("TEXT");
                    h11.type="hidden";
                    h11.name="rec_total";
                    h11.value=rec_total;
                    td9.appendChild(h11);
                    var h12=document.createTextNode(rec_no+"/"+rec_total);
                  //  td9.width="40";
                    td9.appendChild(h12);
                    tr.appendChild(td9);
                    
                    
                    
                    var td10=document.createElement("TD");
                    var h13=document.createElement("TEXT");
                    h13.type="hidden";
                    h13.name="subscribe_slno";
                    h13.value=subscribe_slno;
                    td10.appendChild(h13);
                    tr.appendChild(td10);
                    
                    var td=document.createElement("TD");
                    var REF_ARR_AMT=document.createTextNode(REF_ARR_AMT);
                    td.appendChild(REF_ARR_AMT);
                    tr.appendChild(td);
                    
                    
                    var td=document.createElement("TD");
                    var h1=document.createElement("TEXT");
                    h1.type="hidden";
                    h1.name="REF_ARR_REL_YR";
                    h1.value=REF_ARR_REL_YR;
                    td.appendChild(h1);
                    var h5=document.createElement("TEXT");
                    h5.type="hidden";
                    h5.name="REF_ARR_REL_MTH";
                    h5.value=REF_ARR_REL_MTH;
                    td.appendChild(h5);
                    if(REF_ARR_REL_MTH==0)
                    	var h2=document.createTextNode('');
                    else
                    	var h2=document.createTextNode(REF_ARR_REL_MTH+"-"+REF_ARR_REL_YR);
                    td.appendChild(h2);
                    tr.appendChild(td);
                    
                    tlist.appendChild(tr); 
                    
                    seq1++;
                    } 
                    
                    var totsubamount=response.getElementsByTagName("subamount")[0].firstChild.nodeValue; 
                    var totrefamount=response.getElementsByTagName("refamount")[0].firstChild.nodeValue;   
                    var totarramount=response.getElementsByTagName("arramount")[0].firstChild.nodeValue; 
                  //  alert(totsubamount+""+totrefamount+""+totarramount);
                   // alert("length is----->"+len);
                   // alert("data_value--->"+data_value);
               document.getElementById("ufirstamount").value=parseInt(totsubamount,10);
               document.getElementById("usecondamount").value=parseInt(totrefamount,10);     
               document.getElementById("uthirdamount").value=parseInt(totarramount,10);     
              
               document.getElementById("utotsubscription").value=parseInt(totsubamount,10)+parseInt(totarramount,10);
              
               document.getElementById("utotrefund").value=parseInt(totrefamount,10); 
            
               if(len>0 || data_value =="true"){
            	//   alert("inside if cindition");
            	 document.getElementById("uploaded_data").style.display="block";
            	 document.getElementById("TB_data").style.display="block";            	 
                 call('trialbalance','s');
                 
                // document.Hrm_TransJoinForm.add.disabled=false;
	               document.getElementById("Validate").disabled = false;
	           //    document.getElementById("errorButton").disabled = false;
               }
               else{
            	  // alert("inside else cindition");
            	   document.getElementById("uploaded_data").style.display="none";
            	   document.getElementById("TB_data").style.display="none";  
            	   var File_Valid=response.getElementsByTagName("File_Valid")[0].firstChild.nodeValue;
            	   if(File_Valid=='Invalid'){
            		   alert("Uploaded DBF file is not valid for Subscription.Try to Overwrite the correct DBF file");
            		   clear_select();
            	   }
            	   else{            		   
            		   alert("Records not yet uploaded for this month and year");		              
            	   }
            	   document.Hrm_TransJoinForm.add.disabled=false;
	               document.getElementById("Validate").disabled = true;
	               document.getElementById("errorButton").disabled = true;
	              
	               stopwaiting(document.Hrm_TransJoinForm) ;
               }
               document.getElementById("data_thru_form").style.display="block";
            }
                	catch(e){ //alert("Error Here"+e);
            }
           }
          }     
          else if(command=="Add")
          {
                if(flag=='success')
                {
                	 if (window.opener && !window.opener.closed) {
                		 window.opener.location.reload();
                    	 window.opener.focus();        
                	 } 
                	 alert("Added Successfully");
                     window.close();  
                }
                else if(flag=='Available')
                        alert("Record Already Exists");
            }
          else if(command=="Update")
            {
                if(flag=='success')
                {  
                	 window.close();
                	
                	if (window.opener && !window.opener.closed) {
                        
               	//	alert('here');
                		call('getpop','s');
              		
             
               	 } 
             }
                else
                    alert("Failure in Update");
            }
          else if(command=="Delete")
            {
                if(flag=='success')
                {   
                     
                	 
               	 window.close();

            	 if (window.opener && !window.opener.closed) {
          
            		 window.opener.location.reload();
                	 window.opener.focus();        
            		
          
            	 } 
               	 winemp.close();         	                	
                 alert("Deleted Successfully");
                }
                else
                {
                    alert("Not success in Delete");
                    }
            
        }
          else if(command=="trialbalance")
          {
        	  if(flag=='success')
              {
            	  document.getElementById("trial_sub_cr").value=response.getElementsByTagName("trial_sub_cr")[0].firstChild.nodeValue;
            	  document.getElementById("trial_sub_db").value=response.getElementsByTagName("trial_sub_db")[0].firstChild.nodeValue; 
            	  document.getElementById("trial_ref_cr").value=response.getElementsByTagName("trial_ref_cr")[0].firstChild.nodeValue; 
            	  document.getElementById("trial_ref_db").value=response.getElementsByTagName("trial_ref_db")[0].firstChild.nodeValue; 
            	  
            	  trialbalancesub=parseInt(document.getElementById("trial_sub_cr").value)-parseInt(document.getElementById("trial_sub_db").value); 
            	  //trialbalancesub1=response.getElementsByTagName("trial_sub1")[0].firstChild.nodeValue;
            	  trialbalanceref=parseInt(document.getElementById("trial_ref_cr").value)-parseInt(document.getElementById("trial_ref_db").value);
            	            	 
            	  document.getElementById("trialbalancesub").value=trialbalancesub;
            	 // document.getElementById("trialbalancesub1").value=trialbalancesub1;
            	  document.getElementById("trialbalanceref").value=trialbalanceref;
            	  
            	  
            	usubscription=document.getElementById("utotsubscription").value;
            	subscription=document.getElementById("totsubscription").value;
            	
            	uref=document.getElementById("utotrefund").value;
            	ref=document.getElementById("totrefund").value;
            	
            	totsubscription=parseInt(usubscription,10)+parseInt(subscription,10);
            	
            	totref=parseInt(uref,10)+parseInt(ref,10);
            	 
            	    
            	  document.getElementById("sub_total_all").value=parseInt(totsubscription,10);
            	  
            	  document.getElementById("refund_total_all").value=parseInt(totref,10);
            	  
document.getElementById("totalbalancesub").value=parseInt(totsubscription,10);
            	  
            	  document.getElementById("totalbalanceref").value=parseInt(totref,10);
            	//  adjustmentsub=parseInt(trialbalancesub,10)+parseInt(trialbalancesub1,10)-parseInt(totsubscription,10);
            	  adjustmentsub=parseInt(trialbalancesub,10)-parseInt(totsubscription,10);
            	  adjustmentref=parseInt(trialbalanceref,10)-parseInt(totref,10);
            	  document.getElementById("adjustmentsub").value=parseInt(adjustmentsub,10);
            	  document.getElementById("adjustmentref").value=parseInt(adjustmentref,10);
            	  final_diff_sub=adjustmentsub;
            	  final_diff_ref=adjustmentref;
            	  
            	//  document.getElementById('final_TB_sub').value=parseInt(document.getElementById("trialbalancesub").value)+parseInt(document.getElementById("trialbalancesub1").value);
            	  document.getElementById('final_TB_sub').value=parseInt(document.getElementById("trialbalancesub").value);
          		  document.getElementById('final_TB_ref').value=parseInt(document.getElementById("trialbalanceref").value);
            	 
            	  
            	  
              }
              call('AdjustmentJournal','s');
          }
            	  
            	  //******For Journal Values Shown********************
          else if(command=="AdjustmentJournal")
          {
     
              if(flag=='success')
              {	  
            	  
            	  var command1=response.getElementsByTagName("command1")[0].firstChild.nodeValue;
            	  var recno=0;
            	  try{
            		 
            			//  alert("journal1");
            		  document.getElementById('journal_Adjustment').style.display="block"; 
            		  document.getElementById('excluded_title').style.display="block";
            		  document.getElementById('journalforMan_Adjustment').style.display="none";
            		  document.getElementById('included_title').style.display="none";
            		  document.getElementById('Adj_Jrnl_diff').style.display="none";
            			  var tlist=document.getElementById("journal1");
            		 	
	                  while (tlist.childNodes.length > 0) {
	                 	 tlist.removeChild(tlist.firstChild);
	                  }
            	 		  var journal_val=response.getElementsByTagName("journal_val");
	                      var journal_val_len=journal_val.length;
	                      if(journal_val_len>0){
	                    	  document.getElementById('journal_real').style.display="block";
	                    	  document.getElementById('journal_Adjustment').style.display="block";
	                    	  document.getElementById('excluded_title').style.display="block";
	                      }
	                      else{
	                    	  document.getElementById('journal_real').style.display="none";
	                    	  document.getElementById('journal_Adjustment').style.display="none";
	                    	  document.getElementById('excluded_title').style.display="none";
	                      }
	                      for(var count=0;count<journal_val_len;count+=8){
	                    	  var tr=document.createElement("TR");
	                          tr.id=recno;
	                    	  for(var i=count;i<count+8;i++){
		                    	  var val=response.getElementsByTagName("journal_val")[i].firstChild.nodeValue;
		                    	  var tdg=document.createElement("TD");
		                          var td_val=document.createTextNode(val);
		                          tdg.appendChild(td_val);
		                          if(i+1==count+8)
		                        	  tdg.align="right";
		                          tr.appendChild(tdg); 
		                       }
	                    	  tlist.appendChild(tr);
	                      }
	                  	                    	  
//*****For debit the subscription amount and refund amount from adjustment journal**********	
	                var sub_length= response.getElementsByTagName("sub_cr_amt").length;
	                var sub_cr_amt=0, ref_cr_amt=0,sub_dr_amt=0, ref_dr_amt=0;
	           
	                for(i=0;i<sub_length;i++){
	                	sub_cr_amt=parseInt(sub_cr_amt,10)+parseInt(response.getElementsByTagName("sub_cr_amt")[i].firstChild.nodeValue);
	                  	ref_cr_amt=parseInt(ref_cr_amt)+parseInt(response.getElementsByTagName("ref_cr_amt")[i].firstChild.nodeValue);
	                  	sub_dr_amt=parseInt(sub_dr_amt,10)+parseInt(response.getElementsByTagName("sub_dr_amt")[i].firstChild.nodeValue);
	                  	ref_dr_amt=parseInt(ref_dr_amt)+parseInt(response.getElementsByTagName("ref_dr_amt")[i].firstChild.nodeValue);
	                }
	                var diff_sub_amt=parseInt(adjustmentsub,10)-parseInt(sub_cr_amt,10);
	                var diff_ref_amt=parseInt(adjustmentref,10)-parseInt(ref_cr_amt,10);
	              //  document.getElementById("netpay_sub").value=parseInt(trialbalancesub,10)+parseInt(trialbalancesub1,10)-parseInt(sub_cr_amt,10);
	                document.getElementById("netpay_sub").value=parseInt(trialbalancesub,10)-parseInt(sub_cr_amt,10)+parseInt(sub_dr_amt,10);
	                document.getElementById("netpay_ref").value=parseInt(trialbalanceref,10)-parseInt(ref_cr_amt,10)+parseInt(ref_dr_amt,10); 
	                document.getElementById("journal_sub_cr").value=parseInt(sub_cr_amt,10);
	                document.getElementById("journal_ref_cr").value=parseInt(ref_cr_amt,10);
	                document.getElementById("journal_sub_dr").value=parseInt(sub_dr_amt,10);
	                document.getElementById("journal_ref_dr").value=parseInt(ref_dr_amt,10);

	                final_diff_sub=parseInt(document.getElementById("netpay_sub").value)-parseInt(document.getElementById("totalbalancesub").value);
	                final_diff_ref=parseInt(document.getElementById("netpay_ref").value)-parseInt(document.getElementById("totalbalanceref").value);
	                document.getElementById('final_TB_sub').value=parseInt(document.getElementById("netpay_sub").value);
	          		document.getElementById('final_TB_ref').value=parseInt(document.getElementById("netpay_ref").value);
	            	
	          		
	               
	                }      
              	  catch(e){  }
            	  //****************End of Journal Values Shown*****************            	  
            	 
              }
          
              if(final_diff_sub==0 && final_diff_ref==0 ){ 
            	  	document.getElementById('final_TB_sub').value=parseInt(document.getElementById("netpay_sub").value);
            		document.getElementById('final_TB_ref').value=parseInt(document.getElementById("netpay_ref").value);
            		call('Error','s');  
              }
              else{
            	  document.getElementById('journal_real').style.display="block";
            	  document.getElementById('journal_Adjustment').style.display="block";
            	  document.getElementById('excluded_title').style.display="block";
            	  if(journal_val_len==0){
           		   var tr=document.createElement("TR");
            		  var tdg=document.createElement("TD");
                      var td_val=document.createTextNode("No Entries");
                      tdg.style.fontSize = "16pt";
                      tdg.style.fontWeight = "bold";
                      tdg.appendChild(td_val);
                      tdg.colSpan="8";
                      tdg.align="center";
                      tr.appendChild(tdg); 
                     tlist.appendChild(tr);
            	  }
            	  
              }
              call('AdjustmentJournal1','s');
              stopwaiting(document.Hrm_TransJoinForm) ;
          }          
            /*******************************************/
          else if(command=="AdjustmentJournal1")
          {   
            if(flag=='success')
            {	  
           	  var command1=response.getElementsByTagName("command1")[0].firstChild.nodeValue;
          	  var recno=0;
          	  try{
          		 document.getElementById('journalforMan_Adjustment').style.display="block";
          		 document.getElementById('included_title').style.display="block";
          		 document.getElementById('Adj_Jrnl_diff').style.display="block";
          		
          			  var tlist=document.getElementById("journal2");
          			  while (tlist.childNodes.length > 0) {
	                 	 tlist.removeChild(tlist.firstChild);
	                  }
          	    		  var journal_val=response.getElementsByTagName("journal_val");
	            		  var journal_val_len=journal_val.length;
	            		  if(journal_val_len>0){
	                    	  document.getElementById('Adj_Jrnl_diff').style.display="block";
	                    	  document.getElementById('journalforMan_Adjustment').style.display="block";
	                    	  document.getElementById('included_title').style.display="block";
	                      }
	                      else{
	                    	  document.getElementById('Adj_Jrnl_diff').style.display="none";
	                    	  document.getElementById('journalforMan_Adjustment').style.display="none";
	                    	  document.getElementById('included_title').style.display="none";
	                      }
	                      for(var count=0;count<journal_val_len;count+=8){
	                    	  var tr=document.createElement("TR");
	                          tr.id=recno;
	                    	  for(var i=count;i<count+8;i++){
		                    	  var val=response.getElementsByTagName("journal_val")[i].firstChild.nodeValue;
		                    	  var tdg=document.createElement("TD");
		                          var td_val=document.createTextNode(val);
		                          tdg.appendChild(td_val);
		                          if(i+1==count+8)
		                        	  tdg.align="right";
		                          tr.appendChild(tdg); 
		                       }
	                    	  tlist.appendChild(tr);
	                      }
	                  	                    	  
//*****For debit the subscription amount and refund amount from adjustment journal**********	
	                var sub_length= response.getElementsByTagName("sub_cr_amt").length;
	                var sub_cr_amt=0, ref_cr_amt=0,sub_dr_amt=0, ref_dr_amt=0;
	           
	                for(i=0;i<sub_length;i++){
	                	sub_cr_amt=parseInt(sub_cr_amt,10)+parseInt(response.getElementsByTagName("sub_cr_amt")[i].firstChild.nodeValue);
	                  	ref_cr_amt=parseInt(ref_cr_amt)+parseInt(response.getElementsByTagName("ref_cr_amt")[i].firstChild.nodeValue);
	                  	sub_dr_amt=parseInt(sub_dr_amt,10)+parseInt(response.getElementsByTagName("sub_dr_amt")[i].firstChild.nodeValue);
	                  	ref_dr_amt=parseInt(ref_dr_amt)+parseInt(response.getElementsByTagName("ref_dr_amt")[i].firstChild.nodeValue);
	                 }
	                var diff_sub_amt=parseInt(adjustmentsub,10)-parseInt(sub_cr_amt,10);
	                var diff_ref_amt=parseInt(adjustmentref,10)-parseInt(ref_cr_amt,10);
	         
	                document.getElementById("journal1_sub_cr").value=parseInt(sub_cr_amt,10);
	                document.getElementById("journal1_ref_cr").value=parseInt(ref_cr_amt,10);
	                document.getElementById("journal1_sub_dr").value=parseInt(sub_dr_amt,10);
	                document.getElementById("journal1_ref_dr").value=parseInt(ref_dr_amt,10);
	                
	                document.getElementById("adjustmentsub1").value=parseInt(document.getElementById("netpay_sub").value)-parseInt(totsubscription,10);
	                document.getElementById("adjustmentref1").value=parseInt(document.getElementById("netpay_ref").value)-parseInt(totref,10);
	                
	                document.getElementById("netpay_sub1").value=parseInt(document.getElementById('final_TB_sub').value,10)+parseInt(document.getElementById("journal1_sub_cr").value,10)-parseInt(document.getElementById("journal1_sub_dr").value,10);
	                document.getElementById("netpay_ref1").value=parseInt(document.getElementById('final_TB_ref').value,10)+parseInt(document.getElementById("journal1_ref_cr").value,10)-parseInt(document.getElementById("journal1_ref_dr").value,10);
	                
	                final_diff_sub=parseInt(document.getElementById("netpay_sub1").value,10)-parseInt(document.getElementById("totalbalancesub").value,10);
	                final_diff_ref=parseInt(document.getElementById("netpay_ref1").value,10)-parseInt(document.getElementById("totalbalanceref").value,10);
	                
	                
	                
	            	document.getElementById('final_TB_sub').value=parseInt(document.getElementById("netpay_sub1").value);
            		document.getElementById('final_TB_ref').value=parseInt(document.getElementById("netpay_ref1").value);
	             }
          	   	 catch(e){  }

          	  //****************End of Journal Values Shown*****************
            	  call('Error','s');
          	
            }
            
            
        }
            
            
            
            /******************************************/
          else if(command=="Error"){
        	//  alert("Error");
        	var tagresult=response.getElementsByTagName("result")[0];
            var result=tagresult.firstChild.nodeValue;
         
           	if(result=="Valid"  ){
            	document.getElementById("Validate").disabled = false;
           	}
            else{
             	document.getElementById("Validate").disabled = true;
             }
          
           	if(result=="Valid" || validate=="True")
                document.getElementById("errorButton").disabled = true;
         	else {         		
         		document.getElementById("errorButton").disabled = false;
         	}
           	if(validate=="True"){
           		alert("This File Already Validated");
           		document.getElementById("Validate").disabled = true;
           	    
           	   document.Hrm_TransJoinForm.add.disabled=true;
           	}
        //   	if(result=="Valid" && document.getElementById("Validate").disabled == true && validate=="False")
           //		alert("Form value and Trial Balance value is not tally");
           //	call('Push','s');
          }
        else if(command=="unit")
            {
                if(flag=='success')
                {
                     var unit_id=response.getElementsByTagName("unit_id")[0].firstChild.nodeValue;
                     document.getElementById("oldcode").value=unit_id;
               }
                else
                {
                  
                    alert("problem in unit_id");
                }
            
        }
            
        else if(command=="Validate"){
            var result=response.getElementsByTagName("result")[0].firstChild.nodeValue;
            if(result=="Already"){
                alert("The Corresponding Files are already validated");                           
            }
            else if(result=="Success"){
            	
                alert("Successfully Validated");
            }
            else if(result=="Duplicate"){
            	var gpf_no=response.getElementsByTagName("gpf_no")[0].firstChild.nodeValue;
            	alert("Duplicate Entries Allowed in DBF file upload for GPFNo:"+gpf_no+"  So Before Validate You should update or delete that entry");
            }
            else if(result=="Wrong"){
            	alert("Data incorrect,check the data");
            }
            else{
            	
            }
            clear();
            clear_select();
            clear_journal();
        }    
        }
       else{}
    }
           
}
function clear1()
{
    document.Hrm_TransJoinForm.amount.value="";
        document.Hrm_TransJoinForm.ref_amount.value="";
        document.Hrm_TransJoinForm.ref_no.value="";
        document.Hrm_TransJoinForm.ref_total.value="";
      
        document.Hrm_TransJoinForm.remarks.value="";
        document.Hrm_TransJoinForm.trans[0].checked='checked';
       
        document.Hrm_TransJoinForm.ac_month.disabled=false;
        document.Hrm_TransJoinForm.ac_year.disabled=false;
        document.getElementById("refund_div").disabled=false;
       document.Hrm_TransJoinForm.ref_amount.disabled=false; 
       document.Hrm_TransJoinForm.ref_no.disabled=false;
       document.Hrm_TransJoinForm.ref_total.disabled=false;
       
    
        
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
   
        
        document.getElementById("rel_year").options[5].selected='selected';
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
        
         document.Hrm_TransJoinForm.add.disabled=false;
        document.Hrm_TransJoinForm.update.disabled=true;
        document.Hrm_TransJoinForm.delete1.disabled=true;
}

function clearGPF()
{
          
        document.Hrm_TransJoinForm.ac_month.disabled=false;
        document.Hrm_TransJoinForm.ac_year.disabled=false;
        displayMonth();
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        document.Hrm_TransJoinForm.designation.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.amount.value="";
        document.Hrm_TransJoinForm.ref_amount.value="";
        document.Hrm_TransJoinForm.ref_no.value="";
        document.Hrm_TransJoinForm.ref_total.value="";
        //document.Hrm_TransJoinForm.rec_amount.value="";
        //document.Hrm_TransJoinForm.rec_no.value="";
       // document.Hrm_TransJoinForm.rec_total.value="";
        document.Hrm_TransJoinForm.remarks.value="";
        document.Hrm_TransJoinForm.trans[0].checked='checked';
       // document.Hrm_TransJoinForm.refund.checked=false;
        //document.Hrm_TransJoinForm.arrear.checked=false;
        document.Hrm_TransJoinForm.txtEmpId.focus();
      
        var tlist=document.getElementById("tlist");
        try{
                //tlist.innerText="";
                tlist.innerHTML="";
            
       }
    catch(e)
       {
                tlist.innerText="";
                //tlist.innerHTML="";
               
       }
       
        document.Hrm_TransJoinForm.add.disabled=false;
        document.Hrm_TransJoinForm.update.disabled=true;
        document.Hrm_TransJoinForm.delete1.disabled=true;
        
}

function call(command,fileType)
{
	xmlhttp=getxmlhttpObject();
	if(xmlhttp==null){
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
        var ref_amount,ref_no,ref_total;
        var emp_id;
        var amount;
        var rec_amount,rec_no,rec_total;
        var remarks;
        var subamount;
        var arramount;
        var sub_no;
        var url="";
        if(command=="Add")
        {                
        	if(nullCheckpop())
        	{
        		if(acccheck()){
                gpf_no=document.getElementById("txtGpfNo").value;
                office_id=document.getElementById("officeid").value;
                division_id=document.getElementById("oldcode").value;
                emp_id=document.getElementById("txtEmpId").value;
                ac_month=document.getElementById("acmonth").value;
                ac_year=document.getElementById("acyear").value;
                rel_month=document.getElementById("rel_month").value;
                rel_year=document.getElementById("rel_year").value;
                unit=document.getElementById("rel_year").value;
               
                remarks=document.getElementById("remarks").value;
                 if(remarks=="null"||remarks==""||remarks==null||remarks==" ")
                     {
                        remarks="noRemarks";
                     }
                 
                 
                 
                 subamount=0;
                 arramount=0;
                 ref_amount=0;
                 ref_no=0;
                 ref_total=0;
                 subamount=document.Hrm_TransJoinFormpop.amount.value;
                
                 if(subamount=="")
                 {
                	 subamount=0; 
                 }
                if(document.Hrm_TransJoinFormpop.trans[0].checked)
                {
            	   arramount=document.Hrm_TransJoinFormpop.arramt.value;
                }else  if(document.Hrm_TransJoinFormpop.trans[1].checked){
                	ref_amount=document.Hrm_TransJoinFormpop.ref_amount.value;
                    ref_no=document.Hrm_TransJoinFormpop.ref_no.value;
                    ref_total=document.Hrm_TransJoinFormpop.ref_total.value;
                }
                 
                 type_trans="CR";
                 
                url="../../../../../GPF_Subscription_new.view?command=Add&office_id="+office_id+"&gpf_no="+gpf_no+"&division_id="+division_id+"&emp_id="+emp_id+"&ac_month="+ac_month+"&ac_year="+ac_year+"&type_trans="+type_trans+"&remarks="+remarks+"&rel_month="+rel_month+"&rel_year="+rel_year;
                url=url+"&ref_amount="+ref_amount+"&ref_no="+ref_no+"&ref_total="+ref_total;
                url=url+"&subamount="+subamount+"&arramount="+arramount;
	             url=url+"&sid="+Math.random();
	             xmlhttp.open("GET",url,true);
	             xmlhttp.onreadystatechange=function()
	             {
	            	 stateChanged(xmlhttp);
	             }
	             xmlhttp.send(null);  
        }
        }
    }
    else if(command=="Updatejj")
    { 
    	if(acccheck()){      
    	 gpf_no=document.getElementById("txtGpfNo").value;
         office_id=document.getElementById("officeid").value;
         division_id=document.getElementById("oldcode").value;
         emp_id=document.getElementById("txtEmpId").value;
         ac_month=document.getElementById("acmonth").value;
         ac_year=document.getElementById("acyear").value;
         rel_month=document.getElementById("rel_month").value;
         rel_year=document.getElementById("rel_year").value;      
         
        
         remarks=document.getElementById("remarks").value;
          if(remarks=="null"||remarks==""||remarks==null||remarks==" ")
              {
                 remarks="noRemarks";
              }
          
          
          
          subamount=0;
          arramount=0;
          ref_amount=0;
          ref_no=0;
          ref_total=0;
          subamount=document.Hrm_TransJoinFormpop.amount.value;
          
          if(subamount=="")
          {
         	 subamount=0; 
          }
         if(document.Hrm_TransJoinFormpop.trans[0].checked)
         {
     	   arramount=document.Hrm_TransJoinFormpop.arramt.value;
         }else if(document.Hrm_TransJoinFormpop.trans[1].checked){
         	ref_amount=document.Hrm_TransJoinFormpop.ref_amount.value;
             ref_no=document.Hrm_TransJoinFormpop.ref_no.value;
             ref_total=document.Hrm_TransJoinFormpop.ref_total.value;
         }
          
        
          
          type_trans="CR";
          
          sub_no=document.getElementById("sub_no").value;   
         
          
              
       url="../../../../../GPF_Subscription_new.view?command=Update&office_id="+office_id+"&gpf_no="+gpf_no+"&emp_id="+emp_id+"&ac_month="+ac_month+"&ac_year="+ac_year+"&type_trans="+type_trans+"&remarks="+remarks+"&rel_month="+rel_month+"&rel_year="+rel_year;
               
       
                    url=url+"&ref_amount="+ref_amount+"&ref_no="+ref_no+"&sub_no="+sub_no+"&division_id="+division_id+"&ref_total="+ref_total;
                    url=url+"&subamount="+subamount+"&arramount="+arramount;             
                
                
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=function()
        {
        	stateChanged(xmlhttp);
        }
        xmlhttp.send(null);   
    	}
    }
    else if(command=="Delete")
    {           //office_id=document.getElementById("txtOffId").value;
    	
                //emp_id=document.getElementById("txtEmpId").value;
    	 gpf_no=document.getElementById("txtGpfNo").value;
    	 ac_month=document.getElementById("acmonth").value;
    	 ac_year=document.getElementById("acyear").value;
                
    	 sub_no=document.getElementById("sub_no").value;          
    	 url="../../../../../GPF_Subscription_new.view?command=Delete&gpf_no="+gpf_no+"&sub_no="+sub_no+"&ac_month="+ac_month+"&ac_year="+ac_year;
        
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=function()
        {
        	stateChanged(xmlhttp);
        }
        xmlhttp.send(null);     
    }
   
    else if(command=="get")
    {   
    	  startwaiting(document.Hrm_TransJoinForm) ;
    	  service=null;
        var gpf_no=document.Hrm_TransJoinFormpop.txtGpfNo.value;
        url="../../../../../GPF_Subscription_new.view?command=get&gpf_no="+gpf_no;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=function()
        {
        	stateChanged(xmlhttp);
        }
        xmlhttp.send(null);  
    }
        
    else if(command=="getone")
    { 
    	
    	  startwaiting(document.Hrm_TransJoinForm) ;
    	  service=null;
       var officeid=document.getElementById("txtOffId").value;
       var acmonth=document.forms[0].ac_month.value;
       var acyear=document.forms[0].ac_year.value;
  	   var unit=document.forms[0].Acc_unit_code.value;
  	   if(acmonth>0){
	        url="../../../../../GPF_Subscription_new.view?command=getone&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit+"&fileType=s";
	        url=url+"&sid="+Math.random();
	    //    alert(url)
	        xmlhttp.open("GET",url,true);
	        xmlhttp.onreadystatechange=function()
	        {
	        	stateChanged(xmlhttp);
	        }
	        xmlhttp.send(null);  
  	   }
    	else{
    		//alert("Select the valid month");
    		stopwaiting(document.Hrm_TransJoinForm) ;
    	}
 	   
    }
    else if(command=="getupload")
    {   
    	  startwaiting(document.Hrm_TransJoinForm) ;
    	  service=null;
    	if(nullCheck()){
       var officeid=document.forms[0].txtOffId.value;
   	   var acmonth=document.forms[0].ac_month.value;
   	   var acyear=document.forms[0].ac_year.value;
   	   var unit=document.forms[0].Acc_unit_code.value;
    	
        url="../../../../../GPF_Subscription_new.view?command=getupload&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit+"&fileType=s"+"&offId="+officeid;
        url=url+"&sid="+Math.random();
      //  alert(url);
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=function()
        {
        	stateChanged(xmlhttp);
        }
        xmlhttp.send(null);  
    	}else{
    		  var acmonth=document.forms[0].acm.value;
    	   	   var acyear=document.forms[0].acy.value;
    	   	   var unit=document.forms[0].uc.value;
    		 url="../../../../../GPF_Subscription_new.view?command=getupload&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit+"&fileType=s";
    	        url=url+"&sid="+Math.random();
    	        xmlhttp.open("GET",url,true);
    	        xmlhttp.onreadystatechange=function()
    	        {
    	        	stateChanged(xmlhttp);
    	        }
    	        xmlhttp.send(null);  
    	}
       
    }
    else if(command=="trialbalance")
    {   
    	var officeid=document.getElementById("txtOffId").value;
    	var acmonth=document.forms[0].ac_month.value;
  	   	var acyear=document.forms[0].ac_year.value;
  	   	var unit=document.forms[0].Acc_unit_code.value;
    	 
        url="../../../../../GPF_Subscription_new.view?command=trialbalance&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=function()
        {
        	stateChanged(xmlhttp);
        }
        xmlhttp.send(null);  
 	   
    } 
        
    else if(command=="AdjustmentJournal")
    {   
    	var officeid=document.getElementById("txtOffId").value;
    	var acmonth=document.forms[0].ac_month.value;
  	   	var acyear=document.forms[0].ac_year.value;
  	   	var unit=document.forms[0].Acc_unit_code.value;
    	 
        url="../../../../../GPF_Subscription_new.view?command=AdjustmentJournal&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit+"&command1=journal1";
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=function()
        {
        	stateChanged(xmlhttp);
        }
        xmlhttp.send(null);  
 	   
    }
    else if(command=="AdjustmentJournal1")
    {   
 //	alert("AdjustmentJournal1 in call");
    	var officeid=document.getElementById("txtOffId").value;
    	var acmonth=document.forms[0].ac_month.value;
  	   	var acyear=document.forms[0].ac_year.value;
  	   	var unit=document.forms[0].Acc_unit_code.value;
    	 
        url="../../../../../GPF_Subscription_new.view?command=AdjustmentJournal&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit+"&command1=journal2";
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=function()
        {
        	stateChanged(xmlhttp);
        }
        xmlhttp.send(null);  
 	   
    }      
    
    else if(command=="getpop")
    {   
    	//  startwaiting(document.Hrm_TransJoinForm) ; 	  
    	 var officeid=window.opener.document.forms[0].txtOffId.value;
  	   var acmonth=window.opener.document.forms[0].ac_month.value;
  	   var acyear=window.opener.document.forms[0].ac_year.value;
  	   var unit=window.opener.document.forms[0].Acc_unit_code.value;
    
    	 
    	url="../../../../../GPF_Subscription_new.view?command=getpop&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=function()
        {
        	stateChanged(xmlhttp);
        }
        xmlhttp.send(null);  
 	  
    }  
    else if(command=="Error"){
    
    	var office_id=document.getElementById("txtOffId").value;
        var Acc_unit_id=document.getElementById("unit_name").value;
        var ac_month=document.getElementById("ac_month").value;
        var ac_year=document.getElementById("ac_year").value;
 
        var url="../../../../../GPF_ERROR_LIST?command="+command+"&txtOffId="+office_id+"&unit_name="+Acc_unit_id+"&ac_month="+ac_month+"&ac_year="+ac_year+"&file_type=s";
        url=url+"&sid="+Math.random();    
        xmlhttp.open("get",url,true); 
        xmlhttp.onreadystatechange=function()
        {
        	stateChanged(xmlhttp);
        }
        xmlhttp.send(null);  
    }
        
    else if(command=="UnitName"){    	
    	var unit_id=document.getElementById("unit_name").value;
        document.getElementById("Acc_unit_code").value=unit_id;
      //  clear();        
        document.getElementById("ac_year").value=0;
        document.getElementById("ac_month").value=0;
    } 
    else if(command="Push"){
    //	alert("push");
    	var Acc_unit_id=document.getElementById("unit_name").value;
        var ac_month=document.getElementById("ac_month").value;
        var ac_year=document.getElementById("ac_year").value;
        
        var total_sub=totsubscription;
        var total_ref=totref;
    //    var trial_sub=parseInt(trialbalancesub,10)+parseInt(trialbalancesub1,10);
        var trial_sub=parseInt(trialbalancesub,10);
        var trial_ref=trialbalanceref;
        if(document.getElementById("netpay_sub").value!=0)
        	trial_sub=document.getElementById("netpay_sub").value;
        if(document.getElementById("netpay_ref").value!=0)
        	trial_ref=document.getElementById("netpay_ref").value;
        if(document.getElementById("netpay_sub1").value!=0)
        	trial_sub=document.getElementById("netpay_sub1").value;
        if(document.getElementById("netpay_ref1").value!=0)
        	trial_ref=document.getElementById("netpay_ref1").value;
        
        var ac_code=390302;
        var ac_code1=390303;
        var diff_sub=final_diff_sub;
        var diff_ref=final_diff_ref;
        var url="../../../../../GPF_JRNL_TRN_PUSH?command="+command+"&Acc_unit_id="+Acc_unit_id+"&ac_month="+ac_month+"&ac_year="+ac_year+"&total_sub="+total_sub+"&total_ref="+total_ref+"&ac_code="+ac_code+"&ac_code1="+ac_code1+"&trial_sub="+trial_sub+"&trial_ref="+trial_ref+"&diff_sub="+diff_sub+"&diff_ref="+diff_ref+"&fileType=s";
        xmlhttp.open("post",url,true); 
        xmlhttp.onreadystatechange=function()
        {
        	stateChanged(xmlhttp);
        }
        xmlhttp.send(null);  
    }
        
}

function clear_select(){	
	 document.getElementById("fin_year").value=0;
	 document.getElementById("Acc_unit_code").value=0;
	 document.getElementById("ac_month").value=0;
	 document.getElementById("ac_year").value=0;
	 document.getElementById("unit_name").value=0;	
	 document.getElementById("sub_total_all").value="";
	   document.getElementById("refund_total_all").value="";
}

function clear(){	
	 var tbody = document.getElementById("tupload");
     var tbody1 = document.getElementById("tlist");
     var table = document.getElementById("Existing");
     var t=0;
     for(t=tbody.rows.length-1;t>=0;t--){
            tbody.deleteRow(0);
     }
     for(t=tbody1.rows.length-1;t>=0;t--){
        tbody1.deleteRow(0);
     }
   document.getElementById("ufirstamount").value="";
   document.getElementById("usecondamount").value="";
   document.getElementById("uthirdamount").Value="";
   document.getElementById("utotsubscription").value="";
   document.getElementById("utotrefund").value="";
   document.getElementById("firstamount").value="";
   document.getElementById("secondamount").value="";
   document.getElementById("thirdamount").value="";
   document.getElementById("sub_total_all").value="";
   document.getElementById("refund_total_all").value="";
   document.getElementById("totsubscription").value="";
   document.getElementById("totrefund").value="";
   document.getElementById("totalbalancesub").value="";
   document.getElementById("totalbalanceref").value="";
   document.getElementById("trialbalancesub").value="";
  // document.getElementById("trialbalancesub1").value="";
   document.getElementById("trialbalanceref").value="";
   document.getElementById("adjustmentsub").value="";
   document.getElementById("adjustmentref").value="";
   
   document.getElementById("journal_sub_cr").value="";
   document.getElementById("journal_ref_cr").value="";
   document.getElementById("journal_sub_dr").value="";
   document.getElementById("journal_ref_dr").value="";
   document.getElementById("netpay_sub").value="";
   document.getElementById("netpay_ref").value="";
   
   document.getElementById("journal1_sub_cr").value="";
   document.getElementById("journal1_ref_cr").value="";
   document.getElementById("journal1_sub_dr").value="";
   document.getElementById("journal1_ref_dr").value="";
   document.getElementById("netpay_sub1").value="";
   document.getElementById("netpay_ref1").value="";
   document.getElementById("sub_total_all").value="";
   document.getElementById("refund_total_all").value="";

   
}

function clear_journal(){
	try{
		document.getElementById("sub_total_all").value="";
		document.getElementById("refund_total_all").value="";
	    document.getElementById("journal_Adjustment").style.display="none";
	    document.getElementById('excluded_title').style.display="none";
        document.getElementById("journalforMan_Adjustment").style.display="none";
        document.getElementById('included_title').style.display="none";
        document.getElementById("uploaded_data").style.display="none";
        document.getElementById("data_thru_form").style.display="none";
        document.getElementById("TB_data").style.display="none";
        document.getElementById("journal_real").style.display="none";
        document.getElementById("Adj_Jrnl_diff").style.display="none";
        document.getElementById("sub_total_all").value="";
        document.getElementById("refund_total_all").value="";
    }
	catch(e){
		 document.getElementById("sub_total_all").value="";
		 document.getElementById("refund_total_all").value="";
		 document.getElementById("journal_Adjustment").style.display="none";
		 document.getElementById('excluded_title').style.display="none";
	     document.getElementById("journalforMan_Adjustment").style.display="none";
	     document.getElementById('included_title').style.display="none";
	     document.getElementById("uploaded_data").style.display="none";
	     document.getElementById("data_thru_form").style.display="none";
	     document.getElementById("TB_data").style.display="none";
	     document.getElementById("journal_real").style.display="none";
	     document.getElementById("Adj_Jrnl_diff").style.display="none";
	     document.getElementById("sub_total_all").value="";
	     document.getElementById("refund_total_all").value="";
	}
}
function nullCheck()
{
            if((document.Hrm_TransJoinForm.fin_year.value==0 ))
            {
            alert("Must Choose Financial Year ");
                      
            return 0;
            }
            else if((document.Hrm_TransJoinForm.unit_name.value==0 ))
            {
            alert("Must Choose Account Unit Id ");
                      
            return 0;
            }
            else if((document.Hrm_TransJoinForm.ac_year.value==0 ))
            {
            alert("Must Choose Accounting Year ");
                      
            return 0;
            }
            else if((document.Hrm_TransJoinForm.ac_month.value==0 ))
            {
            alert("Must Choose Accounting Month ");
                      
            return 0;
            }
      
    return 1;

}









function check()
{
//alert("check");
//var trans=document.getElementByName("trans");
  
      if(document.Hrm_TransJoinFormpop.trans[0].checked==true)
     {
        
        document.getElementById("arrid").style.display="block";
        document.getElementById("refid").style.display="none";
     }
     else if(document.Hrm_TransJoinFormpop.trans[1].checked==true)
        {
       
        document.getElementById("arrid").style.display="none";
        document.getElementById("refid").style.display="block";
        }
        
      
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

function showRefund()
{
    if (document.Hrm_TransJoinForm.refund.checked==true)
    {
    	   	
         document.getElementById("refund_div").disabled=false;
     document.Hrm_TransJoinForm.ref_amount.disabled=false; 
      document.Hrm_TransJoinForm.ref_no.disabled=false;
     document.Hrm_TransJoinForm.ref_total.disabled=false;
     
    document.getElementById("ref_amount").value="";
 	document.getElementById("ref_no").value="";
 	document.getElementById("ref_total").value="";

    }
    else
    {
    	
    	document.getElementById("ref_amount").value=0;
    	document.getElementById("ref_no").value=0;
    	document.getElementById("ref_total").value=0;
         document.getElementById("refund_div").disabled=true;
        
         document.Hrm_TransJoinForm.ref_amount.disabled=true; 
       document.Hrm_TransJoinForm.ref_no.disabled=true;
       document.Hrm_TransJoinForm.ref_total.disabled=true;
      

    }

    
}
function showArrear()
{
    if (document.Hrm_TransJoinForm.arrear.checked==true)
    {
    	    	
       document.getElementById("arrear_div").disabled=false;
       document.Hrm_TransJoinForm.rec_amount.disabled=false; 
       document.Hrm_TransJoinForm.rec_no.disabled=false;
       document.Hrm_TransJoinForm.rec_total.disabled=false;
       
       document.getElementById("rec_amount").value="";
   	document.getElementById("rec_no").value="";
   	document.getElementById("rec_total").value="";
       
    }
    else
    {
    	document.getElementById("rec_amount").value=0;
    	document.getElementById("rec_no").value=0;
    	document.getElementById("rec_total").value=0;
    	
        document.getElementById("arrear_div").disabled=true;
        document.Hrm_TransJoinForm.rec_amount.disabled=true; 
       document.Hrm_TransJoinForm.rec_no.disabled=true;
       document.Hrm_TransJoinForm.rec_total.disabled=true;
    }
  
}
function checkName()
{
xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your borwser doesnot support AJAX");
    return;
    }  
    var unit_name=document.getElementById("unit_name").value;
    
        url="../../../../../GPF_Subscription_new.view?command=unit&unit_name="+unit_name;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=function()
        {
        	stateChanged(xmlhttp);
        }
        xmlhttp.send(null);     
}
function checkTotal1()
{
    var rft=parseInt(document.getElementById("ref_total").value);
    var rfn=parseInt(document.getElementById("ref_no").value)
	
    if(rft>36)
    {
    	 alert("Total Installment not shold be greater than 36");
         document.getElementById("ref_total").value="";
         document.getElementById("ref_total").focus();
    }
    
    
    else if(rft<rfn)
    {
        alert("Installment no shold be less than Total Installment");
        document.getElementById("ref_no").value="";
        document.getElementById("ref_no").focus();
        
    }
  /* For Arrear
    else if(parseInt(document.getElementById("rec_total").value)>36)
    {
    	 alert("Total Installment not shold be greater than 36");
         document.getElementById("rec_total").value="";
         document.getElementById("rec_total").focus();
    }
    else if(parseInt(document.getElementById("rec_total").value)<parseInt(document.getElementById("rec_no").value))
    {
        alert("Installment no shold be less than Total Installment");
        document.getElementById("rec_no").value="";
        document.getElementById("rec_no").focus();
        
    }*/
}

function pick(i){
	
	  var emp_id=document.getElementById(i); 
	  
	  var rcells=emp_id.cells;
      var officeid=document.forms[0].txtOffId.value;
      var unit=document.forms[0].Acc_unit_code.value;
      var gpfno=rcells.item(9).lastChild.value;
      var emp_name_val=rcells.item(2).firstChild.nodeValue;
      //alert("emp_name_val==>"+emp_name_val);
      var ac_month_year=rcells.item(3).lastChild.nodeValue.split("-");
      acmonth=ac_month_year[0];
      acyear=ac_month_year[1];
    //  alert(filetype);
      var filetype=rcells.item(2).lastChild.value;
    //  alert(filetype);
      var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
	  var k;
	for(i=0;i<12;i++)
	{
		if(month[i]==acmonth)
        {
            k=i;
        }
	}
	acmonth=k+1;  
	var rel_month_year=new Array();
       rel_month_year=rcells.item(4).lastChild.nodeValue.split("-");
      var relmonth=rel_month_year[0];
      var relyear=rel_month_year[1];
    
      var subamount=rcells.item(6).firstChild.nodeValue;
      var refamount=rcells.item(7).firstChild.nodeValue;
      var ref_tot_no=rcells.item(8).lastChild.nodeValue.split("/");
      var reftot=ref_tot_no[1];
      var refins=ref_tot_no[0];
      var arramount=rcells.item(9).firstChild.nodeValue;
      var sub_no=rcells.item(6).lastChild.value;
      var remark=rcells.item(5).lastChild.value;
      var arr_tot_no=rcells.item(10).lastChild.nodeValue.split("/");
      var arrtot=arr_tot_no[1];
      var arrins=arr_tot_no[0];
      var amt_ref_arr=0;
      if(rcells.item(12).firstChild.nodeValue==''||rcells.item(12).firstChild.nodeValue==null)
    	  amt_ref_arr=0;
      else
          amt_ref_arr=rcells.item(12).firstChild.nodeValue;
      
      var arr_ref_year=new Array();
     if( rcells.item(13).lastChild.nodeValue==''||rcells.item(13).lastChild.nodeValue==null)
    	 {	aarr_year=0;
         	aarr_month=0;
    	 }	
         else
         { 	  arr_ref_year=rcells.item(13).lastChild.nodeValue.split("-");
		      aarr_year=arr_ref_year[1];
		      aarr_month=arr_ref_year[0];;
         }
      
      
      //alert(amt_ref);
      if(remark=="noRemarks")
      {
        remark=" ";
      }
      var officeid=document.forms[0].txtOffId.value;
      var subscribe_slno=rcells.item(11).lastChild.value;
   
      if(emp_name_val==" " || emp_name_val==null){
    	  alert("Only Deletion can perform.Because employee master details not available");
    	 // alert(gpfno);
    	  winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/GPF_Subscription_Popup.jsp?command=update&officeid="+officeid+"&acmonth="+acmonth+"&acyear="+acyear+"&gpfno="+gpfno+"&relmonth="+relmonth+"&unit="+unit+"&relyear="+relyear+"&subamount="+subamount+"&arramount="+arramount+"&refamount="+refamount+"&filetype="+filetype+"&reftot="+reftot+"&refins="+refins+"&sub_no="+sub_no+"&remark="+remark+"&arrtot="+arrtot+"&arrins="+arrins+"&updation=0&subscribe_slno="+subscribe_slno+"","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes");
    	  winemp.moveTo(250,250);  
    	  winemp.focus();
      }
      else{
    
    	  winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/GPF_Subscription_Popup.jsp?command=update&officeid="+officeid+"&acmonth="+acmonth+"&acyear="+acyear+"&gpfno="+gpfno+"&emp_name_val="+emp_name_val+"&relmonth="+relmonth+"&unit="+unit+"&relyear="+relyear+"&subamount="+subamount+"&arramount="+arramount+"&refamount="+refamount+"&filetype="+filetype+"&reftot="+reftot+"&refins="+refins+"&amt_ref_arr="+amt_ref_arr+"&aarr_month="+aarr_month+"&aarr_year="+aarr_year+"&sub_no="+sub_no+"&remark="+remark+"&arrtot="+arrtot+"&arrins="+arrins+"&updation=1&subscribe_slno="+subscribe_slno+"","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes");
    	  winemp.moveTo(250,250);  
    	  winemp.focus();
      }
      
}
function displayMonthpop()
{
             v=new Date();
	 mn=v.getMonth();
	 yr=v.getFullYear();
     
        var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
        var start_months_val = new Array(1,2,3,4,5,6,7,8,9,10,11,12);
       
        
      // alert(document.getElementById("rel_year").options[5].selected='selected');
       var minorcmb = document.Hrm_TransJoinFormpop.rel_month;
       var year=document.Hrm_TransJoinFormpop.rel_year.value;
       
      
       while (minorcmb.childNodes.length > 0) {
    	   minorcmb.removeChild(minorcmb.firstChild);
       }
       
    if( yr==year)
    {
       for(var i=0;i<(mn+1);i++)
       {    
    	   var opt1 = document.createElement('option');
           opt1.value = start_months_val[i];
           opt1.innerHTML = month[i];
           minorcmb.appendChild(opt1);
           //document.getElementById("rel_month").options[i].disabled=true;
          
       }
    } else{
    for(var i=0; i<12; i++){
            var opt1 = document.createElement('option');
            opt1.value = start_months_val[i];
            opt1.innerHTML = month[i];
            minorcmb.appendChild(opt1);
        }
    }
        
}

function acccheck()
{
	 ac_month=document.getElementById("acmonth").value;
     ac_year=document.getElementById("acyear").value;
     rel_month=document.getElementById("rel_month").value;
     rel_year=document.getElementById("rel_year").value;
               
     if(ac_year<rel_year)
     {
    	 alert('Related year should less than Account Year');
    	 return 0;
 	                                	                 	                 	 
     }else if(ac_year==rel_year)
     {
     if(ac_month<rel_month)
     {
    	 alert('Related Month should less than Account Month');
    	 return 0;
     }   
     }
  return 1;   
     
}

function checktest(acmonth,acyear,unit)
{
	
xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your borwser doesnot support AJAX");
    return;
    }  
url="../../../../../GPF_Subscription_new.view?command=getone&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit;
url=url+"&sid="+Math.random();
xmlhttp.open("GET",url,true);
xmlhttp.onreadystatechange=function()
{
	stateChanged(xmlhttp);
}
xmlhttp.send(null);   
}

function checktestupload(acmonth,acyear,unit)
{
	
xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your borwser doesnot support AJAX");
    return;
    }  


url="../../../../../GPF_Subscription_new.view?command=getupload&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit;
url=url+"&sid="+Math.random();
xmlhttp.open("GET",url,true);
xmlhttp.onreadystatechange=function()
{
	stateChanged(xmlhttp);
}
xmlhttp.send(null);  
}


//****************DBF value is inserted to oracle table************
function setTableValue(Val_Type){

    var office_id=document.getElementById("txtOffId").value;
    var Acc_unit_id=document.getElementById("unit_name").value;
    var ac_month=document.getElementById("ac_month").value;
    var ac_year=document.getElementById("ac_year").value;
    
    var total_asper_form_sub=parseInt(document.getElementById("totalbalancesub").value);
    var total_asper_form_ref=parseInt(document.getElementById("totalbalanceref").value);
    
    var total_asper_TB_sub=parseInt(document.getElementById("final_TB_sub").value);
    var total_asper_TB_ref=parseInt(document.getElementById("final_TB_ref").value);
    
    var total_asper_excl_jrnl_sub=0,total_asper_excl_jrnl_ref=0,total_asper_incl_jrnl_sub=0,total_asper_incl_jrnl_ref=0;
    if(document.getElementById("journal_real").style.display=="block"){
	    total_asper_excl_jrnl_sub=parseInt(document.getElementById("journal_sub_cr").value)+parseInt(document.getElementById("journal_sub_dr").value);
	    total_asper_excl_jrnl_ref=parseInt(document.getElementById("journal_ref_cr").value)+parseInt(document.getElementById("journal_ref_dr").value);
    }
    if(document.getElementById("Adj_Jrnl_diff").style.display=="block"){
	    total_asper_incl_jrnl_sub=parseInt(document.getElementById("journal1_sub_cr").value)-parseInt(document.getElementById("journal1_sub_dr").value);
	    total_asper_incl_jrnl_ref=parseInt(document.getElementById("journal1_ref_cr").value)-parseInt(document.getElementById("journal1_ref_dr").value);
    }
  
    var url="../../../../../GPF_Validate_Both_new?Val_Type="+Val_Type+"&txtOffId="+office_id+"&unit_name="+Acc_unit_id+"&ac_month="+ac_month+"&ac_year="+ac_year;
    url=url+"&total_asper_form_sub="+total_asper_form_sub+"&total_asper_form_ref="+total_asper_form_ref;
    url=url+"&total_asper_TB_sub="+total_asper_TB_sub+"&total_asper_TB_ref="+total_asper_TB_ref;
    url=url+"&total_asper_excl_jrnl_sub="+total_asper_excl_jrnl_sub+"&total_asper_excl_jrnl_ref="+total_asper_excl_jrnl_ref;
    url=url+"&total_asper_incl_jrnl_sub="+total_asper_incl_jrnl_sub+"&total_asper_incl_jrnl_ref="+total_asper_incl_jrnl_ref;
  // alert("url is-->"+url);
    xmlhttp.open("post",url,true);
    xmlhttp.onreadystatechange=function()
    {
    	stateChanged(xmlhttp);
    }
    xmlhttp.send(null);  
}

//*******popuo widow for error list***********************
function getErrorpopup(){
	var winemp= window.open("GPF_ErrorList.jsp","ErrorList");
	winemp.focus();
}
//***************push the data into HRM_JRNL_TRN_Table from form***********************


function getTransport()
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