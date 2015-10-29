package com.hybrid.xml;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.core.appender.routing.Route;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.hybrid.model.RouteInfoItem;

public class BusRouteInfoParser {

	static Log log = LogFactory.getLog(BusRouteInfoParser.class);
	
	DocumentBuilderFactory dFactory;
	DocumentBuilder builder;
	
	XPathFactory xFactory;
	
	TransformerFactory tFactory;
	
	public BusRouteInfoParser() throws ParserConfigurationException {
		dFactory = DocumentBuilderFactory.newInstance();
		builder = dFactory.newDocumentBuilder();
		
		xFactory = XPathFactory.newInstance();
		
		tFactory = TransformerFactory.newInstance();
	}
	
	public static void main(String[] args) {
		try {
			
			BusRouteInfoParser parser = new BusRouteInfoParser();
			
			List<RouteInfoItem> list = parser.getBusRouteList("6628");
			log.info("list size = " + list.size());
			for (RouteInfoItem item : list) {
				log.info(item.getBusRouteId());
				log.info(item.getBusRouteNm());
				log.info(item.getEdStationNm());
			}
			
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("Program end...");
		
	}
	
	public List<RouteInfoItem> getBusRouteList(String strSrch) {
		log.info("getBusRouteList = " + strSrch);
		String url = "http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList?strSrch=" + strSrch + "&ServiceKey=AaxqTg02PVW%2BZhaIkh4fVAIiknK6EU6ZkfT1lQEHEo2PRlldpzfhjoBwE63YKQGpiY4JdZCjCktTW2yatRX%2FgA%3D%3D";
		
		List<RouteInfoItem> model = new ArrayList<>();
		
		String result=null;
		try {
			
			//Unmarshall(Deserialization)
			Document document = builder.parse(url);
			
			XPath xpath = xFactory.newXPath();
			XPathExpression expr = xpath.compile("//itemList");
			
			NodeList list = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
			
			for (int i=0; i<list.getLength(); i++) {
				Element el = (Element) list.item(i);
				NodeList childs = el.getChildNodes();
				
				RouteInfoItem item = new RouteInfoItem();
				
				for (int j=0; j<childs.getLength(); j++) {
					if (childs.item(j).getNodeType() == Node.ELEMENT_NODE) {
						
						if (childs.item(j).getNodeName().equals("busRouteId"))
							item.setBusRouteId(childs.item(j).getTextContent());
						
						if (childs.item(j).getNodeName().equals("busRouteNm"))
							item.setBusRouteNm(childs.item(j).getTextContent());
						
						if (childs.item(j).getNodeName().equals("edStationNm"))
							item.setEdStationNm(childs.item(j).getTextContent());
					}
				}
				model.add(item);
				
				log.info("Element Name = " + el.getNodeName());
				
			}
			
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
			System.out.println(result);
			
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
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return model;
	}
}












