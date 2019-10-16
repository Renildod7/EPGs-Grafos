package epg03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;


public class Main {

	public static void main(String[] args) {
		Graph<String, DefaultWeightedEdge> grafo;
		grafo = new DefaultUndirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		importWeightedGraphCSV(grafo, "./src/main/resources/antcolony1000.csv");
		
		ArrayList<Integer> a = new ArrayList<>();
		
		
		for(String s : grafo.vertexSet()) a.add(Integer.parseInt(s));
		Collections.sort(a);
		
		System.out.println(a.toString());
		
		grafo.vertexSet();
		grafo.edgeSet();
		
		
		
	}

	
	
	
	
	
	public static Graph<String, DefaultWeightedEdge> importWeightedGraphCSV(Graph<String, DefaultWeightedEdge> graph,
			String filename) {
		// WEIGHTED EDGE LIST
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String sCurrentLine = br.readLine();
			while ((sCurrentLine = br.readLine()) != null) {
				String[] attributes = sCurrentLine.split(",");
				graph.addVertex(attributes[0]);
				graph.addVertex(attributes[1]);
				DefaultWeightedEdge e = new DefaultWeightedEdge();
				graph.addEdge(attributes[0], attributes[1], e);
				graph.setEdgeWeight(e, new Double(attributes[2]).doubleValue());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return graph;
	}

	static Reader readFile(String filename) {
		StringBuilder contentBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				contentBuilder.append(sCurrentLine).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringReader readergml = new StringReader(contentBuilder.toString());
		return readergml;
	}

}
