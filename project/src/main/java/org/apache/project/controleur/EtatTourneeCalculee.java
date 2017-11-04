package org.apache.project.controleur;

import java.io.File;

import org.apache.project.modele.DemandeDeLivraison;
import org.apache.project.modele.Intersection;
import org.apache.project.modele.Livraison;
import org.apache.project.modele.PlanDeVille;
import org.apache.project.modele.Tournee;
import org.apache.project.vue.FenetrePrincipale;

public class EtatTourneeCalculee extends EtatDefaut{
	
	@Override
	public void ouvrirPlanDeVille(Controleur controleur, PlanDeVille planDeVille, FenetrePrincipale fenetrePrincipale){
		File file = fenetrePrincipale.ouvrirFichierXml(FenetrePrincipale.PDV_FILE_DESCRIPTION, 
				FenetrePrincipale.PDV_FILE_EXTENSION, FenetrePrincipale.PDV_FILEDIALOG_DESCRIPTION);
		if(file == null)
			return;
		controleur.setEtatCourant(controleur.etatInit);
		fenetrePrincipale.clearPlanDeVille();
		fenetrePrincipale.clearLivraison();
		fenetrePrincipale.clearTournee();
		controleur.clearPlanDeVille();
		controleur.clearTournee();
		controleur.clearDemandeDeLivraison();
		controleur.chargerPlanDeVille(file);
	}
	
	@Override
	public void ouvrirDemandeDeLivraison(Controleur controleur, PlanDeVille planDeVille, DemandeDeLivraison demandeDeLivraison, FenetrePrincipale fenetrePrincipale){
		File file = fenetrePrincipale.ouvrirFichierXml(FenetrePrincipale.DDL_FILE_DESCRIPTION,
				FenetrePrincipale.DDL_FILE_EXTENSION, FenetrePrincipale.DDL_FILEDIALOG_DESCRIPTION);
		if(file == null) {
			return;
		}
		controleur.setEtatCourant(controleur.etatPlanCharge);
		fenetrePrincipale.clearLivraison();
		fenetrePrincipale.clearTournee();
		controleur.clearDemandeDeLivraison();
		controleur.clearTournee();
		controleur.chargerDemandeDeLivraison(file);
	}
	
	@Override
	public void ajouterLivraison(Controleur controleur, FenetrePrincipale fenetrePrincipale) {
		controleur.setEtatCourant(controleur.etatAjoutLivraison1);
		fenetrePrincipale.afficherInfo("Veuillez cliquer sur une intersection de la carte");
	}
	
  @Override
	public void intersectionClicked (Controleur controleur, PlanDeVille planDeVille, DemandeDeLivraison demandeDeLivraison, Tournee tournee, FenetrePrincipale fenetrePrincipale, Intersection intersection) {
		fenetrePrincipale.highlightIntersection(intersection);
	}
	
  @Override
	public void livraisonClicked(Controleur controleur, FenetrePrincipale fenetrePrincipale, PlanDeVille plan, Tournee tournee,
			Livraison livraisonPrecedente, ListeDeCommandes commandes) {
		fenetrePrincipale.highlightLivraison(livraisonPrecedente);
  }

	@Override 
	public void supprimerLivraison( Controleur controleur, Tournee tournee,  PlanDeVille planDeVille,  FenetrePrincipale fenetrePrincipale, ListeDeCommandes commandes) {
		
		Livraison livraisonASupprimer = fenetrePrincipale.getSelectedLivraison();
		
		if(livraisonASupprimer == null) {
			controleur.setEtatCourant(controleur.etatTourneeCalculee);
			return;
		}
		
		if(tournee.getLivraisonsOrdonnees().size() == 3) {
			fenetrePrincipale.afficherPopupError("Vous ne pouvez pas suprimer la seule livraison");
			controleur.setEtatCourant(controleur.etatTourneeCalculee);
			return;
		}
		
		commandes.ajouteCommande(new CdeSupprimerLivraison(planDeVille, tournee, livraisonASupprimer));
		
		fenetrePrincipale.clearTournee();
		fenetrePrincipale.afficherTournee(tournee);
	}
	
	@Override
	public void modifierLivraison(Controleur controleur, FenetrePrincipale fenetrePrincipale) {
		Livraison livraisonSelectionnee = fenetrePrincipale.getSelectedLivraison();
		fenetrePrincipale.afficherFenetreModifierLivraison(livraisonSelectionnee);	
		controleur.etatModifierLivraison1.actionEntreeEtatModifierLivraison1(livraisonSelectionnee);
		controleur.setEtatCourant(controleur.etatModifierLivraison1);
	}
	
	@Override
	public void echangerLivraison(Controleur controleur, PlanDeVille planDeVille, FenetrePrincipale fenetrePrincipale, Tournee tournee, int nouveauIndex) {
		Livraison livraisonSelectionnee = fenetrePrincipale.getSelectedLivraison();
		tournee.deplacerLivraison(planDeVille, livraisonSelectionnee, nouveauIndex);
		
		fenetrePrincipale.clearTournee();
		fenetrePrincipale.afficherTournee(tournee);
	}
	
	public void undo(ListeDeCommandes commandes) {
		commandes.undo();
	}
	
	public void redo(ListeDeCommandes commandes) {
		commandes.redo();
	}

}
