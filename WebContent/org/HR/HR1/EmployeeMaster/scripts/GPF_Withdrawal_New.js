var xmlhttp;var seq=0;var common="",mn,yr,v;
var trialVal;
var totsubscription,trialbalancesub,trialbalancesub1,adjustmentsub;
var data_value="false";
var validate="False";
function Exit()
{
    window.close();
}

function doParentEmp(emp)
{
document.getElementById("txtGpfNo").value=emp;
call('get');
}

function popParentEmp(emp)
{
	call('getone','w');
}

	

var winemp;
var winempadd;
function servicepopup()
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
    
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpJoiningPopup.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250); 
    winemp.focus();
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

function subscribepopup(comfun)
{
	
   /*  if (winemp & winemp.open & !winemp.closed) 
    {
    	 winemp.resizeTo(500,500);
    	 winemp.moveTo(250,250); 
    	 winemp.focus();
    }
    else
    {
    	winemp=null;
    }*/
    // startwaiting(document.Hrm_TransJoinForm);  
   if(comfun=='Add'){
	
	   if(nullCheck())
	   {
	   var officeid=document.forms[0].txtOffId.value;
	   var acmonth=document.forms[0].ac_month.value;
	   var acyear=document.forms[0].ac_year.value;
	   var unit=document.forms[0].Acc_unit_code.value;
	   
	 
	   
	   winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/GPF_Withdrawal_Popup.jsp?command=Add&officeid="+officeid+"&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit+"&updation=1"+"","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes");
   
   
   // winemp.document.Hrm_TransJoinFormpop.remarks.value = document.forms[0].txtOffId.value;
	   winemp.moveTo(250,250);  
	   winemp.focus();
	   }
   }
   else if(comfun=="Update")
   {
	   var v=document.getElementsByName("sel");

	   if(v)
	   {
	       for(i=0;i<v.length;i++)
	       {
	           if(v[i].checked==true)
	           {
	        	   
	        	   var emp_id=document.getElementById(i); 
	               var rcells=emp_id.cells;
	               var officeid=document.forms[0].txtOffId.value;
	              // var unit=document.forms[0].Acc_unit_code.value;
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
	              
	               
	               var remark=rcells.item(3).lastChild.value;
	              
	               if(remark=="noRemarks")
	               {
	                
	                 remark=" ";
	               }
	               
	               var officeid=document.forms[0].txtOffId.value;
	               winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/GPF_Subscription_Popup.jsp?command=update&officeid="+officeid+"&acmonth="+acmonth+"&acyear="+acyear+"&gpfno="+gpfno+"&relmonth="+relmonth+"&relyear="+relyear+"&subamount="+subamount+"&arramount="+arramount+"&refamount="+refamount+"&reftot="+reftot+"&refins="+refins+"&remark="+remark+"","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes");
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
String.prototype.trim = function () {
	return this.replace(/^\s*(\S*(\s+\S+)*)\s*$/, "$1");
	};
function stateChanged()
{
    var flag,command,response;
    var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
  
    if(xmlhttp.readyState==4)
    {
    	
       if(xmlhttp.status==200)
       {
            response=xmlhttp.responseXML.getElementsByTagName("response")[0];
            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;

            if(command=="get")
            {
            	
                if(flag=='success')
                {
                   var tlist=document.getElementById("tlist");
                   var empname=response.getElementsByTagName("emp_name")[0].firstChild.nodeValue;
                      var d_id=response.getElementsByTagName("designation")[0].firstChild.nodeValue;
                       var dob=response.getElementsByTagName("date_of_birth")[0].firstChild.nodeValue;
                        var gpfno=response.getElementsByTagName("gpf_no")[0].firstChild.nodeValue;
                        var emp_id=response.getElementsByTagName("emp_id")[0].firstChild.nodeValue;
                        d_id=d_id.trim();
                        if(d_id=='null'||d_id==null)
                        	d_id='';
                    document.getElementById("comEmpId").value=empname;
                    document.getElementById("txtDOB").value=dob;
                    document.getElementById("txtGpfNo").value=gpfno;
                    document.getElementById("designation").value=d_id;
                    document.getElementById("txtEmpId").value=emp_id;
               
                    
                }
                else
                    {
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
            //    stopwaiting(document.Hrm_TransJoinForm) ;     
                   
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
                    rel_month=month[rel_month-1];
                    var relyear=response.getElementsByTagName("rel_year")[i].firstChild.nodeValue;
                    var amount=response.getElementsByTagName("amount")[i].firstChild.nodeValue;
                    var remark=response.getElementsByTagName("remarks")[i].firstChild.nodeValue;
                    var ref_no=response.getElementsByTagName("ref_no")[i].firstChild.nodeValue;
                    var ref_total=response.getElementsByTagName("ref_total")[i].firstChild.nodeValue;
                    var gpf_no=response.getElementsByTagName("gpf_no")[i].firstChild.nodeValue;
                    var unit_id=response.getElementsByTagName("acc_unit")[i].firstChild.nodeValue;
                    var filetype=response.getElementsByTagName("filetype")[i].firstChild.nodeValue;
                    var name=response.getElementsByTagName("name")[i].firstChild.nodeValue;
                    var date=response.getElementsByTagName("date")[i].firstChild.nodeValue;
                    var type=response.getElementsByTagName("type")[i].firstChild.nodeValue;
                    var process_id=response.getElementsByTagName("process_id")[i].firstChild.nodeValue;
                    var with_slno=response.getElementsByTagName("with_slno")[i].firstChild.nodeValue;
                    if(remark=="null"||remark==null||remark=="noRemarks"||remark=="")
                     {
                        remark="noRemarks";
                     }
               
                    if(name=="null"||name==null||name=="notmentioned"||name=="" || name=="No")
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
                       validate="False";
                   }
                    tr.appendChild(td);
                
                                     
                             
                    var tdg=document.createElement("TD");
                    var gpd=document.createTextNode(gpf_no);
                    
                    tdg.appendChild(gpd);
                    var hab=document.createElement("TEXT");
                    hab.type="hidden";
                    hab.name="unit";
                    hab.value=unit_id;
                    tdg.appendChild(hab);
                    tr.appendChild(tdg); 
                    
                    
                    var tdname=document.createElement("TD");
                    var nam=document.createTextNode(name);
                    tdname.appendChild(nam);
                    var fil=document.createElement("TEXT");
                    fil.type="hidden";
                    fil.name="ftype";
                    fil.value=filetype;
                    tdname.appendChild(fil);
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
                    td3.appendChild(h4);
                    tr.appendChild(td3);
                      
                    var tddate=document.createElement("TD");
                    var dat=document.createTextNode(date);
                    tddate.appendChild(dat);
                    tr.appendChild(tddate);
                    
                    var td4=document.createElement("TD");
                    var typetrans=document.createTextNode(type);
                    td4.appendChild(typetrans);
                    var h7=document.createElement("TEXT");
                    h7.type="hidden";
                    h7.name="remarks";
                    h7.value=remark;
                    td4.appendChild(h7);
                    tr.appendChild(td4);
                      
                    var td5=document.createElement("TD");
                    var subamt=document.createTextNode(amount);
                    td5.appendChild(subamt);
                    tr.appendChild(td5);
                    
                    var td6=document.createElement("TD");
                    var ref_no=document.createTextNode(ref_no);
                    td6.appendChild(ref_no);
                    tr.appendChild(td6);
                    
                    var td7=document.createElement("TD");
                    var ref_total=document.createTextNode(ref_total);
                    td7.appendChild(ref_total);
                    tr.appendChild(td7);
                 
                    var td8=document.createElement("TD");
                    var h8=document.createElement("TEXT");
                    h8.type="hidden";
                    h8.name="with_slno";
                    h8.value=with_slno;
                    td8.appendChild(h8);
                    tr.appendChild(td8);
                    
                    tlist.appendChild(tr);             
                    seq++;
                    } 
                    
                    var totsubamount=response.getElementsByTagName("subamount")[0].firstChild.nodeValue; 
                    
                  
               document.getElementById("firstamount").value=parseInt(totsubamount,10);
           if(len>0){
        	   data_value="true";
        	   //document.getElementById("data_thru_form").style.display="block";
           }
           else{
        	   data_value="false";
        	   //document.getElementById("data_thru_form").style.display="none";
           }
               call('getupload','w');
             }catch(e){ alert(e);}
            }
           //     stopwaiting(document.Hrm_TransJoinForm) ;
            }
       
           
            
            else if(command=="getupload")
            {
            
                if(flag=='success')
                {
                	//alert("getUpload");
                	try{
                	  var tlist=document.getElementById("tupload");
                    while (tlist.childNodes.length > 0) {
                   	 tlist.removeChild(tlist.firstChild);
                    }
                    var len=response.getElementsByTagName("emp_id").length;
                   var seq1=0;
                    for(var i=0;i<len;i++)
                    {
                    	 var emp_id=response.getElementsByTagName("emp_id")[i].firstChild.nodeValue;
                         var ac_month=response.getElementsByTagName("ac_month")[i].firstChild.nodeValue;
                         ac_month=month[ac_month-1];
                         var acyear=response.getElementsByTagName("ac_year")[i].firstChild.nodeValue;
                         var rel_month=response.getElementsByTagName("rel_month")[i].firstChild.nodeValue;
                         rel_month=month[rel_month-1];
                         var relyear=response.getElementsByTagName("rel_year")[i].firstChild.nodeValue;
                         var amount=response.getElementsByTagName("amount")[i].firstChild.nodeValue;
                         var remark=response.getElementsByTagName("remarks")[i].firstChild.nodeValue;
                         var ref_no=response.getElementsByTagName("ref_no")[i].firstChild.nodeValue;
                         var ref_total=response.getElementsByTagName("ref_total")[i].firstChild.nodeValue;
                         var gpf_no=response.getElementsByTagName("gpf_no")[i].firstChild.nodeValue;
                         var unit_id=response.getElementsByTagName("acc_unit")[i].firstChild.nodeValue;
                         var filetype=response.getElementsByTagName("filetype")[i].firstChild.nodeValue;
                         var name=response.getElementsByTagName("name")[i].firstChild.nodeValue;
                         var date=response.getElementsByTagName("date")[i].firstChild.nodeValue;
                         var type=response.getElementsByTagName("type")[i].firstChild.nodeValue;
                         var process_id=response.getElementsByTagName("process_id")[i].firstChild.nodeValue;
                         var with_slno=response.getElementsByTagName("with_slno")[i].firstChild.nodeValue;
                       //  alert("with_slno****"+with_slno);
                         if(remark=="null"||remark==null||remark=="noRemarks"||remark=="")
                          {
                             remark="noRemarks";
                          }
                         
                         if(rel_month=="null"||rel_month==null||rel_month==""||rel_month==0)
                         {
                        	 rel_month="";
                         }
                         if(relyear=="null"||relyear==null||relyear==""||relyear==0)
                         {
                        	 relyear="";
                         }
                    
                         if(name=="null"||name==null||name=="notmentioned"||name==""|| name=="No")
                         {
                         	name=" ";
                         }
                         if(date=="Nodate")
                        	 date=" ";
                         var tr=document.createElement("TR");
                         tr.id=seq;
                         
                     /**    var tdg=document.createElement("TD");
                         var slno=document.createTextNode(i+1);
                         tdg.appendChild(slno);
                         tr.appendChild(tdg); /*/
                         
                         var td=document.createElement("TD");
                         var anc=document.createElement("A");
                         var url="javascript:pick('"+seq+"')";
                         anc.href=url;
                         if(process_id=="FR"){
                         	 var edit=document.createTextNode("");
                             td.appendChild(edit);
                             document.getElementById("errorButton").disabled = true;
                             validate="True";
                        }
                        else{
                     	    var edit=document.createTextNode("Edit");
                        	anc.appendChild(edit);
                            td.appendChild(anc);
                            validate="False";
                        }
                         tr.appendChild(td);
                     
                                          
                                  
                         var tdg=document.createElement("TD");
                         var gpd=document.createTextNode(gpf_no);
                         
                         tdg.appendChild(gpd);
                         var hab=document.createElement("TEXT");
                         hab.type="hidden";
                         hab.name="unit";
                         hab.value=unit_id;
                         tdg.appendChild(hab);
                         tr.appendChild(tdg); 
                         
                         
                         var tdname=document.createElement("TD");
                         var nam=document.createTextNode(name);
                         tdname.appendChild(nam);
                         var fil=document.createElement("TEXT");
                         fil.type="hidden";
                         fil.name="ftype";
                         fil.value=filetype;
                         tdname.appendChild(fil);
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
                         td3.appendChild(h4);
                         tr.appendChild(td3);
                           
                         var tddate=document.createElement("TD");
                         var dat=document.createTextNode(date);
                         tddate.appendChild(dat);
                         tr.appendChild(tddate);
                         
                         var td4=document.createElement("TD");
                         var typetrans=document.createTextNode(type);
                         td4.appendChild(typetrans);
                         var h7=document.createElement("TEXT");
                         h7.type="hidden";
                         h7.name="remarks";
                         h7.value=remark;
                         td4.appendChild(h7);
                         tr.appendChild(td4);
                           
                         var td5=document.createElement("TD");
                         var subamt=document.createTextNode(amount);
                         td5.appendChild(subamt);
                         tr.appendChild(td5);
                         
                         var td6=document.createElement("TD");
                         var ref_no=document.createTextNode(ref_no);
                         td6.appendChild(ref_no);
                         tr.appendChild(td6);
                         
                         var td7=document.createElement("TD");
                         var ref_total=document.createTextNode(ref_total);
                         td7.appendChild(ref_total);
                         tr.appendChild(td7);
                         
                         var td9=document.createElement("TD");
                         var h9=document.createElement("TEXT");
                         h9.type="hidden";
                         h9.name="with_slno";
                         h9.value=with_slno;
                         td9.appendChild(h9);
                         tr.appendChild(td9);
                         
                         tlist.appendChild(tr);             
                         seq++;
                         } 
                         
                         var totsubamount=response.getElementsByTagName("subamount")[0].firstChild.nodeValue; 
                      
                 document.getElementById("ufirstamount").value=parseInt(totsubamount,10);
                 
                   if(len>0 || data_value=='true'){
                	 document.getElementById("uploaded_data").style.display="block";
                  	 document.getElementById("TB_data").style.display="block";   
                     call('trialbalance','w');
                   }
                   else{
                	   document.getElementById("uploaded_data").style.display="none";
                  	   document.getElementById("TB_data").style.display="none";   
                	   var File_Valid=response.getElementsByTagName("File_Valid")[0].firstChild.nodeValue;
                	   if(File_Valid=='Invalid'){
                		   alert("Uploaded DBF file is not valid for Withdrwal.Try to Overwrite the correct DBF file");
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
                	catch(e){   	}
                }
                
              //  stopwaiting(document.Hrm_TransJoinForm) ;
            }     
            
           else if(command=="Add")
            {
                if(flag=='success')
                {
                	 if (window.opener && !window.opener.closed) {
                         
                		 window.opener.location.reload();
                    	 window.opener.focus();        
                 	 } 
                	 alert("Add Successfully");
                     window.close();  
                }
                else
                        alert("Record Already Exists");
            }
          else if(command=="Update")
            {
        	  
                if(flag=='success')
                {  
                	 window.close();
                	
                	if (window.opener && !window.opener.closed) {
                        
               		
                		call('getpop','w');
              	  	 } 
                }
                else if(flag=='Available')
                    alert("Record Already Exists");
                else
                    alert("Failure in Update");
            }
          else if(command=="Delete")
            {
        	  if(flag=='success')
              {
              	 if (window.opener && !window.opener.closed) {
                       
              		 window.opener.location.reload();
                  	 window.opener.focus();        
               	 } 
              	 alert("Delete Successfully");
                   window.close();  
              }
              else
                      alert("Problem in Delete");
        }
          else if(command=="trialbalance")
          {
              if(flag=='success')
              {
            	  
            	  document.getElementById("trial_with_cr").value=response.getElementsByTagName("trial_with_cr")[0].firstChild.nodeValue;
            	  document.getElementById("trial_with_db").value=response.getElementsByTagName("trial_with_db")[0].firstChild.nodeValue; 
            	 
            	  
            	  trialbalancesub=parseInt(document.getElementById("trial_with_db").value)-parseInt(document.getElementById("trial_with_cr").value); 
            	  trialbalancesub1=parseInt(response.getElementsByTagName("trial_with1_db")[0].firstChild.nodeValue)-parseInt(response.getElementsByTagName("trial_with1_cr")[0].firstChild.nodeValue); 
            
            	  document.getElementById("trialbalancesub").value=trialbalancesub;
            	  document.getElementById("trialbalancesub1").value=trialbalancesub1;
            	  
            	usubscription=document.getElementById("ufirstamount").value;
            	subscription=document.getElementById("firstamount").value;
            	
            	totsubscription=parseInt(usubscription,10)+parseInt(subscription,10);
            	document.getElementById("totalbalancesub").value=parseInt(totsubscription,10);
            	adjustmentsub=parseInt(trialbalancesub,10)+parseInt(trialbalancesub1,10)-parseInt(totsubscription,10);
            	document.getElementById("adjustmentsub").value=parseInt(trialbalancesub,10)+parseInt(trialbalancesub1,10)-parseInt(totsubscription,10);
            	
            	
            	final_diff_sub=adjustmentsub;
          	    document.getElementById('final_TB_sub').value=parseInt(document.getElementById("trialbalancesub").value)+parseInt(document.getElementById("trialbalancesub1").value);
            	if(final_diff_sub==0){
          		  document.Hrm_TransJoinForm.add.disabled=true;  
          	    }else{
          		  document.Hrm_TransJoinForm.add.disabled=false;  
          	   }
            	
              }
              call('AdjustmentJournal','w');
          }
         
            
          else if(command=="AdjustmentJournal")
          {
              if(flag=='success')
              {	  
            	  var command1=response.getElementsByTagName("command1")[0].firstChild.nodeValue;
            	  var recno=0;
            	  try{
            		 
            		  document.getElementById('journalforMan_Adjustment').style.display="none";
            		  document.getElementById('included_title').style.display="none";
            		  document.getElementById('Adj_Jrnl_diff').style.display="none";
            		var tlist=document.getElementById("journal1");
                    while (tlist.childNodes.length > 0) {
	                 	 tlist.removeChild(tlist.firstChild);
	                }
          	    		  var journal_val=response.getElementsByTagName("journal_val");
	                      var journal_val_len=journal_val.length;
	                   // alert("journal_val_len"+journal_val_len);
	                      if(journal_val_len>0){
	                    	  document.getElementById('journal_real').style.display="block";
	                    	  document.getElementById('journal_Adjustment').style.display="block";
	                    	  document.getElementById('excluded_title').style.display="block";
	                      }
	                      else{
	                    	  
	                    	  document.getElementById('journal_real').style.display="none";
	                    	  document.getElementById('journal_Adjustment').style.display="none";
	                    	  document.getElementById('excluded_title').style.display="none";
	                    	//  alert("No data for cashbook month and year");
	                      }
	                      for(var count=0;count<journal_val_len;count+=8){	                    	  
	                    	  var tr=document.createElement("TR");
	                          tr.id=recno;
	                    	  for(var i=count;i<count+8;i++){
		                    	  var val=response.getElementsByTagName("journal_val")[i].firstChild.nodeValue;
		                    	  var tdg=document.createElement("TD");
		                          var td_val=document.createTextNode(val);
		                          tdg.appendChild(td_val);
		                          tr.appendChild(tdg); 
		                       }
	                    	  tlist.appendChild(tr);
	                      }
	                      
	                    //*****For debit the subscription amount and refund amount from adjustment journal**********	
	  	                var sub_length= response.getElementsByTagName("with_cr_amt").length;
	  	                var with_cr_amt=0,with_dr_amt=0;
	  	                for(i=0;i<sub_length;i++){
	  	                	with_cr_amt=parseInt(with_cr_amt,10)+parseInt(response.getElementsByTagName("with_cr_amt")[i].firstChild.nodeValue);
	  	                  	with_dr_amt=parseInt(with_dr_amt,10)+parseInt(response.getElementsByTagName("with_dr_amt")[i].firstChild.nodeValue);
	  	                }
	  	                var diff_sub_amt=parseInt(adjustmentsub,10)-parseInt(with_cr_amt,10);
		               
		                document.getElementById("netpay_sub").value=parseInt(trialbalancesub,10)+parseInt(trialbalancesub1,10)+parseInt(with_cr_amt,10)-parseInt(with_dr_amt,10);
		                
		                document.getElementById("journal_sub_cr").value=parseInt(with_cr_amt,10);
		                document.getElementById("journal_sub_dr").value=parseInt(with_dr_amt,10);
		                document.getElementById('final_TB_sub').value=parseInt(document.getElementById("netpay_sub").value);
		                final_diff_sub=parseInt(document.getElementById("netpay_sub").value)-parseInt(document.getElementById("totalbalancesub").value);
		                
		                if(final_diff_sub==0){
		            		  document.Hrm_TransJoinForm.add.disabled=true;  
		            	}else{
		            		  document.Hrm_TransJoinForm.add.disabled=false;  
		            	}
		              }
	        	 catch(e){  }
            	 stopwaiting(document.Hrm_TransJoinForm) ;
            }
              if(final_diff_sub==0){
            	  	call('Error','w');                	
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
              call('AdjustmentJournal1','w');
         }
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
	                    	 // alert("No data for rectification month and year");
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
	                   //   alert("b4 find length");
	                var sub_length= response.getElementsByTagName("with_cr_amt").length;
	                var with_cr_amt=0, with_dr_amt=0;
	       
	                for(i=0;i<sub_length;i++){
	                	with_cr_amt=parseInt(with_cr_amt,10)+parseInt(response.getElementsByTagName("with_cr_amt")[i].firstChild.nodeValue);
	                  	with_dr_amt=parseInt(with_dr_amt,10)+parseInt(response.getElementsByTagName("with_dr_amt")[i].firstChild.nodeValue);
	                }
	             
	              //  var diff_sub_amt=parseInt(adjustmentsub,10)-parseInt(with_cr_amt,10);
	              
	                document.getElementById("journal1_sub_cr").value=parseInt(with_cr_amt,10);
	                document.getElementById("journal1_sub_dr").value=parseInt(with_dr_amt,10);
	               // alert("*************"+parseInt(document.getElementById("netpay_sub").value,10)-parseInt(totsubscription,10));
	                document.getElementById("adjustmentsub1").value=parseInt(document.getElementById("netpay_sub").value,10)-parseInt(totsubscription,10);
	                document.getElementById("netpay_sub1").value=parseInt(document.getElementById("final_TB_sub").value,10)-parseInt(document.getElementById("journal1_sub_cr").value,10)+parseInt(document.getElementById("journal1_sub_dr").value,10);;
	                final_diff_sub=parseInt(document.getElementById("netpay_sub1").value,10)-parseInt(document.getElementById("totalbalancesub").value,10);
	                if(final_diff_sub==0){
	            		  document.Hrm_TransJoinForm.add.disabled=true;  
	            	}else{
	            		  document.Hrm_TransJoinForm.add.disabled=false;  
	                }
	              }
            	  catch(e){  }

          	  //****************End of Journal Values Shown*****************
            	  call('Error','w');
          	  //stopwaiting(document.Hrm_TransJoinForm) ;
            }
            
            
        }  
    
          else if(command=="Error"){
        	  var tagresult=response.getElementsByTagName("result")[0];
        	  var result=tagresult.firstChild.nodeValue;
        	 	if(result=="Valid" && final_diff_sub==0){
                	document.getElementById("Validate").disabled = false;
               	}
                else{
                 	document.getElementById("Validate").disabled = true;
                }
        		if(result=="Valid" || validate=="True")
        			document.getElementById("errorButton").disabled = true;
         	    else
         	    	document.getElementById("errorButton").disabled = false;
        		if(validate=="True"){
               		alert("This File Already Validated");
               		document.getElementById("Validate").disabled = true;
               	}
        		if(result=="Valid" && document.getElementById("Validate").disabled == true && validate=="False")
               		alert("Form value and Trial Balance value is not tally");
         	
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
            	alert("Duplicate Entries Allowed in DBF file upload for GPFNo:"+gpf_no+"So Before Validate You should update or delete that entry");
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
        }else{}
    }
           
}
function clear_select(){
  	 document.getElementById("fin_year").value=0;
  	 document.getElementById("Acc_unit_code").value=0;
  	 document.getElementById("ac_month").value=0;
  	 document.getElementById("ac_year").value=0;
  	 document.getElementById("unit_name").value=0;
  }
function clear(){
	var tbody = document.getElementById("tupload");
    var tbody1 = document.getElementById("tlist");
    var table = document.getElementById("Existing");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
    for(t=tbody1.rows.length-1;t>=0;t--)
    {
       tbody1.deleteRow(0);
    }
  document.getElementById("ufirstamount").value="";
  document.getElementById("firstamount").value="";
  document.getElementById("totalbalancesub").value="";
  document.getElementById("trialbalancesub").value="";
  document.getElementById("trialbalancesub1").value="";
  document.getElementById("adjustmentsub").value="";
  
  document.getElementById("journal_sub_cr").value="";
  document.getElementById("journal_sub_dr").value="";
  document.getElementById("netpay_sub").value="";
 
  document.getElementById("journal1_sub_cr").value="";
  document.getElementById("journal1_sub_dr").value="";
  document.getElementById("netpay_sub1").value="";  
}

function clear_journal(){
	try{          
		document.getElementById("journal_Adjustment").style.display="none";
		document.getElementById('excluded_title').style.display="none";
		document.getElementById("journalforMan_Adjustment").style.display="none";
		document.getElementById('included_title').style.display="none";
		document.getElementById("uploaded_data").style.display="none";
		document.getElementById("data_thru_form").style.display="none";
		document.getElementById("TB_data").style.display="none";
		document.getElementById("journal_real").style.display="none";
		document.getElementById("Adj_Jrnl_diff").style.display="none";
	}
	catch(e){
		document.getElementById("journal_Adjustment").style.display="none";
		document.getElementById('excluded_title').style.display="none";
		document.getElementById("journalforMan_Adjustment").style.display="none";
		document.getElementById('included_title').style.display="none";
		document.getElementById("uploaded_data").style.display="none";
		document.getElementById("data_thru_form").style.display="none";
		document.getElementById("TB_data").style.display="none";
		document.getElementById("journal_real").style.display="none";
		document.getElementById("Adj_Jrnl_diff").style.display="none";

	}
}
function clear1()
{
    	document.Hrm_TransJoinForm.amount.value="";
        document.Hrm_TransJoinForm.ref_amount.value="";
        document.Hrm_TransJoinForm.ref_no.value="";
        document.Hrm_TransJoinForm.ref_total.value="";
        //document.Hrm_TransJoinForm.rec_amount.value="";
        //document.Hrm_TransJoinForm.rec_no.value="";
        //document.Hrm_TransJoinForm.rec_total.value="";
        document.Hrm_TransJoinForm.remarks.value="";
        document.Hrm_TransJoinForm.trans[0].checked='checked';
        //document.Hrm_TransJoinForm.refund.checked=false;
        //document.Hrm_TransJoinForm.arrear.checked=false;
        document.Hrm_TransJoinForm.ac_month.disabled=false;
        document.Hrm_TransJoinForm.ac_year.disabled=false;
        document.getElementById("refund_div").disabled=false;
        document.Hrm_TransJoinForm.ref_amount.disabled=false; 
        document.Hrm_TransJoinForm.ref_no.disabled=false;
        document.Hrm_TransJoinForm.ref_total.disabled=false;
        
        //document.getElementById("arrear_div").disabled=true;
        //document.Hrm_TransJoinForm.rec_amount.disabled=true; 
        //document.Hrm_TransJoinForm.rec_no.disabled=true;
        //document.Hrm_TransJoinForm.rec_total.disabled=true;
        
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
       }
       document.Hrm_TransJoinForm.add.disabled=false;
       document.Hrm_TransJoinForm.update.disabled=true;
       document.Hrm_TransJoinForm.delete1.disabled=true;
        
}
function getGetone(){
	clear();
	clear_journal();
	call('getone','w');
}
function call(command,fileType)
{
	
xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your browser doesnot support AJAX");
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
      
   
  
   
     if(command=="get")
    {   
    	  startwaiting(document.Hrm_TransJoinForm) ;
          service=null;
        var gpf_no=document.Hrm_TransJoinFormpop.txtGpfNo.value;
        url="../../../../../GPF_Withdrawal_New?command=get&gpf_no="+gpf_no;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
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
    	
        url="../../../../../GPF_Withdrawal_New?command=getone&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit+"&fileType=w";
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);  
 	   
    }
    else if(command=="getupload")
    {   
    	 startwaiting(document.Hrm_TransJoinForm) ;
         service=null;
        //  alert("getUpload");
    	if(nullCheck()){
     	 var officeid=document.forms[0].txtOffId.value;
   	   var acmonth=document.forms[0].ac_month.value;
   	   var acyear=document.forms[0].ac_year.value;
   	   var unit=document.forms[0].Acc_unit_code.value;
    	
        url="../../../../../GPF_Withdrawal_New?command=getupload&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit+"&fileType=w"+"&offId="+officeid;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    	}else{
    		  var acmonth=document.forms[0].acm.value;
    	   	   var acyear=document.forms[0].acy.value;
    	   	   var unit=document.forms[0].uc.value;
    		 url="../../../../../GPF_Withdrawal_New?command=getupload&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit+"&fileType=w";
    	        url=url+"&sid="+Math.random();
    	        xmlhttp.open("GET",url,true);
    	        xmlhttp.onreadystatechange=stateChanged;
    	        xmlhttp.send(null); 
    	}
       
    }
    else if(command=="trialbalance")
    {   
    	/* startwaiting(document.Hrm_TransJoinForm) ;
         service=null;*/
       var officeid=document.getElementById("txtOffId").value;
       var acmonth=document.forms[0].ac_month.value;
  	   var acyear=document.forms[0].ac_year.value;
  	   var unit=document.forms[0].Acc_unit_code.value;
    	 
        url="../../../../../GPF_Withdrawal_New?command=trialbalance&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);  
 	   
    }  
     
    else if(command=="AdjustmentJournal")
    {   
    	var officeid=document.getElementById("txtOffId").value;
    	var acmonth=document.forms[0].ac_month.value;
  	   	var acyear=document.forms[0].ac_year.value;
  	   	var unit=document.forms[0].Acc_unit_code.value;
    	 
        url="../../../../../GPF_Withdrawal_New?command=AdjustmentJournal&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit+"&command1=journal1";
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
  
    	var officeid=document.getElementById("txtOffId").value;
    	var acmonth=document.forms[0].ac_month.value;
  	   	var acyear=document.forms[0].ac_year.value;
  	   	var unit=document.forms[0].Acc_unit_code.value;
    	 
        url="../../../../../GPF_Withdrawal_New?command=AdjustmentJournal&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit+"&command1=journal2";
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
    	/* startwaiting(document.Hrm_TransJoinForm) ;
         service=null;*/
       	     	  	  
    	 var officeid=window.opener.document.forms[0].txtOffId.value;
  	   var acmonth=window.opener.document.forms[0].ac_month.value;
  	   var acyear=window.opener.document.forms[0].ac_year.value;
  	   var unit=window.opener.document.forms[0].Acc_unit_code.value;
    
    	 
    	url="../../../../../GPF_Withdrawal_New.view?command=getpop&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);  
 	  
    }  
    else if(command=="Error"){
    	var office_id=document.getElementById("txtOffId").value;
        var Acc_unit_id=document.getElementById("unit_name").value;
        var ac_month=document.getElementById("ac_month").value;
        var ac_year=document.getElementById("ac_year").value;
 
        var url="../../../../../GPF_ERROR_LIST?command="+command+"&txtOffId="+office_id+"&unit_name="+Acc_unit_id+"&ac_month="+ac_month+"&ac_year="+ac_year+"&file_type=w";
        xmlhttp.open("get",url,true); 
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);
    }
        
    else if(command=="UnitName"){
    	var unit_id=document.getElementById("unit_name").value;
        document.getElementById("Acc_unit_code").value=unit_id;
        clear();
        document.getElementById("ac_year").value=0;
        document.getElementById("ac_month").value=0;
        //clear_select();
    }     
     
    else if(command="Push"){
      	var Acc_unit_id=document.getElementById("unit_name").value;
        var ac_month=document.getElementById("ac_month").value;
        var ac_year=document.getElementById("ac_year").value;
      
        var total=totsubscription;
       
        var trial=parseInt(trialbalancesub,10)+parseInt(trialbalancesub1,10);
        //var trial=parseInt(trialbalancesub,10);
       
        var ac_code=390305;
   
        var diff=adjustmentsub;
        var url="../../../../../GPF_JRNL_TRN_PUSH?command="+command+"&Acc_unit_id="+Acc_unit_id+"&ac_month="+ac_month+"&ac_year="+ac_year+"&total="+total+"&ac_code="+ac_code+"&trial="+trial+"&diff="+diff+"&fileType=w";
        xmlhttp.open("post",url,true); 
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);
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
{xmlhttp
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
    alert("Your browser doesnot support AJAX");
    return;
    }  
    var unit_name=document.getElementById("unit_name").value;
    
        url="../../../../../GPF_Withdrawal_New?command=unit&unit_name="+unit_name;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
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
	 // alert("Empid"+emp_id);
      var rcells=emp_id.cells;
      var officeid=document.forms[0].txtOffId.value;
      var unit=document.forms[0].Acc_unit_code.value;      
      var refins=rcells.item(8).firstChild.nodeValue;
      var reftot=rcells.item(9).firstChild.nodeValue;
      var gpfno=rcells.item(1).firstChild.nodeValue;
      var ac_month_year=rcells.item(3).lastChild.nodeValue.split("-");
      acmonth=ac_month_year[0];
      acyear=ac_month_year[1];        
      var filetype=rcells.item(2).lastChild.value; 
      var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
	  var k;
	for(i=0;i<12;i++)
	{		
		if(month[i]==acmonth){
            k=i;           
        }			
	}
	acmonth=k+1;
    var rel_month_year=rcells.item(4).lastChild.nodeValue.split("-");
      relmonth=rel_month_year[0];
      relyear=rel_month_year[1];
      var amount=rcells.item(7).firstChild.nodeValue;
      var emp_name_val=rcells.item(2).firstChild.nodeValue;
      var remark=rcells.item(6).lastChild.value;
   
      if(remark=="noRemarks")
      {
         remark=" ";
      }
      
      var date=rcells.item(5).firstChild.nodeValue; 
      var type=rcells.item(6).firstChild.nodeValue; 
      var officeid=document.forms[0].txtOffId.value;
      
      var with_slno=rcells.item(10).lastChild.value;

      if(emp_name_val==" " || emp_name_val==null){
    	  alert("Only Deletion can perform.Because employee master details not available");
    	  winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/GPF_Withdrawal_Popup.jsp?command=update&officeid="+officeid+"&acmonth="+acmonth+"&acyear="+acyear+"&gpfno="+gpfno+"&relmonth="+relmonth+"&unit="+unit+"&relyear="+relyear+"&amount="+amount+"&filetype="+filetype+"&reftot="+reftot+"&refins="+refins+"&remark="+remark+"&type="+type+"&date="+date+"&updation=0&with_slno="+with_slno+"","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes");
    	  winemp.moveTo(250,250);  
    	  winemp.focus();
    	  
      }
      else{
    	  winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/GPF_Withdrawal_Popup.jsp?command=update&officeid="+officeid+"&acmonth="+acmonth+"&acyear="+acyear+"&gpfno="+gpfno+"&relmonth="+relmonth+"&unit="+unit+"&relyear="+relyear+"&amount="+amount+"&filetype="+filetype+"&reftot="+reftot+"&refins="+refins+"&remark="+remark+"&type="+type+"&date="+date+"&updation=1&with_slno="+with_slno+"","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes");
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
    alert("Your browser doesnot support AJAX");
    return;
    }  
url="../../../../../GPF_Withdrawal_New?command=getone&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit;
url=url+"&sid="+Math.random();
xmlhttp.open("GET",url,true);
xmlhttp.onreadystatechange=stateChanged;
xmlhttp.send(null);  
}

function checktestupload(acmonth,acyear,unit)
{
	
xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your browser doesnot support AJAX");
    return;
    }  


url="../../../../../GPF_Subscription.view?command=getupload&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit;
url=url+"&sid="+Math.random();
xmlhttp.open("GET",url,true);
xmlhttp.onreadystatechange=stateChanged;
xmlhttp.send(null); 
}


//****************DBF value is inserted to oracle table************
function setTableValue(Val_Type){
	
    var office_id=document.getElementById("txtOffId").value;
    var Acc_unit_id=document.getElementById("unit_name").value;
    var ac_month=document.getElementById("ac_month").value;
    var ac_year=document.getElementById("ac_year").value;
    
    var total_asper_form_sub=parseInt(document.getElementById("totalbalancesub").value);
    var total_asper_form_ref=0;
    
    var total_asper_TB_sub=parseInt(document.getElementById("final_TB_sub").value);
    var total_asper_TB_ref=0;
    var total_asper_excl_jrnl_sub=0,total_asper_incl_jrnl_sub=0;
    if(document.getElementById("journal_real").style.display=="block"){
    	total_asper_excl_jrnl_sub=parseInt(document.getElementById("journal_sub_cr").value)-parseInt(document.getElementById("journal_sub_dr").value);
    }
    var total_asper_excl_jrnl_ref=0;
    
    if(document.getElementById("Adj_Jrnl_diff").style.display=="block"){
    	total_asper_incl_jrnl_sub=parseInt(document.getElementById("journal1_sub_cr").value)+parseInt(document.getElementById("journal1_sub_dr").value);
    }
    var total_asper_incl_jrnl_ref=0;
       
    var url="../../../../../GPF_Validate_Both?Val_Type="+Val_Type+"&txtOffId="+office_id+"&unit_name="+Acc_unit_id+"&ac_month="+ac_month+"&ac_year="+ac_year;
    url=url+"&total_asper_form_sub="+total_asper_form_sub+"&total_asper_form_ref="+total_asper_form_ref;
    url=url+"&total_asper_TB_sub="+total_asper_TB_sub+"&total_asper_TB_ref="+total_asper_TB_ref;
    url=url+"&total_asper_excl_jrnl_sub="+total_asper_excl_jrnl_sub+"&total_asper_excl_jrnl_ref="+total_asper_excl_jrnl_ref;
    url=url+"&total_asper_incl_jrnl_sub="+total_asper_incl_jrnl_sub+"&total_asper_incl_jrnl_ref="+total_asper_incl_jrnl_ref;
    
    xmlhttp.open("post",url,true);
    xmlhttp.onreadystatechange=stateChanged;
    xmlhttp.send(null);
}

//*******popuo widow for error list***********************
function getErrorpopup(){
	var winemp= window.open("GPF_ErrorList.jsp","ErrorList");
	winemp.focus();
}

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
