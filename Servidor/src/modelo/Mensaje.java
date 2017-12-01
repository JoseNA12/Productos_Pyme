package modelo;

import java.io.Serializable;

import pedido.Pedido;
import productos.Alimento;
import registros.Bitacora;

public class Mensaje implements Serializable { //SERVIDOR

	private static final long serialVersionUID = -7052787477443275175L;
	private TipoMensaje tipo;
    private String contenido;
    private Tabla tablas;
    private Bitacora bitacora;
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

    public Mensaje(TipoMensaje pTipo, Tabla pTablas) {
        this.tipo = pTipo;
        this.tablas = pTablas;
    }

    public Mensaje(TipoMensaje pTipo, Bitacora pBitacora){
    	this.tipo = pTipo;
    	this.bitacora = pBitacora;
    }

    public Mensaje(TipoMensaje pTipo, MontoPedido pMontos){
    	this.tipo = pTipo;
    	this.montosPedido = pMontos;
    }

    public Mensaje(TipoMensaje pTipo, Alimento pAlimento){
    	this.tipo = pTipo;
    	this.miAlimento = pAlimento;
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

    public Pedido getPedido(){
    	return this.miPedido;
    }

    public String getNombre(){
    	return this.nombrePedido;
    }

    public void setMensaje(String pMensaje) {
        this.contenido = pMensaje;
    }

    public String getContenido(){
    	return this.contenido;
    }

    public String toString() {
        return this.tipo + this.contenido;
    }

    public String getTipoPedido(){
    	return this.tipoPedido;
    }

    public Alimento getAlimento(){
    	return this.miAlimento;
    }

    public Alimento getAlimento_2(){
    	return this.miAlimento_2;
    }

}