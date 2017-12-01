package modelo;

import java.io.IOException;

import controlador.Network;

public class TablaDAO {

    Conexion miConexion;

    public TablaDAO() {
        miConexion = new Conexion(Network.direcionIP, Network.puerto); // "localhost"
    }

    public Tabla cargarTablas() {
        Tabla tabla = null;
        Mensaje miMensaje = new Mensaje(TipoMensaje.LOADTABLES, "");

        try {
            // Abrir conexion y enviar el mensaje
            miConexion.abrirConexion();
            miConexion.obtenerFlujos();
            miConexion.enviarMensaje(miMensaje);

            // Revisar el mensaje de respuesta para determinar si tiene acceso
            Mensaje mensaje = miConexion.recibirMensaje();
            System.out.println("Mensaje recibido: "+ mensaje+"\nTipo Msg: "+mensaje.getTipo()+"\nContenido Msg: " + mensaje.getContenido());

            if (mensaje.getTipo() == TipoMensaje.SUCCESSLOAD) {
                tabla = new Tabla(mensaje.getTablaContenido().getTablaEntradas(), mensaje.getTablaContenido().getTablaPrincipal(),
                		mensaje.getTablaContenido().getTablaPostres(), mensaje.getTablaContenido().getTablaBebidas());
            }

            // Cerrar la conexion
            miConexion.cerrarConexion();
        }
        catch (IOException e) {
        	System.out.println(e);
        	System.out.println("Error. Verifique la conexion con el servidor.");
        	}

        return tabla;
    }

    public Tabla cargarTablasDisponibles() {
        Tabla tabla = null;
        Mensaje miMensaje = new Mensaje(TipoMensaje.LOADTABLESVISIBLE, "");

        try {
            // Abrir conexion y enviar el mensaje
            miConexion.abrirConexion();
            miConexion.obtenerFlujos();
            miConexion.enviarMensaje(miMensaje);

            // Revisar el mensaje de respuesta para determinar si tiene acceso
            Mensaje mensaje = miConexion.recibirMensaje();
            System.out.println("Mensaje recibido: "+ mensaje+"\nTipo Msg: "+mensaje.getTipo()+"\nContenido Msg: " + mensaje.getContenido());

            if (mensaje.getTipo() == TipoMensaje.SUCCESSLOAD) {
                tabla = new Tabla(mensaje.getTablaContenido().getTablaEntradas(), mensaje.getTablaContenido().getTablaPrincipal(),
                		mensaje.getTablaContenido().getTablaPostres(), mensaje.getTablaContenido().getTablaBebidas());
            }

            // Cerrar la conexion
            miConexion.cerrarConexion();
        }
        catch (IOException e) {
        	System.out.println(e);
        	System.out.println("Error. Verifique la conexion con el servidor.");
        	}

        return tabla;
    }

    public Tabla cargarTablasNoDisponibles() {
        Tabla tabla = null;
        Mensaje miMensaje = new Mensaje(TipoMensaje.LOADTABLESNONVISIBLE, "");

        try {
            // Abrir conexion y enviar el mensaje
            miConexion.abrirConexion();
            miConexion.obtenerFlujos();
            miConexion.enviarMensaje(miMensaje);

            // Revisar el mensaje de respuesta para determinar si tiene acceso
            Mensaje mensaje = miConexion.recibirMensaje();
            System.out.println("Mensaje recibido: "+ mensaje+"\nTipo Msg: "+mensaje.getTipo()+"\nContenido Msg: " + mensaje.getContenido());

            if (mensaje.getTipo() == TipoMensaje.SUCCESSLOAD) {
                tabla = new Tabla(mensaje.getTablaContenido().getTablaEntradas(), mensaje.getTablaContenido().getTablaPrincipal(),
                		mensaje.getTablaContenido().getTablaPostres(), mensaje.getTablaContenido().getTablaBebidas());
            }

            // Cerrar la conexion
            miConexion.cerrarConexion();
        }
        catch (IOException e) {
        	System.out.println(e);
        	System.out.println("Error. Verifique la conexion con el servidor.");
        	}

        return tabla;
    }
}
