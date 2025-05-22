
var service;
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
        var type=document.Compassionate_search.cmbsgroup.options[document.Compassionate_search.cmbsgroup.selectedIndex].value;
        if(type!=0)
        {
            var din=document.getElementById("divdes");
            din.style.visibility="visible";
            document.Compassionate_search.cmbdes.style.visibility="visible";
            loadOfficesByType(type);
        }
        else
        {
            document.Compassionate_search.cmbdes.value="0";
             var din=document.getElementById("divdes");
            din.style.visibility="hidden";
            document.Compassionate_search.cmbdes.style.visibility="hidden";
        }
    }
    
  function getEmployee()  
  
  {
       if(document.Compassionate_search.txtEmpName.value.length==0 && document.Compassionate_search.app_name.value.length==0)
       {
            
    	  
           
           alert('Please Enter Employee Name OR Applicant Name');
           return false;
        }
       
       
        if(document.Compassionate_search.txtEmpName.value.length!=0)
        {
                if(document.Compassionate_search.txtEmpName.value.length<3)
               {
                    alert('Employee Name should have atleast 3 characters');
                    return false;
                }
        }
        
         if(document.Compassionate_search.app_name.value.length!=0)
         {
                 if(document.Compassionate_search.app_name.value.length<3)
                {
                     alert('Applicant Name should have atleast 3 characters');
                     return false;
                 }
         }
      
       var name=document.Compassionate_search.txtEmpName.value;
       var app_name=document.Compassionate_search.app_name.value;
       startwaiting(document.Compassionate_search);
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
       var url="../../../../../Compassionate_search.view?Command=Emp&name=" + name+"&app_name="+app_name ;
       //url=url+"&cmbdes="+des+"&txtEmpName="+name;
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
  
       if(document.Compassionate_search.txtgpf.value.length==0 || document.Compassionate_search.txtgpf.value==null)
       {
            alert("Enter Employee's  GPF No.");
            document.Compassionate_search.txtgpf.focus();
            return false;
        }
       var group=document.Compassionate_search.txtgpf.value;
     
       var name=document.Compassionate_search.txtEmpName.value;
       startwaiting(document.Compassionate_search);
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
       var url="../../../../../EmpServicePopupServ.view?Command=GPF&txtgpf=" + group ;
       
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
        var type=document.Compassionate_search.cmbsgroup.options[document.Compassionate_search.cmbsgroup.selectedIndex].value;
        startwaiting(document.Compassionate_search) ;
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
        
       var url="../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
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
                
                 stopwaiting(document.Compassionate_search);
                if(flag=="failure")
                {
                    alert("No Designation exists under this level");
                    
                    document.Compassionate_search.txtEmpName.value="";
                    document.Compassionate_search.txtgpf.value="";
                     document.Compassionate_search.cmbsgroup.value="0";
                    document.Compassionate_search.cmbdes.value="0";
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
        	 // alert(req.responseText);
               stopwaiting(document.Compassionate_search);
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
                    document.Compassionate_search.txtEmpName.value="";
                    document.Compassionate_search.txtgpf.value="";
                     document.Compassionate_search.cmbsgroup.value="0";
                    document.Compassionate_search.cmbdes.value="0";
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
var page=document.Compassionate_search.cmbpage.value;
loadPage(parseInt(page));

}



function loadPage(page)
{
            var i=0;
            var c=0;
            var p=__pagination*(page-1);
            
            document.Compassionate_search.cmbpage.selectedIndex=page-1;
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
                        items[0]=service[i].getElementsByTagName("APPLICATION_ID")[0].firstChild.nodeValue;
                        items[1]=service[i].getElementsByTagName("APPLICANT_NAME")[0].firstChild.nodeValue;
                       // items[2]=service[i].getElementsByTagName("EMPLOYEE_INITIAL")[0].firstChild.nodeValue;
                        items[2]=service[i].getElementsByTagName("EMPLOYEE_NAME")[0].firstChild.nodeValue;
                        //items[4]=service[i].getElementsByTagName("ELIGIBLE_DESIG_NAME")[0].firstChild.nodeValue;
                        //alert("inside"+items[3]);
                        items[3]=service[i].getElementsByTagName("dob")[0].firstChild.nodeValue;
                        items[4]=service[i].getElementsByTagName("ELIGIBLE_DESIG_NAME")[0].firstChild.nodeValue;
                        
                        //if(items[5]==0)
                           // items[5]="";
                        
                        var tbody=document.getElementById("tb");
                        var mycurrent_row=document.createElement("TR");
                      
                        
                        
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
                            var sc=document.createElement("<INPUT type='radio' name='sel' id='sel" +i+"' >");
                         }
                         else
                         {
                         var sc=document.createElement("input");
                         sc.type="radio";
                         sc.name="sel";
                         sc.id='sel' +i;
                         }
                         sc.setAttribute("onclick","pick(this)");
                         sc.value=items[0];
                         descell.appendChild(sc);
                         mycurrent_row.appendChild(descell);
                         
                         
                          
                        for(j=0;j<5;j++)
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

           __pagination=document.Compassionate_search.cmbpagination.value;
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

function btnsubmit()
{

var v=document.getElementsByName("sel");

if(v)
{
	var page=document.getElementById("cmbpage").value;
    for(i=0;i<v.length;i++)
    {
        if(v[i].checked==true)
        {
            var items=new Array();
           // opener.document.HRE_EmployeeServiceDetails.txtEmployeeid.value = (v[i].value);
          //  try{self.opener.doFunction('loademp','null');}catch(e){}
            //alert(i);
           /// 
            j=(page-1)*10;
            Minimize();
            var id=items[0]=service[i+j].getElementsByTagName("APPLICATION_ID")[0].firstChild.nodeValue;
        	var name=items[1]=service[i+j].getElementsByTagName("APPLICANT_NAME")[0].firstChild.nodeValue;
        	var intial=items[2]=service[i+j].getElementsByTagName("EMPLOYEE_NAME")[0].firstChild.nodeValue;
        	Minimize();
            opener.doParemp(v[i].value,id);
            //opener.focus();
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
function pick(t)
{

//alert(t.value);
    s=t.value;
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
