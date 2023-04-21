package Pages;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Hosts.Host;

public class Page {
	String pageURI;
	//to store unique page
	ArrayList<Page> pagesLinked;
	Pattern pattern;
	final String regex = "\\b((?:https?|ftp|file):"
            + "//[-a-zA-Z0-9+&@#/%?="
            + "~_|!:, .;]*[-a-zA-Z0-9+"
            + "&@#/%=~_|])";
	
	//contain all the pages, both user input and crawled
	public static ArrayList<Page> pagesList = new ArrayList<Page>();
	
	public Page(String pageURI) {
		this.pageURI = pageURI;
		this.pagesLinked = new ArrayList<>();
		pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		pagesList.add(this);
	}
	
	public void extractLinks() {
		try {
			//jsoup library: extract data from HTML
			URI uri = new URI(this.pageURI);
			Document doc = Jsoup.connect(uri.toString()).get();
			//Reference: https://stackoverflow.com/questions/28660603/how-to-detect-url-to-different-page-also-in-the-same-domain
			//Elements: extract a list of Element 
			Elements htmlLinks = doc.select("a[href]");
			
			for (Element eachHTML : htmlLinks) {
				//get absolute URL only to prevent duplicates
				String link = eachHTML.attr("href");
				//search for pattern of 'pattern' in string
				Matcher matcher = pattern.matcher(link);	
				
				while(matcher.find()) { //.find(): return true if the pattern was found
					Page linkedPage = new Page(link.substring(matcher.start(0), matcher.end(0)));
					
					System.out.println(linkedPage);
					
					//if the link is unique
					if (isLinkedPageUnique(linkedPage)) {
						//if unique, add it to list
						pagesLinked.add(linkedPage);
					}
				}
			}
		}	
		catch (MalformedURLException ex) {
			System.out.println("Invalid URL");
		} 
		catch (IOException ioe) {
			System.out.println("I/O errors: No such file!");
		} 
		catch (URISyntaxException uriE) {
			uriE.printStackTrace();
		}
	}
	
	//unique in this page's pagesLinked
	public boolean isLinkedPageUnique(Page p) throws URISyntaxException {
		return samePage(p) == null;
	}
	
	//return the page in stored list of THIS PAGE which is the same to Page p
	public Page samePage(Page p) throws URISyntaxException {
		for (Page pageInList : pagesLinked) {
			if (pageInList.equals(p)) {
				return pageInList;
			}
		}
		//return null when there is no same page
		return null;
	}
	
	public void extractLinkedPagesHost() throws URISyntaxException {
		for (Page linkedPage : pagesLinked) {
			for (Host host : Host.hostList) {
				if (linkedPage.getHost().equals(host.getHost())) {
					//add 1 to numLinks of hosts in list if the host of this page is the same of that host
					host.setNumLinks(host.getNumLinks() + 1);
				}
			}
		}
	}
	
	public boolean equals(Page p) throws URISyntaxException {
		return this.getPath().equals(p.getPath()) && this.getHost().equals(p.getHost());
	}
	
	public String getPath() throws URISyntaxException {
		return new URI(this.pageURI).getPath();
	}
	
	public String getHost() throws URISyntaxException {
		return new URI(this.pageURI).getHost();
	}

	public String getPageURL() {
		return pageURI;
	}

	public int getNumLink() {
		return pagesLinked.size();
	}

	public Pattern getPattern() {
		return pattern;
	}

	public String getRegex() {
		return regex;
	}
	
	public String toString() {
		return pageURI;
	}
	
	public ArrayList<Page> getPagesLinked() {
		return this.pagesLinked;
	}
}
