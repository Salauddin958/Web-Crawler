package com.epam.Crawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Spider {
	
	private static final int MAX_PAGES_TO_SEARCH = 10;
	private Set<String> pagesVisited = new HashSet<>();
	private List<String> pagesToVisited = new LinkedList<>();
	
	
	private String nextUrl()
	{
		String nextUrl;
		do {
			nextUrl = this.pagesToVisited.remove(0);
		}while(this.pagesVisited.contains(nextUrl));
		this.pagesVisited.add(nextUrl);
		
		return nextUrl;
	}
	
	public void search(String url,String searchWord)
	{
		while(this.pagesVisited.size() < MAX_PAGES_TO_SEARCH)
		{
			String currentUrl;
			SpiderLeg leg = new SpiderLeg();
			if (this.pagesToVisited.isEmpty()) 
			{
				currentUrl = url;
				this.pagesVisited.add(url);
			}
			else 
			{
				currentUrl = nextUrl();
				
			}
			leg.crawl(currentUrl);
			boolean success = leg.searchForWord(searchWord);
			if (success) 
			{
				System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
				break;
			}
			this.pagesVisited.add(currentUrl);
			
		}
		 System.out.println(String.format("**Done** Visited %s web page(s)", this.pagesVisited.size()));
	}
	
}
