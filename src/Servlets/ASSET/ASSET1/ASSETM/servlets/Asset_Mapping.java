package Servlets.ASSET.ASSET1.ASSETM.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;
import Servlets.ASSET.ASSET1.ASSETM.servlets.Controller;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class Asset_Mapping extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    public Asset_Mapping()
    {
        super();  
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType(CONTENT_TYPE);
		Controller obj = new Controller();
		Controller obj1 = new Controller();
		
		Connection con = null,con1 = null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String comment=""; 
		try
		{
			con=obj.con();  
			con1=obj1.con();
			obj1.createStatement(con1);
			obj.createStatement(con);
			HttpSession session = request.getSession(false);
			String userid = (String) session.getAttribute("UserId");
			if (userid == null) 
			{  
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			comment=obj.setValue("command", request);
			System.out.println("ASSET->Asset_Mapping->command->" + comment);  
			String lcompdesc="",lcompid="",xml="<response>";
			String lscompdesc="",lscompid="",msg=""; 
			String MAP_SNO="";
			 Hashtable ht=new Hashtable();
			 Hashtable ht1=new Hashtable();
			 	if (comment.equalsIgnoreCase("delete"))
				{
			 		MAP_SNO=obj.setValue("sno", request);
			 		int row=obj.delRecord("PMS_ASSET_MAP", "where MAP_SNO="+MAP_SNO, con1, 1);
			 		 xml+="<row>"+row+"</row>";
				}
			if (comment.equalsIgnoreCase("update"))
			{
					 
					ht1.put("MAP_SNO",obj.setValue("sno", request));	
					ht.put("COMP_ID	",obj.setValue("comp", request));
					ht.put("SUBCOMP_ID",obj.setValue("subcomp", request));	
					ht.put("FORM_SNO",obj.setValue("form", request));	
					int row=obj.recordSave(ht,ht1, "PMS_ASSET_MAP", con);  
					 xml+="<row>"+row+"</row>";
			}
			if (comment.equalsIgnoreCase("insert"))
			{
					MAP_SNO=Integer.toString(obj.getMax("PMS_ASSET_MAP", "MAP_SNO", ""));	
					ht.put("MAP_SNO",MAP_SNO);	
					ht.put("COMP_ID	",obj.setValue("comp", request));
					ht.put("SUBCOMP_ID",obj.setValue("subcomp", request));	
					ht.put("FORM_SNO",obj.setValue("form", request));	
					int row=obj.recordSave(ht, "PMS_ASSET_MAP", con);  
					 xml+="<row>"+row+"</row>";
			}
			
			
			
			if (comment.equalsIgnoreCase("comp"))
			{
				 ps=con.prepareStatement("select  a.COMP_DESC as COMP_DESC,a.COMP_ID as COMP_ID from  PMS_SCH_LKP_COMPONENT a order by  COMP_DESC ");
				 rs=ps.executeQuery();
 				while (rs.next())  
				{
					lcompdesc=rs.getString("COMP_DESC");
					lcompid=rs.getString("COMP_ID");
					xml+="<sno>"+lcompid+"</sno><name>"+lcompdesc+"</name>";
				}
				
			} 
			if (comment.equalsIgnoreCase("subcomp"))
			{
				String comp=obj.setValue("comp", request);
				 ps=con.prepareStatement("select SUBCOMP_DESC ,SUBCOMP_ID from  PMS_SCH_LKP_SUBCOMPONENT where COMP_ID="+comp+" order by  SUBCOMP_DESC ");
				 rs=ps.executeQuery();
 				while (rs.next())  
				{
					lcompdesc=rs.getString("SUBCOMP_DESC");
					lcompid=rs.getString("SUBCOMP_ID");
					xml+="<sno>"+lcompid+"</sno><name>"+lcompdesc+"</name>";
				}
			}
			if (comment.equalsIgnoreCase("forms"))
			{ 
			 
				 ps=con.prepareStatement("select FORM_SNO ,FORM_DESC from  PMS_ASSET_FORM order by  FORM_SNO");
				 rs=ps.executeQuery();
 				while (rs.next())  
				{
					lcompdesc=rs.getString("FORM_DESC");
					lcompid=rs.getString("FORM_SNO");
					xml+="<sno>"+lcompid+"</sno><name>"+lcompdesc+"</name>";
				}
			} 
			if (comment.equalsIgnoreCase("view"))
			{
				String qry="SELECT mapp.MAP_SNO ,  mapp.form_sno,mapp.comp_id,mapp.subcomp_id,comp.comp_desc,subcomp.subcomp_desc "+
					    " FROM   ( SELECT MAP_SNO,comp_id,subcomp_id, form_sno  FROM "+
					" pms_asset_map   ) mapp  JOIN  (SELECT subcomp_desc,subcomp_id "+
					" FROM   pms_sch_lkp_subcomponent  ) "+
					" subcomp ON subcomp.subcomp_id = mapp.subcomp_id JOIN "+
					" ( SELECT comp_desc,comp_id  FROM  pms_sch_lkp_component "+
						" ) comp ON comp.comp_id = mapp.comp_id   "	;
				  ps=con.prepareStatement(qry);
				  String frmsno="";
				  rs=ps.executeQuery();
				 
				  while (rs.next())  
					{
					  frmsno=rs.getString("form_sno");
						xml+="<MAP_SNO>"+rs.getString("MAP_SNO")+"</MAP_SNO><form_sno>"+frmsno+"</form_sno>";
						xml+="<comp_desc>"+rs.getString("comp_desc")+"</comp_desc><subcomp_desc>"+rs.getString("subcomp_desc")+"</subcomp_desc>";
						xml+="<comp_id>"+rs.getString("comp_id")+"</comp_id><subcomp_id>"+rs.getString("subcomp_id")+"</subcomp_id>";
						xml+="<FORM_DESC>"+obj1.getValue("PMS_ASSET_FORM", "FORM_DESC", " where FORM_SNO="+frmsno)+"</FORM_DESC>";
						xml+="<FORM_JSP>"+obj1.getValue("PMS_ASSET_FORM", "FORM_JSP", " where FORM_SNO="+frmsno)+"</FORM_JSP>";
					}
				 
			}
			//join ( select  , from    ) frm on frm.FORM_SNO=mapp.FORM_SNO
			   
			xml+="</response>";
			PrintWriter pr = response.getWriter();
		 
			pr.write(xml);
			pr.flush();
			pr.close(); 
			
			
			
			  
		}catch(Exception e)
		{
			
			
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}

}
