package org.apache.project.vue;

import org.apache.project.modele.Intersection;
import org.apache.project.modele.Livraison;
import org.apache.project.modele.Troncon;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class MapGestures {
	private static final double MAX_SCALE = 10.0d;
    private static final double MIN_SCALE = .001d;

    private DragContext sceneDragContext = new DragContext();

    MapDisplay map;
    EcouteurDeMap edm = null;

    public MapGestures(MapDisplay canvas) {
        this.map = canvas;
    }

    public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
        return onMousePressedEventHandler;
    }

    public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
        return onMouseDraggedEventHandler;
    }

    public EventHandler<ScrollEvent> getOnScrollEventHandler() {
        return onScrollEventHandler;
    }

    private EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        public void handle(MouseEvent event) {

            // right mouse button => panning
            if( !event.isPrimaryButtonDown())
                return;
            
            // CLICK TARGET HANDLING
            if(event.getTarget() instanceof Node && edm != null) {
	            Node target = (Node)event.getTarget();
	            Object obj = target.getUserData();
	            if(obj instanceof Livraison) {
	            	edm.onLivraisonClicked((Livraison)obj);
	            } else if (obj instanceof Intersection) {
	            	if((Intersection)obj == map.getEntrepot()){
	            		edm.onEntrepotClicked((Intersection)obj);
	            	}
	            	else {
	            		edm.onIntersectionClicked((Intersection)obj);
	            	}
	            } else if(obj instanceof Troncon) {
	            	edm.onTronconClicked((Troncon)obj);
	            }
        	}

            // DRAGGING HANDLING
            sceneDragContext.mouseAnchorX = event.getSceneX();
            sceneDragContext.mouseAnchorY = event.getSceneY();

            sceneDragContext.translateAnchorX = map.getTranslateX();
            sceneDragContext.translateAnchorY = map.getTranslateY();
        }

    };

    private EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {
        	
            if( !event.isPrimaryButtonDown())
                return;

            map.setTranslateX(sceneDragContext.translateAnchorX + event.getSceneX() - sceneDragContext.mouseAnchorX);
            map.setTranslateY(sceneDragContext.translateAnchorY + event.getSceneY() - sceneDragContext.mouseAnchorY);

            event.consume();
        }
    };

    /**
     * Mouse wheel handler: zoom to pivot point
     */
    private EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<ScrollEvent>() {

        
        public void handle(ScrollEvent event) {

            double delta = 1.2;

            double scale = map.getScale(); // currently we only use Y, same value is used for X
            double oldScale = scale;

            if (event.getDeltaY() < 0)
                scale /= delta;
            else
                scale *= delta;

            scale = clamp( scale, MIN_SCALE, MAX_SCALE);

            double f = (scale / oldScale)-1;

            double dx = (event.getSceneX() - (map.getBoundsInParent().getWidth()/2 + map.getBoundsInParent().getMinX()));
            double dy = (event.getSceneY() - (map.getBoundsInParent().getHeight()/2 + map.getBoundsInParent().getMinY()));

            map.setScale( scale);

            // note: pivot value must be untransformed, i. e. without scaling
            map.setPivot(f*dx, f*dy);

            event.consume();

        }

    };


    public static double clamp( double value, double min, double max) {

        if( Double.compare(value, min) < 0)
            return min;

        if( Double.compare(value, max) > 0)
            return max;

        return value;
    }
    
    public void setEcouteurDeMap(EcouteurDeMap edm) {
		this.edm = edm;
	}
}

class DragContext {

    double mouseAnchorX;
    double mouseAnchorY;

    double translateAnchorX;
    double translateAnchorY;

}
