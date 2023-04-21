package Hosts;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Host {
	String host;
	int numLinks;
	public static ArrayList<Host> hostList = new ArrayList<>();
	
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
		hostList.add(new FreeCodeCamp());
		hostList.add(new GeeksForGeeks());
		hostList.add(new HackrIO());
		hostList.add(new StackOverFlow());
		hostList.add(new W3Schools());
	}
}
