package MainRun;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class URLComparator {

	public static boolean compareUrls(String url1, String url2) throws URISyntaxException {
	    // Remove fragment identifier from URL1
	    URI uri1 = new URI(url1);
	    String urlWithoutFragment1 = new URI(uri1.getScheme(), uri1.getAuthority(), uri1.getPath(),
	            uri1.getQuery(), null).toString();

	    // Remove fragment identifier from URL2
	    URI uri2 = new URI(url2);
	    String urlWithoutFragment2 = new URI(uri2.getScheme(), uri2.getAuthority(), uri2.getPath(),
	            uri2.getQuery(), null).toString();

	    // Compare the URLs without fragment identifiers
	    return urlWithoutFragment1.equalsIgnoreCase(urlWithoutFragment2);
	}
    
    public static void main(String[]args) throws IOException, URISyntaxException {
    	String link1 = "http://google.com";
    	String link2 = "http://google.com/#";
    	System.out.println(compareUrls(link1, link2));
    }
}
