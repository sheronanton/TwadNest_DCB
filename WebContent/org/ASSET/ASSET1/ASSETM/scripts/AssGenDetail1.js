var xmlHttp;
function insert() 
{ 
	var count = document.getElementById('count_id1').value;
    if(count==1){
    }
    else{
	var flag=0;
	flag=val_check('pid','Enter  population present value');
	flag=val_check('iid','Enter  population intermediate value');
	flag=val_check('uid','Enter  population ultimate value');
	flag=val_check('tid','Enter  town panchayat');
	flag=val_check('rid','Enter  rural habitation');
	flag=val_check('sid','Enter present actual supply');
	flag=val_check('did','Enter intermediate daily requirement');
	flag=val_check('fid','Enter  ultimate daily requirement ');
	flag=val_check('mid','Enter  pumping intermediate rate');
	flag=val_check('nid','Enter  pumping ultimate rate');
	flag=val_check('oid','Enter  pumping Present rate');
	flag=val_check('reid','Enter remarks');
	flag=val_check('hr_id','Enter Horse Power');
	flag=val_check('corid','Enter Corporation value');
	flag=val_check('munid','Enter Municipality value');
	flag=val_check('yr_pid','Enter Present year');
	flag=val_check('yr_iid','Enter Intermediate Year');
	flag=val_check('yr_uid','Enter Ultimate Year');
	flag=val_check('yr_iid','Enter Intermediate Year');
	var sno = document.getElementById('sno_id').value;
	var year = document.getElementById('yid').value;
	var sanno = document.getElementById('sanno').value;
	var prepop = document.getElementById('pid').value;
	var intpop = document.getElementById('iid').value;
	var ultpop = document.getElementById('uid').value;
	var tp = document.getElementById('tid').value;
	var hab = document.getElementById('rid').value;
	var actsup = document.getElementById('sid').value;
	var intdly = document.getElementById('did').value;
	var ultdly = document.getElementById('fid').value;	
	var intrte = document.getElementById('mid').value;
	var ultrte = document.getElementById('nid').value;
	var remark = document.getElementById('reid').value;
	var prepopyr = document.getElementById('yr_pid').value;
	var intpopyr = document.getElementById('yr_iid').value;
	var ultpopyr = document.getElementById('yr_uid').value;
	var hrs = document.getElementById('hr_id').value;
	var int_hrs = document.getElementById('int_hr').value;
	var ult_hrs = document.getElementById('ult_hr').value;
	var prerte = document.getElementById('oid').value;
	var corp = document.getElementById('corid').value;
    var muni = document.getElementById('munid').value;
    xmlHttp = createObject();
	var url = "";
	url = "../../../../../AssGenDetail1?comment=add&sno=" + sno + "&count="+ count +  "&year="+year +"&prepop=" + prepop
			+ "&intpop=" + intpop + "&ultpop=" + ultpop +  "&corp=" + corp +"&muni=" + muni +"&sanno="+sanno
			+ "&tp=" + tp +"&hab=" + hab + "&actsup=" + actsup + "&intdly=" + intdly 
			+ "&ultdly=" + ultdly +"&intrte=" + intrte + "&ultrte=" + ultrte
			+ "&remark=" + remark + "&prepopyr="+ prepopyr + "&intpopyr=" + intpopyr + "&ultpopyr=" + ultpopyr 
			+ "&int_hrs="+ int_hrs + "&ult_hrs="+ ult_hrs + "&hrs="+ hrs +"&prerte=" + prerte+"&t="+Math.random()  ;
	 
	xmlHttp.open("GET", url, true);
	xmlHttp.onreadystatechange = function() {
	
		process_val(xmlHttp);
	}
	xmlHttp.send(null);
    }
}

function process_val(xmlHttp) 
{ 
	if (xmlHttp.readyState == 4) 
	{
		if (xmlHttp.status == 200) 
		{
			result_val(xmlHttp);
		}
	}
}

function result_val(xmlHttp)
    {
		var BR = xmlHttp.responseXML.getElementsByTagName("response")[0];
		var row = BR.getElementsByTagName("row")[0].firstChild.nodeValue;
		if (row > 0)
				{
					alert("Record saved Successfully");
					document.getElementById('save').style.visibility="hidden";
				//	var count = document.getElementById('count_id').value;
				   //window.location.reload(count);
					display_();
					clear();
				} 
				else
				{
					alert("fail");
				}
    }

function update_val() 
		  {
			var count = document.getElementById('count_id1').value;
			var sno = document.getElementById('no_id').value;
			var year = document.getElementById('yid').value;
			var prepop = document.getElementById('pid').value;
			var intpop = document.getElementById('iid').value;
			var ultpop = document.getElementById('uid').value;	
			var tp = document.getElementById('tid').value;
			var hab = document.getElementById('rid').value;
			var actsup = document.getElementById('sid').value;
			var intdly = document.getElementById('did').value;
			var ultdly = document.getElementById('fid').value;	
			var intrte = document.getElementById('mid').value;
			var ultrte = document.getElementById('nid').value;
			var remark = document.getElementById('reid').value;
			var prepopyr = document.getElementById('yr_pid').value;
			var intpopyr = document.getElementById('yr_iid').value;
			var ultpopyr = document.getElementById('yr_uid').value;
			var hrs = document.getElementById('hr_id').value;
			var int_hrs = document.getElementById('int_hr').value;
			var ult_hrs = document.getElementById('ult_hr').value;
			var prerte = document.getElementById('oid').value;
			var corp = document.getElementById('corid').value;
			var muni = document.getElementById('munid').value;
			
			xmlHttp = createObject();
			var url = "";
			url = "../../../../../AssGenDetail1?comment=update&year="+year +"&prepop=" + prepop
					+ "&intpop=" + intpop + "&ultpop=" + ultpop +  "&corp=" + corp +"&muni=" + muni 
					+ "&tp=" + tp +"&hab=" + hab + "&actsup=" + actsup + "&intdly=" + intdly 
					+ "&ultdly=" + ultdly +"&intrte=" + intrte + "&ultrte=" + ultrte
					+ "&remark=" + remark + "&prepopyr="+ prepopyr + "&intpopyr=" + intpopyr + "&ultpopyr=" + ultpopyr 
					+ "&int_hrs="+ int_hrs + "&ult_hrs="+ ult_hrs + "&hrs="+ hrs +"&prerte=" + prerte + "&sno=" + sno+"&count=" + count+"&t="+Math.random();
			 
			xmlHttp.open("GET", url, true);
			xmlHttp.onreadystatechange = function() {
			process_update(xmlHttp);
			}
			
			xmlHttp.send(null);
}
function process_update(xmlHttp) { 
	if (xmlHttp.readyState == 4) 
	{
		if (xmlHttp.status == 200) {
			result_update(xmlHttp);
		}
	}
}
function result_update(xmlHttp){
	var BR = xmlHttp.responseXML.getElementsByTagName("response")[0];
	var row = BR.getElementsByTagName("row")[0].firstChild.nodeValue;
	if (row > 0)
	{
		alert("Updated Successfully");
		 clear();
		 display_();
	}
	else
	{
		alert("fail");
	}
}
function display_()
  {
			xmlHttp=createObject();
			disabled1("update");
			disabled1("delete");
			var count = document.getElementById('count_id1').value;
	
			   if(count==1)
				{
					 document.getElementById('save').style.visibility="hidden";
			    }
			   else
			    {
				   document.getElementById('save').style.visibility="visible";
			    }
			 
			   var sno = document.getElementById('sno_id').value;
			var url="";
			url = "../../../../../AssGenDetail1?comment=view&sno=" + sno + "&count="+ count + "&t="+Math.random();
			//alert(url);
			xmlHttp.open("GET", url, true);
			xmlHttp.onreadystatechange=function()
			  {
				view_process(xmlHttp);
			  }
			xmlHttp.send(null);
}
function view_process(xmlHttp)
{ 
	if(xmlHttp.readyState == 4){
		if(xmlHttp.status == 200){
			result_checking(xmlHttp);
		}
	}
}
function result_checking(xmlHttp)
{ 
	 var tbody = document.getElementById('tblList');
	 for (t = tbody.rows.length - 1; t >= 0; t--) 
		{
		 tbody.deleteRow(0);
		}
	 var bR = xmlHttp.responseXML.getElementsByTagName("response")[0];
	 //alert("bR"+bR);
     var len=bR.getElementsByTagName("PMS_ASSET_GEN_SNO").length;
     //alert("len"+len);
 	document.getElementById('count_id1').value=len;
 	  if(len==1)
		{
 		  document.getElementById('save').style.visibility="hidden";
	    }
	   else
	    {
		   
		   document.getElementById('save').style.visibility="visible";
	    }
		for (var i=0;i<len;i++)
		{ 
		    var new_row=cell("TR","","","row"+(i+1),(i+1),2,"","","","","","","");
		    var PMS_ASSET_GEN_SNO=xmlValue(bR,"PMS_ASSET_GEN_SNO",i);
			var OFFICE_ID=xmlValue(bR,"OFFICE_ID",i);
			var PROJECT_ID=xmlValue(bR,"PROJECT_ID",i);
			var SCH_SNO=xmlValue(bR,"SCH_SNO",i);
			var ADMIN_SANCTION_SNO=xmlValue(bR,"ADMIN_SANCTION_SNO",i);
			var YEAR_COMMISION=xmlValue(bR,"YEAR_COMMISION",i);
			var PRESENT_YR=xmlValue(bR,"PRESENT_YR",i);
			var POP_PRESENT=xmlValue(bR,"POP_PRESENT",i);
			var POP_INTER=xmlValue(bR,"POP_INTER",i);
			var POP_ULTIMATE=xmlValue(bR,"POP_ULTIMATE",i);
			var SUPPLY_TP=xmlValue(bR,"SUPPLY_TP",i);
			var SUPPLY_HAP=xmlValue(bR,"SUPPLY_HAB",i);
			var DAILY_REQ_INTER=xmlValue(bR,"DAILY_REQ_INTER",i);
			var DAILY_REQ_ULTIMATE=xmlValue(bR,"DAILY_REQ_ULTIMATE",i);
			var RATE_PUMP_INTER=xmlValue(bR,"RATE_PUMP_INTER",i);
			var RATE_PUMP_ULTIMATE=xmlValue(bR,"RATE_PUMP_ULTIMATE",i);
			var REMARKS=xmlValue(bR,"REMARKS",i);
			var UPDATED_BY_USER_ID=xmlValue(bR,"UPDATED_BY_USER_ID",i);
			var UPDATED_TIME=xmlValue(bR,"UPDATED_TIME",i);
			var SUPPLY_ACTUAL=xmlValue(bR,"SUPPLY_ACTUAL",i);
			var POP_PRESENT_YR=xmlValue(bR,"PRESENT_YR",i);
			var POP_INTER_YR=xmlValue(bR,"INTER_YR",i);
			var POP_ULTIMATE_YR=xmlValue(bR,"ULTIMATE_YR",i);
			var HRS_PUMPING=xmlValue(bR,"HRS_PUMPING",i);
			var RATE_PUMP_PRESENT=xmlValue(bR,"RATE_PUMP_PRESENT",i);
			var SUPPLY_CORP=xmlValue(bR,"SUPPLY_CORP",i);
			var SUPPLY_MUN=xmlValue(bR,"SUPPLY_MUN",i);
			var HRS_PUMPING_INTER=xmlValue(bR,"HRS_PUMPING_INTER",i);
			var HRS_PUMPING_ULTIMATE=xmlValue(bR,"HRS_PUMPING_ULTIMATE",i);
			check_val_zero(YEAR_COMMISION,'yid');
			check_val_zero(POP_PRESENT,'pid');
			check_val_zero(POP_PRESENT_YR,'yr_pid');
			check_val_zero(POP_INTER,'iid');
			check_val_zero(POP_INTER_YR,'yr_iid');
			check_val_zero(POP_ULTIMATE,'uid');
			check_val_zero(POP_ULTIMATE_YR,'yr_uid');
			check_val_zero(HRS_PUMPING,'hr_id');
			check_val_zero(SUPPLY_CORP,'corid');
			check_val_zero(SUPPLY_MUN,'munid');
			check_val_zero(SUPPLY_TP,'tid');
			check_val_zero(SUPPLY_HAP,'rid');
			check_val_zero(SUPPLY_ACTUAL,'sid');
			check_val_zero(DAILY_REQ_INTER,'did');
			check_val_zero(DAILY_REQ_ULTIMATE,'fid');
			check_val_zero(RATE_PUMP_PRESENT,'oid');
			check_val_zero(RATE_PUMP_INTER,'mid');
			check_val_zero(RATE_PUMP_ULTIMATE,'nid');
			check_val_zero(REMARKS,'reid');
			check_val_zero(HRS_PUMPING_INTER,'int_hr');
			check_val_zero(HRS_PUMPING_ULTIMATE,'ult_hr');
			var sno_cell=cell("TD","input","hidden","PMS_ASSET_GEN_SNO"+(i+1),PMS_ASSET_GEN_SNO,2,"tdText","","","2%","","","");
			var href_cell = cell("TD","A","EDIT","EDIT","EDIT",2,"","javascript:processaction("+(i + 1)+")","", "5%", "", "", "");     
			var ofid_cell=cell("TD","input","hidden","OFFICE_ID"+(i+1),OFFICE_ID,2,"","","","","","","");
			var projid_cell=cell("TD","input","hidden","PROJECT_ID"+(i+1),PROJECT_ID,2,"","","","","","","");
			var schsno_cell=cell("TD","input","hidden","SCH_SNO"+(i+1),SCH_SNO,2,"","","","","","","");
			var sanction_cell=cell("TD","input","hidden","ADMIN_SANCTION_SNO"+(i+1),ADMIN_SANCTION_SNO,2,"","","","","","","");
			var year_cell=cell("TD","label","","YEAR_COMMISION"+(i+1),YEAR_COMMISION,2,"","","","","","","");
			var pre_year_cell=cell("TD","label","","PRESENT_YR"+(i+1),PRESENT_YR,2,"","","","","","","");
			var actsup_cell=cell("TD","label","","SUPPLY_ACTUAL"+(i+1),SUPPLY_ACTUAL,2,"","","","","","","");
			var pumppre_cell=cell("TD","label","","RATE_PUMP_PRESENT"+(i+1),RATE_PUMP_PRESENT,2,"","","","","","","");
			var HRS_cell=cell("TD","label","","HRS_PUMPING"+(i+1),HRS_PUMPING,2,"","","","","","","");
			
			
		    var poppresent_cell=cell("TD","label","","POP_PRESENT"+(i+1),POP_PRESENT,2,"","","","","","","");
		    var popinter_cell=cell("TD","label","","POP_INTER"+(i+1),POP_INTER,2,"","","","","","","");
            var popultimate_cell=cell("TD","label","","POP_ULTIMATE"+(i+1),POP_ULTIMATE,2,"","","","","","","");
            var tp_cell=cell("TD","label","","SUPPLY_TP"+(i+1),SUPPLY_TP,2,"","","","","","","");
            var hap_cell=cell("TD","label","","SUPPLY_HAB"+(i+1),SUPPLY_HAP,2,"","","","","","","");
            var reqint_cell=cell("TD","label","","DAILY_REQ_INTER"+(i+1),DAILY_REQ_INTER,2,"","","","","","","");
            var reqult_cell=cell("TD","label","","DAILY_REQ_ULTIMATE"+(i+1),DAILY_REQ_ULTIMATE,2,"","","","","","","");
            var pumpint_cell=cell("TD","label","","RATE_PUMP_INTER"+(i+1),RATE_PUMP_INTER,2,"","","","","","","");
            var pumpult_cell=cell("TD","label","","RATE_PUMP_ULTIMATE"+(i+1),RATE_PUMP_ULTIMATE,2,"","","","","","","");
            var remark_cell=cell("TD","input","hidden","REMARKS"+(i+1),REMARKS,2,"","","","","","","");
            var userid_cell=cell("TD","input","hidden","UPDATED_BY_USER_ID"+(i+1),UPDATED_BY_USER_ID,2,"","","","","","","");
            var time_cell=cell("TD","input","hidden","UPDATED_TIME"+(i+1),UPDATED_TIME,2,"","","","","","","");
                     
            var poppresentyr_cell=cell("TD","input","hidden","PRESENT_YR"+(i+1),POP_PRESENT_YR,2,"","","","","","","");
            var popinteryr_cell=cell("TD","input","hidden","INTER_YR"+(i+1),POP_INTER_YR,2,"","","","","","","");
            var popultimateyr_cell=cell("TD","input","hidden","ULTIMATE_YR"+(i+1),POP_ULTIMATE_YR,2,"","","","","","","");
            
            
            var corp_cell=cell("TD","label","","SUPPLY_CORP"+(i+1),SUPPLY_CORP,2,"","","","","","","");
            var muni_cell=cell("TD","label","","SUPPLY_MUN"+(i+1),SUPPLY_MUN,2,"","","","","","","");
            var hr_inter=cell("TD","input","hidden","HRS_PUMPING_INTER"+(i+1),HRS_PUMPING_INTER,2,"","","","","","","");
            var hr_ultimate=cell("TD","input","hidden","HRS_PUMPING_ULTIMATE"+(i+1),HRS_PUMPING_ULTIMATE,2,"","","","","","","");
            new_row.appendChild(sno_cell);
			new_row.appendChild(href_cell);
        	new_row.appendChild(year_cell);
        	      	
        	new_row.appendChild(pre_year_cell);
        	new_row.appendChild(poppresent_cell);
        	new_row.appendChild(actsup_cell);		
        	new_row.appendChild(pumppre_cell);
        	new_row.appendChild(HRS_cell);
             	
			new_row.appendChild(poppresentyr_cell); 
			new_row.appendChild(popinter_cell);
			new_row.appendChild(popinteryr_cell);
			new_row.appendChild(popultimate_cell);
			new_row.appendChild(popultimateyr_cell);
			
			
			
			new_row.appendChild(corp_cell);
			new_row.appendChild(muni_cell);
			new_row.appendChild(tp_cell);
			new_row.appendChild(hap_cell);
			
		    new_row.appendChild(reqint_cell);
			new_row.appendChild(reqult_cell);	
			
			new_row.appendChild(pumpint_cell);
			new_row.appendChild(pumpult_cell);
			new_row.appendChild(remark_cell);
			new_row.appendChild(hr_inter);
			new_row.appendChild(hr_ultimate);
            tbody.appendChild(new_row);
				}
		}

function delete_val()
{           var count = document.getElementById('count_id1').value;
			var sno=document.getElementById("sno_id").value;
			var sno1=document.getElementById("no_id").value;
			var url = "";
			xmlHttp = createObject();
			url = "../../../../../AssGenDetail1?comment=delete&sno=" + sno +"&sno1="+ sno1 +"&count="+ count +"&t="+Math.random()  ;
			//alert(url);
			xmlHttp.open("GET", url, true);
			xmlHttp.onreadystatechange = function() {
				delete_process(xmlHttp);
			}
			xmlHttp.send(null);
	 }
function delete_process(xmlHttp)
{
	
	if(xmlHttp.readyState == 4){
		if(xmlHttp.status == 200){
			delete_result(xmlHttp);
		}
	}
}
function delete_result(xmlHttp) 
{
	var BR = xmlHttp.responseXML.getElementsByTagName("response")[0];
	var row = BR.getElementsByTagName("row")[0].firstChild.nodeValue;
	if (row > 0)
	{	
		alert("Delete Successfully"); 
		document.getElementById('save').visibility="visible";
		 clear();
		display_();
	}
	else
	{   alert("fail");
	}
}

function clear()
	{
document.getElementById('count_id').re	
document.getElementById('no_id').value="";
 document.getElementById('yid').value="";
 document.getElementById('pid').value="";
 document.getElementById('iid').value="";
 document.getElementById('uid').value="";
document.getElementById('tid').value="";
 document.getElementById('rid').value="";
document.getElementById('sid').value="";
 document.getElementById('did').value="";
 document.getElementById('fid').value="";
 document.getElementById('mid').value="";
document.getElementById('nid').value="";
document.getElementById('reid').value="";
document.getElementById('oid').value="";
document.getElementById('yr_pid').value="";	
document.getElementById('yr_iid').value="";	
document.getElementById('yr_uid').value="";	
document.getElementById('hr_id').value="";	
document.getElementById('int_hr').value="";	
document.getElementById('ult_hr').value="";	
document.getElementById('corid').value="";	
document.getElementById('munid').value="";	
	}
function validateDecimal(id){
	var field = id;
	var valo = new String();
	var numere = "0123456789.";
	var chars = field.value.split("");
	for (i = 0; i < chars.length; i++){
		if (numere.indexOf(chars[i]) != -1) 
			valo += chars[i];
		else{
			alert("Only Numbers And '.' !");
		}
	}
	if (field.value != valo) field.value = valo; 
}
function validateNumber(id){
	var field = id;
	var valo = new String();
	var numere = "0123456789";
	var chars = field.value.split("");
	for (i = 0; i < chars.length; i++){
		if (numere.indexOf(chars[i]) != -1) 
			valo += chars[i];
		else{
			alert("Numbers Only Allowed !");
		}
	}
	if (field.value != valo) field.value = valo; 
}
function checkDecimal(id){
	var n=new RegExp("/^\d{4}+(.\d{0,2})?$/");
			if(id.value.match(n))
			{
				return true;
			}
			else
			{
				alert("enter decimal value");
				return false;
			}
}	
//function test(str)
// {
	// str=alltrim(str);
// / return /^\$?[1-9][0-9]{0,2}(,[0-9]{3})*(\.[0-9]{2})?$/.test(str);
	
// }
function check(id) 
{
	var val=
	id.value=Math.round(val*100)/100;
	if(!(val<10000))
	{
		alert("enter 4 digit value only");
	}
}
function yearcheck(id) 
{
	var len=id.value.length;
	if(len<4)
	{
		alert(" Please Check Entered Year... ");
	}
}

  

function Decimalvalidation(control)
{
//alert('regular expression');
var rgexp=("/^\d{4}(.\d{0,2})?$/");


var input=document.getElementById(control).value;
if(input.match(rgexp))
{
alert("ok");}
else{
alert("no");}
}
//******************************************************************
function val(tb,len,dec)
{
	var v=tb.value;
	var regex='^[0-9]{1,'+len+'}[.][0-9]{1,'+dec+'}$';
	if(v.search(regex)==-1)
	{
		 
		return false;
	}
	return true;
	
	
}



function val_check(id,msg)
{
	var field=document.getElementById(id).value; 
	
	if (field == null || field == "") 
	{
		alert(msg);
		document.getElementById(id).focus;
		return 1;
	}else
	{ 
		return 0;
		
	}
}
function change_clr()
{
	document.getElementById('urban_val').value=="";
}

function xml_val_check(id,val)
{
	if(val=="0")
	{
		document.getElementById('urban_val').value="";	
	}
	else
	{
		document.getElementById('urban_val').value=val;	
	}
}
