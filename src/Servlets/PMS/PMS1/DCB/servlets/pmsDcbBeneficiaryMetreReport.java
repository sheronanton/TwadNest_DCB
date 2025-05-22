/* 
  * Created on : dd-mm-yy 
  * Author     :  RUBIN
  * Last Date  : 20/09/2011
  *----------------------------------------------------------------------------- 
  * Revision History (Release 1.0.0.0) 
  *-----------------------------------------------------------------------------
  * Date			Description							Done By
  * 17/09/2011		Add the Beneficiary Status to 'L'  	PS
  * 21/09/2011		Add the Meter Status to 'L'			PS
  *---------|---------------|--------------------------------------------------- 
  */
package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class pmsDcbBeneficiaryMetreReport
 */
public class pmsDcbBeneficiaryMetreReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public pmsDcbBeneficiaryMetreReport()
    {
        super();
    }
    String new_cond=Controller.new_cond; 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int empid=0;
		int oid=0,beneficiarySno=0,schemeSno=0; //,BeneficiaryType=0,subDivisionId=0,BeneficiaryName=0;
		String command="",xmlvariable="";
		Connection con=null;
		PreparedStatement ps_oid=null,ps=null;
		Controller Obj=new Controller();
		
		HttpSession session=request.getSession(false);
		
        try
        {
              if(session==null)
              {
                     System.out.println(request.getContextPath()+"/index.jsp");
                     response.sendRedirect(request.getContextPath()+"/index.jsp");
               }
                System.out.println(session);

        }
        catch(Exception e)
        {
           System.out.println("Redirect Error :"+e);
        }
        String userid=(String)session.getAttribute("UserId");
        UserProfile empProfile = (UserProfile)session.getAttribute("UserProfile");
        empid = empProfile.getEmployeeId();
		try
        {
			con=Obj.con();
            ps_oid = con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
            ps_oid.setInt(1,empid);
            ResultSet result_new = ps_oid.executeQuery();
            if(result_new.next())
            {
            	oid = result_new.getInt("OFFICE_ID");
            }

        }
        catch (Exception e)
        {
             System.out.println(e);
        }
		
		command=Obj.setValue("command", request);
		beneficiarySno= Integer.parseInt(Obj.setValue("beneficiarySno", request)); 
		schemeSno=Integer.parseInt(Obj.setValue("schemeSno", request));
		PrintWriter out=response.getWriter();
		if (command.equalsIgnoreCase("divisionname"))
        {
            xmlvariable = "<response>";
            xmlvariable += "<command>divisionname</command>";
            xmlvariable += getDevisionName(con,ps,oid)+"</response>";
            out.println(xmlvariable);
        }
		if(command.equalsIgnoreCase("schemeBeneficiaryName"))
		{
            xmlvariable = "<response>";
            xmlvariable += "<command>schemeBeneficiaryName</command>";
            xmlvariable += getSchBenName(con,ps,Obj,beneficiarySno,schemeSno)+"</response>";
            out.println(xmlvariable);
		}	
		if(command.equalsIgnoreCase("loadReport"))
		{
			xmlvariable = "<response>";
	        xmlvariable += "<command>loadReport</command>";
	        xmlvariable +=getReport(con,ps,Obj,beneficiarySno,schemeSno) +"</response>";
	        out.println(xmlvariable);
		}
		
		out.flush();
        out.close();
        Obj.conClose(con);
	}
	public String getDevisionName(Connection con,PreparedStatement ps,int officeId)
	{
		String xmlResponse="";
		String officeName="";
		try
        {


			ps = con.prepareStatement("select OFFICE_NAME from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID=?");
            ps.setInt(1,officeId);
            ResultSet result_new = ps.executeQuery();
            if(result_new.next())
            {
            		officeName = result_new.getString("OFFICE_NAME");
            		xmlResponse += "<officename>"+officeName+"</officename>";
            		xmlResponse += "<flag>success</flag>";
                    
            }
            else
            {
            	xmlResponse += "<officename>0</officename>";
            	xmlResponse += "<flag>success</flag>";

            }

        }
        catch (Exception e)
        {
        	xmlResponse += "<flag>failure</flag>";
        }
		return xmlResponse;
	}
	
	public String getSchBenName(Connection con,PreparedStatement ps,Controller Obj,int ben,int sch)
	{
		String xmlResponse="";
		;
		try
        {


			ps = con.prepareStatement("select b.beneficiary_name,c.sch_name from PMS_DCB_MST_BENEFICIARY_METRE a" +
							" join PMS_DCB_MST_BENEFICIARY b on b.status='L' and a.meter_status='L' and  a.BENEFICIARY_SNO=b.BENEFICIARY_SNO" +
							" join pms_sch_master c on a.SCHEME_SNO=c.sch_sno where b.BENEFICIARY_SNO=? and "+
							" c.sch_sno=?");
            ps.setInt(1,ben);
            ps.setInt(2,sch);
            ResultSet res = ps.executeQuery();
            if(res.next())
            {
            	//System.out.println("inside loop");
            	xmlResponse +=generateXML("BeneficiaryName", res.getString(1),2,Obj);
	        	xmlResponse +=generateXML("SchemeName", res.getString(2),2,Obj); 
	        	xmlResponse += "<flag>success</flag>";
                    
            }
            else
            {
            	xmlResponse += "<flag>failure</flag>";
    	    
            }

        }
        catch (Exception e)
        {
        	xmlResponse += "<flag>failure</flag>";
            System.out.println(e + "not reterived!");
        }
		return xmlResponse;
	}
	public String getReport(Connection con,PreparedStatement ps,Controller Obj,int BeneficiarySno,int schemeSno)
	{
		String xmlResponse="";
		
		System.out.println("inside Report");
				
		try
		{
			 
			String qry=" SELECT a.METRE_LOCATION," +
					"  a.METRE_FIXED, " +
					"  a.METRE_WORKING, " +
					"  a.METRE_TYPE," +
					"  a.MULTIPLY_FACTOR," +
					"  a.METRE_INIT_READING, " +
					"  a.PARENT_METRE, " +
					"  a.BENEFICIARY_SNO," +
					"  a.METRE_SNO," +
					"  a.SCHEME_SNO, d.ALLOT_QTY, d.MIN_QTY,"	+
					"  d.MIN_FLAG, " +
					"  d.ALLOT_FLAG  from pms_dcb_mst_beneficiary_metre a" +
					"  left outer join PMS_DCB_ALLOTTED d on" +
					"  a.meter_status='L' and a.metre_sno=d.metre_sno  WHERE " +
					
					"  a.beneficiary_sno = ? AND a.scheme_sno = ?  order by a.BENEFICIARY_SNO";
					
			
			
			Obj.testQry(qry);
			ps = con.prepareStatement(qry);
			ps.setInt(1,BeneficiarySno);
			ps.setInt(2,schemeSno);
	        ResultSet res = ps.executeQuery();
	        
	        String ben_sno="",sch_sno="";
	        if(res!=null)
	        {	
				        while(res.next())
				        {
				        	xmlResponse +=generateXML("METRE_LOCATION", res.getString("METRE_LOCATION"),2,Obj);
				        	xmlResponse +=generateXML("METRE_FIXED", res.getString("METRE_FIXED"),2,Obj);
				        	xmlResponse +=generateXML("METRE_WORKING", res.getString("METRE_WORKING"),2,Obj);
				        	xmlResponse +=generateXML("METRE_TYPE", res.getString("METRE_TYPE"),1,Obj);
				        	xmlResponse +=generateXML("MULTIPLY_FACTOR", res.getString("MULTIPLY_FACTOR"),1,Obj);
				        	xmlResponse +=generateXML("METRE_INIT_READING", res.getString("METRE_INIT_READING"),1 ,Obj);
				        	xmlResponse +=generateXML("PARENT_METRE", res.getString("PARENT_METRE"),2,Obj);
				        	xmlResponse +=generateXML("MIN_QTY", res.getString("MIN_QTY"),1,Obj);
				        	xmlResponse +=generateXML("ALLOT_QTY", res.getString("ALLOT_QTY"),1,Obj);
				        	xmlResponse +=generateXML("MIN_FLAG", res.getString("MIN_FLAG"),2,Obj);
				        	xmlResponse +=generateXML("ALLOT_FLAG", res.getString("ALLOT_FLAG"),2,Obj);
				        	String metre_sno=res.getString("METRE_SNO");
				        	xmlResponse +=generateXML("METRE_SNO",metre_sno,1,Obj);
				        	ben_sno= res.getString("BENEFICIARY_SNO");
				        	sch_sno= res.getString("SCHEME_SNO");
				        	int c=Obj.getCount("PMS_DCB_TARIFF_SLAB", " where BENEFICIARY_SNO="+ben_sno+" and SCH_SNO="+sch_sno+"" +
				        							" and active_status='A' order by BENEFICIARY_SNO ");
				        	xmlResponse +=generateXML("count",Integer.toString(c),1,Obj);
				        	xmlResponse +=generateXML("BENEFICIARY_SNO",ben_sno,1,Obj);
				        	xmlResponse +=generateXML("SCHEME_SNO",sch_sno,1,Obj);
				        	 
				        }	
				        xmlResponse +=traiffRate(ben_sno,sch_sno,Obj);
	        }   
	        else
	        {
	        	xmlResponse += "<flag>failure</flag>";
	        }	
	    } 
		
		catch(Exception e)
		{
			xmlResponse += "<flag>failure</flag>";
	        System.out.println(e + "not reterived!");
		}
		
		return xmlResponse;
	}
	public String generateXML(String tagName,String value,int NullValue,Controller Obj)
	{
		String xml="<"+tagName+"><![CDATA["+ Obj.isNull(value,NullValue)+"]]></"+tagName+">";
		
		return xml;
	} 
	public String traiffRate(String ben,String sch,Controller Obj)
	{
		String xml="";
		ResultSet rs=null;
		 
		try
		{
			Connection con=Obj.con();
			Statement stmt=con.createStatement();
			String qury="SELECT BENEFICIARY_SNO," +
				"  SCH_SNO, QTY_FROM," +
				"  QTY_TO, TARIFF_RATE, TARIFF_FLAG," +
				"  METRE_SNO" +
				"  FROM PMS_DCB_TARIFF_SLAB  WHERE active_status='A' and " +
				"  BENEFICIARY_SNO="+ben+" AND SCH_SNO="+sch +" order by BENEFICIARY_SNO";  // and metre_sno="+ metre;
				
			rs=stmt.executeQuery(qury);	
			if(rs!=null)
			{
				while(rs.next())
				{
					xml+=generateXML("QTY_FROM",rs.getString("QTY_FROM"),1,Obj);
					xml+=generateXML("QTY_TO",rs.getString("QTY_TO"),1,Obj);
					xml+=generateXML("TARIFF_RATE",rs.getString("TARIFF_RATE"),1,Obj);
					
				}	
			}
			else
			{
				xml+=generateXML("QTY_FROM","No data",1,Obj);
				xml+=generateXML("QTY_TO","No data",1,Obj);
				xml+=generateXML("TARIFF_RATE","No data",1,Obj);
			}	
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println(xml);
		return xml;
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
