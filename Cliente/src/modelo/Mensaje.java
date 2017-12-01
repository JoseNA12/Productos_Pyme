package modelo;

import java.io.Serializable;

import pedido.Pedido;
import productos.Alimento;

public class Mensaje implements Serializable {

	private static final long serialVersionUID = -7052787477443275175L;
	private TipoMensaje tipo;
    private String contenido;
    private Tabla tablas;
    private MontoPedido montosPedido;
    private Pedido miPedido;
    private String nombrePedido;
    private String tipoPedido;
    private Alimento miAlimento;
    private Alimento miAlimento_2;


    public Mensaje() {
        setTipo(TipoMensaje.UNKNOWN);
        setMensaje("");
    }

    public Mensaje(TipoMensaje pTipo, String pContenido) {
        this.tipo = pTipo;
        this.contenido = pContenido;
    }

    public Mensaje(TipoMensaje pTipo, Tabla pContenido) {
        this.tipo = pTipo;
        this.tablas = pContenido;
    }

    public Mensaje(TipoMensaje pTipo, MontoPedido pMontos){
    	this.tipo = pTipo;
    	this.montosPedido = pMontos;
    }

    public Mensaje(TipoMensaje pTipo, Pedido pPedido, String pTipoOrden, String nombre){
    	this.tipo = pTipo;
    	this.miPedido = pPedido;
    	this.contenido = pTipoOrden;
    	this.nombrePedido = nombre;
    }

    public Mensaje(TipoMensaje pTipo, String pMonto, String pTipoPedido) {
        this.tipo = pTipo;
        this.contenido = pMonto;
        this.tipoPedido = pTipoPedido;
    }

    public Mensaje(TipoMensaje pTipo, Alimento pAlimento){
    	this.tipo = pTipo;
    	this.miAlimento = pAlimento;
    }

    public Mensaje(TipoMensaje pTipo, Alimento pAlimento, Alimento pAlimento2){
    	this.tipo = pTipo;
    	this.miAlimento = pAlimento;
    	this.miAlimento_2 = pAlimento2;
    }

    public void setTipo(TipoMensaje pTipo) {
        this.tipo = pTipo;
    }

    public TipoMensaje getTipo() {
        return this.tipo;
    }

    public String getContenido() {
        return this.contenido;
    }

    public void setMensaje(String pMensaje) {
        this.contenido = pMensaje;
    }

    public String toString() {
        return this.tipo + this.contenido;
    }

    public Tabla getTablaContenido(){
    	return this.tablas;
    }

    public MontoPedido getMontosPedidos(){
    	return this.montosPedido;
    }

    public String getNombre(){
    	return this.nombrePedido;
    }
}