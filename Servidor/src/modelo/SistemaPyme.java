package modelo;

public class SistemaPyme {

	private String nombrePyme;

	public SistemaPyme(String pNombrePyme){
		setNombrePyme(pNombrePyme);
	}

	public String getNombrePyme() {
		return nombrePyme;
	}

	private void setNombrePyme(String nombrePyme) {
		this.nombrePyme = nombrePyme;
	}
}
