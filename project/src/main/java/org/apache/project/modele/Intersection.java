package org.apache.project.modele;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe Intersection représente un point de départ/arrivée d'un
 * <tt>Troncon</tt>. C'est également le lieu où doit se dérouler une
 * <tt>Livraison</tt>.
 */
public class Intersection {

	private Long idNoeud;
	private Long coordX;
	private Long coordY;
	private List<Troncon> tronconsPartants;

	/**
	 * Crée une intersection aux coordonnées (<tt>x</tt>,<tt>y</tt>) avec une id
	 * <tt>id</tt>
	 * 
	 * @param id
	 *            identifiant unique de l'intersection, on ne s'assure pas qu'elle
	 *            soit unique. On considère que le fichier satisfait cette
	 *            condition.
	 * @param x
	 *            coordonnée x de l'intersection
	 * @param y
	 *            coordonnée y de l'intersection
	 */
	public Intersection(Long id, Long x, Long y) {
		this.setIdNoeud(id);
		this.setCoordX(x);
		this.setCoordY(y);
		this.tronconsPartants = new ArrayList<Troncon>();
	}

	/**
	 * @return
	 */
	public Long getIdNoeud() {
		return idNoeud;
	}

	/**
	 * @param idNoeud
	 */
	public void setIdNoeud(Long idNoeud) {
		this.idNoeud = idNoeud;
	}

	/**
	 * @return
	 */
	public Long getCoordX() {
		return coordX;
	}

	/**
	 * @param coordX
	 */
	public void setCoordX(Long coordX) {
		this.coordX = coordX;
	}

	/**
	 * @return
	 */
	public Long getCoordY() {
		return coordY;
	}

	/**
	 * @param coordY
	 */
	public void setCoordY(Long coordY) {
		this.coordY = coordY;
	}

	/**
	 * Ajoute un <tt>Troncon</tt> à la <tt>List</tt> de <tt>Troncon</tt> qui partent
	 * de cette <tt>Intersection</tt>
	 * 
	 * @param unTroncon
	 *            le <tt>Troncon</tt> qu'on ajoute
	 */
	public void ajouterTronconPartant(Troncon unTroncon) {
		this.tronconsPartants.add(unTroncon);
	}

	/**
	 * @return
	 */
	public List<Troncon> getTronconsPartants() {
		return tronconsPartants;
	}

	/**
	 * Cette méthode renvoie le <tt>Troncon</tt> partant de cette
	 * <tt>Intersection</tt> et allant vers l'<tt>Intersection</tt> dont l'id est
	 * donnée en paramètre, mais si ce <tt>Troncon</tt> existe.
	 * 
	 * @param idIntersectionDestination
	 *            id de l'intersection où le tronçon recherché doit aller.
	 * @return si un des <tt>Troncon</tt> partants arrive sur
	 *         l'<tt>Intersection</tt> indiquée, on retourne ce <tt>Troncon</tt>,
	 *         sinon on retourne <tt>null</tt>
	 */
	public Troncon getTronconParDestination(Long idIntersectionDestination) {
		int nombreTroncon = tronconsPartants.size();
		for (int i = 0; i < nombreTroncon; i++) {
			if (tronconsPartants.get(i).getIntersectionArrivee().getIdNoeud().longValue() == idIntersectionDestination
					.longValue()) {
				return tronconsPartants.get(i);
			}
		}
		return null;
	}

	@Override
	public String toString() {
		String intersection_s = "ID: " + idNoeud + " X:" + coordX + " Y:" + coordY;
		return intersection_s;
	}
}
