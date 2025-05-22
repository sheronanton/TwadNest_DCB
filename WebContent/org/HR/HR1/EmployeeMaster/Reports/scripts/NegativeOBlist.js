
var __pagination=10,_Service_Period_Beg_Year=1966;
  var items1;
    var items2;
    var items3;
    var items4;
    var items5;
    var items6;
    var totalblock=0;
    var checkbox=new Array();
    var empid=new Array();
    var empname=new Array();
    var desig=new Array();
    var off_name=new Array();
    var remark=new Array();
    var check=new Array();
    var flag, command, response = "";
    var winemp = null;
    var winemp1 = null;
    var xmlHttp1 = null, xmlHttpobj = null, xmlHttpreq = null, xmlHttp = null;
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

function loadTable(emp,empnames,desg)
{
                          var i=0;
                          var tbody=document.getElementById("tb");
                          try{tbody.innerHTML="";}
                          catch(e) {tbody.innerText="";}
                          s=0;
                          items1=new Array();
                           items2=new Array();
                           items3=new Array();
                          items4=new Array();
                          items5=new Array();
                            for(i=0;i<emp.length;i++)
                            {
                                    var items=new Array();
                                    items1[i]=emp[i];
                                    items2[i]=empnames[i];
                                    items3[i]=desg[i];
                                    items5[i]=false;
                                    checkbox.splice(checkbox.length,0,false);
                                    empid.splice(checkbox.length,0,emp[i]);
                                    empname.splice(checkbox.length,0,empnames[i]);
                                    desig.splice(checkbox.length,0,desg[i]);
                                    off_name.splice(checkbox.length,0,'');
                                    remark.splice(checkbox.length,0,'');
                            }                            
                            totalblock=0;
                            //alert(parseInt(items1.length/__pagination));
                            if(checkbox.length>0)
                            {
                                    totalblock=parseInt(checkbox.length/__pagination);
                                    if(checkbox.length%__pagination!=0)
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
}   

function loadPage(page)
{


    var i=0;
    var c=0;
    var p=__pagination*(page-1);
    var tbody=document.getElementById("tb");
    try{tbody.innerHTML="";}
    catch(e) {tbody.innerText="";}
    document.Hrm_TransJoinForm.cmbpage.selectedIndex=page-1;
    for(i=p;i<checkbox.length && c<__pagination;i++)
    {
                c++;
                var tbody=document.getElementById("tb");
                var mycurrent_row=document.createElement("TR");
                var descell=document.createElement("TD");
                descell.setAttribute('align','center');
                var sc="";
                if(check[i]=='True')
                	
                	{
                	checkbox[i]=true;
                
                	}
                 if((checkbox[i]==true) || (check[i]=='True'))
                 {
                         if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                         {
                            sc=document.createElement("<INPUT type='checkbox' name='sel' id='sel" +i+"' checked  onclick='setTrue(this,"+i+")'>");
                         }
                         else
                         {
                         sc=document.createElement("input");
                         sc.type="checkbox";
                         sc.name="sel";
                         sc.id='sel' +i;
                         sc.checked=true;
                         sc.setAttribute('onclick','setTrue(this,'+i+')');
                          //alert('setTrue(this,'+i+')');
                         }
                }
                else
                {
                        if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                         {
                          sc=document.createElement("<INPUT type='checkbox' name='sel' id='sel" +i+"' onclick='setTrue(this,"+i+")' >");
                         }
                         else
                         {
                         sc=document.createElement("input");
                         sc.type="checkbox";
                         sc.name="sel";
                         sc.id='sel' +i;
                         sc.checked=false;
                         sc.setAttribute('onclick','setTrue(this,'+i+')');
                         //alert('setTrue(this,'+i+')');
                 }
                
                }
                 descell.appendChild(sc);
                 mycurrent_row.appendChild(descell);
//                 var cbox=document.getElementById('sel'+i);
//                 alert(cbox.value);

                     cell2=document.createElement("TD");
                    cell2.setAttribute('align','left');
                    //alert(items[j]); 
                    if(empid[i]!="null")
                    {
                        var currentText=document.createTextNode(empid[i]);
                    }
                    else
                    {
                        var currentText=document.createTextNode('');
                    }
                    cell2.appendChild(currentText);
                    mycurrent_row.appendChild(cell2);
                     cell2=document.createElement("TD");
                    cell2.setAttribute('align','left');
                    //alert(items[j]); 
                    if(empname[i]!="null")
                    {
                        var currentText=document.createTextNode(empname[i]);
                    }
                    else
                    {
                        var currentText=document.createTextNode('');
                    }
                    cell2.appendChild(currentText);
                    mycurrent_row.appendChild(cell2);
                    cell2=document.createElement("TD");
                    cell2.setAttribute('align','left');
                    //alert(items[j]); 
                    if(desig[i]!="null")
                    {
                        var currentText=document.createTextNode(desig[i]);
                    }
                    else
                    {
                        var currentText=document.createTextNode('');
                    }
                    cell2.appendChild(currentText);
                    mycurrent_row.appendChild(cell2);
                    descell=document.createElement("TD");
                    descell.setAttribute('align','left');
                    //alert(items[j]); 
                    if(off_name[i]!="null")
                    {
                    	 sc=document.createTextNode(off_name[i]);
                    }
                    else
                    { sc=document.createTextNode('');
                    }
                    descell.appendChild(sc);
                    mycurrent_row.appendChild(descell);
                    descell=document.createElement("TD");
                    descell.setAttribute('align','left');
                    //alert(items[j]); 
                    if(remark[i]!="null")
                    {  sc=document.createTextNode(remark[i]);
                    }
                    else
                    {
                    	  sc=document.createTextNode('');
                    }
                    descell.appendChild(sc);
                    mycurrent_row.appendChild(descell);
                    
                tbody.appendChild(mycurrent_row);
                    
    }
   var cell=document.getElementById("divcmbpage");
        cell.style.display="block";
   var cell=document.getElementById("divpage");
        cell.style.display="block";
        cell.innerText= ' / ' +totalblock;
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
function setTrue(t,index)
{
    if(t.checked==true)
    	checkbox[index]=true;
    else
    	checkbox[index]=false;
    
}
function setoff_name(t,index)
{
	off_name[index]=t.value;
}
function setRemark(t,index)
{
	remark[index]=t.value;
}
function DeleteEmp(){
	var i=0,kk=0,mm=0;
	for(var jjk=0;jjk<checkbox.length;jjk++){
		if(checkbox[jjk]==true)
			kk++;
	}
	if(kk>0){
		for(var j=0;j<kk;j++){
	for(var n=0;n<checkbox.length;n++){
		if(checkbox[n]==true)
		{
		checkbox.splice(n, 1);
		empid.splice(n,1);
		empname.splice(n,1);
        desig.splice(n,1);
        off_name.splice(n,1);
        remark.splice(n,1);

        i=0;
		}
	}
    i=0;
    mm++;
   // alert("MM "+mm+" KK "+kk);
    if(mm==kk){
    var tbody=document.getElementById("tb");
    //alert(tbody.innerHTML);
    try{tbody.innerHTML="";}
    catch(e) {tbody.innerText="";}
  //  alert(tbody.innerHTML);
    s=0;
    items1=new Array();
    items2=new Array();
    items3=new Array();
    items4=new Array();
    items5=new Array();
      for(i=0;i<empid.length;i++)
      {
              var items=new Array();
              items1[i]=empid[i];
              items2[i]=empname[i];
              items3[i]=desig[i];
              items5[i]=false;
      }                            
	totalblock=0;
    if(checkbox.length>0)
    {
            totalblock=parseInt(checkbox.length/__pagination);
            if(checkbox.length%__pagination!=0)
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
	}
    }
}
	else{
	alert("Select Employes(s)");	
	}
}
function btncancel()
{
 //document.Hrm_TransJoinForm.cmbControllingLevel.selectedIndex=0;
 var tbody=document.getElementById("tb");
 try{tbody.innerHTML="";}
 catch(e) {tbody.innerText="";}
}

/*function btnsubmit()
{
var v=document.getElementsByName("sel");
var s='';
if(v && items5)
{
	items4=null;
	items4=new Array();
	var cc=0;
	Minimize();
        for(i=0;i<items5.length;i++)
        {
                if(items5[i]==true)
                {
                	items4[cc]=items1[i];
                }
                cc++;
        }
        opener.doParentLoanID(items4);
        return true;
}
else
{
           alert('Select the Employee(s) ');
           return false; 
}
}
  */
function changepage()
{
//alert('hai');
var page=document.Hrm_TransJoinForm.cmbpage.value;
loadPage(parseInt(page));
}

function changepagesize()
{
           __pagination=document.Hrm_TransJoinForm.cmbpagination.value;
            var v=document.getElementsByName("sel");
            //alert(v);
        if(v && checkbox)
        {
                            totalblock=0;
                            //alert(parseInt(items1.length/__pagination));
                            if(checkbox.length>0)
                            {
                                    totalblock=parseInt(checkbox.length/__pagination);
                                    if(checkbox.length%__pagination!=0)
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
function sellectall()
{
        var v=document.getElementsByName("sel");
        if(v && checkbox)
        {
            if(document.Hrm_TransJoinForm.chkall.checked==true)
            {
                for(i=0;i<checkbox.length;i++)
                {
                	checkbox[i]=true;
                }
                for(i=0;i<v.length;i++)
                {
                        v[i].checked=true;
                }
            }
            else
            {
            
                for(i=0;i<checkbox.length;i++)
                {
                	checkbox[i]=false;
                }
                for(i=0;i<v.length;i++)
                {
                        v[i].checked=false;
                }
            }
        }
}


function inverseselect()
{
        var v=document.getElementsByName("sel");
        if(v && checkbox)
        {
                for(i=0;i<checkbox.length;i++)
                {
                        if(checkbox[i]==true)
                        {
                        	checkbox[i]=false;
                        }
                        else
                        {
                        	checkbox[i]=true;
                        }
                }
                for(i=0;i<v.length;i++)
                {
                        if(v[i].checked==true)
                        {
                            v[i].checked=false;
                        }
                        else
                        {
                            v[i].checked=true;
                        }
                        
                }
           
        }

}
function numbersonly1(e, t) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode == 13) {
		try {
			t.blur();
		} catch (e) {
		}
		return true;
	}
	if (unicode != 8 && unicode != 9) {
		if (unicode < 48 || unicode > 57)
			return false;
	}
}
function noEnter(e)
{
  
   isIE=document.all? 1:0
      
   keyEntry = !isIE? e.which:event.keyCode;
                 
   if(keyEntry=='38')
   {
     return false;
   }else return true;
}
function servicepopupLoanID() {
	//if(winemp && winemp.open && !winemp.closed) winemp.close();
	if (winemp1 && winemp1.open && !winemp1.closed) {
		winemp1.resizeTo(500, 600);
		winemp1.moveTo(200, 200);
		winemp1.focus();
		return;
	}
	winemp1 = window.open("../../../../org/hrms/leave/jsp/Leave_Accrual_Popup.jsp",
			"Accrual Sanction Id",
			"status=1,height=500,width=600,resizable=YES, scrollbars=yes");
	winemp1.moveTo(250, 250);
	winemp1.focus();
}

function doParentLoanID(loan_ids, emp, name) {
	document.getElementById("eid").value = loan_ids;
	callServer('Get', 'null');
}

function servicepopup() {
	//if(winemp && winemp.open && !winemp.closed) winemp.close();
	if(document.getElementById("month").value!=0){
		var yy=document.getElementById("year").value;
		if(yy.length>=4){
	if (winemp && winemp.open && !winemp.closed) {
		winemp.resizeTo(500, 600);
		winemp.moveTo(200, 200);
		winemp.focus();
		return;
	}
	var emps="";
	for(var i=0;i<empid.length;i++){
		emps+=empid[i]+",";
	}
	emps=emps.substring(0,emps.length-1);
	if(emps.length<1){
		emps='0';	
	}
	
	winemp = window.open("../../../../org/hrms/leave/jsp/Leave_Accrual_Emp_Popup.jsp?offid="+document.getElementById("offids").value+
			"&empids="+emps+"&year="+yy+"&month="+document.getElementById("month").value,
			"Employees","status=1,height=500,width=600,resizable=YES, scrollbars=yes");
	winemp.moveTo(250, 250);
	winemp.focus();
	}
		else{
			alert("Enter Valid Year");
			document.getElementById("year").focus();
		}	
	}
	else{
		alert("Select Valid Month");
		document.getElementById("month").focus();
	}
}
function doParent(emp,empname,desg) {
	loadTable(emp,empname,desg);
//	document.getElementById("eid").value = loan_ids;
//	callServer('Get', 'null');
}

function clearAll() {
	document.getElementById('year').value = "";
	document.getElementById('prono').value = "";
	document.getElementById('prodate').value = "";
	document.Hrm_TransJoinForm.AuthName.value = "";
	document.Hrm_TransJoinForm.AuthDesg.value = "";
	document.Hrm_TransJoinForm.month.selectedIndex = 0;
	 var cmbpage = document.getElementById("cmbpage");

		try {
			cmbpage.innerHTML = "";
		} catch (e) {
			cmbpage.innerText = "";
		}
		 var tbody=document.getElementById("tb");
		 try{tbody.innerHTML="";}
		 catch(e) {tbody.innerText="";}
		 checkbox=null;
		 empid=null;
		 empname=null;
		 desig=null;
		 off_name=null;
		 remark=null;
		 checkbox=new Array();
		 empid=new Array();
		 empname=new Array();
		 desig=new Array();
		 off_name=new Array();
		 remark=new Array();
	document.Hrm_TransJoinForm.CmdUpdate.disabled = true;
	document.Hrm_TransJoinForm.CmdDelete.disabled = true;
	document.Hrm_TransJoinForm.CmdValidate.disabled = true;
	document.Hrm_TransJoinForm.CmdAdd.disabled = false;
	document.Hrm_TransJoinForm.eid.value = "";
}
function clearUpdate(){
	
	document.getElementById('year').value = "";
	document.getElementById('prono').value = "";
	document.getElementById('prodate').value = "";
	document.Hrm_TransJoinForm.eid.value = "";
	document.Hrm_TransJoinForm.AuthName.value = "";
	document.Hrm_TransJoinForm.eid.value = "";
	document.Hrm_TransJoinForm.AuthDesg.value = "";
	document.Hrm_TransJoinForm.month.selectedIndex = 0;
	document.Hrm_TransJoinForm.CmdUpdate.disabled = false;
	document.Hrm_TransJoinForm.CmdDelete.disabled = true;
	document.Hrm_TransJoinForm.CmdValidate.disabled = true;
	 var cmbpage = document.getElementById("cmbpage");

		try {
			cmbpage.innerHTML = "";
		} catch (e) {
			cmbpage.innerText = "";
		}
		 var tbody=document.getElementById("tb");
		 try{tbody.innerHTML="";}
		 catch(e) {tbody.innerText="";}
	 checkbox=null;
	 empid=null;
	 empname=null;
	 desig=null;
	 off_name=null;
	 remark=null;
	 checkbox=new Array();
	 empid=new Array();
	 empname=new Array();
	 desig=new Array();
	 off_name=new Array();
	 remark=new Array();
	var tlist = document.getElementById("tblList");
	try {
		tlist.innerHTML = "";
	} catch (e) {
		tlist.innerText = "";
	}
}
function showyear() {
	var date_value = document.Hrm_TransJoinForm.appldate.value;
	var dateParts = date_value.split("/");
	selectedDay = parseInt(dateParts[0], 10);
	selectedMonth = parseInt(dateParts[1], 10);
	selectedYear = parseInt(dateParts[2], 10);
	return selectedYear;
}

function checkNull() {
	if (document.Hrm_TransJoinForm.year.value == "") {
		alert("Enter Year & Month of Accrual");
		return false;
	}
	else if (document.Hrm_TransJoinForm.prono.value == "") {
		alert("Enter Sanction Proceedings No.");
		return false;
	}
	else if (document.Hrm_TransJoinForm.prodate.value == "") {
		alert("Enter Sanction Proceedings Date");
		return false;
	}
	else if(document.Hrm_TransJoinForm.prodate.value.length<10){
		alert("Enter Valid Proceedings Date");
		return false;
	}
	else if (document.Hrm_TransJoinForm.AuthName.value == 0) {
		alert("Enter Sanction Authority Name");
		return false;
	}
	else if (document.Hrm_TransJoinForm.AuthDesg.value == 0) {
		alert("Enter Sanction Authority Designation");
		return false;
	}
	else if(checkbox.length==0){
		alert("Select Employee(s)");
		return false;
	}
	else{
	for(var i=0;i<checkbox.length;i++){
		if(off_name[i]==''){
			alert("Enter No. of Days EL Accrual for Employee ID :"+empid[i]+"  Name: "+empname[i]);
			return false;
		}
	}
	}
	return true;
}



function callServer(command, param) {
	
//	var Remarks1 = document.Hrm_TransJoinForm.Remarks.value;
//if(Remarks1=="null")
//		Remarks1="";

	if (command == "Get") {
		var fin_year = document.getElementById('fin_year').value;
	//	alert(fin_year);
		//= document.Hrm_TransJoinForm.loan_id.value;
		if(fin_year.length>0){
		url = "../../../../../../GPF_Interest_OB?form=Neg_OB_list&fin_year="+fin_year;
		var req1 = getTransport();
		req1.onreadystatechange = function() {
			if (req1.readyState == 4) {
				if (req1.status == 200) {
				response = req1.responseXML.getElementsByTagName("response")[0];
					//alert(response);
					var res = response.getElementsByTagName("flag")[0].firstChild.nodeValue;
					if (res == "success") {
						//alert(res);
						var len = response.getElementsByTagName("emp_id").length;
//alert(len);
								var Employee_Id;
								var Employee_Name;
								var Designation;
								checkbox=null;
								 empid=null;
								 empname=null;
								 desig=null;
								 off_name=null;
								 remark=null;
								 checkbox=new Array();
								 empid=new Array();
								 empname=new Array();
								 desig=new Array();
								 off_name=new Array();
								 remark=new Array();
								 check[i]=new Array();
								 var mm=0;
							for ( var i = 0; i <=len; i++) {
								mm++;
								//alert("inside for");
								Employee_Id = response.getElementsByTagName("emp_id")[i].firstChild.nodeValue;
								Employee_Name = response.getElementsByTagName("emp_name")[i].firstChild.nodeValue;
								Designation = response.getElementsByTagName("designation")[i].firstChild.nodeValue;
								checkbox[i]=false;empid[i]=Employee_Id;empname[i]=Employee_Name;desig[i]=Designation;
								off_name[i]=response.getElementsByTagName("Opening_Balance_Regular")[i].firstChild.nodeValue;
								remark[i]=response.getElementsByTagName("Employee_Status_Desc")[i].firstChild.nodeValue;
								check[i]=response.getElementsByTagName("exist")[i].firstChild.nodeValue.trim();
								if(check[i]=='null')check[i]='';
								if(remark[i]=='null')remark[i]='';
								if(mm==len){
								changepagesize();
							//	alert("inside for");
								}
							}
						
					}
					else {
						alert("Details Not Found ");
						//clearUpdate();
						document.Hrm_TransJoinForm.laid.focus();
						return;
					}
				} 
			}
			else{
		        document.body.style.cursor = "wait";
		        var temp ="<br/><table align=center CELLPADDING=0 CELLSPACING=0 WIDTH=300><tr><td align=center><FONT FACE=Arial SIZE=4 COLOR=black><b>Processing please wait</b></font></td></tr><tr><td align=center><img width=250 height=40 src=../../../../../images/loading_bar.gif /></td></tr></table>";
			}

		};
		req1.open("GET", url, true);
		req1.send(null);
		}
	}
	else 	if (command == "GetNo") {
		var fin_year = document.getElementById('fin_year').value;
		//	alert(fin_year);
			//= document.Hrm_TransJoinForm.loan_id.value;
			if(fin_year.length>0){
			url = "../../../../../../GPF_Interest_OB?form=No_trn_list&fin_year="+fin_year;
			var req1 = getTransport();
			req1.onreadystatechange = function() {
				if (req1.readyState == 4) {
					if (req1.status == 200) {
						response = req1.responseXML.getElementsByTagName("response")[0];
						var res = response.getElementsByTagName("flag")[0].firstChild.nodeValue;
						if (res == "success") {
						//	alert(res);
							var len = response.getElementsByTagName("emp_id").length;
	//alert(len);
									var Employee_Id;
									var Employee_Name;
									var Designation;
									checkbox=null;
									 empid=null;
									 empname=null;
									 desig=null;
									 off_name=null;
									 remark=null;
									 checkbox=new Array();
									 empid=new Array();
									 empname=new Array();
									 desig=new Array();
									 off_name=new Array();
									 remark=new Array();
									 check[i]=new Array();
									 var mm=0;
								for ( var i = 0; i <=len; i++) {
									mm++;
									//alert("inside for");
									Employee_Id = response.getElementsByTagName("emp_id")[i].firstChild.nodeValue;
									Employee_Name = response.getElementsByTagName("emp_name")[i].firstChild.nodeValue;
									Designation = response.getElementsByTagName("designation")[i].firstChild.nodeValue;
									checkbox[i]=false;empid[i]=Employee_Id;empname[i]=Employee_Name;desig[i]=Designation;
									off_name[i]=response.getElementsByTagName("office_name")[i].firstChild.nodeValue.trim();
									remark[i]=response.getElementsByTagName("Employee_Status_Desc")[i].firstChild.nodeValue;
									check[i]=response.getElementsByTagName("exist")[i].firstChild.nodeValue.trim();
									if(off_name[i]=='null')off_name[i]='';
								
									if(check[i]=='null')check[i]='';
									if(remark[i]=='null')remark[i]='';
									if(mm==len){
									changepagesize();
								//	alert("inside for");
									}
								}
							
						}
						else {
							alert("Details Not Found ");
							//clearUpdate();
							document.Hrm_TransJoinForm.laid.focus();
							return;
						}
					} 
				}
				else{
			        document.body.style.cursor = "wait";
			        var temp ="<br/><table align=center CELLPADDING=0 CELLSPACING=0 WIDTH=300><tr><td align=center><FONT FACE=Arial SIZE=4 COLOR=black><b>Processing please wait</b></font></td></tr><tr><td align=center><img width=250 height=40 src=../../../../../images/loading_bar.gif /></td></tr></table>";
				}

			};
			req1.open("GET", url, true);
			req1.send(null);
			}
		}
	else if (command == "Add") {
		//var flag = checkNull();
		//if (flag) {
		/*	if (document.getElementById('eid').value== "") {
				alert("Enter Accrual Sanction ID");
			}
			else
			
			*/{
				var empids='';
				var fin_year = document.getElementById('fin_year').value;
				
				
				for(var i=0;i<checkbox.length;i++){
					if(checkbox[i]==true)
						empids=empids+empid[i].trim()+',';
					}
				empids=empids.substring(0, empids.length-1);
			url = "../../../../../../GPF_Interest_OB?form=Add&fin_year="+fin_year+"&empids="+empids;
		//alert(url);
			var req = getTransport();
			req.open("GET", url, true);
			req.onreadystatechange = function() {
				processResponse(req);
			};
			req.send(null);
		}
		}
	else if (command == "AddNotrn") {
		//var flag = checkNull();
		//if (flag) {
		/*	if (document.getElementById('eid').value== "") {
				alert("Enter Accrual Sanction ID");
			}
			else
			
			*/{
				var empids='';
				var fin_year = document.getElementById('fin_year').value;
				
				
				for(var i=0;i<checkbox.length;i++){
					if(checkbox[i]==true)
						empids=empids+empid[i].trim()+',';
					}
				empids=empids.substring(0, empids.length-1);
			url = "../../../../../../GPF_Interest_OB?form=AddNotrn&fin_year="+fin_year+"&empids="+empids;
		//alert(url);
			var req = getTransport();
			req.open("GET", url, true);
			req.onreadystatechange = function() {
				processResponse(req);
			};
			req.send(null);
		}
		}
	
	 else if(command=="Get_emp_config")
     {
		 var off=document.Hrm_TransJoinForm.offid.value;
         url="../../../../hrm_leave_el_accrual_sanc?command=Get_emp_config&offid="+off;
         seq=0;
         var req=getTransport();
         req.open("GET",url,true); 
         req.onreadystatechange=function()
         {
        	 if(req.readyState==4)
             {
                 if(req.status==200)
                 {   response=req.responseXML.getElementsByTagName("response")[0];
                   		changepagesize();
        				changepage();
                     statusflag=true;
                 }
             }
         };   
                 req.send(null);
     }
}
String.prototype.trim = function() {
	a = this.replace(/^\s+/, '');
	return a.replace(/\s+$/, '');
	};
function Exit() {
	self.close();
}
function processResponse(req) {
	if (req.readyState == 4) {
		if (req.status == 200) {
			// alert("in added");
			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			updateResponse(baseResponse);
		}
	}
}
var checkflag = false;
function updateResponse(response) {
	
	var res = response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	// alert(response.getElementsByTagName("command")[0].firstChild.nodeValue);
	if (res == "success") {
		if (response.getElementsByTagName("command")[0].firstChild.nodeValue == "Add") {
			alert("Records Added Successfully");
			 var tbody=document.getElementById("tb");
			 try{tbody.innerHTML="";}
			 catch(e) {tbody.innerText="";}
			return;
		}
		else if (response.getElementsByTagName("command")[0].firstChild.nodeValue == "AddNotrn") {
			alert("Records Added Successfully");
			 var tbody=document.getElementById("tb");
			 try{tbody.innerHTML="";}
			 catch(e) {tbody.innerText="";}
			return;
		}
		else if (res == "Notadded") {
			alert("Data already added");
			clearAll();
			return;
		} else if (response.getElementsByTagName("command")[0].firstChild.nodeValue == "Update") {
			alert("Records Updated Successfully");
			//clearAll();
		//	callServer('Get', 'null');
		} else if (response.getElementsByTagName("command")[0].firstChild.nodeValue == "Validated") {
			alert("Records Validated Successfully");
			document.Hrm_TransJoinForm.CmdValidate.disabled = true;
			//clearAll();
			//callServer('Get', 'null');
		}
		else if (response.getElementsByTagName("command")[0].firstChild.nodeValue == "delete") {
			alert("Records Deleted Successfully");
			clearAll();
			var tlist = document.getElementById("tblList");
			try {
				tlist.innerHTML = "";
			} catch (e) {
				tlist.innerText = "";
			}
		}
	} else {
		alert("Process failure");
	}
}

function validateDate(dtControl)
{
    var input = document.getElementById(dtControl);
    var validformat=/^\d{1,2}\/\d{1,2}\/\d{4}$/; //Basic check for format validity
    var returnval=false;
    if (!validformat.test(input.value))
    alert('Invalid Date Format. Please correct.');
    else{ //Detailed check for valid date ranges
    var dayfield=input.value.split("/")[0];
    var monthfield=input.value.split("/")[1];
    var yearfield=input.value.split("/")[2];
  
    var dayobj = new Date(yearfield, monthfield-1, dayfield);
    if ((dayobj.getMonth()+1!=monthfield)||(dayobj.getDate()!=dayfield)||(dayobj.getFullYear()!=yearfield))
    { alert('Invalid Date');
    input.value='';
    }
    else
    {
        returnval=true;
    }
    }
    if (returnval==false) return false;
    return returnval;
} 

