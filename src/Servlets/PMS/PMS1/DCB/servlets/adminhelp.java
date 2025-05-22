package Servlets.PMS.PMS1.DCB.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class adminhelp extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public adminhelp() {
        super();
    }
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType(CONTENT_TYPE);
		PrintWriter pr = response.getWriter();
		Controller obj = new Controller();
		PrintWriter pw = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");
		String new_cond=Controller.new_cond;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String xml = "<response>";
		int prow = 0;
		String command = request.getParameter("command");// Command view,insert etc
		if (command == null || command.equals(""))
			command = "no command";
		  if (command.equalsIgnoreCase("help")) 
		{
			try {
					try {
						  con=obj.con();
					} catch (Exception e) { 
						e.printStackTrace();
					}
					String table=obj.setValue("tablename", request);
					DatabaseMetaData metadata;
						metadata = con.getMetaData();
					ResultSet resultSet = metadata.getColumns(null, null, table, null);
					ResultSet resultSet1 = metadata.getColumns(null, null, table, null);
					while (resultSet.next()) 
					{
						String name = resultSet.getString("COLUMN_NAME");
						xml+="<columnname>"+name+"</columnname>";
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			xml = xml + "</response>";
			pw.write(xml);
			pw.flush();
			pw.close();  
		}  
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}