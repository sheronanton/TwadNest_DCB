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
	
	if(document.frmEmployee.com_chec.checked==true)
	{
		
		document.frmEmployee.Community.disabled=false;
	}
	else
	{
		
		document.frmEmployee.Community.disabled=true;
	}
}

function showReglion()
 {
	
	if(document.frmEmployee.reg_chec.checked==true)
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
	document.frmEmployee.Religion.disabled=false;
}

function hide()
{
	document.frmEmployee.Religion.disabled=true;
}

function showdist()
{
	if(document.frmEmployee.dist_chec.checked==true)
	{
		document.frmEmployee.Native_District.disabled=false;
	}
	else
	{
		
		document.frmEmployee.Native_District.disabled=true;
	}
}
function showqual()
{
	if(document.frmEmployee.qua_chec.checked==true)
	{
		document.frmEmployee.Qualification.disabled=false;
	}
	else
	{
		document.frmEmployee.Qualification.disabled=true;
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
    var url="../../../../../../EmployeeDetailsReportServ_QueryBased?command=loadServiceGroup";
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
    if(document.frmEmployee.service_group.length>1)
    {
        for(i=0;i<document.frmEmployee.service_group.length;i++)
        {
                if(document.frmEmployee.service_group[i].checked==true)
                        servicegroup= servicegroup+document.frmEmployee.service_group[i].value +",";               
        }
    }
    else 
        servicegroup=document.frmEmployee.service_group.value;
    var url="../../../../../../EmployeeDetailsReportServ_QueryBased?command=loadDesignation&servicegroup="+servicegroup;
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
	 
      if(document.frmEmployee.service_group)
      {
    	
            for(i=0;i<document.frmEmployee.service_group.length;i++)
            {
            	 
                    document.frmEmployee.service_group[i].checked=true;
                   
            }
           
        }
   
        document.getElementById("designation").disabled=false;
    }
    else
    {
        if(document.frmEmployee.design)
        {
            if(document.frmEmployee.design.length>1)
            {
                for(i=0;i<document.frmEmployee.design.length;i++)
                {
                        document.frmEmployee.design[i].checked=true;
                }
            }
            else
            {
                document.frmEmployee.design.checked=true;
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
        for(i=0;i<document.frmEmployee.service_group.length;i++)
        {
                if(document.frmEmployee.service_group[i].checked==true)
                    count++;    
        }
        
        if(count==document.frmEmployee.service_group.length)
        	 document.getElementById("designation").disabled=true;
           // document.frmEmployee.designation.disabled=true;
        else if(count>0)
        	{
        	document.getElementById("designation").disabled=false;
        	loadDesignation();
        	}
           // document.frmEmployee.designation.disabled=false;
        else
        	 document.getElementById("designation").disabled=true;
          //  document.frmEmployee.designation.disabled=true;
        
}
function callFunction()
{
	
	
	
	 var url="../../../../../../EmployeeDetailsReportServ_QueryBased?OLevel=submit";
     
	 if(document.frmEmployee.rad_off[0].checked)
			
		{
		 allhier=1;
		 officeselected=0;
		}
	 else
	 {
		 allhier=0;
		 officeselected=0;
	 }
	 
	if(document.frmEmployee.rad_off[1].checked)
		
		{
		document.frmEmployee.officeselected.value=5000;
		officeselected=5000;
		}
	if(document.frmEmployee.rad_off[2].checked)    
        //  if(type=='RN')
          {
           // alert("inside region");
          ////////
          var region="";
          if(document.frmEmployee.chkregion)
            {
            
                      if(document.frmEmployee.chkregion.length)
                      {
                                for(i=0;i<document.frmEmployee.chkregion.length;i++)
                                  {
                                          if(document.frmEmployee.chkregion[i].checked==true)
                                            region= region+document.frmEmployee.chkregion[i].value +",";
                                             
                                          
                                  }
                                  if(region!="")
                                  {
                                      region=region.substring(0,region.length-1);
                                       officeselected=region;
                                      
                                       document.frmEmployee.officeselected.value=region;
                                       //alert(document.frmEmployee.officeselected.value);
                                  }
                                  else
                                  {
                                         alert('Select the Region.');
                                          document.frmEmployee.cmbregion[0].focus(); 
                                          return;
                                  }
                          
                      }
                      else{
                         
                              if(document.frmEmployee.chkregion.checked==true)
                              {
                                          region= document.frmEmployee.chkregion.value ;
                                          document.frmEmployee.officeselected.value=region;
                              }
                               else
                                  {
                                         alert('Select the Region..');
                                          document.frmEmployee.cmbregion.focus(); 
                                          return;
                                  }
                                   //alert(region);
                                          
                      }
              }
               else
              {
                     alert('Select the Region...');
                      document.frmEmployee.cmbregion.focus(); 
                      return ;
              }    
          ////
          
          }
          
        else if(document.frmEmployee.rad_off[3].checked) 
          
          //else if(type=='CL')
          {
          
          ////////
          var circle="";
          if(document.frmEmployee.chkcircle)
            {
            
                     if(document.frmEmployee.chkcircle.length )
                      {
                              
                                  for(i=0;i<document.frmEmployee.chkcircle.length;i++)
                                  {
                                          if(document.frmEmployee.chkcircle[i].checked==true)
                                                  circle= circle+document.frmEmployee.chkcircle[i].value +",";
                                                  // alert(circle);  
                                  }
                                  if(circle!="")
                                  {
                                      circle=circle.substring(0,circle.length-1);
                                      officeselected=circle;
                                       document.frmEmployee.officeselected.value=circle;
                                  }
                                  else
                                  {
                                         alert('Select the Circle.');
                                          document.frmEmployee.cmbcircle.focus(); 
                                           return;
                                  }
                      }
                      else{
                        
                              if(document.frmEmployee.chkcircle.checked==true)
                              {
                                          circle= document.frmEmployee.chkcircle.value ;
                                          document.frmEmployee.officeselected.value=circle;
                                         // alert(circle);
                              }
                               else
                                  {
                                         alert('Select the Circle..');
                                          document.frmEmployee.cmbcircle.focus(); 
                                          return;
                                  }
                                   //alert(region);
                                          
                      }
              }
              else
              {
                     alert('Select the Circle...');
                     try{
                      document.frmEmployee.cmbcircle.focus(); 
                      }
                      catch(e){
                      document.frmEmployee.cmbregion.focus(); 
                      }
                       return;
              }
                  
          ////
          
          }
          
         else if(document.frmEmployee.rad_off[4].checked) 
         // else  if(type=='DN')
          {
         
          ////////
          var division="";
          if(document.frmEmployee.chkdivision)
            {
           //alert(document.frmEmployee.chkregion.length);
                   if(document.frmEmployee.chkdivision.length )
                      {
                      
                      
                              for(i=0;i<document.frmEmployee.chkdivision.length;i++)
                              {
                                      if(document.frmEmployee.chkdivision[i].checked==true)
                                              division= division+document.frmEmployee.chkdivision[i].value +",";
                                      
                              }
                              
                              if(division!="")
                              {
                                  division=division.substring(0,division.length-1);
                                   officeselected=division;
                                   document.frmEmployee.officeselected.value=division;
                                   
                              }
                              else
                              {
                                     alert('Select the Division.');
                                      document.frmEmployee.cmbdivision.focus(); 
                                       return;
                              }
                      }
                      else
                      {
                              if(document.frmEmployee.chkdivision.checked==true)
                              {
                                              division= document.frmEmployee.chkdivision.value;
                                                  //division=division.substring(0,division.length-1);
                                               officeselected=division;
                                               document.frmEmployee.officeselected.value=division;
                                               
                              }
                              else
                              {
                                     alert('Select the Division..');
                                      document.frmEmployee.cmbdivision.focus(); 
                                       return;
                              }
                      
                      
                      }
              }
               else
              {
                     alert('Select the Division...');
                     try{
                      document.frmEmployee.cmbdivision.focus(); 
                      }catch(e)
                      {
                      try{
                      document.frmEmployee.cmbcircle.focus(); 
                      }
                      catch(e){
                      document.frmEmployee.cmbregion.focus(); 
                      }
                      }
                       return;
              }
                  
          ////
          
          }
	
	
	
    var servicegroup="";
    if(document.frmEmployee.service_group)
    {
        if(document.frmEmployee.service_group.length>1)
        {
            for(i=0;i<document.frmEmployee.service_group.length;i++)
            {
                    if(document.frmEmployee.service_group[i].checked==true)
                            servicegroup= servicegroup+document.frmEmployee.service_group[i].value +",";              
            }
        }
        else 
            servicegroup=document.frmEmployee.service_group.value;
    }
    var designation="";
    if(document.frmEmployee.design)
    {
        if(document.frmEmployee.design.length>1)
        {
            for(i=0;i<document.frmEmployee.design.length;i++)
            {
                    if(document.frmEmployee.design[i].checked==true)
                   designation= designation+document.frmEmployee.design[i].value +",";               
            }
        }
        else 
            designation=document.frmEmployee.design.value;
    }
    var Religion="";
    
    if(document.frmEmployee.reg_chec.checked==true)
    {
    	  Religion=document.frmEmployee.Religion.value;
    }
    else
    {   	
    	Religion="";
    }
    var community="";
    if(document.frmEmployee.com_chec.checked==true)
    {
    	community=document.frmEmployee.Community.value;
    }
    else
    {   	
    	community="";
    }
    
    var district="";
    if(document.frmEmployee.dist_chec.checked==true)
    {
    	district=document.frmEmployee.Native_District.value;
    }
    else
    {   	
    	district="";
    }
    var qualification="";
    if(document.frmEmployee.qua_chec.checked==true)
    {
    	qualification=document.frmEmployee.Qualification.value;
    }
    else
    {   	
    	qualification="";
    }
    
    
    
   // var Religion=document.frmEmployee.Religion.value;
    //var community=document.frmEmployee.Community.value;
   // var district=document.frmEmployee.Native_District.value;
   // var qualification=document.frmEmployee.Qualification.value;
    var specialisation=document.frmEmployee.Specialisation.value;
    var url="../../../../../../EmployeeDetailsReportServ_QueryBased?servicegroup="+servicegroup+"&designation="+designation+"&community="+community+"&district="+district+"&qualification="+qualification+"&specialisation="+specialisation+"&Religion="+Religion+"&officeselected="+officeselected+"&allhier="+allhier;
    //alert("url is-------->"+url);
    document.frmEmployee.action=url;
    document.frmEmployee.method="post";
    document.frmEmployee.submit();
}