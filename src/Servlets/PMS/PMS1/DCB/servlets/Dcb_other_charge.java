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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class dcb_other_charege
 */
public class Dcb_other_charge extends HttpServlet {
 	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dcb_other_charge() {
        super();
        //  Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	/*


		Table  :		PMS_DCB_OTHERCHARGES
		Field  :		TYPE_SNO
						TYPE_DESC
						UPDATED_BY_USER_ID
						UPDATED_DATE
	*/


		response.setContentType(CONTENT_TYPE);
		Connection con;
		Controller obj=new Controller();
		ResultSet rs=null;
		 String process_code=request.getParameter("process_code");// process code 1->   Select ,2->Meter Select
		 if (process_code==null || process_code.equals(""))
		 process_code="0";

		 String qry="";
		 String xml="";
		 String type="";

		 String command=request.getParameter("command");//Command ->view,insert etc
		    if (command==null || command.equals(""))
		    command="no command";
		////obj.testQry("command"+command);
		////obj.testQry("process_code"+process_code);
		try {
			con=obj.con();
			obj.createStatement(con);
			HttpSession session=request.getSession(false);
			String userid="112",Office_id="";
			try
			{
			 	 userid=(String)session.getAttribute("UserId");
			}catch(Exception e) {userid="112";}

			if(userid==null)
	        {
				userid="112";
		           response.sendRedirect(request.getContextPath()+"/index.jsp");
	        }
			Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
			if (Office_id.equals("")) Office_id="5082";
  			int row=0;
  			xml="<result>";
  			
  			 
  			
  			
			if (command.equals("show"))
			{

				if (process_code.equals("1"))
				{

					qry="select TYPE_SNO,TYPE_DESC,ACCOUNTCODE,ADDFLAG from  PMS_DCB_OTHERCHARGES order by TYPE_SNO";

					rs=obj.getRS(qry);
					while (rs.next())
					{
						row++;
						xml+="<TYPE_SNO>"+obj.isNull(rs.getString("TYPE_SNO"),1)+"</TYPE_SNO>";
						xml+="<TYPE_DESC>"+obj.isNull(rs.getString("TYPE_DESC"),2)+"</TYPE_DESC>";
						xml+="<ACCOUNTCODE>"+obj.isNull(rs.getString("ACCOUNTCODE"),2)+"</ACCOUNTCODE>";
						xml+="<ADDFLAG>"+obj.isNull(rs.getString("ADDFLAG"),2)+"</ADDFLAG>";

					}
					xml+="<row>"+row+"</row>";
				}
			}
			 obj.testQry(xml);
			 obj.testQry(command);
				////obj.testQry(process_code);
			if (command.equals("add"))
			   {
				if (process_code.equals("3"))
					{
						type=obj.isNull(request.getParameter("typevalue"),2);//Command ->view,insert etc
						String ac=obj.isNull(request.getParameter("ac"),2);
						String flag=obj.isNull(request.getParameter("flag"),2);
						////obj.testQry(type);
						if (type==null || type.equals(""))
					    	type="";
						int TYPE_SNO_MAX=obj.getMax("PMS_DCB_OTHERCHARGES", "TYPE_SNO","");
						qry="insert into PMS_DCB_OTHERCHARGES " +
								"(TYPE_SNO," +
								"TYPE_DESC," +
								"UPDATED_BY_USER_ID," +
								"UPDATED_DATE,ACCOUNTCODE,ADDFLAG )" +
								" values ( " +
								TYPE_SNO_MAX+"" +
										" ,'"+type+"','"+userid+"',clock_timestamp(),"+ac+",'"+flag+"')";
								 obj.testQry(qry);
						 		int ins_row=obj.setUpd(qry);
 						 		xml+="<insrow>"+ins_row+"</insrow>";
					}

			    } obj.testQry(command);
			if (command.equals("update"))
			   {
			 
						type=obj.isNull(request.getParameter("typevalue"),2);//Command ->view,insert etc
						String ac=obj.isNull(request.getParameter("ac"),2);
						String flag=obj.isNull(request.getParameter("flag"),2);
						String TYPE_SNO_MAX=obj.isNull(request.getParameter("typesno"),2);
						////obj.testQry(type);
						if (type==null || type.equals(""))
					    	type="";
 						qry="update  PMS_DCB_OTHERCHARGES " +
								" SET TYPE_DESC='"+type+"'"+							
								",ACCOUNTCODE="+ac+" ,ADDFLAG='"+flag+"'" +
								" where TYPE_SNO="+TYPE_SNO_MAX;
										
								 obj.testQry(qry); 
						 		int ins_row=obj.setUpd(qry);
						 		xml+="<insrow>"+ins_row+"</insrow>";
					 

			    }

				xml+="</result>";

				PrintWriter pr=response.getWriter();
				pr.write(xml);
				 obj.testQry(xml);
		        pr.flush();
		        pr.close();
		        obj.conClose(con);





		}catch(Exception e) {}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//  Auto-generated method stub
	}

}
