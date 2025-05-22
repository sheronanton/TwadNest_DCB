var service;
var seq = 0;
var __pagination=10;
  
    var totalblock=0;
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

var s=0;
 function getDesignation()
    {
        var type=document.HRM_EmpSearch.cmbsgroup.options[document.HRM_EmpSearch.cmbsgroup.selectedIndex].value;
        if(type!=0)
        {
            var din=document.getElementById("divdes");
            din.style.visibility="visible";
            document.HRM_EmpSearch.cmbdes.style.visibility="visible";
            loadOfficesByType(type);
        }
        else
        {
            document.HRM_EmpSearch.cmbdes.value="0";
             var din=document.getElementById("divdes");
            din.style.visibility="hidden";
            document.HRM_EmpSearch.cmbdes.style.visibility="hidden";
        }
    }
    
  function getEmployee()  
  
  {
       if(document.HRM_EmpSearch.txtEmpName.value.length==0 && document.HRM_EmpSearch.cmbsgroup.options[document.HRM_EmpSearch.cmbsgroup.selectedIndex].value==0)
       {
            alert('Enter or Select the employee values');
            return false;
        }
        if(document.HRM_EmpSearch.txtEmpName.value.length!=0)
        {
                if(document.HRM_EmpSearch.txtEmpName.value.length<3)
               {
                    alert('employee Name should have atleast 3 characters');
                    return false;
                }
        }
       var group=document.HRM_EmpSearch.cmbsgroup.options[document.HRM_EmpSearch.cmbsgroup.selectedIndex].value;
      try{
       var des=document.HRM_EmpSearch.cmbdes.options[document.HRM_EmpSearch.cmbdes.selectedIndex].value;
       }catch(e){var des=0;}
       var name=document.HRM_EmpSearch.txtEmpName.value;
       startwaiting(document.HRM_EmpSearch);
       service=null;
       var cell=document.getElementById("divpre");
        cell.style.display="none";
        cell.innerText='';
         var cell=document.getElementById("divcmbpage");
        cell.style.display="none";
        var cell=document.getElementById("divpage");
        cell.style.display="none";
        cell.innerText='';
        var cell=document.getElementById("divnext");
        cell.style.display="none";
        cell.innerText='';
       var url="../../../../../EmpServicePopupServ_leave.view?Command=Emp&cmbsgroup=" + group ;
       url=url+"&cmbdes="+des+"&txtEmpName="+name;
       //alert(url);
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            loadTable(req);
        }
          if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
  
  }
  
  function getEmployeeGpf()  
  
  {
  
       if(document.HRM_EmpSearch.txtgpf.value.length==0 || document.HRM_EmpSearch.txtgpf.value==null)
       {
            alert("Enter Employee's  GPF No.");
            document.HRM_EmpSearch.txtgpf.focus();
            return false;
        }
       var group=document.HRM_EmpSearch.txtgpf.value;
     
       var name=document.HRM_EmpSearch.txtEmpName.value;
       startwaiting(document.HRM_EmpSearch);
        service=null;
       var cell=document.getElementById("divpre");
        cell.style.display="none";
        cell.innerText='';
         var cell=document.getElementById("divcmbpage");
        cell.style.display="none";
        var cell=document.getElementById("divpage");
        cell.style.display="none";
        cell.innerText='';
        var cell=document.getElementById("divnext");
        cell.style.display="none";
        cell.innerText='';
       var url="../../../../../EmpServicePopupServ_leave.view?Command=GPF&txtgpf=" + group ;
      
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            loadTable(req);
        }
        if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
  
  }
    
    
     function loadOfficesByType(type)
    {
        //alert(type);
        var type=document.HRM_EmpSearch.cmbsgroup.options[document.HRM_EmpSearch.cmbsgroup.selectedIndex].value;
        startwaiting(document.HRM_EmpSearch) ;
         service=null;
       var cell=document.getElementById("divpre");
        cell.style.display="none";
        cell.innerText='';
         var cell=document.getElementById("divcmbpage");
        cell.style.display="none";
        var cell=document.getElementById("divpage");
        cell.style.display="none";
        cell.innerText='';
        var cell=document.getElementById("divnext");
        cell.style.display="none";
        cell.innerText='';
        
       var url="../../../../../EmpServicePopupServ_leave.view?Command=SGroup&cmbsgroup=" + type ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
             loadDesignation(req);
        }
         if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
    }
    
function  loadDesignation(req)
{
     if(req.readyState==4)
        {
          if(req.status==200)
          { 
                //alert(req);
                var des=document.getElementById("cmbdes");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                des.innerHTML="";
                
                 stopwaiting(document.HRM_EmpSearch);
                if(flag=="failure")
                {
                    alert("No Designation exists under this level");
                    
                    document.HRM_EmpSearch.txtEmpName.value="";
                    document.HRM_EmpSearch.txtgpf.value="";
                     document.HRM_EmpSearch.cmbsgroup.value="0";
                    document.HRM_EmpSearch.cmbdes.value="0";
                    s=0;
                    var tbody=document.getElementById("tb");
                      try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}
                    
                }
                else if(flag=="sessionout")
            {
                alert('Session is closed');
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                self.close();
                return;
            }
                else
                {   
                
                    var value=response.getElementsByTagName("option");
                    var option=document.createElement("OPTION");
                    option.text="--Select Designation--";
                    option.value="0";
                    try
                    {
                        des.add(option);
                    }catch(errorObject)
                    {
                        des.add(option,null);
                    }
                    for(var i=0;i<value.length;i++)
                    {
                        var tmpoption=value.item(i);
                        var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                        var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                        var option=document.createElement("OPTION");
                          option.text=name;
                          option.value=id;
                          //Making Browser Independent
                          try
                          {
                              des.add(option);
                          }
                          catch(errorObject)
                          {
                              des.add(option,null);
                          }
                    }
                
                }
        
        }
        
    }
    

}



function loadTable(req)
{

        if(req.readyState==4)
        {
          if(req.status==200)
          { 
               stopwaiting(document.HRM_EmpSearch);
               var response=req.responseXML.getElementsByTagName("response")[0];
                
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="failure")
                {
                  /*  try
                    {
                    
                    var flag1=response.getElementsByTagName("flag")[1].firstChild.nodeValue;
                   if(flag1!=null)
                    {
                        alert(flag1);
                        self.opener.window.close();
                        self.close();
                            return;
                    }
                    }catch(e){}*/
                     
                    alert("No Employee exists under this criterial");
                    document.HRM_EmpSearch.txtEmpName.value="";
                    document.HRM_EmpSearch.txtgpf.value="";
                     document.HRM_EmpSearch.cmbsgroup.value="0";
                    document.HRM_EmpSearch.cmbdes.value="0";
                    s=0;
                    var tbody=document.getElementById("tb");
                      try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}
                }
                else if(flag=="sessionout")
            {
                alert('Session is closed');
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                self.close();
                return;
            }
                else
                {   
               
                    service=response.getElementsByTagName("employee");
                     
                    if(service)
                    {
                    ///////////////////////////////
                       var i=0;
                      
                   var tbody=document.getElementById("tb");
                      try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}
                   //alert(service);
                  s=0;
                  
                  totalblock=0;
                            //alert(parseInt(items1.length/__pagination));
                            if(service.length>0)
                            {
                                    totalblock=parseInt(service.length/__pagination);
                                    if(service.length%__pagination!=0)
                                    {
                                            totalblock=totalblock+1;
                                    }
                                    
                                    
                                    var cmbpage=document.getElementById("cmbpage");
                                   
                                   try{ cmbpage.innerHTML="";
                                   }catch(e){
                                    cmbpage.innerText="";
                                   }
                                     
                                    
                                    for(i=1;i<=totalblock;i++)
                                    {
                                            var option=document.createElement("OPTION");
                                            option.text=i;
                                            option.value=i;
                                            try
                                            {
                                                cmbpage.add(option);
                                            }catch(errorObject)
                                            {
                                            cmbpage.add(option,null);
                                            }
                                    }                                  
                                    loadPage(1);
                            }
                  
                  
                    ///////////////////////////////
                    
                    }
                
                }
            }
        }
       

}   

function changepage()
{
//alert('test');
var page=document.HRM_EmpSearch.cmbpage.value;
loadPage(parseInt(page));

}

//var emp_arr=new Array();

function loadPage(page)
{
            var i=0;
            var c=0;
            var p=__pagination*(page-1);
//            var emp_arr_len=emp_arr.length;
//            for(var i=0;i<emp_arr_len;i++)
//            	{
//            	emp_arr.pop();
//            	}
            document.HRM_EmpSearch.cmbpage.selectedIndex=page-1;
                var tbody=document.getElementById("tb");
                 
                  try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}       
             if(service)
                    {
                    ///////////////////////////////
                   
                   
                  s=0;
                for(i=p;i<service.length&& c<__pagination;i++)
                {
                     c++;
                        var items=new Array();
                        items[0]=service[i].getElementsByTagName("empid")[0].firstChild.nodeValue;
                       // emp_arr.push(items[0]);
                        items[1]=service[i].getElementsByTagName("empname")[0].firstChild.nodeValue;
                        items[2]=service[i].getElementsByTagName("initial")[0].firstChild.nodeValue;
                        items[3]=service[i].getElementsByTagName("designation")[0].firstChild.nodeValue;
                        items[4]=service[i].getElementsByTagName("dob")[0].firstChild.nodeValue;
                        
                        items[5]=service[i].getElementsByTagName("gpf")[0].firstChild.nodeValue;
                        items[6]=service[i].getElementsByTagName("office_short_name")[0].firstChild.nodeValue;
                        items[7]=service[i].getElementsByTagName("EMPLOYEE_STATUS_DESC")[0].firstChild.nodeValue;
                        if(items[5]==0)
                            items[5]="";
                        if(items[6]=="null")
                            items[6]="";
                        var tbody=document.getElementById("tb");
                        var mycurrent_row=document.createElement("TR");
                        mycurrent_row.id = seq;
                        
                        
                         var descell=document.createElement("TD");
                       /*  var sc=document.createElement("<INPUT type='radio' name='sel' id='sel" +i+"' >");
                         //sc.type="radio";
                         //sc.name="sel";
                         
                         sc.setAttribute("onclick","pick(this)");
                         //sc.text=items[0];
                        // sc.id='rad'+i;
                         sc.value=items[0];
                         descell.appendChild(sc);
                         mycurrent_row.appendChild(descell);*/
                        // alert(window.navigator.appName.toLowerCase().indexOf("netscape") == -1);
                         if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                         {
                            var sc=document.createElement("<INPUT type='radio' name='sel' id='sel" +i+"'  >");
                         }
                         else
                         {
                         var sc=document.createElement("input");
                         sc.type="radio";
                         sc.name="sel";
                         sc.id='sel' +i;
                         }
                         sc.setAttribute("onclick","pick(this,"+seq+")");
                         sc.value=items[0];
                         descell.appendChild(sc);
                         
                         
                     	var s_id = document.createElement("TEXT");
            			s_id.type = "hidden";
            			s_id.name = "hidd";
            			s_id.id = "hidd";
            			s_id.value = seq;		
            			descell.appendChild(s_id);
            			//tr.appendChild(td3);
                         
                         
                         mycurrent_row.appendChild(descell);
                         
                         
                          
                        for(j=0;j<8;j++)
                        {
                            cell2=document.createElement("TD");
                            cell2.setAttribute('align','left');
                            //alert(items[j]); 
                            if(items[j]!="null")
                            {
                                var currentText=document.createTextNode(items[j]);
                            }
                            else
                            {
                                var currentText=document.createTextNode('');
                            }
                            cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                        }
                         // alert('ok');        
                        tbody.appendChild(mycurrent_row);
                        seq++;
                       
                }
            
                    
                    
                    
                    ///////////////////////////////
                    
                    }          
                       
                       
            // alert(page);
           // alert(page<totalblock);
           var cell=document.getElementById("divcmbpage");
                cell.style.display="block";
           var cell=document.getElementById("divpage");
                cell.style.display="block";
                //alert(navigator.appName);
                if(navigator.appName.indexOf("Microsoft")!=-1)
                    cell.innerText= ' / ' +totalblock;
                else
                    cell.innerHTML= ' / ' +totalblock;
            if(page<totalblock)
            {
                var cell=document.getElementById("divnext");
                cell.style.display="block";
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
                 var anc=document.createElement("A");
                var url="javascript:loadPage("+(page+1)+")";
                anc.href=url;
                //anc.setAttribute('style','text-decoratin:none');
                var txtedit=document.createTextNode("<<Next>>");
                anc.appendChild(txtedit);
                cell.appendChild(anc);
            }
            else
            {
                var cell=document.getElementById("divnext");
                cell.style.display="block";
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
            
            }
             if(page>1)
            {
                var cell=document.getElementById("divpre");
                cell.style.display="block";
                //cell.innerText='';
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
                 var anc=document.createElement("A");
                var url="javascript:loadPage("+(page-1)+")";
                anc.href=url;
                var txtedit=document.createTextNode("<<Previous>>");
                anc.appendChild(txtedit);
                cell.appendChild(anc);
            }
            else
            {
                var cell=document.getElementById("divpre");
                cell.style.display="block";
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
            
            }
}



function changepagesize()
{

           __pagination=document.HRM_EmpSearch.cmbpagination.value;
            var v=document.getElementsByName("sel");
            //alert(v);
        if(service)
        {
           
                            totalblock=0;
                            //alert(parseInt(items1.length/__pagination));
                            if(service.length>0)
                            {
                                    totalblock=parseInt(service.length/__pagination);
                                    if(service.length%__pagination!=0)
                                    {
                                            totalblock=totalblock+1;
                                    }
                                    
                                    
                                    var cmbpage=document.getElementById("cmbpage");
                                   
                                   try{ cmbpage.innerHTML="";
                                   }catch(e){
                                    cmbpage.innerText="";
                                   }
                                     
                                    
                                    for(i=1;i<=totalblock;i++)
                                    {
                                            var option=document.createElement("OPTION");
                                            option.text=i;
                                            option.value=i;
                                            try
                                            {
                                                cmbpage.add(option);
                                            }catch(errorObject)
                                            {
                                            cmbpage.add(option,null);
                                            }
                                    } 
                            }
                             loadPage(1);
            
            
            
        }
           
}

/////////////////////////////////////////////////////////////////////////////
var Emp_name=null;
var Desig=null;
function btnsubmit()
{

var v=document.getElementsByName("sel");

if(v)
{
    for(i=0;i<v.length;i++)
    {
        if(v[i].checked==true)
        {
           // opener.document.HRE_EmployeeServiceDetails.txtEmployeeid.value = (v[i].value);
          //  try{self.opener.doFunction('loademp','null');}catch(e){}
            //Minimize();
            opener.doParentEmp(v[i].value,Emp_name,Desig);
            //opener.focus();
            temp();
            return true;
        }
       
    }
}
else
{
           alert('Select an Employee ');
           return false; 

}
  
}  
function temp()
{
//alert('test');
 self.close();
}

function pick(t,pass)
{


	
	var ids=t.id;


    s=t.value;
	xmlhttp = getTransport();
	if (xmlhttp == null) {
		alert("Your borwser doesnot support AJAX");
		return;
	}
    if (s == "") {
	} else {
		var urlhh = "../../../../../EmpSelectionPopupforOffice1?Command=loadempedit&txtEmployeeid="+s;

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4) {
				if (xmlhttp.status == 200) {
					var responsee = xmlhttp.responseXML
					.getElementsByTagName("response")[0];
					var emp_status1=	responsee
					.getElementsByTagName("emp_status")[0].firstChild.nodeValue;
					
					var flag = responsee.getElementsByTagName("status")[0].firstChild.nodeValue;
					
					if ((flag == "sameoff") || (flag == ("sr")) ){
						
//						document.getElementById("ename").value = responsee
//						.getElementsByTagName("name")[0].firstChild.nodeValue;
//						document.getElementById("edesg").value = responsee
//						.getElementsByTagName("desg")[0].firstChild.nodeValue;
//					
//						//alert("loading");
//						callServer('cutoff', 'null');
						//stat=true;
					}
					else if(flag == "not_sr")
					{
						
						if((emp_status1=="DTH")||(emp_status1=="RES")||(emp_status1=="DIS")||(emp_status1=="SAN")||(emp_status1=="VRS"))
						{
							
//							document.getElementById('eid').value = "";
//							document.getElementById('ename').value = "";
//							document.getElementById('edesg').value = "";
							document.getElementById(ids).checked=false;
							alert("This Employee Retired!");
							
						}
						//else
						//{
//							document.getElementById('eid').value = "";
//							document.getElementById('ename').value = "";
//							document.getElementById('edesg').value = "";
							//document.getElementById(ids).checked=false;
							//alert("Employee ID not belonging SR Control Office!");
							
						//}
					}
					else if((flag == "wrong") ||(emp_status1=="nostatusid")){
//						document.getElementById('eid').value = "";
//						document.getElementById('ename').value = "";
//						document.getElementById('edesg').value = "";
						document.getElementById(ids).checked=false;
						alert("Employee ID Doesn't Exist!");
						
					}
					else 
						{
//						document.getElementById('eid').value = "";
//						document.getElementById('ename').value = "";
//						document.getElementById('edesg').value = "";
						document.getElementById(ids).checked=false;
						alert("loading failure");
						
						}
					}
			}

		};
		xmlhttp.open("GET", urlhh, true);
		xmlhttp.send(null);
	}
   // alert(stat);
   // return stat;

	
	try{
		var emp_id = document.getElementById(pass);
		var rcells = emp_id.cells;
		//alert(rcells.item(2).firstChild.nodeValue);
		var nam=rcells.item(2).firstChild.nodeValue;
		var init=rcells.item(3).firstChild.nodeValue;
		Emp_name=nam+"."+init;
		Desig=rcells.item(4).firstChild.nodeValue;
	}
	catch(f)
	{
		//alert(f);
	}
}

function btncancel()
{

 self.close();
}

function Minimize() 
{
window.resizeTo(0,0);
window.screenX = screen.width;
window.screenY = screen.height;
opener.window.focus();
}


function numbersonly(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       
       if ( unicode!=8 && unicode !=9  )
        {
            if ((unicode<48||unicode>57 ) && (unicode<35||unicode>40 ) && unicode!=46 )
                return false 
        }
     }
