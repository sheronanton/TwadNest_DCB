package Servlets.PMS.PMS1.DCB.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;

/**
 * Servlet implementation class video_tut
 */
public class video_tut extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	 private static final String TMP_DIR_PATH = "E:/vd/";
	 private File tempDir;
	 private static final String DESTINATION = "/finalfiledir";

	private static final int DEFAULT_BUFFER_SIZE = 1111110;
	 private File destinationDir; 

    public video_tut() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String videoPath= "/usr/twaddcbhelpvd/";
		
		String filename=request.getParameter("file");
		String videoName=filename+".avi";
		ServletContext application= getServletConfig().getServletContext();
		File file = new File(videoPath,videoName);

 
		String contentType = getServletContext().getMimeType(file.getName());
		response.setBufferSize(DEFAULT_BUFFER_SIZE);
		response.setHeader("Content-Type", "video/avi");
		response.setHeader("Content-Length", Long.toString(file.length()));
		response.setHeader("Content-Disposition", "inline;filename=\""+file.getName()+"\"");

		// Prepare streams.
		BufferedInputStream input = null;
		ServletOutputStream outputStream = response.getOutputStream();
		BufferedOutputStream output = null;
		try {
		// Open streams.
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
		output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

		// Write file contents to response.

		int length;
		while ((length = input.read(buffer,0,4096)) != -1) {
		outputStream.write(buffer, 0, length);
		}
		} finally {
		// Gently close streams.
		// close(output);
		// close(input);
		try{
		if(input != null)
		input.close();
		}catch (Exception e) {
		 
		}
		if(outputStream != null)
		outputStream.flush();
		} 
		/* tempDir = new File(getAbsolute(TMP_DIR_PATH));

		 response.setContentType("video/x-flv");
		 String path="E:/vd/opening_balance_edit.avi";
		 PrintWriter pw=response.getWriter();
		  response.addHeader("Content-Disposition", "attachment; filename=\"" + path + "\"");
		 DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();

		    fileItemFactory.setRepository(tempDir);

		   path=fileItemFactory.getRepository().getAbsolutePath();
		   System.out.println(path);
		   pw.println("<br><embed src='E:/vd/opening_balance_edit.avi'>");
		 pw.println("<a href='"+path+"'>test</a>" +
		 		" <object   type='video/x-ms-asf' width='1000' height='600'>"+
		 		"<param name='url' value='"+path+"'>"+
		 		"<param name='filename' value='"+path+"'>"+
		 		"<param name='autostart' value='1'>"+
		 		"<param name='width' value='100'>"+
		 		"<param name='uiMode' value='full'/>"+
		 		"<param name='autosize' value='0'>"+
		 		"<param name='playcount' value='1'>"+
		 		"<embed type='application/x-mplayer2' src='"+path+"' width='100%' height='100%' autostart='true' showcontrols='true' pluginspage='http://www.microsoft.com/Windows/MediaPlayer/'></embed></object>" +
		 		"" +
		 		"");
	  */
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	public String getAbsolute(String relativepath){
	    return getServletContext().getRealPath(relativepath);
	}

}
