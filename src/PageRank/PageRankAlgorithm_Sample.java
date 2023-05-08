
package PageRank;

import java.util.ArrayList;

import Jama.Matrix;
import WebPage.SamplePage;

public class PageRankAlgorithm_Sample {
	 //return PageRank 
	static final double ALPHA = 0.85;
	static final int ITERATIONS = 100;
	
	//contain importance score of pages
	double[] pageRankVector; 
	double[] initialVector;
	ArrayList<SamplePage> graph = SamplePage.allPages;
	Matrix adjacencyMatrix;
	Matrix hyperlinkMatrix;
	//altered hyperlink matrix with dangling nodes fixed
	Matrix alteredHyperlinkMatrix;
	Matrix googleMatrix;
	
	public PageRankAlgorithm_Sample() {
		//if Page ith links to Page j, adjacencyMatrix[i - 1][j - 1] = 1. Otherwise = 0
		//column: the pages analyzed.
		//row: the pages link to it
		adjacencyMatrix = new Matrix(getNumPage(), getNumPage());
		initAdjacencyMatrix();
		
		hyperlinkMatrix = new Matrix(getNumPage(), getNumPage());
		initHyperlinkMatrix();
		
		initialVector = new double[getNumPage()];
		initInitialVector();
		
		alteredHyperlinkMatrix = new Matrix(getNumPage(), getNumPage());
		initAlteredHyperlinkMatrix();
		
		initGoogleMatrix();
		
		pageRankVector = new double[getNumPage()];
		calculatePageRank();
	}
	
	public void initAdjacencyMatrix() {
		for (int c = 0; c < graph.size(); c++) {
			int adjacencySize = graph.get(c).getReference().size();
			
			for (int p = 0; p < adjacencySize; p++) {
				adjacencyMatrix.set(graph.get(c).getReference().get(p), c, 1);
			}
		}
	}
	
	public void initHyperlinkMatrix() {
		for (int i = 0; i < getNumPage(); i++) {
			//get number of links of page ith
			double scale_factor = graph.get(i).getNumLink();
			//if scale_factor == 0: page ith not link to any other page -> do nothing
			if (scale_factor != 0) {
				int adjacencySize = graph.get(i).getReference().size();

				for (int p = 0; p < adjacencySize; p++) {
					hyperlinkMatrix.set(graph.get(i).getReference().get(p), i, 1/scale_factor);
				}
			}
		}
	}
	
	public void initInitialVector() {
		for (int i = 0; i < initialVector.length; i++) {
			initialVector[i] = (double)1 / getNumPage();
		}
	}
	
	public void initAlteredHyperlinkMatrix() {
		//copy hyperlink to altered hyperlink
		alteredHyperlinkMatrix = new Matrix(hyperlinkMatrix.getArray());
				
		double importance_score = (double)1 / getNumPage();
		
		for (int p = 0; p < graph.size(); p++) {
			if (graph.get(p).getNumLink() == 0) {
				for (int r = 0; r < alteredHyperlinkMatrix.getRowDimension(); r++) {
					alteredHyperlinkMatrix.set(r, p, importance_score);
				}
			}
		}
	}
	
	public void initGoogleMatrix() {
		Matrix tmp = new Matrix(getNumPage(), getNumPage());
		for (int r = 0; r < getNumPage(); r++) {
			for (int c = 0; c < getNumPage(); c++) {
				tmp.set(r, c, (double)1 / getNumPage());
			}
		}
		
		googleMatrix = alteredHyperlinkMatrix.times(ALPHA);
		googleMatrix = googleMatrix.plus(tmp.times(1 - ALPHA));
	}
	
	public void calculatePageRank() {
		//use Google Matrix and initial vector
		Matrix vector = new Matrix(initialVector, initialVector.length);

		for (int i = 0; i < ITERATIONS; i++) {
			googleMatrix.times(googleMatrix);
		}
		googleMatrix.times(vector);
		
		//googleMatrix.print(1, 4);
		
		
		for (int i = 0; i < googleMatrix.getRowDimension(); i++) {
			pageRankVector[i] = googleMatrix.get(i, 0);
		}
	}
	
	public void printAdjacencyMatrix() {
		System.out.println("Adjacency Matrix: \n");
		for (int r = 0; r < getNumPage(); r++) {
			for (int c = 0; c < getNumPage(); c++) {
				System.out.printf("%-3.0f", adjacencyMatrix.get(r, c));
			}
			System.out.println();
		}
	}
	
	public void printHyperlinkMatrix() {
		System.out.println("Hyperlink Matrix: \n");
		for (int r = 0; r < getNumPage(); r++) {
			for (int c = 0; c < getNumPage(); c++) {
				System.out.printf("%-10.4f", hyperlinkMatrix.get(r, c));
			}
			System.out.println();
		}
	}
	
	public void printAlteredHyperlinkMatrix() {
		System.out.println("Altered Hyperlink Matrix: \n");
		for (int r = 0; r < getNumPage(); r++) {
			for (int c = 0; c < getNumPage(); c++) {
				System.out.printf("%-10.4f", alteredHyperlinkMatrix.get(r, c));
			}
			System.out.println();
		}
	}
	
	public void printGoogleMatrix() {
		System.out.println("Google Matrix: \n");
		for (int r = 0; r < getNumPage(); r++) {
			for (int c = 0; c < getNumPage(); c++) {
				System.out.printf("%-10.4f", googleMatrix.get(r, c));
			}
			System.out.println();
		}
	}
	
	public void printPageRank() {
		for (double d : pageRankVector) {
			System.out.printf("%-7.3f", d);
		}
	}
	
	public int getNumPage() {
		return graph.size();
	}
}
