package modelo_Administrador;

import java.io.IOException;

import controlador.Network;
import modelo.Conexion;
import modelo.Mensaje;
import modelo.TipoMensaje;
import productos.Alimento;

public class ModificarAlimentoDAO {
	Conexion miConexion;

    public ModificarAlimentoDAO() {
        miConexion = new Conexion(Network.direcionIP, Network.puerto); // "localhost"
    }

    public boolean modificarAlimento(Alimento pAlimento_1, Alimento pAlimento_2) {
    	boolean editado = false;
        Mensaje miMensaje = new Mensaje(TipoMensaje.EDITPRODUCT, pAlimento_1, pAlimento_2);

        try {
            // Abrir conexion y enviar el mensaje
            miConexion.abrirConexion();
            miConexion.obtenerFlujos();
            miConexion.enviarMensaje(miMensaje);

            // Revisar el mensaje de respuesta para determinar si tiene acceso
            Mensaje mensaje = miConexion.recibirMensaje();
            System.out.println("Mensaje recibido: "+ mensaje+"\nTipo Msg: "+mensaje.getTipo());

            if (mensaje.getTipo() == TipoMensaje.SUCCESSLOAD) {
            	editado = true;
            }

            // Cerrar la conexion
            miConexion.cerrarConexion();
        }
        catch (IOException e) {
        	System.out.println(e);
        	System.out.println("Error. Verifique la conexion con el servidor.");
        	}

        return editado;
    }
}
