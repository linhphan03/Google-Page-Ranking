package WebCrawler;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashSet;
//import java.io.File;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.MalformedURLException;
//import java.net.URL;
import java.util.Scanner;
import java.util.Set;

import Pages.Page;

public class HTMLreader {
	// Contain link of each page entered
	public static Set<Page> inputPages = new HashSet<>();
	
	public void receiveInput(Scanner sc) throws UnsupportedEncodingException, MalformedURLException, URISyntaxException {
		System.out.println("Enter web pages' URL, enter extract level to exit ");
		
		while (sc.hasNextLine()) {
			String nextLine = sc.nextLine();
			
			if (!nextLine.chars().allMatch( Character::isDigit )) {
				Page page = new Page(nextLine);
				inputPages.add(page);
			}
			else {
				Page.crawlLevel = Integer.valueOf(nextLine);
				break;
			}
		}
		
		addToAllPages();
	}
	
	public void addToAllPages() {
		for (Page input : HTMLreader.inputPages) {
			input.addPageToAllPages();
		}
	}
}
