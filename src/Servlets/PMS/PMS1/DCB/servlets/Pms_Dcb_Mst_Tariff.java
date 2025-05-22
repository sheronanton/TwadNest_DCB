/* 
  * Created on : dd-mm-yy 
  * Author     : Sathya
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
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Date;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.poi.poifs.filesystem.OfficeXmlFileException;

public class Pms_Dcb_Mst_Tariff extends HttpServlet
{
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }
    String new_cond=Controller.new_cond;
    String meter_status2=Controller.meter_status2;
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
        response.setHeader("Cache-Control","no-cache");
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String command_var="";
        String xmlvariable="";
        int tariff_Id;
        double tariff_Rate;
        int countbentype=0;
        tariff_Id=0;
        tariff_Rate=0;
        int ben_desc_id=0;
   //     int uom_id=0;
        int Beneficiary_Type=0;
    //    int Uom=0;
        int countinscheck=0;
       String status="";
        String Tariff_wef="";
        String tariff_Desc="";
        String charge_type_desc_val="";
        String ben_desc_val="";
        String uom_desc_val="";
        Connection connection = null;
        PreparedStatement ps,ps1,ps2,ps3,ps4,ps_ins_check,ps_max;
        ResultSet res,res1,res2,res3,res4,res_ins_check,res_max;
        Controller obj=new Controller();
        res1= null;
        res=null;
        ps = null;
        ps1 = null;
        ps2=null;
        ps3=null;
        res2=null;
        res3=null;
        res_ins_check=null;
        try
        {
        command_var=obj.setValue("command", request);
        Beneficiary_Type=Integer.parseInt(obj.setValue("Beneficiary_Type", request));
        tariff_Rate=Double.parseDouble(obj.setValue("tariff_Rate", request));
        Tariff_wef=obj.setValue("Tariff_wef", request);
  //     Uom=Integer.parseInt(obj.setValue("UOM", request));
        status=obj.setValue("status", request);
        }
        catch (Exception e)
        {
        System.out.println("Error in reterival:" + e);
        }
        try
        {
            connection = obj.con();
            System.out.println(connection);
            try
            {
                connection.clearWarnings();
            }
            catch (SQLException e)
            {
                System.out.println("Exception in creating statement:" + e);
            }
        }
        catch (Exception e)
        {
            System.out.println("Exception in openeing connection:" + e);
        }
        HttpSession session=request.getSession(false);
        try
        {
              if(session==null)
              {
                     response.sendRedirect(request.getContextPath()+"/index.jsp");
               }
        }
        catch(Exception e)
        {
        }
        String userid=(String)session.getAttribute("UserId");
        if (command_var.equalsIgnoreCase("add"))
        {
                    xmlvariable = "<response>";
                    xmlvariable += "<command>add</command>";
                    try
                    {
	                    ps_max = connection.prepareStatement("select max(TARIFF_ID) AS TARIFF_ID from PMS_DCB_MST_TARIFF");
	                	res_max = ps_max.executeQuery();
	                	if(res_max.next())
	                    {
	                    	tariff_Id= res_max.getInt("TARIFF_ID");
	                    }
                    }
                    catch(Exception e)
                    {
                    	System.out.println("Erroe");
                    }
                     if (tariff_Id>0)
                     {
                    	 tariff_Id=tariff_Id+1;
                     }
                     else
                     {
                    	 tariff_Id=1;
                     }
                    try
                    {

                        ps_ins_check = connection.prepareStatement("select count(*) as countinscheck from  PMS_DCB_MST_TARIFF where BENEFICIARY_TYPE=? and ACTIVE_STATUS='A'");
                        ps_ins_check.setInt(1, Beneficiary_Type);
                        res_ins_check = ps_ins_check.executeQuery();
                        if(res_ins_check.next())
                        {
                            countinscheck=res_ins_check.getInt("countinscheck");

                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println("Error in reteriving the count");
                    }

                    if(countinscheck==0)
                    {
	                    try
	                    {
	                    	
	                    	
	                    	String prv_rate=obj.getValue("PMS_DCB_MST_TARIFF", "TARIFF_RATE", " where BENEFICIARY_TYPE="+Beneficiary_Type);
	                    
	                    	
	                    
	                    	
	                        ps = connection.prepareStatement("insert into PMS_DCB_MST_TARIFF(TARIFF_RATE,TARIFF_WEF,UPDATED_BY_USER_ID,UPDATED_TIME,BENEFICIARY_TYPE,ACTIVE_STATUS,TARIFF_ID,prv_rate) values(?,to_date(?,'DD/MM/YYYY'),?,clock_timestamp(),?,?,?,?::numeric)");
	                        ps.setDouble(1, tariff_Rate);
	                        ps.setString(2, Tariff_wef);
	                       // ps.setInt(3, Uom);
	                        ps.setString(3, userid);
	                        ps.setInt(4, Beneficiary_Type);
	                        ps.setString(5, status);
	                        ps.setInt(6, tariff_Id);
	                        ps.setString(7, prv_rate);
	                         
	                        ps.executeUpdate();
	                        ps1 = connection.prepareStatement("select max(tariff_Id) as tariff_Id from PMS_DCB_MST_TARIFF");
	                        res = ps1.executeQuery();
	                        if(res.next())
	                        {
	                            tariff_Id= res.getInt("tariff_Id");
	                        }
	                        
	                        ps3 = connection.prepareStatement("select BEN_TYPE_ID,BEN_TYPE_DESC FROM PMS_DCB_BEN_TYPE where BEN_TYPE_ID= ?");
	                        ps3.setInt(1, Beneficiary_Type);
	                        res3 = ps3.executeQuery();
	                        if(res3.next())
	                        {
	                            ben_desc_id=res3.getInt("BEN_TYPE_ID");
	                            ben_desc_val= res3.getString("BEN_TYPE_DESC");
	                        }
	                        
	             //           ps4 = connection.prepareStatement("select UOM_SNO,UOM_DESC FROM PMS_DCB_MST_UOM where UOM_SNO= ?");
	       //                 ps4.setInt(1, Uom);
	      //                  res4 = ps4.executeQuery();
	      //                  if(res4.next())
	    //                    {
	     //                       uom_id=res4.getInt("UOM_SNO");
	     //                       uom_desc_val= res4.getString("UOM_DESC");
	     //                   }
	                        xmlvariable += "<tariff_Id>" + tariff_Id + "</tariff_Id>";
	                        xmlvariable += "<data>";
	                        xmlvariable += "</data>";
	                        xmlvariable += "<tariff_Rate>" + tariff_Rate + "</tariff_Rate>";
	                        xmlvariable += "<Tariff_wef>" + Tariff_wef + "</Tariff_wef>";
	                        xmlvariable += "<datatwo>";
	     //                   xmlvariable += "<Uom_id>" + uom_id + "</Uom_id>";
	     //                   xmlvariable += "<uom_val_desc>"+uom_desc_val + "</uom_val_desc>";
	                        xmlvariable += "</datatwo>";
	                        xmlvariable += "<dataone>";
	                        xmlvariable +="<Beneficiary_Type_id>"+ben_desc_id+"</Beneficiary_Type_id>";
	                        xmlvariable += "<Beneficiary_Type_desc>" + ben_desc_val + "</Beneficiary_Type_desc>";
	                        xmlvariable += "</dataone>";
	                        xmlvariable += "<activestatus>"+status + "</activestatus>";
	                        xmlvariable += "<countinscheck>0</countinscheck>";
	                        xmlvariable += "<flag>success</flag>";
	                    }
	                    catch(Exception e)
	                    {
	                        System.out.println("Error in"+e);
	                        xmlvariable += "<flag>failure</flag>";
	                        xmlvariable += "<countinscheck>0</countinscheck>";
	                    }
                    }
                    else
                    {
                        xmlvariable += "<countinscheck>1</countinscheck>";
                        xmlvariable += "<flag>success</flag>";
                    }
          xmlvariable += "</response>";
      }

        else if (command_var.equalsIgnoreCase("get"))
        {
            xmlvariable = "<response>";
            xmlvariable += "<command>get</command>";
            try
            {
            		String qry="select PMS_DCB_MST_TARIFF.TARIFF_ID AS TARIFF_ID,\n" +
                    "PMS_DCB_MST_TARIFF.TARIFF_RATE AS TARIFF_RATE,\n" +
                    "PMS_DCB_BEN_TYPE.BEN_TYPE_DESC AS BEN_TYPE_DESC,\n" +
                    "PMS_DCB_MST_TARIFF.TARIFF_WEF AS TARIFF_WEF,\n" +                     
                    "PMS_DCB_MST_TARIFF.ACTIVE_STATUS AS ACTIVE_STATUS,\n" +
                    "PMS_DCB_MST_TARIFF.BENEFICIARY_TYPE AS BENEFICIARY_TYPE" +
                    " FROM\n" +
                    "PMS_DCB_MST_TARIFF\n" +
                    "JOIN\n" +
                    "PMS_DCB_BEN_TYPE\n" +
                    "ON      \n" +
                    "PMS_DCB_MST_TARIFF.BENEFICIARY_TYPE=PMS_DCB_BEN_TYPE.BEN_TYPE_ID \n" +
                    "WHERE PMS_DCB_BEN_TYPE.BEN_TYPE_CATEGORY='L'\n" +
                    "and\n" +
                    "PMS_DCB_MST_TARIFF.ACTIVE_STATUS='A'\n" +
                    "ORDER BY\n" +
                    "PMS_DCB_MST_TARIFF.TARIFF_ID";
                     ps = connection.prepareStatement(qry);
                res = ps.executeQuery();
                while (res.next())
                { 
                    
                   xmlvariable += "<tariff_Id>" + res.getInt("TARIFF_ID") + "</tariff_Id>";
                    xmlvariable += "<data>";
                    xmlvariable += "</data>";
                    xmlvariable += "<tariff_Rate>" + res.getDouble("TARIFF_RATE") + "</tariff_Rate>";
                    xmlvariable += "<Tariff_wef>" + res.getDate("TARIFF_WEF") + "</Tariff_wef>";
                    xmlvariable += "<dataone>";
                    xmlvariable +="<Beneficiary_Type_id>"+res.getInt("BENEFICIARY_TYPE")+"</Beneficiary_Type_id>";
                    xmlvariable += "<Beneficiary_Type_desc>" + res.getString("BEN_TYPE_DESC") + "</Beneficiary_Type_desc>";
                    xmlvariable += "</dataone>";
                    xmlvariable += "<datatwo>";
                    xmlvariable += "</datatwo>";
                    xmlvariable += "<activestatus>"+res.getString("ACTIVE_STATUS") + "</activestatus>";
                    xmlvariable += "<flag>success</flag>";
                }
            }
            catch (Exception e)
            {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        }
        else if (command_var.equalsIgnoreCase("update"))
        {
            xmlvariable = "<response>";
            xmlvariable += "<command>update</command>";
            try
            {
                 tariff_Id=Integer.parseInt(request.getParameter("tariff_Id"));
                 PreparedStatement ps5=connection.prepareStatement("update PMS_DCB_MST_TARIFF set RATE_FLAG='' where BENEFICIARY_TYPE=?");
                 ps5.setInt(1, Beneficiary_Type);
                 ps5.executeUpdate(); 
                
                 
                 ps = connection.prepareStatement("update PMS_DCB_MST_TARIFF set TARIFF_RATE=?,TARIFF_WEF=to_date(?,'DD/MM/YYYY'),BENEFICIARY_TYPE=?,ACTIVE_STATUS=?, UPDATED_BY_USER_ID=? ,UPDATED_TIME=clock_timestamp() , RATE_FLAG='P'  where TARIFF_ID =?");
                 ps.setDouble(1, tariff_Rate);
                 ps.setString(2, Tariff_wef);
            //     ps.setInt(3, Uom);
                 ps.setInt(3, Beneficiary_Type);
                 ps.setString(4, status);
                 ps.setString(5, userid);  
                 ps.setInt(6, tariff_Id);
                 ps.executeUpdate();
                 ps3 = connection.prepareStatement("select BEN_TYPE_ID,BEN_TYPE_DESC FROM PMS_DCB_BEN_TYPE where BEN_TYPE_ID= ?");
                 ps3.setInt(1, Beneficiary_Type);
                 res3 = ps3.executeQuery();
                 if(res3.next())
                 {
                     ben_desc_id=res3.getInt("BEN_TYPE_ID");
                     ben_desc_val= res3.getString("BEN_TYPE_DESC");
                 }
                 
        //         ps4 = connection.prepareStatement("select UOM_SNO,UOM_DESC FROM PMS_DCB_MST_UOM where UOM_SNO= ?");
       //          ps4.setInt(1, Uom);
       //          res4 = ps4.executeQuery();
       //          if(res4.next())
       //          {
      //               uom_id=res4.getInt("UOM_SNO");
      //               uom_desc_val= res4.getString("UOM_DESC");
      //           }
                 xmlvariable += "<tariff_Id>" + tariff_Id + "</tariff_Id>";
                 xmlvariable += "<data>";
                 xmlvariable += "</data>";
                 xmlvariable += "<tariff_Rate>" + tariff_Rate + "</tariff_Rate>";
                 xmlvariable += "<tariff_Rate>" + tariff_Rate + "</tariff_Rate>";
                 xmlvariable += "<Tariff_wef>" + Tariff_wef + "</Tariff_wef>";
                 xmlvariable += "<datatwo>";
   //              xmlvariable += "<Uom_id>" + uom_id + "</Uom_id>";
                 xmlvariable += "<uom_val_desc>"+uom_desc_val + "</uom_val_desc>";
                 xmlvariable += "</datatwo>";
                 xmlvariable += "<dataone>";
                 xmlvariable +="<Beneficiary_Type_id>"+ben_desc_id+"</Beneficiary_Type_id>";
                 xmlvariable += "<Beneficiary_Type_desc>" + ben_desc_val + "</Beneficiary_Type_desc>";
                 xmlvariable += "</dataone>";
                 xmlvariable += "<activestatus>"+status+ "</activestatus>";
                 xmlvariable += "<flag>success</flag>";
             }
             catch (Exception e)
             {
            	 System.out.println(e);
                   xmlvariable += "<flag>failure</flag>";
             }
                   xmlvariable += "</response>";
        }
        else if (command_var.equalsIgnoreCase("delete"))
        {
               xmlvariable = "<response>";
               xmlvariable += "<command>delete</command>";
               try
               {
                        tariff_Id=Integer.parseInt(request.getParameter("tariff_Id"));
                        ps =connection.prepareStatement("delete from PMS_DCB_MST_TARIFF where TARIFF_ID=?");
                        ps.setInt(1, tariff_Id);
                        ps.executeUpdate();
                        xmlvariable += "<tariff_Id>" + tariff_Id + "</tariff_Id>";
                        xmlvariable += "<tariff_Rate>" + tariff_Rate + "</tariff_Rate>";
                        xmlvariable += "<tariff_Rate>" + tariff_Rate + "</tariff_Rate>";
                        xmlvariable += "<Tariff_wef>" + Tariff_wef + "</Tariff_wef>";
               //         xmlvariable += "<Uom>" + Uom + "</Uom>";
                        xmlvariable += "<Beneficiary_Type>" + Beneficiary_Type + "</Beneficiary_Type>";
                        xmlvariable += "<flag>success</flag>";
                }
                catch (Exception e)
                {
                        xmlvariable += "<flag>failure</flag>";
                }
                    xmlvariable += "</response>";
        }
        else if (command_var.equalsIgnoreCase("comboload"))
        {
            xmlvariable = "<response>";
            xmlvariable += "<command>comboload</command>";
            try
            {
                ps = connection.prepareStatement("select CHARGE_TYPE_ID,CHARGE_TYPE_DESC from PMS_DCB_MST_CHARGES_TYPE");
                res = ps.executeQuery();
                while(res.next())
                {
                    xmlvariable += "<CHARGE_TYPE_ID>" + res.getInt("CHARGE_TYPE_ID") + "</CHARGE_TYPE_ID>";
                    xmlvariable += "<CHARGE_TYPE_DESC_TABLE>" + res.getString("CHARGE_TYPE_DESC") + "</CHARGE_TYPE_DESC_TABLE>";
                    xmlvariable += "<flag>success</flag>";
                }
            }
            catch (Exception e)
            {
                xmlvariable += "<flag>failure</flag>";
            }
            xmlvariable += "</response>";
        }
        else if (command_var.equalsIgnoreCase("loadbeneficiary"))
        {
            xmlvariable = "<response>";
            xmlvariable += "<command>loadbeneficiary</command>";
            try
            {
            	ps = connection.prepareStatement("select BEN_TYPE_ID,BEN_TYPE_SDESC,BEN_TYPE_DESC,UPDATED_BY_USER_ID,UPDATED_DATE from PMS_DCB_BEN_TYPE\n" +
                "where BEN_TYPE_CATEGORY='L'ORDER BY BEN_TYPE_ID");
                res = ps.executeQuery();
                while(res.next())
                {
                    xmlvariable += "<BEN_TYPE_ID>" + res.getInt("BEN_TYPE_ID") + "</BEN_TYPE_ID>";
                    xmlvariable += "<BENEFICIARY_TYPE_DESC>" + res.getString("BEN_TYPE_DESC") + "</BENEFICIARY_TYPE_DESC>";
                    xmlvariable += "<flag>success</flag>";
                }
            }
            catch (Exception e)
            {
                xmlvariable += "<flag>failure</flag>";
            }
            xmlvariable += "</response>";
        }
//        else if (command_var.equalsIgnoreCase("loadUOM"))
//        {
//            xmlvariable = "<response>";
//            xmlvariable += "<command>loadUOM</command>";
//            try
//            {
//                ps = connection.prepareStatement("select UOM_SNO,UOM_DESC from PMS_DCB_MST_UOM ORDER BY UOM_SNO");
//                res = ps.executeQuery();
//                while(res.next())
//                {
//                    xmlvariable += "<UOM_SNO>" + res.getInt("UOM_SNO") + "</UOM_SNO>";
//                    xmlvariable += "<UOM_DESC>" + res.getString("UOM_DESC") + "</UOM_DESC>";
//                    xmlvariable += "<flag>success</flag>";
//                }
//            }
//            catch (Exception e)
//            {
//                xmlvariable += "<flag>failure</flag>";
//            }
//            xmlvariable += "</response>";
//        }
        else if (command_var.equalsIgnoreCase("bentypecheck"))
        {
            xmlvariable = "<response>";
            xmlvariable += "<command>bentypecheck</command>";
            try
            {
                ps = connection.prepareStatement("select count(*) as countbentype from  PMS_DCB_MST_TARIFF where BENEFICIARY_TYPE=? and ACTIVE_STATUS='A'");
                ps.setInt(1,Beneficiary_Type);
                res = ps.executeQuery();

                if(res.next())
                {
                    countbentype=res.getInt("countbentype");
                }
                if(countbentype>0)
                {
                    xmlvariable += "<countbentype>1</countbentype>";
                    xmlvariable += "<flag>success</flag>";
                }
                else
                {
                    xmlvariable += "<countbentype>0</countbentype>";
                    xmlvariable += "<flag>success</flag>";
                }
            }
            catch (Exception e)
            {
                xmlvariable += "<flag>failure</flag>";
            }
            xmlvariable += "</response>";
        }
        else if (command_var.equalsIgnoreCase("newrate"))
        {
        	String rate =obj.setValue("tariff_Rate", request);
        	String tariff_Wef_cell =obj.setValue("tariff_Wef_cell", request);

        	String offid =obj.setValue("offid", request);
        	String Beneficiary_Type1=obj.setValue("Beneficiary_Type", request);
        	
        	CallableStatement proc_stmt = null;
        	 xmlvariable = "<response>";      
        	try
        	{
        		 	System.out.println("offid ---> " + offid );
        		 	System.out.println("tariff_Wef_cell ---> " + tariff_Wef_cell );
        		 	System.out.println("rate ---> " + rate );
        		  	System.out.println("Beneficiary_Type1 ---> " + Beneficiary_Type1 );

        			proc_stmt = connection.prepareCall("call PMS_DCB_TARIFFRATE_CHANGE (?,?,?::numeric,?,?) ");
        			proc_stmt.setInt(1, Integer.parseInt(offid) );
        			proc_stmt.setInt(2, Integer.parseInt(Beneficiary_Type1));
        			proc_stmt.setFloat(3, Float.parseFloat(rate));
        			proc_stmt.setString(4, (tariff_Wef_cell));
        			proc_stmt.registerOutParameter(5,java.sql.Types.NUMERIC);
        			proc_stmt.setInt(5,0);
        			proc_stmt.execute();
        		 	System.out.println("success ---> " + proc_stmt.getBigDecimal(5).intValue() );

        			String totalrow = obj.isNull( String.valueOf(proc_stmt.getBigDecimal(5).intValue()), 1);
        			xmlvariable += "<totalrow>"+totalrow+"</totalrow>";
        	}catch (Exception e) {
        		  System.out.println(e);
        		  xmlvariable += "<flag>failure</flag>";
        		  xmlvariable += "<totalrow>0</totalrow>";  
			}
        	
        	  xmlvariable += "</response>";
        }else if (command_var.equalsIgnoreCase("typeshow")) 
        {
        	
        // NEw for hoganical Division 
        //	System.out.println("inside function ");
        	
        	String type =obj.setValue("type", request);   
        //	System.out.println("type is--> "+type);
        	if(type.equals("yes"))
        	{
        	//	System.out.println("inside yes ");
            	 xmlvariable = "<response>";        	   
            	 String offid =obj.setValue("dv", request);   
          //  	 System.out.println("value offid is--> "+offid);
            	 String qry="select distinct(b.ben_type_desc),b.ben_type_id,c.TARIFF_RATE from (SELECT beneficiary_type_id,BENEFICIARY_SNO FROM pms_dcb_mst_beneficiary  WHERE beneficiary_type_id <=6 and office_id =?  AND status = 'L' )a join  (select  ben_type_desc,ben_type_id from pms_dcb_ben_type)b  on b.ben_type_id=a.beneficiary_type_id  join  (select distinct(TARIFF_RATE),BENEFICIARY_SNO from PMS_DCB_TARIFF_SLAB where ACTIVE_STATUS='A' and office_id =?  )c  on c.BENEFICIARY_SNO=a.BENEFICIARY_SNO ";
            	 int row=0;   
            	    try {  
    						ps = connection.prepareStatement(qry);
    						ps.setString(1,offid);
    						ps.setString(2,offid);
    						res =ps.executeQuery();
    						while(res.next())
    						{      		
    							xmlvariable += "<EXISTINGRATE>"+obj.isNull(res.getString("TARIFF_RATE"),1) + "</EXISTINGRATE>";
    							xmlvariable += "<ben_type_desc>"+obj.isNull(res.getString("ben_type_desc"),1) + "</ben_type_desc>";
    							xmlvariable += "<ben_type_id>"+obj.isNull(res.getString("ben_type_id"),1)+ "</ben_type_id>";
    					//		xmlvariable += "<TARIFF_RATE>"+obj.isNull(res.getString("TARIFF_RATE"),1)+"</TARIFF_RATE>";    														  
    							row++;
    						//	System.out.println("EXISTINGRATE is  "+obj.isNull(res.getString("TARIFF_RATE"),1));
    						}
    						  xmlvariable+= "<row>"+row+"</row>";
    					//	  System.out.println("EXISTINGRATE is  "+obj.isNull(res.getString("TARIFF_RATE"),1));
            	    	} catch (SQLException e) 
            	    	{
            	    		e.printStackTrace();
            	    	}
            	//    System.out.println("last line came");
            	 xmlvariable += "</response>";
        	}else
        	{
        	 xmlvariable = "<response>";        	   
        	 String offid =obj.setValue("dv", request);   
        	 String qry=" SELECT a.ben_type_desc,a.ben_type_id,to_char(TARIFF_WEF, 'dd/mm/yyyy') AS TARIFF_WEF	,b.TARIFF_RATE ,b.PRV_RATE FROM pms_dcb_ben_type a,PMS_DCB_MST_TARIFF b "+
        	 		    " WHERE b.ACTIVE_STATUS='A' and a.ben_type_id IN (SELECT beneficiary_type_id FROM pms_dcb_mst_beneficiary "+
        	 		   " WHERE beneficiary_type_id <=6  AND status = 'L')  and a.ben_type_id=b.BENEFICIARY_TYPE order by a.ben_type_desc";
        	 		    //" WHERE beneficiary_type_id <=6 and office_id = "+offid+"  AND status = 'L')  and a.ben_type_id=b.BENEFICIARY_TYPE order by a.ben_type_desc";
        	 int row=0;   
        	    try {  
						ps = connection.prepareStatement(qry);
						res =ps.executeQuery();
						while(res.next())
						{  
							xmlvariable += "<EXISTINGRATE>"+obj.isNull(res.getString("PRV_RATE"),1) + "</EXISTINGRATE>";
							xmlvariable += "<ben_type_desc>"+obj.isNull(res.getString("ben_type_desc"),1) + "</ben_type_desc>";
							xmlvariable += "<ben_type_id>"+obj.isNull(res.getString("ben_type_id"),1)+ "</ben_type_id>";
							xmlvariable += "<TARIFF_RATE>"+obj.isNull(res.getString("TARIFF_RATE"),1)+"</TARIFF_RATE>";
							xmlvariable += "<TARIFF_WEF>"+obj.isNull(res.getString("TARIFF_WEF"),1)+"</TARIFF_WEF>";
							  
							row++;
						}
						  xmlvariable+= "<row>"+row+"</row>";
        	    	} catch (SQLException e) 
        	    	{
        	    		e.printStackTrace();
        	    	}
        	 xmlvariable += "</response>";
        }
        	}
                out.println(xmlvariable);
    }
}

