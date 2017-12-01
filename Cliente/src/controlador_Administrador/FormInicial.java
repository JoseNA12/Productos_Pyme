package controlador_Administrador;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import controlador_Cliente.VerDetallePlatillo;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import modelo.Mensaje;
import modelo.MontoPedido;
import modelo.Tabla;
import modelo.TablaDAO;
import modelo_Administrador.BitacoraDAO;
import modelo_Administrador.EliminarProductoDAO;
import modelo_Administrador.HistorialPedidosDAO;
import modelo_Administrador.HistorialProductosNuncaPedidosDAO;
import modelo_Administrador.HistorialTopDiez;
import modelo_Administrador.PorcentajesPedidosDAO;
import modelo_Administrador.RegistrarMontoExtraDAO;
import modelo_Administrador.ServidorDAO;
import modelo_Administrador.VisibilidadProductoDAO;
import productos.Alimento;

/**
 * Clase encargada de atenter todas las solicitudes del administrador. Además, controla toda la vista de la pantalla inicial.
 * Las operaciones a atender son: Consultas de: Top-10 y Productos nunca pedidos, Historial de pedidos, Historial del servidor(Bitacora) y
 * el procentaje entre los tres tipos de pedidos. En cuanto a los platillos se encarga de: Agregar, modificar y eliminar producto, cambiar
 * la visibilidad del producto(Disponibles/No disponible) y también, ver detalles del producto.
 * Por último, también brinda la posibilidad al administrador de establecer los cargos adicionales a los pedidos: Para llevar y Express.
 * @author Jose Navarro
 *
 */
public class FormInicial implements Initializable, ControlledScreen_Admi {

	ScreensController_Admi myController;
	VerDetallePlatillo detallePlatillo;

	@FXML private MenuItem menuItem_TopDiez;
	@FXML private MenuItem menuItem_NuncaPedidos;
	@FXML private MenuItem menuItem_menuItem_NuncaPedidos;
	@FXML private MenuItem menuItem_HistorialPedidos;
	@FXML private MenuItem menuItem_menuItem_Bitacora;
	@FXML private MenuItem menuItem_menuItem_PorcentajePedidos;

	@FXML private ChoiceBox<String> choiceBoxMostrarPorTamanio;
	@FXML private ChoiceBox<String> choiceBoxMostrarPorDisponibilidad;
	@FXML private ChoiceBox<String> choiceBox_MontoExtraLlevar;
	@FXML private ChoiceBox<String> choiceBox_MontoExtraExpress;

	@FXML private Button button_EstableverMontoLlevar;
	@FXML private Button button_EstableverMontoExpress;

	@FXML private TextField textField_ProductoActual;

	ObservableList<String> mostrarPorTamanio = FXCollections.observableArrayList("100 gramos", "200 gramos", "300 gramos", "400 gramos",
			"500 gramos", "600 gramos", "700 gramos", "800 gramos", "900 gramos", "1000 gramos");

	ObservableList<String> mostrarDisponibilidad = FXCollections.observableArrayList("Disponible", "No disponible", "Ambos");
	ObservableList<String> mostrarMontoExtraLlevar = FXCollections.observableArrayList("500.0", "1000.0", "1500.0", "2000.0", "2500.0", "3000.0");
	ObservableList<String> mostrarMontoExtraExpress = FXCollections.observableArrayList("1000.0", "1500.0", "2000.0", "2500.0", "3000.0");

	public Tabla tablas = new Tabla();
	public Tabla tablasDisponibles = new Tabla();
	public Tabla tablasNoDisponibles = new Tabla();
	public TablaDAO misTablas = new TablaDAO();
	public BitacoraDAO miBitacora = new BitacoraDAO();
	public HistorialPedidosDAO miHistorial = new HistorialPedidosDAO();
	public RegistrarMontoExtraDAO miMonto = new RegistrarMontoExtraDAO();
	public PorcentajesPedidosDAO miPorcentajes = new PorcentajesPedidosDAO();
	public EliminarProductoDAO miEliminacion = new EliminarProductoDAO();
	public VisibilidadProductoDAO miVisibildad = new VisibilidadProductoDAO();
	public HistorialProductosNuncaPedidosDAO miHistorialProductosNuncaPedidos = new HistorialProductosNuncaPedidosDAO();
	public HistorialTopDiez miTopDiez = new HistorialTopDiez();
	public ServidorDAO modeloServidor = new ServidorDAO();

	// -------Entradas------- //
	@FXML private TableView<Alimento> tablaView_Entradas_A; // A -> Administrador
	@FXML private TableColumn<Alimento, String> tableColumn_Entradas_A;
	@FXML private TableColumn<Alimento, String> tableColumn_Racion_A;
	@FXML private TableColumn<Alimento, String> tableColumn_Calorias_A;
	@FXML private TableColumn<Alimento, String> tableColumn_CaloriasPieza_A;
	public ArrayList<Alimento> listaEntradas = new ArrayList<Alimento>();
	public ArrayList<Alimento> listaEntradas_Disponibles = new ArrayList<Alimento>();
	public ArrayList<Alimento> listaEntradas_NoDisponibles = new ArrayList<Alimento>();
	ObservableList<Alimento> alimentos_Entradas;
	public int posicionEntradaEnTabla;

	// -------Platos fuertes------- //
	@FXML private TableView<Alimento> tablaView_PlatosFuertes_A;
	@FXML private TableColumn<Alimento, String> tableColumn_Fuerte_A;
	@FXML private TableColumn<Alimento, String> tableColumn_FuerteRacion_A;
	@FXML private TableColumn<Alimento, String> tableColumn_FuerteCalorias_A;
	@FXML private TableColumn<Alimento, String> tableColumn_FuerteCaloriasPieza_A;
	public ArrayList<Alimento> listaFuertes = new ArrayList<Alimento>();
	public ArrayList<Alimento> listaFuertes_Disponibles = new ArrayList<Alimento>();
	public ArrayList<Alimento> listaFuertes_NoDisponibles = new ArrayList<Alimento>();
	ObservableList<Alimento> alimentos_Fuertes;
	public int posicionFuerteEnTabla;

	// -------Postres------- //
	@FXML private TableView<Alimento> tablaView_Postres_A;
	@FXML private TableColumn<Alimento, String> tableColumn_Postre_A;
	@FXML private TableColumn<Alimento, String> tableColumn_PostreRacion_A;
	@FXML private TableColumn<Alimento, String> tableColumn_PostreCalorias_A;
	@FXML private TableColumn<Alimento, String> tableColumn_PostreCaloriasPieza_A;
	public ArrayList<Alimento> listaPostres = new ArrayList<Alimento>();
	public ArrayList<Alimento> listaPostres_Disponibles = new ArrayList<Alimento>();
	public ArrayList<Alimento> listaPostres_NoDisponibles = new ArrayList<Alimento>();
	ObservableList<Alimento> alimentos_Postres;
	public int posicionPostreEnTabla;

	// -------Bebidas------- //
	@FXML private TableView<Alimento> tablaView_Bebidas_A;
	@FXML private TableColumn<Alimento, String> tableColumn_Bebida_A;
	@FXML private TableColumn<Alimento, String> tableColumn_BebidaRacion_A;
	@FXML private TableColumn<Alimento, String> tableColumn_BebidaCalorias_A;
	@FXML private TableColumn<Alimento, String> tableColumn_BebidaCaloriasPieza_A;;
	public ArrayList<Alimento> listaBebidas = new ArrayList<Alimento>();
	public ArrayList<Alimento> listaBebidas_Disponibles = new ArrayList<Alimento>();
	public ArrayList<Alimento> listaBebidas_NoDisponibles = new ArrayList<Alimento>();
	ObservableList<Alimento> alimentos_Bebidas;
	public int posicionBebidaEnTabla;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		choiceBoxMostrarPorTamanio.setItems(mostrarPorTamanio);
		choiceBoxMostrarPorDisponibilidad.setItems(mostrarDisponibilidad);
		choiceBoxMostrarPorDisponibilidad.setValue("Ambos");
		choiceBox_MontoExtraLlevar.setItems(mostrarMontoExtraLlevar);
		choiceBox_MontoExtraExpress.setItems(mostrarMontoExtraExpress);

		cargarTablas();
	}

	public void setScreenParent(ScreensController_Admi screenParent) {
		myController = screenParent;
	}

	@FXML
	public void goToAgregarPlatillo(ActionEvent event){
		myController.setScreen(ScreensFramework_Admi.screen2ID);
	}

	/**
	 * Encargado de cambiar de pantalla a Modificar platillo.
	 * @param event
	 */
	@FXML
	public void goToModificarPlatillo(ActionEvent event){

		if(!textField_ProductoActual.getText().equals("")){

			Alimento miAlimento = new Alimento();
			miAlimento = BuscarProducto(textField_ProductoActual.getText());

			ModificarPlatillo.setAlimento(miAlimento);

			myController.setScreen(ScreensFramework_Admi.screen3ID);
		}
		else{
			mostrarAlerta("Por favor, seleccione un alimento de la tabla!",
					"Debe escoger un alimento para poder editar su información\n\n", AlertType.ERROR);
		}
	}

	/**
	 * Muestra un mensaje al usurio.
	 * @param pHeader
	 * @param pContent
	 * @param miAlerta
	 */
	private void mostrarAlerta(String pHeader, String pContent, AlertType miAlerta){
		Alert alert = new Alert(miAlerta);
		alert.setTitle("Atención!");
		alert.setHeaderText(pHeader);
		alert.setContentText(pContent);
		alert.setResizable(false);
		alert.showAndWait();
	}

    @FXML
    public void recargarTablas(ActionEvent event){
    	solicitarTablas();
    }

    /**
     * Encargado de mostrar o filtrar los productos por visibilidad y los almacenados en el servidor.
     * @param event
     */
    @FXML
    public void mostrarTablasPorDisponibilidad(ActionEvent event){
    	if(choiceBoxMostrarPorDisponibilidad.getValue().equals("Ambos")){
    		solicitarTablas();
    	}
    	else{
    		if(choiceBoxMostrarPorDisponibilidad.getValue().equals("Disponible")){
    			solicitarTablas_ProductosVisibles();
        	}
    		else{
    			if(choiceBoxMostrarPorDisponibilidad.getValue().equals("No disponible")){
        			solicitarTablas_ProductosNoVisibles();
            	}
    		}
    	}
    }

    /**
     * Recarga o refresca la vista de las tablas de la interfaz.
     */
    public void solicitarTablas(){
    	listaEntradas.clear();
		alimentos_Entradas.clear();

		listaFuertes.clear();
		alimentos_Fuertes.clear();

		listaPostres.clear();
		alimentos_Postres.clear();

		listaBebidas.clear();
		alimentos_Bebidas.clear();

    	cargarTablas();
    }

    /**
     * Recarga o refresca la vista de las tablas de la interfaz con los productos disponibles.
     */
    public void solicitarTablas_ProductosVisibles(){
    	listaEntradas_Disponibles.clear();
		alimentos_Entradas.clear();

		listaFuertes_Disponibles.clear();
		alimentos_Fuertes.clear();

		listaPostres_Disponibles.clear();
		alimentos_Postres.clear();

		listaBebidas_Disponibles.clear();
		alimentos_Bebidas.clear();

		cargarTablas_ProductosDisponibles();
    }

    /**
     * Recarga o refresca la vista de las tablas de la interfaz con los productos no disponibles.
     */
    public void solicitarTablas_ProductosNoVisibles(){
    	listaEntradas_NoDisponibles.clear();
		alimentos_Entradas.clear();

		listaFuertes_NoDisponibles.clear();
		alimentos_Fuertes.clear();

		listaPostres_NoDisponibles.clear();
		alimentos_Postres.clear();

		listaBebidas_NoDisponibles.clear();
		alimentos_Bebidas.clear();

		cargarTablas_ProductosNoDisponibles();
    }

    /**
     * Busca un alimento en los ArrayList para obterner los atributos y realizar las debidas operaciones según el caso.
     * @param pNombreProducto
     * @return
     */
	private Alimento BuscarProducto(String pNombreProducto){
		Alimento miAlimento = null;

		for(Alimento entrada: listaEntradas){
			if(entrada.getNombre().equals(pNombreProducto)){
				miAlimento = entrada;
			}
		}

		for(Alimento platoFuerte: listaFuertes){
			if(platoFuerte.getNombre().equals(pNombreProducto)){
				miAlimento = platoFuerte;
			}
		}

		for(Alimento postre: listaPostres){
			if(postre.getNombre().equals(pNombreProducto)){
				miAlimento = postre;
			}
		}
		for(Alimento bebida: listaBebidas){
			if(bebida.getNombre().equals(pNombreProducto)){
				miAlimento = bebida;
			}
		}
		return miAlimento;
	}

	/**
	 * Establece el cargo extra al pedido para llevar. Obtiene el valor del ChoiceBox.
	 * @param event
	 */
	@FXML
	public void establecerMontoLlevar(ActionEvent event){
		try{
			String monto = choiceBox_MontoExtraLlevar.getValue();

			if(!monto.equals(null)){
				MontoPedido respuestaServer = miMonto.establecerMonto(monto, "LL");

				if(!respuestaServer.equals(null)){
					mostrarAlerta("\nSe ha establecido el monto satisfactoriamente!",
							"El cargo extra asigando al pedido para llevar fue de: "+monto+".\n\n", AlertType.INFORMATION);
				}
			}
		}
		catch(NullPointerException e){
			mostrarAlerta("\nPor favor, seleccione la cantidad que desea establecer!",
					"No es posible establecer el monto si no ha selecionado la cantidad.\n\n", AlertType.ERROR);
		}
	}

	/**
	 * Establece el cargo extra al pedido express. Obtiene el valor del ChoiceBox.
	 * @param event
	 */
	@FXML
	public void establecerMontoExpress(ActionEvent event){
		try{
			String monto = choiceBox_MontoExtraExpress.getValue();

			if(!monto.equals(null)){
				MontoPedido respuestaServer = miMonto.establecerMonto(monto, "E");

				if(respuestaServer != null){
					mostrarAlerta("\nSe ha establecido el monto satisfactoriamente!",
							"El cargo extra asigando al pedido express fue de: "+monto+".\n\n", AlertType.INFORMATION);
				}
			}
		}
		catch(NullPointerException e){
			mostrarAlerta("\nPor favor, seleccione la cantidad que desea establecer!",
					"No es posible establecer el monto si no ha selecionado la cantidad.\n\n", AlertType.ERROR);
		}
	}

	/**
	 * Aplica un ordenamiento a las tablas de la interfaz por gramos según lo escogido por el usuario.
	 * @param event
	 */
	@FXML
	public void aplicarOrdenamientoTablas(ActionEvent event){
		ArrayList<Alimento> alimentoMostradoEscogido_Entradas = new ArrayList<Alimento>();
		ArrayList<Alimento> alimentoTemp_Entradas = new ArrayList<Alimento>();
		ArrayList<Alimento> alimentoMostradoEscogido_Fuertes = new ArrayList<Alimento>();
		ArrayList<Alimento> alimentoTemp_Fuertes = new ArrayList<Alimento>();
		ArrayList<Alimento> alimentoMostradoEscogido_Postres = new ArrayList<Alimento>();
		ArrayList<Alimento> alimentoTemp_Postres = new ArrayList<Alimento>();
		ArrayList<Alimento> alimentoMostradoEscogido_Bebidas = new ArrayList<Alimento>();
		ArrayList<Alimento> alimentoTemp_Bebidas = new ArrayList<Alimento>();

		String mostradoEscogido = null;

		try{
			mostradoEscogido = choiceBoxMostrarPorTamanio.getValue();

			if(!mostradoEscogido.equals(null)){
				int i = 0;

				// ------ Entradas ------ //
				while(i != listaEntradas.size()){
					if(listaEntradas.get(i).getTamanioPorcion().equals(mostradoEscogido)){
						alimentoMostradoEscogido_Entradas.add(listaEntradas.get(i));
					}
					else{
						alimentoTemp_Entradas.add(listaEntradas.get(i));
					}
					i++;
				}i = 0;

				// ------ Principales ------ //
				while(i != listaFuertes.size()){
					if(listaFuertes.get(i).getTamanioPorcion().equals(mostradoEscogido)){
						alimentoMostradoEscogido_Fuertes.add(listaFuertes.get(i));
					}
					else{
						alimentoTemp_Fuertes.add(listaFuertes.get(i));
					}
					i++;
				}i = 0;

				// ------ Postres ------ //
				while(i != listaPostres.size()){
					if(listaPostres.get(i).getTamanioPorcion().equals(mostradoEscogido)){
						alimentoMostradoEscogido_Postres.add(listaPostres.get(i));
					}
					else{
						alimentoTemp_Postres.add(listaPostres.get(i));
					}
					i++;
				}i = 0;

				// ------ Bebidas ------ //
				while(i != listaBebidas.size()){
					if(listaBebidas.get(i).getTamanioPorcion().equals(mostradoEscogido)){
						alimentoMostradoEscogido_Bebidas.add(listaBebidas.get(i));
					}
					else{
						alimentoTemp_Bebidas.add(listaBebidas.get(i));
					}
					i++;
				}i = 0;

				alimentoMostradoEscogido_Entradas.addAll(alimentoTemp_Entradas);
				alimentos_Entradas.clear();
				alimentos_Entradas.addAll(alimentoMostradoEscogido_Entradas);

				alimentoMostradoEscogido_Fuertes.addAll(alimentoTemp_Fuertes);
				alimentos_Fuertes.clear();
				alimentos_Fuertes.addAll(alimentoMostradoEscogido_Fuertes);

				alimentoMostradoEscogido_Postres.addAll(alimentoTemp_Postres);
				alimentos_Postres.clear();
				alimentos_Postres.addAll(alimentoMostradoEscogido_Postres);

				alimentoMostradoEscogido_Bebidas.addAll(alimentoTemp_Bebidas);
				alimentos_Bebidas.clear();
				alimentos_Bebidas.addAll(alimentoMostradoEscogido_Bebidas);

			}
		}
		catch(NullPointerException e){
			mostrarAlerta("\nPor favor, seleccione el tipo de mostrado que desee!",
					"Para mostrar las tablas por tamaño debe seleccionar la clasificación por gramos.\n\n", AlertType.WARNING);
		}
		catch(Exception o){}

	}

	/**
	 * Muesta una pequeña ventana con la información del producto seleccioado.
	 * @param event
	 */
	@FXML
	public void verDetallesPlatillo(ActionEvent event){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Detalles del Platillo");

		if(detallePlatillo == null){
			alert.setHeaderText("Por favor, seleccione un alimento primero!");
		}
		else{
			alert.setHeaderText(detallePlatillo.getNombre() + " - "+detallePlatillo.getCodigo());
			alert.setContentText("\n- Tamaño: "+detallePlatillo.getTamanio()+
				"\n- Piezas por porción: "+detallePlatillo.getPiezasPorcion()+
				"\n- Calorias por porción: "+detallePlatillo.getCaloriasPorcion()+
				"\n- Calorias por pieza: "+detallePlatillo.getCaloriasPieza()+
				"\n- Precio: "+detallePlatillo.getPrecio()+
				"\n- Descripción: "+detallePlatillo.getDescripcion()+"\n\n\n");
		}
		alert.showAndWait();
	}

	/**
	 * Muestra el historial de los pedido realizados hasta el momento. Muestra el cliente, sus productos y el precio.
	 * @param event
	 */
	@FXML
	public void consultarHistorialPedidos(ActionEvent event){

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Consulta");
		alert.setHeaderText("Historial de productos pedidos");
		alert.setContentText("La lista de productos pedidos hasta el momento es la siguiente:");
		TextArea textArea;
		String historial = miHistorial.cargarHistorialPedidos();

		if(historial.equals(null)){
			textArea = new TextArea("Error al cargar la bitacora. Verifique la conexion con el servidor");
		}
		else{
			textArea = new TextArea(historial.toString());
		}

		textArea.setEditable(false);
		textArea.setWrapText(true);
		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);

		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);
		GridPane expContent = new GridPane();

		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(textArea, 0, 1);

		alert.getDialogPane().setExpandableContent(expContent);
		alert.showAndWait();
	}

	/**
	 * Muetra todos los comandos recibidos hasta el momento por parte del cliente y administrador.
	 * @param event
	 */
	@FXML
	public void consultarBitacoraServidor(ActionEvent event){

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Consulta");
		alert.setHeaderText("Bitacora del servidor");
		String bitacora = miBitacora.cargarBitacora(); //Consulta a servidor

		alert.setContentText("La lista de comandos recibidos al servidor hasta el momento es la siguiente:");
		TextArea textArea;

		if(bitacora.equals(null)){
			textArea = new TextArea("Error al cargar la bitacora. Verifique la conexion con el servidor");
		}
		else{
			textArea = new TextArea(bitacora.toString());
		}

		textArea.setEditable(false);
		textArea.setWrapText(true);
		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);

		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);
		GridPane expContent = new GridPane();

		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(textArea, 0, 1);

		alert.getDialogPane().setExpandableContent(expContent);
		alert.showAndWait();
	}

	/**
	 * Muestra los procentajes entre los tres tipos de pedidos. Sitio, llevar y Express.
	 * El calculo lo realiza el servidor.
	 * @param event
	 */
	@FXML
	public void consultarPorcentajePedidos(ActionEvent event){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Consulta");
		alert.setHeaderText("Los porcentajes de los pedidos hasta el momento\nson los siguientes:");

		String porcentajes = miPorcentajes.cargarPorcentajes();//Consulta a servidor

		if(porcentajes.equals("")){
			porcentajes = ("Error al cargar la lista. Verifique la conexion con el servidor");
		}

		alert.setContentText(porcentajes + "\n\n");
		alert.showAndWait();
	}

	/**
	 * Muetra una ventana con una lista de los productos diez más pedidos hasta el momento.
	 * @param event
	 */
	@FXML
	public void consultarTopDiez(ActionEvent event){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Consulta");
		alert.setHeaderText("10 productos más vendidos");
		alert.setContentText("La lista de productos más vendidos hasta el momento es la siguiente:");

		String topDiez = miTopDiez.consultarListaTopDiez();

		TextArea textArea;

		if(topDiez.equals("")){
			textArea = new TextArea("Sin pedidos.");
		}
		else{
			textArea = new TextArea(topDiez);
		}

		textArea.setEditable(false);
		textArea.setWrapText(true);
		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);

		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);
		GridPane expContent = new GridPane();

		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(textArea, 0, 1);

		alert.getDialogPane().setExpandableContent(expContent);
		alert.showAndWait();

	}

	/**
	 * Muestra una lista de todos los productos que no se han pedido hasta el momento.
	 * @param event
	 */
	@FXML
	public void consultarProductosNuncaPedidos(ActionEvent event){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Consulta");
		alert.setHeaderText("Productos nunca vendidos");
		alert.setContentText("La lista de productos nunca vendidos hasta el momento es la siguiente:");

		String mensaje = miHistorialProductosNuncaPedidos.consultarListaProductosNuncaPedidos(); //Consulta a servidor

		if(mensaje.equals(null)){
			mensaje = ("Error al cargar la lista. Verifique la conexion con el servidor");
		}

		TextArea textArea = new TextArea(mensaje);

		textArea.setEditable(false);
		textArea.setWrapText(true);
		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);

		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);
		GridPane expContent = new GridPane();

		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(textArea, 0, 1);

		alert.getDialogPane().setExpandableContent(expContent);
		alert.showAndWait();
	}

	/**
	 * Método encargado de mandar a eliminar un producto deseado por el administrador.
	 * @param event
	 */
	public void eliminarAlimento(ActionEvent event){
		boolean seElimino = false;
		Alimento alimentoEliminar = new Alimento();

		if(!textField_ProductoActual.getText().equals("")){

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Atención");
			alert.setHeaderText("Estás seguro que desea eliminar el producto?");
			alert.setContentText("Producto: " + textField_ProductoActual.getText()+"\n\n");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
			    alimentoEliminar = BuscarProducto(textField_ProductoActual.getText());

				seElimino = miEliminacion.eliminarProducto(alimentoEliminar);

				if(seElimino){
					solicitarTablas(); textField_ProductoActual.setText("");
					mostrarAlerta("Se ha eliminado satisfactoriamente el producto",
							"Se eliminó: "+alimentoEliminar.getNombre()+"\n\n", AlertType.INFORMATION);
				}
				else{
					mostrarAlerta("Error al eliminar el producto!",
							"Verifique la conexión con el servidor o actualize la tabla para \nverificar la existencia del producto\n\n",
							AlertType.ERROR);
				}
			}
		}
		else{
			mostrarAlerta("Por favor, seleccione el producto que desea eliminar!",
					"Esto es importante para conocer cual producto eliminar\n\n", AlertType.ERROR);
		}
	}

	@FXML
	public void habilitarAlimento(ActionEvent event){
		Alimento alimentoHabilitar = new Alimento();

		if(!textField_ProductoActual.getText().equals("")){

			alimentoHabilitar = BuscarProducto(textField_ProductoActual.getText());

			if(alimentoHabilitar != null){

				Mensaje respuesta = miVisibildad.establecerVisible(alimentoHabilitar);

				if(respuesta != null){
					mostrarAlerta(respuesta.getContenido(), "Producto: "+alimentoHabilitar.getNombre(), AlertType.INFORMATION);
				}
				else{
					mostrarAlerta("Error, verifique la conexión con el servidor!", "Posiblemente se deba a un error interno\n\n",
							AlertType.ERROR);
				}

			}
			else{
				mostrarAlerta("Error, verifique que el alimento se encuentra disponible.!",
						"Refresque o recargue las tablas y repita la acción\n\n", AlertType.ERROR);
			}
		}
		else{
			mostrarAlerta("Por favor, seleccione el producto que desea cambiar\na visible!",
					"Esto es importante para conocer cual producto modificar\n\n", AlertType.ERROR);
		}
	}

	@FXML
	public void inhabilitarAlimento(ActionEvent event){
		Alimento alimentoHabilitar = new Alimento();

		if(!textField_ProductoActual.getText().equals("")){

			alimentoHabilitar = BuscarProducto(textField_ProductoActual.getText());

			if(alimentoHabilitar != null){

				Mensaje respuesta = miVisibildad.establecerNoVisible(alimentoHabilitar);

				if(respuesta != null){
					mostrarAlerta(respuesta.getContenido(), "Producto: "+alimentoHabilitar.getNombre(), AlertType.INFORMATION);
				}
				else{
					mostrarAlerta("Error, verifique la conexión con el servidor!", "Posiblemente se deba a un error interno\n\n",
							AlertType.ERROR);
				}

			}
			else{
				mostrarAlerta("Error, verifique que el alimento se encuentra disponible.!",
						"Refresque o recargue las tablas y repita la acción\n\n", AlertType.ERROR);
			}
		}
		else{
			mostrarAlerta("Por favor, seleccione el producto que desea cambiar a\nno visible!",
					"Esto es importante para conocer cual producto modificar\n\n", AlertType.ERROR);
		}
	}

	/**
	 * Método encargado de registrar todos los productos en los ArrayList locales provenientes del servidor.
	 * @param tabla
	 */
	private void registrarAlimentos(Tabla tabla){
		int i_PlatoEntrada = 0; // Indices iteradores
		int i_PlatoFuerte = 0;
		int i_PlatoPostre = 0;
		int i_PlatoBebida = 0;

		while(i_PlatoEntrada != tabla.getTablaEntradas().size()){
			listaEntradas.add(tabla.getTablaEntradas().get(i_PlatoEntrada));
			i_PlatoEntrada++;
		}

		while(i_PlatoFuerte != tabla.getTablaPrincipal().size()){
			listaFuertes.add(tabla.getTablaPrincipal().get(i_PlatoFuerte));
			i_PlatoFuerte++;
		}

		while(i_PlatoPostre != tabla.getTablaPostres().size()){
			listaPostres.add(tabla.getTablaPostres().get(i_PlatoPostre));
			i_PlatoPostre++;
		}

		while(i_PlatoBebida != tabla.getTablaBebidas().size()){
			listaBebidas.add(tabla.getTablaBebidas().get(i_PlatoBebida));
			i_PlatoBebida++;
		}
	}

	/**
	 * Método encargado de registrar todos los productos disponibles en los ArrayList locales provenientes del servidor.
	 * @param tabla
	 */
	private void registrarAlimentos_Disponibles(Tabla tabla){
		int i_PlatoEntrada = 0; // Indices iteradores
		int i_PlatoFuerte = 0;
		int i_PlatoPostre = 0;
		int i_PlatoBebida = 0;

		while(i_PlatoEntrada != tabla.getTablaEntradas().size()){
			listaEntradas_Disponibles.add(tabla.getTablaEntradas().get(i_PlatoEntrada));
			i_PlatoEntrada++;
		}

		while(i_PlatoFuerte != tabla.getTablaPrincipal().size()){
			listaFuertes_Disponibles.add(tabla.getTablaPrincipal().get(i_PlatoFuerte));
			i_PlatoFuerte++;
		}

		while(i_PlatoPostre != tabla.getTablaPostres().size()){
			listaPostres_Disponibles.add(tabla.getTablaPostres().get(i_PlatoPostre));
			i_PlatoPostre++;
		}

		while(i_PlatoBebida != tabla.getTablaBebidas().size()){
			listaBebidas_Disponibles.add(tabla.getTablaBebidas().get(i_PlatoBebida));
			i_PlatoBebida++;
		}
	}

	/**
	 * Método encargado de registrar todos los productos no disponibles en los ArrayList locales provenientes del servidor.
	 * @param tabla
	 */
	private void registrarAlimentos_NoDisponibles(Tabla tabla){
		int i_PlatoEntrada = 0; // Indices iteradores
		int i_PlatoFuerte = 0;
		int i_PlatoPostre = 0;
		int i_PlatoBebida = 0;

		while(i_PlatoEntrada != tabla.getTablaEntradas().size()){
			listaEntradas_NoDisponibles.add(tabla.getTablaEntradas().get(i_PlatoEntrada));
			i_PlatoEntrada++;
		}

		while(i_PlatoFuerte != tabla.getTablaPrincipal().size()){
			listaFuertes_NoDisponibles.add(tabla.getTablaPrincipal().get(i_PlatoFuerte));
			i_PlatoFuerte++;
		}

		while(i_PlatoPostre != tabla.getTablaPostres().size()){
			listaPostres_NoDisponibles.add(tabla.getTablaPostres().get(i_PlatoPostre));
			i_PlatoPostre++;
		}

		while(i_PlatoBebida != tabla.getTablaBebidas().size()){
			listaBebidas_NoDisponibles.add(tabla.getTablaBebidas().get(i_PlatoBebida));
			i_PlatoBebida++;
		}
	}

	private final ListChangeListener<Alimento> selectorTablaEntrada = new ListChangeListener<Alimento>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends Alimento> c) {
            ponerEntradaSeleccionada();
        }
    };

	private final ListChangeListener<Alimento> selectorTablaPlatoFuerte = new ListChangeListener<Alimento>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends Alimento> c) {
            ponerPlatoFuerteSeleccionado();
        }
    };

	private final ListChangeListener<Alimento> selectorTablaPostre = new ListChangeListener<Alimento>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends Alimento> c) {
            ponerPostreSeleccionado();
        }
    };

	private final ListChangeListener<Alimento> selectorTablaBebida = new ListChangeListener<Alimento>() {
                @Override
                public void onChanged(ListChangeListener.Change<? extends Alimento> c) {
                    ponerBebidaSeleccionada();
                }
            };

    /**
     * Obtiene el alimento selecciondo de la tabla.
     * @return Alimento
     */
    public Alimento getTablaEntradasSelecionadas(){
    	if(tablaView_Entradas_A != null){

    		List<Alimento> tabla = tablaView_Entradas_A.getSelectionModel().getSelectedItems();

    		if(tabla.size() == 1){

    			final Alimento competicionSelecionada = tabla.get(0);
    			return competicionSelecionada;
    			}
    		}
    	return null;
    }

    public Alimento getTablaPlatosFuertesSelecionados(){
    	if(tablaView_PlatosFuertes_A != null){

    		List<Alimento> tabla = tablaView_PlatosFuertes_A.getSelectionModel().getSelectedItems();

    		if(tabla.size() == 1){

    			final Alimento competicionSelecionada = tabla.get(0);
    			return competicionSelecionada;
    			}
    		}
    	return null;
    }

    public Alimento getTablaPostresSelecionados(){
    	if(tablaView_Postres_A != null){

    		List<Alimento> tabla = tablaView_Postres_A.getSelectionModel().getSelectedItems();

    		if(tabla.size() == 1){

    			final Alimento competicionSelecionada = tabla.get(0);
    			return competicionSelecionada;
    			}
    		}
    	return null;
    }

    public Alimento getTablaBebidasSelecionadas(){
    	if(tablaView_Bebidas_A != null){

    		List<Alimento> tabla = tablaView_Bebidas_A.getSelectionModel().getSelectedItems();

    		if(tabla.size() == 1){

    			final Alimento competicionSelecionada = tabla.get(0);
    			return competicionSelecionada;
    			}
    		}
    	return null;
    }

    /**
     * Obtiene los atributos del alimento para ser pintados sobre las tablas.
     */
    private void ponerEntradaSeleccionada(){
    	try{
	    	final Alimento entrada = getTablaEntradasSelecionadas();ModificarPlatillo.setAlimento(entrada);
	    	posicionEntradaEnTabla = alimentos_Entradas.indexOf(entrada);
	    	textField_ProductoActual.setText(alimentos_Entradas.get(posicionEntradaEnTabla).getNombre());
	    	detallePlatillo = new VerDetallePlatillo();
	    	detallePlatillo.setCodigo(alimentos_Entradas.get(posicionEntradaEnTabla).getCodigo());
	    	detallePlatillo.setNombre(alimentos_Entradas.get(posicionEntradaEnTabla).getNombre());
	    	detallePlatillo.setTamanio(alimentos_Entradas.get(posicionEntradaEnTabla).getTamanioPorcion());
	    	detallePlatillo.setPiezasPorcion(alimentos_Entradas.get(posicionEntradaEnTabla).getPiezasPorcion());
	    	detallePlatillo.setCaloriasPorcion(alimentos_Entradas.get(posicionEntradaEnTabla).getCaloriasPorPorcion());
	    	detallePlatillo.setCaloriasPieza(alimentos_Entradas.get(posicionEntradaEnTabla).getCaloriasPorPieza());
	    	detallePlatillo.setPrecio(alimentos_Entradas.get(posicionEntradaEnTabla).getPrecio());
	    	detallePlatillo.setDescripcion(alimentos_Entradas.get(posicionEntradaEnTabla).getDescripcion());
    	}catch(Exception o){}
    }

    private void ponerPlatoFuerteSeleccionado(){
    	try{
	    	final Alimento fuerte = getTablaPlatosFuertesSelecionados();
	    	posicionFuerteEnTabla = alimentos_Fuertes.indexOf(fuerte);
	    	textField_ProductoActual.setText(alimentos_Fuertes.get(posicionFuerteEnTabla).getNombre());
	    	detallePlatillo = new VerDetallePlatillo();
	    	detallePlatillo.setCodigo(alimentos_Fuertes.get(posicionFuerteEnTabla).getCodigo());
	    	detallePlatillo.setNombre(alimentos_Fuertes.get(posicionFuerteEnTabla).getNombre());
	    	detallePlatillo.setTamanio(alimentos_Fuertes.get(posicionFuerteEnTabla).getTamanioPorcion());
	    	detallePlatillo.setPiezasPorcion(alimentos_Fuertes.get(posicionFuerteEnTabla).getPiezasPorcion());
	    	detallePlatillo.setCaloriasPorcion(alimentos_Fuertes.get(posicionFuerteEnTabla).getCaloriasPorPorcion());
	    	detallePlatillo.setCaloriasPieza(alimentos_Fuertes.get(posicionFuerteEnTabla).getCaloriasPorPieza());
	    	detallePlatillo.setPrecio(alimentos_Fuertes.get(posicionFuerteEnTabla).getPrecio());
	    	detallePlatillo.setDescripcion(alimentos_Fuertes.get(posicionFuerteEnTabla).getDescripcion());
    	}catch(Exception o){}
    }

    private void ponerPostreSeleccionado(){
    	try{
	    	final Alimento postre = getTablaPostresSelecionados();
	    	posicionPostreEnTabla = alimentos_Postres.indexOf(postre);
	    	textField_ProductoActual.setText(alimentos_Postres.get(posicionPostreEnTabla).getNombre());
	    	detallePlatillo = new VerDetallePlatillo();
	    	detallePlatillo.setCodigo(alimentos_Postres.get(posicionPostreEnTabla).getCodigo());
	    	detallePlatillo.setNombre(alimentos_Postres.get(posicionPostreEnTabla).getNombre());
	    	detallePlatillo.setTamanio(alimentos_Postres.get(posicionPostreEnTabla).getTamanioPorcion());
	    	detallePlatillo.setPiezasPorcion(alimentos_Postres.get(posicionPostreEnTabla).getPiezasPorcion());
	    	detallePlatillo.setCaloriasPorcion(alimentos_Postres.get(posicionPostreEnTabla).getCaloriasPorPorcion());
	    	detallePlatillo.setCaloriasPieza(alimentos_Postres.get(posicionPostreEnTabla).getCaloriasPorPieza());
	    	detallePlatillo.setPrecio(alimentos_Postres.get(posicionPostreEnTabla).getPrecio());
	    	detallePlatillo.setDescripcion(alimentos_Postres.get(posicionPostreEnTabla).getDescripcion());
    	}catch(Exception o){}
    }

    private void ponerBebidaSeleccionada(){
    	try{
	    	final Alimento bebida = getTablaBebidasSelecionadas();
	    	posicionBebidaEnTabla = alimentos_Bebidas.indexOf(bebida);
	    	textField_ProductoActual.setText(alimentos_Bebidas.get(posicionBebidaEnTabla).getNombre());
	    	detallePlatillo = new VerDetallePlatillo();
	    	detallePlatillo.setCodigo(alimentos_Bebidas.get(posicionBebidaEnTabla).getCodigo());
	    	detallePlatillo.setNombre(alimentos_Bebidas.get(posicionBebidaEnTabla).getNombre());
	    	detallePlatillo.setTamanio(alimentos_Bebidas.get(posicionBebidaEnTabla).getTamanioPorcion());
	    	detallePlatillo.setPiezasPorcion(alimentos_Bebidas.get(posicionBebidaEnTabla).getPiezasPorcion());
	    	detallePlatillo.setCaloriasPorcion(alimentos_Bebidas.get(posicionBebidaEnTabla).getCaloriasPorPorcion());
	    	detallePlatillo.setCaloriasPieza(alimentos_Bebidas.get(posicionBebidaEnTabla).getCaloriasPorPieza());
	    	detallePlatillo.setPrecio(alimentos_Bebidas.get(posicionBebidaEnTabla).getPrecio());
	    	detallePlatillo.setDescripcion(alimentos_Bebidas.get(posicionBebidaEnTabla).getDescripcion());
    	}catch(Exception o){}
    }

    /**
     * Inicializa los componentes internos de las tablas con los atributos correspondientes.
     * Esto para identificar las columnas a la hora de trabajar con las tablas.
     */
    private void inicializarTablaAlimentos(){
    	// --- Entradas --- //
    	tableColumn_Entradas_A.setCellValueFactory(new PropertyValueFactory<Alimento, String>("Nombre"));// ("atributo del Alimento")									// provincia porque es el atributo que va a almacenar
    	tableColumn_Racion_A.setCellValueFactory(new PropertyValueFactory<Alimento, String>("TamanioPorcion"));
    	tableColumn_Calorias_A.setCellValueFactory(new PropertyValueFactory<Alimento, String>("CaloriasPorPorcion"));
    	tableColumn_CaloriasPieza_A.setCellValueFactory(new PropertyValueFactory<Alimento, String>("CaloriasPorPieza"));
    	alimentos_Entradas = FXCollections.observableArrayList();
    	tablaView_Entradas_A.setItems(alimentos_Entradas);

    	// --- Platos Fuertes --- //
    	tableColumn_Fuerte_A.setCellValueFactory(new PropertyValueFactory<Alimento, String>("Nombre"));										// provincia porque es el atributo que va a almacenar
    	tableColumn_FuerteRacion_A.setCellValueFactory(new PropertyValueFactory<Alimento, String>("TamanioPorcion"));
    	tableColumn_FuerteCalorias_A.setCellValueFactory(new PropertyValueFactory<Alimento, String>("CaloriasPorPorcion"));
    	tableColumn_FuerteCaloriasPieza_A.setCellValueFactory(new PropertyValueFactory<Alimento, String>("CaloriasPorPieza"));
    	alimentos_Fuertes = FXCollections.observableArrayList();
    	tablaView_PlatosFuertes_A.setItems(alimentos_Fuertes);

    	// --- Postres --- //
    	tableColumn_Postre_A.setCellValueFactory(new PropertyValueFactory<Alimento, String>("Nombre"));										// provincia porque es el atributo que va a almacenar
    	tableColumn_PostreRacion_A.setCellValueFactory(new PropertyValueFactory<Alimento, String>("TamanioPorcion"));
    	tableColumn_PostreCalorias_A.setCellValueFactory(new PropertyValueFactory<Alimento, String>("CaloriasPorPorcion"));
    	tableColumn_PostreCaloriasPieza_A.setCellValueFactory(new PropertyValueFactory<Alimento, String>("CaloriasPorPieza"));
    	alimentos_Postres = FXCollections.observableArrayList();
    	tablaView_Postres_A.setItems(alimentos_Postres);

    	// --- Bebidas --- //
    	tableColumn_Bebida_A.setCellValueFactory(new PropertyValueFactory<Alimento, String>("Nombre"));										// provincia porque es el atributo que va a almacenar
    	tableColumn_BebidaRacion_A.setCellValueFactory(new PropertyValueFactory<Alimento, String>("TamanioPorcion"));
    	tableColumn_BebidaCalorias_A.setCellValueFactory(new PropertyValueFactory<Alimento, String>("CaloriasPorPorcion"));
    	tableColumn_BebidaCaloriasPieza_A.setCellValueFactory(new PropertyValueFactory<Alimento, String>("CaloriasPorPieza"));
    	alimentos_Bebidas = FXCollections.observableArrayList();
    	tablaView_Bebidas_A.setItems(alimentos_Bebidas);
    }

    /**
     * Método encargado de obtener todos los alimentos registrados en el servidor para luego ser cargados y pintados sobre las tablas.
     */
	private void cargarTablas(){
		tablas = misTablas.cargarTablas();

		if(tablas == null){
			System.out.println("Error!. No hay tablas que cargar");
		}
		else{
	    	this.inicializarTablaAlimentos();// Se inicializa la tabla
	    	registrarAlimentos(tablas);

	    	final ObservableList<Alimento> tablaEntradaCel = tablaView_Entradas_A.getSelectionModel().getSelectedItems();
	    	tablaEntradaCel.addListener(selectorTablaEntrada); // Seleciona las tuplas de la tabla Entrada

	    	final ObservableList<Alimento> tablaPlatoFuerteEntradaCel = tablaView_PlatosFuertes_A.getSelectionModel().getSelectedItems();
	    	tablaPlatoFuerteEntradaCel.addListener(selectorTablaPlatoFuerte);

	    	final ObservableList<Alimento> tablaPostresCel = tablaView_Postres_A.getSelectionModel().getSelectedItems();
	    	tablaPostresCel.addListener(selectorTablaPostre);

	    	final ObservableList<Alimento> tablaBebidasCel = tablaView_Bebidas_A.getSelectionModel().getSelectedItems();
	    	tablaBebidasCel.addListener(selectorTablaBebida);

	    	for(Alimento entrada: listaEntradas){ //Inicializa la tabla con los datos del Alimento
	    		entrada.setNombre(entrada.getNombre());
	    		entrada.setTamanioPorcion(entrada.getTamanioPorcion());
	    		entrada.setCaloriasPorPorcion(entrada.getCaloriasPorPorcion());
	    		entrada.setCaloriasPorPieza(entrada.getCaloriasPorPieza());

	    		alimentos_Entradas.add(entrada);
	    	}

	    	for(Alimento entrada: listaFuertes){ //Inicializa la tabla con los datos del Alimento
	    		entrada.setNombre(entrada.getNombre());
	    		entrada.setTamanioPorcion(entrada.getTamanioPorcion());
	    		entrada.setCaloriasPorPorcion(entrada.getCaloriasPorPorcion());
	    		entrada.setCaloriasPorPieza(entrada.getCaloriasPorPieza());

	    		alimentos_Fuertes.add(entrada);
	    	}

	    	for(Alimento entrada: listaPostres){ //Inicializa la tabla con los datos del Alimento
	    		entrada.setNombre(entrada.getNombre());
	    		entrada.setTamanioPorcion(entrada.getTamanioPorcion());
	    		entrada.setCaloriasPorPorcion(entrada.getCaloriasPorPorcion());
	    		entrada.setCaloriasPorPieza(entrada.getCaloriasPorPieza());
	    		//System.out.println("Postre: " + entrada.getNombre());
	    		alimentos_Postres.add(entrada);
	    	}

	    	for(Alimento entrada: listaBebidas){ //Inicializa la tabla con los datos del Alimento
	    		entrada.setNombre(entrada.getNombre());
	    		entrada.setTamanioPorcion(entrada.getTamanioPorcion());
	    		entrada.setCaloriasPorPorcion(entrada.getCaloriasPorPorcion());
	    		entrada.setCaloriasPorPieza(entrada.getCaloriasPorPieza());
	    		//System.out.println("Bebida: " + entrada.getNombre());
	    		alimentos_Bebidas.add(entrada);
	    	}
		}
	}

    /**
     * Método encargado de obtener todos los alimentos disponibles registrados en el servidor para luego ser cargados
     * y pintados sobre las tablas.
     */
	public void cargarTablas_ProductosDisponibles(){
		tablasDisponibles = misTablas.cargarTablasDisponibles();

		if(tablasDisponibles == null){
			System.out.println("Error!. No hay tablas que cargar");
		}
		else{
	    	this.inicializarTablaAlimentos();// Se inicializa la tabla
	    	registrarAlimentos_Disponibles(tablasDisponibles);

	    	final ObservableList<Alimento> tablaEntradaCel = tablaView_Entradas_A.getSelectionModel().getSelectedItems();
	    	tablaEntradaCel.addListener(selectorTablaEntrada); // Seleciona las tuplas de la tabla Entrada

	    	final ObservableList<Alimento> tablaPlatoFuerteEntradaCel = tablaView_PlatosFuertes_A.getSelectionModel().getSelectedItems();
	    	tablaPlatoFuerteEntradaCel.addListener(selectorTablaPlatoFuerte);

	    	final ObservableList<Alimento> tablaPostresCel = tablaView_Postres_A.getSelectionModel().getSelectedItems();
	    	tablaPostresCel.addListener(selectorTablaPostre);

	    	final ObservableList<Alimento> tablaBebidasCel = tablaView_Bebidas_A.getSelectionModel().getSelectedItems();
	    	tablaBebidasCel.addListener(selectorTablaBebida);

	    	for(Alimento entrada: listaEntradas_Disponibles){ //Inicializa la tabla con los datos del Alimento
	    		entrada.setNombre(entrada.getNombre());
	    		entrada.setTamanioPorcion(entrada.getTamanioPorcion());
	    		entrada.setCaloriasPorPorcion(entrada.getCaloriasPorPorcion());
	    		entrada.setCaloriasPorPieza(entrada.getCaloriasPorPieza());

	    		alimentos_Entradas.add(entrada);
	    	}

	    	for(Alimento entrada: listaFuertes_Disponibles){ //Inicializa la tabla con los datos del Alimento
	    		entrada.setNombre(entrada.getNombre());
	    		entrada.setTamanioPorcion(entrada.getTamanioPorcion());
	    		entrada.setCaloriasPorPorcion(entrada.getCaloriasPorPorcion());
	    		entrada.setCaloriasPorPieza(entrada.getCaloriasPorPieza());

	    		alimentos_Fuertes.add(entrada);
	    	}

	    	for(Alimento entrada: listaPostres_Disponibles){ //Inicializa la tabla con los datos del Alimento
	    		entrada.setNombre(entrada.getNombre());
	    		entrada.setTamanioPorcion(entrada.getTamanioPorcion());
	    		entrada.setCaloriasPorPorcion(entrada.getCaloriasPorPorcion());
	    		entrada.setCaloriasPorPieza(entrada.getCaloriasPorPieza());
	    		//System.out.println("Postre: " + entrada.getNombre());
	    		alimentos_Postres.add(entrada);
	    	}

	    	for(Alimento entrada: listaBebidas_Disponibles){ //Inicializa la tabla con los datos del Alimento
	    		entrada.setNombre(entrada.getNombre());
	    		entrada.setTamanioPorcion(entrada.getTamanioPorcion());
	    		entrada.setCaloriasPorPorcion(entrada.getCaloriasPorPorcion());
	    		entrada.setCaloriasPorPieza(entrada.getCaloriasPorPieza());
	    		//System.out.println("Bebida: " + entrada.getNombre());
	    		alimentos_Bebidas.add(entrada);
	    	}
		}
	}

    /**
     * Método encargado de obtener todos los alimentos no disponiblesregistrados en el servidor para luego ser cargados
     * y pintados sobre las tablas.
     */
	public void cargarTablas_ProductosNoDisponibles(){
		tablasNoDisponibles = misTablas.cargarTablasNoDisponibles();

		if(tablasNoDisponibles == null){
			System.out.println("Error!. No hay tablas que cargar");
		}
		else{
	    	this.inicializarTablaAlimentos();// Se inicializa la tabla
	    	registrarAlimentos_NoDisponibles(tablasNoDisponibles);

	    	final ObservableList<Alimento> tablaEntradaCel = tablaView_Entradas_A.getSelectionModel().getSelectedItems();
	    	tablaEntradaCel.addListener(selectorTablaEntrada); // Seleciona las tuplas de la tabla Entrada

	    	final ObservableList<Alimento> tablaPlatoFuerteEntradaCel = tablaView_PlatosFuertes_A.getSelectionModel().getSelectedItems();
	    	tablaPlatoFuerteEntradaCel.addListener(selectorTablaPlatoFuerte);

	    	final ObservableList<Alimento> tablaPostresCel = tablaView_Postres_A.getSelectionModel().getSelectedItems();
	    	tablaPostresCel.addListener(selectorTablaPostre);

	    	final ObservableList<Alimento> tablaBebidasCel = tablaView_Bebidas_A.getSelectionModel().getSelectedItems();
	    	tablaBebidasCel.addListener(selectorTablaBebida);

	    	for(Alimento entrada: listaEntradas_NoDisponibles){ //Inicializa la tabla con los datos del Alimento
	    		entrada.setNombre(entrada.getNombre());
	    		entrada.setTamanioPorcion(entrada.getTamanioPorcion());
	    		entrada.setCaloriasPorPorcion(entrada.getCaloriasPorPorcion());
	    		entrada.setCaloriasPorPieza(entrada.getCaloriasPorPieza());

	    		alimentos_Entradas.add(entrada);
	    	}

	    	for(Alimento entrada: listaFuertes_NoDisponibles){ //Inicializa la tabla con los datos del Alimento
	    		entrada.setNombre(entrada.getNombre());
	    		entrada.setTamanioPorcion(entrada.getTamanioPorcion());
	    		entrada.setCaloriasPorPorcion(entrada.getCaloriasPorPorcion());
	    		entrada.setCaloriasPorPieza(entrada.getCaloriasPorPieza());

	    		alimentos_Fuertes.add(entrada);
	    	}

	    	for(Alimento entrada: listaPostres_NoDisponibles){ //Inicializa la tabla con los datos del Alimento
	    		entrada.setNombre(entrada.getNombre());
	    		entrada.setTamanioPorcion(entrada.getTamanioPorcion());
	    		entrada.setCaloriasPorPorcion(entrada.getCaloriasPorPorcion());
	    		entrada.setCaloriasPorPieza(entrada.getCaloriasPorPieza());
	    		//System.out.println("Postre: " + entrada.getNombre());
	    		alimentos_Postres.add(entrada);
	    	}

	    	for(Alimento entrada: listaBebidas_NoDisponibles){ //Inicializa la tabla con los datos del Alimento
	    		entrada.setNombre(entrada.getNombre());
	    		entrada.setTamanioPorcion(entrada.getTamanioPorcion());
	    		entrada.setCaloriasPorPorcion(entrada.getCaloriasPorPorcion());
	    		entrada.setCaloriasPorPieza(entrada.getCaloriasPorPieza());
	    		//System.out.println("Bebida: " + entrada.getNombre());
	    		alimentos_Bebidas.add(entrada);
	    	}
		}
	}

	@FXML
	public void cerrarServidor(ActionEvent event){

		boolean cerro = modeloServidor.CerrarServidor();

		if(cerro){
			mostrarAlerta("Se ha cerrado el servidor correctamente!", "Cualquier actividad y/o comando realizado y ejecutado por parte de los administradores y clientes, queda totalmente fuera de servicio.\n\n", AlertType.WARNING);
		}
		else{
			mostrarAlerta("Error al cerrar el servidor", "Verifique que el servidor se encuentre en linea. En todo caso\nya debió ser suspendido por algún administrador.\n\n", AlertType.ERROR);
		}
	}

}
