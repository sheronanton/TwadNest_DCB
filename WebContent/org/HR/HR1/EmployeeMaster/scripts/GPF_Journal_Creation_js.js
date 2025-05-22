var com_id;
var seq=0;
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

function checkNull()
{
       var tbody=document.getElementById("grid_body");
        if(document.getElementById("cmbAcc_UnitCode").value=="")
        {
	            alert("Select the Account Unit code");
	            return false;    
        }
        if(document.getElementById("cmbOffice_code").value=="")
        {
	            alert("Select the Office Code");           
	            return false;
        }
        if(document.getElementById("acc_head_code").value=="")
        {
            	alert("Select Head Code");
            	return false;
        }
        if(document.getElementById("general_amount").value=="")
        {
            	alert("Enter Amount");
            	return false;
        }
        if(tbody.rows.length==0)
            {
                    alert("Enter Details Part");
                    return false;
            }
        else
        {
                        var dr_check_amt=0;var cr_check_amt=0;var count=0;
                        rows=tbody.getElementsByTagName("TR");                           
                        for(i=0;i<rows.length;i++)
                        {
                                    var cells=rows[i].cells;                                              
                                    if(cells.item(2).lastChild.nodeValue=='CR')
                                    {
                                            dr_check_amt=parseFloat(dr_check_amt) + parseFloat(cells.item(5).lastChild.nodeValue);
                                                                                                                
                                    } 
                                    else
                                        {
                                                cr_check_amt=parseFloat(cr_check_amt) + parseFloat(cells.item(5).lastChild.nodeValue);       
                                        }
                         }      
                        if(dr_check_amt!=cr_check_amt)
                        {
                                    alert("Total Amount of DR & CR should be equal");
                                    return false; 
                        }
                        else if(cr_check_amt!=document.getElementById("txtTotalAmt").value)
                        {
                                    alert("Total Amount of CR & TDA Total Debit Amount should be equal");
                                    return false;
                        }
        }            
}

function checkYr()
{
   var generalYear=document.GPF_Journal.ACYear.value;
   var againstyear=document.GPF_Journal.against_year.value;
   if(againstyear>generalYear)
   {
        alert("year should be less than Adjusted Year");
        document.GPF_Journal.against_year.value="";
        document.getElementById("against_year").focus();
       
   }
}

function checkMn()
{
     var generalYear=document.GPF_Journal.ACYear.value;
     var generalMonth=document.GPF_Journal.ACMonth.value;
     var againstyear=document.GPF_Journal.against_year.value;
     var againstmonth=document.GPF_Journal.against_month.value;
     
     var gg=generalYear+generalMonth;
     var aa=againstyear+againstmonth;
     if((parseInt(gg)-parseInt(aa))<0)
     {
    	 alert("month should be less than Adjusted for year and month");
       document.GPF_Journal.against_month.value="";
     }
        
}

function clearall()
{
        document.GPF_Journal.txtAcc_HeadCode.value="";
        document.GPF_Journal.txtAcc_HeadDesc.value="";
        document.GPF_Journal.cmbSL_type.value="";
        document.GPF_Journal.cmbSL_Code.value="";
        document.GPF_Journal.txtEmpID_trs.value="";
        document.GPF_Journal.bill_no.value="";
        document.GPF_Journal.bill_type.value="";
        document.GPF_Journal.bill_date.value="";
        document.GPF_Journal.detail_amount.value="";
        document.GPF_Journal.against_year.value="";
        document.GPF_Journal.against_month.value="";
        document.GPF_Journal.txtParticular.value="";
        
        document.getElementById("txtAcc_HeadCode").disabled=false;
        document.getElementById("detail_amount").disabled=false;
        document.getElementById("txtParticular").disabled=false;
        document.getElementById("txtEmpID_trs").disabled=false;
        document.getElementById("cmbSL_Code").disabled=false;
        document.getElementById("cmbSL_type").disabled=false;
        
        document.GPF_Journal.cmdadd.style.display='block';     //block=visible
        document.GPF_Journal.cmdupdate.style.display='none';    //none =not visible
        document.GPF_Journal.cmdadd.disabled=false;
        
}

function callCrDr(crdr)
{
    if(crdr=="CR"){
    document.GPF_Journal.rad_sub_CR_DR[0].checked=true;
    }
    else
    document.GPF_Journal.rad_sub_CR_DR[1].checked=true 
   
}
function calling()
{
           // var headCode=document.GPF_Journal.acc_head_code.value;
            var unitcode=document.GPF_Journal.cmbAcc_UnitCode.value;
            var officecode=document.GPF_Journal.cmbOffice_code.value;
            var vdate=document.GPF_Journal.txtCrea_date.value;
             var url="../../../../../GPF_Journal_Creation?Command=detload&unitcode="+unitcode+"&officecode="+officecode+"&vdate="+vdate;
             req=getTransport();
             req.open("GET",url,true);        
             req.onreadystatechange=function()
             {   
                   callManipulated(req);
             }   
             req.send(null);  
} 

function changeAccCode()
{
         var unitcode=document.GPF_Journal.cmbAcc_UnitCode.value;
         var officecode=document.GPF_Journal.cmbOffice_code.value;
         var vdate=document.GPF_Journal.txtCrea_date.value;
         var GPF_list= window.open("GPF_Account_Head.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+officecode+"&vdate="+vdate,"GPFList","status=1,height=500,width=500,resizable=YES,scrollbars=yes"); 
         GPF_list.moveTo(250,250);  
         GPF_list.focus();     
}

function doParentGPF_Code(Acc_Head_Code,acyear,acmonth,amount,particulars,headDesc)
{
         document.GPF_Journal.acc_head_code.value=Acc_Head_Code;
         document.GPF_Journal.ACYear.value=acyear;
         document.GPF_Journal.ACMonth.value=acmonth;
         document.GPF_Journal.general_amount.value=amount;
         document.GPF_Journal.txtAcc_HeadCode.value=Acc_Head_Code;
         document.GPF_Journal.txtAcc_HeadDesc.value=headDesc;
         
         document.GPF_Journal.txtAcc_HeadCode.disabled=true;
         document.GPF_Journal.detail_amount.value=amount;     
         document.GPF_Journal.detail_amount.disabled=true;
         document.GPF_Journal.txtParticular.value=particulars;
         document.GPF_Journal.txtParticular.disabled=true;
         
      
}

function callManipulated(req)
{
  
    if(req.readyState==4)
	   {
	      if(req.status==200)
	          {
                    var baseResponse=req.responseXML.getElementsByTagName("response")[0];  
                     var tagCommand=baseResponse.getElementsByTagName("command")[0];
                     var command=tagCommand.firstChild.nodeValue; 
	               if(command=="headAll")
                       { 
                               headAll(baseResponse);
                       }
                       
	          }
	    }
}

function headAll(baseResponse)
{
     acheadcode=baseResponse.getElementsByTagName("acheadcode")[0].firstChild.nodeValue;
     acyear=baseResponse.getElementsByTagName("acyear")[0].firstChild.nodeValue;
     acmonth=baseResponse.getElementsByTagName("acmonth")[0].firstChild.nodeValue;
     amount=baseResponse.getElementsByTagName("difference")[0].firstChild.nodeValue;
     detPartic=baseResponse.getElementsByTagName("remarks")[0].firstChild.nodeValue;
     document.GPF_Journal.acc_head_code.value=acheadcode;
     document.GPF_Journal.ACYear.value=acyear;
     document.GPF_Journal.ACMonth.value=acmonth;
     document.GPF_Journal.general_amount.value=amount;
     document.GPF_Journal.txtAcc_HeadCode.value=acheadcode;
     document.GPF_Journal.txtAcc_HeadCode.disabled=true;
     document.GPF_Journal.detail_amount.value=amount;     
     document.GPF_Journal.detail_amount.disabled=true;
     document.GPF_Journal.txtParticular.value=detPartic;
     document.GPF_Journal.txtParticular.disabled=true;
      doFunction('checkCode','null');
      
}

function clrForm(param)
{		

        document.GPF_Journal.rad_sub_CR_DR[0].checked=true;
        document.getElementById("txtCrea_date").value="";
        document.getElementById("cashbookyear").value="";
        document.getElementById("cashbookmonth").value="";
        document.getElementById("ACYear").value="";
        document.getElementById("ACMonth").value="";
        document.getElementById("general_amount").value="";        
        document.getElementById("txtRemarks").value="";
       	if(param=="cancel")
		{
                    if(window.confirm("Do you want to clear ALL fields ?"))
                        {
                                call_clr();
                        }
		}
		else
				call_clr();
}

function call_clr()
{
      	clearall();
        document.getElementById("bill_no").value="";
        document.getElementById("bill_type").value="";
        document.getElementById("bill_date").value="";
        var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
                tbody.deleteRow(0);
        }
}

function call_mainJSP_script(fromcal_dateCtrl,blr_flag)    
{
         if(blr_flag==1)         
         {                              
                 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
                 var cmbOffice_code=document.getElementById("cmbOffice_code").value;
                 var TB_date=fromcal_dateCtrl.value;                 
                 if(fromcal_dateCtrl.value.length!=0)
                 {
                         var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;                         
                         var req=getTransport();
                         req.open("GET",url,true); 
                         req.onreadystatechange=function()
                         {
                                  check_TB(req,fromcal_dateCtrl);
                         }   
                         req.send(null);
                 }
         }
}

function check_TB(req,dateCtrl)
{
         if(req.readyState==4)
         {
                if(req.status==200)
                {  
                         var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                         var tagcommand=baseResponse.getElementsByTagName("command")[0];
                         var Command=tagcommand.firstChild.nodeValue;
                         var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;                        
                         if(flag=="failure")
                         {
                                  dateCtrl.value="";
                                  alert("Trial Balance Closed");//return false;//
                                  dateCtrl.focus();                                            
                         }
                         else if(flag=="finyear")
                         {
                                  // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                                  dateCtrl.value="";
                                  alert("Cash Book Control Not Found ");//return false;//
                                  dateCtrl.focus();
                                  //document.getElementById("txtVoucher_No").value="";     
                         }
                }
         }
}

function call_date(dateCtrl)                        // TB_checking 
{         
	  if(checkdt(dateCtrl))
          {
                  var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
                  var cmbOffice_code=document.getElementById("cmbOffice_code").value;
                  var TB_date=dateCtrl.value;
                  if(dateCtrl.value.length!=0)
                  {
                          var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                          var req=getTransport();
                          req.open("GET",url,true); 
                          req.onreadystatechange=function()
                          {
                                   check_TB(req,dateCtrl);
                          }   
                          req.send(null);
                  }
         }
     
 }
 function check_TB(req,dateCtrl)
 {
	  if(req.readyState==4)
          {
                 if(req.status==200)
                 {  
                          var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                          var tagcommand=baseResponse.getElementsByTagName("command")[0];
                          var Command=tagcommand.firstChild.nodeValue;
                          var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;                        
                          callYear();
                          if(flag=="failure")
                          {
                          
                                   dateCtrl.value="";
                                   alert("Trial Balance Closed");//return false;//
                                   dateCtrl.focus();                                            
                          }
                          else if(flag=="finyear")
                          {
                          
                                   // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                                   dateCtrl.value="";
                                   alert("Cash Book Control Not Found ");//return false;//
                                   dateCtrl.focus();
                               
                          }
                     }
          }
 }

function callYear()
{
            document.GPF_Journal.cashbookyear.value="";
            document.GPF_Journal.cashbookmonth.value="";
            var dated= document.GPF_Journal.txtCrea_date.value;
            var dt=dated.split("/");
            document.GPF_Journal.cashbookyear.value=dt[2];
            var month;
            if(dt[1]==01){month="Jan"}
             else if(dt[1]==02){month="Feb"}
             else if(dt[1]==03){month="Mar"}
             else if(dt[1]==04){month="Apr"}
             else if(dt[1]==05){month="May"}
             else if(dt[1]==06){month="Jun"}
             else if(dt[1]==07){month="Jul"}
             else if(dt[1]==08){month="Aug"}
             else if(dt[1]==09){month="Sep"}
             else if(dt[1]==10){month="Oct"}
             else if(dt[1]==11){month="Nov"}
             else if(dt[1]==12){month="Dec"}
            document.GPF_Journal.cashbookmonth.value=month;
}

/////////////////////////////////////////////   ADD & UPDATE & DELETE /////////////////////////////////////////////////////
function load_grid(cmd)
{

        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
                alert("Enter A/c Head Code");
                return false;
        }                
             
        if(document.getElementById("detail_amount").value.length==0)
        {
                alert("Enter the Amount ");
                return false;    
        }
        if(document.getElementById("against_year").value.length==0)
        {
                if(document.GPF_Journal.txtAcc_HeadCode.value==document.GPF_Journal.acc_head_code.value)
                {
                    alert("Enter Adjusted Against Year ");
                    return false;   
                }
                                
        }
        if(document.getElementById("against_month").value.length==0)
        {
                if(document.GPF_Journal.txtAcc_HeadCode.value==document.GPF_Journal.acc_head_code.value)
                {
                    alert("Enter Adjusted Against Month ");
                    return false;  
                }
                
        }
        
        var tbody=document.getElementById("grid_body");                            
        var t=0;            
        var items=new Array();
        
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;   
        
        if(document.GPF_Journal.rad_sub_CR_DR[0].checked==true)
        	items[2]=document.GPF_Journal.rad_sub_CR_DR[0].value;
        else if(document.GPF_Journal.rad_sub_CR_DR[1].checked==true)
        	items[2]=document.GPF_Journal.rad_sub_CR_DR[1].value;  
        items[3]=document.GPF_Journal.cmbSL_type.value;
        items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text;         
        items[5]=document.GPF_Journal.cmbSL_Code.value;
        items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
        
        items[7]=document.getElementById("detail_amount").value;
        items[8]=document.getElementById("against_year").value;
        items[9]=document.getElementById("against_month").value;         
        items[10]=document.getElementById("txtParticular").value;
        items[11]=document.getElementById("bill_no").value;
        items[12]=document.getElementById("bill_type").value;
        items[13]=document.getElementById("bill_date").value;
       
        tbody=document.getElementById("grid_body");
        if(cmd=="ADD_GRID")
        {
                var mycurrent_row=document.createElement("TR");                
                mycurrent_row.id=seq;
                
                var cell=document.createElement("TD");
                var anc=document.createElement("A");
                var url="javascript:loadTable('"+mycurrent_row.id+"')";
                anc.href=url;
                var txtedit=document.createTextNode("EDIT");
                anc.appendChild(txtedit);
                cell.appendChild(anc);
                mycurrent_row.appendChild(cell);
                
                cell2=document.createElement("TD");       
                var H_code=document.createElement("input");
                H_code.type="hidden";
                H_code.name="H_code";
                H_code.value=items[0];
                cell2.appendChild(H_code);
                var currentText=document.createTextNode(items[0]+"-"+items[1]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                cell2=document.createElement("TD"); 
                var CR_DR_type=document.createElement("input");
                CR_DR_type.type="hidden";
                CR_DR_type.name="CR_DR_type";
                CR_DR_type.value=items[2];
                cell2.appendChild(CR_DR_type);
                var currentText=document.createTextNode(items[2]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                                               
                cell2=document.createElement("TD");
                var SL_type=document.createElement("input");
                SL_type.type="hidden";
                SL_type.name="SL_type";
                SL_type.value=items[3];
                cell2.appendChild(SL_type);
                var currentText=document.createTextNode(items[4]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                                               
                cell2=document.createElement("TD");
                var SL_code=document.createElement("input");
                SL_code.type="hidden";
                SL_code.name="SL_code";
                SL_code.value=items[5];
                cell2.appendChild(SL_code);
                var currentText=document.createTextNode(items[6]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                cell2=document.createElement("TD"); 
                var sl_amt=document.createElement("input");
                sl_amt.type="hidden";
                sl_amt.name="sl_amt";
                sl_amt.value=items[7];
                cell2.appendChild(sl_amt);
                var currentText=document.createTextNode(items[7]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                cell2=document.createElement("TD"); 
                var adj_year=document.createElement("input");
                adj_year.type="hidden";
                adj_year.name="adj_year";
                adj_year.value=items[8];//first child
                cell2.appendChild(adj_year);
                var bill_no=document.createElement("input");
                bill_no.type="hidden";
                bill_no.name="bill_no1";
                bill_no.value=items[11];      //middle child
                cell2.appendChild(bill_no);
                var currentText=document.createTextNode(items[8]); //lastchild
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                cell2=document.createElement("TD"); 
                var adj_month=document.createElement("input");
                adj_month.type="hidden";
                adj_month.name="adj_month";
                adj_month.value=items[9];
                cell2.appendChild(adj_month);
                var bill_types=document.createElement("input");
                bill_types.type="hidden";
                bill_types.name="bill_types";
                bill_types.value=items[12];
                cell2.appendChild(bill_types);
                var currentText=document.createTextNode(items[9]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                                 
                cell2=document.createElement("TD");                
                var particular=document.createElement("input");
                particular.type="hidden";
                particular.name="sl_particular";
                particular.value=items[10];
                cell2.appendChild(particular);
                var bill_dates=document.createElement("input");
                bill_dates.type="hidden";
                bill_dates.name="bill_dates";
                bill_dates.value=items[13];
                cell2.appendChild(bill_dates);
                var currentText=document.createTextNode(items[10]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                               
                tbody.appendChild(mycurrent_row);
                clearall();
                seq=seq+1;
        }
         else
        {
                      
                var r=document.getElementById(com_id);
                var rcells=r.cells;
        
//                try{rcells.item(1).firstChild.value=items[0];}catch(e){}   //firstchild for value and last for desc
//                try{rcells.item(1).lastChild.nodeValue=items[0]+"-"+items[1];}catch(e){}
             
                try{rcells.item(2).firstChild.value=items[2];}catch(e){}
                try{rcells.item(2).lastChild.nodeValue=items[2];}catch(e){}
               
                try{rcells.item(3).firstChild.value=items[3];}catch(e){}
                try{rcells.item(3).lastChild.nodeValue=items[4];}catch(e){}
            
                try{rcells.item(4).firstChild.value=items[5];}catch(e){}
                try{rcells.item(4).lastChild.nodeValue=items[6];}catch(e){}
            
              
                try{rcells.item(5).firstChild.value=items[7];}catch(e){}
                try{rcells.item(5).lastChild.nodeValue=items[7];}catch(e){}
            
                try{rcells.item(6).firstChild.value=items[8];}catch(e){}
                try{rcells.item(6).lastChild.nodeValue=items[8];}catch(e){}
            
                try{rcells.item(7).firstChild.value=items[9];}catch(e){}
                try{rcells.item(7).lastChild.nodeValue=items[9];}catch(e){}
             
                try{rcells.item(8).firstChild.value=items[10];}catch(e){}
                try{rcells.item(8).lastChild.nodeValue=items[10];}catch(e){}
                
                try{rcells.item(6).childNodes.item(1).value=items[11];}catch(e){}  //rcells.item(6).childNodes.item(1).value
                 
                try{rcells.item(7).childNodes.item(1).value=items[12];}catch(e){}
                
                try{rcells.item(8).childNodes.item(1).value=items[13];}catch(e){}
                
                 document.GPF_Journal.cmdupdate.style.display='none';        
                 document.GPF_Journal.cmdadd.style.display='block';                   
                alert("Record Updated");
                clearall();
        }
  
}
function loadTable(scod)
{	
         com_id=scod;     
         if(com_id>0)
         {
              loadtableValues(scod);
              document.getElementById("txtAcc_HeadCode").disabled=false;
              document.getElementById("detail_amount").disabled=false;
              document.getElementById("txtParticular").disabled=false;
              document.getElementById("txtEmpID_trs").disabled=false;
              document.getElementById("cmbSL_Code").disabled=false;
              document.getElementById("cmbSL_type").disabled=false;

         }
        else
        {
              loadtableValues(scod);
              document.getElementById("txtAcc_HeadCode").disabled=true;
              document.getElementById("detail_amount").disabled=true;
              document.getElementById("txtParticular").disabled=true;
         //     document.getElementById("txtEmpID_trs").disabled=true;
         //     document.getElementById("cmbSL_Code").disabled=true;
        //      document.getElementById("cmbSL_type").disabled=true;

        }
}

function loadtableValues(scod)
{
        com_id=scod;   
        var r=document.getElementById(scod);
        var rcells=r.cells;
        try {document.getElementById("txtAcc_HeadCode").value=rcells.item(1).firstChild.value;}catch(e){}
        doFunction('checkCode','null');   
             
        if(rcells.item(2).firstChild.value=="CR")
        		document.GPF_Journal.rad_sub_CR_DR[0].checked=true;
        else if(rcells.item(2).firstChild.value=="DR")
        		document.GPF_Journal.rad_sub_CR_DR[1].checked=true;
                        
        sltype=rcells.item(3).firstChild.value;
        slcode=rcells.item(4).firstChild.value;
        try{document.getElementById("txtEmpID_trs").value=slcode;}catch(e){}  
        doFunction('Load_SL_Code',sltype);
        
        try{document.getElementById("detail_amount").value=rcells.item(5).firstChild.value;}catch(e){}
        try{document.getElementById("against_year").value=rcells.item(6).firstChild.value;}catch(e){}
        try{document.getElementById("against_month").value=rcells.item(7).firstChild.value;}catch(e){}  
        try{document.getElementById("txtParticular").value=rcells.item(8).firstChild.value;}catch(e){} 
      
        try{document.getElementById("bill_no").value=rcells.item(6).childNodes.item(1).value;}catch(e){}  //for retrieving middle value in item(6)
        try{document.getElementById("bill_type").value=rcells.item(7).childNodes.item(1).value;}catch(e){}  //for retrieving middle value in item(7)
        try{document.getElementById("bill_date").value=rcells.item(8).childNodes.item(1).value;}catch(e){}  //for retrieving middle value in item(8)
          
          
        document.GPF_Journal.cmdadd.style.display='none';     
        document.GPF_Journal.cmddelete.style.display='block';
        document.GPF_Journal.cmddelete.disabled=false;
        document.GPF_Journal.cmdupdate.style.display='block';    
 	setTimeout('document.getElementById("cmbSL_type").value=sltype',900); 
}

function delete_GRID()
{
        if(confirm("Do you want to delete ?"))
        {
        		   
                var tbody=document.getElementById("mytable");
                var r=document.getElementById(com_id);
                var ri=r.rowIndex;
                tbody.deleteRow(ri);
                clearall();
              
        }
}