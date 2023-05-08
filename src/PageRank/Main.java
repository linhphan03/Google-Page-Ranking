package PageRank;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import WebPage.SamplePage;

public class Main {
	public static Set<SamplePage> inputPage = new HashSet<>();
	public static int crawlLevel = 1;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter pages (0 - 50), separated by space: ");
		
		//add all user input into inputPage
		String[] strInput = sc.nextLine().split(" ");
		for (String input : strInput) {
			inputPage.add(new SamplePage(Integer.valueOf(input)));
		}
		//System.out.println("inputPage: " + inputPage.toString());
		
		//add to big list
		addInputToAllPagesList();
		SamplePage.initGraph();
		//System.out.println(SamplePage.graph.toString());
		
		int crawlPages = SamplePage.allPages.size();
		int p = 0;
		
		//ask for extract level
		System.out.print("Enter extract level: ");
		crawlLevel = sc.nextInt();
		
		for (int l = 0; l < crawlLevel; l++) {
			for (; p < crawlPages; p++) {
				//System.out.println(p);
				SamplePage page = SamplePage.allPages.get(p);
				page.extractLinks();
			}
			//after each level, update number of pages crawled
			crawlPages = SamplePage.allPages.size();
		}
		
		PageRankAlgorithm_Sample pr = new PageRankAlgorithm_Sample();
		pr.printPageRank();
		
		sc.close();
	}
	
	public static void addInputToAllPagesList() {
		for (SamplePage p : inputPage) {
			SamplePage.allPages.add(p);
		}
	}

}
