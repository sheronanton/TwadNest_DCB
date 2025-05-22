/* 
  * Created on : dd-mm-yy 
  * Author     : sathya
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

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.Security.classes.UserProfile;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
  
/**
 * Servlet implementation class Pms_Dcb_Beneficiary_OB_Entry_Status
 */
public class Pms_Dcb_Beneficiary_OB_Entry_Status extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private int oid=0,BeneficiaryType=0,subDivisionId=0,BeneficiaryName=0;
    public Pms_Dcb_Beneficiary_OB_Entry_Status() {
        super();
     
    }

    String new_cond=Controller.new_cond; 
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		int empid=0;
		
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
		}
		catch(Exception e)
		{
		   System.out.println("Redirect Error :"+e);
		}
		String userid=(String)session.getAttribute("UserId");
		System.out.println("Session id is--------------------------------------------------------------------------:"+userid);

		UserProfile empProfile = (UserProfile)session.getAttribute("UserProfile");

		System.out.println("emp id::" + empProfile.getEmployeeId());

		empid = empProfile.getEmployeeId();
		
		command=Obj.setValue("command", request);
		subDivisionId=Integer.parseInt(Obj.setValue("subDivision", request));
		 
		
		BeneficiaryName=Integer.parseInt(Obj.setValue("beneficiaryName", request));
		 
		System.out.println("DCB->Pms_Dcb_Beneficiary_OB_Entry_Status->command->"+command+"->BeneficiaryType"+BeneficiaryType+"SubDivision: "+subDivisionId);
		BeneficiaryType= Integer.parseInt(Obj.setValue("beneficiaryType", request)); 
	 
		
		
		
		try
        {
			con=Obj.con();
            System.out.println("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID="+empid);
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
        if(command.equalsIgnoreCase("pdf"))
		{
        	try
        	{
        		String path = "";
        		String condition ="";
        	   	File reportFile=null;
   			 	Map parameters = new HashMap();
   			 	parameters.put("officeId", oid);
   			 	
   			 	OutputStream outuputStream = response.getOutputStream();
			 	JRExporter exporter = null;
			 	JasperPrint jasperPrint =null;
			    response.setContentType("application/pdf");
		        
   			 	
	   			if(BeneficiaryType > 0)
	   			{
	   				parameters.put("beneficiaryType",BeneficiaryType);
	   			}
	   			if(subDivisionId > 0)
	   			{
	   				parameters.put("beneficiaryType",BeneficiaryType);
	   				parameters.put("subDivisionId",subDivisionId);
	   			}
	   			if(BeneficiaryName > 0)
	   			{
	   				parameters.put("beneficiaryType",BeneficiaryType);
	   				parameters.put("BeneficiaryName",BeneficiaryName);
	   				if(subDivisionId > 0)
	   				{
	   					parameters.put("subDivisionId",subDivisionId);
	   				}	
	   				
	   			}	
	   			else
   			 	{	
   			 		path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Ben_Scheme_All.jasper");
   			 		jasperPrint = JasperFillManager.fillReport(path, parameters, con);
   			 		 
   			 		
		            response.setHeader("Content-Disposition","attachment; filename=\"Ben_Scheme_All.pdf\"");
   			 	}   		
	   			exporter = new JRPdfExporter();
	            exporter.setParameter(JRExporterParameter.JASPER_PRINT,
	                                    jasperPrint);
	            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
	                                    outuputStream);
	            exporter.exportReport();
	            System.out.println("The File is Downloaded");
	            outuputStream.close();
	      } 
   		catch (JRException e) {
              e.printStackTrace();
          }
   		catch(Exception se)
   		{
   			se.printStackTrace();
   		}
		}
        
        PrintWriter out=response.getWriter();
        
      if (command.equalsIgnoreCase("divisionname"))
        {
        	 
            xmlvariable = "<response>";
            xmlvariable += "<command>divisionname</command>";
            xmlvariable += getDevisionName(con,ps,oid)+"</response>";
            out.println(xmlvariable);
        }
        if(command.equalsIgnoreCase("loadbeneficiarytype"))
		{
			xmlvariable = "<response>";
            xmlvariable += "<command>loadbeneficiarytype</command>";
            xmlvariable += getBeneficiaryType(con,ps)+"</response>";
            out.println(xmlvariable);
		}
        if(command.equalsIgnoreCase("loadsubdivision"))
		{
        	xmlvariable = "<response>";
            xmlvariable += "<command>loadsubdivision</command>";
            xmlvariable += getLoadSubDivision(con,ps,empid)+"</response>";
         
            out.println(xmlvariable);
		}	
		if(command.equalsIgnoreCase("loadbeneficiaryname"))
		{
	       	xmlvariable = "<response>";
	        xmlvariable += "<command>loadbeneficiaryname</command>";
	        xmlvariable += getLoadBeneficiaryName(con,ps,oid,BeneficiaryType,Obj)+"</response>";
		}
		if(command.equalsIgnoreCase("loadReport"))
		{
	       
			xmlvariable = "<response>";
	        xmlvariable += "<command>loadReport</command>";
	        xmlvariable += getReport(con,ps,Obj,oid,BeneficiaryType,subDivisionId,BeneficiaryName)+"</response>";
	       
	        out.println(xmlvariable);
		}
		
		
		  try {
			int Ben_Sno = Obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE"," where office_id="+oid);
			int Sch_Sno = Obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE"," where office_id="+oid);
			int month = Obj.getCount("PMS_DCB_OB_YEARLY"," where office_id="+oid);
			int fin_year = Obj.getCount("PMS_DCB_OB_YEARLY"," where office_id="+oid);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
                    System.out.println(officeName);
                    
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
            System.out.println(e + "not reterived!");
        }
		return xmlResponse;
	}
	public String getBeneficiaryType(Connection con,PreparedStatement ps)
	{
		String xmlResponse="";
		try
        {

			ps = con.prepareStatement("select BEN_TYPE_ID,BEN_TYPE_SDESC,BEN_TYPE_DESC from PMS_DCB_BEN_TYPE order by BEN_TYPE_ID");
            ResultSet result_new = ps.executeQuery();
            while(result_new.next())
            {
            	xmlResponse += "<BEN_TYPE_ID>" + result_new.getInt("BEN_TYPE_ID") + "</BEN_TYPE_ID>";
            	xmlResponse += "<BEN_TYPE_DESC>" + result_new.getString("BEN_TYPE_DESC") + "</BEN_TYPE_DESC>";
            	xmlResponse += "<BEN_TYPE_SDESC>" + result_new.getString("BEN_TYPE_SDESC") + "</BEN_TYPE_SDESC>";
            	xmlResponse += "<flag>success</flag>";
            
            }
           

        }
		catch (Exception e)
        {
			xmlResponse += "<flag>failure</flag>";
            System.out.println(e + "not reterived!");

        }
       return xmlResponse;
	}
	public String getLoadSubDivision(Connection con,PreparedStatement ps,int empId)
	{
		String xmlResponse="";
		try
	    {
	        ps = con.prepareStatement("select HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID AS EMPLOYEE_ID ,\n" +
	        "COM_MST_ALL_OFFICES_VIEW.SUBDIVISION_OFFICE_ID AS SUBDIVISION_OFFICE_ID ,\n" +
	        "COM_MST_ALL_OFFICES_VIEW.OFFICE_ID AS OFFICE_ID,\n" +
	        "COM_MST_ALL_OFFICES_VIEW.DIVISION_OFFICE_ID AS DIVISION_OFFICE_ID,\n" +
	        "OFFICE_LEVEL_ID AS OFFICE_LEVEL_ID ,\n" +
	        "OFFICE_NAME AS OFFICE_NAME\n" +
	        "from HRM_EMP_CURRENT_POSTING\n" +
	        "JOIN\n" +
	        "COM_MST_ALL_OFFICES_VIEW\n" +
	        "ON\n" +
	        "COM_MST_ALL_OFFICES_VIEW.DIVISION_OFFICE_ID=HRM_EMP_CURRENT_POSTING.OFFICE_ID\n" +
	        "AND\n" +
	        "OFFICE_LEVEL_ID='SD'\n" +
	        "WHERE\n" +
	        "HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=?");
	        //empid=2513
	        ps.setInt(1,empId);
	        ResultSet res_div = ps.executeQuery();
	        int flagvariable=0;
	        while(res_div.next())
	        {
	            flagvariable=1;
	            xmlResponse += "<SUBDIVISION_OFFICE_ID>" + res_div.getInt("SUBDIVISION_OFFICE_ID") + "</SUBDIVISION_OFFICE_ID>";
	            xmlResponse += "<OFFICE_NAME>" + res_div.getString("OFFICE_NAME") + "</OFFICE_NAME>";
	            xmlResponse += "<flag>success</flag>";
	        }
	        if(flagvariable==0)
	        {
	        	xmlResponse += "<SUBDIVISION_OFFICE_ID>-1</SUBDIVISION_OFFICE_ID>";
	        	xmlResponse += "<OFFICE_NAME>No data</OFFICE_NAME>";
	        	xmlResponse += "<flag>success</flag>";
	        }

	    }
	    catch (Exception e)
	    {
	    	xmlResponse += "<flag>failure</flag>";
	        System.out.println(e + "not reterived!");
	    }
	 return  xmlResponse;  
	}
	public String getLoadBeneficiaryName(Connection con,PreparedStatement ps,int officeId,int beneficiaryType,Controller Obj)
	{
		String xmlResponse="";
		try
	    {
	        ps = con.prepareStatement("select BENEFICIARY_SNO,\n" +
	        "BENEFICIARY_TYPE_ID,\n" +
	        "BENEFICIARY_NAME,\n" +
	        "OTHERS_PRIVATE_SNO,\n" +
	        "VILLAGE_PANCHAYAT_SNO,\n" +
	        "URBANLB_SNO,\n" +
	        "PMS_DCB_MST_BENEFICIARY.OFFICE_ID\n" +
	        "from PMS_DCB_MST_BENEFICIARY\n" +
	        "where \n" +new_cond +	        
	        "BENEFICIARY_TYPE_ID=?\n" +
	        "and\n" +
	        "PMS_DCB_MST_BENEFICIARY.OFFICE_ID=?");
	        //officeid=5340
	        ps.setInt(1,beneficiaryType);
	        ps.setInt(2,officeId);
	        ResultSet res = ps.executeQuery();
	        int flagname=0;
	       while(res.next())
	       {
	            flagname=1;
	            
	            
	            xmlResponse +=generateXML("BENEFICIARY_SNO", res.getString("BENEFICIARY_SNO"),1,Obj);
	            xmlResponse +=generateXML("BENEFICIARY_NAME", res.getString("BENEFICIARY_NAME"),2,Obj);
	            //xmlResponse += "<BENEFICIARY_SNO>" + res.getInt("BENEFICIARY_SNO") + "</BENEFICIARY_SNO>";
	            //xmlResponse += "<BENEFICIARY_NAME>" + res.getString("BENEFICIARY_NAME") + "</BENEFICIARY_NAME>";
	            //xmlResponse += "<BENEFICIARY_TYPE_ID>" + res.getInt("BENEFICIARY_TYPE_ID") + "</BENEFICIARY_TYPE_ID>";
	            //xmlResponse += "<OTHERS_PRIVATE_SNO>" + res.getInt("OTHERS_PRIVATE_SNO") + "</OTHERS_PRIVATE_SNO>";
	            //xmlResponse += "<VILLAGE_PANCHAYAT_SNO>" + res.getInt("VILLAGE_PANCHAYAT_SNO") + "</VILLAGE_PANCHAYAT_SNO>";
	            //xmlResponse += "<URBANLB_SNO>" + res.getInt("URBANLB_SNO") + "</URBANLB_SNO>";

	            xmlResponse += "<flag>success</flag>";
	        }
	        if(flagname==0)
	        {
	        	xmlResponse += "<BENEFICIARY_SNO>" + -1 + "</BENEFICIARY_SNO>";
	        	xmlResponse += "<BENEFICIARY_NAME>" + "No Data" + "</BENEFICIARY_NAME>";
	        	
	        	xmlResponse +=generateXML("BENEFICIARY_SNO", "-1",1,Obj);
	            xmlResponse +=generateXML("BENEFICIARY_NAME", "No Data",2,Obj);
	        	
	        	xmlResponse += "<flag>success</flag>";
	        }


	    }
	    catch (Exception e)
	    {
	    	xmlResponse += "<flag>failure</flag>";
	        System.out.println(e + "not reterived!");
	    }
		return xmlResponse;
	}
	public String getReport(Connection con,PreparedStatement ps,Controller Obj,int officeId,int BeneficiaryType,int subDivisionId,int BeneficiaryName)
	{
		String xmlResponse="";
		String condition=" ";
	 
		if(BeneficiaryType > 0)
		{
			condition+=(condition.length()>0)? ("and a.BENEFICIARY_TYPE_ID="+BeneficiaryType):" ";
		}
		if(subDivisionId > 0)
		{
		 
			condition+=(condition.length()>0)? (" and a.SUB_DIV_ID="+subDivisionId):" ";
		}
		if(BeneficiaryName > 0)
		{
			condition+=(condition.length()>0)? (" and a.BENEFICIARY_SNO="+BeneficiaryName):" ";
		}	
		 
		
		try
		{
				ps = con.prepareStatement("select a.BENEFICIARY_SNO,b.beneficiary_name,c.sch_name,a.sub_div_id,d.sch_type_shortdesc,c.sch_sno,count(a.METRE_LOCATION) as count,a.tariff_flag,t.ben_type_desc" +
	        		" from PMS_DCB_MST_BENEFICIARY_METRE a" +
	        		" join PMS_DCB_MST_BENEFICIARY b on  a.BENEFICIARY_SNO=b.BENEFICIARY_SNO" +
	        		" join pms_sch_master c on a.SCHEME_SNO=c.sch_sno" +
	        		" join pms_sch_lkp_type d on a.SCH_TYPE_ID=d.sch_type_id " +
	        		" JOIN PMS_DCB_BEN_TYPE t " +
	        		" on t.ben_type_id= b.beneficiary_type_id" +
	        		" where 0=0 and a.STATUS='L' and   a.OFFICE_ID = "+officeId+ "  and b.OFFICE_ID= "+officeId + condition +"    group by a.BENEFICIARY_SNO,a.tariff_flag,b.beneficiary_name,c.sch_name,d.sch_type_shortdesc,c.sch_sno,a.sub_div_id" +
	        				",t.ben_type_desc order by b.beneficiary_name");
			/*	System.out.println("select a.BENEFICIARY_SNO,b.beneficiary_name,c.sch_name,a.sub_div_id,d.sch_type_shortdesc,c.sch_sno,count(a.METRE_LOCATION) as count,a.tariff_flag,t.ben_type_desc" +
	        		" from PMS_DCB_MST_BENEFICIARY_METRE a" +
	        		" join PMS_DCB_MST_BENEFICIARY b on  a.BENEFICIARY_SNO=b.BENEFICIARY_SNO" +
	        		" join pms_sch_master c on a.SCHEME_SNO=c.sch_sno" +
	        		" join pms_sch_lkp_type d on a.SCH_TYPE_ID=d.sch_type_id " +
	        		" JOIN PMS_DCB_BEN_TYPE t " +
	        		" on t.ben_type_id= b.beneficiary_type_id" +
	        		" where 0=0 and b.OFFICE_ID= "+officeId + condition +"group by a.BENEFICIARY_SNO,a.tariff_flag,b.beneficiary_name,c.sch_name,d.sch_type_shortdesc,c.sch_sno,a.sub_div_id" +
	        				",t.ben_type_desc order by b.beneficiary_name");
 	*/
	        //officeid=5340
	        ResultSet res = ps.executeQuery();
	        String ben="0",sch="0"; int apr_year=0;
	        
	         String  m=Obj.getValue("PMS_DCB_SETTING", "MONTH", "where  OFFICE_ID="+oid);
	   		 String  y=Obj.getValue("PMS_DCB_SETTING", "YEAR", "where OFFICE_ID="+oid);
	   		 
	        if (Integer.parseInt(m)<=3)
			{
				apr_year = Integer.parseInt(y) - 1;
			} 
			else
			{
				apr_year = Integer.parseInt(y) ;
				
			}
	        while(res.next())
	        {
	        	ben=res.getString("BENEFICIARY_SNO");
	        	sch=res.getString("sch_sno");
	        	
	        	xmlResponse +=generateXML("BENEFICIARY_SNO", ben,2,Obj);
	        	xmlResponse +=generateXML("BeneficiaryName", res.getString("beneficiary_name"),2,Obj);
	        	
	        	xmlResponse +=generateXML("SchemeName", res.getString("sch_name"),2,Obj);
	        	xmlResponse +=generateXML("SchemeType", res.getString("sch_type_shortdesc"),2,Obj);
	        	xmlResponse +=generateXML("SchemeSno", sch,1 ,Obj);
	        	xmlResponse +=generateXML("MeterLocation", res.getString("count"),2,Obj);
	        	xmlResponse +=generateXML("TariffMode", res.getString("tariff_flag"),2,Obj);
	        	xmlResponse +=generateXML("ben_type_desc", res.getString("ben_type_desc"),2,Obj);
	        	xmlResponse +=generateXML("sub_div", Obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", " where OFFICE_ID ="+res.getString("sub_div_id")),2,Obj);
	   		 
	   		 
	   		
	   		
	   		 int y_count= Obj.getCount("PMS_DCB_OB_YEARLY"," where OFFICE_ID="+oid+" and   FIN_YEAR="+apr_year+" and MONTH=4 and BENEFICIARY_SNO = "+ben+" and SCH_SNO="+sch );
	   		 int dmdmonth= Obj.getCount("PMS_DCB_OB_YEARLY"," where OFFICE_ID="+oid+" and   FIN_YEAR="+y+" and MONTH="+m+" and BENEFICIARY_SNO = "+ben+" and SCH_SNO="+sch );
	   		 
	   		 
	   	 
	   		xmlResponse +=generateXML("apc", Integer.toString(y_count),2,Obj);
	   		xmlResponse +=generateXML("dmdmonth", Integer.toString(dmdmonth),2,Obj);
	        }	
	    } 
		catch(Exception e)
		{
			xmlResponse += "<flag>failure</flag>";
	        System.out.println(e + "not reterived!");
		}
		return xmlResponse;
	}
	public void reportPdf(Connection con,HttpServletResponse response)
	{
		
	}
	public String generateXML(String tagName,String value,int NullValue,Controller Obj)
	{
		String xml="<"+tagName+">"+ Obj.isNull(value,NullValue)+"</"+tagName+">";
		
		return xml;
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}

}

