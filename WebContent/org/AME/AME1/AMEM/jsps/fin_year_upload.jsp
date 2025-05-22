<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.*"%>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="java.util.Calendar" %>
<%@page import="Servlets.AME.AME1.AMEM.servlets.excel2"%>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller"%>
<%@page import="java.sql.Connection"%>
<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet"media="screen" />

<form action="fin_data_store_db.jsp" method="get">
<table align="center" width="85%" border="1" cellpadding="15"  style="border-collapse: collapse;border-color: skyblue" >

	 
	<%  
			String userid="0",Office_id="",Office_Name="",table_heading="",table_column="",table_header="",html="";
			Controller obj=new Controller();
			Connection con=obj.con();
			obj.createStatement(con);
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
  			String itemName="";
			userid = (String) session.getAttribute("UserId");
			Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
			if (Office_id.equals("")) Office_id="0";
			Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
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
			while (itr.hasNext()) 
			{
				item = (FileItem) itr.next();
				if (item.isFormField()) 
				{
				} else
				{
					try 
					{
						itemName = item.getName();						
						f1=new File(application.getRealPath("/excel")) ;
						f1.mkdir();
						savedFile= new File(f1,itemName);						 				 
						f2=savedFile;
					//	File savedFile = new File(config.getServletContext().getRealPath("/")+ "exsfiles/" + itemName);
						item.write(savedFile);									   
					} catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			}		
		
		}
	%> 
	<tr  class = "tdH">
	<td colspan="2" align="center"> <%=Office_Name%></td>
</tr>
<tr  class = "tdH">
	<td colspan="2" align="center">Upload Design and Pumped Qty -   Financial Year</td>
</tr>
  <tr>
	          <td>
	              <label class="lbl">Select Financial Year </label>
	          </td>
			  <td>
			      <select class="select"  id="year" name="year">
			  	  <option value="0">Select</option>
			  	  <%
			  	   for (int i=2010;i<=year;i++) 
			  	   {
			  	   %>
			  	  <option value="<%=i%>"><%=i%>-<%=i+1%></option>
			  	  <%
			  	    }
			  	  %>
			      </select> 
			  </td>
</tr>   
	
	<tr>
		<td colspan="2" align="center">
			<input type=hidden name="itemName" id="itemName" value="<%=itemName%>">
				
			 <input type="submit" value="Submit">
			 <input type="button" value="Exit" onclick="javascript:window.close()">
	</td></tr>
</table>
 