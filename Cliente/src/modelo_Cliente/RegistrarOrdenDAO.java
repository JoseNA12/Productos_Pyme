package modelo_Cliente;

import java.io.IOException;
import controlador.Network;
import modelo.Conexion;
import modelo.Mensaje;
import modelo.TipoMensaje;
import pedido.Pedido;

public class RegistrarOrdenDAO {
	Conexion miConexion;

    public RegistrarOrdenDAO() {
        miConexion = new Conexion(Network.direcionIP, Network.puerto); // "localhost"
    }

    public String registrarOrdenServidor(Pedido pPedido, String tipoOrden, String nombre) {
    	String respuesta = null;
        Mensaje miMensaje = new Mensaje(TipoMensaje.ORDER, pPedido, tipoOrden, nombre);

        try {
            // Abrir conexion y enviar el mensaje
            miConexion.abrirConexion();
            miConexion.obtenerFlujos();
            miConexion.enviarMensaje(miMensaje);

            // Revisar el mensaje de respuesta para determinar si tiene acceso
            Mensaje mensaje = miConexion.recibirMensaje();
            System.out.println("Mensaje recibido: "+ mensaje+"\nTipo Msg: "+mensaje.getTipo());

            if (mensaje.getTipo() == TipoMensaje.SUCCESSLOAD) {
            	respuesta = "Listo, orden reservado en servidor";
            }

            // Cerrar la conexion
            miConexion.cerrarConexion();
        }
        catch (IOException e) {
        	System.out.println(e);
        	System.out.println("Error. Verifique la conexion con el servidor.");
        	}

        return respuesta;
    }
}
