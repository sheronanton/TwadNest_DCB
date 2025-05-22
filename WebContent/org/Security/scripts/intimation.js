//			intimation			//

function AjaxFunction() {
	var xmlrequest = false;
	try {
		xmlrequest = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e1) {
		try {
			xmlrequest = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			xmlrequest = false;
		}
	}
	if (!xmlrequest && typeof XMLHttpRequest != 'undefined') {
		xmlrequest = new XMLHttpRequest();
	}
	return xmlrequest;
}

function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;

			if (command == "get") {
				intimate1(baseResponse);
			}
		}
	}
}

function intimate(path) {

	var url = path + "/IntimationServlet?command=get";
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
}

function intimate1(baseResponse) {

	var slno = 1;
	var s = "";
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var len = baseResponse.getElementsByTagName("No_of_proforma_raised").length;
		if (len != 0) {
			for ( var k = 0; k < len; k++) {
				var No_of_proforma_raised = baseResponse
						.getElementsByTagName("No_of_proforma_raised")[k].firstChild.nodeValue;
				var TPA_Type = baseResponse.getElementsByTagName("TPA_Type")[k].firstChild.nodeValue;
				if (TPA_Type == "TPAOC") {
					TPA_Type = "No of Proforma Transfers(CR) Raised is ";
				} else if (TPA_Type == "TPAOD") {
					TPA_Type = "No of Proforma Transfers(DR) Raised is ";
				}
				s = s + "<br>" + slno + ". " + TPA_Type + No_of_proforma_raised;
				slno = slno + 1;
			}
		}
	}
	if (flag != "failure") {
		var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
		if (flag1 == "success") {
			var len = baseResponse
					.getElementsByTagName("No_of_proforma_status").length;
			if (len != 0) {
				for ( var k = 0; k < len; k++) {
					var No_of_proforma_status = baseResponse
							.getElementsByTagName("No_of_proforma_status")[k].firstChild.nodeValue;
					var TPA_Type1 = baseResponse
							.getElementsByTagName("TPA_Type1")[k].firstChild.nodeValue;
					var Acceptance_Status = baseResponse
							.getElementsByTagName("Acceptance_Status")[k].firstChild.nodeValue;

					if (TPA_Type1 == "TPAOC") {
						if (Acceptance_Status == "Y") {
							TPA_Type1 = "No of Proforma Transfers(CR) Accepted is ";
						} else if (Acceptance_Status == "N") {
							TPA_Type1 = "No of Proforma Transfers(CR) Rejected is ";
						}
					} else if (TPA_Type1 == "TPAOD") {
						if (Acceptance_Status == "Y") {
							TPA_Type1 = "No of Proforma Transfers(DR) Accepted is ";
						} else if (Acceptance_Status == "N") {
							TPA_Type1 = "No of Proforma Transfers(DR) Rejected is ";
						}
					}
					s = s + "<br>" + slno + ". " + TPA_Type1
							+ No_of_proforma_status;
					slno = slno + 1;
				}
			}
		}

		var flag22 = baseResponse.getElementsByTagName("flag22")[0].firstChild.nodeValue;
		if (flag22 == "success") {
			var len = baseResponse.getElementsByTagName("No_of_TDA_raised1").length;
			if (len != 0) {
				for ( var k = 0; k < len; k++) {
					var No_of_TDA_raised = baseResponse
							.getElementsByTagName("No_of_TDA_raised1")[k].firstChild.nodeValue;
					var TDA_OR_TCA = baseResponse
							.getElementsByTagName("TDA_OR_TCA1")[k].firstChild.nodeValue;
					//alert("TDA_OR_TCA------"+TDA_OR_TCA);
					TDA_OR_TCA = TDA_OR_TCA.substring(1, 2);
					// alert("TDA_OR_TCA------"+TDA_OR_TCA);
					if (TDA_OR_TCA == "D") {
						TDA_OR_TCA = "No of TDA Originated by You and JVR Posted is ";
					} else if (TDA_OR_TCA == "C") {
						TDA_OR_TCA = "No of TCA Originated by You and JVR Posted is ";
					}
					s = s + "<br>" + slno + ". " + TDA_OR_TCA
							+ No_of_TDA_raised;
					slno = slno + 1;
				}
			}
		}

		var flag2 = baseResponse.getElementsByTagName("flag2")[0].firstChild.nodeValue;
		if (flag2 == "success") {
			var len = baseResponse.getElementsByTagName("No_of_TDA_raised4").length;
			if (len != 0) {
				for ( var k = 0; k < len; k++) {
					var No_of_TDA_raised = baseResponse
							.getElementsByTagName("No_of_TDA_raised4")[k].firstChild.nodeValue;
					var TDA_OR_TCA = baseResponse
							.getElementsByTagName("TDA_OR_TCA4")[k].firstChild.nodeValue;

					TDA_OR_TCA = TDA_OR_TCA.substring(1, 2);

					if (TDA_OR_TCA == "D") {
						TDA_OR_TCA = "No of TDA Originated to You is ";
					} else if (TDA_OR_TCA == "C") {
						TDA_OR_TCA = "No of TCA Originated to You is ";
					}
					s = s + "<br>" + slno + ". " + TDA_OR_TCA
							+ No_of_TDA_raised;
					slno = slno + 1;
				}
			}
		}

		var flag33 = baseResponse.getElementsByTagName("flag33")[0].firstChild.nodeValue;
		if (flag33 == "success") {
			var len = baseResponse.getElementsByTagName("No_of_TDA_status3").length;
			if (len != 0) {
				for ( var k = 0; k < len; k++) {
					var No_of_TDA_status = baseResponse
							.getElementsByTagName("No_of_TDA_status3")[k].firstChild.nodeValue;
					var TDA_OR_TCA = baseResponse
							.getElementsByTagName("TDA_OR_TCA3")[k].firstChild.nodeValue;
					var Acceptance_Status = baseResponse
							.getElementsByTagName("Acceptance_Status3")[k].firstChild.nodeValue;

					TDA_OR_TCA = TDA_OR_TCA.substring(1, 2);

					if (TDA_OR_TCA == "D") {
						if (Acceptance_Status == "Y") {
							TDA_OR_TCA = "No of TDA Originated by You and Accepted by Others is ";
						} else if (Acceptance_Status == "N") {
							TDA_OR_TCA = "No of TDA Originated by You and Rejected by Others is ";
						}
					} else if (TDA_OR_TCA == "C") {
						if (Acceptance_Status == "Y") {
							TDA_OR_TCA = "No of TCA Originated by You and Accepted by Others is ";
						} else if (Acceptance_Status == "N") {
							TDA_OR_TCA = "No of TCA Originated by You and Rejected by Others is ";
						}
					}

					s = s + "<br>" + slno + ". " + TDA_OR_TCA
							+ No_of_TDA_status;
					slno = slno + 1;
				}
			}
		}

		var flag3 = baseResponse.getElementsByTagName("flag3")[0].firstChild.nodeValue;
		if (flag3 == "success") {
			var len = baseResponse.getElementsByTagName("No_of_TDA_status").length;
			if (len != 0) {
				for ( var k = 0; k < len; k++) {
					var No_of_TDA_status = baseResponse
							.getElementsByTagName("No_of_TDA_status")[k].firstChild.nodeValue;
					var TDA_OR_TCA = baseResponse
							.getElementsByTagName("TDA_OR_TCA")[k].firstChild.nodeValue;
					var Acceptance_Status = baseResponse
							.getElementsByTagName("Acceptance_Status")[k].firstChild.nodeValue;

					TDA_OR_TCA = TDA_OR_TCA.substring(1, 2);

					if (TDA_OR_TCA == "D") {
						if (Acceptance_Status == "Y") {
							TDA_OR_TCA = "No of TDA Originated to You and Accepted by You is ";
						} else if (Acceptance_Status == "N") {
							TDA_OR_TCA = "No of TDA Originated to You and Rejected by You is is ";
						}
					} else if (TDA_OR_TCA == "C") {
						if (Acceptance_Status == "Y") {
							TDA_OR_TCA = "No of TCA Originated to You and Accepted by You is ";
						} else if (Acceptance_Status == "N") {
							TDA_OR_TCA = "No of TCA Originated to You and Rejected by You is is ";
						}
					}

					s = s + "<br>" + slno + ". " + TDA_OR_TCA
							+ No_of_TDA_status;
					slno = slno + 1;
				}
			}
		}

		var flag10 = baseResponse.getElementsByTagName("flag10")[0].firstChild.nodeValue;
		if (flag10 == "success") {
			var No_of_Transfers_to_Office_ID = baseResponse
					.getElementsByTagName("No_of_Transfers_to_Office_ID")[0].firstChild.nodeValue;                                     
			if (No_of_Transfers_to_Office_ID != 0) {
				var office_id = baseResponse.getElementsByTagName("office_id")[0].firstChild.nodeValue;
				if (office_id != 5000) {                               
					var Msg = "No of Fund Transfer From HO to You is ";
					s = s + "<br>" + slno + ". " + Msg
							+ No_of_Transfers_to_Office_ID;
					slno = slno + 1;
				} else {
					var wing_id = baseResponse.getElementsByTagName("wing_id")[0].firstChild.nodeValue;
					if (wing_id == 2) {
						var Msg = "No of Fund Transfer From Unit to You is ";
						s = s + "<br>" + slno + ". " + Msg
								+ No_of_Transfers_to_Office_ID;
						slno = slno + 1;
					}
				}
			}

		}

	}
	if (s != "") {
		$.prompt(s, {
			show : 'slideDown',
			buttons : {
				Ok : true
			}
		});
	}
}
