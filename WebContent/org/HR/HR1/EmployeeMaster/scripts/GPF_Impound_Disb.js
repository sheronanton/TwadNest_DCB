var xmlhttp;var seq=0;var common="",mn,yr,v;
var final_diff_imp_reg_db=0,final_diff_imp_reg_cr=0,final_diff_imp_2003_db=0,final_diff_imp_2003_cr=0;

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

function popParentEmp(emp)
{
call('getone');
}



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

window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
}


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
          
          
          
        /*  if(impound_type=="Impound(Regular)")
          {
          
          document.Hrm_TransJoinForm.impound_type[0].checked='checked';
          }
          else
          { 
          document.Hrm_TransJoinForm.impound_type[1].checked='checked';
          }
          document.Hrm_TransJoinForm.impound_type[0].disabled=true;
          document.Hrm_TransJoinForm.impound_type[1].disabled=true;*/
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

function activatediv()
{
    var impound=document.getElementById("impound_type").value;
    document.getElementById("impound").style.display="block";
    if (impound=='ImpReg')
      {
            document.getElementById("impreg").style.display="block";
            document.getElementById("imp2003").style.display="none";
      }
       else if (impound=='Imp2003')
      {
             document.getElementById("impreg").style.display="none";
             document.getElementById("imp2003").style.display="block";
      }
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
            
          
            
            if(command=="getone")
            {            	
            	document.getElementById("form_data").style.display="block";
            	document.getElementById("TB_data").style.display="block";
                if(flag=='success')
                {
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
                    var impound_type=response.getElementsByTagName("impound_type")[i].firstChild.nodeValue;
                    var amount=response.getElementsByTagName("amount")[i].firstChild.nodeValue;
                    var date_trans=response.getElementsByTagName("date_trans")[i].firstChild.nodeValue;
                    var remark=response.getElementsByTagName("remarks")[i].firstChild.nodeValue;
                    var dis_slno=response.getElementsByTagName("dis_sl_no")[i].firstChild.nodeValue;
                    var fin_year=response.getElementsByTagName("fin_year")[i].firstChild.nodeValue;
                 //   var inst_no=response.getElementsByTagName("inst_no")[i].firstChild.nodeValue;
                    var install_type=response.getElementsByTagName("install_type")[i].firstChild.nodeValue;
                    
                    var gpfno=response.getElementsByTagName("gpf_no")[i].firstChild.nodeValue;
                    var name=response.getElementsByTagName("name")[i].firstChild.nodeValue;
                   
                    var processfloweid=response.getElementsByTagName("processflowid")[i].firstChild.nodeValue;
                    
                    if(name=="notmentioned")
                    {
                    	name="";
                    }
                    
                   
                    
                    var tr=document.createElement("TR");
                    tr.id=seq;
                    
                    if(processfloweid!="FR"){
                    var td=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:pick('"+seq+"')";
                    anc.href=url;
                    var edit=document.createTextNode("Edit");
                    anc.appendChild(edit);
                    td.appendChild(anc);
                    tr.appendChild(td);
                    }else{
                    	 var tdnot=document.createElement("TD");
                         var gpf12=document.createTextNode("");
                         tdnot.appendChild(gpf12);
                         tr.appendChild(tdnot);  	
                    }
                    var td1=document.createElement("TD");
                    var gpf=document.createTextNode(gpfno);
                    td1.appendChild(gpf);
                    tr.appendChild(td1);  
                    
                    var tda=document.createElement("TD");
                    var nam=document.createTextNode(name);
                    tda.appendChild(nam);
                    tr.appendChild(tda);  
                    
                    var td4=document.createElement("TD");
                    var fin_year=document.createTextNode(fin_year);
                    td4.appendChild(fin_year);
                   
                    tr.appendChild(td4);
                    
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
                    var date=document.createTextNode(date_trans);
                    td6.appendChild(date);
                    tr.appendChild(td6);
                                     
                    var td7=document.createElement("TD");
                    var impoundtype=document.createTextNode(impound_type);
                    td7.appendChild(impoundtype);
                     var h7=document.createElement("TEXT");
                    h7.type="hidden";
                    h7.name="dis_slno";
                    h7.value=dis_slno;
                    td7.appendChild(h7);
                    tr.appendChild(td7);
                    
                    var td8=document.createElement("TD");
                    var inst=document.createTextNode(install_type);
                    td8.appendChild(inst);
                    tr.appendChild(td8);   
                    
                    
                    tlist.appendChild(tr);             
                    seq++;
                    }
                   var dtotal=response.getElementsByTagName("dtotal")[0].firstChild.nodeValue; 
                   document.getElementById("dtotal").value=dtotal;
                   
                   var ctotal=response.getElementsByTagName("ctotal")[0].firstChild.nodeValue; 
                   document.getElementById("ctotal").value=ctotal;
                   
                   var dtotal1=response.getElementsByTagName("dtotal1")[0].firstChild.nodeValue; 
                   document.getElementById("dtotal1").value=dtotal1;
                   
                   var ctotal1=response.getElementsByTagName("ctotal1")[0].firstChild.nodeValue; 
                   document.getElementById("ctotal1").value=ctotal1;
                   
                   
                   document.getElementById("trial_impreg_debit_cr").value=response.getElementsByTagName("impreg_debit_cr")[0].firstChild.nodeValue;
             	   document.getElementById("trial_impreg_debit_db").value=response.getElementsByTagName("impreg_debit_db")[0].firstChild.nodeValue; 
             	   document.getElementById("trial_impreg_credit_cr").value=response.getElementsByTagName("impreg_credit_cr")[0].firstChild.nodeValue; 
             	   document.getElementById("trial_impreg_credit_db").value=response.getElementsByTagName("impreg_credit_db")[0].firstChild.nodeValue; 
             	   
             	   document.getElementById("trial_imp2003_debit_cr").value=response.getElementsByTagName("imp2003_debit_cr")[0].firstChild.nodeValue;
            	   document.getElementById("trial_imp2003_debit_db").value=response.getElementsByTagName("imp2003_debit_db")[0].firstChild.nodeValue; 
            	   document.getElementById("trial_imp2003_credit_cr").value=response.getElementsByTagName("imp2003_credit_cr")[0].firstChild.nodeValue; 
            	   document.getElementById("trial_imp2003_credit_db").value=response.getElementsByTagName("imp2003_credit_db")[0].firstChild.nodeValue; 
                  
                   
                   var credit=parseInt(document.getElementById("trial_impreg_credit_cr").value)-parseInt(document.getElementById("trial_impreg_credit_db").value);
                   document.getElementById("TBimpreg_cr").value=parseInt(credit);
                   var credit1=parseInt(document.getElementById("trial_imp2003_credit_cr").value)-parseInt(document.getElementById("trial_imp2003_credit_db").value);
                   document.getElementById("TBimp03_cr").value=parseInt(credit1);
                   var debit=parseInt(document.getElementById("trial_impreg_debit_db").value)-parseInt(document.getElementById("trial_impreg_debit_cr").value);
                   document.getElementById("TBimpreg_db").value=parseInt(debit);
                   var debit1=parseInt(document.getElementById("trial_imp2003_debit_db").value)-parseInt(document.getElementById("trial_imp2003_debit_cr").value); 
                   document.getElementById("TBimp03_db").value=parseInt(debit1);
                 
                   debit=debit-dtotal;
                   debit1=debit1-dtotal1;
                   credit=credit-ctotal;
                   credit1=credit1-ctotal1;
                   document.getElementById("ddiff").value=debit;
                   document.getElementById("cdiff").value=credit;
                   document.getElementById("ddiff1").value=debit1;
                   document.getElementById("cdiff1").value=credit1;
                   
                   document.getElementById("final_TB_impreg_DB").value=parseInt(document.getElementById("TBimpreg_db").value);
	               document.getElementById("final_TB_impreg_CR").value=parseInt(document.getElementById("TBimpreg_cr").value);
	               document.getElementById("final_TB_imp2003_DB").value=parseInt(document.getElementById("TBimp03_db").value);
	               document.getElementById("final_TB_imp2003_CR").value=parseInt(document.getElementById("TBimp03_cr").value);
                   
                   if(debit==0 & debit1==0 & credit==0 &credit1==0)
                	   document.Hrm_TransJoinForm.validate.disabled=false;
                   else
                	   document.Hrm_TransJoinForm.validate.disabled=true;
                  // document.getElementById("txtGpfNo").value=gpfno;   
                   
                }
                else
                    {
                    document.Hrm_TransJoinForm.txtEmpId.value="";
                    document.getElementById("comEmpId").value="";
                    document.getElementById("txtDOB").value="";
                    document.getElementById("txtGpfNo").value="";
                    document.getElementById("designation").value="";
                    document.Hrm_TransJoinForm.txtGpfNo.focus();
                    alert("This Employee GPF.No doesnot Exist");
                    }
                call('AdjustmentJournal','imp');
            }
            
            else if(command=="AdjustmentJournal")
            {
           // alert("Adj Journal");
                    if(flag=='success')
                    {	  
                  	  
                  	  var command1=response.getElementsByTagName("command1")[0].firstChild.nodeValue;
                  	  var recno=0;
                  	  try{                  		 
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
      	                  //  alert("journal_val_len"+journal_val_len);
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
      	              var sub_length= response.getElementsByTagName("imp_regCR_cr_amt").length;
      	              var imp_regCR_cr_amt=0, imp_regCR_dr_amt=0,imp_2003CR_cr_amt=0, imp_2003CR_dr_amt=0;
      	              var imp_regDB_cr_amt=0, imp_regDB_dr_amt=0,imp_2003DB_cr_amt=0, imp_2003DB_dr_amt=0;
      	         
      	                for(i=0;i<sub_length;i++){
      	                	imp_regCR_cr_amt=parseInt(imp_regCR_cr_amt,10)+parseInt(response.getElementsByTagName("imp_regCR_cr_amt")[i].firstChild.nodeValue);
      	                	imp_regCR_dr_amt=parseInt(imp_regCR_dr_amt)+parseInt(response.getElementsByTagName("imp_regCR_dr_amt")[i].firstChild.nodeValue);
      	                	imp_2003CR_cr_amt=parseInt(imp_2003CR_cr_amt,10)+parseInt(response.getElementsByTagName("imp_2003CR_cr_amt")[i].firstChild.nodeValue);
      	                	imp_2003CR_dr_amt=parseInt(imp_2003CR_dr_amt)+parseInt(response.getElementsByTagName("imp_2003CR_dr_amt")[i].firstChild.nodeValue);
      	                	imp_regDB_cr_amt=parseInt(imp_regDB_cr_amt,10)+parseInt(response.getElementsByTagName("imp_regDB_cr_amt")[i].firstChild.nodeValue);
      	                	imp_regDB_dr_amt=parseInt(imp_regDB_dr_amt)+parseInt(response.getElementsByTagName("imp_regDB_dr_amt")[i].firstChild.nodeValue);
      	                	imp_2003DB_cr_amt=parseInt(imp_2003DB_cr_amt,10)+parseInt(response.getElementsByTagName("imp_2003DB_cr_amt")[i].firstChild.nodeValue);
      	                	imp_2003DB_dr_amt=parseInt(imp_2003DB_dr_amt)+parseInt(response.getElementsByTagName("imp_2003DB_dr_amt")[i].firstChild.nodeValue);
      	                 }
      	       
      	                document.getElementById("netpay_imp_reg_db").value=parseInt(document.getElementById("TBimpreg_db").value,10)+parseInt(imp_regDB_cr_amt,10)-parseInt(imp_regDB_dr_amt,10);
      	                document.getElementById("netpay_imp_2003_db").value=parseInt(document.getElementById("TBimp03_db").value,10)+parseInt(imp_2003DB_cr_amt,10)-parseInt(imp_2003DB_dr_amt,10);
      	                document.getElementById("netpay_imp_reg_cr").value=parseInt(document.getElementById("TBimpreg_cr").value,10)-parseInt(imp_regCR_cr_amt,10)+parseInt(imp_regCR_dr_amt,10);
      	            	document.getElementById("netpay_imp_2003_cr").value=parseInt(document.getElementById("TBimp03_cr").value,10)-parseInt(imp_2003CR_cr_amt,10)+parseInt(imp_2003CR_dr_amt,10);
      	                
      	                document.getElementById("imp_reg_DB__cr").value=parseInt(imp_regDB_cr_amt,10);
      	                document.getElementById("imp_reg_DB__dr").value=parseInt(imp_regDB_dr_amt,10);
      	                document.getElementById("imp_reg_CR__cr").value=parseInt(imp_regCR_cr_amt,10);
      	                document.getElementById("imp_reg_CR__dr").value=parseInt(imp_regCR_dr_amt,10);
      	                
      	                document.getElementById("imp_2003_DB__cr").value=parseInt(imp_2003DB_cr_amt,10);
    	                document.getElementById("imp_2003_DB__dr").value=parseInt(imp_2003DB_dr_amt,10);
    	                document.getElementById("imp_2003_CR__cr").value=parseInt(imp_2003CR_cr_amt,10);
    	                document.getElementById("imp_2003_CR__dr").value=parseInt(imp_2003CR_dr_amt,10);
      	               
    	                final_diff_imp_reg_db=parseInt(document.getElementById("netpay_imp_reg_db").value)-parseInt(document.getElementById("dtotal").value);
    	                final_diff_imp_reg_cr=parseInt(document.getElementById("netpay_imp_reg_cr").value)-parseInt(document.getElementById("ctotal").value);
    	                final_diff_imp_2003_db=parseInt(document.getElementById("netpay_imp_2003_db").value)-parseInt(document.getElementById("dtotal1").value);
    	                final_diff_imp_2003_cr=parseInt(document.getElementById("netpay_imp_2003_cr").value)-parseInt(document.getElementById("ctotal1").value);
    	           //     alert("*************"+document.getElementById("netpay_imp_reg_db").value);
    	                document.getElementById("final_TB_impreg_DB").value=parseInt(document.getElementById("netpay_imp_reg_db").value);
    	                document.getElementById("final_TB_impreg_CR").value=parseInt(document.getElementById("netpay_imp_reg_cr").value);
    	                document.getElementById("final_TB_imp2003_DB").value=parseInt(document.getElementById("netpay_imp_2003_db").value);
    	                document.getElementById("final_TB_imp2003_CR").value=parseInt(document.getElementById("netpay_imp_2003_cr").value);
    	                
    	            //    alert("?????????????"+document.getElementById("final_TB_impreg_DB").value);
    	                
      	                if(final_diff_imp_reg_db==0 && final_diff_imp_reg_cr==0 && final_diff_imp_2003_db==0 && final_diff_imp_2003_cr==0){
      	            		  document.Hrm_TransJoinForm.add.disabled=true;  
      	            	}else{
      	            		  document.Hrm_TransJoinForm.add.disabled=false;  
      	            	}
      	            }
                    catch(e){  }
                  	//****************End of Journal Values Shown*****************
                    }
                   if(final_diff_imp_reg_db==0 && final_diff_imp_reg_cr==0 && final_diff_imp_2003_db==0 && final_diff_imp_2003_cr==0 ){
                	 	   document.Hrm_TransJoinForm.validate.disabled=false;
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
                           tdg.style.fontsize="8";
                           tr.appendChild(tdg); 
                          tlist.appendChild(tr);
                 	  }                	  
                   }
                   call('AdjustmentJournal1','imp');
                }
                
                  /*******************************************/
                else if(command=="AdjustmentJournal1")
                { 
                  if(flag=='success')
                  {	  
                	 // alert("adjustmentjournal1");
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
      	      	              var sub_length= response.getElementsByTagName("imp_regCR_cr_amt").length;
      	      	              var imp_regCR_cr_amt=0, imp_regCR_dr_amt=0,imp_2003CR_cr_amt=0, imp_2003CR_dr_amt=0;
      	      	              var imp_regDB_cr_amt=0, imp_regDB_dr_amt=0,imp_2003DB_cr_amt=0, imp_2003DB_dr_amt=0;
      	      	         
      	      	                for(i=0;i<sub_length;i++){
      	      	                	imp_regCR_cr_amt=parseInt(imp_regCR_cr_amt,10)+parseInt(response.getElementsByTagName("imp_regCR_cr_amt")[i].firstChild.nodeValue);
      	      	                	imp_regCR_dr_amt=parseInt(imp_regCR_dr_amt)+parseInt(response.getElementsByTagName("imp_regCR_dr_amt")[i].firstChild.nodeValue);
      	      	                	imp_2003CR_cr_amt=parseInt(imp_2003CR_cr_amt,10)+parseInt(response.getElementsByTagName("imp_2003CR_cr_amt")[i].firstChild.nodeValue);
      	      	                	imp_2003CR_dr_amt=parseInt(imp_2003CR_dr_amt)+parseInt(response.getElementsByTagName("imp_2003CR_dr_amt")[i].firstChild.nodeValue);
      	      	                	imp_regDB_cr_amt=parseInt(imp_regDB_cr_amt,10)+parseInt(response.getElementsByTagName("imp_regDB_cr_amt")[i].firstChild.nodeValue);
      	      	                	imp_regDB_dr_amt=parseInt(imp_regDB_dr_amt)+parseInt(response.getElementsByTagName("imp_regDB_dr_amt")[i].firstChild.nodeValue);
      	      	                	imp_2003DB_cr_amt=parseInt(imp_2003DB_cr_amt,10)+parseInt(response.getElementsByTagName("imp_2003DB_cr_amt")[i].firstChild.nodeValue);
      	      	                	imp_2003DB_dr_amt=parseInt(imp_2003DB_dr_amt)+parseInt(response.getElementsByTagName("imp_2003DB_dr_amt")[i].firstChild.nodeValue);
      	      	                 }
      	      	          
      	      	            	document.getElementById("diff_imp_reg_db").value=parseInt(document.getElementById("netpay_imp_reg_db").value,10)-parseInt(document.getElementById("dtotal").value,10);
      	      	            	document.getElementById("diff_imp_reg_cr").value=parseInt(document.getElementById("netpay_imp_reg_cr").value,10)-parseInt(document.getElementById("ctotal").value,10);
      	      	            	document.getElementById("diff_imp_2003_db").value=parseInt(document.getElementById("netpay_imp_2003_db").value,10)-parseInt(document.getElementById("dtotal1").value,10);
  	      	            		document.getElementById("diff_imp_2003_cr").value=parseInt(document.getElementById("netpay_imp_2003_cr").value,10)-parseInt(document.getElementById("ctotal1").value,10);
      	      	            	
      	      	            	
      	      	            	document.getElementById("imp_reg_DB__cr1").value=parseInt(imp_regDB_cr_amt,10);
      	      	                document.getElementById("imp_reg_DB__dr1").value=parseInt(imp_regDB_dr_amt,10);
      	      	                document.getElementById("imp_reg_CR__cr1").value=parseInt(imp_regCR_cr_amt,10);
      	      	                document.getElementById("imp_reg_CR__dr1").value=parseInt(imp_regCR_dr_amt,10);
      	      	                
      	      	                document.getElementById("imp_2003_DB__cr1").value=parseInt(imp_2003DB_cr_amt,10);
      	    	                document.getElementById("imp_2003_DB__dr1").value=parseInt(imp_2003DB_dr_amt,10);
      	    	                document.getElementById("imp_2003_CR__cr1").value=parseInt(imp_2003CR_cr_amt,10);
      	    	                document.getElementById("imp_2003_CR__dr1").value=parseInt(imp_2003CR_dr_amt,10);
      	    	                
      	    	                document.getElementById("netpay_imp_reg_db1").value=parseInt(document.getElementById("final_TB_impreg_DB").value,10)-parseInt(document.getElementById("imp_reg_DB__cr1").value,10)+parseInt(document.getElementById("imp_reg_DB__dr1").value,10);
      	    	                document.getElementById("netpay_imp_2003_db1").value=parseInt(document.getElementById("final_TB_imp2003_DB").value,10)-parseInt(document.getElementById("imp_2003_DB__cr1").value,10)+parseInt(document.getElementById("imp_2003_DB__dr1").value,10);
      	    	                document.getElementById("netpay_imp_reg_cr1").value=parseInt(document.getElementById("final_TB_impreg_CR").value,10)+parseInt(document.getElementById("imp_reg_CR__cr1").value,10)-parseInt(document.getElementById("imp_reg_CR__dr1").value,10);    	      	                
    	      	            	document.getElementById("netpay_imp_2003_cr1").value=parseInt(document.getElementById("final_TB_imp2003_CR").value,10)+parseInt(document.getElementById("imp_2003_CR__cr1").value,10)-parseInt(document.getElementById("imp_2003_CR__dr1").value,10);
      	      	               
      	    	                final_diff_imp_reg_db=parseInt(document.getElementById("netpay_imp_reg_db1").value,10)-parseInt(document.getElementById("dtotal").value,10);
      	    	                final_diff_imp_reg_cr=parseInt(document.getElementById("netpay_imp_reg_cr1").value,10)-parseInt(document.getElementById("ctotal").value,10);
      	    	                final_diff_imp_2003_db=parseInt(document.getElementById("netpay_imp_2003_db1").value,10)-parseInt(document.getElementById("dtotal1").value,10);
      	    	                final_diff_imp_2003_cr=parseInt(document.getElementById("netpay_imp_2003_cr1").value,10)-parseInt(document.getElementById("ctotal1").value,10);
      	    	                
      	    	                
      	    	                document.getElementById("final_TB_impreg_DB").value=parseInt(document.getElementById("netpay_imp_reg_db1").value);
	          	                document.getElementById("final_TB_impreg_CR").value=parseInt(document.getElementById("netpay_imp_reg_cr1").value);
	          	                document.getElementById("final_TB_imp2003_DB").value=parseInt(document.getElementById("netpay_imp_2003_db1").value);
	          	                document.getElementById("final_TB_imp2003_CR").value=parseInt(document.getElementById("netpay_imp_2003_cr1").value);
          	              
      	      	                if(final_diff_imp_reg_db==0 && final_diff_imp_reg_cr==0 && final_diff_imp_2003_db==0 && final_diff_imp_2003_cr==0){
      	      	            		  document.Hrm_TransJoinForm.add.disabled=true;  
      	      	            		  document.Hrm_TransJoinForm.validate.disabled=false;
      	      	                }else{
      	      	            		  document.Hrm_TransJoinForm.add.disabled=false;  
      	      	            		  document.Hrm_TransJoinForm.validate.disabled=true;
      	      	            	}
      	      	            }
      	                    catch(e){  }
      	                  	//****************End of Journal Values Shown*****************
      	                    }
      	                
      	                }
            
            
            
        else if(command=="acc_head_code")
            {
                if(flag=='success')
                {
                    var acchead=response.getElementsByTagName("acc_head_value")[0].firstChild.nodeValue;
                    document.getElementById("acchead").value=acchead;
                }
                else
                {
                  
                    alert("problem in Account head code");
                }
            
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
        else if(command=="validate"){
        	
        	var result=response.getElementsByTagName("validate")[0].firstChild.nodeValue;
        	
        	if(result=="true"){
        		alert("Successfully Validated");
        		call('getone');
        		clearall();
        	}
        	else if(result=="already"){
        		alert("These Records are Already Validated");
        		clearall();
        	}
        	else if(result=="false"){
        		alert("Records are Not in Table");
        		clearall();
        	}
        }
  else if(command=="partial"){
	  if(flag=='success')
      {
		  var emp_id=response.getElementsByTagName("EMPLOYEE_ID")[0].firstChild.nodeValue;
		  var name=response.getElementsByTagName("name")[0].firstChild.nodeValue;
		  
		  document.getElementById("txtEmployee").value=name;
		  document.getElementById("txtGpf").value=emp_id;
		  document.getElementById("pa").disabled=false;
		  
      }
	  else
	  {
		  alert("This Employee not yet Retired");
		  document.getElementById("txtEmpId1").value="";
		  document.getElementById("pa").disabled=true;
	  }
        }
   }
           
 }
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
       
     if(command=="acc_head_code")
    {
           if (document.Hrm_TransJoinForm.trans[0].checked)
               {
                    type_trans=document.Hrm_TransJoinForm.trans[0].value;
               }
               else
                {
                   type_trans=document.Hrm_TransJoinForm.trans[1].value;
                }
            impound_type=document.Hrm_TransJoinForm.impound_type.value;
        url="../../../../../GPF_Impound.view?command=acc_head_code&type_trans="+type_trans+"&impound_type="+impound_type;
       
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);    
    }
   
    else if(command=="Delete")
    {
    	gpf_no=document.getElementById("txtGpfNo").value;
                office_id=document.getElementById("txtOffId").value;
                division_id=document.getElementById("oldcode").value;
                emp_id=document.getElementById("txtEmpId").value;
                ac_month=document.getElementById("ac_month").value;
                ac_year=document.getElementById("ac_year").value;
                rel_month=document.getElementById("rel_month").value;
                rel_year=document.getElementById("rel_year").value;
                amount=document.getElementById("amount").value;
                date_trans=document.getElementById("date").value;
                remarks=document.getElementById("remarks").value;
                var fin_year=document.getElementById("fin_year").value;
                
                var dis_sl_no=document.getElementById("Dis_sl_no").value;
               
                impound_type=document.Hrm_TransJoinForm.impound_type.value;
                
        
        url="../../../../../GPF_Impound.view?command=Delete&office_id="+office_id+"&division_id="+division_id+"&gpf_no="+gpf_no+"&emp_id="+emp_id+"&ac_month="+ac_month+"&ac_year="+ac_year+"&type_trans="+type_trans+"&amount="+amount+"&date_trans="+date_trans+"&remarks="+remarks+"&rel_month="+rel_month+"&rel_year="+rel_year+"&impound_type="+impound_type+"&fin_year="+fin_year+"&dis_sl_no="+dis_sl_no;;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);      
    }
  
    else if(command=="getone")
    {   
    	//alert("getone");
        var fin_year=document.getElementById("fin_year").value;
        var unit=document.getElementById("Acc_unit_code").value;
        var ac_month=document.getElementById("ac_month").value;
        var ac_year=document.getElementById("ac_year").value;
        url="../../../../../GPF_Impound.view?command=getone&fin_year="+fin_year+"&unit="+unit+"&ac_year="+ac_year+"&ac_month="+ac_month;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
    else if(command=="UnitName"){
    	var unit_id=document.getElementById("unit_name").value;
        document.getElementById("Acc_unit_code").value=unit_id;
        clearall();
    }    
    else if(command=="AdjustmentJournal")
    {   
    	//alert("Adjustment Journal");
    	var officeid=document.getElementById("txtOffId").value;
    	var acmonth=document.forms[0].ac_month.value;
  	   	var acyear=document.forms[0].ac_year.value;
  	   	var unit=document.forms[0].Acc_unit_code.value;
    	 
        url="../../../../../GPF_Impound.view?command=AdjustmentJournal&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit+"&command1=journal1";
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
  	//alert("AdjustmentJournal1");
    	var officeid=document.getElementById("txtOffId").value;
    	var acmonth=document.forms[0].ac_month.value;
  	   	var acyear=document.forms[0].ac_year.value;
  	   	var unit=document.forms[0].Acc_unit_code.value;
    	 
        url="../../../../../GPF_Impound.view?command=AdjustmentJournal&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit+"&command1=journal2";
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=function()
        {
        	stateChanged(xmlhttp);
        }
        xmlhttp.send(null);  
 	   
    }       
        
}
function nullCheck()
{
        if((document.Hrm_TransJoinForm.unit_name.value=="--Select Account Unit --"))
        {
        alert("please Choose Accounting unit ");
        return 0;
        }
                  
        else if(document.Hrm_TransJoinForm.fin_year.value==0)
        {
        alert("please Choose Financial Year");
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



function impoundpopup(comfun)
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
    
   if(comfun=="Add"){
	   
	   if(nullCheck())
	   {
		
	   var officeid=document.forms[0].txtOffId.value;
	   var acmonth=document.forms[0].ac_month.value;
	   var acyear=document.forms[0].ac_year.value;
	   var finyear=document.forms[0].fin_year.value;
	   var unit=document.forms[0].unit_name.value;
	   winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/GPF_Impound_Disb_Popup.jsp?command=Add&officeid="+officeid+"&finyear="+finyear+"&unit="+unit+"&acmonth="+acmonth+"&acyear="+acyear+"","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes");
   
   
   // winemp.document.Hrm_TransJoinFormpop.remarks.value = document.forms[0].txtOffId.value;
	   winemp.moveTo(250,250);  
	   winemp.focus();
	   }
   }
     
}




window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
}

//function getAccountUnit(){
//
//	var office_lvl=document.getElementById("txtOffLevel").value;
//	var office_id=document.getElementById("txtOffId").value;
//	var fin_year=document.getElementById("fin_year").value;
//	//alert("officeid"+office_id);
//	if(office_lvl=="H.O."){
//	//alert("HO");
//	}
//	else{
//	//alert("not HO");
//	}
//	var url="../../../../../AccountUnitServlet?office_lvl="+office_lvl+"&fin_year="+fin_year+"&office_id="+office_id;
//        req=getxmlhttpObject();
//	//var req=getTransport();
//	    req.open("POST",url,true);
//	   req.onreadystatechange=stateChanged;
//        req.send(null);    
//	 //   alert("********");
//	   
//	}

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
function pick(i){
	  var emp_id=document.getElementById(i); 
    var rcells=emp_id.cells;
   
    var unit=document.forms[0].Acc_unit_code.value;
  
    var gpfno=rcells.item(1).firstChild.nodeValue;
    var ac_month_year=rcells.item(4).lastChild.nodeValue.split("-");
    acmonth=ac_month_year[0];
    acyear=ac_month_year[1];
      
     //alert(gpfno);
   
    
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
         
    var rel_month_year=rcells.item(5).lastChild.nodeValue.split("-");
    relmonth=rel_month_year[0];
    relyear=rel_month_year[1];
    
    var typetrans=rcells.item(6).firstChild.nodeValue;
    
    var amount=rcells.item(7).firstChild.nodeValue;
    
    var date=rcells.item(8).firstChild.nodeValue;
    
    var typeimpound=rcells.item(9).firstChild.nodeValue;
    
    var slno=rcells.item(9).lastChild.value;
    
    var impounddetails=rcells.item(10).firstChild.nodeValue;
           
    var remark=rcells.item(6).lastChild.value;
  //  unit=rcells.item(1).lastChild.value;
   // alert(unit);
    if(remark=="noRemarks")
    {
     
      remark=" ";
    }
    
    var officeid=document.forms[0].txtOffId.value;
    var finyear=document.forms[0].fin_year.value;
   
    
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/GPF_Impound_Disb_Popup.jsp?command=update&officeid="+officeid+"&acmonth="+acmonth+"&acyear="+acyear+"&gpfno="+gpfno+"&relmonth="+relmonth+"&unit="+unit+"&relyear="+relyear+"&amount="+amount+"&remark="+remark+"&date="+date+"&typeimpound="+typeimpound+"&impounddetails="+impounddetails+"&typetrans="+typetrans+"&slno="+slno+"&finyear="+finyear+"","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes");
    winemp.moveTo(250,250);  
	  winemp.focus();
}


function validate1(){
	//alert("validate");
	var officeid=document.forms[0].txtOffId.value;
	var acmonth=document.forms[0].ac_month.value;
	var acyear=document.forms[0].ac_year.value;
	var finyear=document.forms[0].fin_year.value;
	var unit=document.forms[0].unit_name.value;
	
	var total_impreg_DB=parseInt(document.getElementById("dtotal").value);
	var total_impreg_CR=parseInt(document.getElementById("ctotal").value);
	var total_imp2003_DB=parseInt(document.getElementById("dtotal1").value);
	var total_imp2003_CR=parseInt(document.getElementById("ctotal1").value);
	
	var TB_impreg_DB=parseInt(document.getElementById("final_TB_impreg_DB").value);
	var TB_impreg_CR=parseInt(document.getElementById("final_TB_impreg_CR").value);
	var TB_imp2003_DB=parseInt(document.getElementById("final_TB_imp2003_DB").value);
	var TB_imp2003_CR=parseInt(document.getElementById("final_TB_imp2003_CR").value);
	
	
	var excl_impreg_DB=0,excl_impreg_CR=0,excl_imp2003_DB=0,excl_imp2003_CR=0;
	var incl_impreg_DB=0,incl_impreg_CR=0,incl_imp2003_DB=0,incl_imp2003_CR=0;
	if(document.getElementById("journal_real").style.display=="block"){	
		excl_impreg_DB=parseInt(document.getElementById("imp_reg_DB__cr").value)-parseInt(document.getElementById("imp_reg_DB__dr").value);
		excl_impreg_CR=parseInt(document.getElementById("imp_reg_CR__cr").value)+parseInt(document.getElementById("imp_reg_CR__dr").value);
		excl_imp2003_DB=parseInt(document.getElementById("imp_2003_DB__cr").value)-parseInt(document.getElementById("imp_2003_DB__dr").value);
		excl_imp2003_CR=parseInt(document.getElementById("imp_2003_CR__cr").value)+parseInt(document.getElementById("imp_2003_CR__dr").value);
	}
	
	if(document.getElementById("Adj_Jrnl_diff").style.display=="block"){
		incl_impreg_DB=parseInt(document.getElementById("imp_reg_DB__cr1").value)+parseInt(document.getElementById("imp_reg_DB__dr1").value);
		incl_impreg_CR=parseInt(document.getElementById("imp_reg_CR__cr1").value)-parseInt(document.getElementById("imp_reg_CR__dr1").value);
		incl_imp2003_DB=parseInt(document.getElementById("imp_reg_DB__cr1").value)+parseInt(document.getElementById("imp_reg_DB__dr1").value);
		incl_imp2003_CR=parseInt(document.getElementById("imp_reg_CR__cr1").value)-parseInt(document.getElementById("imp_reg_CR__dr1").value);
		
	}
	
	
	
	url="../../../../../GPF_Impound.view?command=validate&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit+"&officeid="+officeid;
	url=url+"&total_impreg_DB="+total_impreg_DB+"&total_impreg_CR="+total_impreg_CR+"&total_imp2003_DB="+total_imp2003_DB+"&total_imp2003_CR="+total_imp2003_CR;
	url=url+"&TB_impreg_DB="+TB_impreg_DB+"&TB_impreg_CR="+TB_impreg_CR+"&TB_imp2003_DB="+TB_imp2003_DB+"&TB_imp2003_CR="+TB_imp2003_CR;
	url=url+"&excl_impreg_DB="+excl_impreg_DB+"&excl_impreg_CR="+excl_impreg_CR+"&excl_imp2003_DB="+excl_imp2003_DB+"&excl_imp2003_CR="+excl_imp2003_CR;
	url=url+"&incl_impreg_DB="+incl_impreg_DB+"&incl_impreg_CR="+incl_impreg_CR+"&incl_imp2003_DB="+incl_imp2003_DB+"&incl_imp2003_CR="+incl_imp2003_CR;
	
	
	url=url+"&sid="+Math.random();
	xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange=stateChanged;
    xmlhttp.send(null);  
}

function clearall()
{
	document.Hrm_TransJoinForm.ac_year.options[0].selected='selected';
	document.Hrm_TransJoinForm.ac_month.options[0].selected='selected';
	 var tlist=document.getElementById("tlist");
     while (tlist.childNodes.length > 0) {
       	 tlist.removeChild(tlist.firstChild);
        }
     document.getElementById("TBimpreg_db").value="";	
     document.getElementById("TBimpreg_cr").value="";
     document.getElementById("TBimp03_db").value="";
     document.getElementById("TBimp03_cr").value="";
     document.getElementById("dtotal").value="";
     document.getElementById("ctotal").value="";
     
     document.getElementById("dtotal1").value="";
     document.getElementById("ctotal1").value="";
     document.getElementById("ddiff").value="";
     document.getElementById("cdiff").value="";
     document.getElementById("ddiff1").value="";
     document.getElementById("cdiff1").value="";
     
     document.getElementById("form_data").style.display="none";
     document.getElementById("TB_data").style.display="none";
     document.getElementById("journal_Adjustment").style.display="none";
	 document.getElementById('excluded_title').style.display="none";
     document.getElementById("journalforMan_Adjustment").style.display="none";
     document.getElementById('included_title').style.display="none";
     document.getElementById('journal_real').style.display="none";
     document.getElementById('Adj_Jrnl_diff').style.display="none";
     //document.getElementById('TB_data').style.display="none";
     
	
	
}


function partial()
{
	
	xmlhttp=getxmlhttpObject();
	var par_gpfno=document.getElementById("txtEmpId1").value;
	if(par_gpfno==" ")
	{
		alert("Enter GPF No");
	}
	else
	{
	var url="../../../../../GPF_Impound.view?command=partial&gpf_no="+par_gpfno;
	 url=url+"&sid="+Math.random();
	  //alert(url);
     xmlhttp.open("GET",url,true);
     xmlhttp.onreadystatechange=stateChanged;
     xmlhttp.send(null);  
	}     
	
}
