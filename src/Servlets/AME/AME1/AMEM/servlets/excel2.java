package Servlets.AME.AME1.AMEM.servlets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class excel2 {   
  
	
	public static void main(String s[]) throws Exception
	{
		Controller obj=new Controller();
		Connection con=obj.con(); 
		obj.createStatement(con);
			File f2=new File("C:\\Users\\sathiya\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp2\\wtpwebapps\\twadonline\\excel\\excel_upload_finyear(2).xls");	
		String []col={"TWAD_SCH_SNO","DESIGN_QTY","PUMPING_QTY","MONTH","YEAR","Office_id","SCH_SNO"};
 		 excel2.read2(f2,con,obj,"6164","PMS_AME_TRN_SCH_DPQTY",col,8,  "2010");;  
	}
	  
	
	public static void assest_excel_read(File f,Connection con,Controller obj,String offid,String table,String []col,int coloumcount ) throws IOException
	{
		InputStream myxls = new FileInputStream(f);
		
		POIFSFileSystem fs = new POIFSFileSystem(myxls);
		
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		
		int nosheets = wb.getNumberOfSheets();
		
		System.out.println("The Number of Sheets in the Excel File = "+ nosheets);
		
		HSSFSheet sheet = wb.getSheetAt(0);
		
		Iterator rows = sheet.rowIterator();
		
		
		
		
	}
	
	
	public static void  read(File f,Connection con,Controller obj,String offid,String table,String []col,int coloumcount ) throws IOException
	{
		InputStream myxls = new FileInputStream(f);  
		POIFSFileSystem fs = new POIFSFileSystem(myxls);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		int nosheets = wb.getNumberOfSheets();
		System.out.println("The Number of Sheets in the Excel File = "+ nosheets);
		HSSFSheet sheet = wb.getSheetAt(0);
		Iterator rows = sheet.rowIterator();
		int i = 0;
		int c=0;
		while (rows.hasNext()) 
		{
			HSSFRow row = (HSSFRow) rows.next();
			i++;  
			c=0; 
		}    
		int count=0;
		 int j=0;      
		 String[][] values = new String[i][14];     
          i=0;
          
          rows = sheet.rowIterator(); 
          while( rows.hasNext() ) 
          {  
        	  	HSSFRow row = (HSSFRow) rows.next();
        	    Iterator cells = row.cellIterator();
        	    j=-1;
        	    while( cells.hasNext() ) 
        	    {
        	    	 j=j+1; 
        	    	count++;
        	    	  HSSFCell cell = (HSSFCell) cells.next();
        	    	try
        	    	{
        	    		values[i][j]=new String();
        	    	  
        	    	    
        	    	    	 switch (cell.getCellType())
        	    	    	 {
        	    	    	 	case 0:
        	    	    	 			double val =cell.getNumericCellValue();
        	    	    	 			values[i][j] = ""+val;
        	    	    	 			break;
        	    	    	 	case 1:
        	    	    	 			String sv = cell.getStringCellValue();
        	    	    	 			values[i][j] = "'"+sv+"'";
        	    	    	 			break; 
        	    	    	 	case 2:
        	    	    	 			val = cell.getNumericCellValue();
        	    	    	 			values[i][j] = ""+val;
        	    	    	 			break;   	  		
        	    	    	 }
        	    	    	  
        	    		}catch(Exception e) {}
			    }       
        	    
		 	      i++;
          }
          dis(values,i,con,obj,offid,table,col,coloumcount);
	}    
	public static void  read2(File f,Connection con,Controller obj,String offid,String table,String []col,int coloumcount,String year) throws IOException
	{
		 
		InputStream myxls = new FileInputStream(f);  
		POIFSFileSystem fs = new POIFSFileSystem(myxls);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		int nosheets = wb.getNumberOfSheets();
		System.out.println("The Number of Sheets in the Excel File = "+ nosheets);
		HSSFSheet sheet = wb.getSheetAt(0);
		Iterator rows = sheet.rowIterator();
		int i = 0;
		int c=0;    
		int ct=0;
		System.out.println("Cell count" + c);
		while (rows.hasNext()) 
		{
			HSSFRow row = (HSSFRow) rows.next();
			i++;
			c=0;   
			int c1=0;
		}     
		int count=0;  
		 int j=0;      
		 String[][] values = new String[i][14];  
          i=0;          
          rows = sheet.rowIterator();
          while( rows.hasNext() ) 
          {  
        	  	HSSFRow row = (HSSFRow) rows.next();
        	    Iterator cells = row.cellIterator();
                j=-1;
        	    while( cells.hasNext() ) 
        	    {
        	    	 j=j+1; 
        	    	count++;
        	    	  HSSFCell cell = (HSSFCell) cells.next();
        	    	        
        	    	try
        	    	{
        	    		values[i][j]=new String();
        	    	  
        	    	    	 switch (cell.getCellType())
        	    	    	 {
        	    	    	 	case 0:
        	    	    	 			double val =cell.getNumericCellValue();
        	    	    	 			values[i][j] = ""+val;
        	    	    	 			break;
        	    	    	 	case 1:
        	    	    	 			String sv = cell.getStringCellValue();
        	    	    	 			values[i][j] = "'"+sv+"'";
        	    	    	 			break; 
        	    	    	 	case 2:
        	    	    	 			val = cell.getNumericCellValue();
        	    	    	 			values[i][j] = ""+val;
        	    	    	 			break;   	  		
        	    	    	 }
        	    		}catch(Exception e) {}
        	    		
        	    		 
			    }                             
		 	      i++;    
          }
             
          dis2(values,i,con,obj,offid,table,col,coloumcount,year);

	}      
	public static void excel_write(File f,String fname,Connection con,String qry) throws IOException, SQLException
	{
		
		String outputFile = "C:/Work/JPOI/excel_in_java.xls";
		
		HSSFWorkbook wb = new HSSFWorkbook();  
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row = sheet.createRow((short) 0);
		  
		
		Servlets.PMS.PMS1.DCB.servlets.Controller obj1=new Servlets.PMS.PMS1.DCB.servlets.Controller();
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(qry);
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int numColumns = rsmd.getColumnCount(); 
	    String outp=" ";
	    outp="<tr><td><font size=2 color='black'>Sl.No</font></td>"; 
		File  wrfile= new File(f,fname);    
	
	   
	    for (int i=1; i<numColumns+1; i++) 
	    {
	        String columnName = rsmd.getColumnName(i);
	        HSSFCell cell = row.createCell((short) (i-1));
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(columnName);
			FileOutputStream fOut = new FileOutputStream(wrfile);
			  wb.write(fOut);
			  fOut.flush(); 
			  fOut.close(); 
	    }  
	    int i=1;
	    
	    	while (rs.next())
	    	{
	    		HSSFRow row1 = sheet.createRow((short) i);
	    		for (int j=1; j<numColumns+1; j++)
	    		{
	    			HSSFCell cell = row1.createCell((short) (j-1));
	    			String columnName =obj1.isNull(rs.getString(j),1);     
	    			 String ct=rsmd.getColumnTypeName(j);  
	    			if (ct.equalsIgnoreCase("VARCHAR2"))
	    			{
	    				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	    				cell.setCellValue(columnName);
	    			}
	    			else if (ct.equalsIgnoreCase("NUMBER"))
	    			{
	    				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	    				cell.setCellValue(Double.parseDouble(columnName));
	    			}
	    			else if (ct.equalsIgnoreCase("CHAR"))
	    			{
	    				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	    				cell.setCellValue(columnName);
	    			}
	    			else 
	    			{
	    			 cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	    			 cell.setCellValue(columnName);
	    			}
	    			  
	    			
	    			
					FileOutputStream fOut = new FileOutputStream(wrfile);
					wb.write(fOut);
					fOut.flush(); 
					fOut.close();
	    		}  
			  i++;  
	    	}
	    	   
		
	}
	public static void excel_write2(File f,String fname,Connection con,String qry) throws IOException, SQLException
	{
		
		String outputFile = "C:/Work/JPOI/excel_in_java.xls";
		
		HSSFWorkbook wb = new HSSFWorkbook();  
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row = sheet.createRow((short) 0);
		  
		
		Servlets.PMS.PMS1.DCB.servlets.Controller obj1=new Servlets.PMS.PMS1.DCB.servlets.Controller();
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(qry);
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int numColumns = rsmd.getColumnCount(); 
	    String outp=" ";
	    outp="<tr><td><font size=2 color='black'>Sl.No</font></td>"; 
		File  wrfile= new File(f,fname);    
	    for (int i=1; i<numColumns+1; i++) 
	    {
	        String columnName = rsmd.getColumnName(i);
	        HSSFCell cell = row.createCell((short) (i-1));
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(columnName);
			FileOutputStream fOut = new FileOutputStream(wrfile);
			  wb.write(fOut);
			  fOut.flush(); 
			  fOut.close(); 
	    }  
	    int i=1;
	    
	    	while (rs.next())
	    	{
	    		HSSFRow row1 = sheet.createRow((short) i);
	    		  
	    		for (int j=1; j<numColumns+1; j++)
	    		{
	    			 
	    			HSSFCell cell = row1.createCell((short) (j-1));
	    			String columnName =obj1.isNull(rs.getString(j),1);     
	    			 String ct=rsmd.getColumnTypeName(j);  
	    			if (ct.equalsIgnoreCase("VARCHAR2"))
	    			{
	    				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	    				cell.setCellValue(columnName);
	    			}
	    			else if (ct.equalsIgnoreCase("NUMBER"))
	    			{
	    				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	    				cell.setCellValue(Double.parseDouble(columnName));
	    			}
	    			else if (ct.equalsIgnoreCase("CHAR"))
	    			{
	    				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	    				cell.setCellValue(columnName);
	    			}
	    			else 
	    			{
	    			 cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	    			 cell.setCellValue(columnName);
	    			}
	    			  
	    			
	    			
					FileOutputStream fOut = new FileOutputStream(wrfile);
					wb.write(fOut);
					fOut.flush(); 
					fOut.close();
	    		}  
			  i++;  
	    	}
	}
	public static void  dis(String [][]sr,int row,Connection con,Controller obj,String offid,String table,String []col,int coloumcount)
	{
		int kk=0;
		int uprow=0;
		 Hashtable ht=new Hashtable();
		 for( int i = 1;i<row;i++)  
         {
			 			kk=-1;
						 for( int j = 0;j<coloumcount;j++)     
				         {      
							 ++kk;  
							 try
							 {
								 ht.put( col[kk],sr[i][j]);
							  }catch(Exception e) {}  
							 
				         }  
						 
						 
						 try {    
							 uprow = obj.recordSave(ht,table , con);
						 	  } catch (SQLException e)   
						 	  {
						 	      e.printStackTrace();
						 	  }
        }  
		    
	}    
	public static void  dis2(String [][]sr,int row,Connection con,Controller obj,String offid,String table,String []col,int coloumcount,String year)
	{
		int kk=0;
		int uprow=0;
		 Hashtable ht=new Hashtable();
		 double []index=new double[14];
		 int year1=Integer.parseInt(year);
		 int finmonth=4;
		 int c=0;
		
		try
		{
		 for( int r = 0;r<row;r++)     
         { 
			 for( int j = 0;j<14-1;j++)
			 {
				 c++;
			 }  
         }
		}catch(Exception e) {}        
		 for( int i = 0;i<row;i++)  
         {
			 			kk=-1;
			 	 
			 			int innercolumn=0;
			 			int c1=0;
			 			finmonth=4; 
						 for( int j = 0;j<14;j++)     
				         {      
							 ++kk;  
							 if (j >=2)	
							 	{  
								  try {  
									  	 if (finmonth < 4 ) 
									  	 {  
									  		 year=Integer.toString(year1+1);
									  	 }
									  	 else  
									  	 { 
									  		 year=Integer.toString(year1);
									  	 } 
									  
									  	 if (c1 < 1)  
									  	 {
										   String project_sno=sr[i][0];
										   String sch_sno="0",sch_off="0";
											try
											{
											//  sch_sno=obj.getValue("PMS_DCB_DIV_SCHEME_MAP", "SCH_SNO", " where PROJECT_SNO="+project_sno);
											  //sch_off=obj.getValue("PMS_DCB_DIV_SCHEME_MAP", "OFFICE_ID", " where PROJECT_SNO="+project_sno);
												  sch_sno=obj.getValue("PMS_AME_MST_SCH_MAPPING", "SCH_SNO", " where TWAD_PROJECT_ID="+project_sno);
												 sch_off=obj.getValue("PMS_AME_MST_SCH_MAPPING", "OFFICE_ID", " where TWAD_PROJECT_ID="+project_sno);
												
											}catch(Exception e) 
											{
												System.out.println(e);
											}
										    ht.put( col[innercolumn], obj.isNull(sr[i][j],1));
											innercolumn++;
										  	int m=0;  
										  	if (finmonth>12)
										  	{
										  		m=finmonth-12;
										  	}  else
										  	{
										  		m=finmonth;
										  	}
										  	ht.put(col[innercolumn],m);
										  	innercolumn++;
										  	ht.put(col[innercolumn],year);
										  	innercolumn++;
										  	ht.put(col[innercolumn],sch_off);
										  	innercolumn++;
										  	ht.put(col[innercolumn],sch_sno);
										  	innercolumn++;
										  	uprow = obj.recordSave(ht,table , con);
										  	innercolumn=0;
										  	c1++;			
										  	finmonth++;
									   }else
									   { 
										   ht.put( col[innercolumn], obj.isNull(sr[i][0],1));
										   innercolumn++;
										   String project_sno=sr[i][0];
										   String sch_sno="0",sch_off="0";
										   try
										   	{
											   //sch_sno=obj.getValue("PMS_DCB_DIV_SCHEME_MAP", "SCH_SNO", " where PROJECT_SNO="+project_sno);
											   //sch_off=obj.getValue("PMS_DCB_DIV_SCHEME_MAP", "OFFICE_ID", " where PROJECT_SNO="+project_sno);
											   sch_sno=obj.getValue("PMS_AME_MST_SCH_MAPPING", "SCH_SNO", " where TWAD_PROJECT_ID="+project_sno);
												 sch_off=obj.getValue("PMS_AME_MST_SCH_MAPPING", "OFFICE_ID", " where TWAD_PROJECT_ID="+project_sno);
										   	}catch(Exception e) {}
										   	ht.put( col[innercolumn], obj.isNull(sr[i][1],1));
										   	
										  innercolumn++;
										  ht.put( col[innercolumn], obj.isNull(sr[i][j],1));
										  innercolumn++;
										  int m=0;
									  		if (finmonth>12)
									  			{
									  				m=finmonth-12;
									  				year=Integer.toString(year1+1);
									  			}  else
									  			{ 
									  				m=finmonth;  
									  			}
									  		ht.put(col[innercolumn],m);
									  		innercolumn++;   
									  		ht.put(col[innercolumn],year);
									  		innercolumn++;
									  		year=Integer.toString(year1-1);
									  		ht.put(col[innercolumn],sch_off);
									  		innercolumn++;
									  		ht.put(col[innercolumn],sch_sno);
									  		uprow = obj.recordSave(ht,table , con);
									  		innercolumn=0;
									  		c1++;
									  		finmonth++;
									   }
							 	  	} catch (SQLException e)   
							 	  	{
							 	      e.printStackTrace();
							 	  	}
							 	}else  
							 	{  
							 		try
									 {
										  ht.put( col[innercolumn], obj.isNull(sr[i][j],1));
										 
										 innercolumn++;
										 
									  }catch(Exception e) {} 		
							 	}
				         }  
						 
						  
        }  
		    
	}    
	public static void batchupdate(int flag,String Office_id,String month,String year,Controller obj,Connection con) throws Exception
	{
		 Hashtable ht=new Hashtable();
		 Hashtable ht1=new Hashtable();
		 obj.createStatement(con);
		if (flag==1)
		{  
			    
			    
			ResultSet rs=obj.getRS("select * from PMS_AME_TRN_SCH_DPQTY where OFFICE_ID is null ");
			while (rs.next())  
			{
					String TWAD_SCH_SNO=rs.getString("TWAD_SCH_SNO");
				//	String sch_sno=obj.getValue("PMS_DCB_DIV_SCHEME_MAP", "SCH_SNO", "where PROJECT_SNO="+TWAD_SCH_SNO);
					String sch_sno=obj.getValue("PMS_AME_MST_SCH_MAPPING", "SCH_SNO", " where TWAD_PROJECT_ID="+TWAD_SCH_SNO);

					ht.put("OFFICE_ID",Office_id);
					ht.put("SCH_SNO",sch_sno);
					ht1.put("TWAD_SCH_SNO",TWAD_SCH_SNO);
					int row=obj.recordSave(ht,ht1,"PMS_AME_TRN_SCH_DPQTY",con);   
			}
			
		}
	}
}
