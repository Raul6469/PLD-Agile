package org.apache.project.modele;

import java.sql.Time;

/**
 * La classe <tt>Livraison</tt> représente une livraison. Ses principaux
 * attributs sont le lieu où elle doit être effectuée, sa durée (temps de
 * déchargement du camion) et la plage horaire que le livreur doit respecter.
 */
public class Livraison {

	private Intersection lieuDeLivraison;
	private int duree;
	private PlageHoraire plageHoraire;
	private Time heureArrivee;
	private int positionDansTournee = -1;

	/**
	 * Crée une livraison avec un lieu de livraison, une durée de déchargement et
	 * une plage horaire connus.
	 * 
	 * @param lieuDeLivraison
	 *            intersection où se déroule la livraison.
	 * @param dureeLivraison
	 *            temps de déchargement des produits livrés en secondes.
	 * @param plageHoraire
	 *            plage horaire où se déroule la livraison.
	 */
	public Livraison(Intersection lieuDeLivraison, int dureeLivraison, PlageHoraire plageHoraire) {
		this.lieuDeLivraison = lieuDeLivraison;
		this.duree = dureeLivraison;
		this.plageHoraire = plageHoraire;
		this.heureArrivee = null;
	}

	/**
	 * Crée une livraison avec un lieu de livraison et une durée de déchargement
	 * connus, mais sans plage horaire.
	 * 
	 * @param lieuDeLivraison
	 *            intersection où se déroule la livraison.
	 * @param dureeLivraison
	 *            temps de déchargement des produits livrés en secondes.
	 */
	public Livraison(Intersection lieuDeLivraison, int dureeLivraison) {
		this.lieuDeLivraison = lieuDeLivraison;
		this.duree = dureeLivraison;
		this.plageHoraire = null;
		this.heureArrivee = null;
	}

	/**
	 * Crée une livraison avec un lieu de livraison mais sans plage horaire et une
	 * durée de déchargement nulle.
	 * 
	 * @param lieuDeLivraison
	 *            intersection où se déroule la livraison.
	 */
	public Livraison(Intersection lieuDeLivraison) {
		this.lieuDeLivraison = lieuDeLivraison;
		this.duree = 0;
		this.plageHoraire = null;
	}

	/**
	 * @return l'intersection où se déroule la livraison.
	 */
	public Intersection getLieuDeLivraison() {
		return lieuDeLivraison;
	}

	/**
	 * Renvoie le temps de déchargement de la livraison en secondes
	 * 
	 * @return durée de déchargement de la livraison en secondes.
	 */

	public int getDuree() {
		return duree;
	}

	/**
	 * @param duree
	 *            nouvelle durée de la livraison
	 */
	public void setDuree(int duree) {
		this.duree = duree;
	}

	/**
	 * @return la plage horaire de la livraison
	 */
	public PlageHoraire getPlageHoraire() {
		return plageHoraire;
	}

	/**
	 * @param plageHoraire
	 *            nouvelle plage horaire de la livraison
	 */
	public void setPlageHoraire(PlageHoraire plageHoraire) {
		this.plageHoraire = plageHoraire;
	}

	/**
	 * @return l'heure d'arrivée de la livraison
	 */
	public Time getHeureArrivee() {
		return heureArrivee;
	}

	/**
	 * @param heureArrivee
	 */
	public void setHeureArrivee(Time heureArrivee) {
		this.heureArrivee = heureArrivee;
	}

	/**
	 * @param positionDansTournee
	 */
	public void setPositionDansTournee(int positionDansTournee) {
		this.positionDansTournee = positionDansTournee;
	}

	/**
	 * @return
	 */
	public int getPositionDansTournee() {
		return this.positionDansTournee;
	}

	@Override
	public String toString() {
		String livraison_s = "Livraison " + positionDansTournee + "\n";
		livraison_s += "	Heure d'arrivée: " + PlageHoraire.timeToString(heureArrivee) + "\n";
		if (plageHoraire != null) {
			livraison_s += "	Plage horaire: " + PlageHoraire.timeToString(plageHoraire.getDebut()) + " - "
					+ PlageHoraire.timeToString(plageHoraire.getFin());
			if (heureArrivee != null) {
				long avance = plageHoraire.getDebut().getTime() - heureArrivee.getTime();
				if (avance > 0) {
					livraison_s += "\n" + "Avance: " + PlageHoraire.afficherMillisecondesEnHeuresEtMinutes(avance);
				}
			}
		} else {
			livraison_s += "	Pas de plage horaire";
		}
		livraison_s += "\n";
		livraison_s += "	Durée sur place: " + PlageHoraire.afficherMillisecondesEnHeuresEtMinutes(duree * 1000);
		return livraison_s;
	}

}
