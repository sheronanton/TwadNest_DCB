function GetXmlHttpObject()
{
    if(window.XMLHttpRequest)
    {
        return new XMLHttpRequest();
    }
    if(window.ActiveXObject)
    {
         return new ActiveXObject("Microsoft.XMLHTTP");
    }
}
function loadCombo()  
{
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    url="../../../../../pms_dcb_mst_tariff?command=comboload";
    url=url+"&sid="+Math.random();
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange= function()
            {
                 stateChangedcombo(xmlhttp);
            }
    xmlhttp.send(null);
}
function stateChangedcombo(xmlhttp)
{
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(xmlhttp.status==200)
        {
             if(commandres=="comboload")
            {
                if(flagres=='success')
                {
                    var charge_by_desc_len=baseres.getElementsByTagName("CHARGE_TYPE_DESC_TABLE").length;
                    for(var i=0;i<charge_by_desc_len;i++)
                     {
                         var charge_type_id_val=baseres.getElementsByTagName("CHARGE_TYPE_ID")[i].firstChild.nodeValue;
                         var charge_by_desc_val=baseres.getElementsByTagName("CHARGE_TYPE_DESC_TABLE")[i].firstChild.nodeValue;
                         addOption(document.chargedetails.charge_type_Id,charge_by_desc_val,charge_type_id_val);
                     }
                }
                 else
                {
                    alert("Not Loaded");
                }
            }
        }
    }
}
function addOption(selectbox,text,value)
{
	var optn = document.createElement("OPTION");
	optn.text = text;
	optn.value = value;
	selectbox.options.add(optn);
}

function loadbeneficiary()
{
    document.getElementById("cmdupdate").style.display='none';
    document.getElementById("cmddelete").style.display='none';
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
		    url="../../../../../pms_dcb_mst_tariff?command=loadbeneficiary";
		    url=url+"&sid="+Math.random();
		    xmlhttp.open("GET",url,true);
		    xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadbeneficiary(xmlhttp);
            }
    xmlhttp.send(null);
}
function stateChangedloadbeneficiary(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(xmlhttp.status==200)
        {
             if(commandres=="loadbeneficiary")
            {
                if(flagres=='success')
                {
                    
                    var beneficiary_desc_len=baseres.getElementsByTagName("BENEFICIARY_TYPE_DESC").length;
                    for(var i=0;i<beneficiary_desc_len;i++)
                     {
                         var Beneficiary_Type_id_val=baseres.getElementsByTagName("BEN_TYPE_ID")[i].firstChild.nodeValue;
                         var Beneficiary_Type_val=baseres.getElementsByTagName("BENEFICIARY_TYPE_DESC")[i].firstChild.nodeValue;
                         addOptiondesc(document.chargedetails.Beneficiary_Type,Beneficiary_Type_val,Beneficiary_Type_id_val);
                     }
                }
                 else
                {
                    alert("Not Loaded");
                }
            }
        }
    }
}
 

  
function addOptiondesc(selectbox,text,value)
{
var optn = document.createElement("OPTION");
optn.text = text;
optn.value = value;
selectbox.options.add(optn);
}

// new for hoganical
function doratecharge_hog(actionval,row)  
{
	 xmlhttp = GetXmlHttpObject();
	if (xmlhttp == null) {
		alert("Your browser does not support AJAX!");
		return;
	}
	document.getElementById("pr_status").value = 0;
//	alert("row is -->"+row)
	var tariff_Rate = document.getElementById("TARIFF_RATE" + row).value;  
//	alert("tariff_Rate--->"+tariff_Rate);

	var tariff_Wef_cell = document.getElementById("Tariff_wef").value;
//	alert("tariff_Wef_cell-->"+tariff_Wef_cell);
	

	var ben_type_id = document.getElementById("ben_type_id" + row).value;
//	alert("ben_type_id-->"+ben_type_id);
	
	if (actionval == "newrate") {
		 
		var offid = document.getElementById("off_id").value;
		url = "../../../../../pms_dcb_mst_tariff?offid=" + offid
				+ "&command=newrate&tariff_Rate=" + tariff_Rate
				+ "&Beneficiary_Type=" + ben_type_id+ "&tariff_Wef_cell=" + tariff_Wef_cell;
		url = url + "&sid=" + Math.random();
		 
		xmlhttp.open("GET", url, true);
		xmlhttp.onreadystatechange = function() {
			rate_change(xmlhttp);
		}
		xmlhttp.send(null);
	 
}	
}

function doratecharge(actionval,row)  
{
	 xmlhttp = GetXmlHttpObject();
	if (xmlhttp == null) {
		alert("Your browser does not support AJAX!");
		return;
	}
	document.getElementById("pr_status").value = 0;
	var tariff_Rate = document.getElementById("TARIFF_RATE" + row).innerHTML;  
	//alert("tariff_Rate--->"+tariff_Rate);

	var tariff_Wef_cell = document.getElementById("Tariff_wef").value;
//	alert("tariff_Wef_cell-->"+tariff_Wef_cell);
	

	var ben_type_id = document.getElementById("ben_type_id" + row).value;
	if (actionval == "newrate") {
		 
	 
			var offid = document.getElementById("off_id").value;
			url = "../../../../../pms_dcb_mst_tariff?offid=" + offid
					+ "&command=newrate&tariff_Rate=" + tariff_Rate
					+ "&Beneficiary_Type=" + ben_type_id+ "&tariff_Wef_cell=" + tariff_Wef_cell;
			url = url + "&sid=" + Math.random();
			 
			xmlhttp.open("GET", url, true);
			xmlhttp.onreadystatechange = function() {
				rate_change(xmlhttp);
			}
			xmlhttp.send(null);
		 
	}	
}

function doFunction(actionval)
{
    xmlhttp = GetXmlHttpObject();
	if (xmlhttp == null) {
		alert("Your browser does not support AJAX!");
		return;
	}
	var tariff_Id;
	try {
		tariff_Id = document.getElementById("tariff_Id").value;
	} catch (e) {
	}

	var tariff_Rate = document.getElementById("tariff_Rate").value;
	var Tariff_wef = document.getElementById("Tariff_wef").value;
	//var UOM = document.getElementById("UOM").value;
	var Beneficiary_Type = document.getElementById("Beneficiary_Type").value;
	var status = document.getElementById("status").value;
 
    if(actionval=="Add")
    {
            var valfn=validate();
            if(valfn==true)
            {
            url="../../../../../pms_dcb_mst_tariff?command=add&tariff_Rate="+tariff_Rate+"&Tariff_wef="+Tariff_wef+"&Beneficiary_Type="+Beneficiary_Type+"&status=A";
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange= stateChanged;
            xmlhttp.send(null);
            }
    }
   else if(actionval=="get")
   {
        
        url="../../../../../pms_dcb_mst_tariff?command=get";
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);        
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);
        
   }
   else if(actionval=="Update")
    {
	   var valfn=validate();
  	
        if(valfn==true)
        {
        url="../../../../../pms_dcb_mst_tariff?command=update&tariff_Id="+tariff_Id+"&tariff_Rate="+tariff_Rate+"&Tariff_wef="+Tariff_wef+"&Beneficiary_Type="+Beneficiary_Type+"&status="+status;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);
       }
    }
    else if(actionval=="Delete")
    {
        url="../../../../../pms_dcb_mst_tariff?command=delete&tariff_Id="+tariff_Id+"&tariff_Rate="+tariff_Rate+"&Tariff_wef="+Tariff_wef+"&Beneficiary_Type="+Beneficiary_Type;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);
    }
        
}
function rate_change(xmlhttp)
{
	 if(xmlhttp.readyState==4)
	    {
	        if(xmlhttp.status==200)
	        {
	        	
	        	var  baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
	        	var totalrow=baseres.getElementsByTagName("totalrow")[0].firstChild.nodeValue;
	        	if (totalrow > 0)
	        	{  
	        		alert("Tariff rate Revision completed for selected beneficiary type \n  "  );
	        		document.getElementById("pr_status").value = 1;
	          //      window.location="tariff_rate_change.jsp";
	        	}else
	        		{
	        		alert(" No Beneficiary found in this type \n (or) Table already updated "  );
	        	
	        		document.getElementById("pr_status").value = 1;

	        		}
	        }
	    }    
	 
	    
}

function stateChanged()
{
    var baseres,commandres,flagres,recordres;
    if(xmlhttp.readyState==4)
    {
        if(xmlhttp.status==200)
        {
            baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
            commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
            flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(commandres=="add")
            {
                if(flagres=='success')
                {  
                     countinscheck=baseres.getElementsByTagName("countinscheck")[0].firstChild.nodeValue;
                    if(countinscheck==0)
                    {
                    	alert("Successfully Added");
                    	tablebody=document.getElementById("getvaluerows");
                    	var tariff_Id = baseres.getElementsByTagName("tariff_Id")[0].firstChild.nodeValue;
						var datares = baseres.getElementsByTagName("data")[0];
						var dataresone = baseres.getElementsByTagName("dataone")[0];
						var datarestwo = baseres.getElementsByTagName("datatwo")[0];
						var Beneficiary_Type_id = dataresone.getElementsByTagName("Beneficiary_Type_id")[0].firstChild.nodeValue;
						var Beneficiary_Type_desc = dataresone.getElementsByTagName("Beneficiary_Type_desc")[0].firstChild.nodeValue;
						//var Uom_id = datarestwo.getElementsByTagName("Uom_id")[0].firstChild.nodeValue;
						//var uom_val_desc = datarestwo.getElementsByTagName("uom_val_desc")[0].firstChild.nodeValue;
						var tariff_Rate = baseres.getElementsByTagName("tariff_Rate")[0].firstChild.nodeValue;
						var Tariff_wef = baseres.getElementsByTagName("Tariff_wef")[0].firstChild.nodeValue;
						var status = baseres.getElementsByTagName("activestatus")[0].firstChild.nodeValue;
						var tbody = document.getElementById("getvaluerows");
						var rowvalue = document.createElement("TR");
						rowvalue.id = tariff_Id;  
						var tabledata = document.createElement("TD");
						var anc = document.createElement("A");
						var url = "javascript:loadvaluesfromtable('"+ tariff_Id + "')";
						var nameval = document.createTextNode("Edit");
						anc.href = url;
						anc.appendChild(nameval);
						tabledata.appendChild(anc);
						rowvalue.appendChild(tabledata);

						var tabledata0 = document.createElement("TD");
						var tariff_Id = document.createTextNode(tariff_Id);
						tabledata0.appendChild(tariff_Id);
						rowvalue.appendChild(tabledata0);
						var tabledata4A = document.createElement("TD");
						var Beneficiary_Type = document.createTextNode(Beneficiary_Type_desc);
						tabledata4A.appendChild(Beneficiary_Type);
						rowvalue.appendChild(tabledata4A);

						var hiddentext1 = document.createElement("input");
						hiddentext1.type = "hidden";
						hiddentext1.name = "qual1";
						hiddentext1.id = "qual1";
						hiddentext1.text = "qual1";
						hiddentext1.value = Beneficiary_Type_id;
						tabledata4A.appendChild(hiddentext1);

						var tabledata3 = document.createElement("TD");
						var tariff_Rate = document.createTextNode(tariff_Rate);
						tabledata3.appendChild(tariff_Rate);
						rowvalue.appendChild(tabledata3);

						var tabledata5 = document.createElement("TD");
						var Tariff_wef = document.createTextNode(Tariff_wef);
						tabledata5.appendChild(Tariff_wef);
						rowvalue.appendChild(tabledata5);

						var tabledata6 = document.createElement("TD");
			//			var Uom = document.createTextNode(uom_val_desc);
			//			tabledata6.appendChild(Uom);
			//			rowvalue.appendChild(tabledata6);

						var hiddentext2 = document.createElement("input");
						hiddentext2.type = "hidden";
						hiddentext2.name = "qual3";
						hiddentext2.id = "qual3";
						hiddentext2.text = "qual3";
						//hiddentext2.value = Uom_id;
						tabledata6.appendChild(hiddentext2);

						var tabledatastatus = document.createElement("TD");
						if (status == "A") 
						{
							status = "Active";
							var status = document.createTextNode(status);
						} else {
							status = "Defunct";
							var status = document.createTextNode(status);
						}

						tabledatastatus.appendChild(status);
						rowvalue.appendChild(tabledatastatus);

						tablebody.appendChild(rowvalue);
					} else if (countinscheck == 1) {
						alert("Tariff Rate Already Exists for this Beneficiary Type ..\n If New Tariff Rate Is To Be Updated, First Update the Existing Tariff Rate As Defunct");
					}
				} else {
					alert("Not success");
				}
            }
            else if(commandres=="get")
            {
                 if(flagres=='success')
                {
                    tablebody = document.getElementById("getvaluerows");
					var len = baseres.getElementsByTagName("tariff_Id").length;
					for ( var i = 0; i < len; i++) {
						var tariff_Id = baseres.getElementsByTagName("tariff_Id")[i].firstChild.nodeValue;
						var datares = baseres.getElementsByTagName("data")[i];
						var dataresone = baseres.getElementsByTagName("dataone")[i];
						var datarestwo = baseres.getElementsByTagName("datatwo")[i];
						var Beneficiary_Type_id = dataresone.getElementsByTagName("Beneficiary_Type_id")[0].firstChild.nodeValue;
						var Beneficiary_Type_desc = dataresone.getElementsByTagName("Beneficiary_Type_desc")[0].firstChild.nodeValue;
				//		var Uom_id = datarestwo.getElementsByTagName("Uom_id")[0].firstChild.nodeValue;
			//			var uom_val_desc = datarestwo.getElementsByTagName("uom_val_desc")[0].firstChild.nodeValue;
						var tariff_Rate = baseres.getElementsByTagName("tariff_Rate")[i].firstChild.nodeValue;
						var Tariff_wef = baseres.getElementsByTagName("Tariff_wef")[i].firstChild.nodeValue;
						var ag = Tariff_wef.split("-");
						var datefor = ag[2] + "/" + ag[1] + "/" + ag[0];
						var activestatus = baseres.getElementsByTagName("activestatus")[i].firstChild.nodeValue;
						var rowvalue = document.createElement("TR");
						rowvalue.id = tariff_Id;
						var tabledata = document.createElement("TD");
						var anc = document.createElement("A");
						var url = "javascript:loadvaluesfromtable('"+ tariff_Id + "')";
						anc.href = url;
						var nameval = document.createTextNode("Edit");
						anc.appendChild(nameval);
						tabledata.appendChild(anc);
						rowvalue.appendChild(tabledata);

						var tabledata0 = document.createElement("TD");
						var tariff_Id = document.createTextNode(tariff_Id);
						tabledata0.appendChild(tariff_Id);
						rowvalue.appendChild(tabledata0);
						var tabledata4A = document.createElement("TD");
						var Beneficiary_Type = document.createTextNode(Beneficiary_Type_desc);
						tabledata4A.appendChild(Beneficiary_Type);

						var hiddentext1 = document.createElement("input");
						hiddentext1.type = "hidden";
						hiddentext1.name = "qual1";
						hiddentext1.id = "qual1";
						hiddentext1.text = "qual1";
						hiddentext1.value = Beneficiary_Type_id;
						tabledata4A.appendChild(hiddentext1);
						rowvalue.appendChild(tabledata4A);
						var tabledata3 = document.createElement("TD");
						var tariff_Rate = document.createTextNode(tariff_Rate);
						tabledata3.appendChild(tariff_Rate);
						rowvalue.appendChild(tabledata3);

						var tabledata5 = document.createElement("TD");
						var Tariff_wef = document.createTextNode(datefor);
						tabledata5.appendChild(Tariff_wef);
						rowvalue.appendChild(tabledata5);

			//			var tabledata6 = document.createElement("TD");
			//			var Uom = document.createTextNode(uom_val_desc);
			//			tabledata6.appendChild(Uom);

						var hiddentext2 = document.createElement("input");
						hiddentext2.type = "hidden";
						hiddentext2.name = "qual3";
						hiddentext2.id = "qual3";
						hiddentext2.text = "qual3";
					//	hiddentext2.value = Uom_id;
					//	tabledata6.appendChild(hiddentext2);

						//rowvalue.appendChild(tabledata6);

						var tabledata9 = document.createElement("TD");
						if (activestatus == "A") 
						{
							activestatus = "Active";
							var activestatus = document.createTextNode(activestatus);
						} else 
						{
							activestatus = "Defunct";
							var activestatus = document.createTextNode(activestatus);
						}

						tabledata9.appendChild(activestatus);
						rowvalue.appendChild(tabledata9);
						tablebody.appendChild(rowvalue);

					}
                   
                } 
                else
                {
                   
                   alert("Not success");
                }
            }
            else if(commandres=="update")
            {
                if(flagres=='success')
                {
                    alert('Successfully Updated');
					var datares = baseres.getElementsByTagName("data")[0];
					var dataresone = baseres.getElementsByTagName("dataone")[0];
					var datarestwo = baseres.getElementsByTagName("datatwo")[0];
					var Beneficiary_Type_id = dataresone.getElementsByTagName("Beneficiary_Type_id")[0].firstChild.nodeValue;
					var Beneficiary_Type_desc = dataresone.getElementsByTagName("Beneficiary_Type_desc")[0].firstChild.nodeValue;
					//var Uom_id = datarestwo.getElementsByTagName("Uom_id")[0].firstChild.nodeValue;
					//var uom_val_desc = datarestwo.getElementsByTagName("uom_val_desc")[0].firstChild.nodeValue;
					var tariff_Id = baseres.getElementsByTagName("tariff_Id")[0].firstChild.nodeValue;
					var tariff_Rate = baseres.getElementsByTagName("tariff_Rate")[0].firstChild.nodeValue;
					var Tariff_wef = baseres.getElementsByTagName("Tariff_wef")[0].firstChild.nodeValue;
					var status = baseres.getElementsByTagName("activestatus")[0].firstChild.nodeValue;
					var rvar = document.getElementById(tariff_Id);
					var rcells = rvar.cells;              
                  	rcells.item(1).firstChild.nodeValue = tariff_Id;
					rcells.item(2).firstChild.nodeValue = Beneficiary_Type_desc;
					rcells.item(2).lastChild.value = Beneficiary_Type_id;
					rcells.item(3).firstChild.nodeValue = tariff_Rate;
					rcells.item(4).firstChild.nodeValue = Tariff_wef;
				//	rcells.item(5).firstChild.nodeValue = uom_val_desc;
				//	rcells.item(5).lastChild.value = Uom_id;
                    if(status=="A")
                    {
                            status="Active";
                           
                    }
                    else
                    {
                            status="Defunct";
                         
                    }
                    rcells.item(6).firstChild.nodeValue=status;
                }
                 else
                {
                    alert("Not updated");
                }
            }
            else if(commandres=="delete")
            {
                if(flagres=='success')
                {
                    alert('Successfully Deleted');
					var tariff_Id = baseres.getElementsByTagName("tariff_Id")[0].firstChild.nodeValue;
					var tariff_Rate = baseres.getElementsByTagName("tariff_Rate")[0].firstChild.nodeValue;
					var Tariff_wef = baseres.getElementsByTagName("Tariff_wef")[0].firstChild.nodeValue;
			//		var Uom = baseres.getElementsByTagName("Uom")[0].firstChild.nodeValue;
					var Beneficiary_Type = baseres.getElementsByTagName("Beneficiary_Type")[0].firstChild.nodeValue;
					var tbody = document.getElementById("existing");
					var r = document.getElementById(tariff_Id);
					var ri = r.rowIndex;
					tbody.deleteRow(ri);
					document.getElementById('tariff_Id').value = "";
					document.getElementById('tariff_Rate').value = "";
					document.getElementById('Tariff_wef').value = "";
				//	document.getElementById('UOM').value = "";
					document.getElementById('Beneficiary_Type').value = "";
					document.getElementById('status').value = "";
                }
                 else
                {
                    alert("Not deleted");
                }
            }
            
        }
    }
}
function loadvaluesfromtable(r)
{
    document.getElementById("cmdadd").style.display = 'none';
	document.getElementById("cmdupdate").style.display = 'block';
	document.getElementById("cmddelete").style.display = 'block';
	document.getElementById("cmdupdate").style.display = 'inline';
	document.getElementById("cmddelete").style.display = 'inline';
	var rvar = document.getElementById(r);
	var rcells = rvar.cells;
	document.getElementById("statusdiv").style.display = 'block';
	document.getElementById("statusdiv_name").style.display = 'block';
	document.getElementById('tariff_Id').value = rcells.item(1).firstChild.nodeValue;
	document.getElementById('tariff_Rate').value = rcells.item(3).firstChild.nodeValue;
	document.getElementById('Beneficiary_Type').value = rcells.item(2).lastChild.value;
	document.getElementById('Tariff_wef').value = rcells.item(4).firstChild.nodeValue;
//	document.getElementById('UOM').value = rcells.item(5).lastChild.value;
	statevalue = rcells.item(5).firstChild.nodeValue
	if (statevalue == "Active")
	{
        document.getElementById('status').value="A";
    }
    else
    {
        document.getElementById('status').value="D";
    }
}
function bentypecheck()
{
		    var Beneficiary_Type=document.getElementById("Beneficiary_Type").value;
		    var xmlhttp=GetXmlHttpObject();
		    if (xmlhttp==null)
		     {
		         alert ("Your browser does not support AJAX!");
		         return;
		     }
		    url="../../../../../pms_dcb_mst_tariff?command=bentypecheck&Beneficiary_Type="+Beneficiary_Type;
		    url=url+"&sid="+Math.random();
		    xmlhttp.open("GET",url,true);
		    xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadbentypecheck(xmlhttp);
            }
		    xmlhttp.send(null);
}
function stateChangedloadbentypecheck(xmlhttp)
{
    var baseres,commandres,flagres,recordres;
    if(xmlhttp.readyState==4)
    {
        if(xmlhttp.status==200)
        {
            baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
            commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
            flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(commandres=="bentypecheck")
            {
                if(flagres=='success')
                {
                    countbentype=baseres.getElementsByTagName("countbentype")[0].firstChild.nodeValue;
                    
                    if(countbentype==1)
                    {
                        alert("Tariff Rate Already Exists for this Beneficiary Type ..\n If New Tariff Rate Is To Be Updated, First Update the Existing Tariff Rate As Defunct");
                    }
                }
            }
        }
    }
}
function refresh()
{
    document.getElementById('tariff_Id').value="";
    document.getElementById('tariff_Rate').value="";
    document.getElementById('Tariff_wef').value="";
 //   document.getElementById('UOM').value="";
    document.getElementById('Beneficiary_Type').value="";
    document.getElementById('status').value="";
    document.getElementById("cmdadd").style.display='block';
    document.getElementById("cmdupdate").style.display='none';
    document.getElementById("cmddelete").style.display='none';
    document.getElementById("cmdadd").style.display='inline';
    
}
function exitwindow()
{
 window.close();
}

function numonly(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode
        if (unicode!=8)//backspace
        { 
            if (unicode<45||unicode>57||unicode==47) 
                return false ;
        }
    }

function validate()
 {
//	alert("inside validation");
    var tariff_Rate=document.getElementById("tariff_Rate").value;
    var Tariff_wef=document.getElementById("Tariff_wef").value;
  //  var UOM=document.getElementById("UOM").value;
    var Beneficiary_Type=document.getElementById("Beneficiary_Type").value;
    var status=document.getElementById("status").value;
  
    if(tariff_Rate=="")
    {
        alert("Enter tariff_Rate");
        return false;
    }
     else if(Beneficiary_Type=="")
    {
        alert("Select Beneficiary_Type");
        return false;
    }
    else if(Tariff_wef=="")
    {
        alert("Enter Tariff_wef");
        return false;
    }
  //  else if(UOM=="")
  //  {
 //       alert("Enter UOM");
 //       return false;
 //   }
    else
    {
        return true;
    }
 }
var message="Permission denied to right click";

	function clickIE4()
	{
		if (event.button==2)
		{
			alert(message);
			return false;
		}
	}
	
function data1()
{
	
	
	
	
}
 
function ben_type_load()
{
	var type;
	if (document.getElementById('Exception_no').checked) {
		type = document.getElementById('Exception_no').value;
		  		}
	if (document.getElementById('Exception_yes').checked) {
		type = document.getElementById('Exception_yes').value;
		  		}
	
	var dv =document.getElementById("off_id").value
	document.getElementById("Div").value=dv
	var url = "../../../../../pms_dcb_mst_tariff?command=typeshow&dv="+dv+"&type="+type;
	var xmlobj2 = createObject();
	xmlobj2.open("GET", url, true);
	xmlobj2.onreadystatechange = function() 
	{
		ben_type_load_process(xmlobj2)
	}
	xmlobj2.send(null);
}
 function ben_type_load_process(xmlobj2)
  {
  	if (xmlobj2.readyState == 4) 
  	{
  		if (xmlobj2.status == 200) 
  		{ 
  			var ben_meter_tbody = document.getElementById("id_tbody");
  			try {
				
				for (t = ben_meter_tbody.rows.length - 1; t >= 0; t--) {
					ben_meter_tbody.deleteRow(0);
				}
			} catch (e) {
			}
  			
  			var bR = xmlobj2.responseXML.getElementsByTagName("response")[0];
  			var len=bR.getElementsByTagName("row")[0].firstChild.nodeValue;
  			
  			// New for hoganikal
  			var type;
  			if (document.getElementById('Exception_no').checked) {
  				type = document.getElementById('Exception_no').value;
  				  		}
  			if (document.getElementById('Exception_yes').checked) {
  				type = document.getElementById('Exception_yes').value;
  				  		}
  		
  			if(type == "yes")
  				{
  				for (i = 0; i < len; i++)
  					{
    	
  				var new_row = cell("TR", "", "", "row" +(i+1),(i+1),2,"","", "", "", "", "", "");
  				var ben_type_desc = bR.getElementsByTagName("ben_type_desc")[i].firstChild.nodeValue;
  				var ben_type_id = bR.getElementsByTagName("ben_type_id")[i].firstChild.nodeValue;
  	//	     	var TARIFF_RATE = bR.getElementsByTagName("TARIFF_RATE")[i].firstChild.nodeValue;
  		//		var TARIFF_WEF = bR.getElementsByTagName("TARIFF_WEF")[i].firstChild.nodeValue;
  				var EXISTINGRATE= bR.getElementsByTagName("EXISTINGRATE")[i].firstChild.nodeValue;
  				var sno_cell = cell("TD", "input", "label", "select" ,(i+1), 2, "", "readonly", "readonly", "2%", "readonly", "readonly", "readonly");
  				var ben_type_desc_cell = cell("TD", "label", "", "ben_type_desc"+(i+1),ben_type_desc,2,"label", "", "", "90%", "left", "", "");
  				var ben_type_id_cell = cell("TD", "input", "hidden", "ben_type_id"+(i+1),ben_type_id,2,"label", "", "", "90%", "", "", "");
  			//	var TARIFF_WEF_cell = cell("TD", "label", "", "TARIFF_WEF"+(i+1),TARIFF_WEF,2,"label", "", "", "90%", "center", "", "");
  				var button_cell = cell("TD", "input", "button", "submit" + (i + 1),"Yes", 7, "fb3", "", "text-align: center", "10%", "right","onclick", "doratecharge_hog('newrate',"+(i+1)+")");
  				var EXISTINGRATE_cell = cell("TD", "label", "", "EXISTINGRATE"+(i+1),EXISTINGRATE,2,"label", "", "", "90%", "right", "", "");
  				var TARIFF_RATE_cell = cell("TD", "input", "", "TARIFF_RATE"+(i+1),"",2,"label", "", "", "90%", "right", "", "");
  		//		var TARIFF_RATE_cell = cell("TD", "input", "", "TARIFF_RATE"+(i+1),TARIFF_RATE,2,"label", "", "", "90%", "right", "", "");

  				
  				new_row.appendChild(sno_cell);  
  				new_row.appendChild(ben_type_desc_cell);  
  				new_row.appendChild(ben_type_id_cell);
  				new_row.appendChild(EXISTINGRATE_cell);
  				new_row.appendChild(TARIFF_RATE_cell);
  			//	new_row.appendChild(TARIFF_WEF_cell);
  				new_row.appendChild(button_cell);
  				ben_meter_tbody.appendChild(new_row);	 
  				
  				}} else
  					
  			{	
  			  			
  			for (i = 0; i < len; i++)
  			{  
  				var new_row = cell("TR", "", "", "row" +(i+1),(i+1),2,"","", "", "", "", "", "");
  				var ben_type_desc = bR.getElementsByTagName("ben_type_desc")[i].firstChild.nodeValue;
  				var ben_type_id = bR.getElementsByTagName("ben_type_id")[i].firstChild.nodeValue;
  				var TARIFF_RATE = bR.getElementsByTagName("TARIFF_RATE")[i].firstChild.nodeValue;
  				var TARIFF_WEF = bR.getElementsByTagName("TARIFF_WEF")[i].firstChild.nodeValue;
  				var EXISTINGRATE= bR.getElementsByTagName("EXISTINGRATE")[i].firstChild.nodeValue;
  				var sno_cell = cell("TD", "input", "label", "select" ,(i+1), 2, "", "readonly", "readonly", "2%", "readonly", "readonly", "readonly");
  				var ben_type_desc_cell = cell("TD", "label", "", "ben_type_desc"+(i+1),ben_type_desc,2,"label", "", "", "90%", "left", "", "");
  				var ben_type_id_cell = cell("TD", "input", "hidden", "ben_type_id"+(i+1),ben_type_id,2,"label", "", "", "90%", "", "", "");
  				var TARIFF_WEF_cell = cell("TD", "label", "", "TARIFF_WEF"+(i+1),TARIFF_WEF,2,"label", "", "", "90%", "center", "", "");
  				if (EXISTINGRATE!=0)
  				{
  					var button_cell = cell("TD", "input", "button", "submit" + (i + 1),"Yes", 7, "fb3", "", "text-align: center", "10%", "right","onclick", "doratecharge('newrate',"+(i+1)+")");
  					var EXISTINGRATE_cell = cell("TD", "label", "", "EXISTINGRATE"+(i+1),EXISTINGRATE,2,"label", "", "", "90%", "right", "", "");
  					var TARIFF_RATE_cell = cell("TD", "label", "", "TARIFF_RATE"+(i+1),TARIFF_RATE,2,"label", "", "", "90%", "right", "", "");
  				}
  				else
  				{
  					var button_cell = cell("TD", "input", "hidden", "submit" + (i + 1),"Yes", 7, "fb3", "", "text-align: center", "10%", "right","onclick", "doratecharge('newrate',"+(i+1)+")");
  					var EXISTINGRATE_cell = cell("TD", "label", "", "EXISTINGRATE"+(i+1),TARIFF_RATE,2,"label", "", "", "90%", "right", "", "");
  					var TARIFF_RATE_cell = cell("TD", "label", "", "TARIFF_RATE"+(i+1),"-",2,"label", "", "", "90%", "right", "", "");	
  				}
  				new_row.appendChild(sno_cell);  
  				new_row.appendChild(ben_type_desc_cell);  
  				new_row.appendChild(ben_type_id_cell);
  				new_row.appendChild(EXISTINGRATE_cell);
  				new_row.appendChild(TARIFF_RATE_cell);
  			//	new_row.appendChild(TARIFF_WEF_cell);
  				new_row.appendChild(button_cell);
  				ben_meter_tbody.appendChild(new_row);	 
  			}
  			
  		}  }
  	}
  }
  

	function clickNS4(e)
	{
		if (document.layers||document.getElementById&&!document.all)
		{
			if (e.which==2||e.which==3)
			{
				alert(message);
				return false;
			}
		}
	}
	
//upto these
if (document.layers){
document.captureEvents(Event.MOUSEDOWN);
document.onmousedown=clickNS4;
}
else if (document.all&&!document.getElementById){
document.onmousedown=clickIE4;
}

document.oncontextmenu=new Function("alert(message);return false")