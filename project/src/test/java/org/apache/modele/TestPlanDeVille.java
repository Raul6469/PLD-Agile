package org.apache.modele;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.project.modele.PlanDeVille;
import org.apache.project.xml.Deserialisateur;
import org.apache.project.xml.ExceptionXML;
import org.junit.Test;
import org.xml.sax.SAXException;

public class TestPlanDeVille {

	@Test
	public void TestClear() throws ParserConfigurationException, SAXException, IOException, ExceptionXML {
		//Creation d'un plan
		File planxml = new File("src/test/java/org/apache/modele/fichiers/planLyonPetit.xml");
		PlanDeVille plan = new PlanDeVille();
		Deserialisateur.chargerPlanDeVilleFichier(plan, planxml);
		
		assertFalse(plan.getAllIntersections().isEmpty());
		assertFalse(plan.getAllTroncons().isEmpty());
		
		//Test du clear
		plan.clear();
		assertTrue(plan.getAllIntersections().isEmpty());
		assertTrue(plan.getAllTroncons().isEmpty());
	}

}
