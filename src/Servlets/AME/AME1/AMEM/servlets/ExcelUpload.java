package Servlets.AME.AME1.AMEM.servlets;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.apache.commons.fileupload.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ExcelUpload
 */
public class ExcelUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExcelUpload() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unused")
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
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
			FileItem item = (FileItem) itr.next();
			String itemName = item.getName();

			String userAgent = request.getHeader("User-Agent");
			System.out.println("The User Agent ========== " + userAgent);
			String userSeparator = "/"; // default
			if (userAgent.indexOf("Windows") != -1)
				userSeparator = "\\";
			if (userAgent.indexOf("Linux") != -1)
				userSeparator = "/";
			System.out.println("The File Separator === " + userSeparator);
			ResourceBundle rs = ResourceBundle.getBundle("test.Config");
			String myfile = getServletContext().getRealPath("/")
					+ "WEB-INF\\excel\\" + itemName;

			System.out.println(myfile);

			File f = new File(getServletConfig().getServletContext()
					.getRealPath(rs.getString("Config.EXCEL_PATH"))
					+ "\\" + itemName);

			String fpath = f.getPath();
			System.out.println("fpath" + fpath);
			File savedFile = new File(fpath);
			// File savedFile = new File(
			// getServletContext().getRealPath("/")+"WEB-INF\\excel\\"+itemName);

			// System.out.println("TECH" + savedFile.getCanonicalPath());
			try {
				item.write(savedFile);
			} catch (Exception e) {
				 
				e.printStackTrace();
			}

		}
	}

}
