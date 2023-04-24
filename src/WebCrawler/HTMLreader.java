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
	
	public void receiveInput() throws UnsupportedEncodingException, MalformedURLException, URISyntaxException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter web pages' URL ('Q' to finish): ");
		
		while (sc.hasNextLine()) {
			String nextLine = sc.nextLine();
			
			if (!nextLine.toLowerCase().trim().equals("q")) {
				Page page = new Page(nextLine);
				inputPages.add(page);
			}
			else {
				break;
			}
		}
		sc.close();
	}
}
