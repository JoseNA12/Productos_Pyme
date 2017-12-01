package productos;

public class ModificarProducto {
	public String Codigo;
	public String Nombre;
	public String Descripcion;
	public String TamanioPorcion; //Racion
	public String PiezasPorcion;
	public String CaloriasPorPorcion;
	public String CaloriasPorPieza; //No está en xml
	public String Precio;
	public String Cantidad;


	public ModificarProducto(){}

	public ModificarProducto(String pCodigo, String pNombre, String pDescripcion, String pTamanioPorcion, String pPiezasPorPorcion,
			String pCaloriasPorPorcion, String pCaloriasPorPieza, String pPrecio){

		setCodigo(pCodigo);
		setNombre(pNombre);
		setDescripcion(pDescripcion);
		setTamanioPorcion(pTamanioPorcion);
		setPiezasPorcion(pPiezasPorPorcion);
		setCaloriasPorPorcion(pCaloriasPorPorcion);
		setCaloriasPorPieza(pCaloriasPorPieza);
		setPrecio(pPrecio);

	}

	public String getCodigo() {
		return Codigo;
	}
	private void setCodigo(String codigo) {
		Codigo = codigo;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	private void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	public String getTamanioPorcion() {
		return TamanioPorcion;
	}
	public void setTamanioPorcion(String tamanioPorcion) {
		TamanioPorcion = tamanioPorcion;
	}
	public String getPiezasPorcion() {
		return PiezasPorcion;
	}
	private void setPiezasPorcion(String piezasPorcion) {
		PiezasPorcion = piezasPorcion;
	}
	public String getCaloriasPorPorcion() {
		return CaloriasPorPorcion;
	}
	public void setCaloriasPorPorcion(String caloriasUnaPorcion) {
		CaloriasPorPorcion = caloriasUnaPorcion;
	}
	public String getCaloriasPorPieza() {
		return CaloriasPorPieza;
	}
	public void setCaloriasPorPieza(String caloriasPorPieza) {
		CaloriasPorPieza = caloriasPorPieza;
	}
	public String getPrecio() {
		return Precio;
	}
	private void setPrecio(String precio) {
		Precio = precio;
	}
	public String getCantidad(){
		return this.Cantidad;
	}
	public void setCantidad(String pCant){
		this.Cantidad = pCant;
	}
}
