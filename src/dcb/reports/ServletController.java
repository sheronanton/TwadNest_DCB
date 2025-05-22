package dcb.reports;

import java.util.Enumeration;
import javax.servlet.http.*;
public class ServletController 
{
	private Enumeration result;
	
	public ServletController()
	{
		
	}
	
	public Enumeration getColumnValues(String omiision,HttpServletRequest request)
	{
		result= request.getAttributeNames();
		
		String []filter=omiision.split("\\,");
		
		
		
		
		
		return result;
	}
	
}
