package WebPage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SamplePage {
	//take sample pages from .txt file and convert into sample page
	int depth;
	//each integer 0, 1,... represent distinct pages
	int nodePage;
	ArrayList<Integer> reference;
	
	//which page links to which pages
	public static ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
	public static ArrayList<SamplePage> allPages = new ArrayList<>();
	public static int crawlLevel = 1;
	
	public SamplePage(int nodePage) {
		this.nodePage = nodePage;
		reference = new ArrayList<>();
	}
	
	public int isPageAdded(SamplePage extracted) {
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
	
	public void addPageToAllPages(SamplePage extracted) {
		allPages.add(extracted);
		this.reference.add(allPages.size() - 1);
	}
	
	public void addPageToAllPages() {
		allPages.add(this);
	}
	
	public boolean equals(SamplePage p) {
		//check equal host, equal path
		//return this.getContent().equals(p.getContent()) && this.getHost().equals(p.getHost());
		return this.nodePage == p.nodePage;
	}
	
	public ArrayList<Integer> getReference(){
		return this.reference;
	}
	
	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String toString() {
		return String.valueOf(this.nodePage);
	}
	
	public int getNumLink() {
		return reference.size();
	}
	
	public void extractLinks() {
		ArrayList<Integer> ref = graph.get(this.nodePage);
		for (int node : ref) {
			SamplePage extracted = new SamplePage(node);
			
			int extractedReference = isPageAdded(extracted);
			
			if (extractedReference == -1) {
				addPageToAllPages(extracted);
				extracted.setDepth(this.depth + 1);
			}
			else {
				if (this.reference.indexOf(extractedReference) == -1) {
					this.reference.add(extractedReference);
				}
			}
		}
	}
	
	public static void initGraph() {
		File input = new File("C:\\Users\\Linh Phan\\eclipse-workspace\\PageRankProject\\src\\WebPage\\inputPage.txt");
		try {
			Scanner readFile = new Scanner(input);
			
			while (readFile.hasNextLine()) {
				String[] pageRef = readFile.nextLine().split(" ");
				ArrayList<Integer> ref = new ArrayList<>();
				
				for (String r : pageRef) {
					ref.add(Integer.valueOf(r));
				}
				graph.add(ref);
			}
			
			readFile.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println("File not found!");
		} 
	}
}
