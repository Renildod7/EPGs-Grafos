import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Main {
	
	public static void main(String[] args) {
		
		Graph<String, DefaultWeightedEdge> grafo;
		grafo = new DefaultUndirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		//grafo = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		//importWeightedGraphCSV(grafo, "./src/main/java/test.csv");
		importWeightedGraphCSV(grafo, "./src/main/resources/Vizinhanca.csv");
		DefaultWeightedEdge e = new DefaultWeightedEdge();
		
		System.out.println(grafo.vertexSet());
		System.out.println(grafo.edgeSet());
		
		Set <String> imoveis1 = new HashSet<>();
		imoveis1.add("I1"); imoveis1.add("I2"); imoveis1.add("I3"); imoveis1.add("I4"); imoveis1.add("I5");

		
		for(String s : grafo.vertexSet()) {
			System.out.println("Mais proximo de " + s + " :" + localizaImovel(grafo, s, grafo.vertexSet()) + System.lineSeparator());
		}

	//	System.out.println(localizaImovel(grafo, "a", imoveis));


		
		
		
		
		
		
	}
	
	public static void addEdge(Graph<String, DefaultWeightedEdge> grafo, String a, String b, double dist) {
		DefaultWeightedEdge e = new DefaultWeightedEdge();
		grafo.addVertex(a);
		grafo.addVertex(b);
		grafo.addEdge(a, b, e);
		grafo.setEdgeWeight(e, new Double(dist).doubleValue());
		System.out.println(grafo.getEdgeWeight(e));
		

	}
	
	public static String localizaImovel (Graph<String, DefaultWeightedEdge> grafo, String pontodeInteresse, Set <String> imoveis) {
		if(grafo.containsVertex(pontodeInteresse) && !imoveis.isEmpty()) {
			DijkstraShortestPath<String, DefaultWeightedEdge> dsp = new DijkstraShortestPath<>(grafo);
			SingleSourcePaths<String, DefaultWeightedEdge> spa = dsp.getPaths(pontodeInteresse);
			
			
			String maisPerto =  "";
			double menorDistancia = Double.POSITIVE_INFINITY;
			System.out.println("##########" + pontodeInteresse + "##########");
			for(String imovel : imoveis) {
				double dist = spa.getWeight(imovel);
				//double dist = dsp.getPath(pontodeInteresse, imovel).getWeight();
				
				System.out.println("Distancia de "+ pontodeInteresse + " ate " + imovel + ": " + dist);
				
				if(dist < menorDistancia) {
					menorDistancia = dist;
					maisPerto = imovel;
				}
			}
			return maisPerto;
			
		} else {
			return null;
		}
		/*
		 * Adicione aqui o c�digo que recebe um ponto de interesse como entrada, uma lista de im�veis e retorna o im�vel dispon�vel mais pr�ximo a este ponto de interesse.
		 * Note que tanto o ponto de interesse quanto o im�vel a ser selecionado s�o v�rtices do grafo.
		 */
		//return null;

	}
	
	
	public static Graph<String,DefaultWeightedEdge> importWeightedGraphCSV 
		(Graph<String,DefaultWeightedEdge> graph, String filename) {
		// WEIGHTED EDGE LIST
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String sCurrentLine = br.readLine();
			while ((sCurrentLine = br.readLine()) != null) {
				String [] attributes = sCurrentLine.split(",");
				graph.addVertex(attributes[0]);
				graph.addVertex(attributes[1]);
				DefaultWeightedEdge e = new DefaultWeightedEdge();
				graph.addEdge(attributes[0], attributes[1], e);
				graph.setEdgeWeight(e, new Double(attributes[2]).doubleValue());
				System.out.println( e + " " + graph.getEdgeWeight(e));
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
