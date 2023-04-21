package Hosts;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Host {
	public String host;
	public int numLinks;
	public static ArrayList<Host> hostLists = new ArrayList<>();
	
	public Host(String hostURI) throws URISyntaxException {
		this.host = new URI(hostURI).getHost();
		numLinks = 0;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getNumLinks() {
		return numLinks;
	}

	public void setNumLinks(int numLinks) {
		this.numLinks = numLinks;
	}
	
	public static void initHostList() throws URISyntaxException {
		hostLists.add(new FreeCodeCamp());
		hostLists.add(new GeeksForGeeks());
		hostLists.add(new HackrIO());
		hostLists.add(new StackOverFlow());
		hostLists.add(new W3Schools());
	}
}
