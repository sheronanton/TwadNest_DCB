var process_code=0;
var input_row_no=0;
var display_table="";
var display_body="";
var sub_flag=0;
var add_flag=0;
var c_value=0;
var flag=0;
function charge_select(row)
{
	c_value=row
}

function charge_show(command,process,input_value)
{
	flag=0;
	process_code=process;
	if (command=='jshow')
	{
		 url="../../../../../Dcb_other_charge?command="+command+"&input_value="+input_value+"&process_code="+process_code
	}
	
	if (command=='show')  
 	{    	 
       if (process==1 || process==2) url="../../../../../Dcb_other_charge?command="+command+"&input_value="+input_value+"&process_code="+process_code
 	}
	
	if (command=='update')
	{
		try
		{
		var len=document.getElementById("rowcnt").value;
		var type="typevalue"+c_value;			
		
		url="../../../../../Dcb_other_charge";
		url+="?command="+command;
		url+="&process_code="+process_code;		
		
		url+="&typesno="+document.getElementById("typesno"+c_value).value;
		url+="&typevalue="+document.getElementById(type).value; 
		url+="&ac="+document.getElementById("ac"+c_value).value; 
		if (document.getElementById("addch"+c_value).checked)
		url+="&flag=y"; 
		else
		url+="&flag=n"; 
		}catch(e) {flag=1;
			alert("Please  check the checkbox which one modified item !")} 
		
	}
	
	if (command=='add')  
	{
		if (process_code==3)
		{
			var len=document.getElementById("rowcnt").value;
			var type="typevalue"+(parseInt(len)+1);			
			
			url="../../../../../Dcb_other_charge";
			url+="?command="+command;
			url+="&process_code="+process_code;			 
			url+="&typevalue="+document.getElementById(type).value; 
			url+="&ac="+document.getElementById("ac"+(parseInt(len)+1)).value; 
			if (document.getElementById("addch"+(parseInt(len)+1)).checked)
			url+="&flag=y"; 
			else
			url+="&flag=n"; 
			 
		}
		if (process_code==4)
		{
			var len=document.getElementById("rowcnt").value;
			for (i=1;i<=(parseInt(len)-1);i++)
			{
			try
			{
				
			}catch(e)
			{
				
				
			}
				
			}
			
			var type="typevalue"+(parseInt(len)+1);			
			
			url="../../../../../Dcb_other_charge";
			url+="?command="+command;
			url+="&process_code="+process_code;			 
			url+="&typevalue="+document.getElementById(type).value;//MONTH
			 
		}
	}
	 
	 if (flag==0)
	 {
				var xmlobj=createObject();
			    xmlobj.open("GET",url,true);
			    xmlobj.onreadystatechange=function()
			    { 
			    	 
			    	charge_process(xmlobj,command,input_value);
			    	
			    }
			    xmlobj.send(null);
	 }
}
function charge_process(xmlobj,command,input_value)
{	 
	 
	 
 if (xmlobj.readyState==4)
 {	   
 	 
	if (xmlobj.status==200)
    {
	
			if(command=='show')
			{	
				charge_result(xmlobj,'show');
			}
			if(command=='add')
			{
				alert("Process Completed.... ")
				charge_show('show',1,'0')
			 	window.location.reload();
			 	
				
			}
			if(command=='update')
			{
				alert("Process Completed.... ")
				charge_show('show',1,'0')
			 	window.location.reload();
			 	
				
			}
    }
 }
}

function charge_result(xmlobj,command)
{	
		var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
		 
		var tbody = document.getElementById("charge_body");
		var table = document.getElementById("charge_data");                                                                                                     
		var t=0;
		for(t=tbody.rows.length-1;t>=0;t--){tbody.deleteRow(0);}
 		
		var len=""
		try
		{
			len=bR.getElementsByTagName("row")[0].firstChild.nodeValue;
		}catch(e){len=0;}
		document.getElementById("rowcnt").value=len;
 		 for (i=0;i<len;i++)
		 { 
			 
			 var TYPE_SNO = bR.getElementsByTagName("TYPE_SNO")[i].firstChild.nodeValue;
			 var TYPE_DESC = bR.getElementsByTagName("TYPE_DESC")[i].firstChild.nodeValue;
			 var ACCOUNTCODE = bR.getElementsByTagName("ACCOUNTCODE")[i].firstChild.nodeValue;
			 var ADDFLAG = bR.getElementsByTagName("ADDFLAG")[i].firstChild.nodeValue;
	 	 	  var new_row=cell("TR","","","row1",1,2,"","","","","","","");//13
  	 	 	  var check_cell=cell("TD","input","checkbox","ch"+(i+1),0,7,"","","","2%","","onclick","charge_select("+(i+1)+")");
 	 	 	  var TYPE_SNO_cell=cell("TD","input","hidden","typesno"+(i+1),TYPE_SNO,7,"","","font-size:14px;","2%","center","","");
 	 	      var sno_cell=cell("TD","label","","",(i+1),7,"","","font-size:14px;","","center","","");
 
	 		  var TYPE_DESC_cell=cell("TD","input","text","typevalue"+(i+1),TYPE_DESC,10,"tb6","","font-size:14px;","","center","","");
 	 		  var AC_cell=cell("TD","input","text","ac"+(i+1),ACCOUNTCODE,50,"tb5","","font-size:14px;","","center","","");
 	 	 	  var add_check_cell=cell("TD","input","checkbox","addch"+(i+1),ADDFLAG,7,"","checked","","2%","center","","");
 	 		  new_row.appendChild(TYPE_SNO_cell);
 	 		  new_row.appendChild(sno_cell);
 	 		  new_row.appendChild(check_cell);
 	 		  new_row.appendChild(TYPE_DESC_cell);
	 		
	 		  new_row.appendChild(AC_cell);
	 		  new_row.appendChild(add_check_cell);
  	 		  tbody.appendChild(new_row);
		 }
		 
		  var new_row1=cell("TR","","","row1",1,2,"","","","","","","");//13
		  var new_sno_cell=cell("TD","label","","",(parseInt(len)+1),7,"","","font-size:14px;","","center","","");
		  var blank_sno_cell=cell("TD","label","","","",7,"","","font-size:14px;","","center","","");
		  var new_TYPE_DESC_cell=cell("TD","input","text","typevalue"+(parseInt(len)+1),"",50,"tb6","","font-size:14px;","","center","","");
		  var new_AC_cell=cell("TD","input","text","ac"+(i+1),0,50,"tb5","","font-size:14px;","","center","","");
		  var new_add_check_cell=cell("TD","input","checkbox","addch"+(i+1),0,7,"","","","2%","center","onclick","");
		  new_row1.appendChild(new_sno_cell);
		  
		  new_row1.appendChild(blank_sno_cell);
		  new_row1.appendChild(new_TYPE_DESC_cell);
		  new_row1.appendChild(new_AC_cell);
		  new_row1.appendChild(new_add_check_cell);
		  tbody.appendChild(new_row1);
		  
		 	 
}