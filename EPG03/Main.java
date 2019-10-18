package epg03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

import org.jgrapht.Graph;
import org.jgrapht.alg.scoring.ClosenessCentrality;
import org.jgrapht.alg.scoring.ClusteringCoefficient;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.io.EdgeProvider;
import org.jgrapht.io.GmlImporter;
import org.jgrapht.io.ImportException;
import org.jgrapht.io.VertexProvider;





public class Main {

	public static void main(String[] args) {
		Graph<String,DefaultEdge> grafo;
		grafo = new DefaultUndirectedWeightedGraph<String,DefaultEdge>(DefaultEdge.class);
		importDefaultGraphGML(grafo, "./src/main/resources/antcolony1000.gml");
		
		System.out.println("As 5 melhores formigas que atuam na condução de informação são: " + maiorGrau(grafo));

		System.out.println("As 5 formigas mais influentes são: " + centrais(grafo));
		
		System.out.println("O coeficiente de centralidade do grafo é : " + clusteringCoefficient(grafo));
		
		System.out.println(String.format("O coeficiente de assortatividade do grafo é: %.3f", assortativityCoefficient(grafo)));
		
		
		
		
		
		
		
	}
	
	/**
	 * Metodo responsavel por determinar os cinco verticer com o maior grau do grafo
	 * 
	 * @param grafo Grafo
	 * @return String com os 5 vertices de maior grau
	 */
	
	public static String maiorGrau(Graph<String,DefaultEdge> grafo) {		
		ArrayList<Pair<String, Integer>> list = new ArrayList<>();
		
		for(String v : grafo.vertexSet()) {
			list.add(new Pair<String, Integer>(v, grafo.degreeOf(v)));
		}

		
		Collections.sort(list, new Comparator<Pair<String, Integer>>()
		
		{
			public int compare(Pair<String, Integer> pair1, Pair<String, Integer> pair2) {
				return pair1.getSecond().compareTo(pair2.getSecond()) * -1;
			}
			
		} );
		
		String retorno = "";
		
		for(int i = 0; i < 4; i++) {
			retorno+=list.get(i).getFirst() + " ";
		}
		retorno+=list.get(5).getFirst();
		
		return retorno;
		
		
	}
	
	
	/**
	 * Metodo responsavel por determinar o coeficiente de centralidade do grafo
	 * 
	 * @param grafo Grafo
	 * @return O ClusteringCoefficient do grafo
	 */
	public static String clusteringCoefficient(Graph<String,DefaultEdge> grafo) {
		ClusteringCoefficient <String,DefaultEdge> cluster = new ClusteringCoefficient <> (grafo);
		
		return String.format("%.3f", cluster.getGlobalClusteringCoefficient());
	}
	
	
	/**
	 * Metodo reponsavel por determinar os 5 vertices mais cetrais do grafo
	 * 
	 * @param grafo Grafo
	 * @return String com os 5 vertices mais centrais
	 */
	public static String centrais(Graph<String,DefaultEdge> grafo) {
		ClosenessCentrality<String,DefaultEdge> cc = new ClosenessCentrality<String,DefaultEdge>(grafo);
		Map<String, Double> map = cc.getScores();
		
		ArrayList<Entry<String, Double>> list = new ArrayList<>();
		list.addAll(map.entrySet());
		Collections.sort(list, new Comparator<Entry<String, Double>>()
		
		{
			public int compare(Entry<String, Double> entry1, Entry<String, Double> entry2) {
				return entry1.getValue().compareTo(entry2.getValue()) * -1;
			}
			
		} );
		
		String retorno = "";
		
		for(int i = 0; i < 4; i++) {
			retorno+=list.get(i).getKey()+ " ";
		}
		retorno+= list.get(5).getKey();
		
		return retorno;
		
	}
	
	
    static <V,E> double assortativityCoefficient (Graph <V, E> graph) {
        // from: https://github.com/Infeligo/jgrapht-metrics/blob/master/src/main/java/org/jgrapht/metrics/AssortativityCoefficientMetric.java
    	double edgeCount = graph.edgeSet().size();
        double n1 = 0, n2 = 0, dn = 0;

        for (E e : graph.edgeSet()) {
            int d1 = graph.degreeOf(graph.getEdgeSource(e));
            int d2 = graph.degreeOf(graph.getEdgeTarget(e));

            n1 += d1 * d2;
            n2 += d1 + d2;
            dn += d1 * d1 + d2 * d2;
        }
        n1 /= edgeCount;
        n2 = (n2 / (2 * edgeCount)) * (n2 / (2 * edgeCount));
        dn /= (2 * edgeCount);
        
        return (n1 - n2) / (dn - n2);
    }
	
	
	public static Graph<String,DefaultEdge> importDefaultGraphGML (Graph<String,DefaultEdge> graph, String filename) {
		VertexProvider<String> vp1 = (label, attributes) -> label;
		EdgeProvider<String, DefaultEdge> ep1 = (from, to, label, attributes) -> new DefaultEdge();
		GmlImporter<String, DefaultEdge> gmlImporter = new GmlImporter<>(vp1, ep1);
		try {
			gmlImporter.importGraph(graph, readFile(filename));
		} catch (ImportException e) {
			throw new RuntimeException(e);
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
