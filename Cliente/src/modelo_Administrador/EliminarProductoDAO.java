package modelo_Administrador;

import java.io.IOException;

import controlador.Network;
import modelo.Conexion;
import modelo.Mensaje;
import modelo.TipoMensaje;
import productos.Alimento;

public class EliminarProductoDAO {

    Conexion miConexion;

    public EliminarProductoDAO() {
        miConexion = new Conexion(Network.direcionIP, Network.puerto); // "localhost"
    }

    public boolean eliminarProducto(Alimento pAlimento) {
    	boolean seElimino = false;
        Mensaje miMensaje = new Mensaje(TipoMensaje.DELETEPRODUCT, pAlimento);

        try {
            // Abrir conexion y enviar el mensaje
            miConexion.abrirConexion();
            miConexion.obtenerFlujos();
            miConexion.enviarMensaje(miMensaje);

            // Revisar el mensaje de respuesta para determinar si tiene acceso
            Mensaje mensaje = miConexion.recibirMensaje();
            System.out.println("Mensaje recibido: "+ mensaje+"\nTipo Msg: "+mensaje.getTipo());

            if (mensaje.getTipo() == TipoMensaje.SUCCESSLOAD) {
            	seElimino = true;
            }

            // Cerrar la conexion
            miConexion.cerrarConexion();
        }
        catch (IOException e) {
        	System.out.println(e);
        	System.out.println("Error. Verifique la conexion con el servidor.");
        	}

        return seElimino;
    }
}
