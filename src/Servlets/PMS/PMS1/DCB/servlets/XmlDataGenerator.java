package Servlets.PMS.PMS1.DCB.servlets;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class XmlDataGenerator 
{ 
		private Connection con;
		private String report_query;
		private static Controller obj_new;
		private String res_xml;
		public   String fpath;
		public String title="";
		
		public XmlDataGenerator(Connection con,Controller obj_new)
		{
			this.con=con;
			this.obj_new=obj_new;
		}
		public void setReport_query(String report_query) {
			this.report_query = report_query;
		}
		public static String child_head(String qry,String column)
		{
			String xml_child="";
			try 
			{
				ResultSet rs_report=obj_new.getRS(qry);
				while(rs_report.next())
				{
						String SCH_TYPE_SUB_DESC=rs_report.getString(column);
						xml_child+="<category name='"+SCH_TYPE_SUB_DESC+"' />";						
				}
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			return xml_child;
		}
		public static String child_tag(String qry,String cmd_name,String color)
		{
			String xml_child="";
			try 
			{  
				String xml_child1="<dataset seriesName='Opening Balance' color='334344' >";
				String xml_child2="<dataset seriesName='Demand Current Month' color='#339966'>";
				String xml_child3="<dataset seriesName='Collection Current Month' color='#FF6666' width='1'>";
				String xml_child4="<dataset seriesName='Balacne' color='457344'>";  
				int r=0;
				ResultSet rs_report=obj_new.getRS(qry);    
				while(rs_report.next())  
				{  
					              
						String ob=rs_report.getString("ob");
						xml_child1+="<set value='"+ob+"'  />";	
						  
						
						String dmd=rs_report.getString("dmd");  
						xml_child2+="<set value='"+dmd+"' />";
						
						String collection=rs_report.getString("collection");
						xml_child3+="<set value='"+collection+"' />";
						
						String balance=rs_report.getString("balance");												
						xml_child4+="<set value='"+balance+"' />";
				}
				
				xml_child1+="</dataset>";
				xml_child2+="</dataset>"; 
				xml_child3+="</dataset>";
				xml_child4+="</dataset>";
				xml_child=xml_child2+xml_child3;  
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			return xml_child;
		} 
		public static String child_tag(String qry,String cmd_name,String color,String head1,String head2) 
		{
			String xml_child="";  
			try 
			{  
				String xml_child1="<dataset seriesName='Opening Balance' color='334344' >";
				String xml_child2="<dataset seriesName='"+head1+"' color='#339966'>";
				String xml_child3="<dataset seriesName='"+head2+"' color='#FF6666' width='1'>";
				String xml_child4="<dataset seriesName='Balacne' color='457344'>";  
				int r=0;
				ResultSet rs_report=obj_new.getRS(qry);    
				while(rs_report.next())  
				{     
					              
						String ob=rs_report.getString("ob");
						xml_child1+="<set value='"+ob+"'  />";	
						  
						
						String dmd=rs_report.getString("dmd");  
						xml_child2+="<set value='"+dmd+"' />";
						
						String collection=rs_report.getString("collection");
						xml_child3+="<set value='"+collection+"' />";
						
						String balance=rs_report.getString("balance");												
						xml_child4+="<set value='"+balance+"' />";
				}
				
				xml_child1+="</dataset>";
				xml_child2+="</dataset>"; 
				xml_child3+="</dataset>";
				xml_child4+="</dataset>";
				xml_child=xml_child2+xml_child3;  
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			return xml_child;
		}
		public static String child_tag(String qry,String cmd_name,String color,String []heading)
		{
			String xml_child="";
			try 
			{  
				String xml_child1="<dataset seriesName='Opening Balance' color='334344' >";
				String xml_child2="<dataset seriesName='Demand Current Month' color='#339966'>";
				String xml_child3="<dataset seriesName='Collection Current Month' color='#FF6666' width='1'>";
				String xml_child4="<dataset seriesName='Balacne' color='457344'>";  
				int r=0;
				ResultSet rs_report=obj_new.getRS(qry);    
				while(rs_report.next())  
				{  
					              
						String ob=rs_report.getString("ob");
						xml_child1+="<set value='"+ob+"'  />";	
						  
						
						String dmd=rs_report.getString("dmd");  
						xml_child2+="<set value='"+dmd+"' />";
						
						String collection=rs_report.getString("collection");
						xml_child3+="<set value='"+collection+"' />";
						
						String balance=rs_report.getString("balance");												
						xml_child4+="<set value='"+balance+"' />";
				}
				
				xml_child1+="</dataset>";
				xml_child2+="</dataset>"; 
				xml_child3+="</dataset>";
				xml_child4+="</dataset>";
				xml_child=xml_child2+xml_child3;  
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			return xml_child;
		}
		public static String child_tag2(String qry,String cmd_name,String color)
		{
			String xml_child="";  
			try    
			{
				xml_child+="<dataset seriesName='"+cmd_name+"' color='"+color+"' showValues='0'>";
				ResultSet rs_report=obj_new.getRS(qry);
				while(rs_report.next())
				{
						String ob=rs_report.getString("ob");
						String dmd=rs_report.getString("dmd"); 
						String collection=rs_report.getString("collection");
						String balance=rs_report.getString("balance");
						 
						xml_child+="<set value='"+collection+"' />";
						xml_child+="<set value='"+balance+"' />";
				}
				xml_child+="</dataset>";
			} catch (Exception e) 
			{
				e.printStackTrace();
			}  
			
			return xml_child;    
		}
		public void  district_char(String user_query,String []head_columns, String []aHead,String []colorcode,String []query_column)  
		{     
		 
			String xml="";
			try
			{
	/*	String qry=" SELECT DISTRICT_CODE,BALANCE_18,NET_DUE_19 FROM (SELECT  DISTRICT_CODE , BALANCE_18, CASE  WHEN NET_DUE_19 < 0  THEN 0  ELSE NET_DUE_19   END AS NET_DUE_19 "
			  +" FROM  (SELECT "    
			  +" DECODE(MONTH,1,'JANUARY', 2,'February', 3,'March', 4,'April', 5,'May', 6,'June', 7,'July', 8,'August', 9,'September', 10,'October', 11,'November', 12,'December', 'N/A')MONTH, "
			  +"  YEAR,DECODE(SUM(NET_DUE),NULL,0.0,SUM(NET_DUE)) / 1  AS NET_DUE_19,sum(BALANCE_18) as BALANCE_18 ,DISTRICT_NAME, DISTRICT_CODE "
			  +"   FROM "
			  +"  (SELECT    MONTH,BALANCE_18,YEAR,NET_DUE+decode(CB_INT_AMT_WC,null,0,CB_INT_AMT_WC) as NET_DUE ,DISTRICT_CODE,DISTRICT_NAME "
			  +"  FROM PMS_DCB_LEDGER_ACTUAL "
			  +"  WHERE MONTH=8 "
			  +"  AND YEAR=2013 "
			  +"  AND BEN_TYPE_ID_SUB IN (1) "  
			  +"  AND net_due         >=0 "  
			  +"  ) "
			  +" GROUP BY YEAR,MONTH,DISTRICT_CODE,DISTRICT_NAME  ORDER BY DISTRICT_NAME "
			  +" ) "
			  +" )rpt ORDER BY  rpt.DISTRICT_CODE ";*/
		 
		String CENSUS_DIST="";     
		  xml="<metadata><column code='layername' value='sample1:sp_tn_pnt'/>"; 
			   xml+="<column code='sp_field_link' value='cen_dcode'/>";
			   xml+="<column code='sp_label_column' value='district'/>";
			   xml+="<column code='lyr_id' value='chart'/>";
			   xml+="<column code='symbology_type' value='pie chart'/>";
			   xml+="<column code='range' value='5'/>";
			   xml+="<column code='filter_column' value='Details'/>";
			   xml+="<column code='filter_value' value='Rupees in Lakhs'/>";
			   xml+="<column code='title' value='"+title+"'/>";		  	
			   xml+="<column code='chartlyr_id' value='tn_dist'/>";
			   xml+="<column code='chart_type' value='pie'/>";  
			   xml+="<column code='noofchartvalues' value='4'/>"; 
			   xml+="<column code='themetype' value='custom' />";
			   xml+="<column code='themerange' value='5' />";  
			   xml+="<column code='themecolor' value='FF0000'/>";
			   xml+="<column code='themetext' value='Top 5 Balance Outstanding' />";
			   xml+="<column_head code='t_header'";  
			   for(int i=0;i<head_columns.length;i++)    
				 {
				   xml+=" header"+(i+1)+"='"+head_columns[i]+"'";  
				 }
			   xml+="/><column_attribute code='aname' tvalue='area' ";
			   for(int i=0;i<aHead.length;i++)
				 {
				   xml+=" avalue"+(i+1)+"='"+aHead[i]+"'";    
				 }			 
			   xml+="/>";//// avalue2='DMD' avalue3='Collection' avalue4='Balance'/>";
			   xml+="<column_color code='colors'";
			   for(int i=0;i<aHead.length;i++)
				 {
				   xml+=" color"+(i+1)+"='"+colorcode[i]+"'";  
				 }			 
			   xml+="/>"; 
			  // xml+="<column_color code='colors' color1='339966' color2='FF6666'/>";// color2='63FF69' color3='6C4FFF' color4='F7FF8A' color5='30EAFF'/>";
			   ResultSet rs_report = null;    
			try {      
				System.out.println(user_query);  
				rs_report = obj_new.getRS(user_query);
			} catch (Exception e) {  
				// TODO Auto-generated catch block
				e.printStackTrace();  
			}     
				try {
					 
					String last_value="";
					while(rs_report.next())  
					{     
							String DISTRICT_CODE=rs_report.getString("DISTRICT_CODE");
							CENSUS_DIST = obj_new.getValue("PMS_DCB_MST_CENSUS_DIST", "CENSUS_DIST","where DISTRICT_CODE=" + DISTRICT_CODE);
							String value="";  
						//	String collection=rs_report.getString("collection");
							 xml+="<attribute code='"+CENSUS_DIST+"'  ";
							 for(int i=0;i<query_column.length;i++)
							 {
								value=rs_report.getString(query_column[i]);//"dmd");
								last_value=value;
							    xml+=" chartvalue"+(i+1)+"='"+value+"' ";    
							 }		
							 xml+=" themevalue='"+last_value+"' />";//xml+=" code='"+CENSUS_DIST+"' themevalue='55' chartvalue1='"+dmd+"' chartvalue2='"+collection+"'/>";
							 last_value="";  
					}    
				} catch (SQLException e) {     
					// TODO Auto-generated catch block
					e.printStackTrace();  
				}
				 xml+="</metadata>";
			}catch(Exception e) {}
			
				System.out.println(xml);
				  res_xml=xml;    
				 
		}
		
	// Testing only
		
		public void  district_char_test(String user_query,String []head_columns, String []aHead,String []colorcode,String []query_column)  
		{     
		 
			String xml="";
			try
			{
			 
		//String CENSUS_DIST="";     
		  xml="<Person>  <first_name>Jonathan</first_name>"; 
			   xml+="<last_name>Freeman</last_name>";
			   xml+="<Marks>400</Marks>";
			   xml+="<Total>500</Marks>";
			   xml+="<pets>";
			   xml+="<pet>";
			   xml+="<name>Lilly</name>";
			   xml+="<type>Raccoon</type>";
			   xml+="</pet>";		  	
			   xml+="</pets>";
		       xml+="</person>";
			 
			
				} catch (Exception e) {     
					// TODO Auto-generated catch block
					e.printStackTrace();  
				}
	//			 xml+="</metadata>";
						
				System.out.println(xml);
				  res_xml=xml;    
				 
		}
		
		
		
		
		public String bar_chart2(String column)  
		{ 
			String xml_data="";     
			try {  
				xml_data="<graph caption='Monthly Unit Sales' xAxisName='Month' yAxisName='Units' decimalPrecision='0' ";		 
				xml_data+="formatNumberScale='0' chartRightMargin='30'>";
				xml_data+="<set name='Jan' value='462' color='AFD8F8' />";
				xml_data="<set name='Feb' value='857' color='F6BD0F' />";
						xml_data="<set name='Mar' value='671' color='8BBA00' />";
							xml_data="<set name='Apr' value='494' color='FF8E46'/>";
							xml_data="<set name='May' value='761' color='008E8E'/>";
							xml_data="<set name='Jun' value='960' color='D64646'/>";
							xml_data="<set name='Jul' value='629' color='8E468E'/>";
							xml_data="<set name='Aug' value='622' color='588526'/>";
							xml_data="<set name='Sep' value='376' color='B3AA00'/>";
							xml_data="<set name='Oct' value='494' color='008ED6'/>";
							xml_data="<set name='Nov' value='761' color='9D080D'/>";
							xml_data="<set name='Dec' value='960' color='A186BE'/></graph>";
			} catch (Exception e) 
			{
				e.printStackTrace(); 
			}
		return xml_data;  
		}
		
		
		
		public String bar_chart(String column,String head1,String head2)
		{    
			String xml_data="";             
			try { 
				// xml_data="<graph xaxisname='' yaxisname='Rupees in Lakhs' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='1' yAxisMaxValue='100' numdivlines='12' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='2' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='Demand,Collection and Balance Statement' subcaption='' >";
				// xml_data="<graph xaxisname='Scheme Type' yaxisname='Rupees in Lakhs' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='180' yAxisMaxValue='100' numdivlines='10' divLineColor='CCCCCC' divLineAlpha='180'  decimalPrecision='2' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='Demand,Collection and Balance Statement ' subcaption='' > ";
				//xml_data=" <graph caption='Sales' PYAxisName='Revenue' SYAxisName='Quantity' numberPrefix='' showvalues='10'  numDivLines='4' formatNumberScale='0' decimalPrecision='0'  anchorSides='10' anchorRadius='13' anchorBorderColor='009900'>";
   				   xml_data="<graph rotateNames='1' canvasBgColor='e4b84b' canvasBgAlpha='20' canvasBorderColor='7B3F00' canvasBorderThickness='0' divLineColor='a82925'  slantLabels='1' yAxisName='Rupees in Lakhs'  caption='Demand And Collection' PYAxisName='Revenue' SYAxisName='Quantity' numberPrefix='' showvalues='50'  numDivLines='14' formatNumberScale='0' decimalPrecision='2' anchorSides='10' anchorRadius='10' anchorBorderColor='129900'  hovercapborder='889E6D' >";
					int name_count=0;		   			
					xml_data+="<categories>";  	           
					xml_data+=XmlDataGenerator.child_head(report_query,column); 
					xml_data+="</categories>";		  
					ResultSet rs_report=obj_new.getRS(report_query);
					while(rs_report.next()) 
					{  
						String column_value=rs_report.getString(column);
						xml_data+=XmlDataGenerator.child_tag(report_query,column_value,"FDC12E",head1,head2);
					}
					   
					xml_data+="</graph>";					
					 
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			return xml_data;  
		}public String bar_chart(String column)
		{      
			String xml_data="";             
			try { 
				// xml_data="<graph xaxisname='' yaxisname='Rupees in Lakhs' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='1' yAxisMaxValue='100' numdivlines='12' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='2' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='Demand,Collection and Balance Statement' subcaption='' >";
				// xml_data="<graph xaxisname='Scheme Type' yaxisname='Rupees in Lakhs' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='180' yAxisMaxValue='100' numdivlines='10' divLineColor='CCCCCC' divLineAlpha='180'  decimalPrecision='2' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='Demand,Collection and Balance Statement ' subcaption='' > ";
				//xml_data=" <graph caption='Sales' PYAxisName='Revenue' SYAxisName='Quantity' numberPrefix='' showvalues='10'  numDivLines='4' formatNumberScale='0' decimalPrecision='0'  anchorSides='10' anchorRadius='13' anchorBorderColor='009900'>";
   				    xml_data="<graph rotateNames='1' canvasBgColor='e4b84b' canvasBgAlpha='20' canvasBorderColor='7B3F00' canvasBorderThickness='0' divLineColor='a82925'  slantLabels='1' yAxisName='Rupees in Lakhs'  caption='Demand And Collection' PYAxisName='Revenue' SYAxisName='Quantity' numberPrefix='' showvalues='50'  numDivLines='14' formatNumberScale='0' decimalPrecision='2' anchorSides='10' anchorRadius='10' anchorBorderColor='129900'  hovercapborder='889E6D' >";
					int name_count=0;		   			
					xml_data+="<categories>";  	           
					xml_data+=XmlDataGenerator.child_head(report_query,column); 
					xml_data+="</categories>";		  
					ResultSet rs_report=obj_new.getRS(report_query);
					while(rs_report.next()) 
					{  
						String column_value=rs_report.getString(column);
						xml_data+=XmlDataGenerator.child_tag(report_query,column_value,"FDC12E");
					}
					   
					xml_data+="</graph>";					
					 
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			return xml_data;  
		}
		 public String bar_chart(String column,String []heading)
		{    
			String xml_data="";               
			try { 
				// xml_data="<graph xaxisname='' yaxisname='Rupees in Lakhs' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='1' yAxisMaxValue='100' numdivlines='12' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='2' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='Demand,Collection and Balance Statement' subcaption='' >";
				// xml_data="<graph xaxisname='Scheme Type' yaxisname='Rupees in Lakhs' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='180' yAxisMaxValue='100' numdivlines='10' divLineColor='CCCCCC' divLineAlpha='180'  decimalPrecision='2' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='Demand,Collection and Balance Statement ' subcaption='' > ";
				//xml_data=" <graph caption='Sales' PYAxisName='Revenue' SYAxisName='Quantity' numberPrefix='' showvalues='10'  numDivLines='4' formatNumberScale='0' decimalPrecision='0'  anchorSides='10' anchorRadius='13' anchorBorderColor='009900'>";
   				   xml_data="<graph rotateNames='1' canvasBgColor='e4b84b' canvasBgAlpha='20' canvasBorderColor='7B3F00' canvasBorderThickness='0' divLineColor='a82925'  slantLabels='1' yAxisName='Rupees in Lakhs'  caption='Demand And Collection' PYAxisName='Revenue' SYAxisName='Quantity' numberPrefix='' showvalues='50'  numDivLines='14' formatNumberScale='0' decimalPrecision='2' anchorSides='10' anchorRadius='10' anchorBorderColor='129900'  hovercapborder='889E6D' >";
					int name_count=0;		   			
					xml_data+="<categories>";  	           
					xml_data+=XmlDataGenerator.child_head(report_query,column); 
					xml_data+="</categories>";		  
					ResultSet rs_report=obj_new.getRS(report_query);
					while(rs_report.next()) 
					{  
						String column_value=rs_report.getString(column);
						xml_data+=XmlDataGenerator.child_tag(report_query,column_value,"FDC12E",heading);
					}
					   
					xml_data+="</graph>";					
					 
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			return xml_data;  
		}
		public   void setFpath(String fpath) {
			this.fpath = fpath;
		}
	 
		public String getRes_xml() {			
			return res_xml.toString();
		}
}
