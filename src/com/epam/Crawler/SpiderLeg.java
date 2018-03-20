package com.epam.Crawler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderLeg {
	private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

	private List<String> links = new LinkedList<>();
	private Document htmlDocument;
	
	public boolean crawl(String url)
	{
	 try{
		Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
		Document document = connection.get();
		this.htmlDocument = document;
		
		if (connection.response().statusCode() == 200) 
		{
			System.out.println("\n**Visiting** Received web page at " + url);
		}
		if (!connection.response().contentType().contains("text/html")) 
		{
			System.out.println("**Failure** Retrieved something other than HTML");
            return false;
		}
		System.out.println("Received web page at " + url);
		Elements linksOnPage = document.select("a[href]");
		System.out.println("Found (" + linksOnPage.size() + ") links");
		
		for(Element element : linksOnPage)
		{
			this.links.add(element.absUrl("href"));
			System.out.println(element.absUrl("href"));
		}
		return true;
	 }catch(IOException e)
	 {
		 System.out.println("Error in out HTTP request " + e);
		 return false;
	 }
		
	}
	
	public boolean searchForWord(String word)
	{
		System.out.println("Searching for the word " + word + "...");
		
		if (this.htmlDocument == null) 
		{
			 System.out.println("ERROR! Call crawl() before performing analysis on the document");
	         return false;
		}
		
		String bodyText = this.htmlDocument.body().text();
		return bodyText.toLowerCase().contains(word.toLowerCase());
	}
	
	public List<String> getLinks()
	{
		return this.links;
	}

}
