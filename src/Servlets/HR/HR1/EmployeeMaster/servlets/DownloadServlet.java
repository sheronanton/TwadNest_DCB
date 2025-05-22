package Servlets.HR.HR1.EmployeeMaster.servlets;

    import java.io.*;  
    import javax.servlet.ServletException;  
    import javax.servlet.http.*;  
      
    public class DownloadServlet extends HttpServlet {  
      
    public void doGet(HttpServletRequest request, HttpServletResponse response)  
                throws ServletException, IOException {  
      
    response.setContentType("text/html");  
    PrintWriter out = response.getWriter();  
    String filename = null;
    String book_type=null;
    book_type=request.getParameter("book");
    if(book_type.equals("MB")) 
    	filename="/ManagementBooks.pdf";
    else if(book_type.equals("LB"))
    	filename="/LawBooks.pdf";
    else if(book_type.equals("CB"))
    	filename="/ISCodeBooks.pdf";
    else if(book_type.equals("GB"))
    	filename="/GeneralBooks.pdf";
    else if(book_type.equals("TB"))
    	filename="/TechnicalBooks.pdf";
    
//    String filepath =request.getContextPath()+"\\";
    String filepath =request.getRealPath("/Library_Books/");
   // String filepath="C:/Users/Nicsi/Desktop/";
    response.setContentType("APPLICATION/OCTET-STREAM");   
    response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");   
      
    FileInputStream fileInputStream = new FileInputStream(filepath + filename);  
                
    int i;   
    while ((i=fileInputStream.read()) != -1) {  
    out.write(i);   
    }   
    fileInputStream.close();   
    out.close();   
    }  
      
    }  