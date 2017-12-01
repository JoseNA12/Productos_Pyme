package controlador_Cliente;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Tabla;
import modelo.TablaDAO;
import pedido.LineaPedido;
import pedido.Pedido;
import productos.Alimento;

/**
 *
 * Clase encargada de tramitar todas peticiones realizadas por el cliente.
 * Además, corresponde a la pantalla inicial del Cliente de la aplicación.
 * Las operaciones que lleva acabo son: Agregar platillo a la orden, eliminar platillo de la orden,
 * ver detalles del producto seleccionado y proceder con la orden para realizar el trámite.
 * @author Jose Navarro
 *
 */
public class RealizarPedido implements Initializable, ControlledScreen {

	ScreensController myController;
	VerDetallePlatillo detallePlatillo;

	@FXML private ChoiceBox<String> choiceBoxMostrarPorTamanio;
	@FXML private ChoiceBox<String> choiceBoxCantPlatillos;

	ObservableList<String> mostrarPorTamanio = FXCollections.observableArrayList("100 gramos", "200 gramos", "300 gramos", "400 gramos",
			"500 gramos", "600 gramos", "700 gramos", "800 gramos", "900 gramos", "1000 gramos", "250 ml", "300 ml", "350 ml",
			"400 ml", "450 ml", "500 ml", "550 ml", "600 ml", "650 ml", "700 ml");

	ObservableList<String> mostrarCantPlatillos = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

	public Tabla tablas = new Tabla();
	public TablaDAO misTablas = new TablaDAO();

	@FXML private TextField textField_AlimentoSeleccionado;

	// -------Entradas------- //
	@FXML private TableView<Alimento> tablaView_Entradas_C; // C -> Clente
	@FXML private TableColumn<Alimento, String> tableColumn_Entradas_C;
	@FXML private TableColumn<Alimento, String> tableColumn_Racion_C;
	@FXML private TableColumn<Alimento, String> tableColumn_Calorias_C;
	@FXML private TableColumn<Alimento, String> tableColumn_CaloriasPieza_C;
	public ArrayList<Alimento> listaEntradas = new ArrayList<Alimento>();
	ObservableList<Alimento> alimentos_Entradas;
	public int posicionEntradaEnTabla;

	// -------Platos fuertes------- //
	@FXML private TableView<Alimento> tablaView_PlatosFuertes_C;
	@FXML private TableColumn<Alimento, String> tableColumn_Fuerte_C;
	@FXML private TableColumn<Alimento, String> tableColumn_FuerteRacion_C;
	@FXML private TableColumn<Alimento, String> tableColumn_FuerteCalorias_C;
	@FXML private TableColumn<Alimento, String> tableColumn_FuerteCaloriasPieza_C;
	public ArrayList<Alimento> listaFuertes = new ArrayList<Alimento>();
	ObservableList<Alimento> alimentos_Fuertes;
	public int posicionFuerteEnTabla;

	// -------Postres------- //
	@FXML private TableView<Alimento> tablaView_Postres_C;
	@FXML private TableColumn<Alimento, String> tableColumn_Postre_C;
	@FXML private TableColumn<Alimento, String> tableColumn_PostreRacion_C;
	@FXML private TableColumn<Alimento, String> tableColumn_PostreCalorias_C;
	@FXML private TableColumn<Alimento, String> tableColumn_PostreCaloriasPieza_C;
	public ArrayList<Alimento> listaPostres = new ArrayList<Alimento>();
	ObservableList<Alimento> alimentos_Postres;
	public int posicionPostreEnTabla;

	// -------Bebidas------- //
	@FXML private TableView<Alimento> tablaView_Bebidas_C;
	@FXML private TableColumn<Alimento, String> tableColumn_Bebida_C;
	@FXML private TableColumn<Alimento, String> tableColumn_BebidaRacion_C;
	@FXML private TableColumn<Alimento, String> tableColumn_BebidaCalorias_C;
	@FXML private TableColumn<Alimento, String> tableColumn_BebidaCaloriasPieza_C;;
	public ArrayList<Alimento> listaBebidas = new ArrayList<Alimento>();
	ObservableList<Alimento> alimentos_Bebidas;
	public int posicionBebidaEnTabla;

	// -------Orden/Pedido------- //
	@FXML private TableView<Alimento> tableView_Orden;
	@FXML private TableColumn<Alimento, String> tableColumn_Cantidad;
	@FXML private TableColumn<Alimento, String> tableColumn_OrdenActual;
	public static ArrayList<Alimento> listaOrdenActual = new ArrayList<Alimento>();
	static ObservableList<Alimento> alimentos_OrdenActual;
	public int posicionOrdenEnTabla;

	// -------Resultado del pedido------- //
	@FXML private TableView<Pedido> tableView_InformacionOrdenActual;
	@FXML private TableColumn<Pedido, String> tableColumn_TotalCalorias;
	@FXML private TableColumn<Pedido, String> tableColumn_PrecioTotal;
	public static ArrayList<Pedido> listaInfoOrden = new ArrayList<Pedido>();
	static ObservableList<Pedido> Informacion_OrdenActual;
	public int posicionInformacion_OrdenActualEnTabla;

	public static Pedido miPedido = new Pedido();


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		choiceBoxMostrarPorTamanio.setItems(mostrarPorTamanio);
		choiceBoxCantPlatillos.setItems(mostrarCantPlatillos);

		cargarTablas();
	}

	public void setScreenParent(ScreensController screenParent){
		myController = screenParent;
	}

	@FXML
	public void mostrarAyuda(ActionEvent event){
		mostrarAlerta("Ayuda", "Para escoger los productos que deseas ordenar, solo tienes que seleccionar los que gustes "+
							   "en las tablas de la pantalla donde se encuentran las listas de los alimentos. "+
							   "Una vez escogido el producto, selecciona la cantidad que deseas ordenar y presiona el bóton Agregar al carrito.\n"+
							   "En caso de quieras elminar un producto de tu orden actual, solo selecciona el que quieres eliminar "+
							   "y presiona e botón Eliminar platillo.\n"+"Para proceder con la orden de tus alimentos presiona "+
							   "el botón Realizar pedido. Una vez hecho este paso, escoge el tipo de entrega e ingresa tus datos correspondientes.\n\n",
							   AlertType.INFORMATION);
	}

	/**
	 * Aplica un ordenamiento de acuerdo a la opción seleccionada. Se ordenan por gramos (tamaño): 100g, 200g, 300g, 400g, ..., etc.
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
					"Para mostrar las tablas ordenadas debe seleccionar la clasificación por gramos.\n\n", AlertType.ERROR);
		}
		catch(Exception o){}
	}

	/**
	 * Método encargado de procesar el pedido del cliente. Además, cambia de pantalla a: Realizar Pedido.
	 * @param event
	 */
	@FXML
	public void goToRealizarPedido(ActionEvent event){

		if(listaOrdenActual.size() != 0){

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Atención");
			alert.setHeaderText("\nEstás seguro de tu pedido?. Verifica que no olvidaste nada ;)");
			alert.setContentText("Presiona aceptar para proceder con el pedido.\nCaso contrario, presiona cancelar para seguir ordenando.\n\n");
			alert.setResizable(false);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){

				SolicitarPedido.setPedido(miPedido);
			    myController.setScreen(ScreensFramework.screen2ID);

			}
			else {
			    alert.close();
			}
		}else{
			mostrarAlerta("\nNo es posible realizar el pedido si no tiene productos \nen su orden actual!",
					"Agregue los alimentos deseados desde las tablas de productos \npara poder proceder.\n\n", AlertType.ERROR);
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

	/**
	 * Encargado de buscar un alimento en los ArrayList locales.
	 * Retorna el objeto para realizar las operaciones debidas según el caso.
	 * @param pNombreProducto
	 * @return Alimento
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
	 * Encargado de agregar a la orden del cliente el producto seleccionado.
	 * Calcula los montos de precio y calorias según el alimento.
	 * @param event
	 */
	@FXML
	public void agregarAlimentoOrden(ActionEvent event){
		String cantidadEscogida = null;
		String alimentoEscogido = null;
		try{
			cantidadEscogida = choiceBoxCantPlatillos.getValue();
			alimentoEscogido = textField_AlimentoSeleccionado.getText();

			if(!alimentoEscogido.equals("")){

			}
			else{
				mostrarAlerta("\nPor favor, seleccione un alimento deseado!", "Esto es importante si desea ordenar!", AlertType.ERROR);
			}

			if(!cantidadEscogida.equals(null)){ // else -> Muestra el msg del catch(NullPointerException)
				LineaPedido miLinea = new LineaPedido();
				int cantidadProductos = Integer.parseInt(cantidadEscogida);

				miLinea.setCantidadProducto(cantidadProductos);

				Alimento miAlimento = BuscarProducto(alimentoEscogido);
				double precio = Double.parseDouble(miAlimento.getPrecio());
				String caloriasString = miAlimento.getCaloriasPorPorcion();
				String caloriasSubstring = "";

				int i = 1;
				while(!caloriasString.substring(i-1, i).equals(" ")){
					caloriasSubstring += caloriasString.substring(i-1, i);
					i++;
				}

				double calorias = Integer.parseInt(caloriasSubstring);

				miLinea.calcularMontoProducto(precio, cantidadProductos);

				registrarEnOrden(miAlimento); //ArrayList en RealizarPedido
				agregarAlimentoTablaOrden(String.valueOf(cantidadProductos));

				// -- Se registra en Pedido -- //
				miPedido.calcularMonto(miLinea.calcularMontoProducto(precio, cantidadProductos));
				miPedido.calcularCaloriasTotales(miLinea.calcularTotalCalorias(calorias, cantidadProductos));

				registrarInformacionOrdenEnTabla(miPedido);

				miPedido.agregarLineaPedido(miLinea); // Se envia el monto del alimento y las calorias
				miPedido.agregarProductoLista(miAlimento); //ArrayList en Pedido

				//PENDIENTE: Hacer ArrayList de MiLinea, esto para el final cuando ya se manda la orden para mas facil!!!
			}
		}
		catch(NullPointerException e){
			mostrarAlerta("\nPor favor, seleccione la cantidad de platillos deseada", "Esto es importante para realizar su orden!", AlertType.ERROR);
		}
	}

	/**
	 * Elimina el producto seleccionado por el usuario en su orden actual. Actualiza el pedido con los precios y calorias
	 * @param event
	 */
	@FXML
	public void eliminarAlimentoOrden(ActionEvent event){
		final Alimento orden = getTablaOrdenAlimentosSelecionadas();

		if(orden == null){
			mostrarAlerta("\nNo es posible eliminar el producto",
					"Su orden actual se encuentra vacía o no has selecciondo \nningún alimento de la tabla del pedido actual!\n\n", AlertType.ERROR);
		}else{

			Alimento producto = BuscarProducto(orden.getNombre());

			// ---- Interfaz ---- //
			alimentos_OrdenActual.remove(orden); //ObservableList - Borra el pedido de la tabla (lo visual)

			// ---- Orden Actual ---- //
			listaOrdenActual.remove(producto); //ArrayList local - Borra el producto del Array de orden actual

			// ---- Orden Pedido ---- //
			miPedido.eliminarProductoLista(producto); //ArrayList de Pedido - Borra el producto del Array de Pedido actual

			// ---- Obtener variables ---- //
			String CantidadPlatoString = orden.getCantidad();
			double cantidadPlatos = Integer.parseInt(CantidadPlatoString);
			String PrecioString = producto.getPrecio();
			double precio = Double.parseDouble(PrecioString);
			double montoTotal = cantidadPlatos * precio;

			String CaloriasPlatoString = producto.getCaloriasPorPorcion();
			double calorias = 0;int k = 1;String caloriasTemp = "";

			while(!CaloriasPlatoString.substring(k-1,k).equals(" ")){
				caloriasTemp += CaloriasPlatoString.substring(k-1,k); // Corrige "210 kcal", para solo almacenar 210
				k++;
			}
			calorias = Integer.parseInt(caloriasTemp);

			double totalCalorias = cantidadPlatos * calorias;

			// ---- Eliminar Linea de detalle ---- //
			miPedido.eliminarLineaPedido(montoTotal, totalCalorias); //Se envia el total y las calorias para comparar en el Array y eliminar el correspondiente

			// ---- Actualizar montos del pedido ---- //
			double montoTotal_Anterior = miPedido.getPrecioTotal();
			double totalCalorias_Anterior = miPedido.getTotalCalorias();
			miPedido.actualizarMontosPedido(montoTotal_Anterior - montoTotal, totalCalorias_Anterior - totalCalorias);

			// ---- Actualizar informacion de la tabla (lo visual) ---- //
			registrarInformacionOrdenEnTabla(miPedido);
		}

	}

	/**
	 * Reinicia la orden del usuario. Esto es utilizado en caso de realizar pedidos seguidos.
	 */
	public static void ReiniciarOrden(){

		alimentos_OrdenActual.clear();
		Informacion_OrdenActual.clear();

		listaOrdenActual = new ArrayList<Alimento>();
		miPedido = new Pedido();
		listaInfoOrden = new ArrayList<Pedido>();
	}

	/**
	 * Método encargado de mostrar una ventana con toda la información del producto seleccionado.
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
			alert.setHeaderText(detallePlatillo.getNombre());
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
	 * Encargado de registrar todos los alimentos provenientes del sevidor en los ArrayList locales.
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


    private final ListChangeListener<Alimento> selectorTablaOrden = new ListChangeListener<Alimento>() {
    	@Override
        public void onChanged(ListChangeListener.Change<? extends Alimento> c) {
    		ponerOrdenSeleccionada();
            }
        };

    private final ListChangeListener<Pedido> selectorTablaInformacionOrden = new ListChangeListener<Pedido>() {
    	@Override
        public void onChanged(ListChangeListener.Change<? extends Pedido> c) {
    		ponerInformacionOrdenSeleccionada();
            }
    };

    /**
     * Obtiene el alimento selecciondo de la tabla.
     * @return Alimento
     */
    public Alimento getTablaEntradasSelecionadas(){
    	if(tablaView_Entradas_C != null){

    		List<Alimento> tabla = tablaView_Entradas_C.getSelectionModel().getSelectedItems();

    		if(tabla.size() == 1){

    			final Alimento competicionSelecionada = tabla.get(0);
    			return competicionSelecionada;
    			}
    		}
    	return null;
    }

    public Alimento getTablaPlatosFuertesSelecionados(){
    	if(tablaView_PlatosFuertes_C != null){

    		List<Alimento> tabla = tablaView_PlatosFuertes_C.getSelectionModel().getSelectedItems();

    		if(tabla.size() == 1){

    			final Alimento competicionSelecionada = tabla.get(0);
    			return competicionSelecionada;
    			}
    		}
    	return null;
    }

    public Alimento getTablaPostresSelecionados(){
    	if(tablaView_Postres_C != null){

    		List<Alimento> tabla = tablaView_Postres_C.getSelectionModel().getSelectedItems();

    		if(tabla.size() == 1){

    			final Alimento competicionSelecionada = tabla.get(0);
    			return competicionSelecionada;
    			}
    		}
    	return null;
    }

    public Alimento getTablaBebidasSelecionadas(){
    	if(tablaView_Bebidas_C != null){

    		List<Alimento> tabla = tablaView_Bebidas_C.getSelectionModel().getSelectedItems();

    		if(tabla.size() == 1){

    			final Alimento competicionSelecionada = tabla.get(0);
    			return competicionSelecionada;
    			}
    		}
    	return null;
    }

    public Alimento getTablaOrdenAlimentosSelecionadas(){
    	if(tableView_Orden != null){

    		List<Alimento> tabla = tableView_Orden.getSelectionModel().getSelectedItems();

    		if(tabla.size() == 1){

    			final Alimento competicionSelecionada = tabla.get(0);
    			return competicionSelecionada;
    			}
    		}
    	return null;
    }

    public Pedido getTablaInformacionOrdenSelecionada(){
    	if(tableView_InformacionOrdenActual != null){

    		List<Pedido> tabla = tableView_InformacionOrdenActual.getSelectionModel().getSelectedItems();

    		if(tabla.size() == 1){

    			final Pedido competicionSelecionada = tabla.get(0);
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
	    	final Alimento entrada = getTablaEntradasSelecionadas();
	    	posicionEntradaEnTabla = alimentos_Entradas.indexOf(entrada);
	    	textField_AlimentoSeleccionado.setText(alimentos_Entradas.get(posicionEntradaEnTabla).getNombre());
	    	detallePlatillo = new VerDetallePlatillo();
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
	    	textField_AlimentoSeleccionado.setText(alimentos_Fuertes.get(posicionFuerteEnTabla).getNombre());
	    	detallePlatillo = new VerDetallePlatillo();
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
	    	textField_AlimentoSeleccionado.setText(alimentos_Postres.get(posicionPostreEnTabla).getNombre());
	    	detallePlatillo = new VerDetallePlatillo();
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
	    	textField_AlimentoSeleccionado.setText(alimentos_Bebidas.get(posicionBebidaEnTabla).getNombre());
	    	detallePlatillo = new VerDetallePlatillo();
	    	detallePlatillo.setNombre(alimentos_Bebidas.get(posicionBebidaEnTabla).getNombre());
	    	detallePlatillo.setTamanio(alimentos_Bebidas.get(posicionBebidaEnTabla).getTamanioPorcion());
	    	detallePlatillo.setPiezasPorcion(alimentos_Bebidas.get(posicionBebidaEnTabla).getPiezasPorcion());
	    	detallePlatillo.setCaloriasPorcion(alimentos_Bebidas.get(posicionBebidaEnTabla).getCaloriasPorPorcion());
	    	detallePlatillo.setCaloriasPieza(alimentos_Bebidas.get(posicionBebidaEnTabla).getCaloriasPorPieza());
	    	detallePlatillo.setPrecio(alimentos_Bebidas.get(posicionBebidaEnTabla).getPrecio());
	    	detallePlatillo.setDescripcion(alimentos_Bebidas.get(posicionBebidaEnTabla).getDescripcion());
    	}catch(Exception o){}
    }

    private void ponerOrdenSeleccionada(){
    	final Alimento orden = getTablaOrdenAlimentosSelecionadas();
    	posicionOrdenEnTabla = alimentos_OrdenActual.indexOf(orden);
    }

    private void ponerInformacionOrdenSeleccionada(){
    	final Pedido info = getTablaInformacionOrdenSelecionada();
    	posicionInformacion_OrdenActualEnTabla = Informacion_OrdenActual.indexOf(info);
    }

    /**
     * Inicializa los componentes internos de las tablas con los atributos correspondientes.
     * Esto para identificar las columnas a la hora de trabajar con las tablas.
     */
    private void inicializarTablaAlimentos(){
    	// --- Entradas --- //
    	tableColumn_Entradas_C.setCellValueFactory(new PropertyValueFactory<Alimento, String>("Nombre"));// ("atributo de Alimento")
    	tableColumn_Racion_C.setCellValueFactory(new PropertyValueFactory<Alimento, String>("TamanioPorcion"));
    	tableColumn_Calorias_C.setCellValueFactory(new PropertyValueFactory<Alimento, String>("CaloriasPorPorcion"));
    	tableColumn_CaloriasPieza_C.setCellValueFactory(new PropertyValueFactory<Alimento, String>("CaloriasPorPieza"));
    	alimentos_Entradas = FXCollections.observableArrayList();
    	tablaView_Entradas_C.setItems(alimentos_Entradas);

    	// --- Platos Fuertes --- //
    	tableColumn_Fuerte_C.setCellValueFactory(new PropertyValueFactory<Alimento, String>("Nombre"));
    	tableColumn_FuerteRacion_C.setCellValueFactory(new PropertyValueFactory<Alimento, String>("TamanioPorcion"));
    	tableColumn_FuerteCalorias_C.setCellValueFactory(new PropertyValueFactory<Alimento, String>("CaloriasPorPorcion"));
    	tableColumn_FuerteCaloriasPieza_C.setCellValueFactory(new PropertyValueFactory<Alimento, String>("CaloriasPorPieza"));
    	alimentos_Fuertes = FXCollections.observableArrayList();
    	tablaView_PlatosFuertes_C.setItems(alimentos_Fuertes);

    	// --- Postres --- //
    	tableColumn_Postre_C.setCellValueFactory(new PropertyValueFactory<Alimento, String>("Nombre"));
    	tableColumn_PostreRacion_C.setCellValueFactory(new PropertyValueFactory<Alimento, String>("TamanioPorcion"));
    	tableColumn_PostreCalorias_C.setCellValueFactory(new PropertyValueFactory<Alimento, String>("CaloriasPorPorcion"));
    	tableColumn_PostreCaloriasPieza_C.setCellValueFactory(new PropertyValueFactory<Alimento, String>("CaloriasPorPieza"));
    	alimentos_Postres = FXCollections.observableArrayList();
    	tablaView_Postres_C.setItems(alimentos_Postres);

    	// --- Bebidas --- //
    	tableColumn_Bebida_C.setCellValueFactory(new PropertyValueFactory<Alimento, String>("Nombre"));
    	tableColumn_BebidaRacion_C.setCellValueFactory(new PropertyValueFactory<Alimento, String>("TamanioPorcion"));
    	tableColumn_BebidaCalorias_C.setCellValueFactory(new PropertyValueFactory<Alimento, String>("CaloriasPorPorcion"));
    	tableColumn_BebidaCaloriasPieza_C.setCellValueFactory(new PropertyValueFactory<Alimento, String>("CaloriasPorPieza"));
    	alimentos_Bebidas = FXCollections.observableArrayList();
    	tablaView_Bebidas_C.setItems(alimentos_Bebidas);

    	// --- Orden/Pedido --- //
    	tableColumn_Cantidad.setCellValueFactory(new PropertyValueFactory<Alimento, String>("Cantidad"));
    	tableColumn_OrdenActual.setCellValueFactory(new PropertyValueFactory<Alimento, String>("Nombre"));
    	alimentos_OrdenActual = FXCollections.observableArrayList();
    	tableView_Orden.setItems(alimentos_OrdenActual);

    	// --- Informacion Orden Actual --- //
    	tableColumn_PrecioTotal.setCellValueFactory(new PropertyValueFactory<Pedido, String>("PrecioTotal"));
    	tableColumn_TotalCalorias.setCellValueFactory(new PropertyValueFactory<Pedido, String>("TotalCalorias"));
    	Informacion_OrdenActual = FXCollections.observableArrayList();
    	tableView_InformacionOrdenActual.setItems(Informacion_OrdenActual);
    }

    /**
     * Limpia completamente los ArrayList locales relacionados a los productos.
     * Además, limpia la vista de las tablas de la interfaz para implementar los nuevos produtos provenientes del servidor.
     * @param event
     */
    @FXML
    public void recargarTablas(ActionEvent event){

    	miPedido = new Pedido();

    	listaInfoOrden.clear();
    	alimentos_OrdenActual.clear();
    	Informacion_OrdenActual.removeAll();

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
     * Método encargado de obtener todos los alimentos disponibles registrados en el servidor para luego ser cargados
     * y pintados sobre las tablas.
     */
	private void cargarTablas(){

		tablas = misTablas.cargarTablasDisponibles();

		if(tablas == null){
			System.out.println("Error!. No hay tablas que cargar");
		}
		else{
	    	this.inicializarTablaAlimentos();// Se inicializa la tabla
	    	registrarAlimentos(tablas);

	    	final ObservableList<Alimento> tablaEntradaCel = tablaView_Entradas_C.getSelectionModel().getSelectedItems();
	    	tablaEntradaCel.addListener(selectorTablaEntrada); // Seleciona las tuplas de la tabla Entrada

	    	final ObservableList<Alimento> tablaPlatoFuerteEntradaCel = tablaView_PlatosFuertes_C.getSelectionModel().getSelectedItems();
	    	tablaPlatoFuerteEntradaCel.addListener(selectorTablaPlatoFuerte);

	    	final ObservableList<Alimento> tablaPostresCel = tablaView_Postres_C.getSelectionModel().getSelectedItems();
	    	tablaPostresCel.addListener(selectorTablaPostre);

	    	final ObservableList<Alimento> tablaBebidasCel = tablaView_Bebidas_C.getSelectionModel().getSelectedItems();
	    	tablaBebidasCel.addListener(selectorTablaBebida);

	    	final ObservableList<Alimento> tablaOrdenCel = tableView_Orden.getSelectionModel().getSelectedItems();
	    	tablaOrdenCel.addListener(selectorTablaOrden);

	    	final ObservableList<Pedido> tablaInformacionOrdenCel = tableView_InformacionOrdenActual.getSelectionModel().getSelectedItems();
	    	tablaInformacionOrdenCel.addListener(selectorTablaInformacionOrden);

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
	    		alimentos_Postres.add(entrada);
	    	}

	    	for(Alimento entrada: listaBebidas){ //Inicializa la tabla con los datos del Alimento
	    		entrada.setNombre(entrada.getNombre());
	    		entrada.setTamanioPorcion(entrada.getTamanioPorcion());
	    		entrada.setCaloriasPorPorcion(entrada.getCaloriasPorPorcion());
	    		entrada.setCaloriasPorPieza(entrada.getCaloriasPorPieza());

	    		alimentos_Bebidas.add(entrada);
	    	}
		}
	}

	/**
	 * Registra el producto selecionado en el ArrayList de la orden atual del cliente.
	 * @param pAlimento
	 */
	private void registrarEnOrden(Alimento pAlimento){
		listaOrdenActual.add(pAlimento);
	}

	/**
	 * Registra el producto deseado a ordenar en el pedido del usuario.
	 * @param cantidadProductos
	 */
	private void agregarAlimentoTablaOrden(String cantidadProductos){
		Alimento miProducto = new Alimento();
		int i = 0;

		while(i != listaOrdenActual.size()){
			i++;
		}

		miProducto.setNombre(listaOrdenActual.get(i-1).getNombre());
		miProducto.setCantidad(cantidadProductos);
		alimentos_OrdenActual.add(miProducto);
	}

	/**
	 * Pinta el producto ordenado en la tabla de: Orden Actual.
	 * Además, se registra el alimento en ArrayList de precio y calorias.
	 * @param miPedido
	 */
	private void registrarInformacionOrdenEnTabla(Pedido miPedido){

		if(Informacion_OrdenActual.size() != 0){
			Informacion_OrdenActual.remove(0); // Evitar la acumulación. Una tabla con un solo elemento
			listaInfoOrden.remove(0);
		}
		Informacion_OrdenActual.add(miPedido);
		listaInfoOrden.add(miPedido);
	}

}