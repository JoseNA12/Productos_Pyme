package registros;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bitacora implements Serializable {

	private static final long serialVersionUID = 1L;
	private DateFormat fechaAccion;
	private DateFormat horaAccion;
	private String accion;
	private String tipoAccion;
	Date fechaActual;

	public void agregarAccion(String pAccion, String pTipoAccion){
		fechaActual = new Date();
		setFechaAccion();
		setHoraAccion();
		setAccion(pAccion);
		setTipoAccion(pTipoAccion);
	}

	private String getFechaAccion() {
		return fechaAccion.format(fechaActual);
	}

	private void setFechaAccion() {
		fechaAccion = new SimpleDateFormat("dd/MM/yyyy");
	}

	private String getHoraAccion() {
		return horaAccion.format(fechaActual);
	}

	private void setHoraAccion() {
		horaAccion = new SimpleDateFormat("HH:mm:ss");
	}

	private String getAccion() {
		return accion;
	}

	private void setAccion(String accion) {
		this.accion = accion;
	}

	private String getTipoAccion() {
		return tipoAccion;
	}

	private void setTipoAccion(String tipoAccion) {
		this.tipoAccion = tipoAccion;
	}

	@Override
	public String toString() {
		return getFechaAccion() + ", " + getHoraAccion() + ", " + accion
				+ ", " + tipoAccion + "\n";
	}


}
