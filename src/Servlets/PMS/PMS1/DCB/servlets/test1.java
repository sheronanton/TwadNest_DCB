package Servlets.PMS.PMS1.DCB.servlets;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 
		    Calendar cal = Calendar.getInstance();
		    cal.set(Calendar.YEAR, 2012);
		    cal.set(Calendar.MONTH, 12);
		    cal.set(Calendar.DAY_OF_MONTH, 1);
 

		    int ndays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		    System.out.println(ndays);
		    int weeks[] = new int[ndays];
		    
		    for (int i = 0; i < ndays; i=i+7)
		    {
		       
		         
		       
		    }
		   
		 
		 
	}

}
