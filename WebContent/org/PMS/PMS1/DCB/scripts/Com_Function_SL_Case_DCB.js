/////////////////////////////////////////////   doFunction()  /////////////////////////////////////////////////////

isMan={
             account_head_status : false
          }

function handleResponse2(req)
{  
    if(req.readyState==4)
    { 
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            
             
            	sch_code(baseResponse);
            
        }
    }
}
function handleResponse3(req)
{  
    if(req.readyState==4)
    { 
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            
             
            	sch_code(baseResponse,"2");
            
        }
    }
}
function sch_code(bR,code)
{
	if (code=="2")
	{
		var ACCOUNT_HEAD_DESC=bR.getElementsByTagName("ACCOUNT_HEAD_DESC")[0].firstChild.nodeValue;
		if (ACCOUNT_HEAD_DESC==0)
		{
				alert("Not Valid  Account Head")
				document.getElementById("txtAcc_HeadCode").value="";
				document.getElementById("txtAcc_HeadDesc").value="";
	}
			else
			{
				document.getElementById("txtAcc_HeadDesc").value=ACCOUNT_HEAD_DESC
			}
 	}
	else
	{
	var len=bR.getElementsByTagName("sno").length;
	var status=bR.getElementsByTagName("status")[0].firstChild.nodeValue;
	if (len==0)
		alert(status+"\n-------------------------------")
		for (i=0;i<len;i++)
		{
			var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
		    var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
		    addOption(document.getElementById("sch_code"),name,sno)
	    }
	}
}
function doFunction(Command,param)
{    
	document.getElementById("sch_code").options.length = 0;
	 
   try
   {
    var addtional_field_value;
    
    
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    
        if (Command=="sch_type_load")
        {
            var cmbSL_Code=document.getElementById("cmbSL_Code");   

        	 var url="../../../../../Dcb_receipt_alais?command=data&input_value="+cmbSL_Code.value;
        	 
        	 var req=getTransport();
             req.open("GET",url,true); 
             req.onreadystatechange=function()
             {
            	 handleResponse2(req);
             }   
                     req.send(null);
        }
        if (Command=="AC_desc")
        {
            var cmbSL_Code=document.getElementById("txtAcc_HeadCode")			;   

        	 var url="../../../../../Dcb_receipt_alais?command="+Command+"&ACCOUNT_HEAD_CODE="+cmbSL_Code.value;
        	 
        	 var req=getTransport();
             req.open("GET",url,true); 
             req.onreadystatechange=function()
             {
            	 handleResponse3(req);
             }   
                     req.send(null);
        }
        	
        
        if(Command=="checkCode")
        {  
             //Reset isMan.account_head_status
             isMan.account_head_status = false;
    
             var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
             var cmbSL_Code=document.getElementById("cmbSL_Code");   
             clear_Combo(cmbSL_Code);
             
             document.getElementById("txtAcc_HeadDesc").value="";
             
             
            try
            {
            // Sub Ledger Mandatory Check               
             Sub_Ledger_Mandatory(txtAcc_HeadCode);             
           }
           catch(e)
           {
             alert(e.description);
           }
           
           
            if(txtAcc_HeadCode.length>=6)
            {
                var url="../../../../../Receipt_SL_DCB.view?Command=checkCode&txtAcc_HeadCode="+txtAcc_HeadCode;                
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
            }         
        }
        else if(Command=="Load_MasterSL_Code")
        {  
            var cmbSL_type=param;    
            document.getElementById("offlist_div_master").style.display='none';
            document.getElementById("emplist_div_master").style.display='none';
            
            var bentype=document.getElementById("bentype").value;
            if(cmbSL_type==5)
              {
                  document.getElementById("offlist_div_master").style.display='block';
                  addtional_field_value=document.getElementById("txtOfficeID_mas").value;
                  
                  if(addtional_field_value=="")
                  {
                        clear_Combo(document.getElementById("cmbMas_SL_Code")); 
                        alert("Enter or select the office code");
                        return true;
                  }
              }
            else
              {
                  document.getElementById("txtOfficeID_mas").value="";
              }
             if(cmbSL_type==7)
              {
                  document.getElementById("emplist_div_master").style.display='block';
                  //clear_Combo(document.getElementById("cmbMas_SL_Code"));
                  //document.getElementById("txtOfficeID_mas").value="";
                  addtional_field_value=document.getElementById("txtEmpID_mas").value;
                  //alert("USE search ICON to select the office");
                  if(addtional_field_value=="")
                  {
                        clear_Combo(document.getElementById("cmbMas_SL_Code")); 
                        alert("Enter or select the employee code");
                        return true;
                  }
              }
            else
              {
                  document.getElementById("txtEmpID_mas").value="";
              }
              
           if(cmbSL_type!="")                              // called only not equal to null and 5 is for office
            {
                var cmbMas_SL_type=document.getElementById("cmbMas_SL_type").value;
                var other_dept_off_alias_id=document.getElementById("cmbMas_SL_Code").value;
                
                var url="../../../../../Receipt_SL_DCB.view?Command=Load_MasterSL_Code&cmbSL_type="+cmbSL_type+"&cmbMas_SL_type="+cmbMas_SL_type+
                    "&other_dept_off_alias_id="+other_dept_off_alias_id+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                    "&cmbOffice_code="+cmbOffice_code+"&addtional_field_value="+addtional_field_value+"&bentype="+bentype;
                 
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   
                   handleResponse(req);
                }   
                        req.send(null);
            }
            else if(cmbSL_type=="")
               clear_Combo(document.getElementById("cmbMas_SL_Code")); 
        }
        else if(Command=="Load_SL_Code")
        {  
            var cmbSL_type=param;
            document.getElementById("offlist_div_trans").style.display='none';
            document.getElementById("emplist_div_trans").style.display='none';
            
            if(cmbSL_type==5 )
              {
                  document.getElementById("offlist_div_trans").style.display='block';
               //   clear_Combo(document.getElementById("cmbSL_Code"));
                  //document.getElementById("txtOfficeID_trs").value="";
                  addtional_field_value=document.getElementById("txtOfficeID_trs").value;
                  //alert("USE search ICON to select the office");
                  if(addtional_field_value=="")
                  {
                        clear_Combo(document.getElementById("cmbSL_Code"));
                        alert("Enter or select the office code");
                        return true;
                  }
              }
            else
             {
                document.getElementById("txtOfficeID_trs").value="";
             }
              
             if(cmbSL_type==7)
              {
                  document.getElementById("emplist_div_trans").style.display='block';
                  //clear_Combo(document.getElementById("cmbMas_SL_Code"));
                  //document.getElementById("txtOfficeID_mas").value="";
                  addtional_field_value=document.getElementById("txtEmpID_trs").value;
                  //alert("USE search ICON to select the office");
                  if(addtional_field_value=="")
                  {
                        clear_Combo(document.getElementById("cmbSL_Code")); 
                        alert("Enter or select the employee code");
                        return true;
                  }
              }
            else
              {
                  document.getElementById("txtEmpID_trs").value="";
              }
              
          if(cmbSL_type!="")                              // called only not equal to null
            {
                 var cmbMas_SL_type=document.getElementById("cmbMas_SL_type").value;
                 var other_dept_off_alias_id=document.getElementById("cmbMas_SL_Code").value;
                 var url="../../../../../Receipt_SL_DCB.view?Command=Load_SL_Code&cmbSL_type="+cmbSL_type+"&cmbMas_SL_type="+cmbMas_SL_type+
                 "&other_dept_off_alias_id="+other_dept_off_alias_id+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                 "&cmbOffice_code="+cmbOffice_code+"&addtional_field_value="+addtional_field_value;
                //alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
            }
            else if(cmbSL_type=="")
            clear_Combo(document.getElementById("cmbSL_Code"));
            
        }
        
    }
     catch (e) 
     {
       alert(e.description);
       return false;
     }
   
} 
/////////////////////////////////////////////   handleResponse()  /////////////////////////////////////////////////////
function handleResponse(req)
{  
    if(req.readyState==4)
    { 
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="checkCode")
            {
                loadcheckCode(baseResponse);
            }
            else if(Command=="Load_SL_Code")
            {
                Load_SL_Code(baseResponse);
            }
            else if(Command=="Load_MasterSL_Code")
            {
                Load_MasterSL_Code(baseResponse);
            }
        }
    }
}

/////////////////////////////////////////////////// account head code function  ------------------------------
function loadcheckCode(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
         var hid=baseResponse.getElementsByTagName("hid")[0].firstChild.nodeValue;
         document.getElementById("txtAcc_HeadCode").value=hid;
         var hdesc=baseResponse.getElementsByTagName("hdesc")[0].firstChild.nodeValue;
        // var BalType=baseResponse.getElementsByTagName("BalType")[0].firstChild.nodeValue;
         var SL_YN =baseResponse.getElementsByTagName("SL_YN")[0].firstChild.nodeValue;
         
         var sl_man = baseResponse.getElementsByTagName("sl_man")[0].firstChild.nodeValue;
         
         
         document.getElementById("txtAcc_HeadCode").value=hid;
         document.getElementById("txtAcc_HeadDesc").value=hdesc;
      
       var cmbSL_type=document.getElementById("cmbSL_type");   
     try{   
     
       
       if(SL_YN=="Y")
       {
            
            
            if(sl_man == "Y" ) 
            {
              isMan.account_head_status = true;     
            }
            
            var items_SLcode=new Array();
            var items_SLdesc=new Array();
            var SLCODE=baseResponse.getElementsByTagName("SLCODE");
            var SLDESC=baseResponse.getElementsByTagName("SLDESC");
            for(var k=0;k<SLCODE.length;k++)
            {
                items_SLcode[k]=baseResponse.getElementsByTagName("SLCODE")[k].firstChild.nodeValue;
                items_SLdesc[k]=baseResponse.getElementsByTagName("SLDESC")[k].firstChild.nodeValue;
            }
            
            cmbSL_type.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Type--";
            option.value="";
            try
            {
                cmbSL_type.add(option);
            }catch(errorObject)
            {
                cmbSL_type.add(option,null);
            }
            for(var k=0;k<SLCODE.length;k++)
            {   
              var option=document.createElement("OPTION");
              option.text=items_SLdesc[k];
              option.value=items_SLcode[k];
               try
              {
                  cmbSL_type.add(option);
              }
              catch(errorObject)
              {
                  cmbSL_type.add(option,null);
              }
            }
            
            if(common_cmbSL_type=="")
                document.getElementById("cmbSL_type").value="";
            else
                document.getElementById("cmbSL_type").value=common_cmbSL_type;    //set from grid
       }
        
     }catch(e)
     {  
       alert(e.description);
       return false;
     }   


        if(SL_YN=="N" || SL_YN=="null")
           {    
                cmbSL_type.innerHTML=""; 
                var option=document.createElement("OPTION");
                option.text="--Select Type--";
                option.value="";
                try
                {
                    cmbSL_type.add(option);
                }catch(errorObject)
                {
                    cmbSL_type.add(option,null);
                }
            }
          
    }
     else if(flag=="failure")
     {
         alert("Account Head Code '"+document.getElementById("txtAcc_HeadCode").value+"' doesn't Exist");
         document.getElementById("txtAcc_HeadCode").value="";
         document.getElementById("txtAcc_HeadCode").focus();
     }
     
        //common_AHead_code_flag="";
        common_cmbSL_type="";
}
/////////////////////////////////////////////   Load_SL_Code() by User /////////////////////////////////////////////////////

function Load_SL_Code(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

    if(flag=="success")
    {
         var cmbSL_Code=document.getElementById("cmbSL_Code");
         
         var items_id=new Array();
         var items_name=new Array();
        // var items_init=new Array();
        
            var cid=baseResponse.getElementsByTagName("cid");
            var cname=baseResponse.getElementsByTagName("cname");
            for(var k=0;k<cid.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue;
                //alert(items_id[k]);
                items_name[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
             //   items_init[k]=baseResponse.getElementsByTagName("init")[k].firstChild.nodeValue;
            }
           clear_Combo(cmbSL_Code);
            //alert('here second');
            for(var k=0;k<cid.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_name[k];
                  option.value=items_id[k];
                   try
                  {
                      cmbSL_Code.add(option);
                  }
                  catch(errorObject)
                  {
                      cmbSL_Code.add(option,null);
                  }
            }
             document.getElementById("cmbSL_Code").value=items_id[0];
             //alert(items_id[0]);
           if(document.getElementById("cmbSL_type").value==5)
           {
                var state=baseResponse.getElementsByTagName("state")[0].firstChild.nodeValue;
                if(state!="CR")
                alert("Office is not in working status");
           }
           
           if(document.getElementById("cmbMas_SL_type").value!=9 && document.getElementById("cmbSL_type").value==7)
           {
                var state=baseResponse.getElementsByTagName("state")[0].firstChild.nodeValue;
                if(state=="DPN")
                alert("Employee in Deputation");
           } 
           
          // document.getElementById("cmbSL_Code").value=common_cmbSL_Code;
    }
    else if(flag=="failure")
    {
        alert("No data found");
        var cmbSL_Code=document.getElementById("cmbSL_Code");
        clear_Combo(cmbSL_Code);
    }
    
    common_cmbSL_Code="";
}
/////////////////////////////////////////////  For MASTER Combo SL Code //////////////////////////////////

function Load_MasterSL_Code(baseResponse)
{
 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

    if(flag=="success")
    {
         var cmbSL_Code=document.getElementById("cmbMas_SL_Code");      // value assigned to same local variable name
         
         var items_id=new Array();
         var items_name=new Array();
        
            var cid=baseResponse.getElementsByTagName("cid");
            var cname=baseResponse.getElementsByTagName("cname");
            for(var k=0;k<cid.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue;
                items_name[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
               
            }
           
           clear_Combo(cmbSL_Code);
            for(var k=0;k<items_id.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_name[k];
                  option.value=items_id[k];
                   try
                  {
                      cmbSL_Code.add(option);
                  }
                  catch(errorObject)
                  {
                      cmbSL_Code.add(option,null);
                  }
            }
            
            document.getElementById("cmbMas_SL_Code").value=items_id[0];
            loadName_Mas(items_name[0]);
          if(document.getElementById("cmbMas_SL_type").value==5)
           {
                var state=baseResponse.getElementsByTagName("state")[0].firstChild.nodeValue;
                if(state!="CR")
                alert("Office is not in working status");
           }
           
          if(document.getElementById("cmbMas_SL_type").value==7)
           {
                var state=baseResponse.getElementsByTagName("state")[0].firstChild.nodeValue;
                if(state=="DPN")
                alert("Employee in Deputation");
           }
    }
    else if(flag=="failure")
    {
        alert("No data found");
        var cmbSL_Code=document.getElementById("cmbMas_SL_Code");   // value assigned to same local variable name
        clear_Combo(cmbSL_Code);
    }
}