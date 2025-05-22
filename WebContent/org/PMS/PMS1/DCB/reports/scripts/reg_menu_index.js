 function rpt_process()
 { 
	 var ref_sno=document.getElementById("rpt").value;	 
	 var month=document.getElementById("selmonth").value;
	 var year=document.getElementById("year").value;
	 var oid=document.getElementById("off_id").value;
	 var res=month_year_check(month,year);
	 var reporttype=0;	  
	 if (res!=1)
	 {
		 try
		 { 	 oid=document.getElementById("off_id").value;
			 reporttype=document.getElementById("reporttype").value;
			
		 }catch(e) {}
		 window.open("../../../../../../reg_menu_index?ref_sno="+ref_sno+"&reporttype="+reporttype+"&year="+year+"&month="+month+"&oid="+oid+"&option="+reporttype);
	 }
 }  
 
 
 function rpt_defunt(a)
 { 
	 var fyear=document.getElementById("year1").value;
		
	 if(fyear==0 || fyear=="")
		 {
		 var year=document.getElementById("year").value;
		 var to_year=year;
		 var month=document.getElementById("selmonth").value;
		 var Frm_month=document.getElementById("selmonth1").value;
		 
		 }
	 else
		 {
		 var year=fyear;
		 var to_year=parseInt(year) + 1;
		 var month=3;
		 var Frm_month=4;
		 
			 }  
	 
//	 alert("year"+year);
//	 alert("month"+month);
//	 alert("Frm_month"+Frm_month);
	 var oid=document.getElementById("def_div").value;	
	 var ref_sno=a;
	
//	 alert("a"+a);
//	 alert("ref_sno"+ref_sno);
	 reporttype=document.getElementById("reporttype").value;

	 window.open("../../../../../../reg_menu_index?command="+ref_sno+"&to_year="+to_year+"&year="+year+"&month="+month+"&Frm_month="+Frm_month+"&oid="+oid+"&option="+reporttype);
	 
 }  
 
 function rpt_defunt1()
 { 
	 
	 var fyear=document.getElementById("year1").value;
		
	 if(fyear==0 || fyear=="")
		 {
		 var year=document.getElementById("year").value;
		 var to_year=year;
		 var month=document.getElementById("selmonth").value;
		 var Frm_month=document.getElementById("selmonth1").value;
		 
		 }
	 else
		 {
		 var year=fyear;
		 var to_year=parseInt(year) + 1;
		 var month=3;
		 var Frm_month=4;
		 
			 }
	 
	 
	 var ben_name=document.getElementById("ben_name").value;	
	 var oid=document.getElementById("def_div").value;	
	 var ref_sno="defunt1";
	 reporttype=document.getElementById("reporttype").value;
	// alert("oid"+oid);
	// alert("reporttype"+reporttype);

	 window.open("../../../../../../reg_menu_index?command="+ref_sno+"&to_year="+to_year+"&year="+year+"&month="+month+"&Frm_month="+Frm_month+"&oid="+oid+"&option="+reporttype+"&ben_name="+ben_name);
	 
 }  
 
 
