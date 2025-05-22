
function getTransport()
{
var req=false;
try
{
req=new ActiveXObject("Msxml2.XMLHTTP");
}catch(e1)
 {
    try{
    req=new ActiveXObject("Microsoft.XMLHTTP");
    }
    catch(e2)
    {
    req=false;
    }
 }
    if (!req && typeof XMLHttpRequest != 'undefined') 
        {
        req=new XMLHttpRequest();
        }
   return req;   
}
function showcommunity()
{ 
	
	if(document.frmEmployeeorderby.com_chec.checked==true)
	{
		
		document.frmEmployeeorderby.Community.disabled=false;
	}
	else
	{
		
		document.frmEmployeeorderby.Community.disabled=true;
	}
}

function showReglion()
 {
	
	if(document.frmEmployeeorderby.reg_chec.checked==true)
	{
		show();
	}
	else 
	{
		hide();
	}
 }

function show()
{
	document.frmEmployeeorderby.Religion.disabled=false;
}

function hide()
{
	document.frmEmployeeorderby.Religion.disabled=true;
}

function showdist()
{
	if(document.frmEmployeeorderby.dist_chec.checked==true)
	{
		document.frmEmployeeorderby.Native_District.disabled=false;
	}
	else
	{
		
		document.frmEmployeeorderby.Native_District.disabled=true;
	}
}
function showqual()
{
	if(document.frmEmployeeorderby.qua_chec.checked==true)
	{
		document.frmEmployeeorderby.Qualification.disabled=false;
	}
	else
	{
		document.frmEmployeeorderby.Qualification.disabled=true;
	}
}
function nullcheck()
{
	if(document.getElementById("Religion").selectedIndex==0)
	{
		alert("please choose atleast one Religion");
		return false;
	}
	return true;
}


function loadServiceGroup()
{
    var iframe=document.getElementById("divframegroup");
    iframe.innerHTML="";
    iframe.style.visibility="hidden";
    var url="../../../../../../EmpDetailsRep_QueryBased_orderby?command=loadServiceGroup";
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        if(req.readyState==4)
        { 
              if(req.status==200)
              {  
                var iframe=document.getElementById("divframegroup");
                iframe.style.visibility='visible';
                iframe.focus();
                iframe.innerHTML=req.responseText;
              }
        }
    };
    req.send(null);
}
function loadDesignation()
{
	
	
    var iframe=document.getElementById("divframedesign");
    iframe.innerHTML="";
    iframe.style.visibility="hidden";
    var servicegroup="";
    if(document.frmEmployeeorderby.service_group.length>1)
    {
        for(i=0;i<document.frmEmployeeorderby.service_group.length;i++)
        {
                if(document.frmEmployeeorderby.service_group[i].checked==true)
                        servicegroup= servicegroup+document.frmEmployeeorderby.service_group[i].value +",";               
        }
    }
    else 
        servicegroup=document.frmEmployeeorderby.service_group.value;
    var url="../../../../../../EmpDetailsRep_QueryBased_orderby?command=loadDesignation&servicegroup="+servicegroup;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        if(req.readyState==4)
        { 
              if(req.status==200)
              {  
                var iframe=document.getElementById("divframedesign");
                iframe.style.visibility='visible';
                iframe.focus();
                iframe.innerHTML=req.responseText;
              }
        }
    };
    req.send(null);
}

function elementSelectAll(id)
{
	
  if(id=="ServiceGroup")
  {
	 
      if(document.frmEmployeeorderby.service_group)
      {
    	
            for(i=0;i<document.frmEmployeeorderby.service_group.length;i++)
            {
            	 
                    document.frmEmployeeorderby.service_group[i].checked=true;
                   
            }
           
        }
   
        document.getElementById("designation").disabled=false;
    }
    else
    {
        if(document.frmEmployeeorderby.design)
        {
            if(document.frmEmployeeorderby.design.length>1)
            {
                for(i=0;i<document.frmEmployeeorderby.design.length;i++)
                {
                        document.frmEmployeeorderby.design[i].checked=true;
                }
            }
            else
            {
                document.frmEmployeeorderby.design.checked=true;
            }
        }
    }
}
function elementclose(id)
{
    if(id=="ServiceGroup")
    {
        var iframe=document.getElementById("divframegroup");
        document.getElementById("designation").disabled=true;
    }
    else
        var iframe=document.getElementById("divframedesign");
    iframe.style.visibility='hidden';
    
}
function checkDesignation()
{
        var count=0;
        for(i=0;i<document.frmEmployeeorderby.service_group.length;i++)
        {
                if(document.frmEmployeeorderby.service_group[i].checked==true)
                    count++;    
        }
        
        if(count==document.frmEmployeeorderby.service_group.length)
        	 document.getElementById("designation").disabled=true;
           // document.frmEmployeeorderby.designation.disabled=true;
        else if(count>0)
        	{
        	document.getElementById("designation").disabled=false;
        	loadDesignation();
        	}
           // document.frmEmployeeorderby.designation.disabled=false;
        else
        	 document.getElementById("designation").disabled=true;
          //  document.frmEmployeeorderby.designation.disabled=true;
        
}
function callFunc()
{
	

	
	 var url="../../../../../../EmpDetailsRep_QueryBased_orderby?OLevel=submit";
		//alert("hia"+url);
	 if(document.frmEmployeeorderby.rad_off[0].checked)
			
		{
		 allhier=1;
		 officeselected=0;
		}
	 else
	 {
		 allhier=0;
		 officeselected=0;
	 }
	 
	if(document.frmEmployeeorderby.rad_off[1].checked)
		
		{
		document.frmEmployeeorderby.officeselected.value=5000;
		officeselected=5000;
		}
	if(document.frmEmployeeorderby.rad_off[2].checked)    
        //  if(type=='RN')
          {
           // alert("inside region");
          ////////
          var region="";
          if(document.frmEmployeeorderby.chkregion)
            {
            
                      if(document.frmEmployeeorderby.chkregion.length)
                      {
                                for(i=0;i<document.frmEmployeeorderby.chkregion.length;i++)
                                  {
                                          if(document.frmEmployeeorderby.chkregion[i].checked==true)
                                            region= region+document.frmEmployeeorderby.chkregion[i].value +",";
                                             
                                          
                                  }
                                  if(region!="")
                                  {
                                      region=region.substring(0,region.length-1);
                                       officeselected=region;
                                      
                                       document.frmEmployeeorderby.officeselected.value=region;
                                       //alert(document.frmEmployeeorderby.officeselected.value);
                                  }
                                  else
                                  {
                                         alert('Select the Region.');
                                          document.frmEmployeeorderby.cmbregion[0].focus(); 
                                          return;
                                  }
                          
                      }
                      else{
                         
                              if(document.frmEmployeeorderby.chkregion.checked==true)
                              {
                                          region= document.frmEmployeeorderby.chkregion.value ;
                                          document.frmEmployeeorderby.officeselected.value=region;
                              }
                               else
                                  {
                                         alert('Select the Region..');
                                          document.frmEmployeeorderby.cmbregion.focus(); 
                                          return;
                                  }
                                   //alert(region);
                                          
                      }
              }
               else
              {
                     alert('Select the Region...');
                      document.frmEmployeeorderby.cmbregion.focus(); 
                      return ;
              }    
          ////
          
          }
          
        else if(document.frmEmployeeorderby.rad_off[3].checked) 
          
          //else if(type=='CL')
          {
          
          ////////
          var circle="";
          if(document.frmEmployeeorderby.chkcircle)
            {
            
                     if(document.frmEmployeeorderby.chkcircle.length )
                      {
                              
                                  for(i=0;i<document.frmEmployeeorderby.chkcircle.length;i++)
                                  {
                                          if(document.frmEmployeeorderby.chkcircle[i].checked==true)
                                                  circle= circle+document.frmEmployeeorderby.chkcircle[i].value +",";
                                                  // alert(circle);  
                                  }
                                  if(circle!="")
                                  {
                                      circle=circle.substring(0,circle.length-1);
                                      officeselected=circle;
                                       document.frmEmployeeorderby.officeselected.value=circle;
                                  }
                                  else
                                  {
                                         alert('Select the Circle.');
                                          document.frmEmployeeorderby.cmbcircle.focus(); 
                                           return;
                                  }
                      }
                      else{
                        
                              if(document.frmEmployeeorderby.chkcircle.checked==true)
                              {
                                          circle= document.frmEmployeeorderby.chkcircle.value ;
                                          document.frmEmployeeorderby.officeselected.value=circle;
                                         // alert(circle);
                              }
                               else
                                  {
                                         alert('Select the Circle..');
                                          document.frmEmployeeorderby.cmbcircle.focus(); 
                                          return;
                                  }
                                   //alert(region);
                                          
                      }
              }
              else
              {
                     alert('Select the Circle...');
                     try{
                      document.frmEmployeeorderby.cmbcircle.focus(); 
                      }
                      catch(e){
                      document.frmEmployeeorderby.cmbregion.focus(); 
                      }
                       return;
              }
                  
          ////
          
          }
          
         else if(document.frmEmployeeorderby.rad_off[4].checked) 
         // else  if(type=='DN')
          {
         
          ////////
          var division="";
          if(document.frmEmployeeorderby.chkdivision)
            {
           //alert(document.frmEmployeeorderby.chkregion.length);
                   if(document.frmEmployeeorderby.chkdivision.length )
                      {
                      
                      
                              for(i=0;i<document.frmEmployeeorderby.chkdivision.length;i++)
                              {
                                      if(document.frmEmployeeorderby.chkdivision[i].checked==true)
                                              division= division+document.frmEmployeeorderby.chkdivision[i].value +",";
                                      
                              }
                              
                              if(division!="")
                              {
                                  division=division.substring(0,division.length-1);
                                   officeselected=division;
                                   document.frmEmployeeorderby.officeselected.value=division;
                                   
                              }
                              else
                              {
                                     alert('Select the Division.');
                                      document.frmEmployeeorderby.cmbdivision.focus(); 
                                       return;
                              }
                      }
                      else
                      {
                              if(document.frmEmployeeorderby.chkdivision.checked==true)
                              {
                                              division= document.frmEmployeeorderby.chkdivision.value;
                                                  //division=division.substring(0,division.length-1);
                                               officeselected=division;
                                               document.frmEmployeeorderby.officeselected.value=division;
                                               
                              }
                              else
                              {
                                     alert('Select the Division..');
                                      document.frmEmployeeorderby.cmbdivision.focus(); 
                                       return;
                              }
                      
                      
                      }
              }
               else
              {
                     alert('Select the Division...');
                     try{
                      document.frmEmployeeorderby.cmbdivision.focus(); 
                      }catch(e)
                      {
                      try{
                      document.frmEmployeeorderby.cmbcircle.focus(); 
                      }
                      catch(e){
                      document.frmEmployeeorderby.cmbregion.focus(); 
                      }
                      }
                       return;
              }
                  
          ////
          
          }
	
	
	
    var servicegroup="";
    if(document.frmEmployeeorderby.service_group)
    {
        if(document.frmEmployeeorderby.service_group.length>1)
        {
            for(i=0;i<document.frmEmployeeorderby.service_group.length;i++)
            {
                    if(document.frmEmployeeorderby.service_group[i].checked==true)
                            servicegroup= servicegroup+document.frmEmployeeorderby.service_group[i].value +",";              
            }
        }
        else 
            servicegroup=document.frmEmployeeorderby.service_group.value;
    }
    var designation="";
    if(document.frmEmployeeorderby.design)
    {
        if(document.frmEmployeeorderby.design.length>1)
        {
            for(i=0;i<document.frmEmployeeorderby.design.length;i++)
            {
                    if(document.frmEmployeeorderby.design[i].checked==true)
                   designation= designation+document.frmEmployeeorderby.design[i].value +",";               
            }
        }
        else 
            designation=document.frmEmployeeorderby.design.value;
    }
    var Religion="";
    
    if(document.frmEmployeeorderby.reg_chec.checked==true)
    {
    	  Religion=document.frmEmployeeorderby.Religion.value;
    	  
    }
    else
    {   	
    	Religion="";
    }
    var community="";
    if(document.frmEmployeeorderby.com_chec.checked==true)
    {
    	community=document.frmEmployeeorderby.Community.value;
    }
    else
    {   	
    	community="";
    }
    
    var district="";
    if(document.frmEmployeeorderby.dist_chec.checked==true)
    {
    	district=document.frmEmployeeorderby.Native_District.value;
    }
    else
    {   	
    	district="";
    }
    var qualification="";
    if(document.frmEmployeeorderby.qua_chec.checked==true)
    {
    	qualification=document.frmEmployeeorderby.Qualification.value;
    }
    else
    {   	
    	qualification="";
    }
   /* var orderby_off="";
    
    if(document.frmEmployeeorderby.orderby_off.checked==true)
    {
    	
    	orderby_off="OFF_ORDER";
    	
    	document.getElementById("orderby_Desig").disabled=true;
    	//document.getElementById("orderby_reg").style.display='none';
    	//document.frmEmployeeorderby.orderby_Desig.disabled=true;
    	//document.frmEmployeeorderby.orderby_Desig.disabled==true;
    	//document.frmEmployeeorderby.orderby_reg.disabled==true
    	
    }
    else
    {   	
    	orderby_off="";
    }
   var orderby_Desig="";
        if(document.frmEmployeeorderby.orderby_Desig.checked==true)
        {
        	orderby_Desig="Desig";
        }
        else
        {   	
        	orderby_Desig="";
        }
    var orderby_reg="";
        if(document.frmEmployeeorderby.orderby_reg.checked==true)
        {
        	orderby_reg="Relig";
        }
        else
        {   	
        	orderby_reg="";
        }*/
   // var Religion=document.frmEmployeeorderby.Religion.value;
    //var community=document.frmEmployeeorderby.Community.value;
   // var district=document.frmEmployeeorderby.Native_District.value;
   // var qualification=document.frmEmployeeorderby.Qualification.value;
    //var specialisation=document.frmEmployeeorderby.Specialisation.value;
    var url="../../../../../../EmpDetailsRep_QueryBased_orderby?servicegroup="+servicegroup+"&designation="+designation+"&community="+community+"&district="+district+"&qualification="+qualification+"&Religion="+Religion+"&officeselected="+officeselected+"&allhier="+allhier+"&order_by="+order_by;
   // alert("url is-------->"+url);
    document.frmEmployeeorderby.action=url;
    document.frmEmployeeorderby.method="post";
    document.frmEmployeeorderby.submit();
}

var order_by="";
function getcheck()
{
	

	 if(document.frmEmployeeorderby.orderby_off[0].checked==true)
	 {
		 
		    var orderby_off='OFF_ORDER';
    		order_by=order_by+','+orderby_off;
	 }
	 
}
function getcheck1()
{
	  if(document.frmEmployeeorderby.orderby_off[1].checked==true)
	 {
		 var orderby_Desig="desig";
     	 order_by=order_by+','+orderby_Desig;
	 }
	 
}
	
function getcheck2()
{
	  if(document.frmEmployeeorderby.orderby_off[2].checked==true)
	 {
		 var orderby_emp_id="employee_id";
     	 order_by=order_by+','+orderby_emp_id;
	 }
	  
}
function getcheck3()
{
	 if(document.frmEmployeeorderby.orderby_off[3].checked==true)
	 {
		 var orderby_emp_name="employee_name";
     	 order_by=order_by+','+orderby_emp_name;
	 }
	 
	
}
