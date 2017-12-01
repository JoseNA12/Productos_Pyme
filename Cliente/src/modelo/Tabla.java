package modelo;


import java.io.Serializable;
import java.util.ArrayList;

import productos.Alimento;

public class Tabla implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<Alimento> tablaEntradas = new ArrayList<Alimento>();
	private ArrayList<Alimento> tablaPrincipal = new ArrayList<Alimento>();
	private ArrayList<Alimento> tablaPostres = new ArrayList<Alimento>();
	private ArrayList<Alimento> tablaBebidas = new ArrayList<Alimento>();

	public Tabla(){}

	public Tabla(ArrayList<Alimento> pTablaEntradas, ArrayList<Alimento> pTablaPrincipal,
			ArrayList<Alimento> pTablaPostres, ArrayList<Alimento> pTablaBebidas){

		setTablaEntradas(pTablaEntradas);
		setTablaPrincipal(pTablaPrincipal);
		setTablaPostres(pTablaPostres);
		setTablaBebidas(pTablaBebidas);
	}

	public ArrayList<Alimento> getTablaEntradas() {
		return tablaEntradas;
	}

	private void setTablaEntradas(ArrayList<Alimento> tablaEntradas) {
		this.tablaEntradas = tablaEntradas;
	}

	public ArrayList<Alimento> getTablaPrincipal() {
		return tablaPrincipal;
	}

	private void setTablaPrincipal(ArrayList<Alimento> tablaPrincipal) {
		this.tablaPrincipal = tablaPrincipal;
	}

	public ArrayList<Alimento> getTablaPostres() {
		return tablaPostres;
	}

	private void setTablaPostres(ArrayList<Alimento> tablaPostres) {
		this.tablaPostres = tablaPostres;
	}

	public ArrayList<Alimento> getTablaBebidas() {
		return tablaBebidas;
	}

	private void setTablaBebidas(ArrayList<Alimento> tablaBebidas) {
		this.tablaBebidas = tablaBebidas;
	}

}
