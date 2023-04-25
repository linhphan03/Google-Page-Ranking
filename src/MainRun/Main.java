package MainRun;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import Hosts.Host;
import WebCrawler.HTMLreader;

public class Main {

	public static void main(String[]args) throws URISyntaxException, UnsupportedEncodingException, MalformedURLException {
//		Host.initHostList();
//		
//		HTMLreader html = new HTMLreader();
//		html.receiveInput();

//		for (Page page : HTMLreader.inputPages) {
//			page.extractHost();
//		}
		
//		Host.rankHost();
//		
//		for (Host host : Host.hostList) {
//			System.out.println(host.getHost() + " " + host.getNumLinks());
//		}
		
//		for (Page input : HTMLreader.inputPages) {
//			input.addPageToAllPages();
//			input.extractLinks();
//			System.out.println("-----------------------------------------");
//		}
		
//		int count = 0;
//		for (Page p : Page.allPages) {
//			System.out.println("(" + (++count) + ") " + p);
//		}
		
//		for (Page input : HTMLreader.inputPages) {
//			System.out.println(input.getReference().toString());
//			System.out.println(input.getNumLink());
//		}
		
		URI x = new URI("https://www.facebook.com/sharer.php?u=https%253A%252F%252Flithub.com%252Faccording-to-the-new-york-times-bestseller-lists-a-lot-of-people-are-buying-books-about-racism%252F&t=According+to+the+New+York+Times+bestseller+lists%252C+a+lot+of+people+are+reading+about+racism");
		System.out.println(x.getHost());
		System.out.println(x.getPath());
	}

}
