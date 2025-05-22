function getTransport()
{
 var req=false;
 try
 {
	 req=new ActiveXobject("Msxml2.XMLHTTP");
 }
 catch(e)
{
	 try
{
	 req=new ActiveXobject("Microsoft.XMLHTTP");
}catch(e2)
{
	req=false;
}
}
if(!req && typeof XMLHttpRequest !='undefined')
	{
	req=new XMLHttpRequest();
	}
return req;
}


function CheckDatadel()
{
	//	alert("hi");
	var old_office_id=new Array();
	var y=document.getElementById("pyear").value;
	 var m=document.getElementById("pmonth").value;
	 var id=document.getElementById("office").value;
//	 alert("y is ------->"+y);
//	 alert("m is ------->"+m);
//	 alert("id is ------->"+id);
	var url ="../../../../../Office_Shift_new?command=CheckDatadel&y="+y+"&m="+m+"&id="+id;
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
	if(req.readyState==4)
		{
	//	alert(req.responseText);
		if(req.status==200)
		{
			var response=req.responseXML.getElementsByTagName("response")[0];
			var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
			var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if(command=="CheckDatadel")
			{
			if(flag=="success")
				{
	   		        alert("Deleted Successfully");
			        }  else
					   {
				       	alert("No change");
				       }
					    			
				}}}};
	req.send(null);
	
}



function CheckData()
{
	//	alert("hi");
	var old_office_id=new Array();
	var y=document.getElementById("pyear").value;
	 var m=document.getElementById("pmonth").value;
	 var id=document.getElementById("office").value;
//	 alert("y is ------->"+y);
//	 alert("m is ------->"+m);
//	 alert("id is ------->"+id);
	var url ="../../../../../Office_Shift_new?command=CheckData&y="+y+"&m="+m+"&id="+id;
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
	if(req.readyState==4)
		{
	//	alert(req.responseText);
		if(req.status==200)
		{
			var response=req.responseXML.getElementsByTagName("response")[0];
			var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
			var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if(command=="CheckData")
			{
			if(flag=="success")
				{
			//	alert("inside ");
				var len=response.getElementsByTagName("beneficiary_name").length;
				//	  alert("inside len"+len);
if(len!=0)
	{
	 alert("Delete the Beneficiary bill and Regenerate for the following Ben");
					 for ( var i = 0; i <= len; i++)
					   {
				    old_office_id[i]=response.getElementsByTagName("beneficiary_name")[i].firstChild.nodeValue;
					    alert("ben name is  -"+old_office_id[i]);
					   }
					
					
	}else
	{
	 alert("All beneficiaies are Correct");
	 	 
						 
		}
				
			//	var beneficiary_name=response.getElementsByTagName("beneficiary_name")[0].firstChild.nodeValue;
			//	alert("beneficiary_name is"+beneficiary_name);
				}}}}};
	req.send(null);
	 req1.send(null);

}


function CheckMiss()
{
//	alert("hi");
	var old_office_id=new Array();
	var y=document.getElementById("pyear").value;
	 var m=document.getElementById("pmonth").value;
	 var id=document.getElementById("office").value;
//	 alert("y is ------->"+y);
//	 alert("m is ------->"+m);
//	 alert("id is ------->"+id);
	var url ="../../../../../Office_Shift_new?command=CheckMiss&y="+y+"&m="+m+"&id="+id;
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
	if(req.readyState==4)
		{
//		alert(req.responseText);
		if(req.status==200)
		{
			var response=req.responseXML.getElementsByTagName("response")[0];
			var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
			var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if(command=="CheckMiss")
			{
			if(flag=="success")
				{
				
				var len=response.getElementsByTagName("beneficiary_name").length;
				//	  alert("inside len"+len);
if(len!=0)
	{
	 alert("The Following Beneficiary are Missing");
					 for ( var i = 0; i <= len; i++)
					   {
							 
						 
					    old_office_id[i]=response.getElementsByTagName("beneficiary_name")[i].firstChild.nodeValue;
					    alert("ben name is  -"+old_office_id[i]);
					   }
	}else
		{
		 alert("No Beneficiaies are Missing");
		}
				
			//	var beneficiary_name=response.getElementsByTagName("beneficiary_name")[0].firstChild.nodeValue;
			//	alert("beneficiary_name is"+beneficiary_name);
					}}}}};
	req.send(null);


}

function CheckDup()
{
//	alert("hi");
//	var old_office_id=new Array();
	var y=document.getElementById("pyear").value;
	 var m=document.getElementById("pmonth").value;
	 var id=document.getElementById("office").value;
//	 alert("y is ------->"+y);
//	 alert("m is ------->"+m);
//	 alert("id is ------->"+id);
	var url ="../../../../../Office_Shift_new?command=CheckDupdel&y="+y+"&m="+m+"&id="+id;
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
	if(req.readyState==4)
		{
//		alert(req.responseText);
		if(req.status==200)
		{
			var response=req.responseXML.getElementsByTagName("response")[0];
			var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
			var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if(command=="CheckDupdel")
			{
			if(flag=="success")
				{
				      alert("Deleted Successfully");
			     }  else
			     {
			          	alert("No Change");
				      }
			}}}};
			req.send(null);
			}
					    		
			



function yearValidation() 
{
//	alert("inside year");
	var month=document.getElementById("month").value;
	var Year=document.getElementById("Year").value;
	if(Year <=0)
	{
//		alert("Enter the Year ");
		document.getElementById("Year").value="";
		document.getElementById("Year").focus();
		return false;
		}

	var url="../../../../../Office_Shift_new?command=yearValidation";
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
	if(req.readyState==4)
		{
//		alert(req.responseText);
		if(req.status==200)
		{
			var response=req.responseXML.getElementsByTagName("response")[0];
			var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
			var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
		
		if(command=="yearValidation")
			{
			if(flag=="success")
				{
	//			alert("success");

				var Server_Year=response.getElementsByTagName("Year")[0].firstChild.nodeValue;
				var Server_month=response.getElementsByTagName("month")[0].firstChild.nodeValue;
				var Da_te=response.getElementsByTagName("Da")[0].firstChild.nodeValue;

				document.getElementById("Server_Year").value=Server_Year;
				document.getElementById("Server_month").value=Server_month;
				document.getElementById("Da_te").value=Da_te;

				
				
//    --------------------------  Year Validation will allow only for 2 months ------------------------				
//				var Server_month1=Server_month++;
//				var Server_Year1=Server_Year+1;

//				if(month==1)
//					{
//				if(Year != Server_Year1)
//					{
//					alert("Check the Year");
//					location = location;
//					document.getElementById("Year").value="";
//					document.getElementById("Year").focus();
//					return false;					
//					}
//					}
//				else if(month != Server_month && month != Server_month1 )
//				{
//					alert("Check the month");
//					location = location;
//
//					document.getElementById("month").value="";
//					document.getElementById("month").focus();
//					return false;
//				}	
//				else if(Year != Server_Year  )
//				{
//					alert("Invalid Year");
//				//	alert("	Server_Year1"+Server_Year);
//
//					location = location;
//
//					document.getElementById("Year").value="";
//					document.getElementById("Year").focus();
//					return false;
//				}
				
				
		
				}}}}};
	req.send(null);
	}

function check_month()
{
	var month=document.getElementById("month").value;
	if(month <=0)
	{
		alert("Enter the month ");
		document.getElementById("month").value="";
		document.getElementById("month").focus();

		return false;
		}
if(month>12)
{
alert("The month can be only upto 12");
document.getElementById("month").value="";

document.getElementById("month").focus();
return false;

}


}

function goBack() {
    window.history.back();
}

function check() {
//	alert("inside ");
	var month=document.getElementById("month").value;
	var Year=document.getElementById("Year").value;
	if(Year <=0)
	{
		alert("Enter the Year ");
	    location=location;
		}
	if(month <=0)
	{
		alert("Enter the month ");
	    location=location;
		}
	

}



	
function AllowNumbersOnly(e) {
    var code = (e.which) ? e.which : e.keyCode;
    if (code > 31 && (code < 48 || code > 57)) {
      e.preventDefault();
    }
  }
function setvalue(frm_off,off_name,off_status)
{
//	alert(frm_off);
//	alert(off_name);
//	alert(off_status);
 var frm_off=frm_off;
 var off_name=off_name;
 var off_status=off_status;

}
function getdata()
{
	if (document.getElementById("New_off_id").value == 0) {
		alert("Enter the changed Office Id ");
		return false;
	}
if (document.getElementById("New_off_id").value.length > 4) {
alert("Office Id is only 4 digit");
document.getElementById("New_off_id").value="";
document.getElementById("New_off_name").value="";
return false;
}	
	//alert("inside");
var New_off_id=document.getElementById("New_off_id").value;
var url="../../../../../Office_Shift_new?command=getdata&New_off_id="+New_off_id;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
	//alert(req.responseText);
	if(req.status==200)
	{
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="getdata")
		{
		if(flag=="success")
			{
			var New_off_name=response.getElementsByTagName("New_off_name")[0].firstChild.nodeValue;
			var OFFICE_STATUS_ID=response.getElementsByTagName("OFFICE_STATUS_ID")[0].firstChild.nodeValue;
			document.getElementById("New_off_name").value=New_off_name;
			document.getElementById("New_off_status").value=OFFICE_STATUS_ID;

			}}}}};
req.send(null);
}

function getoff()
{
	
	if (document.getElementById("off_id").value == 0) {
		alert("Enter the Office Id ");
		return false;
	}

	
var New_off_id=document.getElementById("off_id").value;
var url="../../../../../Office_Shift_new?command=getdata&New_off_id="+New_off_id;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
	//alert(req.responseText);
	if(req.status==200)
	{
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="getdata")
		{
		
		if(flag=="success")
			{
			var New_off_name=response.getElementsByTagName("New_off_name")[0].firstChild.nodeValue;
			
			var OFFICE_STATUS_ID=response.getElementsByTagName("OFFICE_STATUS_ID")[0].firstChild.nodeValue;
		
			document.getElementById("New_off_name").value=New_off_name;
	//		document.getElementById("New_off_status").value=OFFICE_STATUS_ID;

			}}}}};
req.send(null);
}

function getdata4()
{
	if (document.getElementById("New_off_id4").value == 0) {
		alert("Enter the changed Office Id ");
		return false;
	}
if (document.getElementById("New_off_id4").value.length > 4) {
alert("Office Id is only 4 digit");
document.getElementById("New_off_id4").value="";
document.getElementById("New_off_name4").value="";
return false;
}	
	//alert("inside");
var New_off_id4=document.getElementById("New_off_id4").value;
var url="../../../../../Office_Shift_new?command=getdata4&New_off_id4="+New_off_id4;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
	//alert(req.responseText);
	if(req.status==200)
	{
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="getdata4")
		{
		if(flag=="success")
			{
			var New_off_name4=response.getElementsByTagName("New_off_name4")[0].firstChild.nodeValue;
			var OFFICE_STATUS_ID4=response.getElementsByTagName("OFFICE_STATUS_ID4")[0].firstChild.nodeValue;

			document.getElementById("New_off_name4").value=New_off_name4;
			document.getElementById("New_off_status4").value=OFFICE_STATUS_ID4;

			}}}}};
req.send(null);
}

//            -----------------------   Merge for Sub Division   -------------------------  

function loadgrid5()
{
	// alert("inside");
	var frm_off=document.getElementById("frm_off").value;
	//alert("inside"+frm_off);

	var url="../../../../../Office_Shift_new?command=loadgrid&frm_off="+frm_off;
	//alert(url);
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
		if(req.readyState==4)
			{
	//		alert(req.responseText);
			if(req.status==200)
				{
				
//				alert("wwww--->"+req.responseText);
				var response=req.responseXML.getElementsByTagName("response")[0];
				var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
				var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
				if(command=="loadgrid")
					{
					if(flag=="success")
						{
				//	alert("inside success");
						var tgrid5=document.getElementById("tgrid5");
				         try{
					    	  tgrid5.innerHTML="";
					          }
					      catch(e)
					        {
					    	  tgrid5.innerText="";
					        }
					     while (tgrid5.childNodes.length > 0) {
					    	  tgrid5.removeChild(tgrid5.firstChild);
					          }    
			//		     alert("inside clean");
					     var len=response.getElementsByTagName("count").length;
					     var seq5=2000;
					        for(var i=0;i<len;i++)
					        {
					        var SUB_DIV_ID=response.getElementsByTagName("SUB_DIV_ID")[i].firstChild.nodeValue;
					        var OFFICE_NAME=response.getElementsByTagName("OFFICE_NAME")[i].firstChild.nodeValue;
					        var OFFICE_STATUS_ID=response.getElementsByTagName("OFFICE_STATUS_ID")[i].firstChild.nodeValue;
//					        
//					        
		// edit button to pass the value to register
					        
//					        var tr=document.createElement("TR");
//							tr.id=seq;
//							var td=document.createElement("TD");
//							var anc=document.createElement("A");
//							var url="javascript:loadvalue('"+seq+"')";
//							anc.href=url;
//							var edit=document.createTextNode("Edit");
//							anc.appendChild(edit);
//							td.appendChild(anc);
//							tr.appendChild(td);   
//							
							
//							var tr=document.createElement("TR");
//							tr.id=seq;
//							var td=document.createElement("TD");
//							var name=document.createTextNode(name_element);
//							td.appendChild(name);
//							tr.appendChild(td);  
					        
					        
					        var tr=document.createElement("TR");
							tr.id=seq5;
                     	    var td1=document.createElement("TD");
					      var SUB_DIV_ID=document.createTextNode(SUB_DIV_ID);
					        td1.appendChild(SUB_DIV_ID);
					        tr.appendChild(td1);
					       
                     	    var td2=document.createElement("TD");
                            var OFFICE_NAME=document.createTextNode(OFFICE_NAME);
  					        td2.appendChild(OFFICE_NAME);
  					        tr.appendChild(td2);				        
					        					        
					        var td3=document.createElement("TD");
					        var OFFICE_STATUS_ID=document.createTextNode(OFFICE_STATUS_ID);
				            td3.appendChild(OFFICE_STATUS_ID);
					        tr.appendChild(td3);
					        
					        var td4=document.createElement("TD");
					        var x = document.createElement("INPUT");
					        x.setAttribute("type", "text");
					        x.setAttribute("size", "4");
					        x.setAttribute("id",seq5);
					  //     x.setAttribute("value",seq2);
					    //    x.setAttribute("id",seq2);
					 //       x.setAttribute("value",seq2);
					        x.setAttribute("onblur", "myFunction5("+seq5+")");
					        x.setAttribute("onkeypress", "return AllowNumbersOnly(event)");
					        x.setAttribute("maxlength", "4");
					//      x.onblur=function(){myFunction(+seq2)};
					        
					        td4.appendChild(x);
					        tr.appendChild(td4);
					        
					        
					        var td5=document.createElement("TD");
						      var hi=document.createTextNode("");
					        td5.appendChild(hi);
					        tr.appendChild(td5);


//					        var td5=document.createElement("TD");
//					        var x1 = document.createElement("INPUT");
//					        x1.setAttribute("type", "text");
//					        x1.setAttribute("id", "NewSub");
//					        x1.setAttribute("readonly", "true");
//					        td5.appendChild(x1);
//					        tr.appendChild(td5);

					        var td6=document.createElement("TD");
						      var hi1=document.createTextNode("");
					        td6.appendChild(hi1);
					        tr.appendChild(td6);
					        
					        
					        var td7=document.createElement("TD");
							var anc=document.createElement("A");
						    var url="javascript:loadvalue5('"+seq5+"')";
							anc.href=url;
							var edit=document.createTextNode("Proceed");
							anc.appendChild(edit);
							td7.appendChild(anc);
				         	tr.appendChild(td7);   
										
				         	
				        
				         	var td8=document.createElement("TD");
						      var hi2=document.createTextNode("");
					        td8.appendChild(hi2);
					        tr.appendChild(td8);
//				         	var td7=document.createElement("TD");
//							var anc=document.createElement("A");
//						    var url="javascript:loadvalue1('"+seq+"')";
//							anc.href=url;
//							var edit=document.createTextNode("Proceed");
//							anc.appendChild(edit);
//							td7.appendChild(anc);
//				         	tr.appendChild(td7);   
//				         	
//				         	var td8=document.createElement("TD");
//							var anc=document.createElement("A");
//						    var url="javascript:loadvalue2('"+seq+"')";
//							anc.href=url;
//							var edit=document.createTextNode("Proceed");
//							anc.appendChild(edit);
//							td8.appendChild(anc);
//				         	tr.appendChild(td8);   
//					        
			//		    	var td5=document.createElement("TD");
			//				var anc=document.createElement("A");
			//				var url="javascript:loadvalue('"+seq+"')";
			//				anc.href=url;
			//				var edit=document.createTextNode("View BENEFICIARY");
			//				anc.appendChild(edit);
			//				td5.appendChild(anc);
         	//			tr.appendChild(td5);   
						
					        
					        
					        tgrid5.appendChild(tr); 
					        seq5++;
					}	
					} else
			        {
				         
//				        document.getElementById("ename").value="";
//				        document.getElementById("eadd").value="";
				       
				        alert("Failure in loading values");
				        }
				}
			}
	}}
	req.send(null);	
};


// - ---------------------------  Shift Beneficiaries Based on Sub Division


function loadgrid()
{
	//alert("inside");
	var frm_off=document.getElementById("frm_off").value;
	//alert("inside"+frm_off);

	var url="../../../../../Office_Shift_new?command=loadgrid&frm_off="+frm_off;
	//alert(url);
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
		if(req.readyState==4)
			{
	//		alert(req.responseText);
			if(req.status==200)
				{
				
//				alert("wwww--->"+req.responseText);
				var response=req.responseXML.getElementsByTagName("response")[0];
				var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
				var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
				if(command=="loadgrid")
					{
					if(flag=="success")
						{
				//	alert("inside success");
						var tgrid=document.getElementById("tgrid");
				         try{
					    	  tgrid.innerHTML="";
					          }
					      catch(e)
					        {
					    	  tgrid.innerText="";
					        }
					     while (tgrid.childNodes.length > 0) {
					    	  tgrid.removeChild(tgrid.firstChild);
					          }    
			//		     alert("inside clean");
					     var len=response.getElementsByTagName("count").length;
					     var seq2=0;
					        for(var i=0;i<len;i++)
					        {
					        var SUB_DIV_ID=response.getElementsByTagName("SUB_DIV_ID")[i].firstChild.nodeValue;
					        var OFFICE_NAME=response.getElementsByTagName("OFFICE_NAME")[i].firstChild.nodeValue;
					        var OFFICE_STATUS_ID=response.getElementsByTagName("OFFICE_STATUS_ID")[i].firstChild.nodeValue;
//					        
//					        
		// edit button to pass the value to register
					        
//					        var tr=document.createElement("TR");
//							tr.id=seq;
//							var td=document.createElement("TD");
//							var anc=document.createElement("A");
//							var url="javascript:loadvalue('"+seq+"')";
//							anc.href=url;
//							var edit=document.createTextNode("Edit");
//							anc.appendChild(edit);
//							td.appendChild(anc);
//							tr.appendChild(td);   
//							
							
//							var tr=document.createElement("TR");
//							tr.id=seq;
//							var td=document.createElement("TD");
//							var name=document.createTextNode(name_element);
//							td.appendChild(name);
//							tr.appendChild(td);  
					        
					        
					        var tr=document.createElement("TR");
							tr.id=seq2;
                     	    var td1=document.createElement("TD");
					      var SUB_DIV_ID=document.createTextNode(SUB_DIV_ID);
					        td1.appendChild(SUB_DIV_ID);
					        tr.appendChild(td1);
					       
                     	    var td2=document.createElement("TD");
                            var OFFICE_NAME=document.createTextNode(OFFICE_NAME);
  					        td2.appendChild(OFFICE_NAME);
  					        tr.appendChild(td2);				        
					        					        
					        var td3=document.createElement("TD");
					        var OFFICE_STATUS_ID=document.createTextNode(OFFICE_STATUS_ID);
				            td3.appendChild(OFFICE_STATUS_ID);
					        tr.appendChild(td3);
					        
					        var td4=document.createElement("TD");
					        var x = document.createElement("INPUT");
					        x.setAttribute("type", "text");
					        x.setAttribute("size", "4");
					        x.setAttribute("id",seq2);
					  //     x.setAttribute("value",seq2);
					    //    x.setAttribute("id",seq2);
					 //       x.setAttribute("value",seq2);
					        x.setAttribute("onblur", "myFunction("+seq2+")");
					        x.setAttribute("onkeypress", "return AllowNumbersOnly(event)");
					        x.setAttribute("maxlength", "4");
					//      x.onblur=function(){myFunction(+seq2)};
					        
					        td4.appendChild(x);
					        tr.appendChild(td4);
					        
					        
					        var td5=document.createElement("TD");
						      var hi=document.createTextNode("");
					        td5.appendChild(hi);
					        tr.appendChild(td5);


//					        var td5=document.createElement("TD");
//					        var x1 = document.createElement("INPUT");
//					        x1.setAttribute("type", "text");
//					        x1.setAttribute("id", "NewSub");
//					        x1.setAttribute("readonly", "true");
//					        td5.appendChild(x1);
//					        tr.appendChild(td5);

					        var td6=document.createElement("TD");
						      var hi1=document.createTextNode("");
					        td6.appendChild(hi1);
					        tr.appendChild(td6);
					        
					        
					        var td7=document.createElement("TD");
							var anc=document.createElement("A");
						    var url="javascript:loadvalue('"+seq2+"')";
							anc.href=url;
							var edit=document.createTextNode("Proceed");
							anc.appendChild(edit);
							td7.appendChild(anc);
				         	tr.appendChild(td7);   
										
				         	
				        
				         	var td8=document.createElement("TD");
						      var hi2=document.createTextNode("");
					        td8.appendChild(hi2);
					        tr.appendChild(td8);
//				         	var td7=document.createElement("TD");
//							var anc=document.createElement("A");
//						    var url="javascript:loadvalue1('"+seq+"')";
//							anc.href=url;
//							var edit=document.createTextNode("Proceed");
//							anc.appendChild(edit);
//							td7.appendChild(anc);
//				         	tr.appendChild(td7);   
//				         	
//				         	var td8=document.createElement("TD");
//							var anc=document.createElement("A");
//						    var url="javascript:loadvalue2('"+seq+"')";
//							anc.href=url;
//							var edit=document.createTextNode("Proceed");
//							anc.appendChild(edit);
//							td8.appendChild(anc);
//				         	tr.appendChild(td8);   
//					        
			//		    	var td5=document.createElement("TD");
			//				var anc=document.createElement("A");
			//				var url="javascript:loadvalue('"+seq+"')";
			//				anc.href=url;
			//				var edit=document.createTextNode("View BENEFICIARY");
			//				anc.appendChild(edit);
			//				td5.appendChild(anc);
         	//			tr.appendChild(td5);   
						
					        
					        
					        tgrid.appendChild(tr); 
					        seq2++;
					}	
					} else
			        {
				         
//				        document.getElementById("ename").value="";
//				        document.getElementById("eadd").value="";
				       
				        alert("Failure in loading values");
				        }
				}
			}
	}}
	req.send(null);	
};


//        ----------------------------------Shift Beneficiaries Based on Scheme-----------------------------
function loadgrid1()
{
//	alert("inside loadgrid1");
	var frm_off=document.getElementById("frm_off").value;
//	alert("inside"+frm_off);

	var url="../../../../../Office_Shift_new?command=loadgrid1&frm_off="+frm_off;
//	alert(url);
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
		if(req.readyState==4)
			{
	//		alert(req.responseText);
			if(req.status==200)
				{
				
				var response=req.responseXML.getElementsByTagName("response")[0];
				var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
				var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
				if(command=="loadgrid1")
					{
					if(flag=="success")
						{
						var tgrid1=document.getElementById("tgrid1");
				         try{
					    	  tgrid1.innerHTML="";
					          }
					      catch(e)
					        {
					    	  tgrid1.innerText="";
					        }
					     while (tgrid1.childNodes.length > 0) {
					    	  tgrid1.removeChild(tgrid.firstChild);
					          }    
					     var len=response.getElementsByTagName("count").length;
					     var seq3=1000;
					        for(var i=0;i<len;i++)
					        {
					        var scheme_sno=response.getElementsByTagName("scheme_sno")[i].firstChild.nodeValue;
					        var sch_name=response.getElementsByTagName("sch_name")[i].firstChild.nodeValue;
					              
					        
					        var tr=document.createElement("TR");
							tr.id=seq3;
                     	    var td1=document.createElement("TD");
					      var scheme_sno=document.createTextNode(scheme_sno);
					        td1.appendChild(scheme_sno);
					        tr.appendChild(td1);
					       
                     	    var td2=document.createElement("TD");
                            var sch_name=document.createTextNode(sch_name);
  					        td2.appendChild(sch_name);
  					        tr.appendChild(td2);				        
					        					        
  					      var td3=document.createElement("TD");
							var anc=document.createElement("A");
							var url="javascript:loadvalue1('"+seq3+"')";
							anc.href=url;
							var edit=document.createTextNode("View BENEFICIARY");
							anc.appendChild(edit);
							td3.appendChild(anc);
       				tr.appendChild(td3);   
						 
					        var td4=document.createElement("TD");
					        var x = document.createElement("INPUT");
					        x.setAttribute("type", "text");
					        x.setAttribute("size", "4");
					        x.setAttribute("id",seq3);
					        x.setAttribute("onkeypress", "return AllowNumbersOnly(event)");
					        x.setAttribute("maxlength", "4");
					        x.setAttribute("onblur", "myFunction1("+seq3+")");
					        td4.appendChild(x);
					        tr.appendChild(td4);
					        
					        
					        var td5=document.createElement("TD");
						      var hi=document.createTextNode("");
					        td5.appendChild(hi);
					        tr.appendChild(td5);
            
					        
					        var td6=document.createElement("TD");
							var anc=document.createElement("A");
						    var url="javascript:SchemeProceed('"+seq3+"')";
							anc.href=url;
							var edit=document.createTextNode("Proceed");
							anc.appendChild(edit);
							td6.appendChild(anc);
				         	tr.appendChild(td6);   
										
				         	
				        
				         	var td7=document.createElement("TD");
						      var hi2=document.createTextNode("");
					        td7.appendChild(hi2);
					        tr.appendChild(td7);
					        
					        
					        tgrid1.appendChild(tr); 
					        seq3++;
					}	
					} else
			        {
				         
//				        document.getElementById("ename").value="";
//				        document.getElementById("eadd").value="";
				       
				        alert("Failure in loading values");
				        }
				}
			}
	}}
	req.send(null);	
};

//   ---------------------------------   Merge for Scheme   within division           -------------------------------------------------------
function loadgrid6()
{
//	alert("inside loadgrid1");
	var frm_off=document.getElementById("frm_off").value;
//	alert("inside"+frm_off);

	var url="../../../../../Office_Shift_new?command=loadgrid1&frm_off="+frm_off;
//	alert(url);
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
		if(req.readyState==4)
			{
	//		alert(req.responseText);
			if(req.status==200)
				{
				
				var response=req.responseXML.getElementsByTagName("response")[0];
				var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
				var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
				if(command=="loadgrid1")
					{
					if(flag=="success")
						{
						var tgrid6=document.getElementById("tgrid6");
				         try{
					    	  tgrid6.innerHTML="";
					          }
					      catch(e)
					        {
					    	  tgrid6.innerText="";
					        }
					     while (tgrid6.childNodes.length > 0) {
					    	  tgrid6.removeChild(tgrid.firstChild);
					          }    
					     var len=response.getElementsByTagName("count").length;
					     var seq6=10000;
					        for(var i=0;i<len;i++)
					        {
					        var scheme_sno=response.getElementsByTagName("scheme_sno")[i].firstChild.nodeValue;
					        var sch_name=response.getElementsByTagName("sch_name")[i].firstChild.nodeValue;
					              
					        
					        var tr=document.createElement("TR");
							tr.id=seq6;
                     	    var td1=document.createElement("TD");
					      var scheme_sno=document.createTextNode(scheme_sno);
					        td1.appendChild(scheme_sno);
					        tr.appendChild(td1);
					       
                     	    var td2=document.createElement("TD");
                            var sch_name=document.createTextNode(sch_name);
  					        td2.appendChild(sch_name);
  					        tr.appendChild(td2);				        
					        					        
  					      var td3=document.createElement("TD");
							var anc=document.createElement("A");
							var url="javascript:loadvalue1('"+seq6+"')";
							anc.href=url;
							var edit=document.createTextNode("View BENEFICIARY");
							anc.appendChild(edit);
							td3.appendChild(anc);
       				tr.appendChild(td3);   
						 
					        var td4=document.createElement("TD");
					        var x = document.createElement("INPUT");
					        x.setAttribute("type", "text");
					        x.setAttribute("size", "4");
					        x.setAttribute("id",seq6);
					        x.setAttribute("onkeypress", "return AllowNumbersOnly(event)");
					   //     x.setAttribute("maxlength", "4");
					        x.setAttribute("onblur", "myFunction6("+seq6+")");
					        td4.appendChild(x);
					        tr.appendChild(td4);
					        
					        
					        var td5=document.createElement("TD");
						      var hi=document.createTextNode("");
					        td5.appendChild(hi);
					        tr.appendChild(td5);
            
					        
					        var td6=document.createElement("TD");
							var anc=document.createElement("A");
						    var url="javascript:SchemeProceed6('"+seq6+"')";
							anc.href=url;
							var edit=document.createTextNode("Proceed");
							anc.appendChild(edit);
							td6.appendChild(anc);
				         	tr.appendChild(td6);   
										
				         	
				        
				         	var td7=document.createElement("TD");
						      var hi2=document.createTextNode("");
					        td7.appendChild(hi2);
					        tr.appendChild(td7);
					        
					        
					        tgrid6.appendChild(tr); 
					        seq6++;
					}	
					} else
			        {
				         
//				        document.getElementById("ename").value="";
//				        document.getElementById("eadd").value="";
				       
				        alert("Failure in loading values");
				        }
				}
			}
	}}
	req.send(null);	
};




//  --------------------------------- Transfer To New Division -------------------------------

function loadgrid4()
{
//	alert("inside loadgrid1");
	var frm_off=document.getElementById("frm_off").value;
//	alert("inside"+frm_off);

	var url="../../../../../Office_Shift_new?command=loadgrid4&frm_off="+frm_off;
//	alert(url);
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
		if(req.readyState==4)
			{
	//		alert(req.responseText);
			if(req.status==200)
				{
				
				var response=req.responseXML.getElementsByTagName("response")[0];
				var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
				var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
				if(command=="loadgrid4")
					{
					if(flag=="success")
						{
						var tgrid4=document.getElementById("tgrid4");
				         try{
					    	  tgrid4.innerHTML="";
					          }
					      catch(e)
					        {
					    	  tgrid4.innerText="";
					        }
					     while (tgrid4.childNodes.length > 0) {
					    	  tgrid4.removeChild(tgrid4.firstChild);
					          }    
					     var len=response.getElementsByTagName("count").length;
					     var seq4=4000;
					        for(var i=0;i<len;i++)
					        {
					        var scheme_sno4=response.getElementsByTagName("scheme_sno")[i].firstChild.nodeValue;
					        var sch_name4=response.getElementsByTagName("sch_name")[i].firstChild.nodeValue;
					              
					    //    alert("scheme_sno4"+scheme_sno4);
					    //    alert("sch_name4"+sch_name4);
					        var tr=document.createElement("TR");
							tr.id=seq4;
                     	    var td1=document.createElement("TD");
					      var scheme_sno4=document.createTextNode(scheme_sno4);
					        td1.appendChild(scheme_sno4);
					        tr.appendChild(td1);
					       
                     	    var td2=document.createElement("TD");
                            var sch_name4=document.createTextNode(sch_name4);
  					        td2.appendChild(sch_name4);
  					        tr.appendChild(td2);				        
					        					        
  					      var td3=document.createElement("TD");
							var anc=document.createElement("A");
							var url="javascript:loadvalue4('"+seq4+"')";
							anc.href=url;
							var edit=document.createTextNode("View BENEFICIARY");
							anc.appendChild(edit);
							td3.appendChild(anc);
       				tr.appendChild(td3);   
						 
					        var td4=document.createElement("TD");
					        var x = document.createElement("INPUT");
					        x.setAttribute("type", "text");
					        x.setAttribute("size", "4");
					        x.setAttribute("id",seq4);
					        x.setAttribute("onkeypress", "return AllowNumbersOnly(event)");
					        x.setAttribute("maxlength", "4");
					        x.setAttribute("onblur", "myFunction4("+seq4+")");
					        td4.appendChild(x);
					        tr.appendChild(td4);
					        
					        
					        var td5=document.createElement("TD");
						      var hi=document.createTextNode("");
					        td5.appendChild(hi);
					        tr.appendChild(td5);
					        
					        var td6=document.createElement("TD");
						    var hi=document.createTextNode("");
					        td6.appendChild(hi);
					        tr.appendChild(td6);
            			        
					        var td7=document.createElement("TD");
							var anc=document.createElement("A");
						    var url="javascript:SchemeProceed4('"+seq4+"')";
							anc.href=url;
							var edit=document.createTextNode("Proceed");
							anc.appendChild(edit);
							td7.appendChild(anc);
				         	tr.appendChild(td7);   
										
				         	
				        
				         	var td8=document.createElement("TD");
						      var hi2=document.createTextNode("");
					        td8.appendChild(hi2);
					        tr.appendChild(td8);
					        
			        
					        tgrid4.appendChild(tr); 
					        seq4++;
					}	
					} else
			        {
				         
//				        document.getElementById("ename").value="";
//				        document.getElementById("eadd").value="";
				       
				        alert("Failure in loading values");
				        }
				}
			}
	}}
	req.send(null);	
};


function checknull()
{
    var Year1=document.getElementById("Year").value;
    //alert("Year1"+Year1);
    var month=document.getElementById("month").value;
	// alert("Ymonth"+month);
    var New_off_id=document.getElementById("New_off_id").value;
    
    if(month <=0)
	{
	//	alert("Enter the month");
		document.getElementById("month").focus();
		return false;
	}
    if(Year1 <=0)
	{
		//alert("Enter the Year ");
		document.getElementById("Year").focus();
		return false;
	}
    
    return true;
}

function checknull1()
{
    var New_off_id=document.getElementById("New_off_id").value;

	 if(New_off_id <=0)
	    {
	    	alert("Enter the New_off_id");
	    	document.getElementById("New_off_id").focus();
	    	return false;
	    	}
	    return true;


}
function setfalse()
{
	return false;
}


function SchemeProceed(seq3)
{
	var flag=checknull();
	//alert(flag);
	if(flag)
		{
   // alert("SchemeProceed"+seq3);
    
	    var user_id=document.getElementById("user_id").value;
	    var Da_te=document.getElementById("Da_te").value;

    var frm_off=document.getElementById("frm_off").value;
    var Year1=document.getElementById("Year").value;
   // alert("Year1"+Year1);
	var month=document.getElementById("month").value;
	// alert("Ymonth"+month);
	try{
	

	var Year=document.getElementById("Year").value;
	
	var month1=month-1;
	if(month1==0)
	{
	month1=12;
	}
	if(month==1)
		{
		Year1=Year-1;
		}
	}
	catch(e)
	{
		 alert("Schem"+e);
	}
    var seq3=document.getElementById(seq3); 
    var rcells=seq3.cells;
    var new_off_id=rcells.item(3).children[0].value;
    var sche_no=rcells.item(0).firstChild.nodeValue;
    
    
    var new_off_name=rcells.item(4).firstChild.nodeValue;

    if(new_off_name == "" || new_off_name == null)
    	{
    	alert("New office id is not exist");
		rcells.item(6).firstChild.nodeValue="New office id is not exist";
		setfalse();
		}
//    alert("New_off_id"+New_off_id);
//    alert("new_id"+new_id);
//    alert("sub_div"+sub_div);
    
  	var url="../../../../../Office_Shift_new?command=SchemeProceed&new_off_id="+new_off_id+"&sche_no="+sche_no+"&frm_off="+frm_off+"&month="+month+"&month1="+month1+"&Year1="+Year1+"&Year="+Year+"&user_id="+user_id+"&Da_te="+Da_te;
//	alert(url);
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
		if(req.readyState==4)
			{
			alert(req.responseText);
			if(req.status==200)
				{
				
//				alert("wwww--->"+req.responseText);
				var response=req.responseXML.getElementsByTagName("response")[0];
				var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
				var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
				alert("command is"+command);
				if(command=="SchemeProceed")
					{
					alert("command is 6666"+command);
					if(flag=="success")
						{
							alert("Update Sucessfull");
							rcells.item(6).firstChild.nodeValue="Sucess";
	
							
						}else
							{
						alert("Update Not Sucessfull");
					rcells.item(6).firstChild.nodeValue="Failed";
							}
					}}}};
			req.send(null);
			
		}
}


function SchemeProceed6(seq6)
{
	 var user_id=document.getElementById("user_id").value;
	// var Da_te=document.getElementById("Da_te").value;
	var frm_off=document.getElementById("frm_off").value;
    var Year1=document.getElementById("Year").value;
 
	var month=document.getElementById("month").value;
	
	try{
	

	var Year=document.getElementById("Year").value;
	
	var month1=month-1;
	if(month1==0)
	{
	month1=12;
	}
	if(month==1)
		{
		Year1=Year-1;
		}
	}
	catch(e)
	{
	//	 alert("Schem"+e);
	}
    var seq6=document.getElementById(seq6); 
    var rcells=seq6.cells;
    var new_Scheme_no=rcells.item(3).children[0].value;
    var sche_no=rcells.item(0).firstChild.nodeValue;
 //   var new_Scheme_name=rcells.item(4).firstChild.nodeValue;

    if(new_Scheme_no == "" || new_Scheme_no == null)
    	{
    	alert("Enter New Scheme No");
		setfalse();
		}
    
  	var url="../../../../../Office_Shift_new?command=SchemeProceed6&new_Scheme_no="+new_Scheme_no+"&sche_no="+sche_no+"&frm_off="+frm_off+"&month="+month+"&Year="+Year+"&month1="+month1+"&Year1="+Year1+"&user_id="+user_id;
//	alert(url);
//  	 alert("month"+month);
//	 alert("Year"+Year);
//	 alert("month1"+month1);
//	 alert("Year1"+Year1);
//	 alert("user_id"+user_id);
//	 alert("new_Scheme_no"+new_Scheme_no);
//	 alert("sche_no"+sche_no);
  	
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
		if(req.readyState==4)
			{
	//		alert(req.responseText);
			if(req.status==200)
				{
				
//				alert("wwww--->"+req.responseText);
				var response=req.responseXML.getElementsByTagName("response")[0];
				var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
				var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
				if(command=="SchemeProceed6")
					{
					if(flag=="success")
						{
							alert("Update Sucessfull");
							rcells.item(6).firstChild.nodeValue="Sucess";
	
							
						}else
							{
						alert("Update Not Sucessfull");
					rcells.item(6).firstChild.nodeValue="Failed";
							}
					}}}};
			req.send(null);
	}





function SchemeProceed4(seq4)
{
	var flag=checknull();
	//alert(flag);
	if(flag)
		{
      
	    var New_off_id4=document.getElementById("New_off_id4").value;
	    if(New_off_id4 == "" || New_off_id4 == null)
    	{
    	alert("Enter New Office ID");
		setfalse();
		}
    

    var frm_off=document.getElementById("frm_off").value;
    var Year1=document.getElementById("Year").value;
   // alert("Year1"+Year1);
	var month=document.getElementById("month").value;
	// alert("Ymonth"+month);
	try{
	

	var Year=document.getElementById("Year").value;
	
	var month1=month-1;
	if(month1==0)
	{
	month1=12;
	}
	if(month==1)
		{
		Year1=Year-1;
		}
	}
	catch(e)
	{
	//	 alert("Schem"+e);
	}
    var seq4=document.getElementById(seq4); 
    var rcells=seq4.cells;
    var new_Sub_id=rcells.item(3).children[0].value;
    var sche_no=rcells.item(0).firstChild.nodeValue;
    var new_off_name=rcells.item(4).firstChild.nodeValue;

    if(new_Sub_id == "" || new_Sub_id == null)
    	{
    	alert("Enter New Sub Division ID");
		setfalse();
		}
    
  	var url="../../../../../Office_Shift_new?command=SchemeProceed4&new_Sub_id="+new_Sub_id+"&sche_no="+sche_no+"&frm_off="+frm_off+"&New_off_id4="+New_off_id4;
//	alert(url);
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
		if(req.readyState==4)
			{
	//		alert(req.responseText);
			if(req.status==200)
				{
				
//				alert("wwww--->"+req.responseText);
				var response=req.responseXML.getElementsByTagName("response")[0];
				var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
				var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
				if(command=="SchemeProceed4")
					{
					if(flag=="success")
						{
							alert("Update Sucessfull");
							rcells.item(7).firstChild.nodeValue="Sucess";
	
							
						}else
							{
						alert("Update Not Sucessfull");
					rcells.item(7).firstChild.nodeValue="Failed";
							}
					}}}};
			req.send(null);
	}
}


function myFunction6(seq6)
{
  //  alert("seq3 is-->"+seq3);
    var seq6=document.getElementById(seq6); 
   
    var rcells=seq6.cells;
      var New_scheme=rcells.item(3).children[0].value;
   
    var url="../../../../../Office_Shift_new?command=getscheme&New_scheme="+New_scheme;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
    if(req.readyState==4)
    	{
  //  	alert("request"+req.responseText);
    	if(req.status==200)
    	{
    		
    		var response=req.responseXML.getElementsByTagName("response")[0];
    		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
    		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
    	
    		if(command=="getscheme")
    		{
    		if(flag=="success")
    			{
    			
    			var sch_name=response.getElementsByTagName("sch_name")[0].firstChild.nodeValue;
    			
    			rcells.item(4).firstChild.nodeValue=sch_name;

    			}}}}};
    req.send(null);
    }



function myFunction1(seq3)
{
  //  alert("seq3 is-->"+seq3);
    var seq3=document.getElementById(seq3); 
   
    var rcells=seq3.cells;
      var New_off_id=rcells.item(3).children[0].value;
   
    var url="../../../../../Office_Shift_new?command=getname&New_off_id="+New_off_id;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
    if(req.readyState==4)
    	{
  //  	alert("request"+req.responseText);
    	if(req.status==200)
    	{
    		
    		var response=req.responseXML.getElementsByTagName("response")[0];
    		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
    		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
    	
    		if(command=="getname")
    		{
    		if(flag=="success")
    			{
    			
    			var New_off_name=response.getElementsByTagName("New_off_name")[0].firstChild.nodeValue;
    			
    			rcells.item(4).firstChild.nodeValue=New_off_name;

    			}}}}};
    req.send(null);
    }

function myFunction4(seq4)
{
  //  alert("seq3 is-->"+seq3);
    var seq4=document.getElementById(seq4); 
   
    var rcells=seq4.cells;
      var New_Sub_div_id=rcells.item(3).children[0].value;
   
    var url="../../../../../Office_Shift_new?command=getname4&New_Sub_div_id="+New_Sub_div_id;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
    if(req.readyState==4)
    	{
  //  	alert("request"+req.responseText);
    	if(req.status==200)
    	{
    		
    		var response=req.responseXML.getElementsByTagName("response")[0];
    		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
    		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
    	
    		if(command=="getname4")
    		{
    		if(flag=="success")
    			{
    			
    			var SUB_STATUS_ID=response.getElementsByTagName("SUB_STATUS_ID")[0].firstChild.nodeValue;
    			var SUB_NAME=response.getElementsByTagName("SUB_NAME")[0].firstChild.nodeValue;
    		
    			rcells.item(4).firstChild.nodeValue=SUB_NAME;
    			rcells.item(5).firstChild.nodeValue=SUB_STATUS_ID;

    			}}}}};
    req.send(null);
    }


function myFunction(seq2)
{
 //   alert("Input field lost focus."+seq2);
    var seq2=document.getElementById(seq2); 
   
    var rcells=seq2.cells;
    var sub_div=rcells.item(0).firstChild.nodeValue;
    var new_id=rcells.item(3).children[0].value;
//    if(sub_div == new_id )
//    	{
//		rcells.item(7).firstChild.nodeValue="No change";
//
//    	}
    var url="../../../../../Office_Shift_new?command=getdata1&new_id="+new_id;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
    if(req.readyState==4)
    	{
 //   	alert("request"+req.responseText);
    	if(req.status==200)
    	{
    		
    		var response=req.responseXML.getElementsByTagName("response")[0];
    		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
    		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
    	
    		if(command=="getdata1")
    		{
    		if(flag=="success")
    			{
   // 			alert("the values are inside")
    			var OFFICE_NAME=response.getElementsByTagName("OFFICE_NAME")[0].firstChild.nodeValue;
    			var OFFICE_STATUS_ID=response.getElementsByTagName("OFFICE_STATUS_ID")[0].firstChild.nodeValue;
    			rcells.item(4).firstChild.nodeValue=OFFICE_NAME;
    			rcells.item(5).firstChild.nodeValue=OFFICE_STATUS_ID;

    			}}}}};
    req.send(null);
    }
  

function myFunction5(seq5)
{
 //   alert("Input field lost focus."+seq2);
    var seq5=document.getElementById(seq5); 
   
    var rcells=seq5.cells;
    var sub_div=rcells.item(0).firstChild.nodeValue;
    var new_id=rcells.item(3).children[0].value;
    if(sub_div == new_id )
    	{
		rcells.item(7).firstChild.nodeValue="No change";

    	}
    var url="../../../../../Office_Shift_new?command=getdata5&new_id="+new_id;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
    if(req.readyState==4)
    	{
 //   	alert("request"+req.responseText);
    	if(req.status==200)
    	{
    		
    		var response=req.responseXML.getElementsByTagName("response")[0];
    		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
    		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
    	
    		if(command=="getdata5")
    		{
    		if(flag=="success")
    			{
   // 			alert("the values are inside")
    			var OFFICE_NAME=response.getElementsByTagName("OFFICE_NAME")[0].firstChild.nodeValue;
    			var OFFICE_STATUS_ID=response.getElementsByTagName("OFFICE_STATUS_ID")[0].firstChild.nodeValue;
    			rcells.item(4).firstChild.nodeValue=OFFICE_NAME;
    			rcells.item(5).firstChild.nodeValue=OFFICE_STATUS_ID;

    			}}}}};
    req.send(null);
    }


function loadvalue5(seq5)
{
  //  alert("loadvalue"+seq2);
  	    var user_id=document.getElementById("user_id").value;
	    var Da_te=document.getElementById("Da_te").value;
        var New_off_id=document.getElementById("frm_off").value;

    var seq5=document.getElementById(seq5); 
    var rcells=seq5.cells;
    var new_id=rcells.item(3).children[0].value;
    var sub_div=rcells.item(0).firstChild.nodeValue;
    if(new_id == "" || new_id=="0" )
	{
	alert("Enter new Sub Division Office ");
	setfalse();
	}
//    if(sub_div == new_id)
//    	{
//    	alert("Closed Sub Division SAME as change Sub Division");
//    	alert("Updation will not Happen OR change Sub Division to proceed");
//		rcells.item(7).firstChild.nodeValue="No change";
//		setfalse();
//    	}
//    alert("New_off_id"+New_off_id);
//    alert("new_id"+new_id);
//    alert("sub_div"+sub_div);
    
  	var url="../../../../../Office_Shift_new?command=loadvalue&New_off_id="+New_off_id+"&new_id="+new_id+"&sub_div="+sub_div+"&user_id="+user_id+"&Da_te="+Da_te;
//	alert(url);
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
		if(req.readyState==4)
			{
	//		alert(req.responseText);
			if(req.status==200)
				{
				
//				alert("wwww--->"+req.responseText);
				var response=req.responseXML.getElementsByTagName("response")[0];
				var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
				var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
				if(command=="loadvalue")
					{
					if(flag=="success")
						{
							alert("Update Sucessfull");
							rcells.item(7).firstChild.nodeValue="Sucess";
	                        location=location;
							
						}else
							{
							alert("Update Not Sucessfull");
					rcells.item(7).firstChild.nodeValue="Failed";
							}
					}}}};
			req.send(null);
   }

    
 function loadvalue(seq2)
{
  //  alert("loadvalue"+seq2);
    var flag=checknull1()
    if(flag){

	    var user_id=document.getElementById("user_id").value;
	    var Da_te=document.getElementById("Da_te").value;
        var New_off_id=document.getElementById("New_off_id").value;

    var seq2=document.getElementById(seq2); 
    var rcells=seq2.cells;
    var new_id=rcells.item(3).children[0].value;
    var sub_div=rcells.item(0).firstChild.nodeValue;
    if(new_id == "" || new_id=="0" )
	{
	alert("Enter new Sub Division Office ");
	setfalse();
	}
 //    alert("New_off_id"+New_off_id);
//    alert("new_id"+new_id);
//    alert("sub_div"+sub_div);
    
  	var url="../../../../../Office_Shift_new?command=loadvalue&New_off_id="+New_off_id+"&new_id="+new_id+"&sub_div="+sub_div+"&user_id="+user_id+"&Da_te="+Da_te;
//	alert(url);
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
		if(req.readyState==4)
			{
	//		alert(req.responseText);
			if(req.status==200)
				{
				
//				alert("wwww--->"+req.responseText);
				var response=req.responseXML.getElementsByTagName("response")[0];
				var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
				var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
				if(command=="loadvalue")
					{
					if(flag=="success")
						{
							alert("Update Sucessfull");
							rcells.item(7).firstChild.nodeValue="Sucess";
	
							
						}else
							{
							alert("Update Not Sucessfull");
					rcells.item(7).firstChild.nodeValue="Failed";
							}
					}}}};
			req.send(null);
    }
}

 function loadvalue1(seq3)
 {      
 	
// 	var Row = document.getElementById("tgrid1");
// 	var Cells = Row.getElementsByTagName("td");
 	
 	// alert("reached in loadvalue1  -->"+seq3);
 	 
 	 var seq3=document.getElementById(seq3); 
 	 var r1cells=seq3.cells;
 var sche_id=r1cells.item(0).firstChild.nodeValue;
 var sche_Name=r1cells.item(1).firstChild.nodeValue;
 var frm_off=document.getElementById("frm_off").value;
 var off_name=document.getElementById("off_name").value;

 window.location.href = "Scheme_Beneficiary.jsp?command=add&frm_off=" + frm_off + "&sche_id=" + sche_id +  "&sche_Name=" + sche_Name + "&off_name=" + off_name ;

  // winemp= window.open("Scheme_Beneficiary.jsp?command=add&frm_off=" + frm_off + "&sche_id=" + sche_id +  "&sche_Name=" + sche_Name + "&off_name=" + off_name,"status=1,height=500,width=500,resizable=YES, scrollbars=yes");

 		
 req.send(null);	
 };


 function loadvalue4(seq4)
 {      
 	
// 	var Row = document.getElementById("tgrid1");
// 	var Cells = Row.getElementsByTagName("td");
 	
 	// alert("reached in loadvalue1  -->"+seq3);
 	 
 	 var seq4=document.getElementById(seq4); 
 	 var r1cells=seq4.cells;
 var sche_id=r1cells.item(0).firstChild.nodeValue;
 var sche_Name=r1cells.item(1).firstChild.nodeValue;
 var frm_off=document.getElementById("frm_off").value;
 var off_name=document.getElementById("off_name").value;

 window.location.href = "Scheme_Beneficiary.jsp?command=add&frm_off=" + frm_off + "&sche_id=" + sche_id +  "&sche_Name=" + sche_Name + "&off_name=" + off_name ;

  // winemp= window.open("Scheme_Beneficiary.jsp?command=add&frm_off=" + frm_off + "&sche_id=" + sche_id +  "&sche_Name=" + sche_Name + "&off_name=" + off_name,"status=1,height=500,width=500,resizable=YES, scrollbars=yes");

 		
 req.send(null);	
 };
 
 
 // -----------------------------------------------   3. Based on Sub Division   -----------------------------
 
function proceed()
{
//	alert("inside");
	
	
	var Year1=document.getElementById("Year").value;
	var month=document.getElementById("month").value;

    var user_id=document.getElementById("user_id").value;
    var Da_te=document.getElementById("Da_te").value;
	if(month <=0)
	{
		alert("Enter the month");
		document.getElementById("month").focus();
		return false;
		}

	var Year=document.getElementById("Year").value;
	if(Year <=0)
	{
		alert("Enter the Year ");
		document.getElementById("Year").focus();
		return false;
		}
	var month1=month-1;
	if(month1==0)
	{
	month1=12;
	}
	if(month==1)
		{
		Year1=Year-1;
		}

	 var str1 = "";
	 var str = "Updation takes time!";
	    var result = str.fontcolor("red");
	   
	
//	var month=document.getElementById("month").value;
//	var Year=document.getElementById("Year").value;
	var frm_off=document.getElementById("frm_off").value;
	var New_off_id=document.getElementById("New_off_id").value;
	if(New_off_id <=0)
	{
		alert("Enter the New_off_id");
		document.getElementById("New_off_id").focus();
		return false;
		}
	var New_off_status=document.getElementById("New_off_status").value;
	if(New_off_status != "CR")
		{
		alert("The changed office status should be CR ");
		return false;
		}

//	alert("Please Wait ");
	
    document.getElementById("demo").innerHTML = result;

	var url="../../../../../Office_Shift_new?command=proceed&frm_off="+frm_off+"&New_off_id="+New_off_id+"&month="+month+"&Year="+Year+"&month1="+month1+"&Year1="+Year1+"&user_id="+user_id+"&Da_te="+Da_te;
	
//	alert(url);
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
		if(req.readyState==4)
			{
	//		alert(req.responseText);
			if(req.status==200)
				{
				
//				alert("wwww--->"+req.responseText);
				var response=req.responseXML.getElementsByTagName("response")[0];
				var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
				var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//			alert("command"+command);
	//			alert("flag"+flag);

				if(command=="proceed")
					{
					if(flag=="success")
						{
							alert("Update Sucessfull");
						    document.getElementById("demo").innerHTML = str1;

						}
					if(flag=="failed1")
					{
						alert("ERROR in table1 ");
					    document.getElementById("demo").innerHTML = str1;

					}
					if(flag=="failed2")
					{
						alert("ERROR in table2 ");
					    document.getElementById("demo").innerHTML = "";

					}
					if(flag=="failed3")
					{
						alert("ERROR in table3 ");
					    document.getElementById("demo").innerHTML = "";

					}
					if(flag=="failed4")
					{
						alert("ERROR in table4 ");
					    document.getElementById("demo").innerHTML = "";
					}
					if(flag=="failed5")
					{
						alert("ERROR in table5 ");
					    document.getElementById("demo").innerHTML = "";

					}
					if(flag=="failed6")
					{
						alert("ERROR in table6 ");
					    document.getElementById("demo").innerHTML = "";

					}
					if(flag=="failed7")
					{
						alert("ERROR in table7 ");
					    document.getElementById("demo").innerHTML = "";

					}
					if(flag=="failed8")
					{
						alert("ERROR in table8 ");
					    document.getElementById("demo").innerHTML = "";

					}
					if(flag=="failed9")
					{
						alert("ERROR in table9 ");
					    document.getElementById("demo").innerHTML = "";

					}

						
					
					
					}}}};
			req.send(null);
			}

// -----------------------------------5. Transfer To New Division  -----------------------------------------

function proceed1()
{
//	alert("inside");
	var Year1=document.getElementById("Year").value;
	var month=document.getElementById("month").value;

    var user_id=document.getElementById("user_id").value;
    
	if(month <=0)
	{
		alert("Enter the month");
		document.getElementById("month").focus();
		return false;
		}

	var Year=document.getElementById("Year").value;
	if(Year <=0)
	{
		alert("Enter the Year ");
		document.getElementById("Year").focus();
		return false;
		}
	var month1=month-1;
	if(month1==0)
	{
	month1=12;
	}
	if(month==1)
		{
		Year1=Year-1;
		}

	 var str1 = "";
	 var str = "Updation takes time!";
	    var result = str.fontcolor("red");
	   
	
//	var month=document.getElementById("month").value;
//	var Year=document.getElementById("Year").value;
	var frm_off=document.getElementById("frm_off").value;
	var New_off_id4=document.getElementById("New_off_id4").value;
	if(New_off_id4 <=0)
	{
		alert("Enter the New_off_id");
		document.getElementById("New_off_id4").focus();
		return false;
		}
	var New_off_status4=document.getElementById("New_off_status4").value;
	if(New_off_status4 != "CR")
		{
		alert("The changed office status should be CR ");
		return false;
		}

//	alert("Please Wait ");
    document.getElementById("demo5").innerHTML = result;

	var url="../../../../../Office_Shift_new?command=proceed1&frm_off="+frm_off+"&New_off_id4="+New_off_id4+"&month="+month+"&Year="+Year+"&month1="+month1+"&Year1="+Year1+"&user_id="+user_id;
	
//	alert(url);
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
		if(req.readyState==4)
			{
		//	alert(req.responseText);
			if(req.status==200)
				{
				
//				alert("wwww--->"+req.responseText);
				var response=req.responseXML.getElementsByTagName("response")[0];
				var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
				var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//			alert("command"+command);
	//			alert("flag"+flag);

				if(command=="proceed1")
					{
					if(flag=="success")
						{
							alert("Update Sucessfull");
						    document.getElementById("demo5").innerHTML = str1;

						}
					if(flag=="failed1")
					{
						alert("ERROR in table1 ");
					    document.getElementById("demo5").innerHTML = str1;

					}
					if(flag=="failed2")
					{
						alert("ERROR in table2 ");
					    document.getElementById("demo5").innerHTML = "";

					}
					if(flag=="failed3")
					{
						alert("ERROR in table3 ");
					    document.getElementById("demo5").innerHTML = "";

					}
					if(flag=="failed4")
					{
						alert("ERROR in table4 ");
					    document.getElementById("demo5").innerHTML = "";
					}
					if(flag=="failed5")
					{
						alert("ERROR in table5 ");
					    document.getElementById("demo5").innerHTML = "";

					}
					if(flag=="failed6")
					{
						alert("ERROR in table6 ");
					    document.getElementById("demo5").innerHTML = "";

					}
					if(flag=="failed7")
					{
						alert("ERROR in table7 ");
					    document.getElementById("demo5").innerHTML = "";
					}
								
					
					
					}}}};
			req.send(null);
			}





function getBen1()
{
//	alert("inside");

var frm_off=document.getElementById("frm_off").value;
var month=document.getElementById("month").value;
var Year=document.getElementById("Year").value;

if(month <=0)
{
	alert("Enter the month");
	document.getElementById("month").focus();
	return false;
	}
if(Year <=0)
{
//	alert("Enter the Year ");
	document.getElementById("Year").focus();
	return false;
	}
if(frm_off <=0)
	{
	alert("Enter the from office");
	return false;
	}

var url="../../../../../Office_Shift_new?command=getBen1&frm_off="+frm_off;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
//	alert(req.responseText);
	if(req.status==200)
	{
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="getBen1")
		{
		if(flag=="success")
			{
			var ben1=response.getElementsByTagName("BENEFICIARY_SNO")[0].firstChild.nodeValue;
			
			document.getElementById("ben1").innerHTML=ben1;
			
		//	document.getElementById("ben1").value=ben1;
			
			
				}}}}};
req.send(null);
}

function getBen2()
{
//	alert("inside Ben2");
var month=document.getElementById("month").value;
var Year=document.getElementById("Year").value;
var frm_off=document.getElementById("frm_off").value;
var month1=month-1;
if(month1==0)
{
month1=12;
}
if(month==1)
	{
	Year=Year-1;
	}
//alert("month1 is displayed"+month1);
//alert("Year is displayed"+Year);


if(frm_off <=0)
{
	//alert("Enter the from office");
	return false;
	}

if(month <=0)
{
//	alert("Enter the month");
	document.getElementById("month").focus();
	return false;
	}

if(Year <=0)
{
//	alert("Enter the Year");
	return false;
	}
var url="../../../../../Office_Shift_new?command=getBen2&month="+month1+"&Year="+Year+"&frm_off="+frm_off;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
//	alert(req.responseText);
	if(req.status==200)
	{
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="getBen2")
		{
		if(flag=="success")
			{
			var ben2=response.getElementsByTagName("BENEFICIARY_SNO")[0].firstChild.nodeValue;
			document.getElementById("ben2").value=ben2;
			document.getElementById("Ben2").innerHTML=ben2;

			if(ben2==0)
			{
			//	alert("Bills Not Generated and Ledger Not Posted for "+getmonth1);
			//	alert("pls Check Entered Shifting Month and Year");
				var sr = "Options will be disabled if "+getmonth1+" Demand Bill Generation and Ledge poster is not completed";
				   var res = sr.fontcolor("red");
				   document.getElementById("demo2").innerHTML = res;
				   document.getElementById("b1").disabled = true; 
				   document.getElementById("b2").disabled = true; 
				   document.getElementById("b3").disabled = true; 
				   document.getElementById("b4").disabled = true; 
				   document.getElementById("b5").disabled = true; 
				   document.getElementById("b6").disabled = true; 
				   document.getElementById("b7").disabled = true;
			}else
				{
				   document.getElementById("demo2").innerHTML = "";

				 document.getElementById("b1").disabled = false; 
				   document.getElementById("b2").disabled = false; 
				   document.getElementById("b3").disabled = false; 
				   document.getElementById("b4").disabled = false; 
				   document.getElementById("b5").disabled = false;
				   document.getElementById("b6").disabled = false; 
				   document.getElementById("b7").disabled = false;
				}
			
				}
		
		}}}};
req.send(null);
}
function getBen3()
{
//	alert("inside Ben3");
var month=document.getElementById("month").value;
var Year=document.getElementById("Year").value;
var frm_off=document.getElementById("frm_off").value;
var month1=month-1;
if(month1==0)
{
month1=12;
}
if(month==1)
	{
	Year=Year-1;
	}
//alert("month1 is displayed"+month1);
//alert("Year is displayed"+Year);


if(frm_off <=0)
{
//	alert("Enter the from office");
	return false;
	}

if(month <=0)
{
	//alert("Enter the month");
	document.getElementById("month").focus();
	return false;
	}

if(Year <=0)
{
	//alert("Enter the Year");
	return false;
	}
var url="../../../../../Office_Shift_new?command=getBen3&month="+month1+"&Year="+Year+"&frm_off="+frm_off;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
	//alert(req.responseText);
	if(req.status==200)
	{
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="getBen3")
		{
		if(flag=="success")
			{
			var ben3=response.getElementsByTagName("BENEFICIARY_SNO")[0].firstChild.nodeValue;
			document.getElementById("ben3").innerHTML=ben3;
			//document.getElementById("ben3").value=ben3;

				}
		
		}}}};
req.send(null);
}

function getBen4()
{
	//alert("inside Ben4");
	var month=document.getElementById("month").value;
	var Year=document.getElementById("Year").value;

	if(month <=0)
	{
	//	alert("Enter the month");
		document.getElementById("month").focus();
		return false;
		}
	if(Year <=0)
	{
	//	alert("Enter the Year");
		return false;
		}
var Year=document.getElementById("Year").value;
var frm_off=document.getElementById("frm_off").value;
var url="../../../../../Office_Shift_new?command=getBen4&month="+month+"&Year="+Year+"&frm_off="+frm_off;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
//	alert(req.responseText);
	if(req.status==200)
	{
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="getBen4")
		{
		if(flag=="success")
			{
			var ben4=response.getElementsByTagName("BENEFICIARY_SNO")[0].firstChild.nodeValue;
		//	document.getElementById("ben4").value=ben4;
			document.getElementById("ben4").innerHTML=ben4;

				}
		
		}}}};
req.send(null);
}



function getBen5()
{
//	alert("inside Ben5");

	var ben2=document.getElementById("ben2").value;
	var getmonth1=document.getElementById("getmonth1").value;

	var month=document.getElementById("month").value;
	if(month <=0)
	{
	//	alert("Enter the month");
		document.getElementById("month").focus();
		return false;
		}

var Year=document.getElementById("Year").value;

var frm_off=document.getElementById("frm_off").value;
var month1=month-1;
if(month1==0)
{
month1=12;
}
if(month==1)
	{
	Year=Year-1;
	}
//alert("month1 is displayed"+month1);
//alert("Year is displayed"+Year);


if(frm_off <=0)
{
//	alert("Enter the from office");
	return false;
	}

if(Year <=0)
{
//	alert("Enter the Year");
	return false;
	}
var url="../../../../../Office_Shift_new?command=getBen5&month="+month1+"&Year="+Year+"&frm_off="+frm_off;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
	//alert(req.responseText);
	if(req.status==200)
	{
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="getBen5")
		{
		if(flag=="success")
			{
			var ben5=response.getElementsByTagName("BENEFICIARY_SNO")[0].firstChild.nodeValue;
			document.getElementById("ben5").value=ben5;
			document.getElementById("Ben5").innerHTML=ben5;

		

			
			if(ben5==0)
			{
				alert("Bills Not Generated and Ledger Not Posted for "+getmonth1);
				alert("pls Check Entered Shifting Month and Year");
				var sr = "Options will be disabled if "+getmonth1+" Demand Bill Generation and Ledge poster is not completed";
				   var res = sr.fontcolor("red");
				   document.getElementById("demo2").innerHTML = res;
				   document.getElementById("b1").disabled = true; 
				   document.getElementById("b2").disabled = true; 
				   document.getElementById("b3").disabled = true; 
				   document.getElementById("b4").disabled = true; 
				   document.getElementById("b5").disabled = true; 
				   document.getElementById("b6").disabled = true; 
				   document.getElementById("b7").disabled = true; 
			}
			
			}
		}}}};
req.send(null);
}



function getBen6()
{
//	alert("inside getBen6");

var frm_off=document.getElementById("frm_off").value;
var month=document.getElementById("month").value;
var Year=document.getElementById("Year").value;

if(Year <=0)
{
//	alert("Enter the Year");
	return false;
	}
if(month <=0)
{
//	alert("Enter the month");
	document.getElementById("month").focus();
	return false;
	}

if(frm_off <=0)
	{
//	alert("Enter the from office");
	return false;
	}


var url="../../../../../Office_Shift_new?command=getBen6&frm_off="+frm_off;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
//	alert(req.responseText);
	if(req.status==200)
	{
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="getBen6")
		{
		if(flag=="success")
			{
			var ben6=response.getElementsByTagName("BENEFICIARY_SNO")[0].firstChild.nodeValue;
			
			document.getElementById("ben6").innerHTML=ben6;
		//	document.getElementById("ben6").value=ben6;
			
			
				}}}}};
req.send(null);
}
function getBen7()
{
//	alert("inside");

var frm_off=document.getElementById("frm_off").value;
var month=document.getElementById("month").value;
var Year=document.getElementById("Year").value;

if(Year <=0)
{
//	alert("Enter the Year");
	return false;
	}

if(month <=0)
{
//	alert("Enter the month");
	document.getElementById("month").focus();
	return false;
	}

if(frm_off <=0)
	{
//	alert("Enter the from office");
	return false;
	}


var url="../../../../../Office_Shift_new?command=getBen7&frm_off="+frm_off;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
//	alert(req.responseText);
	if(req.status==200)
	{
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="getBen7")
		{
		if(flag=="success")
			{
			var ben7=response.getElementsByTagName("BENEFICIARY_SNO")[0].firstChild.nodeValue;
			
			document.getElementById("ben7").innerHTML=ben7;			
		//	document.getElementById("ben7").value=ben7;
			
			
				}}}}};
req.send(null);
}


function getmonth()
{
//	alert("inside getmonth");
	var mon;
	var k=",";
	
	var getmonth1=document.getElementById("getmonth1").value;

	
	var month=document.getElementById("month").value;
	if(month <=0)
	{
//		alert("Enter the month");
		document.getElementById("month").focus();
		return false;
		}

var Year=document.getElementById("Year").value;
if(Year <=0)
{
//	alert("Enter the Year");
	document.getElementById("Year").focus();
	return false;
	}
var month1=month-1;
if(month1==0)
{
month1=12;
}
if(month==1)
	{
	Year=Year-1;
	}

if(month1==1)
{
mon="January";
}
if(month1==2)
{
mon="February";
}
if(month1==3)
{
mon="March";
}
if(month1==4)
	{
mon="April";
	}
if(month1==5)
{
mon="May";
}
if(month1==6)
	{
mon="June";
	}
if(month1==7)
	{
mon="July";
	}
if(month1==8)
	{
mon="August";
	}
if(month1==9)
	{
mon="September";
	}
if(month1==10)
	{
mon="October";
	}
if(month1==11)
{
mon="November";
}
if(month1==12){
mon="December";	
}
//alert("month is")
mon=mon +k + Year;
document.getElementById("getmonth").value=mon;
document.getElementById("getmonth").hidden = false;
document.getElementById("getmonth1").value=mon;
//document.getElementById("getmonth1").hidden = false;
document.getElementById("get").innerHTML=mon+"  : ";

}
	
function getmon()
{
	//alert("inside getmon and year");
	var mon;
	var k=",";

	var month=document.getElementById("month").value;
	if(month <=0)
	{
//		alert("Enter the month");
		document.getElementById("month").focus();
		return false;
		}

var Year=document.getElementById("Year").value;
if(Year <=0)
{
//	alert("Enter the Year");
	document.getElementById("Year").focus();
	return false;
	}
if(month==1)
{
mon="January";
}
if(month==2)
{
mon="February";
}
if(month==3)
{
mon="March";
}
if(month==4)
	{
mon="April";
	}
if(month==5)
{
mon="May";
}
if(month==6)
	{
mon="June";
	}
if(month==7)
	{
mon="July";
	}
if(month==8)
	{
mon="August";
	}
if(month==9)
	{
mon="September";
	}
if(month==10)
	{
mon="October";
	}
if(month==11)
{
mon="November";
}
if(month==12){
mon="December";	
}

mon=mon +k + Year;
//document.getElementById("mon").value=mon;
document.getElementById("mon").innerHTML = mon;
//alert("month is"+mon)
}
	
function getval()
{
	var ben2=document.getElementById("ben2").value;
	var ben5=document.getElementById("ben5").value;
	var getmonth1=document.getElementById("getmonth1").value;
//	alert("getmonth1"+getmonth1);

	if(ben2 == 0 || ben5==0)
	{
	//	alert("ben2"+ben2);
	var sr = "Options will be disabled if "+getmonth1+" Demand Bill Generation and Ledge poster is not completed";
	   var res = sr.fontcolor("red");
	   document.getElementById("demo2").innerHTML = res;
	}	
}

function Newcheck()
{

var month=document.getElementById("month").value;
var year=document.getElementById("year").value;

if(month=="" || month==null )
{
alert("Enter the month");
document.getElementById("month").focus();
return false;
}
if(month>12)
{
alert("The month can be only upto 12");
document.getElementById("month").value="";

document.getElementById("month").focus();
return false;
}
if(year=="" || year==null )
{
alert("Enter the year");
document.getElementById("year").focus();
return false;
}



var url="../../../../../Office_Shift_new?command=yearValidation";
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
//	alert(req.responseText);
	if(req.status==200)
	{
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if(command=="yearValidation")
		{
		if(flag=="success")
			{
		//	alert("success");

			var Server_Year=response.getElementsByTagName("Year")[0].firstChild.nodeValue;
			var Server_month=response.getElementsByTagName("month")[0].firstChild.nodeValue;
			var Da_te=response.getElementsByTagName("Da")[0].firstChild.nodeValue;
			/*
			if(month>Server_month)
			{
			alert("The month is not correct");
			document.getElementById("month").value="";

			document.getElementById("month").focus();
		    location=location;
		    return false;
			}
			if(year>Server_Year)
			{
			alert("The year is not correct");
			document.getElementById("year").value="";

			document.getElementById("year").focus();
		    location=location;
			return false;
			}
			*/
			}}}}};		
			req.send(null);
			}

function NewValue()
{
	var month=document.getElementById("month").value;
	var year=document.getElementById("year").value;
	var Div=document.getElementById("Div").value;
	var mon;

	if(month==1)
	{
	mon="January";
	}
	if(month==2)
	{
	mon="February";
	}
	if(month==3)
	{
	mon="March";
	}
	if(month==4)
		{
	mon="April";
		}
	if(month==5)
	{
	mon="May";
	}
	if(month==6)
		{
	mon="June";
		}
	if(month==7)
		{
	mon="July";
		}
	if(month==8)
		{
	mon="August";
		}
	if(month==9)
		{
	mon="September";
		}
	if(month==10)
		{
	mon="October";
		}
	if(month==11)
	{
	mon="November";
	}
	if(month==12){
	mon="December";	
	}
	
	if(Div=="" || Div==null )
	{
		return false;
	}
	
	
	if(month=="" || month==null )
	{
		return false;
	}
	if(month>12)
	{
		return false;
	}
	if(year=="" || year==null )
	{
		return false;
	}
	
	var url="../../../../../Office_Shift_new?command=NewValue&month="+month+"&year="+year+"&Div="+Div;
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{

	if(req.readyState==4)
		{
	//	alert(req.responseText);

		if(req.status==200)
		{
			var response=req.responseXML.getElementsByTagName("response")[0];
			var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
			var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
					
		if(command=="NewValue")
			{
			if(flag=="success")
				{
			//	alert("success");

				var DCB_FREEZE=response.getElementsByTagName("DCB_FREEZE")[0].firstChild.nodeValue;

			//	alert("DCB_FREEZE"+DCB_FREEZE);

				if(DCB_FREEZE=='Y')
	{
//	alert("Freezed");

	var sr = mon+","+year+" is Freezed";
	   var res = sr.fontcolor("green");
	   document.getElementById("demo2").innerHTML = res;
		}
				}else
				{
			//		alert("Not Freezed");

					var sr = mon+","+year+" is not Freezed";
					   var res = sr.fontcolor("red");
					   document.getElementById("demo2").innerHTML = res;
								}
			}}}};
				req.send(null);
				}


function getid()
{
//	alert("inside");

var off_id=document.getElementById("off_id").value;
//var selmonth=document.getElementById("selmonth").value;
//var year=document.getElementById("year").value;
// alert("off_id"+off_id);
// alert("selmonth"+selmonth);
// alert("year"+year);
var old_office_id=new Array();
var option=new Array();
var office_name=new Array();

var url ="../../../../../../Office_Shift_new?command=getid&off_id="+off_id;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
//	alert(req.responseText);
	if(req.status==200)
	{
		
		
//		  var select = document.getElementById("def_div");
//		    select.innerText="";
//		    select.innerHTML="";
//		    while (select.childNodes.length > 0) {
//		    	select.removeChild(tgrid4.firstChild);
//		   					          }   
		    
		    
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="getid")
		{
		if(flag=="success")
			{
		//	   alert("inside success");

			var len=response.getElementsByTagName("count").length;
		//	  alert("inside len"+len);

			 for ( var i = 0; i <= len; i++)
			   {
					 
				 
			    old_office_id[i]=response.getElementsByTagName("old_office_id")[i].firstChild.nodeValue;
			    
			    office_name[i]=response.getElementsByTagName("office_name")[i].firstChild.nodeValue;
			 
			   	    
			     select = document.getElementById("def_div");
			     option[i] = document.createElement("option");
			     option[i].value=old_office_id[i];
			     option[i].innerHTML=office_name[i];
			     select.appendChild(option[i]);

			   				 

			// document.getElementById("def_div").innerHTML="<option value="+old_office_id[i]+">"+old_office_id[i]+"</option>";
			    }
			
		
				}}}}};
req.send(null);
}

//----------------------------------------used in Review Report ---------------------------------------------------------

function check1()
{
//	alert("inside");
var def_name=document.getElementById("def_name").value;	
var def_div=document.getElementById("def_div").value;
var month=document.getElementById("pmonth").value;
var year=document.getElementById("pyear").value;
// alert("month"+month);
// alert("year"+year);
// alert("def_div"+def_div);
var old_office_id=new Array();
var option=new Array();
var office_name=new Array();

var url ="../../../../../../Office_Shift_new?command=check1&month="+month+"&year="+year+"&def_div="+def_div;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
//	alert(req.responseText);
	if(req.status==200)
	{
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="check1")
		{
		if(flag=="success")
			{
			if(def_div !="5000")
				{
		//	   alert("inside success");
			var ben1=response.getElementsByTagName("BENEFICIARY_SNO")[0].firstChild.nodeValue;
		if(ben1<0 || ben1==0)
			{
			alert("There is no data for "+def_name+ " for the given month="+month+ "and year=" +year);
			
			}
				}
		
				}}}}};
req.send(null);
}
//---------------------------------------used in review Report ----------------------

function check2()
{
//	alert("inside success");
var def_div=document.getElementById("def_div").value;
// alert("def_div is"+def_div);
var url="../../../../../../Office_Shift_new?command=check2&def_div="+def_div;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
	//alert(req.responseText);
	if(req.status==200)
	{
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="check2")
		{
		if(flag=="success")
			{
			var def_name=response.getElementsByTagName("def_name")[0].firstChild.nodeValue;
			document.getElementById("def_name").value=def_name;

			}}}}};
req.send(null);
}


function getdefunt()
{
	alert("inside");

var off_id=document.getElementById("off_id").value;
// alert("off_id"+off_id);
// alert("selmonth"+selmonth);
// alert("year"+year);
var old_office_id=new Array();
var option=new Array();
var office_name=new Array();

var url ="../../../../../Office_Shift_new?command=getdefunt&off_id="+off_id;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
	alert(req.responseText);
	if(req.status==200)
	{
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="getdefunt")
		{
		if(flag=="success")
			{
		//	   alert("inside success");

			var len=response.getElementsByTagName("count").length;
		//	  alert("inside len"+len);

			 for ( var i = 0; i <= len; i++)
			   {
					 
				 
			    old_office_id[i]=response.getElementsByTagName("old_office_id")[i].firstChild.nodeValue;
			    
			    office_name[i]=response.getElementsByTagName("office_name")[i].firstChild.nodeValue;

			    var select = document.getElementById("def_div");
			     option[i] = document.createElement("option");
			     option[i].value=old_office_id[i];
			     option[i].innerHTML=office_name[i];
			     select.appendChild(option[i]);

			   				 

			// document.getElementById("def_div").innerHTML="<option value="+old_office_id[i]+">"+old_office_id[i]+"</option>";
			    }
			
		
				}}}}};
req.send(null);
}
function del()
{		 
	var office_id=document.getElementById("office_id").value;
    var month=document.getElementById("month").value;
    var year=document.getElementById("year").value;
 //   alert("office_id"+office_id);
 //   alert("month"+month);
 //   alert("year"+year);
    var txt;
    if (confirm("Confirm delete ?")) {
        txt = "You pressed OK!";
        
        var url="../../../../../Office_Shift_new?command=delete&month="+month+"&year="+year+"&office_id="+office_id;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
    if(req.readyState==4)
    	{
//    	alert(req.responseText);
    	if(req.status==200)
    	{
    		var response=req.responseXML.getElementsByTagName("response")[0];
    		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
    		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
    		
        			if(command=="delete")
        			{
        				// alert("inside delete");
        				if(flag=="success")	  
        					{
        					   						
        					    alert("Successfully Deleted");
        					    window.close(); 
        						
        					}
        				if(flag=="failure")	  
        	    			{
        						alert("Posted Data Cannot Be Deleted for  " + month+ " / "+ year + ".\n Either DCB Data Freezed OR Ledger Data Not Posted.\n Unfreeze DCB Data is Freezed." );
        	    			}
        				if(flag=="error")	
        					{
        					alert("Error");
        					}
        			}
        			
    	}}};
    	req.send(null);
        
    } else {
        txt = "You pressed Cancel!";
    }
       	}

function bencode1()
{		 
	//alert("inside bencode1");
	var bencode1=document.getElementById("bencode1").value;
	var office_id=document.getElementById("frm_off").value;
	//alert("office_id"+office_id);
	if(bencode1 <=0 )
		{
		alert("Enter B1");
		return false;
		}
	 var url="../../../../../Office_Shift_new?command=bencode1&bencode1="+bencode1+"&office_id="+office_id;
	      var req=getTransport();
      req.open("GET",url,true);
      req.onreadystatechange=function()
      {
    //	  alert("req"+req)
       if(req.readyState==4)
  	{
  	// alert(req.responseText);
  	if(req.status==200)
  	{
  	//	alert("inside");
  		var response=req.responseXML.getElementsByTagName("response")[0];
  		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
  		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
  		    if(command=="bencode1")
      			{
  		    	if(flag=="success")	  
				{
  		    		
  		    		var OLD_OFFICE_STATUS=response.getElementsByTagName("OLD_OFFICE_STATUS")[0].firstChild.nodeValue;
  		    		var OFFICE=response.getElementsByTagName("OFFICE")[0].firstChild.nodeValue;
  					var OLD_OFFICE=response.getElementsByTagName("OLD_OFFICE")[0].firstChild.nodeValue;
  					var BEN_TYPE_DESC=response.getElementsByTagName("BEN_TYPE_DESC")[0].firstChild.nodeValue;		
  					var URBANLB_SNO=response.getElementsByTagName("URBANLB_SNO")[0].firstChild.nodeValue;	
  					var VILLAGE_PANCHAYAT_SNO=response.getElementsByTagName("VILLAGE_PANCHAYAT_SNO")[0].firstChild.nodeValue;	
  					var OTHERS_PRIVATE_SNO=response.getElementsByTagName("OTHERS_PRIVATE_SNO")[0].firstChild.nodeValue;	
  					var BENEFICIARY_NAME=response.getElementsByTagName("BENEFICIARY_NAME")[0].firstChild.nodeValue;	
  					var total=parseInt(URBANLB_SNO)+parseInt(VILLAGE_PANCHAYAT_SNO)+parseInt(OTHERS_PRIVATE_SNO);
  					 var set="-";
  				
  					if(OLD_OFFICE_STATUS == "null" ||OLD_OFFICE_STATUS == "" || OLD_OFFICE_STATUS == "NULL" )
  						{
  						OLD_OFFICE_STATUS = set;
  						OLD_OFFICE = set;
  						
  						}
  					
  					document.getElementById("b1_office").innerHTML = OFFICE;
  					document.getElementById("b1_old").innerHTML = OLD_OFFICE;
  					document.getElementById("b1_old111").innerHTML = OLD_OFFICE_STATUS;
  					document.getElementById("b1_ben").innerHTML = BEN_TYPE_DESC;
  					document.getElementById("b1_ref").innerHTML = total;
  					document.getElementById("b1_hidden").value= total;

  					document.getElementById("ben_name1").innerHTML = BENEFICIARY_NAME;
 					
				}else
					{
					alert("Check the Beneficiary No");
					
					}
					
      			}}}
  	        			
	};
	req.send(null);
    
      }

function bensch1()
{		 
	//alert("inside bensch1");
	var bencode1=document.getElementById("bencode1").value;
	var office_id=document.getElementById("frm_off").value;
	
	document.getElementById("code1").innerHTML ="("+bencode1+")";
	//alert("office_id"+office_id);
	if(bencode1 <=0 )
		{
		return false;
		}
		  var url="../../../../../Office_Shift_new?command=bensch1&bencode1="+bencode1+"&office_id="+office_id;
	   
      var req=getTransport();
      req.open("GET",url,true);
      req.onreadystatechange=function()
      {
    //	  alert("req"+req)
       if(req.readyState==4)
  	{
  //	alert(req.responseText);
  	if(req.status==200)
  	{
  	//	alert("inside");
  		var response=req.responseXML.getElementsByTagName("response")[0];
  		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
  		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
  		    if(command=="bensch1")
      			{
  		    	if(flag=="success")	  
				{
  		    		var len=response.getElementsByTagName("count").length;
  		    	//	alert("inside");
  		    		var total_scheme="";
  		    		var comma=" , ";
  		    		var Scheme=new Array();
  		    		
  		    		if(len==1)
  		    			{
  		    			for ( var i = 0; i <= len; i++)
  	  				   {
  	  						   					 
  	  		    			Scheme[i]=response.getElementsByTagName("Scheme")[i].firstChild.nodeValue;
  	  		    			total_scheme=Scheme[i];
  	  		    			document.getElementById("b1_scheme").innerHTML =total_scheme;
  	  				   }	
  		    					    			
  		    			}
  		    		else
  		    			{
  		    			
  		    			
  		    		for ( var i = 0; i <= len; i++)
  				   {
  						   					 
  		    			Scheme[i]=response.getElementsByTagName("Scheme")[i].firstChild.nodeValue;
  		    			total_scheme=Scheme[i]+comma+total_scheme;
  		    		//	alert("Scheme[i]"+Scheme[i]);	
  		    		//	alert("total_scheme is "+total_scheme);
  		    			document.getElementById("b1_scheme").innerHTML =total_scheme;
  				   }				   
  					         		
  		    			}
  		 					
				}else
					{
					alert("Their is no beneficiary");
					}
					
      			}}}
  	        			
	};
	req.send(null);
    
      }
   	
function bencode2()
{		 
	//alert("inside bencode1");
	var bencode1=document.getElementById("bencode2").value;
	var office_id=document.getElementById("frm_off").value;
	document.getElementById("code2").innerHTML ="("+bencode1+")";
	//alert("office_id"+office_id);
	if(bencode1 <=0 )
		{
		alert("Enter B2");
		return false;
		}
	 var url="../../../../../Office_Shift_new?command=bencode1&bencode1="+bencode1+"&office_id="+office_id;
	      var req=getTransport();
      req.open("GET",url,true);
      req.onreadystatechange=function()
      {
    //	  alert("req"+req)
       if(req.readyState==4)
  	{
  //	alert(req.responseText);
  	if(req.status==200)
  	{
  	//	alert("inside");
  		var response=req.responseXML.getElementsByTagName("response")[0];
  		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
  		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
  		    if(command=="bencode1")
      			{
  		    	if(flag=="success")	  
				{
  		    		var OLD_OFFICE_STATUS=response.getElementsByTagName("OLD_OFFICE_STATUS")[0].firstChild.nodeValue;
  		    		var OFFICE=response.getElementsByTagName("OFFICE")[0].firstChild.nodeValue;
  					var OLD_OFFICE=response.getElementsByTagName("OLD_OFFICE")[0].firstChild.nodeValue;
  					var BEN_TYPE_DESC=response.getElementsByTagName("BEN_TYPE_DESC")[0].firstChild.nodeValue;		
  					var URBANLB_SNO=response.getElementsByTagName("URBANLB_SNO")[0].firstChild.nodeValue;	
  					var VILLAGE_PANCHAYAT_SNO=response.getElementsByTagName("VILLAGE_PANCHAYAT_SNO")[0].firstChild.nodeValue;	
  					var OTHERS_PRIVATE_SNO=response.getElementsByTagName("OTHERS_PRIVATE_SNO")[0].firstChild.nodeValue;	
  					var BENEFICIARY_NAME=response.getElementsByTagName("BENEFICIARY_NAME")[0].firstChild.nodeValue;
  					var total=parseInt(URBANLB_SNO)+parseInt(VILLAGE_PANCHAYAT_SNO)+parseInt(OTHERS_PRIVATE_SNO);
  					 var set="-";
  					
  					if(OLD_OFFICE_STATUS == "null" ||OLD_OFFICE_STATUS == "" || OLD_OFFICE_STATUS == "NULL" )
						{
						OLD_OFFICE_STATUS = set;
						OLD_OFFICE = set;
						}
  					
  					document.getElementById("b2_office").innerHTML = OFFICE;
  					document.getElementById("b2_old").innerHTML = OLD_OFFICE;
  					document.getElementById("b2_old111").innerHTML = OLD_OFFICE_STATUS;
  					document.getElementById("b2_ben").innerHTML = BEN_TYPE_DESC;
  					document.getElementById("b2_ref").innerHTML = total;
  					document.getElementById("b2_hidden").value= total;
  					document.getElementById("ben_name2").innerHTML = BENEFICIARY_NAME;
  					
				}else
					{
					alert("Check the Beneficiary No");
					}
					
      			}}}
  	        			
	};
	req.send(null);
    
      }

function bensch2()
{		 
	//alert("inside bensch1");
	var bencode1=document.getElementById("bencode2").value;
	var office_id=document.getElementById("frm_off").value;
	
	
	//alert("office_id"+office_id);
	if(bencode1 <=0 )
		{
		return false;
		}
		  var url="../../../../../Office_Shift_new?command=bensch1&bencode1="+bencode1+"&office_id="+office_id;
	   
      var req=getTransport();
      req.open("GET",url,true);
      req.onreadystatechange=function()
      {
    //	  alert("req"+req)
       if(req.readyState==4)
  	{
  //	alert(req.responseText);
  	if(req.status==200)
  	{
  	//	alert("inside");
  		var response=req.responseXML.getElementsByTagName("response")[0];
  		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
  		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
  		    if(command=="bensch1")
      			{
  		    	if(flag=="success")	  
				{
  		    		var len=response.getElementsByTagName("count").length;
  		    	//	alert("inside");
  		    		var total_scheme="";
  		    		var comma=" , ";
  		    		var Scheme=new Array();
  		    		
  		    		if(len==1)
		    			{
		    			for ( var i = 0; i <= len; i++)
	  				   {
	  						   					 
	  		    			Scheme[i]=response.getElementsByTagName("Scheme")[i].firstChild.nodeValue;
	  		    			total_scheme=Scheme[i];
	  		    			document.getElementById("b2_scheme").innerHTML =total_scheme;
	  				   }	
		    					    			
		    			}
		    		else
		    			{
		    			
		    			
		    		for ( var i = 0; i <= len; i++)
				   {
						   					 
		    			Scheme[i]=response.getElementsByTagName("Scheme")[i].firstChild.nodeValue;
		    			total_scheme=Scheme[i]+comma+total_scheme;
		    		//	alert("Scheme[i]"+Scheme[i]);	
		    		//	alert("total_scheme is "+total_scheme);
		    			document.getElementById("b2_scheme").innerHTML =total_scheme;
				   }				   
					         		
		    			}
  					
				}else
					{
					
					}
					
      			}}}
  	        			
	};
	req.send(null);
    
      }
function sel_b1()
{
	document.getElementById("dis").innerHTML ="";
	var bencode1=document.getElementById("bencode1").value;
	document.getElementById("dis").innerHTML ="B1 ("+bencode1+")";
	
}
function sel_b2()
{
	document.getElementById("dis").innerHTML ="";
	var bencode2=document.getElementById("bencode2").value;
	document.getElementById("dis").innerHTML ="B2 ("+bencode2+")";
}

function Ben_Merge()
{		 
	var user_id=document.getElementById("user_id").value;
	var b1_ref=document.getElementById("b1_hidden").value;
	var b2_ref=document.getElementById("b2_hidden").value;
	var office_id=document.getElementById("frm_off").value;
	 var Year1=document.getElementById("Year").value;
	 var month=document.getElementById("month").value;
	var Year=document.getElementById("Year").value;
	var closed_Ben="";
	var open_Ben="";
	var month1=month-1;
		if(month1==0)
		{
		month1=12;
		}
		if(month==1)
			{
			Year1=Year-1;
			}
	if (document.getElementById('r1').checked) {
		closed_Ben = document.getElementById('bencode1').value;
		open_Ben = document.getElementById('bencode2').value;
		}
	if (document.getElementById('r2').checked) {
		closed_Ben = document.getElementById('bencode2').value;
		open_Ben = document.getElementById('bencode1').value;
		}
		
	/*	
	if(b1_ref != b2_ref)
		{
		alert ("The B1 Reference No and B2 Reference no are not same ");
		return false;		
		}
	*/	
	
	if(closed_Ben <=0 || closed_Ben =="" || open_Ben <=0 || open_Ben =="" )
	{
	alert ("Select the Merge Option ");
	return false;		
	}
	 var url="../../../../../Office_Shift_new?command=Ben_Merge&closed_Ben="+closed_Ben+"&open_Ben="+open_Ben+"&Year1="+Year1+"&Year="+Year+"&month1="+month1+"&month="+month+"&office_id="+office_id+"&user_id="+user_id;
	
	 var req=getTransport();
     req.open("GET",url,true);
     req.onreadystatechange=function()
     {
   	//  alert("url"+url);
      if(req.readyState==4)
 	{
 
   // 	alert(req.responseText);
 	if(req.status==200)
 	{
 	//	alert("inside");
 		var response=req.responseXML.getElementsByTagName("response")[0];
 		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
 		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
 		    if(command=="Ben_Merge")
     			{
 		    	if(flag=="success")	  
				{
 		    		    alert("Updated Sucessfully");		    					    			
		    			}
		    		else
		    			{
		    			alert("Failed");		    		 		
		    			}
 							
     			}}}
 	        			
	};
	req.send(null);
   
}

function Delete_Demand()
{		 
	var Office_id=document.getElementById("Office_id").value;
    var month=document.getElementById("fmonth").value;
    var year=document.getElementById("fyear").value;
 //   alert("Office_id"+Office_id);
 //   alert("month"+month);
 //   alert("year"+year);
    var txt;
    if (confirm("Confirm delete ?")) {
        txt = "You pressed OK!";
        
        var url="../../../../../Office_Shift_new?command=Delete_Demand&month="+month+"&year="+year+"&Office_id="+Office_id;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
    if(req.readyState==4)
    	{
//    	alert(req.responseText);
    	if(req.status==200)
    	{
    		var response=req.responseXML.getElementsByTagName("response")[0];
    		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
    		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
    		
        			if(command=="Delete_Demand")
        			{
        				// alert("inside delete");
        				if(flag=="success")	  
        					{
        					   						
        					    alert("Successfully Deleted");
        					    location=location; 
        						
        					}else
        						{
        						alert("Not Scessfull");
        				        location=location; 
        						}
        			}
        			
    	}}};
    	req.send(null);
        
    } else {
        txt = "You pressed Cancel!";
    }
       	}
  	

function get_ben_type()
{
//	alert("inside");

	var fyear=document.getElementById("year1").value;
	
	 if(fyear==0 || fyear=="")
		 {
		 var year=document.getElementById("year").value;
		 var to_year=year;
		 var month=document.getElementById("selmonth").value;
		 var Frm_month=document.getElementById("selmonth1").value;
		 }
	 else
		 {
		 var year=fyear;
		 var to_year=parseInt(year) + 1;
		 var month=3;
		 var Frm_month=4;
			 }
	
var old_off_id=document.getElementById("def_div").value;

// alert("old_off_id"+old_off_id);
// alert("selmonth"+selmonth);
// alert("year"+year);
var BEN_TYPE_ID=new Array();
var option=new Array();
 var BEN_TYPE_DESC=new Array();

var url ="../../../../../../Office_Shift_new?command=get_ben_type&old_off_id="+old_off_id+"&year="+year+"&to_year="+to_year+"&month="+month+"&Frm_month="+Frm_month;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
//	alert(req.responseText);
	if(req.status==200)
	{
		    var select = document.getElementById("ben_type");
		    select.innerText="";
		    select.innerHTML="";
		    while (select.childNodes.length > 0) {
		    	select.removeChild(tgrid4.firstChild);
		   					          }   
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="get_ben_type")
		{
		if(flag=="success")
			{
		//	   alert("inside success");

			var len=response.getElementsByTagName("count").length;
		//	  alert("inside len"+len);

			 select = document.getElementById("ben_type");
		     option = document.createElement("option");
		     option.value=0;
		     option.innerHTML="Select Beneficiary type";
		     select.appendChild(option);
			
			 for ( var i = 0; i <= len; i++)
			   {
					 
				 
				 BEN_TYPE_ID[i]=response.getElementsByTagName("BEN_TYPE_ID")[i].firstChild.nodeValue;
			    
				 BEN_TYPE_DESC[i]=response.getElementsByTagName("BEN_TYPE_DESC")[i].firstChild.nodeValue;
			 		   	    
			     select = document.getElementById("ben_type");
			     option[i] = document.createElement("option");
			     option[i].value=BEN_TYPE_ID[i];
			     option[i].innerHTML=BEN_TYPE_DESC[i];
			     select.appendChild(option[i]);

			   				 

			// document.getElementById("def_div").innerHTML="<option value="+old_office_id[i]+">"+old_office_id[i]+"</option>";
			    }
			
		
				}}}}};
req.send(null);
}


function get_ben_name()
{
//alert("inside");
	var fyear=document.getElementById("year1").value;
	
	 if(fyear==0 || fyear=="")
		 {
		 var year=document.getElementById("year").value;
		 var to_year=year;
		 var month=document.getElementById("selmonth").value;
		 var Frm_month=document.getElementById("selmonth1").value;
		 }
	 else
		 {
		 var year=fyear;
		 var to_year=parseInt(year) + 1;
		 var month=3;
		 var Frm_month=4;
			 }
	 
var old_off_id=document.getElementById("def_div").value;
var ben_type=document.getElementById("ben_type").value;

 //alert("old_off_id"+old_off_id);
 //alert("ben_type"+ben_type);
 //alert("year"+year);
var BENEFICIARY_SNO=new Array();
var option=new Array();
 var BENEFICIARY_NAME=new Array();

var url ="../../../../../../Office_Shift_new?command=get_ben_name&old_off_id="+old_off_id+"&ben_type="+ben_type+"&year="+year+"&to_year="+to_year+"&month="+month+"&Frm_month="+Frm_month;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
//	alert(req.responseText);
	if(req.status==200)
	{
		    var select = document.getElementById("ben_name");
		    select.innerText="";
		    select.innerHTML="";
		    while (select.childNodes.length > 0) {
		    	select.removeChild(tgrid4.firstChild);
		   					          }   
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="get_ben_name")
		{
		if(flag=="success")
			{
		//	   alert("inside success");

			var len=response.getElementsByTagName("count").length;
		//	  alert("inside len"+len);

			 for ( var i = 0; i <= len; i++)
			   {
					 
				 
				 BENEFICIARY_NAME[i]=response.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
			    
				 BENEFICIARY_SNO[i]=response.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
			 		   	    
			     select = document.getElementById("ben_name");
			     option[i] = document.createElement("option");
			     option[i].value=BENEFICIARY_SNO[i];
			     option[i].innerHTML=BENEFICIARY_NAME[i];
			     select.appendChild(option[i]);

			   				 

			// document.getElementById("def_div").innerHTML="<option value="+old_office_id[i]+">"+old_office_id[i]+"</option>";
			    }
			
		
				}}}}};
req.send(null);
}

function getdata5()
{
	if (document.getElementById("New_off_id").value == 0) {
		alert("Enter the New Office Id ");
		return false;
	}
if (document.getElementById("New_off_id").value.length > 4) {
alert("Office Id is only 4 digit");
document.getElementById("New_off_id").value="";
document.getElementById("New_off_name").value="";
return false;
}	
	//alert("inside");
var New_off_id=document.getElementById("New_off_id").value;
var url="../../../../../Office_Shift_new?command=getdata&New_off_id="+New_off_id;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
	//alert(req.responseText);
	if(req.status==200)
	{
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="getdata")
		{
		if(flag=="success")
			{
			var New_off_name=response.getElementsByTagName("New_off_name")[0].firstChild.nodeValue;
			var OFFICE_STATUS_ID=response.getElementsByTagName("OFFICE_STATUS_ID")[0].firstChild.nodeValue;
			document.getElementById("New_off_name").innerHTML=New_off_name;
			document.getElementById("New_off_status").innerHTML=OFFICE_STATUS_ID;
			checkclosed();
			}}}}};
req.send(null);
}
function getdata6()
{
	if (document.getElementById("closed_off_id").value == 0) {
		alert("Enter the closed Office Id ");
		
		return false;
	}
if (document.getElementById("New_off_id").value.length > 4) {
alert("Office Id is only 4 digit");
document.getElementById("closed_off_id").value="";
document.getElementById("closed_off_name").value="";
return false;
}	
	//alert("inside");
var New_off_id=document.getElementById("closed_off_id").value;
var url="../../../../../Office_Shift_new?command=getdata&New_off_id="+New_off_id;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
	//alert(req.responseText);
	if(req.status==200)
	{
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="getdata")
		{
		if(flag=="success")
			{
			var New_off_name=response.getElementsByTagName("New_off_name")[0].firstChild.nodeValue;
			var OFFICE_STATUS_ID=response.getElementsByTagName("OFFICE_STATUS_ID")[0].firstChild.nodeValue;
			document.getElementById("closed_off_name").innerHTML=New_off_name;
			document.getElementById("closed_off_status").innerHTML=OFFICE_STATUS_ID;
			
			
			}}}}};
req.send(null);
}
//-----------------------------------used in Closed Office - New Office Mapping -------------------------
function insert_closed()
{
	var closed_off_id=document.getElementById("closed_off_id").value;
	var New_off_id=document.getElementById("New_off_id").value;	
	var month=document.getElementById("month").value;	
	var year=document.getElementById("year").value;	
	if (document.getElementById("month").value == 0) {
		alert("Enter the month ");
		return false;
	}
	if (document.getElementById("year").value == 0) {
		alert("Enter the year ");
		return false;
	}
	if (document.getElementById("closed_off_id").value == 0) {
		alert("Enter the closed Office Id ");
		return false;
	}
if (document.getElementById("New_off_id").value.length > 4) {
alert("Office Id is only 4 digit");
return false;
}	
if (document.getElementById("New_off_id").value == 0) {
	alert("Enter the New Office Id ");
	return false;
}
if (document.getElementById("New_off_id").value.length > 4) {
alert("Office Id is only 4 digit");
return false;
}	

var url ="../../../../../Office_Shift_new?command=closeddata&New_off_id="+New_off_id+"&closed_off_id="+closed_off_id+"&year="+year+"&month="+month;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
	//alert(req.responseText);
	if(req.status==200)
	{
		  
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="closeddata")
		{
		if(flag=="success")
			{
alert("Update Sucessfull");
		
				}else 
					alert("Update Failed");
		}}}};
req.send(null);
}

function checkclosed()
{
	var closed_off_id=document.getElementById("closed_off_id").value;
	var New_off_id=document.getElementById("New_off_id").value;
var url ="../../../../../Office_Shift_new?command=checkclosed&closed_off_id="+closed_off_id+"&New_off_id="+New_off_id;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
	//alert(req.responseText);
	if(req.status==200)
	{
		  
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	   if(command=="checkclosed")
		{
		if(flag=="success")
			{
			var len=response.getElementsByTagName("count").length;
		//	alert("len"+len);
			if(len>0)
		{
				alert("Data already exist");
				location = location;
		}
			
				}
		}}}};
req.send(null);
}

function Add_Demand()
{
	// alert("Start");
	var off_id=document.getElementById("off_id").value;
	var month=document.getElementById("month").value;
	var year=document.getElementById("year").value;
	var val1=document.getElementById("val11").value;
	var val2=document.getElementById("val22").value;
	var prv_month=0, prv_year=0;
	
	document.getElementById("val3").innerHTML="Please Wait";
	
	if(val1==val2)
	{
	//  alert("val1 is"+val1);
	//	alert("val2 is "+val2);
		alert("The datas are already correct");
		location=location;
		return false;
	}
	
//	if(month==4)
//		{
//		alert("This cannot be done for April");
//		location=location;
//		return false;
		
//		}else
//			{
	
	if(month==1)
		{
		prv_month=12;
		prv_year=year-1;
		}else
			{
			prv_month=month-1;
			prv_year=year;
			}
	// alert("Month is"+month  + "and prevmonth is"+prv_month);
	// alert("Year is"+year+"and Prv Year is "+prv_year);
	// alert("Office id is"+off_id);
	
var url ="../../../../../Add_Demand?command=Add_Demand&year="+year+"&month="+month+"&off_id="+off_id+"&prv_month="+prv_month+"&prv_year="+prv_year;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
	//alert(req.responseText);
	if(req.status==200)
	{
		  
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	   if(command=="Add_Demand")
		{
		if(flag=="success")
			{
			alert("Data Sucessfull");
			document.getElementById("val3").innerHTML="";
			location=location;
				}else
					{
					alert("Data failed");
					}
		}}}};
		
req.send(null);
}
//}
function getval11() // To get the pervious add demand value from pms ledger actual
{
	
	//var off_id=document.getElementById("off_id").value;
	var month=document.getElementById("month").value;
	var year=document.getElementById("year").value;
	var prv_month=0, prv_year=0;
	
	//if(month==4)
//		{
	//	alert("This cannot be done for April");
	//	location=location;
	//	return false;
	//	}else
	//		{
	
	if(month==1)
		{
		prv_month=12;
		prv_year=(year)-1;
		}else
			{
			prv_month=month-1;
			prv_year=year;
			}
// alert("prv_year is"+prv_year);
if(month <=0)
{
	alert("Enter the month");
	document.getElementById("month").focus();
	return false;
	}
if(year <=0)
{
	alert("Enter the Year ");
	document.getElementById("year").focus();
	return false;
	}

var url ="../../../../../Add_Demand?command=getval11&year="+prv_year+"&month="+prv_month;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
//	alert(req.responseText);
	if(req.status==200)
	{
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="getval11")
		{
		if(flag=="success")
			{
			var total=response.getElementsByTagName("total")[0].firstChild.nodeValue;
			
			document.getElementById("val1").innerHTML=total;
			document.getElementById("val11").value=total;
				
				}}}}};
req.send(null);
}

function getval1() // To get the pervious add demand value from pms ledger actual
{
	
	var off_id=document.getElementById("off_id").value;
	var month=document.getElementById("month").value;
	var year=document.getElementById("year").value;
	var prv_month=0, prv_year=0;
	
	//if(month==4)
//		{
	//	alert("This cannot be done for April");
	//	location=location;
	//	return false;
	//	}else
	//		{
	
	if(month==1)
		{
		prv_month=12;
		prv_year=(year)-1;
		}else
			{
			prv_month=month-1;
			prv_year=year;
			}
// alert("prv_year is"+prv_year);
if(month <=0)
{
	alert("Enter the month");
	document.getElementById("month").focus();
	return false;
	}
if(year <=0)
{
	alert("Enter the Year ");
	document.getElementById("year").focus();
	return false;
	}

var url ="../../../../../Add_Demand?command=getval1&year="+prv_year+"&month="+prv_month+"&off_id="+off_id;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
//	alert(req.responseText);
	if(req.status==200)
	{
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="getval1")
		{
		if(flag=="success")
			{
			var total=response.getElementsByTagName("total")[0].firstChild.nodeValue;
			
			document.getElementById("val1").innerHTML=total;
			document.getElementById("val11").value=total;
				
				}}}}};
req.send(null);
}
//}


/// BYPASS DCB FREEZE

function rld()
{
	//alert("the month is :"+month);
	 var month=document.getElementById("month").value;
     var year=document.getElementById("year").value;
     var Office_id=document.getElementById("Office_id").value;
     var count=document.getElementById("count").value;
     var unit=document.getElementById("unit").value;
     var id=document.getElementById("id").value;
  //   alert("the id is :"+id);
 //    alert("the year is :"+year);
 ////    alert("the Office_id is :"+Office_id);
 //    alert("the count is :"+count);
   if (confirm("Do you want to delete all the bills ?")) {
        txt = "You pressed OK!";
        
        if(count==0)
        	{
        	        
        var url="../../../../../Office_Shift_new?command=rld&month="+month+"&year="+year+"&unit="+unit+"&Office_id="+Office_id+"&id="+id;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
    if(req.readyState==4)
    	{
//    	alert(req.responseText);
    	if(req.status==200)
    	{
    		var response=req.responseXML.getElementsByTagName("response")[0];
    		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
    		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
    		
        			if(command=="rld")
        			{
        				// alert("inside delete");
        				if(flag=="success")	  
        					{
        					   						
        					    alert("Successfully Deleted");
        					          						
        					}else
        						{
        						alert("Not Scessfull");
        				 			}
        			}
        			
    	}}};
    	req.send(null);
   }  
     if(count==1)   
    	 alert("Already Deleted");
    } else {
        txt = "You pressed Cancel!";
    }
       	}
 //Check the beneficiary in DCB Freeze 	

function rldcheck_coll()
{
	//	alert("hi");
	var old_office_id=new Array();
	var Diff=new Array();
	var Diff1=new Array();
	 var month=document.getElementById("month").value;
     var year=document.getElementById("year").value;
     var Office_id=document.getElementById("Office_id").value;
     
     
//	 alert("y is ------->"+y);
//	 alert("m is ------->"+m);
//	 alert("id is ------->"+id);
	var url ="../../../../../Office_Shift_new?command=rldcheck_coll&year="+year+"&month="+month+"&Office_id="+Office_id;
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
	if(req.readyState==4)
		{
	//	alert(req.responseText);
		if(req.status==200)
		{
			var response=req.responseXML.getElementsByTagName("response")[0];
			var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
			var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if(command=="rldcheck_coll")
			{
			if(flag=="success")
				{
			//	alert("inside ");
				var len=response.getElementsByTagName("beneficiary_name").length;
				//	  alert("inside len"+len);
if(len!=0)
	{
	 alert("Delete the Beneficiary bill and Regenerate for the following Ben");
					 for ( var i = 0; i <= len; i++)
					   {
				    old_office_id[i]=response.getElementsByTagName("beneficiary_name")[i].firstChild.nodeValue;
				    Diff[i]=response.getElementsByTagName("Diff")[i].firstChild.nodeValue;
				    Diff1[i]=response.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
					    alert("ben name is  -"+old_office_id[i]+"("+ Diff1[i]  +") and difference is -"+Diff[i]  );
					   }
					
					}else
	{
	 alert("All beneficiaies are Correct");
	 					 
		}
				}}}}};
	req.send(null);
	 req1.send(null);}
	 
	 
//Created for checking benewisiarywise collection from FAS and DCB 

function rldcheck_coll_current_month()
{
	//	alert("hi");
	var old_office_id=new Array();
	var Diff=new Array();
	var Diff1=new Array();
	 var month=document.getElementById("month").value;
     var year=document.getElementById("year").value;
     var Office_id=document.getElementById("Office_id").value;
     
     var url ="../../../../../Office_Shift_new?command=rldcheck_coll_current_month&year="+year+"&month="+month+"&Office_id="+Office_id;
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
	if(req.readyState==4)
		{
	//	alert(req.responseText);
		if(req.status==200)
		{
			var response=req.responseXML.getElementsByTagName("response")[0];
			var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
			var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if(command=="rldcheck_coll_current_month")
			{
			if(flag=="success")
				{
			//	alert("inside ");
				var len=response.getElementsByTagName("beneficiary_name").length;
				//	  alert("inside len"+len);
if(len!=0)
	{
	 alert("Delete the Beneficiary bill and Regenerate for the following Ben");
					 for ( var i = 0; i <= len; i++)
					   {
				    old_office_id[i]=response.getElementsByTagName("beneficiary_name")[i].firstChild.nodeValue;
				    Diff[i]=response.getElementsByTagName("difference")[i].firstChild.nodeValue;
				    Diff1[i]=response.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
					    alert("ben name is  -"+old_office_id[i]+"("+ Diff1[i]  +") and difference is -"+Diff[i]  );
					   }
					
					}else
	{
	 alert("All beneficiaies are Correct");
	 					 
		}
				}}}}};
	req.send(null);
     
     
     
     }

//END Created for checking benewisiarywise collection from FAS and DCB 


	 
//Created for checking benewisiarywise collection from FAS and DCB 

function rldcheck_dmd_current_month()
{
	//	alert("hi");
	var old_office_id=new Array();
	var Diff=new Array();
	var Diff1=new Array();
	 var month=document.getElementById("month").value;
     var year=document.getElementById("year").value;
     var Office_id=document.getElementById("Office_id").value;
     
     var url ="../../../../../Office_Shift_new?command=rldcheck_dmd_current_month&year="+year+"&month="+month+"&Office_id="+Office_id;
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
	if(req.readyState==4)
		{
	//	alert(req.responseText);
		if(req.status==200)
		{
			var response=req.responseXML.getElementsByTagName("response")[0];
			var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
			var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if(command=="rldcheck_dmd_current_month")
			{
			if(flag=="success")
				{
			//	alert("inside ");
				var len=response.getElementsByTagName("beneficiary_name").length;
				//	  alert("inside len"+len);
if(len!=0)
	{
	 alert("Delete the Beneficiary bill and Regenerate for the following Ben");
					 for ( var i = 0; i <= len; i++)
					   {
				    old_office_id[i]=response.getElementsByTagName("beneficiary_name")[i].firstChild.nodeValue;
				    Diff[i]=response.getElementsByTagName("difference")[i].firstChild.nodeValue;
				    Diff1[i]=response.getElementsByTagName("beneficiary_sno")[i].firstChild.nodeValue;
					    alert("ben name is  -"+old_office_id[i]+"("+ Diff1[i]  +") and difference is -"+Diff[i]  );
					   }
					
					}else
	{
	 alert("All beneficiaies are Correct");
	 					 
		}
				}}}}};
	req.send(null);
     
     
     
     }

//END Created for checking benewisiarywise collection from FAS and DCB 








function rldcheck_dmd()
{
	//	alert("hi");
	var old_office_id=new Array();
	var Diff=new Array();
	var Diff1=new Array();
	 var month=document.getElementById("month").value;
     var year=document.getElementById("year").value;
     var Office_id=document.getElementById("Office_id").value;
     
     
//	 alert("y is ------->"+y);
//	 alert("m is ------->"+m);
//	 alert("id is ------->"+id);
	var url ="../../../../../Office_Shift_new?command=rldcheck_dmd&year="+year+"&month="+month+"&Office_id="+Office_id;
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
	if(req.readyState==4)
		{
	//	alert(req.responseText);
		if(req.status==200)
		{
			var response=req.responseXML.getElementsByTagName("response")[0];
			var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
			var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if(command=="rldcheck_dmd")
			{
			if(flag=="success")
				{
			//	alert("inside ");
				var len=response.getElementsByTagName("beneficiary_name").length;
				//	  alert("inside len"+len);
if(len!=0)
	{
	 alert("Delete the Beneficiary bill and Regenerate for the following Ben");
					 for ( var i = 0; i <= len; i++)
					   {
				    old_office_id[i]=response.getElementsByTagName("beneficiary_name")[i].firstChild.nodeValue;
				    Diff[i]=response.getElementsByTagName("Diff")[i].firstChild.nodeValue;
				    Diff1[i]=response.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
				    alert("ben name is  -"+old_office_id[i]+"("+ Diff1[i]  +") and difference is -"+Diff[i]  );
					   }
					
					}else
	{
	 alert("All beneficiaies are Correct");
	 					 
		}
				}}}}};
	req.send(null);
	 req1.send(null);}


function rldcheck_main_ob_coll()
{
	//	alert("hi");
	var old_office_id=new Array();
	var Diff=new Array();
	var Diff1=new Array();
	 var month=document.getElementById("month").value;
     var year=document.getElementById("year").value;
     var Office_id=document.getElementById("Office_id").value;
     
     
//	 alert("y is ------->"+y);
//	 alert("m is ------->"+m);
//	 alert("id is ------->"+id);
	var url ="../../../../../Office_Shift_new?command=rldcheck_main_ob_coll&year="+year+"&month="+month+"&Office_id="+Office_id;
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
	if(req.readyState==4)
		{
	//	alert(req.responseText);
		if(req.status==200)
		{
			var response=req.responseXML.getElementsByTagName("response")[0];
			var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
			var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if(command=="rldcheck_main_ob_coll")
			{
			if(flag=="success")
				{
			//	alert("inside ");
				var len=response.getElementsByTagName("beneficiary_name").length;
				//	  alert("inside len"+len);
if(len!=0)
	{
	 alert("Delete the Beneficiary bill and Regenerate for the following Ben");
					 for ( var i = 0; i <= len; i++)
					   {
				    old_office_id[i]=response.getElementsByTagName("beneficiary_name")[i].firstChild.nodeValue;
				    Diff[i]=response.getElementsByTagName("Diff")[i].firstChild.nodeValue;
				    Diff1[i]=response.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
				    alert("ben name is  -"+old_office_id[i]+"("+ Diff1[i]  +") and difference is -"+Diff[i]  );
					   }
					
					}else
	{
	 alert("All beneficiaies are Correct");
	 					 
		}
				}}}}};
	req.send(null);
	 req1.send(null);}


function rldcheck_int_out_coll()
{
	//	alert("hi");
	var old_office_id=new Array();
	var Diff=new Array();
	var Diff1=new Array();
	 var month=document.getElementById("month").value;
     var year=document.getElementById("year").value;
     var Office_id=document.getElementById("Office_id").value;
     
     
//	 alert("y is ------->"+y);
//	 alert("m is ------->"+m);
//	 alert("id is ------->"+id);
	var url ="../../../../../Office_Shift_new?command=rldcheck_int_out_coll&year="+year+"&month="+month+"&Office_id="+Office_id;
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
	if(req.readyState==4)
		{
	//	alert(req.responseText);
		if(req.status==200)
		{
			var response=req.responseXML.getElementsByTagName("response")[0];
			var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
			var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if(command=="rldcheck_int_out_coll")
			{
			if(flag=="success")
				{
			//	alert("inside ");
				var len=response.getElementsByTagName("beneficiary_name").length;
				//	  alert("inside len"+len);
if(len!=0)
	{
	 alert("Delete the Beneficiary bill and Regenerate for the following Ben");
					 for ( var i = 0; i <= len; i++)
					   {
				    old_office_id[i]=response.getElementsByTagName("beneficiary_name")[i].firstChild.nodeValue;
				    Diff[i]=response.getElementsByTagName("Diff")[i].firstChild.nodeValue;
				    Diff1[i]=response.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
				    alert("ben name is  -"+old_office_id[i]+"("+ Diff1[i]  +") and difference is -"+Diff[i]  );
					   }
					
					}else
	{
	 alert("All beneficiaies are Correct");
	 					 
		}
				}}}}};
	req.send(null);
	 req1.send(null);}


function rldcheck_int_dmd()
{
	//	alert("hi");
	var old_office_id=new Array();
	var Diff=new Array();
	var Diff1=new Array();
	 var month=document.getElementById("month").value;
     var year=document.getElementById("year").value;
     var Office_id=document.getElementById("Office_id").value;
     
     
//	 alert("y is ------->"+y);
//	 alert("m is ------->"+m);
//	 alert("id is ------->"+id);
	var url ="../../../../../Office_Shift_new?command=rldcheck_int_dmd&year="+year+"&month="+month+"&Office_id="+Office_id;
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
	if(req.readyState==4)
		{
	//	alert(req.responseText);
		if(req.status==200)
		{
			var response=req.responseXML.getElementsByTagName("response")[0];
			var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
			var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if(command=="rldcheck_int_dmd")
			{
			if(flag=="success")
				{
			//	alert("inside ");
				var len=response.getElementsByTagName("beneficiary_name").length;
				//	  alert("inside len"+len);
if(len!=0)
	{
	 alert("Delete the Beneficiary bill and Regenerate for the following Ben");
					 for ( var i = 0; i <= len; i++)
					   {
				    old_office_id[i]=response.getElementsByTagName("beneficiary_name")[i].firstChild.nodeValue;
				    Diff[i]=response.getElementsByTagName("Diff")[i].firstChild.nodeValue;
				    Diff1[i]=response.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
				    alert("ben name is  -"+old_office_id[i]+"("+ Diff1[i]  +") and difference is -"+Diff[i]  );
					   }
					
					}else
	{
	 alert("All beneficiaies are Correct");
	 					 
		}
				}}}}};
	req.send(null);
	 req1.send(null);}


function getval2() // To get the present add demand value from pms ledger actual
{
	
	var off_id=document.getElementById("off_id").value;
	var month=document.getElementById("month").value;
	var year=document.getElementById("year").value;
	
	
var url ="../../../../../Add_Demand?command=getval1&year="+year+"&month="+month+"&off_id="+off_id;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
//	alert(req.responseText);
	if(req.status==200)
	{
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="getval1")
		{
		if(flag=="success")
			{
			var total=response.getElementsByTagName("total")[0].firstChild.nodeValue;
			
			document.getElementById("val2").innerHTML=total;
			document.getElementById("val22").value=total;
						}}}}};
req.send(null);
}
function getval22() // To get the present add demand value from pms ledger actual
{
	
//	var off_id=document.getElementById("off_id").value;
	var month=document.getElementById("month").value;
	var year=document.getElementById("year").value;
	
	
var url ="../../../../../Add_Demand?command=getval11&year="+year+"&month="+month;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
//	alert(req.responseText);
	if(req.status==200)
	{
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="getval11")
		{
		if(flag=="success")
			{
			var total=response.getElementsByTagName("total")[0].firstChild.nodeValue;
			
			document.getElementById("val2").innerHTML=total;
			document.getElementById("val22").value=total;
						}}}}};
req.send(null);
}