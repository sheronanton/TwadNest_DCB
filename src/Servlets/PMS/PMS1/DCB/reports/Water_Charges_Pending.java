/*
 * 	Name :Panneer Selvam.K
 *  Date :20/07/2014
 *  Purpose : Water Chages Pending
 *  
 * */
package Servlets.PMS.PMS1.DCB.reports;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import Servlets.PMS.PMS1.DCB.servlets.Controller;

public class Water_Charges_Pending extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Water_Charges_Pending() {
		super();
	}
	public String option="";
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException 
			{
		Map parameters = new HashMap();
		Controller obj = new Controller();
		OutputStream outuputStream_dcb=null;
		JasperPrint jasperPrint_dcb=null ;
		String command = request.getParameter("command");// Command
		String com = request.getParameter("com");
		if (command == null || command.equals("")) 	command = "no command";
		Connection con = null;
		try {
			con = obj.con();
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		try {
			Statement stmt = con.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
		int count=1;
		String month="0",month1="0",year="0",month2="0",year2="0",option="0",option_new1="0",option_new="0",reporttype="0",path="",ftype="1";
		String ofday=obj.setValue("date", request);
		String ofmonth=obj.setValue("prmonth", request);
		System.out.println("ofday" + ofday);
		String dis=obj.setValue("dis", request);
		month=obj.setValue("month", request);
		month1=obj.setValue("month1", request);
		year=obj.setValue("year", request);
		String year1=obj.setValue("year1", request);
		 year2=obj.setValue("year2", request);
		 month2=obj.setValue("month2", request);
		this.option=obj.setValue("option", request);
		reporttype=obj.setValue("reporttype", request);	
		option_new=obj.setValue("option_new", request);	
		option_new1=obj.setValue("option_new1", request);	
		int app_year=0,end_days=0;
		String month_name=obj.month_val(month,1);
		int endofday=obj.month_val2(Integer.parseInt(month),Integer.parseInt(year)) ;
		int endofday1=obj.month_val2(Integer.parseInt(month1),Integer.parseInt(year)) ;
		 ftype=obj.setValue("ftype", request);	
		 if(Integer.parseInt(month1)==4)
				count=1;
		       
			if(Integer.parseInt(month1)==5)
				count=2;
			if(Integer.parseInt(month1)==6)
				count=3;
			if(Integer.parseInt(month1)==7)
				count=4;
			if(Integer.parseInt(month1)==8)
				count=5;
			if(Integer.parseInt(month1)==9)
				count=6;
			if(Integer.parseInt(month1)==10)
				count=7;
			if(Integer.parseInt(month1)==11)
				count=8;
			if(Integer.parseInt(month1)==12)
				count=9;
			if(Integer.parseInt(month1)==1)
				count=10;
			if(Integer.parseInt(month1)==2)
				count=11;
			if(Integer.parseInt(month1)==3)
				count=12;
	     System.out.println("DCB->Water_Charges_Pending ->Command->"+com+"->process_code->"+reporttype+"->option->" +option+"->option_new->" +option_new+"->count->" +count);

		if (Integer.parseInt(month)<=4)
		{
			app_year=Integer.parseInt(year)-1;
		}else
		{
			app_year=Integer.parseInt(year);
		}
		String month_val="(Apr-"+app_year+"-"+month_name+"-"+year+")";
		String month_label=obj.month_val(month)+" - " + Integer.parseInt(year) ;
		if (Integer.parseInt(month)!=4)
		{
			month_val="(Apr-"+app_year+"-"+month_name+"-"+year+")";
			
		}else
		{
			month_val="(Apr-"+app_year+")";
		}  
		String prv_label="";
		if (Integer.parseInt(month)==4)
		{
			prv_label="";	
		}else if (Integer.parseInt(month)==5)
		{
			prv_label=" April "+app_year;  
			
		} else if (Integer.parseInt(month)==1)
		{
			prv_label=" From April "+app_year+" Dec "+app_year ;
		}else
		{ 
			prv_label=" From April "+app_year+"   "+obj.month_val(Integer.parseInt(month)-1)+"  "+year ;
		}
		
		if(com.equals("Dis"))
		{
			if(Integer.parseInt(month)==Integer.parseInt(ofmonth))
			{
				endofday=Integer.parseInt(ofday);
			}
			if(option_new.equals("1"))
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/district_water_due_new.jasper");
			}if(option_new.equals("2"))
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/district_water_due.jasper");
			}
			
		//	path = getServletContext().getRealPath("/WEB-INF/ReportSrc/district_water_due.jasper");
		//	path = getServletContext().getRealPath("/WEB-INF/ReportSrc/district_water_due1.jasper");
			response.setHeader("Content-Disposition", "attachment; filename=\"Districtwise Water Due\"");
			parameters.put("dis",dis);
		}
		
		if(com.equals("Dis1"))
		{	
		if(option_new.equals("1"))
		{
		if (reporttype.equals("1"))    
		{
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/ .jasper");
		}else if (reporttype.equals("2"))    
		{
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_Main_Ben_type_wise.jasper");
		}
		else if (reporttype.equals("30"))    
		{
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_Main_Ben_type_wise_mul.jasper");
		}
		else if (reporttype.equals("31"))    
		{
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_Main_Ben_type_wise_mul_all.jasper");
		}
		else if (reporttype.equals("33"))    
		{
			
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Reg_water_due_new_format.jasper");
			parameters.put("count",count);
			
		}
		else if (reporttype.equals("3"))    
		{
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_CMA_Abstract.jasper");
		}else if (reporttype.equals("4"))    
		{
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_DTP_Abstract.jasper");
		}else if (reporttype.equals("5"))    
		{
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_VP_Abstract.jasper");
		}else if (reporttype.equals("6"))    
		{
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_CORP_New.jasper");
		}else if (reporttype.equals("7"))    
		{
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_MUN_New.jasper");
		}else if (reporttype.equals("8"))    
		{
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_MUN_Details.jasper"); 
		}else if (reporttype.equals("9"))    
		{
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_DTP_New.jasper");
		}else if (reporttype.equals("10"))    
		{
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_DTP_Details.jasper");
		}else if (reporttype.equals("11"))    
		{
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_DTP_RTP_Details.jasper");
		}else if (reporttype.equals("12"))    
		{  
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_VP_New.jasper");
		}else if (reporttype.equals("14"))    
		{  
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_VP_New_BLK.jasper");
		} else if (reporttype.equals("15"))    
		{  
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_MUN_III_Details.jasper");
		} else if (reporttype.equals("16"))    
		{  
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_Private_Details.jasper");
		} else if (reporttype.equals("17"))    
		{  
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Regionwise.jasper");
			response.setHeader("Content-Disposition", "attachment; filename=\"Regionwise Details\"");

		} else if (reporttype.equals("18"))    
		{  
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_Private_abstract.jasper");

		}  else if (reporttype.equals("19"))    
		{  
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Off.jasper");

		}
		else if (reporttype.equals("31"))    
		{  
			if(option_new1.equals("1"))
			{
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Dcb_bentype_report.jasper");
			response.setHeader("Content-Disposition", "attachment; filename=\"Ben_Type\"");
			parameters.put("count",count);
			}
		}
			else if (reporttype.equals("20"))    
		{  
			if(option_new1.equals("1"))
			{
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Corporation_new.jasper");
			response.setHeader("Content-Disposition", "attachment; filename=\"Corporation\"");
			parameters.put("count",count);
			}
			if(option_new1.equals("2"))
			{
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Corporation_new_demand.jasper");
			response.setHeader("Content-Disposition", "attachment; filename=\"Corporation\"");
			parameters.put("count",count);
			}
			
		} else if (reporttype.equals("21"))    
		{  
			if(option_new1.equals("1"))
			{
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Municipality_new.jasper");
			response.setHeader("Content-Disposition", "attachment; filename=\"Municipality\"");
			parameters.put("count",count);
			}
			if(option_new1.equals("2"))
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Municipality_new_demand.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Municipality\"");
				parameters.put("count",count);
			}
		} 
		else if (reporttype.equals("29"))    
		{  
			if(option_new1.equals("1"))
			{
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Municipality_abst_new.jasper");
			response.setHeader("Content-Disposition", "attachment; filename=\"Municipality\"");
			parameters.put("count",count);
			}
		//	if(option_new1.equals("2"))
		//	{
		//		path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Municipality_new_demand.jasper");
		//		response.setHeader("Content-Disposition", "attachment; filename=\"Municipality\"");
		//		parameters.put("count",count);
		//	}
		} 
		else if (reporttype.equals("22"))    
		{  
			if(option_new1.equals("1"))
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/TP_new.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Town Panchayat\"");
				parameters.put("count",count);
			}
			if(option_new1.equals("2"))
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/TP_new_demand.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Town Panchayat\"");
				parameters.put("count",count);
			}		
		} 
		else if (reporttype.equals("28"))    
		{  
			if(option_new1.equals("1"))
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/pri_new.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Private\"");
				parameters.put("count",count);
			}
			if(option_new1.equals("2"))
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/pri_new_demand.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Private\"");
				parameters.put("count",count);
			}		
		}
		else if (reporttype.equals("32"))    
		{  
			if(option_new1.equals("1"))
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/VP_new_detailed1.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Village Panchayat\"");
				parameters.put("count",count);
			}
			if(option_new1.equals("2"))
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/");
				response.setHeader("Content-Disposition", "attachment; filename=\"Village Panchayat\"");
				parameters.put("count",count);
			}		
			
		} 
		else if (reporttype.equals("23"))    
		{  
			if(option_new1.equals("1"))
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/VP_new1.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Village Panchayat\"");
				parameters.put("count",count);
			}
			if(option_new1.equals("2"))
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/VP_new1_demand.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Village Panchayat\"");
				parameters.put("count",count);
			}		
			
		}
		else if (reporttype.equals("25"))    
		{  
			
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/water_due_new.jasper");
			response.setHeader("Content-Disposition", "attachment; filename=\"WATER CHARGES DUE\"");
			
		} 
		else if (reporttype.equals("26"))    
		{  
			
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Division_Demand.jasper");
			response.setHeader("Content-Disposition", "attachment; filename=\"Divisionwise Demand and collection\"");
			parameters.put("count",count);
		} 
		else if (reporttype.equals("27"))    
		{  
			
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Reg_water_due.jasper");
			response.setHeader("Content-Disposition", "attachment; filename=\"Region Officewise WC\"");
			parameters.put("count",count);
		} 
		}
		
		
		else if(option_new.equals("2"))
		{
			
			if(Integer.parseInt(month)==Integer.parseInt(ofmonth))
			{
				endofday=Integer.parseInt(ofday);
			}
			
			
			if (reporttype.equals("2"))    
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_Main_Ben_type_wise_new.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Over All Abstract\"");
			}
			else if (reporttype.equals("30"))    
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_Main_Ben_type_wise_new_mul.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Over All Abstract\"");
			}
			else if (reporttype.equals("31"))    
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_Main_Ben_type_wise_new_mul_all.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Over All Abstract\"");
			}
			else if  (reporttype.equals("3"))    
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_CMA_Abstract_NEW.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"CMA Abstract\"");
			}else if (reporttype.equals("4"))    
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_DTP_Abstract_NEW.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"DTP Abstract\"");
			}else if (reporttype.equals("5"))    
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_VP_Abstract_NEW.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"DRD Abstract\"");
			}else if (reporttype.equals("6"))    
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_CORP_New1.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"CMA Report - Corporation\"");
			}else if (reporttype.equals("7"))    
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_MUN_New1.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"CMA Report - Municipality District\"");
			}else if (reporttype.equals("8"))    
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_MUN_Details1.jasper"); 
				response.setHeader("Content-Disposition", "attachment; filename=\"CMA Report - Municipality Details\"");
			}else if (reporttype.equals("9"))    
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_DTP_New1.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"DTP Report - Town Panchayats\"");
			}else if (reporttype.equals("10"))    
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_DTP_Details1.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"DTP Report - Town Panchayats Block Wise\"");
			}
			else if (reporttype.equals("12"))    
			{  
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_VP_New1.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"DRD- Village Panchayats\"");
			}		
			else if (reporttype.equals("14"))    
			{  
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_VP_New_BLK1.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"DRD- Village Panchayats Block Wise\"");
			}		
			else if (reporttype.equals("15"))    
			{  
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_MUN_III_Details1.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"CMA Report - Municipality III Grade Details\"");
			}else if (reporttype.equals("16"))    
			{  
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_Private_Details1.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Private Details\"");

			} 
			else if (reporttype.equals("17"))    
			{  
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Reg.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Regionwise Details\"");

			} 
			else if (reporttype.equals("18"))    
			{  
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DRD_Private_abstract1.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Private Abstract\"");

			}
			else if (reporttype.equals("19"))    
			{  
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Off_new.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Officewise\"");

			}
			else if (reporttype.equals("31"))    
			{  
				
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Dcb_bentype_report_new.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Dcb_bentype_report\"");
				parameters.put("count",count);
			}
			else if (reporttype.equals("20"))    
			{  
				
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Corporation.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Corporation\"");
				parameters.put("count",count);
			} else if (reporttype.equals("21"))    
			{  
				
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Municipality.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Municipality\"");
				parameters.put("count",count);
			} 
			else if (reporttype.equals("29"))    
			{  
				
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Municipality_abs.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Municipality\"");
				parameters.put("count",count);
			} 
			else if (reporttype.equals("22"))    
			{  
				
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/TP.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Town Panchayat\"");
				parameters.put("count",count);
			} 
			else if (reporttype.equals("28"))    
			{  
				
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/pri.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Private\"");
				parameters.put("count",count);
			} 
			else if (reporttype.equals("23"))    
			{  
				
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/VP.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Village Panchayat\"");
				parameters.put("count",count);
			} 
			else if (reporttype.equals("24"))    
			{  
				
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/VP_new.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Village Panchayat\"");
				parameters.put("count",count);
			} else if (reporttype.equals("25"))    
			{  
				
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/water_due.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"WATER CHARGES DUE\"");
				
			} 
			else if (reporttype.equals("26"))    
			{  
				
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Division_Demand_new.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Divisionwise Demand and collection\"");
				parameters.put("count",count);
			} 
			else if (reporttype.equals("27"))    
			{  
				
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Reg_water_due_new.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Region Officewise WC\"");
				parameters.put("count",count);
			}
						
		}
			}
		System.out.println("path"+path);
		System.out.println("month2"+month2);
		System.out.println("year2"+year2);
		
		parameters.put("month2",month2);
		parameters.put("year2",year2);
		parameters.put("month1",month1);
		parameters.put("year",year);
		parameters.put("year1",year1);
		parameters.put("month",month);
		parameters.put("app_year",Integer.toString(app_year));
		parameters.put("monthval",month_val);
		parameters.put("month_label",month_label);
		parameters.put("prv_label",prv_label);
		parameters.put("endofday",endofday);
		parameters.put("endofday1",endofday1);
		System.out.println("ftype" + ftype);
		System.out.println("parameters"+parameters);
		if (Integer.parseInt(ftype)==1)
		{   
			parameters.put("divby","10000000");
			parameters.put("label","Rs.in Crores");
		}else if (Integer.parseInt(ftype)==2)
		{
			parameters.put("divby","100000");
			parameters.put("label","Rs.in Lakhs");
		}else if (Integer.parseInt(ftype)==3)
		{
			parameters.put("divby","1");
			parameters.put("label",""); 
		}
		
		try
		{
			outuputStream_dcb = response.getOutputStream();
			jasperPrint_dcb=JasperFillManager.fillReport(path, parameters,con);
		}catch(Exception e)
		{ 
//			obj.dis(e.toString());
			System.out.println(e);
			
		}
		pdfshow(response, jasperPrint_dcb, outuputStream_dcb);
		
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}
	public void pdfshow(HttpServletResponse response,JasperPrint jasperPrint,OutputStream outuputStream)
	{
		 
		if (option.equalsIgnoreCase("2"))  
		{   
			excelshow( response, jasperPrint, outuputStream);
		}else
		{
			try {
				JRExporter exporter = null;
			  response.setContentType("application/pdf");
			  response.setHeader("Content-Disposition","attachment; filename=\"Report.pdf\"");
			  exporter = new JRPdfExporter();
			  exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
			  exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
			  exporter.exportReport();
			  outuputStream.close();  
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
		}
	}
	public void excelshow(HttpServletResponse response,JasperPrint jasperPrint,OutputStream outuputStream)
	{ 
		if (option.equalsIgnoreCase("1"))
		{
			pdfshow( response, jasperPrint, outuputStream);
		}else
		{
			try {
			response.setContentType("application/vnd.ms-excel");
		//	response.setHeader("Content-Disposition","attachment;filename=\"Report.xls\"");  
		//	response.addHeader("Content-Disposition", "attachment");
			//response.setHeader("Content-Disposition","attachment;filename=\"Report.xls\"");
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, xlsReport);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS,Boolean.FALSE);
		    exporterXLS.exportReport();
			byte[] bytes;
			bytes = xlsReport.toByteArray(); 
			outuputStream.write(bytes, 0, bytes.length);
			outuputStream.flush();
			outuputStream.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}  
}
