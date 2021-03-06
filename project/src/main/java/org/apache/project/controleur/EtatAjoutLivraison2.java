package org.apache.project.controleur;

import org.apache.project.modele.DemandeDeLivraison;
import org.apache.project.modele.Intersection;
import org.apache.project.modele.Livraison;
import org.apache.project.modele.PlanDeVille;
import org.apache.project.modele.Tournee;
import org.apache.project.vue.FenetrePrincipale;
import org.apache.project.vue.MapGestures.SelectionMode;

/**
 *
 */
public class EtatAjoutLivraison2 extends EtatDefaut {

	private Intersection intersectionLivraison;

	@Override
	public void livraisonClicked(Controleur controleur, FenetrePrincipale fenetrePrincipale, PlanDeVille plan,
			Tournee tournee, Livraison livraisonPrecedente, ListeDeCommandes commandes) {

		fenetrePrincipale.highlightLivraison(livraisonPrecedente);
		fenetrePrincipale.getListDisplay().disableAddHint();
		controleur.setEtatCourant(controleur.etatAjoutLivraison3);
		Livraison nouvelleLivraison = new Livraison(intersectionLivraison);
		fenetrePrincipale.afficherFenetreAjouterLivraison();
		controleur.etatAjoutLivraison3.actionEntreeEtatAjoutLivraison3(livraisonPrecedente, nouvelleLivraison);
	}

	@Override
	public void intersectionClicked(Controleur controleur, PlanDeVille planDeVille,
			DemandeDeLivraison demandeDeLivraison, Tournee tournee, FenetrePrincipale fenetrePrincipale,
			Intersection intersection) {

		fenetrePrincipale.highlightIntersection(intersection);

		if (intersection != null) {
			controleur.setEtatCourant(controleur.etatAjoutLivraison2);
			controleur.etatAjoutLivraison2.actionEntreeEtatAjoutLivraison2(intersection);
			fenetrePrincipale
					.afficherInfo("Sélectionner livraison précédant la nouvelle livraison, ou nouvelle intersection");
		} else {
			fenetrePrincipale.afficherPopupError("Intersection invalide");
		}
	}

	@Override
	public void annuler(Controleur controleur, FenetrePrincipale fenetrePrincipale) {
		controleur.setEtatCourant(controleur.etatTourneeCalculee);
		fenetrePrincipale.getListDisplay().disableAddHint();
		fenetrePrincipale.afficherInfo("Ajout annulé. Action libre");
		fenetrePrincipale.getMapContainer().setSelectionMode(SelectionMode.Troncon);
		fenetrePrincipale.setVisibleAnnulerButton(false);
	}

	/**
	 * @param intersection
	 */
	protected void actionEntreeEtatAjoutLivraison2(Intersection intersection) {
		this.intersectionLivraison = intersection;
	}

}
