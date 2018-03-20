package com.epam.Crawler;

public class SpiderTest {

	public static void main(String[] args) 
	{
		Spider spider = new Spider();
		spider.search("http://quora.com/", "Salauddin");
	}

}
