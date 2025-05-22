package dcb.reports;
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

import Servlets.PMS.PMS1.DCB.servlets.Controller;

public class excel2 {

	  static int column=0;
	  
	  
	  
	public static void  read(File f,Connection con,Controller obj,String offid) throws IOException
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
		String[][] values = new String[i][7];  
		 
          i=0;
          
          rows = sheet.rowIterator(); 
          
          	     
          while( rows.hasNext() ) 
          {  
        	  	HSSFRow row = (HSSFRow) rows.next();
        	    Iterator cells = row.cellIterator();
        	    j=-1;
        	    
        	    
        	    while( cells.hasNext() ) 
        	    {
        	    	++j;
        	    	count++;
        	    	 values[i][j]=new String();  
        	    	 HSSFCell cell = (HSSFCell) cells.next();
        	    	 switch (cell.getCellType())
        	    	 {
        	    	 case 0:
        	    		 	double val =cell.getNumericCellValue();
        	    		 	values[i][j] = ""+val;
					        break;
					 case 1:
					       String sv = cell.getStringCellValue();
					       values[i][j] = sv;
					       break;
					 case 2:
	                       val = cell.getNumericCellValue();
	                       values[i][j] = ""+val;
	                       break;	  		
        	    	 }
        	         
			    }
			column=j;
		i++;    	   
          }
          dis(values,i,con,obj,offid);    

	}    
	public static void excel_write(File f,String fname,Connection con,String qry) throws IOException, SQLException
	{
		
		String outputFile = "C:/Work/JPOI/excel_in_java.xls";
		HSSFWorkbook wb = new HSSFWorkbook();  
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row = sheet.createRow((short) 0);
		
	 
		 Controller obj1=new  Controller();
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
	    	  
	    			if (ct.equalsIgnoreCase("VARCHAR2") || ct.equalsIgnoreCase("VARCHAR"))
	    			{
	    				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	    				cell.setCellValue(columnName); 
	    			}
	    			else if (ct.equalsIgnoreCase("NUMBER") || ct.equalsIgnoreCase("INTEGER"))
	    			{
	    				 cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	    				 
	    				try
	    				{
	    					    				cell.setCellValue(Integer.getInteger(columnName));
	    				}catch(Exception e)
	    				{
		    				 
		    				
		    					cell.setCellValue(Double.parseDouble(columnName));
		    				 
	    				}
	    				
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
	
	public static void  dis(String [][]sr,int row,Connection con,Controller obj,String offid)
	{
		
		String []col={"OFFICE_ID","TWAD_SCH_SNO","SCH_SNO","DESIGN_QTY","PUMPING_QTY","MONTH","YEAR"};
		int kk=0;
		int uprow=0;
		 Hashtable ht=new Hashtable();
		 for( int i = 1;i<row;i++)  
         {
			 			kk=0;
			 			 ht.put( col[kk],offid);
						 for( int j = 0;j<column+1;j++) 
				         {  
							 kk++;
							 ht.put( col[kk],sr[i][j]);
				         }
						  try {    
							    uprow = obj.recordSave(ht,"PMS_AME_TRN_SCH_DPQTY" , con);
						 	  } catch (SQLException e) 
						 	  {
						 	      e.printStackTrace();  
						 	  }
        }
		  
	}
}
