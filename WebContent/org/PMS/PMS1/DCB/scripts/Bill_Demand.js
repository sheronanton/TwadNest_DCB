//alert("Bill_Demand_Edit");



/*
 *  
 * 
 * Document    : Bill Demand 
    Module	   : PMS
    Created on : 28/1/2010
    File Name  : Bill_Demand.js
    Action To  :
    File Desc  : Look up table of schme category
    Author     : KPS
    -----------------------------------------------------
    Sno	  Date	 	Type	EMP ID	
    -----------------------------------------------------
    1	  28/1/2010  N
    
 * 
 * createObject()
 * data_show()
 * result_process()
 * show()
 * */
 /*
  * major - TD ,TR
	item  -  input ,select ,
	value - value 
	type,- text,radio
	name - name
	size - size	
	sclass - styleclass
	url - url
  */

var total_demand;
var useform=0;
var combo_name="";
var formflag=0;
var row_value=0;
var pcount=0;
function ben_combo_load()
{
	var btype=document.getElementById("btype").value;
	var off=document.getElementById("off").value;
	var	url="../../../../../Counting";	 
	url+="?bentype="+btype+"&process_code=3&office_id="+off;       
	var xmlobj = createObject();  
	xmlobj.open("GET", url, true); 
	xmlobj.onreadystatechange = function() {
		ben_combo_load_process(xmlobj );  
	}

	xmlobj.send(null);
}
function ben_combo_load_process(xmlobj)
{
	 if (xmlobj.readyState==4)
	 {	   
	 	 
		if (xmlobj.status==200)
	    {
			
			ben_combo_load_res(xmlobj)
	    }
		
	 }
}
function ben_combo_load_res(xmlobj)
{
	var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
	document.getElementById('ben_combo').options.length = 0;

	var len = bR.getElementsByTagName("sno").length;
	var status = bR.getElementsByTagName("status")[0].firstChild.nodeValue;
	//if (len==0) 			alert(status+"\n-------------------------------")
	
	for (i = 0; i < len; i++) {
		var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
		var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
		addOption(document.getElementById('ben_combo'), name+" - "+sno+" ", sno)
	}
}
function Ben_Type_Wise_Delete(process_code)
{
	var billyear=document.getElementById("billyear").value;
	var billmonth=document.getElementById("billmonth").value;
	var btype=document.getElementById("btype").value;
	var off=document.getElementById("off").value;
	var	url="../../../../../Counting";
		url+="&process_code="+process_code;
		url+="&office_id="+off;//OFFICE_ID
		url+="&year="+billyear;
		url+="&month="+billmonth;

}

function pr_edit_start(command,process_code)
{
	
	//alert("Welcome====>Edit Function")
	
	var netunit=document.getElementById("netunit").value;    	 	  			 
		if (netunit==""  )
		{
			alert("Total Units Missing")
			sub_flag=1;
		}else
		{ 
			var rowcnt_meter=document.getElementById("rowcnt_meter").value;
			var selbentype=document.getElementById("selbentype").value;
			alert("rowcnt_meter is "+rowcnt_meter);
			alert("selbentype is "+selbentype);
			var	url="../../../../../Beneficiary_DCB_ob";
				url+="?command="+command;
				url+="&process_code="+process_code;
				url+="&rowcnt_meter="+rowcnt_meter;//OFFICE_ID
				url+="&MONPR_SNO="+document.getElementById("MONPR_SNO").value;
				url+="&year="+document.getElementById("year").value;
				url+="&month="+document.getElementById("month").value;
				url+="&netunit="+document.getElementById("netunit").value;//netunit
				
			//	alert("URL============>"+url);
	  					for (i=1;i<=rowcnt_meter;i++)
	  					{  
 	  						var unit=document.getElementById("nounit"+i).value;
 	  						var read=document.getElementById("read"+i).value;
 	  					   	url+=("&METRE_SNO"+i)+"="+document.getElementById("METRE_SNO"+i).value;;
 	  						url+="&METRE_INITIAL_READING"+i+"="+document.getElementById("METRE_INIT_READING"+i).value;
 	  						url+=("&METRE_CLOSING_READING"+i)+"="+read;
 	  						url+=("&QTY_CONSUMED"+i)+"="+document.getElementById("nounit"+i).value;;
 	  						url+=("&PR_SNO"+i)+"="+document.getElementById("PR_SNO"+i).value;  						 
 	  						var aqty=document.getElementById("ALLOTED_QTY"+i).value;    	 	  						 
 	  						// 	Excess Qty 
 	  						var ben_type=document.getElementById("bentype").value;
    	 	  						if (aqty>0)
    	 	  						{    	if (ben_type > 6)
    	 	  								{    	 			  			   	
    	 	  									var eqty=(parseFloat(unit)-parseFloat(aqty));
    	 	  									if (eqty>0)
    	 	  										eqty=eqty;
    	 	  									else
    	 	  										eqty=0;
    	 	  								}
    	 	  								else
    	 	  								{
    	 	  									eqty=0;
    	 			  				  
    	 	  								}
    	 	  						}
    	 	  						else
    	 	  						{
    	 	  									eqty=0;
    	 	  				 	    }
    	 	  						 		var meter="";
    	 	  						 		if (document.getElementById("METRE_FIXED"+i).innerHTML=="Yes" || document.getElementById("METRE_FIXED"+i).innerHTML=="Y")
    	 	  						 			meter="Y";
    	 	  						 		else
    	 	  						 			meter="N";
    	 	  						 		
    	 	  						 		var meterw="";
    	 	  						 		if (document.getElementById("METRE_WORKING"+i).innerHTML=="Yes" || document.getElementById("METRE_WORKING"+i).innerHTML=="Y")
    	 	  						 			meterw="Y";
    	 	  						 		else
    	 	  						 			meterw="N";
 	  						 
			    	 	   url+="&METRE_WORKING"+i+"="+meterw;
			    	 	   url+="&METRE_FIXED"+i+"="+meter;
			    	 	   url+=("&ALLOTED_QTY"+i)+"="+aqty;
			    	 	   url+=("&EXCESS_QTY"+i)+"="+eqty;
	    	 	 	
			    	 	 			    
	  					} 
		} 
	    var xmlobj1=createObject();
		var v=0;
		xmlobj1.open("GET",url,true);
		xmlobj1.onreadystatechange=function() 
		{	    
		    result_process_ed(xmlobj1,command,process_code);
		}
	 xmlobj1.send(null);
}

function billcount()
{
//	alert("HI")
	pcount++; 
	var off=document.getElementById("office").value;
//	alert("off is"+off)
	var url = "bill_demand_ajax.jsp?off="+off;	 
	var xmlobj = createObject();  
	xmlobj.open("GET", url, true); 
	xmlobj.onreadystatechange = function() {
		billcount_process(xmlobj );  
	}

	xmlobj.send(null);
}  
function billcount_process(xmlobj ) {
  
	if (xmlobj.readyState == 4) {

				if (xmlobj.status == 200) 
				{ 
					
					var results_str =new String(xmlobj.responseText);
					var results=results_str.split("|");
					var res = results[1];
				
					document.getElementById("idtab").innerHTML=res;		
					document.getElementById("bcount").value=res;
					var bencount=document.getElementById("bencount").value;
				   
							/* if ( parseInt(res) < parseInt(bencount) )
							 {  								  	
							      
								   setTimeout('billcount()',900);		 		 
						 		   
							 }    
							  */
				}  
	}     
}

function demand_count()
{
	var xmlobj=createObject();
	var billyear=document.getElementById("billyear").value;
	var billmonth=document.getElementById("billmonth").value;
	var command="dmdfalsh";
	var datefrom=document.getElementById("datefrom").value;
	var dateto=document.getElementById("dateto").value;
	var date=document.getElementById("date").value;
	url="../../../../../Bill_Demand_Report?billmonth="+billmonth+"&date="+date+"&billyear="+billyear+"&command="+command+"&dateto="+dateto+"&datefrom="+datefrom;
	
	document.getElementById("pr_status").value=0;
    xmlobj.open("GET",url,true);
    
	xmlobj.onreadystatechange=function()
    {	 
		demand_count_process(xmlobj);
    } 
	
	xmlobj.send(null);
}
function demand_count_process(xmlobj)
{
	 if (xmlobj.readyState==4)
	 {	   
	 	 
		if (xmlobj.status==200)
	    {
			
			demand_count_res(xmlobj)
	    }
		
	 }
}

function demand_ins()
{
	var xmlobj=createObject();
	var billyear=document.getElementById("billyear").value;
	var billmonth=document.getElementById("billmonth").value;
	var command="demand_insert";
	var datefrom=document.getElementById("datefrom").value;
	var dateto=document.getElementById("dateto").value;
	var btype=document.getElementById("btype").value;
	var ben_combo=document.getElementById("ben_combo").value;
	var date=document.getElementById("date").value;
	url="../../../../../Bill_Demand?billmonth="+billmonth+"&date="+date+"&billyear="+billyear+"&command="+command+"&bentype="+btype+"&dateto="+dateto+"&datefrom="+datefrom+"&ben_combo="+ben_combo;
	 
	document.getElementById("pr_status").value=0;
    xmlobj.open("GET",url,true);
      
	xmlobj.onreadystatechange=function()
    {	 
		demand_ins_process(xmlobj);
    } 
	
	xmlobj.send(null);
}
function demand_ins2()
{
	var xmlobj=createObject();
	var billyear=document.getElementById("billyear").value;
	var billmonth=document.getElementById("billmonth").value;
	var command="full_dep_demand_insert";
	var datefrom=document.getElementById("datefrom").value;
	var dateto=document.getElementById("dateto").value;
	var date=document.getElementById("date").value;
	url="../../../../../Bill_Demand?billmonth="+billmonth+"&date="+date+"&billyear="+billyear+"&command="+command+"&dateto="+dateto+"&datefrom="+datefrom;
	
	document.getElementById("pr_status").value=0;
    xmlobj.open("GET",url,true);
    
	xmlobj.onreadystatechange=function()
    {	 
		demand_ins_process(xmlobj);
    } 
	
	xmlobj.send(null);
}

function demand_ins_process(xmlobj)
{
	 if (xmlobj.readyState==4)
	 {	   
	 	 
		if (xmlobj.status==200)
	    {
			
			demand_ins_res(xmlobj)
	    }
		
	 }
}

function demand_ins_res(xmlobj)
{
	document.getElementById("pr_status").value=1;
	var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
	var row=bR.getElementsByTagName("row")[0].firstChild.nodeValue;;
	var already_demand=bR.getElementsByTagName("already_demand")[0].firstChild.nodeValue;;
	  total_demand=bR.getElementsByTagName("total_demand")[0].firstChild.nodeValue;;
	var msg="";
	if (total_demand==0 && parseInt(already_demand) > 0)
	{
		msg+=" Already Generated for "+already_demand+" Beneficiaries";
	}
	else if (total_demand > 0 )
	{
		msg=" Demand Generated Successfully for "+total_demand+" Beneficiary  \n ";
		
	    if (parseInt(already_demand) > 0 )
	    {
	    	msg+="\n and Already Generated for "+already_demand+" Beneficiaries  ";
	    }
	    	
	      
	}else {
		try
		{
		   var msg2=bR.getElementsByTagName("msg2")[0].firstChild.nodeValue;;
		   msg=msg2;
		}catch(e)
		{
		    msg=" Demand Not Generated !!";
		}  
		    
	}   
	
	  
	 alert(msg);
}
function pumping_pdf_show(process,command)
{
	    var option=0;
	    
	    try
	    {
	    	option=document.getElementById("pr_type").value;
	    	
	    }catch(e) {
	    	
	    	option=1;
	    }
	    
		process_code=process;
		if (command=="pdf" && process==1)
		{
			var ben=document.getElementById("bensno").value;
			var year=document.getElementById("year").value;
			var month=document.getElementById("month").value;
			
			window.open("../../../../../Bill_Demand?command="+command+"&process_code="+process_code+"&bensno="+ben+"&year="+year+"&month="+month+"&option="+option);
		}else
		{
			var year=document.getElementById("year").value;
			var month=document.getElementById("month").value;
			
			window.open("../../../../../Bill_Demand?command="+command+"&process_code="+process_code+"&bensno=0&year="+year+"&month="+month+"&option="+option);
			
		}
		
}
function select_ben(type,combo_name,process_code)
{
	var Child_div=0;
	try {Child_div=document.getElementById("div").value;}catch(e){}
	var div=0;
	try
	{
		div=document.getElementById("div").value;
	}catch(e){}
	document.getElementById("pr_status").value=0; 
	var xmlobj=createObject();
		url="../../../../../report?Child_div="+Child_div+"&process_code="+process_code+"&input_value="+type+"&div="+div;
		combo_name=combo_name;
	    xmlobj.open("GET",url,true);
	     
		xmlobj.onreadystatechange=function()
	    {	 
			select_ben_process(xmlobj,combo_name);
	    } 
		
		xmlobj.send(null);
		  
	
}

function select_ben_process(xmlobj,combo_name)
{
	 
	document.getElementById(combo_name).options.length = 0;
	 if (xmlobj.readyState==4)
	 {	   
	 	 
		if (xmlobj.status==200)
	    {
			document.getElementById("pr_status").value=1; 
			var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
			var len=bR.getElementsByTagName("sno").length;
		 
						document.getElementById(combo_name).options.length = 0;

 			if (len==0) 
 						
				msgload(status,1);
				for (i=0;i<len;i++)
				{
					var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
				    var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
				    addOption(document.getElementById(combo_name),name,sno)
			    }	
	    }
	}
}
  
function data_show(command,process,input_value)
 {     
	 
		var url="";
		var process_code="0";
         process_code=process;                 
         var xmlobj=createObject();
         try
         {
        	 
        	 useform=document.getElementById("useform").value;
         }catch(e) {useform=0;}
         
         if (command=='schemewisevalidate')
         {
        		var subdiv=document.getElementById("subdiv").value
        		var scheme_select=document.getElementById("sch_load").value
             	var fyear=document.getElementById("fyear").value
            	var fmonth=document.getElementById("fmonth").value
            	  url="../../../../../Bill_Demand?subdiv="+subdiv+"&command="+command+"&scheme_select="+scheme_select+"&process_code="+process+"&fmonth="+fmonth+"&fyear="+fyear;
        		
        		 
        		xmlobj.open("GET",url,true);
                xmlobj.onreadystatechange=function()
                {
                 result_validate(xmlobj,command);
                }
                xmlobj.send(null);
         }else if (command=='schemewiserevoke')
         {
        	 var subdiv=document.getElementById("subdiv").value
     		var scheme_select=document.getElementById("sch_load").value
          	var fyear=document.getElementById("fyear").value
         	var fmonth=document.getElementById("fmonth").value
         	  url="../../../../../Bill_Demand?subdiv="+subdiv+"&command="+command+"&scheme_select="+scheme_select+"&process_code="+process+"&fmonth="+fmonth+"&fyear="+fyear;
     		
     		 
     		xmlobj.open("GET",url,true);
             xmlobj.onreadystatechange=function()
             {
              result_validate(xmlobj,command);
             }
             xmlobj.send(null);
      }
         else if (command=='Delete')
         {
        	 	var ben_sel=document.getElementById("ben_sel").value
             	var fyear=document.getElementById("fyear").value
            	var fmonth=document.getElementById("fmonth").value
             	row_value=process;
             url="../../../../../Bill_Demand?command="+command+"&input_value="+input_value+"&process_code="+process+"&fmonth="+fmonth+"&fyear="+fyear+"&ben_sel="+ben_sel;
             
           
             xmlobj.open("GET",url,true);
             xmlobj.onreadystatechange=function()
             {
              result_validate(xmlobj,command);
             }
             xmlobj.send(null);
        	 
         }else  if (command=='validate' || command=='notvalidate')  
      	{ 
         	var ben_sel=document.getElementById("ben_sel").value
         	var fyear=document.getElementById("fyear").value
        	var fmonth=document.getElementById("fmonth").value
        	var fmonth=document.getElementById("fmonth").value
        	row_value=process;
         url="../../../../../Bill_Demand?command="+command+"&input_value="+input_value+"&process_code="+process+"&fmonth="+fmonth+"&fyear="+fyear+"&ben_sel="+ben_sel;
         
        
         xmlobj.open("GET",url,true);
         xmlobj.onreadystatechange=function()
         {
          result_validate(xmlobj,command);
         }
         xmlobj.send(null);
      	}
         else  if (command=='show')  
     	{   
    	 	// data_show('show',2,this)
            if (process==1) url="../../../../../Bill_Demand?command="+command+"&input_value="+input_value+"&process_code="+process;
            if (process==2) 
            	url="../../../../../Bill_Demand?command=show&input_value="+window.opener.document.getElementById('selected_ben').value+"&process_code="+process+"&fyear="+window.opener.document.getElementById('year').value+"&fmonth="+window.opener.document.getElementById('month').value;
            	//url="../../../../../Bill_Demand?command=show&input_value="+input_value.value+"&process_code="+process;
            
            if (process==11)
            {
            	url="../../../../../Bill_Demand?command=show&input_value="+input_value+"&process_code="+process+"&fyear="+window.opener.document.getElementById('year').value+"&fmonth="+window.opener.document.getElementById('month').value+"&consumer="+consumer;
            }
            if (process==15)
            {
            	document.getElementById("pr_status").value=0; 
            	var consumer=input_value;
            	var fyear=document.getElementById("fyear").value
            	var fmonth=document.getElementById("fmonth").value
            	
            	
            	
            	var bentype=0;
            	var subdiv=0;
            	var sch_load=0;
            	try
            	{
            		sch_load=document.getElementById("sch_load").value
            		 
            	}catch(e) {formflag=0; }
            	try
            	{
            			formflag=document.getElementById("formflag").value
            	
            	}catch(e) {formflag=0;}
            	
            	
            	try
            	{
            	      bentype=document.getElementById("bentype").value
            	}catch(e) {bentype=0;}
            	
            	try
            	{
            				subdiv=document.getElementById("subdiv").value
            				 
            	}catch(e) {subdiv=0; }
            	
            	var ben_sel=0;
            	try
            	{
            		ben_sel=document.getElementById("ben_sel").value
            	}catch(e) {ben_sel=0;}
            	 
            	
             	var flag=0;
            	 
             	if (bentype!=0)
             		flag=6; // SUB
             	if (sch_load!=0 && bentype==0)
             		flag=4; // SUB
             	if (subdiv!=0 && ben_sel==0 && bentype==0)
            		flag=1; // SUB
            	if (bentype!=0 && ben_sel==0 && subdiv!=0)
             		flag=2;// SUB & BEN TYPE
            	if (ben_sel!=0 && bentype!=0 && subdiv!=0)
            		flag=3; // SUB BEN BEN
            	if (sch_load!=0 && bentype!=0)
             		flag=5; // SUB
            	if (sch_load!=0 && bentype!=0 && ben_sel!=0)
             		flag=3; // SUB
            	url="../../../../../Bill_Demand?command=show&flag="+flag+"&bentype="+bentype+"&subdiv="+subdiv+"&input_value="+input_value+"&sch_load="+sch_load+"&process_code="+process+"&fyear="+fyear+"&fmonth="+fmonth+"&consumer="+consumer;
            	 
            }
            if (process==3 || process==4) 
            {    
            	document.getElementById("pr_status").value=0; 
            	var consumer=input_value;
            	var fyear=document.getElementById("fyear").value
            	var fmonth=document.getElementById("fmonth").value
            	try
            	{
            		formflag=document.getElementById("formflag").value
            	
            	}catch(e) {formflag=0;}
            	url="../../../../../Bill_Demand?command=show&input_value="+window.opener.document.getElementById('selected_ben').value+"&process_code="+process+"&fyear="+window.opener.document.getElementById('year').value+"&fmonth="+window.opener.document.getElementById('month').value+"&consumer="+consumer;
            }
            if (process==6)
            {
            	url="../../../../../Bill_Demand?command=show&process_code="+process;
            	
            } 
            if (process==14)
            {
           	 url="../../../../../Bill_Demand?command=show&input_value="+input_value+"&process_code="+process+"&fyear="+document.getElementById('year').value+"&fmonth="+document.getElementById('month').value;
        	 }
            
            var xmlobj=createObject();
            xmlobj.open("GET",url,true);
            xmlobj.onreadystatechange=function()
            {
             result_process(xmlobj,command,process_code);
            }
            xmlobj.send(null);
     	}
     else if (command=='add')
     {
    	  
    	 			var xmlobj1=createObject();
    	 			if (parseInt(process_code)==12)
    	 	  		{
    	 	  			
    	 			} 
	    	 		  else
	    	 		  {
    	 				 
    	 			  document.getElementById("command").value="add";
    	 			  document.getElementById("pr_status").value=0;  
    	 			  url="../../../../../Bill_Demand";
    	 			  var data="" 
// Basic Details 
          
			         var  BENEFICIARY_SNO=document.getElementById("BENEFICIARY_SNO").value;
			         data+="?BENEFICIARY_SNO="+BENEFICIARY_SNO;
			         
			         var  DIV_BILL_NO=document.getElementById("Billno").value;
			         var  BILLING_DT=document.getElementById("date").value;
			         var  billmonth=document.getElementById("billmonth").value;
			         data+="&billmonth="+billmonth;
			         var  billyear=document.getElementById("billyear").value;
			         data+="&billyear="+billyear;
			         data+="&BILLING_DT="+BILLING_DT;
			         var  BILL_PERIOD_FROM=document.getElementById("datefrom").value;
			         data+="&BILL_PERIOD_FROM="+BILL_PERIOD_FROM;
			         data+="&BILL_PERIOD_TO="+document.getElementById("dateto").value;
//Main         
			         var  MAINT_OB=document.getElementById("MAINT_CB_TOTAL").value;	
			         data+="&MAINT_OB="+MAINT_OB;
			         var  MAINT_COLN=document.getElementById("MAINT_COLN").value;
			         data+="&MAINT_COLN="+MAINT_COLN;
			         var MAINT_cb_Value=0;
			         try
			         {
			        	 MAINT_cb_Value=parseFloat(MAINT_OB)-parseFloat(MAINT_COLN);
			         }catch(e)
			         {
			        	 MAINT_cb_Value=0; 
			         }
			         
			         var  MAINT_CB=MAINT_cb_Value;
			         data+="&MAINT_CB="+MAINT_CB;
         
				// add any          
				         var  OTHER_MTH_TOTAL=document.getElementById("othercharges").value;
				         data+="&OTHER_MTH_TOTAL="+OTHER_MTH_TOTAL;
				// Water charegs          
				         var  WC_OB=document.getElementById("cbtotal").value;
				         data+="&WC_OB="+WC_OB;
				         var  WC_COLN=document.getElementById("waterreceipt").value;
				         data+="&WC_COLN="+WC_COLN;
				         var  WC_INT_COLN=document.getElementById("interestreceipt").value;
				         data+="&WC_INT_COLN="+WC_INT_COLN;
				         var  WC_MTH_TOTAL=document.getElementById("netwcamt").value;
				         data+="&WC_MTH_TOTAL="+WC_INT_COLN;
				         
				// Interest          
				         var  INT_CALC=document.getElementById("penalty").value;
				// yester year         
				         var  YY_OB=document.getElementById("YESTERYR_CB").value;
				         data+="&YY_OB="+YY_OB;
				         data+="&INT_CALC="+INT_CALC;
				         var  YY_COLN=document.getElementById("yesteryearreceipt").value;
				         data+="&YY_COLN="+YY_COLN;
				         var YY_cb_Value=0;
         
				         try
				         {
				        	 YY_cb_Value=parseFloat(YY_OB)-parseFloat(YY_COLN);
				         }catch(e)
				         {
				        	 YY_cb_Value=0; 
				         }
			         
				         var  YY_CB=YY_cb_Value;
				         data+="&YY_CB="+YY_CB;
	         
	         
	//final          
	         			 var  MONTH_BILL_AMT=document.getElementById("nettotal").value;
	         			 data+="&MONTH_BILL_AMT="+MONTH_BILL_AMT;
				      	 var rs_value=chequeAmount(parseInt(MONTH_BILL_AMT));
				      	 data+="&rs_value='"+rs_value+"'";
	         
	         			  var wc_cb_Value=0;
		         
					      try
					      {
					      	 wc_cb_Value=(parseFloat(WC_OB)+parseFloat(MONTH_BILL_AMT))-parseFloat(WC_COLN);
					      }catch(e)
					      {
					      	 wc_cb_Value=0; 
					      }
					      var  WC_CB_TOTAL=wc_cb_Value;
	         			  data+="&WC_CB_TOTAL="+WC_CB_TOTAL;
	         			  var rows=document.getElementById("rows").value;        
	         			  data+="&DIV_BILL_NO="+DIV_BILL_NO+"";
        		  
     	
		     	        var NET_CONSUMPTION=0;	 
		     			for (i=1;i<=parseInt(rows);i++)
		     				{
		     					var  SUBDIV_OFFICE_ID=document.getElementById("SUBDIV_OFFICE_ID"+i).value;
		     					data+="&SUBDIV_OFFICE_ID"+i+"="+SUBDIV_OFFICE_ID;
		     					var  METRE_SNO=document.getElementById("cmbmeter"+i).value;
		     					data+="&METRE_SNO"+i+"="+METRE_SNO;
		     					var  QTY_CONSUMED=document.getElementById("totalread"+i).value;
		     					data+="&QTY_CONSUMED"+i+"="+QTY_CONSUMED;
		     					data+="&rate"+i+"="+document.getElementById("rate"+i).value;
		     					
		     					var  AMT=document.getElementById("amount"+i).value;
		     					data+="&AMT"+i+"="+AMT;	
		     					
		     					try
		     					{
		     					data+="&excessrate"+i+"="+document.getElementById("excessrate"+i).value;
		     					
		     					var  EXCESS_QTY=document.getElementById("excess"+i).value;
		     					data+="&EXCESS_QTY"+i+"="+EXCESS_QTY;	
		     					
		     					var  EXCESS_AMT=document.getElementById("excessamount"+i).value;
		     					data+="&EXCESS_AMT"+i+"="+EXCESS_AMT;	
		     					
		     					}catch(e)
		     					{
		     						EXCESS_QTY=0;
		     						data+="&excessrate"+i+"=0";
		     						data+="&EXCESS_QTY"+i+"=0";	
		     						data+="&excessrate"+i+"=0";
		     					}
		     					var  TOTAL_AMT=document.getElementById("subtotal"+i).value;
		     					
		     					
		     					data+="&TOTAL_AMT"+i+"="+TOTAL_AMT;	
		     					var  WC_TARIFF_ID=document.getElementById("TARIFF_ID"+i).value;
		     					data+="&WC_TARIFF_ID"+i+"="+WC_TARIFF_ID;
		     					var  SCHEME_SNO=document.getElementById("SCHEME_SNO"+i).value;
		     					data+="&SCHEME_SNO"+i+"="+SCHEME_SNO;	
		     					var  SCH_TYPE_ID=document.getElementById("SCH_TYPE_ID"+i).value;
		     					data+="&SCH_TYPE_ID"+i+"="+SCH_TYPE_ID	
		     					var  PR_SNO=document.getElementById("PR_SNO"+i).value;
		     					data+="&PR_SNO"+i+"="+PR_SNO;
		     			        NET_CONSUMPTION+=(parseFloat(EXCESS_QTY)+parseFloat(QTY_CONSUMED));	        
		     				}
		     			
		     			 
		     			for (i=1;i<=parseInt(rows);i++)
	     				{
		     				var  SCH_MAINT_OB=document.getElementById("MAINT_OB"+i).value;
	     					data+="&SCH_MAINT_OB"+i+"="+SCH_MAINT_OB;
	     					var  SCH_WC_OB=document.getElementById("WC_OB"+i).value;
	     					data+="&SCH_WC_OB"+i+"="+WC_OB;
	     					var  SCH_YY_OB=document.getElementById("YY_OB"+i).value;
	     					data+="&SCH_YY_OB"+i+"="+YY_OB;
	     					var  rc_schemesno=document.getElementById("rc_schemesno"+i).value;
	     					data+="&rc_schemesno"+i+"="+rc_schemesno;
	     					var  SCH_WC_COLN=document.getElementById("rc_schemeAMOUNT"+i).value;
	     					data+="&SCH_WC_COLN"+i+"="+SCH_WC_COLN;
	     					
	     				}
		     					data+="&NET_CONSUMPTION="+NET_CONSUMPTION;
 		     					data+="&rows="+rows;
		     					data+="&command="+command;	
		     					data+="&input_value="+input_value;
		     					data+="&process_code=7";
		     					url=url+""+data;
		     					 
		     					var ret_value=form_val();
		     					if (ret_value=="0")
		     					{   	   
		     							
								    	  xmlobj.open("GET",url,true);
								    	  xmlobj.onreadystatechange=function()
								          {
								           result_process(xmlobj,command,process_code);
								          }
								          xmlobj.send(null);
		     					}
		     					else
		     					{
		     							alert(" Not Valid for submit")
		     					}
    	
     							}		
    	  
     }
  }
 
function result_validate(xmlobj,command) 
{
	
 if (xmlobj.readyState==4)
  { 
	 
  if (xmlobj.status==200)
    { 
	  					 
	  
						  if (command=="schemewisevalidate")
						  {
							  var sp_flag=xmlobj.responseXML.getElementsByTagName("sp_flag")[0].firstChild.nodeValue;
							  var msg=xmlobj.responseXML.getElementsByTagName("msg")[0].firstChild.nodeValue;
							  var row=xmlobj.responseXML.getElementsByTagName("row")[0].firstChild.nodeValue;
							 
							  if (parseInt(row) >= 1 )
							  {
								  alert(" Validation Completed For All Beneficiaries Under Selected Scheme. ")
								  //self.location=self.location;
							  }
							   
						  } else if (command=="schemewiserevoke")
						  {
							  var sp_flag=xmlobj.responseXML.getElementsByTagName("sp_flag")[0].firstChild.nodeValue;
							  var msg=xmlobj.responseXML.getElementsByTagName("msg")[0].firstChild.nodeValue;
							  var row=xmlobj.responseXML.getElementsByTagName("row")[0].firstChild.nodeValue;
							  
							  if (parseInt(row) >= 1 )
							  {
								  alert(" Revoke Completed For All Beneficiaries Under Selected Scheme.  ")
								  self.location=self.location;
							  }
							   
						  }
						  else
							  
						  {
						  
											  var row=xmlobj.responseXML.getElementsByTagName("row")[0].firstChild.nodeValue;
											  var sp_flag=xmlobj.responseXML.getElementsByTagName("sp_flag")[0].firstChild.nodeValue;
											  var PR_SNO=xmlobj.responseXML.getElementsByTagName("PR_SNO")[0].firstChild.nodeValue;
											  
											  
											  if (sp_flag==2)
											  {
												  document.getElementById("Validated"+row_value).value="Validate";
												  document.getElementById("Validated"+row_value).setAttribute("onclick","data_show('validate','"+(i+1)+"','"+PR_SNO+"')")
												  document.getElementById("msg_lab"+row_value).innerHTML="Created";
											  }
											  else if (sp_flag==1)
											  {
												  document.getElementById("Validated"+row_value).value="Revoke";
												  document.getElementById("Validated"+row_value).setAttribute("onclick","data_show('notvalidate','"+(i+1)+"','"+PR_SNO+"')")
												  document.getElementById("msg_lab"+row_value).innerHTML="Validated";
												  document.getElementById("DeValidated"+row_value).setAttribute("type","hidden");
												//  var button=document.getElementById(document.getElementById("DeValidated"+row_value));
												   // button.setAttribute("type", "hidden")
										
											  }
											   
											  
											  document.getElementById("row"+row_value).style.backgroundColor="#dddfdd";
											  var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
											  var msg=bR.getElementsByTagName("msg")[0].firstChild.nodeValue;
											  
						  }
    }
  }
     
}

function result_process_ed(xmlobj3,command1,process_code1) 

{
	 
	 if (xmlobj3.readyState==4)
		 alert("readyState==>"+xmlobj3.readyState)
	   { 	 
	 
	   if (xmlobj3.status==200)
		   alert("status==>"+xmlobj3.status)
	     {
		   alert("command1===="+command1+"process_code1======"+process_code1)
		   if (command1=="add" && process_code1==12)
		   {
			  
			   alert("command1"+command1+"process_code1"+process_code1)
			   pr_ed(xmlobj3,process_code1,command1);  
		       
		   } 
 	     }
	   }
  
}

function pr_ed(xmlobj3,process_code,command)    
{ 
	
	alert("WELCOME")
	try
	  {
		 var  bR=xmlobj3.responseXML.getElementsByTagName("result")[0];
		 
		 var  ins_row1=xmlobj3.responseXML.getElementsByTagName("ins_row1")[0].firstChild.nodeValue;
	 alert("ins_row1"+ins_row1);
		 
		  
		 	if (parseInt(ins_row1)!=0)		 		
		  		alert("Updated Successfully..Pls Refresh ")
		  	else
		  		alert("Pumping Return Cannot be edited...pls refer Help ")
		  	//window.close();
		  
	  }catch(e) {
		  //window.close();
		 
	  }

    
} 




 function result_process(xmlobj,command,process_code) 
 {
	  
	  
  if (xmlobj.readyState==4)
   { 	 
   if (xmlobj.status==200)
     {
	   
	   if (process_code!=12)
	    show(xmlobj,process_code,command);         
	  
	 
		   
     }
   }
     
 }
 
 
 
 function show(xmlobj,process_code,command)   
 { 
	 
  
		
  var t=0;
  var bR="";
  
  try
  {
	  bR=xmlobj.responseXML.getElementsByTagName("result")[0];
  }catch(e) {}
  if (parseInt(process_code)==12)
  {     
	 
	      self.close();	  
	    // window.opener.location.reload(true);
	     window.opener.document.getElementById('year').value =document.getElementById("year").value;
  	     window.opener.document.list_demnad.month.value =document.getElementById("month").value;
  	 
  	    
  }else if (parseInt(process_code)==1)
  {
    var len=bR.getElementsByTagName("sno").length; 
    for (i=0;i<len;i++)
 	{  
     var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
     var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
     addOption(document.getElementById("cmbconsumer"),name,sno)
    }
  }else if (parseInt(process_code)==7)
  {
    var tran_row=bR.getElementsByTagName("tran_row").length;
    var row=bR.getElementsByTagName("row").length; 
    var maxsno=bR.getElementsByTagName("maxsno")[0].firstChild.nodeValue;
    if (tran_row!=0)
    {
    	document.getElementById("pr_status").value=1;
    	 
    	msgload("Insert Successfully",2)
    	document.getElementById("maxsno").value=maxsno;
    }
   
 // var  s=window.open('Bill_Demand_Report.jsp""?maxsno='+tran_row+'&month_value='+month_value+'&year_value='+year_value+"&mv="+mv+"&yv="+yv,'windowname1','width=900, height=700')
  // window.close();
  }else  if (parseInt(process_code)==14 )
	{
    	 
    	
		var len=bR.getElementsByTagName("sno").length;
		var status=bR.getElementsByTagName("status")[0].firstChild.nodeValue;
		if (len==0)
			msgload(status,2)
			for(i=document.getElementById("dmdlist").options.length-1;i>=0;i--)
			{
				document.getElementById("dmdlist").remove(i);
			}

			
			for (i=0;i<len;i++)
			{
				var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
			    var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
			    addOption(document.getElementById("dmdlist"),name,sno)
		    }
		
	}	else if (parseInt(process_code)==2)
      { 
	
	  
		 var eqty =0;
         var eamount =0;
         var totamount=0;
         var amount =0;
         var mul_qty=0;
         var difference=0;
         
	  document.getElementById("pr_status").value=1;
	 
	  var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
	  var tbody = document.getElementById("data_tbody");
	  var table = document.getElementById("data_table");                                                                                                     
	  var t=0;
	  for(t=tbody.rows.length-1;t>=0;t--){tbody.deleteRow(0);}
	  var len=bR.getElementsByTagName("metersno").length;
	  var benname=bR.getElementsByTagName("benname")[0].firstChild.nodeValue;
	  var BENEFICIARY_TYPE_ID=bR.getElementsByTagName("BENEFICIARY_TYPE_ID")[0].firstChild.nodeValue;
 	   var bentype=bR.getElementsByTagName("bentype")[0].firstChild.nodeValue;
	  var bentypevalue=bR.getElementsByTagName("bentypevalue")[0].firstChild.nodeValue;
	  var row=bR.getElementsByTagName("row")[0].firstChild.nodeValue;
	  var maxbillno=0;//bR.getElementsByTagName("maxbillno")[0].firstChild.nodeValue;
	  var WC_CB_TOTAL=0;//bR.getElementsByTagName("WC_CB_TOTAL")[0].firstChild.nodeValue;
	  var MAINT_CB_TOTAL =0;//bR.getElementsByTagName("MAINT_CB_TOTAL")[0].firstChild.nodeValue;
	  var YESTERYR_CB=0;//bR.getElementsByTagName("YESTERYR_CB")[0].firstChild.nodeValue;
	  var BENEFICIARY_SNO=bR.getElementsByTagName("BENEFICIARY_SNO")[0].firstChild.nodeValue;
	  var int_vlaue=bR.getElementsByTagName("int_vlaue")[0].firstChild.nodeValue;
	  cbtotal=WC_CB_TOTAL;
	  var net_qty=0;
	  var net_eqty=0;
	  if (int_vlaue==0) int_vlaue=1;
	  document.getElementById("penaltyint").value=int_vlaue;
	  
	  document.getElementById("cbtotal").value=cbtotal;
	  document.getElementById("MAINT_CB_TOTAL").value=MAINT_CB_TOTAL;
	  document.getElementById("YESTERYR_CB").value=YESTERYR_CB;
	  document.getElementById("BENEFICIARY_SNO").value=BENEFICIARY_SNO;
	  document.getElementById("Billno").value=maxbillno;
	  document.getElementById("ben_name").innerHTML=benname;
	  document.getElementById("ben_type").innerHTML=bentypevalue;
	  var Due_as_per_Last=bR.getElementsByTagName("Due_as_per_Last")[0].firstChild.nodeValue;
	  document.getElementById("cbtotal").value=Due_as_per_Last;
      amount = bR.getElementsByTagName("amount")[0].firstChild.nodeValue;
      eamount= bR.getElementsByTagName("eamount")[0].firstChild.nodeValue;
	  
	  var penaltyint=document.getElementById("penaltyint").value;
	  var penalty=(parseFloat(Due_as_per_Last)*parseFloat(penaltyint))/100;
	  document.getElementById("penalty").value=penalty;
	  document.getElementById("nettotal").value=parseFloat(eamount)+parseFloat(amount)+parseFloat(penalty);
	  if (bentype==6)
	  {
		  var blockvalue=bR.getElementsByTagName("blockvalue")[0].firstChild.nodeValue;
		  var distvalue=bR.getElementsByTagName("distvalue")[0].firstChild.nodeValue;
		  document.getElementById("block-div").innerHTML="Block  :"+blockvalue;
		  document.getElementById("dist-div").innerHTML="District : "+distvalue;
	  }
	  var ben_type_flag="0";
	  var netamount=0;
	  var excessnetamount=0;
	
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  for (i=0;i<len;i++)
	 	{  
		  
   		          var ALLOTED_FLG=bR.getElementsByTagName("ALLOTED_FLG")[i].firstChild.nodeValue;  

		  		  var SCH_TYPE_ID=bR.getElementsByTagName("SCH_TYPE_ID")[i].firstChild.nodeValue;
		  		  var MULTIPLY_FACTOR=bR.getElementsByTagName("MULTIPLY_FACTOR")[i].firstChild.nodeValue;
		  		  var MIN_BILL_QTY=0;//bR.getElementsByTagName("MIN_BILL_QTY")[i].firstChild.nodeValue;
		  		  var SUBDIV_OFFICE_ID = bR.getElementsByTagName("SUBDIV_OFFICE_ID")[i].firstChild.nodeValue;
		  		  
		    	  var SCHEME_SNO = bR.getElementsByTagName("SCHEME_SNO")[i].firstChild.nodeValue;
		    	  var PR_SNO = bR.getElementsByTagName("PR_SNO")[i].firstChild.nodeValue;
		    	  var TARIFF_ID= bR.getElementsByTagName("TARIFF_ID")[i].firstChild.nodeValue;
	              var metersno = bR.getElementsByTagName("metersno")[i].firstChild.nodeValue;
	              var meterlocation = bR.getElementsByTagName("meterlocation")[i].firstChild.nodeValue;
	               
	              var closingreading = bR.getElementsByTagName("closingreading")[i].firstChild.nodeValue;
	              var ini_reading = bR.getElementsByTagName("meterreading")[i].firstChild.nodeValue;
	              var wcrate_cell =0;// bR.getElementsByTagName("rate")[i].firstChild.nodeValue;
	              var excessrate = bR.getElementsByTagName("excessrate")[i].firstChild.nodeValue;
	              var qty = bR.getElementsByTagName("qty")[i].firstChild.nodeValue;
	            //  var eqty = bR.getElementsByTagName("eqty")[i].firstChild.nodeValue;
	              var ALLOTED_QTY1 =0;// bR.getElementsByTagName("ALLOTED_QTY")[i].firstChild.nodeValue;
	              var MULTIPLY_FACTOR1= bR.getElementsByTagName("MULTIPLY_FACTOR")[i].firstChild.nodeValue;              
	              var MIN_BILL_QTY=0;// bR.getElementsByTagName("MIN_BILL_QTY")[i].firstChild.nodeValue;
	    /*  if (parseInt(BENEFICIARY_TYPE_ID)>6)
	      {
	    	  
	    	      mul_qty=(parseFloat(MULTIPLY_FACTOR1)*parseFloat(qty));
	    	      
	    	      if (ALLOTED_FLG=="n" || ALLOTED_FLG=="N" || ALLOTED_FLG=="0" || ALLOTED_FLG=="")
	              {
	            	  amount=(parseFloat(mul_qty)*parseFloat(wcrate_cell));
	            	  
	              }	              
	              else
	              {    
	            	  difference= (parseFloat(mul_qty)-parseFloat(ALLOTED_QTY1));	              
		              eqty =difference;
		              if (eqty < 0)
		              eqty=0;
		              
		              if (difference > ALLOTED_QTY1)
		              {
						  difference=parseFloat(difference);
		              }	  
		              else
		              {
		            	 if( parseFloat(MIN_BILL_QTY)>0) 
		            		 difference=MIN_BILL_QTY;
		            	 else
		            		 difference=mul_qty;
		              }
										              
				              if (parseFloat(MIN_BILL_QTY)>0  )
				              {
						            	 if (parseFloat(mul_qty) > parseFloat(MIN_BILL_QTY))
										 {
										   if (parseFloat(ALLOTED_QTY1)>0)
										   amount=(parseFloat(ALLOTED_QTY1)*parseFloat(wcrate_cell));
										   
										 }
										 else
										 {
										 
											 	if (parseFloat(MIN_BILL_QTY)>0  )
										           		   amount=(parseFloat(MIN_BILL_QTY)*parseFloat(wcrate_cell));
										        else
										        		   amount=(parseFloat(difference)*parseFloat(wcrate_cell));
										            	  
							             }
				      		 }
		            	  	 else
		            	  	 {
		            	  	  amount=(parseFloat(difference)*parseFloat(wcrate_cell));
		            	     }
						 eamount=parseFloat(eqty)*parseFloat(excessrate);
		          }
              }
              else
              {
            	  
            	 
            	  if (parseFloat(mul_qty) > parseFloat(MIN_BILL_QTY))
	              {
	            	  amount=(parseFloat(mul_qty)*parseFloat(wcrate_cell));
	            	  
	              }
	              else
	              {
	            	  amount=(parseFloat(MIN_BILL_QTY)*parseFloat(wcrate_cell));
	            	  
	              }
            	  
              }        
              
	      */
	              mul_qty=parseFloat(qty);
              totamount=parseFloat(amount)+parseFloat(eamount);
              netamount=parseFloat(netamount)+parseFloat(totamount);
              
               
	              var reading="read"+i;
	              var new_row=cell("TR","",metersno,"");
	              /*
	               * major - TD ,TR
	             	item  -  input ,select ,
	             	value - value 
	             	type,- text,radio
	             	name - name
	             	size - size	
	             	sclass - styleclass
	             	url - url
	               */
	              net_qty+=parseFloat(qty);
	              net_eqty+=parseFloat(eqty);
	              
	              
	              var sno_cell=cell("TD","input","hidden","cmbmeter"+(i+1),metersno,2,"","","","","","","");
	              var cell_SUBDIV_OFFICE_ID=cell("TD","input","hidden","SUBDIV_OFFICE_ID"+(i+1),SUBDIV_OFFICE_ID,2,"","","","","","","");
	              var cell_SCHEME_SNO=cell("TD","input","hidden","SCHEME_SNO"+(i+1),SCHEME_SNO,2,"","","","","","","");
	              var cell_PR_SNO=cell("TD","input","hidden","PR_SNO"+(i+1),PR_SNO,2,"","","","","","","");
	              var cell_TARIFF_ID=cell("TD","input","hidden","TARIFF_ID"+(i+1),TARIFF_ID,2,"","","","","","","");
	              var cell_SCH_TYPE_ID=cell("TD","input","hidden","SCH_TYPE_ID"+(i+1),SCH_TYPE_ID,2,"","","","","","","");    
	             
	              var check_cell=cell("TD","input","checkbox","ch"+(i+1),0,7,"","","","","center","","");
	              var name_cell=cell("TD","label","","",meterlocation,7,"tdText","font-size:2","left","25%","","","");
	              
	              
	              var ini_reading_cell=cell("TD","input","text","prvread"+(i+1),ini_reading,7,"tb4","","text-align: right;","5%","center","","");
	              var read_cell=cell("TD","input","text","currread"+(i+1),closingreading,7,"tb4","","text-align: right;","5%","center","","");
	              var tot_reading_cell=cell("TD","input","text","totalread"+(i+1),qty,7,"tb4","","text-align: right;","5%","center","","");
	              var wcrate_cell=cell("TD","input","text","rate"+(i+1),wcrate_cell,2,"tb1","","text-align: right;","","center","","");
	              var MULTIPLY_FACTOR_cell=cell("TD","input","text","MULTIPLY_FACTOR"+(i+1),MULTIPLY_FACTOR,2,"tb0","","text-align: right;","","center","","");
	              var MULTIPLY_FACTOR_value_cell=cell("TD","input","text","MULTIPLY_FACTOR_value"+(i+1),parseFloat(MULTIPLY_FACTOR)*parseFloat(qty),7,"tb4","","text-align: right;","","center","","");

	              var wcamount_cell=cell("TD","input","text","amount"+(i+1),amount,7,"tb4","","text-align: right;","","center","","");
	              var excess_reading_cell=cell("TD","input","text","excess"+(i+1),eqty,7,"tb4","","text-align: right;","","center","","");
	              var excess_rate_cell=cell("TD","input","text","excessrate"+(i+1),excessrate,2,"tb1","","text-align: right;","","center","","");
	              var excess_amount_cell=cell("TD","input","text","excessamount"+(i+1),eamount,7,"tb4","","text-align: right;","","center","","");
	             
	              var net_amount_cell=cell("TD","input","text","subtotal"+(i+1),totamount,7,"tb4","","text-align: right;","","center","","");
	              var sno_label=cell("TD","label","","",(i+1),2,"","","","2%","","","");
	              
	              new_row.appendChild(sno_label);
	              new_row.appendChild(cell_SUBDIV_OFFICE_ID);
	              new_row.appendChild(cell_SCHEME_SNO);
	              new_row.appendChild(cell_PR_SNO);
	              new_row.appendChild(cell_TARIFF_ID);
	              new_row.appendChild(cell_SCH_TYPE_ID);
	              
	              
	              new_row.appendChild(sno_cell);
 	              new_row.appendChild(name_cell);
	 	          new_row.appendChild(read_cell);
	 	          new_row.appendChild(ini_reading_cell);
	 	          new_row.appendChild(tot_reading_cell);
	 	          // Ben Type > 6 only show the muliply factor,excess_read,excess amount
	 	         if (parseInt(BENEFICIARY_TYPE_ID)>6)
	 	         {
	 	        	 new_row.appendChild(MULTIPLY_FACTOR_cell);
	 	        	 new_row.appendChild(MULTIPLY_FACTOR_value_cell);
	 	         }
	 	         
	 	          
	 	        //  new_row.appendChild(wcrate_cell);
	 	       //   new_row.appendChild(wcamount_cell);
	 	          
	 	         if (parseInt(BENEFICIARY_TYPE_ID)>6)
	 	         {
	 	        //	 new_row.appendChild(excess_reading_cell);
	 	        //	 new_row.appendChild(excess_rate_cell);
	 	        //	 new_row.appendChild(excess_amount_cell);
	 	         }
	 	          
	 	         
	 	         
	 	         
	 	         
	 	          //new_row.appendChild(net_amount_cell);
	 	     
	              tbody.appendChild(new_row);
	               
	              
	         }
	  
	      document.getElementById("netexcessconsumption").value=eamount;
		  document.getElementById("netconsumption").value=amount;
		  document.getElementById("netwcamt").value=parseFloat(eamount)+parseFloat(amount);
	  var SCH_rc_count=bR.getElementsByTagName("rc_count")[0].firstChild.nodeValue;
	  var tot_wc_rc_amount=0;
	  for (i=0;i<SCH_rc_count;i++)
	 
	  {
		  var new_row=cell("TR","",metersno,"");

		  var SCH_SNO_OB=bR.getElementsByTagName("SCH_SNO_OB")[i].firstChild.nodeValue;
		  var MAINT_OB=bR.getElementsByTagName("MAINT_OB")[i].firstChild.nodeValue;
		  var WC_OB =bR.getElementsByTagName("WC_OB")[i].firstChild.nodeValue;
		  var YY_OB=bR.getElementsByTagName("YY_OB")[i].firstChild.nodeValue;
		  var rc_schemesno=bR.getElementsByTagName("rc_schemesno")[i].firstChild.nodeValue;
		  var AMOUNT =bR.getElementsByTagName("AMOUNT")[i].firstChild.nodeValue;
		  
		  var MAINT_OB_cell=cell("TD","input","hidden","MAINT_OB"+(i+1),MAINT_OB,7,"tb4","","text-align: right;","","center","","");
		  var WC_OB_cell=cell("TD","input","hidden","WC_OB"+(i+1),WC_OB,7,"tb4","","text-align: right;","","center","","");
		  var YY_OB_cell=cell("TD","input","hidden","YY_OB"+(i+1),YY_OB,7,"tb4","","text-align: right;","","center","","");
		  
          var rc_schemesno_cell=cell("TD","input","hidden","rc_schemesno"+(i+1),rc_schemesno,7,"tb4","","text-align: right;","","center","","");
          var rc_AMOUNT_cell=cell("TD","input","hidden","rc_schemeAMOUNT"+(i+1),AMOUNT,7,"tb4","","text-align: right;","","center","","");
		  new_row.appendChild(rc_schemesno_cell);
		  new_row.appendChild(MAINT_OB_cell);
		  new_row.appendChild(WC_OB_cell);
		  new_row.appendChild(YY_OB_cell);
		  new_row.appendChild(rc_AMOUNT_cell);
		  
		  tot_wc_rc_amount+=parseFloat(AMOUNT);
		  tbody.appendChild(new_row);
		  
	  }
	  document.getElementById("waterreceipt").value=tot_wc_rc_amount;
 
	  var rc_count_cell=cell("TD","input","hidden","rc_count",SCH_rc_count,7,"tb4","","text-align: right;","","center","","");
	  new_row.appendChild(rc_count_cell);
	  tbody.appendChild(new_row);
	  /*Scheme Wise Process End   
	   * */
	         document.getElementById("net_qty").value=net_qty;	
			document.getElementById("net_eqty").value=net_eqty;
	  		  
	  		
	  		document.getElementById("rows").value=row;
	  		 
  }  else  if (parseInt(process_code)==3)
  { 
	   
        var reading = bR.getElementsByTagName("meterreading")[0].firstChild.nodeValue;
        var rate = bR.getElementsByTagName("rate")[0].firstChild.nodeValue;
        var excessrate = bR.getElementsByTagName("excessrate")[0].firstChild.nodeValue;
        var cbtotal = bR.getElementsByTagName("cbtotal")[0].firstChild.nodeValue;    
        document.getElementById("prvread").value=reading;
         
        document.getElementById("rate").value=rate;
        document.getElementById("excessrate").value=excessrate;
        //if (cbtotal=="") cbtotal="0";	
       // document.getElementById("cbtotal").value=cbtotal;
  } else  if (parseInt(process_code)==6)
  {
	  var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
	  var tbody = document.getElementById("charge_body");
	  var table = document.getElementById("charge_data");                                                                                                     
	  var t=0;
	  for(t=tbody.rows.length-1;t>=0;t--){tbody.deleteRow(0);}
	  var len=bR.getElementsByTagName("sno").length;
	  document.getElementById("total_charge_row").value=len;
	  for (i=1;i<len;i++)
	 	{  
		          
	              var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
	              var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
	              var sno_cell=cell("TD","input","hidden","select"+(i+1),sno,2,"","","","2%","","onchange","");
	              
	              var check_cell=cell("TD","input","checkbox","ch"+(i+1),"",7,"","","","10%","","onclick","other_charges()");
	              var name_cell=cell("TD","label","","",name,7,"","font-size:2","","left","","");
	              var add_cell=cell("TD","input","text","add"+(i+1),0,5,"","","text-align: right;  ","10%","right","",""); 	             
	              var sub_cell=cell("TD","input","text","sub"+(i+1),0,5,"","","text-align: right;  ","10%","right","","");
	              var new_row=cell("TR","",sno,"");
	              new_row.appendChild(sno_cell);
	 	          
	 	          new_row.appendChild(name_cell);
	 	          new_row.appendChild(add_cell);
	 	          new_row.appendChild(sub_cell);
	 	         new_row.appendChild(check_cell);
	              tbody.appendChild(new_row);

	 	}
      			  var net_new_row=cell("TR","","","","","","","","","","","","");
       			  var net_null1_cell=cell("TD","label","","","",7,"","font-size:12;text-align: right;","","right","","","");
      			  var net_name_cell=cell("TD","label","","","Total",7,"","font-size:2;text-align: right;","","right","","","");
      			  var net_add_cell=cell("TD","input","text","net_add",0,5,"","","text-align: right;","10%","right","",""); 	             
	              var net_sub_cell=cell("TD","input","text","net_sub",0,5,"","","text-align: right;","10%","right","","");
	              
 	              net_new_row.appendChild(net_name_cell);
	              net_new_row.appendChild(net_add_cell);
	              net_new_row.appendChild(net_sub_cell);
	              net_new_row.appendChild(net_null1_cell);
	              tbody.appendChild(net_new_row);	
  }   else    if (parseInt(process_code)==4 || parseInt(process_code)==15)
	   
  { 
	 
	    //  Dispaly the all meter of Consumer 
	    // Sub Div Wise Total also Here 
	  
	   document.getElementById("pr_status").value=1; 
	  var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
	   
	  var tbody = document.getElementById("data_tbody");
	  var table = document.getElementById("data_table");                                                                                                     
	  var t=0;
	  for(t=tbody.rows.length-1;t>=0;t--){tbody.deleteRow(0);}
	  var len=bR.getElementsByTagName("metersno").length; 
	
	 
	  if (len!=0)
	  { 
	  var benname=bR.getElementsByTagName("benname")[0].firstChild.nodeValue;
	  var bentypevalue=bR.getElementsByTagName("bentypevalue")[0].firstChild.nodeValue;
	  var netamount=0;
		  //bR.getElementsByTagName("netamount")[0].firstChild.nodeValue;
	  
	  var p=0;
	  try
	  {
	  p=document.getElementById("spl").Value;
	  
	  }catch(e) {}
	  try
	  {
	   document.getElementById("ben_name").innerHTML=benname;
	   document.getElementById("bentypevalue").innerHTML=bentypevalue;
	  }catch(e) {}
	  
	   
	  try
	  {
      var ALLOTED_FLG_DES= bR.getElementsByTagName("ALLOTED_FLG_DES")[0].firstChild.nodeValue;
      document.getElementById("Alloated").innerHTML=ALLOTED_FLG_DES;
      var TARIFF_MODE_DES= bR.getElementsByTagName("TARIFF_MODE_DES")[0].firstChild.nodeValue;
      document.getElementById("traiff").innerHTML=TARIFF_MODE_DES;
	  }catch(e) {}
	  
	  
   	  for (i=0;i<len;i++)
	 	{  
   		  
   		  		var ALLOTED_FLG=bR.getElementsByTagName("ALLOTED_FLG")[i].firstChild.nodeValue;  
	              var metersno = bR.getElementsByTagName("metersno")[i].firstChild.nodeValue;
	             
	              var meterlocation = bR.getElementsByTagName("meterlocation")[i].firstChild.nodeValue;
	           //   var ALLOTED_QTY1 = bR.getElementsByTagName("ALLOTED_QTY")[i].firstChild.nodeValue;
	              var CHILD_METER_QTY  = bR.getElementsByTagName("CHILD_METER_QTY")[i].firstChild.nodeValue;
	              var METRE_FIXED    = bR.getElementsByTagName("METRE_FIXED")[i].firstChild.nodeValue;
	              var METRE_WORKING   = bR.getElementsByTagName("METRE_WORKING")[i].firstChild.nodeValue;
	              var closingreading =  bR.getElementsByTagName("closingreading")[i].firstChild.nodeValue ;
	             
	              var ini_reading = bR.getElementsByTagName("meterreading")[i].firstChild.nodeValue;
	              //var wcrate_cell = bR.getElementsByTagName("rate")[i].firstChild.nodeValue;
	              var excessrate = bR.getElementsByTagName("excessrate")[i].firstChild.nodeValue;
	               
	              var qty =roundNumber(bR.getElementsByTagName("qty")[i].firstChild.nodeValue,3);
	              var PR_SNO= bR.getElementsByTagName("PR_SNO")[i].firstChild.nodeValue;
	              var QTY_CONSUMED=  bR.getElementsByTagName("QTY_CONSUMED")[i].firstChild.nodeValue  ;
	              var QTY_CONSUMED_NET=  bR.getElementsByTagName("QTY_CONSUMED_NET")[i].firstChild.nodeValue ;
	              var eqty =0;
	              var eamount =0;
	              var totamount=0;
	              var SCH_NAME=bR.getElementsByTagName("SCH_NAME")[i].firstChild.nodeValue;
	              var  PROCESS_FLAG=bR.getElementsByTagName("PROCESS_FLAG")[i].firstChild.nodeValue;
	              var MULTIPLY_FACTOR1= bR.getElementsByTagName("MULTIPLY_FACTOR")[i].firstChild.nodeValue;
	              var ALLOTED_FLG_DES= bR.getElementsByTagName("ALLOTED_FLG_DES")[i].firstChild.nodeValue;
	               
	            //  var MIN_BILL_QTY= bR.getElementsByTagName("MIN_BILL_QTY")[i].firstChild.nodeValue;
	              
	              var TARIFF_MODE= bR.getElementsByTagName("TARIFF_MODE")[i].firstChild.nodeValue;
	              var BENEFICIARY_TYPE_ID=bR.getElementsByTagName("BENEFICIARY_TYPE_ID")[i].firstChild.nodeValue;
	        	   var BENEFICIARY_NAME=bR.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
 	              /*
	               * 
	               * 
	               * qty > MIN_BILL_QTY =>qty other wise MIN_BILL_QTY
	               * */
	              totamount=roundNumber( parseFloat(amount)+parseFloat(eamount),2);
	              netamount=roundNumber(parseFloat(netamount)+parseFloat(totamount),2);
 	              var reading="read"+(i+1);
	              var new_row=cell("TR","","","row"+(i+1),metersno,"","","","","","","",""); 	 
	              /*
	               * major - TD ,TR
	             	item  -  input ,select ,
	             	value - value 
	             	type,- text,radio
	             	name - name
	             	size - size	
	             	sclass - styleclass
	             	url - url
	               */
	             
	              //mul_qty=parseFloat(parseFloat(QTY_CONSUMED)*parseFloat(MULTIPLY_FACTOR1));
	                
	              if (QTY_CONSUMED_NET=='NaN' || QTY_CONSUMED_NET=="null" ) QTY_CONSUMED_NET="0";
	              mul_qty=parseFloat(QTY_CONSUMED_NET);
	              
	                
	             
	              mul_qty= roundNumber(parseFloat(mul_qty),3);
	                
  	              var name_cell=cell("TD","label","","",meterlocation,5,"tdText","font-size:2","","12%","left","","");
  	              var BENEFICIARY_NAME_cell=cell("TD","label","","",BENEFICIARY_NAME,7,"tdText","font-size:2","","12%","left","","");
  	              var cls=roundNumber(closingreading,3);
  	              if (isNaN(cls) || cls=="null" ) cls='-';
	              var ini_reading_cell=cell("TD","label","","prvread"+i,ini_reading,7,"tdText","","text-align: right;","4%","right","","");
	              var SCH_NAME_cell=cell("TD","label","","prvread"+i,SCH_NAME,7,"tdText","","text-align: left;","12%","left","",""); 
	              var read_cell=cell("TD","label","","currread"+i, cls,7,"tdText","","text-align: right;","4%","right","","");
	              var tot_reading_cell=cell("TD","label","","totalread"+i, parseFloat(qty)+parseFloat(CHILD_METER_QTY) ,7,"tdText","","text-align: right;","4%","right","","");
	              var MULTIPLY_FACTOR1_cell=cell("TD","label","","MULTIPLY_FACTOR"+i,MULTIPLY_FACTOR1,7,"tdText","","text-align: right;","4%","right","","");
	              var ALLOTED_QTY1_cell=cell("TD","label","","ALLOTED_QTY1"+i,ALLOTED_QTY1,7,"tdText","","text-align: right;","4%","right","","");
	              var MIN_BILL_QTY1_cell=cell("TD","label","","MIN_BILL_QTY"+i,MIN_BILL_QTY,7,"tdText","","text-align: right;","4%","right","","");
	              var final_qty=cell("TD","label","","qty"+i,qty,7,"tdText","","text-align: right;","4%","right","","");
	              var tot_cons_cell=cell("TD","label","","tot_cons"+i,mul_qty,7,"tdText","","text-align: right;","5%","right","","");
	              var wcrate_cell=cell("TD","label","","rate"+i,wcrate_cell,2,"tdText","","text-align: right;","4%","right","","");
	              var wcamount_cell=cell("TD","label","","amount"+i,amount,7,"tdText","","text-align: right;","4%","right","","");
	              var excess_reading_cell=cell("TD","label","","excess"+i,eqty,7,"","","text-align: right;","4%","right","","");
	              var excess_rate_cell=cell("TD","label","","excessrate"+i,excessrate,2,"","","text-align: right;","4%","right","","");
	              var excess_amount_cell=cell("TD","label","","excessamount"+i,eamount,7,"tdText","","text-align: right;","4%","right","","");
	              var net_amount_cell=cell("TD","label","","subtotal"+i,totamount,7,"tdText","","text-align: right;","4%","right","","");
	              var sno_cell=cell("TD","label","","",(i+1),7,"tdText","","text-align: right;","2%","center","","");
	              var CHILD_METER_QTY_cell =cell("TD","label","","tot_cons"+i,parseFloat(CHILD_METER_QTY),7,"tdText","","text-align: right;","4%","right","","");
	             
	              if (METRE_FIXED=='y' || METRE_FIXED=='yes' || METRE_FIXED=='Yes' || METRE_FIXED=='Y')  
	            	  METRE_FIXED="Yes";
	              else
	            	  METRE_FIXED="No";    
	              
	              if (METRE_WORKING=='y' || METRE_WORKING=='Y' || METRE_WORKING=='yes' || METRE_WORKING=='Yes') 
	            	  METRE_WORKING="Yes";
	              else
	            	  METRE_WORKING="No";
	              
	              var msg_v="";
	              
	              
	              if (parseInt(process_code)==15)
		 	       {
		 	    	   
		 	         if (PROCESS_FLAG=='V')
		 	         {
		 	        	msg_v='Revoke';
		 	         }
		 	         else if (PROCESS_FLAG=='CR')
		 	         {
		 	        	 	  if (p==0)
		 	        	 		msg_v='Validate';
		 	         }
		 	         else
		 	         {
		 	        	msg_v='VD & FR';
		 	         }
		 	       } 
		 	
	              var msg_Status="";
	              if (PROCESS_FLAG=='V')
		 	         {
	            	  msg_Status='Validated ';
		 	         }
		 	         else if (PROCESS_FLAG=='CR')
		 	         {
		 	        	 msg_Status='Created ';  	
		 	         }
		 	         else
		 	         {
		 	        	msg_Status=' Freezed ';
		 	         }
	              
	              
	              
	              
	              var METRE_FIXED_cell   =cell("TD","label","","METRE_FIXED"+i,METRE_FIXED,7,"tdText","","text-align: right;","3%","right","","");
	              var METRE_WORKING_cell   =cell("TD","label","","METRE_WORKING"+i,METRE_WORKING,7,"tdText","","text-align: right;","3%","right","","");
	              var approve_cell   =cell("TD","input","button","Validated"+(i+1),msg_v,7,"","","text-align: center;","4%","right","onclick","data_show('validate','"+(i+1)+"','"+PR_SNO+"')");
	              var approve_cell2   =cell("TD","label","","msg_lab"+(i+1),msg_Status,7,"fnt","","text-align: center;","4%","right","","");
 	              var PR_SNO_cell   =cell("TD","input","hidden","PR_SNO"+i,PR_SNO,7,"","","text-align: right;","5%","right","","");
 	              
 	              var PROCESS_FLAG_cell =cell("TD","input","button","Validated"+(i+1),msg_v,7,"newbut","","text-align: center;","4%","right","onclick","data_show('notvalidate','"+(i+1)+"','"+PR_SNO+"')");
 	              var PROCESS_delete_cell =cell("TD","input","button","DeValidated"+(i+1),"Delete",7,"","","text-align: center;","4%","right","onclick","data_show('Delete','"+(i+1)+"','"+PR_SNO+"')");
 	              
 	              new_row.appendChild(PR_SNO_cell); 
	              new_row.appendChild(sno_cell);
	              
	              
	              if (p==0)
	  	              new_row.appendChild(BENEFICIARY_NAME_cell);
	             
	            	  
  	              new_row.appendChild(name_cell);
	              
  	              new_row.appendChild(SCH_NAME_cell);
  	             
  	              if (p==0)
  	              {
  	              new_row.appendChild(METRE_FIXED_cell);
  	              new_row.appendChild(METRE_WORKING_cell);
  	              }
  	           
  	           if ( parseInt(process_code)==4)
  	           {
  	        	  new_row.appendChild(METRE_FIXED_cell);
  	              new_row.appendChild(METRE_WORKING_cell);
  	           }
	 	         
	 	     
	 	          	          
  	         new_row.appendChild(ini_reading_cell);
  	         new_row.appendChild(read_cell);
	 	       
	 	      
	 	  //    new_row.appendChild(tot_reading_cell);
  	       
	 	        new_row.appendChild(CHILD_METER_QTY_cell);
	 	     //  if (parseInt(process_code)==15)
	 	  //     {
	 	        	new_row.appendChild(final_qty);
	 	    	   	new_row.appendChild(MULTIPLY_FACTOR1_cell);
	 	      
	 	   //    }
	 	         if (p==0)
	 	          new_row.appendChild(tot_cons_cell);
	 	          
	 	       
	 	       if (parseInt(formflag)==1)
	 	       {
	 	         if (parseInt(BENEFICIARY_TYPE_ID)>6)
	 	         {
	 	          new_row.appendChild(MULTIPLY_FACTOR1_cell);
	 	         }
	 	         
	 	          new_row.appendChild(tot_cons_cell);
	 	       } 
	 	         
	 	     
	 	       if (parseInt(process_code)==15)
	 	       {
	 	    	  
	 	    	  new_row.appendChild(approve_cell2);
	 	         if (PROCESS_FLAG=='V')
	 	         {
	 	        	new_row.appendChild(PROCESS_FLAG_cell);
	 	         }
	 	         else if (PROCESS_FLAG=='CR')
	 	         {
	 	        	 	  if (p==0)
	 		 	          new_row.appendChild(approve_cell);
	 	        	 	  new_row.appendChild(PROCESS_delete_cell);
	 	        	 	
	 	        	 	  
	 	         }
	 	         
	 	         else
	 	         {
	 	        	var empoty_cell   =cell("TD","label","","","",7,"tdText","","text-align: right;","5%","right","","");
	 	        	     new_row.appendChild(empoty_cell);
	 	         }
	 	       }
	 	 
	              tbody.appendChild(new_row);
	              
	             
	         }
   	  try
   	  {
  	  		document.getElementById("net_amount").innerHTML=netamount;	
   	  }catch(e) {}
   	  
	  }// end (len!=0)
	  else
	  {
		  msgload("No Data ",1)
		  
	  }
  }     else     if (parseInt(process_code)==11)
   { 
	     
	      var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
	  
	     
		  var tbody = document.getElementById("ben_meter_body_ed");
		  var table = document.getElementById("ben_meter_data_ed");                                                                                                     
		  var t=0;
		  for(t=tbody.rows.length-1;t>=0;t--){tbody.deleteRow(0);}
		  var len=bR.getElementsByTagName("metersno").length;
		  var benname="";
		  
		  try
		  { 
			  benname=bR.getElementsByTagName("BENEFICIARY_NAME")[0].firstChild.nodeValue;
			  
		  }catch(e) {
			  benname="-";
			  
		  }
		  try
		  { 
		  
					  var BEN_TYPE_DESC=bR.getElementsByTagName("BEN_TYPE_DESC")[0].firstChild.nodeValue;
					  var NET_CONSUMED =0;//bR.getElementsByTagName("NET_CONSUMED")[0].firstChild.nodeValue;
					  var MONPR_SNO=bR.getElementsByTagName("MONPR_SNO")[0].firstChild.nodeValue;
					  var YEAR=bR.getElementsByTagName("YEAR")[0].firstChild.nodeValue;
					  var MONTH=bR.getElementsByTagName("MONTH")[0].firstChild.nodeValue;		 
					  document.getElementById("ben_name").innerHTML=benname;
					  document.getElementById("bentype").innerHTML=BEN_TYPE_DESC;		  
					  document.getElementById("year").value=YEAR;
					  document.getElementById("vmonth").value=monthselect(MONTH);		 
					  document.getElementById("rowcnt_meter").value=len;	
					  document.getElementById("MONPR_SNO").value=MONPR_SNO;
			  
			    for (i=0;i<len;i++)
			 	{    
			          var PARENT_METRE = bR.getElementsByTagName("PARENT_METRE")[i].firstChild.nodeValue;
			          var PARENT_H_METRE_cell=cell("TD","input","hidden","PARENT_H_METRE"+(i+1),PARENT_METRE,2,"","","","2%","","","");
		              var metersno = bR.getElementsByTagName("metersno")[i].firstChild.nodeValue;
		              var meterlocation = bR.getElementsByTagName("METRE_LOCATION")[i].firstChild.nodeValue;
		              var SCHEME_NAME = bR.getElementsByTagName("SCHEME_NAME")[i].firstChild.nodeValue;
		              var PR_SNO= bR.getElementsByTagName("PR_SNO")[i].firstChild.nodeValue;
		              var METRE_TYPE= bR.getElementsByTagName("METRE_TYPE")[i].firstChild.nodeValue;
		              var PROCESS_FLAG= bR.getElementsByTagName("PROCESS_FLAG")[i].firstChild.nodeValue;
		              var METRE_FIXED="";
					  var METRE_WORKING="";
					  var ALLOTED_QTY = bR.getElementsByTagName("ALLOTED_QTY")[i].firstChild.nodeValue;
						  try
						  {
							  METRE_FIXED = bR.getElementsByTagName("METRE_FIXED")[i].firstChild.nodeValue;					  
						  }catch(e) {
							  METRE_FIXED="No";
						  }
 					
						  //var METRE_CLOSE_READING= bR.getElementsByTagName("METRE_CLOSE_READING")[i].firstChild.nodeValue;
						  try
						  {
							  METRE_WORKING = bR.getElementsByTagName("METRE_WORKING")[i].firstChild.nodeValue;
						  }catch(e) {
							  METRE_FIXED="No";
						  }
					  
						  var opreading_cell_flag="";
						  var clsreading_cell_flag="";
							        if (METRE_FIXED == "No" ||  METRE_FIXED == "no") 
							      	{ 
								  		opreading_cell_flag="#readonly";
								  		clsreading_cell_flag="#readonly";
							      	 } else if (METRE_WORKING == "No"){
									    opreading_cell_flag="#readonly";
								  		clsreading_cell_flag="#readonly";
							      	 }
							      	 else
							      	 {
							      		 opreading_cell_flag="";
								  		 clsreading_cell_flag="";
							      	 }
 					  
							        var CHILD_METER_QTY=bR.getElementsByTagName("CHILD_METER_QTY")[i].firstChild.nodeValue;
							        var PR_SNO_cell=cell("TD","input","hidden","PR_SNO"+(i+1),PR_SNO,2,"","","","","","","");
							        var metersno_cell=cell("TD","input","hidden","METRE_SNO"+(i+1),metersno,2,"","","","10%","center","","");
							        var meter_available_cell=cell("TD","label","","METRE_FIXED"+(i+1),METRE_FIXED,2,"select","","","10%","center","center","");
							        var allocatedqty_cell=cell("TD","input","hidden","ALLOTED_QTY"+(i+1),ALLOTED_QTY,2,"","","","10%","right","","");
							        var flag_desc="";
							        switch (PROCESS_FLAG)
							        {
							        	case 'CR':
							        			flag_desc="Created";
							        			break;
							        	case 'V':
							        			flag_desc="Validated";
							        			break;
							        	case 'FR':
							        			flag_desc=" Freezed ";
							        			break;
							        	default: 
							        			flag_desc=".";
							        			break;
							        			
							        }
		              
							        var PROCESS_FLAG_cell=cell("TD","label","","PROCESS_FLAG"+(i+1),flag_desc,2,"","","color: red","5%","center","center","");            
							        var meterworking_cell=cell("TD","label","","METRE_WORKING"+(i+1),METRE_WORKING,2,"select","","","10%","center","onchange","statuschange("+(i+1)+")");
							        var METRE_INITIAL_READING = bR.getElementsByTagName("METRE_INITIAL_READING")[i].firstChild.nodeValue;
		              	            var METRE_CLOSING_READING= bR.getElementsByTagName("METRE_CLOSING_READING")[i].firstChild.nodeValue;
		              	            //  	var closingreading = bR.getElementsByTagName("closingreading")[i].firstChild.nodeValue;
		              	            //  var excessrate = bR.getElementsByTagName("excessrate")[i].firstChild.nodeValue;
		              	            //  var qty = bR.getElementsByTagName("qty")[i].firstChild.nodeValue;
		              	            //   var eqty = bR.getElementsByTagName("eqty")[i].firstChild.nodeValue;		                        
		              	            var METRE_TYPE_cell=cell("TD","input","hidden","METRE_TYPE"+(i+1),METRE_TYPE,2,"","","","2%","","","");
		              	            var reading="read"+i;
		              	            var new_row=cell("TR","",metersno,"");
		              	            /*
							               * major - TD ,TR
							             	item  -  input ,select ,
							             	value - value 
							             	type,- text,radio
							             	name - name
							             	size - size	
							             	sclass - styleclass
							             	url - url
		              	             */

		             
		              	            var QTY_CONSUMED =0;
		              	            if ( METRE_WORKING=="Yes")
		              	            {
		              	            	QTY_CONSUMED = bR.getElementsByTagName("QTY_CONSUMED")[i].firstChild.nodeValue;
		              	            }  
		              	            else
		              	            {
		              	            	var QTY_CONSUMED_NET =bR.getElementsByTagName("QTY_CONSUMED_NET")[i].firstChild.nodeValue;
		              	            	QTY_CONSUMED = QTY_CONSUMED_NET;
		              	            }
		             
		              
		              	            var name_cell=cell("TD","label","","",meterlocation,7,"tdText","font-size:2","","left","","","");
		              	            var SCHEME_NAME_cell=cell("TD","label","","",SCHEME_NAME,7,"tdText","font-size:2","","","left","","");
		              	            var METRE_INITIAL_READING_cell=cell("TD","input","text","METRE_INIT_READING"+(i+1),METRE_INITIAL_READING,7,"tb4","","text-align: right;"+opreading_cell_flag,"5%","right","onKeyup","isInteger(this,9,event),digit_control('METRE_INIT_READING"+(i+1)+"',3)");
		              	            var METRE_CLOSING_READING_cell=cell("TD","input","text","read"+(i+1),METRE_CLOSING_READING,7,"tb4","","text-align: right"+clsreading_cell_flag,"5%","right","onblur#onKeyup","calcuate("+(i+1)+")#isInteger(this,9,event),digit_control('read"+(i+1)+"',3)");
		              	            var QTY_CONSUMED_cell=cell("TD","input","text","nounit"+(i+1),QTY_CONSUMED,7,"tb4","","text-align: right;","5%","right","onblur#onKeyup","calcuate("+(i+1)+")#isInteger(this,9,event),digit_control('nounit"+(i+1)+"',3)"); 
		              	            var CHILD_METER_QTY_cell=cell("TD","input","text","PARENT_METER"+(i+1),CHILD_METER_QTY,7,"tb4","","text-align: right;"+clsreading_cell_flag,"5%","right","onblur","calcuate("+(i+1)+")");
		              	            NET_CONSUMED=parseFloat(NET_CONSUMED)+parseFloat(QTY_CONSUMED);		              	           
		              	            new_row.appendChild(PR_SNO_cell);
		              	            new_row.appendChild(name_cell);
		              	            new_row.appendChild(METRE_TYPE_cell);
		              	            new_row.appendChild(PARENT_H_METRE_cell);
		              	            new_row.appendChild(allocatedqty_cell);
		              	            new_row.appendChild(PROCESS_FLAG_cell);
		              	            new_row.appendChild(SCHEME_NAME_cell);
		              	            new_row.appendChild(meter_available_cell);
		              	            new_row.appendChild(meterworking_cell);
		              	            new_row.appendChild(METRE_INITIAL_READING_cell);
		              	            new_row.appendChild(METRE_CLOSING_READING_cell);
		              	            new_row.appendChild(CHILD_METER_QTY_cell);	  	            
		              	            new_row.appendChild(QTY_CONSUMED_cell);
		              	            new_row.appendChild(metersno_cell);		              	           
		              	            tbody.appendChild(new_row);
		            
		         }
		  
		  document.getElementById("netunit").value=NET_CONSUMED;
		 
		  }catch (e) {}
   }
 }

 
 
 function callServer(command)
 {
	url="../../../../../sample?command=Report";        
        document.billdemand.action=url
        document.billdemand.method="post";
        document.billdemand.submit();
 }
 
 function cal(cal_process)
  {
  
  if (cal_process==1)
  {
        //   var dec_format=new Packages.java.text.DecimalFormat(".00") 
        var prvread=document.getElementById("prvread").value;
        var currread=document.getElementById("currread").value;
        var totalread=0;
        if (prvread=="") prvread="0";
        if (currread=="") currread="0";
        if (parseFloat(currread) > parseFloat(prvread))
        {
             totalread=parseFloat(currread)-parseFloat(prvread);
             var rate=document.getElementById("rate").value;
             document.getElementById("totalread").value=totalread;
             amount=parseFloat(totalread)*parseFloat(rate);
             document.getElementById("amount").value=amount;
             var eamount=document.getElementById("excessamount").value;
             if (eamount=="") eamount="0";
             document.getElementById("grossamount").value=parseFloat(amount)+parseFloat(eamount);          
             net_cal();
             }
        else
        {
            msgload("Check the reading! ",1)
        }
   }
    if (cal_process==2)
    {     
        document.getElementById("grossamount").value=0;
        var excessread=document.getElementById("excess").value;
        var excessrate=document.getElementById("excessrate").value;
        if (excessread=="") excessread="0";
        if (parseFloat(excessread) >= 0)
        {                
             document.getElementById("excessamount").value=parseFloat(excessread)*parseFloat(excessrate);;
             
        }
        document.getElementById("grossamount").value=0;
        document.getElementById("grossamount").value=parseFloat(document.getElementById("amount").value)+
        net_cal();
    } 
 }
    
 function net_cal()
 {
	 var grossamount=document.getElementById("grossamount").value;
	 var cbtotal=document.getElementById("cbtotal").value;
	 var totalreceipt=document.getElementById("totalreceipt").value;
	 var balance=parseFloat(cbtotal)-parseFloat(totalreceipt);
	 document.getElementById("balance").value=balance;
	 var percen=1;
	 var penalty=(parseFloat(balance)*parseFloat(percen))/100;
	 document.getElementById("penalty").value=penalty;
	 document.getElementById("nettotal").value=parseFloat(penalty)+parseFloat(grossamount);
 }
 
 function month_select(input_value)
 {
	 	var input_date=new String(input_value.value);
	 	var dp=input_date.split("/");
	 	document.getElementById("fmonth").value=dp[1];
	 	document.getElementById("fyear").value=dp[2];
 }
 
 function hide_div()
 {
	 document.getElementById('othercharge').style.visibility = 'hidden';

}
 function show_div()
 {
	 document.getElementById('othercharge').style.visibility = 'visible';
	 data_show('show',6,0)
}
 function selectAll()
 {
  try
	 {
	 var rows=document.getElementById("rows").value;
	 
	 for (i=1;i<=rows;i++)
	 {
		 document.getElementById("ch"+i).checked=true;
	 }
   }catch(e)
	 {
		msgload("--------------------------\n"+e.message,1)

	 }	 
 }
 function test()
 {
	 var rows=document.getElementById("total_charge_row").value;
	 for (i=1;i<=rows;i++)
	 {
		 msgload(document.getElementById("add"+i).value,1)
	 }
 }
 function calculate()
 {
	 var cbtotal=document.getElementById("cbtotal").value;
	 var Water_Charges=document.getElementById("waterreceipt").value;
	 var Interest=document.getElementById("interestreceipt").value;
	 var Yester_Year=document.getElementById("yesteryearreceipt").value;
	 var charge_MAINT_COLN=document.getElementById("MAINT_COLN").value;
	 if (charge_MAINT_COLN=="") charge_MAINT_COLN="0";
	 var bal =parseFloat(cbtotal)-(parseFloat(Water_Charges)+parseFloat(Interest)+parseFloat(Yester_Year)+parseFloat(charge_MAINT_COLN));
	 if (parseFloat(bal) > 0)
		document.getElementById("balance").value=bal;
	 else
		document.getElementById("balance").value=0;
	 document.getElementById("penalty").value=(parseFloat(cbtotal)*parseFloat(document.getElementById("penaltyint").value))/100;
 }
 
 function other_charges()
 {
	 var total_charge_row=document.getElementById("total_charge_row").value;
	 var total_add=0;
	 var total_sub=0;
	 try
	 {
		 for (i=1;i<=parseInt(total_charge_row);i++)
	 	{
			  
			 if (document.getElementById("ch"+i).checked)
			 {
			 
				 total_add+=parseFloat(document.getElementById("add"+i).value);
				 total_sub+=parseFloat(document.getElementById("sub"+i).value);
			 }
	 	}
	 }catch(e)
	 {
		 total_add=0;
		 total_sub=0;
	 }
	 document.getElementById("net_add").value=total_add;
	 document.getElementById("net_sub").value=total_sub;
 }
 function net_calculation()
 {
	 var nettotal=0;
	 var net_add=0;
	 var net_sub=0;
	 try
	 {
	 var netwcamt=document.getElementById("netwcamt").value;
	 if (netwcamt=="") netwcamt="0";
	 var penalty=document.getElementById("penalty").value;
	 if (penalty=="") penalty="0";
	 var othercharges=document.getElementById("othercharges").value;
	 if (othercharges=="") othercharges="0";
	 	try
	 	{
	 		net_add=document.getElementById("net_add").value;
	 		net_sub=document.getElementById("net_sub").value;
	 	}catch(e){}
	 		nettotal=(parseFloat(othercharges)+parseFloat(netwcamt)+parseFloat(penalty)+parseFloat(net_add))-parseFloat(net_sub)
	 }catch(e)
	 { 
		alert("Problem Occur in net_calculation") 
	 }
	 

	 document.getElementById("nettotal").value=nettotal;
 }
 
 function form_val()
 {
	 var res="0";
	  var  WC_COLN=document.getElementById("waterreceipt").value;
	  var  WC_INT_COLN=document.getElementById("interestreceipt").value;
      var  OTHER_MTH_TOTAL=document.getElementById("othercharges").value;
      var  BILL_PERIOD_FROM=document.getElementById("datefrom").value;
      var  BILL_PERIOD_TO=document.getElementById("dateto").value;
      if (BILL_PERIOD_FROM=="" || BILL_PERIOD_TO=="" )
      {
    	alert(" from/to date should be select !")
    	res=1;
      }else if (OTHER_MTH_TOTAL==""   )
      {
    	alert("total other charge should not blank  !")
    	res=1;
      }else if (WC_INT_COLN=="" || WC_COLN==""   )
      {
    	alert("some releated collection value missing  !")
    	res=1;
      }
      var ret=date_val('datefrom','dateto');
      
      
      if (ret==0 && res==0)
    	  res=0;
      else
    	  res=1;
    	  
      return res;
 }
 
 function dmd_view()
 {
	 var maxsno=document.getElementById("dmdlist").value;
	// v=window.open("Bill_Demand_Report.jsp?maxsno="+maxsno,'windowname1','width=1900, height=1700')
	if (maxsno!=0) 
	 document.getElementById("ifr").src = "Bill_Demand_Report.jsp?maxsno="+maxsno;
	else
		document.getElementById("ifr").src="NoData.jsp";

 }
 
 function myround(inp,round)
 {
	 
	 var inv=inp;
	 var tot=round;
	 var v="";
	 var str=new String(inv);
	 var str_v=str.split('.');
 	 
	 if (parseInt(str_v.length)==1)
	 {
		 
		 str=str+".00";
		 return str;
	 }
	 else
	 {  
		 str=roundNumber(inp,round)
		 return str;
	 }
	
 }
 function roundNumber(inputNumber, numberOfDigits)
 {

     var multiple = Math.pow(10, numberOfDigits);
     var output = Math.round(inputNumber * multiple) / multiple;
     return output;

 }

 