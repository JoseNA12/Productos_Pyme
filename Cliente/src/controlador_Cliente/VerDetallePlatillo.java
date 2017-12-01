package controlador_Cliente;


public class VerDetallePlatillo implements ControlledScreen {

	ScreensController myController;

	public String Codigo;
	public String Nombre;
	public String Tamanio;
	public String PiezasPorcion;
	public String CaloriasPorcion;
	public String CaloriasPieza;
	public String Precio;
	public String Descripcion;


	public String getCodigo() {
		return Codigo;
	}

	public void setCodigo(String codigo) {
		Codigo = codigo;
	}

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

	public void setScreenParent(ScreensController screenParent){
		myController = screenParent;
	}
}
