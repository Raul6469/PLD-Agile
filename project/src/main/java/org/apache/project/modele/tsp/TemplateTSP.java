package org.apache.project.modele.tsp;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 */
public abstract class TemplateTSP implements TSP {

	protected Integer[] meilleureSolution;
	protected int coutMeilleureSolution = 0;
	protected Boolean tempsLimiteAtteint;

	@Override
	public Boolean getTempsLimiteAtteint() {
		return tempsLimiteAtteint;
	}

	@Override
	public void chercheSolution(int tpsLimite, int nbSommets, int[][] cout, int[] duree) {
		tempsLimiteAtteint = false;
		coutMeilleureSolution = Integer.MAX_VALUE;
		meilleureSolution = new Integer[nbSommets];
		ArrayList<Integer> nonVus = new ArrayList<Integer>();
		for (int i = 1; i < nbSommets; i++)
			nonVus.add(i);
		ArrayList<Integer> vus = new ArrayList<Integer>(nbSommets);
		vus.add(0); // le premier sommet visite est 0
		branchAndBound(0, nonVus, vus, 0, cout, duree, System.currentTimeMillis(), tpsLimite);
	}

	@Override
	public Integer getMeilleureSolution(int i) {
		if ((meilleureSolution == null) || (i < 0) || (i >= meilleureSolution.length))
			return null;
		return meilleureSolution[i];
	}

	@Override
	public int getCoutMeilleureSolution() {
		return coutMeilleureSolution;
	}

	/**
	 * Methode devant etre redefinie par les sous-classes de TemplateTSP
	 * 
	 * @param sommetCourant
	 * @param nonVus
	 *            : tableau des sommets restant a visiter
	 * @param cout
	 *            : cout[i][j] = duree pour aller de i a j, avec 0 inférieur ou = i
	 *            inférieur à nbSommets et 0 inférieur ou = j inférieur à nbSommets
	 * @param duree
	 *            : duree[i] = duree pour visiter le sommet i, avec 0 inférieur ou =
	 *            i inférieur à nbSommets
	 * @return une borne inferieure du cout des permutations commencant par
	 *         sommetCourant, contenant chaque sommet de nonVus exactement une fois
	 *         et terminant par le sommet 0
	 */
	protected abstract int bound(Integer sommetCourant, ArrayList<Integer> nonVus, int[][] cout, int[] duree);

	/**
	 * Methode devant etre redefinie par les sous-classes de TemplateTSP
	 * 
	 * @param nonVus
	 *            : tableau des sommets restant a visiter
	 * @return un iterateur permettant d'iterer sur tous les sommets de nonVus
	 */
	protected abstract Iterator<Integer> iterator(ArrayList<Integer> nonVus);

	/**
	 * Methode definissant le patron (template) d'une resolution par separation et
	 * evaluation (branch and bound) du TSP
	 * 
	 * @param sommetCrt
	 *            le dernier sommet visite
	 * @param nonVus
	 *            la liste des sommets qui n'ont pas encore ete visites
	 * @param vus
	 *            la liste des sommets visites (y compris sommetCrt)
	 * @param coutVus
	 *            la somme des couts des arcs du chemin passant par tous les sommets
	 *            de vus + la somme des duree des sommets de vus
	 * @param cout
	 *            : cout[i][j] = duree pour aller de i a j, avec 0 inférieur ou = i
	 *            inférieur à nbSommets et 0 inférieur ou = j inférieur à nbSommets
	 * @param duree
	 *            : duree[i] = duree pour visiter le sommet i, avec 0 inférieur ou =
	 *            i inférieur à nbSommets
	 * @param tpsDebut
	 *            : moment ou la resolution a commence
	 * @param tpsLimite
	 *            : limite de temps pour la resolution
	 */
	void branchAndBound(int sommetCrt, ArrayList<Integer> nonVus, ArrayList<Integer> vus, int coutVus, int[][] cout,
			int[] duree, long tpsDebut, int tpsLimite) {
		if (System.currentTimeMillis() - tpsDebut > tpsLimite) {
			tempsLimiteAtteint = true;
			return;
		}
		if (nonVus.size() == 0) { // tous les sommets ont ete visites
			coutVus += cout[sommetCrt][0];
			if (coutVus < coutMeilleureSolution) { // on a trouve une solution meilleure que meilleureSolution
				vus.toArray(meilleureSolution);
				coutMeilleureSolution = coutVus;
			}
		} else if (coutVus + bound(sommetCrt, nonVus, cout, duree) < coutMeilleureSolution) {
			Iterator<Integer> it = iterator(nonVus);
			while (it.hasNext()) {
				Integer prochainSommet = it.next();
				vus.add(prochainSommet);
				nonVus.remove(prochainSommet);
				branchAndBound(prochainSommet, nonVus, vus,
						coutVus + cout[sommetCrt][prochainSommet] + duree[prochainSommet], cout, duree, tpsDebut,
						tpsLimite);
				vus.remove(prochainSommet);
				nonVus.add(prochainSommet);
			}
		}
	}
}
