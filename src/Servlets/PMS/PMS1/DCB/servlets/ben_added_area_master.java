package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

public class ben_added_area_master extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ben_added_area_master() {
		super();
	}
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost( request,response);
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		Controller obj = new Controller();
		response.setContentType(CONTENT_TYPE);
		PrintWriter pr = response.getWriter();
		Connection con = null;
		Statement stmt = null;
		String userid="";
		try {
			con = obj.con();
			stmt = con.createStatement();		
		HttpSession session = request.getSession(false);
		  userid = (String) session.getAttribute("UserId");
		if (userid == null) 
		{
				response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		String Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	//	String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
		if (Office_id.equalsIgnoreCase("0")) Office_id="5000";
				
		String xml = "<result>";		
		String flag=obj.setValue("flag", request);
		String type=obj.setValue("type", request); 
		String type2=obj.setValue("type2", request);  
		System.out.println("DCB->ben_added_area_master->flag->" + flag);
		String dist1=obj.setValue("dist1", request); 
		String dist2=obj.setValue("dist2", request);
		String block_1=obj.setValue("block_1", request); 
		String block_2=obj.setValue("block_2", request);
		if (Integer.parseInt(flag)==1)
		{
			xml+=obj.resultXML("select BEN_TYPE_ID,BEN_TYPE_DESC from PMS_DCB_BEN_TYPE where (1=1) and BEN_TYPE_ID in (select BENEFICIARY_TYPE_ID  from PMS_DCB_MST_BENEFICIARY where status='L' and office_id="+Office_id+") ",con,obj);
		}else if (Integer.parseInt(flag)==2)
		{
			String cond="";
			if (!block_1.equalsIgnoreCase("0"))
				 cond=" and block_sno="+block_1;
			xml+=obj.resultXML("select BENEFICIARY_SNO,BENEFICIARY_NAME from PMS_DCB_MST_BENEFICIARY where BENEFICIARY_TYPE_ID="+type+" and status='L' and office_id="+Office_id+" and DISTRICT_CODE="+dist1+" "+cond+" order by BENEFICIARY_NAME",con,obj);
		}else if (Integer.parseInt(flag)==3)
		{
			String cond=" "; 
			if (!block_2.equalsIgnoreCase("0"))
				 cond=" and block_sno="+block_2;
			xml+=obj.resultXML("select BENEFICIARY_SNO,BENEFICIARY_NAME from PMS_DCB_MST_BENEFICIARY where BENEFICIARY_TYPE_ID="+type2+" and status='L' and office_id="+Office_id+"  and DISTRICT_CODE="+dist2+" "+cond+" order by BENEFICIARY_NAME",con,obj);
		}
		else if (Integer.parseInt(flag)==4)
		{
			int ben1_sno=Integer.parseInt(obj.setValue("ben1_sno", request));
			int ben2_sno=Integer.parseInt(obj.setValue("ben2_sno", request));

			String BENEFICIARY_TYPE_ID=obj.getValue("PMS_DCB_MST_BENEFICIARY", "BENEFICIARY_TYPE_ID", "where BENEFICIARY_SNO="+ben2_sno);
			String BENEFICIARY_TYPE_ID_SUB=obj.getValue("PMS_DCB_MST_BENEFICIARY", "BENEFICIARY_TYPE_ID_SUB", "where BENEFICIARY_SNO="+ben2_sno);
			String BENEFICIARY_NAME=obj.getValue("PMS_DCB_MST_BENEFICIARY", "BENEFICIARY_NAME", "where BENEFICIARY_SNO="+ben2_sno);
			PreparedStatement ps=con.prepareStatement("update PMS_DCB_MST_BENEFICIARY set BENEFICIARY_TYPE_ID_OLD=BENEFICIARY_TYPE_ID,BENEFICIARY_TYPE_ID_SUB_OLD=BENEFICIARY_TYPE_ID_SUB,BENEFICIARY_TYPE_ID="+BENEFICIARY_TYPE_ID+" ,BENEFICIARY_TYPE_ID_SUB="+BENEFICIARY_TYPE_ID_SUB+",ADDED_AREA='Y',ADDED_TO_BEN_SNO=? ,UPDATED_BY_USER_ID='"+userid+"',UPDATED_DATE=clock_timestamp() where BENEFICIARY_SNO=?");			
			ps.setInt(1, ben2_sno);  
			ps.setInt(2, ben1_sno);		   	  
			int row_insert=ps.executeUpdate(); 
			  
			ps=con.prepareStatement("update PMS_DCB_MST_BENEFICIARY set ADDED_TO_BEN_NAME='"+BENEFICIARY_NAME+"', BENEFICIARY_TYPE_ID="+BENEFICIARY_TYPE_ID+" ,BENEFICIARY_TYPE_ID_SUB="+BENEFICIARY_TYPE_ID_SUB+" where BENEFICIARY_SNO=?");			
			ps.setInt(1, ben1_sno);		   	
			  row_insert=ps.executeUpdate();
			  
			 
			ps=con.prepareStatement("update PMS_DCB_MST_BENEFICIARY_METRE set BENEFICIARY_TYPE_ID=?,UPDATED_BY_USER_ID='"+userid+"',UPDATED_DATE=clock_timestamp() where BENEFICIARY_SNO=?");
			ps.setInt(1,Integer.parseInt(type2));
			ps.setInt(2, ben1_sno);			
			row_insert=ps.executeUpdate();   
			ps=null;			 
			xml+="<row_count>"+row_insert+"</row_count>";			
		}if (Integer.parseInt(flag)==7)
		{
			 
			int ben2_sno=Integer.parseInt(obj.setValue("ben2_sno", request));
			PreparedStatement ps=con.prepareStatement("update PMS_DCB_MST_BENEFICIARY set BENEFICIARY_TYPE_ID_OLD=?,ADDED_AREA='N',ADDED_TO_BEN_SNO=? where BENEFICIARY_SNO=?");
			ps.setInt(1,0);
			ps.setInt(2, 0); 
			ps.setInt(3, ben2_sno);			
			int row_insert=ps.executeUpdate();			
			xml+="<row_count>"+row_insert+"</row_count>";			
		}else if (Integer.parseInt(flag)==5)
		{
			String qry="select  a.BENEFICIARY_NAME,a.BENEFICIARY_SNO,a.BENEFICIARY_TYPE_ID,(select BEN_TYPE_DESC from  PMS_DCB_BEN_TYPE b where b.BEN_TYPE_ID=a.BENEFICIARY_TYPE_ID) as typedesc,A.BLOCK_SNO ,(select block_name  from com_mst_blocks WHERE block_sno=a.BLOCK_SNO) as BLOCK_NAME, "+ 
					   "(select BEN_TYPE_DESC from  PMS_DCB_BEN_TYPE b where b.BEN_TYPE_ID=a.BENEFICIARY_TYPE_ID_OLD) as oldtypedesc,"+
					   "(select BENEFICIARY_NAME from PMS_DCB_MST_BENEFICIARY where status='L' and BENEFICIARY_SNO=a.ADDED_TO_BEN_SNO) as added_name,(select block_name  from com_mst_blocks WHERE block_sno in (select block_sno from PMS_DCB_MST_BENEFICIARY where ADDED_TO_BEN_SNO=a.ADDED_TO_BEN_SNO and ADDED_AREA='Y' and status='L' and BENEFICIARY_TYPE_ID_SUB = 6) ) as BLOCK_NAME2 from PMS_DCB_MST_BENEFICIARY a  where office_id="+Office_id+" and  added_area='Y'  order by oldtypedesc";
			 
			xml+=obj.resultXML(qry,con,obj);
		}    
		else if (Integer.parseInt(flag)==9)
		{
			xml+=obj.resultXML("  select block_name,block_sno from com_mst_blocks where DISTRICT_CODE="+dist1+" order by block_name",con,obj,1);
		} 
		else if (Integer.parseInt(flag)==10)
		{
			xml+=obj.resultXML("  select block_name,block_sno from com_mst_blocks where DISTRICT_CODE="+dist2+" order by block_name",con,obj,1);
		}else if (Integer.parseInt(flag)==11)
		{
			int ben1_sno=Integer.parseInt(obj.setValue("ben1_sno", request));
			String qry=" select METRE_LOCATION,METRE_SNO,SCH_NAME from PMS_DCB_METRE_LIVE where BENEFICIARY_SNO="+ben1_sno;
		
			xml+=obj.resultXML(qry,con,obj);
		}  
		xml += "</result>";	  
		pr.write(xml);
		pr.flush();
		pr.close();
		obj.conClose(con);
		}catch (Exception e)  
		{
			System.out.println( e);
		}
		
		
		
		
	}
}
