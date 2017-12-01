package modelo;

import java.io.IOException;

import controlador.Network;

/**
 *
 * Registro de transacciones con el servidor
 *
 */
public class UsuarioDAO {
    Conexion miConexion;

    public UsuarioDAO() {
        miConexion = new Conexion(Network.direcionIP, Network.puerto); // "localhost"
    }

    public Usuario verificarCliente(String pNombreUsuario, String pClave) {
        Usuario usuario = null;
        Mensaje miMensaje = new Mensaje(TipoMensaje.LOGIN, pNombreUsuario + ";" + pClave);

        try {
            // Abrir conexion y enviar el mensaje
            miConexion.abrirConexion();
            miConexion.obtenerFlujos();
            miConexion.enviarMensaje(miMensaje);

            // Revisar el mensaje de respuesta para determinar si tiene acceso
            Mensaje mensaje = miConexion.recibirMensaje();
            System.out.println("Mensaje recibido: "+ mensaje); System.out.println("Tipo Msg: "+mensaje.getTipo());
            System.out.println("Contenido Msg: " + mensaje.getContenido());

            if (mensaje.getTipo() == TipoMensaje.SUCCESSLOGIN) {
                String tipoUsuario = mensaje.getContenido();
                usuario = new Usuario();
                usuario.setTipo(tipoUsuario);
                usuario.setClave(pClave);
                usuario.setNombreUsuario(pNombreUsuario);
            }

            // Cerrar la conexion
            miConexion.cerrarConexion();
        }
        catch (IOException e) {
        	System.out.println(e);
        	System.out.println("Error. Verifique la conexion con el servidor.");
        	}

        return usuario;
    }
}
