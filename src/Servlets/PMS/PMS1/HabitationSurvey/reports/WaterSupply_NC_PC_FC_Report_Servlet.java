package Servlets.PMS.PMS1.HabitationSurvey.reports;

import java.io.IOException;
import java.io.OutputStream;

import java.sql.Connection;
import java.sql.DriverManager;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class WaterSupply_NC_PC_FC_Report_Servlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, 
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, 
                       HttpServletResponse response) throws ServletException, 
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        Connection connection = null;
        /*
         * Get the Database Connection Object
         */
        try {
            ResourceBundle rs = 
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";
            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs.getString("Config.DSN");
            String strhostname = rs.getString("Config.HOST_NAME");
            String strportno = rs.getString("Config.PORT_NUMBER");
            String strsid = rs.getString("Config.SID");
            String strdbusername = rs.getString("Config.USER_NAME");
            String strdbpassword = rs.getString("Config.PASSWORD");
      //      ConnectionString = 
      //              strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + 
       //             ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
            Class.forName(strDriver.trim());
            connection = 
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(), 
                                                strdbpassword.trim());
        } catch (Exception ex) {
            String connectMsg = 
                "Could not create the connection" + ex.getMessage() + " " + 
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }

        String action = request.getParameter("action");
        System.out.println("The Action = " + action);
        if ((action.equalsIgnoreCase("Districts")) || 
            (action.equalsIgnoreCase("Districts_Hilly"))) {
            /*
             * Get the URL Path Information for Referential Parameter in the PDF
             */
            String ctxpath = request.getRequestURL().toString();
            String Qry="select dis,dname,syear,\n" + 
            "       (case when NC is null then 0 else NC end) as NC,\n" + 
            "       (case when PC is null then 0 else PC end) as PC,\n" + 
            "       (case when FC is null then 0 else FC end) as FC,\n" + 
            "       (case when tot is null then 0 else tot end) as tot\n" + 
            "from\n" + 
            "(\n" + 
            "  (\n" + 
            "    select district_code as dis, district_name as dname\n" + 
            "    from com_mst_districts\n" + 
            "  )a\n" + 
            "  join\n" + 
            "  (\n" + 
            "   select district_code as dist,count(habitation_code) as tot,max(survey_year) as syear\n" + 
            "   from hbs_mst_lpcd\n" + 
            "   group by district_code\n" + 
            "  )c\n" + 
            "  on a.dis=c.dist\n" + 
            "  full outer join\n" + 
            "  (\n" + 
            "   select district_code as dist,count(habitation_code) as NC\n" + 
            "   from hbs_mst_lpcd\n" + 
            "   where lpcd_current between 0 and 9\n" + 
            "   group by district_code\n" + 
            "  )d\n" + 
            "  on a.dis=d.dist\n" + 
            "  full outer join\n" + 
            "  (\n" + 
            "   select district_code as dist,count(habitation_code) as PC\n" + 
            "   from hbs_mst_lpcd\n" + 
            "   where lpcd_current between 10 and 39\n" + 
            "   group by district_code\n" + 
            "  )e\n" + 
            "  on a.dis=e.dist\n" + 
            "  full outer join\n" + 
            "  (\n" + 
            "   select district_code as dist,count(habitation_code) as FC\n" + 
            "   from hbs_mst_lpcd\n" + 
            "   where lpcd_current >=40\n" + 
            "   group by district_code\n" + 
            "  )f\n" + 
            "  on a.dis=f.dist\n" + 
            ")\n" + 
            "order by dis";
            String hill="";
            String is_hilly="false";
            System.out.println("The Context URL Path = " + ctxpath);
            Map parameters = new HashMap();
            parameters.put("ctxpath", ctxpath);
            if (action.equalsIgnoreCase("Districts_Hilly")) {
                Qry = "select dis,dname,max(syear) as syear,\n" + 
                "       (case when sum(NC) is null then 0 else sum(NC) end) as NC,\n" + 
                "       (case when sum(PC) is null then 0 else sum(PC) end) as PC,\n" + 
                "       (case when sum(FC) is null then 0 else sum(FC) end) as FC,\n" + 
                "       (case when sum(tot) is null then 0 else sum(tot) end) as tot\n" + 
                "from\n" + 
                "(\n" + 
                "  (\n" + 
                "    select district_code as dis, district_name as dname\n" + 
                "    from com_mst_districts\n" + 
                "  )a\n" + 
                "  join\n" + 
                "  (\n" + 
                "   select distinct district_code as dist, block_code as blk\n" + 
                "   from com_mst_blocks\n" + 
                "   where terrain='Hilly'\n" + 
                "  )b\n" + 
                "  on a.dis=b.dist\n" + 
                "  join\n" + 
                "  (\n" + 
                "   select district_code as dist,block_code as blok,count(habitation_code) as tot,max(survey_year) as syear\n" + 
                "   from hbs_mst_lpcd\n" + 
                "   group by block_code,district_code\n" + 
                "  )c\n" + 
                "  on a.dis=c.dist and b.blk=c.blok\n" + 
                "  full outer join\n" + 
                "  (\n" + 
                "   select district_code as dist,block_code as blok,count(habitation_code) as NC\n" + 
                "   from hbs_mst_lpcd\n" + 
                "   where lpcd_current between 0 and 9\n" + 
                "   group by block_code,district_code\n" + 
                "  )d\n" + 
                "  on a.dis=d.dist and b.blk=d.blok\n" + 
                "  full outer join\n" + 
                "  (\n" + 
                "   select district_code as dist,block_code as blok,count(habitation_code) as PC\n" + 
                "   from hbs_mst_lpcd\n" + 
                "   where lpcd_current between 10 and 39\n" + 
                "   group by block_code,district_code\n" + 
                "  )e\n" + 
                "  on a.dis=e.dist and b.blk=e.blok\n" + 
                "  full outer join\n" + 
                "  (\n" + 
                "   select district_code as dist,block_code as blok,count(habitation_code) as FC\n" + 
                "   from hbs_mst_lpcd\n" + 
                "   where lpcd_current >=40\n" + 
                "   group by block_code,district_code\n" + 
                "  )f\n" + 
                "  on a.dis=f.dist and b.blk=f.blok\n" + 
                ")\n" + 
                "where dis is not null\n" + 
                "group by dname,dis\n" + 
                "order by dis";
                hill="Hilly Habitation ";
                is_hilly="true";
            }
            parameters.put("Qry", Qry);
            parameters.put("hill", hill);
            parameters.put("is_hilly", is_hilly);
            String path = "";
            try {
                path = 
    getServletContext().getRealPath("/WEB-INF/ReportSrc/WaterSupply_NC_PC_FC.jasper");
                JasperPrint jasperPrint = 
                    JasperFillManager.fillReport(path, parameters, connection);
                System.out.println("Report is Created from Jasper Print...");

                OutputStream outuputStream = response.getOutputStream();

                JRExporter exporter = null;

                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", 
                                   "attachment; filename=\"WaterSupply_NC_PC_FC.pdf\"");
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
        } else if (action.equalsIgnoreCase("NC_PC_FC")) {
            int distCode = 
                Integer.parseInt(request.getParameter("district_code"));
            int category = Integer.parseInt(request.getParameter("category"));
            String is_hilly=request.getParameter("is_hilly");
            String whr_terrain="block_code is not null";
            if("true".equals(is_hilly))
            whr_terrain="terrain='Hilly'";
            String whr_lpcd="";
            switch(category)
            {
              case 1:whr_lpcd="and lpcd_current between 0 and 9"; break;
              case 2:whr_lpcd="and lpcd_current between 10 and 39"; break;
              case 3:whr_lpcd="and lpcd_current >= 40"; break;
            }
            System.out.println("District Code = " + distCode);
            System.out.println("Category = " + category);
            System.out.println("whr_lpcd = " + whr_lpcd);
            System.out.println("[[ is_hilly = " + is_hilly+" ]]");
            System.out.println("whr_terrain = " + whr_terrain);
            String ctxpath = request.getRequestURL().toString();
            System.out.println("The Context URL Path = " + ctxpath);
            Map parameters = new HashMap();
            parameters.put("district_code", distCode);
            parameters.put("whr_lpcd", whr_lpcd);
            parameters.put("whr_terrain", whr_terrain);
            parameters.put("ctxpath", ctxpath);
            String path = "";
            try {
                path = 
    getServletContext().getRealPath("/WEB-INF/ReportSrc/vs_General_Abstract_Habitations.jasper");
                JasperPrint jasperPrint = 
                    JasperFillManager.fillReport(path, parameters, connection);
                System.out.println("Report is Created from Jasper Print...");

                OutputStream outuputStream = response.getOutputStream();

                JRExporter exporter = null;

                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", 
                                   "attachement; filename=\"vs_General_Abstract_Habitations.pdf\"");
                exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, 
                                      jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, 
                                      outuputStream);
                exporter.exportReport();
                System.out.println("The BLOCK File is Downloaded");
                outuputStream.close();
            } catch (JRException e) {
                throw new ServletException(e);
            }
        } else if (action.equalsIgnoreCase("Districts_Hilly")) {
            /*
             * Get the URL Path Information for Referential Parameter in the PDF
             */
            String ctxpath = request.getRequestURL().toString();

            System.out.println("The Context URL Path = " + ctxpath);
            Map parameters = new HashMap();
            parameters.put("ctxpath", ctxpath);


            String path = "";
            try {
                path = 
    getServletContext().getRealPath("/WEB-INF/ReportSrc/WaterSupply_NC_PC_FC.jasper");
                JasperPrint jasperPrint = 
                    JasperFillManager.fillReport(path, parameters, connection);
                System.out.println("Report is Created from Jasper Print...");

                OutputStream outuputStream = response.getOutputStream();

                JRExporter exporter = null;

                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", 
                                   "attachment; filename=\"WaterSupply_NC_PC_FC.pdf\"");
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
        else if(action.equalsIgnoreCase("Hab_details")){
            int distCode = Integer.parseInt(request.getParameter("district_code"));
            int blkCode = Integer.parseInt(request.getParameter("block_code"));
            int panCode = Integer.parseInt(request.getParameter("panch_code"));
            int habCode = Integer.parseInt(request.getParameter("hab_code"));
            
            System.out.println("The District Code = "+distCode+" and Block Code = "+blkCode);
            System.out.println("The Panchayat Code = "+panCode+" and Habitation Code = "+habCode);
            /*
             * Show the Population Information for the latest two Census Year
             */
            String popQuery = "SELECT CENSUS_YEAR,POPULATION_TOTAL,POPULATION_SC,POPULATION_ST,HOUSES_TOTAL,RANK,LPCD_CURRENT FROM " + 
            "(SELECT A.CENSUS_YEAR, RANK() OVER (ORDER BY A.CENSUS_YEAR DESC) RANK ,A.POPULATION_TOTAL,A.POPULATION_SC,     " + 
            "A.POPULATION_ST,A.HOUSES_TOTAL,B.LPCD_CURRENT FROM HBS_MST_POPULATION A  " + 
            "INNER JOIN HBS_MST_LPCD B ON A.DISTRICT_CODE = B.DISTRICT_CODE AND A.BLOCK_CODE = B.BLOCK_CODE  " + 
            "AND A.PANCHAYAT_CODE = B.PANCHAYAT_CODE AND A.HABITATION_CODE = B.HABITATION_CODE  " + 
            "AND A.DISTRICT_CODE= "+distCode+" AND A.BLOCK_CODE="+blkCode+" AND A.PANCHAYAT_CODE="+panCode+" AND A.HABITATION_CODE="+habCode+")     " + 
            "WHERE RANK BETWEEN 1 AND 2";
            /*
             * Show the Scheme Types Information
             */
            String stQuery = "select * from (select district_code,scheme_slno,block_code,panchayat_code,habitation_code,  " + 
            "scheme_type_id,upper(to_char(scheme_completion_date,'mon')) as mon,   " + 
            "to_char(scheme_completion_date,'yyyy') as yr,programme_id,exec_agency_id,cost_in_lakhs,  " + 
            "scheme_status_id,OFFICE_ID from hbs_mst_schemes order by yr asc)a  inner join   " + 
            "(select scheme_type_no,scheme_type_name from rws_mst_scheme_types)b   " + 
            "on a.scheme_type_id=b.scheme_type_no inner join (select MCODE,prog_desc from   " + 
            "mode_of_programme)c on a.programme_id=c.MCODE inner join   " + 
            "(select exec_agency_code,exec_agency_name from rws_mst_exec_agencies)d   " + 
            "on a.exec_agency_id=d.exec_agency_code inner join (select scheme_status_id,  " + 
            "scheme_status_desc from rws_mst_scheme_status)e on a.scheme_status_id=e.scheme_status_id   " + 
            "where a.district_code= "+distCode+" and a.block_code="+blkCode+" and  " + 
            "a.panchayat_code="+panCode+" and a.habitation_code="+habCode+" order by a.scheme_slno";
            /*
             * Show the Source Details
             */
            String srcQuery = "Select a.SCHEME_SLNO||'-'||a.SOURCE_NUMBER as slno,b.WATER_SOURCE_TYPE as watersourcetype, " + 
            "a.DEPTH_IN_METRE,a.DIAMETRE_IN_METRE,a.SUMMER_YIELD_IN_LPM,a.WINTER_YIELD_IN_LPM, " + 
            "a.PRESENT_IN_USE,a.RIVER_DISTANCE,a.OORANI_DISTANCE,a.IRRIGATION_DISTANCE, " + 
            "a.DISTANCE_SOURCE_DEST,a.ELEVATION_METRES from HBS_MST_SCHEME_COMP_SOURCE a, " + 
            "RWS_MST_WATER_SOURCE_TYPE b where a.DISTRICT_CODE =  "+distCode+" and a.BLOCK_CODE = "+blkCode+" " + 
            "and a.PANCHAYAT_CODE = "+panCode+" and a.HABITATION_CODE = "+habCode+" and  " + 
            "a.WATER_SOURCE_TYPE_ID = b.WATER_SOURCE_TYPE_ID order by a.SCHEME_SLNO";
            /*
             * Show the Pumping Main Information
             */
            String pmQuery = "Select a.SCHEME_SLNO||'-'||a.PUMPMAIN_NO as pmno,a.LENGTH_KM,a.PIPE_SIZE_IN_MM, " + 
            "b.PUMPMAIN_PIPE_TYPE,c.PUMPMAIN_STATUS from HBS_MST_SCHEME_COMP_PM a,RWS_MST_PUMPMAIN_PIPETYPES b, " + 
            "RWS_MST_PUMPMAIN_STATUS c where DISTRICT_CODE =  "+distCode+" and BLOCK_CODE = "+blkCode+" " + 
            "and PANCHAYAT_CODE = "+panCode+" and  HABITATION_CODE = "+habCode+" and a.PUMPMAIN_PIPE_TYPE_ID = b.PUMPMAIN_PIPE_TYPE_ID and a.PUMPMAIN_STATUS_ID =  " + 
            "c.PUMPMAIN_STATUS_ID ORDER BY a.SCHEME_SLNO";
            /*
             * Show the Pumpset Information
             */
            String psQuery = "Select a.SCHEME_SLNO||'-'||a.SOURCE_NO||'-'||a.PUMPSET_NO as psno,a.MONTH_INSTALLED||'/'||a.YEAR_INSTALLED as installed, " + 
            "a.PS_HORSE_POWER,a.DUTY_Q_IN_LPM,a.DUTY_H_IN_LPM,a.HOURS_WORKING_PER_DAY,b.PUMPSET_STATUS, " + 
            "c.PUMPSET_TYPE from HBS_MST_SCHEME_COMP_PS a,RWS_MST_PUMPSET_STATUS b,RWS_MST_PUMPSET_TYPES c where DISTRICT_CODE =  "+distCode+" " + 
            "and BLOCK_CODE = "+blkCode+" and PANCHAYAT_CODE = "+panCode+" and  HABITATION_CODE = "+habCode+" and " + 
            "a.PUMPSET_STATUS_ID = b.PUMPSET_STATUS_ID and a.PUMPSET_TYPE_ID = c.PUMPSET_TYPE_ID ORDER BY a.SCHEME_SLNO";
            /*
             * Show the Service Reservoir Information
             */
            String srQuery = "Select a.SCHEME_SLNO||'-'||a.SR_NO as srno,a.SR_CAPACITY_LITRES,a.MONTH_EXECUTED||'/'||a.YEAR_EXECUTED as compdate,e.LOCATION,b.SERVICE_RESERVOIR_TYPE,c.SR_MATERIAL_TYPE,d.SR_RESERVOIR_STATUS  " + 
            "from HBS_MST_SCHEME_COMP_SR a,RWS_MST_SR_TYPES b,RWS_MST_SR_MATERIAL_TYPES c,RWS_MST_SR_STATUS d,RWS_MST_LOCATIONS e where DISTRICT_CODE =  "+distCode+" and BLOCK_CODE = "+blkCode+" " + 
            "and PANCHAYAT_CODE = "+panCode+" and  HABITATION_CODE = "+habCode+" and a.SERVICE_RESERVOIR_TYPE_ID = b.SERVICE_RESERVOIR_TYPE_ID and a.SR_MATERIAL_TYPE_ID =  " + 
            "c.SR_MATERIAL_TYPE_ID and a.SR_STATUS_ID = d.SR_RESERVOIR_STATUS_ID and a.SR_LOCATION_ID = e.LCODE ORDER BY a.SCHEME_SLNO";
            /*
             * Show the Distribution System Information
             */
            String dsQuery = "Select a.SCHEME_SLNO||'-'||a.DS_NO as dsno,a.LENGTH_KM,a.SIZE_IN_MM,a.WHETHER_ADEQUATE, " + 
            "b.DS_PIPE_TYPE,c.DS_PIPE_STATUS from HBS_MST_SCHEME_COMP_DS a,RWS_MST_DS_PIPE_TYPES b,RWS_MST_DS_PIPE_STATUS c  " + 
            "where DISTRICT_CODE =  "+distCode+" and BLOCK_CODE = "+blkCode+" and PANCHAYAT_CODE = "+panCode+" and  HABITATION_CODE = "+habCode+"  " + 
            "and a.DS_PIPE_TYPE_ID = b.DS_PIPE_TYPE_ID and a.DS_STATUS_ID = c.DS_PIPE_TYPE_ID ORDER BY a.SCHEME_SLNO";
            /*
             * Sample Query for the Sub Report Main Form
             */
            String generalQuery = "Select * from " + 
            "(Select DISTRICT_CODE,DISTRICT_NAME,ROWNUM RN from COM_MST_DISTRICTS WHERE ROWNUM <= 1 order by DISTRICT_NAME) " + 
            "WHERE RN > 0";
            
            Map parameters = new HashMap();    
            parameters.put("popQuery",popQuery);
            parameters.put("stQuery",stQuery);
            parameters.put("srcQuery",srcQuery);
            parameters.put("pmQuery",pmQuery);
            parameters.put("psQuery",psQuery);
            parameters.put("srQuery",srQuery);
            parameters.put("dsQuery",dsQuery);
            parameters.put("generalQuery",generalQuery);
            
            //String ctxpath=request.getRequestURL().toString();
            //parameters.put("SUBREPORT_DIR",ctxpath);
            
            String path="";        
            try
            {          
            
                path = getServletContext().getRealPath("/WEB-INF/ReportSrc/SchemeWiseHabitReport.jasper");  // old
                String ctxpath = path.substring(0,path.lastIndexOf("SchemeWiseHabitReport.jasper"));        // old
                
                //path = getServletContext().getRealPath("/WEB-INF/ReportSrc/SchemeWiseHabitReport.jasper"); // new
                //String ctxpath = request.getRequestURL().toString();                                         //new
                System.out.println("\nThe Path = "+path+"\nThe Context Path = "+ctxpath);
                parameters.put("SUBREPORT_DIR",ctxpath);
                
                JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, connection);
            
                OutputStream outuputStream = response.getOutputStream();
            
                JRExporter exporter = null;
            
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachement; filename=\"SchemeWiseHabitReport.pdf\"");
                exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outuputStream);
                exporter.exportReport();
                System.out.println("The File is Downloaded");
                outuputStream.close();
            }
            catch (JRException e)
            {
                throw new ServletException(e);
            }  
            /*
            try {
                path = 
            getServletContext().getRealPath("/WEB-INF/ReportSrc/SchemeWiseHabitReport.jasper");
                JasperPrint jasperPrint = 
                    JasperFillManager.fillReport(path, parameters, connection);
                System.out.println("Report is Created from Jasper Print...");

                OutputStream outuputStream = response.getOutputStream();

                JRExporter exporter = null;

                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", 
                                   "attachment; filename=\"SchemeWiseHabitReport.pdf\"");
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
            }*/
        }
    }
 }

