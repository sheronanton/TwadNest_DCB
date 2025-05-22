//alert('detail');

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

var s=0;
var hier=true;
var level=true;
var city=true;
var  other=true;

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



function designationselectAll()
{
    if(document.Emp_Retire_Befini_Rep.chkdesig)
      {
      
            for(i=0;i<document.Emp_Retire_Befini_Rep.chkdesig.length;i++)
            {
                    document.Emp_Retire_Befini_Rep.chkdesig[i].checked=true;
                    
            }
        }
}
function rankselectAll()
{
    if(document.Emp_Retire_Befini_Rep.chkrank)
      {
      
            for(i=0;i<document.Emp_Retire_Befini_Rep.chkrank.length;i++)
            {
                    document.Emp_Retire_Befini_Rep.chkrank[i].checked=true;
                    
            }
        }
}
function cadreselectAll()
{
    if(document.Emp_Retire_Befini_Rep.chkcadre)
      {
      
            for(i=0;i<document.Emp_Retire_Befini_Rep.chkcadre.length;i++)
            {
                    document.Emp_Retire_Befini_Rep.chkcadre[i].checked=true;
                    
            }
        }
}








function hidedisignation()
{
    document.Emp_Retire_Befini_Rep.optdesignation[0].checked=true;
   var offlevel=document.getElementById("trsergroup");
    offlevel.style.display="none";
    
      var offlevel=document.getElementById("divdest");
    offlevel.style.visibility="hidden";
     var offlevel=document.getElementById("cmbDesignation");
    offlevel.style.visibility="hidden";
    document.Emp_Retire_Befini_Rep.cmbsgroup.value="0";
}

function showdesignation()
{
   document.Emp_Retire_Befini_Rep.optdesignation[1].checked=true;
    var offlevel=document.getElementById("trsergroup");
    offlevel.style.display="block";
}

function getDesignation()
{
document.getElementById("desigblock").style.display='block';
//alert("getDesisg");
   var type=document.Emp_Retire_Befini_Rep.cmbsgroup.options[document.Emp_Retire_Befini_Rep.cmbsgroup.selectedIndex].value;
   //alert(type);
   if(type!=0)
    {
    //alert("inside");
           // var din=document.getElementById("divdest");
           // din.style.visibility="visible";
           // document.Emp_Retire_Befini_Rep.cmbDesignation.style.visibility="visible";
            loadOfficesByType(type);
    }
   else
    {
             var des=document.getElementById("cmbDesignation");
             des.innerHTML='';
            // var din=document.getElementById("divdest");
           //  din.style.visibility="hidden";
           //  document.Emp_Retire_Befini_Rep.cmbDesignation.style.visibility="hidden";
     }
}
    
/*function loadOfficesByType(type)
    {
        //alert(type);
        var type=document.Emp_Retire_Befini_Rep.cmbsgroup.options[document.Emp_Retire_Befini_Rep.cmbsgroup.selectedIndex].value;
        //startwaiting(document.Emp_Retire_Befini_Rep) ;
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
       
        var type=document.Emp_Retire_Befini_Rep.cmbsgroup.options[document.Emp_Retire_Befini_Rep.cmbsgroup.selectedIndex].value;
        //startwaiting(document.Emp_Retire_Befini_Rep) ;
         //service=null;
      
       //var url="../../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
       var url="../../../../../../DesigCadRankServ_New?Command=Desig&cmbsgroup=" + type ;
       //alert(url);
        var req=getTransport();
        req.open("GET",url,true);        
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
                divdes.innerText=req.responseText;
                 divdes.innerHTML=req.responseText;
             //alert("show");
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
                
                // stopwaiting(document.Emp_Retire_Befini_Rep);
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
  /*
    //alert('submit');
        var url="../../../../../../EmployeeDetailsReportServ_New1?OLevel=submit" ;
        
        //office validation
        if(document.Emp_Retire_Befini_Rep.optofficelevel[0].checked==true)
        {
                url=url+"&offlevel=all";
                document.Emp_Retire_Befini_Rep.offlevel.value='all';
        }
        else
        {
                url=url+"&offlevel=specific";
                 document.Emp_Retire_Befini_Rep.offlevel.value='specific';
                
                // select the office 
                  var type=document.Emp_Retire_Befini_Rep.cmbolevel.options[document.Emp_Retire_Befini_Rep.cmbolevel.selectedIndex].value;
                 if(type=="")
                 {
                    alert('Select the Office Level');
                    document.Emp_Retire_Befini_Rep.cmbolevel.focus();
                    return;
                }
                else
                {
                    url=url+"&office="+type;
                    document.Emp_Retire_Befini_Rep.office.value=type;
                    
                    if(document.Emp_Retire_Befini_Rep.optoffice[0].checked==true)
                    {
                            url=url+"&officetype=all";
                             document.Emp_Retire_Befini_Rep.officetype.value='all';
                    }
                    else
                    {
                            url=url+"&officetype=specific";
                             document.Emp_Retire_Befini_Rep.officetype.value='specific';
                            if(type=='RN')
                            {
                            ////////
                            var region="";
                            if(document.Emp_Retire_Befini_Rep.chkregion)
                              {
                              
                                        if(document.Emp_Retire_Befini_Rep.chkregion.length)
                                        {
                                                  for(i=0;i<document.Emp_Retire_Befini_Rep.chkregion.length;i++)
                                                    {
                                                            if(document.Emp_Retire_Befini_Rep.chkregion[i].checked==true)
                                                                    region= region+document.Emp_Retire_Befini_Rep.chkregion[i].value +",";
                                                            
                                                    }
                                                    if(region!="")
                                                    {
                                                        region=region.substring(0,region.length-1);
                                                         url=url+"&officeselected="+region;
                                                         document.Emp_Retire_Befini_Rep.officeselected.value=region;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Region.');
                                                            document.Emp_Retire_Befini_Rep.cmbregion[0].focus(); 
                                                            return;
                                                    }
                                            
                                        }
                                        else{
                                           
                                                if(document.Emp_Retire_Befini_Rep.chkregion.checked==true)
                                                {
                                                            region= document.Emp_Retire_Befini_Rep.chkregion.value ;
                                                            document.Emp_Retire_Befini_Rep.officeselected.value=region;
                                                }
                                                 else
                                                    {
                                                           alert('Select the Region..');
                                                            document.Emp_Retire_Befini_Rep.cmbregion.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                 else
                                {
                                       alert('Select the Region...');
                                        document.Emp_Retire_Befini_Rep.cmbregion.focus(); 
                                        return ;
                                }    
                            ////
                            
                            }
                            
                            else if(type=='CL')
                            {
                            
                            ////////
                            var circle="";
                            if(document.Emp_Retire_Befini_Rep.chkcircle)
                              {
                              
                                       if(document.Emp_Retire_Befini_Rep.chkcircle.length )
                                        {
                                                
                                                    for(i=0;i<document.Emp_Retire_Befini_Rep.chkcircle.length;i++)
                                                    {
                                                            if(document.Emp_Retire_Befini_Rep.chkcircle[i].checked==true)
                                                                    circle= circle+document.Emp_Retire_Befini_Rep.chkcircle[i].value +",";
                                                            
                                                    }
                                                    if(circle!="")
                                                    {
                                                        circle=circle.substring(0,circle.length-1);
                                                         url=url+"&officeselected="+circle;
                                                         document.Emp_Retire_Befini_Rep.officeselected.value=circle;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Circle.');
                                                            document.Emp_Retire_Befini_Rep.cmbcircle.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.Emp_Retire_Befini_Rep.chkcircle.checked==true)
                                                {
                                                            circle= document.Emp_Retire_Befini_Rep.chkcircle.value ;
                                                            document.Emp_Retire_Befini_Rep.officeselected.value=circle;
                                                           // alert(circle);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Circle..');
                                                            document.Emp_Retire_Befini_Rep.cmbcircle.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Circle...');
                                       try{
                                        document.Emp_Retire_Befini_Rep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.Emp_Retire_Befini_Rep.cmbregion.focus(); 
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                            else  if(type=='DN')
                            {
                           
                            ////////
                            var division="";
                            if(document.Emp_Retire_Befini_Rep.chkdivision)
                              {
                             //alert(document.Emp_Retire_Befini_Rep.chkregion.length);
                                     if(document.Emp_Retire_Befini_Rep.chkdivision.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.Emp_Retire_Befini_Rep.chkdivision.length;i++)
                                                {
                                                        if(document.Emp_Retire_Befini_Rep.chkdivision[i].checked==true)
                                                                division= division+document.Emp_Retire_Befini_Rep.chkdivision[i].value +",";
                                                        
                                                }
                                                
                                                if(division!="")
                                                {
                                                    division=division.substring(0,division.length-1);
                                                     url=url+"&officeselected="+division;
                                                     document.Emp_Retire_Befini_Rep.officeselected.value=division;
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the Division.');
                                                        document.Emp_Retire_Befini_Rep.cmbdivision.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.Emp_Retire_Befini_Rep.chkdivision.checked==true)
                                                {
                                                                division= document.Emp_Retire_Befini_Rep.chkdivision.value;
                                                                    //division=division.substring(0,division.length-1);
                                                                 url=url+"&officeselected="+division;
                                                                 document.Emp_Retire_Befini_Rep.officeselected.value=division;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the Division..');
                                                        document.Emp_Retire_Befini_Rep.cmbdivision.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the Division...');
                                       try{
                                        document.Emp_Retire_Befini_Rep.cmbdivision.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.Emp_Retire_Befini_Rep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.Emp_Retire_Befini_Rep.cmbregion.focus(); 
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
        if(document.Emp_Retire_Befini_Rep.optoutputtype[0].checked==true)
        {
                url=url+"&outputtype=pdf";
                document.Emp_Retire_Befini_Rep.outputtype.value='pdf';
        }
        else if(document.Emp_Retire_Befini_Rep.optoutputtype[1].checked==true)
        {
                url=url+"&outputtype=excel";
                document.Emp_Retire_Befini_Rep.outputtype.value='excel';
        }
        else if(document.Emp_Retire_Befini_Rep.optoutputtype[2].checked==true)
        {
                url=url+"&outputtype=html";
                 document.Emp_Retire_Befini_Rep.outputtype.value='html';
        }
        
   */
	var des_id="";
    var val=nullcheck();
    if(val ==true)
    	{
    	
    	var des_id="";
    	if(document.Emp_Retire_Befini_Rep.chkdesig)
        {
          if(document.Emp_Retire_Befini_Rep.chkdesig.length )
           {
    	    for(i=0;i<document.Emp_Retire_Befini_Rep.chkdesig.length;i++)
              {
              if(document.Emp_Retire_Befini_Rep.chkdesig[i].checked==true)
                    des_id= des_id+document.Emp_Retire_Befini_Rep.chkdesig[i].value +",";                                                                                                       
               }
               if(des_id!="")
               {
              des_id= des_id.substring(0, des_id.length-1);
              }
              else
              {
               alert("Please Select Designation!");
               return false;
              }
    	   }
        }
    	var rand_id="";
    	if(document.Emp_Retire_Befini_Rep.chkrank)
	    {
	      if(document.Emp_Retire_Befini_Rep.chkrank.length )
	       {
		    for(i=0;i<document.Emp_Retire_Befini_Rep.chkrank.length;i++)
	          {
	          if(document.Emp_Retire_Befini_Rep.chkrank[i].checked==true)
	                rand_id= rand_id+document.Emp_Retire_Befini_Rep.chkrank[i].value +",";                                                                                                       
	           }
	           
	          if(rand_id!="")
	          {
	          rand_id= rand_id.substring(0, rand_id.length-1);
	          }
	          else
	          {
	           alert("Please Select Rank! ");
	           return false;
	          }
		   }
		   
		     
	    }
    	
    	var cadre_id="";
    	if(document.Emp_Retire_Befini_Rep.chkcadre)
        {
          if(document.Emp_Retire_Befini_Rep.chkcadre.length )
           {
    	    for(i=0;i<document.Emp_Retire_Befini_Rep.chkcadre.length;i++)
              {
              if(document.Emp_Retire_Befini_Rep.chkcadre[i].checked==true)
                    cadre_id= cadre_id+document.Emp_Retire_Befini_Rep.chkcadre[i].value +",";                                                                                                       
               }
               if(cadre_id!="")
               {
                cadre_id= cadre_id.substring(0, cadre_id.length-1);
                }
                else
                {
                alert("Please Select Cadre!");
                return false;
                }
    	   }
        }
        
var frm_dat=document.getElementById("txtfromdate").value;
var to_dat=document.getElementById("txttodate").value;
var des="";
var desall="";
if(document.Emp_Retire_Befini_Rep.optselectgrp[0].checked==true)
	{
	des="All";
	}
else if(document.Emp_Retire_Befini_Rep.optselect[0].checked==true)
	{
	des="DESIG";
	}
else if(document.Emp_Retire_Befini_Rep.optselect[1].checked==true)
	{
	des="RANK";
	}
else if(document.Emp_Retire_Befini_Rep.optselect[2].checked==true)
	{
	des="CADRE";
	}

    	var url="&des_id="+des_id+"&rand_id="+rand_id+"&cadre_id="+cadre_id+"&frm_dat="+frm_dat+"&to_dat="+to_dat+"&des="+des;
    	//alert(url)
    document.Emp_Retire_Befini_Rep.action="Employee_Benifi_details_rep.jsp?"+url;
    	document.Emp_Retire_Befini_Rep.method="POST";
    document.Emp_Retire_Befini_Rep.submit();
    	}
   /* var req=getTransport();
    req.open("POST",url,false);  
    req.send(null);*/

}



//////////////////////////// for next purpose ///////////////////////
///////////////////////  for designation,cadre,rank   ///////////////////////

function sellectall()
{
   
  //  if(document.Emp_Retire_Befini_Rep.optselect[1].checked)
  if(document.Emp_Retire_Befini_Rep.optselectgrp[1].checked)
    {
      //alert("1");
      //alert(document.Emp_Retire_Befini_Rep.optselect[1].checked);
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
   //if(document.Emp_Retire_Befini_Rep.optselect[0].checked)
   if(document.Emp_Retire_Befini_Rep.optselectgrp[0].checked)
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
    
function  selectoption1()
{



    
    if(document.Emp_Retire_Befini_Rep.optselect[0].checked)
    {
      
        var id=document.getElementById("divdest");
        id.style.display='block';
        var divdesigid=document.getElementById("divdesignation");
        divdesigid.style.visibility='hidden';
        var id=document.getElementById("divrank");
        id.style.display='none';
        
        var id=document.getElementById("divcadre");
        id.style.display='none';
        if(document.Emp_Retire_Befini_Rep.cmbsgroup)
        {
        	document.Emp_Retire_Befini_Rep.cmbsgroup.value="0";
        }
        if(document.Emp_Retire_Befini_Rep.chkdesig)
        {
    
                 if(document.Emp_Retire_Befini_Rep.chkdesig.length)
                {
                //alert("2");
               
                              for(i=0;i<document.Emp_Retire_Befini_Rep.chkdesig.length;i++)
                              {
                              
                                      document.Emp_Retire_Befini_Rep.chkdesig[i].checked=false;
                                         
                              }
                              
                              
                 }
          } 
    }
    
   
    else if(document.Emp_Retire_Befini_Rep.optselect[1].checked)
    {
      
         var id=document.getElementById("divdest");
        id.style.display='none';
        var divdesigid=document.getElementById("divdesignation");
        divdesigid.style.visibility='hidden';
        var id=document.getElementById("divrank");
        id.style.display='block';
         var id=document.getElementById("divcadre");
        id.style.display='none';     
        if(document.Emp_Retire_Befini_Rep.cmbsgroup1)
        {
        	document.Emp_Retire_Befini_Rep.cmbsgroup1.value="0";
        }
        if(document.Emp_Retire_Befini_Rep.chkrank)
        {
       // alert("1");
                 if(document.Emp_Retire_Befini_Rep.chkrank.length)
                {
            //  alert("2");
               
                              for(i=0;i<document.Emp_Retire_Befini_Rep.chkrank.length;i++)
                              {
                              
                                      document.Emp_Retire_Befini_Rep.chkrank[i].checked=false;
                                          
                              }
                              
                              
                             
                 }
          }  
    }
   
    else if(document.Emp_Retire_Befini_Rep.optselect[2].checked)
    {
       
        var id=document.getElementById("divdest");
        id.style.display='none';
        var divdesigid=document.getElementById("divdesignation");
        divdesigid.style.visibility='hidden';
        var id=document.getElementById("divrank");
        id.style.display='none';
       
        var id=document.getElementById("divcadre");
        id.style.display='block';    
        if(document.Emp_Retire_Befini_Rep.cmbsgroup2)
        {
        	document.Emp_Retire_Befini_Rep.cmbsgroup2.value="0";
        }
        if(document.Emp_Retire_Befini_Rep.chkcadre)
        {
        //alert("1");
                 if(document.Emp_Retire_Befini_Rep.chkcadre.length)
                {
            //   alert("2");
               
                              for(i=0;i<document.Emp_Retire_Befini_Rep.chkcadre.length;i++)
                              {
                              
                                      document.Emp_Retire_Befini_Rep.chkcadre[i].checked=false;
                                          
                              }
                              
                             
                 }
          }  
    
    }

}    
    
function checkGroup()
{
        
  var type=document.Emp_Retire_Befini_Rep.cmbsgroup.options[document.Emp_Retire_Befini_Rep.cmbsgroup.selectedIndex].value;
  if(type==0)
   {
     alert('Select Service Group');
     document.Emp_Retire_Befini_Rep.cmbsgroup.focus();
     return false;
   }
}    



function getDesignation1()
{
document.getElementById("desigblock").style.display='block';
  var type=document.Emp_Retire_Befini_Rep.cmbsgroup1.options[document.Emp_Retire_Befini_Rep.cmbsgroup1.selectedIndex].value;
 // alert(type);    
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




 function loadOfficesByType1(type)
    {
        //alert(type);
        var type=document.Emp_Retire_Befini_Rep.cmbsgroup1.options[document.Emp_Retire_Befini_Rep.cmbsgroup1.selectedIndex].value;
        //startwaiting(document.Emp_Retire_Befini_Rep) ;
         //service=null;
      
       //var url="../../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
       var url="../../../../../../DesigCadRankServ_New?Command=Rank&cmbsgroup=" + type ;
       //alert(url);
       var req=getTransport();
       req.open("GET",url,true);        
	    req.onreadystatechange=function()
	    {
	    	
	        //requesthandle(req);
	        if(req.readyState==4)
	        { 
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
                	divdes.innerText=req.responseText;
            divdes.innerHTML=req.responseText;
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
                
                 stopwaiting(document.Emp_Retire_Befini_Rep);
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
        
        var type=document.Emp_Retire_Befini_Rep.cmbsgroup1.options[document.Emp_Retire_Befini_Rep.cmbsgroup1.selectedIndex].value;
        if(type==0)
        {
            alert('Select Service Group');
            document.Emp_Retire_Befini_Rep.cmbsgroup1.focus();
            return false;
        }
}

function getDesignation2()
{
document.getElementById("desigblock").style.display='block';
  var type=document.Emp_Retire_Befini_Rep.cmbsgroup2.options[document.Emp_Retire_Befini_Rep.cmbsgroup2.selectedIndex].value;
       
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
        var type=document.Emp_Retire_Befini_Rep.cmbsgroup2.options[document.Emp_Retire_Befini_Rep.cmbsgroup2.selectedIndex].value;
        //startwaiting(document.Emp_Retire_Befini_Rep) ;
         //service=null;
      
       //var url="../../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
       var url="../../../../../../DesigCadRankServ_New?Command=Cadre&cmbsgroup=" + type ;
       //alert(url);
        var req=getTransport();
        req.open("GET",url,true);        
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
                divdes.innerText=req.responseText;
           divdes.innerHTML=req.responseText;
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
                
                 stopwaiting(document.Emp_Retire_Befini_Rep);
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
        
  var type=document.Emp_Retire_Befini_Rep.cmbsgroup2.options[document.Emp_Retire_Befini_Rep.cmbsgroup2.selectedIndex].value;
  if(type==0)
  {
     alert('Select Service Group');
     document.Emp_Retire_Befini_Rep.cmbsgroup2.focus();
     return false;
  }
}

function desigChange()
{
var desig="";
//alert('designationchange');
if(document.Emp_Retire_Befini_Rep.chkdesig)
      {
     // alert("1");
               if(document.Emp_Retire_Befini_Rep.chkdesig.length)
              {
              //alert("2");
             
                            for(i=0;i<document.Emp_Retire_Befini_Rep.chkdesig.length;i++)
                            {
                            
                                    if(document.Emp_Retire_Befini_Rep.chkdesig[i].checked==true)
                                        { 
                                           //alert("inside");
                                            desig=desig+document.Emp_Retire_Befini_Rep.chkdesig[i].value +",";
                                         }
                            }
                            
                            if(desig!="")
                            {
                            //alert("ids:"+desig);
                            desig=desig.substring(0,desig.length-1);
                            var url="../../../../../../RetirementList1_Serv1_all?desigs="+desig;
                            }
               }
        }  
        
}

function rankChange()
{
var rank="";
//alert('rank');
if(document.Emp_Retire_Befini_Rep.chkdesig)
      {
      //alert("1");
               if(document.Emp_Retire_Befini_Rep.chkrank.length)
              {
        //      alert("2");
             
                            for(i=0;i<document.Emp_Retire_Befini_Rep.chkrank.length;i++)
                            {
                            
                                    if(document.Emp_Retire_Befini_Rep.chkrank[i].checked==true)
                                        { 
                                           //alert("inside");
                                            rank=rank+document.Emp_Retire_Befini_Rep.chkrank[i].value +",";
                                         }
                            }
                            
                            if(rank!="")
                            {
                            //alert("ids:"+rank);
                            rank=rank.substring(0,rank.length-1);
                            var url="../../../../../../RetirementList1_Serv1_all?ranks="+rank;
                            }
               }
        }  
        
}
    
function cadreChange()
{
var cadre="";
//alert('cadre');
if(document.Emp_Retire_Befini_Rep.chkcadre)
      {
      //alert("1");
               if(document.Emp_Retire_Befini_Rep.chkcadre.length)
              {
             // alert("2");
             
                            for(i=0;i<document.Emp_Retire_Befini_Rep.chkcadre.length;i++)
                            {
                            
                                    if(document.Emp_Retire_Befini_Rep.chkcadre[i].checked==true)
                                        { 
                                           //alert("inside");
                                            cadre=cadre+document.Emp_Retire_Befini_Rep.chkcadre[i].value +",";
                                         }
                            }
                            
                            if(cadre!="")
                            {
                            //alert("ids:"+cadre);
                            cadre=cadre.substring(0,cadre.length-1);
                            var url="../../../../../../RetirementList1_Serv1_all?cadres="+cadre;
                            }
               }
        }  
        
}

/*function enableHierarhy()
{
alert('show');
alert(document.getElementById("divallhier"));
document.getElementById("divallhier").style.display='block';
}*/


function checkdt(t)
{
  
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
        try{
        var f=DateFormat(t,c,event,true,'3');
        }catch(e){}
        //exception  start
        
         t.value=c;
       /*     var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear > getCurrentYear()  || currentYear<1940)
            {
            
                   // alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    //t.value="";
                   // t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        //t.value="";
                        //t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                               // alert('Entered date should be less than current date and \n year should be greater than or equal to '+ _Service_Period_Beg_Year);
                                //t.value="";
                                //t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
             if(err!=0)
                {
                    //t.value="";
                    return false;
                }
            return true;
        
        
        //exception end
        
        }
        if( f==true)
        {
            //alert(f);
            //t.value=c.replace(/\//g,'-');
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
         
            if(currentYear > getCurrentYear()  || currentYear<1940)
            {
            
                    //alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    //t.value="";
                    //t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        // alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        //t.value="";
                        //t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date and \n year should be greater than or equal to '+1940);
                               // t.value="";
                               // t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
           
            return true;
            
        }
        else
        {
                if(err!=0)
                {
                   // t.value="";
                    return false;
                }
        }*/
            
    }
    else
    {
            alert('Date format  should be (dd/mm/yyyy)');
            t.value="";
            //t.focus();
            return false
    }
    
}

function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false 
        }
       

}



//second calendar
/*

function checkcurdt(t)
{
  
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
        try{
        var f=DateFormat(t,c,event,true,'3');
        }catch(e){
        //exception  start
        
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+ _Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
             if(err!=0)
                {
                    t.value="";
                    return false;
                }
            return true;
        
        
        //exception end
        
        }
        if( f==true)
        {
            //alert(f);
            //t.value=c.replace(/\//g,'-');
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
         
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
           
            return true;
            
        }
        else
        {
                if(err!=0)
                {
                    t.value="";
                    return false;
                }
        }
            
    }
    else
    {
            alert('Date format  should be (dd-mm-yyyy)');
            t.value="";
            //t.focus();
            return false
    }
    return true;
    
}

function getCurrentYear() {
    var year = new Date().getYear();
    if(year < 1900) year += 1900;
    return year;
  }

  function getCurrentMonth() {
    return new Date().getMonth() + 1;
  } 

  function getCurrentDay() {
    return new Date().getDate();
  }  
  

function checkdt1(t)
{

   if(t.value.length==0)
       return false;
   if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
   {


       // var c=t.value.replace(/-/g,'/');
        var c=t.value;
       try{
       var f=DateFormat(t,c,event,true,'3');
       }catch(e){
       //exception  start

        t.value=c;
           var sc=t.value.split('/');
           var currenDay =sc[0];
           var currentMonth=sc[1];
           var currentYear=sc[2];
           //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
           if(currentYear<1970)
           {

                   alert('Entered date should be greater than or equal to 1970');
                   t.value="";
                   t.focus();
                   return false;
          }
         

           t.value=c;
            if(err!=0)
               {
                   t.value="";
                   return false;
               }
           return true;


       //exception end

       }
       if( f==true)
       {
           //alert(f);
           //t.value=c.replace(/\//g,'-');
           t.value=c;
           var sc=t.value.split('/');
           var currenDay =sc[0];
           var currentMonth=sc[1];
           var currentYear=sc[2];
           //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());

           if(currentYear<1970)
           {

                   alert('Entered date should be greater than or equal to 1970');
                   t.value="";
                   t.focus();
                   return false;
          }
         

           t.value=c;

           return true;

       }
       else
       {
               if(err!=0)
               {
                   t.value="";
                   return false;
               }
       }

   }
   else
   {
           alert('Date format  should be (dd/mm/yyyy)');
           t.value="";
           //t.focus();
           return false
   }

}
  
  */
  
  
function checkdat()
{
     var dt=document.Emp_Retire_Befini_Rep.txtfromdate.value;
     //alert('dt...'+dt);
     
         var dateParts = dt.split("/");
        selectedDay = dateParts[0];
        selectedMonth = dateParts[1];
        selectedYear = dateParts[2];
        //alert('selectedYear...'+selectedYear);
        
        var year = new Date().getYear();
        var mon = new Date().getMonth();
        var d=new Date().getDate();
        
        //alert(mon);
        //alert('year..'+year);
        //alert('selectedMonth'+selectedMonth);
        
        if(selectedYear<year)
        {
           alert('Year should be greater than current Year');
           document.Emp_Retire_Befini_Rep.txtfromdate.value="";
           document.Emp_Retire_Befini_Rep.txtfromdate.focus();
       }
       
       else if(selectedYear==year)
       {
       if(selectedMonth<(mon+1))
       {
         alert('Month should be greater than current Month');
           document.Emp_Retire_Befini_Rep.txtfromdate.value="";
           document.Emp_Retire_Befini_Rep.txtfromdate.focus();
       }
       
       if(selectedMonth==(mon+1))
        {
          //alert('equal');
           if(selectedDay<d)
           {
           alert('Date should be greater than current Date');
           document.Emp_Retire_Befini_Rep.txtfromdate.value="";
           document.Emp_Retire_Befini_Rep.txtfromdate.focus();
           }
       }
       }
       
       return true;

}

function checkdat1()
{
     var dt=document.Emp_Retire_Befini_Rep.txttodate.value;
     //alert('dt...'+dt);
     
         var dateParts = dt.split("/");
        selectedDay = dateParts[0];
        selectedMonth = dateParts[1];
        selectedYear = dateParts[2];
                
        var year = new Date().getYear();
        var mon = new Date().getMonth();
        var d=new Date().getDate();
        
        
        
        
        if(selectedYear<year)
        {
           alert('Year should be greater than current Year');
           document.Emp_Retire_Befini_Rep.txttodate.value="";
           document.Emp_Retire_Befini_Rep.txttodate.focus();
       }
       
       else if(selectedYear==year)
       {
       if(selectedMonth<(mon+1))
        {
           alert('Month should be greater than current Month');
           document.Emp_Retire_Befini_Rep.txttodate.value="";
           document.Emp_Retire_Befini_Rep.txttodate.focus();
       }
       
       if(selectedMonth==(mon+1))
        {
          //alert('equal');
           if(selectedDay < d)
           {
           alert('Date should be greater than current Date');
           document.Emp_Retire_Befini_Rep.txttodate.value="";
           document.Emp_Retire_Befini_Rep.txttodate.focus();
           
           }
       }
       }
       
       return true;

}

function nullcheck()
{
	
	/*if(document.Emp_Retire_Befini_Rep.optselectgrp[1].checked==true)
		{
		if(document.Emp_Retire_Befini_Rep.optselect[0].checked==false && document.Emp_Retire_Befini_Rep.optselect[1].checked==false && document.Emp_Retire_Befini_Rep.optselect[2].checked==false)
		{
			alert("select Designation");
			 return false;
		}
		}
	if(document.Emp_Retire_Befini_Rep.optselect[0].checked==true )
	{
		if(document.Emp_Retire_Befini_Rep.cmbsgroup.selectedIndex ==0)
			
			{			
			alert("select Service Group");
			return false;
			}
		else
        {
	if(document.Emp_Retire_Befini_Rep.chkdesig)
    {
      if(document.Emp_Retire_Befini_Rep.chkdesig.length )
       {
	    for(i=0;i<document.Emp_Retire_Befini_Rep.chkdesig.length;i++)
          {
          if(document.Emp_Retire_Befini_Rep.chkdesig[i].checked==true)
                des_id= des_id+document.Emp_Retire_Befini_Rep.chkdesig[i].value +",";                                                                                                       
           }
           if(des_id!="")
           {
          des_id= des_id.substring(0, des_id.length-1);
          }
          else
          {
           alert("Please Select Designation!");
           return false;
          }
	   }
    }
	}
	}*/
	var des_id="";

	try
	{
		if(document.Emp_Retire_Befini_Rep.optselectgrp[1].checked==true)
			{
	 if((document.Emp_Retire_Befini_Rep.optselect[0].checked==false)&&(document.Emp_Retire_Befini_Rep.optselect[1].checked==false)&&(document.Emp_Retire_Befini_Rep.optselect[2].checked==false))
        	
        {
        	alert("Please select any Desigantion / Rank / Cadre category ");
        	return false;
        }
			
        else
        ///alert("sdgfsg");
	
	if(document.Emp_Retire_Befini_Rep.optselect[0].checked==true)
	{
	if(document.Emp_Retire_Befini_Rep.cmbsgroup.value==0){
        alert("Please select the service group");
        return false;
        }
        else
        {
	if(document.Emp_Retire_Befini_Rep.chkdesig)
    {
      if(document.Emp_Retire_Befini_Rep.chkdesig.length )
       {
	    for(i=0;i<document.Emp_Retire_Befini_Rep.chkdesig.length;i++)
          {
          if(document.Emp_Retire_Befini_Rep.chkdesig[i].checked==true)
                des_id= des_id+document.Emp_Retire_Befini_Rep.chkdesig[i].value +",";                                                                                                       
           }
           if(des_id!="")
           {
          des_id= des_id.substring(0, des_id.length-1);
          }
          else
          {
           alert("Please Select Designation!");
           return false;
          }
	   }
    }
    }
    }
			}
    }
    catch (e) {
		// TODO: handle exception
	}
    
    var rand_id="";
    try
    {
    
	if(document.Emp_Retire_Befini_Rep.optselectgrp[1].checked==true)
	{
    if(document.Emp_Retire_Befini_Rep.optselect[1].checked==true)
    {
    if(document.Emp_Retire_Befini_Rep.cmbsgroup1.value==0){
        alert("Please select the service group");
        return false;
        }
        else
        {
				if(document.Emp_Retire_Befini_Rep.chkrank)
			    {
			      if(document.Emp_Retire_Befini_Rep.chkrank.length )
			       {
				    for(i=0;i<document.Emp_Retire_Befini_Rep.chkrank.length;i++)
			          {
			          if(document.Emp_Retire_Befini_Rep.chkrank[i].checked==true)
			                rand_id= rand_id+document.Emp_Retire_Befini_Rep.chkrank[i].value +",";                                                                                                       
			           }
			           
			          if(rand_id!="")
			          {
			          rand_id= rand_id.substring(0, rand_id.length-1);
			          }
			          else
			          {
			           alert("Please Select Rank! ");
			           return false;
			          }
				   }
				   
				     
			    }
			    }
			    }
			    }
    }
    catch(e)
    {
    }
    var cadre_id="";
    try
    {
    
	if(document.Emp_Retire_Befini_Rep.optselectgrp[1].checked==true)
	{
    if(document.Emp_Retire_Befini_Rep.optselect[2].checked==true)
    {
    if(document.Emp_Retire_Befini_Rep.cmbsgroup2.value==0){
        alert("Please select the service group");
        return false;
        }
        else
        {
	if(document.Emp_Retire_Befini_Rep.chkcadre)
    {
      if(document.Emp_Retire_Befini_Rep.chkcadre.length )
       {
	    for(i=0;i<document.Emp_Retire_Befini_Rep.chkcadre.length;i++)
          {
          if(document.Emp_Retire_Befini_Rep.chkcadre[i].checked==true)
                cadre_id= cadre_id+document.Emp_Retire_Befini_Rep.chkcadre[i].value +",";                                                                                                       
           }
           if(cadre_id!="")
           {
            cadre_id= cadre_id.substring(0, cadre_id.length-1);
            }
            else
            {
            alert("Please Select Cadre!");
            return false;
            }
	   }
    }
    }
    }
    }
    }
    catch (e) {
		// TODO: handle exception
	}
    
    
    
    
    if(document.Emp_Retire_Befini_Rep.txtfromdate.value.length==0 && document.Emp_Retire_Befini_Rep.txtfromdate.value=="")
    {
       alert('Please Enter the From Date');
       document.Emp_Retire_Befini_Rep.txtfromdate.focus();
       return false;
    }
    
    
    if(document.Emp_Retire_Befini_Rep.txttodate.value.length==0 && document.Emp_Retire_Befini_Rep.txttodate.value=="")
    {
       alert('Please Enter the To Date');
       document.Emp_Retire_Befini_Rep.txttodate.focus();
       return false;
    }
    
    return true;
    
}


  
