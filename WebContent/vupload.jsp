
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
			 
  			String itemName="";
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
						
						f1=new File("/usr/twaddcbhelpvd/") ;
						try
						{
						f1.mkdir();
						}catch(Exception e) 
						{
							out.println(e);	
						}
						savedFile= new File(f1,itemName);  
						f2=savedFile;
						item.write(savedFile);
						 
   

									   
					} catch (Exception e) 
					{
						out.println(e);
						
					}
				}
			}
		 
		
		}
		     
		
	%>
	 
	
	<tr>
		<td>
			<input type="button" onclick="window.history.go(-1)" value="Back"/>
	</td></tr>
</table>
</center>