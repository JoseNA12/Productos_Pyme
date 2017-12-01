package cuentas;

public class Administrador extends Usuario {

	private String identificador;

	public Administrador(String pIdentificador){
		setIdentificador(pIdentificador);
	}

	private String getIdentificador() {
		return identificador;
	}

	private void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

}
