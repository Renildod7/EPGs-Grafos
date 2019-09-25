
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.ListSingleSourcePathsImpl;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.io.CSVFormat;
import org.jgrapht.io.CSVImporter;
import org.jgrapht.io.EdgeProvider;
import org.jgrapht.io.ImportException;
import org.jgrapht.io.VertexProvider;

public class VendaImoveis {
	
	Graph <String,DefaultWeightedEdge> distrito;
	
	VendaImoveis (String fileName) {
		
		this.distrito = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		importWeightedGraphCSV(distrito, fileName);

		/* Adicione aqui o c�digo que cria uma instancia da classe grafo desejada a partir de um arquivo
		 * cujo nome est� definido em filename.
		 * Veja m�todos para importa��o de arquivos em MyJGraphTUtil.java
		 */
	}
	
	public String localizaImovel (String pontodeInteresse, Set <String> imoveis) {
		if(this.distrito.containsVertex(pontodeInteresse) && !imoveis.isEmpty()) {
			DijkstraShortestPath<String, DefaultWeightedEdge> dsp = new DijkstraShortestPath<>(distrito);
			SingleSourcePaths<String, DefaultWeightedEdge> spa = dsp.getPaths(pontodeInteresse);
			
			String maisPerto =  "";
			double menorDistancia = Double.MAX_VALUE;
			
			for(String imovel : imoveis) {
				double dist = spa.getWeight(imovel);
				
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
