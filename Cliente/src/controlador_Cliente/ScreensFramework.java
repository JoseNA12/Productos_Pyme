package controlador_Cliente;

import java.io.IOException;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * @author José Andrés Navarro
 * Clase encargada de manejar las diferentes pantallas de la aplicación cliente
 *
 */
public class ScreensFramework {

	public static String screen1ID = "RealizarPedido";
	public static String screen1File = "/vista_Cliente/FXMLRealizarPedido.fxml";
	public static String screen2ID = "SolicitarPedido";
	public static String screen2File = "/vista_Cliente/FXMLSolicitarPedido.fxml";


	public void iniciarPantallaCliente(Stage pPrimaryStage) throws IOException {

		// Se  cargan todas lo datos de las ventanas
		ScreensController mainContainer = new ScreensController();
		mainContainer.loadScreen(ScreensFramework.screen1ID, ScreensFramework.screen1File);
		mainContainer.loadScreen(ScreensFramework.screen2ID, ScreensFramework.screen2File);

		mainContainer.setScreen(ScreensFramework.screen1ID); // Se define como pantalla principal la 'screen1'

		Group root = new Group();
		root.getChildren().addAll(mainContainer); // Se añade al grupo el conjunto de ventanas cargadas anteriormente
		Scene scene = new Scene(root);
		pPrimaryStage.setScene(scene);
		pPrimaryStage.setResizable(false);
		pPrimaryStage.setTitle("Realizar Pedido");
		pPrimaryStage.show();
	}
}
