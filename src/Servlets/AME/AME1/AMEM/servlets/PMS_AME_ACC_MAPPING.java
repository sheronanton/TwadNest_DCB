package Servlets.AME.AME1.AMEM.servlets;
 
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PMS_AME_ACC_MAPPING extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	public PMS_AME_ACC_MAPPING() {
		super();
	}
  
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		Controller obj = new Controller();
		response.setContentType(CONTENT_TYPE);
		Connection con = null;
		Hashtable ht = new Hashtable();
		Hashtable condht = new Hashtable();
		Hashtable tab1 = new Hashtable();
		Hashtable cols = new Hashtable();
		int tab_max_=0,r=0;
		String res="";
		try {
			con = obj.con();
			obj.createStatement(con);

			int process_code_new = Integer.parseInt(obj.setValue("process_code", request));
			int type_new = Integer.parseInt(obj.setValue("type", request));
			// ------- Code for Security
			String process_code = Integer.toString(process_code_new);
			String type = Integer.toString(type_new);
			HttpSession session = request.getSession(false);
			String userid = (String) session.getAttribute("UserId");
			if (userid == null) 
			{
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			String mainitem = obj.setValue("mainitem", request);
			String subitem = obj.setValue("subitem", request);
			String sch_type = obj.setValue("sch_type", request);
			String ACC_GROUP_SNO = obj.setValue("ACC_GROUP_SNO", request);
			String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
			System.out.println("AME-------->PMS_AME_ACC_MAPPING------TYPE(" + type+ ")---->PROCESSCODE(" + process_code + ")");
			// TYPE ========================= 1
			if (Integer.parseInt(type) == 9) 
			{

				if (Integer.parseInt(process_code) == 1) 
				{
					String Cond_=" where (1=1) ";
						 	
					if (!sch_type.equalsIgnoreCase("0"))
					{
						Cond_+=" and SCH_TYPE_ID="+sch_type;
					}
					if (!mainitem.equalsIgnoreCase("0"))
						Cond_+=" and MAIN_ITEM_SNO="+mainitem;
					
					if (!subitem.equalsIgnoreCase("0"))
						Cond_+=" and SUB_ITEM_SNO="+subitem;
					
					if (!ACC_GROUP_SNO.equalsIgnoreCase("0"))
						Cond_+=" and ACC_GROUP_SNO="+ACC_GROUP_SNO;
					String qry="	SELECT ac.*, "
						+ " mitem.MAIN_ITEM_DESC, "
						+ " sitem.SUB_ITEM_DESC, (select SCH_TYPE_DESC from   PMS_SCH_LKP_TYPE   where SCH_TYPE_ID=ac.SCH_TYPE_ID) as sch_type_desc "
						+ " ,(SELECT ACC_GROUP_DESC FROM PMS_AME_ACC_GROUP WHERE ACC_GROUP_SNO=ac.ACC_GROUP_SNO ) AS ACC_GROUP_DESC FROM "
						+ " (SELECT ACC_CODE_SNO,CODE_W_E_F, "
						+ " MAIN_ITEM_SNO," 
						+ " SUB_ITEM_SNO,"
						+ " ACCOUNT_HEAD_CODE,ACC_GROUP_SNO,"
						+ " ACC_DESC,SCH_TYPE_ID,UPDATED_DATE "
						+ " FROM PMS_AME_MST_ACC_HEAD_MAP  "+ Cond_
						+ "   "   
						+ " ) ac "      
						+ " LEFT JOIN "   
						+ " ( SELECT MAIN_ITEM_SNO, MAIN_ITEM_DESC FROM PMS_AME_MST_MAIN_ITEM "
						+ "  )mitem "
						+ " ON mitem.MAIN_ITEM_SNO=ac.MAIN_ITEM_SNO "
						+ " LEFT JOIN "
						+ " ( SELECT SUB_ITEM_SNO,SUB_ITEM_DESC FROM PMS_AME_MST_SUB_ITEM " 
						+ "  )sitem "
						+ " ON sitem.SUB_ITEM_SNO=ac.SUB_ITEM_SNO order by SCH_TYPE_ID,ac.MAIN_ITEM_SNO,ACCOUNT_HEAD_CODE  ";
					res = obj.resultXML(qry,con, obj );  
					
				} else if (Integer.parseInt(process_code) == 2 || Integer.parseInt(process_code) == 5) 
				{ 
					String wef = obj.setValue("wef", request);
					String acdesc= obj.setValue("acdesc", request);
					String ac = obj.setValue("ac", request); 				
					String astatus = obj.setValue("astatus", request);						
					int c = obj.getCount("PMS_AME_MST_ACC_HEAD_MAP",
							"where MAIN_ITEM_SNO=" + mainitem
									+ " and SUB_ITEM_SNO=" + subitem
									+ " and SCH_TYPE_ID='" + sch_type
									+ "' and ACCOUNT_HEAD_CODE="+Integer.parseInt(ac)+"");  
				
					tab_max_ = obj.getMax("PMS_AME_MST_ACC_HEAD_MAP","ACC_CODE_SNO", "");
					tab1.put("ACC_CODE_SNO", tab_max_);
					tab1.put("MAIN_ITEM_SNO", mainitem); 
					tab1.put("SUB_ITEM_SNO", subitem);
					tab1.put("SCH_TYPE_ID", sch_type);
					tab1.put("ACC_DESC", "'"+acdesc+"'"); 
					tab1.put("ACCOUNT_HEAD_CODE", "'"+ac+"'"); 
					tab1.put("ACC_GROUP_SNO", ACC_GROUP_SNO);
					tab1.put("UPDATED_BY_USER_ID", "'" + userid + "'");
					tab1.put("UPDATED_DATE", "clock_timestamp()");
					int ins_row = 0;
					if (c == 0) {
						ins_row = obj.recordSave(tab1,"PMS_AME_MST_ACC_HEAD_MAP", con);
					} else {
						ins_row = 0;
					}
					res = "<response><ins_row>" + ins_row+ "</ins_row></response>";
					
				} else if (Integer.parseInt(process_code) == 3) {
					String ACC_CODE_SNO = obj.setValue("ACC_CODE_SNO", request);
					tab1.put("ACTIVE_STATUS", "'D'");
					tab1.put("UPDATED_DATE", "clock_timestamp()");

					condht.put("ACC_CODE_SNO", ACC_CODE_SNO);
					r = obj.recordSave(tab1, condht,"PMS_AME_MST_ACC_HEAD_MAP ", con);
					if (r > 0) {
						res = "<response><rows>" + r + "</rows></response>";
					}
				}else if (Integer.parseInt(process_code) == 4) {
					String ACC_CODE_SNO = obj.setValue("ACC_CODE_SNO", request);
					r = obj.delRecord("PMS_AME_MST_ACC_HEAD_MAP", "where ACC_CODE_SNO="+ACC_CODE_SNO, con,1);
					if (r > 0) {
						res = "<response><rows>" + r + "</rows></response>";
					} 
				}else if (Integer.parseInt(process_code) == 6) {
					String ac = obj.setValue("ac", request);
					
					int account_code_check = obj.getCount("COM_MST_ACCOUNT_HEADS","where ACCOUNT_HEAD_CODE=" +Integer.parseInt(ac));  
					if (account_code_check>=1)
					{
						String acv=obj.getValue("COM_MST_ACCOUNT_HEADS", "ACCOUNT_HEAD_DESC", "where ACCOUNT_HEAD_CODE="+Integer.parseInt(ac));
						int c = obj.getCount("PMS_AME_MST_ACC_HEAD_MAP","where MAIN_ITEM_SNO=" + mainitem+ " and SUB_ITEM_SNO=" + subitem 
										+ " and SCH_TYPE_ID='" + sch_type+ "' and ACCOUNT_HEAD_CODE="+Integer.parseInt(ac)+"");  
							res = "<response><status>2</status><acv>" + acv + "</acv><count>"+c+"</count></response>"; 
					}else     
					{   
						res = "<response><status>1</status><acv>-</acv><count>0</count></response>";
					}
					 
				}else if (Integer.parseInt(process_code) == 7) {
					String qry="SELECT SUB_ITEM_SNO,SUB_ITEM_DESC FROM PMS_AME_MST_SUB_ITEM where  MAIN_ITEM_SNO="+Integer.parseInt(mainitem);
					res=obj.resultXML(qry,con, obj);
				}    
			}
			
		}catch(Exception e)     
		{
			System.out.println(e);
			//response.sendRedirect(request.getContextPath() + "/index.jsp");	
		}obj.resposeWr(response, res); 
		obj = null;
		con = null;    
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
		
	}

}
