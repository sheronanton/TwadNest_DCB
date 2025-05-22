function OtherChargesObject()
{
	var req;
	if(window.XMLHttpRequest)
	{
		try { req = new XMLHttpRequest(); }
		catch(e) { req = false; }
	}
	else if(window.ActiveXObject)
	{
		try {
			 req = new ActiveXObject("Msxml2.XMLHTTP"); 
		    }
		catch(e)
		{
			try {
				 req = new ActiveXObject("Microsoft.XMLHTTP"); 
			  }
			catch(e) { req = false; }
		}
	}
	 
	return req;
	
}

var process_code = 0;
var command = "";

function charges( )
{
	var url = "";
	var xmlobj = "";
	xmlobj = OtherChargesObject();
	url = "";
			var row_cnt = document.getElementById("row_cnt").value;
			var month = document.getElementById("month").value;
			var year = document.getElementById("year").value;
			
		//	var def_id = document.getElementById("def_id").value;
        //    alert("def_id"+def_id);
            
			var param = "?month=" + month + "&year=" + year;
			 document.getElementById("pr_status").value=0;;
			for (   i = 1; i <= parseInt(row_cnt); i++) 
			{
				var BENEFICIARY_SNO = document.getElementById("bsno" + i).value;
				var SCH_SNO = 0;// document.getElementById("cmbOffice_code").value;
				var VOUCHER_NO = document.getElementById("no" + i).value;
				var VOUCHER_DATE = document.getElementById("vdate" + i).value;
				var ACCOUNT_HEAD_CODE = document.getElementById("aco" + i).value;
				var CR_DR_INDICATOR = document.getElementById("crdr" + i).value;
				var AMOUNT = document.getElementById("crdramt" + i).value;
				var CONFIRM_FLAG = document.getElementById("ck" + i).checked;
				var PARTICULARS = document.getElementById("pr" + i).value;
			 if (CONFIRM_FLAG==true) {
				param  =param+ "&BENEFICIARY_SNO"+i+"="+BENEFICIARY_SNO+"&VOUCHER_NO"+i+"="+VOUCHER_NO+ "&VOUCHER_DATE"+i+"="+VOUCHER_DATE;
				param = param+"&ACCOUNT_HEAD_CODE"+i+"="+ACCOUNT_HEAD_CODE+"&CR_DR_INDICATOR"+i+"=" + CR_DR_INDICATOR;
				param = param+"&AMOUNT"+i+"=" + AMOUNT + "&PARTICULARS"+i+"='" + PARTICULARS;
				param =param+ "'&CONFIRM_FLAG"+i+"=" + CONFIRM_FLAG;
			 }				 
			}
			url =url+ "../../../../../other_charges_collection" + param+"&row_cnt="+row_cnt; 
		xmlobj.open("GET", url, true)  ;
		xmlobj.onreadystatechange = function() {
			otherchargesprocess(xmlobj, process_code);
		}
		xmlobj.send(null);
}

function otherchargesprocess(xmlobj, id) {

	if (xmlobj.readyState==4)
	 {	   
		if (xmlobj.status==200)
	    {
			report_show_othercharges(xmlobj, id)
	    }
	 }
}
function report_show_othercharges(xmlobj, id) {
	var bR;
	 try 
	  { 
		   bR=xmlobj.responseXML.getElementsByTagName("chargeresponse")[0];
	  }catch (e) {
	}
	
		 document.getElementById("pr_status").value=1;;
		 var ins_row=bR.getElementsByTagName("insrow")[0].firstChild.nodeValue;
		  if (ins_row>0) {
			  alert(" Journal Adjustments Included.......Please Click Generate Demand");
			  window.location.reload();
			  parent.window.location.reload(true);   
		  }  
		  else
		  {
			  var month=document.getElementById("month");
			  var year=document.getElementById("year");
			  alert("Journal Adjustments Already Included for "+ month.options[month.selectedIndex].text+" / "+year.options[year.selectedIndex].text);
		  } 
}