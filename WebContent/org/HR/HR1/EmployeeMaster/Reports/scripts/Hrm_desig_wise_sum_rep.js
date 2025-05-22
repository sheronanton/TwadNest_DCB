

var regionflag=false;
var circleflag=true;
var divisionflag=true;

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


function hideofficelevel()
{
    officevisible('none','none','none'); 
    hideoffice()
    var offlevel=document.getElementById("troffice");
            offlevel.style.display="none";
            
    document.Desig_wise_summ_rep.optofficelevel[0].checked=true;
    var offlevel=document.getElementById("trofficelevel");
    offlevel.style.display="none";
}

function showofficelevel()
{
    document.Desig_wise_summ_rep.optofficelevel[1].checked=true;
    var offlevel=document.getElementById("trofficelevel");
    offlevel.style.display="block";
    
}




function officevisible(r,c,d)
{
    var rn=document.getElementById("divregion");
    rn.style.display=r;
    var cl=document.getElementById("divcircle");
    cl.style.display=c;
    var dn=document.getElementById("divdivision");
    dn.style.display=d;
    
    var rn=document.getElementById("cmbregion");
    rn.style.display=r;
    var cl=document.getElementById("cmbcircle");
    cl.style.display=c;
    var dn=document.getElementById("cmbdivision");
    dn.style.display=d;
}



function hideoffice()
{
    document.Desig_wise_summ_rep.optoffice[0].checked=true;
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="none";
}

function showoffice()
{
   
    document.Desig_wise_summ_rep.optoffice[1].checked=true;
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    document.getElementById("divallhier").style.display='block';
    //alert('hai');
    var type=document.Desig_wise_summ_rep.cmbolevel.options[document.Desig_wise_summ_rep.cmbolevel.selectedIndex].value;
    if(type=='RN') 
    {
        officevisible('block','none','none');
        
    }
    else if(type=='CL')     {
       officevisible('block','block','none');
    }

    else if(type=='DN')     {
       officevisible('block','block','block');
    }
    document.Desig_wise_summ_rep.cmbcircle.disabled=true;
    document.Desig_wise_summ_rep.cmbdivision.disabled=true;
    
    var iframe=document.getElementById("diviframeregion");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframecircle");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframedivision");
    iframe.style.visibility='hidden';
    
    
    regionflag=false;
    circleflag=true;
    divisionflag=true;
    
    
    
}


var s=0;
var hier=true;
var level=true;
var city=true;
var  other=true;

function getLevel()
    {
    //alert("show me");
        var type=document.Desig_wise_summ_rep.cmbolevel.options[document.Desig_wise_summ_rep.cmbolevel.selectedIndex].value;
        //alert('getoffice:'+type);
       officevisible('none','none','none'); 
       hideoffice()
      if(type=="")
      {
            var offlevel=document.getElementById("troffice");
            offlevel.style.display="none";
      }
      else
      {
            
             if(type!='HO') 
            {
                 var offlevel=document.getElementById("troffice");
                 offlevel.style.display="block";        
            }
            
             if(type=='DN')
              {
                var agg=document.getElementById("aggreate");
                agg.style.display="block";
              }
              else
              {
                var agg=document.getElementById("aggreate");
                agg.style.display="none";
                document.Desig_wise_summ_rep.aggid.checked=false;
              }
           
      }
    }
    
    
  
function getRegion()
    {
       // alert(regionflag);
              
        
        if(regionflag==true)
        {
               
                 var iframe=document.getElementById("diviframeregion");
                 iframe.style.visibility='visible';
                 iframe.focus();
                return;
        }
        var type=document.Desig_wise_summ_rep.cmbolevel.options[document.Desig_wise_summ_rep.cmbolevel.selectedIndex].value;
    
         //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=region" ;
         var url="../../../../../../EmployeeDetailsReportServ_New1?OLevel=region" ;
     // alert(url);
                
        document.Desig_wise_summ_rep.cmbregion.focus();
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
                    var iframe=document.getElementById("diviframeregion");
                    iframe.style.visibility='visible';
                    iframe.focus();
                   // alert(navigator.appName);
                   // alert(navigator.appName.indexOf('Microsoft'));
                    if(navigator.appName.indexOf('Microsoft')!=-1)
                        iframe.innerHTML=req.responseText;
                    else
                        iframe.innerText=req.responseText;
                       
                   
                  document.Desig_wise_summ_rep.cmbcircle.disabled=false;
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
    
        
       var region="";
        var type=document.Desig_wise_summ_rep.cmbolevel.options[document.Desig_wise_summ_rep.cmbolevel.selectedIndex].value;
    
      if(document.Desig_wise_summ_rep.chkregion)
      {
               if(document.Desig_wise_summ_rep.chkregion.length)
              {
             
                            for(i=0;i<document.Desig_wise_summ_rep.chkregion.length;i++)
                            {
                                    if(document.Desig_wise_summ_rep.chkregion[i].checked==true)
                                            region= region+document.Desig_wise_summ_rep.chkregion[i].value +",";
                                    
                            }
                            
                            if(region!="")
                            {
                                 if(circleflag==true && cir=='null')
                                {
                                       
                                         var iframe=document.getElementById("diviframecircle");
                                            iframe.style.visibility='visible';
                                          iframe.focus();
                                           document.Desig_wise_summ_rep.cmbdivision.disabled=false;
                                        return;
                                }
                                region=region.substring(0,region.length-1);
                                //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                
                                var url="../../../../../../EmployeeDetailsReportServ_New1?OLevel=circle&regions="+region;
                              // document.Desig_wise_summ_rep.cmbregion.focus();
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
                                             if((type=='CL' || type=='DN') && cir=='null' )
                                            {
                                            
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                            
                                            document.Desig_wise_summ_rep.cmbdivision.disabled=false;
                                              var iframe=document.getElementById("diviframedivision");
                                             iframe.style.visibility='hidden';
                                            
                                            
                                            }
                                             iframe.innerHTML=req.responseText;
                                             
                                             //alert(iframe.innerHTML);
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
                                document.Desig_wise_summ_rep.cmbdivision.disabled=false;
                                document.Desig_wise_summ_rep.cmbcircle.focus();
                                alert('Please Select a Region');
                            }
            }
            else
            {
            
                             if(document.Desig_wise_summ_rep.chkregion.checked==true)
                             {
                                            region= document.Desig_wise_summ_rep.chkregion.value ;
                                    
                                    
                                         if(circleflag==true && cir=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframecircle");
                                                    iframe.style.visibility='visible';
                                                  iframe.focus();
                                                   document.Desig_wise_summ_rep.cmbdivision.disabled=false;
                                                return;
                                        }
                                       // region=region.substring(0,region.length-1);
                                       // var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                        
                                        var url="../../../../../../EmployeeDetailsReportServ_New1?OLevel=circle&regions="+region;
                                        
                                      // document.Desig_wise_summ_rep.cmbregion.focus();
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
                                                     if((type=='CL' || type=='DN') && cir=='null' )
                                                    {
                                                    
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                    
                                                    document.Desig_wise_summ_rep.cmbdivision.disabled=false;
                                                      var iframe=document.getElementById("diviframedivision");
                                                     iframe.style.visibility='hidden';
                                                    
                                                    
                                                    }
                                                     iframe.innerHTML=req.responseText;
                                                     
                                                     //alert(iframe.innerHTML);
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
                                document.Desig_wise_summ_rep.cmbdivision.disabled=false;
                                document.Desig_wise_summ_rep.cmbcircle.focus();
                                alert('Please Select a Region');
                            }

            
            
            
            
            }
            
      }
   
}


function getDivision(div)
    {
        
       // alert('division');
       var circle="";
      if(document.Desig_wise_summ_rep.chkcircle)
      {
      
            if(document.Desig_wise_summ_rep.chkcircle.length)
            {
            
                            for(i=0;i<document.Desig_wise_summ_rep.chkcircle.length;i++)
                            {
                                    if(document.Desig_wise_summ_rep.chkcircle[i].checked==true)
                                            circle= circle+document.Desig_wise_summ_rep.chkcircle[i].value +",";
                                    
                            }
                            if(circle!="")
                            {
                               
                                //alert(circle);
                               // alert(divisionflag);
                                 if(divisionflag==true && div=='null')
                                {
                                       
                                         var iframe=document.getElementById("diviframedivision");
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                        return;
                                }
                                circle=circle.substring(0,circle.length-1);
                               // var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=division&circles="+circle;
                              
                              var url="../../../../../../EmployeeDetailsReportServ_New1?OLevel=division&circles="+circle;
                              
                              
                              // document.Desig_wise_summ_rep.cmbregion.focus();
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
                                            var type=document.Desig_wise_summ_rep.cmbolevel.options[document.Desig_wise_summ_rep.cmbolevel.selectedIndex].value;
                                            if(type=='DN' && div=='null')
                                            {
                                            
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                            }
                                             iframe.innerHTML=req.responseText;
                                             
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
                                document.Desig_wise_summ_rep.cmbdivision.focus();
                                alert('Please Select a Circle');
                            }
                           // alert("circles:"+circle);
                }
                else
                {
                
                             if(document.Desig_wise_summ_rep.chkcircle.checked==true)
                             {
                                            circle= document.Desig_wise_summ_rep.chkcircle.value ;
                                    
                           
                                         if(divisionflag==true && div=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframedivision");
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                return;
                                        }
                                       // circle=circle.substring(0,circle.length-1);
                                      //  var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=division&circles="+circle;
                                      
                                      var url="../../../../../../EmployeeDetailsReportServ_New1?OLevel=division&circles="+circle;
                                      
                                      // document.Desig_wise_summ_rep.cmbregion.focus();
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
                                                    var type=document.Desig_wise_summ_rep.cmbolevel.options[document.Desig_wise_summ_rep.cmbolevel.selectedIndex].value;
                                                    if(type=='DN' && div=='null')
                                                    {
                                                    
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                    }
                                                     iframe.innerHTML=req.responseText;
                                                     
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
                                document.Desig_wise_summ_rep.cmbdivision.focus();
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


function regionselectAll()
{

   if(document.Desig_wise_summ_rep.chkregion)
      {
      
            
            for(i=0;i<document.Desig_wise_summ_rep.chkregion.length;i++)
            {
                    document.Desig_wise_summ_rep.chkregion[i].checked=true;
                    
            }
             regiononchange();
        }
}


function circleclose()
{
    
    var iframe=document.getElementById("diviframecircle");
    iframe.style.visibility='hidden';
  
}


function circleselectAll()
{
    if(document.Desig_wise_summ_rep.chkcircle)
      {
      
            for(i=0;i<document.Desig_wise_summ_rep.chkcircle.length;i++)
            {
                    document.Desig_wise_summ_rep.chkcircle[i].checked=true;
                    
            }
            circleonchange();
        }
}

function divisionclose()
{
    
    var iframe=document.getElementById("diviframedivision");
    iframe.style.visibility='hidden';
  
}
function designationclose()
{
    
    var iframe=document.getElementById("divdesignation");
    iframe.style.visibility='hidden';
  
}
function rankclose()
{
    
   // var iframe=document.getElementById("divranknew");
  //  iframe.style.visibility='hidden';
  
}
function cadreclose()
{
    
  //  var iframe=document.getElementById("divcadrenew");
  //  iframe.style.visibility='hidden';
  
}

function divisionselectAll()
{
    if(document.Desig_wise_summ_rep.chkdivision)
      {
      
            for(i=0;i<document.Desig_wise_summ_rep.chkdivision.length;i++)
            {
                    document.Desig_wise_summ_rep.chkdivision[i].checked=true;
                    
            }
        }
}

function designationselectAll()
{
    if(document.Desig_wise_summ_rep.chkdesig)
      {
      
            for(i=0;i<document.Desig_wise_summ_rep.chkdesig.length;i++)
            {
                    document.Desig_wise_summ_rep.chkdesig[i].checked=true;
                    
            }
        }
}
function rankselectAll()
{
    if(document.Desig_wise_summ_rep.chkrank)
      {
      
            for(i=0;i<document.Desig_wise_summ_rep.chkrank.length;i++)
            {
                    document.Desig_wise_summ_rep.chkrank[i].checked=true;
                    
            }
        }
}
function cadreselectAll()
{
    if(document.Desig_wise_summ_rep.chkcadre)
      {
      
            for(i=0;i<document.Desig_wise_summ_rep.chkcadre.length;i++)
            {
                    document.Desig_wise_summ_rep.chkcadre[i].checked=true;
                    
            }
        }
}


function regiononchange()
{
     var type=document.Desig_wise_summ_rep.cmbolevel.options[document.Desig_wise_summ_rep.cmbolevel.selectedIndex].value;
   circlefalg=false;
    if(type=='CL' || type=='DN')
    {
        getCircle('circle');
    }

}

function circleonchange()
{
     var type=document.Desig_wise_summ_rep.cmbolevel.options[document.Desig_wise_summ_rep.cmbolevel.selectedIndex].value;
    divisionfalg=false;
    if(type=='DN')
    {
    getDivision('division');
    }

}



function hidedisignation()
{
    document.Desig_wise_summ_rep.optdesignation[0].checked=true;
   var offlevel=document.getElementById("trsergroup");
    offlevel.style.display="none";
    
      var offlevel=document.getElementById("divdest");
    offlevel.style.visibility="hidden";
     var offlevel=document.getElementById("cmbDesignation");
    offlevel.style.visibility="hidden";
    document.Desig_wise_summ_rep.cmbsgroup.value="0";
}

function showdesignation()
{
   document.Desig_wise_summ_rep.optdesignation[1].checked=true;
    var offlevel=document.getElementById("trsergroup");
    offlevel.style.display="block";
}

function getDesignation()
{
document.getElementById("desigblock").style.display='block';
//alert("getDesisg");
   var type=document.Desig_wise_summ_rep.cmbsgroup.options[document.Desig_wise_summ_rep.cmbsgroup.selectedIndex].value;
   //alert(type);
   if(type!=0)
    {
    //alert("inside");
           // var din=document.getElementById("divdest");
           // din.style.visibility="visible";
           // document.Desig_wise_summ_rep.cmbDesignation.style.visibility="visible";
            loadOfficesByType(type);
    }
   else
    {
             var des=document.getElementById("cmbDesignation");
             des.innerHTML='';
            // var din=document.getElementById("divdest");
           //  din.style.visibility="hidden";
           //  document.Desig_wise_summ_rep.cmbDesignation.style.visibility="hidden";
     }
}
    
/*function loadOfficesByType(type)
    {
        //alert(type);
        var type=document.Desig_wise_summ_rep.cmbsgroup.options[document.Desig_wise_summ_rep.cmbsgroup.selectedIndex].value;
        //startwaiting(document.Desig_wise_summ_rep) ;
         //service=null;
      
       //var url="../../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
       var url="../../../../../../DesigCadRankServ_New?Command=Desig&cmbsgroup=" + type ;
       //alert(url);
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadDesignation(req);
        }
        req.send(null);
    }*/
    
    
    function loadOfficesByType(type)
    {
        //alert(type);
        var type=document.Desig_wise_summ_rep.cmbsgroup.options[document.Desig_wise_summ_rep.cmbsgroup.selectedIndex].value;
        //startwaiting(document.Desig_wise_summ_rep) ;
         //service=null;
      
       //var url="../../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
       var url="../../../../../../DesigCadRankServ_New?Command=Desig&cmbsgroup=" + type ;
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
            // alert("show");
            //document.getElementById("cmbRank").visibility='hidden';  
            //document.getElementById("cmbCadre").visibility='hidden';
            }
            }
             
             }
        req.send(null);
    }
    
function  loadDesignation(req)
{
     if(req.readyState==4)
        {
          if(req.status==200)
          { 
               
                var des=document.getElementById("cmbDesignation");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                des.innerHTML="";
                
                // stopwaiting(document.Desig_wise_summ_rep);
                if(flag=="failure")
                {
                    alert("No Designation exists under this level");
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


function frmsubmit()
{
    //alert('submit');
        var url="../../../../../../EmployeeDetailsReportServ_New1?OLevel=submit" ;
        if((document.Desig_wise_summ_rep.optselect[1].checked==false)&&(document.Desig_wise_summ_rep.optselect[2].checked==false)&&(document.Desig_wise_summ_rep.optselect[3].checked==false))
        	
        {
        	alert("Please select any Desigantion category ");
        	return false;
        }
        else
        	alert("else");
        //office validation
        if(document.Desig_wise_summ_rep.optofficelevel[0].checked==true)
        {
                url=url+"&offlevel=all";
                document.Desig_wise_summ_rep.offlevel.value='all';
        }
        else
        {
                url=url+"&offlevel=specific";
                 document.Desig_wise_summ_rep.offlevel.value='specific';
                
                // select the office 
                  var type=document.Desig_wise_summ_rep.cmbolevel.options[document.Desig_wise_summ_rep.cmbolevel.selectedIndex].value;
                 if(type=="")
                 {
                    alert('Select the Office Level');
                    document.Desig_wise_summ_rep.cmbolevel.focus();
                    return;
                }
                else
                {
                    url=url+"&office="+type;
                    document.Desig_wise_summ_rep.office.value=type;
                    
                    if(document.Desig_wise_summ_rep.optoffice[0].checked==true)
                    {
                            url=url+"&officetype=all";
                             document.Desig_wise_summ_rep.officetype.value='all';
                    }
                    else
                    {
                            url=url+"&officetype=specific";
                             document.Desig_wise_summ_rep.officetype.value='specific';
                            if(type=='RN')
                            {
                            ////////
                            var region="";
                            if(document.Desig_wise_summ_rep.chkregion)
                              {
                              
                                        if(document.Desig_wise_summ_rep.chkregion.length)
                                        {
                                                  for(i=0;i<document.Desig_wise_summ_rep.chkregion.length;i++)
                                                    {
                                                            if(document.Desig_wise_summ_rep.chkregion[i].checked==true)
                                                                    region= region+document.Desig_wise_summ_rep.chkregion[i].value +",";
                                                            
                                                    }
                                                    if(region!="")
                                                    {
                                                        region=region.substring(0,region.length-1);
                                                         url=url+"&officeselected="+region;
                                                         document.Desig_wise_summ_rep.officeselected.value=region;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Region.');
                                                            document.Desig_wise_summ_rep.cmbregion[0].focus(); 
                                                            return;
                                                    }
                                            
                                        }
                                        else{
                                           
                                                if(document.Desig_wise_summ_rep.chkregion.checked==true)
                                                {
                                                            region= document.Desig_wise_summ_rep.chkregion.value ;
                                                            document.Desig_wise_summ_rep.officeselected.value=region;
                                                }
                                                 else
                                                    {
                                                           alert('Select the Region..');
                                                            document.Desig_wise_summ_rep.cmbregion.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                 else
                                {
                                       alert('Select the Region...');
                                        document.Desig_wise_summ_rep.cmbregion.focus(); 
                                        return ;
                                }    
                            ////
                            
                            }
                            
                            else if(type=='CL')
                            {
                            
                            ////////
                            var circle="";
                            if(document.Desig_wise_summ_rep.chkcircle)
                              {
                              
                                       if(document.Desig_wise_summ_rep.chkcircle.length )
                                        {
                                                
                                                    for(i=0;i<document.Desig_wise_summ_rep.chkcircle.length;i++)
                                                    {
                                                            if(document.Desig_wise_summ_rep.chkcircle[i].checked==true)
                                                                    circle= circle+document.Desig_wise_summ_rep.chkcircle[i].value +",";
                                                            
                                                    }
                                                    if(circle!="")
                                                    {
                                                        circle=circle.substring(0,circle.length-1);
                                                         url=url+"&officeselected="+circle;
                                                         document.Desig_wise_summ_rep.officeselected.value=circle;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Circle.');
                                                            document.Desig_wise_summ_rep.cmbcircle.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.Desig_wise_summ_rep.chkcircle.checked==true)
                                                {
                                                            circle= document.Desig_wise_summ_rep.chkcircle.value ;
                                                            document.Desig_wise_summ_rep.officeselected.value=circle;
                                                           // alert(circle);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Circle..');
                                                            document.Desig_wise_summ_rep.cmbcircle.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Circle...');
                                       try{
                                        document.Desig_wise_summ_rep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.Desig_wise_summ_rep.cmbregion.focus(); 
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                            else  if(type=='DN')
                            {
                           
                            ////////
                            var division="";
                            if(document.Desig_wise_summ_rep.chkdivision)
                              {
                             //alert(document.Desig_wise_summ_rep.chkregion.length);
                                     if(document.Desig_wise_summ_rep.chkdivision.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.Desig_wise_summ_rep.chkdivision.length;i++)
                                                {
                                                        if(document.Desig_wise_summ_rep.chkdivision[i].checked==true)
                                                                division= division+document.Desig_wise_summ_rep.chkdivision[i].value +",";
                                                        
                                                }
                                                
                                                if(division!="")
                                                {
                                                    division=division.substring(0,division.length-1);
                                                     url=url+"&officeselected="+division;
                                                     document.Desig_wise_summ_rep.officeselected.value=division;
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the Division.');
                                                        document.Desig_wise_summ_rep.cmbdivision.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.Desig_wise_summ_rep.chkdivision.checked==true)
                                                {
                                                                division= document.Desig_wise_summ_rep.chkdivision.value;
                                                                    //division=division.substring(0,division.length-1);
                                                                 url=url+"&officeselected="+division;
                                                                 document.Desig_wise_summ_rep.officeselected.value=division;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the Division..');
                                                        document.Desig_wise_summ_rep.cmbdivision.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the Division...');
                                       try{
                                        document.Desig_wise_summ_rep.cmbdivision.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.Desig_wise_summ_rep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.Desig_wise_summ_rep.cmbregion.focus(); 
                                        }
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                        
                    }
                        
                    
                }
        }
       
      
        //select the output type
        if(document.Desig_wise_summ_rep.optoutputtype[0].checked==true)
        {
                url=url+"&outputtype=pdf";
                document.Desig_wise_summ_rep.outputtype.value='pdf';
        }
        else if(document.Desig_wise_summ_rep.optoutputtype[1].checked==true)
        {
                url=url+"&outputtype=excel";
                document.Desig_wise_summ_rep.outputtype.value='excel';
        }
        else if(document.Desig_wise_summ_rep.optoutputtype[2].checked==true)
        {
                url=url+"&outputtype=html";
                 document.Desig_wise_summ_rep.outputtype.value='html';
        }
        
   
    
    document.Desig_wise_summ_rep.action="../../../../../../EmployeeDetailsReportServ_New1";
    document.Desig_wise_summ_rep.submit();
   /* var req=getTransport();
    req.open("POST",url,false);  
    req.send(null);*/

}



//////////////////////////// for next purpose ///////////////////////
///////////////////////  for designation,cadre,rank   ///////////////////////

function sellectall()
{
   
  //  if(document.Desig_wise_summ_rep.optselect[1].checked)
  if(document.Desig_wise_summ_rep.optselectgrp[1].checked)
    {
      //alert("1");
      //alert(document.Desig_wise_summ_rep.optselect[1].checked);
      var id=document.getElementById("divselect");
      id.style.display='block';          
                
    }    
                      
    else
    {
      var id=document.getElementById("divselect");
      id.style.display='none';      
     
     }
        
}

function hidedesig()
{
//alert("here");
   //if(document.Desig_wise_summ_rep.optselect[0].checked)
   if(document.Desig_wise_summ_rep.optselectgrp[0].checked)
   {
  // alert('sec')
      var id1=document.getElementById("divselect");
      id1.style.display='none';
      var id2=document.getElementById("divdest");
      id2.style.display='none';
      var id3=document.getElementById("divrank");
      id3.style.display='none';
      var id4=document.getElementById("divcadre");
      id4.style.display='none';
    document.getElementById("desigblock").style.display='none';
      
   }
   
   else
   {
     var id1=document.getElementById("divselect");
      id1.style.display='block';
      var id2=document.getElementById("divdest");
      id2.style.display='block';
      var id3=document.getElementById("divrank");
      id3.style.display='block';
      var id4=document.getElementById("divcadre");
      id4.style.display='block';
      document.getElementById("desigblock").style.display='block';
   }

}
/*
function  selectoption1()
{
//alert('test');


    if(document.Desig_wise_summ_rep.optselect[0].checked)
    {
      alert(document.Desig_wise_summ_rep.optselect[0].checked);
        var id=document.getElementById("divdest");
        id.style.display='block';
        var id=document.getElementById("divrank");
        id.style.display='none';
        var id=document.getElementById("divcadre");
        id.style.display='none';
    }
    
    else if(document.Desig_wise_summ_rep.optselect[1].checked)
    {
      alert(document.Desig_wise_summ_rep.optselect[1].checked);
         var id=document.getElementById("divdest");
        id.style.display='none';
        var id=document.getElementById("divrank");
        id.style.display='block';
         var id=document.getElementById("divcadre");
        id.style.display='none';       
    
    }
    else if(document.Desig_wise_summ_rep.optselect[2].checked)
    {
       alert(document.Desig_wise_summ_rep.optselect[2].checked);
        var id=document.getElementById("divdest");
        id.style.display='none';
        var id=document.getElementById("divrank");
        id.style.display='none';
        var id=document.getElementById("divcadre");
        id.style.display='block';       
    
    
    }

}
    */
    
function  selectoption1()
{
//alert('test');


    //if(document.Desig_wise_summ_rep.optselect[2].checked)
    if(document.Desig_wise_summ_rep.optselect[0].checked)
    {
      //alert(document.Desig_wise_summ_rep.optselect[2].checked);
        var id=document.getElementById("divdest");
        id.style.display='block';
        var divdesigid=document.getElementById("divdesignation");
        divdesigid.style.visibility='hidden';
        var id=document.getElementById("divrank");
        id.style.display='none';
        //var divcadreid=document.getElementById("divranknew");
       // divcadreid.style.visibility='hidden';
        var id=document.getElementById("divcadre");
        id.style.display='none';
      //  var divcadreid=document.getElementById("divcadrenew");
      //  divcadreid.style.visibility='hidden';
    }
    
   // else if(document.Desig_wise_summ_rep.optselect[3].checked)
    else if(document.Desig_wise_summ_rep.optselect[1].checked)
    {
      //alert(document.Desig_wise_summ_rep.optselect[3].checked);
         var id=document.getElementById("divdest");
        id.style.display='none';
        var divdesigid=document.getElementById("divdesignation");
        divdesigid.style.visibility='hidden';
        var id=document.getElementById("divrank");
        id.style.display='block';
         var id=document.getElementById("divcadre");
        id.style.display='none';     
       //  var divcadreid=document.getElementById("divcadrenew");
       // divcadreid.style.visibility='hidden';
    
    }
   // else if(document.Desig_wise_summ_rep.optselect[4].checked)
    else if(document.Desig_wise_summ_rep.optselect[2].checked)
    {
       //alert(document.Desig_wise_summ_rep.optselect[4].checked);
        var id=document.getElementById("divdest");
        id.style.display='none';
        var divdesigid=document.getElementById("divdesignation");
        divdesigid.style.visibility='hidden';
        var id=document.getElementById("divrank");
        id.style.display='none';
       //  var divcadreid=document.getElementById("divranknew");
       // divcadreid.style.visibility='hidden';
        var id=document.getElementById("divcadre");
        id.style.display='block';       
    
    
    }

}
        
    
function checkGroup()
{
        
  var type=document.Desig_wise_summ_rep.cmbsgroup.options[document.Desig_wise_summ_rep.cmbsgroup.selectedIndex].value;
  if(type==0)
   {
     alert('Select Service Group');
     document.Desig_wise_summ_rep.cmbsgroup.focus();
     return false;
   }
}    



function getDesignation1()
{
document.getElementById("desigblock").style.display='block';
  var type=document.Desig_wise_summ_rep.cmbsgroup1.options[document.Desig_wise_summ_rep.cmbsgroup1.selectedIndex].value;
  //alert(type)     
  if(type!=0)
  {
    loadOfficesByType1(type);
  }
  else
  {
     var des=document.getElementById("cmbRank");
     des.innerHTML='';
  }
}


/*function loadOfficesByType1(type)
{
        //alert(type);
  var type=document.Desig_wise_summ_rep.cmbsgroup1.options[document.Desig_wise_summ_rep.cmbsgroup1.selectedIndex].value;
  startwaiting(document.Desig_wise_summ_rep) ;
  //var url="../../../../../../SR_Based_QueryServ.rep?Command=SGroup&cmbsgroup=" + type ; DesigCadRankServ
  var url="../../../../../../DesigCadRankServ?Command=Rank&cmbsgroup=" + type ; 
  var req=getTransport();
        //alert(url);
  req.open("GET",url,true);        
  req.onreadystatechange=function()
  {
            
     loadDesignation1(req);
  }
    req.send(null);
}*/

 function loadOfficesByType1(type)
    {
        //alert(type);
        var type=document.Desig_wise_summ_rep.cmbsgroup1.options[document.Desig_wise_summ_rep.cmbsgroup1.selectedIndex].value;
        //startwaiting(document.Desig_wise_summ_rep) ;
         //service=null;
      
       //var url="../../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
       var url="../../../../../../DesigCadRankServ_New?Command=Rank&cmbsgroup=" + type ;
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
            //alert(req.responseText);
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

function  loadDesignation1(req)
{
     if(req.readyState==4)
        {
          if(req.status==200)
          { 
                //alert(req);
                var des=document.getElementById("cmbRank");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                des.innerHTML="";
                
                 stopwaiting(document.Desig_wise_summ_rep);
                if(flag=="failure")
                {
                    alert("No Rank exists under this level");
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
                    option.text="--Select Rank--";
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

function checkGroup1()
{
        
        var type=document.Desig_wise_summ_rep.cmbsgroup1.options[document.Desig_wise_summ_rep.cmbsgroup1.selectedIndex].value;
        if(type==0)
        {
            alert('Select Service Group');
            document.Desig_wise_summ_rep.cmbsgroup1.focus();
            return false;
        }
}

function getDesignation2()
{
document.getElementById("desigblock").style.display='block';
  var type=document.Desig_wise_summ_rep.cmbsgroup2.options[document.Desig_wise_summ_rep.cmbsgroup2.selectedIndex].value;
       
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

/*function loadOfficesByType2(type)
{
        //alert(type);
  var type=document.Desig_wise_summ_rep.cmbsgroup2.options[document.Desig_wise_summ_rep.cmbsgroup2.selectedIndex].value;
  startwaiting(document.Desig_wise_summ_rep) ;
  var url="../../../../../../DesigCadRankServ?Command=Cadre&cmbsgroup=" + type ;
  var req=getTransport();
        //alert(url);
  req.open("GET",url,true);        
  req.onreadystatechange=function()
  {
            
     loadDesignation2(req);
  }
    req.send(null);
}*/

 function loadOfficesByType2(type)
    {
        //alert(type);
        var type=document.Desig_wise_summ_rep.cmbsgroup2.options[document.Desig_wise_summ_rep.cmbsgroup2.selectedIndex].value;
        //startwaiting(document.Desig_wise_summ_rep) ;
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
            //alert(req.responseText);
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

function  loadDesignation2(req)
{
     if(req.readyState==4)
        {
          if(req.status==200)
          { 
                //alert(req);
                var des=document.getElementById("cmbCadre");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                des.innerHTML="";
                
                 stopwaiting(document.Desig_wise_summ_rep);
                if(flag=="failure")
                {
                    alert("No Rank exists under this level");
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
                    option.text="--Select Cadre--";
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

function checkGroup2()
{
        
  var type=document.Desig_wise_summ_rep.cmbsgroup2.options[document.Desig_wise_summ_rep.cmbsgroup2.selectedIndex].value;
  if(type==0)
  {
     alert('Select Service Group');
     document.Desig_wise_summ_rep.cmbsgroup2.focus();
     return false;
  }
}

function desigChange()
{
var desig="";
//alert('designationchange');
if(document.Desig_wise_summ_rep.chkdesig)
      {
     // alert("1");
               if(document.Desig_wise_summ_rep.chkdesig.length)
              {
              //alert("2");
             
                            for(i=0;i<document.Desig_wise_summ_rep.chkdesig.length;i++)
                            {
                            
                                    if(document.Desig_wise_summ_rep.chkdesig[i].checked==true)
                                        { 
                                           //alert("inside");
                                            desig=desig+document.Desig_wise_summ_rep.chkdesig[i].value +",";
                                         }
                            }
                            
                            if(desig!="")
                            {
                            //alert("ids:"+desig);
                            desig=desig.substring(0,desig.length-1);
                            var url="../../../../../../EmployeeDetailsReportServ_New1?desigs="+desig;
                            }
               }
        }  
        
}

function rankChange()
{
var rank="";
//alert('rank');
if(document.Desig_wise_summ_rep.chkdesig)
      {
      //alert("1");
               if(document.Desig_wise_summ_rep.chkrank.length)
              {
        //      alert("2");
             
                            for(i=0;i<document.Desig_wise_summ_rep.chkrank.length;i++)
                            {
                            
                                    if(document.Desig_wise_summ_rep.chkrank[i].checked==true)
                                        { 
                                           //alert("inside");
                                            rank=rank+document.Desig_wise_summ_rep.chkrank[i].value +",";
                                         }
                            }
                            
                            if(rank!="")
                            {
                            //alert("ids:"+rank);
                            rank=rank.substring(0,rank.length-1);
                            var url="../../../../../../EmployeeDetailsReportServ_New1?ranks="+rank;
                            }
               }
        }  
        
}
    
function cadreChange()
{
var cadre="";
//alert('cadre');
if(document.Desig_wise_summ_rep.chkcadre)
      {
      //alert("1");
               if(document.Desig_wise_summ_rep.chkcadre.length)
              {
             // alert("2");
             
                            for(i=0;i<document.Desig_wise_summ_rep.chkcadre.length;i++)
                            {
                            
                                    if(document.Desig_wise_summ_rep.chkcadre[i].checked==true)
                                        { 
                                           //alert("inside");
                                            cadre=cadre+document.Desig_wise_summ_rep.chkcadre[i].value +",";
                                         }
                            }
                            
                            if(cadre!="")
                            {
                            //alert("ids:"+cadre);
                            cadre=cadre.substring(0,cadre.length-1);
                            var url="../../../../../../EmployeeDetailsReportServ_New1?cadres="+cadre;
                            }
               }
        }  
        
}
function showradio()
{
	
	document.Desig_wise_summ_rep.optselect[0].disabled=false;
	document.Desig_wise_summ_rep.optselect[2].disabled=false;
	document.Desig_wise_summ_rep.dep_str[0].disabled=false;
	
}
function hideradio()
{
	document.Desig_wise_summ_rep.dep_str[0].disabled=true;
	document.Desig_wise_summ_rep.optselect[0].disabled=true;
	document.Desig_wise_summ_rep.optselect[2].disabled=true;	
	selectoption1();hide();
}
function hide()
{
	
if(document.Desig_wise_summ_rep.optselectgrp[0].checked==true)
	{
document.getElementById("cmbsgroup").style.display='none';	
document.getElementById("cmbsgroup1").style.display='none';	
document.getElementById("cmbsgroup2").style.display='none';
document.getElementById("divdesignation").style.display='none';

	}
else
	{
	document.getElementById("cmbsgroup").style.display='';	
	}
}
function unchkrank()
{
	
	if(document.Desig_wise_summ_rep.optselect[1].checked)
		document.Desig_wise_summ_rep.optselect[1].checked=false;
}
function show()
{
	
if(document.Desig_wise_summ_rep.optselectgrp[1].checked==true)
	{
document.getElementById("cmbsgroup").style.display='';	
document.getElementById("cmbsgroup1").style.display='';	
document.getElementById("cmbsgroup2").style.display='';
document.getElementById("divdesignation").style.display='';

	}
else
	{
	document.getElementById("cmbsgroup").style.display='none';	
	}
}
/*function enableHierarhy()
{
alert('show');
alert(document.getElementById("divallhier"));
document.getElementById("divallhier").style.display='block';
}*/