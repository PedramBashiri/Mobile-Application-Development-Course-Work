package com.example.homework05;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;
import android.util.Xml;
import android.util.Xml.Encoding;


public class AppUtils_SAX {

	
static public class AppsSAXParser extends DefaultHandler{
		
	
	ArrayList<AppInfo> appinfoList;
		AppInfo info;
		StringBuilder xmlInnerText;
		public int check = 0;
		public int check2 = 0;
		static public ArrayList<AppInfo> parseApp(InputStream in) throws IOException, SAXException
		{
			AppsSAXParser parser= new AppsSAXParser();
			Xml.parse(in, Encoding.UTF_8, parser);
			return parser.getAppsList();
			
		}
		

		public ArrayList<AppInfo> getAppsList() {
			return appinfoList;
		}


		@Override
		public void startDocument() throws SAXException {
			// TODO Auto-generated method stub
			super.startDocument();
			xmlInnerText = new StringBuilder();
			appinfoList = new ArrayList<AppInfo>();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			// TODO Auto-generated method stub
			super.startElement(uri, localName, qName, attributes);
			if(localName.equals("entry"))
			{
				info = new AppInfo();
				Log.d("demo","Entry");
				check = 1;
			}
			else if(localName.equals("id") && check==1)
			{
				info.setId(attributes.getValue("im:id"));
				Log.d("id",attributes.getValue("im:id"));
			}
			else if(localName.equals("link")&& check==1)
			{
				info.setAppUrl(attributes.getValue("href"));
				Log.d("link",attributes.getValue("href"));
			}
			
			else if(localName.equals("price"))
			{
				info.setPrice(attributes.getValue("amount"));
				Log.d("price",attributes.getValue("amount"));
			}
			else if(localName.equals("image"))
			{
				if((attributes.getValue("height")).equals("53"))
					check2 = 1;
			}
			
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			// TODO Auto-generated method stub
			super.endElement(uri, localName, qName);
			if(localName.equals("entry"))
			{
				appinfoList.add(info);
				Log.d("demo","Adding to list");
				check = 0;
				check2 = 0;
			}
			else if(localName.equals("title")&& check==1)
			{
				info.setAppTitle(xmlInnerText.toString().trim());
				Log.d("title",xmlInnerText.toString());
			}
			else if(localName.equals("artist"))
			{
				info.setDevName(xmlInnerText.toString().trim());
				Log.d("artist",xmlInnerText.toString());
			}
			else if(localName.equals("image") && check2 == 1)
			{
				info.setSmallPhotoUrl(xmlInnerText.toString().trim());
				Log.d("imgURL",xmlInnerText.toString());
			}
			xmlInnerText.setLength(0);
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			// TODO Auto-generated method stub
			super.characters(ch, start, length);
			xmlInnerText.append(ch, start, length);
		}
		
		
	}
	
}
