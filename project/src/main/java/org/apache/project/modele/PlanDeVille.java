package org.apache.project.modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class PlanDeVille extends Observable {
	
	private Map<Integer,Intersection> intersections;
	private List<Troncon> troncons;
	
	public PlanDeVille() {
		intersections = new HashMap<Integer, Intersection>();
		troncons = new ArrayList<Troncon>();
	}
	
	public void ajouterIntersection(int id, int coordX, int coordY) {
		intersections.put(id, new Intersection(id,coordX,coordY));
	}
	
	public void ajouterTroncon(int numDepart, int numArrivee, String nomRue, int distance) {
		troncons.add(new Troncon(distance,intersections.get(numDepart),intersections.get(numArrivee),nomRue));
	}
	
	public Map<Integer,Intersection> getAllIntersections(){
		return intersections;
	}
	
	public List<Troncon> getAllTroncons(){
		return troncons;
	}
}