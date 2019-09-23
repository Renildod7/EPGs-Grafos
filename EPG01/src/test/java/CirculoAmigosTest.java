import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.alg.util.Pair;
import org.junit.Test;

public class CirculoAmigosTest {

	@Test
	public void test1() {
		ArrayList<Pair<String,String>> listaAmigos = new ArrayList <Pair<String,String>> ();
		listaAmigos.add(new Pair<String, String>("a","b"));
		listaAmigos.add(new Pair<String, String>("c","d"));
		listaAmigos.add(new Pair<String, String>("e","f"));
		listaAmigos.add(new Pair<String, String>("f","g"));
		listaAmigos.add(new Pair<String, String>("i","j"));
		
		// Circulos que devem ser encontrados
		Set <String> c1 = new HashSet<String> (); c1.add("a"); c1.add("b");
		Set <String> c2 = new HashSet<String> (); c2.add("c"); c2.add("d");
		Set <String> c3 = new HashSet<String> (); c3.add("e"); c3.add("f"); c3.add("g"); 
		Set <String> c4 = new HashSet<String> (); c4.add("i"); c4.add("j");
		
		// Calculando c�rculos
		List <Set<String>> circuloAmigos = CirculoAmigos.retornaCirculos(listaAmigos);
		
		assertTrue(circuloAmigos.contains(c1));
		assertTrue(circuloAmigos.contains(c2));
		assertTrue(circuloAmigos.contains(c3));
		assertTrue(circuloAmigos.contains(c4));
		assertEquals(circuloAmigos.size(),4);		
	}
	
	@Test 
	public void test2 () {
		ArrayList<Pair<String,String>> listaAmigos = new ArrayList <Pair<String,String>> ();
		listaAmigos.add(new Pair<String, String>("a","b"));
		listaAmigos.add(new Pair<String, String>("c","b"));
		listaAmigos.add(new Pair<String, String>("e","a"));
		listaAmigos.add(new Pair<String, String>("f","b"));
		listaAmigos.add(new Pair<String, String>("f","a"));		
		
		// Circulos que devem ser encontrados
		Set <String> c1 = new HashSet<String> (); c1.add("a"); c1.add("b"); c1.add("c"); c1.add("e"); c1.add("f");
		
		// Calculando c�rculos
		List <Set<String>> circuloAmigos = CirculoAmigos.retornaCirculos(listaAmigos);
		
		assertTrue(circuloAmigos.contains(c1));
		assertEquals(circuloAmigos.size(),1);	
	}
	
	@Test
	public void test3 () {
		ArrayList<Pair<String,String>> listaAmigos = new ArrayList <Pair<String,String>> ();

		// Calculando c�rculos
		List <Set<String>> circuloAmigos = CirculoAmigos.retornaCirculos(listaAmigos);

		assertEquals(circuloAmigos.size(),0);	
	}
	
	
	
	
	// Testes da Parte 1
	
	@Test
	public void testPrimeiraParte1() {
		ArrayList<Pair<String,String>> listaAmigos = new ArrayList <Pair<String,String>> ();
		listaAmigos.add(new Pair<String, String>("a", "b"));
		listaAmigos.add(new Pair<String, String>("b", "d"));
		listaAmigos.add(new Pair<String, String>("d", "c"));
		listaAmigos.add(new Pair<String, String>("c", "a"));
		
		Set<String> c1 = new HashSet<String>(); 
		c1.add("a"); 
		c1.add("b");
		c1.add("c");
		c1.add("d");
		
		List <Set<String>> circuloAmigos = CirculoAmigos.retornaCirculos(listaAmigos);
		
		assertTrue(circuloAmigos.contains(c1));
		assertEquals(circuloAmigos.size(),1);
		
	}
	
	@Test
	public void testPrimeiraParte2() {
		ArrayList<Pair<String,String>> listaAmigos = new ArrayList <Pair<String,String>> ();
		listaAmigos.add(new Pair<String, String>("a", "b"));
		listaAmigos.add(new Pair<String, String>("c", "d"));

		
		Set<String> c1 = new HashSet<String>(); c1.add("a"); c1.add("b");
		Set<String> c2 = new HashSet<String>(); c2.add("c"); c2.add("d");
		
		List <Set<String>> circuloAmigos = CirculoAmigos.retornaCirculos(listaAmigos);
		
		assertTrue(circuloAmigos.contains(c1));
		assertTrue(circuloAmigos.contains(c2));
		assertEquals(circuloAmigos.size(),2);
		
	}
	
	@Test
	public void testPrimeiraParte3() {
		ArrayList<Pair<String,String>> listaAmigos = new ArrayList <Pair<String,String>> ();
		listaAmigos.add(new Pair<String, String>("a", "b"));
		listaAmigos.add(new Pair<String, String>("b", "d"));
		listaAmigos.add(new Pair<String, String>("c", ""));

		
		Set<String> c1 = new HashSet<String>(); c1.add("a"); c1.add("b"); c1.add("d");
		Set<String> c2 = new HashSet<String>(); c2.add("c");
		
		List <Set<String>> circuloAmigos = CirculoAmigos.retornaCirculos(listaAmigos);
		
		assertTrue(circuloAmigos.contains(c1));
		assertTrue(circuloAmigos.contains(c2));
		assertEquals(circuloAmigos.size(),2);
		
	}
	
	
	
	
	
	// Testes extras

	@Test
	public void test4() {
		ArrayList<Pair<String,String>> listaAmigos = new ArrayList <Pair<String,String>> ();
		listaAmigos.add(new Pair<String, String>("a", "b"));
		listaAmigos.add(new Pair<String, String>("c", ""));
		
		Set<String> c1 = new HashSet<String>(); c1.add("a"); c1.add("b");
		Set<String> c2 = new HashSet<String>(); c2.add("c");
		
		List <Set<String>> circuloAmigos = CirculoAmigos.retornaCirculos(listaAmigos);
		
		assertTrue(circuloAmigos.contains(c1));
		assertTrue(circuloAmigos.contains(c2));
		assertEquals(circuloAmigos.size(),2);
	}
	
	@Test
	public void test5() {
		ArrayList<Pair<String,String>> listaAmigos = new ArrayList <Pair<String,String>> ();
		listaAmigos.add(new Pair<String, String>("a", "b"));
		listaAmigos.add(new Pair<String, String>("", "c"));
		
		Set<String> c1 = new HashSet<String>(); c1.add("a"); c1.add("b");
		Set<String> c2 = new HashSet<String>(); c2.add("c");
		
		List <Set<String>> circuloAmigos = CirculoAmigos.retornaCirculos(listaAmigos);
		
		assertTrue(circuloAmigos.contains(c1));
		assertTrue(circuloAmigos.contains(c2));
		assertEquals(circuloAmigos.size(),2);
	}
	
	@Test
	public void test6() {
		ArrayList<Pair<String,String>> listaAmigos = new ArrayList <Pair<String,String>> ();
		listaAmigos.add(new Pair<String, String>("a", "b"));
		listaAmigos.add(new Pair<String, String>("c", ""));
		listaAmigos.add(new Pair<String, String>("", ""));
		
		
		Set<String> c1 = new HashSet<String>(); c1.add("a"); c1.add("b");
		Set<String> c2 = new HashSet<String>(); c2.add("c");
		
		List <Set<String>> circuloAmigos = CirculoAmigos.retornaCirculos(listaAmigos);
		
		assertTrue(circuloAmigos.contains(c1));
		assertTrue(circuloAmigos.contains(c2));
		assertEquals(circuloAmigos.size(),2);
	}
	
	@Test
	public void test7() {
		ArrayList<Pair<String,String>> listaAmigos = new ArrayList <Pair<String,String>> ();
		listaAmigos.add(new Pair<String, String>("a", ""));
		listaAmigos.add(new Pair<String, String>("b", ""));
		
		
		Set<String> c1 = new HashSet<String>(); c1.add("a");
		Set<String> c2 = new HashSet<String>(); c2.add("b");
		
		List <Set<String>> circuloAmigos = CirculoAmigos.retornaCirculos(listaAmigos);
		
		assertTrue(circuloAmigos.contains(c1));
		assertTrue(circuloAmigos.contains(c2));
		assertEquals(circuloAmigos.size(),2);
	}
	
	@Test
	public void test8() {
		ArrayList<Pair<String,String>> listaAmigos = new ArrayList <Pair<String,String>> ();
		listaAmigos.add(new Pair<String, String>("", ""));
		
				
		List <Set<String>> circuloAmigos = CirculoAmigos.retornaCirculos(listaAmigos);
		
		assertEquals(circuloAmigos.size(),0);
	}
	
	
	@Test
	public void test9() {
		ArrayList<Pair<String,String>> listaAmigos = new ArrayList <Pair<String,String>> ();
		listaAmigos.add(new Pair<String, String>("a","b"));
		listaAmigos.add(new Pair<String, String>("c","d"));
		listaAmigos.add(new Pair<String, String>("e","f"));
		listaAmigos.add(new Pair<String, String>("f","g"));
		listaAmigos.add(new Pair<String, String>("i","j"));
		listaAmigos.add(new Pair<String, String>("k",""));
		listaAmigos.add(new Pair<String, String>("","l"));
		
		

		Set <String> c1 = new HashSet<String> (); c1.add("a"); c1.add("b");
		Set <String> c2 = new HashSet<String> (); c2.add("c"); c2.add("d");
		Set <String> c3 = new HashSet<String> (); c3.add("e"); c3.add("f"); c3.add("g"); 
		Set <String> c4 = new HashSet<String> (); c4.add("i"); c4.add("j");
		Set <String> c5 = new HashSet<String> (); c5.add("k");
		Set <String> c6 = new HashSet<String> (); c6.add("l");

		
		List <Set<String>> circuloAmigos = CirculoAmigos.retornaCirculos(listaAmigos);
		
		assertTrue(circuloAmigos.contains(c1));
		assertTrue(circuloAmigos.contains(c2));
		assertTrue(circuloAmigos.contains(c3));
		assertTrue(circuloAmigos.contains(c4));
		assertTrue(circuloAmigos.contains(c5));
		assertTrue(circuloAmigos.contains(c6));
		assertEquals(circuloAmigos.size(),6);
	}
	
}