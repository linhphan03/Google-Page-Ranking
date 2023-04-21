package MainRun;

import java.net.URISyntaxException;

import Hosts.Host;
import Pages.Page;
import WebCrawler.HTMLreader;

public class Main {

	public static void main(String[]args) throws URISyntaxException {
		Host.initHostList();
		
		HTMLreader html = new HTMLreader();
		html.receiveInput();

		for (Page page : HTMLreader.inputPages) {
			page.extractLinkedPagesHost();
		}
		
//		for (Host host : Host.hostList) {
//			System.out.println(host.getHost() + " " + host.getNumLinks());
//		}
		
		for (Page inputPage : HTMLreader.inputPages) {
			System.out.println("Pages in " + inputPage.toString() + ": ");
			for (Page page : inputPage.getPagesLinked()) {
				System.out.println("\t" + page);
			}
			System.out.println("--------------------------");
		}
	}

}
