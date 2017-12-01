package pedido;

import java.io.Serializable;

public class LineaPedido implements Serializable {

	private static final long serialVersionUID = 1L;

	private int cantidadProducto;
	private double resultadoPrecioTotal;
	private double resultadoTotalCalorias;


	public double calcularMontoProducto(double pPrecio, double pCantidadProducto){
		this.resultadoPrecioTotal = pPrecio * pCantidadProducto;
		return resultadoPrecioTotal;
	}

	public double calcularTotalCalorias(double pCalorias, double pCantidadProducto){
		resultadoTotalCalorias = pCalorias * pCantidadProducto;
		return resultadoTotalCalorias;
	}

	public double getResultado() {
		return resultadoPrecioTotal;
	}
	public int getCantidadProducto() {
		return cantidadProducto;
	}
	public void setCantidadProducto(int cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}

	public double getResultadoTotalCalorias() {
		return resultadoTotalCalorias;
	}

	private void setResultadoTotalCalorias(double resultadoTotalCalorias) {
		this.resultadoTotalCalorias = resultadoTotalCalorias;
	}

}
