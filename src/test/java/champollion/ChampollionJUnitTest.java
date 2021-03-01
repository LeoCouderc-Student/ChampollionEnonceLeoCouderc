package champollion;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class ChampollionJUnitTest {
	Enseignant untel;
	UE uml, java;
	Intervention tdUml;
		
	@BeforeEach
	public void setUp() {
		untel = new Enseignant("untel", "untel@gmail.com");
		uml = new UE("UML");
		java = new UE("Programmation en java");	
		Salle A111 = new Salle("A111", 20);
		tdUml = new Intervention(new Date(), 1, false, 10, TypeIntervention.TD, A111);
	}
	

	@Test
	public void testNouvelEnseignantSansService() {
		assertEquals(0, untel.heuresPrevues(),
                        "Un nouvel enseignant doit avoir 0 heures prévues");
	}
	
	@Test
	public void testAjouteHeures() {
                // 10h TD pour UML
		untel.ajouteEnseignement(uml, 0, 10, 0);

		assertEquals(10, untel.heuresPrevuesPourUE(uml),
                        "L'enseignant doit maintenant avoir 10 heures prévues pour l'UE 'uml'");

                // 20h TD pour UML
                untel.ajouteEnseignement(uml, 0, 20, 0);
                
		assertEquals(10 + 20, untel.heuresPrevuesPourUE(uml),
                         "L'enseignant doit maintenant avoir 30 heures prévues pour l'UE 'uml'");		
	}
	
	@Test
	public void testEnSousService() {
		untel.ajouteEnseignement(uml, 1, 2, 3);
		assertTrue(untel.enSousService());
	}
	
	@Test
	public void testResteAPlanifier() {
		untel.ajouteEnseignement(uml, 0, 5, 0);
		untel.ajouteIntervention(uml, tdUml);
		assertEquals(4, untel.resteAPlanifier(uml, TypeIntervention.TD));
	}
	
}
