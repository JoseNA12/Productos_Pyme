package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import cuentas.Usuario;
import productos.Alimento;

/**
 *
 * Clase encargada de manejar y controlar los archivos xml del programa.
 * Carga, añade, modifica y elimina productos del archivo menu.xml.
 * Además, maneja ArrayList de cada tipo de producto, las cuentas de los usuarios y de las listas: Top-10 y platillos nunca pedidos.
 * @author Jose Navarro
 *
 */
public class Menu {

	public static ArrayList<Usuario> cuentasClientes = new ArrayList<Usuario>();
	public static ArrayList<Usuario> cuentasAdministradores = new ArrayList<Usuario>();

	public static ArrayList<Alimento> platillosEntradas = new ArrayList<Alimento>();
	public static ArrayList<Alimento> platillosPrincipales = new ArrayList<Alimento>();
	public static ArrayList<Alimento> platillosBebidas = new ArrayList<Alimento>();
	public static ArrayList<Alimento> platillosPostres = new ArrayList<Alimento>();

	public static ArrayList<Alimento> platilloNuncaPedidos = new ArrayList<Alimento>();
	public static ArrayList<Alimento> platillosTop10 = new ArrayList<Alimento>();

	public static ArrayList<Alimento> platillosEntradas_Disponibles = new ArrayList<Alimento>();
	public static ArrayList<Alimento> platillosPrincipales_Disponibles = new ArrayList<Alimento>();
	public static ArrayList<Alimento> platillosBebidas_Disponibles = new ArrayList<Alimento>();
	public static ArrayList<Alimento> platillosPostres_Disponibles = new ArrayList<Alimento>();

	public static ArrayList<Alimento> platillosEntradas_NoDisponibles = new ArrayList<Alimento>();
	public static ArrayList<Alimento> platillosPrincipales_NoDisponibles = new ArrayList<Alimento>();
	public static ArrayList<Alimento> platillosBebidas_NoDisponibles = new ArrayList<Alimento>();
	public static ArrayList<Alimento> platillosPostres_NoDisponibles = new ArrayList<Alimento>();

	public Menu(){}

	/**
	 * Método encargado de cargar los usuarios de los archivos Clientes.xml y Adminisrador.xml.
	 * Además, se lleva un registra mediante un ArrayList.
	 * @param pArchivoXML
	 * @param pTipoCuenta
	 */
	public void cargarXmlUsuarios(String pArchivoXML, TipoArchivo pTipoCuenta){ //Carga los archivos: Clientes.xml y Administradores.xml

	    SAXBuilder builder = new SAXBuilder();//Se crea un SAXBuilder para poder parsear el archivo
	    File xmlFile = new File(pArchivoXML);

	    try{
	        Document document = (Document) builder.build(xmlFile);//Se crea el documento a traves del archivo
	        Element rootNode = document.getRootElement();//Se obtiene la raiz 'cuentas'

	        List<Element> list = rootNode.getChildren("cuenta"); //Se obtiene la lista de hijos de la raiz 'cuentas'

	        for ( int i = 0; i < list.size(); i++ ){//Se recorre la lista de hijos de 'cuentas'

	            Element tabla = (Element) list.get(i);//Se obtiene el elemento 'cuenta'

	            List<Element> lista_campos = tabla.getChildren();//Se obtiene la lista de hijos del tag 'cuenta'

	            for ( int j = 0; j < lista_campos.size(); j++ ){//Se recorre la lista de campos

	                Element campo = (Element)lista_campos.get( j );//Se obtiene el elemento '<cuenta>'

	                String nombre = campo.getChildTextTrim("nombreCuenta");
	                String clave = campo.getChildTextTrim("claveCuenta");

	                if(pTipoCuenta == TipoArchivo.CLIENTES){
	                	Usuario miCuentaCliente = new Usuario(nombre, nombre, clave);
	                	cuentasClientes.add(miCuentaCliente);
	                }
	                else{
	                	if(pTipoCuenta == TipoArchivo.ADMINISTRADORES){
	                		Usuario miCuentaAdministrador = new Usuario(nombre, nombre, clave);
	                		cuentasAdministradores.add(miCuentaAdministrador);
	                	}
	                }
	            }
	        }

	    }catch (IOException io) {
	        System.out.println(io.getMessage());
	        System.out.println("Error en el parseo de alguno de los archivos Clientes.xml/Administradores.xml"); //Solo por conocer el error
	    }catch (JDOMException jdomex) {
	        System.out.println(jdomex.getMessage());
	        System.out.println("Error en el parseo de alguno de los archivos Clientes.xml/Administradores.xml");
	    }
	}

	/**
	 * Método encargado de cargar todos los prouctos del archivo menu.xml.
	 * Además, lleva un registra mediante un ArrayList y añade cada platillo a la lista de platillos nunca pedidos por omisión.
	 * @param pArchivoXML
	 */
	public void cargarXmlMenu(String pArchivoXML){ //Carga los archivos: Clientes.xml y Administradores.xml

		try{

			File inputFile = new File(pArchivoXML);

	        SAXBuilder saxBuilder = new SAXBuilder();

	        Document document = saxBuilder.build(inputFile);

	        Element classElement = document.getRootElement();

	        List<Element> studentList = classElement.getChildren();

	        for (int temp = 0; temp < studentList.size(); temp++) {
	            Element student = studentList.get(temp);

	            String codigo = student.getChild("codigo").getText();
	            String nombre = student.getChild("nombre").getText();
	            String descripcion = student.getChild("descripcion").getText();
	            String tamanoPorcion = student.getChild("tamanoPorcion").getText();
	            String piezasPorPorcion = student.getChild("piezasPorPorcion").getText();
	            String caloriasPorPorcion = student.getChild("caloriasPorPorcion").getText();
	            String precio = student.getChild("precio").getText();

	            String caloriassPorPiezaSubstring = "";
	            int totalPiezasPorcion = Integer.parseInt(piezasPorPorcion);

	            int t = 1;
	            while(!caloriasPorPorcion.substring(t-1, t).equals(" ")){
					caloriassPorPiezaSubstring += caloriasPorPorcion.substring(t-1, t);
					t++;
				}

				int caloriasPorPieza_Int = Integer.parseInt(caloriassPorPiezaSubstring);
				String caloriasPorPieza = Integer.toString(caloriasPorPieza_Int/totalPiezasPorcion);caloriasPorPieza+=" kcal";

	            Alimento miAlimento = new Alimento(codigo, nombre, descripcion,
	                	tamanoPorcion, piezasPorPorcion, caloriasPorPorcion, caloriasPorPieza, precio);

	            String subCodigo = codigo.substring(0,3); //Para distinguir y almacenar en listas los productos

	            if(subCodigo.equals("ENT")){
	                platillosEntradas.add(miAlimento);
	                platillosEntradas_Disponibles.add(miAlimento);
	            }
	            else{
	                if(subCodigo.equals("PRN")){
	                	platillosPrincipales.add(miAlimento);
	                	platillosPrincipales_Disponibles.add(miAlimento);
	                }
	                else{
	                	if(subCodigo.equals("BEB")){
	                		platillosBebidas.add(miAlimento);
	                		platillosBebidas_Disponibles.add(miAlimento);
	                	}
	                	else{
	                		if(subCodigo.equals("PTR")){
	                			platillosPostres.add(miAlimento);
	                			platillosPostres_Disponibles.add(miAlimento);
	                		}
	                	}
	                }
	             }
	            platilloNuncaPedidos.add(miAlimento);
	          }
	    }catch (IOException io) {
	        System.out.println(io.getMessage());
	        System.out.println("Error en el parseo del archivo menu.xml"); //Solo por conocer el error
	    }catch (JDOMException jdomex) {
	        System.out.println(jdomex.getMessage());
	        System.out.println("Error en el parseo del archivo menu.xml");
	    }
	}

	/**
	 * Se establece como visible para los clientes el producto que el administrador seleccione.
	 * Además, el producto se agrega al correspondiente ArrayList.
	 * @param pAlimento
	 */
	public static boolean habilitarAlimentos(Alimento pAlimento){

		int i = 0; boolean elimino = false; boolean respuesta = false;

		// --------------- Entradas --------------- //

		while(i < platillosEntradas_NoDisponibles.size()){
			if(platillosEntradas_NoDisponibles.get(i).getNombre().equals(pAlimento.getNombre()) &&
					platillosEntradas_NoDisponibles.get(i).getCodigo().equals(pAlimento.getCodigo())){

				platillosEntradas_NoDisponibles.remove(i);
				elimino = true;
				break;
			}
			i++;
		}
		if(elimino){
			platillosEntradas_Disponibles.add(pAlimento);
			respuesta = true;
		}
		i = 0;

		// --------------- Platos fuertes --------------- //

		if(!respuesta){

			while(i < platillosPrincipales_NoDisponibles.size()){
				if(platillosPrincipales_NoDisponibles.get(i).getNombre().equals(pAlimento.getNombre()) &&
						platillosPrincipales_NoDisponibles.get(i).getCodigo().equals(pAlimento.getCodigo())){

					platillosPrincipales_NoDisponibles.remove(i);
					elimino = true;
					break;
				}
				i++;
			}
			if(elimino){
				platillosPrincipales_Disponibles.add(pAlimento);
				respuesta = true;
			}
			i = 0;

			// --------------- Postres --------------- //

			if(!respuesta){

				while(i < platillosPostres_NoDisponibles.size()){
					if(platillosPostres_NoDisponibles.get(i).getNombre().equals(pAlimento.getNombre()) &&
							platillosPostres_NoDisponibles.get(i).getCodigo().equals(pAlimento.getCodigo())){

						platillosPostres_NoDisponibles.remove(i);
						elimino = true;
						break;
					}
					i++;
				}
				if(elimino){
					platillosPostres_Disponibles.add(pAlimento);
					respuesta = true;
				}
				i = 0;

				// --------------- Bebidas --------------- //

				if(!respuesta){

					while(i < platillosBebidas_NoDisponibles.size()){
						if(platillosBebidas_NoDisponibles.get(i).getNombre().equals(pAlimento.getNombre()) &&
								platillosBebidas_NoDisponibles.get(i).getCodigo().equals(pAlimento.getCodigo())){

							platillosBebidas_NoDisponibles.remove(i);
							elimino = true;
							break;
						}
						i++;
					}
					if(elimino){
						platillosBebidas_Disponibles.add(pAlimento);
						respuesta = true;
					}
					i = 0;
				}
			}
		}
		return respuesta;
	}

	/**
	 * Se establece como no visible para los clientes el producto que el administrador seleccione.
	 * Además, el producto se agrega al correspondiente ArrayList.
	 * @param pAlimento
	 */
	public static boolean inhabilitarAlimento(Alimento pAlimento){
		int i = 0; boolean elimino = false; boolean respuesta = false;

		// --------------- Entradas --------------- //

		while(i < platillosEntradas_Disponibles.size()){
			if(platillosEntradas_Disponibles.get(i).getNombre().equals(pAlimento.getNombre()) &&
					platillosEntradas_Disponibles.get(i).getCodigo().equals(pAlimento.getCodigo())){

				platillosEntradas_Disponibles.remove(i);
				elimino = true;
				break;
			}
			i++;
		}
		if(elimino){
			platillosEntradas_NoDisponibles.add(pAlimento);
			respuesta = true;
		}
		i = 0;

		// --------------- Platos fuertes --------------- //

		if(!respuesta){

			while(i < platillosPrincipales_Disponibles.size()){
				if(platillosPrincipales_Disponibles.get(i).getNombre().equals(pAlimento.getNombre()) &&
						platillosPrincipales_Disponibles.get(i).getCodigo().equals(pAlimento.getCodigo())){

					platillosPrincipales_Disponibles.remove(i);
					elimino = true;
					break;
				}
				i++;
			}
			if(elimino){
				platillosPrincipales_NoDisponibles.add(pAlimento);
				respuesta = true;
			}
			i = 0;

			// --------------- Postres --------------- //

			if(!respuesta){

				while(i < platillosPostres_Disponibles.size()){
					if(platillosPostres_Disponibles.get(i).getNombre().equals(pAlimento.getNombre()) &&
							platillosPostres_Disponibles.get(i).getCodigo().equals(pAlimento.getCodigo())){

						platillosPostres_Disponibles.remove(i);
						elimino = true;
						break;
					}
					i++;
				}
				if(elimino){
					platillosPostres_NoDisponibles.add(pAlimento);
					respuesta = true;
				}
				i = 0;

				// --------------- Bebidas --------------- //

				if(!respuesta){

					while(i < platillosBebidas_Disponibles.size()){
						if(platillosBebidas_Disponibles.get(i).getNombre().equals(pAlimento.getNombre()) &&
								platillosBebidas_Disponibles.get(i).getCodigo().equals(pAlimento.getCodigo())){

							platillosBebidas_Disponibles.remove(i);
							elimino = true;
							break;
						}
						i++;
					}
					if(elimino){
						platillosBebidas_NoDisponibles.add(pAlimento);
						respuesta = true;
					}
					i = 0;
				}
			}
		}
		return respuesta;
	}

	/**
	 * Encargado de agregar un nuevo producto al sistema. Además, se añade, dependiendo del producto, a su ArrayList correspondiente.
	 * Actualiza además, las lista de productos nunca pedidos.
	 * Actualiza las listas de disponibilidad.
	 * @param codigo
	 * @param nombre
	 * @param descripcion
	 * @param tamanioPorcion
	 * @param piezasPorPorcion
	 * @param caloriasPorPorcion
	 * @param caloriasPorPieza
	 * @param precio
	 */
	public static void agregarAlimento(String codigo, String nombre, String descripcion, String tamanioPorcion,
			String piezasPorPorcion, String caloriasPorPorcion, String caloriasPorPieza, String precio){

	    String caloriassPorPiezaSubstring = "";
        int totalPiezasPorcion = Integer.parseInt(piezasPorPorcion);

        int t = 1;
        while(!caloriasPorPorcion.substring(t-1, t).equals(" ")){
			caloriassPorPiezaSubstring += caloriasPorPorcion.substring(t-1, t);
			t++;
		}

		int caloriasPorPieza_Int = Integer.parseInt(caloriassPorPiezaSubstring);
		String caloriasPorPiezaString1 = Integer.toString(caloriasPorPieza_Int/totalPiezasPorcion);caloriasPorPiezaString1+=" kcal";

        Alimento miAlimento = new Alimento(codigo, nombre, descripcion,
            	tamanioPorcion, piezasPorPorcion, caloriasPorPorcion, caloriasPorPiezaString1, precio);

        String subCodigo = codigo.substring(0,3); //Para distinguir y almacenar en listas los productos

        if(subCodigo.equals("ENT")){
            platillosEntradas.add(miAlimento);
            platillosEntradas_Disponibles.add(miAlimento);
        }
        else{
            if(subCodigo.equals("PRN")){
            	platillosPrincipales.add(miAlimento);
            	platillosPrincipales_Disponibles.add(miAlimento);
            }
            else{
            	if(subCodigo.equals("BEB")){
            		platillosBebidas.add(miAlimento);
            		platillosBebidas_Disponibles.add(miAlimento);
            	}
            	else{
            		if(subCodigo.equals("PTR")){
            			platillosPostres.add(miAlimento);
            			platillosPostres_Disponibles.add(miAlimento);
            		}
            	}
            }
         }
        platilloNuncaPedidos.add(miAlimento);

        // -------- Añadido al XML -------- //

        Document document = null;
	    Element root = null;

	    try{
		    File xmlFile = new File("archivosXML/menu.xml");
		    if(xmlFile.exists()) {
		        // try to load document from xml file if it exist
		        // create a file input stream
		        FileInputStream fis = new FileInputStream(xmlFile);
		        // create a sax builder to parse the document
		        SAXBuilder sb = new SAXBuilder();
		        // parse the xml content provided by the file input stream and create a Document object
		        document = sb.build(fis);
		        // get the root element of the document
		        root = document.getRootElement();
		        fis.close();
		    } else {
		        // if it does not exist create a new document and new root
		        document = new Document();
		        root = new Element("archivosXML/menu.xml");
		    }
	    }catch(Exception e){System.out.println("Error al anadir alimento");}

	    Element child = new Element("producto");

	    child.addContent(new Element("codigo").setText(codigo));
	    child.addContent(new Element("nombre").setText(nombre));
	    child.addContent(new Element("descripcion").setText(descripcion));
	    child.addContent(new Element("tamanoPorcion").setText(tamanioPorcion));
	    child.addContent(new Element("piezasPorPorcion").setText(piezasPorPorcion));
	    child.addContent(new Element("caloriasPorPorcion").setText(caloriasPorPorcion));
	    child.addContent(new Element("precio").setText(precio));

	    root.addContent(child);
	    document.setContent(root);
	    try {
	        FileWriter writer = new FileWriter("archivosXML/menu.xml");
	        XMLOutputter outputter = new XMLOutputter();
	        outputter.setFormat(Format.getPrettyFormat());
	        outputter.output(document, writer);
	        outputter.output(document, System.out);
	        writer.close(); // close writer
	    } catch (IOException e) {
	    	System.out.println("*Error en la libreria");
	    	//e.printStackTrace();
	    }
	}

	/**
	 * Encargado de borrar un producto del sistema. Además, actualiza los ArrayList correspondientes y la lista de productos nunca pedidos.
	 * Borra y actualiza las listas de disponibilidad.
	 * @param pAlimento
	 */
	public static void eliminarAlimento(Alimento pAlimento){

		String codigo = pAlimento.getCodigo();
        String subCodigo = codigo.substring(0,3); //Para distinguir y almacenar en listas los productos

        if(subCodigo.equals("ENT")){ int a = 0;
	        while(a < platillosEntradas.size()){
	        	if(pAlimento.getNombre().equals(platillosEntradas.get(a).getNombre()) &&
	        			pAlimento.getCodigo().equals(platillosEntradas.get(a).getCodigo())){

	        		platillosEntradas.remove(a);
	        	}
	        	a++;
	        }
	        a = 0;
	     // --------------- Entradas Disponibles --------------- //
	        while(a < platillosEntradas_Disponibles.size()){
	        	if(pAlimento.getNombre().equals(platillosEntradas_Disponibles.get(a).getNombre()) &&
	        			pAlimento.getCodigo().equals(platillosEntradas_Disponibles.get(a).getCodigo())){

	        		platillosEntradas_Disponibles.remove(a);
	        	}
	        	a++;
	        }
	        a = 0;
	     // --------------- Entradas No disponibles --------------- //
	        while(a < platillosEntradas_NoDisponibles.size()){
	        	if(pAlimento.getNombre().equals(platillosEntradas_NoDisponibles.get(a).getNombre()) &&
	        			pAlimento.getCodigo().equals(platillosEntradas_NoDisponibles.get(a).getCodigo())){

	        		platillosEntradas_NoDisponibles.remove(a);
	        	}
	        	a++;
	        }
        }
        else{
            if(subCodigo.equals("PRN")){ int e = 0;
            	while(e < platillosPrincipales.size()){
                	if(pAlimento.getNombre().equals(platillosPrincipales.get(e).getNombre()) &&
                			pAlimento.getCodigo().equals(platillosPrincipales.get(e).getCodigo())){

                		platillosPrincipales.remove(e);
                	}
                	e++;
                }
            	e = 0;
            	// --------------- Platos fuertes Disponibles --------------- //
            	while(e < platillosPrincipales_Disponibles.size()){
                	if(pAlimento.getNombre().equals(platillosPrincipales_Disponibles.get(e).getNombre()) &&
                			pAlimento.getCodigo().equals(platillosPrincipales_Disponibles.get(e).getCodigo())){

                		platillosPrincipales_Disponibles.remove(e);
                	}
                	e++;
                }
            	e = 0;
            	// --------------- Platos fuertes No disponibles --------------- //
            	while(e < platillosPrincipales_NoDisponibles.size()){
                	if(pAlimento.getNombre().equals(platillosPrincipales_NoDisponibles.get(e).getNombre()) &&
                			pAlimento.getCodigo().equals(platillosPrincipales_NoDisponibles.get(e).getCodigo())){

                		platillosPrincipales_NoDisponibles.remove(e);
                	}
                	e++;
                }
            }
            else{
            	if(subCodigo.equals("BEB")){ int i = 0;
	            	while(i < platillosBebidas.size()){
	                	if(pAlimento.getNombre().equals(platillosBebidas.get(i).getNombre()) &&
	                			pAlimento.getCodigo().equals(platillosBebidas.get(i).getCodigo())){

	                		platillosBebidas.remove(i);
	                	}
	                	i++;
	                }
	            	i = 0;
	            	// --------------- Bebidas Disponibles --------------- //
	            	while(i < platillosBebidas_Disponibles.size()){
	                	if(pAlimento.getNombre().equals(platillosBebidas_Disponibles.get(i).getNombre()) &&
	                			pAlimento.getCodigo().equals(platillosBebidas_Disponibles.get(i).getCodigo())){

	                		platillosBebidas_Disponibles.remove(i);
	                	}
	                	i++;
	                }
	            	i = 0;
	            	// --------------- Bebidas No disponibles --------------- //
	            	while(i < platillosBebidas_NoDisponibles.size()){
	                	if(pAlimento.getNombre().equals(platillosBebidas_NoDisponibles.get(i).getNombre()) &&
	                			pAlimento.getCodigo().equals(platillosBebidas_NoDisponibles.get(i).getCodigo())){

	                		platillosBebidas_NoDisponibles.remove(i);
	                	}
	                	i++;
	                }
            	}
            	else{
            		if(subCodigo.equals("PTR")){ int o = 0;
            			while(o < platillosPostres.size()){
    	                	if(pAlimento.getNombre().equals(platillosPostres.get(o).getNombre()) &&
    	                			pAlimento.getCodigo().equals(platillosPostres.get(o).getCodigo())){

    	                		platillosPostres.remove(o);
    	                	}
    	                	o++;
    	                }
            			o = 0;
            			// --------------- Postres Disponibles --------------- //
            			while(o < platillosPostres_Disponibles.size()){
    	                	if(pAlimento.getNombre().equals(platillosPostres_Disponibles.get(o).getNombre()) &&
    	                			pAlimento.getCodigo().equals(platillosPostres_Disponibles.get(o).getCodigo())){

    	                		platillosPostres_Disponibles.remove(o);
    	                	}
    	                	o++;
    	                }
            			o = 0;
            			// --------------- Postres No disponibles --------------- //
            			while(o < platillosPostres_NoDisponibles.size()){
    	                	if(pAlimento.getNombre().equals(platillosPostres_NoDisponibles.get(o).getNombre()) &&
    	                			pAlimento.getCodigo().equals(platillosPostres_NoDisponibles.get(o).getCodigo())){

    	                		platillosPostres_NoDisponibles.remove(o);
    	                	}
    	                	o++;
    	                }
            		}
            	}
            }
         }

		// -------- Borrado del xml -------- //

		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("archivosXML/menu.xml");
		XMLOutputter xmlOut = new XMLOutputter();

		try {

			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List<Element> list = rootNode.getChildren("producto");

			for (Element node: list) {
				String Nombre = node.getChildText("nombre");
				String Codigo = node.getChildText("codigo");

				if(Nombre.equals(pAlimento.getNombre()) && Codigo.equals(pAlimento.getCodigo())){
			        // Borrar
			        node.getParent().removeContent(node);
			        break;
				}
			}

			xmlOut.setFormat(Format.getPrettyFormat());
			xmlOut.output(document, new FileWriter("archivosXML/menu.xml"));

		  } catch (IOException io) {
			  System.out.println("*Error en la libreria");
			  //System.out.println(io.getMessage());
		  } catch (JDOMException jdomex) {
			  System.out.println("*Error en la libreria");
			//System.out.println(jdomex.getMessage());
		  }

		actualizarLista_PlatillosNuncaPedidos(pAlimento); //Borra tambien de la lista de platillos nunca pedidos
	}

	/**
	 * Encargado de modificar y actualizar un alimento del sistema. Actualiza el ArrayList correspondiente.
	 * Borra además, el producto anterior de la lista de productos nunca pedidos y añade el actualizado.
	 * Borra y actualiza las listas de disponibilidad.
	 * @param pAlimento_Antiguo
	 * @param pAlimento_Nuevo
	 */
	public static void modificarAlimento(Alimento pAlimento_Antiguo, Alimento pAlimento_Nuevo){

		String codigo = pAlimento_Antiguo.getCodigo();
        String subCodigo = codigo.substring(0,3);

        // ---- Eliminar el producto actual ---- //
        if(subCodigo.equals("ENT")){ int a = 0; // ------- ENTRADAS ------- //

        	while(a < platillosEntradas.size()){
        		if(platillosEntradas.get(a).getNombre().equals(pAlimento_Antiguo.getNombre())){
        			platillosEntradas.remove(a);
        		}
        		a++;
        	}
        	a = 0;
        	// --------------- Entradas Disponibles --------------- //
	        while(a < platillosEntradas_Disponibles.size()){
	        	if(pAlimento_Antiguo.getNombre().equals(platillosEntradas_Disponibles.get(a).getNombre()) &&
	        			pAlimento_Antiguo.getCodigo().equals(platillosEntradas_Disponibles.get(a).getCodigo())){

	        		platillosEntradas_Disponibles.remove(a);
	        	}
	        	a++;
	        }
	        a = 0;
	     // --------------- Entradas No disponibles --------------- //
	        while(a < platillosEntradas_NoDisponibles.size()){
	        	if(pAlimento_Antiguo.getNombre().equals(platillosEntradas_NoDisponibles.get(a).getNombre()) &&
	        			pAlimento_Antiguo.getCodigo().equals(platillosEntradas_NoDisponibles.get(a).getCodigo())){

	        		platillosEntradas_NoDisponibles.remove(a);
	        	}
	        	a++;
	        }
        }
        else{
            if(subCodigo.equals("PRN")){ int e = 0; // ------- PLATOS FUERTES ------- //

            	while(e < platillosPrincipales.size()){
            		if(platillosPrincipales.get(e).getNombre().equals(pAlimento_Antiguo.getNombre())){
            			platillosPrincipales.remove(e);
            		}
            		e++;
            	}
            	e = 0;
            	// --------------- Platos fuertes Disponibles --------------- //
            	while(e < platillosPrincipales_Disponibles.size()){
                	if(pAlimento_Antiguo.getNombre().equals(platillosPrincipales_Disponibles.get(e).getNombre()) &&
                			pAlimento_Antiguo.getCodigo().equals(platillosPrincipales_Disponibles.get(e).getCodigo())){

                		platillosPrincipales_Disponibles.remove(e);
                	}
                	e++;
                }
            	e = 0;
            	// --------------- Platos fuertes No disponibles --------------- //
            	while(e < platillosPrincipales_NoDisponibles.size()){
                	if(pAlimento_Antiguo.getNombre().equals(platillosPrincipales_NoDisponibles.get(e).getNombre()) &&
                			pAlimento_Antiguo.getCodigo().equals(platillosPrincipales_NoDisponibles.get(e).getCodigo())){

                		platillosPrincipales_NoDisponibles.remove(e);
                	}
                	e++;
                }
            }
            else{
            	if(subCodigo.equals("BEB")){ int i = 0;// ------- BEBIDAS ------- //

            	while(i < platillosBebidas.size()){
            		if(platillosBebidas.get(i).getNombre().equals(pAlimento_Antiguo.getNombre())){
            			platillosBebidas.remove(i);
            		}
            		i++;
            	}
            	i = 0;
            	// --------------- Bebidas Disponibles --------------- //
            	while(i < platillosBebidas_Disponibles.size()){
                	if(pAlimento_Antiguo.getNombre().equals(platillosBebidas_Disponibles.get(i).getNombre()) &&
                			pAlimento_Antiguo.getCodigo().equals(platillosBebidas_Disponibles.get(i).getCodigo())){

                		platillosBebidas_Disponibles.remove(i);
                	}
                	i++;
                }
            	i = 0;
            	// --------------- Bebidas No disponibles --------------- //
            	while(i < platillosBebidas_NoDisponibles.size()){
                	if(pAlimento_Antiguo.getNombre().equals(platillosBebidas_NoDisponibles.get(i).getNombre()) &&
                			pAlimento_Antiguo.getCodigo().equals(platillosBebidas_NoDisponibles.get(i).getCodigo())){

                		platillosBebidas_NoDisponibles.remove(i);
                	}
                	i++;
                }

            	}
            	else{
            		if(subCodigo.equals("PTR")){ int o = 0; // ------- POSTRES ------- //

	            		while(o < platillosPostres.size()){
	            			if(platillosPostres.get(o).getNombre().equals(pAlimento_Antiguo.getNombre())){
	            				platillosPostres.remove(o);
	            			}
	            			o++;
	            		}
            			o = 0;
            			// --------------- Postres Disponibles --------------- //
            			while(o < platillosPostres_Disponibles.size()){
    	                	if(pAlimento_Antiguo.getNombre().equals(platillosPostres_Disponibles.get(o).getNombre()) &&
    	                			pAlimento_Antiguo.getCodigo().equals(platillosPostres_Disponibles.get(o).getCodigo())){

    	                		platillosPostres_Disponibles.remove(o);
    	                	}
    	                	o++;
    	                }
            			o = 0;
            			// --------------- Postres No disponibles --------------- //
            			while(o < platillosPostres_NoDisponibles.size()){
    	                	if(pAlimento_Antiguo.getNombre().equals(platillosPostres_NoDisponibles.get(o).getNombre()) &&
    	                			pAlimento_Antiguo.getCodigo().equals(platillosPostres_NoDisponibles.get(o).getCodigo())){

    	                		platillosPostres_NoDisponibles.remove(o);
    	                	}
    	                	o++;
    	                }
            		}
            	}
            }
         }

    	codigo = pAlimento_Nuevo.getCodigo();
        subCodigo = codigo.substring(0,3);

        // ---- Añadir el producto editado ---- //
        if(subCodigo.equals("ENT")){
            platillosEntradas.add(pAlimento_Nuevo);
            platillosEntradas_Disponibles.add(pAlimento_Nuevo);
        }
        else{
            if(subCodigo.equals("PRN")){
            	platillosPrincipales.add(pAlimento_Nuevo);
            	platillosPrincipales_Disponibles.add(pAlimento_Nuevo);
            }
            else{
            	if(subCodigo.equals("BEB")){
            		platillosBebidas.add(pAlimento_Nuevo);
            		platillosBebidas_Disponibles.add(pAlimento_Nuevo);
            	}
            	else{
            		if(subCodigo.equals("PTR")){
            			platillosPostres.add(pAlimento_Nuevo);
            			platillosPostres_Disponibles.add(pAlimento_Nuevo);
            		}
            	}
            }
         }

		// ---- Para comparar atributos en el momento de editar en xml ---- //
		String Codigo_Antiguo = pAlimento_Antiguo.getCodigo();
		String Nombre_Antiguo = pAlimento_Antiguo.getNombre();
		String Precio_Antiguo = pAlimento_Antiguo.getPrecio();
		// ---------------------------------------------------------------- //

		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("archivosXML/menu.xml");
		XMLOutputter xmlOut = new XMLOutputter();

		try {
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List<Element> Archivo = rootNode.getChildren("producto");

			int i = 0;
			for (Element node: Archivo) {

				if(node.getChildText("codigo").equals(Codigo_Antiguo) && node.getChildText("nombre").equals(Nombre_Antiguo) &&
						node.getChildText("precio").equals(Precio_Antiguo)){ // Si existe, edita los atributos del producto

					Archivo.get(i).getChild("codigo").setText(pAlimento_Nuevo.getCodigo());
					Archivo.get(i).getChild("nombre").setText(pAlimento_Nuevo.getNombre());
					Archivo.get(i).getChild("descripcion").setText(pAlimento_Nuevo.getDescripcion());
					Archivo.get(i).getChild("tamanoPorcion").setText(pAlimento_Nuevo.getTamanioPorcion());
					Archivo.get(i).getChild("piezasPorPorcion").setText(pAlimento_Nuevo.getPiezasPorcion());
					Archivo.get(i).getChild("caloriasPorPorcion").setText(pAlimento_Nuevo.getCaloriasUnaPorcion());
					Archivo.get(i).getChild("precio").setText(pAlimento_Nuevo.getPrecio());
				}
			i++;
			}

			xmlOut.setFormat(Format.getPrettyFormat());
			xmlOut.output(document, new FileWriter("archivosXML/menu.xml"));

		  } catch (IOException io) {
			  System.out.println("*Error en la libreria");
			//System.out.println(io.getMessage());
		  } catch (JDOMException jdomex) {
			  System.out.println("*Error en la libreria");
			//System.out.println(jdomex.getMessage());
		  }
		actualizarLista_PlatillosNuncaPedidos(pAlimento_Antiguo); // Elimina el 'viejo'
	}

	/**
	 * Actualiza la lista de pedidos nunca pedidos, borrando el ingresado en la firma del metodo.
	 * @param pAlimento
	 */
	public static void actualizarLista_PlatillosNuncaPedidos(Alimento pAlimento){

		int i = 0;
		while(i < platilloNuncaPedidos.size()){

			if(platilloNuncaPedidos.get(i).getNombre().equals(pAlimento.getNombre()) &&
					platilloNuncaPedidos.get(i).getCodigo().equals(pAlimento.getCodigo())){

				platilloNuncaPedidos.remove(i);
			}
			i++;
		}
	}

	public static ArrayList<Alimento> getPlatilloNuncaPedidos(){
		return platilloNuncaPedidos;
	}

	/**
	 * Agrega un nuevo alimento a las lista Top-10.
	 * @param pAlimento
	 */
	public static void agregarALista_PlatillosTop10(Alimento pAlimento){
		int i = 0; int a = 0; boolean esta = false;
		Alimento alimentoTemp = null; int menor = 0;

		if(platillosTop10.size() != 0){

		// -------------- Limite es 10, se elimina uno -------------- //

			menor = platillosTop10.get(0).getCantidad();

			if(platillosTop10.size() == 10){

				while(i < platillosTop10.size()){

					if(menor > platillosTop10.get(i).getCantidad()){
						menor = platillosTop10.get(i).getCantidad();
						a = i;
					}
					i++;
				}
				platillosTop10.remove(a);
				i = 0;
			}
		// ---------------------------------------------------------- //

			while(i < platillosTop10.size()){
				if(platillosTop10.get(i).getNombre().equals(pAlimento.getNombre()) &&
						platillosTop10.get(i).getCodigo().equals(pAlimento.getCodigo())){

					esta = true;
					alimentoTemp = platillosTop10.get(i);
					alimentoTemp.setCantidad(1);

					platillosTop10.remove(i);
					platillosTop10.add(alimentoTemp);
				}
				i++;
			}

			if(!esta){
				pAlimento.setCantidad(1);
				platillosTop10.add(pAlimento);
			}
		}
		else{
			// Entra cuando es la primera vez que se inserta un alimento en el top
			pAlimento.setCantidad(1);
			platillosTop10.add(pAlimento);
		}
	}

	public static ArrayList<Alimento> getProductosTop10(){
		return platillosTop10;
	}

	public void mostrarMenuPorDisponibilidad(){

	}

}
