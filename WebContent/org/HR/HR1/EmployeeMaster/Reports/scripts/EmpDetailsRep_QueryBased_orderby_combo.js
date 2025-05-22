
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
	
	if(document.frmEmployeeorderby_combo.com_chec.checked==true)
	{
		
		document.frmEmployeeorderby_combo.Community.disabled=false;
	}
	else
	{
		
		document.frmEmployeeorderby_combo.Community.disabled=true;
	}
}

function showReglion()
 {
	
	if(document.frmEmployeeorderby_combo.reg_chec.checked==true)
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
	document.frmEmployeeorderby_combo.Religion.disabled=false;
}

function hide()
{
	document.frmEmployeeorderby_combo.Religion.disabled=true;
}

function showdist()
{
	if(document.frmEmployeeorderby_combo.dist_chec.checked==true)
	{
		document.frmEmployeeorderby_combo.Native_District.disabled=false;
	}
	else
	{
		
		document.frmEmployeeorderby_combo.Native_District.disabled=true;
	}
}
function showqual()
{
	if(document.frmEmployeeorderby_combo.qua_chec.checked==true)
	{
		document.frmEmployeeorderby_combo.Qualification.disabled=false;
	}
	else
	{
		document.frmEmployeeorderby_combo.Qualification.disabled=true;
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
    var url="../../../../../../EmpDetailsRep_QueryBased_orderby_combo?command=loadServiceGroup";
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
    if(document.frmEmployeeorderby_combo.service_group.length>1)
    {
        for(i=0;i<document.frmEmployeeorderby_combo.service_group.length;i++)
        {
                if(document.frmEmployeeorderby_combo.service_group[i].checked==true)
                        servicegroup= servicegroup+document.frmEmployeeorderby_combo.service_group[i].value +",";               
        }
    }
    else 
        servicegroup=document.frmEmployeeorderby_combo.service_group.value;
    var url="../../../../../../EmpDetailsRep_QueryBased_orderby_combo?command=loadDesignation&servicegroup="+servicegroup;
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
	 
      if(document.frmEmployeeorderby_combo.service_group)
      {
    	
            for(i=0;i<document.frmEmployeeorderby_combo.service_group.length;i++)
            {
            	 
                    document.frmEmployeeorderby_combo.service_group[i].checked=true;
                   
            }
           
        }
   
        document.getElementById("designation").disabled=false;
    }
    else
    {
        if(document.frmEmployeeorderby_combo.design)
        {
            if(document.frmEmployeeorderby_combo.design.length>1)
            {
                for(i=0;i<document.frmEmployeeorderby_combo.design.length;i++)
                {
                        document.frmEmployeeorderby_combo.design[i].checked=true;
                }
            }
            else
            {
                document.frmEmployeeorderby_combo.design.checked=true;
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
        for(i=0;i<document.frmEmployeeorderby_combo.service_group.length;i++)
        {
                if(document.frmEmployeeorderby_combo.service_group[i].checked==true)
                    count++;    
        }
        
        if(count==document.frmEmployeeorderby_combo.service_group.length)
        	 document.getElementById("designation").disabled=true;
           // document.frmEmployeeorderby_combo.designation.disabled=true;
        else if(count>0)
        	{
        	document.getElementById("designation").disabled=false;
        	loadDesignation();
        	}
           // document.frmEmployeeorderby_combo.designation.disabled=false;
        else
        	 document.getElementById("designation").disabled=true;
          //  document.frmEmployeeorderby_combo.designation.disabled=true;
        
}
var order_by="";
function callFunc()
{

	
	 var url="../../../../../../EmpDetailsRep_QueryBased_orderby_combo?OLevel=submit";
		
		
		
var sel_list_tot="";

var order_by="";
// alert(document.frmEmployeeorderby_combo.sel_list.length)
        if(document.frmEmployeeorderby_combo.sel_list.length)
        {
                  for(j=0;j<document.frmEmployeeorderby_combo.sel_list.length;j++)
                    {
                	  
                	  sel_list_tot= sel_list_tot+document.frmEmployeeorderby_combo.sel_list[j].value +",";
                             //alert("sel_list_tot-------->"+sel_list_tot);  
                	  if(document.frmEmployeeorderby_combo.sel_list[j].value=='Designation')
                  		order_by=order_by+',desig';
                  
                  	//alert(order_by+"order_by===>")
                  		else if(document.frmEmployeeorderby_combo.sel_list[j].value=='office_name')
                  			order_by=order_by+',off_order'	;
                  		else if(document.frmEmployeeorderby_combo.sel_list[j].value=='Religion')
                  			order_by=order_by+',RELIGON_ID'	;
                  		else if(document.frmEmployeeorderby_combo.sel_list[j].value=='Community')
                  			order_by=order_by+',community_id'	;
                  			else 
                  				order_by=order_by+','+document.frmEmployeeorderby_combo.sel_list[j].value;
                            
                    }
                    
        }
		
		
    	//alert(order_by+"order_by===>");
		
	
		
		
	 if(document.frmEmployeeorderby_combo.rad_off[0].checked)
			
		{
		 allhier=1;
		 officeselected=0;
		}
	 else
	 {
		 allhier=0;
		 officeselected=0;
	 }
	 
	if(document.frmEmployeeorderby_combo.rad_off[1].checked)
		
		{
		document.frmEmployeeorderby_combo.officeselected.value=5000;
		officeselected=5000;
		}
	if(document.frmEmployeeorderby_combo.rad_off[2].checked)    
        //  if(type=='RN')
          {
           // alert("inside region");
          ////////
          var region="";
          if(document.frmEmployeeorderby_combo.chkregion)
            {
            
                      if(document.frmEmployeeorderby_combo.chkregion.length)
                      {
                                for(i=0;i<document.frmEmployeeorderby_combo.chkregion.length;i++)
                                  {
                                          if(document.frmEmployeeorderby_combo.chkregion[i].checked==true)
                                            region= region+document.frmEmployeeorderby_combo.chkregion[i].value +",";
                                             
                                          
                                  }
                                  if(region!="")
                                  {
                                      region=region.substring(0,region.length-1);
                                       officeselected=region;
                                      
                                       document.frmEmployeeorderby_combo.officeselected.value=region;
                                       //alert(document.frmEmployeeorderby_combo.officeselected.value);
                                  }
                                  else
                                  {
                                         alert('Select the Region.');
                                          document.frmEmployeeorderby_combo.cmbregion[0].focus(); 
                                          return;
                                  }
                          
                      }
                      else{
                         
                              if(document.frmEmployeeorderby_combo.chkregion.checked==true)
                              {
                                          region= document.frmEmployeeorderby_combo.chkregion.value ;
                                          document.frmEmployeeorderby_combo.officeselected.value=region;
                              }
                               else
                                  {
                                         alert('Select the Region..');
                                          document.frmEmployeeorderby_combo.cmbregion.focus(); 
                                          return;
                                  }
                                   //alert(region);
                                          
                      }
              }
               else
              {
                     alert('Select the Region...');
                      document.frmEmployeeorderby_combo.cmbregion.focus(); 
                      return ;
              }    
          ////
          
          }
          
        else if(document.frmEmployeeorderby_combo.rad_off[3].checked) 
          
          //else if(type=='CL')
          {
          
          ////////
          var circle="";
          if(document.frmEmployeeorderby_combo.chkcircle)
            {
            
                     if(document.frmEmployeeorderby_combo.chkcircle.length )
                      {
                              
                                  for(i=0;i<document.frmEmployeeorderby_combo.chkcircle.length;i++)
                                  {
                                          if(document.frmEmployeeorderby_combo.chkcircle[i].checked==true)
                                                  circle= circle+document.frmEmployeeorderby_combo.chkcircle[i].value +",";
                                                  // alert(circle);  
                                  }
                                  if(circle!="")
                                  {
                                      circle=circle.substring(0,circle.length-1);
                                      officeselected=circle;
                                       document.frmEmployeeorderby_combo.officeselected.value=circle;
                                  }
                                  else
                                  {
                                         alert('Select the Circle.');
                                          document.frmEmployeeorderby_combo.cmbcircle.focus(); 
                                           return;
                                  }
                      }
                      else{
                        
                              if(document.frmEmployeeorderby_combo.chkcircle.checked==true)
                              {
                                          circle= document.frmEmployeeorderby_combo.chkcircle.value ;
                                          document.frmEmployeeorderby_combo.officeselected.value=circle;
                                         // alert(circle);
                              }
                               else
                                  {
                                         alert('Select the Circle..');
                                          document.frmEmployeeorderby_combo.cmbcircle.focus(); 
                                          return;
                                  }
                                   //alert(region);
                                          
                      }
              }
              else
              {
                     alert('Select the Circle...');
                     try{
                      document.frmEmployeeorderby_combo.cmbcircle.focus(); 
                      }
                      catch(e){
                      document.frmEmployeeorderby_combo.cmbregion.focus(); 
                      }
                       return;
              }
                  
          ////
          
          }
          
         else if(document.frmEmployeeorderby_combo.rad_off[4].checked) 
         // else  if(type=='DN')
          {
         
          ////////
          var division="";
          if(document.frmEmployeeorderby_combo.chkdivision)
            {
           //alert(document.frmEmployeeorderby_combo.chkregion.length);
                   if(document.frmEmployeeorderby_combo.chkdivision.length )
                      {
                      
                      
                              for(i=0;i<document.frmEmployeeorderby_combo.chkdivision.length;i++)
                              {
                                      if(document.frmEmployeeorderby_combo.chkdivision[i].checked==true)
                                              division= division+document.frmEmployeeorderby_combo.chkdivision[i].value +",";
                                      
                              }
                              
                              if(division!="")
                              {
                                  division=division.substring(0,division.length-1);
                                   officeselected=division;
                                   document.frmEmployeeorderby_combo.officeselected.value=division;
                                   
                              }
                              else
                              {
                                     alert('Select the Division.');
                                      document.frmEmployeeorderby_combo.cmbdivision.focus(); 
                                       return;
                              }
                      }
                      else
                      {
                              if(document.frmEmployeeorderby_combo.chkdivision.checked==true)
                              {
                                              division= document.frmEmployeeorderby_combo.chkdivision.value;
                                                  //division=division.substring(0,division.length-1);
                                               officeselected=division;
                                               document.frmEmployeeorderby_combo.officeselected.value=division;
                                               
                              }
                              else
                              {
                                     alert('Select the Division..');
                                      document.frmEmployeeorderby_combo.cmbdivision.focus(); 
                                       return;
                              }
                      
                      
                      }
              }
               else
              {
                     alert('Select the Division...');
                     try{
                      document.frmEmployeeorderby_combo.cmbdivision.focus(); 
                      }catch(e)
                      {
                      try{
                      document.frmEmployeeorderby_combo.cmbcircle.focus(); 
                      }
                      catch(e){
                      document.frmEmployeeorderby_combo.cmbregion.focus(); 
                      }
                      }
                       return;
              }
                  
          ////
          
          }
	
	
	
    var servicegroup="";
    if(document.frmEmployeeorderby_combo.service_group)
    {
        if(document.frmEmployeeorderby_combo.service_group.length>1)
        {
            for(i=0;i<document.frmEmployeeorderby_combo.service_group.length;i++)
            {
                    if(document.frmEmployeeorderby_combo.service_group[i].checked==true)
                            servicegroup= servicegroup+document.frmEmployeeorderby_combo.service_group[i].value +",";              
            }
        }
        else 
            servicegroup=document.frmEmployeeorderby_combo.service_group.value;
    }
    var designation="";
    if(document.frmEmployeeorderby_combo.design)
    {
        if(document.frmEmployeeorderby_combo.design.length>1)
        {
            for(i=0;i<document.frmEmployeeorderby_combo.design.length;i++)
            {
                    if(document.frmEmployeeorderby_combo.design[i].checked==true)
                    {
                    	
                   designation= designation+document.frmEmployeeorderby_combo.design[i].value +",";
                   
                    }
            }
        }
        else 
            designation=document.frmEmployeeorderby_combo.design.value;
    }
   
   /* var Religion="";
    
    if(document.frmEmployeeorderby_combo.reg_chec.checked==true)
    {
    	  Religion=document.frmEmployeeorderby_combo.Religion.value;
    	  
    }
    else
    {   	
    	Religion="";
    }
    var community="";
    if(document.frmEmployeeorderby_combo.com_chec.checked==true)
    {
    	community=document.frmEmployeeorderby_combo.Community.value;
    }
    else
    {   	
    	community="";
    }
    
    var district="";
    if(document.frmEmployeeorderby_combo.dist_chec.checked==true)
    {
    	district=document.frmEmployeeorderby_combo.Native_District.value;
    }
    else
    {   	
    	district="";
    }
    var qualification="";
    if(document.frmEmployeeorderby_combo.qua_chec.checked==true)
    {
    	qualification=document.frmEmployeeorderby_combo.Qualification.value;
    }
    else
    {   	
    	qualification="";
    }
    var orderby_off="";
    
    if(document.frmEmployeeorderby_combo.orderby_off.checked==true)
    {
    	
    	orderby_off="OFF_ORDER";
    	
    	document.getElementById("orderby_Desig").disabled=true;
    	//document.getElementById("orderby_reg").style.display='none';
    	//document.frmEmployeeorderby_combo.orderby_Desig.disabled=true;
    	//document.frmEmployeeorderby_combo.orderby_Desig.disabled==true;
    	//document.frmEmployeeorderby_combo.orderby_reg.disabled==true
    	
    }
    else
    {   	
    	orderby_off="";
    }
   var orderby_Desig="";
        if(document.frmEmployeeorderby_combo.orderby_Desig.checked==true)
        {
        	orderby_Desig="Desig";
        }
        else
        {   	
        	orderby_Desig="";
        }
    var orderby_reg="";
        if(document.frmEmployeeorderby_combo.orderby_reg.checked==true)
        {
        	orderby_reg="Relig";
        }
        else
        {   	
        	orderby_reg="";
        }*/
   // var Religion=document.frmEmployeeorderby_combo.Religion.value;
    //var community=document.frmEmployeeorderby_combo.Community.value;
   // var district=document.frmEmployeeorderby_combo.Native_District.value;
   // var qualification=document.frmEmployeeorderby_combo.Qualification.value;
    //var specialisation=document.frmEmployeeorderby_combo.Specialisation.value;
    var url="../../../../../../EmpDetailsRep_QueryBased_orderby_combo?servicegroup="+servicegroup+"&designation="+designation+"&officeselected="+officeselected+"&allhier="+allhier+"&sel_list_tot="+sel_list_tot+"&col_len="+j+"&order_by="+order_by;
   // alert("url is-------->"+url);
    document.frmEmployeeorderby_combo.action=url;
    document.frmEmployeeorderby_combo.method="post";
    document.frmEmployeeorderby_combo.submit();
}
function disa()
{
	if(document.frmEmployeeorderby_combo.orderby_off.checked==true)
	{
	document.getElementById("orderby_Desig").disabled=true;
    document.frmEmployeeorderby_combo.orderby_reg.disabled=true;
	}
	else
	{
		document.getElementById("orderby_Desig").disabled=false;
	    document.frmEmployeeorderby_combo.orderby_reg.disabled=false;
	}
}
function hide()
{
	 if(document.frmEmployeeorderby_combo.orderby_Desig.checked==true)
	 {
	document.getElementById("orderby_off").disabled=true;
    document.frmEmployeeorderby_combo.orderby_reg.disabled=true;
	 }
	 else
	 {
		 document.getElementById("orderby_off").disabled=false;
		 document.frmEmployeeorderby_combo.orderby_reg.disabled=false;
	 }
}
function hides()
{
	 if(document.frmEmployeeorderby_combo.orderby_reg.checked==true)
	 {
	document.getElementById("orderby_off").disabled=true;
    document.frmEmployeeorderby_combo.orderby_Desig.disabled=true;
	 }
	 else
	 {
		 document.getElementById("orderby_off").disabled=false;
		 document.frmEmployeeorderby_combo.orderby_Desig.disabled=false;
	 }
		 
}