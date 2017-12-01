package modelo_Administrador;

import java.io.IOException;
import controlador.Network;
import modelo.Conexion;
import modelo.Mensaje;
import modelo.TipoMensaje;

public class ServidorDAO {
    Conexion miConexion;

    public ServidorDAO() {
        miConexion = new Conexion(Network.direcionIP, Network.puerto); // "localhost" , 6565
    }

    public boolean CerrarServidor() {
    	boolean cerro = false;
        Mensaje miMensaje = new Mensaje(TipoMensaje.TERMINATESERVER, "");

        try {
            // Abrir conexion y enviar el mensaje
            miConexion.abrirConexion();
            miConexion.obtenerFlujos();
            miConexion.enviarMensaje(miMensaje);

            // Revisar el mensaje de respuesta para determinar si tiene acceso
            Mensaje mensaje = miConexion.recibirMensaje();
            System.out.println("Mensaje recibido: "+ mensaje+"\nTipo Msg: "+mensaje.getTipo());

            if (mensaje.getTipo() == TipoMensaje.SUCCESSLOAD) {
            	cerro = true;
            }

            // Cerrar la conexion
            miConexion.cerrarConexion();
        }
        catch (IOException e) {
        	System.out.println(e);
        	System.out.println("Error. Verifique la conexion con el servidor.");
        	}

        return cerro;
    }

}
