package modelo_Administrador;

import java.io.IOException;

import controlador.Network;
import modelo.Conexion;
import modelo.Mensaje;
import modelo.TipoMensaje;

public class HistorialProductosNuncaPedidosDAO {
	Conexion miConexion;

    public HistorialProductosNuncaPedidosDAO() {
        miConexion = new Conexion(Network.direcionIP, Network.puerto); // "localhost"
    }

    public String consultarListaProductosNuncaPedidos() {
    	String lista = null;
        Mensaje miMensaje = new Mensaje(TipoMensaje.LOADPRODUCTNEVERORDER, "");

        try {
            // Abrir conexion y enviar el mensaje
            miConexion.abrirConexion();
            miConexion.obtenerFlujos();
            miConexion.enviarMensaje(miMensaje);

            // Revisar el mensaje de respuesta para determinar si tiene acceso
            Mensaje mensaje = miConexion.recibirMensaje();
            System.out.println("Mensaje recibido: "+ mensaje+"\nTipo Msg: "+mensaje.getTipo());

            if (mensaje.getTipo() == TipoMensaje.SUCCESSLOAD) {
            	lista = mensaje.getContenido();
            }

            // Cerrar la conexion
            miConexion.cerrarConexion();
        }
        catch (IOException e) {
        	System.out.println(e);
        	System.out.println("Error. Verifique la conexion con el servidor.");
        	}

        return lista;
    }
}
