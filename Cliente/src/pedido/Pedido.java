package pedido;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import productos.Alimento;

public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nombreCliente;
	private DateFormat fechaPedido;
	private Date fechaActual;
	private static int numeroPedido;
	private double PrecioTotal;
	private double TotalCalorias;
	private ArrayList<LineaPedido> misLineasPedidos = new ArrayList<LineaPedido>();
	private ArrayList<Alimento> productosOrdenados = new ArrayList<Alimento>();


	public Pedido(){}

	public Pedido(String pNombreCliente){
		fechaActual = new Date();
		setNombreCliente(pNombreCliente);
		setFechaPedido();
		numeroPedido++;
	}

	public double getPrecioTotal() {
		return PrecioTotal;
	}

	public void calcularMonto(double precioTotal) {
		PrecioTotal += precioTotal;
	}

	public double getTotalCalorias() {
		return TotalCalorias;
	}

	public void calcularCaloriasTotales(double totalCalorias) {
		TotalCalorias += totalCalorias;
	}

	public void actualizarMontosPedido(double pMontoTotal, double pTotalCalorias){
		this.PrecioTotal = pMontoTotal;
		this.TotalCalorias = pTotalCalorias;
	}

	public void tramitarPedido(){

	}

	public void agregarProductoLista(Alimento pAlimento){
		productosOrdenados.add(pAlimento);
	}

	public void eliminarProductoLista(Alimento pAlimento){
		productosOrdenados.remove(pAlimento);
	}

	public ArrayList<Alimento> getProductosOrdenados(){
		return productosOrdenados;
	}

	public void mostrarProductosOrdenados(){
		int i = 0;

		while(i != productosOrdenados.size()){
			System.out.println("Pedido: "+productosOrdenados.get(i).getNombre());
			i++;
		}
	}

	public void agregarLineaPedido(LineaPedido pLineaPedido){
		misLineasPedidos.add(pLineaPedido);
	}

	public void eliminarLineaPedido(double pMontoTotal, double pTotalCalorias){
		int i = 0;
		while(i < misLineasPedidos.size()){
			if(misLineasPedidos.get(i).getResultado() == pMontoTotal && misLineasPedidos.get(i).getResultadoTotalCalorias() == pTotalCalorias){
				//System.out.println("Se elimino de linea producto: "+misLineasPedidos.get(i).getResultado()+" y "+misLineasPedidos.get(i).getResultadoTotalCalorias());
				misLineasPedidos.remove(misLineasPedidos.get(i));
			}
			i++;
		}
	}

	public void mostrarLineasPedido(){
		int i = 0;
		System.out.println("\nMOSTRANDO LINEAS:");
		while(i != misLineasPedidos.size()){
			System.out.println("MontoTotal: "+misLineasPedidos.get(i).getResultado()+" y TotalCalorias: "+misLineasPedidos.get(i).getResultadoTotalCalorias());
			i++;
		}
	}

	public ArrayList<LineaPedido> getLineaPedido(){
		return misLineasPedidos;
	}

	public void seleccionarPorciones(){

	}

	private String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	private String getFechaPedido() {
		return fechaPedido.format(fechaActual);
	}
	private void setFechaPedido() {
		this.fechaPedido = new SimpleDateFormat("dd/MM/yyyy");;
	}

	private static int getNumeroPedido(){
		return numeroPedido;
	}

}
