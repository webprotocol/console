package com.hybrid.xml;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class BusRouteInfoParser {

	static Log log = LogFactory.getLog(BusRouteInfoParser.class);
	
	DocumentBuilderFactory dFactory;
	DocumentBuilder builder;
	
	TransformerFactory tFactory;
	
	public BusRouteInfoParser() throws ParserConfigurationException {
		dFactory = DocumentBuilderFactory.newInstance();
		builder = dFactory.newDocumentBuilder();
		
		tFactory = TransformerFactory.newInstance();
	}
	
	public static void main(String[] args) {
		try {
			
			BusRouteInfoParser parser = new BusRouteInfoParser();
			
			String xml = parser.getBusRouteList("6628");
			
			System.out.println(xml);
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("Program end...");
		
	}
	
	public String getBusRouteList(String strSrch) {
		log.info("getBusRouteList = " + strSrch);
		String url = "http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList?strSrch=" + strSrch + "&ServiceKey=AaxqTg02PVW%2BZhaIkh4fVAIiknK6EU6ZkfT1lQEHEo2PRlldpzfhjoBwE63YKQGpiY4JdZCjCktTW2yatRX%2FgA%3D%3D";
		
		String result=null;
		try {
			
			//Unmarshall(Deserialization)
			Document document = builder.parse(url);
			
			//
			
			//Marshall (Serialization)
			tFactory.setAttribute("indent-number", 4);
			Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			
			DOMSource xmlSource = new DOMSource(document);
			
			StringWriter writer = new StringWriter();
			StreamResult outputTarget = new StreamResult(writer);
			
			transformer.transform(xmlSource, outputTarget);
			
			result = outputTarget.getWriter().toString();
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}












