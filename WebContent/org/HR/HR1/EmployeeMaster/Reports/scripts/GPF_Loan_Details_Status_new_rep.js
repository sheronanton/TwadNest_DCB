//alert('detail');

var regionflag=false;
//var regionflag=true;
var circleflag=true;
var divisionflag=true;
var offtypeflag=true;
var auditflag=true;
var labflag=true;

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


function loadMonth()
{
	
	var sales_year=document.getElementById("fin_year").value;
	var length=sales_year.length;
	//alert(length);
	if(length!=4)
		alert("Check it your Year ")
	
	
    var dt=new Date();
    var year=dt.getFullYear();
    var month=dt.getMonth();
    month=month+1;
    var sales_year=document.getElementById("fin_year").value;
    var monthNames = new Array("January","February","March","April","May","June","July","August","September","October","November","December");
    var sales_month=document.getElementById("month");
    if(sales_year!=year){
    	       month=12;
    	    
    }
       
    
   var child=sales_month.childNodes;
   for(var c=child.length-1;c>1;c--)
   {
       sales_month.removeChild(child[c]);
   }
    for(i=0;i<month;i++)
    {
        var opt =document.createElement("option"); 
        var text=document.createTextNode(monthNames[i]);
        
        opt.setAttribute("value",i+1);
        opt.appendChild(text);
        sales_month.appendChild(opt);
       
    }
    
   
   
}

function frmsubmit()
{
  
    //alert('inside submit');
	
	
	if(document.getElementById("fin_year").value==0)
		{
			alert("Please Select The Year");
			return false;
		}
	
	if(document.getElementById("month").value==0)
	{
		alert("Please Select The Month");
		return false;
	}
	
	
        var url="";
        var officeselected="";
              
        if(document.frmValidationSummaryRep.rad_off[0].checked)   
        	{
        	officeselected=5000;
        		url=url+"&officeselected="+officeselected;
        	}
        
                         if(document.frmValidationSummaryRep.rad_off[1].checked)    
                          //  if(type=='RN')
                            {
                             // alert("inside region");
                            ////////
                            var region="";
                            if(document.frmValidationSummaryRep.chkregion)
                              {
                              
                                        if(document.frmValidationSummaryRep.chkregion.length)
                                        {
                                                  for(i=0;i<document.frmValidationSummaryRep.chkregion.length;i++)
                                                    {
                                                            if(document.frmValidationSummaryRep.chkregion[i].checked==true)
                                                              region= region+document.frmValidationSummaryRep.chkregion[i].value +",";
                                                               
                                                            
                                                    }
                                                    if(region!="")
                                                    {
                                                        region=region.substring(0,region.length-1);
                                                         url=url+"&officeselected="+region;
                                                        // alert(url);
                                                         document.frmValidationSummaryRep.officeselected.value=region;
                                                         //alert(document.frmValidationSummaryRep.officeselected.value);
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Region.');
                                                            document.frmValidationSummaryRep.cmbregion[0].focus(); 
                                                            return;
                                                    }
                                            
                                        }
                                        else{
                                           
                                                if(document.frmValidationSummaryRep.chkregion.checked==true)
                                                {
                                                            region= document.frmValidationSummaryRep.chkregion.value ;
                                                            document.frmValidationSummaryRep.officeselected.value=region;
                                                }
                                                 else
                                                    {
                                                           alert('Select the Region..');
                                                            document.frmValidationSummaryRep.cmbregion.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                 else
                                {
                                       alert('Select the Region...');
                                        document.frmValidationSummaryRep.cmbregion.focus(); 
                                        return ;
                                }    
                            ////
                            
                            }
                            
                          else if(document.frmValidationSummaryRep.rad_off[2].checked) 
                            
                            //else if(type=='CL')
                            {
                            
                            ////////
                            var circle="";
                            if(document.frmValidationSummaryRep.chkcircle)
                              {
                              
                                       if(document.frmValidationSummaryRep.chkcircle.length )
                                        {
                                                
                                                    for(i=0;i<document.frmValidationSummaryRep.chkcircle.length;i++)
                                                    {
                                                            if(document.frmValidationSummaryRep.chkcircle[i].checked==true)
                                                                    circle= circle+document.frmValidationSummaryRep.chkcircle[i].value +",";
                                                                    // alert(circle);  
                                                    }
                                                    if(circle!="")
                                                    {
                                                        circle=circle.substring(0,circle.length-1);
                                                         url=url+"&officeselected="+circle;
                                                         document.frmValidationSummaryRep.officeselected.value=circle;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Circle.');
                                                            document.frmValidationSummaryRep.cmbcircle.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.frmValidationSummaryRep.chkcircle.checked==true)
                                                {
                                                            circle= document.frmValidationSummaryRep.chkcircle.value ;
                                                            document.frmValidationSummaryRep.officeselected.value=circle;
                                                           // alert(circle);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Circle..');
                                                            document.frmValidationSummaryRep.cmbcircle.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Circle...');
                                       try{
                                        document.frmValidationSummaryRep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.frmValidationSummaryRep.cmbregion.focus(); 
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                            
                           else if(document.frmValidationSummaryRep.rad_off[3].checked) 
                           // else  if(type=='DN')
                            {
                           
                            ////////
                            var division="";
                            if(document.frmValidationSummaryRep.chkdivision)
                              {
                             //alert(document.frmValidationSummaryRep.chkregion.length);
                                     if(document.frmValidationSummaryRep.chkdivision.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.frmValidationSummaryRep.chkdivision.length;i++)
                                                {
                                                        if(document.frmValidationSummaryRep.chkdivision[i].checked==true)
                                                                division= division+document.frmValidationSummaryRep.chkdivision[i].value +",";
                                                        
                                                }
                                                
                                                if(division!="")
                                                {
                                                    division=division.substring(0,division.length-1);
                                                     url=url+"&officeselected="+division;
                                                     document.frmValidationSummaryRep.officeselected.value=division;
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the Division.');
                                                        document.frmValidationSummaryRep.cmbdivision.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.frmValidationSummaryRep.chkdivision.checked==true)
                                                {
                                                                division= document.frmValidationSummaryRep.chkdivision.value;
                                                                    //division=division.substring(0,division.length-1);
                                                                 url=url+"&officeselected="+division;
                                                                 document.frmValidationSummaryRep.officeselected.value=division;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the Division..');
                                                        document.frmValidationSummaryRep.cmbdivision.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the Division...');
                                       try{
                                        document.frmValidationSummaryRep.cmbdivision.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.frmValidationSummaryRep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.frmValidationSummaryRep.cmbregion.focus(); 
                                        }
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                        
                        else if(document.frmValidationSummaryRep.rad_off[4].checked) 
                            
                            //else if(type=='CL')
                            {
                            
                            ////////
                            var audit="";
                            if(document.frmValidationSummaryRep.chkaudit)
                              {
                              
                                       if(document.frmValidationSummaryRep.chkaudit.length )
                                        {
                                                
                                                    for(i=0;i<document.frmValidationSummaryRep.chkaudit.length;i++)
                                                    {
                                                            if(document.frmValidationSummaryRep.chkaudit[i].checked==true)
                                                                    audit= audit+document.frmValidationSummaryRep.chkaudit[i].value +",";
                                                                    // alert(circle);  
                                                    }
                                                    if(audit!="")
                                                    {
                                                        audit=audit.substring(0,audit.length-1);
                                                         url=url+"&officeselected="+audit;
                                                         document.frmValidationSummaryRep.officeselected.value=audit;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Audit.');
                                                            document.frmValidationSummaryRep.cmbaudit.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.frmValidationSummaryRep.chkaudit.checked==true)
                                                {
                                                            audit= document.frmValidationSummaryRep.chkaudit.value ;
                                                            document.frmValidationSummaryRep.officeselected.value=audit;
                                                           // alert(circle);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Audit..');
                                                            document.frmValidationSummaryRep.cmbaudit.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Aduit...');
                                       try{
                                        document.frmValidationSummaryRep.cmbaudit.focus(); 
                                        }
                                        catch(e){
                                        document.frmValidationSummaryRep.cmbregion.focus(); 
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                            
                            else if(document.frmValidationSummaryRep.rad_off[5].checked) 
                           // else  if(type=='DN')
                            {
                           
                            ////////
                            var lab="";
                            if(document.frmValidationSummaryRep.chklab)
                              {
                             //alert(document.frmValidationSummaryRep.chkregion.length);
                                     if(document.frmValidationSummaryRep.chklab.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.frmValidationSummaryRep.chklab.length;i++)
                                                {
                                                        if(document.frmValidationSummaryRep.chklab[i].checked==true)
                                                                lab= lab+document.frmValidationSummaryRep.chklab[i].value +",";
                                                        
                                                }
                                                
                                                if(lab!="")
                                                {
                                                    lab=lab.substring(0,lab.length-1);
                                                     url=url+"&officeselected="+lab;
                                                     document.frmValidationSummaryRep.officeselected.value=lab;
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the Lab.');
                                                        document.frmValidationSummaryRep.cmblab.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.frmValidationSummaryRep.chklab.checked==true)
                                                {
                                                                lab= document.frmValidationSummaryRep.chklab.value;
                                                                    //division=division.substring(0,division.length-1);
                                                                 url=url+"&officeselected="+lab;
                                                                 document.frmValidationSummaryRep.officeselected.value=lab;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the Lab..');
                                                        document.frmValidationSummaryRep.cmblab.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the Lab...');
                                       try{
                                        document.frmValidationSummaryRep.cmblab.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.frmValidationSummaryRep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.frmValidationSummaryRep.cmbregion.focus(); 
                                        }
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                        
                    //}
                        
                    
               // }
       // }
       
      
        //select the output type
       
        var fin_year=document.getElementById("fin_year").value;
        var month=document.getElementById("month").value;
   url=url+"&fin_year="+fin_year+"&month="+month;
       //alert(url);           
    document.frmValidationSummaryRep.action="Gpf_Loan_Application_Status_rep_det.jsp?"+url;
    document.frmValidationSummaryRep.submit();
   /* var req=getTransport();
    req.open("POST",url,false);  
    req.send(null);*/

}






//////////////////////////// for next purpose ///////////////////////
///////////////////////  for designation,cadre,rank   ///////////////////////

