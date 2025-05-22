package Servlets.AME.AME1.AMEM.servlets;
import java.io.*;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
public class Readxml 
{

		public Readxml()
		{
			
			
		}
		
		public static void main(String s[])
		{
			
			try
			{
			
				File f=new File ("E://s1.xml"  );
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(f);

				NodeList nList = doc.getElementsByTagName("staff");
				
				for (int temp = 0; temp < nList.getLength(); temp++)
				{
					 
					Node nNode = nList.item(temp);
					System.out.println("\nCurrent Element :" + nNode.getNodeName());
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						 
						Element eElement = (Element) nNode;
			 
						System.out.println("Staff id : " + eElement.getAttribute("id"));
						System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
						System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
						System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
						System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());
			 
					}
				}
				
				
				

			}catch(Exception e) 
			{
				
				
			}
			
		}
	
	
}
