var xmlhttp;
var seq = 0;
var common = "", mn, yr, v;

function getxmlhttpObject() {
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
function check() {
	balance = parseInt(document.getElementById("Balance").value);
	if (impound_type == 'ImpReg') {
		balance_per = balance * (20 / 100);
		balance = balance + balance_per;
	}
	amt = parseInt(document.getElementById("amount").value);
	/*
	 * if(balance<amt) { alert("Impound Amount should not exceed
	 * balance***check()" ); document.getElementById("amount").value=""; }
	 */
}

function acccheck() {
	ac_month = document.getElementById("acmonth").value;
	ac_year = document.getElementById("acyear").value;
	rel_month = document.getElementById("rel_month").value;
	rel_year = document.getElementById("rel_year").value;

	if (parseInt(ac_year, 10) < parseInt(rel_year, 10)) {
		alert('Relative year should less than Account Year');
		return 0;

	} else if (ac_year == rel_year) {

		if (parseInt(ac_month, 10) < parseInt(rel_month, 10)) {
			alert('Relative Month should less than Account Month');
			return 0;
		}
	}
	return true;

}
function call(command) {
	// alert(command);
	xmlhttp = getxmlhttpObject();
	if (xmlhttp == null) {
		alert("Your browser doesnot support AJAX");
		return;
	}

	var office_id;
	var division_id;
	var ac_month;
	var rel_month;
	var ac_year;
	var rel_year;
	var type_trans;
	var impound_type;
	var emp_id;
	var amount;
	var old_amt;
	var date_trans;
	var remarks;

	var url = "";
	if (command == "Add") { // alert("123");
		if (nullCheck()) {

			balance = parseInt(document.getElementById("Balance").value);
			// alert(balance);
			var impound = document.getElementById("impound_type").value;
			amount = parseInt(document.getElementById("amount").value);
			impound_type = document.getElementById("impound_type").value;

			if (impound_type == 'ImpReg') {
				balance_per = balance * (20 / 100);
				balance = balance + balance_per;

			}
			var flag = 1;
			if (balance <= 0 || balance < amount) {
				if (document.Hrm_TransJoinFormpop.trans[1].checked == true) {
					if (document.Hrm_TransJoinFormpop.trans[1].disabled == true) {
						if ((amount + balance) > 0) {
							alert("Impound Amount should not exceed balance****1");
							document.getElementById("amount").value = "";
							flag = 0;
						}
					}
				} else {
					flag = 1;
				}

			}
			/*
			 * else if(balance<amount) { alert("Impound Amount should not
			 * exceed balance****2" );
			 * document.getElementById("amount").value=""; flag=0; }
			 */
			if (flag == 1) {
				gpf_no = document.getElementById("txtGpfNo").value;
				office_id = document.getElementById("officeid").value;
				division_id = document.getElementById("oldcode").value;
				emp_id = document.getElementById("txtEmpId").value;
				ac_month = document.getElementById("ac_month").value;
				ac_year = document.getElementById("ac_year").value;
				rel_month = document.getElementById("rel_month").value;
				rel_year = document.getElementById("rel_year").value;
				// amount=document.getElementById("amount").value;
				date_trans = document.getElementById("date").value;
				remarks = document.getElementById("remarks").value;
				var fin_year = document.getElementById("finyear").value;

				if (remarks == "null" || remarks == "" || remarks == null
						|| remarks == " ") {
					remarks = "noRemarks";
				}
				impound_type = document.Hrm_TransJoinFormpop.impound_type.value;

				if (document.Hrm_TransJoinFormpop.trans[0].checked) {
					type_trans = document.Hrm_TransJoinFormpop.trans[0].value;
				} else {
					type_trans = document.Hrm_TransJoinFormpop.trans[1].value;
				}

				var i;
				var install_type = "";

				for (i = 0; i < document.Hrm_TransJoinFormpop.install_type.length; i++) {
					if (document.Hrm_TransJoinFormpop.install_type[i].checked == true) {
						if (install_type == "") {
							install_type = document.Hrm_TransJoinFormpop.install_type[i].value;
						} else {
							install_type += ","
									+ document.Hrm_TransJoinFormpop.install_type[i].value;
						}
					}
				}
				var accHeadCode = document.getElementById('acchead').value;
				url = "../../../../../GPF_Impound_new.view?command=Add&office_id="
						+ office_id + "&division_id=" + division_id
						+ "&gpf_no=" + gpf_no + "&emp_id=" + emp_id
						+ "&ac_month=" + ac_month + "&ac_year=" + ac_year
						+ "&type_trans=" + type_trans + "&amount=" + amount
						+ "&date_trans=" + date_trans + "&remarks=" + remarks
						+ "&rel_month=" + rel_month + "&rel_year=" + rel_year
						+ "&impound_type=" + impound_type + "&install_type="
						+ install_type + "&fin_year=" + fin_year
						+ "&accHeadCode=" + accHeadCode;
				url = url + "&sid=" + Math.random();
				xmlhttp.open("GET", url, true);
				xmlhttp.onreadystatechange = stateChanged;
				xmlhttp.send(null);
			}

		}// else
	} else if (command == "acc_head_code") {
		if (document.Hrm_TransJoinFormpop.trans[0].checked) {
			type_trans = document.Hrm_TransJoinFormpop.trans[0].value;
		} else {
			type_trans = document.Hrm_TransJoinFormpop.trans[1].value;
		}
		impound_type = document.Hrm_TransJoinFormpop.impound_type.value;
		var unit = document.getElementById('oldcode').value;
		var acMonth = document.getElementById('ac_month').value;
		var acYear = document.getElementById('ac_year').value;
		var gpfNo = document.getElementById('txtGpfNo').value;
		var checkUpdate = document.getElementById('checkUpdate').value;
		if (checkUpdate != "update") {
			url = "../../../../../GPF_Impound_new.view?command=addAcc_head_code&type_trans="
					+ type_trans + "&impound_type=" + impound_type;
		} else {
			url = "../../../../../GPF_Impound_new.view?command=acc_head_code&type_trans="
					+ type_trans
					+ "&impound_type="
					+ impound_type
					+ "&accunitId="
					+ unit
					+ "&ac_month="
					+ acMonth
					+ "&ac_year=" + acYear + "&gpfNo=" + gpfNo;
		}
		url = url + "&sid=" + Math.random();
		xmlhttp.open("GET", url, true);
		xmlhttp.onreadystatechange = stateChanged;
		xmlhttp.send(null);
	} else if (command == "Update") {
		if (nullCheck()) {

			balance = parseInt(document.getElementById("Balance").value);
			if (impound_type == 'ImpReg') {
				balance_per = balance * (20 / 100);
				balance = balance + balance_per;

			}
			var impound = document.getElementById("impound_type").value;
			var diff_amt = 0;
			amt = parseInt(document.getElementById("amount").value);
			old_amt = parseInt(document.getElementById("old_amount").value);

			// alert("balance="+balance+":amt="+amt);

			if (amt > old_amt)
				diff_amt = amt - old_amt;

			/*
			 * if(diff_amt>balance) { alert("Impound Amount should not exceed
			 * balance" ); document.getElementById("amount").value=""; } else {
			 */
			gpf_no = document.getElementById("txtGpfNo").value;
			office_id = document.getElementById("officeid").value;
			division_id = document.getElementById("oldcode").value;
			emp_id = document.getElementById("txtEmpId").value;
			ac_month = document.getElementById("ac_month").value;
			ac_year = document.getElementById("ac_year").value;
			rel_month = document.getElementById("rel_month").value;
			rel_year = document.getElementById("rel_year").value;
			amount = document.getElementById("amount").value;
			date_trans = document.getElementById("date").value;
			remarks = document.getElementById("remarks").value;
			var fin_year = document.getElementById("finyear").value;
			if (remarks == "null" || remarks == "" || remarks == null
					|| remarks == " ") {
				remarks = "noRemarks";
			}
			impound_type = document.Hrm_TransJoinFormpop.impound_type.value;

			if (document.Hrm_TransJoinFormpop.trans[0].checked) {
				type_trans = document.Hrm_TransJoinFormpop.trans[0].value;
			} else {
				type_trans = document.Hrm_TransJoinFormpop.trans[1].value;
			}

			var i;
			var install_type = "";

			for (i = 0; i < document.Hrm_TransJoinFormpop.install_type.length; i++) {
				if (document.Hrm_TransJoinFormpop.install_type[i].checked == true) {
					if (install_type == "") {
						install_type = document.Hrm_TransJoinFormpop.install_type[i].value;
					} else {
						install_type += ","
								+ document.Hrm_TransJoinFormpop.install_type[i].value;
					}
				}
			}
			var dis_sl_no = document.getElementById("Dis_sl_no").value;
			var accheadCode = document.getElementById("acchead").value;
			url = "../../../../../GPF_Impound_new.view?command=Update&office_id="
					+ office_id + "&division_id=" + division_id + "&gpf_no="
					+ gpf_no + "&emp_id=" + emp_id + "&ac_month=" + ac_month
					+ "&ac_year=" + ac_year + "&type_trans=" + type_trans
					+ "&amount=" + amount + "&date_trans=" + date_trans
					+ "&remarks=" + remarks + "&rel_month=" + rel_month
					+ "&rel_year=" + rel_year + "&impound_type=" + impound_type
					+ "&fin_year=" + fin_year + "&install_type=" + install_type
					+ "&dis_sl_no=" + dis_sl_no + "&accHeadCode=" + accheadCode;
			// alert(url);
			url = url + "&sid=" + Math.random();
			xmlhttp.open("GET", url, true);
			xmlhttp.onreadystatechange = stateChanged;
			xmlhttp.send(null);
			// }

		}
	} else if (command == "Delete") {
		gpf_no = document.getElementById("txtGpfNo").value;
		office_id = document.getElementById("officeid").value;
		division_id = document.getElementById("oldcode").value;
		emp_id = document.getElementById("txtEmpId").value;
		ac_month = document.getElementById("ac_month").value;
		ac_year = document.getElementById("ac_year").value;
		/*
		 * ac_month=opener.getElementById("ac_month").value;
		 * ac_year=opener.getElementById("ac_year").value;
		 */

		rel_month = document.getElementById("rel_month").value;
		rel_year = document.getElementById("rel_year").value;
		amount = document.getElementById("amount").value;
		date_trans = document.getElementById("date").value;
		remarks = document.getElementById("remarks").value;
		var fin_year = document.getElementById("finyear").value;

		if (remarks == "null" || remarks == "" || remarks == null
				|| remarks == " ") {
			remarks = "noRemarks";
		}
		impound_type = document.Hrm_TransJoinFormpop.impound_type.value;

		if (document.Hrm_TransJoinFormpop.trans[0].checked) {
			type_trans = document.Hrm_TransJoinFormpop.trans[0].value;
		} else {
			type_trans = document.Hrm_TransJoinFormpop.trans[1].value;
		}

		var i;
		var install_type = "";

		for (i = 0; i < document.Hrm_TransJoinFormpop.install_type.length; i++) {
			if (document.Hrm_TransJoinFormpop.install_type[i].checked == true) {
				if (install_type == "") {
					install_type = document.Hrm_TransJoinFormpop.install_type[i].value;
				} else {
					install_type += ","
							+ document.Hrm_TransJoinFormpop.install_type[i].value;
				}
			}
		}

		var dis_sl_no = document.getElementById("Dis_sl_no").value;

		url = "../../../../../GPF_Impound_new.view?command=Delete&office_id="
				+ office_id + "&division_id=" + division_id + "&gpf_no="
				+ gpf_no + "&emp_id=" + emp_id + "&ac_month=" + ac_month
				+ "&ac_year=" + ac_year + "&type_trans=" + type_trans
				+ "&amount=" + amount + "&date_trans=" + date_trans
				+ "&remarks=" + remarks + "&rel_month=" + rel_month
				+ "&rel_year=" + rel_year + "&impound_type=" + impound_type
				+ "&fin_year=" + fin_year + "&dis_sl_no=" + dis_sl_no;
		url = url + "&sid=" + Math.random();
		xmlhttp.open("GET", url, true);
		xmlhttp.onreadystatechange = stateChanged;
		xmlhttp.send(null);
	}

	else if (command == "get") {
		document.Hrm_TransJoinFormpop.add.disabled = false;
		// alert("her"+document.Hrm_TransJoinFormpop.txtGpfNo.value) ;
		if ((document.Hrm_TransJoinFormpop.txtGpfNo.value == "")
				|| (document.Hrm_TransJoinFormpop.txtGpfNo.value.length == 0)) {
			alert("Null Value not accepted...Select GPF.No");
			document.Hrm_TransJoinFormpop.txtGpfNo.focus();
			return false;
		}
		var gpf_no = document.Hrm_TransJoinFormpop.txtGpfNo.value;

		if ((document.Hrm_TransJoinFormpop.impound_type.value == "0")) {
			alert("select Impound Type");
			document.Hrm_TransJoinFormpop.txtGpfNo.value = "";
			document.Hrm_TransJoinFormpop.impound_type.focus();
			return false;
		}
		var transType = "";
		var impound_type = document.getElementById("impound_type").value;
		var gpf_no = document.Hrm_TransJoinFormpop.txtGpfNo.value;
		var fin_year = document.getElementById("finyear").value;
		// alert("get******2"+impound_type+":"+gpf_no+":"+fin_year+":");
		document.getElementById("comEmpId").value = "";
		document.getElementById("txtDOB").value = "";
		// document.getElementById("txtGpfNo").value="";
		document.getElementById("designation").value = "";
		document.getElementById("txtEmpId").value = "";
		document.getElementById("Balance").value = "";
		if (document.Hrm_TransJoinFormpop.trans[0].checked == true) {
			transType = document.Hrm_TransJoinFormpop.trans[0].value;
		} else {
			transType = document.Hrm_TransJoinFormpop.trans[1].value;
		}
		url = "../../../../../GPF_Impound_new.view?command=get&gpf_no=" + gpf_no
				+ "&impound_type=" + impound_type + "&fin_year=" + fin_year
				+ "&type_trans=" + transType;

		url = url + "&sid=" + Math.random();
		xmlhttp.open("GET", url, true);
		xmlhttp.onreadystatechange = stateChanged;
		xmlhttp.send(null);
	}
}
function getRelMonth(acYear, acMonth) {
	var rel_year = parseInt(document.Hrm_TransJoinFormpop.rel_year.value);
	document.getElementById("rel_month").innerHTML = "";
	var monthvalue = new Array("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL",
			"AUG", "SEP", "OCT", "NOV", "DEC");
	var months_val = new Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
	var minorcmb = document.Hrm_TransJoinFormpop.rel_month;
	document.Hrm_TransJoinFormpop.rel_month.length = 0;
	if (acYear == rel_year) {
		for ( var i = 0; i < acMonth; i++) {
			var opt1 = document.createElement('option');
			opt1.value = months_val[i];
			opt1.innerHTML = monthvalue[i];
			minorcmb.appendChild(opt1);
		}
	} else {
		for ( var i = 0; i < monthvalue.length; i++) {
			var opt1 = document.createElement('option');
			opt1.value = months_val[i];
			opt1.innerHTML = monthvalue[i];
			minorcmb.appendChild(opt1);
		}
	}
}
function selectRelMonth(ac_year) {
	rel_year = document.getElementById("rel_year").value;
	if (parseInt(rel_year, 10) > parseInt(ac_year, 10)) {
		alert("Relative Year should be greater than Accounting Year");
		document.getElementById("rel_year").value = ac_year;

	}
	v = new Date();
	mn = v.getMonth();
	yr = v.getFullYear();
	var month = new Array('JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL',
			'AUG', 'SEP', 'OCT', 'NOV', 'DEC');
	var year2 = document.getElementById("rel_year").value;

	if (year2 == yr) {

		for ( var i = (mn + 1); i < 12; i++) {

			document.getElementById("rel_month").options[i].disabled = true;

		}
		document.getElementById("rel_month").options[mn].selected = 'selected';

	} else {
		// document.getElementById("rel_month").disabled=false;

		for ( var i = 0; i < 12; i++) {

			document.getElementById("rel_month").options[i].disabled = false;

		}

	}

}
function checkmonth(ac_month) {
	relmonth = document.getElementById("rel_month").value;
	if (parseInt(relmonth, 10) > parseInt(ac_month, 10)) {
		alert("Relative month should not be greater than Accounting month");
		document.getElementById("rel_month").value = ac_month;
	}

}
function stateChanged() {
	var flag, command, response;
	var month = new Array('JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL',
			'AUG', 'SEP', 'OCT', 'NOV', 'DEC');
	if (xmlhttp.readyState == 4) {
		if (xmlhttp.status == 200) {
			response = xmlhttp.responseXML.getElementsByTagName("response")[0];
			command = response.getElementsByTagName("command")[0].firstChild.nodeValue;
			flag = response.getElementsByTagName("flag")[0].firstChild.nodeValue;
			if (command == "get") {
				// alert(flag);
				if (flag == 'success') {
					impound_type = document.getElementById("impound_type").value;
					var empname = response.getElementsByTagName("emp_name")[0].firstChild.nodeValue;
					var d_id = response.getElementsByTagName("designation")[0].firstChild.nodeValue;
					var dob = response.getElementsByTagName("date_of_birth")[0].firstChild.nodeValue;
					var gpfno = response.getElementsByTagName("gpf_no")[0].firstChild.nodeValue;
					var emp_id = response.getElementsByTagName("emp_id")[0].firstChild.nodeValue;
					var balance = response.getElementsByTagName("balance")[0].firstChild.nodeValue;
					var acchead = response
							.getElementsByTagName("acc_head_value")[0].firstChild.nodeValue;
					var command = document.getElementById("command").value;
					// alert("i am here"+gpfno);
					balance = response.getElementsByTagName("balance_1")[0].firstChild.nodeValue;

					document.getElementById("acchead").value = acchead;

					document.getElementById("comEmpId").value = empname;
					document.getElementById("txtDOB").value = dob;
					document.getElementById("txtGpfNo").value = gpfno;
					d_id = d_id.trim();
					if (d_id == 'null' || d_id == null)
						d_id = '';
					document.getElementById("designation").value = d_id;
					document.getElementById("txtEmpId").value = emp_id;
					document.getElementById("Balance").value = balance;

					balance = parseInt(balance);
					if (impound_type == 'ImpReg') {
						balance_per = balance * (20 / 100);
						balance = balance + balance_per;

					}

					/*
					 * if(balance==0) { //trial var
					 * ac_mon=document.Hrm_TransJoinFormpop.ac_month.value; var
					 * ac_yer=document.Hrm_TransJoinFormpop.ac_year.value; var
					 * gt_date=new Date(); gt_date.setFullYear(ac_yer,
					 * (ac_mon-1), 1); // alert("Date is "+gt_date); var
					 * val_date=new Date(); val_date.setFullYear(2010, 8, 1); //
					 * alert(gt_date>val_date); END Trial if(gt_date>val_date)
					 * alert("This Employee's Impound processes has completed ,
					 * No Balance for further proceedings "); else{ var answer =
					 * confirm ("No Balance! Want to Proceed. ") if (answer)
					 * document.Hrm_TransJoinFormpop.trans[1].disabled=false;
					 * else { // document.getElementById("add").disabled=true;
					 * document.Hrm_TransJoinFormpop.trans[0].checked=true;
					 * document.getElementById("update").disabled=true;
					 * document.Hrm_TransJoinFormpop.trans[1].disabled=true;
					 * document.getElementById("delete1").disabled=true; } }
					 * }else if(balance<0){
					 * document.Hrm_TransJoinFormpop.trans[0].checked=true;
					 * document.Hrm_TransJoinFormpop.trans[1].checked=false;
					 * document.Hrm_TransJoinFormpop.trans[1].disabled=true;
					 * //trial var
					 * ac_mon=document.Hrm_TransJoinFormpop.ac_month.value; var
					 * ac_yer=document.Hrm_TransJoinFormpop.ac_year.value; var
					 * gt_date=new Date(); gt_date.setFullYear(ac_yer,
					 * (ac_mon-1), 1); // alert("Date is "+gt_date); var
					 * val_date=new Date(); val_date.setFullYear(2010, 8, 1); //
					 * alert(gt_date>val_date); END Trial if(gt_date>val_date)
					 * alert("This Employee's Impound processes has completed ,
					 * No Balance for further proceedings "); else{ var answer =
					 * confirm ("Negative Balance! Want to Proceed. ") if
					 * (answer)
					 * document.Hrm_TransJoinFormpop.trans[1].disabled=false;
					 * else { // document.getElementById("add").disabled=true;
					 * document.Hrm_TransJoinFormpop.trans[0].checked=true;
					 * document.getElementById("update").disabled=true;
					 * document.Hrm_TransJoinFormpop.trans[1].disabled=true;
					 * document.getElementById("delete1").disabled=true; } }
					 * }else{
					 */
					/*
					 * document.Hrm_TransJoinFormpop.trans[1].disabled=false;
					 * document.Hrm_TransJoinFormpop.trans[0].checked=false;
					 * document.Hrm_TransJoinFormpop.trans[1].checked=true;
					 */
					if (document.Hrm_TransJoinFormpop.add.disabled == true) {
						document.getElementById("update").disabled = false;
						document.getElementById("delete1").disabled = false;
					} else {
						document.getElementById("update").disabled = true;
						document.getElementById("delete1").disabled = true;
					}
					// document.getElementById("update").disabled=false;
					// document.getElementById("delete1").disabled=false;
					// }

					/*
					 * else document.getElementById("add").disabled=false;
					 */
					// alert("******"+document.getElementById("txtGpfNo").value);
					/*
					 * if(document.getElementById("txtGpfNo").value!=""){
					 * document.getElementById("add").disabled=true; }
					 */

				} else if (flag == "notExist") {

					document.Hrm_TransJoinFormpop.txtEmpId.value = "";

					document.getElementById("comEmpId").value = "";
					document.getElementById("txtDOB").value = "";
					document.getElementById("txtGpfNo").value = "";
					document.getElementById("designation").value = "";
					document.Hrm_TransJoinFormpop.txtGpfNo.focus();
					alert("This Employee GPF.No doesnot Exist in the master");
				}

			} else if (command == "Add") {
				if (flag == 'success') {
					if (window.opener && !window.opener.closed) {

						window.close();
						window.opener.focus();

						opener.popParentEmp(4);

					}

					alert("Add Successfully");
					window.close();
				} else
					alert("Same data wont be allowed");
			} else if (command == "Update") {
				if (flag == 'success') {
					if (window.opener && !window.opener.closed) {

						window.close();
						window.opener.focus();

						opener.popParentEmp(4);

					}

					alert("update Successfully");
					window.close();

				} else
					alert("Failure in Update");
			} else if (command == "Delete") {
				if (flag == 'success') {
					if (flag == 'success') {
						if (window.opener && !window.opener.closed) {

							window.close();
							window.opener.focus();

							opener.popParentEmp(4);

						}

						alert("Delete Successfully");
						window.close();
					}

				} else {
					alert("Not success in Delete");
				}

			}

			else if (command == "acc_head_code") {
				if (flag == 'success') {

					// --------------------------------
					impound_type = document.getElementById("impound_type").value;
					var empname = response.getElementsByTagName("emp_name")[0].firstChild.nodeValue;

					var d_id = response.getElementsByTagName("designation")[0].firstChild.nodeValue;
					var dob = response.getElementsByTagName("date_of_birth")[0].firstChild.nodeValue;

					var emp_id = response.getElementsByTagName("emp_id")[0].firstChild.nodeValue;
					var balance = response.getElementsByTagName("balance")[0].firstChild.nodeValue;

					var command = document.getElementById("command").value;

					balance = response.getElementsByTagName("balance")[0].firstChild.nodeValue;

					document.getElementById("comEmpId").value = empname;
					document.getElementById("txtDOB").value = dob;

					d_id = d_id.trim();
					if (d_id == 'null' || d_id == null)
						d_id = '';
					document.getElementById("designation").value = d_id;
					document.getElementById("txtEmpId").value = emp_id;
					document.getElementById("Balance").value = balance;

					// ------------------------

					var len = response.getElementsByTagName("acc_head_value").length;
					var selectId = document.getElementById("acchead");
					var listOpt = document.createElement('option');
					selectId.length = 0;
					selectId.appendChild(listOpt);
					for ( var i = 0; i < len; i++) {
						listOpt = document.createElement('option');
						selectId.appendChild(listOpt);
						listOpt.text = response
								.getElementsByTagName("acc_head_value")[i].firstChild.nodeValue;
						listOpt.value = response
								.getElementsByTagName("acc_head_value")[i].firstChild.nodeValue;
					}
					if (response.getElementsByTagName("AC_HEAD_CODE")[0].firstChild.nodeValue == 0) {
						if (document.Hrm_TransJoinFormpop.trans[0].checked == true) {
							document.getElementById("acchead").selectedIndex = 1;
						} else {
							document.getElementById("acchead").selectedIndex = 2;
						}
					} else {
						document.getElementById("acchead").value = response
								.getElementsByTagName("AC_HEAD_CODE")[0].firstChild.nodeValue;
					}

				} else {

					// alert("problem in Account head code");
				}

			}

			else if (command == "addacc_head_code") {
				if (flag == 'success') {

					var len = response.getElementsByTagName("acc_head_value").length;
					var selectId = document.getElementById("acchead");
					var listOpt = document.createElement('option');
					selectId.length = 0;
					selectId.appendChild(listOpt);
					for ( var i = 0; i < len; i++) {
						listOpt = document.createElement('option');
						selectId.appendChild(listOpt);
						listOpt.text = response
								.getElementsByTagName("acc_head_value")[i].firstChild.nodeValue;
						listOpt.value = response
								.getElementsByTagName("acc_head_value")[i].firstChild.nodeValue;
					}
					if (response.getElementsByTagName("AC_HEAD_CODE")[0].firstChild.nodeValue == 0) {
						if (document.Hrm_TransJoinFormpop.trans[0].checked == true) {
							document.getElementById("acchead").selectedIndex = 1;
						} else {
							document.getElementById("acchead").selectedIndex = 2;
						}
					} else {
						document.getElementById("acchead").value = response
								.getElementsByTagName("AC_HEAD_CODE")[0].firstChild.nodeValue;
					}

				} else {

					// alert("problem in Account head code");
				}

			}

			else if (command == "AccountUnit") {

				var minorcmb = document.Hrm_TransJoinForm.unit_name;
				document.Hrm_TransJoinForm.unit_name.length = 1;
				var acc_unit_id = baseResponse
						.getElementsByTagName("acc_unit_id");
				// alert(acc_unit_id);
				var acc_unit_name = baseResponse
						.getElementsByTagName("acc_unit_name");
				// alert(acc_unit_name);
				for ( var i = 0; i < acc_unit_id.length; i++) {
					var opt1 = document.createElement('option');
					opt1.value = acc_unit_id[i].firstChild.nodeValue;
					opt1.innerHTML = acc_unit_name[i].firstChild.nodeValue;
					minorcmb.appendChild(opt1);
				}
			}
		}
	}

}
String.prototype.trim = function() {
	return this.replace(/^\s*(\S*(\s+\S+)*)\s*$/, "$1");
};
function disablefun() {
	// alert("function called-------->");
	var ac_year = 0, ac_month = 0;
	ac_year = document.getElementById("ac_year").value;
	ac_month = document.getElementById("ac_month").value;
	document.getElementById("rel_month").value = ac_month;
	document.getElementById("rel_year").value = ac_year;
	document.getElementById("rel_month").disabled = true;
	document.getElementById("rel_year").disabled = true;
	document.getElementById("checkUpdate").value = "add";
	call('acc_head_code');

}
function assignyear(ac_year, ac_month) {

	document.getElementById("rel_month").value = ac_month;
	document.getElementById("rel_year").value = ac_year;
	// alert(ac_month,ac_year);
}

function enablefun() {
	document.getElementById("rel_month").disabled = false;
	document.getElementById("rel_year").disabled = false;
	/*
	 * if(document.Hrm_TransJoinFormpop.add.disabled==true) {
	 * document.getElementById("update").disabled=false;
	 * document.getElementById("delete1").disabled=false; }
	 */
	document.getElementById("checkUpdate").value = "add";
	call('acc_head_code');
}

/*
 * function assignyear() { var ac_year=0;
 * ac_year=document.getElementById("ac_year").value;
 * document.getElementById("rel_year").value=ac_year;
 *  } function assignmonth() { var ac_month=0;
 * ac_year=document.getElementById("ac_month").value;
 * document.getElementById("ac_month").value=ac_year; }
 */

function activatediv() {
	var impound = document.getElementById("impound_type").value;
	document.getElementById("impound").style.display = "block";
	if (impound == 'ImpReg') {
		document.getElementById("impreg").style.display = "block";
		document.getElementById("imp2003").style.display = "none";
		// document.getElementById("balancediv").style.display="none";
	} else if (impound == 'Imp2003') {
		document.getElementById("impreg").style.display = "none";
		document.getElementById("imp2003").style.display = "block";
		// document.getElementById("balancediv").style.display="block";
	}

	document.getElementById("comEmpId").value = "";
	document.getElementById("txtDOB").value = "";
	document.getElementById("txtGpfNo").value = "";
	document.getElementById("designation").value = "";
	document.getElementById("txtEmpId").value = "";
	document.getElementById("Balance").value = "";
	call('acc_head_code');

}
function nullCheck() {
	// alert("inside null chek");
	if ((document.Hrm_TransJoinFormpop.txtEmpId.value == "")
			|| (document.Hrm_TransJoinFormpop.txtEmpId.value == "0")) {
		alert("Employee Id must be Correct");
		return 0;
	} else if (document.Hrm_TransJoinFormpop.impound_type.value == "0") {
		alert("Select Impound Type");
		return 0;
	} else if (document.Hrm_TransJoinFormpop.amount.value == "") {
		alert("Must enter the Amount");
		return 0;
	} else if (document.Hrm_TransJoinFormpop.date.value == "") {
		alert("Select Date from Calendar");
		return 0;
	}

	var month = parseInt(document.getElementById("ac_month").value);
	var year = parseInt(document.getElementById("ac_year").value);
	var date = document.getElementById("date").value;

	var mnt = date.substring(3, 5);
	var yr = parseInt(date.substring(6, 10));

	if (mnt - month != 0) {

		alert('Please Select the Date of Payment which belongs to accounting month and year');
		document.getElementById("date").value = "";
		return 0;
	} else if (yr != year) {
		alert('Please Select the Date of Payment which belongs to accounting month and year');
		document.getElementById("date").value = "";
		return 0;
	}

	var flag = 0;
	for ( var i = 0; i < document.Hrm_TransJoinFormpop.install_type.length; i++) {
		if (document.Hrm_TransJoinFormpop.install_type[i].checked == true) {
			flag = 1;
		}
	}

	if (flag == 0) {
		alert("please select Impound Details");
		return 0;
	}

	return 1;

}

function filter_real(evt, item, n, pre) {
	var charCode = (evt.which) ? evt.which : event.keyCode
	// allow "." for one time
	if (charCode == 46) {
		// alert("Position of . "+item.value.indexOf("."));
		if (item.value.indexOf(".") < 0)
			return (item.value.length > 0) ? true : false;
		else
			return false;
	}
	if (!(charCode > 31 && (charCode < 48 || charCode > 57))) {
		// to avoid over flow
		if (item.value.indexOf(".") < 0) {
			// alert("Length without . ="+item.value.length);
			return (item.value.length < n) ? true : false;
		}
		// dont allow more than 2 precision no's after the point
		if (item.value.indexOf(".") > 0) {
			// alert("precision count ="+item.value.split(".")[1].length);
			if (item.value.split(".")[1].length < pre)
				return true;
			else
				return false;
		}
		return false;
	} else {
		return false;
	}
}

function selectActMonthByActYear() {
	var acyear = document.getElementById("ac_year");
	var year1 = acyear.options[1].value;

	// var year2=acyear.options[2].value;
	var sel_year = acyear.options[acyear.selectedIndex].value;
	var start_months = new Array('APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEP',
			'OCT', 'NOV', 'DEC');
	var start_months_val = new Array(4, 5, 6, 7, 8, 9, 10, 11, 12);
	var end_months = new Array('JAN', 'FEB', 'MAR');
	var end_months_val = new Array(1, 2, 3);
	var minorcmb = document.Hrm_TransJoinFormpop.ac_month;
	document.Hrm_TransJoinFormpop.ac_month.length = 1;
	if (parseInt(sel_year) == parseInt(year1)) {
		for ( var i = 0; i < start_months.length; i++) {
			var opt1 = document.createElement('option');
			opt1.value = start_months_val[i];
			opt1.innerHTML = start_months[i];
			minorcmb.appendChild(opt1);
		}
	} else {
		for ( var i = 0; i < end_months.length; i++) {
			var opt1 = document.createElement('option');
			opt1.value = end_months_val[i];
			opt1.innerHTML = end_months[i];
			minorcmb.appendChild(opt1);
		}
	}

}

function selectActMonthByActYearRel() {
	var acyear = document.getElementById("rel_year");
	var year1 = acyear.options[1].value;
	if (year1 == "Select Month") {
		year1 = year1;
	} else {
		year1 = (parseInt(year1) + 1);
	}
	// alert(year1);
	// var year2=acyear.options[2].value;
	var sel_year = acyear.options[acyear.selectedIndex].value;
	// alert(sel_year);
	var start_months = new Array('APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEP',
			'OCT', 'NOV', 'DEC');
	var start_months_val = new Array(4, 5, 6, 7, 8, 9, 10, 11, 12);
	var end_months = new Array('JAN', 'FEB', 'MAR');
	var end_months_val = new Array(1, 2, 3);
	var minorcmb = document.Hrm_TransJoinFormpop.rel_month;
	document.Hrm_TransJoinFormpop.rel_month.length = 1;
	if (parseInt(sel_year) == parseInt(year1)) {
		for ( var i = 0; i < start_months.length; i++) {
			var opt1 = document.createElement('option');
			opt1.value = start_months_val[i];
			opt1.innerHTML = start_months[i];
			minorcmb.appendChild(opt1);
		}
	} else {
		for ( var i = 0; i < end_months.length; i++) {
			var opt1 = document.createElement('option');
			opt1.value = end_months_val[i];
			opt1.innerHTML = end_months[i];
			minorcmb.appendChild(opt1);
		}
	}

}

function activatediv1() {
	var impound = document.getElementById("impound_type").value;
	document.getElementById("impound").style.display = "block";
	if (impound == 'ImpReg') {
		document.getElementById("impreg").style.display = "block";
		document.getElementById("imp2003").style.display = "none";
		// document.getElementById("balancediv").style.display="none";
	} else if (impound == 'Imp2003') {
		document.getElementById("impreg").style.display = "none";
		document.getElementById("imp2003").style.display = "block";
		// document.getElementById("balancediv").style.display="block";
	}

	/*
	 * document.getElementById("comEmpId").value="";
	 * document.getElementById("txtDOB").value="";
	 * document.getElementById("txtGpfNo").value="";
	 * document.getElementById("designation").value="";
	 * document.getElementById("txtEmpId").value="";
	 * document.getElementById("Balance").value="";
	 */

}