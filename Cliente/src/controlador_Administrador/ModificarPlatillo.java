package controlador_Administrador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import modelo_Administrador.ModificarAlimentoDAO;
import productos.Alimento;

/**
 * Clase controladora de la vista modificar producto.
 * @author Jose Navarro
 *
 */
public class ModificarPlatillo  implements Initializable, ControlledScreen_Admi {

	ScreensController_Admi myController;

	@FXML public TextField textField_Nombre;
	@FXML public TextArea textField_Descripcion;
	@FXML public ChoiceBox<String> choiceBox_Codigos;
	@FXML public ChoiceBox<String> choiceBox_TamaniosRacion;
	@FXML public ChoiceBox<String> choiceBox_PiezasPorPorcion;
	@FXML public ChoiceBox<String> choiceBox_CaloriasPorcion;
	@FXML public ChoiceBox<String> choiceBox_Precio;
	@FXML public ChoiceBox<String> choiceBox_Codigo_1;
	@FXML public ChoiceBox<String> choiceBox_Codigo_2;
	@FXML public ChoiceBox<String> choiceBox_Codigo_3;

	private ModificarAlimentoDAO modeloModificarAlimento = new ModificarAlimentoDAO();
	private static Alimento miAlimento = null;

	ObservableList<String> mostrarCodigos = FXCollections.observableArrayList("ENT", "BEB", "PTR", "PRN");
	ObservableList<String> mostrarTamaniosRacion = FXCollections.observableArrayList("100 gramos", "200 gramos", "300 gramos", "400 gramos",
			"500 gramos", "600 gramos", "700 gramos", "800 gramos", "900 gramos", "1000 gramos", "250 ml", "300 ml", "350 ml",
			"400 ml", "450 ml", "500 ml", "550 ml", "600 ml", "650 ml", "700 ml");
	ObservableList<String> mostrarCantPiezasPorPorcion = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
	ObservableList<String> mostrarCaloriasPorcion = FXCollections.observableArrayList("50 kcal", "70 kcal", "100 kcal", "120 kcal", "150 kcal",
			"170 kcal", "200 kcal", "220 kcal", "250 kcal", "270 kcal", "300 kcal", "320 kcal", "350 kcal", "370 kcal", "400 kcal", "500 kcal",
			"520 kcal", "550 kcal", "570 kcal", "600 kcal", "620 kcal", "650 kcal", "670 kcal", "700 kcal", "720 kcal", "750 kcal", "770 kcal",
			"800 kcal", "820 kcal", "850 kcal", "870 kcal", "900 kcal", "1000 kcal");
	ObservableList<String> mostrarPrecios = FXCollections.observableArrayList("500.0","700.0","1000.0", "1200.0", "1500.0", "1700.0", "1900.0",
			"2000.0", "2200.0", "2400.0", "2700.0", "3000.0", "3200.0", "3400.0", "3600.0", "3800.0", "4000.0", "4200.0", "4500.0", "4700.0",
			"5000.0", "5200.0", "5500.0", "5800.0", "6000.0");
	ObservableList<String> mostrarCodigoNumero = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");


	public void setScreenParent(ScreensController_Admi screenParent){
		myController = screenParent;
	}

	@FXML
	public void goToFormInicial(ActionEvent event){
		limpiarCampos();
		myController.setScreen(ScreensFramework_Admi.screen1ID);
	}

	public static void setAlimento(Alimento pAlimento){
		miAlimento = pAlimento;
	}

	public static Alimento getAlimento(){
		return miAlimento;
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

	public void limpiarCampos(){
		textField_Nombre.clear();
		textField_Descripcion.clear();
		choiceBox_Codigos.setValue(null);
		choiceBox_TamaniosRacion.setValue(null);
		choiceBox_PiezasPorPorcion.setValue(null);
		choiceBox_CaloriasPorcion.setValue(null);
		choiceBox_Precio.setValue(null);
		choiceBox_Codigo_1.setValue(null);
		choiceBox_Codigo_2.setValue(null);
		choiceBox_Codigo_3.setValue(null);
	}

	/**
	 * Encargado de mostrar la información correspondiente del producto seleccionado para editar.
	 * @param event
	 */
	public void mostrarInfoProducto(ActionEvent event){
		textField_Nombre.setText(getAlimento().getNombre());
		textField_Descripcion.setText(getAlimento().getDescripcion());

		String subCodigo = getAlimento().getCodigo().substring(0,3);
		String subCodigo_1 = getAlimento().getCodigo().substring(4,5);
		String subCodigo_2 = getAlimento().getCodigo().substring(5,6);
		String subCodigo_3 = getAlimento().getCodigo().substring(6,7);

		double precioDouble = Double.parseDouble(getAlimento().getPrecio());
		String precioString = String.valueOf(precioDouble);

		choiceBox_Codigos.setValue(subCodigo);
		choiceBox_TamaniosRacion.setValue(getAlimento().getTamanioPorcion());
		choiceBox_PiezasPorPorcion.setValue(getAlimento().getPiezasPorcion());
		choiceBox_CaloriasPorcion.setValue(getAlimento().getCaloriasPorPorcion());
		choiceBox_Precio.setValue(precioString);
		choiceBox_Codigo_1.setValue(subCodigo_1);
		choiceBox_Codigo_2.setValue(subCodigo_2);
		choiceBox_Codigo_3.setValue(subCodigo_3);
	}

	private void mostrarAlerta(String pHeader, String pContent, AlertType miAlerta){
		Alert alert = new Alert(miAlerta);
		alert.setTitle("Atención!");
		alert.setHeaderText(pHeader);
		alert.setContentText(pContent);
		alert.setResizable(false);
		alert.showAndWait();
	}

	/**
	 * Método encargado de modificar el producto seleccionado en las tablas de la interfaz.
	 * Consulta y modifica en el servidor según correspondan los atributos modificados.
	 * @param event
	 */
	@FXML
	public void modificarAlimento(ActionEvent event){
		boolean modifico = false;

		try{
			if(!textField_Nombre.getText().equals("") && !textField_Descripcion.getText().equals("") &&
					!choiceBox_Codigos.getValue().equals("") && !choiceBox_TamaniosRacion.getValue().equals("") &&
					!choiceBox_PiezasPorPorcion.getValue().equals("") && !choiceBox_CaloriasPorcion.getValue().equals("") &&
					!choiceBox_Precio.getValue().equals("") && !choiceBox_Codigo_1.getValue().equals("") && !choiceBox_Codigo_2.getValue().equals("") &&
					!choiceBox_Codigo_3.getValue().equals("")){

				String codigo = choiceBox_Codigos.getValue() +"-"+choiceBox_Codigo_1.getValue()+choiceBox_Codigo_2.getValue()+
						choiceBox_Codigo_3.getValue();

				String caloriasPorPorcion = choiceBox_CaloriasPorcion.getValue();
				String caloriassPorPiezaSubstring = "";
		        int totalPiezasPorcion = Integer.parseInt(choiceBox_PiezasPorPorcion.getValue());

		        int t = 1;
		        while(!caloriasPorPorcion.substring(t-1, t).equals(" ")){
					caloriassPorPiezaSubstring += caloriasPorPorcion.substring(t-1, t);
					t++;
				}

				int caloriasPorPieza_Int = Integer.parseInt(caloriassPorPiezaSubstring);
				String caloriasPorPiezaString1 = Integer.toString(caloriasPorPieza_Int/totalPiezasPorcion);caloriasPorPiezaString1+=" kcal";

				Alimento alimentoEditado = new Alimento(codigo, textField_Nombre.getText(), textField_Descripcion.getText(),
						choiceBox_TamaniosRacion.getValue(), choiceBox_PiezasPorPorcion.getValue(), choiceBox_CaloriasPorcion.getValue(),
						caloriasPorPiezaString1, choiceBox_Precio.getValue());

				modifico = modeloModificarAlimento.modificarAlimento(getAlimento(), alimentoEditado);

				if(modifico){
					mostrarAlerta("Se ha modificado con exito el producto!", "", AlertType.INFORMATION);
					limpiarCampos();
					myController.setScreen(ScreensFramework_Admi.screen1ID);
				}
				else{
					mostrarAlerta("Error al modificar el producto!", "Verifique la conexión con el servidor o quizas hubo un posible\nerror interno en el sevidor\n\n", AlertType.ERROR);
				}
			}
			else{
				mostrarAlerta("Por favor, complete los campos de 'Nombre' y 'Descripción'", "Es importante que rellene estos campos si desea editar el alimento\n\n", AlertType.ERROR);
			}
		}catch(NullPointerException e){
			mostrarAlerta("Por favor, complete todos los campos!", "Es importante que rellene con toda la información los\ncampos disponibles\n\n", AlertType.ERROR);
		}
	}
}
