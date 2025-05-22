
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.*"%>
<%@ page
	import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="java.util.Calendar" %>
<%@page import="Servlets.AME.AME1.AMEM.servlets.excel2"%>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller"%>
<%@page import="java.sql.Connection"%><center>
<table border="2">
	<tr>
		<td align="center">
		 	<font color='green' size="3">Excel File Uploaded Successfully</font>
		</td>
	</tr>
	<%     
			Controller obj=new Controller();
			Connection con=obj.con();
			obj.createStatement(con);
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
  			String itemName="";
			 String	userid = (String) session.getAttribute("UserId");
			 String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
				if (Office_id.equals("")) Office_id="0";  
			File f1=null;
		  File f2=null;
		  File savedFile=null;
		  FileItem item=null;
			String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) 
		{
			
		} else
		{
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List items = null;
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				item = (FileItem) itr.next();
				if (item.isFormField()) {
				} else {
					try {
						  itemName = item.getName();
						
						f1=new File(application.getRealPath("/excel")) ;
						f1.mkdir();
						  savedFile= new File(f1,itemName);  
						f2=savedFile;
					//	File savedFile = new File(config.getServletContext().getRealPath("/")+ "exsfiles/" + itemName);
						item.write(savedFile);
						 
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			String []col={"TWAD_SCH_SNO","DESIGN_QTY","PUMPING_QTY","MONTH","YEAR"};
			excel2.read(f2,con,obj,Office_id,"PMS_AME_TRN_SCH_DPQTY",col,6);   
		    //String []col={"PROJECT_ID","PROJECT_DESC"};  
		    //excel2.read(f2,con,obj,Office_id,"TWAD_SCH_MASTER",col,7);
		    //   
		        
		        
			//System.out.println(" batchupdate Before " + month +"-"+year);
		 	excel2.batchupdate(1,Office_id,"23","45",obj,con); 
		    //System.out.println(" batchu pdate Last ");
		    
		    response.sendRedirect("excelUpload.jsp?msg=Excel File Uploaded Successfully");
		
		}
		     
		
	%>
	 
	
	<tr>
		<td>
			<a href="data_store_db.jsp?itemName=<%=itemName%>">Back To Upload Form</a>
	</td></tr>
</table>
</center>