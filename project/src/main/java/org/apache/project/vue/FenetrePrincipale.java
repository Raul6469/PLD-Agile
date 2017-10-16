package org.apache.project.vue;

import org.apache.project.modele.DemandeDeLivraison;
import org.apache.project.modele.PlanDeVille;
import org.apache.project.modele.Tournee;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FenetrePrincipale extends Application{
	
	MapContainer mapContainer;
	
	public static void main(String[] args) {
        launch(args);
    }
	
    @Override
    public void start(Stage stage) {
    	
    	stage.setTitle("Issou delivery optimiser 2000");
        
    	// layout for the full window
    	HBox layout = new HBox();
    	layout.setStyle("-fx-padding: 10; -fx-spacing: 10;");
    	
    	Scene scene = new Scene(layout, 1024, 500);
    	
    	/////////////////////////////////////////////
    	///// CREATING THE MAP AND ITS CONTROLS /////
    	/////////////////////////////////////////////
    	
    	// layout for the map and its controls buttons
    	VBox mapLayout = new VBox();
    	
    	HBox mapButtonsLayout = new HBox();
    	
    	Button resetPosButton = new Button("Reset map position");
    	Button resetScaleButton = new Button("Reset map zoom");
    	
    	mapButtonsLayout.setAlignment(Pos.CENTER);
    	mapButtonsLayout.setSpacing(10);
    	
    	mapButtonsLayout.getChildren().add(resetPosButton);
    	mapButtonsLayout.getChildren().add(resetScaleButton);
        
        mapContainer = new MapContainer(600,600, scene);
        mapLayout.getChildren().add(mapContainer);
        mapLayout.getChildren().add(mapButtonsLayout);
        mapLayout.setSpacing(10d);
    	
        layout.getChildren().add(mapLayout);
        
		/////////////////////////////////////////////
		///// 	CREATING THE DELIVERY LIST	    /////
		/////////////////////////////////////////////
        
        ListView liste = new ListView();
        liste.getItems().add("Livraison 1");
        liste.getItems().add("Livraison 2");
        liste.getItems().add("Livraison 3");
        liste.getItems().add("Livraison 4");
        
        layout.getChildren().add(liste);
        layout.setHgrow(liste, Priority.ALWAYS);
        liste.setMaxWidth(Double.MAX_VALUE);
        
        
		/////////////////////////////////////////////
		///// 			MAPPING BUTTONS  	    /////
		/////////////////////////////////////////////
        
        resetPosButton.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	        mapContainer.resetMapPosition();
    	    }
    	});
        resetScaleButton.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	        mapContainer.resetMapZoom();
    	    }
    	});
        
        
        stage.setScene(scene);
        stage.show();
    }
    
    public void afficherPlanDeVille(PlanDeVille plan){
    	mapContainer.getMapDisplay().afficherPlanDeVille(plan);
    }
    
    public void afficherDemandeDeLivraison(DemandeDeLivraison livraison) {
    	mapContainer.getMapDisplay().afficherDemandeDeLivraison(livraison);
    }
    
    public void afficherTournee(Tournee tournee) {
    	mapContainer.getMapDisplay().afficherTournee(tournee);
    }
}



