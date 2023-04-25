package Pages;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.IDN;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Hosts.Host;

public class Page {
	String strURI;
	URI uri;
	//to store unique page
	//ArrayList<Page> pagesLinked;
	
	//reference to index page in allPages
	ArrayList<Integer> reference;
	int depth;
	String content;
	final static String regex = "\\b((?:https?|ftp|file):"
            + "//[-a-zA-Z0-9+&@#/%?="
            + "~_|!:, .;]*[-a-zA-Z0-9+"
            + "&@#/%=~_|])";
	final static Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

	
	//contain all the pages, both user input and crawled
	public static ArrayList<Page> allPages = new ArrayList<Page>();
	
	public Page(String strURL) throws UnsupportedEncodingException, MalformedURLException, URISyntaxException {
		URL url = new URL(strURL);
		this.uri = new URI(url.getProtocol(), url.getUserInfo(), IDN.toASCII(url.getHost()), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
		this.strURI = this.uri.toASCIIString();
		
		this.reference = new ArrayList<>();
		content = "";
	}
	
	public void extractLinks() {
		//int count = 0;
		try {
			//jsoup library: extract data from HTML
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
				
					//System.out.println("(" + (++count) + ") " + extracted);
					
					int checkCanAdd = isPageAdded(extracted);
			
					if (checkCanAdd == -1) {
						addPageToAllPages(extracted);
					}
					//System.out.println(checkCanAdd);
					//System.out.println("\n");
				}
			}
			//System.out.println("\n".repeat(2));
		}	
		catch (IOException ioe) {
			System.out.println("I/O errors: No such file!");
		} 
		catch (URISyntaxException e) {
			System.out.println("Wrong URI");
		} 
	}

	public void addPageToAllPages() {
		allPages.add(this);
	}
	
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
		
	//Check if 2 pages are same (navigate to the same)
	public boolean equals(Page p) throws URISyntaxException {
		//check equal content, equal host, equal path
		//return this.getContent().equals(p.getContent()) && this.getHost().equals(p.getHost());
		return this.getHost().equals(p.getHost()) && this.getPath().equals(p.getPath());
	}
	
	public void extractContent() {
		try {
			//http:// is required for URL class
			URL url = new URL(this.strURI);
			Scanner sc = new Scanner(url.openStream());
			
			while (sc.hasNext()) {
				content += sc.nextLine();
			}
		}
		//Invalid URL, esp invalid / missing protocol
		catch (MalformedURLException ex) {
			System.out.println("Invalid URL");
		}
		
		//failed input / output
		catch (IOException ex) {
			System.out.println("I/O errors: no such file");
		}
	}
	
	public String getPath() throws URISyntaxException {
		return new URI(this.strURI).getPath();
	}
	
	public String getHost() throws URISyntaxException {
		return new URI(this.strURI).getHost();
	}

	public String getPageURI() {
		return strURI;
	}

	public int getNumLink() {
		return reference.size();
	}
	
	public String getContent() {
		return this.content;
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
		return strURI;
	}
}