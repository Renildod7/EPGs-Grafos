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
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.GraphMeasurer;
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
		
  	   	// Compute Metrics
  	   	System.out.println("-BETWEENESS CENTRALITY- ");
  	   	BetweennessCentrality <DefaultVertex, RelationshipEdge> bc = 
  	   		new BetweennessCentrality <> (grafo,true);
  	   	printOrderedVertexMeasures (bc.getScores(),0,true);

  	   	System.out.println("\n-ALPHA CENTRALITY- ");
  	   	AlphaCentrality <DefaultVertex, RelationshipEdge> ac = 
  	   		new AlphaCentrality <> (grafo,0.001);
  	   	printOrderedVertexMeasures (ac.getScores(),0,true);
	    
  	   	System.out.println("\n-CLOSENESS CENTRALITY- ");
  	   	ClosenessCentrality <DefaultVertex, RelationshipEdge> cc = 
	   		new ClosenessCentrality <> (grafo);
  	   	printOrderedVertexMeasures (cc.getScores(),0,true);
	    
  	   	System.out.println("\n-HARMONIC CENTRALITY- ");
  	   	HarmonicCentrality <DefaultVertex, RelationshipEdge> hc = 
	   		new HarmonicCentrality <> (grafo);
  	   	printOrderedVertexMeasures (hc.getScores(),0,true);
	    
  	   	ClusteringCoefficient <DefaultVertex,RelationshipEdge> cluster = 
	    	new ClusteringCoefficient <> (grafo);
  	   	System.out.println("\n\nCluster Coefficient: " + cluster.getGlobalClusteringCoefficient());
	    
  	   	System.out.println("\nASSORTATIVITY: " + assortativityCoefficient(grafo));
	   
  	   	if (GraphTests.hasMultipleEdges(grafo)==false) {
  	  	   	double E = (grafo.edgeSet()).size();
  	  	   	double V = (grafo.vertexSet()).size();
  	  	   	double density = (2*E) / (V*(V-1));
  	  	   	System.out.println("\nDENSITY: " + density);   		
  	   	}   
  	   	GraphMeasurer <DefaultVertex, RelationshipEdge> g = new GraphMeasurer <> (grafo);
	   
  	   	System.out.println("\nDIAMETER: " + g.getDiameter());	
  	   	System.out.println("RADIUS: " + g.getRadius());
	   
  	   	System.out.println("CENTER: " + g.getGraphCenter());
  	   	System.out.println("PERIPHERY: " + g.getGraphPeriphery());
  	   	System.out.println("\n-Eccentricity-");
  	   	printOrderedVertexMeasures(g.getVertexEccentricityMap(),0,false);
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
	
    static public <V,E> Set <V> getTreeCentroidPoints (Graph <V,E> graph) {
		// graph must be a Tree
		if (GraphTests.isTree(graph)==false) {
			return new HashSet <> ();
		}
        Set<Entry<V, Double>> set = getCutPointWeights(graph).entrySet();
        List<Entry<V, Double>> list = new ArrayList<Entry<V, Double>>(set);
		list = list.stream().sorted((e1,e2) -> (e1.getValue()).compareTo(e2.getValue())).collect(Collectors.toList());
		double size = list.get(0).getValue();
		return (list.stream().filter(e -> e.getValue().doubleValue() == size).collect(Collectors.toSet())).stream().map(e -> e.getKey()).collect(Collectors.toSet());
    }
    
	static public <V,E> Map <V,Double> getCutPointWeights (Graph <V,E> graph) {
		// graph must be a Tree
		Map <V,Double> weights = new HashMap <> (); 
		if (GraphTests.isTree(graph)==false) {
			return weights;
		}
		BiconnectivityInspector <V, E> insp =
				new BiconnectivityInspector <> (graph);
		DijkstraShortestPath <V, E> paths = 
				new DijkstraShortestPath <> (graph);

		Iterator <V> it1 = insp.getCutpoints().iterator();
		while (it1.hasNext()) {
			V v1 = it1.next();
			Iterator <V> it2 = Graphs.neighborListOf(graph, v1).iterator();
            int size = 0;
			while (it2.hasNext()) {
                V v2 = it2.next();
    			List <E> s = new ArrayList <>();
                Iterator <V> it3 = graph.vertexSet().iterator();
                while (it3.hasNext()) {
                	V v3 = it3.next();
                    if (paths.getPath(v2, v3).getVertexList().contains(v1)==false && graph.degreeOf(v3)==1) {
        	            s.addAll( paths.getPath(v2, v3).getEdgeList().stream().filter(e -> s.contains(e)==false ).collect(Collectors.toList())); 
                    }
                }
                if(s.size() >= size) { 
                	size = s.size()+1; 
                }
			}
            weights.put(v1,new Double(size));			
		}
		return weights;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	static <V> void printOrderedVertexMeasures (Map <V,Double> M, int count, boolean descending) {
		// count representa a quantidade de elementos que devem ser exibidos 
		// em ordem decrescente do score. Se count = 0, ent�o todos ser�o exibidos
        Set<Entry<V, Double>> set = M.entrySet();
        List<Entry<V, Double>> list = new ArrayList<Entry<V, Double>>(set);
        if (descending) {
        	Collections.sort( list, new Comparator<Map.Entry<V, Double>>()
        		{
        			public int compare( Map.Entry<V, Double> o1, Map.Entry<V, Double> o2 ) {
        				return (o2.getValue()).compareTo( o1.getValue() );
        			}
        		} );
        } else {
        	Collections.sort( list, new Comparator<Map.Entry<V, Double>>()
    		{
    			public int compare( Map.Entry<V, Double> o1, Map.Entry<V, Double> o2 ) {
    				return (o1.getValue()).compareTo( o2.getValue() );
    			}
    		} );
        }
        if (count == 0) {
        	count = list.size();
        }
        for (int i = 0; i<count; i++) {
        	Entry<V,Double> e = list.get(i);
        	System.out.print(e.getKey()+": "+ String.format("%.2f",(e.getValue()))+ "; ");
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

}
