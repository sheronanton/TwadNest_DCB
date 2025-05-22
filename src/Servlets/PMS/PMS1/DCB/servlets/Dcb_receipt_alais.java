/* 
  * Created on : dd-mm-yy 
  * Author     : Panneer Selvam.K
  * Last Date  : 20/09/2011
  *----------------------------------------------------------------------------- 
  * Revision History (Release 1.0.0.0) 
  *-----------------------------------------------------------------------------
  * Date			Description
  * 17/09/2011		Add the Beneficiary Status to 'L'  
  * 20/09/2011		Add the Meter Status to 'L'
  *---------|---------------|--------------------------------------------------- 
  */
package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Dcb_receipt_alais
 */
public class Dcb_receipt_alais extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    private static Connection con=null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dcb_receipt_alais() {
        super();
        //  Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//  Auto-generated method stub
		response.setContentType(CONTENT_TYPE);

		// class variable

		PrintWriter pr = response.getWriter();
		Controller obj=new Controller();
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;

		// local variable

		String xml="<response>";
		int prow=0;
		String command=request.getParameter("command");//Command ->view,insert etc
		if (command==null || command.equals(""))
		command="no command";

		String input_value=request.getParameter("input_value"); //input value
        if (input_value==null || input_value.equals(""))
        input_value="0";

 
        
        try {
			con=obj.con();
			stmt=con.createStatement();
			obj.createStatement(con);
			System.out.println(command);
			if (command.equals("AC_desc"))
			{
				String	ACCOUNT_HEAD_CODE=request.getParameter("ACCOUNT_HEAD_CODE"); //input value
		        if (ACCOUNT_HEAD_CODE==null || ACCOUNT_HEAD_CODE.equals(""))
		        	ACCOUNT_HEAD_CODE="0";
 				String ACCOUNT_HEAD_DESC=obj.isNull(obj.getValue("COM_MST_ACCOUNT_HEADS", "ACCOUNT_HEAD_DESC","where ACCOUNT_HEAD_CODE="+ACCOUNT_HEAD_CODE),1);
 				xml+="<ACCOUNT_HEAD_DESC>"+ACCOUNT_HEAD_DESC+"</ACCOUNT_HEAD_DESC>";
			}
			else
			{
			String SCH_TYPE_ID=obj.getValue("PMS_SCH_MASTER", "SCH_TYPE_ID","where SCH_SNO="+input_value);
			xml+=obj.combo_lkup("ACCOUNT_HEAD_CODE", "RECEIPT_TRN_DESC", "PMS_DCB_RECEIPT_ACCOUNT_MAP", "where SCH_TYPE_ID="+SCH_TYPE_ID,2,"--Select--");
			}
			xml+="</response>";
			pr.write(xml);
	        pr.flush();
	        pr.close();
            obj.conClose(con);
			
		} catch (Exception e1) {
			//  Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//  Auto-generated method stub
	}

}
