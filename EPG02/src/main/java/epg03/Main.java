package epg03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.GraphTests;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.BiconnectivityInspector;
import org.jgrapht.alg.scoring.AlphaCentrality;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.alg.scoring.ClosenessCentrality;
import org.jgrapht.alg.scoring.ClusteringCoefficient;
import org.jgrapht.alg.scoring.HarmonicCentrality;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.io.EdgeProvider;
import org.jgrapht.io.GmlImporter;
import org.jgrapht.io.ImportException;
import org.jgrapht.io.VertexProvider;





public class Main {

	public static void main(String[] args) {
		Graph<DefaultVertex,RelationshipEdge> grafo;
		grafo = new DefaultUndirectedWeightedGraph<DefaultVertex,RelationshipEdge>(RelationshipEdge.class);
		importGraphGML(grafo, "./src/main/resources/antcolony1000.gml");
		
		centrais(grafo);
		
		agrupamento(grafo);
		
		System.out.println(assortativityCoefficient(grafo));
		
		
		
	}
	
	public static void agrupamento(Graph<DefaultVertex,RelationshipEdge> grafo) {
		ClusteringCoefficient <DefaultVertex,RelationshipEdge> cluster = new ClusteringCoefficient <> (grafo);
		
		System.out.println(cluster.getGlobalClusteringCoefficient());
	}
	
	public static void centrais(Graph<DefaultVertex,RelationshipEdge> grafo) {
		ClosenessCentrality<DefaultVertex, RelationshipEdge> cc = new ClosenessCentrality<DefaultVertex, RelationshipEdge>(grafo);
		
		double valor = Double.MIN_VALUE;
		DefaultVertex vertice = null;
		Map<DefaultVertex, Double> map = cc.getScores();
		
		ArrayList<Entry<DefaultVertex, Double>> list = new ArrayList<>();
		list.addAll(map.entrySet());
		Collections.sort(list, new Comparator<Entry<DefaultVertex, Double>>()
		
		{
			public int compare(Entry<DefaultVertex, Double> entry1, Entry<DefaultVertex, Double> entry2) {
				return entry1.getValue().compareTo(entry2.getValue()) * -1;
			}
			
		} );
		
		
		for(int i = 0; i < 5; i++) {
			System.out.println(Integer.parseInt(list.get(i).getKey().getId()) +1 );
		}
		
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

	
	
	
	
	
	public static Graph<DefaultVertex,RelationshipEdge> importGraphGML (Graph<DefaultVertex,RelationshipEdge> graph, String filename) {
		VertexProvider<DefaultVertex> vp1 = (label, attributes) -> new DefaultVertex(label, attributes);
		EdgeProvider<DefaultVertex, RelationshipEdge> ep1 = (from, to, label, attributes) -> new RelationshipEdge(from,
				to, attributes);
		GmlImporter<DefaultVertex, RelationshipEdge> gmlImporter = new GmlImporter<>(vp1, ep1);
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
