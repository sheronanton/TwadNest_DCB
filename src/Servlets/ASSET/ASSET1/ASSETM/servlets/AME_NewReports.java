package Servlets.ASSET.ASSET1.ASSETM.servlets;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Hashtable;

import Servlets.AME.AME1.AMEM.servlets.Controller;

public class AME_NewReports extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AME_NewReports() {
		super();
	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		String command=null;
		String query_cond=null;
		String result_xml=null;
		String table=null;
		int process_code=0;
		try {
			Connection con = null;
			Controller obj = new Controller();
			con = obj.con();
			obj.createStatement(con);	
			process_code=Integer.parseInt(obj.setValue("process_code", request));
			result_xml="<response>";
			if (process_code==0)
			{ 
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}else if (process_code==1)
			{
				query_cond=" ";
				table="PMS_AME_MST_MAIN_ITEM";
				ArrayList columns=new ArrayList(); 
					columns.add("MAIN_ITEM_SNO");
					columns.add("MAIN_ITEM_DESC");
				
				Hashtable cond=new Hashtable();
					cond.put("GROUP_SNO",Integer.parseInt("1"));
				
				result_xml+=obj.recordShow(columns,cond , table, con) ;
			}
			result_xml+="</response>";
			System.out.println(result_xml);  
			obj.resposeWr(response, result_xml); 
			obj = null;
			con = null;   
			
		}catch(Exception e) {
			System.out.println(e);
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
	}

}
