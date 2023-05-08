package Test;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Scanner;

import PageHost.Host;
import PageRank.PageRankAlgorithm;
import WebCrawler.HTMLreader;
import WebPage.Page;

public class Main {
	public static final int RANK_HOST = 0;
	public static final int RANK_PAGE = 1;

	public static void main(String[]args) throws URISyntaxException, UnsupportedEncodingException, MalformedURLException {
		Host.initHostList();
				
		//Receive links from user input
		Scanner sc = new Scanner(System.in);
		HTMLreader html = new HTMLreader();
		html.receiveInput(sc);
		
		int crawlPages = Page.allPages.size();
		int p = 0;
		
		for (int l = 0; l < HTMLreader.crawlLevel; l++) {
			for (; p < crawlPages; p++) {
				Page page = Page.allPages.get(p);
				page.extractLinks();
			}
			//after each level, update number of pages crawled
			crawlPages = Page.allPages.size();
		}
		//extract hosts
		for (Page page : HTMLreader.inputPages) {
			page.extractHost();
		}
		//rank the popularity of hosts according to input pages and crawled pages
		Host.rankHost();
		
		System.out.print("Choose 0 to rank hosts, 1 to rank pages: ");
		
		int choice = sc.nextInt();
		
		if (choice == 0) {
			Host.printHostRank();
		}
		else {
			PageRankAlgorithm pr = new PageRankAlgorithm();
			pr.printPageRank();
		}

//		for (Host host : Host.hostList) {
//			System.out.println(host.getHost() + " " + host.getNumLinks());
//		}
		
//		int count = 0;
//		for (Page p : Page.allPages) {
//			System.out.println("(" + (++count) + ") " + p);
//		}
		
//		for (Page input : HTMLreader.inputPages) {
//			System.out.println(input.getReference().toString());
//			System.out.println(input.getNumLink());
//			System.out.println(input.getCrawled());
//		}	
		
//		PageRankAlgorithm pr = new PageRankAlgorithm();
//		pr.printAdjacencyMatrix();
//		System.out.println("\n".repeat(4));
//		pr.printHyperlinkMatrix();
//		System.out.println("\n".repeat(4));
//		pr.printAlteredHyperlinkMatrix();;
//		pr.printGoogleMatrix();
	}
}
