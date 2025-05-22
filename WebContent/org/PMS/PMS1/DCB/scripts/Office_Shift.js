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



function Help()
{
	window.location.href = "../reports/jsps/Office_Region_Details.jsp";
 // winemp= window.open("../reports/jsps/Office_Region_Details.jsp");


}




function getdata()
{
	
//	alert("inside");
var frm_off=document.getElementById("frm_off").value;
if (frm_off == 0 || frm_off=="") {
	alert("Enter Office Id");
	location = location;
	document.getElementById("frm_off").value="";
	document.getElementById("frm_off").focus();
	return false;
}

var url="../../../../../Office_Shift?command=getdata&frm_off="+frm_off;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
	// alert(req.responseText);
	if(req.status==200)
	{
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="getdata")
		{
		if(flag=="success")
			{
			var off_name=response.getElementsByTagName("off_name")[0].firstChild.nodeValue;
			var off_level=response.getElementsByTagName("off_level")[0].firstChild.nodeValue;
			var off_status=response.getElementsByTagName("off_status")[0].firstChild.nodeValue;
			
			if(off_level == "DN")
				{
				var off_level1="Division";
				}
			if(off_level == "SD")
			{
			var off_level1="Sub Division";
			}if(off_level == "RN")
				{
				var off_level1="Region";
				}if(off_level == "CL")
				{
					var off_level1="Circle";
					}
				
				
			if(off_status == "CR")
				{
				var off_status1="Created";
				}
			if(off_status == "RD")
			{
			var off_status1="Redeployed";
			}
			if(off_status == "NC")
			{
			var off_status1="Nomenclature";
			}
			if(off_status == "CL")
			{
			var off_status1="Closed";
			}

			
			
			}
			document.getElementById("off_name").value=off_name;
			document.getElementById("off_level").value=off_level1;
			document.getElementById("off_status").value=off_status1;
			
			if(off_status1 == "Redeployed" || off_status1 == "Nomenclature" || off_status1 == "Closed" )
			{
					alert("Note: If Office Status is shown Nomenclature , Redeployed or Closed. Sub Division List OR Beneficiary list will not be Displayed.")

			}	
			
				}}}};
req.send(null);
}
function loadgrid1()
{
	var frm_off=document.getElementById("frm_off").value;
	
	var url="../../../../../Office_Shift?command=loadgrid1&frm_off="+frm_off;
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
				if(command=="loadgrid1")
					{
					if(flag=="success")
						{
		//			alert("inside success");
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
		//			     alert("inside clean");
					     var len=response.getElementsByTagName("count").length;
					        var seq1=1000;

					        for(var i=0;i<len;i++)
					        {
					        var scheme_sno=response.getElementsByTagName("scheme_sno")[i].firstChild.nodeValue;
					        var sch_name=response.getElementsByTagName("sch_name")[i].firstChild.nodeValue;
					        
					      
//					        
//					        
		// edit button to pass the value to register
//					       
							
//							var tr=document.createElement("TR");
//							tr.id=seq;
//							var td=document.createElement("TD");
//							var name=document.createTextNode(name_element);
//							td.appendChild(name);
//							tr.appendChild(td);  
					        
					        
					        var tr=document.createElement("TR");
					        tr.id=seq1;
                     	    var td1=document.createElement("TD");
					        var scheme_sno=document.createTextNode(scheme_sno);
					        td1.appendChild(scheme_sno);
					        tr.appendChild(td1);
					       
		        	        var td2=document.createElement("TD");
					        var sch_name=document.createTextNode(sch_name);
					        td2.appendChild(sch_name);
					        tr.appendChild(td2);
					        
//					        				        
//					    					        
		//					tr.id=seq;
							var td3=document.createElement("TD");
							var anc=document.createElement("A");
							var url="javascript:loadvalue1('"+seq1+"')";
							anc.href=url;
							var edit=document.createTextNode("View BENEFICIARY");
							anc.appendChild(edit);
							td3.appendChild(anc);
         				tr.appendChild(td3);   
						
					        
					        tgrid1.appendChild(tr); 
					        seq1++;
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
function loadgrid()
{
	var frm_off=document.getElementById("frm_off").value;
	if (frm_off == 0 || frm_off=="") {
	 //     alert("Enter Office Id");
	//		location = location;
	//		document.getElementById("frm_off").value="";
	//		document.getElementById("frm_off").focus();
			return false;
		}
	var url="../../../../../Office_Shift?command=loadgrid&frm_off="+frm_off;
//	alert(url);
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
		if(req.readyState==4)
			{
//			alert(req.responseText);
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
	//				alert("inside success");
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
	//				     alert("inside clean");
					     var len=response.getElementsByTagName("count").length;
					     var seq=0;
					        for(var i=0;i<len;i++)
					        {
					        var SUB_DIV_ID=response.getElementsByTagName("SUB_DIV_ID")[i].firstChild.nodeValue;
					        var OFFICE_NAME=response.getElementsByTagName("OFFICE_NAME")[i].firstChild.nodeValue;
					        var OFFICE_LEVEL_ID=response.getElementsByTagName("OFFICE_LEVEL_ID")[i].firstChild.nodeValue;
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
							tr.id=seq;
                     	    var td1=document.createElement("TD");
					        var SUB_DIV_ID=document.createTextNode(SUB_DIV_ID);
					        td1.appendChild(SUB_DIV_ID);
					        tr.appendChild(td1);
					       
		        	        var td2=document.createElement("TD");
					        var OFFICE_NAME=document.createTextNode(OFFICE_NAME);
					        td2.appendChild(OFFICE_NAME);
					        tr.appendChild(td2);
					        
					        var td3=document.createElement("TD");
					        var OFFICE_LEVEL_ID=document.createTextNode(OFFICE_LEVEL_ID);
					        td3.appendChild(OFFICE_LEVEL_ID);
					        tr.appendChild(td3);
					        
					        var td4=document.createElement("TD");
					        var OFFICE_STATUS_ID=document.createTextNode(OFFICE_STATUS_ID);
				            td4.appendChild(OFFICE_STATUS_ID);
					        tr.appendChild(td4);
					        
					    	var td5=document.createElement("TD");
							var anc=document.createElement("A");
							var url="javascript:loadvalue('"+seq+"')";
							anc.href=url;
							var edit=document.createTextNode("View BENEFICIARY");
							anc.appendChild(edit);
							td5.appendChild(anc);
         				tr.appendChild(td5);   
						
					        
					        
					        tgrid.appendChild(tr); 
					        seq++;
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

function loadvalue(seq)
{      
	
	
//	 alert("reached---->"+seq);
     var seq=document.getElementById(seq); 
	 var rcells=seq.cells;
var sub_div_id=rcells.item(0).firstChild.nodeValue;
var sub_div_Name=rcells.item(1).firstChild.nodeValue;
var frm_off=document.getElementById("frm_off").value;
var off_name=document.getElementById("off_name").value;

//var queryString = "?frm_off=" + frm_off + "&sub_div_id=" + sub_div_id;
//window.location.href = "Pdf.jsp" + queryString;

// alert("From Off id ***"+frm_off);

 window.location.href = "Subdiv_Beneficiary.jsp?frm_off=" + frm_off + "&sub_div_id=" + sub_div_id +  "&sub_div_Name=" + sub_div_Name + "&off_name=" + off_name ;

 // winemp= window.open("Subdiv_Beneficiary.jsp?command=add&frm_off=" + frm_off + "&sub_div_id=" + sub_div_id +  "&sub_div_Name=" + sub_div_Name + "&off_name=" + off_name,"status=1,height=500,width=500,resizable=YES, scrollbars=yes");

		
//		var queryString = "?frm_off=" + frm_off + "&sub_div_id=" + sub_div_id;
//		window.location.href = "Pdf.jsp" + queryString;

		
req.send(null);	
};


function loadvalue1(seq1)
{      
	
//	var Row = document.getElementById("tgrid1");
//	var Cells = Row.getElementsByTagName("td");
	
	// alert("reached in load1  -->"+seq1);
	 
	 var seq1=document.getElementById(seq1); 
	 var r1cells=seq1.cells;
var sche_id=r1cells.item(0).firstChild.nodeValue;
var sche_Name=r1cells.item(1).firstChild.nodeValue;
var frm_off=document.getElementById("frm_off").value;
var off_name=document.getElementById("off_name").value;

//  alert("sche_id  -->"+sche_id);
//  alert("sche_Name -->"+sche_Name);

	 
//     var seq1=document.getElementById(seq1); 
//	 var tcells=seq1.cells;
//var sche_id=Cells[0].innerText;
//var sche_Name=Cells[1].innerText;
//var frm_off=document.getElementById("frm_off").value;
//var off_name=document.getElementById("off_name").value;
window.location.href = "Scheme_Beneficiary.jsp?command=add&frm_off=" + frm_off + "&sche_id=" + sche_id +  "&sche_Name=" + sche_Name + "&off_name=" + off_name ;


 // winemp= window.open("Scheme_Beneficiary.jsp?command=add&frm_off=" + frm_off + "&sche_id=" + sche_id +  "&sche_Name=" + sche_Name + "&off_name=" + off_name,"status=1,height=500,width=500,resizable=YES, scrollbars=yes");

		
req.send(null);	
};

function proceed()
{
	// alert("inside");
	var frm_off=document.getElementById("frm_off").value;
	var off_name=document.getElementById("off_name").value;
	var off_status=document.getElementById("off_status").value;
    var off_status1;
			
	if(off_status == "Created")
		{
		var off_status1="CR";
		}
	if(off_status == "Redeployed")
	{
	var off_status1="RD";
	}
	if(off_status == "Nomenclature")
		{
		var off_status1="NC";
		}
	if(off_status == "Closed")
	{
	var off_status1="CL";
	}



	if(frm_off <=0)
		{
		alert("Enter the from office");
		return false;
		}
	
	var txt;
    if (confirm("Proceed with Shifting of Beneficiaries")) {
        txt = "You pressed OK!";
        window.location.href = "Office_Shift_new.jsp?frm_off="+frm_off+"&off_name="+off_name+"&off_status="+off_status1;
    } else {
        txt = "You pressed Cancel!";
    }
//    alert(txt);
	
}





