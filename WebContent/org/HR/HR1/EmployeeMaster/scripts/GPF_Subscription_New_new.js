var xmlhttp;var seq=0;var common="",mn,yr,v;


function doParentEmp(emp)
{
document.getElementById("txtGpfNo").value=emp;
call('get');
}
var winemp1;

function popParentEmp(emp)
{
call('getone');
}

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
    // startwaiting(document.Hrm_TransJoinForm);   
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpJoiningPopup.jsp","mywindow2","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
}

window.onunload=function()
{
if (winemp1 && winemp1.open && !winemp1.closed) winemp1.close();
}



function nullCheckpop()
{
            if((document.Hrm_TransJoinFormpop.txtGpfNo.value=="")||(document.Hrm_TransJoinFormpop.txtGpfNo.value=="0"))
            {
            alert("Employee GPF.NO must be Correct");
            return 0;
            }
            else if (document.Hrm_TransJoinFormpop.trans[0].checked==true)
            {
                if(document.Hrm_TransJoinFormpop.arramt.value=="")
                {
                	 alert("Enter the Arrear Amount");
                     return 0;
                }
            }
            else if (document.Hrm_TransJoinFormpop.trans[1].checked==true)
            {
                if(document.Hrm_TransJoinFormpop.ref_amount.value=="")
                {
                	 alert("Enter the Refund Amount");
                     return 0;
                } 
                else if(document.Hrm_TransJoinFormpop.ref_total.value=="")
                {
               	 alert("Enter the Refund Total Installment ");
                    return 0;
               } else if(document.Hrm_TransJoinFormpop.ref_no.value=="")
               {
                 	 alert("Enter the Refund Installment No ");
                      return 0;
                 }
                
            }
              if(document.Hrm_TransJoinFormpop.ref_amount.value=="" && document.Hrm_TransJoinFormpop.ref_amount.value==0 
            	&& document.Hrm_TransJoinFormpop.arramt.value=="" && document.Hrm_TransJoinFormpop.arramt.value==0
            	&& document.Hrm_TransJoinFormpop.amount.value=="" && document.Hrm_TransJoinFormpop.amount.value==0){
            	alert("You Should enter the either Subscription amount or Refund Amount or Arrear Amount");
            	return 0;
            }
             
    return 1;

}

function acccheck()
{
	 ac_month=document.getElementById("acmonth").value;
     ac_year=document.getElementById("acyear").value;
     rel_month=document.getElementById("rel_month").value;
     rel_year=document.getElementById("rel_year").value;
               
     if(parseInt(ac_year,10)<parseInt(rel_year,10))
     {
    	 alert('Relative year should less than Account Year');
    	 return 0;
 	                                	                 	                 	 
     }else if(ac_year==rel_year)
     {
    	
     if(parseInt(ac_month,10)<parseInt(rel_month,10))
     {
    	 alert('Relative Month should less than Account Month');
    	 return 0;
     }   
     }
  return true;   
     
}

function selectMonth(slipyear)
{   
	     v=new Date();
	     mn=v.getMonth();
	     yr=v.getFullYear();
	
	var rel_year=document.getElementById("rel_year").value;   
	var i;
	for(i=document.Hrm_TransJoinFormpop.rel_month.options.length-1;i>=0;i--)
	{
	
	    document.Hrm_TransJoinFormpop.rel_month.remove(i);
	}
	
	var minorcmb = document.Hrm_TransJoinFormpop.rel_month;
	var start_months = new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
	var start_months_val = new Array(1,2,3,4,5,6,7,8,9,10,11,12);
	      
	if(parseInt(rel_year)==parseInt(yr))
	{
	    for(var i=0; i<parseInt(mn); i++)
	    {
	         var opt1 = document.createElement('option');
	         opt1.value = start_months_val[i];
	         opt1.innerHTML = start_months[i];
	         minorcmb.appendChild(opt1);
	    }
	   
	}else{
	    for(var i=0; i<12; i++)
	    {
	         var opt1 = document.createElement('option');
	         opt1.value = start_months_val[i];
	         opt1.innerHTML = start_months[i];
	         minorcmb.appendChild(opt1);
	    }
	}
}


function call(command)
{
	
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
        var ref_amount,ref_no,ref_total;
        var emp_id;
        var amount,amount1;
        var rec_amount,rec_no,rec_total;
        var remarks;
        var subamount,subamount1;
        var arramount,arramount1;
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
             
               unit=document.getElementById("unit").value;
              // alert("unit"+unit);
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
              
                 if(subamount==0)
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
              
                url="../../../../../GPF_Subscription_new.view?command=Add&office_id="+office_id+"&gpf_no="+gpf_no+"&division_id="+division_id+"&emp_id="+emp_id+"&ac_month="+ac_month+"&ac_year="+ac_year+"&type_trans="+type_trans+"&remarks="+remarks+"&rel_month="+rel_month+"&rel_year="+rel_year+"&unit="+unit;
               
                    
                    url=url+"&ref_amount="+ref_amount+"&ref_no="+ref_no+"&ref_total="+ref_total;
                    url=url+"&subamount="+subamount+"&arramount="+arramount;
                    
                   
             url=url+"&sid="+Math.random();
             xmlhttp.open("GET",url,true);
             xmlhttp.onreadystatechange=stateChanged;
             xmlhttp.send(null);  
        	}
        	}
    }
    else if(command=="Update")
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
         filetype=document.getElementById("filetype").value;
         remarks=document.getElementById("remarks").value;
         subscribe_slno=document.getElementById("subscribe_slno").value;
         if(remarks=="null"||remarks==""||remarks==null||remarks==" ")
         {
                 remarks="noRemarks";
         }
          subamount=0,subamount1=0;
          arramount=0,arramount1=0;
          ref_amount=0,ref_amount1=0;
          ref_no=0;
          ref_total=0;
          if(document.Hrm_TransJoinFormpop.amount.value==null||document.Hrm_TransJoinFormpop.amount.value==''|| document.Hrm_TransJoinFormpop.amount.length<1)
        	{
        	  subamount=0;                        
        	}
        	else
        		 subamount=document.Hrm_TransJoinFormpop.amount.value;
         
         
        if(document.Hrm_TransJoinFormpop.trans[0].checked)
         {
        	
        	if(document.Hrm_TransJoinFormpop.arramt.value==null||document.Hrm_TransJoinFormpop.arramt.value==''|| document.Hrm_TransJoinFormpop.arramt.length<1)
         	{
        		arramount=0;
                       
         	}
         	else
         		 arramount=document.Hrm_TransJoinFormpop.arramt.value;
     	   
     	 
         }
         else if(document.Hrm_TransJoinFormpop.trans[1].checked){
        	if(document.Hrm_TransJoinFormpop.ref_amount.value==null||document.Hrm_TransJoinFormpop.ref_amount.value==''|| document.Hrm_TransJoinFormpop.ref_amount.length<1)
          	{
        		 ref_amount=0;                        
          	}
          	else
          		ref_amount=document.Hrm_TransJoinFormpop.ref_amount.value;
                  
         	if(document.Hrm_TransJoinFormpop.ref_no.value==null||document.Hrm_TransJoinFormpop.ref_no.value==''|| document.Hrm_TransJoinFormpop.ref_no.length<1)
         	{
         		ref_no=0;
                       
         	}
         	else
         		 ref_no=document.Hrm_TransJoinFormpop.ref_no.value;
         	if(document.Hrm_TransJoinFormpop.ref_total.value==null||document.Hrm_TransJoinFormpop.ref_total.value=='' || document.Hrm_TransJoinFormpop.ref_total.length<1)
         	{
         		ref_total=0;
         	}
         	else
         		ref_total=document.Hrm_TransJoinFormpop.ref_total.value;
         }
          type_trans="CR";
          sub_no=document.getElementById("sub_no").value;
        
       url="../../../../../GPF_Subscription_new.view?command=Update&office_id="+office_id+"&gpf_no="+gpf_no+"&emp_id="+emp_id+"&ac_month="+ac_month+"&ac_year="+ac_year+"&type_trans="+type_trans+"&remarks="+remarks+"&rel_month="+rel_month+"&rel_year="+rel_year+"&subscribe_slno="+subscribe_slno;
       url=url+"&ref_amount="+ref_amount+"&filetype="+filetype+"&ref_no="+ref_no+"&sub_no="+sub_no+"&division_id="+division_id+"&ref_total="+ref_total;
       url=url+"&subamount="+subamount+"&arramount="+arramount;
      // url=url+"&subamount1="+subamount1+"&arramount1="+arramount1+"&ref_amount1="+ref_amount1;        
       url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
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
         rel_month=document.getElementById("rel_month").value;
    	 rel_year=document.getElementById("rel_year").value;
    	 division_id=document.getElementById("oldcode").value;
    	 filetype=document.getElementById("filetype").value;
    	 subscribe_slno=document.getElementById("subscribe_slno").value;
      url="../../../../../GPF_Subscription_new.view?command=Delete&gpf_no="+gpf_no+"&sub_no="+sub_no+"&ac_month="+ac_month+"&filetype="+filetype+"&ac_year="+ac_year+"&rel_month="+rel_month+"&rel_year="+rel_year+"&division_id="+division_id+"&subscribe_slno="+subscribe_slno;
        
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);      
    }
   
    else if(command=="get")
    {   
      
    	document.getElementById("comEmpId").value="";
        document.getElementById("txtDOB").value="";
      //  document.getElementById("txtGpfNo").value="";
        document.getElementById("designation").value="";
        document.getElementById("txtEmpId").value="";
       // document.getElementById("Balance").value="";
        
        var gpf_no=document.Hrm_TransJoinFormpop.txtGpfNo.value;
        url="../../../../../GPF_Subscription_new.view?command=get&gpf_no="+gpf_no;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
        
    else if(command=="getone1")
    {   
          
    	 
    	 
 	   
    	 var officeid=document.getElementById("txtOffId").value;
    	
  	   var acmonth=document.forms[0].ac_month.value;
  	   var acyear=document.forms[0].ac_year.value;
  	   var unit=document.forms[0].Acc_unit_code.value;
    	 
        url="../../../../../GPF_Subscription_new.view?command=getone&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);  
 	   
    }
    else if(command=="getupload")
    {   
          
    	if(nullCheck()){
     	 var officeid=document.forms[0].txtOffId.value;
   	   var acmonth=document.forms[0].ac_month.value;
   	   var acyear=document.forms[0].ac_year.value;
   	   var unit=document.forms[0].Acc_unit_code.value;
    	
        url="../../../../../GPF_Subscription_new.view?command=getupload&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    	}else{
    		  var acmonth=document.forms[0].acm.value;
    	   	   var acyear=document.forms[0].acy.value;
    	   	   var unit=document.forms[0].uc.value;
    		 url="../../../../../GPF_Subscription_new.view?command=getupload&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit;
    	        url=url+"&sid="+Math.random();
    	        xmlhttp.open("GET",url,true);
    	        xmlhttp.onreadystatechange=stateChanged;
    	        xmlhttp.send(null); 
    	}
       
    }
    else if(command=="getpop")
    {   
       	     	  	  
    	 var officeid=window.opener.document.forms[0].txtOffId.value;
  	   var acmonth=window.opener.document.forms[0].ac_month.value;
  	   var acyear=window.opener.document.forms[0].ac_year.value;
  	   var unit=window.opener.document.forms[0].Acc_unit_code.value;
    	url="../../../../../GPF_Subscription_new.view?command=getpop&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);  
 	  
    }  
        
        
    else if(command=="UnitName")
    {
           var unit_id=document.getElementById("unit_name").value;
            //alert("unit_id"+unit_id);
           document.getElementById("Acc_unit_code").value=unit_id;
    /*    url="../../../../../GPF_Withdrawal_Details.av?command=unit&unit_name="+unit_name;
        url=url+"&sid="+Math.random();
         req.open("GET",url,true);
         req.onreadystatechange=stateChanged;
         req.send(null);   */
    }      
        
        
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
                  
                    var empname=response.getElementsByTagName("emp_name")[0].firstChild.nodeValue;
                      var d_id=response.getElementsByTagName("designation")[0].firstChild.nodeValue;
                       var dob=response.getElementsByTagName("date_of_birth")[0].firstChild.nodeValue;
                        var gpfno=response.getElementsByTagName("gpf_no")[0].firstChild.nodeValue;
                        var emp_id=response.getElementsByTagName("emp_id")[0].firstChild.nodeValue;
                    document.getElementById("comEmpId").value=empname;
                    document.getElementById("txtDOB").value=dob;
                    document.getElementById("txtGpfNo").value=gpfno;
                    d_id=d_id.trim();
                    if(d_id=='null'||d_id==null)
                    	d_id='';
                    document.getElementById("designation").value=d_id;
                    document.getElementById("txtEmpId").value=emp_id;
 
                    
                }
                else  if(flag=='failure')
                	  {
                		  alert("Employee details not available for this GPfno ");
                		 // document.getElementById("txtGpfNo").value=gpfno;
                	  }
                else
                    {
                     /*document.getElementById("txtEmpId").value="";
                     document.getElementById("comEmpId").value="";
                    document.getElementById("txtDOB").value="";
                    document.getElementById("txtGpfNo").value="";
                    document.getElementById("designation").value="";
                    document.getElementById("amount").value="";
                    document.getElementById("ref_amount").value="";
                    document.getElementById("ref_no").value="";
                    document.getElementById("ref_total").value="";*/
                    //document.getElementById("rec_amount").value="";
                   // document.getElementById("rec_no").value="";
                   // document.getElementById("rec_total").value="";
                    document.Hrm_TransJoinForm.txtGpfNo.focus();
                    alert("This Employee GPF.NO doesnot Exist");
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
                    
                    for(var i=0;i<len;i++)
                    {
                    var emp_id=response.getElementsByTagName("emp_id")[i].firstChild.nodeValue;
                     
                    var ac_month=response.getElementsByTagName("ac_month")[i].firstChild.nodeValue;
                    ac_month=month[ac_month-1];
                    var acyear=response.getElementsByTagName("ac_year")[i].firstChild.nodeValue;
                    var rel_month=response.getElementsByTagName("rel_month")[i].firstChild.nodeValue;
                    rel_month=month[rel_month-1];
                    var relyear=response.getElementsByTagName("rel_year")[i].firstChild.nodeValue;
                    var type_trans=response.getElementsByTagName("type_trans")[i].firstChild.nodeValue;
                    var amount=response.getElementsByTagName("amount")[i].firstChild.nodeValue;
                    var remark=response.getElementsByTagName("remarks")[i].firstChild.nodeValue;
                    var ref_amount=response.getElementsByTagName("ref_amount")[i].firstChild.nodeValue;
                    var ref_no=response.getElementsByTagName("ref_no")[i].firstChild.nodeValue;
                    var ref_total=response.getElementsByTagName("ref_total")[i].firstChild.nodeValue;
                    var rec_amount=response.getElementsByTagName("rec_amount")[i].firstChild.nodeValue;
                    //var rec_no=response.getElementsByTagName("rec_no")[i].firstChild.nodeValue;
                    //var rec_total=response.getElementsByTagName("rec_total")[i].firstChild.nodeValue;
                    var gpf_no=response.getElementsByTagName("gpf_no")[i].firstChild.nodeValue;
                   // var sub_no=response.getElementsByTagName("sub_seq")[i].firstChild.nodeValue;
                   
                    var unit_id=response.getElementsByTagName("acc_unit")[i].firstChild.nodeValue;
                    
                    if(remark=="null"||remark==null||remark=="noRemarks"||remark=="")
                     {
                        remark="noRemarks";
                     }
                     
                   var tr=document.createElement("TR");
                    tr.id=seq;
                    
                    var td=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:pick('"+seq+"')";
                    anc.href=url;
                    var edit=document.createTextNode("Edit");
                    anc.appendChild(edit);
                    td.appendChild(anc);
                    tr.appendChild(td);
                   
                  /* var td1=document.createElement("TD");
                   var sc=document.createElement("input");
                   sc.setAttribute('type', 'radio');
                  
                   sc.setAttribute("value", "val");
                   sc.setAttribute("name", "sel");
            
                   sc.setAttribute("onclick","pick("+seq+")");
                  
                 // sc.type="radio";
                   td1.appendChild(sc);
                 
                    tr.appendChild(td1);*/
                                     
                                       
                    var tdg=document.createElement("TD");
                    var gpd=document.createTextNode(gpf_no);
                    
                    tdg.appendChild(gpd);
                    var hab=document.createElement("TEXT");
                    hab.type="hidden";
                    hab.name="unit";
                    hab.value=unit_id;
                    tdg.appendChild(hab);
                    tr.appendChild(tdg); 
                    
                   /* var tds=document.createElement("TD");
                    var subn=document.createTextNode(sub_no);
                    tds.appendChild(subn);
                    tr.appendChild(tds);*/ 
                    
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
                      
                    var td4=document.createElement("TD");
                    var typetrans=document.createTextNode(type_trans);
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
                    var refamt=document.createTextNode(ref_amount);
                    td6.appendChild(refamt);
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
                    td7.appendChild(h13);
                    tr.appendChild(td7);
                    
                    var td8=document.createElement("TD");
                    var recamt=document.createTextNode(rec_amount);
                    td8.appendChild(recamt);  
                    var h10=document.createElement("TEXT");
                    h10.type="hidden";
                    h10.name="gpf";
                    h10.value=gpf_no;
                    td8.appendChild(h10);
                                 
                    tr.appendChild(td8);
                     
                   /* var td9=document.createElement("TD");
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
                    td9.appendChild(h12);
                    tr.appendChild(td9);*/
                 
                    tlist.appendChild(tr);             
                    seq++;
                    } 
                    call('getupload','s');
                }catch(e){ alert(e);}
                }
            }
       
            else if(command=="getpop")
            {
            	
                if(flag=='success')
                {
                	 var tlist=null;
                	try{
                		
              //  alert('i am in pop up');		
                		
                		tlist=window.opener.document.getElementById("tlist");
                        
                		 while (window.opener.document.getElementById("tlist").childNodes.length > 0) {
                			 window.opener.document.getElementById("tlist").removeChild(tlist.firstChild);
                         }               		
                		
                	  
                    var len=response.getElementsByTagName("emp_id").length;
                    var seq=0;
                  
                    for(var i=0;i<len;i++)
                    {
                    	
                    var emp_id=response.getElementsByTagName("emp_id")[i].firstChild.nodeValue;
                     
                    var ac_month=response.getElementsByTagName("ac_month")[i].firstChild.nodeValue;
                    ac_month=month[ac_month-1];
                    var acyear=response.getElementsByTagName("ac_year")[i].firstChild.nodeValue;
                    var rel_month=response.getElementsByTagName("rel_month")[i].firstChild.nodeValue;
                    rel_month=month[rel_month-1];
                    var relyear=response.getElementsByTagName("rel_year")[i].firstChild.nodeValue;
                    var type_trans=response.getElementsByTagName("type_trans")[i].firstChild.nodeValue;
                    var amount=response.getElementsByTagName("amount")[i].firstChild.nodeValue;
                    var remark=response.getElementsByTagName("remarks")[i].firstChild.nodeValue;
                    var ref_amount=response.getElementsByTagName("ref_amount")[i].firstChild.nodeValue;
                    var ref_no=response.getElementsByTagName("ref_no")[i].firstChild.nodeValue;
                    var ref_total=response.getElementsByTagName("ref_total")[i].firstChild.nodeValue;
                    var rec_amount=response.getElementsByTagName("rec_amount")[i].firstChild.nodeValue;
                    //var rec_no=response.getElementsByTagName("rec_no")[i].firstChild.nodeValue;
                    //var rec_total=response.getElementsByTagName("rec_total")[i].firstChild.nodeValue;
                    var gpf_no=response.getElementsByTagName("gpf_no")[i].firstChild.nodeValue;
                   // var sub_no=response.getElementsByTagName("sub_seq")[i].firstChild.nodeValue;
                    
                  
                    if(remark=="null"||remark==null||remark=="noRemarks"||remark=="")
                     {
                        remark="noRemarks";
                     }
                  /*  alert('Start');
                    alert(ac_month);
                    alert(acyear);
                    alert(rel_month);
                    alert(relyear);
                    alert(type_trans);
                    alert(amount);
                    alert(remark);
                    alert(ref_amount);
                    alert(ref_no);
                    alert(ref_total);
                    alert(rec_amount);
                    alert(gpf_no);
                    alert(sub_no);
                    alert('end');*/
                     
                   var tr1=document.createElement("TR");
                    tr1.setAttribute("id", seq);
                    
                    var td=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:pick('"+seq+"')";
                    anc.setAttribute("href", url);
                    var edit=document.createTextNode("Edit");
                    anc.appendChild(edit);
                    td.appendChild(anc);
                    tr1.appendChild(td);
                   
                 
                          
                                      
                    var tdg=document.createElement("TD");
                    var gpd=document.createTextNode(gpf_no);
                    tdg.appendChild(gpd);
                    tr1.appendChild(tdg); 
                    
                 /*   var tds=document.createElement("TD");
                    var subn=document.createTextNode(sub_no);
                    tds.appendChild(subn);
                    tr1.appendChild(tds); */
                    
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
                    tr1.appendChild(td2);
                        
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
                    tr1.appendChild(td3);
                      
                    var td4=document.createElement("TD");
                    var typetrans=document.createTextNode(type_trans);
                    td4.appendChild(typetrans);
                    var h7=document.createElement("TEXT");
                    h7.type="hidden";
                    h7.name="remarks";
                    h7.value=remark;
                    td4.appendChild(h7);
                    tr1.appendChild(td4);
                      
                    var td5=document.createElement("TD");
                    var subamt=document.createTextNode(amount);
                    td5.appendChild(subamt);
                    tr1.appendChild(td5);
                   
                    var td6=document.createElement("TD");
                    var refamt=document.createTextNode(ref_amount);
                    td6.appendChild(refamt);
                    tr1.appendChild(td6);
                 
                   // alert('fine');
                    
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
                    td7.appendChild(h13);
                    tr1.appendChild(td7);
                    
                    var td8=document.createElement("TD");
                    var recamt=document.createTextNode(rec_amount);
                    td8.appendChild(recamt);  
                    var h10=document.createElement("TEXT");
                    h10.type="hidden";
                    h10.name="gpf";
                    h10.value=gpf_no;
                    td8.appendChild(h10);
                    tr1.appendChild(td8);
                   
                
                                                     
                    
                   window.opener.document.getElementById("tlist").appendChild(tr1);
                              
                    seq=seq+1;
               
                    } 
                  

                   
                }catch(e){ alert("Error Here"+e);}
                alert("Updated Successfully");
                window.close();  
                }
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
                    
                    for(var i=0;i<len;i++)
                    {
                    var emp_id=response.getElementsByTagName("emp_id")[i].firstChild.nodeValue;
                     
                    var ac_month=response.getElementsByTagName("ac_month")[i].firstChild.nodeValue;
                    ac_month=month[ac_month-1];
                    var acyear=response.getElementsByTagName("ac_year")[i].firstChild.nodeValue;
                    var rel_month=response.getElementsByTagName("rel_month")[i].firstChild.nodeValue;
                    rel_month=month[rel_month-1];
                    var relyear=response.getElementsByTagName("rel_year")[i].firstChild.nodeValue;
                    var type_trans=response.getElementsByTagName("type_trans")[i].firstChild.nodeValue;
                    var amount=response.getElementsByTagName("amount")[i].firstChild.nodeValue;
                    var remark=response.getElementsByTagName("remarks")[i].firstChild.nodeValue;
                    var ref_amount=response.getElementsByTagName("ref_amount")[i].firstChild.nodeValue;
                    var ref_no=response.getElementsByTagName("ref_no")[i].firstChild.nodeValue;
                    var ref_total=response.getElementsByTagName("ref_total")[i].firstChild.nodeValue;
                    var rec_amount=response.getElementsByTagName("rec_amount")[i].firstChild.nodeValue;
                    //var rec_no=response.getElementsByTagName("rec_no")[i].firstChild.nodeValue;
                    //var rec_total=response.getElementsByTagName("rec_total")[i].firstChild.nodeValue;
                    var gpf_no=response.getElementsByTagName("gpf_no")[i].firstChild.nodeValue;
                    var sub_no=response.getElementsByTagName("sub_seq")[i].firstChild.nodeValue;
                   
                    
                    if(remark=="null"||remark==null||remark=="noRemarks"||remark=="")
                     {
                        remark="noRemarks";
                     }
                     
                   var tr=document.createElement("TR");
                    tr.id=seq;
                    
                   
                   
                  /* var td1=document.createElement("TD");
                   var sc=document.createElement("input");
                   sc.setAttribute('type', 'radio');
                  
                   sc.setAttribute("value", "val");
                   sc.setAttribute("name", "sel");
            
                   sc.setAttribute("onclick","pick("+seq+")");
                  
                 // sc.type="radio";
                   td1.appendChild(sc);
                 
                    tr.appendChild(td1);*/
                    
                    var tdg=document.createElement("TD");
                    var gpd=document.createTextNode(gpf_no);
                    tdg.appendChild(gpd);
                    tr.appendChild(tdg); 
                    
                    
                 /*   var tds=document.createElement("TD");
                    var subn=document.createTextNode(sub_no);
                    tds.appendChild(subn);
                    tr.appendChild(tds); */
                   
                                                        
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
                      
                    var td4=document.createElement("TD");
                    var typetrans=document.createTextNode(type_trans);
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
                    var refamt=document.createTextNode(ref_amount);
                    td6.appendChild(refamt);
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
                    td7.appendChild(h13);
                    tr.appendChild(td7);
                    
                    var td8=document.createElement("TD");
                    var recamt=document.createTextNode(rec_amount);
                    td8.appendChild(recamt);  
                    var h10=document.createElement("TEXT");
                    h10.type="hidden";
                    h10.name="gpf";
                    h10.value=gpf_no;
                    td8.appendChild(h10);
                                 
                    tr.appendChild(td8);
                     
                   /* var td9=document.createElement("TD");
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
                    td9.appendChild(h12);
                    tr.appendChild(td9);*/
                 
                    tlist.appendChild(tr); 
                    
                    seq++;
                    } 	
                }catch(e){ alert("Error Here"+e);}
                }
            }     
            
           else if(command=="Add")
            {
                if(flag=='success')
                {
                       	if (window.opener && !window.opener.closed) {
                		window.close();
                		 window.opener.focus();  
                		opener.popParentEmp(4);         		
             
                       	} 
                	 alert("Add Successfully");
                     window.close();  
                }
                else if(flag=='Available'){
                	 
                	  var result="";
                	  var Add_val="";
                	  var months = new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
                	//  for(i=0;i<Add.length;i++)
                	  {
                		  Add_val=response.getElementsByTagName("sub")[0].firstChild.nodeValue;
                		 
                		  if(Add_val=='already'){
                    			sub_ac_month=response.getElementsByTagName("sub_ac_month")[0].firstChild.nodeValue;
                    			month_name=months[sub_ac_month-1];
                    			sub_ac_year=response.getElementsByTagName("sub_ac_year")[0].firstChild.nodeValue;
                    			sub_ac_unit=response.getElementsByTagName("sub_ac_unit")[0].firstChild.nodeValue;
                    			result=result+"SubAmount in"+sub_ac_unit+" for "+month_name+"-"+sub_ac_year;
                    			
                    		  } 
                		  Add_val=response.getElementsByTagName("arr")[0].firstChild.nodeValue;
                    		  if(Add_val=='already'){
                    			arr_ac_month=response.getElementsByTagName("arr_ac_month")[0].firstChild.nodeValue;
                    			month_name=months[sub_ac_month-1];
                        		arr_ac_year=response.getElementsByTagName("arr_ac_year")[0].firstChild.nodeValue;
                        		arr_ac_unit=response.getElementsByTagName("arr_ac_unit")[0].firstChild.nodeValue;
                        		result=result+" , ArrearAmount in"+arr_ac_unit+" for "+month_name+"-"+arr_ac_year;
                    		  }
                    		  Add_val=response.getElementsByTagName("ref")[0].firstChild.nodeValue;
                    		  if(Add_val=='already'){
                    			
                    			
                    			ref_ac_month=response.getElementsByTagName("ref_ac_month")[0].firstChild.nodeValue;
                    			month_name=months[sub_ac_month-1];
                    			
                        		ref_ac_year=response.getElementsByTagName("ref_ac_year")[0].firstChild.nodeValue;
                        		ref_ac_unit=response.getElementsByTagName("ref_ac_unit")[0].firstChild.nodeValue;
                        		result=result+" , RefundAmount in"+ref_ac_unit+" for "+month_name+"-"+ref_ac_year;
                    		  }
                	  }
                      alert(result+" already Exists for this employee");
                }
                else if(flag=='nogpf'){
                	alert("GPF No does not exist");
                	  document.getElementById("txtGpfNo").value="";
                }
                
            }
          else  if(command=="Update")
            {
                if(flag=='success')
                {  
                	if (window.opener && !window.opener.closed) {
                		window.close();
                		window.opener.focus();  
                		opener.popParentEmp(4);         		
                	} 
               	alert("Update Successfully");
               	window.close();	
                }
                else if(flag=='Available'){
              	  var Update=response.getElementsByTagName("Update");
              	  var result="", Update_val="";
              	  var months = new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
              	  for(i=0;i<Update.length;i++){
              		Update_val=response.getElementsByTagName("Update")[i].firstChild.nodeValue;
              		  if(Update_val=='sub'){
              			sub_ac_month=response.getElementsByTagName("sub_ac_month")[i].firstChild.nodeValue;
              			month_name=months[sub_ac_month-1];
              			sub_ac_year=response.getElementsByTagName("sub_ac_year")[i].firstChild.nodeValue;
              			sub_ac_unit=response.getElementsByTagName("sub_ac_unit")[i].firstChild.nodeValue;
              			result=result+"SubAmount in"+sub_ac_unit+" for "+month_name+"-"+sub_ac_year;
              		  }
              		  if(Update_val=='arrear'){
              			arr_ac_month=response.getElementsByTagName("arr_ac_month")[i].firstChild.nodeValue;
              			month_name=months[sub_ac_month-1];
                  		arr_ac_year=response.getElementsByTagName("arr_ac_year")[i].firstChild.nodeValue;
                  		arr_ac_unit=response.getElementsByTagName("arr_ac_unit")[i].firstChild.nodeValue;
                  		result=result+" , ArrearAmount in"+arr_ac_unit+" for "+month_name+"-"+arr_ac_year;
              		  }
              		  if(Update_val=='refund'){
              			ref_ac_month=response.getElementsByTagName("ref_ac_month")[i].firstChild.nodeValue;
              			month_name=months[sub_ac_month-1];
                  		ref_ac_year=response.getElementsByTagName("ref_ac_year")[i].firstChild.nodeValue;
                  		ref_ac_unit=response.getElementsByTagName("ref_ac_unit")[i].firstChild.nodeValue;
                  		result=result+" , RefundAmount in"+ref_ac_unit+" for "+month_name+"-"+ref_ac_year;
              		  }
              	  }
                    alert(result+" already Exists for this employee");
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
            		
            		window.close();
            		 window.opener.focus();  
            		
            		opener.popParentEmp(4);         		
         
           	 } 
                 	                	
                 alert("Deleted Successfully");
             	window.close();       
                }
                else
                {
                    alert("Not success in Delete");
                    }
            
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
        }else{}
    }
           
}
function Exit()
{
    window.close();
}
String.prototype.trim = function () {
	return this.replace(/^\s*(\S*(\s+\S+)*)\s*$/, "$1");
	};

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

function selectActMonthByActYear(option){
	//alert("************");
	var start_months_val = new Array(1,2,3,4,5,6,7,8,9,10,11,12);
	var months = new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
	if(option==1){
    var acyear=document.getElementById("rel_year").value;
    var curr_date=new Date();
    var curr_year=curr_date.getFullYear();
    var curr_mnth=curr_date.getMonth();
   
    var start_months_val1=new Array();
   
    var months1=new Array();
    if(curr_year==acyear){
    	for(var i=0;i<=curr_mnth;i++)
    		start_months_val1[i] =start_months_val[i];
    		months1[i]=months[i];
    }
    var minorcmb = document.Hrm_TransJoinFormpop.rel_month;
     document.Hrm_TransJoinFormpop.rel_month.length=1;
   
        for(var i=0; i<start_months_val1.length; i++){
            var opt1 = document.createElement('option');
            opt1.value = start_months_val1[i];
            opt1.innerHTML = months1[i];
            minorcmb.appendChild(opt1);
        }
   }
	else{
		var minorcmb = document.Hrm_TransJoinFormpop.rel_month;
	     document.Hrm_TransJoinFormpop.rel_month.length=1;
	   
	        for(var i=0; i<start_months_val.length; i++){
	            var opt1 = document.createElement('option');
	            opt1.value = start_months_val[i];
	            opt1.innerHTML = months[i];
	            minorcmb.appendChild(opt1);
	        }
	}
}

function getRelMonth(acYear,acMonth){
	
	var rel_year=parseInt(document.Hrm_TransJoinFormpop.rel_year.value);
	document.getElementById("rel_month").innerHTML="";
	var monthvalue=new Array("JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC");
	var months_val = new Array(1,2,3,4,5,6,7,8,9,10,11,12);
	var minorcmb = document.Hrm_TransJoinFormpop.rel_month;
    document.Hrm_TransJoinFormpop.rel_month.length=0;
   if(acYear==rel_year){
	   for(var i=0; i<acMonth; i++){
           var opt1 = document.createElement('option');
           opt1.value = months_val[i];
           opt1.innerHTML = monthvalue[i];
           minorcmb.appendChild(opt1);
       }
   } 
   else{
       for(var i=0; i<monthvalue.length; i++){
           var opt1 = document.createElement('option');
           opt1.value = months_val[i];
           opt1.innerHTML = monthvalue[i];
           minorcmb.appendChild(opt1);
       }
   }   
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
