package cuentas;


public class Usuario {

	private String nombre;
	private String nombreCuenta;
	private String claveCuenta;
	//private String tipoUsuario;

	public Usuario(){}

	public Usuario(String pNombre, String pNombreCuenta, String pClaveCuenta){
		setNombre(pNombre);
		setNombreCuenta(pNombreCuenta);
		setClaveCuenta(pClaveCuenta);
	}

	private String getNombre() {
		return nombre;
	}

	private void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreCuenta() {
		return nombreCuenta;
	}

	private void setNombreCuenta(String nombreCuenta) {
		this.nombreCuenta = nombreCuenta;
	}

	public String getClaveCuenta() {
		return claveCuenta;
	}

	private void setClaveCuenta(String claveCuenta) {
		this.claveCuenta = claveCuenta;
	}
}
