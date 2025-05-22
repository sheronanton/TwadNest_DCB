package Servlets.AME.AME1.AMEM.servlets;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.print.DocFlavor.STRING;

public class DataUploadAmeCollection {
	 
	public void DataUploadAmeCollection()
	{
		System.out.println("Object");
		  
	}
	public void data_upload(int year,int month,int office_id)
	{
		try
		{
		Ame_Data_Upload s=new Ame_Data_Upload();
		s.dataUpload(office_id, month,year);
		}catch(Exception e) {
			
			System.out.println(e);
		} 
	}
}
