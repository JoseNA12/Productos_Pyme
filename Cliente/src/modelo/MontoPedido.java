package modelo;

import java.io.Serializable;

public class MontoPedido  implements Serializable {

	private static final long serialVersionUID = 1L;

	public double montoAdicional_ParaLlevar;
	public double montoAdicional_Express;


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
