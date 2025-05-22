var xmlHttp1 = null, xmlHttpobj = null, xmlHttpreq = null, xmlHttp = null;
var sel_len = 0;
var counter = 0;
var field = new Array();
var winemp = null;
var winemp1 = null;
var checkState = "Before";
var common = "";
var length = 0;
var flag, command, response = "";
var pagesize = 10;
var seq = 0;

var txtid1;
var txtid2;

function getTransport() {
	var req = false;
	try {
		req = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e) {
		try {
			req = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			req = false;
		}
	}
	if (!req && typeof XMLHttpRequest != 'undefined') {
		req = new XMLHttpRequest();
	}
	return req;
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



function servicepopupLoanID() {

	if (winemp1 && winemp1.open && !winemp1.closed) {
		winemp1.resizeTo(500, 600);
		winemp1.moveTo(200, 200);
		winemp1.focus();
		return;
	}
	winemp1 = window.open(
			"../../../../../org/HR/HR1/EmployeeMaster/jsps/gpf_subscription_change_popup.jsp",
			"Employeesearch",
			"status=1,height=500,width=600,resizable=YES, scrollbars=yes");
	winemp1.moveTo(250, 250);
	winemp1.focus();
}

function doParentEmp(emp, name, designation) {
//alert(designation   +"   in doParentEmp");
	document.getElementById("txtEmpId").value = emp;
	document.getElementById("comEmpId").value = name;
	document.getElementById("edesg").value = designation;
	//alert(designation);
	callServer('Get', 'null');

}

function doParentLoanID(eff_year,gpf_no,amount,eid, name, designation) {
	
	//alert("inside loadid");
	document.getElementById("eff_year").value = eff_year;
	document.getElementById("txtGpfNo").value = gpf_no;
	document.getElementById("comEmpId").value = name;
	document.getElementById("no").value = designation;
	document.getElementById("designation").value = amount;
	document.getElementById("txtEmpId").value = eid;
	//document.getElementById("designation").value = dess;
	
	//alert(designation);
	//alert(eff_year +""+gpf_no +""+amount +""+eid+""+ name);
	//alert(designation);
	callServer('Get', 'null');
}
function met() {

	xmlhttp = getTransport();
	if (xmlhttp == null) {
		alert("Your borwser doesnot support AJAX");
		return;
	}

	empn = document.getElementById("eid").value;
	var offid = document.getElementById("offid").value;
	if (empn == "") {
	} else {
		var urlhh = "../../../../../EmpSelectionPopupforOffice?Command=loadempedit&txtEmployeeid="
				+ empn;

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4) {
				if (xmlhttp.status == 200) {
					var responsee = xmlhttp.responseXML
							.getElementsByTagName("response")[0];

					var flag = responsee.getElementsByTagName("flag")[0].firstChild.nodeValue;
					if (flag == "failure") {
						document.getElementById('eid').value = "";
						document.getElementById('ename').value = "";
						document.getElementById('edesg').value = "";

						alert("Sorry! Employee ID Not Found");

					} else if (flag == "Success") {

						if (document.getElementById("offid").value == responsee
								.getElementsByTagName("Office_Id")[0].firstChild.nodeValue) {
							document.getElementById("comEmpId").value = responsee
									.getElementsByTagName("comEmpId")[0].firstChild.nodeValue;
							document.getElementById("edesg").value = responsee
									.getElementsByTagName("Designation")[0].firstChild.nodeValue;
						} else {
							alert("Employee ID not belongs to this Office.");
							document.getElementById('eid').value = "";
							document.getElementById('comEmpId').value = "";
							document.getElementById('edesg').value = "";

						}

					}
				}
			}

		};
		xmlhttp.open("GET", urlhh, true);
		xmlhttp.send(null);
	}

	callServer('Get', 'null');

}
function clearAll() {
	
	
	document.Hrm_TransJoinForm.fin_year.selectedIndex = 0;
	document.Hrm_TransJoinForm.eff_month.selectedIndex = 0;
	document.Hrm_TransJoinForm.eff_year.value= 0;
	document.Hrm_TransJoinForm.amount.value="";
		document.Hrm_TransJoinForm.no.value="";
	document.Hrm_TransJoinForm.txtGpfNo.value="";
	document.Hrm_TransJoinForm.date.value="";
	document.Hrm_TransJoinForm.remarks.value="";
	document.Hrm_TransJoinForm.txtEmpId.value="";
	document.Hrm_TransJoinForm.comEmpId.value="";	
	document.Hrm_TransJoinForm.designation.value="";



	}

function checkNull() {

	if (document.Hrm_TransJoinForm.txtGpfNo.value == "") {
		alert("Please Enter the GPF No ");

		return false;
	}
	
	
	if (document.Hrm_TransJoinForm.fin_year.selectedIndex == 0) {
		alert("Select The Financial Year");

		return false;
	}
	

	if (document.Hrm_TransJoinForm.amount.value == "") {
		alert("Please Enter the GPF Subscription Amount ");

		return false;
	}
	if (document.Hrm_TransJoinForm.eff_year.selectedIndex == 0) {
		alert("Select The Year from which it is Effective from");

		return false;
	}
	
	
	
	
	if (document.Hrm_TransJoinForm.date.value == "") {
		alert("Enter The Date ");

		return false;
	}
	if (document.Hrm_TransJoinForm.amount.value == "") {
		alert("Select Transfer Order No ");

		return false;
	}
	
	
	return true;

}

function callServer(command, param) {
//alert("inside update");
	
	var fin_year=document.Hrm_TransJoinForm.fin_year.value;
	var eff_month=document.Hrm_TransJoinForm.eff_month.value;
	var eff_year=document.Hrm_TransJoinForm.eff_year.value;
	var amount=document.Hrm_TransJoinForm.amount.value;
	var no=document.Hrm_TransJoinForm.no.value;
	var txtGpfNo=document.Hrm_TransJoinForm.txtGpfNo.value;
	var date=document.Hrm_TransJoinForm.date.value;
	var remarks=document.Hrm_TransJoinForm.remarks.value;


	if (command == "update") {
		var flag = checkNull();
		if (flag) 
{
			url = "../../../../../GPF_Subscription_Change_update?command=update" + "&fin_year=" + fin_year
					+ "&eff_month=" + eff_month + "&eff_year=" + eff_year
					+ "&amount=" + amount + "&no=" + no
					+ "&txtGpfNo=" + txtGpfNo + "&date=" + date+ "&remarks=" + remarks;
					
					
	//	alert(url);
			var req = getTransport();
			req.open("GET", url, true);
			req.onreadystatechange = function() {
				processResponse(req);
			};
			req.send(null);
		}

	}
	
	else if (command == "validate") {
		var flag = checkNull();
		if (flag) 
{
			url = "../../../../../GPF_Subscription_Change_update?command=validate" + "&fin_year=" + fin_year
					+ "&eff_month=" + eff_month + "&eff_year=" + eff_year
					+ "&amount=" + amount + "&no=" + no
					+ "&txtGpfNo=" + txtGpfNo + "&date=" + date+ "&remarks=" + remarks;
					
					
	
			var req = getTransport();
			req.open("GET", url, true);
			req.onreadystatechange = function() {
				processResponse(req);
			};
			req.send(null);
		}

	}

	else	if (command == "Delete") {
		var flag = checkNull();
		if (flag) {

			url = "../../../../../GPF_Subscription_Change_update?command=Delete" + "&txtGpfNo=" + txtGpfNo+ "&no=" + no;
					
					
					
		//	alert(url);
			var req = getTransport();
			req.open("GET", url, true);
			req.onreadystatechange = function() {
				processResponse(req);
			};
			req.send(null);
		}

	}
	
	else if (command == "Get") {
		var no=document.Hrm_TransJoinForm.no.value;
		var txtGpfNo=document.Hrm_TransJoinForm.txtGpfNo.value;

		
		

		url = "../../../../../GPF_Subscription_Change_update?command=Get&no=" + no;
		//alert(url);		
		
	
		
		var req = getTransport();
		req.onreadystatechange = function() {
			if (req.readyState == 4) {
				if (req.status == 200) {

					// alert(req.responseText);
					
		
					 
					var baseResponse = req.responseXML
							.getElementsByTagName("response")[0];

					var res = baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue;
					if (res == "success") {
						document.Hrm_TransJoinForm.fin_year.value = baseResponse
								.getElementsByTagName("fin_year")[0].firstChild.nodeValue;

						document.Hrm_TransJoinForm.eff_month.value = baseResponse
								.getElementsByTagName("eff_month")[0].firstChild.nodeValue;

						document.Hrm_TransJoinForm.amount.value = baseResponse
								.getElementsByTagName("amount")[0].firstChild.nodeValue;
						document.Hrm_TransJoinForm.no.value = baseResponse
								.getElementsByTagName("no")[0].firstChild.nodeValue;
						document.Hrm_TransJoinForm.txtGpfNo.value = baseResponse
								.getElementsByTagName("txtGpfNo")[0].firstChild.nodeValue;
						document.Hrm_TransJoinForm.date.value = baseResponse
						.getElementsByTagName("date")[0].firstChild.nodeValue;
						
						document.Hrm_TransJoinForm.remarks.value = baseResponse
						.getElementsByTagName("remarks")[0].firstChild.nodeValue;
						
						if(document.Hrm_TransJoinForm.remarks.value='null')
						{
							document.Hrm_TransJoinForm.remarks.value="";
						}
						
						pfid1 = baseResponse
						.getElementsByTagName("PROCESS_FLOW_STATUS_ID")[0].firstChild.nodeValue;
				if ( pfid1 == 'FR') {
					alert("This ID Has been Already Validated");

					
					document.Hrm_TransJoinForm.validate.disabled = true;
					
				

				} else {

				
					document.Hrm_TransJoinForm.validate.disabled = false;
					
				}
					}
				}
				}
			};

		
		req.open("GET", url, true);
		req.send(null);
	}


}

function Exit() {
	self.close();
}

function processResponse(req) {

	if (req.readyState == 4) {

		if (req.status == 200) {

			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			updateResponse(baseResponse);
		}
	}
}
var checkflag = false;
function updateResponse(response) {

	var res = response.getElementsByTagName("status")[0].firstChild.nodeValue;

	if (res == "success") {

	
			if (response.getElementsByTagName("command")[0].firstChild.nodeValue == "Validate") {
				alert("Records Validated Successfully");

				clearAll();

				return;
			}


		else	if (response.getElementsByTagName("command")[0].firstChild.nodeValue == "Delete") {
				alert("Records Deleted Successfully");

				clearAll();

				return;
			}

		 else if (res == "Notadded") {

			alert("Data already added");

			clearAll();
			return;

		}

	} else {

		alert("Process failure");
	}

}


 
