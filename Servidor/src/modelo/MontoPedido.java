package modelo;

import java.io.Serializable;

public class MontoPedido implements Serializable { // SERVIDOR

	private static final long serialVersionUID = 1L;

	public double montoAdicional_ParaLlevar = 1500;
	public double montoAdicional_Express = 2500;


	public double getMontoAdicional_ParaLlevar() {
		return montoAdicional_ParaLlevar;
	}
	public void setMontoAdicional_ParaLlevar(double pMontoAdicional_ParaLlevar) {
		this.montoAdicional_ParaLlevar = pMontoAdicional_ParaLlevar;
	}
	public double getMontoAdicional_Express() {
		return montoAdicional_Express;
	}
	public void setMontoAdicional_Express(double pMontoAdicional_Express) {
		this.montoAdicional_Express = pMontoAdicional_Express;
	}


}

