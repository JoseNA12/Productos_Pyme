package modelo_Administrador;

import java.io.IOException;

import controlador.Network;
import modelo.Conexion;
import modelo.Mensaje;
import modelo.TipoMensaje;

public class HistorialPedidosDAO {
    Conexion miConexion;

    public HistorialPedidosDAO() {
        miConexion = new Conexion(Network.direcionIP, Network.puerto); // "localhost"
    }

    public String cargarHistorialPedidos() {
    	String historial = null;
        Mensaje miMensaje = new Mensaje(TipoMensaje.READRECORDORDER, "");

        try {
            // Abrir conexion y enviar el mensaje
            miConexion.abrirConexion();
            miConexion.obtenerFlujos();
            miConexion.enviarMensaje(miMensaje);

            // Revisar el mensaje de respuesta para determinar si tiene acceso
            Mensaje mensaje = miConexion.recibirMensaje();
            System.out.println("Mensaje recibido: "+ mensaje+"\nTipo Msg: "+mensaje.getTipo());

            if (mensaje.getTipo() == TipoMensaje.SUCCESSLOAD) {
            	historial = mensaje.getContenido();
            }

            // Cerrar la conexion
            miConexion.cerrarConexion();
        }
        catch (IOException e) {
        	System.out.println(e);
        	System.out.println("Error. Verifique la conexion con el servidor.");
        	}

        return historial;
    }
}
