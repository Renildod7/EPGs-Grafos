import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class VendaImoveisTest2 {
	
	VendaImoveis v1;
	
	HashSet<String> imoveis1;
	HashSet<String> imoveis2;
	HashSet<String> imoveis3;
	HashSet<String> imoveis4;
	HashSet<String> imoveis5;
	HashSet<String> imoveis6;
	
	@Before
	public void preparaTestes () {
		// Criando Dados de Teste
		v1 = new VendaImoveis("./src/main/resources/Vizinhanca2.csv");
		imoveis1 = new HashSet<String>();
		imoveis1.add("C2"); imoveis1.add("C3"); imoveis1.add("C4");
		
		imoveis2 = new HashSet<String>();
		imoveis2.add("A1"); imoveis2.add("C3"); imoveis2.add("B2");
		
		imoveis3 = new HashSet<String>();
		imoveis3.add("C3"); imoveis3.add("B2"); imoveis3.add("C1");
		
		imoveis4 = new HashSet<String>();
		imoveis4.add("C4"); imoveis4.add("B1"); imoveis4.add("A1");
		
		imoveis5 = new HashSet<String>();
		imoveis5.add("C4"); imoveis5.add("A3"); imoveis5.add("A2");
		
		imoveis6 = new HashSet<String>();
		imoveis6.add("K4"); imoveis6.add("H5"); imoveis6.add("F8");
	}
	
	@Test
	public void test1() {
		String imovel = v1.localizaImovel("SUPERMERCADO",imoveis1);
		assertEquals("C3",imovel);

	}
	
	@Test
	public void test2() {
		String imovel = v1.localizaImovel("SHOPPING",imoveis2);
		assertEquals("B2",imovel);

	}
	
	@Test
	public void test3() {
		String imovel = v1.localizaImovel("A2",imoveis3);
		assertEquals("C1",imovel);

	}
	
	@Test
	public void test4() {
		String imovel = v1.localizaImovel("B3",imoveis4);
		assertEquals("C4",imovel);

	}
	
	@Test
	public void test5() {
		String imovel = v1.localizaImovel("C2",imoveis5);
		assertEquals("A2",imovel);

	}
	
	// Ponto de interece nao existe no grafo.
	@Test
	public void test6() {
		String imovel = v1.localizaImovel("H15",imoveis1);
		assertNull(imovel);

	}
	
	
	// Conjundo de imoveis nao existe no grafo.
	@Test
	public void test7() {
		String imovel = v1.localizaImovel("SHOPPING",imoveis6);
		assertNull(imovel);

	}

}
