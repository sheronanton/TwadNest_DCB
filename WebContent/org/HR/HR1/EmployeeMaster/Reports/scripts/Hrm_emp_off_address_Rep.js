//alert('detail');

var regionflag=false;
//var regionflag=true;
var circleflag=true;
var divisionflag=true;
var offtypeflag=true;

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


function officevisible(r,c,d,o)
{
   //alert("inside officevisible");
    
    var rn=document.getElementById("divregion");
    rn.style.display=r;
    var cl=document.getElementById("divcircle");
    cl.style.display=c;
    var dn=document.getElementById("divdivision");
    dn.style.display=d;
    var ot=document.getElementById("divofftype");
    ot.style.display=o;
    
    var rn=document.getElementById("cmbregion");
    rn.style.display=r;
    var cl=document.getElementById("cmbcircle");
    cl.style.display=c;
    var dn=document.getElementById("cmbdivision");
    dn.style.display=d;
    var ot=document.getElementById("cmbofftype");
    ot.style.display=o;
}


function hideService(cmd)
{
	document.getElementById("orderdivabs").style.display='none';
	document.getElementById("service").style.display='none';
	if(cmd=="A")
		{
			document.getElementById("orderdivabs").style.display='';
			if(document.getElementsByName("rank")[0].checked==false)
			document.getElementById("otype").style.display='';
			else
			document.getElementById("otype").style.display='none';
		}
	else
		{
			document.getElementById("service").style.display='';
		}
}

function selectoption2()
{
   
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    document.getElementById("divallhier").style.display='block';
    document.getElementById("selectoff").style.display="";
    
    if(document.Hrm_emp_off_address_Rep.rad_off[0].checked)
    {
       
       var offlevel=document.getElementById("trofficeselection");
       offlevel.style.display="none";
       
    }

  if(document.Hrm_emp_off_address_Rep.rad_off[1].checked)
  {
   // alert(document.Hrm_emp_off_address_Rep.rad_off[1].checked);
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
    
     officevisible('block','none','none','none');
  }
  
  else if(document.Hrm_emp_off_address_Rep.rad_off[2].checked)
  {
   // alert(document.Hrm_emp_off_address_Rep.rad_off[2].checked);
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
     officevisible('block','block','none','none');
  }
  
  else if(document.Hrm_emp_off_address_Rep.rad_off[3].checked)
  {
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
    officevisible('block','block','block','block','block');
  }
  if(document.Hrm_emp_off_address_Rep.rad_off[4].checked)
  {
     var offlevel=document.getElementById("selectoff");
     offlevel.style.display="none";
   
     
  }
  
  document.Hrm_emp_off_address_Rep.cmbcircle.disabled=true;
   // document.Hrm_emp_off_address_Rep.cmbofftype.disabled=true;
    document.Hrm_emp_off_address_Rep.cmbdivision.disabled=true;
    
    var iframe=document.getElementById("diviframeregion");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframecircle");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframeofftype");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframedivision");
    iframe.style.visibility='hidden';
    
    
    regionflag=true;
    circleflag=true;
    offtypeflag=true;
    divisionflag=true;
   

}


var s=0;
var hier=true;
var level=true;
var city=true;
var  other=true;

  
function getRegion()
    {
       
              
        
        if(regionflag==true)
        {
               
                 var iframe=document.getElementById("diviframeregion");
                 iframe.style.visibility='visible';
                 iframe.focus();
               // return;
        }
       
    
         
         var url="../../../../../../EmployeeDPReport_office?OLevel=region" ;
        //alert(url);
                
        document.Hrm_emp_off_address_Rep.cmbregion.focus();
        var req=getTransport();
        req.open("GET",url,false);        
        req.onreadystatechange=function()
        {
            //requesthandle(req);
            if(req.readyState==4)
            { 
                  if(req.status==200)
                  {  
                   // alert(req.responseText);
                 
                 
                  
                    var iframe=document.getElementById("diviframeregion");
                    iframe.style.visibility='visible';
                    iframe.focus();
                   // alert(navigator.appName);
                   // alert(navigator.appName.indexOf('Microsoft'));
                   
                  
                    if(navigator.appName.indexOf('Microsoft')!=-1)
                    	{
                        iframe.innerHTML=req.responseText;
                    iframe.innerText=req.responseText;
                    	}
                    else
                    	{
                        iframe.innerText=req.responseText;
                    iframe.innerHTML=req.responseText;
                    	}
                  document.Hrm_emp_off_address_Rep.cmbcircle.disabled=false;
                   var iframe=document.getElementById("diviframecircle");
                            iframe.style.visibility='hidden';
                   var iframe=document.getElementById("diviframedivision");
                            iframe.style.visibility='hidden';
                   
                   regionflag=true;
                  }
            }
        }
        req.send(null);
    }
    
    
function getCircle(cir)
{
      //alert("inside getcircle");
        
       var region="";
     //   var type=document.Hrm_emp_off_address_Rep.cmbolevel.options[document.Hrm_emp_off_address_Rep.cmbolevel.selectedIndex].value;
     
      
    
     
       if(document.Hrm_emp_off_address_Rep.chkregion)
      {
      
         // alert(document.Hrm_emp_off_address_Rep.chkregion);
           
               if(document.Hrm_emp_off_address_Rep.chkregion.length > 0)
              {
              
                // alert(document.Hrm_emp_off_address_Rep.chkregion.length);
             
                            for(i=0;i<document.Hrm_emp_off_address_Rep.chkregion.length;i++)
                            {
                               // alert("document.Hrm_emp_off_address_Rep.chkregion[i].checked..."+document.Hrm_emp_off_address_Rep.chkregion[i].checked);
                                    if(document.Hrm_emp_off_address_Rep.chkregion[i].checked==true)
                                            region= region+document.Hrm_emp_off_address_Rep.chkregion[i].value +",";
                                            
                                         //   alert("region..."+region);
                                    
                            }
                            
                            if(region!="")
                            {
                              // alert("inside 1");
                                 if(circleflag==true && cir=='null')
                                {
                                  //     alert("inside 2");
                                         var iframe=document.getElementById("diviframecircle");
                                            iframe.style.visibility='visible';
                                          iframe.focus();
                                           document.Hrm_emp_off_address_Rep.cmbdivision.disabled=false;
                                      //  return;
                                }
                                region=region.substring(0,region.length-1);
                               // alert("region1..."+region);
                                //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                
                                var url="../../../../../../EmployeeDPReport_office?OLevel=circle&regions="+region;
                               //alert(url);
                              // document.Hrm_emp_off_address_Rep.cmbregion.focus();
                                var req=getTransport();
                                req.open("GET",url,false);        
                                req.onreadystatechange=function()
                                {  //alert("success");
                                    //requesthandle(req);
                                    if(req.readyState==4)
                                    { 
                                          if(req.status==200)
                                          {  
                                        
                                         //  var iframe=document.getElementById("diviframecircle");
                                            // if((type=='CL' || type=='DN') && cir=='null' )
                                            if(circleflag==true && cir=='null')
                                            {
                                         //   alert("see these");
                                            var iframe=document.getElementById("diviframecircle");
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                            
                                            if(navigator.appName.indexOf('Microsoft')!=-1)
                                            	{
                                            	 iframe.innerText=req.responseText;
                                              iframe.innerHTML=req.responseText;
                                            	}
                                              else
                                            	  {
                                              iframe.innerText=req.responseText;
                                            iframe.innerHTML=req.responseText;
                                            	  }
                                            
                                            document.Hrm_emp_off_address_Rep.cmbdivision.disabled=false;
                                              var iframe=document.getElementById("diviframedivision");
                                             iframe.style.visibility='hidden';
                                            
                                            
                                            }
                                             iframe.innerHTML=req.responseText;
                                             iframe.innerText=req.responseText;
                                         //    alert(iframe.innerHTML);
                                             /*
                                             if(navigator.appName.indexOf('Microsoft')!=-1)
                                              iframe.innerHTML=req.responseText;
                                              else
                                              iframe.innerText=req.responseText;
                                              */
                                             circleflag=true;
                                            
                                          }
                                    }
                                }
                                req.send(null);
                            }
                            else
                            {
                                var iframe=document.getElementById("diviframecircle");
                                iframe.style.visibility='hidden';
                                document.Hrm_emp_off_address_Rep.cmbdivision.disabled=false;
                                document.Hrm_emp_off_address_Rep.cmbcircle.focus();
                                alert('Please Select a Region');
                            }
            }
            else
            {
            
                             if(document.Hrm_emp_off_address_Rep.chkregion.checked==true)
                             {
                                            region= document.Hrm_emp_off_address_Rep.chkregion.value ;
                                    
                                    
                                         if(circleflag==true && cir=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframecircle");
                                                    iframe.style.visibility='visible';
                                                  iframe.focus();
                                                   document.Hrm_emp_off_address_Rep.cmbdivision.disabled=false;
                                               // return;
                                        }
                                       // region=region.substring(0,region.length-1);
                                       // var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                        
                                        var url="../../../../../../EmployeeDPReport_office?OLevel=circle&regions="+region;
                                       // alert(url)
                                      // document.Hrm_emp_off_address_Rep.cmbregion.focus();
                                        var req=getTransport();
                                        req.open("GET",url,false);        
                                        req.onreadystatechange=function()
                                        {
                                        	
                                            //requesthandle(req);
                                            if(req.readyState==4)
                                            { 
                                            	
                                                  if(req.status==200)
                                                  {  
                                                   var iframe=document.getElementById("diviframecircle");
                                                   if(navigator.appName.indexOf('Microsoft')!=-1)
                                                	   {
                                                	   iframe.innerText=req.responseText;
                                                       iframe.innerHTML=req.responseText;
                                                	   }
                                                         else
                                                        	 {
                                                       iframe.innerText=req.responseText;
                                                   iframe.innerHTML=req.responseText;
                                                        	 }
                                                    // if((type=='CL' || type=='DN') && cir=='null' )
                                                    if(cir=='null')
                                                    {
                                                    
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                    
                                                    document.Hrm_emp_off_address_Rep.cmbdivision.disabled=false;
                                                      var iframe=document.getElementById("diviframedivision");
                                                     iframe.style.visibility='hidden';
                                                    
                                                    
                                                    }
                                                     iframe.innerHTML=req.responseText;
                                                     iframe.innerText=req.responseText;
                                            //         alert(iframe.innerHTML);
                                                     /*
                                                     if(navigator.appName.indexOf('Microsoft')!=-1)
                                                       iframe.innerHTML=req.responseText;
                                                         else
                                                       iframe.innerText=req.responseText;
                                                       */
                                                     circleflag=true;
                                                    
                                                  }
                                            }
                                        }
                                        req.send(null);
                            }
                            else
                            {
                               
                            
                                var iframe=document.getElementById("diviframecircle");
                                iframe.style.visibility='hidden';
                                document.Hrm_emp_off_address_Rep.cmbdivision.disabled=false;
                                document.Hrm_emp_off_address_Rep.cmbcircle.focus();
                                alert('Please Select a Region');
                            }

            
            
            
            
            }
            
     }
      
   
}


function getDivision(div)
    {
        
       // alert('division');
       var region="";
       var circle="";
       var offtype="";
       
       //if(document.Hrm_emp_off_address_Rep.chkregion)
      //{
      
         
           
               if(document.Hrm_emp_off_address_Rep.chkregion.length)
              {
              
                
             
                            for(i=0;i<document.Hrm_emp_off_address_Rep.chkregion.length;i++)
                            {
                             
                                    if(document.Hrm_emp_off_address_Rep.chkregion[i].checked==true)
                                            region= region+document.Hrm_emp_off_address_Rep.chkregion[i].value +",";
                                            
                                         
                                    
                            }
                 }
        //}
       
       if(document.Hrm_emp_off_address_Rep.chkofftype)
       {
               
         if(document.Hrm_emp_off_address_Rep.chkofftype.length)
         {
                    
          for(i=0;i<document.Hrm_emp_off_address_Rep.chkofftype.length;i++)
          {
            if(document.Hrm_emp_off_address_Rep.chkofftype[i].checked==true)
             offtype= offtype+"'"+document.Hrm_emp_off_address_Rep.chkofftype[i].value+"'"+",";
             
                                  
          }
           
                      
         
         }
       }
       
      if(document.Hrm_emp_off_address_Rep.chkcircle)
      {
      
            if(document.Hrm_emp_off_address_Rep.chkcircle.length)
            {
            
                            for(i=0;i<document.Hrm_emp_off_address_Rep.chkcircle.length;i++)
                            {
                                    if(document.Hrm_emp_off_address_Rep.chkcircle[i].checked==true)
                                            circle= circle+document.Hrm_emp_off_address_Rep.chkcircle[i].value +",";
                                  
                            }
                            if(circle!="")
                            {
                               
                               
                                 if(divisionflag==true && div=='null')
                                {
                                   
                                       
                                         var iframe=document.getElementById("diviframedivision");
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                       // return;
                                }
                                circle=circle.substring(0,circle.length-1);
                                offtype=offtype.substring(0,offtype.length-1);
                                region=region.substring(0,region.length-1);
                               // var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=division&circles="+circle;
                              
                              var url="../../../../../../EmployeeDPReport_office?OLevel=division&circles="+circle+"&offtype="+offtype+"&regions="+region;
                             //alert(url);
                              
                              // document.Hrm_emp_off_address_Rep.cmbregion.focus();
                                var req=getTransport();
                                req.open("GET",url,false);        
                                req.onreadystatechange=function()
                                {
                                    //requesthandle(req);
                                    if(req.readyState==4)
                                    { 
                                          if(req.status==200)
                                          { 
                                           // alert("success");
                                            var iframe=document.getElementById("diviframedivision");
                                            
                                            //var type=document.Hrm_emp_off_address_Rep.cmbolevel.options[document.Hrm_emp_off_address_Rep.cmbolevel.selectedIndex].value;
                                            if(div=='null')
                                            {
                                            //alert("visible");
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                            }
                                            if(navigator.appName.indexOf('Microsoft')!=-1)
                                              {iframe.innerHTML=req.responseText;
                                            iframe.innerText=req.responseText;}
                                              else
                                            	  {
                                              iframe.innerText=req.responseText;
                                            iframe.innerHTML=req.responseText;
                                             
                                            	  }
                                             
                                           divisionflag=true;  
                                          }
                                    }
                                }
                                req.send(null);
                            }
                            else
                            {
                                var iframe=document.getElementById("diviframedivision");
                                iframe.style.visibility='hidden';
                                document.Hrm_emp_off_address_Rep.cmbdivision.focus();
                                alert('Please Select a Circle');
                            }
                           // alert("circles:"+circle);
                }
                else
                {
                
                             if(document.Hrm_emp_off_address_Rep.chkcircle.checked==true)
                             {
                                            circle= document.Hrm_emp_off_address_Rep.chkcircle.value ;
                                    
                           
                                         if(divisionflag==true && div=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframedivision");
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                              //  return;
                                        }
                                       // circle=circle.substring(0,circle.length-1);
                                      //  var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=division&circles="+circle;
                                     offtype=offtype.substring(0,offtype.length-1); 
                                      var url="../../../../../../EmployeeDPReport_office?OLevel=division&circles="+circle+"&offtype="+offtype;
                                      
                                      // document.Hrm_emp_off_address_Rep.cmbregion.focus();
                                        var req=getTransport();
                                        req.open("GET",url,false);        
                                        req.onreadystatechange=function()
                                        {
                                            //requesthandle(req);
                                            if(req.readyState==4)
                                            { 
                                                  if(req.status==200)
                                                  { 
                                                    var iframe=document.getElementById("diviframedivision");
                                                    //var type=document.Hrm_emp_off_address_Rep.cmbolevel.options[document.Hrm_emp_off_address_Rep.cmbolevel.selectedIndex].value;
                                                    if(div=='null')
                                                    {
                                                    
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                    }
                                                    if(navigator.appName.indexOf('Microsoft')!=-1)
                                                    	{
                                              iframe.innerHTML=req.responseText;
                                                    iframe.innerText=req.responseText;
                                                    	}
                                              else
                                            	  {
                                              iframe.innerText=req.responseText;
                                                    iframe.innerHTML=req.responseText; }   
                                                     //iframe.innerHTML=req.responseText;
                                                     
                                                   divisionflag=true;  
                                                  }
                                            }
                                        }
                                        req.send(null);
                            }
                            else
                            {
                                var iframe=document.getElementById("diviframedivision");
                                iframe.style.visibility='hidden';
                                document.Hrm_emp_off_address_Rep.cmbdivision.focus();
                                alert('Please Select a Circle');
                            }
                           // alert("circles:"+circle);
                
                }
            
      }
   
}

function regionclose()
{
    
    var iframe=document.getElementById("diviframeregion");
    iframe.style.visibility='hidden';
  
}

function offtypeclose()
{
    
    var iframe=document.getElementById("diviframeofftype");
    iframe.style.visibility='hidden';
  
}


/*function regionselectAll()
{

   if(document.Hrm_emp_off_address_Rep.chkregion)
      {
     
            
            for(i=0;i<document.Hrm_emp_off_address_Rep.chkregion.length;i++)
            {
            	
                    document.Hrm_emp_off_address_Rep.chkregion[i].checked=true;
                    
            }
             //regiononchange();
        }
}*/
function regionselectAll()
{
    if(document.Hrm_emp_off_address_Rep.chkregion)
      {
      
            for(i=0;i<document.Hrm_emp_off_address_Rep.chkregion.length;i++)
            {
                    document.Hrm_emp_off_address_Rep.chkregion[i].checked=true;
                    
            }
           // circleonchange();
        }
}

function offtypeselectAll()
{

   if(document.Hrm_emp_off_address_Rep.chkofftype)
      {
      
            
            for(i=0;i<document.Hrm_emp_off_address_Rep.chkofftype.length;i++)
            {
                    document.Hrm_emp_off_address_Rep.chkofftype[i].checked=true;
                    
            }
             oftypeonchange();
        }
}


function circleclose()
{
    
    var iframe=document.getElementById("diviframecircle");
    iframe.style.visibility='hidden';
  
}


function circleselectAll()
{
    if(document.Hrm_emp_off_address_Rep.chkcircle)
      {
      
            for(i=0;i<document.Hrm_emp_off_address_Rep.chkcircle.length;i++)
            {
                    document.Hrm_emp_off_address_Rep.chkcircle[i].checked=true;
                    
            }
           // circleonchange();
        }
}

function divisionclose()
{
    
    var iframe=document.getElementById("diviframedivision");
    iframe.style.visibility='hidden';
  
}

function oftypeonchange()
{

     if(document.Hrm_emp_off_address_Rep.rad_off[3].checked)
     {
       getDivision('division');
     }
     /*
     var type=document.Hrm_emp_off_address_Rep.cmbolevel.options[document.Hrm_emp_off_address_Rep.cmbolevel.selectedIndex].value;
   circlefalg=false;
    if(type=='CL' || type=='DN')
    {
        getCircle('circle');
    }
   */
}




function frmsubmit()
{
  
    //alert('inside submit');
        var url="";
        
                             
                         if(document.Hrm_emp_off_address_Rep.rad_off[1].checked)    
                          //  if(type=='RN')
                            {
                             // alert("inside region");
                            ////////
                            var region="";
                            if(document.Hrm_emp_off_address_Rep.chkregion)
                              {
                              
                                        if(document.Hrm_emp_off_address_Rep.chkregion.length)
                                        {
                                                  for(i=0;i<document.Hrm_emp_off_address_Rep.chkregion.length;i++)
                                                    {
                                                            if(document.Hrm_emp_off_address_Rep.chkregion[i].checked==true)
                                                              region= region+document.Hrm_emp_off_address_Rep.chkregion[i].value +",";
                                                               
                                                            
                                                    }
                                                    if(region!="")
                                                    {
                                                        region=region.substring(0,region.length-1);
                                                         url=url+"&officeselected="+region;
                                                        // alert(url);
                                                         document.Hrm_emp_off_address_Rep.officeselected.value=region;
                                                         //alert(document.Hrm_emp_off_address_Rep.officeselected.value);
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Region.');
                                                            document.Hrm_emp_off_address_Rep.cmbregion[0].focus(); 
                                                            return;
                                                    }
                                            
                                        }
                                        else{
                                           
                                                if(document.Hrm_emp_off_address_Rep.chkregion.checked==true)
                                                {
                                                            region= document.Hrm_emp_off_address_Rep.chkregion.value ;
                                                            document.Hrm_emp_off_address_Rep.officeselected.value=region;
                                                }
                                                 else
                                                    {
                                                           alert('Select the Region..');
                                                            document.Hrm_emp_off_address_Rep.cmbregion.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                 else
                                {
                                       alert('Select the Region...');
                                        document.Hrm_emp_off_address_Rep.cmbregion.focus(); 
                                        return ;
                                }    
                            ////
                            
                            }
                            
                          else if(document.Hrm_emp_off_address_Rep.rad_off[2].checked) 
                            
                            //else if(type=='CL')
                            {
                            
                            ////////
                            var circle="";
                            if(document.Hrm_emp_off_address_Rep.chkcircle)
                              {
                              
                                       if(document.Hrm_emp_off_address_Rep.chkcircle.length )
                                        {
                                                
                                                    for(i=0;i<document.Hrm_emp_off_address_Rep.chkcircle.length;i++)
                                                    {
                                                            if(document.Hrm_emp_off_address_Rep.chkcircle[i].checked==true)
                                                                    circle= circle+document.Hrm_emp_off_address_Rep.chkcircle[i].value +",";
                                                                    // alert(circle);  
                                                    }
                                                    if(circle!="")
                                                    {
                                                        circle=circle.substring(0,circle.length-1);
                                                         url=url+"&officeselected="+circle;
                                                         document.Hrm_emp_off_address_Rep.officeselected.value=circle;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Circle.');
                                                            document.Hrm_emp_off_address_Rep.cmbcircle.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.Hrm_emp_off_address_Rep.chkcircle.checked==true)
                                                {
                                                            circle= document.Hrm_emp_off_address_Rep.chkcircle.value ;
                                                            document.Hrm_emp_off_address_Rep.officeselected.value=circle;
                                                           // alert(circle);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Circle..');
                                                            document.Hrm_emp_off_address_Rep.cmbcircle.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Circle...');
                                       try{
                                        document.Hrm_emp_off_address_Rep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.Hrm_emp_off_address_Rep.cmbregion.focus(); 
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                            
                           else if(document.Hrm_emp_off_address_Rep.rad_off[3].checked) 
                           // else  if(type=='DN')
                            {
                           
                            ////////
                            var division="";
                            if(document.Hrm_emp_off_address_Rep.chkdivision)
                              {
                             //alert(document.Hrm_emp_off_address_Rep.chkregion.length);
                                     if(document.Hrm_emp_off_address_Rep.chkdivision.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.Hrm_emp_off_address_Rep.chkdivision.length;i++)
                                                {
                                                        if(document.Hrm_emp_off_address_Rep.chkdivision[i].checked==true)
                                                                division= division+document.Hrm_emp_off_address_Rep.chkdivision[i].value +",";
                                                        
                                                }
                                                
                                                if(division!="")
                                                {
                                                    division=division.substring(0,division.length-1);
                                                     url=url+"&officeselected="+division;
                                                     document.Hrm_emp_off_address_Rep.officeselected.value=division;
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the Division.');
                                                        document.Hrm_emp_off_address_Rep.cmbdivision.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.Hrm_emp_off_address_Rep.chkdivision.checked==true)
                                                {
                                                                division= document.Hrm_emp_off_address_Rep.chkdivision.value;
                                                                    //division=division.substring(0,division.length-1);
                                                                 url=url+"&officeselected="+division;
                                                                 document.Hrm_emp_off_address_Rep.officeselected.value=division;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the Division..');
                                                        document.Hrm_emp_off_address_Rep.cmbdivision.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the Division...');
                                       try{
                                        document.Hrm_emp_off_address_Rep.cmbdivision.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.Hrm_emp_off_address_Rep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.Hrm_emp_off_address_Rep.cmbregion.focus(); 
                                        }
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                           else if(document.Hrm_emp_off_address_Rep.rad_off[0].checked)
                    	   {
                    	   url=url+"&officeselected=HO";
                    	   document.Hrm_emp_off_address_Rep.officeselected.value="HO";
                    	   }
                           else
                           {
                        	   url=url+"&officeselected=all";
                        	   document.Hrm_emp_off_address_Rep.officeselected.value="all";
                           }
                       
                      	
                    //}
                        
                    
               // }
       // }
                        
   // alert(url+cadre_id);
    
    document.Hrm_emp_off_address_Rep.action="Emp_address_det_rep.jsp?"+url;
    document.Hrm_emp_off_address_Rep.submit();
    	   
   /* var req=getTransport();
    req.open("POST",url,false);  
    req.send(null);*/

}

function getofftype()
    {
      //  alert(regionflag);
           //alert("inside office type");   
        
        if(offtypeflag==true)
        {
               
                 var iframe=document.getElementById("diviframeofftype");
                 iframe.style.visibility='visible';
                 iframe.focus();
               // return;
        }
       // var type=document.Hrm_emp_off_address_Rep.cmbolevel.options[document.Hrm_emp_off_address_Rep.cmbolevel.selectedIndex].value;
    
         //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=region" ;
         var url="../../../../../../EmployeeServiceReport_Interface_New?OLevel=offtype" ;
         //alert(url);
                
        document.Hrm_emp_off_address_Rep.cmbofftype.focus();
        var req=getTransport();
        req.open("GET",url,false);        
        req.onreadystatechange=function()
        {
            //requesthandle(req);
            if(req.readyState==4)
            { 
                  if(req.status==200)
                  {  
                   // alert(req.responseText);
                 
                 // document.frames("iframregion").document.body.innerHTML=req.responseText;
                  //(document.frames("iframregion").document.body.innerText);
                    var iframe=document.getElementById("diviframeofftype");
                    iframe.style.visibility='visible';
                    iframe.focus();
                   // alert(navigator.appName);
                   // alert(navigator.appName.indexOf('Microsoft'));
                   
                  
                    if(navigator.appName.indexOf('Microsoft')!=-1)
                        iframe.innerHTML=req.responseText;
                    else
                        iframe.innerText=req.responseText;
                    iframe.innerHTML=req.responseText;
                       
                   /*
                  document.Hrm_emp_off_address_Rep.cmbcircle.disabled=false;
                   var iframe=document.getElementById("diviframecircle");
                            iframe.style.visibility='hidden';
                   var iframe=document.getElementById("diviframedivision");
                            iframe.style.visibility='hidden';
                   */
                   offtypeflag=true;
                  }
            }
        }
        req.send(null);
    }
    

function divisionselectAll()
{

   if(document.Hrm_emp_off_address_Rep.chkdivision)
      {
      
            
            for(i=0;i<document.Hrm_emp_off_address_Rep.chkdivision.length;i++)
            {
                    document.Hrm_emp_off_address_Rep.chkdivision[i].checked=true;
                    
            }
             //regiononchange();
        }
}


function callService()
{	
	if(document.Hrm_emp_off_address_Rep.order[1].checked==true)
	{
//		document.Hrm_emp_off_address_Rep.service.style.display="block";
		document.getElementById("repabtstract").checked=true;
		hideService('A');
	}
	else
		{
		
		document.getElementById("orderdivabs").style.display='none';
		document.getElementById("service").style.display='none';
		
		}
	
	
	
}

function numbersonly(e,t)
{
     var unicode=e.charCode? e.charCode : e.keyCode;
     if(unicode==13)
      {
        try{t.blur();}catch(e){}
        return true;
      
      }
      if (unicode!=8 && unicode !=9  )
      {
          if (unicode<48||unicode>57 ) 
              return false; 
      }
}

function checkType()
{
	if(document.getElementsByName("yeartype")[0].checked==true)
		document.getElementById("nyear").style.display='none';
	else
		document.getElementById("nyear").style.display='';
	
	document.getElementById("nyear").value="";
}

function selectAll()
{
	var len=document.getElementsByName("service").length;
	for(var i=0;i<len;i++)
		document.getElementsByName("service")[i].checked=true;
	
}
function clearAll()
{
	var len=document.getElementsByName("service").length;
	for(var i=0;i<len;i++)
		document.getElementsByName("service")[i].checked=false;
	
}
function checkValue()
{
		if(!document.getElementById("nyear").value=="")
		{
			var val=document.getElementById("nyear").value;
			val=parseInt(val);
			if(val>35)
				{
					alert("Please Enter year of Service less or equal to 35 ");
					document.getElementById("nyear").value="";
				}
		}
	
}

function download(output)
{
	
	document.getElementById("output").value=output;
	document.frmTypeOfService.submit();
}
function  Caders()
{



    //if(document.Hrm_emp_off_address_Rep.optselect[2].checked)
   
     if(document.Hrm_emp_off_address_Rep.optselect.checked==true)
    {
      
        var id=document.getElementById("divcadre");
        id.style.display='block';       
    
    
    }

}
function getDesignation2()
{
document.getElementById("desigblock").style.display='block';
  var type=document.Hrm_emp_off_address_Rep.cmbsgroup2.options[document.Hrm_emp_off_address_Rep.cmbsgroup2.selectedIndex].value;
       
  if(type!=0)
  {
    loadOfficesByType2(type);
  }
  else
  {
     var des=document.getElementById("cmbCadre");
     des.innerHTML='';
  }
}
function loadOfficesByType2(type)
{
    //alert(type);
    var type=document.Hrm_emp_off_address_Rep.cmbsgroup2.options[document.Hrm_emp_off_address_Rep.cmbsgroup2.selectedIndex].value;
    //startwaiting(document.Hrm_emp_off_address_Rep) ;
     //service=null;
  
   //var url="../../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
   var url="../../../../../../DesigCadRankServ_New?Command=Cadre&cmbsgroup=" + type ;
   //alert(url);
    var req=getTransport();
    req.open("GET",url,false);        
    req.onreadystatechange=function()
    {
    //alert(req.readystate);
        if(req.readyState==4)
        {
        //alert(req.status);
        if(req.status==200)
        {
       // alert(req.responseText);
        var divdes=document.getElementById("divdesignation");
        divdes.style.visibility='visible';
        divdes.focus();
        //divdes.style.display='block';
       // var des=document.getElementById("cmbDesignation");
        
        
      //  alert(des);
        
       if(navigator.appName.indexOf('Microsoft')!=-1)
            divdes.innerHTML=req.responseText;
        else
        	 divdes.innerHTML=req.responseText;
            divdes.innerText=req.responseText;
         //alert("show");
        //document.getElementById("cmbRank").visibility='hidden';  
        //document.getElementById("cmbCadre").visibility='hidden';
        }
        }
         
         }
    req.send(null);
}
function cadreselectAll()
{
    if(document.Hrm_emp_off_address_Rep.chkcadre)
      {
      
            for(i=0;i<document.Hrm_emp_off_address_Rep.chkcadre.length;i++)
            {
                    document.Hrm_emp_off_address_Rep.chkcadre[i].checked=true;
                    
            }
        }
}
function designationclose()
{
    
    var iframe=document.getElementById("divdesignation");
    iframe.style.visibility='hidden';
  
}