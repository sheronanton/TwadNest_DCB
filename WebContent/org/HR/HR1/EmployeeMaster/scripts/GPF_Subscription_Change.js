var xmlhttp;var seq=0;var common="",mn,yr,v;
function Exit()
{
    window.close();
}
function doParentEmp(emp)
{
document.getElementById("txtGpfNo").value=emp;
call('get');
}
var winemp;

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
     winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpRelievalPopup_TRN_Order.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
}

window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
}


 function loadvalue(empid)
    {      
          common=empid;
          var emp_id=document.getElementById(empid); 
          var rcells=emp_id.cells;
          document.getElementById("req_no").value=rcells.item(1).firstChild.nodeValue;
          document.getElementById("no").value=rcells.item(1).firstChild.nodeValue;
           document.getElementById("amount").value=rcells.item(2).firstChild.nodeValue;
           var eff_month=rcells.item(3).firstChild.value;
           var eff_year=rcells.item(4).firstChild.value;
          
           document.getElementById("eff_month").options[eff_month-1].selected='selected';
           if(eff_year==2009)
           {
            document.getElementById("eff_year").options[0].selected='selected';
            }
            else if(eff_year==2010)
           {
            document.getElementById("eff_year").options[1].selected='selected';
            } 
            else
           {
            document.getElementById("eff_year").options[2].selected='selected';
            }
            document.getElementById("date").value=rcells.item(4).lastChild.nodeValue; 
            
           
            var remark=rcells.item(2).lastChild.value;
           
           if(remark=="")
          {
           
            remark=" ";
          }
          document.getElementById("remarks").value=remark;
          
          
          document.Hrm_TransJoinForm.add.disabled=true;
        document.Hrm_TransJoinForm.update.disabled=false;
        document.Hrm_TransJoinForm.validate.disabled=false;
        
          
      
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
    var flag,command,response,status;
    var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
    if(xmlhttp.readyState==4)
    {
       if(xmlhttp.status==200)
       {
       
            response=xmlhttp.responseXML.getElementsByTagName("response")[0];
            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            status=response.getElementsByTagName("status")[0].firstChild.nodeValue;
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
               //     document.getElementById("txtDOB").value=dob;
                    document.getElementById("txtGpfNo").value=gpfno;
                    document.getElementById("designation").value=d_id;
                    document.getElementById("txtEmpId").value=emp_id;
                    var len=response.getElementsByTagName("sino").length;
                
               
                    clear1();
                }
                else
                    {
                        document.Hrm_TransJoinForm.txtEmpId.value="";
                      document.getElementById("comEmpId").value="";
                 //   document.getElementById("txtDOB").value="";
                    document.getElementById("txtGpfNo").value="";
                    document.getElementById("designation").value="";
                    document.Hrm_TransJoinForm.txtGpfNo.focus();
                    alert("This Employee GPF.No doesnot Exist");
                    }
            }
             else if(command=="load")
            {
                if(flag=='success')
                {
                   
     
                    if(status=="add")
                    {
                        alert("Record Inserted Successfully");
                    }
                    else if(status=="update")
                    {
                        alert("Record Updated Successfully");
                    }
                    else
                    {
                        alert("Record Validated Successfully");
                    }
                      clear1();
                }
                else
                    {
                        document.Hrm_TransJoinForm.txtEmpId.value="";
                      document.getElementById("comEmpId").value="";
                //    document.getElementById("txtDOB").value="";
                    document.getElementById("txtGpfNo").value="";
                    document.getElementById("designation").value="";
                    document.Hrm_TransJoinForm.txtGpfNo.focus();
                    alert("Loading Error");
                    }
            }
           else if(command=="Add")
            {
                if(flag=='success')
                {
                     
                    var emp_id=response.getElementsByTagName("emp_id")[0].firstChild.nodeValue;
                   var ac_month=response.getElementsByTagName("ac_month")[0].firstChild.nodeValue;
                    ac_month=month[ac_month-1];
                    var acyear=response.getElementsByTagName("ac_year")[0].firstChild.nodeValue;
                    var rel_month=response.getElementsByTagName("rel_month")[0].firstChild.nodeValue;
                    rel_month=month[rel_month-1];
                    var relyear=response.getElementsByTagName("rel_year")[0].firstChild.nodeValue;
                    var type_trans=response.getElementsByTagName("type_trans")[0].firstChild.nodeValue;
                    var impound_type=response.getElementsByTagName("impound_type")[0].firstChild.nodeValue;
                    var amount=response.getElementsByTagName("amount")[0].firstChild.nodeValue;
                    var date_trans=response.getElementsByTagName("date_trans")[0].firstChild.nodeValue;
                  
                    var remark=response.getElementsByTagName("remarks")[0].firstChild.nodeValue;
                    
                   var tr=document.createElement("TR");
                    tr.id=seq;
                    var td=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadvalue('"+seq+"')";
                    anc.href=url;
                    var edit=document.createTextNode("Edit");
                    anc.appendChild(edit);
                    td.appendChild(anc);
                    tr.appendChild(td);
                    
                    var td1=document.createElement("TD");
                    var empid=document.createTextNode(emp_id);
                    td1.appendChild(empid);
                    tr.appendChild(td1);     
                    
                    
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
                     var amt=document.createTextNode(amount);
                    td5.appendChild(amt);
                    tr.appendChild(td5);
                    
                    var td6=document.createElement("TD");
                    var h8=document.createElement("TEXT");
                    h8.type="hidden";
                    h8.name="date_trans";
                    h8.value=date_trans;
                    td6.appendChild(h8);
                    var trans=new Array();
                    trans=date_trans.split("/");
                    var day=trans[0];
                    var mon=trans[1];
                    var yer=trans[2];
                     var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
                    mon=month[eval(mon)-1];
                   var h9=document.createTextNode(day+"-"+mon+"-"+yer);
                   td6.appendChild(h9);
                    tr.appendChild(td6);
                    
                    var td7=document.createElement("TD");
                    var impoundtype=document.createTextNode(impound_type);
                    td7.appendChild(impoundtype);
                    tr.appendChild(td7);
                    
                    tlist.appendChild(tr);             
                    seq++;
                    
                   
                     document.Hrm_TransJoinForm.update.disabled=true;
                     document.Hrm_TransJoinForm.validate.disabled=true;
                     alert("Added Successfully");
                     clear1();
                }
                else
                    alert("Same data wont be allowed");
            }
          else  if(command=="Update")
            {
                if(flag=='success')
                {
                   var tlist=document.getElementById("tlist");
                     try{
                          tlist.innerHTML="";
                        }
                     catch(e)
                          {
                           tlist.innerText="";
                           }
                    var emp_id=response.getElementsByTagName("emp_id")[0].firstChild.nodeValue;
                    var ac_month=response.getElementsByTagName("ac_month")[0].firstChild.nodeValue;
                    ac_month=month[ac_month-1];
                    var rel_month=response.getElementsByTagName("rel_month")[0].firstChild.nodeValue;
                    rel_month=month[rel_month-1];
                    var impound_type=response.getElementsByTagName("impound_type")[0].firstChild.nodeValue;
                    var type_trans=response.getElementsByTagName("type_trans")[0].firstChild.nodeValue;
                    var amount=response.getElementsByTagName("amount")[0].firstChild.nodeValue;
                    var date_trans=response.getElementsByTagName("date_trans")[0].firstChild.nodeValue;
                    var remarks=response.getElementsByTagName("remarks")[0].firstChild.nodeValue;
                    var ac_year=response.getElementsByTagName("ac_year")[0].firstChild.nodeValue;
                    var rel_year=response.getElementsByTagName("rel_year")[0].firstChild.nodeValue;
                   
                   
                    
                  var readCell=document.getElementById(common);
                    var cells=readCell.cells;
                    cells.item(1).firstChild.nodeValue=emp_id;
                    cells.item(2).lastChild.nodeValue=ac_month+"-"+ac_year;
                    //cells.item(2).firstChild.value=ac_month;
                    //cells.item(2).lastChild.value=ac_year;
                    cells.item(3).lastChild.nodeValue=rel_month+"-"+rel_year;
                    cells.item(3).firstChild.value=rel_month;
                    //cells.item(3).lastChild.value=rel_year;
                    cells.item(4).firstChild.nodeValue=type_trans;
                    cells.item(4).lastChild.value=remarks;
                    
                    cells.item(5).firstChild.nodeValue=amount;
                    cells.item(6).firstChild.value=date_trans;
                    var trans=new Array();
                    trans=date_trans.split("/");
                    var day=trans[0];
                   
                    var mon=trans[1];
                    var yer=trans[2];
                     var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
                    mon=month[eval(mon)-1];
                    cells.item(6).lastChild.nodeValue=day+"-"+mon+"-"+yer;;
                    cells.item(7).firstChild.nodeValue=impound_type; 
                   
                    alert("Updated Successfully");
                    document.Hrm_TransJoinForm.impound_type.disabled=false;
                    document.Hrm_TransJoinForm.impound_type[0].selected='selected';
                     document.Hrm_TransJoinForm.add.disabled=true;
                    document.Hrm_TransJoinForm.update.disabled=true;
                    document.Hrm_TransJoinForm.validate.disabled=true;
                    clear1();
                                       
                }
                else
                    alert("Failure in Update");
            }
          else if(command=="Delete")
            {
                if(flag=='success')
                {   
                var tlist=document.getElementById("tlist");
                     try{
                          tlist.innerHTML="";
                        }
                     catch(e)
                          {
                           tlist.innerText="";
                           }
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
                    var impound_type=response.getElementsByTagName("impound_type")[i].firstChild.nodeValue;
                    var amount=response.getElementsByTagName("amount")[i].firstChild.nodeValue;
                    var date_trans=response.getElementsByTagName("date_trans")[i].firstChild.nodeValue;
                    
                    var remark=response.getElementsByTagName("remarks")[i].firstChild.nodeValue;
                    
                    var tr=document.createElement("TR");
                    tr.id=seq;
                    var td=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadvalue('"+seq+"')";
                    anc.href=url;
                    var edit=document.createTextNode("Edit");
                    anc.appendChild(edit);
                    td.appendChild(anc);
                    tr.appendChild(td);
                    
                    var td1=document.createElement("TD");
                    var empid=document.createTextNode(emp_id);
                    td1.appendChild(empid);
                    tr.appendChild(td1);     
                    
                    
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
                    var amt=document.createTextNode(amount);
                    td5.appendChild(amt);
                    tr.appendChild(td5);
                    
                    var td6=document.createElement("TD");
                    var h8=document.createElement("TEXT");
                    h8.type="hidden";
                    h8.name="date_trans";
                    h8.value=date_trans;
                    td6.appendChild(h8);
                    var trans=new Array();
                    trans=date_trans.split("/");
                    var day=trans[0];
                    var mon=trans[1];
                    var yer=trans[2];
                     var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
                    mon=month[eval(mon)-1];
                   var h9=document.createTextNode(day+"-"+mon+"-"+yer);
                   td6.appendChild(h9);
                    tr.appendChild(td6);
                    
                    var td7=document.createElement("TD");
                    var impoundtype=document.createTextNode(impound_type);
                    td7.appendChild(impoundtype);
                    tr.appendChild(td7);
                    
                    tlist.appendChild(tr);             
                    seq++;
                    }
                    
                    
                  /*  var tlist=document.getElementById("tlist");
                   
                    alert(common);
                    if(common==0)
                    {
                    var field=document.getElementById(common);
                    }
                    else
                    {
                    var field=document.getElementById(common-1);
                    }
                    var index=field.rowIndex;
                    tlist.deleteRow(index);         */
                    alert("Deleted Successfully");
                    document.Hrm_TransJoinForm.update.disabled=true;
                    document.Hrm_TransJoinForm.validate.disabled=true;       
                    clear1();
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
        }
    }
           
}

function clearGPF()
{
          
  

        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.eff_year[1].selected='selected';
        document.Hrm_TransJoinForm.eff_month.selectedIndex = 0;
        document.Hrm_TransJoinForm.fin_year.selectedIndex = 0;
        document.Hrm_TransJoinForm.comEmpId.value="";
        document.Hrm_TransJoinForm.designation.value="";
    //    document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
         document.Hrm_TransJoinForm.amount.value="";
          document.Hrm_TransJoinForm.date.value="";
           document.Hrm_TransJoinForm.remarks.value="";
          
        document.Hrm_TransJoinForm.txtGpfNo.focus();
      
       
       
       
   
    
         document.Hrm_TransJoinForm.add.disabled=false;
      
        
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
        var impound_type;
        var emp_id;
        var amount;
        var date_trans;
        var remarks;
        
        var url="";
        if(command=="Add")
        { 
                if(nullCheck())
                {
                	  gpf_no=document.getElementById("txtGpfNo").value;
                      emp_id=document.getElementById("txtEmpId").value;
                      eff_month=document.getElementById("eff_month").value;
                      eff_year=document.getElementById("eff_year").value;
                      amount=document.getElementById("amount").value;
                      date_trans=document.getElementById("date").value;
                      remarks=document.getElementById("remarks").value;
                      fin_year=document.getElementById("fin_year").value;
                      Acc_unit_code=document.getElementById("Acc_unit_code").value;
                      
                       if(remarks=="null"||remarks==""||remarks==null||remarks==" ")
                       {
                          remarks="";
                       }
                       
                     
                      url="../../../../../GPF_Subscription_Change.view?command=Add&gpf_no="+gpf_no+"&eff_month="+eff_month+"&eff_year="+eff_year+"&amount="+amount+"&date_trans="+date_trans+"&remarks="+remarks+"&fin_year="+fin_year+"&Acc_unit_code="+Acc_unit_code;
                    
                      url=url+"&sid="+Math.random();
                      
                      xmlhttp.open("GET",url,true);
                      xmlhttp.onreadystatechange=stateChanged;
                      xmlhttp.send(null);       
        }
    }
    else if(command=="Update")
    {
                if(nullCheck())
                {
                	
                gpf_no=document.getElementById("txtGpfNo").value;
                eff_month=document.getElementById("eff_month").value;
                eff_year=document.getElementById("eff_year").value;
                no=document.getElementById("no").value;
                amount=document.getElementById("amount").value;
                date_trans=document.getElementById("date").value;
                remarks=document.getElementById("remarks").value;
                    if(remarks=="null"||remarks==""||remarks==null||remarks==" ")
                     {
                        remarks="noRemarks";
                     }

               
        url="../../../../../GPF_Subscription_Change.view?command=Update&gpf_no="+gpf_no+"&eff_month="+eff_month+"&eff_year="+eff_year+"&amount="+amount+"&date_trans="+date_trans+"&remarks="+remarks+"&no="+no;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);   
        }
    }
    else if(command=="validate")
    {
    	gpf_no=document.getElementById("txtGpfNo").value;
                eff_month=document.getElementById("eff_month").value;
                eff_year=document.getElementById("eff_year").value;
                no=document.getElementById("no").value;
                amount=document.getElementById("amount").value;
                date_trans=document.getElementById("date").value;
                remarks=document.getElementById("remarks").value;
                    if(remarks=="null"||remarks==""||remarks==null||remarks==" ")
                     {
                        remarks="noRemarks";
                     }
              
             
               
        url="../../../../../GPF_Subscription_Change.view?command=Validate&gpf_no="+gpf_no+"&eff_month="+eff_month+"&eff_year="+eff_year+"&amount="+amount+"&date_trans="+date_trans+"&remarks="+remarks+"&no="+no;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);   
    }
  
    else if(command=="get")
    {   
        
        if((document.Hrm_TransJoinForm.txtGpfNo.value=="")||(document.Hrm_TransJoinForm.txtGpfNo.value.length==0))
            {
                alert("Null Value not accepted...Select GPF.No");
                document.Hrm_TransJoinForm.txtGpfNo.focus();
                return false;
            }
        var gpf_no=document.Hrm_TransJoinForm.txtGpfNo.value;
        
         
       
        url="../../../../../GPF_Subscription_Change.view?command=get&gpf_no="+gpf_no;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
}
function nullCheck()
{
	 
	
	 if(document.Hrm_TransJoinForm.fin_year.selectedIndex == 0)
     {
     alert("Select The financial Year");
      return 0;
     }
	 else    if((document.Hrm_TransJoinForm.txtGpfNo.value=="")||(document.Hrm_TransJoinForm.txtGpfNo.value=="0"))
        {
        alert("GPF no must be Correct");
         return 0;
        }
       
        else if(document.Hrm_TransJoinForm.amount.value=="")
        {
        alert("Enter  the Subscription  Amount");
         return 0;
        }
        else if(document.Hrm_TransJoinForm.date.value=="")
        {
        alert("Select Date from Calendar");
         return 0;
        }
        
      
    return 1;

}
function filter_real(evt,item,n,pre)
{
         var charCode = (evt.which) ? evt.which : event.keyCode;
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

function checkName()
{
xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your borwser doesnot support AJAX");
    return;
    }  
    var unit_name=document.getElementById("unit_name").value;
    
        url="../../../../../GPF_Impound.view?command=unit&unit_name="+unit_name;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);   
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


function validate(isField){

	splitDate = isField.value.split("/");
	if (splitDate[2] && splitDate[2].length == 2){
		splitDate[2] = "20"+splitDate[2];
	                                                                            }
	refDate = new Date(splitDate[1]+"/"+splitDate[0]+"/"+splitDate[2]);
	if (splitDate[1] < 1 || splitDate[1] > 12 || refDate.getDate() != splitDate[0] || splitDate[2].length != 4 || (!/^20/.test(splitDate[2])))
	{
	alert("Please Enter A Valid Date");
	isField.value = "";
	isField.focus();
	}
	}