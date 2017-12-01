package modelo;

import java.io.*;
import java.net.*;
import java.net.Socket;
import java.util.ArrayList;

import pedido.Pedido;
import productos.Alimento;
import registros.Bitacora;


/**
 * @author lpsoto - Jose Navarro
 * Clase encargada de manejar la base de datos de la aplicación (Servidor).
 * Contiene todo lo relacionado a los productos y clientes.
 * Además, se encarga de responder lo solicitado por el cliente.
 */
public class Servidor {

    private int puerto;
    private ServerSocket servidor;
    private Socket conexion;
    private Mensaje mensajeES;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;

    private ArrayList<Bitacora> listaBitacora = new ArrayList<Bitacora>();
    public MontoPedido montosActuales_pedidos = new MontoPedido();
    private ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>(); // Historial
    private ArrayList<Pedido> listaPedidos_Llevar = new ArrayList<Pedido>();
    private ArrayList<Pedido> listaPedidos_Express = new ArrayList<Pedido>();
    private int totalPedidos_Sitio;   private int totalPedidos_Llevar;
    private int totalPedidos_Express; private int totalPedidos;


    /**
     * @param pPuerto
     * Constructor del servidor, recibe un puerto e inicializa mediante recursividad al método 'iniciarServidor()'.
     */
    public Servidor(int pPuerto) {
        this.puerto = pPuerto;
        iniciarServidor();
    }

    /**
     * Método que inicializa el socket junto con su debida conexión.
     */
    public void iniciarServidor() {
        try {
            // Start the Server listening to port
            servidor = new ServerSocket(this.puerto);
            conexion = new Socket();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    /**
     *Se encarga de recibir la peticiones por parte del cliente.
     * @throws IOException
     */
    private void escucharPeticiones() throws IOException {
        // Wait here until a Client attempts to connect
        conexion = servidor.accept();
        System.out.println("\nRecibiendo mensaje de: " + conexion.getInetAddress().getHostName());
    }

    /**
     * Crea una referencia para un objeto que viene por parte del cliente.
     * @throws IOException
     */
    private void obtenerFlujos() throws IOException {
        // create a reference for an object to come from the client.
        salida = new ObjectOutputStream(conexion.getOutputStream());
        salida.flush();

        entrada = new ObjectInputStream(conexion.getInputStream());
    }

    /**
     * Método encargado de recibir por parte del clinete la petición o mensaje a realizar.
     * @return Mensaje
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Mensaje leerMensaje() throws IOException, ClassNotFoundException {
        // create a reference for an object to come from the client.
        mensajeES = new Mensaje();
        mensajeES = (Mensaje) entrada.readObject();
        System.out.println("Mensaje recibido: " + mensajeES.toString());
        return mensajeES;
    }

    /**
     * Método encargado de enviar un mensaje con el resultado debido de la pedicion recibida por el cliente.
     * @param pMsg
     * @param pEnvio
     */
    private void enviarMensaje(Mensaje pMsg, String pEnvio){
        mensajeES = pMsg;
        System.out.println(pEnvio + mensajeES.toString());
        try {
			salida.writeObject(mensajeES);
			salida.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * En caso de recibir un TipoMensaje no reconocido por el servidor se envia un mensaje de desconocido.
     * @throws IOException
     */
    private void enviarMensajeError() throws IOException {
        mensajeES = new Mensaje(TipoMensaje.UNKNOWN, " - Error desconocido");
        System.out.println("Enviando mensaje: " + mensajeES.toString());
        salida.writeObject(mensajeES);
        salida.flush();
    }

    /**
     * Cierra la conexión con el hilo.
     * @throws IOException
     */
    private void cerrarConexion() throws IOException {
        salida.close();
        entrada.close();
        conexion.close();
    }

    /**
     * Termina la conexión del socket y del servidor.
     * @throws IOException
     */
    private void terminarServidor() throws IOException {
        servidor.close();
    }

    /**
     * Método encargado de instanciar la clase Menu para cargar los archivos del Menu y de los usuarios.
     */
    private void cargarArchivosXML(){
    	Menu cargarArchivos = new Menu();

        cargarArchivos.cargarXmlUsuarios("archivosXML/Clientes.xml", TipoArchivo.CLIENTES);
        System.out.println("- Archivo: Clientes.xml: LISTO.");

        cargarArchivos.cargarXmlUsuarios("archivosXML/Administradores.xml", TipoArchivo.ADMINISTRADORES);
        System.out.println("- Archivo: Administradores.xml: LISTO.");

        cargarArchivos.cargarXmlMenu("archivosXML/menu.xml");
        System.out.println("- Archivo: menu.xml: LISTO.");
    }

    /**
     * Verifica si existe el usuario ingresado por parte del cliente.
     * @param pMensaje
     * @return Mensaje
     */
    private Mensaje verificarUsuario(String pMensaje){
    	Mensaje msg = new Mensaje(TipoMensaje.FAILLOGIN, " - Desconocido"); // Se establce de forma default que no existe

    	int i = 1; String nombreCuenta = ""; String clave = ""; boolean esta = false;

    	while(!pMensaje.substring((i-1),i).equals(";")){ // Se obtiene del mensaje el user y pass
    		nombreCuenta += pMensaje.substring((i-1),i);
    		i++;
    	}
    	i += 1; //Evitar la coma

    	while(i != pMensaje.length()+1){
    		clave += pMensaje.substring((i-1),i);
    		i++;
    	}

    	for(int x=0; x<Menu.cuentasClientes.size(); x++){ //Buscar el nombreCuenta/Clave en el ArrayList
    		if(Menu.cuentasClientes.get(x).getNombreCuenta().equals(nombreCuenta)
    				&& Menu.cuentasClientes.get(x).getClaveCuenta().equals(clave)){

    			esta = true;
    			msg = new Mensaje(TipoMensaje.SUCCESSLOGIN, "C"); // Se envia el Success y de contenido: 'C'-> de Cliente
    			registrarEnBitacora("Sesion iniciada: " + Menu.cuentasClientes.get(x).getNombreCuenta(), "Iniciar Sesion");
    		}
    	}

    	if(!esta){
    		for(int k=0; k<Menu.cuentasAdministradores.size(); k++){
    			if(Menu.cuentasAdministradores.get(k).getNombreCuenta().equals(nombreCuenta)
    					&& Menu.cuentasAdministradores.get(k).getClaveCuenta().equals(clave)){

    				esta = true;
    				msg = new Mensaje(TipoMensaje.SUCCESSLOGIN, "A");
    				registrarEnBitacora("Sesion iniciada: " + Menu.cuentasAdministradores.get(k).getNombreCuenta(), "Iniciar Sesion");
        		}
    		}
    	}
    	if(!esta){
    		registrarEnBitacora("Error al ingresar", "Iniciar sesion");
    	}
    	return msg;
    }

    /**
     * Carga las tablas (interfaz) con los productos actuales.
     * @return Mensaje
     */
    private Mensaje cargarTablasProdutos(){
    	Mensaje msg = new Mensaje(TipoMensaje.FAILLOAD, "");
    	Tabla tablas = new Tabla(Menu.platillosEntradas, Menu.platillosPrincipales, Menu.platillosPostres, Menu.platillosBebidas);

    	msg = new Mensaje(TipoMensaje.SUCCESSLOAD, tablas);
    	return msg;
    }

    private Mensaje cargarTablasProdutos_Disponibles(){
    	Mensaje msg = new Mensaje(TipoMensaje.FAILLOAD, "");
    	Tabla tablas = new Tabla(Menu.platillosEntradas_Disponibles, Menu.platillosPrincipales_Disponibles,
    			Menu.platillosPostres_Disponibles, Menu.platillosBebidas_Disponibles);

    	msg = new Mensaje(TipoMensaje.SUCCESSLOAD, tablas);
    	return msg;
    }

    private Mensaje cargarTablasProdutos_NoDisponibles(){
    	Mensaje msg = new Mensaje(TipoMensaje.FAILLOAD, "");
    	Tabla tablas = new Tabla(Menu.platillosEntradas_NoDisponibles, Menu.platillosPrincipales_NoDisponibles,
    			Menu.platillosPostres_NoDisponibles, Menu.platillosBebidas_NoDisponibles);

    	msg = new Mensaje(TipoMensaje.SUCCESSLOAD, tablas);
    	return msg;
    }

    /**
     * Carga la bitacora del servidor on los comandos recibidos hasta el momento.
     * @return Mensaje
     */
    private Mensaje cargarBitacora(){
    	Mensaje msg = new Mensaje(TipoMensaje.FAILLOAD, "");

    	msg = new Mensaje(TipoMensaje.SUCCESSLOAD, listaBitacora.toString());
    	return msg;
    }

    /**
     * Registra cualquier actividad por parte de Cliente o Administrador.
     * @param pAccion
     * @param pTipoAccion
     */
    private void registrarEnBitacora(String pAccion, String pTipoAccion){
    	Bitacora miAccion = new Bitacora();

    	miAccion.agregarAccion(pAccion, pTipoAccion);
    	listaBitacora.add(miAccion);
    }

    /**
     * Retona los cargos adicionales a los pedidos para llevar y express.
     * @return MontoPedido
     */
    private MontoPedido getMontosActuales(){
    	return this.montosActuales_pedidos;
    }

    /**
     * Carga los montos actuales de los pedidos para llevar y express.
     * @return Mensaje
     */
    private Mensaje cargarMontosPedido(){
    	Mensaje msg = new Mensaje(TipoMensaje.FAILLOAD, "");

    	msg = new Mensaje(TipoMensaje.SUCCESSLOAD, getMontosActuales());

    	return msg;
    }

    /**
     * Método encargado de registrar los pedidos de los clientes en el servidor
     * @param pPedido
     * @param pTipoOrden
     * @return Mensaje
     */
    private Mensaje registrarPedidoCliente(Pedido pPedido, String pTipoOrden){
    	Mensaje msg = new Mensaje(TipoMensaje.FAILLOAD, "");

    	this.listaPedidos.add(pPedido); // Lista general

    	if(pTipoOrden.equals("LL")){ //Llevar
    		listaPedidos_Llevar.add(pPedido); // Lista especifica
    		totalPedidos_Llevar += 1;
    	}
    	else{
    		if(pTipoOrden.equals("E")){ //Express
    			listaPedidos_Express.add(pPedido); // Lista especifica
    			totalPedidos_Express += 1;
    		}
    		else{
    			totalPedidos_Sitio += 1;
    		}
    	}

    	totalPedidos += 1;

    	int i = 0; // Actualiza la lista de Platillos nunca pedidos y el Ranking Top-10
    	while(i != pPedido.getProductosOrdenados_ArrayList().size()){

    		// Elimina el producto de la lista de Nunca pedidos
    		Menu.actualizarLista_PlatillosNuncaPedidos(pPedido.getProductosOrdenados_ArrayList().get(i));

    		// Añade el producto al Top-10 en caso de entrar al Ranking
    		Menu.agregarALista_PlatillosTop10(pPedido.getProductosOrdenados_ArrayList().get(i));
    		i++;
    	}

    	msg = new Mensaje(TipoMensaje.SUCCESSLOAD, "");

    	return msg;
    }

    /**
     * Carga el historial de los pedidos de los clientes registrados hasta el momento
     * @return Mensaje
     */
    private Mensaje cargarHistorial(){
    	Mensaje msg = new Mensaje(TipoMensaje.FAILLOAD, "");
    	String historial = null;

    	historial = "------------------------- General -------------------------\n";
    	int i = 0;
    	while(i != listaPedidos.size()){

    		historial += "- "+listaPedidos.get(i).getNombreCliente() + ":\n"+listaPedidos.get(i).getProductosOrdenados() +
    				listaPedidos.get(i).getPrecioTotal() +"\n";

    		i++;
    	}i = 0;

    	historial += "\n------------------------- Para llevar -------------------------\n";
    	while(i != listaPedidos_Llevar.size()){

    		historial += "- "+listaPedidos_Llevar.get(i).getNombreCliente() + ":\n"+listaPedidos_Llevar.get(i).getProductosOrdenados() +
    				+listaPedidos_Llevar.get(i).getPrecioTotal() +"\n";

    		i++;
    	}i = 0;

    	historial += "\n------------------------- Express -------------------------\n";
    	while(i != listaPedidos_Express.size()){

    		historial += "- "+listaPedidos_Express.get(i).getNombreCliente() + ":\n"+listaPedidos_Express.get(i).getProductosOrdenados() +
    				+listaPedidos_Express.get(i).getPrecioTotal() +"\n";

    		i++;
    	}

    	msg = new Mensaje(TipoMensaje.SUCCESSLOAD, historial);
    	return msg;
    }

    /**
     * Método encargado de establecer los cargos extras a los pedidos de llevar y express
     * @param pMonto
     * @param pTipo
     * @return Mensaje
     */
    public Mensaje establecerMontosExtrasPedidos(String pMonto, String pTipo){
    	Mensaje msg = new Mensaje(TipoMensaje.FAILLOAD, "");

    	double monto = Double.parseDouble(pMonto);

    	if(pTipo.equals("LL")){
    		montosActuales_pedidos.setMontoAdicional_ParaLlevar(monto);
    		msg = new Mensaje(TipoMensaje.SUCCESSLOAD, montosActuales_pedidos);
    	}
    	else{
    		if(pTipo.equals("E")){
    			montosActuales_pedidos.setMontoAdicional_Express(monto);
    			msg = new Mensaje(TipoMensaje.SUCCESSLOAD, montosActuales_pedidos);
    		}
    	}
    	return msg;
    }

    /**
     * Método encargado de registrar un nuevo producto en el servidor
     * @param pAlimento
     * @return Mensaje
     */
    public Mensaje registrarNuevoAlimento(Alimento pAlimento){
    	Mensaje msg = new Mensaje(TipoMensaje.FAILLOAD, "");

    	Menu.agregarAlimento(pAlimento.getCodigo(), pAlimento.getNombre(), pAlimento.getDescripcion(), pAlimento.getTamanioPorcion(),
    			pAlimento.getPiezasPorcion(), pAlimento.getCaloriasUnaPorcion(), pAlimento.getCaloriasPorPieza(), pAlimento.getPrecio());

    	msg = new Mensaje(TipoMensaje.SUCCESSLOAD, "");
    	return msg;
    }

    /**
     * Modifica el producto escogido por el administrador con sus atributos actualizados o editados
     * @param pAlimento_Antiguo
     * @param pAlimento_Editado
     * @return Mensaje
     */
    public Mensaje modificarProducto(Alimento pAlimento_Antiguo, Alimento pAlimento_Editado){
    	Mensaje msg = new Mensaje(TipoMensaje.FAILLOAD, "");

    	Menu.modificarAlimento(pAlimento_Antiguo, pAlimento_Editado);

    	msg = new Mensaje(TipoMensaje.SUCCESSLOAD, "");
    	return msg;
    }

    /**
     * Método encargado de eliminar el producto escogido por el administrador
     * @param pAlimento
     * @return Mensaje
     */
    public Mensaje eliminarProducto(Alimento pAlimento){
    	Mensaje msg = new Mensaje(TipoMensaje.FAILLOAD, "");

    	Menu.eliminarAlimento(pAlimento);

    	msg = new Mensaje(TipoMensaje.SUCCESSLOAD, "");
    	return msg;
    }

    /**
     * Calcula el procentaje de la cantidad de los pedidos hasta el momento
     * @return Mensaje
     */
    public Mensaje calcularPorcentajePedidos(){

    	Mensaje msg = new Mensaje(TipoMensaje.FAILLOAD, "");

    	String porcentajeTotal = "";
    	float porcentajeSitio = 0;
    	float porcentajeLlevar = 0;
    	float porcentajeExpress = 0;

    	System.out.println("TOTAL: " + totalPedidos);
    	System.out.println("Total sitios: "+totalPedidos_Sitio);
    	System.out.println("Total llevar: "+totalPedidos_Llevar);
    	System.out.println("Total express: "+totalPedidos_Express);

    	try{
	    	porcentajeSitio = (float)totalPedidos_Sitio/(float)totalPedidos *100; System.out.println("Porcentaje SITIO: "+porcentajeSitio);
    	}catch(ArithmeticException e){}

    	try{
	    	porcentajeLlevar = (float)totalPedidos_Llevar/(float)totalPedidos *100; System.out.println("Porcentaje LLEVAR: "+porcentajeLlevar);
    	}catch(ArithmeticException e){}

    	try{
	    	porcentajeExpress = (float)totalPedidos_Express/(float)totalPedidos *100; System.out.println("Porcentaje EXPRESS: "+porcentajeExpress);
    	}catch(ArithmeticException e){}


    	porcentajeTotal += "Sitio " + String.valueOf(porcentajeSitio) +"%";
    	porcentajeTotal += "  -  Para Llevar: " + String.valueOf(porcentajeLlevar)+"%";
    	porcentajeTotal += "  -  Express: " + String.valueOf(porcentajeExpress)+"%";

    	msg = new Mensaje(TipoMensaje.SUCCESSLOAD, porcentajeTotal);

    	return msg;
    }

    /**
     * Carga toda la lista de productos nunc ordenados
     * @return Mensaje
     */
    public Mensaje obtenerLista_PlatillosNuncaPedidos(){

    	Mensaje msg = new Mensaje(TipoMensaje.FAILLOAD, "");
    	String lista = "";

    	int i = 0;
    	while(i < Menu.getPlatilloNuncaPedidos().size()){
    		lista += Menu.getPlatilloNuncaPedidos().get(i).getCodigo() +": " +
    				Menu.getPlatilloNuncaPedidos().get(i).getNombre()+"\n";
    		i++;
    	}

    	msg = new Mensaje(TipoMensaje.SUCCESSLOAD, lista);
    	return msg;
    }

    /**
     * Carga toda la lista del ranking de los 10 productos más pedidos
     * @return Mensaje
     */
    public Mensaje obtenerLista_ProductosTopDiez(){

    	Mensaje msg = new Mensaje(TipoMensaje.FAILLOAD, "");
    	String lista = "";

    	int i = 0;
    	while(i < Menu.getProductosTop10().size()){
    		lista += Menu.getProductosTop10().get(i).getCantidad() +"x " +
    				Menu.getProductosTop10().get(i).getNombre()+"\n";
    		i++;
    	}

    	msg = new Mensaje(TipoMensaje.SUCCESSLOAD, lista);
    	return msg;
    }

    /**
     * Encargado de establecer como visible un producto.
     * @param pAlimento
     * @return Mensaje
     */
    public Mensaje establecerProducto_Visible(Alimento pAlimento){
    	Mensaje msg = new Mensaje(TipoMensaje.FAILLOAD, "");
    	boolean respuesta = false;
    	String mensaje = "";

    	respuesta = Menu.habilitarAlimentos(pAlimento);

    	if(respuesta){
    		mensaje = "Se ha cambiado correctamente a: Visible!";
    	}
    	else{
    		mensaje = "Error, el producto ya está en: Visible!";
    	}

    	msg = new Mensaje(TipoMensaje.SUCCESSLOAD, mensaje);
    	return msg;
    }

    /**
     * Encargado de establecer como no visible un producto.
     * @param pAlimento
     * @return Mensaje
     */
    public Mensaje establecerProducto_NoVisible(Alimento pAlimento){
    	Mensaje msg = new Mensaje(TipoMensaje.FAILLOAD, "");
    	boolean respuesta = false;
    	String mensaje = "";

    	respuesta = Menu.inhabilitarAlimento(pAlimento);

    	if(respuesta){
    		mensaje = "Se ha cambiado correctamente a: No visible";
    	}
    	else{
    		mensaje = "Error, el producto ya está en: No visible!";
    	}

    	msg = new Mensaje(TipoMensaje.SUCCESSLOAD, mensaje);
    	return msg;
    }

    /**
     * main encargado de iniciar el servidor, y entrar a un bucle donde reciba todas las petiiones por parte de los usuarios
     * @param args
     */
    public static void main(String[] args) {
    	Servidor server = new Servidor(6565);
    	Mensaje mensajeRecibido;

    	SistemaPyme miPyme = new SistemaPyme("JHF"); // Por default
    	server.registrarEnBitacora("Pyme registrada: " + miPyme.getNombrePyme(), "Registro");

        server.cargarArchivosXML();
        System.out.println("\n=== Servidor listo ===\n- Esperando peticiones...\n");

        try {
        	while (true) {
        		server.escucharPeticiones();
                server.obtenerFlujos();
                mensajeRecibido = server.leerMensaje();
                boolean envioMsg = false;

                if(mensajeRecibido.getTipo() == TipoMensaje.LOGIN) { //Iniciar sesion
                	server.registrarEnBitacora("Iniciar Sesion", "Consulta");
                	Mensaje resultado = server.verificarUsuario(mensajeRecibido.getContenido());
                	server.enviarMensaje(resultado, "Enviando mensaje LOGIN: ");
                	server.cerrarConexion();
                	envioMsg = true;
                }

                if(mensajeRecibido.getTipo() == TipoMensaje.LOADTABLESVISIBLE){
                	Mensaje resultado = server.cargarTablasProdutos_Disponibles();
                	server.enviarMensaje(resultado, "Enviando mensaje Cargar tablas disponibles: ");
                	server.cerrarConexion();
                	server.registrarEnBitacora("Cargar productos", "Cargar recursos");
                	envioMsg = true;
                }

                if(mensajeRecibido.getTipo() == TipoMensaje.READAMOUNT){
                	Mensaje resultado = server.cargarMontosPedido();
                	server.enviarMensaje(resultado, "Enviando mensaje Cargar montos: ");
                	server.cerrarConexion();
                	server.registrarEnBitacora("Cargar montos adicionales del pedido", "Consulta");
                	envioMsg = true;
                }

                if(mensajeRecibido.getTipo() == TipoMensaje.ORDER){
                	Mensaje resultado = server.registrarPedidoCliente(mensajeRecibido.getPedido(), mensajeRecibido.getContenido());
                	server.enviarMensaje(resultado, "Enviando mensaje Orden: ");
                	server.cerrarConexion();
                	server.registrarEnBitacora("Se ha realizado un pedido: "+mensajeRecibido.getNombre(), "Pedido");
                	envioMsg = true;
                }

                // -------------- ADMINISTRADOR -------------- //

                if(mensajeRecibido.getTipo() == TipoMensaje.LOADTABLES){
                	Mensaje resultado = server.cargarTablasProdutos();
                	server.enviarMensaje(resultado, "Enviando mensaje Cargar tablas: ");
                	server.cerrarConexion();
                	server.registrarEnBitacora("Cargar productos", "Cargar recursos");
                	envioMsg = true;
                }

                if(mensajeRecibido.getTipo() == TipoMensaje.LOADTABLESNONVISIBLE){
                	Mensaje resultado = server.cargarTablasProdutos_NoDisponibles();
                	server.enviarMensaje(resultado, "Enviando mensaje Cargar tablas no disponibles: ");
                	server.cerrarConexion();
                	server.registrarEnBitacora("Cargar productos", "Cargar recursos");
                	envioMsg = true;
                }

                if(mensajeRecibido.getTipo() == TipoMensaje.READBINNACLE){
                	Mensaje resultado = server.cargarBitacora();
                	server.enviarMensaje(resultado, "Enviando mensaje Cargar bitacora: ");
                	server.cerrarConexion();
                	envioMsg = true;
                }

                if(mensajeRecibido.getTipo() == TipoMensaje.READRECORDORDER){
                	Mensaje resultado = server.cargarHistorial();
                	server.enviarMensaje(resultado, "Enviando mensaje Cargar historial: ");
                	server.cerrarConexion();
                	envioMsg = true;
                }

                if(mensajeRecibido.getTipo() == TipoMensaje.SETAMOUNT){
                	Mensaje resultado = server.establecerMontosExtrasPedidos(mensajeRecibido.getContenido(), mensajeRecibido.getTipoPedido());
                	server.enviarMensaje(resultado, "Enviando mensaje Establecer monto: ");
                	server.cerrarConexion();
                	server.registrarEnBitacora("Se ha modificado el monto de: " + mensajeRecibido.getTipoPedido()+", por: "+mensajeRecibido.getContenido(), "Edicion");
                	envioMsg = true;
                }

                if(mensajeRecibido.getTipo() == TipoMensaje.ADDPRODUCT){
                	Mensaje resultado = server.registrarNuevoAlimento(mensajeRecibido.getAlimento());
                	server.enviarMensaje(resultado, "Enviando mensaje Anadir producto: ");
                	server.cerrarConexion();
                	server.registrarEnBitacora("Se ha anadido un nuevo producto: "+mensajeRecibido.getAlimento().getNombre(), "Edicion");
                	envioMsg = true;
                }

                if(mensajeRecibido.getTipo() == TipoMensaje.EDITPRODUCT){
                	Mensaje resultado = server.modificarProducto(mensajeRecibido.getAlimento(), mensajeRecibido.getAlimento_2());
                	server.enviarMensaje(resultado, "Enviando mensaje Editar producto: ");
                	server.cerrarConexion();
                	server.registrarEnBitacora("Se ha modificado un producto: "+mensajeRecibido.getAlimento().getNombre(), "Edicion");
                	envioMsg = true;
                }

                if(mensajeRecibido.getTipo() == TipoMensaje.DELETEPRODUCT){
                	Mensaje resultado = server.eliminarProducto(mensajeRecibido.getAlimento());
                	server.enviarMensaje(resultado, "Enviando mensaje Eliminar producto: ");
                	server.cerrarConexion();
                	server.registrarEnBitacora("Se ha eliminado un producto: "+mensajeRecibido.getAlimento().getNombre(), "Edicion");
                	envioMsg = true;
                }

                if(mensajeRecibido.getTipo() == TipoMensaje.LOADPORCENTORDER){
                	Mensaje resultado = server.calcularPorcentajePedidos();
                	server.enviarMensaje(resultado, "Enviando mensaje Cargar porcentajes: ");
                	server.cerrarConexion();
                	server.registrarEnBitacora("Se han consultado los cargos de los pedidos", "Consulta");
                	envioMsg = true;
                }

                if(mensajeRecibido.getTipo() == TipoMensaje.LOADPRODUCTNEVERORDER){
                	Mensaje resultado = server.obtenerLista_PlatillosNuncaPedidos();
                	server.enviarMensaje(resultado, "Enviando mensaje Lista productos nunca pedidos: ");
                	server.cerrarConexion();
                	server.registrarEnBitacora("Se han consultado la lista de productos nunca pedidos", "Consulta");
                	envioMsg = true;
                }

                if(mensajeRecibido.getTipo() == TipoMensaje.LOADPRODUCTTOTEN){
                	Mensaje resultado = server.obtenerLista_ProductosTopDiez();
                	server.enviarMensaje(resultado, "Enviando mensaje Lista productos Top-10: ");
                	server.cerrarConexion();
                	server.registrarEnBitacora("Se han consultado la lista de productos Top-10", "Consulta");
                	envioMsg = true;
                }

                if(mensajeRecibido.getTipo() == TipoMensaje.SETVISIBLEPRODUCT){
                	Mensaje resultado = server.establecerProducto_Visible(mensajeRecibido.getAlimento());
                	server.enviarMensaje(resultado, "Enviando mensaje Producto visible ");
                	server.cerrarConexion();
                	server.registrarEnBitacora("Se ha cambiado a visible: "+mensajeRecibido.getAlimento().getNombre(), "Edicion");
                	envioMsg = true;
                }

                if(mensajeRecibido.getTipo() == TipoMensaje.SETNONVISIBLEPRODUCT){
                	Mensaje resultado = server.establecerProducto_NoVisible(mensajeRecibido.getAlimento());
                	server.enviarMensaje(resultado, "Enviando mensaje Producto visible ");
                	server.cerrarConexion();
                	server.registrarEnBitacora("Se ha cambiado a no visible: "+mensajeRecibido.getAlimento().getNombre(), "Edicion");
                	envioMsg = true;
                }

                if (mensajeRecibido.getTipo() == TipoMensaje.TERMINATESERVER) {
                	Mensaje resultado = new Mensaje();
                	resultado.setTipo(TipoMensaje.SUCCESSLOAD);
                	server.enviarMensaje(resultado, "");

                    server.cerrarConexion();
                    server.terminarServidor();
                    server.registrarEnBitacora("Cerrar servidor", "Terminar conexiones");
                    envioMsg = true;
                }

                if(!envioMsg){
                	server.enviarMensajeError();
                	server.cerrarConexion();
                    server.registrarEnBitacora("Accion invalida", "Error");
                }
            }
        }
        catch (IOException e) {System.out.println(e);}
        catch (ClassNotFoundException e) {System.out.println(e);}
    }

}
