package modelo;

import java.io.IOException;

import controlador.Network;

public class MontoPedidoDAO {

    Conexion miConexion;

    public MontoPedidoDAO() {
        miConexion = new Conexion(Network.direcionIP, Network.puerto); // "localhost"
    }

    public MontoPedido cargarMontosActuales_Pedido() {
    	MontoPedido montosPedido = null;
        Mensaje miMensaje = new Mensaje(TipoMensaje.READAMOUNT, " ");

        try {
            // Abrir conexion y enviar el mensaje
            miConexion.abrirConexion();
            miConexion.obtenerFlujos();
            miConexion.enviarMensaje(miMensaje);

            // Revisar el mensaje de respuesta para determinar si tiene acceso
            Mensaje mensaje = miConexion.recibirMensaje();
            System.out.println("Mensaje recibido: "+ mensaje+"\nTipo Msg: "+mensaje.getTipo());

            if (mensaje.getTipo() == TipoMensaje.SUCCESSLOAD) {

            	montosPedido = mensaje.getMontosPedidos();
            }

            // Cerrar la conexion
            miConexion.cerrarConexion();
        }
        catch (IOException e) {
        	System.out.println(e);
        	System.out.println("Error. Verifique la conexion con el servidor.");
        	}

        return montosPedido;
    }
}
