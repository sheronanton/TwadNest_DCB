/* 
  * Created on : dd-mm-yy 
  * Author     : RUBIN
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
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class privateBeneficiaryListReport
 */
public class privateBeneficiaryListReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public privateBeneficiaryListReport() {
        super();
        //   Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Connection con=null;
		PreparedStatement ps=null;
		Controller Obj=new Controller();
		String command="";
		int empId=0,dis=0,type=0;
		HttpSession session=request.getSession(false);
		 
		try
        {

            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp");
                response.sendRedirect(request.getContextPath()+"/index.jsp");

            }
            System.out.println(session);

        }catch(Exception e)
        {
        	System.out.println("Redirect Error :"+e);
        }
		
		try
        {
        	dis = Integer.parseInt(request.getParameter("dis"));
            System.out.println("dis : "+ dis);
        }
        catch(Exception e)
        {
            System.out.println("Exception fetching dis ===> " + e);
        }
        try
        {
        	type = Integer.parseInt(request.getParameter("type"));
            System.out.println("dis : "+ type);
        }
        catch(Exception e)
        {
            System.out.println("Exception fetching dis ===> " + e);
        }
        
        try
        {
        	command=request.getParameter("action");
        	
        }
        catch(Exception e)
        {
        	System.out.println("Exception Occured"+command);
        }
		UserProfile empProfile = (UserProfile)session.getAttribute("UserProfile");
     	System.out.println("user id::" + empProfile.getEmployeeId());
        
        empId=empProfile.getEmployeeId();
        
        //empId=2513;
        

     	String oid="";
     	String oname = "";

     	try
     	{
     		con=Obj.con();
     		
     		
     		
     		oid=Obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID ='"+empId+"'","OFFICE_ID") ;

     		oname=Obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+oid+ "  ");


     	}
     	catch (Exception e)
     	{
     		System.out.println(e);
     	}
     	
     	if (command.equalsIgnoreCase("listAll"))
        {
     		String condition=" ";
     		
     		if(dis!=0 && type==0)
    		{
    			condition+=(condition.length()>0)? (" and DISTRICT_CODE="+dis):" ";
    		}
    		if(dis==0 && type!=0)
    		{
    			condition+=(condition.length()>0)? (" and BENEFICIARY_TYPE_ID="+type):" ";
    		}
    		if(dis!=0 && type!=0)
    		{
    			condition+=(condition.length()>0)? (" and DISTRICT_CODE="+dis+" and BENEFICIARY_TYPE_ID="+type):" ";
    		}
    		if(dis==0 && type==0)
    		{
    			condition=" ";
    		}	
            Map parameters = new HashMap();
            parameters.put("Off", oid+condition);
            parameters.put("div", oname);

            String path = "";
            try {
            	
            	 String qryrpt= " SELECT " +            	   
            	" BENEFICIARY_SNO, " +
            	  " BENEFICIARY_NAME, " +
            	  " BEN_TYPE_DESC, " +
            	  " DECODE(DISTRICT_NAME,NULL,'  -',DISTRICT_NAME) AS DISTRICT_NAME, " +
            	  " BILLING_ADDRESS1,BILLING_ADDRESS2,BILLING_ADDRESS3  " +
            	  " FROM( ( SELECT BENEFICIARY_SNO,BENEFICIARY_NAME,BENEFICIARY_TYPE_ID, " + 
            			" DISTRICT_CODE, BLOCK_SNO, BILLING_ADDRESS1,BILLING_ADDRESS2, BILLING_ADDRESS3 " +
            	          " FROM PMS_DCB_MST_BENEFICIARY " +
            	    " WHERE status='L' and  office_id=$P!{Off} " +
            	" ORDER BY BENEFICIARY_TYPE_ID " +
            	    " )a   " +
            	  " JOIN ( " +
            			   " SELECT  BEN_TYPE_ID, BEN_TYPE_DESC FROM PMS_DCB_BEN_TYPE where BEN_TYPE_ID>6 " +
            	    " )b " +
            	  " ON a.BENEFICIARY_TYPE_ID = b.BEN_TYPE_ID " +
            	  " LEFT OUTER JOIN " +
            	  " ( " +
            			  " SELECT " +
            	    " DISTRICT_CODE, " +
            	      " DISTRICT_NAME " +
            	      " FROM COM_MST_DISTRICTS " +
            	    " )c " +
            	  "    ON a.DISTRICT_CODE = c.DISTRICT_CODE " +            	  
            	" ) " ;
            	
            	
            	
            	
            	
            	
                 parameters.put("qry", qryrpt);
            	
            	
                path = getServletContext().getRealPath("/WEB-INF/ReportSrc/PrivateBeneficiaryList.jasper");
                JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, con);
                System.out.println("Report is Created from Jasper Print...");

                OutputStream outuputStream = response.getOutputStream();

                JRExporter exporter = null;

                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition",
                                   "attachment; filename=\"PrivateBeneficiaryList.pdf\"");
                exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                      jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                      outuputStream);
                exporter.exportReport();
                System.out.println("The File is Downloaded");
                outuputStream.close();
            } catch (JRException e) {
                throw new ServletException(e);
            }
        }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//   Auto-generated method stub
	}

}
