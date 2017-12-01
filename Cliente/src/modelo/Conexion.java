/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.io.*;
import java.net.*;
/**
 * @author lpsoto
 */
public class Conexion {
    private String servidorIP;
    private int servidorPuerto;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private Mensaje mensajeES;
    private Socket cliente;

    public Conexion(String pDireccionIP, int pPuerto) {
        this.servidorIP = pDireccionIP;
        this.servidorPuerto = pPuerto;
    }

    public void abrirConexion() {
       try {
            cliente = new Socket(this.servidorIP, this.servidorPuerto);
       }
       catch (IOException e) {System.out.println(e);}
    }

    public void cerrarConexion() {
        try {
            cliente.close();
        }
        catch (IOException e) {System.out.println(e);}
    }

    public void obtenerFlujos() throws IOException   {

		salida = new ObjectOutputStream(cliente.getOutputStream());
		salida.flush();
		entrada = new ObjectInputStream(cliente.getInputStream());

    }


    public void enviarMensaje(Mensaje miMensaje) {
        // Create the ObjectOutputStream
        try {
            // Write the myTextMessage object to the OutputStream
            salida.writeObject(miMensaje);
            salida.flush();
        }
        catch (IOException e) {System.out.println(e);}
    }

	@SuppressWarnings("finally")
	public Mensaje recibirMensaje() {

        mensajeES = new Mensaje();
        try {
            mensajeES = (Mensaje) entrada.readObject();
        }
        catch (ClassNotFoundException e) {System.out.println(e);}
        catch (IOException e) {System.out.println(e);}
        catch (Exception e) {System.out.println(e);}

        finally {
            return mensajeES;
        }
    }

}
