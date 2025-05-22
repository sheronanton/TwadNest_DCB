var winemp;
var my_window;



function togetFocus()
{
   document.frmEmployeeProfile.Employee_Initial.focus();
}
function servicepopup()
{

    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,600);
       winemp.moveTo(200,200); 
       winemp.focus();
       return ;
    }
    else
    {
        winemp=null
    }
   
    winemp= window.open("../../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","Employee_Search_for_Emp_Editing","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{

document.frmEmployeeProfile.txtGpf.value=emp;
closeWind();
callServer('Existg','null');
}
//this is the function to close the employee popup windows...
window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
if (my_window && my_window.open && !my_window.closed) my_window.close();

}
function closeWind(){
	if (winemp && winemp.open && !winemp.closed) winemp.close();
	if (my_window && my_window.open && !my_window.closed) my_window.close();
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
 function Exit()
 {
    window.open('','_parent','');
    window.close();
 }

 function StrToAscii(str){
	 var cp1="";
	 for (i=0;i < str.length; i++) {
		 cp1 += str.charCodeAt(i) + ',';
	 }
  cp1 = cp1.substr(0, cp1.length - 1);
  return cp1;
 }

function stateChanged(req)
{
    var flag,command,response;
    if( req.readyState==4)
    {
       if( req.status==200)
       {
            response= req.responseXML.getElementsByTagName("response")[0];
            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(command=="Validate"){
        	if(flag=='success')
        	{
        	   alert("Validated successfully");
        	   document.getElementById("validateTXT").value="1";
        	   document.getElementById("draft").value="Generate Final Report";
        	   document.frmEmployeeProfile.validate.disabled=true;
        	   document.frmEmployeeProfile.draft.disabled=false;
        	
        	   
        	  
        	   /*document.getElementById('txtAdditionAuthFlagYes').readOnly=true;
        	  	document.getElementById('txtAdditionAuthFlagNo').readOnly=true;
        	  	document.getElementById('txtMissCreditContentYes').readOnly=true;
        	  	document.getElementById('txtMissCreditContentNo').readOnly=true;*/
        	  	document.frmEmployeeProfile[0].txtAdditionAuthFlag[0].disabled=true;
        	  	document.frmEmployeeProfile[0].txtAdditionAuthFlag[1].disabled=true;
        	  	document.frmEmployeeProfile[0].txtMissCreditContent[0].disabled=true;
        	  	document.frmEmployeeProfile[0].txtMissCreditContent[1].disabled=true;
        	}
        	  else
              {
                  alert("Not Validated");
              }
          }
       }
    }
           
}
	    
 function clearAllit()
 {
	 var dat=new Date();
	document.frmEmployeeProfile.txtGpf.value="";
	document.frmEmployeeProfile.txtEmpId1.value="";
	document.frmEmployeeProfile.EmpId.value="";
	document.frmEmployeeProfile.gpf.value="";
	document.frmEmployeeProfile.txtEmployee.value="";
	document.frmEmployeeProfile.txtEmployeeDes.value="";
    document.frmEmployeeProfile.validate.disabled=true;
    document.frmEmployeeProfile.draft.disabled=false;
    document.getElementById("finyear").innerHTML="";
	document.getElementById("txt1GpfReg").innerHTML="";
	document.getElementById("txt2GpfReg").innerHTML="";
	document.getElementById("txt3GpfReg").innerHTML="";
	document.getElementById("txt4GpfReg").innerHTML="";
	document.getElementById("txt5GpfReg").innerHTML="";
	document.getElementById("txt6GpfReg").innerHTML="";
	document.getElementById("txt7GpfReg").innerHTML="";
	document.getElementById("txt8GpfReg").innerHTML="";
	document.getElementById("txt9GpfReg").innerHTML="";
	document.getElementById("txt10GpfReg").innerHTML="";
	document.getElementById("txt1GpfIm").innerHTML="";
	document.getElementById("txt2GpfIm").innerHTML="";
	document.getElementById("txt3GpfIm").innerHTML="";
	document.getElementById("txt4GpfIm").innerHTML="";
	document.getElementById("txt5GpfIm").innerHTML="";
	document.getElementById("txt6GpfIm").innerHTML="";
	document.getElementById("txt7GpfIm").innerHTML="";
	document.getElementById("txt8GpfIm").innerHTML="";
	document.getElementById("txt9GpfIm").innerHTML="";
	document.getElementById("txt10GpfIm").innerHTML="";
	document.getElementById("txtadd").innerHTML="";
	document.getElementById("txtadd1").innerHTML="";
	document.getElementById("txtSubr").innerHTML="";
	document.getElementById("txtSubr1").innerHTML="";	
	document.getElementById("spanrelievaldate").innerHTML="";
	document.getElementById("interestdate").innerHTML="";
	document.getElementById("spanrelieval").innerHTML="";
	document.getElementById("spanFin").innerHTML="";
	document.getElementById("spanFrom").innerHTML="";
	document.getElementById("spanTo1").innerHTML="";
	document.getElementById("spanTo2").innerHTML="";
	document.getElementById("spanTo3").innerHTML="";
	document.getElementById("span33To3").innerHTML="";
	document.getElementById("span11To1").innerHTML="";
	document.getElementById('notice').length = 1;
	document.getElementById('c1').innerHTML="";
	document.getElementById("interestamount").innerHTML="";
	document.getElementById("interest_amt_imp").innerHTML="";
	document.getElementById("draft").value="Generate Draft";
	document.frmEmployeeProfile.txtSubject.value="";
	   document.frmEmployeeProfile.txtRef.value="";
	   document.frmEmployeeProfile.txtbills.value="";
	   document.frmEmployeeProfile.txtbills1.value="";
	   document.frmEmployeeProfile.txtbills2.value="";
	   document.frmEmployeeProfile.txtCopy.value="";    			 
	//   document.frmEmployeeProfile.txtpno.value="";    			   
	   document.frmEmployeeProfile.txtref.value="";    			  
	   document.frmEmployeeProfile.prefix.value="";
	   document.frmEmployeeProfile.txtPO.value="";
	   document.frmEmployeeProfile.suffix.value="";
	   document.frmEmployeeProfile.txtPODesig.value="";
	   
	   
	   
	   document.getElementById("txtSubfirst").innerHTML="";
		document.getElementById("txtSubfirst1").innerHTML="";
		document.getElementById("txtSubfirstt").innerHTML="";
		document.getElementById("txtSubfirstt1").innerHTML="";
	   
	   
	   
	   //alert(document.frmEmployeeProfile.txtSubject.value);
	   var dd=dat.getDate();dd+="'";
	   if(dd.length==2){
		   dd="0"+dat.getDate();
	   }
	   else {
		   dd=dat.getDate();
	   }
	  // document.frmEmployeeProfile.txtPDat.value=dd+"/"+(dat.getMonth()+1)+"/"+dat.getFullYear();
	   document.getElementById('txtref').readOnly=false;
	//   document.getElementById('txtGpf').readOnly=false;
	   document.getElementById('txtSubject').readOnly=false;
	   document.getElementById('txtRef').readOnly=false;
	   document.getElementById('txtCopy').readOnly=false;
	   document.getElementById('prefix').readOnly=false;
	   document.getElementById('txtPO').readOnly=false;
	   document.getElementById('suffix').readOnly=false;
	   document.getElementById('txtPODesig').readOnly=false;
	   document.getElementById('txtPDat').readOnly=false;
	   
	   document.getElementById("txtNew11GpfReg").innerHTML="";
		document.getElementById("txtNew12GpfReg").innerHTML="";
		document.getElementById("txtNew13GpfReg").innerHTML="";
		document.getElementById("txtNew14GpfReg").innerHTML="";
		document.getElementById("txtNew11GpfIm").innerHTML="";		
		document.getElementById("txtNew12GpfIm").innerHTML="";		
		document.getElementById("txtNew13GpfIm").innerHTML="";		
		document.getElementById("txtNew14GpfIm").innerHTML="";
	
 }
 function clearHTML(){
	 document.getElementById("finyear").innerHTML="";
	 document.getElementById("validateTXT").value="";
		document.getElementById("txt1GpfReg").innerHTML="";
		document.getElementById("txt2GpfReg").innerHTML="";
		document.getElementById("txt3GpfReg").innerHTML="";
		document.getElementById("txt4GpfReg").innerHTML="";
		document.getElementById("txt5GpfReg").innerHTML="";
		document.getElementById("txt6GpfReg").innerHTML="";
		document.getElementById("txt7GpfReg").innerHTML="";
		document.getElementById("txt8GpfReg").innerHTML="";
		document.getElementById("txt9GpfReg").innerHTML="";
		document.getElementById("txt10GpfReg").innerHTML="";
		document.getElementById("txt1GpfIm").innerHTML="";
		document.getElementById("txt2GpfIm").innerHTML="";
		document.getElementById("txt3GpfIm").innerHTML="";
		document.getElementById("txt4GpfIm").innerHTML="";
		document.getElementById("txt5GpfIm").innerHTML="";
		document.getElementById("txt6GpfIm").innerHTML="";
		document.getElementById("txt7GpfIm").innerHTML="";
		document.getElementById("txt8GpfIm").innerHTML="";
		document.getElementById("txt9GpfIm").innerHTML="";
		document.getElementById("txt10GpfIm").innerHTML="";
		document.getElementById("txtadd").innerHTML="";
		document.getElementById("txtadd1").innerHTML="";
		document.getElementById("txtSubr").innerHTML="";
		document.getElementById("txtSubr1").innerHTML="";	
		document.getElementById("spanrelievaldate").innerHTML="";
		document.getElementById("interestdate").innerHTML="";
		document.getElementById("spanrelieval").innerHTML="";
		document.getElementById("spanFin").innerHTML="";
		document.getElementById("spanFrom").innerHTML="";
		document.getElementById("spanTo1").innerHTML="";
		document.getElementById("spanTo2").innerHTML="";
		document.getElementById("spanTo3").innerHTML="";
		document.getElementById("span33To3").innerHTML="";
		document.getElementById("span11To1").innerHTML="";
		document.getElementById("interestamount").innerHTML="";
		document.getElementById("interest_amt_imp").innerHTML="";
		document.getElementById("c1").innerHTML="";
		document.getElementById("draft").value="Generate Draft";
		clearHTML12();
		document.frmEmployeeProfile.validate.disabled=true;
		document.frmEmployeeProfile.draft.disabled=false;
		document.getElementById("txtNew11GpfReg").innerHTML="";
		document.getElementById("txtNew12GpfReg").innerHTML="";
		document.getElementById("txtNew13GpfReg").innerHTML="";
		document.getElementById("txtNew14GpfReg").innerHTML="";
		document.getElementById("txtNew11GpfIm").innerHTML="";		
		document.getElementById("txtNew12GpfIm").innerHTML="";		
		document.getElementById("txtNew13GpfIm").innerHTML="";		
		document.getElementById("txtNew14GpfIm").innerHTML="";
		
		
		document.getElementById("txtSubfirst").innerHTML="";
		document.getElementById("txtSubfirst1").innerHTML="";
		document.getElementById("txtSubfirstt").innerHTML="";
		document.getElementById("txtSubfirstt1").innerHTML="";
		
		
 }
 function clearHTML12(){
	 var dat=new Date();
	 document.frmEmployeeProfile.draft.disabled=false;
	   document.frmEmployeeProfile.txtRef.value="";
	   document.frmEmployeeProfile.txtbills.value="";
	   document.frmEmployeeProfile.txtbills1.value="";
	   document.frmEmployeeProfile.txtbills2.value="";
	   document.frmEmployeeProfile.txtCopy.value="";    			 
	//   document.frmEmployeeProfile.txtpno.value="";    			   
	   document.frmEmployeeProfile.txtref.value="";    			  
	   document.frmEmployeeProfile.prefix.value="";
	   document.frmEmployeeProfile.txtPO.value="";
	   document.frmEmployeeProfile.suffix.value="";
	   document.frmEmployeeProfile.txtPODesig.value="";
	   	 document.getElementById("txtAdditionAuthFlagYes").checked=true;	     
	     document.getElementById("txtMissCreditContentNo").checked=true;
	     
	   //alert(document.frmEmployeeProfile.txtSubject.value);
	   var dd=dat.getDate();dd+="'";
	   if(dd.length==2){ 
		   dd="0"+dat.getDate();
	   }
	   else {
		   dd=dat.getDate();
	   }
	  // document.frmEmployeeProfile.txtPDat.value=dd+"/"+(dat.getMonth()+1)+"/"+dat.getFullYear();
	   document.getElementById('txtref').readOnly=false;
	//   document.getElementById('txtGpf').readOnly=false;
	   document.getElementById('txtSubject').readOnly=false;
	   document.getElementById('txtRef').readOnly=false;
	   document.getElementById('txtCopy').readOnly=false;
	   document.getElementById('prefix').readOnly=false;
	   document.getElementById('txtPO').readOnly=false;
	   document.getElementById('suffix').readOnly=false;
	   document.getElementById('txtPODesig').readOnly=false;
	   document.getElementById('txtPDat').readOnly=false;
 }
 function nullCheck()
 {
	 if(document.frmEmployeeProfile.txtGpf.value=="")
	    {
	       alert("Please Enter the GPF No");
	       document.frmEmployeeProfile.txtGpf.focus();
	       return false;
	    }
	 else if(document.frmEmployeeProfile.notice.options[0].selected){
		   alert("Select the Authorisation Note Serial No");
	       document.frmEmployeeProfile.notice.focus();
	       return false;
	 }
	 return true;
 }
 function bodyload()
 {
	 document.frmEmployeeProfile.Employee_Initial.focus();
 }

	function fillGpfData2(response){
		
		var txtFinYear=response.getElementsByTagName("finance_year")[0].firstChild.nodeValue;	
		var txtCurrentYear=response.getElementsByTagName("current_opening_year")[0].firstChild.nodeValue;
		var txt1GpfReg=response.getElementsByTagName("cb_ac_re")[0].firstChild.nodeValue;	
		var txt2GpfReg=response.getElementsByTagName("subseq_deposit_re")[0].firstChild.nodeValue;
		var txt3GpfReg=response.getElementsByTagName("int_amount_re")[0].firstChild.nodeValue;
		var txt4GpfReg=response.getElementsByTagName("dep_amount_re")[0].firstChild.nodeValue;
		var txt5GpfReg=response.getElementsByTagName("dep_interest_re")[0].firstChild.nodeValue;
		var txt6GpfReg=response.getElementsByTagName("subseq_withdraw_re")[0].firstChild.nodeValue;
		var txt7GpfReg=response.getElementsByTagName("withdraw_amount_re")[0].firstChild.nodeValue;
		var txt8GpfReg=response.getElementsByTagName("withdraw_interest_re")[0].firstChild.nodeValue;
		var txt9GpfReg=response.getElementsByTagName("amount_already_re")[0].firstChild.nodeValue;
		var txt10GpfReg=response.getElementsByTagName("reg_amt_bal_auth")[0].firstChild.nodeValue;
		var txt1GpfIm=response.getElementsByTagName("cb_ac_im")[0].firstChild.nodeValue;
		var txt2GpfIm=response.getElementsByTagName("subseq_deposit_im")[0].firstChild.nodeValue;
		var txt3GpfIm=response.getElementsByTagName("int_amount_im")[0].firstChild.nodeValue;
		var txt4GpfIm=response.getElementsByTagName("dep_amount_im")[0].firstChild.nodeValue;
		var txt5GpfIm=response.getElementsByTagName("dep_interest_im")[0].firstChild.nodeValue;
		var txt6GpfIm=response.getElementsByTagName("subseq_withdraw_im")[0].firstChild.nodeValue;
		var txt7GpfIm=response.getElementsByTagName("withdraw_amount_im")[0].firstChild.nodeValue;
		var txt8GpfIm=response.getElementsByTagName("withdraw_interest_im")[0].firstChild.nodeValue;
		var txt9GpfIm=response.getElementsByTagName("amount_already_im")[0].firstChild.nodeValue;	
		var txt10GpfIm=response.getElementsByTagName("imp_amt_bal_auth")[0].firstChild.nodeValue;
		
		var txtNew11GpfReg=response.getElementsByTagName("REG_EXCESS_DB")[0].firstChild.nodeValue;
		var txtNew12GpfReg=response.getElementsByTagName("REG_INT_EXCESS_DB")[0].firstChild.nodeValue;
		var txtNew13GpfReg=response.getElementsByTagName("REG_MISSING_DB")[0].firstChild.nodeValue;
		var txtNew14GpfReg=response.getElementsByTagName("REG_INT_MISSING_DB")[0].firstChild.nodeValue;
		var txtNew11GpfIm=response.getElementsByTagName("IMP_EXCESS_DB")[0].firstChild.nodeValue;
		var txtNew12GpfIm=response.getElementsByTagName("IMP_INT_EXCESS_DB")[0].firstChild.nodeValue;
		var txtNew13GpfIm=response.getElementsByTagName("IMP_MISSING_DB")[0].firstChild.nodeValue;
		var txtNew14GpfIm=response.getElementsByTagName("IMP_INT_MISSING_DB")[0].firstChild.nodeValue;
		
		document.getElementById("finyear").innerHTML=txtFinYear;
		document.getElementById("c1").innerHTML=txtFinYear;
		document.getElementById("txt1GpfReg").innerHTML=txt1GpfReg;
		document.getElementById("txt2GpfReg").innerHTML=txt2GpfReg;
		document.getElementById("txt3GpfReg").innerHTML=txt3GpfReg;
		document.getElementById("txt4GpfReg").innerHTML=txt4GpfReg;
		document.getElementById("txt5GpfReg").innerHTML=txt5GpfReg;
		document.getElementById("txt6GpfReg").innerHTML=txt6GpfReg;
		document.getElementById("txt7GpfReg").innerHTML=txt7GpfReg;
		document.getElementById("txt8GpfReg").innerHTML=txt8GpfReg;
		document.getElementById("txt9GpfReg").innerHTML=txt9GpfReg;
		document.getElementById("txt10GpfReg").innerHTML=txt10GpfReg;
		document.getElementById("txtNew11GpfReg").innerHTML=txtNew11GpfReg;
		document.getElementById("txtNew12GpfReg").innerHTML=txtNew12GpfReg;
		document.getElementById("txtNew13GpfReg").innerHTML=txtNew13GpfReg;
		document.getElementById("txtNew14GpfReg").innerHTML=txtNew14GpfReg;
		
		/*var gpfRegAdd=parseInt(txt1GpfReg)+parseInt(txt2GpfReg)+parseInt(txt3GpfReg)+parseInt(txt4GpfReg)+parseInt(txt5GpfReg)+parseInt(txtNew11GpfReg)+parseInt(txtNew12GpfReg);
		var gpfRegSub=parseInt(txt6GpfReg)+parseInt(txt7GpfReg)+parseInt(txt8GpfReg)+parseInt(txt9GpfReg)+parseInt(txtNew13GpfReg)+parseInt(txtNew14GpfReg);
		var gpfImAdd=parseInt(txt1GpfIm)+parseInt(txt2GpfIm)+parseInt(txt3GpfIm)+parseInt(txt4GpfIm)+parseInt(txt5GpfIm)+parseInt(txtNew11GpfIm)+parseInt(txtNew12GpfIm);
		var gpfImSub=parseInt(txt6GpfIm)+parseInt(txt7GpfIm)+parseInt(txt8GpfIm)+parseInt(txt9GpfIm)+parseInt(txtNew13GpfIm)+parseInt(txtNew14GpfIm);
		document.getElementById("txtadd").innerHTML=gpfRegAdd;
		document.getElementById("txtadd1").innerHTML=gpfImAdd;
		document.getElementById("txtSubr").innerHTML=gpfRegSub;
		document.getElementById("txtSubr1").innerHTML=gpfImSub;*/
		
		
		var gpfRegAdd=parseInt(txt1GpfReg)+parseInt(txt4GpfReg)+parseInt(txt5GpfReg)+parseInt(txtNew11GpfReg)+parseInt(txtNew12GpfReg);
		var gpfRegSub=parseInt(gpfRegAdd)-(parseInt(txt7GpfReg)+parseInt(txt8GpfReg)+parseInt(txtNew13GpfReg)+parseInt(txtNew14GpfReg));
		var gpfImAdd=parseInt(txt1GpfIm)+parseInt(txt4GpfIm)+parseInt(txt5GpfIm)+parseInt(txtNew11GpfIm)+parseInt(txtNew12GpfIm);
		var gpfImSub=parseInt(gpfImAdd)-(parseInt(txt7GpfIm)+parseInt(txt8GpfIm)+parseInt(txtNew13GpfIm)+parseInt(txtNew14GpfIm));
		document.getElementById("txtadd").innerHTML=gpfRegAdd;
		document.getElementById("txtadd1").innerHTML=gpfImAdd;
		document.getElementById("txtSubr").innerHTML=gpfRegSub;
		document.getElementById("txtSubr1").innerHTML=gpfImSub;	
		
		
		document.getElementById("txt1GpfIm").innerHTML=txt1GpfIm;
		document.getElementById("txt2GpfIm").innerHTML=txt2GpfIm;
		document.getElementById("txt3GpfIm").innerHTML=txt3GpfIm;
		document.getElementById("txt4GpfIm").innerHTML=txt4GpfIm;
		document.getElementById("txt5GpfIm").innerHTML=txt5GpfIm;
		document.getElementById("txt6GpfIm").innerHTML=txt6GpfIm;
		document.getElementById("txt7GpfIm").innerHTML=txt7GpfIm;
		document.getElementById("txt8GpfIm").innerHTML=txt8GpfIm;
		document.getElementById("txt9GpfIm").innerHTML=txt9GpfIm;
		document.getElementById("txt10GpfIm").innerHTML=txt10GpfIm;				
		
		document.getElementById("txtNew11GpfIm").innerHTML=txtNew11GpfIm;		
		document.getElementById("txtNew12GpfIm").innerHTML=txtNew12GpfIm;		
		document.getElementById("txtNew13GpfIm").innerHTML=txtNew13GpfIm;		
		document.getElementById("txtNew14GpfIm").innerHTML=txtNew14GpfIm;	
		
		
		
		/*var gpfsubtotfirst=parseInt(txt2GpfReg)+parseInt(txt3GpfReg);
		var gpfsubtotfirst1=parseInt(txt2GpfIm)+parseInt(txt3GpfIm);
		
		var gpfsubtotfirstt=parseInt(txt6GpfReg)+parseInt(txt9GpfReg);
		var gpfsubtotfirstt1=parseInt(txt6GpfIm)+parseInt(txt9GpfIm);*/
		
		
		
		
		var gpfsubtotfirst=parseInt(gpfRegSub)+parseInt(txt2GpfReg)+parseInt(txt3GpfReg);
		var gpfsubtotfirst1=parseInt(gpfImSub)+parseInt(txt2GpfIm)+parseInt(txt3GpfIm);
		
		var gpfsubtotfirstt=parseInt(txt6GpfReg)+parseInt(txt9GpfReg);
		var gpfsubtotfirstt1=parseInt(txt6GpfIm)+parseInt(txt9GpfIm);
		
		
		document.getElementById("txtSubfirst").innerHTML=gpfsubtotfirst;
		document.getElementById("txtSubfirst1").innerHTML=gpfsubtotfirst1;
		document.getElementById("txtSubfirstt").innerHTML=gpfsubtotfirstt;
		document.getElementById("txtSubfirstt1").innerHTML=gpfsubtotfirstt1;
		
		
    }
   

	function fillGpfData3(response){
		var date2=response.getElementsByTagName("relieve_date")[0].firstChild.nodeValue;
		var radRlv=response.getElementsByTagName("relieve_status")[0].firstChild.nodeValue;
		var SLIP_ISSUED_FIN_YEAR=response.getElementsByTagName("SLIP_ISSUED_FIN_YEAR")[0].firstChild.nodeValue;	
		var txtCurrentYear=response.getElementsByTagName("current_opening_year")[0].firstChild.nodeValue;
		//var date3=response.getElementsByTagName("int_tobe_calc_date")[0].firstChild.nodeValue;
		var date3=response.getElementsByTagName("to_date")[0].firstChild.nodeValue;
		var interestamount=response.getElementsByTagName("interest_amt_reg")[0].firstChild.nodeValue;
		var interest_amt_imp=response.getElementsByTagName("interest_amt_imp")[0].firstChild.nodeValue;
		if(date3=="null"){
			date3="";
        }
		if(date2=="null"){
			date2="";
        }
		
		
		
		document.getElementById("interest_amt_imp").innerHTML=interest_amt_imp;
		document.getElementById("interestamount").innerHTML=interestamount;
		document.getElementById("spanrelievaldate").innerHTML=date2;
		document.getElementById("interestdate").innerHTML=date3;
		var retiredContent="retired";
		if(radRlv=='SAN'){
			document.getElementById("spanrelieval").innerHTML="Super-Annuation";
			retiredContent="retired";
		}
		else if(radRlv=='VRS'){
			document.getElementById("spanrelieval").innerHTML="VRS";
			retiredContent="Voluntary Retirement";
		}
		else if(radRlv=='DTH'){
			document.getElementById("spanrelieval").innerHTML="Death";
			retiredContent="Death";
		}		
		
		//alert(SLIP_ISSUED_FIN_YEAR);
		var lastslipyearnew=SLIP_ISSUED_FIN_YEAR.split('-')
		var lastslipyearnewval='31'+'/'+'03'+'/'+lastslipyearnew[1];
		//alert(lastslipyearnewval);
		
		document.getElementById("spanFin").innerHTML=SLIP_ISSUED_FIN_YEAR;
		document.getElementById("spanFrom").innerHTML=response.getElementsByTagName("from_date")[0].firstChild.nodeValue;
		/*document.getElementById("spanTo1").innerHTML=date3;
		document.getElementById("spanTo2").innerHTML=date3;
		document.getElementById("spanTo3").innerHTML=date3;
		document.getElementById("span33To3").innerHTML=date3;
		document.getElementById("span11To1").innerHTML=date3;*/
		
		
		document.getElementById("spanTo2").innerHTML=date3;
		document.getElementById("spanTo1").innerHTML=lastslipyearnewval;		
		document.getElementById("spanTo3").innerHTML=lastslipyearnewval;
		document.getElementById("span33To3").innerHTML=lastslipyearnewval;
		document.getElementById("span11To1").innerHTML=lastslipyearnewval;
		
		var ff=document.frmEmployeeProfile.txtGpf.value;
        if(ff.length>3){
      	if(ff.substring(0,1)=="0"){
      		ff=ff.substring(1,ff.length);
      	}
        }
        var empName=document.frmEmployeeProfile.txtEmployee.value;
        var des=document.frmEmployeeProfile.txtEmployeeDes.value; 
        
        
        var subjectContent="GPF-Authorisation for the interest payment of GPF(Regular) and GPF(impounded) Account in respect of Thiru/Tmt. "+empName+","+des+"- GPF Account No."+ff+" TWAD - "+retiredContent+" on "+date2+" interest payment Authorise - reg.";	
        document.frmEmployeeProfile.txtSubject.value=subjectContent;	
       
       
		
		
	}
 
 function btn11(){
	// alert("Hi");
	  var winemp;
	  winemp= window.open("GPF_Proceedings_PaymentOfInterest.jsp","win","status=1,resizable=YES,scrollbars=yes");
	  winemp.focus();
	  self.close();
	 }
function callServer(command,param)
{
      
	//alert(command);
	if(command=="Existg")
       {
       var strEmpId=document.frmEmployeeProfile.txtGpf.value;
       var len=strEmpId.length;      
       if(len==4)
            strEmpId='0'+strEmpId;
       if(len==3)     
            strEmpId='00'+strEmpId;
        if(len==2)     
            strEmpId='000'+strEmpId; 
        if(len==1)     
            strEmpId='0000'+strEmpId;     
        document.frmEmployeeProfile.gpf.value=strEmpId ;
        
       // alert(yer);
        url="../../../../../../Create_GPF_Proceeding.av?Command=Existg&EmpGPF=" + strEmpId;
           var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);              
            }   
            req.send(null);        
       }      
      else if(command=="GetDetails"){
    	  //alert("not workingdddddd");
    	  if(document.frmEmployeeProfile.notice.options[0].selected){
    		 // alert("clear HTML working");
    		  clearHTML();
    		 
  			document.frmEmployeeProfile.notice.focus();
         return false;
  	 }
    	  //alert("calling");
     var auth=document.frmEmployeeProfile.notice.value;    
     var gpf=document.frmEmployeeProfile.txtGpf.value;  
     var yer=document.frmEmployeeProfile.per1.value;
     //alert(yer);
     url="../../../../../../Create_GPF_Proceeding.av?Command=GetDetails&auth="+auth+"&gpf="+gpf;        
     var req=getTransport();
     req.open("GET",url,true); 
     req.onreadystatechange=function()
     {           
        processResponse(req);         
     }   
     req.send(null);
 }
}
function validate12(){
	//alert("Val ");
	 if(nullCheck())
    {
		 
//		 if(document.frmEmployeeProfile.fwd_desig.value=="")
//		    {
//		       alert("Please Enter the Forwarding Officer Designation");
//		       
//		       return false;
//		    }
//		 else if(document.frmEmployeeProfile.fwd_place.value=="")
//		    {
//		       alert("Please Enter the Forwarding Officer Place");
//		       
//		       return false;
//		    }
		 
		 var auth=document.frmEmployeeProfile.notice.value;    
		 var gpf_no=document.frmEmployeeProfile.txtGpf.value;
		 var txtPDat=document.frmEmployeeProfile.txtPDat.value;
		 var txtAdditionAuthFlag="No";
		 var txtMissCreditContent="No";
		 
		 
		// var fwd_desig=document.frmEmployeeProfile.fwd_desig.value;
		// var fwd_place=document.frmEmployeeProfile.fwd_place.value;
		 
			 if(document.getElementById("txtAdditionAuthFlagYes").checked)
			 {
				 txtAdditionAuthFlag="Yes";
			 }
			 if(document.getElementById("txtMissCreditContentYes").checked)
			 {
				 txtMissCreditContent="Yes";
			 }
		 url="../../../../../../Create_GPF_Proceeding.av?Command=Validate&txtGpf="+gpf_no+"&auth="+auth+"&txtPDat="+txtPDat;
		 url+="&txtAdditionAuthFlag="+txtAdditionAuthFlag;
		 url+="&txtMissCreditContent="+txtMissCreditContent;
		 var req=getTransport();
		// alert(url);
		 req.open("GET",url,true);
		 //alert("up12");
		 req.onreadystatechange=function()
		 {
		 	//alert("up12");
		 stateChanged(req);
		 }
		 if(window.XMLHttpRequest)
		              req.send(null);
		      else req.send();
		 
    }
}

function processResponse(req)
    {   
      if(req.readyState==4)
        {
          if(req.status==200)
          {       
          //stopwaiting(document.frmEmployeeProfile) ;
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
            
              if(command=="Existg")
              {
            	  clearHTML();
                  existRow(baseResponse);  
                 
              }
              else if(command=="GetDetails"){
            	  clearHTML();
            	  var auth=baseResponse.getElementsByTagName("auth")[0].firstChild.nodeValue;	
            	  if(auth=="success"){
            	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;	            	              	
            		fillGpfData2(baseResponse);
            		fillGpfData3(baseResponse);  
            		  if(flag=="success"){
            			  Procc(baseResponse);
            	  }
            		  else{
            			  clearHTML12();
            		  }
            	  }
            	  else if(auth=="NOT_VALIDATED"){
            		  alert("Authorisation Note Details Not Validated");
            		  document.frmEmployeeProfile.draft.disabled=true;
            		  
            	  }
            	  else{
            		 alert("Authorisation Note Details Not Generated");
            		 document.frmEmployeeProfile.draft.disabled=true;
            		
            		 
            	  }
            	 
              }
              else if(command=="sessionout")
              {
                alert('Session is closed');
                try{
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                }catch(e){
                	
                }
                self.close();
                return;
             }
          }
        }
  }
function Procc(response){
	

	
	var data_qry=response.getElementsByTagName("data_qry")[0].firstChild.nodeValue;
	

	
	
    
   // document.frmEmployeeProfile.txtbills1.value=response.getElementsByTagName("BILLS1")[0].firstChild.nodeValue;
    
    var ref_no=response.getElementsByTagName("REFERENCE")[0].firstChild.nodeValue;
   
    	 if((ref_no=="null") || (ref_no=="NIL") ||(ref_no=="")){
        	 document.frmEmployeeProfile.txtRef.value="";
           }
           else
           {
          
        	   document.frmEmployeeProfile.txtRef.value=ref_no; 
           }
  //  document.frmEmployeeProfile.txtbills2.value=response.getElementsByTagName("BILLS2")[0].firstChild.nodeValue;
    
    document.frmEmployeeProfile.txtEmpId1.value=document.frmEmployeeProfile.txtGpf.value;
    document.frmEmployeeProfile.EmpId.value=document.frmEmployeeProfile.txtEmpId1.value;
    

	// var fwd_desg=response.getElementsByTagName("FWD_OFFICER_DESG")[0].firstChild.nodeValue;
    // var fwd_place=response.getElementsByTagName("FWD_OFFICER_PLACE")[0].firstChild.nodeValue;

    
var bills1=response.getElementsByTagName("BILLS1")[0].firstChild.nodeValue;
    
    if((bills1=="null")||(bills1=="")||(bills1=="NIL")){
    	document.frmEmployeeProfile.txtbills1.value="";
    }
    else
    {
    	document.frmEmployeeProfile.txtbills1.value=bills1;
    }
   
   
    if(data_qry=="PTD")
    {
    	
    	var prdate=response.getElementsByTagName("PROC_DATE")[0].firstChild.nodeValue; 
     	
     	
     	
    	document.frmEmployeeProfile.txtSubject.value=response.getElementsByTagName("SUBJECT")[0].firstChild.nodeValue; 
    	document.frmEmployeeProfile.txtPDat.value= prdate;
    }
    else  if(data_qry=="MTD")
    {
    	
    	
    	var dat=new Date();

    	 var dd=dat.getDate();dd+="'";
  	   if(dd.length==2){
  		   dd="0"+dat.getDate();
  	   }
  	   else {
  		   dd=dat.getDate();
  	   }
  	   document.frmEmployeeProfile.txtPDat.value=dd+"/"+(dat.getMonth()+1)+"/"+dat.getFullYear();
    	
    	}
     


	 var copyto=response.getElementsByTagName("COPY_TO")[0].firstChild.nodeValue;
   
    if((copyto=="null")||(copyto=="")||(copyto=="NIL")){
   	 var empName=document.frmEmployeeProfile.txtEmployee.value;
   	 document.frmEmployeeProfile.txtCopy.value=empName+",";
      }
    else
    {document.frmEmployeeProfile.txtCopy.value=copyto; 
    }
     
     var pref=response.getElementsByTagName("PREFIX")[0].firstChild.nodeValue;       
     if((pref=="null")||(pref=="")||(pref=="NIL")){
    	 document.frmEmployeeProfile.prefix.value="";
     }
     else
     {
     document.frmEmployeeProfile.prefix.value=pref;
     }
     

     
     var suf=response.getElementsByTagName("SUFFIX")[0].firstChild.nodeValue;

    
    
     if((suf=="null") || (suf=="")|| (suf=="NIL")){
    	 document.frmEmployeeProfile.suffix.value="";
     }
     else
     {
    
     document.frmEmployeeProfile.suffix.value=suf; 
     }
    
  
      
    	   
     
       
    
     
     
//     if((fwd_desg=="null")||(fwd_desg=="")||(fwd_desg=="NIL")){
//    	 document.frmEmployeeProfile.fwd_desig.value="";
//       }
//     else
//     {
//    	 document.frmEmployeeProfile.fwd_desig.value=fwd_desg;
//     }
//       
//       
//     if((fwd_place=="null")||(fwd_place=="")||(fwd_place=="NIL")){
//    	   document.frmEmployeeProfile.fwd_place.value="";
//         }
//       else
//       {
//    	   document.frmEmployeeProfile.fwd_place.value=fwd_place;
//       }
         
        
     	//document.frmEmployeeProfile.fwd_desig.value=fwd_desg;
      	//document.frmEmployeeProfile.fwd_place.value=fwd_place;
     var pro_no=response.getElementsByTagName("PROC_REFE_NO")[0].firstChild.nodeValue;
 	var prdate=response.getElementsByTagName("PROC_DATE")[0].firstChild.nodeValue; 
 	
 	
 
 	 if((pro_no=="null") || (pro_no=="NIL")){
    	 document.frmEmployeeProfile.txtref.value="";
       }
       else
       {
    	  
    	   document.frmEmployeeProfile.txtref.value=pro_no; 
       }
 	

   
    var	proc_off=nullcheck(response.getElementsByTagName("PROC_OFFICER")[0].firstChild.nodeValue); 
    
    if((proc_off=="null")||(proc_off=="")||(proc_off=="NIL")){
    	document.frmEmployeeProfile.txtPO.value="";
    }
    else
    {
    	document.frmEmployeeProfile.txtPO.value=proc_off;
    }
 	
  var	proc_des=nullcheck(response.getElementsByTagName("PROC_OFFICE_DESIGNATION")[0].firstChild.nodeValue); 
    
    if((proc_des=="null")||(proc_des=="")||(proc_des=="NIL")){
    	document.frmEmployeeProfile.txtPODesig.value="";
    }
    else
    {
    	document.frmEmployeeProfile.txtPODesig.value=proc_des;
    } 

var bills=response.getElementsByTagName("BILLS")[0].firstChild.nodeValue;
    
    if((bills=="null")||(bills=="")||(bills=="NIL")){
    	document.frmEmployeeProfile.txtbills.value="";
    }
    else
    {
    	document.frmEmployeeProfile.txtbills.value=bills;
    }
    
    

    
    
var bills2=response.getElementsByTagName("BILLS2")[0].firstChild.nodeValue;
    
    if((bills2=="null")||(bills2=="")||(bills2=="NIL")){
    	document.frmEmployeeProfile.txtbills2.value="";
    }
    else
    {
    	document.frmEmployeeProfile.txtbills2.value=bills2;
    }	 
    var additional_auth_flag=nullcheck(response.getElementsByTagName("ADDITIONAL_AUTH_FLAG")[0].firstChild.nodeValue);
    var missing_content_flag=nullcheck(response.getElementsByTagName("MISSING_CREDIT_CONTENT")[0].firstChild.nodeValue);
   
     if(additional_auth_flag=="Yes")
     {
    	 document.getElementById("txtAdditionAuthFlagYes").checked=true;
     }
     else
     {
    	 document.getElementById("txtAdditionAuthFlagNo").checked=true;
     }
     if(missing_content_flag=="Yes")
     {
    	 document.getElementById("txtMissCreditContentYes").checked=true;
     }
     else
     {
    	 document.getElementById("txtMissCreditContentNo").checked=true;
     }
    
     var stu=response.getElementsByTagName("PROCESS_FLOW_ID")[0].firstChild.nodeValue;
     
    
     if(stu=="FR"){
    	 
    	
    	 document.getElementById("draft").value="Generate Final Report";
    	 document.frmEmployeeProfile.draft.disabled=false; 
    	
    	// var text_button="Generate Final Report";
    	// document.getElementById("draft").innerHTML=text_button;
    	// document.frmEmployeeProfile.draft.value="Generate Final Report";
     	//document.getElementById("draft").innerHTML="Generate Final Report";
     	document.getElementById("validateTXT").value="1";
  	   document.frmEmployeeProfile.validate.disabled=true;
  	   
  	   document.getElementById('txtref').readOnly=true;
   	//   document.getElementById('txtGpf').readOnly=true;
   	   document.getElementById('txtSubject').readOnly=true;
   	   document.getElementById('txtRef').readOnly=true;
   	   document.getElementById('txtCopy').readOnly=true;
   	   document.getElementById('prefix').readOnly=true;
   	   document.getElementById('txtPO').readOnly=true;
   	   document.getElementById('suffix').readOnly=true;
   	   document.getElementById('txtPODesig').readOnly=true;
   	   document.getElementById('txtPDat').readOnly=true;
   	  document.getElementById('txtbills').readOnly=true;
   	document.getElementById('txtbills1').readOnly=true;
   	document.getElementById('txtbills2').readOnly=true;
   	document.frmEmployeeProfile.txtAdditionAuthFlag[0].disabled=true;
  	document.frmEmployeeProfile.txtAdditionAuthFlag[1].disabled=true;
  	document.frmEmployeeProfile.txtMissCreditContent[0].disabled=true;
  	document.frmEmployeeProfile.txtMissCreditContent[1].disabled=true;
  //	document.frmEmployeeProfile.fwd_desig.readOnly=true;
 // 	document.frmEmployeeProfile.fwd_place.readOnly=true;
  	
   	/*document.frmEmployeeProfile.txtAdditionAuthFlag[0].disabled="disabled";
  	document.frmEmployeeProfile.txtAdditionAuthFlag[1].disabled="disabled";
  	document.frmEmployeeProfile.txtMissCreditContent[0].disabled="disabled";
  	document.frmEmployeeProfile.txtMissCreditContent[1].disabled="disabled";*/
     }
     else{
    	 
    	 
    	 document.getElementById("draft").value="Generate Draft Note";
    	 document.frmEmployeeProfile.draft.disabled=false;
    	// var text_button="Generate Draft Note";
    	// document.getElementById("draft").innerHTML=text_button;
    	// document.frmEmployeeProfile.draft.value="Generate Draft Note";
     //	document.getElementById("draft").value="Generate Draft Note";
     	document.getElementById("validateTXT").value="";
     	   document.frmEmployeeProfile.validate.disabled=false;
      	  
      	  document.getElementById('txtref').readOnly=false;
      //	   document.getElementById('txtGpf').readOnly=false;
      	   document.getElementById('txtSubject').readOnly=false;
      	   document.getElementById('txtRef').readOnly=false;
      	   document.getElementById('txtCopy').readOnly=false;
      	   document.getElementById('prefix').readOnly=false;
      	   document.getElementById('txtPO').readOnly=false;
      	   document.getElementById('suffix').readOnly=false;
      	   document.getElementById('txtPODesig').readOnly=false;
      	   document.getElementById('txtPDat').readOnly=false;
      	  document.getElementById('txtbills').readOnly=false;
      	document.getElementById('txtbills1').readOnly=false;
      	document.getElementById('txtbills2').readOnly=false;
      	
      	//document.frmEmployeeProfile.fwd_desig.readOnly=false;
      //	document.frmEmployeeProfile.fwd_place.readOnly=false;
      	document.frmEmployeeProfile.txtAdditionAuthFlag[0].disabled=false;
	  	document.frmEmployeeProfile.txtAdditionAuthFlag[1].disabled=false;
	  	document.frmEmployeeProfile.txtMissCreditContent[0].disabled=false;
	  	document.frmEmployeeProfile.txtMissCreditContent[1].disabled=false;
     }
}
function existRow(baseResponse)
{
              var flag=baseResponse.getElementsByTagName("GPFflag")[0].firstChild.nodeValue;
              if(flag=="success")
              {
                  document.frmEmployeeProfile.txtEmployee.value=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue;
                  var empName=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue;
                  var des=baseResponse.getElementsByTagName("EmpDes")[0].firstChild.nodeValue; 
                  if(des=="null")
                	  des="";
                  if(des.length==0){
                	des="";  
                  }
                  document.frmEmployeeProfile.txtEmployeeDes.value=des;
                  var gpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue;
                  var gpf1="";
                  if(gpf==0)
                    gpf="";
                  var ff=document.frmEmployeeProfile.txtGpf.value;
                  if(ff.length>3){
                	if(ff.substring(0,1)=="0"){
                		ff=ff.substring(1,ff.length);
                	}
                  }
                  document.frmEmployeeProfile.txtEmpId1.value=ff;
                  document.frmEmployeeProfile.txtGpf.value=gpf;
                  document.frmEmployeeProfile.EmpId.value=document.frmEmployeeProfile.txtEmpId1.value;
                  
                  var notice=baseResponse.getElementsByTagName("notice")[0].firstChild.nodeValue;
                  if(notice=="success")
                  {
                	  var noticeID=baseResponse.getElementsByTagName("noticeID")[0].firstChild.nodeValue;
                      document.getElementById('notice').length = 1;
                 	  var si="",jk=0,j=0;
    		    	  for ( j = 0; j < noticeID.length; ++j ) {
    		    		 for ( jk = j; jk < noticeID.length; ++jk ) {
    		    			if(noticeID.substring(jk,jk+1)!=','){
    		    				 si+=noticeID.substring(jk,jk+1);		        			 
    		    		 }else break;
    		    		 }
    		    		 var opt = document.createElement("option");
    	    			 opt.setAttribute('value',si);
    	    			 opt.innerHTML =si;
    	    			 document.forms["frmEmployeeProfile"].elements["notice"].appendChild(opt);
    		    		 si="";j=jk;
    		    	 }  
                  }
                  else{
                	  alert("Authorisation Note Details not Found");  
             		  clearAllit();
             		  document.frmEmployeeProfile.txtGpf.focus();
        		  }
              }
              else if(flag=="failure")
              {
                alert("Given GPF does not exist");
                document.frmEmployeeProfile.txtGpf.focus();
                document.frmEmployeeProfile.txtGpf.value='';
                document.frmEmployeeProfile.txtEmpId1.value='';
                document.frmEmployeeProfile.txtEmployee.value='';
                document.frmEmployeeProfile.txtEmployeeDes.value='';
              }
              else if(flag=="failurea")
              {
                alert("Given gpf is not under this controlling office");
                document.frmEmployeeProfile.txtGpf.focus();
                document.frmEmployeeProfile.txtGpf.value='';
                document.frmEmployeeProfile.txtEmpId1.value='';
                document.frmEmployeeProfile.txtEmployee.value='';
                document.frmEmployeeProfile.txtEmployeeDes.value='';
              }
              else if(flag=="failureb")
              {
                alert("User doesn't have office id");
                document.frmEmployeeProfile.txtGpf.focus();
                document.frmEmployeeProfile.txtGpf.value='';
                document.frmEmployeeProfile.txtEmpId1.value='';
                document.frmEmployeeProfile.txtEmployee.value='';
                document.frmEmployeeProfile.txtEmployeeDes.value='';
              }
              else if(flag=="failurec")
              {
                alert("Given gpf is not under this controlling office");
                document.frmEmployeeProfile.txtGpf.focus();
                document.frmEmployeeProfile.txtGpf.value='';
                document.frmEmployeeProfile.txtEmpId1.value='';
                document.frmEmployeeProfile.txtEmployee.value='';
                document.frmEmployeeProfile.txtEmployeeDes.value='';
              }
              else
             {
               alert("Given Employee Id is not exist ");
               document.frmEmployeeProfile.txtEmpId1.focus();
               document.frmEmployeeProfile.txtEmpId1.value='';
               document.frmEmployeeProfile.txtEmployee.value='';
               document.frmEmployeeProfile.txtGpf.value='';
             }
}         

function toCheck()
{
 if(!isNaN(document.frmEmployeeProfile.txtEmployee.value))
   {
      alert("Employee Name must be in Character");
      document.frmEmployeeProfile.txtEmployee.value="";
      document.frmEmployeeProfile.txtEmployee.focus();
      return false;
   }
   return true;
}  

function numbersonly1(e,t)
    {
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false; 
        }
     }
     
function toFocus()
{
 //alert("Hai");
  //var FirstField=document.frmEmployeeProfile.txtEmpId1.value;
 if((document.frmEmployeeProfile.txtEmpId1.value=="") || (document.frmEmployeeProfile.txtEmpId1.value<=0))
  {
     alert("Please Enter Employee Id First");
     document.frmEmployeeProfile.txtEmpId1.focus();
     return false;
  }
  return true;
}

function noEnter(e)
{
  // alert("Enter");
   isIE=document.all? 1:0
   keyEntry = !isIE? e.which:event.keyCode;
         // alert(keyEntry);        
   if(keyEntry=='13')
   {
	   document.frmEmployeeProfile.Cpy.value+=document.frmEmployeeProfile.txtCopy.length+",";
   }
}
function nullcheck(str)
{
	var printString="";
	if((str!=null) && (str!="null"))
	{
		printString=str;
	}
	return printString;
}


function getCurrentYear() {
    var year = new Date().getYear();
    if(year < 1900) year += 1900;
    return year;
  }

  function getCurrentMonth() {
    return new Date().getMonth() + 1;
  } 

  function getCurrentDay() {
    return new Date().getDate();
  }

function checkdt(t)
{
	
	

    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
        try{
        var f=DateFormat(t,c,event,true,'3');
        }catch(e){
        //exception  start
        
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            
            if(currentYear > getCurrentYear())
            {
            
                    alert("Entered date should be less than or equal to current date ");
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert("Entered date should be less than or equal to current date ");
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert("Entered date should be less than or equal to current date ");
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
             if(err!=0)
                {
                    t.value="";
                    return false;
                }
            return true;
        
        
        //exception end
        
        }
        if( f==true)
        {
            //alert(f);
            //t.value=c.replace(/\//g,'-');
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
         
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
           
            return true;
            
        }
        else
        {
                if(err!=0)
                {
                    t.value="";
                    return false;
                }
        }
            
    }
    else
    {
            alert('Date format  should be (dd/mm/yyyy)');
            t.value="";
            //t.focus();
            return false
    }
    
}