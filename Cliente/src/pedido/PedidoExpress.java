package pedido;


public class PedidoExpress extends Pedido {

	private String celularPersona;
	private String direccionEntrega;

	public PedidoExpress(String pNombreCliente, String pCelular, String pDireccionEntrega){
		super(pNombreCliente);
		setCelularPersona(pCelular);
		setDireccionEntrega(pDireccionEntrega);
	}

	public double calcularMontoExpress(double pMonto, double montoAdicional){
		return pMonto + montoAdicional;

	}

	private String getCelularPersona() {
		return celularPersona;
	}

	private void setCelularPersona(String celularPersona) {
		this.celularPersona = celularPersona;
	}

	private String getDireccionEntrega() {
		return direccionEntrega;
	}

	private void setDireccionEntrega(String direccionEntrega) {
		this.direccionEntrega = direccionEntrega;
	}

}
