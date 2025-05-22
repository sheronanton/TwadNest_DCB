var winemp="";
var winjob="";
var checklist="";

function getxmlhttpObject()
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
function load_status()
{
	
	if(document.request.incom[1].checked==true) 
	{
		document.request.Status_Id.selectedIndex=2;
	}
	if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,600);
       winemp.moveTo(200,200); 
       winemp.focus();
       return ;
    }
    else
    {
        winemp=null;
    }
        
    winemp= window.open("Compassionate_checklist.jsp","mywindow_ESP1","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
    
    winemp.moveTo(250,250);  
    winemp.focus();
		
}


function load_status1()
{
	appl_id=document.request.Appl_id.value;
	
	if(document.request.incom[1].checked==true) 
	{
		document.request.Status_Id.selectedIndex=2;
	}
	if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,600);
       winemp.moveTo(200,200); 
       winemp.focus();
       return ;
    }
    else
    {
        winemp=null;
    }
        
    winemp= window.open("Compassionate_checklist_update.jsp?Appl_id="+appl_id,"mywindow_ESP1","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
    
    winemp.moveTo(250,250);  
    winemp.focus();
		
}
function Applicationpopup()
{
	
	 if (winemp && winemp.open && !winemp.closed) 
	    {
	       winemp.resizeTo(500,600);
	       winemp.moveTo(200,200); 
	       winemp.focus();
	       return ;
	    }
	    else
	    {
	        winemp=null;
	    }
	        
	    winemp= window.open("Compassionate_search.jsp","mywindow_ESP1","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
	    winemp.moveTo(250,250);  
	    winemp.focus();
}
function servicepopup()
{
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,600);
       winemp.moveTo(200,200); 
       winemp.focus();
       return ;
    }
    else
    {
        winemp=null;
    }
        
    winemp= window.open("EmpdthPopup.jsp","mywindow_ESP1","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}
function jobpopup()
{
    if (winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,500);
       winjob.moveTo(250,250); 
       winjob.focus();
       return;
    }
    else
    {
        winjob=null;
    }
        //alert(context);
    winjob= window.open(context+"/org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
    winjob.focus();
    
}
function doParentJob(jobid,deptid)
{
   
    document.request.Last_Office_id.value=jobid;
   
    calls("office");
    return true;
}
function CheckList(checklist)
{
	document.request.checking.value=checklist;
	getchecklist();
}
function doParentEmp(emp)
{
document.request.Emp_id.value=emp;
calls('dispEmp');

}
function doParemp(id)
{
	document.request.Appl_id.value=id;
	calls('load');
}
function Check_empid()
{
	if(document.request.Emp_id.value=="")
	{
		alert("Enter Employee ID");
		document.request.Emp_id.focus();
	}
}
function Check_officeid()
{
	if(document.request.Last_Office_id.value=="")
	{
		alert("Enter Last Worked office id");
		return false;
	}
}
function nullcheck()
{
	
	
	 if(document.request.Appl_Name.value=="")
	{
		alert("Enter the Applicant Name");
		return false;
	}
	 
	else if(document.request.Appl_addr1.value=="" && document.request.Appl_addr2.value=="" && document.request.Appl_addr3.value=="")
	{
		alert("Enter Applicant Address");
		return false;
	}
	else if(document.request.Pincode.value=="")
	{
		
		alert("Enter Applicant Pincode");
		return false;
	}
	else if(document.request.Emp_id.value=="")
	{
		alert("Enter Employee ID");
		return false;
	}
	/*else if(document.request.Appl_addr3.value=="")
	{
		alert("Enter Applicant Address3");
		return false;
	}*/
	
	
	else if(document.request.Emp_Name.value=="")
	{
		alert("Enter Employee Name");
		return false;
	}
	else if(document.request.Desig_at_dth.value=="")
	{
		alert("Enter Designation at the Death");
		return false;
	}
	else if(document.request.Last_Office_id.value=="")
	{
		alert("Enter Last Worked Office");
		return false;
	}
	
	else if(document.request.Emp_Rel.value=="")
	{
		alert("Enter Relation");
		return false;
	}
	else if(document.request.Date_of_Rec.value=="")
	{
		alert("Enter Date of Receipt");
		return false;
	}
	else if(document.request.Appl_DOB.value=="")
	{
		alert("Enter Applicant DOB");
		return false;
	}
	else if(document.request.Appl_Com.value=="")
	{
		alert("Enter Applicant Community");
		return false;
	}
	else if(document.request.Edu_level.value=="")
	{
		alert("Enter Applicant Education Level");
		return false;
	}
	else if(document.request.File_no.value=="")
	{
		alert("Enter File no");
		return false;
	}
	else if(document.request.incom.value=="")
	{
		alert("Enter is completed field");
		return false;
	}
	
	else if(document.request.incom[0].checked==false && document.request.incom[1].checked==false)
		{
		alert("please select Is Complete ");
		return false;
		
		}
	else if(document.request.eli_post[0].checked==true)
   {
		
	    if(document.request.Post_code.selectedIndex==0)
	    	
	   {
		alert("Enter Eligible post code");
		return false;
	    }
    }
	
	
	
	return true;
}

function nullcheck1()
{
	

	
	
	 if(document.request.Appl_Name.value=="")
	{
		alert("Enter the Applicant Name");
		return false;
	}
	 
	else if(document.request.Appl_addr1.value=="" && document.request.Appl_addr2.value=="" && document.request.Appl_addr3.value=="")
	{
		alert("Enter Applicant Address");
		return false;
	}
	else if(document.request.Pincode.value=="")
	{
		
		alert("Enter Applicant Pincode");
		return false;
	}
	
	/*else if(document.request.Appl_addr3.value=="")
	{
		alert("Enter Applicant Address3");
		return false;
	}*/
	
	
	else if(document.request.Emp_Name.value=="")
	{
		alert("Enter Employee Name");
		return false;
	}
	else if(document.request.Desig_at_dth.value=="")
	{
		alert("Enter Designation at the Death");
		return false;
	}
	
	
	else if(document.request.Emp_Rel.value=="")
	{
		alert("Enter Relation");
		return false;
	}
	else if(document.request.Date_of_Rec.value=="")
	{
		alert("Enter Date of Receipt");
		return false;
	}
	else if(document.request.Appl_DOB.value=="")
	{
		alert("Enter Applicant DOB");
		return false;
	}
	else if(document.request.Appl_Com.value=="")
	{
		alert("Enter Applicant Community");
		return false;
	}
	else if(document.request.Edu_level.value=="")
	{
		alert("Enter Applicant Education Level");
		return false;
	}
	else if(document.request.File_no.value=="")
	{
		alert("Enter File no");
		return false;
	}
	else if(document.request.incom.value=="")
	{
		alert("Enter is completed field");
		return false;
	}
	
	else if(document.request.incom[0].checked==false && document.request.incom[1].checked==false)
		{
		alert("please select Is Complete ");
		return false;
		
		}
	else if(document.request.eli_post[0].checked==true)
  {
		
	    if(document.request.Post_code.selectedIndex==0)
	    	
	   {
		alert("Enter Eligible post code");
		return false;
	    }
   }
	
	
	
	return true;

}


function calls(command)
{
	
	 if(command=="Add")
     {
		 if(nullcheck())
			{
			 
			var post_code=0;
     var Appl_Name=document.request.Appl_Name.value;
     var Appl_Ini=document.request.Appl_Ini.value;
     var Appl_addr1=document.request.Appl_addr1.value;
     var Appl_addr2=document.request.Appl_addr2.value;
     var Appl_addr3=document.request.Appl_addr3.value;
     
     var Pincode=document.request.Pincode.value;
     var Emp_id=document.request.Emp_id.value;
     if((Emp_id=='')||(Emp_id=='null'))
    	 Emp_id=0;
     var Emp_Ini=document.request.Emp_Ini.value;
     var Desig_at_dth=document.request.Desig_at_dth.value;
     
     var Last_Office_id=document.request.Last_Office_id.value;
     var Emp_Rel=document.request.Emp_Rel.value;
     var Date_of_Rec=document.request.Date_of_Rec.value;
     var Appl_DOB=document.request.Appl_DOB.value;
     var Appl_Com=document.request.Appl_Com.value;
     var Edu_level=document.request.Edu_level.value;
     
     var File_no=document.request.File_no.value;
     var incom=document.request.incom.value;
     //var Status_Rsn=document.request.Status_Rsn.value;
     var remarks=document.request.remarks.value;
     var Emp_Name=document.request.Emp_Name.value;
     
     if(document.request.incom[0].checked==true) 
    	 incom='Y';
 	 else
 		 incom='N';
      post_code=document.request.Post_code.value;
      if(post_code=='' || post_code=='null' || post_code==null)
 	 {
 	  post_code=0;
 	 }
     var Date_of_dth=document.request.Date_of_dth.value;
     var Last_Office_Name=document.request.Last_Office_Name.value;
     var Status_Id=document.request.Status_Id.value;
     
        url="../../../../../Compassionate_servlet?Command=Add&Emp_id=" + Emp_id+"&Appl_Name="+Appl_Name+"&Appl_addr1="+Appl_addr1+"&Appl_addr2="+Appl_addr2+"&Appl_addr3="+Appl_addr3+"&Pincode="+Pincode+"&Desig_at_dth="+Desig_at_dth;
        url=url+"&Last_Office_id="+Last_Office_id+"&Emp_Rel="+Emp_Rel+"&Date_of_Rec="+Date_of_Rec+"&Appl_DOB="+Appl_DOB+"&Appl_Com="+Appl_Com+"&Edu_level="+Edu_level+"&File_no="+File_no+"&incom="+incom+"&remarks="+remarks+"&Emp_Ini="+Emp_Ini+"&Emp_Name="+Emp_Name+"&post_code="+post_code+"&Date_of_dth="+Date_of_dth+"&Last_Office_Name="+Last_Office_Name+"&Status_Id="+Status_Id+"&Appl_Ini="+Appl_Ini;
       
        checklist=document.getElementById("checking").value;
        if(incom=='Y')
        	checklist=null;
        if((incom=='N')&&(checklist==null))
        	alert("Please Select reason for incompletion");
        else
        	url=url+"&checklist="+checklist;
       // alert(url);
          var req=getxmlhttpObject();
          req.open("GET",url,true); 
          req.onreadystatechange=function()
          {
             processResponse(req);
          };   
          req.send(null);
			}
     }
	 else if(command=="Update")
	     {
			 if(nullcheck1())
				{
				 var post_code=0;
		 var Appl_id=document.request.Appl_id.value;		
	     var Appl_Name=document.request.Appl_Name.value;
	     var Appl_Ini=document.request.Appl_Ini.value;
	     var Appl_addr1=document.request.Appl_addr1.value;
	     var Appl_addr2=document.request.Appl_addr2.value;
	     var Appl_addr3=document.request.Appl_addr3.value;
	     var Pincode=document.request.Pincode.value;
	     var Emp_id=document.request.Emp_id.value;
	     if((Emp_id=='')||(Emp_id=='null'))
	    	 Emp_id=0;
	     
	     var Emp_Ini=document.request.Emp_Ini.value;
	     var Desig_at_dth=document.request.Desig_at_dth.value;
	     var Last_Office_id=document.request.Last_Office_id.value;
	     if(Last_Office_id=='' || Last_Office_id=='null' || Last_Office_id==null)
    	 {
    	 Last_Office_id=0;
    	 }
	     var Emp_Rel=document.request.Emp_Rel.value;
	     var Date_of_Rec=document.request.Date_of_Rec.value;
	     var Appl_DOB=document.request.Appl_DOB.value;
	     var Appl_Com=document.request.Appl_Com.value;
	     var Edu_level=document.request.Edu_level.value;
	     var File_no=document.request.File_no.value;
	     var incom=document.request.incom.value;
	     //var Status_Rsn=document.request.Status_Rsn.value;
	     var remarks=document.request.remarks.value;
	     var Emp_Name=document.request.Emp_Name.value;

	     if(document.request.incom[0].checked==true) 
	    	 incom='Y';
	 	 else
	 		 incom='N';
	      post_code=document.request.Post_code.value;
	      if(post_code=='' || post_code=='null' || post_code==null)
	    	 {
	    	  post_code=0;
	    	 }
	     var Date_of_dth=document.request.Date_of_dth.value;
	     var Last_Office_Name=document.request.Last_Office_Name.value;
	     var Status_Id=document.request.Status_Id.value;
	        url="../../../../../Compassionate_servlet?Command=Update&Emp_id=" + Emp_id+"&Appl_Name="+Appl_Name+"&Appl_addr1="+Appl_addr1+"&Appl_addr2="+Appl_addr2+"&Appl_addr3="+Appl_addr3+"&Pincode="+Pincode+"&Desig_at_dth="+Desig_at_dth;
	        url=url+"&Last_Office_id="+Last_Office_id+"&Emp_Rel="+Emp_Rel+"&Date_of_Rec="+Date_of_Rec+"&Appl_DOB="+Appl_DOB+"&Appl_Com="+Appl_Com+"&Edu_level="+Edu_level+"&File_no="+File_no+"&incom="+incom+"&remarks="+remarks+"&Emp_Ini="+Emp_Ini+"&Emp_Name="+Emp_Name+"&post_code="+post_code+"&Date_of_dth="+Date_of_dth+"&Last_Office_Name="+Last_Office_Name+"&Status_Id="+Status_Id+"&Appl_Ini="+Appl_Ini+"&Appl_no="+Appl_id;
	       
	        checklist=document.getElementById("checking").value;
	        if(incom=='Y')
	        	checklist='';
	        if((incom=='N')&&(checklist==null))
	        	alert("Please Select reason for incompletion");
	        else
	        	url=url+"&checklist="+checklist;
	      // alert(url)
	          var req=getxmlhttpObject();
	          req.open("GET",url,true); 
	          req.onreadystatechange=function()
	          {
	             processResponse(req);
	          };   
	          req.send(null);
				}
	     }
	 else if(command=='dispEmp')
	 {
		 var Emp_id=document.request.Emp_id.value;
		 url="../../../../../Compassionate_servlet?Command=dispEmp&Emp_id=" + Emp_id;
		//alert("url"+url);
		 var req=getxmlhttpObject();
         req.open("GET",url,true); 
         req.onreadystatechange=function()
         {
            processResponse(req);
         } ;  
         req.send(null);
	 }
	 else if(command=="office")
	  {
		
	            var oid=document.request.Last_Office_id.value;
	           
	            var url="../../../../../Create_Transfer_OrderServ?Command=office&oid="+oid;
	           
	            var req=getxmlhttpObject();
	            req.open("GET",url,true); 
	            req.onreadystatechange=function()
	            {
	            	if(req.readyState==4)
	                {
	                  if(req.status==200)
	                  {
	            
	            	 var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	            	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	            	  
	            	  if(flag=="success")
	            	  {
	            		  document.request.Last_Office_Name.value= baseResponse.getElementsByTagName("oname")[0].firstChild.nodeValue;

	            	  return true;
	            	  }
	            	  else
	            	  {
	            	     alert("Office Id '"+oid+"' doesn't Exists");
	            	     
	            	     document.request.Last_Office_Name.value="";
	            	     document.request.Last_Office_id.value="";
	            	                return false;

	            	  }
	                  }
	                }

	            } ;
	            req.send(null);
					  
	                    
	   }
	 else if(command=="checklist")
	 {
		 
		 
                   for(i=0;i<document.request1.check_list.length;i++)
                     {
                             if(document.request1.check_list[i].checked==true)
                               checklist= checklist+document.request1.check_list[i].value +",";
                                
                             
                     }
                   //document.request.remarks.value="ghfg";
                
                 //document.getElementById("checking").value=checklist;
                 document.request1.textaa.value=checklist;
                 document.request.checking.value=checklist;
                
	 }
	 else if(command=="seniority")
	 {
		 var exsit=document.getElementById("exsisting").value;
		 var newss=document.getElementById("new").value;
		 //alert(newss)
		 url="../../../../../Compassionate_servlet?Command=seniority&exsit="+exsit+"&newss="+newss;
			//alert(url);
		 var req=getxmlhttpObject();
         req.open("GET",url,true); 
         req.onreadystatechange=function()
         {
            processResponse(req);
         } ;  
         req.send(null);
                  
                
	 }
	 else if(command=="load")
	 {
		 var Appl_id=document.request.Appl_id.value; 
		 url="../../../../../Compassionate_servlet?Command=load&Appl_id="+Appl_id;
			
		 var req=getxmlhttpObject();
         req.open("GET",url,true); 
         req.onreadystatechange=function()
         {
            processResponse(req);
         } ;  
         req.send(null);
                  
                
	 }
	 
}
function getreason()
{
	 var Appl_id=document.request.Appl_id.value; 
	 url="../../../../../Compassionate_servlet?Command=getreason&Appl_id="+Appl_id;
		
	 var req=getxmlhttpObject();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
       processResponse(req);
    } ;  
    req.send(null);
             
           
}
function processResponse(req)
{   
  if(req.readyState==4)
    {
      if(req.status==200)
      {       
     //alert(req.responseText);
          var baseResponse=req.responseXML.getElementsByTagName("response")[0];
          var tagCommand=baseResponse.getElementsByTagName("command")[0];
          var command=tagCommand.firstChild.nodeValue; 
          if(command=="dispEmp")
          {
        	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              if(flag=="success")
              { 
                 //alert("success");
                    var empname=baseResponse.getElementsByTagName("name")[0].firstChild.nodeValue;
                    var empintial=baseResponse.getElementsByTagName("initial")[0].firstChild.nodeValue;
                    var Dateofdth=baseResponse.getElementsByTagName("dth")[0].firstChild.nodeValue;
                    var desing_dth=baseResponse.getElementsByTagName("des")[0].firstChild.nodeValue; 
                    
                    if(empname=="null" || empname==null || empname=="")
                    	{
                    	empname="";
                    	}
                    
                    if(empintial==null || empintial=="null" || empintial=="")
                    	{
                    	empintial="";
                    	}
                     if(Dateofdth=="" || Dateofdth=="null" || Dateofdth==null)
                    	 {
                    	 Dateofdth="";
                    	 }
                      if(desing_dth==null || desing_dth == "null" || desing_dth=="")
                    	  {
                    	  desing_dth="";
                    	  }
                      document.request.Emp_Name.value=empname;
                      document.request.Emp_Ini.value=empintial;
                      document.request.Date_of_dth.value=Dateofdth;
                      document.request.Desig_at_dth.value=desing_dth;
               }
              else if(flag=="status")
            	  {
            	  alert("This Employee Status is not Death!");
            	  document.request.Emp_id.value="";
            	  }
              else if(flag=="notexist")
        	  {
        	  alert("This Employee id does not Exsit!");
        	  document.request.Emp_id.value="";
        	  }
              
          }
          else if(command=="sessionout")
        {
            alert('Session is closed');
            try{
            //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
            }catch(e){}
            self.close();
            return;
        }
          else if(command=="Add")
          {
        	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              if(flag=="success")
              {
            	  alert("inserted Sucessfully");
            	  clearfields();
              }
              else
            	  alert("failure");
          }
          else if(command=="update")
          {
        	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              if(flag=="success")
              {
            	  alert("Updated Sucessfully");
            	  clearfields();
            	  document.request.Appl_id.value="";
              }
              else
            	  alert("failure");
          }
          else if(command=="seniority")
          {
        	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              if(flag=="success")
              {
            	  alert("Sucessfully Updated");
            	  
              }
              else
            	  alert("failure");
          }
          else if(command=="load")
          {
        	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              if(flag=="success")
              {
            	  
            	    var Appl_Name=baseResponse.getElementsByTagName("APPLICANT_NAME")[0].firstChild.nodeValue;
            	     var Appl_Ini=baseResponse.getElementsByTagName("Applicant_Initial")[0].firstChild.nodeValue;
            	     if((Appl_Ini==null||Appl_Ini=='null'))
            	    	 Appl_Ini='';
            	     var Appl_addr1=baseResponse.getElementsByTagName("APPLICANT_ADDRESS1")[0].firstChild.nodeValue;
            	     if((Appl_addr1=='null')||(Appl_addr1==null))
            	    	 Appl_addr1='';
            	     
            	     var Appl_addr2=baseResponse.getElementsByTagName("APPLICANT_ADDRESS2")[0].firstChild.nodeValue;
            	    if((Appl_addr2=='null')||(Appl_addr2==null))
            	    Appl_addr2='';
            	     var Appl_addr3=baseResponse.getElementsByTagName("APPLICANT_ADDRESS3")[0].firstChild.nodeValue;
            	     if((Appl_addr3=='null')||(Appl_addr3==null))
             	    Appl_addr3='';
            	     var Pincode=baseResponse.getElementsByTagName("APPLICANT_PINCODE")[0].firstChild.nodeValue;
            	     var Emp_id=baseResponse.getElementsByTagName("EMPLOYEE_ID")[0].firstChild.nodeValue;
            	     if((Emp_id=='0'))
            	    	 Emp_id='';
            	     var Emp_Ini=baseResponse.getElementsByTagName("EMPLOYEE_INITIAL")[0].firstChild.nodeValue;
            	     if((Emp_Ini==null||Emp_Ini=='null'))
            	    	 Emp_Ini='';
            	     
            	     var Desig_at_dth=baseResponse.getElementsByTagName("Designation_At_Dth")[0].firstChild.nodeValue;
            	     
            	     if((Desig_at_dth==null||Emp_Ini=='null'))
            	    	 Desig_at_dth='';
            	     
            	     var Last_Office_id=baseResponse.getElementsByTagName("LAST_OFFICE_ID")[0].firstChild.nodeValue;
            	     
            	     if((Last_Office_id==null||Last_Office_id=='null'))
            	    	 Last_Office_id='';
            	              	     
            	     var Emp_Rel=baseResponse.getElementsByTagName("Emp_Relation_Id")[0].firstChild.nodeValue;
            	    
            	     if((Emp_Rel==null||Emp_Rel=='null'))
            	    	 Emp_Rel='';
            	     
            	     var Date_of_Rec=baseResponse.getElementsByTagName("Date_Of_Receipt")[0].firstChild.nodeValue;
            	     
            	     if((Date_of_Rec==null||Date_of_Rec=='null'))
            	    	 Date_of_Rec='';
            	     
            	     var Appl_DOB=baseResponse.getElementsByTagName("DOB_OF_APPLICANT")[0].firstChild.nodeValue;
                     
            	     if((Appl_DOB==null||Appl_DOB=='null'))
            	    	 Appl_DOB='';
            	     
            	     var Appl_Com=baseResponse.getElementsByTagName("COMMUNITY_CODE")[0].firstChild.nodeValue;
            	    
            	     if((Appl_Com==null||Appl_Com=='null'))
            	    	 Appl_Com='';
            	     
            	     var Edu_level=baseResponse.getElementsByTagName("EDU_LEVEL")[0].firstChild.nodeValue;
            	     
            	     if((Edu_level==null||Edu_level=='null'))
            	    	 Edu_level='';
            	     
            	     var File_no=baseResponse.getElementsByTagName("FILE_REF_NO")[0].firstChild.nodeValue;
            
            	     if((Edu_level==null||Edu_level=='null'))
            	    	 Edu_level='';
            	     
            	     var incom=baseResponse.getElementsByTagName("IS_INCOMPLETE")[0].firstChild.nodeValue;
            	                 	     
            	     /*var Status_Rsn=baseResponse.getElementsByTagName("STATUS_REASON")[0].firstChild.nodeValue;
            	    
            	     if((Status_Rsn==null||Status_Rsn=='null'))
            	    	 Status_Rsn='';*/
            	     
            	     var remarks=baseResponse.getElementsByTagName("REMARKS")[0].firstChild.nodeValue;
            	    // var REASON_ID=baseResponse.getElementsByTagName("REASON_ID")[0].firstChild.nodeValue;
            	     //var REASON_DESC=baseResponse.getElementsByTagName("REASON_DESC")[0].firstChild.nodeValue;
            	     
            	     if(remarks=='null'||remarks==null)
            	    	 remarks="";
            	     
            	     var Emp_Name=baseResponse.getElementsByTagName("EMPLOYEE_NAME")[0].firstChild.nodeValue;
            	     
                       if(incom=='N')
                       {  
            	          document.request.incom[1].checked=true;
                       }
                       else
                       {
                    	   document.request.incom[0].checked=true;
                    	  
                    	   
                       }
            	 	 
            	     var post_code=baseResponse.getElementsByTagName("ELIGIBLE_POST_CODE")[0].firstChild.nodeValue;
            	     
            	     var Date_of_dth=baseResponse.getElementsByTagName("DATE_OF_DEATH")[0].firstChild.nodeValue;
            	     var Last_Office_Name=baseResponse.getElementsByTagName("LAST_OFFICE_NAME")[0].firstChild.nodeValue;
            	     
            	     if((Last_Office_Name==null||Last_Office_Name=='null'))
            	    	 Last_Office_Name='';
            	     
            	     var Status_Id=baseResponse.getElementsByTagName("APPL_STATUS_ID")[0].firstChild.nodeValue;
            	     
            	     document.request.Appl_Name.value=Appl_Name;
            	     document.request.Appl_Ini.value=Appl_Ini;
            	     document.request.Appl_addr1.value=Appl_addr1;
            	     document.request.Appl_addr2.value=Appl_addr2;
            	     document.request.Appl_addr3.value=Appl_addr3;
            	     document.request.Pincode.value=Pincode;
            	     document.request.Emp_id.value=Emp_id;
            	    
            	     document.request.Emp_Ini.value=Emp_Ini;
            	     document.request.Desig_at_dth.value=Desig_at_dth;
            	     document.request.Last_Office_id.value=Last_Office_id;
            	     document.request.Emp_Rel.value=Emp_Rel;
            	     document.request.Date_of_Rec.value=Date_of_Rec;
            	     document.request.Appl_DOB.value=Appl_DOB;
            	     document.request.Appl_Com.value=Appl_Com;
            	     document.request.Edu_level.value=Edu_level;
            	     document.request.File_no.value=File_no;
            	    
            	     //document.request.Status_Rsn.value=Status_Rsn;
            	     document.request.remarks.value=remarks;
            	     document.request.Emp_Name.value=Emp_Name;
            	     if(post_code==0)
            	    	 {
            	    	 
            	    	 document.getElementById("pc_code").style.display='none';
            	    	 document.request.eli_post[1].checked=true;
            	    	 }
            	     else
            	    	 {
            	    	 
            	    	 document.request.eli_post[0].checked=true;
            	    	 document.getElementById("pc_code").style.display='';
            	        document.request.Post_code.value=post_code;
            	    	 }
            	     document.request.Date_of_dth.value=Date_of_dth;
            	     document.request.Last_Office_Name.value=Last_Office_Name;
            	     document.request.Status_Id.value=Status_Id;
            	     
            	     getreason();
            	     
              }
              else if(flag=="Norecord")
            	  {
            	  alert("There is No Record for This Application Id");
            	  document.getElementById("Appl_id").value="";
            	  document.getElementById("Appl_id").focus();
            	  clearfields();
            	  
            	  }
              
          }
          else if(command=="getreason")
        	  {
        	   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              
        	   if(flag=="success")
                 {
                	 var len = baseResponse.getElementsByTagName("tot").length;
                	 var res="";
                	 var res_id="";
                	 var j=0;
		            	for ( var i = 0; i <len; i++) 
		            	{
		            		j++
		            		
		            		var REASON_ID=baseResponse.getElementsByTagName("REASON_ID")[i].firstChild.nodeValue;
		            		var REASON_DESC=baseResponse.getElementsByTagName("REASON_DESC")[i].firstChild.nodeValue;
		            		
		            		if(REASON_ID=="null" || REASON_ID=="null" || REASON_ID==null || REASON_ID==0)
		            			{
		            			REASON_ID="";
		            			}
		            		if(REASON_DESC=="null" || REASON_DESC=="null" || REASON_DESC==null)
	            			{
		            			REASON_DESC="";
	            			}
		            		
		            		
		            		var res=res+ j+"-"+REASON_DESC;
		            	     
		            		document.getElementById("incom_status").value=res;
		            		
  		            	}
                	 
                 }
        	  }
          else if(command=="getchecklist")
          {
       	   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
           
    	   if(flag=="success")
             {
            	 var len = baseResponse.getElementsByTagName("tot").length;
            	 var res="";
            	 var res_id="";
            	 var j=0;
	            	for ( var i = 0; i <len; i++) 
	            	{
	            		j++;
	            		var REASON_ID=baseResponse.getElementsByTagName("REASON_ID")[i].firstChild.nodeValue;
	            		var REASON_DESC=baseResponse.getElementsByTagName("REASON_DESC")[i].firstChild.nodeValue;
	            		
	            		if(REASON_ID=="null" || REASON_ID=="null" || REASON_ID==null || REASON_ID==0)
	            			{
	            			REASON_ID="";
	            			}
	            		if(REASON_DESC=="null" || REASON_DESC=="null" || REASON_DESC==null)
            			{
	            			REASON_DESC="";
            			}
	            		
	            		
	            		var res=res+ j+"-"+REASON_DESC;
	            	     
	            		document.getElementById("incom_status").value=res;
	            		
		            	}
            	 
             }
    	  }
          
          
      }
    }
}

function CompareDate(fdate,tdate)
{

	var fromDate=document.getElementById(fdate).value;
	var toDate=document.getElementById(tdate).value;
	//alert(fromDate);
     var today = new Date();
    today_month=today.getMonth();
    today_day=today.getDay();
    today_year=today.getYear();
	//alert(today_day+today_month+today_year);
	var ret=true;
	if(fromDate =="" || toDate == "")
	{
	}
	else
	{
		var fret=check_dateformat(document.getElementById(fdate));
		var tret=check_dateformat(document.getElementById(tdate));
		if(fret==true && tret==true)
		{
	var f_date =new Array();
	var t_date =new Array();
	
	f_date=fromDate.split("/");
	t_date=toDate.split("/");
	
	var fromYear=0,toYear=0,fromMonth=0,toMonth=0,fromDay=0,toDay=0;
	
	if(f_date[1]=='08' || f_date[1] =='8')
		f_date[1]="08";
	
	if(f_date[1]=='09' || f_date[1] =='9')
		f_date[1]="09";
	
	if(f_date[0]=='08' || f_date[0] =='8')
		f_date[0]="08";
	
	if(f_date[0]=='09' || f_date[0] =='9')
		f_date[0]="09";
	
	if(t_date[1]=='08' || t_date[1] =='8')
		t_date[1]="08";
	
	if(t_date[1]=='09' || t_date[1] =='9')
		t_date[1]="09";
	
	if(t_date[0]=='08' || t_date[0] =='8')
		t_date[0]="08";
	
	if(t_date[0]=='09' || t_date[0] =='9')
		t_date[0]="09";
	
	fromYear=parseInt(f_date[2]);
	fromMonth=parseInt(f_date[1]);
	fromDay=parseInt(f_date[0]);
	
	toYear=parseInt(t_date[2]);
	toMonth=parseInt(t_date[1]);
	toDay=parseInt(t_date[0]);
	
	if(fromYear>toYear)
	{
		ret= false;
	}
	else if(fromYear < toYear)
	{
		ret= true;
	}
	else if(fromMonth > toMonth)
	{
		ret= false;
		
	}
	else if(fromMonth < toMonth)
	{
		ret= true;
		
	}
	else if(fromDay < toDay)
	{
		ret= true;
		
	}
	else if(fromDay >= toDay)
	{
		ret= false;
		
	}
	
	}
	}
	
	if(ret==false)
	{
		alert("From Date Should be less than TO Date");
		document.getElementById(fdate).value="";
		document.getElementById(tdate).value="";
		return false;
	}
	else
	{
		return true;
	}
	
}
function clearfields()
{
	
	document.request.Appl_Name.value="";
    document.request.Appl_Ini.value="";
    document.request.Appl_addr1.value="";
    document.request.Appl_addr2.value="";
    document.request.Appl_addr3.value="";
    document.request.Pincode.value="";
    document.request.Emp_id.value="";
    
   
    document.request.Emp_Ini.value="";
    //document.request.Date_of_dth.value="";
    document.request.Desig_at_dth.value="";
    document.request.Last_Office_id.value="";
    document.request.Emp_Rel.value="";
    document.request.Date_of_Rec.value="";
    document.request.Appl_DOB.value="";
    document.request.Appl_Com.value="";
    document.request.Edu_level.value="";
    document.request.File_no.value="";
   
   // document.request.Status_Rsn.value="";
    
    document.request.remarks.value="";
    document.request.Emp_Name.value="";
    document.request.Post_code.value="";
    document.request.Date_of_dth.value="";
    document.request.Last_Office_Name.value="";
    document.request.Status_Id.selectedIndex=0;
    document.request.incom_status.value="";
}
function Compare(fdate,tdate)
{
	
	var fromDate=document.getElementById(fdate).value;
	var toDate=document.getElementById(tdate).value;
	
	
	
	
	var ret=true;
	if(fromDate =="" || toDate == "")
	{
	}
	else
	{
		
		var fret=check_dateformat(document.getElementById(fdate));
		
		var tret=check_dateformat(document.getElementById(tdate));
		
		if(fret==true && tret==true)
		{
			
	var f_date =new Array();
	var t_date =new Array();
	
	f_date=fromDate.split("/");
	t_date=toDate.split("/");
	
	var fromYear=0,toYear=0,fromMonth=0,toMonth=0,fromDay=0,toDay=0;
	
	if(f_date[1]=='08' || f_date[1] =='8')
		f_date[1]="08";
	
	if(f_date[1]=='09' || f_date[1] =='9')
		f_date[1]="09";
	
	if(f_date[0]=='08' || f_date[0] =='8')
		f_date[0]="08";
	
	if(f_date[0]=='09' || f_date[0] =='9')
		f_date[0]="09";
	
	if(t_date[1]=='08' || t_date[1] =='8')
		t_date[1]="08";
	
	if(t_date[1]=='09' || t_date[1] =='9')
		t_date[1]="09";
	
	if(t_date[0]=='08' || t_date[0] =='8')
		t_date[0]="08";
	
	if(t_date[0]=='09' || t_date[0] =='9')
		t_date[0]="09";
	
	fromYear=parseInt(f_date[2],10);
	fromMonth=parseInt(f_date[1],10);
	fromDay=parseInt(f_date[0],10);
	
	toYear=parseInt(t_date[2],10);
	toMonth=parseInt(t_date[1],10);
	toDay=parseInt(t_date[0],10);
	
	if(fromYear>toYear)
	{
		ret= false;
	}
	else if(fromYear < toYear)
	{
		ret= true;
	}
	else if(fromMonth > toMonth)
	{
		ret= false;
		
	}
	else if(fromMonth < toMonth)
	{
		ret= true;
		
	}
	else if(fromDay < toDay)
	{
		ret= true;
		
	}
	else if(fromDay >= toDay)
	{
		ret= false;
		
	}
	
	}
	}
	
	if(ret==false)
	{
		alert("Date of death Should be less than Date of Receipt Date");
		//document.getElementById(fdate).value="";
		document.getElementById(tdate).value="";
		return false;
	}
	else
	{
		return true;
	}
	
}
function Comp(fdate,tdate)
{
	
	var fromDate=document.getElementById(fdate).value;
	var toDate=document.getElementById(tdate).value;
	
	
	
	
	var ret=true;
	if(fromDate =="" || toDate == "")
	{
	}
	else
	{
		
		var fret=check_dateformat(document.getElementById(fdate));
		
		var tret=check_dateformat(document.getElementById(tdate));
		
		if(fret==true && tret==true)
		{
			
	var f_date =new Array();
	var t_date =new Array();
	
	f_date=fromDate.split("/");
	t_date=toDate.split("/");
	
	var fromYear=0,toYear=0,fromMonth=0,toMonth=0,fromDay=0,toDay=0;
	
	if(f_date[1]=='08' || f_date[1] =='8')
		f_date[1]="08";
	
	if(f_date[1]=='09' || f_date[1] =='9')
		f_date[1]="09";
	
	if(f_date[0]=='08' || f_date[0] =='8')
		f_date[0]="08";
	
	if(f_date[0]=='09' || f_date[0] =='9')
		f_date[0]="09";
	
	if(t_date[1]=='08' || t_date[1] =='8')
		t_date[1]="08";
	
	if(t_date[1]=='09' || t_date[1] =='9')
		t_date[1]="09";
	
	if(t_date[0]=='08' || t_date[0] =='8')
		t_date[0]="08";
	
	if(t_date[0]=='09' || t_date[0] =='9')
		t_date[0]="09";
	
	fromYear=parseInt(f_date[2],10);
	fromMonth=parseInt(f_date[1],10);
	fromDay=parseInt(f_date[0],10);
	
	toYear=parseInt(t_date[2],10);
	toMonth=parseInt(t_date[1],10);
	toDay=parseInt(t_date[0],10);
	
	if(fromYear>toYear)
	{
		ret= false;
	}
	else if(fromYear < toYear)
	{
		ret= true;
	}
	else if(fromMonth > toMonth)
	{
		ret= false;
		
	}
	else if(fromMonth < toMonth)
	{
		ret= true;
		
	}
	else if(fromDay < toDay)
	{
		ret= true;
		
	}
	else if(fromDay >= toDay)
	{
		ret= false;
		
	}
	
	}
	}
	
	if(ret==false)
	{
		alert(" DOB of Applicant Should be less than Date of death");
		document.getElementById(fdate).value="";
		//document.getElementById(tdate).value="";
		return false;
	}
	else
	{
		return true;
		//futuredate2();
	}
	
}
function check_dateformat(field)
{
	//alert("jai r")
	var arr=new Array();
	
	var field_value=field.value;
	field_value=field_value.trim();

	if(field_value=="")
	{
	}
	else
	{

		
	arr=field_value.split("/");
	
	//alert(arr.length);
	if(arr.length==3)
	{
		
		var ret=check_validdate(arr[0],arr[1],arr[2]);
		
		if(ret==false)
		{
			alert("Invalid Date.");
			field.value="";
			return false;
		}
		else
		{
			return true;
		}
	}
	else
	{
		alert("Date format Should be DD/MM/YYYY");
		field.value="";
		return false;
	}
	
	}
	
}
function check_validdate(Day,Mn,Yr){
    var DateVal = Mn + "/" + Day + "/" + Yr;
    var dt = new Date(DateVal);
    
    if(parseInt(Yr)<=1900 || parseInt(Yr)>2100)
	{
		return false;
	}
	else if(dt.getDate()!=Day)
	{
       return(false);
        
	}
    else if(dt.getMonth()!=Mn-1)
    {        
        return(false);
       
    }
    else if(dt.getFullYear()!=Yr)
    {
    	return(false);
    }
    else
    {
    	return true;
    }

 }
function futuredate()
{
	var Date_of_Rec=document.getElementById("Date_of_Rec").value;
	
	var fromYear=0,toYear=0,fromMonth=0,toMonth=0,fromDay=0,toDay=0;
	
	var currentTime = new Date();
	 toMonth = currentTime.getMonth() + 1;
	 toDay = currentTime.getDate();
	 toYear = currentTime.getFullYear();
	var t_date=toMonth + "/" + toDay + "/" + toYear;
	
	
	var d_date=Date_of_Rec.split("/");
	 t_date=t_date.split("/");
	
	if(d_date[1]=='08' || d_date[1] =='8')
		d_date[1]="08";
	
	if(d_date[1]=='09' || d_date[1] =='9')
		d_date[1]="09";
	
	if(d_date[0]=='08' || d_date[0] =='8')
		d_date[0]="08";
	
	if(d_date[0]=='09' || d_date[0] =='9')
		d_date[0]="09";
	
	if(t_date[1]=='08' || t_date[1] =='8')
		t_date[1]="08";
	
	if(t_date[1]=='09' || t_date[1] =='9')
		t_date[1]="09";
	
	if(t_date[0]=='08' || t_date[0] =='8')
		t_date[0]="08";
	
	if(t_date[0]=='09' || t_date[0] =='9')
		t_date[0]="09";
	
	fromYear=parseInt(d_date[2],10);
	fromMonth=parseInt(d_date[1],10);
	fromDay=parseInt(d_date[0],10);
	
	toYear=parseInt(t_date[2],10);
	toMonth=parseInt(t_date[1],10);
	toDay=parseInt(t_date[0],10);
	
	
	
	
	
	if(fromYear>toYear)
	{
		ret= false;
	}
	else if(fromYear < toYear)
	{
		ret= true;
	}
	else if(fromMonth > toMonth)
	{
		ret= false;
		
	}
	else if(fromMonth < toMonth)
	{
		ret= true;
		
	}
	else if(fromDay < toDay)
	{
		ret= true;
		
	}
	else if(fromDay >= toDay)
	{
		ret= false;
		
	}
	
	
	if(ret==false)
	{
		alert("  Date of Receipt Date  Should be less than Future Date");
		//document.getElementById(fdate).value="";
		document.getElementById("Date_of_Rec").value="";
		return false;
	}
	else
	{
		return true;
	}
	
	
	
	
}
function futuredate1()
{

	var Date_of_dth=document.getElementById("Date_of_dth").value;
	
	var fromYear=0,toYear=0,fromMonth=0,toMonth=0,fromDay=0,toDay=0;
	
	var currentTime = new Date();
	 toMonth = currentTime.getMonth() + 1;
	 toDay = currentTime.getDate();
	 toYear = currentTime.getFullYear();
	var t_date=toMonth + "/" + toDay + "/" + toYear;
	
	
	var d_date=Date_of_dth.split("/");
	 t_date=t_date.split("/");
	
	if(d_date[1]=='08' || d_date[1] =='8')
		d_date[1]="08";
	
	if(d_date[1]=='09' || d_date[1] =='9')
		d_date[1]="09";
	
	if(d_date[0]=='08' || d_date[0] =='8')
		d_date[0]="08";
	
	if(d_date[0]=='09' || d_date[0] =='9')
		d_date[0]="09";
	
	if(t_date[1]=='08' || t_date[1] =='8')
		t_date[1]="08";
	
	if(t_date[1]=='09' || t_date[1] =='9')
		t_date[1]="09";
	
	if(t_date[0]=='08' || t_date[0] =='8')
		t_date[0]="08";
	
	if(t_date[0]=='09' || t_date[0] =='9')
		t_date[0]="09";
	
	fromYear=parseInt(d_date[2],10);
	fromMonth=parseInt(d_date[1],10);
	fromDay=parseInt(d_date[0],10);
	
	toYear=parseInt(t_date[2],10);
	toMonth=parseInt(t_date[1],10);
	toDay=parseInt(t_date[0],10);
	
	
	
	
	
	if(fromYear>toYear)
	{
		ret= false;
	}
	else if(fromYear < toYear)
	{
		ret= true;
	}
	else if(fromMonth > toMonth)
	{
		ret= false;
		
	}
	else if(fromMonth < toMonth)
	{
		ret= true;
		
	}
	else if(fromDay < toDay)
	{
		ret= true;
		
	}
	else if(fromDay >= toDay)
	{
		ret= false;
		
	}
	
	
	if(ret==false)
	{
		alert("  Date of Death Should be less than Future Date");
		//document.getElementById(fdate).value="";
		document.getElementById("Date_of_dth").value="";
		return false;
	}
	else
	{
		return true;
	}
	
	
	
	

}
function futuredate2()
{


	var Appl_DOB=document.getElementById("Appl_DOB").value;
	
	var fromYear=0,toYear=0,fromMonth=0,toMonth=0,fromDay=0,toDay=0;
	
	var currentTime = new Date();
	 toMonth = currentTime.getMonth() + 1;
	 toDay = currentTime.getDate();
	 toYear = currentTime.getFullYear();
	var t_date=toMonth + "/" + toDay + "/" + toYear;
	
	
	var d_date=Appl_DOB.split("/");
	 t_date=t_date.split("/");
	
	if(d_date[1]=='08' || d_date[1] =='8')
		d_date[1]="08";
	
	if(d_date[1]=='09' || d_date[1] =='9')
		d_date[1]="09";
	
	if(d_date[0]=='08' || d_date[0] =='8')
		d_date[0]="08";
	
	if(d_date[0]=='09' || d_date[0] =='9')
		d_date[0]="09";
	
	if(t_date[1]=='08' || t_date[1] =='8')
		t_date[1]="08";
	
	if(t_date[1]=='09' || t_date[1] =='9')
		t_date[1]="09";
	
	if(t_date[0]=='08' || t_date[0] =='8')
		t_date[0]="08";
	
	if(t_date[0]=='09' || t_date[0] =='9')
		t_date[0]="09";
	
	fromYear=parseInt(d_date[2],10);
	fromMonth=parseInt(d_date[1],10);
	fromDay=parseInt(d_date[0],10);
	
	toYear=parseInt(t_date[2],10);
	toMonth=parseInt(t_date[1],10);
	toDay=parseInt(t_date[0],10);
	
	
	
	
	
	if(fromYear>toYear)
	{
		ret= false;
	}
	else if(fromYear < toYear)
	{
		ret= true;
	}
	else if(fromMonth > toMonth)
	{
		ret= false;
		
	}
	else if(fromMonth < toMonth)
	{
		ret= true;
		
	}
	else if(fromDay < toDay)
	{
		ret= true;
		
	}
	else if(fromDay >= toDay)
	{
		ret= false;
		
	}
	
	
	if(ret==false)
	{
		alert("  Date of Death Should be less than Future Date");
		//document.getElementById(fdate).value="";
		document.getElementById("Appl_DOB").value="";
		return false;
	}
	else
	{
		return true;
	}
	
	
	
	



}
function show()
{
	document.getElementById("is_com").style.display='';
}
function hide()
{
	document.getElementById("is_com").style.display='none';
}
function show1()
{
	document.getElementById("pc_code").style.display='';
}
function hide1()
{
	document.getElementById("pc_code").style.display='none';
}
function getchecklist()
{
	
var chklist=document.getElementById("checking").value;	
chklist=chklist.substring(0,chklist.length-1);
url="../../../../../Compassionate_servlet?Command=getchecklist&chklist="+chklist;
//alert(url)
var req=getxmlhttpObject();
req.open("GET",url,true); 
req.onreadystatechange=function()
{
   processResponse(req);
};
req.send(null);
}