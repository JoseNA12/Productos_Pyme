package controlador_Administrador;

/**
 * Clase controladora de la vista Ver detalles del platillo
 * @author Jose Navarro
 *
 */
public class VerDetallePlatillo implements ControlledScreen_Admi {

	ScreensController_Admi myController;

	public String Nombre;
	public String Tamanio;
	public String PiezasPorcion;
	public String CaloriasPorcion;
	public String CaloriasPieza;
	public String Precio;
	public String Descripcion;

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getTamanio() {
		return Tamanio;
	}

	public void setTamanio(String tamanio) {
		Tamanio = tamanio;
	}

	public String getPiezasPorcion() {
		return PiezasPorcion;
	}

	public void setPiezasPorcion(String piezasPorion) {
		PiezasPorcion = piezasPorion;
	}

	public String getCaloriasPorcion() {
		return CaloriasPorcion;
	}

	public void setCaloriasPorcion(String caloriasPorcion) {
		CaloriasPorcion = caloriasPorcion;
	}

	public String getCaloriasPieza() {
		return CaloriasPieza;
	}

	public void setCaloriasPieza(String caloriasPieza) {
		CaloriasPieza = caloriasPieza;
	}

	public String getPrecio() {
		return Precio;
	}

	public void setPrecio(String precio) {
		Precio = precio;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public void setScreenParent(ScreensController_Admi screenParent){
		myController = screenParent;
	}
}
