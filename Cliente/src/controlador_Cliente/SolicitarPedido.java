package controlador_Cliente;

import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import modelo.MontoPedido;
import modelo.MontoPedidoDAO;
import modelo_Administrador.RegistrarMontoExtraDAO;
import modelo_Cliente.RegistrarOrdenDAO;
import pedido.Pedido;
import pedido.PedidoExpress;
import pedido.PedidoRecoger;
import pedido.PedidoSitio;
import pedido.TipoPedido;

public class SolicitarPedido implements Initializable, ControlledScreen {

	ScreensController myController;

	@FXML private TextField textField_Nombre;
	@FXML private TextField textField_Celular;
	@FXML private TextArea textArea_Direccion;

	@FXML private Button button_AceptarPedido;
	@FXML private Button button_Volver;
	@FXML private Button button_SolicitarPedido;

	@FXML private ChoiceBox<String> checkBoxTipoPedido;

	@FXML private GridPane pane_IngresarDatos;

	@FXML private Label label_MshIngreseDatos;

	ObservableList<String> mostrarTipoPedido = FXCollections.observableArrayList("Comer en el local", "Para llevar", "Express");

	private static Pedido miPedido = new Pedido();
	private TipoPedido tipoOrdenSelecionado;

	private MontoPedido montosActuales_Pedido = new MontoPedido();
	private MontoPedidoDAO modeloMontosActuales = new MontoPedidoDAO();
	private RegistrarOrdenDAO modeloRegistrarOrden = new RegistrarOrdenDAO();

	public void setScreenParent(ScreensController screenParent){
		myController = screenParent;
	}

	@FXML
	public void goToRealizarPedido(ActionEvent event){
		myController.setScreen(ScreensFramework.screen1ID);
	}

	@FXML void reestablecerComponentes(){
		checkBoxTipoPedido.setDisable(false);
		button_AceptarPedido.setDisable(false);
		button_Volver.setDisable(false);
		pane_IngresarDatos.setDisable(true);
		label_MshIngreseDatos.setDisable(true);
		button_SolicitarPedido.setDisable(true);

		textField_Nombre.setText("");
		textField_Celular.setText("");
		textArea_Direccion.setText("");
		textField_Nombre.setDisable(true);
		textField_Celular.setDisable(true);
		textArea_Direccion.setDisable(true);
	}

	@FXML
	public void goToProcesarPedido(ActionEvent event){ // *Misma pantanlla* //
		try{
			String option = checkBoxTipoPedido.getValue().toString();
			label_MshIngreseDatos.setDisable(false);

			if(option == "Comer en el local"){
				textField_Nombre.setDisable(false);
				tipoOrdenSelecionado = TipoPedido.LOCAL;
			}
			else{
				if(option == "Para llevar"){
					textField_Nombre.setDisable(false);
					textField_Celular.setDisable(false);
					tipoOrdenSelecionado = TipoPedido.LLEVAR;
				}
				else{
					if(option == "Express"){
						textField_Nombre.setDisable(false);
						textField_Celular.setDisable(false);
						textArea_Direccion.setDisable(false);
						tipoOrdenSelecionado = TipoPedido.EXPRESS;
					}
				}
			}

			checkBoxTipoPedido.setDisable(true);
			button_AceptarPedido.setDisable(true);
			button_SolicitarPedido.setDisable(false);
			pane_IngresarDatos.setDisable(false);
			button_Volver.setDisable(true);

		} catch (Exception e){
			//System.out.println(e);
			mostrarAlerta("Por favor, selecione el tipo de pedido que desea realizar!",
					"Esto es importante si desea ordenar sus platillos!\n\n", AlertType.ERROR);
		}


	}

	@Override
	public void initialize(java.net.URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		checkBoxTipoPedido.setItems(mostrarTipoPedido);
	}

	@FXML
	public void realizarPedido(ActionEvent event){
		String nombre; String celular; String direccion;

		montosActuales_Pedido = modeloMontosActuales.cargarMontosActuales_Pedido();

		if(montosActuales_Pedido != null){

			if(tipoOrdenSelecionado == TipoPedido.LOCAL){
				nombre = textField_Nombre.getText();

				if(!nombre.equals("")){

					PedidoSitio miPedido_Sitio = new PedidoSitio(nombre);
					miPedido.setNombreCliente(nombre);
					modeloRegistrarOrden.registrarOrdenServidor(miPedido, "L", nombre);

					RealizarPedido.ReiniciarOrden();

					mostrarAlerta("Se ha completado con exito su pedido!",
							"En máximo 10 minutos llegará su pedido a su mesa!\n\n", AlertType.INFORMATION);

					reestablecerComponentes();
					myController.setScreen(ScreensFramework.screen1ID);
				}
				else{
					mostrarAlerta("Por favor, complete el formulario con su nombre!",
							"Esto es importante para saber de quien es el pedido!\n\n", AlertType.ERROR);
				}
			}
			else{
				if(tipoOrdenSelecionado == TipoPedido.LLEVAR){
					nombre = textField_Nombre.getText();
					celular = textField_Celular.getText();

					if(!nombre.equals("") && !celular.equals("")){
						PedidoRecoger miPedido_Recoger = new PedidoRecoger(nombre, celular);

						double montoFinal = miPedido_Recoger.calcularMontoRecoger(miPedido.getPrecioTotal(), montosActuales_Pedido.getMontoAdicional_ParaLlevar());

						miPedido.setNombreCliente(nombre);
						modeloRegistrarOrden.registrarOrdenServidor(miPedido, "LL", nombre);

						RealizarPedido.ReiniciarOrden();

						mostrarAlerta("Se ha completado con exito su pedido!",
								"En máximo 10 minutos su pedido estará listo!.\nAdemás el precio final del pedido más encargos es: "+montoFinal+"\n\n",
								AlertType.INFORMATION);

						reestablecerComponentes();
						myController.setScreen(ScreensFramework.screen1ID);

					}
					else{
						mostrarAlerta("Por favor, complete el formulario con su nombre y con su número de celular!",
								"Esto es importante para saber de quien es el pedido y comunciar cuando esté listo!\n\n", AlertType.ERROR);
					}
				}
				else{
					if(tipoOrdenSelecionado == TipoPedido.EXPRESS){
						nombre = textField_Nombre.getText();
						celular = textField_Celular.getText();
						direccion = textArea_Direccion.getText();

						if(!nombre.equals("") && !celular.equals("") && !direccion.equals("")){
							PedidoExpress miPedido_Express = new PedidoExpress(nombre, celular, direccion);

							double montoFinal = miPedido_Express.calcularMontoExpress(miPedido.getPrecioTotal(), montosActuales_Pedido.getMontoAdicional_Express());

							miPedido.setNombreCliente(nombre);
							modeloRegistrarOrden.registrarOrdenServidor(miPedido, "E", nombre);

							RealizarPedido.ReiniciarOrden();

							mostrarAlerta("Se ha completado con exito su pedido!",
									"En máximo 20 minutos llegará su pedido a su destino!\nAdemás el precio final del pedido más el envio es: "+montoFinal+"\n\n",
									AlertType.INFORMATION);

							reestablecerComponentes();
							myController.setScreen(ScreensFramework.screen1ID);
						}
						else{
							mostrarAlerta("Por favor, complete el formulario con su nombre, su número de celular y\n"
									+ "la dirección exacta donde desea que le llegue el pedido!",
									"Esto es importante para saber de quien es el pedido y saber donde se debe entregar la orden!\n\n", AlertType.ERROR);
						}
					}
				}
			}
		}
		else{
			mostrarAlerta("Error inesperado!", "Verifique la conexion con el servidor!\n\n", AlertType.ERROR);
		}
	}

	private void mostrarAlerta(String pHeader, String pContent, AlertType miAlerta){
		Alert alert = new Alert(miAlerta);
		alert.setTitle("Atención!");
		alert.setHeaderText(pHeader);
		alert.setContentText(pContent);
		alert.setResizable(false);
		alert.showAndWait();
	}

	public static void setPedido(Pedido pPedido){ // Se asigns desde pedido
		miPedido = pPedido;
	}
}
