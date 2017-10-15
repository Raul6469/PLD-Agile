package org.apache.project.modele;

import java.util.ArrayList;
import java.util.List;

public class Chemin {
	
	private int duree;
	private Intersection debut;
	private Intersection fin;
	private List<Troncon> listeTroncons;
	
	public Chemin(Intersection debut, Intersection fin, int duree) {
		this.debut=debut;
		this.fin=fin;
		this.duree=duree;
		listeTroncons=new ArrayList<Troncon>();
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public Intersection getDebut() {
		return debut;
	}

	public void setDebut(Intersection debut) {
		this.debut = debut;
	}

	public Intersection getFin() {
		return fin;
	}

	public void setFin(Intersection fin) {
		this.fin = fin;
	}

	public void ajouterTroncon(Troncon unTroncon) {
		this.listeTroncons.add(unTroncon);
	}

}