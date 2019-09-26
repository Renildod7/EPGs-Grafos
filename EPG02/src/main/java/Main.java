import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Main {
	
	public static void main(String[] args) {
		Graph<String, DefaultWeightedEdge> distrito;
		distrito = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		addEdge(distrito, "a", "b", 5.0);
		addEdge(distrito, "a", "c", 4.0);
		addEdge(distrito, "a", "d", 3.0);
		addEdge(distrito, "a", "e", 2.0);
		addEdge(distrito, "a", "f", 1.0);
		
		Set <String> imoveis = new HashSet<>();
		imoveis.add("b");
		imoveis.add("c");
		imoveis.add("d");
		imoveis.add("e");
		imoveis.add("f");
		
		System.out.println(localizaImovel(distrito,"a", imoveis));
		
		addEdge(distrito, "f", "escola", 1.0);
		addEdge(distrito, "f", "c", 2.0);
		addEdge(distrito, "f", "d", 3.0);
		
		Set <String> imoveis2 = new HashSet<>();
		imoveis2.add("escola");
		imoveis2.add("c");
		imoveis2.add("d");
		
		
		System.out.println(localizaImovel(distrito,"f", imoveis2));
		
		System.out.println("importando o grafo");
		
		Graph<String, DefaultWeightedEdge> distrito2;
		distrito2 = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		importWeightedGraphCSV(distrito2, "./src/main/resources/Vizinhanca.csv");
		DefaultWeightedEdge e = new DefaultWeightedEdge();
		
		System.out.println(distrito2.vertexSet());
		System.out.println(distrito2.edgeSet());
		
		Set <String> imoveis1 = new HashSet<>();
		imoveis1.add("I1"); imoveis1.add("I2"); imoveis1.add("I3"); imoveis1.add("I4"); imoveis1.add("I5");
		
		System.out.println(localizaImovel(distrito2, "ESCOLA", distrito2.vertexSet()));


		
		
		
		
		
		
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
			double menorDistancia = Double.MAX_VALUE;
			
			for(String imovel : imoveis) {
				double dist = dsp.getPathWeight(pontodeInteresse, imovel);
				System.out.println(imovel + " " + dist);
				
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
