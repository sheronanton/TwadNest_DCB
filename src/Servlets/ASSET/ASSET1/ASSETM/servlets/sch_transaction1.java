/* 
 * Created on : dd-mm-yy 
 * Author     : Joanofark.E
 * Last Date  : 
 *----------------------------------------------------------------------------- 
 * Revision History (Release 1.0.0.0) 
 *-----------------------------------------------------------------------------
 * Date			Description
 * 07/03/2012	New   
 * 
 *---------|---------------|--------------------------------------------------- 
 */
package Servlets.ASSET.ASSET1.ASSETM.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class sch_transaction1 extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";    
    public sch_transaction1() 
    {
        super();
    }
	public void init(ServletConfig config) throws ServletException 
	{
	}
	public void destroy() 
	{
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Controller obj = new Controller();
		PrintWriter pr=response.getWriter();
		Connection con = null;
		String command=request.getParameter("command");
		String res="";
		 try {
			 response.setContentType(CONTENT_TYPE);
				con = obj.con();
				obj.createStatement(con);
				String process_code = obj.setValue("process_code", request);
				String type = obj.setValue("type", request);
				 HttpSession session = request.getSession(false);
				 String userid = (String) session.getAttribute("UserId");
				 
				if (userid == null) {
					//userid ="twad10950";
					 response.sendRedirect(request.getContextPath() + "/index.jsp");
				}    
			
				//String Office_id ="0";
				String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
				if (Office_id == "0") {
					Office_id="0"; 					//response.sendRedirect(request.getContextPath() + "/index.jsp");
				}  
				System.out.println("ASSET-------->sch_transaction1------TYPE("+type+")---->PROCESSCODE("+process_code+")");

				if (Integer.parseInt(type) == 1) {
					//Annual Maintenance Estimate Entry - New Entry 
					if (Integer.parseInt(process_code) == 1) {
						 
						String SCH_CATEGORY_ID=obj.setValue("category_sno",request);
						String sch_year=obj.setValue("pyear",request);  
						String sch_code=obj.setValue("sch_code",request);
						String sch_name=obj.setValue("sch_name",request);
						String sch_type=obj.setValue("sch_type",request);
						String cond=" "; 
						String cond1=" ";
						if (!sch_code.equals("0"))
		                 {
		                	cond = cond + (cond.length()>0 ? " and " : "") + "Upper(sm.sch_code) like '" + sch_code + "%'";
		                	cond1 = cond1 + (cond1.length()>0 ? " and " : "") + "Upper(sch_code) like '" + sch_code + "%'";
		                 }  
		                if (!sch_name.equals("0"))
		                 {
		               	 cond = cond + (cond.length()>0 ? " and " : "") + "Upper(sm.sch_name) like '" + sch_name + "%'";
		                 cond1 = cond1+ (cond1.length()>0 ? " and " : "") + "Upper(sch_name) like '" + sch_name + "%'";
		                }
		                if (!SCH_CATEGORY_ID.equals("0"))
		                {
		                cond = cond + (cond.length()>0 ? " and " : "") + "sm.SCH_CATEGORY_ID = " +SCH_CATEGORY_ID;
		                cond1 = cond1 + (cond1.length()>0 ? " and " : "") + "SCH_CATEGORY_ID = " +SCH_CATEGORY_ID;
		                }
		                if (!sch_year.equalsIgnoreCase("0"))
		                 {
		                	 cond = cond + (cond.length()>0 ? " and " : "") + "sm.SCH_YEAR like '" + sch_year + "'";
		                	 cond1= cond1 + (cond1.length()>0 ? " and " : "") + "SCH_YEAR like '" + sch_year + "'";
		                 } 
		                 if (!sch_type.equalsIgnoreCase("0"))
		                 {
		                	 cond = cond + (cond.length()>0 ? " and " : "") + "sm.SCH_TYPE_ID="+sch_type;
		                	cond1= cond1 + (cond1.length()>0 ? " and " : "") + " SCH_TYPE_ID like '" + sch_type + "'";
		               } 
		              		String search=obj.setValue("search",request);
		     			  
		      			   if (search.equalsIgnoreCase("SEARCH")) {
		      			  		cond=cond; 
		      			   }
		      			  else {
		      			   	 cond="  AND sm.SCH_CATEGORY_ID= "+SCH_CATEGORY_ID+" AND sm.SCH_TYPE_ID    = "+sch_type;
		      				 cond1="  AND SCH_CATEGORY_ID= "+SCH_CATEGORY_ID;
		      			  }
		      			  //PAGINATION VARIABLES//
		      	        int page = Integer.parseInt(obj.setValue("page",request));;
		      	        int total = 2;
		      	         //and SCH_CATEGORY_ID= "+SCH_CATEGORY_ID+"  AND SCH_TYPE_ID    = "+sch_type
		      	         int records = obj.getCount("PMS_SCH_MASTER", "  WHERE     SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+")"	+cond1 );
		      	        int start = 1;
		      	        int limit = Integer.parseInt(obj.setValue("limit",request));
		      	        int end = 10;
		      	       
		      	        start = (page-1)*limit + 1;
		      	        end   = start + limit - 1;
		      	        total = (int)Math.ceil((float)records/limit);   

						//	String qry="select * from PMS_SCH_MASTER   WHERE  SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+")"	+cond1;
		      	      String qry="SELECT sc.* " 
							+"FROM ( " 
			//				+"  (SELECT ROWNUM AS ID, " 
							+"  (SELECT  " 
        					+"   row_number() OVER () ROWNUM,"
							+"    sm.SCH_CATEGORY_ID, " 
							+"    sm.SCH_YEAR, " 
							+"    lp.SCH_TYPE_ID, " 
							+"    lp.SCH_TYPE_DESC, " 
							+"    sm.SCH_CODE,sm.SCH_SNO, " 
							+"    sm.SCH_NAME, " 
							+"    sm.SCH_SHORT_DESC, " 
							+"    DECODE(sm.DPR_AMOUNT, '', 0, sm.DPR_AMOUNT)                               AS DPR_AMOUNT, " 
							+"    DECODE(sm.ACTUAL_DATE_COMMENCEMENT, '','--', sm.ACTUAL_DATE_COMMENCEMENT) AS ACTUAL_DATE_COMMENCEMENT, " 
							+"    DECODE(sm.ACTUAL_DATE_COMPLETION, '','--', sm.ACTUAL_DATE_COMPLETION)     AS acompdate " 
							+"  FROM PMS_SCH_MASTER sm, " 
							+"    PMS_SCH_LKP_TYPE lp " 
							+"  WHERE lp.SCH_TYPE_ID =sm.SCH_TYPE_ID and SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+") and sch_sno in (select scheme_sno from PMS_DCB_MST_BENEFICIARY_METRE where METER_STATUS='L' and office_id="+Office_id+")"						
							+ cond +"    order by SCH_SNO ) sc  ) WHERE ID BETWEEN  " + start + " AND " + end + "  order by sc.SCH_NAME";

						//				+ cond +"    order by SCH_SNO ) as opt1  ) as opt2 WHERE ID BETWEEN  " + start + " AND " + end + "  order by sc.SCH_NAME";
					
		      	        
							PreparedStatement ps = con.prepareStatement(qry);
							ResultSet rs = ps.executeQuery();
						  
							String XML = "<response>";
							XML+="<flag>success</flag>";		            	 
							XML+="<page>"+page+"</page>";
							XML+="<total>"+total+"</total>";
							XML+="<records>"+records+"</records>";
							XML+=obj.resultXML(qry,con,obj,1);						
							XML+="</response>";
							pr.write(XML);	
							pr.flush();
							pr.close();
							con.close();
	
					}   
				}
			 
				obj.resposeWr(response, res);
		 }catch (Exception e) { 		}
		   
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				 
	}

}
