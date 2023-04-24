package Pages;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.IDN;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
	//ArrayList<Page> pagesLinked;
	
	//reference to index page in allPages
	ArrayList<Integer> reference;
	Pattern pattern;
	int depth;
	final String regex = "\\b((?:https?|ftp|file):"
            + "//[-a-zA-Z0-9+&@#/%?="
            + "~_|!:, .;]*[-a-zA-Z0-9+"
            + "&@#/%=~_|])";
	
	//contain all the pages, both user input and crawled
	public static ArrayList<Page> allPages = new ArrayList<Page>();
	
	public Page(String pageURI) throws UnsupportedEncodingException, MalformedURLException, URISyntaxException {
		//this.pageURI = pageURI.replaceAll("#", "%23");
		//this.pageURI = URLEncoder.encode(pageURI, StandardCharsets.UTF_8.toString());
		//System.out.println(this.pageURI);
		this.pageURI = pageURI;
		URL url = new URL(pageURI);
		URI uri = new URI(url.getProtocol(), url.getUserInfo(), IDN.toASCII(url.getHost()), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
		this.pageURI = uri.toASCIIString();
		
		//this.pagesLinked = new ArrayList<>();
		this.reference = new ArrayList<>();
		pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		allPages.add(this);
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
					Page extracted = new Page(link.substring(matcher.start(0), matcher.end(0)));
					if (isPageAdded(extracted) == -1) { //not added yet
						addPageToAllPages(extracted);
					}
					
					
					//if the link extracted is unique
//					if (isLinkedPageUnique(linkedPage)) {
//						//if unique, add it to reference list
//						//pagesLinked.add(linkedPage);
//					}
				}
			}
		}	
		catch (IOException ioe) {
			System.out.println("I/O errors: No such file!");
		} 
		catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
//	//unique in this page's pagesLinked
//	public boolean isLinkedPageUnique(Page p) throws URISyntaxException {
//		return samePage(p) == null;
//	}
//	
//	//return the page in stored list of THIS PAGE which is the same to Page p
//	public Page samePage(Page p) throws URISyntaxException {
//		for (Page pageInList : pagesLinked) {
//			if (pageInList.equals(p)) {
//				return pageInList;
//			}
//		}
//		//return null when there is no same page
//		return null;
//	}
	
	//added in big list containing ALL PAGES (allPages)
	public int isPageAdded(Page extracted) throws URISyntaxException {
		for (int i = 0; i < allPages.size(); i++) {
			if (extracted.equals(allPages.get(i))) {
				//if extracted is added in allPages, return the index of the same page
				return i;
			}
		}
		//else return -1
		//this also means that 'extracted' is the unique link extracted from 'this' page
		return -1;
	}
	
	//if 'extracted' ISN'T added to allPages, this means 'extracted' is unique in both reference & allPages
	//  if so, add it to allPages and add its reference (last index in allPages) to 'this.reference'
	public void addPageToAllPages(Page extracted) throws URISyntaxException {
		allPages.add(extracted);
		this.reference.add(getNumLink() - 1);
	}
	
	//from exisiting extracted pages, extract their host
	public void extractHost() throws URISyntaxException {
		for (int i : this.reference) {
			for (Host host : Host.hostList) {
				if (allPages.get(i).getHost().equals(host.getHost())) {
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

	public String getPageURI() {
		return pageURI;
	}

	public int getNumLink() {
		return reference.size();
	}

	public Pattern getPattern() {
		return pattern;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getRegex() {
		return regex;
	}
	
	public String toString() {
		return pageURI;
	}
	
//	public ArrayList<Page> getPagesLinked() {
//		return this.pagesLinked;
//	}
}
