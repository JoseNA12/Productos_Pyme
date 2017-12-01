package cuentas;

public class Cliente extends Usuario {

	private String celular;
	private String direccion;
	private String correoElectronico;


	public Cliente(String pCelular, String pDireccion, String pCorreoElectronico){
		setCelular(pCelular);
		setDireccion(pDireccion);
		setCorreoElectronico(pCorreoElectronico);
	}

	private String getCelular() {
		return celular;
	}

	private void setCelular(String celular) {
		this.celular = celular;
	}

	private String getDireccion() {
		return direccion;
	}

	private void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	private String getCorreoElectronico() {
		return correoElectronico;
	}

	private void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
}
