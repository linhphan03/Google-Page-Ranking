package MainRun;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import Hosts.Host;
import Pages.Page;
import WebCrawler.HTMLreader;

public class Main {

	public static void main(String[]args) throws URISyntaxException, UnsupportedEncodingException, MalformedURLException {
		Host.initHostList();
		
		HTMLreader html = new HTMLreader();
		html.receiveInput();

//		for (Page page : HTMLreader.inputPages) {
//			page.extractHost();
//		}
		
//		Host.rankHost();
//		
//		for (Host host : Host.hostList) {
//			System.out.println(host.getHost() + " " + host.getNumLinks());
//		}
		
		for (Page input : HTMLreader.inputPages) {
			input.addPageToAllPages();
			input.extractLinks();
		}
		
		int count = 0;
//		for (Page p : Page.allPages) {
//			System.out.println("(" + (++count) + ") " + p);
//		}
		
		sysout
	}

}
