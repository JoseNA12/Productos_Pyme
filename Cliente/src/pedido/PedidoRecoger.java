package pedido;


public class PedidoRecoger extends Pedido {

	private String celularPersona;


	public PedidoRecoger(String pNombrePersona, String pCelularPersona){
		super(pNombrePersona);
		setCelularPersona(pCelularPersona);
	}

	public double calcularMontoRecoger(double pMonto, double montoAdicional){
		return pMonto + montoAdicional;
	}

	private String getCelularPersona() {
		return celularPersona;
	}

	private void setCelularPersona(String celularPersona) {
		this.celularPersona = celularPersona;
	}

}
