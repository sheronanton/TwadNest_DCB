function callServer()
{
	
	 document.frmEmployeeCategory.action="../../../../../../Emp_Fund_details_Report";
     document.frmEmployeeCategory.method="POST";
     document.frmEmployeeCategory.submit();
     return true;
}