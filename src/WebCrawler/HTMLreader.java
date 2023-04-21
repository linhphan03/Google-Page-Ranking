package WebCrawler;

import java.net.URISyntaxException;
import java.util.HashSet;
//import java.io.File;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.MalformedURLException;
//import java.net.URL;
import java.util.Scanner;
import java.util.Set;

import Hosts.Host;
import Hosts.HostList;
import Pages.Page;

public class HTMLreader {
	// Contain link of each page entered
	static Set<Page> inputPages = new HashSet<>();
	
	public void receiveInput() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter web pages' URL ('Q' to finish): ");
		
		while (sc.hasNextLine()) {
			String nextLine = sc.nextLine();
			
			if (!nextLine.toLowerCase().trim().equals("quit")) {
				Page page = new Page(nextLine);
				page.extractLinks();
				inputPages.add(page);
			}
			else {
				break;
			}
		}
		sc.close();
	}

	public static void main(String[]args) throws URISyntaxException {
		HostList.initHostList();
		
		HTMLreader h = new HTMLreader();
		h.receiveInput();

		for (Page page : inputPages) {
			page.extractLinkedPagesHost();
		}
		
		for (Host host : HostList.hostLists) {
			System.out.println(host.getHost() + " " + host.getNumLinks());
		}
	}
}
