package Hosts;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
	
	public String toString() {
		return this.host;
	}
	
	public static void initHostList() throws URISyntaxException {
		hostList.add(new WallStreetJournal());
		hostList.add(new NewYorkTimes());
		hostList.add(new USAToday());
		hostList.add(new WashingtonPost());
		hostList.add(new LosAngelesTimes());
	}
	
	public static void rankHost() {
		Collections.sort(hostList, Comparator.comparingInt(Host::getNumLinks).reversed());
	}
	
	public static void printHostRank() {
		System.out.println("Host rank in descending order");
		for (Host host : Host.hostList) {
			System.out.println(host.toString() + ": " + host.getNumLinks());
		}		
	}
}
