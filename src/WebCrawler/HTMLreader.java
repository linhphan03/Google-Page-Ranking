package WebCrawler;

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
	Set<Set<Page>> linkList = new HashSet<>();
	
	public void takeLink() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter web pages' URL ('Quit' to finish): ");
		
		while (sc.hasNextLine()) {
			String nextLine = sc.nextLine();
			
			if (!nextLine.toLowerCase().trim().equals("quit")) {
				Page page = new Page(nextLine);
				page.extractLinks();
				linkList.add(page.getPagesLinked());
			}
			else {
				break;
			}
		}
		sc.close();
	}
	
	public void viewList() {
		for (Set<Page> pages : linkList) {
			for (Page p : pages) {
				System.out.println(p);
			}
			System.out.println("--------------------------------------");
		}
	}
	
	public static void main(String[]args) {
		HTMLreader h = new HTMLreader();
		h.takeLink();
		h.viewList();
	}
}
