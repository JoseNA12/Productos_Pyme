package modelo_Administrador;

import java.io.IOException;

import controlador.Network;
import modelo.Conexion;
import modelo.Mensaje;
import modelo.TipoMensaje;

public class PorcentajesPedidosDAO {
    Conexion miConexion;

    public PorcentajesPedidosDAO() {
        miConexion = new Conexion(Network.direcionIP, Network.puerto); // "localhost"
    }

    public String cargarPorcentajes() {
    	String porcentajes = null;
        Mensaje miMensaje = new Mensaje(TipoMensaje.LOADPORCENTORDER, "");

        try {
            // Abrir conexion y enviar el mensaje
            miConexion.abrirConexion();
            miConexion.obtenerFlujos();
            miConexion.enviarMensaje(miMensaje);

            // Revisar el mensaje de respuesta para determinar si tiene acceso
            Mensaje mensaje = miConexion.recibirMensaje();
            System.out.println("Mensaje recibido: "+ mensaje+"\nTipo Msg: "+mensaje.getTipo());

            if (mensaje.getTipo() == TipoMensaje.SUCCESSLOAD) {
            	porcentajes = mensaje.getContenido();
            }

            // Cerrar la conexion
            miConexion.cerrarConexion();
        }
        catch (IOException e) {
        	System.out.println(e);
        	System.out.println("Error. Verifique la conexion con el servidor.");
        	}

        return porcentajes;
    }
}
