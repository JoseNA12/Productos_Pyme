package controlador_Cliente;

import java.util.HashMap;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class ScreensController extends StackPane {
				  //String: Pantalla en particular, Node: Direccion de la pantalla
	private HashMap<String, Node> screens = new HashMap<>();

	public ScreensController(){
		super();
	}

	public void addScreen(String name, Node screen) {
	       screens.put(name, screen);
	}

	public Node getScreen(String name){
		return screens.get(name);
	}

	public boolean loadScreen(String name, String resource){
		try{
			FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource)); // Lee el .fxml
			Parent loadScreen = (Parent) myLoader.load();
			ControlledScreen myScreenController = ((ControlledScreen) myLoader.getController());
			myScreenController.setScreenParent(this);
			addScreen(name, loadScreen);
			return true;
		} catch(Exception e){
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean setScreen(final String name){
		if(screens.get(name) != null) { //screen loaded
			final DoubleProperty opacity = opacityProperty();

			if(!getChildren().isEmpty()){
				Timeline fade = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
				        new KeyFrame(new Duration(80), new EventHandler<ActionEvent>() {
				    @Override
				    public void handle(ActionEvent t){
				       getChildren().remove(0); // Se remueve la anterior
				       getChildren().add(0, screens.get(name));
				       Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
				    		   						  new KeyFrame(new Duration(90), new KeyValue(opacity, 1.0)));
				       fadeIn.play(); // Efecto de animacion
				    }
				}, new KeyValue(opacity, 0.0)));
				fade.play();
			}else{
				//no one else been displayed, then just show, o sea primera vez que se despliega
		         setOpacity(0.0);
		         getChildren().add(screens.get(name)); // no one else been displayed, then just show
		         Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
		        								new KeyFrame(new Duration(90), new KeyValue(opacity, 1.0)));
		         fadeIn.play();
			}
			return true;
		}else{
			System.out.println("Screen hasn't been loaded!\n");
	        return false;
		}
	}

	public boolean unloadScreen(String name) {
		if(screens.remove(name) == null) {
			System.out.println("Screen didn't exist");
		       return false;
		}else{
			return true;
		}
	}

}
