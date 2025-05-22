 function rpt_process()
 {
	 var ref_sno=document.getElementById("rpt").value;
	 var month=document.getElementById("month").value;
	 var year=document.getElementById("year").value;
	 var oid=0;
	 try
	 {
	 oid=document.getElementById("off_id").value;
	 }catch(e) {}
	 window.open("../../../../../../reg_menu_index?ref_sno="+ref_sno+"&year="+year+"&month="+month+"&oid="+oid);
	 
 } 