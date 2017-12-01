package controlador;

import java.io.IOException;
import controlador_Administrador.ScreensFramework_Admi;
import controlador_Cliente.ScreensFramework;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.Usuario;
import modelo.UsuarioDAO;

/**
 *
 * @author José Navarro
 * Clase encargada de iniciar sesión en la aplicación. Carga los recursos fxml de la pantalla..
 * ..con sus respectivos atributos. Tiene validaciones en las campos de texto; Usuario y Clave.
 * Encargada tambien de mostrar ventanas emergentes con respectivsos avisos.
 *
 */
public class IniciarSesion extends Application  {

	@FXML private Stage ventana_IniciarSesion;
	@FXML private AnchorPane mainLayout;

	@FXML private TextField textField_User;
	@FXML private PasswordField PassField_Pass;
	@FXML private Button button_IniciarSesion;

	private ScreensFramework miCliente = new ScreensFramework();
	private ScreensFramework_Admi miAdministrador = new ScreensFramework_Admi();

	public Stage pantalla_Cliente = new Stage();
	public Stage pantalla_Administrador = new Stage();

	public UsuarioDAO modeloLogin = new UsuarioDAO();
	public Usuario usuario = new Usuario();


	@Override
	public void start(Stage pPrimaryStage) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/vista/FXMLInicioSesion.fxml"));
		Scene scene = new Scene(root);

		ventana_IniciarSesion = pPrimaryStage;
		ventana_IniciarSesion.setTitle("Iniciar Sesión - Pyme JHF");
		ventana_IniciarSesion.setResizable(false);
		ventana_IniciarSesion.setScene(scene);
		ventana_IniciarSesion.show();
	}

	public static void main(String[] args) {

		launch(args);
	}

	public void mostrarAvisoEmergente(String pAviso){

		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Atención!");
		alert.setHeaderText(pAviso);
		alert.showAndWait();
	}

	public void mostrarMensajeBienvenida(String pNombre){

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Atención!");
		alert.setHeaderText("Bienvenido " + textField_User.getText() + " !");
		alert.setContentText("Gracias por preferirnos!\nEsperamos que disfrute de nuestros deliciosos platillos!\n\n");
		alert.showAndWait();
	}

	@FXML
	public boolean verificarCampos(){
		if(textField_User.getText().equals("") || PassField_Pass.getText().equals("")){
			return false;
		}
		else{
			return true;
		}
	}

	/**
	 * Método que establece la comunicacion con la vista y el controlador para enviar una respuesta debida
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void IniciarSesionUsuario(ActionEvent event) throws IOException{ // Envia las credenciales al servidor

		if(!verificarCampos()){

			mostrarAvisoEmergente("\nPor favor, rellene ambos campos de texto!\n\n");
		}
		else{
			String nombreUsuario = textField_User.getText();
			String claveUsuario = PassField_Pass.getText();

			usuario = modeloLogin.verificarCliente(nombreUsuario, claveUsuario);

			if(usuario == null){
				mostrarAvisoEmergente("\nError. Usuario invalido!\n");
			}
			else{
				if(usuario.getTipo().equals("C")){ //Cliente
					miCliente.iniciarPantallaCliente(pantalla_Cliente);
					mostrarMensajeBienvenida(usuario.getNombre());
				}
				else{
					if(usuario.getTipo().equals("A")){ //Administrador
						miAdministrador.iniciarPantallaAdministrador(pantalla_Administrador);
						mostrarMensajeBienvenida(usuario.getNombre());
					}
				}
			}
		}
	}

}
