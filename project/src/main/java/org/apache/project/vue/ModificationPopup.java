package org.apache.project.vue;

import java.sql.Time;
import java.time.LocalTime;

import org.apache.project.modele.Livraison;
import org.apache.project.modele.PlageHoraire;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Fenêtre permettant à l'utilisateur de modifier une livraison
 */
public class ModificationPopup extends VBox {

	private Button boutonAnnuler;
	private Button boutonValider;

	private Region opaqueLayer;
	private Pane parentPane;

	private Label heureDebLabel;
	private Label heureFinLabel;
	private TimeSpinner dureeSpinner;
	private TimeSpinner heureDebSpinner;
	private TimeSpinner heureFinSpinner;
	private CheckBox checkBox;

	private Label dureeInvalideLabel;
	private Label invalidLabel;

	private GridPane mainLayout;

	public static final String VALIDATE = "Valider";
	public static final String VALIDATE_ID = "validerModificationLivraisonButton";

	public static final String CANCEL = "Annuler";
	public static final String CANCEL_ID = "annulerModificationLivraisonButton";

	/**
	 * Crée un <tt>VBox</tt> contenant tous les widgets nécessaire à la modification
	 * d'une livraison
	 * 
	 * @param livraison
	 *            La livraison à modifier
	 * @param parent
	 *            Le layout parent
	 * @param edb
	 *            L'écouteur de bouton
	 */
	public ModificationPopup(Livraison livraison, Pane parent, EcouteurDeBouton edb) {
		parentPane = parent;
		edb.setModificationPopup(this);
		this.setMaxSize(400, 300);
		this.setSpacing(40);
		this.setPadding(new Insets(20, 40, 20, 40));
		setStyle(
				"-fx-background-color: #FFFFFF; -fx-background-radius: 10px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");

		Label title = new Label("Modifier livraison");
		title.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");
		this.getChildren().add(title);

		StackPane.setMargin(this, new Insets(100, 100, 100, 100));

		mainLayout = new GridPane();
		mainLayout.setHgap(10);
		mainLayout.setVgap(10);
		mainLayout.setAlignment(Pos.CENTER_LEFT);

		dureeInvalideLabel = new Label("Durée invalide");
		dureeInvalideLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
		dureeInvalideLabel.setVisible(false);
		mainLayout.add(dureeInvalideLabel, 1, 0);

		Label dureeLabel = new Label("Durée sur place :");
		dureeSpinner = new TimeSpinner(PlageHoraire.secondesEnLocalTime(livraison.getDuree()));
		mainLayout.add(dureeLabel, 0, 1);
		mainLayout.add(dureeSpinner, 1, 1);

		checkBox = new CheckBox();
		checkBox.setText("Plage horaire");
		checkBox.setSelected(false);
		checkBox.setAlignment(Pos.CENTER_LEFT);
		mainLayout.add(checkBox, 0, 2);

		mainLayout.setAlignment(Pos.CENTER_LEFT);

		invalidLabel = new Label("Plage horaire invalide");
		invalidLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
		invalidLabel.setVisible(false);
		mainLayout.add(invalidLabel, 1, 2);

		mainLayout.setAlignment(Pos.CENTER_LEFT);
		heureDebLabel = new Label("Heure de début:");
		heureFinLabel = new Label("Heure de fin:");

		if (livraison.getPlageHoraire() == null) {
			heureDebSpinner = new TimeSpinner(LocalTime.of(12, 0, 0));
			heureFinSpinner = new TimeSpinner(LocalTime.of(12, 15, 0));
		} else {
			heureDebSpinner = new TimeSpinner(livraison.getPlageHoraire().getDebut().toLocalTime());
			heureFinSpinner = new TimeSpinner(livraison.getPlageHoraire().getFin().toLocalTime());
		}

		mainLayout.add(heureDebLabel, 0, 3);
		mainLayout.add(heureDebSpinner, 1, 3);
		mainLayout.add(heureFinLabel, 0, 4);
		mainLayout.add(heureFinSpinner, 1, 4);

		HBox buttonLayout = new HBox();
		boutonAnnuler = new Button(CANCEL);
		boutonAnnuler.setUserData(CANCEL_ID);
		boutonValider = new Button(VALIDATE);
		boutonValider.setUserData(VALIDATE_ID);
		buttonLayout.getChildren().add(boutonAnnuler);
		buttonLayout.getChildren().add(boutonValider);
		buttonLayout.setAlignment(Pos.CENTER_RIGHT);
		buttonLayout.setSpacing(10);

		getChildren().add(mainLayout);
		getChildren().add(buttonLayout);

		this.disablePlageHoraire(livraison.getPlageHoraire() == null);
		checkBox.setSelected(livraison.getPlageHoraire() != null);

		opaqueLayer = new Region();
		opaqueLayer.setStyle("-fx-background-color: #00000088;");
		opaqueLayer.setVisible(true);

		parent.getChildren().add(opaqueLayer);
		parent.getChildren().add(this);

		checkBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				disablePlageHoraire(!checkBox.isSelected());
			}
		});

		setAlignment(Pos.CENTER);

		ColumnConstraints cc1 = new ColumnConstraints();
		cc1.setPercentWidth(50d);
		mainLayout.getColumnConstraints().add(cc1);

		boutonValider.setOnAction(edb);
		boutonAnnuler.setOnAction(edb);
	}

	/**
	 * Permet d'activer ou de désactiver les widgets de la plage horaire
	 * 
	 * @param disable
	 */
	public void disablePlageHoraire(boolean disable) {
		heureDebLabel.setDisable(disable);
		heureDebSpinner.setDisable(disable);
		heureFinLabel.setDisable(disable);
		heureFinSpinner.setDisable(disable);
	}

	/**
	 * Permet de récupérer l'objet <tt>Time</tt> correspondant à l'heure de début
	 * sélectionnée par l'utilisateur
	 * 
	 * @return l'objet Time ou null si la plage horaire est désactivée
	 */
	@SuppressWarnings("deprecation")
	public Time getNewHeureDeb() {
		if (checkBox.isSelected()) {
			return new Time(heureDebSpinner.getValue().getHour(), heureDebSpinner.getValue().getMinute(),
					heureDebSpinner.getValue().getSecond());
		}
		return null;
	}

	/**
	 * Permet de récupérer l'objet <tt>Time</tt> correspondant à l'heure de fin
	 * sélectionnée par l'utilisateur
	 * 
	 * @return l'objet Time ou null si la plage horaire est désactivée
	 */
	@SuppressWarnings("deprecation")
	public Time getNewHeureFin() {
		if (checkBox.isSelected()) {
			return new Time(heureFinSpinner.getValue().getHour(), heureFinSpinner.getValue().getMinute(),
					heureFinSpinner.getValue().getSecond());
		}
		return null;
	}

	/**
	 * Permet de récupérer la durée (en secondes) sélectionnée par l'utilisateur
	 * 
	 * @return
	 */
	public Integer getNewDuree() {
		return dureeSpinner.getValue().toSecondOfDay();
	}

	/**
	 * Permet de vérifier que les paramètres entrés par l'utilisateur ne sont pas
	 * érronés.
	 * 
	 * @return <tt>true</tt> si les paramètres sont ok, <tt>false</tt> sinon
	 */
	public boolean checkTimeOk() {
		if (!checkBox.isSelected())
			return true;

		LocalTime debut = heureDebSpinner.getValue();
		LocalTime fin = heureFinSpinner.getValue();
		if (fin.isBefore(debut)) {
			setInvalid(true);
			return false;
		}

		// On vérifie que la durée de livraison est inférieure à la longueur de la plage
		// horaire
		LocalTime dureePlageHoraire;
		int diff = fin.getMinute() - debut.getMinute();
		if (diff < 0) {
			dureePlageHoraire = LocalTime.of(fin.getHour() - debut.getHour() - 1, -diff);
		} else {
			dureePlageHoraire = LocalTime.of(fin.getHour() - debut.getHour(), diff);
		}
		if (dureePlageHoraire.compareTo(dureeSpinner.getValue()) < 0) {
			setDureeInvalide(true);
			return false;
		}

		setInvalid(false);
		setDureeInvalide(false);
		return true;
	}

	/**
	 * Affiche un message d'erreur "plage horaire invalide"
	 * 
	 * @param invalid
	 */
	public void setInvalid(boolean invalid) {
		invalidLabel.setVisible(invalid);

		if (invalid) {
			heureDebSpinner.setStyle("-fx-effect: dropshadow(three-pass-box, #FF0000, 10, 0, 0, 0);");
			heureFinSpinner.setStyle("-fx-effect: dropshadow(three-pass-box, #FF0000, 10, 0, 0, 0);");
		} else {
			heureDebSpinner.setStyle("");
			heureFinSpinner.setStyle("");
		}
	}

	/**
	 * Affiche un message d'erreur "durée invalide"
	 * 
	 * @param invalid
	 */
	public void setDureeInvalide(boolean invalid) {
		dureeInvalideLabel.setVisible(invalid);
		if (invalid) {
			dureeSpinner.setStyle("-fx-effect: dropshadow(three-pass-box, #FF0000, 10, 0, 0, 0);");
			dureeSpinner.setStyle("-fx-effect: dropshadow(three-pass-box, #FF0000, 10, 0, 0, 0);");
		} else {
			dureeSpinner.setStyle("");
			dureeSpinner.setStyle("");
		}
	}

	/**
	 * supprime la popup
	 */
	public void selfDestruct() {
		parentPane.getChildren().remove(this);
		parentPane.getChildren().remove(opaqueLayer);
	}

}
