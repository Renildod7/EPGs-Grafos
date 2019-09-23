import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

/**
 * Classe responsavel por retornar a conectividade existente entre os "Amigos", 
 * pares de vertices, com uma ligacao de amizade, aresta, entre eles.
 */
public class CirculoAmigos {	
	/**
	 * Metodo Responsavel pela criacao e analise do grafo de Amizades, recebe como entrada
	 * uma tabela de pares de amigos, indicando quem eh amigo direto de quem. O grafo e
	 * construido e o programa retorna os circulos de amizade, que sao definidos pela
	 * conectividade dos vertices do grafo.
	 * 
	 * Para adicionar um vertice que nao eh adjacente a nenhum outro o Pair eh formado 
	 * pelo vertice e uma string vazia.
	 * 
	 * @param paresAmigos
	 * @return
	 */
	public static List <Set <String>> retornaCirculos (List<Pair<String,String>> paresAmigos) {	
		Graph<String, DefaultEdge> grafo = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);

		for(Pair<String, String> par : paresAmigos) {
			String f = par.getFirst();
			String s = par.getSecond();
			if(!f.equals("") && !grafo.containsVertex(f)) {
				grafo.addVertex(f);				
			}
			
			if(!s.equals("") && !grafo.containsVertex(s)) {
				grafo.addVertex(s);
			}

			if(!f.equals("") && !s.equals("") && !grafo.containsEdge(f, s)) {
				
				grafo.addEdge(f, s);				
			}
		}
		
		
		ConnectivityInspector<String, DefaultEdge> conIns = new ConnectivityInspector<String, DefaultEdge>(grafo);
		
		return conIns.connectedSets();
	}
}