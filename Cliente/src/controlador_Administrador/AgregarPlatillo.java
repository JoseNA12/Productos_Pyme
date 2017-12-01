package controlador_Administrador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modelo_Administrador.AgregarAlimentoDAO;
import productos.Alimento;

public class AgregarPlatillo implements Initializable, ControlledScreen_Admi {

	ScreensController_Admi myController;

	@FXML private TextField textField_Nombre;
	@FXML private TextArea textArea_Descripcion;
	@FXML private ChoiceBox<String> choiceBox_Codigos;
	@FXML private ChoiceBox<String> choiceBox_TamaniosRacion;
	@FXML private ChoiceBox<String> choiceBox_PiezasPorPorcion;
	@FXML private ChoiceBox<String> choiceBox_CaloriasPorcion;
	@FXML private ChoiceBox<String> choiceBox_Precio;
	@FXML private ChoiceBox<String> choiceBox_Codigo_1;
	@FXML private ChoiceBox<String> choiceBox_Codigo_2;
	@FXML private ChoiceBox<String> choiceBox_Codigo_3;
	private AgregarAlimentoDAO modeloAgregarAlimento = new AgregarAlimentoDAO();

	ObservableList<String> mostrarCodigos = FXCollections.observableArrayList("ENT", "PRN", "PTR", "BEB");
	ObservableList<String> mostrarTamaniosRacion = FXCollections.observableArrayList("100 gramos", "200 gramos", "300 gramos", "400 gramos",
			"500 gramos", "600 gramos", "700 gramos", "800 gramos", "900 gramos", "1000 gramos", "250 ml", "300 ml", "350 ml",
			"400 ml", "450 ml", "500 ml", "550 ml", "600 ml", "650 ml", "700 ml");
	ObservableList<String> mostrarCantPiezasPorPorcion = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
	ObservableList<String> mostrarCaloriasPorcion = FXCollections.observableArrayList("50 kcal", "70 kcal", "100 kcal", "120 kcal", "150 kcal",
			"170 kcal", "200 kcal", "220 kcal", "250 kcal", "270 kcal", "300 kcal", "320 kcal", "350 kcal", "370 kcal", "400 kcal", "500 kcal",
			"520 kcal", "550 kcal", "570 kcal", "600 kcal", "620 kcal", "650 kcal", "670 kcal", "700 kcal", "720 kcal", "750 kcal", "770 kcal",
			"800 kcal", "820 kcal", "850 kcal", "870 kcal", "900 kcal");
	ObservableList<String> mostrarPrecios = FXCollections.observableArrayList("500.0","700.0","1000.0", "1200.0", "1500.0", "1700.0", "1900.0",
			"2000.0", "2200.0", "2400.0", "2700.0", "3000.0", "3200.0", "3400.0", "3600.0", "3800.0", "4000.0", "4200.0", "4500.0", "4700.0",
			"5000.0", "5200.0", "5500.0", "5800.0", "6000.0");
	ObservableList<String> mostrarCodigoNumero = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");


	public void setScreenParent(ScreensController_Admi screenParent){
		myController = screenParent;
	}

	@FXML
	public void goToFormInicial(ActionEvent event){
		myController.setScreen(ScreensFramework_Admi.screen1ID);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		choiceBox_Codigos.setItems(mostrarCodigos);
		choiceBox_TamaniosRacion.setItems(mostrarTamaniosRacion);
		choiceBox_PiezasPorPorcion.setItems(mostrarCantPiezasPorPorcion);
		choiceBox_CaloriasPorcion.setItems(mostrarCaloriasPorcion);
		choiceBox_Precio.setItems(mostrarPrecios);
		choiceBox_Codigo_1.setItems(mostrarCodigoNumero);
		choiceBox_Codigo_2.setItems(mostrarCodigoNumero);
		choiceBox_Codigo_3.setItems(mostrarCodigoNumero);
	}

	private void mostrarAlerta(String pHeader, String pContent, AlertType miAlerta){
		Alert alert = new Alert(miAlerta);
		alert.setTitle("Atención!");
		alert.setHeaderText(pHeader);
		alert.setContentText(pContent);
		alert.setResizable(false);
		alert.showAndWait();
	}

	private void reestablecerComponentes(){
		textField_Nombre.setText("");
		textArea_Descripcion.setText("");
		choiceBox_Codigos.setValue(null);
		choiceBox_TamaniosRacion.setValue(null);
		choiceBox_PiezasPorPorcion.setValue(null);
		choiceBox_CaloriasPorcion.setValue(null);
		choiceBox_Precio.setValue(null);
		choiceBox_Codigo_1.setValue(null);
		choiceBox_Codigo_2.setValue(null);
		choiceBox_Codigo_3.setValue(null);
	}

	@FXML
	public void agregarAlimento(ActionEvent event){
		if(!textField_Nombre.getText().equals("") && !textArea_Descripcion.getText().equals("")){
			try{
				String nombre = textField_Nombre.getText();
				String descripcion = textArea_Descripcion.getText();
				String codigo = choiceBox_Codigos.getValue();
				String tamanioRacion = choiceBox_TamaniosRacion.getValue();
				String piezasPorPorcion = choiceBox_PiezasPorPorcion.getValue();
				String caloriasPorcion = choiceBox_CaloriasPorcion.getValue();
				String precio = choiceBox_Precio.getValue();
				String codigoAlimento = "-" + choiceBox_Codigo_1.getValue();
				codigoAlimento += choiceBox_Codigo_2.getValue();
				codigoAlimento += choiceBox_Codigo_3.getValue();

				if(!codigo.equals(null) && !tamanioRacion.equals(null) && !piezasPorPorcion.equals(null) &&
						!caloriasPorcion.equals(null) && !precio.equals(null) && codigoAlimento.length() == 4){

					codigo += codigoAlimento;
					Alimento nuevoAlimento = new Alimento(codigo, nombre, descripcion, tamanioRacion,
							piezasPorPorcion, caloriasPorcion, caloriasPorcion, precio);

					boolean agregado = modeloAgregarAlimento.agregarAlimento(nuevoAlimento); // Pregunta al servidor

					if(agregado){
						mostrarAlerta("Se ha registrado con exito el nuevo alimento!",
								"Comer es una de las cosas más satisfactorias que hay ;)\n\n", AlertType.INFORMATION);

						reestablecerComponentes();
						myController.setScreen(ScreensFramework_Admi.screen1ID);

					}
					else{
						mostrarAlerta("Error, no se ha podido agregar el alimento!",
								"Verifique la conexión con el servidor\n\n", AlertType.ERROR);
					}
				}
				else{
					mostrarAlerta("Por favor, complete toda la información restante!",
						"Esto es importante si desea registrar el producto\n\n", AlertType.ERROR);
				}
			}
			catch(Exception e){}
		}
		else{
			mostrarAlerta("Por favor, rellene el campo del nombre y descripción!",
					"Esto es importante si desea registrar el producto\n\n", AlertType.ERROR);
		}
	}
}
