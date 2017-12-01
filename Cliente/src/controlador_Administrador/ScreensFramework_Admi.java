package controlador_Administrador;

import java.io.IOException;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ScreensFramework_Admi {

	public static String screen1ID = "FormInicial";
	public static String screen1File = "/vista_Administrador/FXMLFormInicial.fxml";
	public static String screen2ID = "AgregarPlatillo";
	public static String screen2File = "/vista_Administrador/FXMLAgregarPlatillo.fxml";
	public static String screen3ID = "ModificarPlatillo";
	public static String screen3File = "/vista_Administrador/FXMLModificarPlatillo.fxml";


	public void iniciarPantallaAdministrador(Stage pPrimaryStage) throws IOException {

		// Se  cargan todas lo datos de las ventanas
		ScreensController_Admi mainContainer = new ScreensController_Admi();
		mainContainer.loadScreen(ScreensFramework_Admi.screen1ID, ScreensFramework_Admi.screen1File);
		mainContainer.loadScreen(ScreensFramework_Admi.screen2ID, ScreensFramework_Admi.screen2File);
		mainContainer.loadScreen(ScreensFramework_Admi.screen3ID, ScreensFramework_Admi.screen3File);

		mainContainer.setScreen(ScreensFramework_Admi.screen1ID); // Se define como pantalla principal la 'screen1'

		Group root = new Group();
		root.getChildren().addAll(mainContainer); // Se añade al grupo el conjunto de ventanas cargadas anteriormente
		Scene scene = new Scene(root);
		pPrimaryStage.setScene(scene);
		pPrimaryStage.setResizable(false);
		pPrimaryStage.setTitle("Labores de mantenimiento");
		pPrimaryStage.show();

	}


}
